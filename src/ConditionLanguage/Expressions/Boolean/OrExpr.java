package ConditionLanguage.Expressions.Boolean;

import ConditionLanguage.Expressions.Expr;

import java.util.Arrays;

public class OrExpr extends Expr {
    private Expr[] children;

    public OrExpr(Expr... args) {
        if(args.length < 2) {
            throw new IllegalArgumentException("OR needs at least two arguments");
        }

        for(Expr arg : args) {
            if(arg.getType() != ExprType.BOOL) {
                throw new IllegalArgumentException("OR received non-boolean argument: " + arg.toString());
            }
        }

        this.children = args;
    }

    @Override
    public ExprKind getKind() {
        return ExprKind.OR;
    }

    @Override
    public ExprType getType() {
        return ExprType.BOOL;
    }

    @Override
    public String toString() {
        return "OrExpr";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return Arrays.equals(children, ((OrExpr) o).children);
    }

    public Expr childAt(int i) {
        return this.children[i];
    }
}
