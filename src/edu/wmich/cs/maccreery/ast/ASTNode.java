package edu.wmich.cs.maccreery.ast;

public abstract class ASTNode
{
  private int lineNumber;
  private ASTNode parent;

  public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
  public int getLineNumber() { return lineNumber; }

  public void setParent(ASTNode parent) { this.parent = parent; }
  public ASTNode getParent() { return parent; }
}
