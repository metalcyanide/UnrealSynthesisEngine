package Proofs;

import ConditionLanguage.Condition;
import GrammarsLanguage.Program;
import java.io.BufferedInputStream;

/*
   Interface for unrealizability triples {P} S {Q}
 */
public interface ULTriple <Cond extends Condition, Prog extends Program>{

  /*
  Given a buffer to read from, constructs triple.
 */
  Proof parseProofWithVerification(BufferedInputStream readFrom);

  /*
    Returns precondition
   */
  Cond getPreCondition();

  /*
    Returns postcondition
   */
  Cond getPostCondition();

  /*
     Returns "S" in {P} S {Q} -- middle part of UL Triple
   */
  Prog getProgram();

}
