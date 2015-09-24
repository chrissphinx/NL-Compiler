package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class ReturnStatementNode extends StatementNode implements Visitable
{
  private ExpressionNode returnExpr;

  public ReturnStatementNode(ExpressionNode returnExpr) {
    this.setReturnExpr(returnExpr);
  }

  @SuppressWarnings("unchecked")
  public void setReturnExpr(ExpressionNode returnExpr) { this.returnExpr = returnExpr; children.add(returnExpr); }
  public ExpressionNode getReturnExpr() { return returnExpr; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
