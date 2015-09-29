package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class WriteStatementNode extends StatementNode implements Visitable
{
  private ASTNode writeExpr;

  public WriteStatementNode(ASTNode writeExpr) {
    children = new ASTVectorNode<ASTNode>();
    this.setWriteExpr(writeExpr);
  }

  @SuppressWarnings("unchecked")
  public void setWriteExpr(ASTNode writeExpr) { this.writeExpr = writeExpr; children.add(writeExpr); }
  public ASTNode getWriteExpr() { return writeExpr; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
