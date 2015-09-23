package edu.wmich.cs.maccreery.visitor;

import edu.wmich.cs.maccreery.ast.*;

public interface Visitor
{
  // SubProgramDeclNodes - - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public void visit(ProcedureDeclNode procedureDeclNode);
  public void visit(FunctionDeclNode functionDeclNode);
  public void visit(ProgramNode programNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  // DimensionNodes  - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public void visit(CharacterDimensionNode characterDimensionNode);
  public void visit(IntegerDimensionNode integerDimensionNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  // TypeNodes - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public void visit(StandardTypeNode standardTypeNode);
  public void visit(ArrayTypeNode arrayTypeNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public void visit(StringNode stringNode);

  // StatementNodes  - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public void visit(CaseStatementNode caseStatementNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public void visit(VariableDeclarationNode variableDeclarationNode);

  public void visit(CaseElementNode caseElementNode);
}
