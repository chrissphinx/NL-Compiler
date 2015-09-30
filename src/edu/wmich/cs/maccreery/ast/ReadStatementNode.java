package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class ReadStatementNode extends StatementNode implements Visitable
{
  private VariableReferenceNode variable;

  public ReadStatementNode(VariableReferenceNode variable) {
    children = new Vector();
    this.setVariable(variable);
  }

  @SuppressWarnings("unchecked")
  public void setVariable(VariableReferenceNode variable) { this.variable = variable; children.add(variable); }
  public VariableReferenceNode getVariable() { return variable; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
