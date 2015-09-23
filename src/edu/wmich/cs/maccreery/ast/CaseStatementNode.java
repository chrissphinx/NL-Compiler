package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class CaseStatementNode extends StatementNode implements Visitable
{
  private ASTVectorNode<ASTNode> caseList;
  private ExpressionNode caseExpr;

  public CaseStatementNode(ExpressionNode caseExpr, ASTVectorNode<ASTNode> caseList) {
    this.setCaseList(caseList);
    this.setCaseExpr(caseExpr);
  }

  public void setCaseList(ASTVectorNode<ASTNode> caseList) { this.caseList = caseList; children.add(caseList); }
  public ASTVectorNode<ASTNode> getCaseList() { return caseList; }

  public void setCaseExpr(ExpressionNode caseExpr) { this.caseExpr = caseExpr; children.add(caseExpr); }
  public ExpressionNode getCaseExpr() { return caseExpr; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
