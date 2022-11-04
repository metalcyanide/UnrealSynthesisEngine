package ConditionLanguage;

/*
   Interface for the P, Q condition objects of the logic.
 */
public interface ICondition <T extends ICondition<T>>{
  /*
    Checks if self implies b, given that self and b are of the same type.
   */
  boolean implies(T b);

  // We may need to be able to do transformations on conditions depending on what inference rules we support.
}
