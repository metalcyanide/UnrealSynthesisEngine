package GrammarsLanguage;

import java.io.BufferedInputStream;

/*
   An interface for the middle part of the UL triple (the "S" in {P} S {Q}
 */
public interface Program {

  /*
  Given a buffer to read from, constructs program object.
  Ought to be static constructor.
 */
  Program parseProgram(BufferedInputStream readFrom);

}
