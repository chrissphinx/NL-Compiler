package edu.wmich.cs.maccreery.ast;

public abstract class VariableReferenceNode extends ExpressionNode
{
  private String image;
  private int nestingLevel;

  public void setImage(String image) { this.image = image; }
  public String getImage() { return image; }

  public void setNestingLevel(int nestingLevel) { this.nestingLevel = nestingLevel; }
  public int getNestingLevel() { return nestingLevel; }
}
