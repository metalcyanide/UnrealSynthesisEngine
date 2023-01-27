import GrammarsLanguage.Grammar;
import Proofs.Proof;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) throws Exception {
        String proofDir = "proof-files/";
        String gramDir = "grammar-files/";
        String fileType = ".txt";
        String truePrefix = "true_";
        String falsePrefix = "false_";

        // proofs that should verify to true
        ArrayList<String[]> filesTrue = new ArrayList<>();
//        filesTrue.add(new String[] {proofDir + "true_zero.txt", gramDir + "gram_trivial.txt"});
//        filesTrue.add(new String[] {proofDir + "true_twoline.txt", gramDir + "gram_trivial.txt"});
//        filesTrue.add(new String[] {proofDir + "true_weaken.txt", gramDir + "gram_trivial.txt"});
//        filesTrue.add(new String[] {proofDir + "true_seq.txt", gramDir + "gram_trivial.txt"});
//        filesTrue.add(new String[] {proofDir + "true_assign.txt", gramDir + "gram_trivial.txt"});
//        filesTrue.add(new String[] {proofDir + "test.txt", gramDir + "gram_trivial.txt"});
        filesTrue.add(new String[] {proofDir + "true_nonterm.txt", gramDir + "gram_S1.txt"});

        // proofs that should verify to false
        ArrayList<String[]> filesFalse = new ArrayList<>();
//        filesFalse.add(new String[] {proofDir + "false_incorrect_hypotheses.txt", gramDir + "gram_trivial.txt"});
//        filesFalse.add(new String[] {proofDir + "false_zero.txt", gramDir + "gram_trivial.txt"});
//        filesFalse.add(new String[] {proofDir + "false_twoline.txt", gramDir + "gram_trivial.txt"});
//        filesFalse.add(new String[] {proofDir + "false_complex.txt", gramDir + "gram_trivial.txt"});
//        filesFalse.add(new String[] {proofDir + "false_nonterm.txt", gramDir + "gram_trivial.txt"});

        verifyAndPrint(filesTrue, true);
        System.out.println();
        verifyAndPrint(filesFalse, false);
    }

    public static void verifyAndPrint(ArrayList<String[]> files, boolean expected) throws Exception {
        for(String[] filePair : files) {
            System.out.println("Verifying file: " + filePair[0]);
            File proofFile = new File(filePair[0]);
            String grammarString = Files.readString(Path.of(filePair[1]));
            Scanner currFile = new Scanner(proofFile);
            Proof root = Proof.parseProof(currFile, new Grammar(grammarString));
            System.out.println("\tresult: " + root.validate() + "\texpected: " + expected);

            //todo: add profiling
        }
    }
}
