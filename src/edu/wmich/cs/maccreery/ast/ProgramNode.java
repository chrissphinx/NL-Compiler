package edu.wmich.cs.maccreery.ast;

import java.util.Vector;

public class ProgramNode extends SubProgramDeclNode
{
  private Vector subProgDecls;

  public ProgramNode(String image, Vector variableDecls, Vector subProgDecls, CompoundStatementNode body) {
    this.setImage(image);
    this.setVariableDecls(variableDecls);
    this.setSubProgDecls(subProgDecls);
    this.setBody(body);
  }

  public void setSubProgDecls(Vector subProgDecls) { this.subProgDecls = subProgDecls; }
  public Vector getSubProgDecls() { return subProgDecls; }
}
