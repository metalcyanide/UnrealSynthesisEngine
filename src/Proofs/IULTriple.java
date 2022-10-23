package Proofs;

import ConditionLanguage.ICondition;
import GrammarsLanguage.IProgram;
import java.io.BufferedInputStream;

/*
   Interface for unrealizability triples {P} S {Q}
   Eventually, we'll probably want to extend this to include a context \Gamma, but let's call it a stretch goal :p
 */
public interface IULTriple {

  /*
  Given a buffer to read from, constructs triple.
 */
  IProof parseProofWithVerification(BufferedInputStream readFrom);

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
}
