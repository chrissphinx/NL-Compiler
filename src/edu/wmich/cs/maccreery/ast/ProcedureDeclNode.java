package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class ProcedureDeclNode extends SubProgramDeclNode implements Visitable
{
  private ASTVectorNode<ASTNode> paramList;

  public ProcedureDeclNode(String image, ASTVectorNode<ASTNode> paramList) {
    this.setImage(image);
    this.setParamList(paramList);
  }

  public void setParamList(ASTVectorNode<ASTNode> paramList) { this.paramList = paramList; children.add(paramList); }
  public ASTVectorNode<ASTNode> getParamList() { return paramList; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
