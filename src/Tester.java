import Proofs.Proof;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) throws Exception {
        String dir = "proof-files/";

        HashMap<String, Boolean> filesAndOutputs = new HashMap<>();
        filesAndOutputs.put(dir + "zero_true.txt", true);

        for(String file : filesAndOutputs.keySet()) {
            System.out.println("Verifying file: " + file);
            File proofFile = new File(file);
            System.out.println(proofFile.getAbsoluteFile());
            Scanner currFile = new Scanner(proofFile);
            Proof root = Proof.parseProof(currFile);
            System.out.println("\tresult: " + root.validate() + "\texpected: " + filesAndOutputs.get(file));

            //todo: add profiling
        }
    }
}