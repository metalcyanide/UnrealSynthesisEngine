##Some thoughts on implementing proofs and verifiers

We can think of proofs as trees as we have been.

Each node is an application of an inference rule that proves some claim X (or a hole for a proof that will prove a claim X). It would be nice to have proof subtypes for each of these inference rules, but we can use string tags if we want, and the visitor design pattern below will still work.

It is useful to decouple the way that we define and construct statements form the way that we write proofs. For this reason, we can consider having our verifier be a visitor on proof objects. This would allow us to consolidate all of the verification logic in a single place, rather than spreading it out through each type of proof/inference rule.



## Additional Todos
 - At some point, we should add contexts. Maybe milestone 2?
