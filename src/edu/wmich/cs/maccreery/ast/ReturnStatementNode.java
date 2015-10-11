package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class ReturnStatementNode extends StatementNode implements Visitable
{
  private ExpressionNode returnExpr;

  public ReturnStatementNode(ExpressionNode returnExpr) {
    children = new Vector();
    this.setReturnExpr(returnExpr);
  }

  @SuppressWarnings("unchecked")
  public void setReturnExpr(ExpressionNode returnExpr) { this.returnExpr = returnExpr; children.add(returnExpr); }
  public ExpressionNode getReturnExpr() { return returnExpr; }

  public SubProgramDeclNode getContainingSubProgram() {
    ASTNode node;
    for (node = this;
         node != null && !(node instanceof SubProgramDeclNode);
         node = node.getParent());

    return (SubProgramDeclNode) node;
  }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
