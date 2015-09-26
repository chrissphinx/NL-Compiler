package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class IntegerConstNode extends ConstantNode implements Visitable
{
  private int constant;

  public IntegerConstNode(int constant) {
    this.setConstant(constant);
  }

  public void setConstant(int constant) { this.constant = constant; }
  public int getConstant() { return constant; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
