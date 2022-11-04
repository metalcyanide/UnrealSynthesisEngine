package Proofs;

import Proofs.Claim.IClaim;
import java.util.Scanner;

/*
   Interface for proof objects.
 */
public interface IProof {
    /*
     * Returns claim that the proof claims to prove
     */
    IClaim getClaim();

    /*
     * Returns the name of the inference rule this proof first applies.
     */
    String getTopInferenceRuleTag();

    /*
     * Returns proofs of the claims that the top level inference rule relies on.
     */
    IProof[] getChildren();

    /*
     * Returns a bool, telling you whether the given proof is correct or not.
     */
    boolean validate();

    /*
     * Processes ProofVisitor, routing to the correct visit method in the visitor, depending on the proof's type.
     */
//    <RetType, V extends IProofVisitor<RetType>> RetType accept(V visitor);
}
