package ConditionLanguage.Expressions;

/*
 * Class to represent expressions for conditions.
 * Represented in AST format.
 */
public abstract class Expr {

    public enum ExprType
    {
        BOOL, INT
    }
    public enum ExprKind
    {
        //TODO implement remaining
        //Boolean
        AND, OR, NOT, IMPL,
        //Comparison
        EQ, GEQ, GT,
        //Integer
        PLUS, MINUS, MULT, DIV, MOD,
        //Constants/Variables
        ICONST, BCONST, IVAR, BVAR
    }

    public abstract ExprKind getKind(); //used to check/cast generic Expr
    public abstract ExprType getType(); //used to type check arguments
    public abstract String toString();

    public abstract void subVar(String oldVar, String newVar);
    public abstract void existVar(String varName);

    //TODO converter to z3
//    public abstract String toSMT(); //converts AST into a z3 query
    //TODO pretty print?
//    public abstract String prettyPrint(); //prints out entire AST
}
