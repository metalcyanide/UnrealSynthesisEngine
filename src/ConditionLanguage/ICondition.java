package ConditionLanguage;

import java.util.Set;

/*
   Interface for the P, Q condition objects of the logic.
 */
public interface ICondition <T extends ICondition<T>> {
  /*
    Checks if self implies b, given that self and b are of the same type.
   */
  boolean implies(T b);

  /*
   * Return condition with new variable substituted in place of old variable.
   */
  T subs(String newVar, String oldVar);

  /*
   * Return condition with new variable existentially quantified.
   */
  T existentialBind(String newVar);

  /*
   * Returns this ^ b.
   */
  T and(T b);

  /*
   * Returns name of current variable used for e_t
   */
  String getET();

  /*
   * Returns name of next variable that would be used for e_t
   */
  String getNextET();
 
  /*
   * Returns name of current variable used for b_t
   */
  String getBT();
  
  /*
   * Returns name of next variable that would be used for b_t
   */
  String getNextBT();

  /*
   * Returns an unused variable name 
   */
  String getFreshVar();

  /*
   * Returns a list of all vars that appear in the condition 
   */
  Set<String> getVars();
}
