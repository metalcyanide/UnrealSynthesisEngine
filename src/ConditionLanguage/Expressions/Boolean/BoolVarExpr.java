package ConditionLanguage.Expressions.Boolean;

import ConditionLanguage.Expressions.Expr;
import ConditionLanguage.Expressions.Integer.IntVarExpr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BoolVarExpr extends Expr {
    private String name;
    private final String PREFIX = "b$";
    private boolean existential = false;
    private boolean bt = false;

    public BoolVarExpr(String n) {
        this.name = n;
        if(n.charAt(0) == '#') this.bt = true;
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
        return "BoolVar("+PREFIX + this.name+")";
    }

    @Override
    public void subVar(String oldVar, String newVar) {
        if(this.name.equals(oldVar)) {
            this.name = newVar;
        }
    }

    @Override
    public void existVar(String varName) {
        if(this.name.equals(varName)) {
            this.existential = true;
        }
    }

    @Override
    public ArrayList<String> getEtVars() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> getBtVars() {
        ArrayList<String> toReturn = new ArrayList<>();
        if(this.bt) toReturn.add(this.name);

        return toReturn;
    }

    @Override
    public ArrayList<String> getVars() {
        return new ArrayList<>(Collections.singleton(this.name));
    }

    @Override
    public ArrayList<String> getSubs(ArrayList<String> oldVars, Expr expr) {
        if(!(expr instanceof BoolVarExpr)) return null;
        BoolVarExpr newExpr = (BoolVarExpr) expr;

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
        map.put(this.name, 1);
        // todo does this need to check for existential?
        return this.name;
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

    public boolean isExistential() { return this.existential; }
    public boolean isBt() { return this.bt; }

}
