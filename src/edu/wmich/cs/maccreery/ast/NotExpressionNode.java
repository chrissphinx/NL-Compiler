package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class NotExpressionNode extends ExpressionNode implements Visitable
{
  private ExpressionNode operand;

  public NotExpressionNode(ExpressionNode operand) {
    children = new ASTVectorNode<ASTNode>();
    this.setOperand(operand);
  }

  @SuppressWarnings("unchecked")
  public void setOperand(ExpressionNode operand) { this.operand = operand; children.add(operand); }
  public ExpressionNode getOperand() { return operand; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
