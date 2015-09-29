package edu.wmich.cs.maccreery.visitor;

import edu.wmich.cs.maccreery.ast.*;

public interface Visitor<T>
{
  // SubProgramDeclNodes - - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public T visit(ProcedureDeclNode procedureDeclNode);
  public T visit(FunctionDeclNode functionDeclNode);
  public T visit(ProgramNode programNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  // DimensionNodes  - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public T visit(CharacterDimensionNode characterDimensionNode);
  public T visit(IntegerDimensionNode TegerDimensionNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  // TypeNodes - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public T visit(StandardTypeNode standardTypeNode);
  public T visit(ArrayTypeNode arrayTypeNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public T visit(StringNode stringNode);

  // StatementNodes  - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public T visit(CaseStatementNode caseStatementNode);
  public T visit(ProcedureInvocationNode procedureInvocationNode);
  public T visit(WhileStatementNode whileStatementNode);
  public T visit(CompoundStatementNode compoundStatementNode);
  public T visit(ReturnStatementNode returnStatementNode);
  public T visit(ReadStatementNode readStatementNode);
  public T visit(WriteStatementNode writeStatementNode);
  public T visit(IfStatementNode ifStatementNode);
  public T visit(AssignmentStatementNode assignmentStatementNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  // ExpressionNodes - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/
  // VariableReferenceNodes  - - - - - - - - - - - - - - - - - - - - - - - - -/
  public T visit(ScalarReferenceNode scalarReferenceNode);
  public T visit(ArrayReferenceNode arrayReferenceNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public T visit(NotExpressionNode notExpressionNode);
  public T visit(ParenthesisNode parenthesisNode);

  // BinaryExpressionNodes - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public T visit(SubtractExpressionNode subtractExpressionNode);
  public T visit(AddExpressionNode addExpressionNode);
  public T visit(ModExpressionNode modExpressionNode);

  // BinaryComparisonNodes - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public T visit(GreaterThanExpressionNode greaterThanExpressionNode);
  public T visit(NotEqualExpressionNode notEqualExpressionNode);
  public T visit(LessEqualExpressionNode lessEqualExpressionNode);
  public T visit(LessThanExpressionNode lessThanExpressionNode);
  public T visit(EqualExpressionNode equalExpressionNode);
  public T visit(GreaterEqualExpressionNode greaterEqualExpressionNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public T visit(AndExpressionNode andExpressionNode);
  public T visit(MultiplyExpressionNode multiplyExpressionNode);
  public T visit(OrExpressionNode orExpressionNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public T visit(FunctionInvocationNode functionInvocationNode);
  public T visit(InvocationNode invocationNode);

  // ConstantNodes - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/
  public T visit(FloatConstNode floatConstNode);
  public T visit(IntegerConstNode TegerConstNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public T visit(CharacterNode characterNode);
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -/

  public T visit(VariableDeclarationNode variableDeclarationNode);

  public T visit(CaseElementNode caseElementNode);
}
