package edu.wmich.cs.maccreery.visitor;

import edu.wmich.cs.maccreery.ast.*;

import java.util.HashMap;
import java.util.Vector;

public class CompileVisitor implements Visitor<Integer>
{
  private SymbolTable symTable;
  private int labelCounter = 0;

  public CompileVisitor() {
    symTable = new SymbolTable();
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
  @SuppressWarnings("unchecked")
  public Integer visit(ProgramNode node) {
    System.out.println(".data\n.string .s_nop \"\"");
    System.out.print(".text\n.frame main ");

    symTable.beginScope();
    int f = 0;
    for (ASTNode n : (Vector<ASTNode>) node.getVariableDecls()) {
      f += n.accept(this);
    }
    System.out.println(f);

    int r = node.getBody().accept(this);
    System.out.println("ret");
    return r;
  }

  @Override
  public Integer visit(CharacterDimensionNode characterDimensionNode) {
    return null;
  }

  @Override
  public Integer visit(IntegerDimensionNode TegerDimensionNode) {
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
  public Integer visit(WhileStatementNode node) {
    System.out.println(".L" + labelCounter + ": nop");
    labelCounter++;

    int a = node.getWhileExpr().accept(this);

    String s = "loadI 0";
    int r = ExpressionTable.getInstance().check(s);
    System.out.println(s + " => %vr" + r);

    s = "comp %vr" + a + " %vr" + r;
    r = ExpressionTable.getInstance().check(s);
    System.out.println(s + " => %vr" + r);

    s = "testeq %vr" + r;
    r = ExpressionTable.getInstance().check(s);
    System.out.println(s + " => %vr" + r);

    System.out.println("cbr %vr" + r + " => .L" + labelCounter);

    node.getControlledStmt().accept(this);

    System.out.println(".L" + labelCounter + ": nop");
    labelCounter++;

    return 0;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Integer visit(CompoundStatementNode node) {
    int r = 4;

    for (ASTNode n : (Vector<ASTNode>) node.getStmtList()) {
      r = n.accept(this);
    }

    return r;
  }

  @Override
  public Integer visit(ReturnStatementNode returnStatementNode) {
    return null;
  }

  @Override
  public Integer visit(ReadStatementNode readStatementNode) {
    int r = readStatementNode.getVariable().accept(this);

    System.out.println("iread %vr" + r);

    return r;
  }

  @Override
  public Integer visit(WriteStatementNode node) {
    int r = node.getWriteExpr().accept(this);

    System.out.println("iwrite %vr" + r);
    return r;
  }

  @Override
  public Integer visit(IfStatementNode ifStatementNode) {
    return null;
  }

  @Override
  public Integer visit(AssignmentStatementNode node) {
    int r = node.getLeft().accept(this);

    System.out.println("store %vr" + node.getRight().accept(this) + " => %vr" + r);

    return r;
  }

  @Override
  public Integer visit(ScalarReferenceNode node) {
    String s = "loadI " + symTable.getEntry(node.getImage()).getOffset();
    int r = ExpressionTable.getInstance().check(s);
    System.out.println(s + " => %vr" + r);

    if (node.getParent() instanceof AssignmentStatementNode
     || node.getParent() instanceof ReadStatementNode
     || node.getParent() instanceof InvocationNode) {
      s = "add %vr0 %vr" + r;
      r = ExpressionTable.getInstance().check(s);
      System.out.println(s + " => %vr" + r);
    } else {
      s = "loadAO %vr0 %vr" + r;
      r = ExpressionTable.getInstance().check(s);
      System.out.println(s + " => %vr" + r);
    }

    return r;
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
  public Integer visit(ParenthesisNode node) {
    return node.getExprNode().accept(this);
  }

  @Override
  public Integer visit(SubtractExpressionNode subtractExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(AddExpressionNode node) {
    StringBuilder s;

    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int rl = 0;
    rl = leftOperand.accept(this);

    int rr = 0;
    rr = rightOperand.accept(this);

    s = new StringBuilder();
    s.append("add %vr")
            .append(rl)
            .append(" %vr")
            .append(rr);

    int r = ExpressionTable.getInstance().check(s.toString());
    System.out.println(s.append(" => %vr").append(r));
    return r;
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
  public Integer visit(NotEqualExpressionNode node) {
    int left = node.getLeftOperand().accept(this);
    int right = node.getRightOperand().accept(this);

    String s = "comp %vr" + left + " %vr" + right;
    int r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    System.out.println(s);

    s = "testne %vr" + r;
    r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    System.out.println(s);

    return r;
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
  public Integer visit(AndExpressionNode node) {
    StringBuilder s;

    ExpressionNode leftOperand = node.getLeftOperand();
    ExpressionNode rightOperand = node.getRightOperand();

    int rl = 0;
    rl = leftOperand.accept(this);

    int rr = 0;
    rr = rightOperand.accept(this);

    s = new StringBuilder();
    s.append("and %vr")
            .append(rl)
            .append(" %vr")
            .append(rr);

    int r = ExpressionTable.getInstance().check(s.toString());
    System.out.println(s.append(" => %vr").append(r));
    return r;
  }

  @Override
  public Integer visit(MultiplyExpressionNode multiplyExpressionNode) {
    return null;
  }

  @Override
  public Integer visit(OrExpressionNode node) {
    int left = node.getLeftOperand().accept(this);
    int right = node.getRightOperand().accept(this);

    String s = "or %vr" + left + " %vr" + right;
    int r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    System.out.println(s);

    return r;
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
  public Integer visit(IntegerConstNode node) {
    StringBuilder s = new StringBuilder();
    s.append("loadI ").append(node.getConstant());

    int r = ExpressionTable.getInstance().check(s.toString());
    System.out.println(s.append(" => %vr").append(r).toString());
    return r;
  }

  @Override
  public Integer visit(CharacterNode characterNode) {
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Integer visit(VariableDeclarationNode node) {
    Vector<String> idList = node.getVariableList();
    TypeNode idType = node.getType();

    for (String v : (Vector<String>) idList) {
      symTable.add(v, idType);
    }

    return idType.getSize() * idList.size();
  }

  @Override
  public Integer visit(CaseElementNode caseElementNode) {
    return null;
  }

  private static class ExpressionTable
  {
    private HashMap<String, Integer> table;
    private int reg;

    public Integer check(String e) {
      if (table.containsKey(e)) {
        return table.get(e);
      } else {
        int t = reg;
        table.put(e, reg++);
        return t;
      }
    }

    /* Singleton */
    private ExpressionTable() {
      table = new HashMap<String, Integer>();
      reg   = 5;
    }

    private static class ExpressionTableHolder {
      private static final ExpressionTable INSTANCE = new ExpressionTable();
    }

    public static ExpressionTable getInstance() {
      return ExpressionTableHolder.INSTANCE;
    }
  }
}
