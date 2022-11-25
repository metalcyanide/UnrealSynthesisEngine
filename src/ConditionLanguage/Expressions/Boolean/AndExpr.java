package ConditionLanguage.Expressions.Boolean;

import ConditionLanguage.Expressions.Expr;

import java.util.Arrays;

public class AndExpr extends Expr {

    private Expr[] children;

    public AndExpr(Expr... args) {
        if(args.length < 2) {
            throw new IllegalArgumentException("AND needs at least two arguments");
        }

        for(Expr arg : args) {
            if(arg.getType() != ExprType.BOOL) {
                throw new IllegalArgumentException("AND received non-boolean argument: " + arg.toString());
            }
        }

        this.children = args;
    }

    @Override
    public ExprKind getKind() {
        return ExprKind.AND;
    }

    @Override
    public ExprType getType() {
        return ExprType.BOOL;
    }

    @Override
    public String toString() {
        return "AndExpr";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return Arrays.equals(children, ((AndExpr) o).children);
    }

    public Expr childAt(int i) {
        return this.children[i];
    }
}
