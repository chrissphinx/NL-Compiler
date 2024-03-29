package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class WhileStatementNode extends StatementNode implements Visitable
{
  private ExpressionNode whileExpr;
  private StatementNode controlledStmt;

  public WhileStatementNode(ExpressionNode whileExpr, StatementNode controlledStmt) {
    children = new Vector();
    this.setWhileExpr(whileExpr);
    this.setControlledStmt(controlledStmt);
  }

  @SuppressWarnings("unchecked")
  public void setWhileExpr(ExpressionNode whileExpr) { this.whileExpr = whileExpr; children.add(whileExpr); }
  public ExpressionNode getWhileExpr() { return whileExpr; }

  @SuppressWarnings("unchecked")
  public void setControlledStmt(StatementNode controlledStmt) { this.controlledStmt = controlledStmt; children.add(controlledStmt); }
  public StatementNode getControlledStmt() { return controlledStmt; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
