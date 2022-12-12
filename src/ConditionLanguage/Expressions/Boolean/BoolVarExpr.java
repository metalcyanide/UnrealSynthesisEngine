package ConditionLanguage.Expressions.Boolean;

import ConditionLanguage.Expressions.Expr;
import ConditionLanguage.Expressions.Integer.IntVarExpr;

import java.util.ArrayList;

public class BoolVarExpr extends Expr {
    private String name;
    private final String PREFIX = "b$";
    private boolean existential = false;
    private boolean bt = false;

    public BoolVarExpr(String n) {
        this.name = PREFIX + n;
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
        return "BoolVar("+this.name+")";
    }

    @Override
    public void subVar(String oldVar, String newVar) {
        if(this.name.equals(PREFIX + oldVar)) {
            this.name = PREFIX + newVar;
        }
    }

    @Override
    public void existVar(String varName) {
        if(this.name.equals(PREFIX + varName)) {
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
        if(this.bt) toReturn.add(this.name.substring(PREFIX.length()));

        return toReturn;
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
