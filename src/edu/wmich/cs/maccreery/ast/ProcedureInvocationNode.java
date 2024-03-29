package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class ProcedureInvocationNode extends StatementNode implements Visitable
{
  private InvocationNode invocation;

  public ProcedureInvocationNode(InvocationNode invocation) {
    children = new Vector();
    this.setInvocation(invocation);
  }

  public void setInvocation(InvocationNode invocation) { this.invocation = invocation; children.add(invocation); }
  public InvocationNode getInvocation() { return invocation; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
