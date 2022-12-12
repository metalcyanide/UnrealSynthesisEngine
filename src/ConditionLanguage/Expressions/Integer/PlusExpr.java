package ConditionLanguage.Expressions.Integer;

import ConditionLanguage.Expressions.Expr;

import java.util.Arrays;

public class PlusExpr extends Expr {
    private Expr[] children;

    public PlusExpr(Expr... args) {
        if(args.length < 2) {
            throw new IllegalArgumentException("PLUS needs at least two arguments");
        }

        for(Expr arg : args) {
            if(arg.getType() != Expr.ExprType.INT) {
                throw new IllegalArgumentException("PLUS received non-integer argument: " + arg.toString());
            }
        }

        this.children = args;
    }

    @Override
    public Expr.ExprKind getKind() {
        return Expr.ExprKind.PLUS;
    }

    @Override
    public Expr.ExprType getType() {
        return Expr.ExprType.INT;
    }

    @Override
    public String toString() {
        return "PlusExpr";
    }

    @Override
    public void subVar(String oldVar, String newVar) {
        for(Expr child : children) {
            child.subVar(oldVar, newVar);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return Arrays.equals(children, ((PlusExpr) o).children);
    }

    public Expr childAt(int i) {
        return this.children[i];
    }
}
