from z3 import * 
constraints = Not(Or(Not(False),True))
s = Solver()
s.add(constraints)
print(s.check())
