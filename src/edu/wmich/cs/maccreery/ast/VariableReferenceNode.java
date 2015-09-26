package edu.wmich.cs.maccreery.ast;

public abstract class VariableReferenceNode extends ExpressionNode
{
  private String image;

  public void setImage(String image) { this.image = image; }
  public String getImage() { return image; }
}
