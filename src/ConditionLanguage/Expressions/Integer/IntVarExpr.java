package ConditionLanguage.Expressions.Integer;

import ConditionLanguage.Expressions.Expr;

import java.util.ArrayList;

public class IntVarExpr extends Expr {
    private String name;
    private final String PREFIX = "i$";
    private boolean et = false;

    public IntVarExpr(String n) {
        this.name = PREFIX + n;
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
        return "IntVar("+this.name+")";
    }

    @Override
    public void subVar(String oldVar, String newVar) {
        if(this.name.equals(PREFIX + oldVar)) {
            this.name = PREFIX + newVar;
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
            toReturn.add(this.name.substring(PREFIX.length()));
        }

        return toReturn;
    }

    @Override
    public ArrayList<String> getBtVars() {
        return new ArrayList<>();
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
