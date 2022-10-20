package Proofs;

import java.io.BufferedInputStream;

/*
   Interface for proof objects
 */
public interface Proof {
    /*
      Given a buffer to read from, constructs proof.
      Should probably perform verification during construction.
      Ought to be static constructor.
     */
    Proof parseProofWithVerification(BufferedInputStream readFrom);

}
