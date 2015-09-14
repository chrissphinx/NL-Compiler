package edu.wmich.cs.maccreery.ast;

import java.util.Vector;

public class ProcedureDeclNode extends SubProgramDeclNode
{
  private Vector paramList;

  public ProcedureDeclNode(String image, Vector paramList) {
    this.setImage(image);
    this.setParamList(paramList);
  }

  public void setParamList(Vector paramList) { this.paramList = paramList; }
  public Vector getParamList() { return paramList; }
}
