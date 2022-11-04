package Proofs;

import java.util.Scanner;

/*
   Interface for proof objects.
 */
public interface IProof {
    /*
      Given a buffer to read from, constructs proof.
      Ought to be static constructor.
     */
    static IProof parseProof(Scanner readFrom) {
        return null;
    }

    /*
     * Returns claim that the proof claims to prove
     */
    IULTriple getClaim();

    /*
     * Returns the name of the inference rule this proof first applies.
     */
    String getTopInferenceRuleTag();

    /*
     * Returns proofs of the claims that the top level inference rule relies on.
     */
    IProof[] getChildren();

    /*
     * Processes ProofVisitor, routing to the correct visit method in the visitor, depending on the proof's type.
     */
//    <RetType, V extends IProofVisitor<RetType>> RetType accept(V visitor);
}
