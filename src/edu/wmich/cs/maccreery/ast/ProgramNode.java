package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.SymbolTable;
import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class ProgramNode extends SubProgramDeclNode implements Visitable
{
  private Vector subProgDecls;

  public ProgramNode(String image, Vector variableDecls, Vector subProgDecls, CompoundStatementNode body) {
    children = new Vector();
    this.setImage(image);
    this.setVariableDecls(variableDecls);
    this.setSubProgDecls(subProgDecls);
    this.setBody(body);
  }

  public void setSubProgDecls(Vector subProgDecls) { this.subProgDecls = subProgDecls; children.add(subProgDecls); }
  public Vector getSubProgDecls() { return subProgDecls; }

  @Override
  public void addToSymTable(SymbolTable symTable) {}

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
