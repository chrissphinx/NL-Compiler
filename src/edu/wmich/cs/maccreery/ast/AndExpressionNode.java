package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class AndExpressionNode extends BinaryExpressionNode implements Visitable
{
  public AndExpressionNode(ExpressionNode rightOperand) {
    this.setRightOperand(rightOperand);
  }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
