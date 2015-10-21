package edu.wmich.cs.maccreery.visitor;

public class SymbolTableEntry
{

  private String name;
  private int dataType = TypeTable.ANY_TYPE;
  private int nestingLevel;
  private int offset;
  private int register;

  public SymbolTableEntry(String name) { this.name = name; }

  public void setDataType(int dataType) {
    this.dataType = dataType;
  }

  public void setNestingLevel(int level) {
    this.nestingLevel = level;
  }

  public void setOffset(int offset) { this.offset = offset; }

  public void setRegister(int register) { this.register = register; }

  public String getName() {
    return name;
  }

  public int getDataType() {
    return dataType;
  }

  public int getNestingLevel() { return nestingLevel; }

  public int getOffset() { return this.offset; }

  public int getRegister() { return this.register; }
}
