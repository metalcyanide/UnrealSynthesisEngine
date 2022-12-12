package ConditionLanguage.Expressions.Boolean;

import ConditionLanguage.Expressions.Expr;
import ConditionLanguage.Expressions.Integer.IntVarExpr;

public class BoolVarExpr extends Expr {
    private String name;
    private final String PREFIX = "b$";

    public BoolVarExpr(String n) {
        this.name = PREFIX + n;
    }

    @Override
    public Expr.ExprKind getKind() {
        return ExprKind.BVAR;
    }

    @Override
    public ExprType getType() {
        return ExprType.BOOL;
    }

    @Override
    public String toString() {
        return "BoolVar("+this.name+")";
    }

    @Override
    public void subVar(String oldVar, String newVar) {
        if(this.name.equals(PREFIX + oldVar)) {
            this.name = PREFIX + newVar;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return this.getName().equals(((BoolVarExpr) o).getName());
    }

    public String getName() {
        return this.name;
    }
}
