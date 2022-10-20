# UnrealSynthesisEngine
Proof synthesizer for Unrealizability Logic

## Current Todos:
### Establish Input Languages
- Choose language (and solver) for specifying conditions. [NRA & Z3 is one option.]
- Choose language for encoding grammars.
- Choose language for encoding proofs.

### Establish Necessary Program Constructs (& Their Interfaces)
- Define proof class.
- Define statement (\Gamma \proves {P} S {Q}, or just {P} S {Q} for now) class.
- Define inference rules (perhaps as subtypes or string-semantic pairs).
- Define class for conditions (P & Q).
- Define class for grammar & non-terminals/incomplete programs. Also define what the useful info from these is. Probably hard and important.
- Define relationship between incomplete programs and inference rules (e.g., in a visitor).

### Build Main Verifier Logic
- Bottom/Leaf-up proof parser, checking rules as we go? 
