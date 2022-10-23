package ConditionLanguage;


import java.util.Scanner;

/*
   Interface for the P, Q condition objects of the logic.
 */
public interface ICondition <T extends ICondition<T>>{

  /*
    Given a buffer to read from, reads condition from sequence of chars and returns a condition object.
    Should probably be a static constructor.
   */
  ICondition parseCondition(Scanner readFrom);

  /*
    Checks if self implies b, given that self and b are of the same type.
   */
  boolean implies(T b);

  // We may need to be able to do transformations on conditions depending on what inference rules we support.
}
