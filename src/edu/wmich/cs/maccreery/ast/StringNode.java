package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitor;

public class StringNode extends ASTNode
{
  private String string;

  public StringNode(String string) {
    this.setString(string);
  }

  public void setString(String string) { this.string = string; }
  public String getString() { return string; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
