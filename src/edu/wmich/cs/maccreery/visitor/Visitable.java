package edu.wmich.cs.maccreery.visitor;

public interface Visitable
{
  public <T> T accept(Visitor<T> v);
}
