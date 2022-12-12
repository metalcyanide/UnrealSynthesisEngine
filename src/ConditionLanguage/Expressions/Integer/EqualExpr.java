package ConditionLanguage.Expressions.Integer;

import ConditionLanguage.Expressions.Expr;

import java.util.ArrayList;
import java.util.Arrays;

public class EqualExpr extends Expr {
    private Expr[] children;

    public EqualExpr(Expr... args) {
        if(args.length != 2) {
            throw new IllegalArgumentException("EQUAL needs exactly two arguments");
        }

        for(Expr arg : args) {
            if(arg.getType() != Expr.ExprType.INT) {
                throw new IllegalArgumentException("EQUAL received non-integer argument: " + arg.toString());
            }
        }

        this.children = args;
    }

    @Override
    public Expr.ExprKind getKind() {
        return Expr.ExprKind.EQ;
    }

    @Override
    public Expr.ExprType getType() {
        return Expr.ExprType.BOOL;
    }

    @Override
    public String toString() {
        return "EqualExpr";
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;

        // check equality, independent of commutativity
        return Arrays.equals(children, ((EqualExpr) o).children)
                || (children[0] == ((EqualExpr) o).children[1] && children[1] == ((EqualExpr) o).children[0]);
    }

    public Expr childAt(int i) {
        return this.children[i];
    }
}
