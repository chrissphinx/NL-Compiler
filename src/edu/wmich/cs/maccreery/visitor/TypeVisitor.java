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
    return 0;
  }

  @Override
  public Integer visit(FunctionDeclNode functionDeclNode) {
    return 0;
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
    return 0;
  }

  @Override
  public Integer visit(IntegerDimensionNode integerDimensionNode) {
    return 0;
  }

  @Override
  public Integer visit(StandardTypeNode standardTypeNode) {
    return 0;
  }

  @Override
  public Integer visit(ArrayTypeNode arrayTypeNode) {
    return 0;
  }

  @Override
  public Integer visit(StringNode node) {
    node.setRealType(TypeTable.STRING_TYPE);

    return TypeTable.STRING_TYPE;
  }

  @Override
  public Integer visit(CaseStatementNode node) {
    ExpressionNode caseExpr = node.getCaseExpr();
    Vector caseList = node.getCaseList();

    int testType = caseExpr.accept(this);

    caseExpr.setConvertedType(testType);

    for (int i = 0; i < caseList.size(); i++) {
      CaseElementNode caseElement = (CaseElementNode) caseList.elementAt(i);
      caseElement.accept(this);

      if (testType != caseElement.getRealType()) {
        System.err.println("Line " + caseElement.getLineNumber() + ": Type mismatch in case label");
        AbstractSyntaxTree.setError();
      }
    }

    node.setRealType(TypeTable.NO_TYPE);

    return TypeTable.NO_TYPE;
  }

  @Override
  public Integer visit(ProcedureInvocationNode node) {
    InvocationNode invocation = node.getInvocation();

    node.setRealType(invocation.accept(this));
    invocation.setConvertedType(TypeTable.NO_TYPE);

    int realType = node.getRealType();
    if (realType != TypeTable.NO_TYPE && realType != TypeTable.ANY_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": Function invoked as a procedure");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.NO_TYPE);
    }

    return TypeTable.NO_TYPE;
  }

  @Override
  public Integer visit(WhileStatementNode node) {
    ExpressionNode whileExpr = node.getWhileExpr();

    int exprType = whileExpr.accept(this);

    if (exprType == TypeTable.CHAR_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": while expression of type char not allowed");
      AbstractSyntaxTree.setError();
    }

    whileExpr.setConvertedType(TypeTable.INT_TYPE);

    node.getControlledStmt().accept(this);

    node.setRealType(TypeTable.NO_TYPE);

    return TypeTable.NO_TYPE;
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
  public Integer visit(ReturnStatementNode node) {
    ExpressionNode returnExpr = node.getReturnExpr();

    int returnType = returnExpr.accept(this);
    SubProgramDeclNode subDecl = node.getContainingSubProgram();

    if (returnType != TypeTable.getFunctionReturnType(subDecl.getRealType())) {
      System.err.println("Line " + node.getLineNumber() + ": return type does not match declaration");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    } else {
      returnExpr.setConvertedType(returnType);
      node.setRealType(returnType);
    }

    return 0;
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
  public Integer visit(IfStatementNode node) {
    ExpressionNode testExpr = node.getTestExpr();

    int exprType = testExpr.accept(this);

    if (exprType == TypeTable.CHAR_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": if test expression of type char not allowed");
      AbstractSyntaxTree.setError();
    }

    testExpr.setConvertedType(TypeTable.INT_TYPE);

    node.getThenStmt().accept(this);

    StatementNode elseStmt = node.getElseStmt();
    if (elseStmt != null)
      elseStmt.accept(this);

    node.setRealType(TypeTable.NO_TYPE);

    return TypeTable.NO_TYPE;
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

    return scalarReferenceNode.getRealType();
  }

  @Override
  public Integer visit(ArrayReferenceNode node) {
    ExpressionNode subscript = node.getSubscript();

    int subscriptType = subscript.accept(this);

    subscript.setConvertedType(subscriptType);

    SymbolTableEntry entry = symTable.getEntry(node.getImage());

    if (entry == null) {
      System.err.println("Line " + node.getLineNumber() + ": Undeclared variable " + node.getImage());
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    } else {
      node.setRealType(TypeTable.getArrayBasicType(entry.getDataType()));

      if (node.getRealType() == TypeTable.ERROR_TYPE) {
        System.err.println("Line " + node.getLineNumber() + ": Subscript applied to non-array");
        AbstractSyntaxTree.setError();
        node.setRealType(TypeTable.ANY_TYPE);
      } else {
        int arrayType = entry.getDataType();
        if (subscriptType != TypeTable.getArraySubscriptType(arrayType)) {
          System.err.println("Line " + node.getLineNumber() + ": subscripting type error on array " + node.getImage());
          AbstractSyntaxTree.setError();
        } else {
          int lowerBound = TypeTable.getArrayLowerBoundValue(arrayType);
          if (subscript instanceof IntegerConstNode) {
            int upb = TypeTable.getArrayIntUpperBound(arrayType);
            int index = ((IntegerConstNode) subscript).getConstant();

            if (index < lowerBound || index > upb) {
              System.err.println("Line " + node.getLineNumber() + ": Constant index out of bounds on array " + node.getImage());
              AbstractSyntaxTree.setError();
            }
          } else if (subscript instanceof CharacterNode) {
            char c_lwb = TypeTable.getArrayCharLowerBound(arrayType);
            char c_upb = TypeTable.getArrayCharUpperBound(arrayType);
            char c_index = ((CharacterNode) subscript).getCharacter();

            if (c_index < c_lwb || c_index > c_upb) {
              System.err.println("Line " + node.getLineNumber() + ": Constant index out of bounds on array " + node.getImage());
              AbstractSyntaxTree.setError();
            }
          }
        }
      }

      node.setNestingLevel(entry.getNestingLevel());
    }

    return node.getRealType();
  }

  @Override
  public Integer visit(NotExpressionNode node) {
    ExpressionNode operand = node.getOperand();

    node.setRealType(operand.accept(this));
    operand.setConvertedType(TypeTable.INT_TYPE);

    if (node.getRealType() != TypeTable.INT_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": NOT requires an integer operand");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    }

    return node.getRealType();
  }

  @Override
  public Integer visit(ParenthesisNode node) {
    ExpressionNode exprNode = node.getExprNode();

    node.setRealType(exprNode.accept(this));
    exprNode.setConvertedType(node.getRealType());

    return node.getRealType();
  }

  @Override
  public Integer visit(SubtractExpressionNode node) {
    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    node.setRealType(TypeTable.getResultArithmeticType(lhsType, rhsType));

    if (node.getRealType() == TypeTable.ERROR_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": Type mismatch in subtraction operation");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    } else if (lhsType != rhsType) {
      if (node.getRealType() == lhsType)
        leftOperand.setConvertedType(lhsType);
      else if (lhsType == TypeTable.INT_TYPE)
        leftOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        leftOperand.setConvertedType(lhsType);

      if (node.getRealType() == rhsType)
        rightOperand.setConvertedType(rhsType);
      else if (rhsType == TypeTable.INT_TYPE)
        rightOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        rightOperand.setConvertedType(rhsType);
    } else {
      leftOperand.setConvertedType(node.getRealType());
      rightOperand.setConvertedType(node.getRealType());
    }

    return node.getRealType();
  }

  @Override
  public Integer visit(AddExpressionNode node) {
    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    node.setRealType(TypeTable.getResultArithmeticType(lhsType, rhsType));

    if (node.getRealType() == TypeTable.ERROR_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": Type mismatch in addition operation");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    }
    else if (lhsType != rhsType) {
      if (node.getRealType() == lhsType)
        leftOperand.setConvertedType(lhsType);
      else if (lhsType == TypeTable.INT_TYPE)
        leftOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        leftOperand.setConvertedType(lhsType);

      if (node.getRealType() == rhsType)
        rightOperand.setConvertedType(rhsType);
      else if (rhsType == TypeTable.INT_TYPE)
        rightOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        rightOperand.setConvertedType(rhsType);
    }
    else {
      leftOperand.setConvertedType(node.getRealType());
      rightOperand.setConvertedType(node.getRealType());
    }

    return node.getRealType();
  }

  @Override
  public Integer visit(ModExpressionNode node) {
    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    leftOperand.setConvertedType(TypeTable.INT_TYPE);
    rightOperand.setConvertedType(TypeTable.INT_TYPE);

    if (lhsType != TypeTable.INT_TYPE || rhsType != TypeTable.INT_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": MOD requires integer operands");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    }
    else
      node.setRealType(TypeTable.INT_TYPE);

    return node.getRealType();
  }

  @Override
  public Integer visit(GreaterThanExpressionNode node) {
    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    node.setRealType(TypeTable.getResultComparisonType(lhsType, rhsType));

    if (node.getRealType() == TypeTable.ERROR_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": Type mismatch in greater-than operation");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    }
    else if (lhsType != rhsType) {
      if (node.getRealType() == lhsType)
        leftOperand.setConvertedType(lhsType);
      else if (lhsType == TypeTable.INT_TYPE)
        leftOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        leftOperand.setConvertedType(lhsType);

      if (node.getRealType() == rhsType)
        rightOperand.setConvertedType(rhsType);
      else if (rhsType == TypeTable.INT_TYPE)
        rightOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        rightOperand.setConvertedType(rhsType);
    }
    else {
      leftOperand.setConvertedType(node.getRealType());
      rightOperand.setConvertedType(node.getRealType());
    }

    return node.getRealType();
  }

  @Override
  public Integer visit(NotEqualExpressionNode node) {
    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    node.setRealType(TypeTable.getResultComparisonType(lhsType, rhsType));

    if (node.getRealType() == TypeTable.ERROR_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": Type mismatch in not-equal operation");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    }
    else if (lhsType != rhsType) {
      if (node.getRealType() == lhsType)
        leftOperand.setConvertedType(lhsType);
      else if (lhsType == TypeTable.INT_TYPE)
        leftOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        leftOperand.setConvertedType(lhsType);

      if (node.getRealType() == rhsType)
        rightOperand.setConvertedType(rhsType);
      else if (rhsType == TypeTable.INT_TYPE)
        rightOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        rightOperand.setConvertedType(rhsType);
    }
    else {
      leftOperand.setConvertedType(node.getRealType());
      rightOperand.setConvertedType(node.getRealType());
    }

    return node.getRealType();
  }

  @Override
  public Integer visit(LessEqualExpressionNode node) {
    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    node.setRealType(TypeTable.getResultComparisonType(lhsType, rhsType));

    if (node.getRealType() == TypeTable.ERROR_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": Type mismatch in less-than-or-equal operation");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    } else if (lhsType != rhsType) {
      if (node.getRealType() == lhsType)
        leftOperand.setConvertedType(lhsType);
      else if (lhsType == TypeTable.INT_TYPE)
        leftOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        leftOperand.setConvertedType(lhsType);

      if (node.getRealType() == rhsType)
        rightOperand.setConvertedType(rhsType);
      else if (rhsType == TypeTable.INT_TYPE)
        rightOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        rightOperand.setConvertedType(rhsType);
    }
    else {
      leftOperand.setConvertedType(node.getRealType());
      rightOperand.setConvertedType(node.getRealType());
    }

    return node.getRealType();
  }

  @Override
  public Integer visit(LessThanExpressionNode node) {
    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    node.setRealType(TypeTable.getResultComparisonType(lhsType, rhsType));

    if (node.getRealType() == TypeTable.ERROR_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": Type mismatch in less-than operation");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    }
    else if (lhsType != rhsType) {
      if (node.getRealType() == lhsType)
        leftOperand.setConvertedType(lhsType);
      else if (lhsType == TypeTable.INT_TYPE)
        leftOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        leftOperand.setConvertedType(lhsType);

      if (node.getRealType() == rhsType)
        rightOperand.setConvertedType(rhsType);
      else if (rhsType == TypeTable.INT_TYPE)
        rightOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        rightOperand.setConvertedType(rhsType);
    }
    else {
      leftOperand.setConvertedType(node.getRealType());
      rightOperand.setConvertedType(node.getRealType());
    }

    return node.getRealType();
  }

  @Override
  public Integer visit(EqualExpressionNode node) {
    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    node.setRealType(TypeTable.getResultComparisonType(lhsType, rhsType));

    if (node.getRealType() == TypeTable.ERROR_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": Type mismatch in equal comparison");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    }
    else if (lhsType != rhsType) {
      if (node.getRealType() == lhsType)
        leftOperand.setConvertedType(lhsType);
      else if (lhsType == TypeTable.INT_TYPE)
        leftOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        leftOperand.setConvertedType(lhsType);

      if (node.getRealType() == rhsType)
        rightOperand.setConvertedType(rhsType);
      else if (rhsType == TypeTable.INT_TYPE)
        rightOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        rightOperand.setConvertedType(rhsType);
    }
    else {
      leftOperand.setConvertedType(node.getRealType());
      rightOperand.setConvertedType(node.getRealType());
    }

    return node.getRealType();
  }

  @Override
  public Integer visit(GreaterEqualExpressionNode node) {
    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    node.setRealType(TypeTable.getResultComparisonType(lhsType, rhsType));

    if (node.getRealType() == TypeTable.ERROR_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": Type mismatch in greater-than-or-equal operation");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    } else if (lhsType != rhsType) {
      if (node.getRealType() == lhsType)
        leftOperand.setConvertedType(lhsType);
      else if (lhsType == TypeTable.INT_TYPE)
        leftOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        leftOperand.setConvertedType(lhsType);

      if (node.getRealType() == rhsType)
        rightOperand.setConvertedType(rhsType);
      else if (rhsType == TypeTable.INT_TYPE)
        rightOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        rightOperand.setConvertedType(rhsType);
    } else {
      leftOperand.setConvertedType(node.getRealType());
      rightOperand.setConvertedType(node.getRealType());
    }

    return node.getRealType();
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
  public Integer visit(MultiplyExpressionNode node) {
    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    node.setRealType(TypeTable.getResultArithmeticType(lhsType, rhsType));

    if (node.getRealType() == TypeTable.ERROR_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": Type mismatch in multiply operation");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    }
    else if (lhsType != rhsType) {
      if (node.getRealType() == lhsType)
        leftOperand.setConvertedType(lhsType);
      else if (lhsType == TypeTable.INT_TYPE)
        leftOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        leftOperand.setConvertedType(lhsType);

      if (node.getRealType() == rhsType)
        rightOperand.setConvertedType(rhsType);
      else if (rhsType == TypeTable.INT_TYPE)
        rightOperand.setConvertedType(TypeTable.FLOAT_TYPE);
      else
        rightOperand.setConvertedType(rhsType);
    }
    else {
      leftOperand.setConvertedType(node.getRealType());
      rightOperand.setConvertedType(node.getRealType());
    }

    return node.getRealType();
  }

  @Override
  public Integer visit(OrExpressionNode node) {
    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int lhsType = leftOperand.accept(this);
    int rhsType = rightOperand.accept(this);

    node.setRealType(TypeTable.getResultBooleanType(lhsType, rhsType));

    if (node.getRealType() == TypeTable.ERROR_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": Type mismatch in OR operation");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    }
    else if (lhsType != rhsType) {
      if (node.getRealType() == lhsType)
        leftOperand.setConvertedType(lhsType);
      else if (lhsType == TypeTable.FLOAT_TYPE)
        leftOperand.setConvertedType(TypeTable.INT_TYPE);
      else
        leftOperand.setConvertedType(lhsType);

      if (node.getRealType() == rhsType)
        rightOperand.setConvertedType(rhsType);
      else if (rhsType == TypeTable.FLOAT_TYPE)
        rightOperand.setConvertedType(TypeTable.INT_TYPE);
      else
        rightOperand.setConvertedType(rhsType);
    }
    else {
      leftOperand.setConvertedType(node.getRealType());
      rightOperand.setConvertedType(node.getRealType());
    }

    return node.getRealType();
  }

  @Override
  public Integer visit(FunctionInvocationNode node) {
    InvocationNode invocation = node.getInvocation();

    node.setRealType(invocation.accept(this));
    invocation.setConvertedType(node.getRealType());

    if (node.getRealType() == TypeTable.NO_TYPE) {
      System.err.println("Line " + node.getLineNumber() + ": Procedure invoked as function");
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    }

    return node.getRealType();
  }

  @Override
  public Integer visit(InvocationNode node) {
    Vector actualParameters = node.getActualParameters();

    for (int i = 0; i < actualParameters.size(); i++) {
      ExpressionNode expr = (ExpressionNode) actualParameters.elementAt(i);
      int paramType = expr.accept(this);
      expr.setConvertedType(paramType);
    }

    SymbolTableEntry entry = symTable.getEntry(node.getImage());

    if (entry == null) {
      System.err.println("Line " + node.getLineNumber() + ": Undeclared variable " + node.getImage());
      AbstractSyntaxTree.setError();
      node.setRealType(TypeTable.ANY_TYPE);
    }
    else {
      String typeString = TypeTable.getTypeString(entry.getDataType());
      int returnType = TypeTable.getFunctionReturnType(entry.getDataType());
      if (typeString.compareTo(node.toString() + TypeTable.getTypeString(returnType)) != 0) {
        System.err.println("Line " + node.getLineNumber() + ": Mismatched parameter types for function " + node.getImage());
        AbstractSyntaxTree.setError();
      }
      node.setRealType(returnType);
    }

    return node.getRealType();
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
  public Integer visit(CaseElementNode node) {
    Vector caseLabelList = node.getCaseLabelList();

    ConstantNode constNode = (ConstantNode) caseLabelList.elementAt(0);

    node.setRealType(constNode.accept(this));
    constNode.setConvertedType(node.getRealType());

    for (int i = 1; i < caseLabelList.size(); i++) {
      constNode = (ConstantNode) caseLabelList.elementAt(i);

      int labelType = constNode.accept(this);
      constNode.setConvertedType(node.getRealType());

      if (labelType != node.getRealType()) {
        System.err.println("Line " + node.getLineNumber() + ": Multiple types in set of case labels");
        AbstractSyntaxTree.setError();
      }
    }

    node.getStmtNode().accept(this);

    return node.getRealType();
  }
}
