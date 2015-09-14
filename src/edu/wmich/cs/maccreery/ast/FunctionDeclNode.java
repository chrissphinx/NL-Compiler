package edu.wmich.cs.maccreery.ast;

import java.util.Vector;

public class FunctionDeclNode extends SubProgramDeclNode
{
  private Vector paramList;
  private StandardTypeNode returnType;

  public FunctionDeclNode(String image, Vector paramList, StandardTypeNode returnType) {
    this.setImage(image);
    this.setParamList(paramList);
    this.setReturnType(returnType);
  }

  public void setParamList(Vector paramList) { this.paramList = paramList; }
  public Vector getParamList() { return paramList; }

  public void setReturnType(StandardTypeNode returnType) { this.returnType = returnType; }
  public StandardTypeNode getReturnType() { return returnType; }
}
