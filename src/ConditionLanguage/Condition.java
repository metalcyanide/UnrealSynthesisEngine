package ConditionLanguage;

import ConditionLanguage.Expressions.Boolean.*;
import ConditionLanguage.Expressions.Expr;
import ConditionLanguage.Expressions.Integer.*;

import java.util.ArrayList;
import java.util.Scanner;

/*
   Interface for the P, Q condition objects of the logic.
 */
public class Condition implements ICondition{

  private String condition;
  private Expr root;

  /*
   * Basic constructor.
   */
  public Condition (String stuff) //throws Exception
  {
//	  this(new Scanner(stuff)); //todo
    this.condition = stuff;
  }
  /*
    Given a buffer to read from, reads condition from sequence of chars and returns a condition object.
   */
  public Condition(Scanner readFrom) throws Exception {
    StringBuilder condString = new StringBuilder();

    // Read "{|"
    if (readFrom.findInLine(".").charAt(0) != '{' || readFrom.findInLine(".").charAt(0) !='|') {
      // TODO We could make a parse exception class if we wanted.
      throw new Exception("bad condtion");
    }

    // Extract condition string
    char n = readFrom.findInLine(".").charAt(0);
    while (n != '|') {
      condString.append(n);
      n = readFrom.findInLine(".").charAt(0);
    }

    // Read "|}"
    if (readFrom.findInLine(".").charAt(0) != '}') {
      throw new Exception("bad condtion");
    }

    this.condition = condString.toString();
    root = parseConditionString(condString.toString());
  }

  /*
   * Internal constructor used for functional uses
   */
  private Condition(Expr newRoot, String newCondition) {
    this.condition = newCondition;
    this.root = newRoot;
  }

  /*
   * Recursive procedure to take a string and construct an AST
   * representing the condition. Returns root of the AST.
   */
  private Expr parseConditionString(String condString) {
    // check valid condition
    if(condString == null || condString.length() == 0) {
      throw new IllegalStateException("empty string on condition parsing");
    }

    if(condString.charAt(0) != '(' || condString.charAt(condString.length()-1) != ')') {
      throw new IllegalArgumentException("invalid parenthesis on condition input");
    }

    // split up condition into command and args
    String subCond = condString.substring(1, condString.length()-1);
    String command = subCond.substring(0, subCond.indexOf(" ")).replaceAll("\\s", "");
    String remaining = subCond.substring(subCond.indexOf(" ")+1);

    // figure out where args are split
    int term1 = 0;
    int parenCount = 0;
    char[] remainArray = remaining.toCharArray();
    do {
      if(remainArray[term1] == '(') parenCount++;
      if(remainArray[term1] == ')') parenCount--;
      term1++;
    } while(parenCount > 0);

    // create AST (recursively) based on parsing
    switch(command) {
      case "INTCONST": {
        int value = Integer.parseInt(remaining);
        return new IntConstExpr(value);
      }
      case "BOOLCONST": {
        if(remaining.equals("T")) return new BoolConstExpr(true);
        else if (remaining.equals("F")) return new BoolConstExpr(false);
        else throw new IllegalArgumentException("BoolConst requires either T or F");
      }
      case "INTVAR": {
        return new IntVarExpr(remaining);
      }
      case "BOOLVAR": {
        return new BoolVarExpr(remaining);
      }
      case "NOT": {
        Expr child = parseConditionString(remaining);
        return new NotExpr(child);
      }
      case "AND": {
        Expr child1 = parseConditionString(remaining.substring(0, term1));
        Expr child2 = parseConditionString(remaining.substring(term1+1));
        return new AndExpr(child1, child2);
      }
      case "OR": {
        Expr child1 = parseConditionString(remaining.substring(0, term1));
        Expr child2 = parseConditionString(remaining.substring(term1+1));
        return new OrExpr(child1, child2);
      }
      case "+": {
        Expr child1 = parseConditionString(remaining.substring(0, term1));
        Expr child2 = parseConditionString(remaining.substring(term1+1));
        return new PlusExpr(child1, child2);
      }
      case "=": {
        Expr child1 = parseConditionString(remaining.substring(0, term1));
        Expr child2 = parseConditionString(remaining.substring(term1+1));
        return new EqualExpr(child1, child2);
      }
      default:
        throw new UnsupportedOperationException("Operation not supported: " + command);
    }
  }

  //TODO: interface with z3 (likely input IR of formula), return result query
  private boolean checkSmtQuery(Expr query) {
//    String z3_query = query.toSMT();
//    todo write z3_query to file
//    todo execute python3 on file
//    todo read in terminal result

    return true;
  }

  /*
    Checks if self implies b, given that self and b are of the same type.
   */
  public boolean implies(ICondition b) {
    Expr neg = new NotExpr(this.root);
    Expr query = new OrExpr(neg, ((Condition)b).root);
	return checkSmtQuery(query);
  }

  public String toString() {
    return condition; //todo possibly replace with pretty print in expr?
  }

  public Condition subs(ArrayList<String> newVar, ArrayList<String> oldVar) {
    // assumes lists are same size
    if(newVar.size() != oldVar.size()) {
      throw new IllegalArgumentException("length of lists inputted to Condition.subs() not same length");
    }

    for(int i = 0; i < newVar.size(); i++) {
      this.root.subVar(newVar.get(i), oldVar.get(i));
    }
    // note: method edits current condition, rather than returning functional new one
    return this;
  }

  public Condition existentialBind(ArrayList<String> newVar) {
    // TODO Implement this
    return this;
  }

  public Condition and(ICondition b) {
    Expr newRoot = new AndExpr(this.root, ((Condition)b).root);
    return new Condition(newRoot, "(And " + this.condition + " " + ((Condition) b).condition + ")");
  }

  public ArrayList<String> getETs() {
    // TODO Implement this
    return new ArrayList<String>();
  }

  public ArrayList<String> getBTs() {
    // TODO Implement this
    return new ArrayList<String>();
  }
  
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
    return true;
  }

  public ArrayList<String> getVarsByName(String x) {
    // TODO Auto-generated method stub
    return null;
  }
}
