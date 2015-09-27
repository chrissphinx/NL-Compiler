package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class LessThanExpressionNode extends BinaryComparisonNode implements Visitable
{
  public LessThanExpressionNode(ExpressionNode rightOperand) {
    children = new ASTVectorNode<ASTNode>();
    this.setRightOperand(rightOperand);
  }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
