package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class IfStatementNode extends StatementNode implements Visitable
{
  private ExpressionNode testExpr;
  private StatementNode thenStmt;
  private StatementNode elseStmt;

  public IfStatementNode(ExpressionNode testExpr, StatementNode thenStmt, StatementNode elseStmt) {
    children = new ASTVectorNode<ASTNode>();
    this.setTestExpr(testExpr);
    this.setThenStmt(thenStmt);
    this.setElseStmt(elseStmt);
  }

  @SuppressWarnings("unchecked")
  public void setTestExpr(ExpressionNode testExpr) { this.testExpr = testExpr; children.add(testExpr); }
  public ExpressionNode getTestExpr() { return testExpr; }

  @SuppressWarnings("unchecked")
  public void setThenStmt(StatementNode thenStmt) { this.thenStmt = thenStmt; children.add(thenStmt); }
  public StatementNode getThenStmt() { return thenStmt; }

  @SuppressWarnings("unchecked")
  public void setElseStmt(StatementNode elseStmt) { this.elseStmt = elseStmt; children.add(elseStmt); }
  public StatementNode getElseStmt() { return elseStmt; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
