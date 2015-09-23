package edu.wmich.cs.maccreery.ast;

public abstract class SubProgramDeclNode extends ASTNode
{
  private String image;
  private CompoundStatementNode body;
  private ASTVectorNode<ASTNode> variableDecls;

  public void setImage(String image) { this.image = image; }
  public String getImage() { return image; }

  public void setBody(CompoundStatementNode body) { this.body = body; children.add(body); }
  public CompoundStatementNode getBody() { return body; }

  public void setVariableDecls(ASTVectorNode<ASTNode> variableDecls) { this.variableDecls = variableDecls; children.add(variableDecls); }
  public ASTVectorNode<ASTNode> getVariableDecls() { return variableDecls; }
}
