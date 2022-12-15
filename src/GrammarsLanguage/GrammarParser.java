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
    ArrayList<String> binaryOperators = new ArrayList<String>(Arrays.asList("+", "-", "*", "/", "^", "<", "==", "While", ":="));
    ArrayList<String> condStatements = new ArrayList<String>(Arrays.asList("ITE"));

    HashMap<String, Program[]> nonTerminalMap = new HashMap<String, Program[]>();
    
    

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
            this.nodeType = setNodeType(value);
        }

        public boolean equals(Node o){
            if(this.value == o.value && this.nodeType == o.nodeType 
            && (o.first == null && this.first == null) 
            && (o.second == null && this.second == null) 
            && (o.third == null && this.third == null) ){
                return true;
            }
            else if(this.value == o.value && this.nodeType == o.nodeType 
            && this.first.equals(o.first)
            && this.second.equals(o.second)
            && this.third.equals(o.second)){
                return true;
            }
            return false;
        }
    
        Node(String value) {
            this.value = value;
            this.nodeType = setNodeType(value);
            this.first = null;
            this.second = null;
            this.third = null;
        }
        Node(String value, Node first) {
            this.value = value;
            this.nodeType = setNodeType(value);
            this.first = first;
            this.second = null;
            this.third = null;
        }
        Node(String value, Node first, Node second) {
            this.value = value;
            this.nodeType = setNodeType(value);
            this.first = first;
            this.second = second;
            this.third = null;
        }
        Node(String value, Node first, Node second, Node third) {
            this.value = value;
            this.nodeType = setNodeType(value);
            this.first = first;
            this.second = second;
            this.third = third;
        }
    }

    private String setNodeType(String nodeValue) {

        HashMap<String, String> typeMap = new HashMap<String, String>();
        typeMap.put("T", "True");
        typeMap.put("F", "False");
        typeMap.put("0", "0");
        typeMap.put("1", "1");
        typeMap.put("+", "Plus");
        typeMap.put("-", "Minus");
        typeMap.put("*", "Times");
        typeMap.put("/", "Div");
        typeMap.put("^", "And");
        typeMap.put("<", "Less");
        typeMap.put("==", "Equal");
        typeMap.put("if then else", "ITE");
        typeMap.put(":=", "Assign");
        typeMap.put(";", "Seq");
        typeMap.put("while do", "While");

        String nodeType = "";

        if(nodeValue == "") {
            return null;
        }

        if(Character.isUpperCase(nodeValue.toCharArray()[0])) {
            nodeType = "NonTerm";
        }
        else if(Character.isLowerCase(nodeValue.toCharArray()[0])) {
            nodeType = "var";
        }
        else {
            nodeType = typeMap.get(nodeValue);
        }

        return nodeType;
    }

    class ParserObject {

        String nonTerminal;
        ArrayList<Node> productionRules;
        ArrayList<String> nonTerminals;

        public ParserObject(String nonTerminal, ArrayList<Node> productionRules, ArrayList<String> nonTerminals) {
            this.nonTerminal = nonTerminal;
            this.productionRules = productionRules;
            this.nonTerminals = nonTerminals;
        }
    }

    public Node createNode(String[] ruleComponents) {
        String nodeValue = ruleComponents[0].strip();
        if (terminals.contains(nodeValue)){
            Node node = new Node(nodeValue);
            return node;
        }
        else if (binaryOperators.contains(nodeValue)) {
            Node nodefirst = new Node(ruleComponents[1].strip());
            Node nodesecond = new Node(ruleComponents[2].strip());
            Node node = new Node(nodeValue, nodefirst, nodesecond);
            return node;
        }
        else if (condStatements.contains(nodeValue)){
            Node nodefirst = new Node(ruleComponents[1].strip());
            Node nodesecond = new Node(ruleComponents[2].strip());
            Node nodethird = new Node(ruleComponents[3].strip());
            Node node = new Node(nodeValue, nodefirst, nodesecond, nodethird);
            return node;
        }

        // need to handle exception
        return new Node(nodeValue);
    }

    // use this method to parse Grammar from a scanner
    // structure of production rule => A ::= A + B | E | A - B
    public ParserObject parseGrammarLine(String grammarLine) {
        // Get references for proof nodes that are required to prove this one
        String regexSplitRHSLHS = "::=";
        String regexSplitProductionRules = ";;";
        String[] components = grammarLine.split(regexSplitRHSLHS);
        String nonTerminal = components[0].strip();
        String productionRule = components[1].strip();
        String[] productionRuleComponents = productionRule.split(regexSplitProductionRules);
        // System.out.println(Arrays.toString(productionRuleCompoments));
        ArrayList<String> nonTerminals = new ArrayList<>();
        ArrayList<Node> nodes = new ArrayList<>();

        for (String rule : productionRuleComponents){
            String[] ruleComponents = rule.strip().split( " ");
            Node node = createNode(ruleComponents);
            ArrayList<String> ruleCompList = new ArrayList<>(Arrays.asList(ruleComponents));
            nodes.add(node);
            for (String component: ruleCompList){
                // System.out.println(component);
                if(Character.isLowerCase(component.toCharArray()[0])){
                    if(!nonTerminals.contains(component)){
                        nonTerminals.add(component);
                    }
                }
            }     
        } 
        
        ParserObject parsedInfo = new ParserObject(nonTerminal, nodes, nonTerminals);
        return parsedInfo;
    }

    public GrammarParser.Node getNodeForStatement(String ruleLeft, String ruleRight, 
                                ArrayList<String> nonTerminals){
        String[] ruleLeftComponents = ruleLeft.strip().split( " ");
        Node nodeLeft = createNode(ruleLeftComponents);
        ArrayList<String> ruleCompList = new ArrayList<>(Arrays.asList(ruleLeftComponents));
        for (String component: ruleCompList){
            if(Character.isLowerCase(component.toCharArray()[0])){
                if(!nonTerminals.contains(component)){
                    nonTerminals.add(component);
                }
            }
        }   

        if (ruleRight == null) {
            return nodeLeft;
        }

        String[] productionRuleComponents = ruleRight.strip().split(";", 2);

        if(productionRuleComponents.length == 1) {
            String[] ruleRightComponents = ruleLeft.strip().split( " ");
            Node nodeRight = createNode(ruleRightComponents);
            ruleCompList = new ArrayList<>(Arrays.asList(ruleLeftComponents));
            for (String component: ruleCompList){
                if(Character.isLowerCase(component.toCharArray()[0])){
                    if(!nonTerminals.contains(component)){
                        nonTerminals.add(component);
                    }
                }
            }  
            return new Node(productionRuleComponents[0], nodeLeft, nodeRight);
        }
        else {
            Node nodeRight = getNodeForStatement(productionRuleComponents[0], productionRuleComponents[1], nonTerminals);
            return new Node(";", nodeLeft, nodeRight);
        }
    }

    public ParserObject parseStatementLine(String statementLine) {
        // Get references for proof nodes that are required to prove this one
        String regexSplitRHSLHS = ";";
        // System.out.println(statementLine);
        // System.out.println(statementLine.strip());
        String[] productionRuleComponents = statementLine.strip().split(regexSplitRHSLHS, 2);
        // System.out.println(Arrays.toString(productionRuleComponents));
        // System.out.println(productionRuleComponents.length);

        ArrayList<String> nonTerminals = new ArrayList<>();
        ArrayList<Node> nodes = new ArrayList<>();
        String nonTerminal = ";";
        if (productionRuleComponents.length == 1 || productionRuleComponents[1].strip() == "" ) {
            
            nonTerminal = productionRuleComponents[0];
            Node node;
            if(productionRuleComponents[0].strip() == ""){
                node = new Node();
            }
            else {
                node = getNodeForStatement(productionRuleComponents[0], null, nonTerminals);
            }
            nodes.add(node);
        }
        else if(productionRuleComponents.length == 2){
            Node node = getNodeForStatement(productionRuleComponents[0], productionRuleComponents[1], nonTerminals);
            nodes.add(node.first);
            nodes.add(node.second);
        }
        
        ParserObject parsedInfo = new ParserObject(nonTerminal, nodes, nonTerminals);
        return parsedInfo;
    }

    public IProgram[] getProductions(String t){
        return nonTerminalMap.get(t);
    }

    /*
    * Test method for proof parsing
    */
    public static void main(String args[]) {
        String grammarExample = "A ::= + x B ;; - B C ;; E";
        // String grammarExample = "A ::= A + B | B - C | E \n B := T | F | C";
        GrammarParser.ParserObject parsedInfo = GrammarParser.getInstance().parseGrammarLine(grammarExample);
        System.out.println(parsedInfo.productionRules.get(0));
        
    }
   
}
  