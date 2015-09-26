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
  public void visit(ProcedureInvocationNode procedureInvocationNode);
  public void visit(WhileStatementNode whileStatementNode);
  public void visit(CompoundStatementNode compoundStatementNode);
  public void visit(ReturnStatementNode returnStatementNode);
  public void visit(ReadStatementNode readStatementNode);
  public void visit(WriteStatementNode writeStatementNode);
  public void visit(IfStatementNode ifStatementNode);
  public void visit(AssignmentStatementNode assignmentStatementNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  // ExpressionNodes - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/
  // VariableReferenceNodes  - - - - - - - - - - - - - - - - - - - - - - - - -/
  public void visit(ScalarReferenceNode scalarReferenceNode);
  public void visit(ArrayReferenceNode arrayReferenceNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public void visit(NotExpressionNode notExpressionNode);
  public void visit(ParenthesisNode parenthesisNode);

  // BinaryExpressionNodes - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public void visit(SubtractExpressionNode subtractExpressionNode);
  public void visit(AddExpressionNode addExpressionNode);
  public void visit(ModExpressionNode modExpressionNode);

  // BinaryComparisonNodes - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public void visit(GreaterThanExpressionNode greaterThanExpressionNode);
  public void visit(NotEqualExpressionNode notEqualExpressionNode);
  public void visit(LessEqualExpressionNode lessEqualExpressionNode);
  public void visit(LessThanExpressionNode lessThanExpressionNode);
  public void visit(EqualExpressionNode equalExpressionNode);
  public void visit(GreaterEqualExpressionNode greaterEqualExpressionNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public void visit(AndExpressionNode andExpressionNode);
  public void visit(MultiplyExpressionNode multiplyExpressionNode);
  public void visit(OrExpressionNode orExpressionNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public void visit(FunctionInvocationNode functionInvocationNode);
  public void visit(InvocationNode invocationNode);

  // ConstantNodes - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public void visit(FloatConstNode floatConstNode);
  public void visit(IntegerConstNode integerConstNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public void visit(CharacterNode characterNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public void visit(VariableDeclarationNode variableDeclarationNode);

  public void visit(CaseElementNode caseElementNode);
}
