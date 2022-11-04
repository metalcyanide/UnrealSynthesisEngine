package Proofs;

import Proofs.IProof;
import Proofs.IULTriple;
import java.util.Scanner;
import java.util.function.Function;

public class Proof implements IProof {
  private Proof[] children;
  private Function<Proof, Boolean> validationFunction; // Should be 0-ary function, but Java doesn't have that type? We can make our own if we want.
  private String ruleName;
  private IULTriple claim;

  public Proof(String rule, Function<Proof, Boolean> f, IULTriple claim, Proof... children)
  {
    this.claim = claim;
    this.children = children;
    this.validationFunction = f;
    this.ruleName = rule;
  }

  // TODO Rahul
  static public Proof parseProof(Scanner readFrom) {
	  return new Proof(null, null, null);
  }

  public IULTriple getClaim() {
	  return claim;
  }

  public String getTopInferenceRuleTag() {
    return ruleName;
  }

  public IProof[] getChildren() {
    return children;
  }

  public boolean validate() {
    return this.validationFunction.apply(this);
  }

}

