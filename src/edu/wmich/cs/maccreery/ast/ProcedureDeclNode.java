package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.*;

public class ProcedureDeclNode extends SubProgramDeclNode implements Visitable
{
  private ASTVectorNode<ASTNode> paramList;

  public ProcedureDeclNode(String image, ASTVectorNode<ASTNode> paramList) {
    children = new ASTVectorNode<ASTNode>();
    this.setImage(image);
    this.setParamList(paramList);
  }

  public void setParamList(ASTVectorNode<ASTNode> paramList) { this.paramList = paramList; children.add(paramList); }
  public ASTVectorNode<ASTNode> getParamList() { return paramList; }

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
