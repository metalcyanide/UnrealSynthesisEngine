package ConditionLanguage.Expressions.Boolean;

import ConditionLanguage.Expressions.Expr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrExpr extends Expr {
    private Expr[] children;

    public OrExpr(Expr... args) {
        if(args.length < 2) {
            throw new IllegalArgumentException("OR needs at least two arguments");
        }

        for(Expr arg : args) {
            if(arg.getType() != ExprType.BOOL) {
                throw new IllegalArgumentException("OR received non-boolean argument: " + arg.toString());
            }
        }

        this.children = args;
    }

    @Override
    public ExprKind getKind() {
        return ExprKind.OR;
    }

    @Override
    public ExprType getType() {
        return ExprType.BOOL;
    }

    @Override
    public String toString() {
        return "OrExpr";
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;

        // check equality, independent of commutativity
        List<Expr> thisChildren = Arrays.asList(this.children);
        List<Expr> otherChildren = Arrays.asList(((OrExpr) o).children);
        return thisChildren.containsAll(otherChildren) && otherChildren.containsAll(thisChildren);
    }

    public Expr childAt(int i) {
        return this.children[i];
    }
}
