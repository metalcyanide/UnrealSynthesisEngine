package Proofs.Claim;

import ConditionLanguage.Condition;
import ConditionLanguage.ICondition;
import GrammarsLanguage.IProgram;
import Proofs.Context.Context;
import Proofs.Context.IContext;
import java.util.Scanner;

public class Claim implements IClaim {

  private IContext context;
  private ICondition precondition;
  private IProgram partialProgram;
  private ICondition postcondition;

  /**
   * Private constructor of claim; probably will not need for verifier.
   */
  private Claim(IContext context, ICondition precondition, IProgram program, ICondition postcondition) {
    this.context = context;
    this.precondition = precondition;
    this.partialProgram = program;
    this.postcondition = postcondition;
  }

  /**
   * Public constructor of claim, from scanner
   * @param readFrom Scanner with string that has claim in it
   */
  public Claim(Scanner readFrom) throws Exception {
    this.context = new Context(readFrom);    // TODO fix these as static methods
    this.precondition = new Condition(readFrom);
    this.partialProgram = null;
    new Context(readFrom);  // TODO implement actual program class @Chaithanya
    this.postcondition = new Condition(readFrom);
  }

  public ICondition getPreCondition() {
    return precondition;
  }

  public ICondition getPostCondition() {
    return postcondition;
  }

  public IProgram getProgram() {
    return partialProgram;
  }

  public IContext getContext() {
    return context;
  }
}
