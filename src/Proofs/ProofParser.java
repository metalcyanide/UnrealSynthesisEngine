package Proofs;

import java.io.File;
import java.util.Scanner;

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

    // use this method to parse proofs from a scanner
    // note: very jank
    public Proof parseProof(Scanner readFrom) {
        while(readFrom.hasNext()) {
            String proofLine = readFrom.nextLine();
            // structure of proof:[i]* --> [j] context |- {|P|}S{|Q|}*
            
            // Get references for proof nodes that are required to prove this one
            List<Integer> proofReferences = new ArrayList<Integer>();
            Scanner scan = new Scanner(proofLine);
            String result = scan.next();
            while(!result.equals("-->") {
                if(!scan.hasNext()) throw new IllegalArgumentException("invalid proof, error code 1"); //TODO more descriptive errors

                proofReferences.add(Integer.parseInt(result.substring(result.indexOf("[")+1, result.indexOf("]"))));
                result = scan.next(); //assumes space delimiter
            };

            // Get current proof node reference 
            result = scan.next();
            int currReference = Integer.parseInt(result.substring(result.indexOf("[")+1, result.indexOf("]")));

            //TODO implement context, assume empty for now
            String turnstile = scan.next();
            if(!turnstile.equals("|-")) throw new IllegalArgumentException("invalid proof, error code 2");

            // Get all logic triples
            List<String> triples = new ArrayList<String>();
            do {
                //assume unrealizable triples have no spaces
                String triple = scan.next();
                triples.add(triple);
            } while(scan.hasNext());

            //TODO: convert references, context, and triples into ProofFactory format 
        }

        //TODO: return head node? idk will figure this out
    }
}
