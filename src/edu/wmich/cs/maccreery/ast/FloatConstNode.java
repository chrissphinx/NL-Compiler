package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class FloatConstNode extends ConstantNode implements Visitable
{
  private float constant;

  public FloatConstNode(float constant) {
    this.setConstant(constant);
  }

  public void setConstant(float constant) { this.constant = constant; }
  public float getConstant() { return constant; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
