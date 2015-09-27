package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class FunctionDeclNode extends SubProgramDeclNode implements Visitable
{
  private ASTVectorNode<ASTNode> paramList;
  private StandardTypeNode returnType;

  public FunctionDeclNode(String image, ASTVectorNode<ASTNode> paramList, StandardTypeNode returnType) {
    children = new ASTVectorNode<ASTNode>();
    this.setImage(image);
    this.setParamList(paramList);
    this.setReturnType(returnType);
  }

  public void setParamList(ASTVectorNode<ASTNode> paramList) { this.paramList = paramList; children.add(paramList); }
  public ASTVectorNode<ASTNode> getParamList() { return paramList; }

  public void setReturnType(StandardTypeNode returnType) { this.returnType = returnType; children.add(returnType); }
  public StandardTypeNode getReturnType() { return returnType; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
