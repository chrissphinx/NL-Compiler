package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class WhileStatementNode extends StatementNode implements Visitable
{
  private ExpressionNode whileExpr;
  private StatementNode controlledStmt;

  public WhileStatementNode(ExpressionNode whileExpr, StatementNode controlledStmt) {
    children = new ASTVectorNode<ASTNode>();
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
  public void accept(Visitor v) { v.visit(this); }
}
