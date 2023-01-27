package GrammarsLanguage;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Stream;

public class Grammar extends AGrammar<Program> {

  public Grammar(String grammarString) throws Exception {
    super();
    for (String line: grammarString.split(System.getProperty("line.separator"))) {
      if (line.equals("")) continue;
      if (line.split("::=").length != 2) throw new Exception("Grammar line " + line + "has incorrect number of ::=");
      String nonTerminal = line.split("::=")[0];
      LinkedList<Program> expansions = new LinkedList<Program>();
      for ( String expansion : line.split("::=")[1].split("|")
      ) {
        expansions.add(Program.parseStatement(new Scanner(expansion), this));
      }
    }
  }

}
