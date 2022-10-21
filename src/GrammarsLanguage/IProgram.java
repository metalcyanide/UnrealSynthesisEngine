package GrammarsLanguage;

import java.io.BufferedInputStream;

/*
   An interface for the middle part of the UL triple (the "S" in {P} S {Q}).
   Ideally, we'd have a method here to extract the useful info from a program.
   By that I mean the top-level program construct (as in an AST), or more specifically the form of the program that determines what inference rules may be applied.
   I'm not 100% sure what this information looks like, but figuring it our is a big todo before we can get a working verifier.
 */
public interface IProgram {

  /*
  Given a buffer to read from, constructs program object.
  Ought to be static constructor.
 */
  IProgram parseProgram(BufferedInputStream readFrom);

}
