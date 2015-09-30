package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class AndExpressionNode extends BinaryExpressionNode implements Visitable
{
  public AndExpressionNode(ExpressionNode rightOperand) {
    children = new Vector();
    this.setRightOperand(rightOperand);
  }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
