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
    ArrayList<String> terminals = new ArrayList<String>(Arrays.asList("True", "False", "0", "1"));
    ArrayList<String> binaryOperators = new ArrayList<String>(Arrays.asList("+", "-", "*", "/", "^", "<", "==", ":="));
    ArrayList<String> condStatements = new ArrayList<String>(Arrays.asList("ITE", "while"));

    private static Program instance = null;
    private IProgram[] productions;
    private Program() { }
    public static Program getInstance() {
        if(instance == null) {
            instance = new Program();
        }
        return instance;
    }

    private Program[] children; // Children of the Production Rule
    private static ArrayList<String> nonTerminals; // List of all non-terminals
    private String nodeType;
    private GrammarParser.Node node;
    public static HashMap<String, IProgram> mapPrograms = new HashMap<String, IProgram>();

    /**
     * Generic constructor used in factories.
     */
    private Program(GrammarParser.Node node, Program[] children, ArrayList<String> nonTerminals) {
        this.node = node;
        this.children = children;
        this.nonTerminals = nonTerminals;
        this.nodeType = node.nodeType; 
    }

    private Program(GrammarParser.Node node, ArrayList<String> nonTerminals) {
        this.node = node;
        this.children = null;
        this.nonTerminals = nonTerminals;
        this.nodeType = node.nodeType;
    }

    /**
    * Given a scanner with a string representation of a proof (single line), constructs a proof object describing said proof.
    * Should use the parse method of IULTriples. Haven't added contexts yet...
    */
    public static IProgram parseGrammar(Scanner readFrom) throws Exception {
        IProgram parsedGrammarLine = null;
        //parses all grammar lines
        GrammarParser.ParserObject info = GrammarParser.getInstance().parseGrammarLine(readFrom.nextLine());
        Program[] children = new Program[info.productionRules.size()];
        for(int i = 0; i < info.productionRules.size(); i++) {
            GrammarParser.Node production = info.productionRules.get(i);
            ArrayList<String> nonTerminals = info.nonTerminals;
            children[i] = new Program(production, nonTerminals);
        }

        GrammarParser.Node root = GrammarParser.getInstance().new Node(info.nonTerminal);
        // GrammarParser.updateMap(info.nonTerminal, children);
        parsedGrammarLine = new Program(root, children, nonTerminals);
        boolean checkKey = mapPrograms.containsKey(root.value);

        if(checkKey == false){
            mapPrograms.put(root.value, parsedGrammarLine);
        }
        return parsedGrammarLine;
    }

    public static Program getProgramForChild(GrammarParser.Node production, ArrayList<String> nonTerminals) {
        Program[] children = new Program[2];
        if(production.first == null ){
            children[0] = null;
            children[1] = null;
            return new Program(production, children, nonTerminals);
        }
        else if(production.second == null) {
            children[0] = new Program(production.first, nonTerminals);
            children[1] = null;
            return new Program(production, children, nonTerminals);
        }
        else if(production.second != null && production.second.first != null){
            children[0] = new Program(production.first, nonTerminals);
            children[1] = getProgramForChild(production.second, nonTerminals);
            return new Program(production, children, nonTerminals);
        }

        return new Program(production, children, nonTerminals);
    }

    public static IProgram parseStatement(Scanner readFrom) throws Exception {
        StringBuilder stmtString = new StringBuilder();
        IProgram parsedStatement = null;

        if (readFrom.findInLine(".").charAt(0) != '{') {
            throw new Exception("bad program");
        }
        while (true) {
            char n = readFrom.findInLine(".").charAt(0);
            // System.out.println("Inside the loop");
            if (n == '}') {
                break;
            }
            stmtString.append(n);
        }

        GrammarParser.ParserObject info = GrammarParser.getInstance().parseStatementLine(stmtString.toString());
        Program[] children = new Program[info.productionRules.size()];

        if(info.productionRules.size() == 1) {
            children[0] = getProgramForChild(info.productionRules.get(0), nonTerminals);
            return children[0];
        }
        else {
            children[0] = getProgramForChild(info.productionRules.get(0), nonTerminals);
            children[1] = getProgramForChild(info.productionRules.get(1), nonTerminals);
        }
        GrammarParser.Node root = GrammarParser.getInstance().new Node(info.nonTerminal);
        parsedStatement = new Program(root, children, nonTerminals);

        return parsedStatement;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Program)) {
            return false;
        }
        if(this.node.equals(((Program)o).node) 
        && this.nonTerminals ==((Program)o).nonTerminals
        && this.nodeType ==((Program)o).nodeType){
            if(this.children == null && !(((Program)o).children == null)){
                return false;
            }
            else if(Arrays.equals(this.children, ((Program)o).children)){
                return true;
            }

        }
        return false;
    }

    public String getNodeType(){
        return nodeType;
    }

    public GrammarParser.Node getNode(){
        return node;
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
        boolean checkKey = mapPrograms.containsKey(node.value);

        if(checkKey){
            productions = mapPrograms.get(node.value).getChildren();
        }
        else {
            productions = null;
        }
        return productions;
    }

    /*
    * Returns var as string if program is a var (e.g., x).
    * Returns lhs of an assignment.
    * Else, returns null.
    */
    public String getVarName(){
        if(node.nodeType == "var") {
            return node.value;
        }
        else if(node.nodeType == "Assign") {
            return node.first.value;
        }
        return null;
    }

    /*
    * Returns a set containing all program variables in the program
    */
    public ArrayList<String> getVars(){
        return nonTerminals;
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
            printAST(child.getNode());
        }
        
        
    }
    
   
}
  