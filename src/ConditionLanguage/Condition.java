package ConditionLanguage;

import java.io.IOException;
import java.util.Scanner;

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
  public Condition parseCondition(Scanner readFrom) {
    StringBuilder condString = new StringBuilder();

    // Read "{|"
    if (!readFrom.next().equals("{") || !readFrom.next().equals("|")) {
      // TODO We could make a parse exception class if we wanted.
      return null;
    }

    // Extract condition string
    String n = readFrom.next();
    while (!n.equals("|")) {
      condString.append(n);
      n = readFrom.next();
    }

    // Read "|}"
    if (!readFrom.next().equals("}")) {
      return null;
    }
    
    return new Condition(condString.toString());
  }

  /*
    Checks if self implies b, given that self and b are of the same type.
   */
  public boolean implies(Condition b) {
	return true;
  }

  public String toString() {
    return condition;
  }

  // We may need to be able to do transformations on conditions depending on what inference rules we support.
}
