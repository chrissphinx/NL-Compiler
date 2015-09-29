package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class ReturnStatementNode extends StatementNode implements Visitable
{
  private ExpressionNode returnExpr;

  public ReturnStatementNode(ExpressionNode returnExpr) {
    children = new ASTVectorNode<ASTNode>();
    this.setReturnExpr(returnExpr);
  }

  @SuppressWarnings("unchecked")
  public void setReturnExpr(ExpressionNode returnExpr) { this.returnExpr = returnExpr; children.add(returnExpr); }
  public ExpressionNode getReturnExpr() { return returnExpr; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
