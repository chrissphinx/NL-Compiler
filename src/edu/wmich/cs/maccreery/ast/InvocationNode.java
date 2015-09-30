package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class InvocationNode extends ExpressionNode implements Visitable
{
  private String image;
  private Vector actualParameters;

  public InvocationNode(String image, Vector actualParameters) {
    children = new Vector();
    this.setImage(image);
    this.setActualParameters(actualParameters);
  }

  public void setImage(String image) { this.image = image; }
  public String getImage() { return image; }

  @SuppressWarnings("unchecked")
  public void setActualParameters(Vector actualParameters) { this.actualParameters = actualParameters; children.add(actualParameters); }
  public Vector getActualParameters() { return actualParameters; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
