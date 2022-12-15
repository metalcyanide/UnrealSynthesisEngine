import Proofs.Proof;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) throws Exception {
        String dir = "proof-files/";
        String fileType = ".txt";
        String truePrefix = "true_";
        String falsePrefix = "false_";

        // proofs that should verify to true
        ArrayList<String> filesTrue = new ArrayList<>();
        filesTrue.add(dir + "true_zero.txt");
        filesTrue.add(dir + "true_twoline.txt");
        filesTrue.add(dir + "true_weaken.txt");
        filesTrue.add(dir + "true_seq.txt");
//        filesTrue.add(dir + "test.txt");

        // proofs that should verify to false
        ArrayList<String> filesFalse = new ArrayList<>();
        filesFalse.add(dir + "false_incorrect_hypotheses.txt");
        filesFalse.add(dir + "false_zero.txt");
        filesFalse.add(dir + "false_twoline.txt");

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
