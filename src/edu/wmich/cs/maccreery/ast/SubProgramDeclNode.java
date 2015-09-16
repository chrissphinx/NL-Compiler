package edu.wmich.cs.maccreery.ast;

import java.util.Vector;

public abstract class SubProgramDeclNode extends ASTNode
{
  private String image;
  private CompoundStatementNode body;
  private Vector variableDecls;

  public void setImage(String image) { this.image = image; }
  public String getImage() { return image; }

  public void setBody(CompoundStatementNode body) { this.body = body; children.add(body); }
  public CompoundStatementNode getBody() { return body; }

  public void setVariableDecls(Vector variableDecls) { this.variableDecls = variableDecls; children.add(variableDecls); }
  public Vector getVariableDecls() { return variableDecls; }
}
