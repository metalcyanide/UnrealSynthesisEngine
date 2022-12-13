package ConditionLanguage.Expressions.Integer;

import ConditionLanguage.Expressions.Boolean.AndExpr;
import ConditionLanguage.Expressions.Expr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlusExpr extends Expr {
    private Expr[] children;

    public PlusExpr(Expr... args) {
        if(args.length < 2) {
            throw new IllegalArgumentException("PLUS needs at least two arguments");
        }

        for(Expr arg : args) {
            if(arg.getType() != Expr.ExprType.INT) {
                throw new IllegalArgumentException("PLUS received non-integer argument: " + arg.toString());
            }
        }

        this.children = args;
    }

    @Override
    public Expr.ExprKind getKind() {
        return Expr.ExprKind.PLUS;
    }

    @Override
    public Expr.ExprType getType() {
        return Expr.ExprType.INT;
    }

    @Override
    public String toString() {
        return "PlusExpr";
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
        if(! (expr instanceof PlusExpr)) return null;
        if(((PlusExpr)expr).children.length != this.children.length) return null;

        ArrayList<String> toReturn = new ArrayList<>();
        for(int i = 0; i < this.children.length; i++) {
            ArrayList<String> result = this.children[i].getSubs(oldVars, ((PlusExpr)expr).children[i]);
            if(result == null) return null;
            toReturn.addAll(result);
        }

        return toReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;

        // check equality, independent of commutativity
        List<Expr> thisChildren = Arrays.asList(this.children);
        List<Expr> otherChildren = Arrays.asList(((PlusExpr) o).children);
        return thisChildren.containsAll(otherChildren) && otherChildren.containsAll(thisChildren);
    }

    public Expr childAt(int i) {
        return this.children[i];
    }
}
