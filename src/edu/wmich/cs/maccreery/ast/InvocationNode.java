package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class InvocationNode extends ExpressionNode implements Visitable
{
  private String image;
  private ASTVectorNode<ASTNode> actualParameters;

  public InvocationNode(String image, ASTVectorNode<ASTNode> actualParameters) {
    children = new ASTVectorNode<ASTNode>();
    this.setImage(image);
    this.setActualParameters(actualParameters);
  }

  public void setImage(String image) { this.image = image; }
  public String getImage() { return image; }

  @SuppressWarnings("unchecked")
  public void setActualParameters(ASTVectorNode<ASTNode> actualParameters) { this.actualParameters = actualParameters; children.add(actualParameters); }
  public ASTVectorNode<ASTNode> getActualParameters() { return actualParameters; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
