package GrammarsLanguage;

import java.io.File;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import Proofs.Claim.IClaim;

public class GrammarParser implements IGrammar {
    // Singleton class
    ArrayList<String> terminals = new ArrayList<String>(Arrays.asList("True", "False", "0", "1"));
    ArrayList<String> binaryOperators = new ArrayList<String>(Arrays.asList("+", "-", "*", "/", "^", "<", "==", "While"));
    ArrayList<String> condStatements = new ArrayList<String>(Arrays.asList("ITE"));
    
    private HashMap<String, Program[]> nonTerminalMap = new HashMap<String, Program[]>();

    private static GrammarParser instance = null;
    private GrammarParser() { }
    public static GrammarParser getInstance() {
        if(instance == null) {
            instance = new GrammarParser();
        }
        return instance;
    }

    class Node {
        String value;
        String nodeType;
        Node first;
        Node second;
        Node third;

        Node() {
            this.value = null;
            this.nodeType = null;
            this.first = null;
            this.second = null;
            this.third = null;
        }

        //need to do these updates
        public void updateNodeValue(String value) {
            this.value = value;
            this.nodeType = getNodeType(value);
        }
    
        Node(String value) {
            this.value = value;
            this.nodeType = getNodeType(value);
            this.first = null;
            this.second = null;
            this.third = null;
        }
        Node(String value, Node first) {
            this.value = value;
            this.nodeType = getNodeType(value);
            this.first = first;
            this.second = null;
            this.third = null;
        }
        Node(String value, Node first, Node second) {
            this.value = value;
            this.nodeType = getNodeType(value);
            this.first = first;
            this.second = second;
            this.third = null;
        }
        Node(String value, Node first, Node second, Node third) {
            this.value = value;
            this.nodeType = getNodeType(value);
            this.first = first;
            this.second = second;
            this.third = third;
        }
    }

    private String getNodeType(String nodeValue) {

        String nodeType = "";
        if (terminals.contains(nodeValue)){
            nodeType = "Terminal";
        }
        else if (binaryOperators.contains(nodeValue)) {
            nodeType = "BinaryOP";
        }
        else if (condStatements.contains(nodeValue)){
            nodeType = "TertiaryOP";
        }

        return nodeType;
    }

    class ParserObject {

        String nonTerminal;
        ArrayList<Node> productionRules;
        ArrayList<ArrayList<String>> nonTerminals;

        public ParserObject(String nonTerminal, ArrayList<Node> productionRules, ArrayList<ArrayList<String>> nonTerminals) {
            this.nonTerminal = nonTerminal;
            this.productionRules = productionRules;
            this.nonTerminals = nonTerminals;
        }
    }

    public Node createNode(String[] ruleComponents) {
        String nodeValue = ruleComponents[0];
        if (terminals.contains(nodeValue)){
            Node node = new Node(nodeValue);
            return node;
        }
        else if (binaryOperators.contains(nodeValue)) {
            Node nodefirst = new Node(ruleComponents[1]);
            Node nodesecond = new Node(ruleComponents[2]);
            Node node = new Node(nodeValue, nodefirst, nodesecond);
            return node;
        }
        else if (condStatements.contains(nodeValue)){
            Node nodefirst = new Node(ruleComponents[1]);
            Node nodesecond = new Node(ruleComponents[2]);
            Node nodethird = new Node(ruleComponents[3]);
            Node node = new Node(nodeValue, nodefirst, nodesecond, nodethird);
            return node;
        }

        // need to handle exception
        return new Node(" ");
    }

    // use this method to parse Grammar from a scanner
    // structure of production rule => A ::= A + B | E | A - B
    public ParserObject parseGrammarLine(String grammarLine) {
        // Get references for proof nodes that are required to prove this one
        String regexSplitRHSLHS = "::=";
        String[] components = grammarLine.split(regexSplitRHSLHS);
        String nonTerminal = components[0];
        String productionRule = components[1];
        String[] productionRuleCompoments = productionRule.split("|");
        ArrayList<ArrayList<String>> nonTerminals = new ArrayList<>();
        ArrayList<Node> nodes = new ArrayList<>();
        for (String rule : productionRuleCompoments){
            String[] ruleComponents = rule.split(" ");
            Node node = createNode(ruleComponents);
            ArrayList<String> ruleCompList = new ArrayList<>(Arrays.asList(ruleComponents));
            ruleCompList.remove(0);
            nodes.add(node);
            nonTerminals.add(ruleCompList);
        } 
        
        return new ParserObject(nonTerminal, nodes, nonTerminals);
    }

    public void updateMap(String t, Program[] children){
        nonTerminalMap.put(t, children);
    }

    public IProgram[] getProductions(String t){
        return nonTerminalMap.get(t);
    }

    /*
    * Test method for proof parsing
    */
    private static void main(String args[]) {
        String grammarExample = "A := A + B | B - C | E \n B := T | F | C";
        GrammarParser.ParserObject parsedInfo = GrammarParser.getInstance().parseGrammarLine(grammarExample);
        
    }
  
   
}
  