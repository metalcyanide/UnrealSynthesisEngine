package Proofs;

import Proofs.Claim.IClaim;
import Proofs.Context.Context;
import Proofs.IProof;
import Proofs.Claim.IClaim;
import Proofs.Claim.Claim;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;

public class Proof implements IProof {
  private Proof[] children; // Children of the proof (proofs of hypotheses)
  private Function<Proof, Boolean> validationFunction; // Should be 0-ary function, but Java doesn't have that type? We can make our own if we want.
  private String ruleName; // Name of the inference rule
  private IClaim claim; // Claim this proof/rule is proving

  private static HashMap<Integer, Proof> proofMap = new HashMap<Integer, Proof>();

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
  * Given a scanner with a string representation of a proof (single line), constructs a proof object describing said proof.
  * Should use the parse method of IULTriples. Haven't added contexts yet...
  */
  public static Proof parseProof(Scanner readFrom) throws Exception {
    Proof parsedProof = null;
    //parses one proof line
    ProofParser.ParserObject info = ProofParser.getInstance().parseProofLine(readFrom.nextLine());
    //TODO update to use nonempty context
    IClaim claim = new Claim(new Scanner(info.claim));
    Proof[] children = new Proof[info.references.size()];
    for(int i = 0; i < info.references.size(); i++) {
        children[i] = proofMap.get(info.references.get(i));
    }

    // generate relevant ProofFactory
    switch(info.proofType) {
        case "Assign": parsedProof = ProofFactory.Assign.generateProof(claim, children); break; 
        case "Seq": parsedProof = ProofFactory.Seq.generateProof(claim, children); break; 
        case "Zero": parsedProof = ProofFactory.Zero.generateProof(claim, children); break; 
        case "One": parsedProof = ProofFactory.One.generateProof(claim, children); break; 
        case "True": parsedProof = ProofFactory.True.generateProof(claim, children); break; 
        case "False": parsedProof = ProofFactory.False.generateProof(claim, children); break; 
        case "Var": parsedProof = ProofFactory.Var.generateProof(claim, children); break; 
        case "Plus": parsedProof = ProofFactory.Plus.generateProof(claim, children); break; 
        case "GrmDisj": parsedProof = ProofFactory.GrmDisj.generateProof(claim, children); break; 
        case "Not": parsedProof = ProofFactory.Not.generateProof(claim, children); break; 
        case "Comp": parsedProof = ProofFactory.Comp.generateProof(claim, children); break; 
        case "Inv": parsedProof = ProofFactory.Inv.generateProof(claim, children); break; 
        case "HP": parsedProof = ProofFactory.HP.generateProof(claim, children); break; 
        case "ApplyHP": parsedProof = ProofFactory.ApplyHP.generateProof(claim, children); break; 
        case "And": parsedProof = ProofFactory.And.generateProof(claim, children); break; 
        case "ITE": parsedProof = ProofFactory.ITE.generateProof(claim, children); break; 
        case "While": parsedProof = ProofFactory.While.generateProof(claim, children); break; 
        case "Weaken": parsedProof = ProofFactory.Weaken.generateProof(claim, children); break; 
        case "Conj": parsedProof = ProofFactory.Conj.generateProof(claim, children); break; 
        case "Sub1": parsedProof = ProofFactory.Sub1.generateProof(claim, children); break; 
        case "Sub2": parsedProof = ProofFactory.Sub2.generateProof(claim, children); break; 
        default: throw new IllegalArgumentException("invalid proof type");
    }
    return parsedProof;
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

