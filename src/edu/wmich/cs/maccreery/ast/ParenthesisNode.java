package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class ParenthesisNode extends ExpressionNode implements Visitable
{
  private ExpressionNode exprNode;

  public ParenthesisNode(ExpressionNode exprNode) {
    children = new ASTVectorNode<ASTNode>();
    this.setExprNode(exprNode);
  }

  @SuppressWarnings("unchecked")
  public void setExprNode(ExpressionNode exprNode) { this.exprNode = exprNode; children.add(exprNode); }
  public ExpressionNode getExprNode() { return exprNode; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
