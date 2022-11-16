package Proofs;

import Proofs.Claim.Claim;
import Proofs.Claim.IClaim;
import java.util.function.Function;

import ConditionLanguage.Condition;
import ConditionLanguage.ICondition;
import GrammarsLanguage.IProgram;

public enum ProofFactory {
  Assign((x) -> true),
  Seq((x) -> {
    if(x.getChildren().length != 2) {
      return false;
    }
    ICondition lHypPrec = x.getChildren()[0].getClaim().getPreCondition();
    ICondition lHypPostc = x.getChildren()[0].getClaim().getPostCondition();
    ICondition rHypPrec = x.getChildren()[1].getClaim().getPreCondition();
    ICondition rHypPostc = x.getChildren()[1].getClaim().getPostCondition();
    ICondition claimPrec = x.getClaim().getPreCondition();
    ICondition claimPostc = x.getClaim().getPostCondition();
    IProgram lHypProg = x.getChildren()[0].getClaim().getProgram();
    IProgram rHypProg = x.getChildren()[1].getClaim().getProgram();
    IProgram claimProg = x.getClaim().getProgram();
   return lHypPrec.equals(claimPrec) && lHypPostc.equals(rHypPrec) && rHypPrec.equals(claimPostc);
  //  && claimProg == lHypProg ; rHypProg
  }),
  Zero(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String e_old = prec.getET();
    String e_new = prec.getNextET();
   return x.getChildren().length == 0 
    && prec.subs(e_new, e_old).and(new Condition(e_old + "=0")).existentialBind(e_new).equals(prec);
    // && prog == 0;
  }),
  One(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String e_old = prec.getET();
    String e_new = prec.getNextET();
   return x.getChildren().length == 0 
    && prec.subs(e_new, e_old).and(new Condition(e_old + "=1")).existentialBind(e_new).equals(prec);
    // && prog == 1;
  }),
  True(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String b_old = prec.getBT();
    String b_new = prec.getNextBT();
   return x.getChildren().length == 0 
    && prec.subs(b_new, b_old).and(new Condition(b_old + "=t")).existentialBind(b_new).equals(prec);
    // && prog == t;
  }),
  False(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String b_old = prec.getBT();
    String b_new = prec.getNextBT();
   return x.getChildren().length == 0 
    && prec.subs(b_new, b_old).and(new Condition(b_old + "=f")).existentialBind(b_new).equals(prec);
    // && prog == f;
  }),
  Var((x) -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String e_old = prec.getET();
    String e_new = prec.getNextET();
    String var = null; // check that prog is a var and get the string var value
   return x.getChildren().length == 0 
   && prec.subs(e_new, e_old).and(new Condition(e_old + "=" + var)).existentialBind(e_new).equals(prec);
  }),
  Plus((x) -> true),
  GrmDisj((x) -> true),
  Not((x) -> {
    if(x.getChildren().length != 1) {
      return false;
    }
    ICondition hypPrec = x.getChildren()[0].getClaim().getPreCondition();
    ICondition hypPostc = x.getChildren()[0].getClaim().getPostCondition();
    ICondition claimPrec = x.getClaim().getPreCondition();
    ICondition claimPostc = x.getClaim().getPostCondition();
    IProgram hypProg = x.getChildren()[0].getClaim().getProgram();
    IProgram claimProg = x.getClaim().getProgram();
    String b_old = claimPostc.getBT();
    String b_new = claimPostc.getNextBT();
   return hypPrec.equals(claimPrec)
  //  && !claimProg == hypProg
   && hypPostc.subs(b_new, b_old).and(new Condition(b_old + "=!" + b_new)).existentialBind(b_new).equals(claimPostc);
  }),
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
