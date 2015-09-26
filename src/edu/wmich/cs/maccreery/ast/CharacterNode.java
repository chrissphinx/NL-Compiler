package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class CharacterNode extends ExpressionNode implements Visitable
{
  private char character;

  public CharacterNode(char character) {
    this.setCharacter(character);
  }

  public void setCharacter(char character) { this.character = character; }
  public char getCharacter() { return character; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
