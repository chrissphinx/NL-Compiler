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
    Vector stmtList = compoundStatementNode.getStmtList();

    for (int i = 0; i < stmtList.size(); i++) {
      ((ASTNode) stmtList.elementAt(i)).accept(this);
    }

    compoundStatementNode.setRealType(TypeTable.NO_TYPE);

    return TypeTable.NO_TYPE;
  }

  @Override
  public Integer visit(ReturnStatementNode returnStatementNode) {
    return null;
  }

  @Override
  public Integer visit(ReadStatementNode readStatementNode) {
    VariableReferenceNode variable = readStatementNode.getVariable();

    variable.setConvertedType(variable.accept(this));

    variable.setRealType(TypeTable.NO_TYPE);

    return TypeTable.NO_TYPE;
  }

  @Override
  public Integer visit(WriteStatementNode writeStatementNode) {
    writeStatementNode.setConvertedType(writeStatementNode.getWriteExpr().accept(this));

    writeStatementNode.setRealType(TypeTable.NO_TYPE);

    return TypeTable.NO_TYPE;
  }

  @Override
  public Integer visit(IfStatementNode ifStatementNode) {
    return null;
  }

  @Override
  public Integer visit(AssignmentStatementNode assignmentStatementNode) {
    VariableReferenceNode lhs = assignmentStatementNode.getLeft();
    ExpressionNode rhs = assignmentStatementNode.getRight();

    int lhsType = lhs.accept(this);
    int rhsType = rhs.accept(this);

    int realType = TypeTable.getResultAssignmentType(lhsType,rhsType);

    if (realType == TypeTable.ERROR_TYPE) {
      System.err.println("Line "+assignmentStatementNode.getLineNumber()+": Type mismatch in assignment statement");
      AbstractSyntaxTree.setError();
      assignmentStatementNode.setConvertedType(TypeTable.ANY_TYPE);
    }
    else
      assignmentStatementNode.setConvertedType(lhsType);

    int convertedType = assignmentStatementNode.getConvertedType();
    rhs.setConvertedType(convertedType);
    lhs.setConvertedType(convertedType);

    assignmentStatementNode.setRealType(TypeTable.NO_TYPE);

    return TypeTable.NO_TYPE;
  }

  @Override
  public Integer visit(ScalarReferenceNode scalarReferenceNode) {
    String name = scalarReferenceNode.getImage();

    SymbolTableEntry entry = symTable.getEntry(name);

    if (entry == null) {
      System.err.println("Line "+scalarReferenceNode.getLineNumber()+": Undeclared variable "+name);
      AbstractSyntaxTree.setError();
      scalarReferenceNode.setRealType(TypeTable.ANY_TYPE);
    }
    else {
      scalarReferenceNode.setRealType(entry.getDataType());
      scalarReferenceNode.setNestingLevel(entry.getNestingLevel());
    }

    // SubProgramDeclNode subProg = getContainingSubProgram();

    //
    // if this is a global variable accessed outside the main procedure
    // then it must be allocated to the stack
    //

    return scalarReferenceNode.getRealType();
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
    ExpressionNode leftOperand = addExpressionNode.getLeftOperand();
    ExpressionNode rightOperand = addExpressionNode.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    addExpressionNode.setRealType(TypeTable.getResultArithmeticType(lhsType, rhsType));

    if (addExpressionNode.getRealType() == TypeTable.ERROR_TYPE) {
      System.err.println("Line " + addExpressionNode.getLineNumber() +
              ": Type mismatch in addition operation");
      AbstractSyntaxTree.setError();
      addExpressionNode.setRealType(TypeTable.ANY_TYPE);
    }
    else if (lhsType != rhsType) {
      if (addExpressionNode.getRealType() == lhsType)
        leftOperand.setConvertedType(lhsType);
      else if (lhsType == TypeTable.INT_TYPE)
        leftOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        leftOperand.setConvertedType(lhsType);

      if (addExpressionNode.getRealType() == rhsType)
        rightOperand.setConvertedType(rhsType);
      else if (rhsType == TypeTable.INT_TYPE)
        rightOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        rightOperand.setConvertedType(rhsType);
    }
    else {
      leftOperand.setConvertedType(addExpressionNode.getRealType());
      rightOperand.setConvertedType(addExpressionNode.getRealType());
    }

    return addExpressionNode.getRealType();
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
    ExpressionNode leftOperand = andExpressionNode.getLeftOperand();
    ExpressionNode rightOperand = andExpressionNode.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    andExpressionNode.setRealType(TypeTable.getResultBooleanType(lhsType, rhsType));

    if (andExpressionNode.getRealType() == TypeTable.ERROR_TYPE) {
      System.err.println("Line " + andExpressionNode.getLineNumber() +
              ": Type mismatch in AND operation");
      AbstractSyntaxTree.setError();
      andExpressionNode.setRealType(TypeTable.ANY_TYPE);
    }
    else if (lhsType != rhsType) {
      if (andExpressionNode.getRealType() == lhsType)
        leftOperand.setConvertedType(lhsType);
      else if (lhsType == TypeTable.FLOAT_TYPE)
        leftOperand.setConvertedType(TypeTable.INT_TYPE);
      else
        leftOperand.setConvertedType(lhsType);

      if (andExpressionNode.getRealType() == rhsType)
        rightOperand.setConvertedType(rhsType);
      else if (rhsType == TypeTable.FLOAT_TYPE)
        rightOperand.setConvertedType(TypeTable.INT_TYPE);
      else
        rightOperand.setConvertedType(rhsType);
    }
    else {
      leftOperand.setConvertedType(andExpressionNode.getRealType());
      rightOperand.setConvertedType(andExpressionNode.getRealType());
    }

    return andExpressionNode.getRealType();
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
    floatConstNode.setRealType(TypeTable.FLOAT_TYPE);

    return TypeTable.FLOAT_TYPE;
  }

  @Override
  public Integer visit(IntegerConstNode integerConstNode) {
    integerConstNode.setRealType(TypeTable.INT_TYPE);

    return TypeTable.INT_TYPE;
  }

  @Override
  public Integer visit(CharacterNode characterNode) {
    characterNode.setRealType(TypeTable.CHAR_TYPE);

    return TypeTable.CHAR_TYPE;
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
