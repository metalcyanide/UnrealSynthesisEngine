package Proofs.Claim;

import ConditionLanguage.ICondition;
import GrammarsLanguage.IProgram;
import Proofs.Context.IContext;
import Proofs.IProof;
import java.util.Scanner;

/*
   Interface for unrealizability triples {P} S {Q}
   Eventually, we'll probably want to extend this to include a context \Gamma, but let's call it a stretch goal :p
 */
public interface IClaim {
  /*
    Returns precondition
   */
  ICondition getPreCondition();

  /*
    Returns postcondition
   */
  ICondition getPostCondition();

  /*
     Returns "S" in {P} S {Q} -- middle part of UL Triple
   */
  IProgram getProgram();

  /**
   * Gets context (\Gamma) of claim.
   */
  IContext getContext();
}
