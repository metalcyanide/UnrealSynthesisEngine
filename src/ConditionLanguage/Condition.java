package ConditionLanguage;

import java.util.ArrayList;
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
   */
  public Condition(Scanner readFrom) throws Exception {
    StringBuilder condString = new StringBuilder();

    // Read "{|"
    if (!readFrom.next().equals("{") || !readFrom.next().equals("|")) {
      // TODO We could make a parse exception class if we wanted.
      throw new Exception("bad condtion");
    }

    // Extract condition string
    String n = readFrom.next();
    while (!n.equals("|")) {
      condString.append(n);
      n = readFrom.next();
    }

    // Read "|}"
    if (!readFrom.next().equals("}")) {
      throw new Exception("bad condtion");
    }
    
    this.condition = condString.toString();
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

  public Condition subs(String newVar, String oldVar) {
    // TODO Implement this
    return this;
  }

  public Condition existentialBind(String newVar) {
    // TODO Implement this
    return this;
  }

  public Condition and(Condition b) {
    // TODO Implement this
    return this;
  }

  public String getET() {
    // TODO Implement this
    return "fish";
  }

  public String getNextET() {
    // TODO Implement this
    return "fish";
  }

  public String getBT() {
    // TODO Implement this
    return "fish";
  }

  public String getNextBT() {
    // TODO Implement this
    return "fish";
  }
  
  public String getFreshVar() {
    // TODO Implement this
    return "fish";
  }
  
  public ArrayList<String> getVars() {
    // TODO Implement this
    return null;
  }

  // TODO fill in everything then have this be equivalence, perhaps?
  @Override
  public boolean equals(Object c){
    return true;
  }
}
