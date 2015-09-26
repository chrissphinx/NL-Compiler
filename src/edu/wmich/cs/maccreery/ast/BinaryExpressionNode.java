package edu.wmich.cs.maccreery.ast;

public abstract class BinaryExpressionNode extends ExpressionNode
{
  private ExpressionNode leftOperand;
  private ExpressionNode rightOperand;

  @SuppressWarnings("unchecked")
  public void setLeftOperand(ExpressionNode leftOperand) { this.leftOperand = leftOperand; children.add(0, leftOperand); }
  public ExpressionNode getLeftOperand() { return leftOperand; }

  @SuppressWarnings("unchecked")
  public void setRightOperand(ExpressionNode rightOperand) { this.rightOperand = rightOperand; children.add(rightOperand); }
  public ExpressionNode getRightOperand() { return rightOperand; }
}
