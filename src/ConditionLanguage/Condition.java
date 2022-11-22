package ConditionLanguage;

import java.util.ArrayList;
import java.util.Scanner;

/*
   Interface for the P, Q condition objects of the logic.
 */
public class Condition implements ICondition{

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
  public boolean implies(ICondition b) {
	return true;
  }

  public String toString() {
    return condition;
  }

  public Condition subs(ArrayList<String> newVar, ArrayList<String> oldVar) {
    // TODO Implement this
    return this;
  }

  public Condition existentialBind(ArrayList<String> newVar) {
    // TODO Implement this
    return this;
  }

  public Condition and(ICondition b) {
    // TODO Implement this
    return this;
  }

  public ArrayList<String> getETs() {
    // TODO Implement this
    return new ArrayList<String>();
  }

  // public String getNextET() {
  //   // TODO Implement this
  //   return "fish";
  // }

  public ArrayList<String> getBTs() {
    // TODO Implement this
    return new ArrayList<String>();
  }

  // public String getNextBT() {
  //   // TODO Implement this
  //   return "fish";
  // }
  
  public ArrayList<String> getFreshVars(int n) {
    // TODO Implement this
    return new ArrayList<String>();
  }
  
  public ArrayList<String> getVars() {
    // TODO Implement this
    return new ArrayList<String>();
  }

  // TODO fill in everything then have this be equivalence, perhaps? Or syntactic equality up to commutative/ associative ops
  @Override
  public boolean equals(Object c){
    return true;
  }
  
  public ArrayList<String> getSubs(ArrayList<String> oldVars, ICondition oldCondition) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isSubs(ArrayList<String> oldVars, ICondition oldCondition) {
    // TODO Auto-generated method stub
    return true;
  }

  public ArrayList<String> getVarsByName(String x) {
    // TODO Auto-generated method stub
    return null;
  }
}
