package Proofs;

import java.util.function.Function;

public enum ProofFactory {
  WEAKEN((x) -> true);


  private Function<Proof, Boolean> validationFunction;

  ProofFactory(Function<Proof, Boolean> validator) {
    this.validationFunction = validator;
  }

  public Proof generateProof(IULTriple claim, Proof... children) {
    return new Proof(this.toString(), this.validationFunction, claim, children);
  }
}
