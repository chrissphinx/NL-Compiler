package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import java.util.Vector;

public abstract class ASTNode implements Visitable
{
  private int lineNumber;
  private ASTNode parent;
  protected Vector children;

  public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
  public int getLineNumber() { return lineNumber; }

  public void setParent(ASTNode parent) { this.parent = parent; }
  public ASTNode getParent() { return parent; }
}
