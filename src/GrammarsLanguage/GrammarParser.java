package GrammarsLanguage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;
import Proofs.Claim.IClaim;

public class GrammarParser implements IGrammar {
  // Singleton class
  private static GrammarParser instance = null;
  private IProgram[] productions;
  private GrammarParser() { }
  public static GrammarParser getInstance() {
      if(instance == null) {
          instance = new GrammarParser();
      }
      return instance;
  }

  class ParserObject {
        HashMap<String, List<String>> productionRules;
        List<String> nonTerminals;

        public ParserObject(HashMap<String, List<String>> productionRules, List<String> nonTerminals) {
            this.productionRules = productionRules;
            this.nonTerminals = nonTerminals;
        }
  }
  
  // use this method to parse Grammar from a scanner
  // structure of production rule => A : A + B | E | A - B
  // note: very jank
  public ParserObject parseGrammarLine(String grammarLines) {
      // Get references for proof nodes that are required to prove this one
      HashMap<String, List<String>> productions = new HashMap<String, List<String>>();
      List<String> nonTerminals = new ArrayList<>();
      Scanner scan = new Scanner(grammarLines);
      while(scan.hasNextLine()){
        String result = scan.nextLine();
        String[] components = result.split(":");
        String nonTerminal = components[0];
        String productionRule = components[1];

        String[] productionRuleCompoments = productionRule.split("|");
        List<String> production = new ArrayList<>();
        for (String rule : productionRuleCompoments){
            production.add(rule);
        }
        productions.put(nonTerminal, production);
      } 


      return new ParserObject(productions, nonTerminals);
  }

   public <nonTerminal extends IProgram> IProgram[] getProductions(nonTerminal t){
        return productions;
  }
  

  /*
   * Test method for proof parsing
   */
  private static void main(String args[]) {
      String grammarExample = "A : A + B | B - C | E";
      GrammarParser.getInstance().parseGrammarLine(grammarExample);
  }
  
   
}
  