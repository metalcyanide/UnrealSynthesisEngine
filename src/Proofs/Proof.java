package Proofs;

import Proofs.Claim.IClaim;
import Proofs.IProof;
import Proofs.Claim.IClaim;
import Proofs.Claim.Claim;
import java.util.Scanner;
import java.util.function.Function;

public class Proof implements IProof {
  private Proof[] children; // Children of the proof (proofs of hypotheses)
  private Function<Proof, Boolean> validationFunction; // Should be 0-ary function, but Java doesn't have that type? We can make our own if we want.
  private String ruleName; // Name of the inference rule
  private IClaim claim; // Claim this proof/rule is proving

  /**
   * Generic constructor used in factories.
   */
  Proof(String rule, Function<Proof, Boolean> f, IClaim claim, Proof... children)
  {
    this.claim = claim;
    this.children = children;
    this.validationFunction = f;
    this.ruleName = rule;
  }

  /**
  * Given a scanner with a string representation of a proof, constructs a proof object describing said proof.
   * Should use the parse method of IULTriples. Haven't added contexts yet...
  */
  public Proof parseProof(Scanner readFrom) {
    Proof head = null;
//    while(readFrom.hasNext()) {
//        ProofParser.ParserObject info = ProofParser.getInstance().parseProofLine(readFrom.nextLine());
//        IClaim claim = new Claim(new Scanner(info.triple));
//        Proof currProof = new Proof(info.triple, null, claim, ...);
//    }
    //TODO: return head node
    return head;
  }

  /*
   * Getter for claim that proof should prove.
   */
  public IClaim getClaim() {
	  return claim;
  }

  /*
   * Getter for name of inference rule type.
   */
  public String getTopInferenceRuleTag() {
    return ruleName;
  }

  /*
   * Getter for children of this proof (proofs of its hypotheses)
   */
  public IProof[] getChildren() {
    return children;
  }

  /*
   * Returns a bool telling you whether this proof is correct, according to the semantics of validationFunction.
   */
  public boolean validate() {
    for (IProof proof: this.getChildren()) {
      if (!proof.validate()) {
          return false;
      }
    }
    return this.validationFunction.apply(this);
  }

}

