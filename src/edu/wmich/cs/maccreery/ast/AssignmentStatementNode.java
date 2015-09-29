package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class AssignmentStatementNode extends StatementNode implements Visitable
{
  private VariableReferenceNode left;
  private ExpressionNode right;

  public AssignmentStatementNode(VariableReferenceNode left, ExpressionNode right) {
    children = new ASTVectorNode<ASTNode>();
    this.setLeft(left);
    this.setRight(right);
  }

  @SuppressWarnings("unchecked")
  public void setLeft(VariableReferenceNode left) { this.left = left; children.add(left); }
  public VariableReferenceNode getLeft() { return left; }

  @SuppressWarnings("unchecked")
  public void setRight(ExpressionNode right) { this.right = right; children.add(right); }
  public ExpressionNode getRight() { return right; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
