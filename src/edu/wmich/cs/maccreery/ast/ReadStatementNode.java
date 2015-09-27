package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class ReadStatementNode extends StatementNode implements Visitable
{
  private VariableReferenceNode variable;

  public ReadStatementNode(VariableReferenceNode variable) {
    children = new ASTVectorNode<ASTNode>();
    this.setVariable(variable);
  }

  @SuppressWarnings("unchecked")
  public void setVariable(VariableReferenceNode variable) { this.variable = variable; children.add(variable); }
  public VariableReferenceNode getVariable() { return variable; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
