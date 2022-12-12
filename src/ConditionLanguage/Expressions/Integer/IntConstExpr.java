package ConditionLanguage.Expressions.Integer;

import ConditionLanguage.Expressions.Expr;

import java.util.ArrayList;


public class IntConstExpr extends Expr {
    private final int value;

    public IntConstExpr(int val) {
        this.value = val;
    }

    @Override
    public ExprKind getKind() {
        return ExprKind.ICONST;
    }

    @Override
    public ExprType getType() {
        return ExprType.INT;
    }

    @Override
    public String toString() {
        return "IntExpr("+this.value+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return this.getValue() == ((IntConstExpr)o).getValue();
    }

    @Override
    public void subVar(String oldVar, String newVar) {
        // do nothing
        return;
    }

    @Override
    public void existVar(String varName) {
        // do nothing
        return;
    }

    @Override
    public ArrayList<String> getEtVars() {
        // empty
        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> getBtVars() {
        // empty
        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> getVars() {
        // empty
        return new ArrayList<>();
    }

    public int getValue() {
        return this.value;
    }
}
