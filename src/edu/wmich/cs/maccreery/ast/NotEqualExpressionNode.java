package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class NotEqualExpressionNode extends BinaryComparisonNode implements Visitable
{
  public NotEqualExpressionNode(ExpressionNode rightOperand) {
    children = new Vector();
    this.setRightOperand(rightOperand);
  }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
