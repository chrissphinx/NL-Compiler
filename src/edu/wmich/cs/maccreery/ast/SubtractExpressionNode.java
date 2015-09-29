package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class SubtractExpressionNode extends BinaryExpressionNode implements Visitable
{
  public SubtractExpressionNode(ExpressionNode rightOperand) {
    children = new ASTVectorNode<ASTNode>();
    this.setRightOperand(rightOperand);
  }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
