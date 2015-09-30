package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.*;

import java.util.Vector;

public class FunctionDeclNode extends SubProgramDeclNode implements Visitable
{
  private Vector paramList;
  private StandardTypeNode returnType;

  public FunctionDeclNode(String image, Vector paramList, StandardTypeNode returnType) {
    children = new Vector();
    this.setImage(image);
    this.setParamList(paramList);
    this.setReturnType(returnType);
  }

  public void setParamList(Vector paramList) { this.paramList = paramList; children.add(paramList); }
  public Vector getParamList() { return paramList; }

  public void setReturnType(StandardTypeNode returnType) { this.returnType = returnType; children.add(returnType); }
  public StandardTypeNode getReturnType() { return returnType; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }

  @Override
  public void addToSymTable(SymbolTable symTable) {
    SymbolTableEntry entry = symTable.add(this.getImage(), new StandardTypeNode(TypeTable.getTypeVal(toString())));

    if (entry == null) {
      System.err.println("Line "+this.getLineNumber()+": Redeclaration of variable: "+this.getImage());
      AbstractSyntaxTree.setError();
      this.setRealType(TypeTable.ANY_TYPE);
    }
    else {
      int typeVal = TypeTable.getTypeVal(toString());
      entry.setDataType(typeVal);
      this.setRealType(typeVal);
    }
  }
}
