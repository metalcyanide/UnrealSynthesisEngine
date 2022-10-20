package Proofs;

import ConditionLanguage.Condition;
import GrammarsLanguage.Program;
import java.io.BufferedInputStream;

/*
   Interface for unrealizability triples {P} S {Q}
   Eventually, we'll probably want to extend this to include a context \Gamma, but let's call it a stretch goal :p
 */
public interface ULTriple {

  /*
  Given a buffer to read from, constructs triple.
 */
  Proof parseProofWithVerification(BufferedInputStream readFrom);

  /*
    Returns precondition
   */
  Condition getPreCondition();

  /*
    Returns postcondition
   */
  Condition getPostCondition();

  /*
     Returns "S" in {P} S {Q} -- middle part of UL Triple
   */
  Program getProgram();
}
