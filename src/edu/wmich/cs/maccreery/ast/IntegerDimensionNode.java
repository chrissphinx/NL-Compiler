package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class IntegerDimensionNode extends DimensionNode implements Visitable
{
  private int lowerBound;
  private int upperBound;

  public IntegerDimensionNode(int lowerBound, int upperBound) {
    this.setLowerBound(lowerBound);
    this.setUpperBound(upperBound);
  }

  public void setLowerBound(int lowerBound) { this.lowerBound = lowerBound; }
  public int getLowerBound() { return lowerBound; }

  public void setUpperBound(int upperBound) { this.upperBound = upperBound; }
  public int getUpperBound() { return upperBound; }

  @Override
  public int getRangeLength() { return upperBound - lowerBound + 1; }

  @Override
  public String toString() { return "%int%" + lowerBound + ".." + upperBound; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
