package Proofs;

import Proofs.Claim.IClaim;
import java.util.function.Function;

public enum ProofFactory {
  Assign((x) -> true),
  Seq((x) -> true),
  Zero((x) -> true),
  One((x) -> true),
  True((x) -> true),
  False((x) -> true),
  Var((x) -> true),
  Plus((x) -> true),
  GrmDisj((x) -> true),
  Not((x) -> true),
  Comp((x) -> true),
  Inv((x) -> true),
  HP((x) -> true),
  ApplyHP((x) -> true),
  And((x) -> true),
  ITE((x) -> true),
  While((x) -> true),
  Weaken((x) -> true),
  Conj((x) -> true),
  Sub1((x) -> true),
  Sub2((x) -> true);


  private Function<Proof, Boolean> validationFunction;

  /*
   * Constructs a proofFactory given semantic correctness semantics.
   */
  ProofFactory(Function<Proof, Boolean> validator) {
    this.validationFunction = validator;
  }

  /**
   * Returns a proof of this inference rule type.
   * @param claim The claim this proof proves.
   * @param children Proofs of this rule's hypotheses
   * @return A Proof object
   */
  public Proof generateProof(IClaim claim, Proof... children) {
    return new Proof(this.toString(), this.validationFunction, claim, children);
  }
}
