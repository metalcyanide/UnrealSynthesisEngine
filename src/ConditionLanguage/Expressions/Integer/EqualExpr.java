package ConditionLanguage.Expressions.Integer;

import ConditionLanguage.Expressions.Expr;

import java.util.Arrays;

public class EqualExpr extends Expr {
    private Expr[] children;

    public EqualExpr(Expr... args) {
        if(args.length != 2) {
            throw new IllegalArgumentException("EQUAL needs exactly two arguments");
        }

        for(Expr arg : args) {
            if(arg.getType() != Expr.ExprType.INT) {
                throw new IllegalArgumentException("EQUAL received non-integer argument: " + arg.toString());
            }
        }

        this.children = args;
    }

    @Override
    public Expr.ExprKind getKind() {
        return Expr.ExprKind.EQ;
    }

    @Override
    public Expr.ExprType getType() {
        return Expr.ExprType.BOOL;
    }

    @Override
    public String toString() {
        return "EqualExpr";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return Arrays.equals(children, ((EqualExpr) o).children);
    }

    public Expr childAt(int i) {
        return this.children[i];
    }
}
