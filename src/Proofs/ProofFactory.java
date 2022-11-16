package Proofs;

import Proofs.Claim.Claim;
import Proofs.Claim.IClaim;
import java.util.function.Function;

import ConditionLanguage.Condition;
import ConditionLanguage.ICondition;
import GrammarsLanguage.IProgram;

public enum ProofFactory {
  Assign((x) -> true),
  Seq((x) -> true),
  Zero(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String e_old = postc.getET();
    String e_new = postc.getNextET();
   return x.getChildren().length == 0 
    && postc.subs(e_new, e_old).and(new Condition(e_old + "=0")).existentialBind(e_new).equals(prec);
    // && prog == 0;
  }),
  One(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String e_old = postc.getET();
    String e_new = postc.getNextET();
   return x.getChildren().length == 0 
    && postc.subs(e_new, e_old).and(new Condition(e_old + "=1")).existentialBind(e_new).equals(prec);
    // && prog == 1;
  }),
  True(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String b_old = postc.getBT();
    String b_new = postc.getNextBT();
   return x.getChildren().length == 0 
    && postc.subs(b_new, b_old).and(new Condition(b_old + "=t")).existentialBind(b_new).equals(prec);
    // && prog == t;
  }),
  False(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String b_old = postc.getBT();
    String b_new = postc.getNextBT();
   return x.getChildren().length == 0 
    && postc.subs(b_new, b_old).and(new Condition(b_old + "=f")).existentialBind(b_new).equals(prec);
    // && prog == f;
  }),
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
