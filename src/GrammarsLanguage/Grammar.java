package GrammarsLanguage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Stream;

public class Grammar {

  private HashMap<String, LinkedList<Program>> gram;


  public Grammar(String grammarString) throws Exception {
    gram = new HashMap<String, LinkedList<Program>>();
    for (String line: grammarString.split(System.getProperty("line.separator"))) {
      if (line.equals("")) continue;
      if (line.split("::=").length != 2) throw new Exception("Grammar line " + line + "has incorrect number of ::=");
      String nonTerminal = line.split("::=")[0];
      LinkedList<Program> expansions = new LinkedList<Program>();
      for ( String expansion : line.split("::=")[1].split(" ")  // Lines of the form (nS)::={P1} {P2} {P3}...\n
      ) {
        expansions.add(Program.parseStatement(new Scanner(expansion), this));
      }
      this.add(nonTerminal, expansions);
    }
  }

  /**
   * Adds a new nonterm, expansions pair to the grammar
   * @param nonTerm The nonterminal to add
   * @param expansions  The expansions to add
   * @return  true if add successful, false else
   */
  public void add(String nonTerm, LinkedList<Program> expansions) throws Exception {
    gram.put(nonTerm, expansions);
  }

  public LinkedList<Program> getExpansions(Program nonTerm) {
    return gram.get("(n" + nonTerm.getVarName() + ")");
  }
}
