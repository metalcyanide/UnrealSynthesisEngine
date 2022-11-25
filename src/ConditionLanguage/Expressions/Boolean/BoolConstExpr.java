package ConditionLanguage.Expressions.Boolean;

import ConditionLanguage.Expressions.Expr;

public class BoolConstExpr extends Expr {
    private final boolean value;

    public BoolConstExpr(boolean val) {
        this.value = val;
    }

    @Override
    public Expr.ExprKind getKind() {
        return ExprKind.BCONST;
    }

    @Override
    public ExprType getType() {
        return ExprType.BOOL;
    }

    @Override
    public String toString() {
        return "BoolExpr("+this.value+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return this.getValue() == ((BoolConstExpr)o).getValue();
    }

    public boolean getValue() {
        return this.value;
    }
}
