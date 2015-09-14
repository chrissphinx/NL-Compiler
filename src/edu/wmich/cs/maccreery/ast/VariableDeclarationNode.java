package edu.wmich.cs.maccreery.ast;

import java.util.Vector;

public class VariableDeclarationNode extends ASTNode
{
  private Vector idList;
  private TypeNode idType;

  public VariableDeclarationNode(Vector idList, TypeNode idType) {
    this.idList = idList;
    this.idType = idType;
    this.children.add(idType);
  }

  public Vector getVariableList() { return idList; }

  public TypeNode getType() { return idType; }
}
