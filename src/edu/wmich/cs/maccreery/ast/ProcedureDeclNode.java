package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.*;

import java.util.Vector;

public class ProcedureDeclNode extends SubProgramDeclNode implements Visitable
{
  private Vector paramList;

  public ProcedureDeclNode(String image, Vector paramList) {
    children = new Vector();
    this.setImage(image);
    this.setParamList(paramList);
  }

  public void setParamList(Vector paramList) { this.paramList = paramList; children.add(paramList); }
  public Vector getParamList() { return paramList; }

  @Override
  public void addToSymTable(SymbolTable symTable) {
    SymbolTableEntry entry = symTable.add(this.getImage(), new StandardTypeNode(TypeTable.getTypeVal(toString())));

    if (entry == null) {
      System.err.println("Line "+this.getLineNumber()+": Redeclaration of variable: "+this.getImage());
      AbstractSyntaxTree.setError();
      this.setRealType(TypeTable.NO_TYPE);
    }
    else {
      int typeVal = TypeTable.getTypeVal(toString());
      entry.setDataType(typeVal);
      this.setRealType(typeVal);
    }
  }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
