package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.TypeTable;
import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class StandardTypeNode extends TypeNode implements Visitable
{
  private int basicType;

  public StandardTypeNode(int basicType) {
    this.setBasicType(basicType);
  }

  public void setBasicType(int basicType) { this.basicType = basicType; }
  public int getBasicType() { return basicType; }

  @Override
  public int getSize() { return TypeTable.getDataSize(basicType); }

  @Override
  public String toString() { return TypeTable.getBasicTypeString(basicType); }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
