import Proofs.Proof;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) throws Exception {
        String dir = "proof-files/";


        // proofs that should verify to true
        ArrayList<String> filesTrue = new ArrayList<>();
        filesTrue.add(dir + "zero_true.txt");
        filesTrue.add(dir + "twoline_true.txt");
        filesTrue.add(dir + "weaken_true.txt");

        // proofs that should verify to false
        ArrayList<String> filesFalse = new ArrayList<>();
        filesFalse.add(dir + "incorrect_hypotheses_false.txt");
        filesFalse.add(dir + "zero_false.txt");
        filesFalse.add(dir + "twoline_false.txt");

        verifyAndPrint(filesTrue, true);
        System.out.println();
        verifyAndPrint(filesFalse, false);
    }

    public static void verifyAndPrint(ArrayList<String> files, boolean expected) throws Exception {
        for(String file : files) {
            System.out.println("Verifying file: " + file);
            File proofFile = new File(file);
            Scanner currFile = new Scanner(proofFile);
            Proof root = Proof.parseProof(currFile);
            System.out.println("\tresult: " + root.validate() + "\texpected: " + expected);

            //todo: add profiling
        }
    }
}
