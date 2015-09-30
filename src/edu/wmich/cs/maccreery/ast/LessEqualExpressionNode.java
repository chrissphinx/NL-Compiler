package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class LessEqualExpressionNode extends BinaryComparisonNode implements Visitable
{
  public LessEqualExpressionNode(ExpressionNode rightOperand) {
    children = new Vector();
    this.setRightOperand(rightOperand);
  }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
