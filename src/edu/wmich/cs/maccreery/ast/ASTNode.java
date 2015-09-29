package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;

import java.util.Vector;

public abstract class ASTNode implements Visitable
{
  private int lineNumber;
  private int realType;
  private int convertedType;
  private ASTNode parent;
  protected ASTVectorNode<ASTNode> children;

  public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
  public int getLineNumber() { return lineNumber; }

  public void setRealType(int realType) { this.realType = realType; }
  public int getRealType() { return realType; }

  public void setConvertedType(int convertedType) { this.convertedType = convertedType; }
  public int getConvertedType() { return convertedType; }

  public void setParent(ASTNode parent) { this.parent = parent; }
  public ASTNode getParent() { return parent; }

  public boolean containsReturnStatement(Object node) {
    if (node instanceof ReturnStatementNode)
      return true;
    else {
      ASTVectorNode<ASTNode> vec;
      if (!(node instanceof ASTVectorNode))
        vec = ( (ASTNode) node).children;
      else
        vec = (ASTVectorNode<ASTNode>) node;
      boolean contains = false;
      for (int i = 0; i < vec.size() && !contains; i++)
        contains = contains || containsReturnStatement(vec.elementAt(i));

      return contains;
    }
  }
}
