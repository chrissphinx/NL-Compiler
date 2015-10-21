package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.SymbolTable;

import java.util.Vector;

public abstract class SubProgramDeclNode extends ASTNode
{
  private String image;
  private CompoundStatementNode body;
  private Vector variableDecls;
  private int frameSize = 0;

  public void setImage(String image) { this.image = image; }
  public String getImage() { return image; }

  public void setBody(CompoundStatementNode body) { this.body = body; children.add(body); }
  public CompoundStatementNode getBody() { return body; }

  public void setVariableDecls(Vector variableDecls) { this.variableDecls = variableDecls; children.add(variableDecls); }
  public Vector getVariableDecls() { return variableDecls; }

  public int getFrameSize() { return frameSize; }
  public void addFrameSize(int additional) { this.frameSize += additional; }

  public abstract void addToSymTable(SymbolTable symTable);
}
