package ConditionLanguage;

import java.io.BufferedInputStream;

/*
   Interface for the P, Q condition objects of the logic.
 */
public class Condition implements ICondition<Condition>{

  private String condition;

  /*
   * Basic constructor.
   */
  public Condition (String stuff){
	condition = stuff;
  }


  /*
    Given a buffer to read from, reads condition from sequence of chars and returns a condition object.
    Should probably be a static constructor.
   */
  Condition parseCondition(BufferedInputStream readFrom) {
       return Condition("");
  }

  /*
    Checks if self implies b, given that self and b are of the same type.
   */
  boolean implies(Condtion b) {
	return true;
  }

  // We may need to be able to do transformations on conditions depending on what inference rules we support.
}
