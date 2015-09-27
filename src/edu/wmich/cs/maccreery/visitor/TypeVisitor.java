package edu.wmich.cs.maccreery.visitor;

import edu.wmich.cs.maccreery.ast.*;

public class TypeVisitor implements Visitor
{
  private SymbolTable symbolTable;

  public TypeVisitor(AbstractSyntaxTree ast) {
    symbolTable = new SymbolTable();
    TypeTable.initTypeTable(100);
  }

  @Override
  public void visit(ProcedureDeclNode procedureDeclNode) {

  }

  @Override
  public void visit(FunctionDeclNode functionDeclNode) {

  }

  @Override
  public void visit(ProgramNode programNode) {

  }

  @Override
  public void visit(CharacterDimensionNode characterDimensionNode) {

  }

  @Override
  public void visit(IntegerDimensionNode integerDimensionNode) {

  }

  @Override
  public void visit(StandardTypeNode standardTypeNode) {

  }

  @Override
  public void visit(ArrayTypeNode arrayTypeNode) {

  }

  @Override
  public void visit(StringNode stringNode) {

  }

  @Override
  public void visit(CaseStatementNode caseStatementNode) {

  }

  @Override
  public void visit(ProcedureInvocationNode procedureInvocationNode) {

  }

  @Override
  public void visit(WhileStatementNode whileStatementNode) {

  }

  @Override
  public void visit(CompoundStatementNode compoundStatementNode) {

  }

  @Override
  public void visit(ReturnStatementNode returnStatementNode) {

  }

  @Override
  public void visit(ReadStatementNode readStatementNode) {

  }

  @Override
  public void visit(WriteStatementNode writeStatementNode) {

  }

  @Override
  public void visit(IfStatementNode ifStatementNode) {

  }

  @Override
  public void visit(AssignmentStatementNode assignmentStatementNode) {

  }

  @Override
  public void visit(ScalarReferenceNode scalarReferenceNode) {

  }

  @Override
  public void visit(ArrayReferenceNode arrayReferenceNode) {

  }

  @Override
  public void visit(NotExpressionNode notExpressionNode) {

  }

  @Override
  public void visit(ParenthesisNode parenthesisNode) {

  }

  @Override
  public void visit(SubtractExpressionNode subtractExpressionNode) {

  }

  @Override
  public void visit(AddExpressionNode addExpressionNode) {

  }

  @Override
  public void visit(ModExpressionNode modExpressionNode) {

  }

  @Override
  public void visit(GreaterThanExpressionNode greaterThanExpressionNode) {

  }

  @Override
  public void visit(NotEqualExpressionNode notEqualExpressionNode) {

  }

  @Override
  public void visit(LessEqualExpressionNode lessEqualExpressionNode) {

  }

  @Override
  public void visit(LessThanExpressionNode lessThanExpressionNode) {

  }

  @Override
  public void visit(EqualExpressionNode equalExpressionNode) {

  }

  @Override
  public void visit(GreaterEqualExpressionNode greaterEqualExpressionNode) {

  }

  @Override
  public void visit(AndExpressionNode andExpressionNode) {

  }

  @Override
  public void visit(MultiplyExpressionNode multiplyExpressionNode) {

  }

  @Override
  public void visit(OrExpressionNode orExpressionNode) {

  }

  @Override
  public void visit(FunctionInvocationNode functionInvocationNode) {

  }

  @Override
  public void visit(InvocationNode invocationNode) {

  }

  @Override
  public void visit(FloatConstNode floatConstNode) {

  }

  @Override
  public void visit(IntegerConstNode integerConstNode) {

  }

  @Override
  public void visit(CharacterNode characterNode) {

  }

  @Override
  public void visit(VariableDeclarationNode variableDeclarationNode) {

  }

  @Override
  public void visit(CaseElementNode caseElementNode) {

  }
}
