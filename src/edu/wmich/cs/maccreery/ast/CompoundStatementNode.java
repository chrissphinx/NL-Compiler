package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class CompoundStatementNode extends StatementNode implements Visitable
{
  private ASTVectorNode<ASTNode> stmtList;

  public CompoundStatementNode(ASTVectorNode<ASTNode> stmtList) {
    children = new ASTVectorNode<ASTNode>();
    this.setStmtList(stmtList);
  }

  @SuppressWarnings("unchecked")
  public void setStmtList(ASTVectorNode<ASTNode> stmtList) { this.stmtList = stmtList; children.add(stmtList); }
  public ASTVectorNode<ASTNode> getStmtList() { return stmtList; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
