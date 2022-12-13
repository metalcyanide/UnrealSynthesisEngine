package ConditionLanguage.Expressions.Integer;

import ConditionLanguage.Expressions.Expr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GreaterExpr extends Expr {
    private Expr[] children;

    public GreaterExpr(Expr... args) {
        if(args.length != 2) {
            throw new IllegalArgumentException("GREATER needs exactly two arguments");
        }

        for(Expr arg : args) {
            if(arg.getType() != Expr.ExprType.INT) {
                throw new IllegalArgumentException("GREATER received non-integer argument: " + arg.toString());
            }
        }

        this.children = args;
    }

    @Override
    public ExprKind getKind() {
        return Expr.ExprKind.GT;
    }

    @Override
    public ExprType getType() {
        return Expr.ExprType.BOOL;
    }

    @Override
    public String toString() {
        return "GreaterExpr";
    }

    @Override
    public void subVar(String oldVar, String newVar) {
        for(Expr child : children) {
            child.subVar(oldVar, newVar);
        }
    }

    @Override
    public void existVar(String varName) {
        for(Expr child : children) {
            child.existVar(varName);
        }
    }

    @Override
    public ArrayList<String> getEtVars() {
        ArrayList<String> toReturn = new ArrayList<>();
        for(Expr child : children) {
            toReturn.addAll(child.getEtVars());
        }

        return toReturn;
    }

    @Override
    public ArrayList<String> getBtVars() {
        ArrayList<String> toReturn = new ArrayList<>();
        for(Expr child : children) {
            toReturn.addAll(child.getBtVars());
        }

        return toReturn;
    }

    @Override
    public ArrayList<String> getVars() {
        ArrayList<String> toReturn = new ArrayList<>();
        for(Expr child : children) {
            toReturn.addAll(child.getVars());
        }

        return toReturn;
    }

    @Override
    public ArrayList<String> getSubs(ArrayList<String> oldVars, Expr expr) {
        if(! (expr instanceof GreaterExpr)) return null;
        if(((GreaterExpr)expr).children.length != 2) return null;

        ArrayList<String> toReturn = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            ArrayList<String> result = this.children[i].getSubs(oldVars, ((GreaterExpr)expr).children[i]);
            if(result == null) return null;
            toReturn.addAll(result);
        }

        return toReturn;
    }

    @Override
    public String toSMT(HashMap<String, Integer> map) {
        return children[0].toSMT(map) + ">" + children[1].toSMT(map);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;

        return Arrays.equals(children, ((GreaterExpr) o).children);
    }
}
