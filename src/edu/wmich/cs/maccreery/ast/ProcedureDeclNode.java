package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class ProcedureDeclNode extends SubProgramDeclNode implements Visitable
{
  private Vector paramList;

  public ProcedureDeclNode(String image, Vector paramList) {
    this.setImage(image);
    this.setParamList(paramList);
  }

  public void setParamList(Vector paramList) { this.paramList = paramList; children.add(paramList); }
  public Vector getParamList() { return paramList; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
