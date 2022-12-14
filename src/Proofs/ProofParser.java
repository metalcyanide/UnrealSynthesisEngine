package Proofs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Proofs.Claim.IClaim;
import java.util.regex.Pattern;

public class ProofParser {
    // Singleton class
    private static ProofParser instance = null;
    private ProofParser() { }
    public static ProofParser getInstance() {
        if(instance == null) {
            instance = new ProofParser();
        }
        return instance;
    }

    class ParserObject {
        List<Integer> references;
        int currReference;
        String claim;
        String proofType;

        public ParserObject(List<Integer> references, int curr, String claim, String type) {
            this.references = references;
            this.currReference = curr;
            this.claim = claim;
            this.proofType = type;
        }
    }

    // use this method to parse proofs from a scanner
    // structure of proof:[i]* --> proofType [j] context |- {|P|}S{|Q|}
    // note: very jank
    public ParserObject parseProofLine(String proofLine) {
        // Get references for proof nodes that are required to prove this one
        List<Integer> proofReferences = new ArrayList<>();
        Scanner scan = new Scanner(proofLine);
        String result = scan.next();
        while(!result.equals("-->")) {
            if(!scan.hasNext()) throw new IllegalArgumentException("invalid proof, error code 1"); //TODO more descriptive errors

            proofReferences.add(Integer.parseInt(result.substring(result.indexOf("[")+1, result.indexOf("]"))));
            result = scan.next(); //assumes space delimiter
        }
        
        // Get current proof type
        String proofType = scan.next();

        // Get current proof node reference
        result = scan.next();
        int currReference = Integer.parseInt(result.substring(result.indexOf("[")+1, result.indexOf("]")));

//        String turnstile = scan.next();
//        if(!turnstile.equals("|-")) throw new IllegalArgumentException("invalid proof, error code 2");
//
//        // Get triple, without any spaces
//        String triple = scan.nextLine().replaceAll("\\s", "");

        String claim = scan.nextLine().replaceAll("\\s\\{", "{");

        // Return parser info
        return new ParserObject(proofReferences, currReference, claim, proofType);
    }

    /*
     * Test method for proof parsing
     */
    public static void main(String args[]) throws Exception {
//        String proofExample = "[0] [1] --> [2] {} |- {|True|}{Statement}{|Q|}";
        String proofExample = " --> Zero [2] {} {|(BOOLCONST T)|}{1}{|(BOOLCONST T)|}";
        ParserObject info = ProofParser.getInstance().parseProofLine(proofExample);
        System.out.println(info.claim);

        Proof proof = Proof.parseProofLine(new Scanner(proofExample));
        System.out.println(proof.getClaim());
        System.out.println(proof.validate());
    }
}
