package GrammarsLanguage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import GrammarsLanguage.GrammarParser;

public class Program implements IProgram {
    // Singleton class
    private static ArrayList<String> terminals = new ArrayList<String>(Arrays.asList("True", "False", "0", "1"));
    private static ArrayList<String> binaryOperators = new ArrayList<String>(Arrays.asList("+", "-", "*", "/", "^", "<", "==", ":=", ";"));
    private static ArrayList<String> condStatements = new ArrayList<String>(Arrays.asList("ITE", "while"));

    private static Program instance = null;
    private IProgram[] productions;
    private Program() { }
    public static Program getInstance() {
        if(instance == null) {
            instance = new Program();
        }
        return instance;
    }

    private Program[] children; // Children of the Program
    private String nodeType;
    private String varOrTermName;
    public static HashMap<String, IProgram> mapPrograms = new HashMap<String, IProgram>();

    /**
     * Generic constructor used in factories.
     */
    private Program(String nodeType, Program[] children) {
        this.children = children;
        this.nodeType = nodeType;
        this.varOrTermName = "";
    }

    private Program(String nodeType, Program[] children, String varOrTermName) {
        this.children = children;
        this.nodeType = nodeType;
        this.varOrTermName = varOrTermName;
    }


    /**
    * Given a scanner with a string representation of a proof (single line), constructs a proof object describing said proof.
    * Should use the parse method of IULTriples. Haven't added contexts yet...
    */
//    public static IProgram parseGrammar(Scanner readFrom) throws Exception {
//        IProgram parsedGrammarLine = null;
//        //parses all grammar lines
//        GrammarParser.ParserObject info = GrammarParser.getInstance().parseGrammarLine(readFrom.nextLine());
//        Program[] children = new Program[info.productionRules.size()];
//        for(int i = 0; i < info.productionRules.size(); i++) {
//            GrammarParser.Node production = info.productionRules.get(i);
//            ArrayList<String> nonTerminals = info.nonTerminals;
//            children[i] = new Program(production, nonTerminals);
//        }
//
//        GrammarParser.Node root = GrammarParser.getInstance().new Node(info.nonTerminal);
//        // GrammarParser.updateMap(info.nonTerminal, children);
//        parsedGrammarLine = new Program(root, children, nonTerminals);
//        boolean checkKey = mapPrograms.containsKey(root.value);
//
//        if(checkKey == false){
//            mapPrograms.put(root.value, parsedGrammarLine);
//        }
//        return parsedGrammarLine;
//    }

//    public static Program getProgramForChild(GrammarParser.Node production, ArrayList<String> nonTerminals) {
//        Program[] children = new Program[2];
//        if(production.first == null ){
//            children[0] = null;
//            children[1] = null;
//            return new Program(production, children, nonTerminals);
//        }
//        else if(production.second == null) {
//            children[0] = new Program(production.first, nonTerminals);
//            children[1] = null;
//            return new Program(production, children, nonTerminals);
//        }
//        else if(production.second != null && production.second.first != null){
//            children[0] = new Program(production.first, nonTerminals);
//            children[1] = getProgramForChild(production.second, nonTerminals);
//            return new Program(production, children, nonTerminals);
//        }
//
//        return new Program(production, children, nonTerminals);
//    }

    public static IProgram parseStatement(Scanner readFrom) throws Exception {
        pullScan(readFrom, "{");
        Program retval = parseFrag(readFrom);
        pullScan(readFrom, "}");
        return retval;
    }

    // Expect (prog) and parse recursively
    private static Program parseFrag(Scanner readFrom) throws Exception {
        if (!readFrom.findInLine(".").equals("(")) {
            System.out.println("bad prog frag");
            return null;
        }
        String body = readFrom.findInLine(".");
        // Cases where terminal
        if (terminals.contains(body)) {
            pullScan(readFrom, ")");
            switch (body) {
                case "F":
                    return new Program("False", new Program[0]);
                case "T":
                    return new Program("True", new Program[0]);
                case "0":
                    return new Program("0", new Program[0]);
                case "1":
                    return new Program("1", new Program[0]);
            }
        }
        else if (binaryOperators.contains(body) || body.equals(":") || body.equals("=")) {
            Program l;
            Program r;
            switch (body) {
                case "+":
                    l = parseFrag(readFrom);
                    r = parseFrag(readFrom);
                    pullScan(readFrom, ")");
                    return new Program("Plus", new Program[] {l, r});
                case "-":
                    l = parseFrag(readFrom);
                    r = parseFrag(readFrom);
                    pullScan(readFrom, ")");
                    return new Program("Minus", new Program[] {l, r});
                case "*":
                    l = parseFrag(readFrom);
                    r = parseFrag(readFrom);
                    pullScan(readFrom, ")");
                    return new Program("Times", new Program[] {l, r});
                case "/":
                    l = parseFrag(readFrom);
                    r = parseFrag(readFrom);
                    pullScan(readFrom, ")");
                    return new Program("Div", new Program[] {l, r});
                case "^":
                    l = parseFrag(readFrom);
                    r = parseFrag(readFrom);
                    pullScan(readFrom, ")");
                    return new Program("And", new Program[] {l, r});
                case "<":
                    l = parseFrag(readFrom);
                    r = parseFrag(readFrom);
                    pullScan(readFrom, ")");
                    return new Program("Less", new Program[] {l, r});
                case ":":
                    pullScan(readFrom, "=");
                    l = parseFrag(readFrom);
                    r = parseFrag(readFrom);
                    pullScan(readFrom, ")");
                    return new Program("Assign", new Program[] {l, r});
                case "=":
                    pullScan(readFrom, "=");
                    l = parseFrag(readFrom);
                    r = parseFrag(readFrom);
                    pullScan(readFrom, ")");
                    return new Program("1", new Program[] {l, r});
                case ";":
                    l = parseFrag(readFrom);
                    r = parseFrag(readFrom);
                    pullScan(readFrom, ")");
                    return new Program("Seq", new Program[] {l, r});
            }
        }
        else if (condStatements.contains(body)) {
            return null;    // TODO
        }
        else {
            StringBuilder name = new StringBuilder();
            if (body.equals("v")) { // if var
                while (true) {
                    char n = readFrom.findInLine(".").charAt(0);
                    // System.out.println("Inside the loop");
                    if (n == ')') {
                        break;
                    }
                    name.append(n);
                }
                return new Program("Var", new Program[0], name.toString());
            }
            if (body.equals("n")) { // if nonterm
                while (true) {
                    char n = readFrom.findInLine(".").charAt(0);
                    // System.out.println("Inside the loop");
                    if (n == ')') {
                        break;
                    }
                    name.append(n);
                }
                return new Program("NonTerm", new Program[0], name.toString());
            }
        }
        System.out.println("unrecognizable prog frag");
        return null;
    }

    private static void pullScan(Scanner readFrom, String charac) throws Exception {
        if (!readFrom.findInLine(".").equals(charac)) {
            throw new Exception("unexpected character");
        }
    }
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Program)) {
            return false;
        }
        if(!this.nodeType.equals(((Program) o).nodeType)) {
            return false;
        }
        if(!this.varOrTermName.equals(((Program) o).varOrTermName)) {
            return false;
        }
        if(!(this.children == null && ((Program) o).children == null) && !Arrays.equals(this.children, ((Program) o).children)) {
            return false;
        }
        return true;
    }

    public String getNodeType(){
        return nodeType;
    }

    /*
    * Returns a list of the AST children of this node.
    */
    public IProgram[] getChildren(){
        return children;
    }

    /*
    * If this node is a non-terminal, returns a list of rhs production rules as programs.
    */
    public IProgram[] getProductionRHS(){
        boolean checkKey = mapPrograms.containsKey(varOrTermName);

        if(checkKey){
            return mapPrograms.get(varOrTermName).getChildren();
        }
        return null;
    }

    /*
    * Returns var as string if program is a var (e.g., x).
    * Returns lhs of an assignment.
    * Else, returns null.
    */
    public String getVarName(){
        if(this.nodeType.equals("Var") || this.nodeType.equals("NonTerm")) {
            return varOrTermName;
        }
        else if(this.nodeType.equals("Assign")) {
            return this.children[0].getVarName();
        }
        return null;
    }

    /*
    * Returns a set containing all program variables in the program
    */
    public ArrayList<String> getVars(){
        ArrayList<String> vars = new ArrayList<String>();
        if(this.nodeType.equals("Var")) {
            vars.add(this.varOrTermName);
        }
        else if (this.children != null) {
            for (Program child : this.children) {
                vars.addAll(child.getVars());
            }
        }
        return vars;
    }

    public static void printAST(GrammarParser.Node node){
        if(node == null){
            System.out.println("empty node");
            return;
        }
        else if(node.value != null){
            System.out.println("\nleaf node");
            System.out.println(node.value);
            System.out.println(node.nodeType);
            System.out.println("first node");
            printAST(node.first);
            System.out.println("second node");
            printAST(node.second);
            System.out.println("third node");
            printAST(node.third);
            System.out.println("\n");
            return;
        }
        else {
            System.out.println("\nfirst node");
            printAST(node.first);
            System.out.println("second node");
            printAST(node.second);
            System.out.println("third node");
            printAST(node.third);
            System.out.println("\n");
        }
        return;
    }

    /*
    * Test method for proof parsing
    */
    private static void main(String args[]) throws Exception{
        String statementExample = "{0; 0}";
        IProgram parsedInfo = parseStatement(new Scanner(statementExample));
        for (IProgram child : parsedInfo.getChildren()) {
            System.out.println(parsedInfo.getChildren().length);
//            printAST(child);
        }
        
        
    }
    
   
}
  