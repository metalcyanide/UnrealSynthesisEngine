package ConditionLanguage.Expressions.Boolean;

import ConditionLanguage.Expressions.Expr;

public class NotExpr extends Expr {
    private Expr child;

    public NotExpr(Expr toNegate) {
        if(toNegate.getType() != ExprType.BOOL) {
            throw new IllegalArgumentException("NOT received non-boolean argument: " + toNegate.toString());
        }
        this.child = toNegate;
    }

    @Override
    public Expr.ExprKind getKind() {
        return ExprKind.NOT;
    }

    @Override
    public ExprType getType() {
        return ExprType.BOOL;
    }

    @Override
    public String toString() {
        return "NotExpr";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return this.getChild() == ((NotExpr)o).getChild();
    }

    public Expr getChild() {
        return this.child;
    }
}
