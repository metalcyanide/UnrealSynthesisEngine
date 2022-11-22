package ConditionLanguage;

import java.util.ArrayList;

/*
   Interface for the P, Q condition objects of the logic.
   Should never be two variables a & b s.t. a = b_1, b_2, b_1', b_2' 
 */
public interface ICondition {
  /*
    Checks if self implies b, given that self and b are of the same type.
    Null if type mismatch. 
    TODO find better fix
   */
  boolean implies(ICondition b);

  /*
   * Return condition with new variables substituted in place of old variables.
   * Assume lists of same size.
   */
  ICondition subs(ArrayList<String> newVar, ArrayList<String> oldVar);

  /*
   * Given a new condition, determines if this condition is syntactially equivalent 
   * (up to commutative and associative operations) to newCondition, with the variables 
   * oldVars in this condition substituted for *distinct* variables. If so, returns a list
   * of the new vars in newCondition corresponding to the old vars, in the order given by 
   * oldVars. Else, returns null.
   */
  ArrayList<String> getSubs(ArrayList<String> oldVars, ICondition newCondition);

  /*
   * True if newCondition is syntactically equivalent (up to commutative and associative
   * operations) to this condition with oldvars substitued. False else.
   */
  boolean isSubs(ArrayList<String> oldVars, ICondition newCondition);

  /*
   * Return condition with new variables existentially quantified.
   */
  ICondition existentialBind(ArrayList<String> newVars);

  /*
   * Returns this ^ b.
   * Null if type mismatch
   */
  ICondition and(ICondition b);

  /*
   * Returns list of the names of current variables used for e_t (since e_t is [we think] a vector in some cases)
   */
  ArrayList<String> getETs();

  // /* DEPRECATED FOR NOW
  //  * Returns name of next variable that would be used for e_t
  //  */
  // String getNextET();
 
  /*
   * Returns list of the names of current variables used for b_t (since b_t is [we think] a vector in some cases)
   */
  ArrayList<String> getBTs();

  /*
   * Returns list of vars used for value of a given program variable x
   */
  ArrayList<String> getVarsByName(String x);
  
  // /* DEPRECATED FOR NOW
  //  * Returns name of next variable that would be used for b_t
  //  */
  // String getNextBT();

  /*
   * Returns a list of n unused variable names.
   * Should return distinct variables on every call. 
   *    *May relax this later, but it makes things easier to write in ProofFactory.
   */
  ArrayList<String> getFreshVars(int n);

  /*
   * Returns a list of all vars that appear in the condition 
   */
  ArrayList<String> getVars();
}
