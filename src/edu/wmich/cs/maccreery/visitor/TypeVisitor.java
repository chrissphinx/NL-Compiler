package edu.wmich.cs.maccreery.visitor;

import edu.wmich.cs.maccreery.ast.*;

import java.util.Vector;

public class TypeVisitor implements Visitor<Integer>
{
  private SymbolTable symTable;

  public TypeVisitor() {
    symTable = new SymbolTable();
    TypeTable.initTypeTable(100);
  }

  @Override
  public Integer visit(ProcedureDeclNode procedureDeclNode) {
    return null;
  }

  @Override
  public Integer visit(FunctionDeclNode functionDeclNode) {
    return null;
  }

  @Override
  public Integer visit(ProgramNode programNode) {
    symTable.beginScope();

    Vector variableDecls = programNode.getVariableDecls();

    for (int i = 0; i < variableDecls.size(); i++)
      ((ASTNode) variableDecls.elementAt(i)).accept(this);

    Vector subProgDecls = programNode.getSubProgDecls();

    for (int i = 0; i < subProgDecls.size(); i++) {
      SubProgramDeclNode subProg = (SubProgramDeclNode)subProgDecls.elementAt(i);
      subProg.addToSymTable(symTable);
    }

    for (int i = 0; i < subProgDecls.size(); i++) {
      ((ASTNode) subProgDecls.elementAt(i)).accept(this);
    }

    programNode.getBody().accept(this);

    if (programNode.containsReturnStatement(programNode.getBody())) {
      System.err.println("Line "+programNode.getLineNumber()+": main body contains return statement");
    }

    symTable.endScope(programNode.getImage());

    programNode.setRealType(TypeTable.NO_TYPE);

    return TypeTable.NO_TYPE;
  }

  @Override
  public Integer visit(CharacterDimensionNode characterDimensionNode) {
    return null;
  }

  @Override
  public Integer visit(IntegerDimensionNode integerDimensionNode) {
    return null;
  }

  @Override
  public Integer visit(StandardTypeNode standardTypeNode) {
    return null;
  }

  @Override
  public Integer visit(ArrayTypeNode arrayTypeNode) {
    return null;
  }

  @Override
  public Integer visit(StringNode stringNode) {
    return null;
  }

  @Override
  public Integer visit(CaseStatementNode caseStatementNode) {
    return null;
  }

  @Override
  public Integer visit(ProcedureInvocationNode procedureInvocationNode) {
    return null;
  }

  @Override
  public Integer visit(WhileStatementNode whileStatementNode) {
    return null;
  }

  @Override
  public Integer visit(CompoundStatementNode compoundStatementNode) {
    return null;
  }

  @Override
  public Integer visit(ReturnStatementNode returnStatementNode) {
    return null;
  }

  @Override
  public Integer visit(ReadStatementNode readStatementNode) {
    return null;
  }

  @Override
  public Integer visit(WriteStatementNode writeStatementNode) {
    return null;
  }

  @Override
  public Integer visit(IfStatementNode ifStatementNode) {
    return null;
  }

  @Override
  public Integer visit(AssignmentStatementNode assignmentStatementNode) {
    return null;
  }

  @Override
  public Integer visit(ScalarReferenceNode scalarReferenceNode) {
    return null;
  }

  @Override
  public Integer visit(ArrayReferenceNode arrayReferenceNode) {
    return null;
  }

  @Override
  public Integer visit(NotExpressionNode notExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(ParenthesisNode parenthesisNode) {
    return null;
  }

  @Override
  public Integer visit(SubtractExpressionNode subtractExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(AddExpressionNode addExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(ModExpressionNode modExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(GreaterThanExpressionNode greaterThanExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(NotEqualExpressionNode notEqualExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(LessEqualExpressionNode lessEqualExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(LessThanExpressionNode lessThanExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(EqualExpressionNode equalExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(GreaterEqualExpressionNode greaterEqualExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(AndExpressionNode andExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(MultiplyExpressionNode multiplyExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(OrExpressionNode orExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(FunctionInvocationNode functionInvocationNode) {
    return null;
  }

  @Override
  public Integer visit(InvocationNode invocationNode) {
    return null;
  }

  @Override
  public Integer visit(FloatConstNode floatConstNode) {
    return null;
  }

  @Override
  public Integer visit(IntegerConstNode integerConstNode) {
    return null;
  }

  @Override
  public Integer visit(CharacterNode characterNode) {
    return null;
  }

  @Override
  public Integer visit(VariableDeclarationNode variableDeclarationNode) {

    TypeNode idType = variableDeclarationNode.getType();

    int typeVal = TypeTable.getTypeVal(idType.toString());

    Vector idList = variableDeclarationNode.getVariableList();

    for (int i = 0; i < idList.size(); i++) {
      String id = (String) idList.elementAt(i);

      SymbolTableEntry entry;
      if ((entry = symTable.add(id, idType)) == null) {
        System.err.println("Line "+variableDeclarationNode.getLineNumber()+": Duplicate variable declaration: "+id);
        AbstractSyntaxTree.setError();
      }
      else {
        entry.setDataType(typeVal);
        entry.setNestingLevel(symTable.getCurrentLevel());
        idType.setRealType(typeVal);
      }
    }

    return TypeTable.NO_TYPE;
  }

  @Override
  public Integer visit(CaseElementNode caseElementNode) {
    return null;
  }
}
