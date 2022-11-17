package Proofs.Context;

import java.util.Scanner;

import ConditionLanguage.ICondition;
import GrammarsLanguage.IProgram;

/*
 * Dummy stand-in for context class. Should have some functionality... eventually.
 */
public class Context implements IContext {
  private String context;

  public Context(String context) {
    this.context = context;
  }

  public Context(Scanner readFrom) throws Exception {
    StringBuilder contString = new StringBuilder();

    // Read "{"
    if (readFrom.findInLine(".").charAt(0) != '{') {
      // TODO We could make a parse exception class if we wanted.
      throw new Exception("bad context");
    }

    // Extract context string
    char n = readFrom.findInLine(".").charAt(0);
    while (n != '}') {
      contString.append(n);
      n = readFrom.findInLine(".").charAt(0);
    }

    this.context = contString.toString();
  }

  public String toString() {
    return "{" + this.context + "}";
  }

  public boolean inContext(ICondition prec, IProgram prog, ICondition postc) {
    // TODO Implement later
    return true;
  }

  public IContext addToContext(ICondition prec, IProgram prog, ICondition postc) {
    // TODO Implement later
    return this;
  }
}
