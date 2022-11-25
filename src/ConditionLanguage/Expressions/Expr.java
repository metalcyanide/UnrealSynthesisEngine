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
        //TODO implement all of these
        //Boolean
        AND, OR, IMPL, EQ, GEQ, GT,
        //Integer
        PLUS, MINUS, MULT, DIV, MOD,
        //Constants/Variables
        TRUE, FALSE, ICONST, IVAR, BVAR
    }

    public abstract ExprKind getKind();
    public abstract ExprType getType();
    public abstract String toString();
    //TODO converter to z3
//    public abstract String toSMT();
}
