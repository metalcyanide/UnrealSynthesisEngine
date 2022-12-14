package Proofs.Context;

import ConditionLanguage.Condition;
import java.util.ArrayList;
import java.util.Scanner;

import ConditionLanguage.ICondition;
import GrammarsLanguage.IProgram;

/*
 * Dummy stand-in for context class. Should have some functionality... eventually.
 */
public class Context implements IContext {
  private ArrayList<ICondition> preconds;
  private ArrayList<IProgram> progs;
  private ArrayList<ICondition> postconds;

//  public Context(String context) {
//    String[] triples = context.split(",");
//  }

  private Context(Context cont) {
    this.preconds = cont.getPreconds();
    this.progs = cont.getProgs();
    this.postconds = cont.getPostconds();
  }

  public Context(Scanner readFrom) throws Exception {
    preconds = new ArrayList<ICondition>();
    progs = new ArrayList<IProgram>();
    postconds = new ArrayList<ICondition>();

    // Read "{"
    if (readFrom.findInLine(".").charAt(0) != '{') {
      // TODO We could make a parse exception class if we wanted.
      throw new Exception("bad context");
    }

    // Extract context string
    while (!readFrom.hasNext("}.*")) {
      preconds.add(new Condition(readFrom));
      progs.add(null);
      postconds.add(new Condition(readFrom));
      if (readFrom.hasNext("}")) {
        break;
      }
      else if(readFrom.findInLine(".").charAt(0) != ',') {
        throw new Exception("context malformated");
      }
    }
    readFrom.findInLine(".");

  }

//  public String toString() {
//    return "{" + this.context + "}";
//  }

  public boolean inContext(ICondition prec, IProgram prog, ICondition postc) {
    for (int i = 0; i < preconds.size(); ++i) {
      if (preconds.get(i).equals(prec) && progs.get(i).equals(prog) && postconds.get(i).equals(postc)) {
        return true;
      }
    }
    return false;
  }

  public IContext addToContext(ICondition prec, IProgram prog, ICondition postc) {
    Context newCon = new Context(this);
    newCon.preconds.add(prec);
    newCon.progs.add(prog);
    newCon.postconds.add(postc);

    return newCon;
  }

  public ArrayList<ICondition> getPreconds() {
    return preconds;
  }

  public ArrayList<IProgram> getProgs() {
    return progs;
  }

  public ArrayList<ICondition> getPostconds() {
    return postconds;
  }

}
