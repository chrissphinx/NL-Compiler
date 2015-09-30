package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class VariableDeclarationNode extends ASTNode implements Visitable
{
  private Vector idList;
  private TypeNode idType;

  public VariableDeclarationNode(Vector idList, TypeNode idType) {
    children = new Vector();
    this.setVariableList(idList);
    this.setType(idType);
  }

  public void setVariableList(Vector idList) { this.idList = idList; }
  public Vector getVariableList() { return idList; }

  public void setType(TypeNode idType) { this.idType = idType; children.add(idType); }
  public TypeNode getType() { return idType; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
