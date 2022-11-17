package GrammarsLanguage;

import java.util.ArrayList;

/*
   A little messy, but this should be sufficient to get us the info we need.
   An aesthetic redesign can be done later, if ever.
 */
public interface IProgram {
 /*
  * Returns the type of the program (root of AST) as one of the following:
  * True - T
  * False - F
  * 0 - 0
  * 1 - 1
  * Plus - +
  * Minus - -
  * Times - x
  * Div - /
  * And - ^
  * Less - <
  * Equal - ==
  * ITE - if then else
  * Assign - :=
  * Seq - ;
  * While - while do
  * NonTerm - T (non-terminal symbol)
  */
  public String getNodeType();

  /*
   * Returns a list of the AST children of this node.
   */
  public IProgram[] getChildren();

  /*
   * If this node is a non-terminal, returns a list of rhs production rules as programs.
   */
  public IProgram[] getProductionRHS();

  /*
   * Returns var as string if program is a var (e.g., x).
   * Returns lhs of an assignment.
   * Else, returns null.
   */
  public String getVarName();

  /*
  * Returns a set containing all program variables in the program
  */
  public ArrayList<String> getVars();
}
