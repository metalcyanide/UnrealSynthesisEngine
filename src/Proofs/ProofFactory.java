package Proofs;

import Proofs.Claim.IClaim;
import Proofs.Context.IContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

import ConditionLanguage.Condition;
import ConditionLanguage.ICondition;
import GrammarsLanguage.IProgram;

/*
 * The general model for checking that conditions are correct is thus:
 * 1. We construct a syntactically valid condition where we choose the names of new variables arbitrarily.
 * 2. We ask whether the condition we have in the proof matches what we generated, modulo the names of the variables we made up.
 */
public enum ProofFactory{
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
    ArrayList<String> e = hypPostc.getETs();                        // e
    ArrayList<String> varsOld = hypPostc.getVarsByName(varOld);     // x
    ArrayList<String> varsNew = hypPostc.getFreshVars(varsOld.size()); // x'
    int i = 0;
    String newAssignments = join(joinTwo(varsOld,e,"=")," ^ "); // x'
   return hypPrec.equals(claimPrec)
   && claimProg.getNodeType().equals("Assign")
   && hypProg.equals(claimProg.getChildren()[1])
   && hypPostc.subs(varsNew, varsOld).and(new Condition(newAssignments)).existentialBind(varsNew).isSubs(varsOld, claimPostc);  // Non-null if form correct
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
    ArrayList<String> e_old = prec.getETs();    // e_t
    ArrayList<String> e_new = prec.getFreshVars(e_old.size());
   return x.getChildren().length == 0 
    && prec.subs(e_new, e_old).and(new Condition(e_old.stream().reduce("", (a,b) -> a + " ^ " + b + "=0"))).existentialBind(e_new).isSubs(e_new, postc)
    && prog.getNodeType().equals("0");
  }),
  One(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    ArrayList<String> e_old = prec.getETs();
    ArrayList<String> e_new = prec.getFreshVars(e_old.size());
   return x.getChildren().length == 0 
    && prec.subs(e_new, e_old).and(new Condition(e_old.stream().reduce("", (a,b) -> a + " ^ " + b + "=1"))).existentialBind(e_new).isSubs(e_new, postc)
    && prog.getNodeType().equals("1");
  }),
  True(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    ArrayList<String> b_old = prec.getBTs();
    ArrayList<String> b_new = prec.getFreshVars(b_old.size());
   return x.getChildren().length == 0 
    && prec.subs(b_new, b_old).and(new Condition(b_old.stream().reduce("", (a,b) -> a + " ^ " + b + "=t"))).existentialBind(b_new).isSubs(b_new, postc)
    && prog.getNodeType().equals("True");
  }),
  False(x -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    ArrayList<String> b_old = prec.getBTs();
    ArrayList<String> b_new = prec.getFreshVars(b_old.size());
   return x.getChildren().length == 0 
   && prec.subs(b_new, b_old).and(new Condition(b_old.stream().reduce("", (a,b) -> a + " ^ " + b + "=f"))).existentialBind(b_new).isSubs(b_new, postc)
   && prog.getNodeType().equals("False");
  }),
  Var((x) -> {
    ICondition prec = x.getClaim().getPreCondition();
    ICondition postc = x.getClaim().getPostCondition();
    IProgram prog = x.getClaim().getProgram();
    ArrayList<String> e_old = prec.getETs();
    ArrayList<String> e_new = prec.getFreshVars(e_old.size());
    String var = prog.getVarName(); // check that prog is a var and get the string var value
    ArrayList<String> varsOld = prec.getVarsByName(var);     // x 
    String newAssignments = join(joinTwo(e_old, varsOld, "="), " ^ ");
   return x.getChildren().length == 0 
   && prog.getNodeType().equals("Var")
   && prec.subs(e_new, e_old).and(new Condition(newAssignments)).existentialBind(e_new).isSubs(e_new, postc);
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
    ArrayList<String> b_old = hypPostc.getBTs();
    ArrayList<String> b_new = hypPostc.getFreshVars(b_old.size());
    String newAssignments = join(joinTwo(b_old, b_new, "=!"), " ^ ");
   return hypPrec.equals(claimPrec)
   && claimProg.getNodeType().equals("Not")
   && hypProg.equals(claimProg.getChildren()[0])
   && hypPostc.subs(b_new, b_old).and(new Condition(newAssignments)).existentialBind(b_new).isSubs(b_new, claimPostc);
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
  // Continue revisions below
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

    ArrayList<String> v = claimPrec.getVars();
    ArrayList<String> v_1 = claimPrec.getFreshVars(v.size());
    ArrayList<String> v_2 = claimPrec.getFreshVars(v.size());
    // ArrayList<String> v_1p = claimPrec.getFreshVars(v.size());
    // ArrayList<String> v_2p = claimPrec.getFreshVars(v.size());

    // Get v_1 and v_2, checking precondition agreement
    // Construct dummy lprec and rprec
    
    String v1AssignmentsFake = join(joinTwo(v_1, v, "="), " ^ ");
    String v2AssignmentsFake = join(joinTwo(v_2, v, "="), " ^ ");
    v_1 = claimPrec.and(new Condition(v1AssignmentsFake)).getSubs(v_1, lHypPostc);
    v_2 = claimPrec.and(new Condition(v2AssignmentsFake)).getSubs(v_2, rHypPostc);
    if (v_1 == null || v_2 == null || !v_1.stream().anyMatch(v_2::contains)) {  // Renamings should exist and be disjoint
      return false;
    }
    String v1Assignments = join(joinTwo(v_1, v, "="), " ^ ");
    String v2Assignments = join(joinTwo(v_2, v, "="), " ^ ");
    

    // Check claimPostc
    // Construct dummy claimPostc
    ICondition dummyPostc = claimPrec.and(lHypPostc).and(rHypPostc).and(new Condition(v1Assignments)).and(new Condition(v2Assignments));
    // Do internal b_t subs before continuing to build expression 
    ArrayList<String> b = claimPrec.getBTs();
    ArrayList<String> bPRenames = dummyPostc.getFreshVars(b.size());
    dummyPostc = dummyPostc.subs(bPRenames, b);
    // Set b = b1 ^ b2
    ArrayList<String> b1 = lHypPostc.getBTs();
    ArrayList<String> b2 = rHypPostc.getBTs();
    
    String bAndClauses = join(joinTwo(b, joinTwo(b1, b2, "^"), "="), " ^ ");
    dummyPostc = dummyPostc.and(new Condition(bAndClauses));
    // Q1[v1'/v] and Q2[v2'/v] substitutions 
    ArrayList<String> v_1P = dummyPostc.getFreshVars(v.size());
    ArrayList<String> v_2P = dummyPostc.getFreshVars(v.size());
    dummyPostc = claimPrec.and(lHypPostc.subs(v_1P, v)).and(rHypPostc.subs(v_2P, v)).and(new Condition(v1Assignments)).and(new Condition(v2Assignments)).subs(bPRenames, b).and(new Condition(bAndClauses));
    // Existential binds
    dummyPostc = dummyPostc.existentialBind(bPRenames).existentialBind(v_1).existentialBind(v_2).existentialBind(v_1P).existentialBind(v_2P);
    
    // Build list of new vars bc ArrayLists are not functional
    ArrayList<String> newVars = bPRenames; newVars.addAll(v_1); newVars.addAll(v_2); newVars.addAll(v_1P); newVars.addAll(v_2P);

    return claimProg.getNodeType().equals("And")  // Is child1 And child2
    && claimProg.getChildren()[0].equals(lHypProg)
    && claimProg.getChildren()[1].equals(rHypProg)
    && dummyPostc.isSubs(newVars, claimPostc);
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

  /*
   * Merges two arraylists elementwise. Assumes first.size==second.size
   */
  static private final ArrayList<String> joinTwo(ArrayList<String> first, ArrayList<String> second, String joiner) {
    ArrayList<String> newList = new ArrayList<String>();
    for (int i = 0; i < first.size(); ++i) {
        newList.add(first.get(i) + joiner + second.get(i));
    }
    return newList;
  }

  /*
   * Cats arraylist elems together with given joiner
   */
  static private final String join(ArrayList<String> arList, String joiner) {
    return arList.stream().reduce("", (a, b) -> a + joiner + b);
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
