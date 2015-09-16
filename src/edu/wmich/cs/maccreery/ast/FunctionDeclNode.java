package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class FunctionDeclNode extends SubProgramDeclNode implements Visitable
{
  private Vector paramList;
  private StandardTypeNode returnType;

  public FunctionDeclNode(String image, Vector paramList, StandardTypeNode returnType) {
    this.setImage(image);
    this.setParamList(paramList);
    this.setReturnType(returnType);
  }

  public void setParamList(Vector paramList) { this.paramList = paramList; children.add(paramList); }
  public Vector getParamList() { return paramList; }

  public void setReturnType(StandardTypeNode returnType) { this.returnType = returnType; children.add(returnType); }
  public StandardTypeNode getReturnType() { return returnType; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
