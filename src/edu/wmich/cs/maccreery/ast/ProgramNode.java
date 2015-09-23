package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class ProgramNode extends SubProgramDeclNode implements Visitable
{
  private ASTVectorNode<ASTNode> subProgDecls;

  public ProgramNode(String image, ASTVectorNode<ASTNode> variableDecls, ASTVectorNode<ASTNode> subProgDecls, CompoundStatementNode body) {
    this.setImage(image);
    this.setVariableDecls(variableDecls);
    this.setSubProgDecls(subProgDecls);
    this.setBody(body);
  }

  public void setSubProgDecls(ASTVectorNode<ASTNode> subProgDecls) { this.subProgDecls = subProgDecls; children.add(subProgDecls); }
  public ASTVectorNode<ASTNode> getSubProgDecls() { return subProgDecls; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
