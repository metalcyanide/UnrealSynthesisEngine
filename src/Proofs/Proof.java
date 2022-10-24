import java.util.Scanner;

public abstract class Proof extends IProof{
  private Proof[] children;

  private IULTriple claim;

  public Proof(IULTriple claim, Proof... children)
  {
    this.claim = claim;
    this.children = children;
  }

  public static Proof parseProof(Scanner readFrom) {
	  return new Proof(null, null);
  }

  public IULTriple getClaim() {
	  return claim;
  }

  public Proof[] getChildren() {
	  return children;
  }


}

