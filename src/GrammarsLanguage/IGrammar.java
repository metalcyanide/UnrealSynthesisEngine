package GrammarsLanguage;

/*
 * Not 100% sure, but this should just be a lookup table/mapping thing that maps non-terminals to production rules.
 * Given a non-terminal, return a list of production rules, which are each just partial programs.
 * Note that non-terminals are really just partial programs themselves.
 */
public interface IGrammar {
	/*
	 * Given a non-terminal t, return as a list the (partial) programs resulting from the productions of t.
	 *
	 */
	// <nonTerminal extends IProgram> IProgram[] getProductions(nonTerminal t);
	public IProgram[] getProductions(String t);
}
