package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class FunctionInvocationNode extends ExpressionNode implements Visitable
{
  private InvocationNode invocation;

  public FunctionInvocationNode(InvocationNode invocation) {
    children = new ASTVectorNode<ASTNode>();
    this.setInvocation(invocation);
  }

  @SuppressWarnings("unchecked")
  public void setInvocation(InvocationNode invocation) { this.invocation = invocation; children.add(invocation); }
  public InvocationNode getInvocation() { return invocation; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
