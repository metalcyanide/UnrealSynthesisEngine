package Proofs;

import java.io.BufferedInputStream;

/*
   Interface for proof objects.
 */
public interface Proof {
    /*
      Given a buffer to read from, constructs proof.
      Should probably perform verification during construction.
      Ought to be static constructor.
     */
    Proof parseProofWithVerification(BufferedInputStream readFrom);

    /*
     * Returns claim that the proof claims to prove
     */
    ULTriple getClaim();

    /*
     * Returns the name of the inference rule this proof first applies.
     */
    String getTopInferenceRuleTag();

    /*
     * Returns proofs of the claims that the top level inference rule relies on.
     */
    Proof[] getSubProofs();

    /*
     * Processes ProofVisitor, routing to the correct visit method in the visitor, depending on the proof's type.
     */
    RetType accept(<V extends ProofVisitor<RetType>> visitor);
}
