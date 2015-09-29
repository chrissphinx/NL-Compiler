package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class ArrayTypeNode extends TypeNode implements Visitable
{
  private StandardTypeNode basicType;
  private DimensionNode dimension;

  public ArrayTypeNode(DimensionNode dimension, StandardTypeNode basicType) {
    children = new ASTVectorNode<ASTNode>();
    this.setBasicType(basicType);
    this.setDimension(dimension);
  }

  public void setBasicType(StandardTypeNode basicType) { this.basicType = basicType; children.add(basicType); }
  public StandardTypeNode getBasicType() { return basicType; }

  public void setDimension(DimensionNode dimension) { this.dimension = dimension; children.add(dimension); }
  public DimensionNode getDimension() { return dimension; }

  @Override
  public int getSize() { return basicType.getSize() * dimension.getRangeLength(); }

  @Override
  public String toString() { return "array::" + dimension.toString() + "$$" + basicType.toString(); }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
