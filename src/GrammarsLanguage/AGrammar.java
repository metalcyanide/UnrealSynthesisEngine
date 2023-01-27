package GrammarsLanguage;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * A mapping from non-terminals to sets of partial programs.
 * Is abstract bc cannot initialize objects of type P.
 * @param <P> The type of program contained in the grammar -- should usually be program
 */
public abstract class AGrammar<P extends IProgram> {

  private HashMap<String, LinkedList<P>> gram;

  public AGrammar(String grammarString) throws Exception {
    this();
  }

  public AGrammar() {
      gram = new HashMap<String, LinkedList<P>>();
  }

  /**
   * Adds a new nonterm, expansions pair to the grammar
   * @param nonTerm The nonterminal to add
   * @param expansions  The expansions to add
   * @return  true if add successful, false else
   */
  void add(String nonTerm, LinkedList<P> expansions) throws Exception {
    gram.put(nonTerm, expansions);
  }

  public LinkedList<P> getExpansions(P nonTerm) {
    return gram.get("(n" + nonTerm.getVarName() + ")");
  }
}
