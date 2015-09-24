package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class ProcedureInvocationNode extends StatementNode implements Visitable
{
  private InvocationNode invocation;

  public ProcedureInvocationNode(InvocationNode invocation) {
    this.setInvocation(invocation);
  }

  public void setInvocation(InvocationNode invocation) { this.invocation = invocation; children.add(invocation); }
  public InvocationNode getInvocation() { return invocation; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
