package ConditionLanguage.Expressions.Boolean;

import ConditionLanguage.Expressions.Expr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    public ArrayList<String> getSubs(ArrayList<String> oldVars, Expr expr) {
        if(! (expr instanceof OrExpr)) return null;
        if(((OrExpr)expr).children.length != this.children.length) return null;

        ArrayList<String> toReturn = new ArrayList<>();
        for(int i = 0; i < this.children.length; i++) {
            ArrayList<String> result = this.children[i].getSubs(oldVars, ((OrExpr)expr).children[i]);
            if(result == null) return null;
            toReturn.addAll(result);
        }

        return toReturn;
    }

    @Override
    public ArrayList<String> getVarsByName(String x) {
        ArrayList<String> toReturn = new ArrayList<>();
        for(Expr child : children) {
            toReturn.addAll(child.getVarsByName(x));
        }

        return toReturn;
    }

    @Override
    public String toSMT(HashMap<String, Integer> map) {
        StringBuilder result = new StringBuilder("Or(");
        for(Expr child : children) {
            result.append(child.toSMT(map));
            result.append(",");
        }

        result.setCharAt(result.length()-1, ')');
        return result.toString();
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
