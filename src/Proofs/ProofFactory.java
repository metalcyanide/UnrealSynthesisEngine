package Proofs;

import Proofs.Claim.Claim;
import Proofs.Claim.IClaim;
import Proofs.Context.IContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

import ConditionLanguage.Condition;
import ConditionLanguage.ICondition;
import GrammarsLanguage.IProgram;

public enum ProofFactory {
  Assign((x) -> {
    if(x.getChildren().length != 1) {
      return false;
    }
    ICondition hypPrec = x.getChildren()[0].getClaim().getPreCondition();
    ICondition hypPostc = x.getChildren()[0].getClaim().getPostCondition();
    ICondition claimPrec = x.getClaim().getPreCondition();
    ICondition claimPostc = x.getClaim().getPostCondition();
    IProgram hypProg = x.getChildren()[0].getClaim().getProgram();
    IProgram claimProg = x.getClaim().getProgram();
    String varOld = claimProg.getVarName(); // Get var on lhs of assignment
    String varNew = hypPostc.getFreshVar();
    String e = hypPostc.getET();
   return hypPrec.equals(claimPrec)
   && claimProg.getNodeType().equals("Assign")
   && hypProg.equals(claimProg.getChildren()[1])
   && hypPostc.subs(varNew, varOld).and(new Condition(varOld + "=" + e)).existentialBind(varNew).equals(claimPostc);
  }),
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
   return lHypPrec.equals(claimPrec) && lHypPostc.equals(rHypPrec) && rHypPostc.equals(claimPostc)
   && claimProg.getNodeType().equals("Seq")
   && claimProg.getChildren()[0].equals(lHypProg)
   && claimProg.getChildren()[1].equals(rHypProg);
  }),
  Zero(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String e_old = prec.getET();
    String e_new = prec.getNextET();
   return x.getChildren().length == 0 
    && prec.subs(e_new, e_old).and(new Condition(e_old + "=0")).existentialBind(e_new).equals(postc)
    && prog.getNodeType().equals("0");
  }),
  One(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String e_old = prec.getET();
    String e_new = prec.getNextET();
   return x.getChildren().length == 0 
    && prec.subs(e_new, e_old).and(new Condition(e_old + "=1")).existentialBind(e_new).equals(postc)
    && prog.getNodeType().equals("1");
  }),
  True(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String b_old = prec.getBT();
    String b_new = prec.getNextBT();
   return x.getChildren().length == 0 
    && prec.subs(b_new, b_old).and(new Condition(b_old + "=t")).existentialBind(b_new).equals(postc)
    && prog.getNodeType().equals("True");
  }),
  False(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String b_old = prec.getBT();
    String b_new = prec.getNextBT();
   return x.getChildren().length == 0 
    && prec.subs(b_new, b_old).and(new Condition(b_old + "=f")).existentialBind(b_new).equals(postc)
    && prog.getNodeType().equals("False");
  }),
  Var((x) -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    String e_old = prec.getET();
    String e_new = prec.getNextET();
    String var = prog.getVarName(); // check that prog is a var and get the string var value
   return x.getChildren().length == 0 
   && prog.getNodeType().equals("Var")
   && prec.subs(e_new, e_old).and(new Condition(e_old + "=" + var)).existentialBind(e_new).equals(postc);
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
    String b_old = hypPostc.getBT();
    String b_new = hypPostc.getNextBT();
   return hypPrec.equals(claimPrec)
   && claimProg.getNodeType().equals("Not")
   && hypProg.equals(claimProg.getChildren()[0])
   && hypPostc.subs(b_new, b_old).and(new Condition(b_old + "=!" + b_new)).existentialBind(b_new).equals(claimPostc);
  }),
  Comp((x) -> true),
  Inv((x) -> {
    if(x.getChildren().length != 0) {
      return false;
    }
    ICondition claimPrec = x.getClaim().getPreCondition();
    ICondition claimPostc = x.getClaim().getPostCondition();
    IProgram claimProg = x.getClaim().getProgram();
   return claimPrec.equals(claimPostc) && Collections.disjoint(claimPrec.getVars(), claimProg.getVars());
  }),
  HP((x) -> true),
  ApplyHP((x) -> {
    IContext claimCtx = x.getClaim().getContext();
    ICondition claimPrec = x.getClaim().getPreCondition();
    ICondition claimPostc = x.getClaim().getPostCondition();
    IProgram claimProg = x.getClaim().getProgram();
    return (x.getChildren().length == 0) && claimCtx.inContext(claimPrec, claimProg, claimPostc);
  }),
  And((x) -> {
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
    ArrayList<String> rename_1 = claimPrec.getVars(); rename_1.replaceAll(y -> y + "_1");
    ArrayList<String> rename_2 = claimPrec.getVars(); rename_2.replaceAll(y -> y + "_2");
    ArrayList<String> rename_1p = claimPrec.getVars(); rename_1p.replaceAll(y -> y + "_1'");
    ArrayList<String> rename_2p = claimPrec.getVars(); rename_2p.replaceAll(y -> y + "_2'");
    return claimProg.getNodeType().equals("And")
    && claimProg.getChildren()[0].equals(lHypProg)
    && claimProg.getChildren()[1].equals(rHypProg);
    // && lHypPrec.equals(claimPrec.and(new Condition("")));
// TODO - finish this
  }),
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
