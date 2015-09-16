package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class ProgramNode extends SubProgramDeclNode implements Visitable
{
  private Vector subProgDecls;

  public ProgramNode(String image, Vector variableDecls, Vector subProgDecls, CompoundStatementNode body) {
    this.setImage(image);
    this.setVariableDecls(variableDecls);
    this.setSubProgDecls(subProgDecls);
    this.setBody(body);
  }

  public void setSubProgDecls(Vector subProgDecls) { this.subProgDecls = subProgDecls; children.add(subProgDecls); }
  public Vector getSubProgDecls() { return subProgDecls; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
