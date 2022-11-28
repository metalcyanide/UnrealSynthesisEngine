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
    ArrayList<String> binaryOperators = new ArrayList<String>(Arrays.asList("+", "-", "x", "/", "^", "<", "=="));
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
    private ArrayList<String> nonTerminals; // List of all non-terminals
    private String nodeType;
    private GrammarParser.Node node;

    

    /**
     * Generic constructor used in factories.
     */
    private Program(GrammarParser.Node node, Program[] children, ArrayList<String> nonTerminals) {
        this.node = node;
        this.children = children;
        this.nonTerminals = nonTerminals;
        this.nodeType = node.nodeType;
        this.nonTerminals = nonTerminals; 
    }

    private Program(GrammarParser.Node node, ArrayList<String> nonTerminals) {
        this.node = node;
        this.nonTerminals = nonTerminals;
        this.nodeType = node.nodeType;
        this.nonTerminals = nonTerminals; 
    }

    /**
    * Given a scanner with a string representation of a proof (single line), constructs a proof object describing said proof.
    * Should use the parse method of IULTriples. Haven't added contexts yet...
    */
    public Program parseGrammar(Scanner readFrom) throws Exception {
        IProgram parsedProof = null;
        //parses all grammar lines
        GrammarParser.ParserObject info = GrammarParser.getInstance().parseGrammarLine(readFrom.nextLine());
        Program[] children = new Program[info.productionRules.size()];
        for(int i = 0; i < info.productionRules.size(); i++) {
            GrammarParser.Node production = info.productionRules.get(i);
            ArrayList<String> nonTerminals = info.nonTerminals.get(i);
            children[i] = Program(production, nonTerminals);
        }

        GrammarParser.Node root = new GrammarParser.Node(info.nonTerminal);
        // GrammarParser.updateMap(info.nonTerminal, children);
        return Program(root, children, nonTerminals);
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
        return productions;
    }

    /*
    * Returns var as string if program is a var (e.g., x).
    * Returns lhs of an assignment.
    * Else, returns null.
    */
    public String getVarName(){
        return node.value;
    }

    /*
    * Returns a set containing all program variables in the program
    */
    public ArrayList<String> getVars(){
        return nonTerminals;
    }

    /*
    * Test method for proof parsing
    */
    private static void main(String args[]) {
        String grammarExample = "A := A + B | B - C | E \n B := T | F | C";
    //   ParseObject parsedInfo = GrammarParser.getInstance().parseGrammarLines(grammarExample);
        
    }
    
   
}
  