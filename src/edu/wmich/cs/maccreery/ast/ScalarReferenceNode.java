package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class ScalarReferenceNode extends VariableReferenceNode implements Visitable
{
  public ScalarReferenceNode(String image) {
    this.setImage(image);
  }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
