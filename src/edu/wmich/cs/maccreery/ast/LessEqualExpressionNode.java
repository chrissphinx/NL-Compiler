package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class LessEqualExpressionNode extends BinaryComparisonNode implements Visitable
{
  public LessEqualExpressionNode(ExpressionNode rightOperand) {
    this.setRightOperand(rightOperand);
  }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
