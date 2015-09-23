package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class VariableDeclarationNode extends ASTNode implements Visitable
{
  private ASTVectorNode<ASTNode> idList;
  private TypeNode idType;

  public VariableDeclarationNode(ASTVectorNode<ASTNode> idList, TypeNode idType) {
    this.setVariableList(idList);
    this.setType(idType);
  }

  public void setVariableList(ASTVectorNode<ASTNode> idList) { this.idList = idList; }
  public ASTVectorNode<ASTNode> getVariableList() { return idList; }

  public void setType(TypeNode idType) { this.idType = idType; children.add(idType); }
  public TypeNode getType() { return idType; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
