# UnrealSynthesisEngine
Proof synthesizer for Unrealizability Logic

## Current Todos: This is garbage- incomplete
### Establish Input Languages
- Choose language (and solver) for specifying conditions. [NRA & Z3 is one option.]
- Choose language for encoding grammars.
- Choose language for encoding proofs.

### Establish Necessary Program Constructs (& Their Interfaces)
- Define proof class.
- Define statement ({P} S {Q}) class.
- Define inference rules (perhaps as factories).
- Define class for conditions (P & Q).
- Define class for grammar & non-terminals/incomplete programs.
- Define map from non-terminals to allowed inference rules (e.g., to factories).

### Build Main Verifier Logic
- Bottom/Leaf-up proof parser, checking rules as we go? 
