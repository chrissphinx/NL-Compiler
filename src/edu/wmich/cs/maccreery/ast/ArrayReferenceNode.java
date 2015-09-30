package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class ArrayReferenceNode extends VariableReferenceNode implements Visitable
{
  private ExpressionNode subscript;

  public ArrayReferenceNode(String image, ExpressionNode subscript) {
    children = new Vector();
    this.setImage(image);
    this.setSubscript(subscript);
  }

  @SuppressWarnings("unchecked")
  public void setSubscript(ExpressionNode subscript) { this.subscript = subscript; children.add(subscript); }
  public ExpressionNode getSubscript() { return subscript; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
