package Proofs.Context;

import ConditionLanguage.ICondition;
import GrammarsLanguage.IProgram;

/**
 * The contexts (\Gamma) of unrealizability claims are represented by these objects.
 */
public interface IContext {

    /*
     * Tells you whether prec prog postc is in the context.
     */
    public boolean inContext(ICondition prec, IProgram prog, ICondition postc);

    /*
     * Returns a context with prec prog postc added to it.
     */
    public IContext addToContext(ICondition prec, IProgram prog, ICondition postc);
}
