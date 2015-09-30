package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class CaseStatementNode extends StatementNode implements Visitable
{
  private Vector caseList;
  private ExpressionNode caseExpr;

  public CaseStatementNode(ExpressionNode caseExpr, Vector caseList) {
    children = new Vector();
    this.setCaseList(caseList);
    this.setCaseExpr(caseExpr);
  }

  public void setCaseList(Vector caseList) { this.caseList = caseList; children.add(caseList); }
  public Vector getCaseList() { return caseList; }

  public void setCaseExpr(ExpressionNode caseExpr) { this.caseExpr = caseExpr; children.add(caseExpr); }
  public ExpressionNode getCaseExpr() { return caseExpr; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
