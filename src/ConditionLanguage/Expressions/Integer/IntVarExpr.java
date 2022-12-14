package ConditionLanguage.Expressions.Integer;

import ConditionLanguage.Expressions.Boolean.BoolVarExpr;
import ConditionLanguage.Expressions.Expr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class IntVarExpr extends Expr {
    private String name;
    private final String PREFIX = "i$";
    private boolean et = false;

    public IntVarExpr(String n) {
        this.name = n;
        if(n.charAt(0) == '@') this.et = true;
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
        return "IntVar("+PREFIX + this.name+")";
    }

    @Override
    public void subVar(String oldVar, String newVar) {
        if(this.name.equals(oldVar)) {
            this.name = newVar;
        }
    }

    @Override
    public void existVar(String varName) {
        // do nothing
        return;
    }

    @Override
    public ArrayList<String> getEtVars() {
        ArrayList<String> toReturn = new ArrayList<>();
        if(this.et) {
            toReturn.add(this.name);
        }

        return toReturn;
    }

    @Override
    public ArrayList<String> getBtVars() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> getVars() {
        return new ArrayList<>(Collections.singleton(this.name));
    }

    @Override
    public ArrayList<String> getSubs(ArrayList<String> oldVars, Expr expr) {
        if(!(expr instanceof IntVarExpr)) return null;
        IntVarExpr newExpr = (IntVarExpr) expr;

        ArrayList<String> toReturn = new ArrayList<>();
        if(oldVars.contains(this.name)) {
            toReturn.add(newExpr.getName());
        }

        return toReturn;
    }

    @Override
    public ArrayList<String> getVarsByName(String x) {
        ArrayList<String> toReturn = new ArrayList<>();
        if(this.name.startsWith(x)) toReturn.add(this.name);

        return toReturn;
    }

    @Override
    public String toSMT(HashMap<String, Integer> map) {
        map.put(this.name, 0);
        // todo does this need to check for existential?
        return this.name;
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

    public boolean isEt() { return this.et; }
}
