package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class CharacterDimensionNode extends DimensionNode implements Visitable
{
  private char lowerBound;
  private char upperBound;

  public CharacterDimensionNode(char lowerBound, char upperBound) {
    this.setLowerBound(lowerBound);
    this.setUpperBound(upperBound);
  }

  public void setLowerBound(char lowerBound) { this.lowerBound = lowerBound; }
  public char getLowerBound() { return lowerBound; }

  public void setUpperBound(char upperBound) { this.upperBound = upperBound; }
  public char getUpperBound() { return upperBound; }

  @Override
  public int getRangeLength() { return ((int) lowerBound - (int) upperBound) + 1; }

  @Override
  public String toString() { return "%char%" + lowerBound + ".." + upperBound; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
