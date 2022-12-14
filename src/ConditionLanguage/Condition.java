package ConditionLanguage;

import ConditionLanguage.Expressions.Boolean.*;
import ConditionLanguage.Expressions.Expr;
import ConditionLanguage.Expressions.Integer.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
   Interface for the P, Q condition objects of the logic.
 */
public class Condition implements ICondition{

  private final String condition;
  private final Expr root;
  private int count = 0;

  /*
   * Basic constructor.
   */
  public Condition (String stuff) //throws Exception
  {
    this.condition = stuff;
    this.root = parseConditionString(stuff);
  }
  /*
    Given a buffer to read from, reads condition from sequence of chars and returns a condition object.
   */
  public Condition(Scanner readFrom) throws Exception {
    StringBuilder condString = new StringBuilder();

    // Read "{|"
    if (readFrom.findInLine(".").charAt(0) != '{' || readFrom.findInLine(".").charAt(0) !='|') {
      throw new Exception("bad condtion"); // maybe make this a parse class?
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
      case ">": {
        Expr child1 = parseConditionString(remaining.substring(0, term1));
        Expr child2 = parseConditionString(remaining.substring(term1+1));
        return new GreaterExpr(child1, child2);
      }
      default:
        throw new UnsupportedOperationException("Operation not supported: " + command);
    }
  }

  private boolean checkSmtQuery(Expr query) {
    try {
      // get z3 query
      HashMap<String, Integer> varMaps = new HashMap<>(); // for initializing variables
      String z3_query = query.toSMT(varMaps);

      // write variable init to file
      FileWriter fw = new FileWriter("smt_query.py");
      fw.write("from z3 import * \n");
      for(String var : varMaps.keySet()) {
        if(varMaps.get(var) == 0) { // integer
          fw.write(var + " = Int('" + var + "')");
        } else if(varMaps.get(var) == 1) { // boolean
          fw.write(var + " = Bool('" + var + "')");
        } else {
          throw new UnsupportedOperationException("unknown variable type for " + var);
        }
      }

      // write smt query to file
      fw.write("constraints = " + z3_query);
      fw.write("s = Solver()");
      fw.write("s.add(constraints)");
      fw.write("print(s.check())");
      fw.write("print(s.model())");
      fw.close();

//    todo execute python3 on file
//    todo read in terminal result
      String queryFilePath = "smt_query.py";
      String[] cmd = new String[2];
      cmd[0] = "/Users/chaithanyanaikmude/opt/miniforge3/bin/python";
      cmd[1] = queryFilePath;

      Runtime rt = Runtime.getRuntime();
      Process pr = rt.exec(cmd);

      BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
      String line = "";
      while((line = bfr.readLine()) != null) {
          System.out.println(line);
      }

      return true; //todo change to query result

    } catch (IOException e) {
      e.printStackTrace();
      throw new IllegalStateException("error with discharging SMT query, need z3 installed");
    }
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
    return condition;
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
    for(String newVarName : newVar) {
      this.root.existVar(newVarName);
    }
    // note: method edits current condition, rather than returning functional new one
    return this;
  }

  public Condition and(ICondition b) {
    Expr newRoot = new AndExpr(this.root, ((Condition)b).root);
    return new Condition(newRoot, "(And " + this.condition + " " + ((Condition) b).condition + ")");
  }

  /*
   * Note: when creating conditions, e_t must be defined with @ before, i.e. @e1
   */
  public ArrayList<String> getETs() {
    return this.root.getEtVars();
  }

  /*
   * Note: when creating conditions, b_t must be defined with # before, i.e. #b1
   */
  public ArrayList<String> getBTs() {
    return this.root.getBtVars();
  }
  
  public ArrayList<String> getFreshVars(int n) {
    ArrayList<String> newVars = new ArrayList<>();
    do {
      String newVar = "fvar"+this.count++;
      if(!this.getVars().contains(newVar)) {
        newVars.add(newVar);
      }
    } while(newVars.size() < n);

    return newVars;
  }
  
  public ArrayList<String> getVars() {
    return this.root.getVars();
  }

  @Override
  public boolean equals(Object c){
    if (this == c) return true;
    if (c == null || c.getClass() != getClass()) return false;
    return this.root.equals(((Condition)c).root);
  }
  
  public ArrayList<String> getSubs(ArrayList<String> oldVars, ICondition newCondition) {
    return this.root.getSubs(oldVars, ((Condition) newCondition).root);
  }

  public boolean isSubs(ArrayList<String> oldVars, ICondition oldCondition) {
    return this.getSubs(oldVars, oldCondition) != null;
  }

  public ArrayList<String> getVarsByName(String x) {
    return this.root.getVarsByName(x);
  }
}
