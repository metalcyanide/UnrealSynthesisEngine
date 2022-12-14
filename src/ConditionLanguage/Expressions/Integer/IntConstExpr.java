package ConditionLanguage.Expressions.Integer;

import ConditionLanguage.Expressions.Boolean.BoolConstExpr;
import ConditionLanguage.Expressions.Expr;

import java.util.ArrayList;
import java.util.HashMap;


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

    @Override
    public ArrayList<String> getVarsByName(String x) {
        // empty
        return new ArrayList<>();
    }

    @Override
    public String toSMT(HashMap<String, Integer> map) {
        return value + "";
    }

    @Override
    public ArrayList<String> getSubs(ArrayList<String> oldVars, Expr expr) {
        if(!(expr instanceof IntConstExpr)) return null;

        //check that they're both the same number
        IntConstExpr otherExpr = (IntConstExpr) expr;
        if(this.getValue() != otherExpr.getValue()) return null;

        return new ArrayList<>();
    }

    public int getValue() {
        return this.value;
    }
}
