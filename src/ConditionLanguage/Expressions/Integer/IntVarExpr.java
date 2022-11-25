package ConditionLanguage.Expressions.Integer;

import ConditionLanguage.Expressions.Expr;

public class IntVarExpr extends Expr {
    private final String name;

    public IntVarExpr(String n) {
        this.name = "$" + n;
    }

    @Override
    public Expr.ExprKind getKind() {
        return ExprKind.IVAR;
    }

    @Override
    public ExprType getType() {
        return ExprType.INT;
    }

    @Override
    public String toString() {
        return "IntVar("+this.name+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return this.getName().equals(((IntVarExpr) o).getName());
    }

    public String getName() {
        return this.name;
    }
}
