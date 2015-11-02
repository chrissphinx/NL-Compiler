package edu.wmich.cs.maccreery.visitor;

import edu.wmich.cs.maccreery.ast.*;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class CompileVisitor implements Visitor<Integer>
{
  private SymbolTable symTable;
  private int labelCounter = 0;
  private int stringCounter = 0;
  private StringBuilder head = new StringBuilder(
    ".data\n" +
    ".string .s_nop \"\"\n" +
    ".string .int_wformat \"%d\\12\"\n" +
    ".string .float_wformat \"%f\\12\"\n" +
    ".string .char_wformat \"%c\\12\"\n" +
    ".string .string_wformat \"%s\\12\"\n" +
    ".string .int_rformat \"%d\"\n" +
    ".string .float_rformat \"%f\"\n" +
    ".string .char_rformat \"%c\"\n" +
    ".string .string_rformat \"%s\"\n"
  );
  private StringBuilder body = new StringBuilder(".text\n");

  public CompileVisitor() { symTable = new SymbolTable(); }

  @Override
  public Integer visit(ProcedureDeclNode procedureDeclNode) {
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Integer visit(FunctionDeclNode node) {
    symTable.beginScope();
    int r = 0;
    for (VariableDeclarationNode n : (Vector<VariableDeclarationNode>) node.getParamList()) {
      int size = n.accept(this);
      r = size / TypeTable.getDataSize(n.getType().getRealType());
    }

    for (VariableDeclarationNode n : (Vector<VariableDeclarationNode>) node.getVariableDecls())
      n.accept(this);

    body.append(".frame ").append(node.getImage()).append(" ").append(node.getFrameSize());
    for (int i = 0; i < r; i++) {
      body.append(" %vr").append(i + 4);
    }
    body.append("\n");
    ExpressionTable.getInstance().beginScope(r + 4);

    node.getBody().accept(this);

    // body.append("ret\n");

    symTable.endScope("");
    ExpressionTable.getInstance().endScope();
    return 0;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Integer visit(ProgramNode node) {
    symTable.beginScope();
    ExpressionTable.getInstance().beginScope();

    for (VariableDeclarationNode n : (Vector<VariableDeclarationNode>) node.getVariableDecls()) {
      n.accept(this);
    }
    body.append(".frame main ").append(node.getFrameSize()).append("\n");

    node.getBody().accept(this);
    body.append("ret\n");

    for (ASTNode n : (Vector<ASTNode>) node.getSubProgDecls()) {
      n.accept(this);
    }

    System.out.print(head);
    System.out.print(body);

    symTable.endScope("");
    ExpressionTable.getInstance().endScope();
    return 0;
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
  public Integer visit(StringNode node) {
    int c = stringCounter;
    stringCounter++;

    head.append(".string .string_const_").append(c).append(" \"").append(node.getString()).append("\"\n");

    String s = "loadI .string_const_" + c;
    int r = ExpressionTable.getInstance().check(s);
    body.append(s).append(" => %vr").append(r).append("\n");

    return r;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Integer visit(CaseStatementNode node) {
    int l = labelCounter;
    int ll = l;
    labelCounter += node.getCaseList().size();

    int expression = node.getCaseExpr().accept(this);

    for (CaseElementNode element : (Vector<CaseElementNode>) node.getCaseList()) {
      if (ll != l) {
        body.append(".L").append(ll).append(": nop").append("\n");
      }

      String s = "";
      int r = 0;
      Stack<Integer> registers = new Stack<Integer>();

      for (ConstantNode c : (Vector<ConstantNode>) element.getCaseLabelList()) {
        int constant = c.accept(this);

        s = "comp %vr" + expression + " %vr" + constant;
        r = ExpressionTable.getInstance().check(s);
        body.append(s).append(" => %vr").append(r).append("\n");

        s = "testne %vr" + r;
        r = ExpressionTable.getInstance().check(s);
        body.append(s).append(" => %vr").append(r).append("\n");

        registers.push(r);
      }

      if (registers.size() > 1) {
        s = "and %vr" + registers.pop() + " %vr" + registers.pop();
        r = ExpressionTable.getInstance().check(s);
        body.append(s).append(" => %vr").append(r).append("\n");

        for (Integer i : registers) {
          s = "and %vr" + i + " %vr" + r;
          body.append(s).append(" => %vr").append(r).append("\n");
        }
      }

      body.append("cbr %vr").append(r).append(" => ").append(".L").append(++ll).append("\n");

      element.getStmtNode().accept(this);

      body.append("jumpI .L").append(l).append("\n");
    }

    body.append(".L").append(ll).append(": nop").append("\n");

    body.append(".L").append(l).append(": nop").append("\n");

    return 0;
  }

  @Override
  public Integer visit(CaseElementNode node) {
    return 0;
  }

  @Override
  public Integer visit(ProcedureInvocationNode procedureInvocationNode) {
    return null;
  }

  @Override
  public Integer visit(WhileStatementNode node) {
    int l = labelCounter;
    labelCounter += 2;
    body.append(".L").append(l).append(": nop").append("\n");

    int a = node.getWhileExpr().accept(this);

    String s = "loadI 0";
    int r = ExpressionTable.getInstance().check(s);
    body.append(s).append(" => %vr").append(r).append("\n");

    s = "comp %vr" + a + " %vr" + r;
    r = ExpressionTable.getInstance().check(s);
    body.append(s).append(" => %vr").append(r).append("\n");

    s = "testeq %vr" + r;
    r = ExpressionTable.getInstance().check(s);
    body.append(s).append(" => %vr").append(r).append("\n");

    body.append("cbr %vr").append(r).append(" => .L").append(l + 1).append("\n");

    node.getControlledStmt().accept(this);

    body.append("jumpI .L").append(l).append("\n");

    body.append(".L").append(l + 1).append(": nop").append("\n");

    return 0;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Integer visit(CompoundStatementNode node) {
    for (ASTNode n : (Vector<ASTNode>) node.getStmtList()) {
      n.accept(this);
    }

    return 0;
  }

  @Override
  public Integer visit(ReturnStatementNode node) {
    int r = node.getReturnExpr().accept(this);

    switch (node.getRealType()) {
      case TypeTable.INT_TYPE:
        body.append("iret %vr");
        break;
      case TypeTable.FLOAT_TYPE:
        body.append("fret %vr");
        break;
      default:
        body.append("ret %vr");
        break;
    }

    body.append(r).append("\n");

    return r;
  }

  @Override
  public Integer visit(ReadStatementNode readStatementNode) {
    int r = readStatementNode.getVariable().accept(this);

    body.append("iread %vr").append(r).append("\n");

    return r;
  }

  @Override
  public Integer visit(WriteStatementNode node) {
    int r = node.getWriteExpr().accept(this);

    if (node.getConvertedType() == TypeTable.STRING_TYPE) {
      body.append("swrite %vr").append(r).append("\n");
    } else {
      body.append("iwrite %vr").append(r).append("\n");
    }

    return r;
  }

  @Override
  public Integer visit(IfStatementNode node) {
    int l = labelCounter;
    labelCounter += 2;
    int a = node.getTestExpr().accept(this);

    body.append("cbrne %vr").append(a).append(" => .L").append(l).append("\n");

    node.getThenStmt().accept(this);

    body.append("jumpI .L").append(l + 1).append("\n");

    body.append(".L").append(l).append(": nop").append("\n");

    StatementNode elseStmt = node.getElseStmt();
    if (elseStmt != null) {
      elseStmt.accept(this);
    }

    body.append(".L").append(l + 1).append(": nop").append("\n");

    return 0;
  }

  @Override
  public Integer visit(AssignmentStatementNode node) {
    int l = node.getLeft().accept(this);
    int r = node.getRight().accept(this);

    body.append("store %vr").append(r).append(" => %vr").append(l).append("\n");

    return r;
  }

  @Override
  public Integer visit(ScalarReferenceNode node) {
    int register = symTable.getInRegister(node.getImage());

    if (register != 0)
    {
      if (node.getParent() instanceof InvocationNode) {
        return register;
      } else {
        String s = "load %vr" + register;
        int r = ExpressionTable.getInstance().check(s);
        body.append(s).append(" => %vr").append(r).append("\n");

        return r;
      }
    }
    else
    {
      String s = "loadI " + symTable.getEntry(node.getImage()).getOffset();
      int r = ExpressionTable.getInstance().check(s);
      body.append(s).append(" => %vr").append(r).append("\n");

      if (node.getParent() instanceof AssignmentStatementNode
       || node.getParent() instanceof ReadStatementNode
       || node.getParent() instanceof InvocationNode) {
        s = "add %vr0 %vr" + r;
        r = ExpressionTable.getInstance().check(s);
        body.append(s).append(" => %vr").append(r).append("\n");
      } else {
        s = "loadAO %vr0 %vr" + r;
        r = ExpressionTable.getInstance().check(s);
        body.append(s).append(" => %vr").append(r).append("\n");
      }

      return r;
    }
  }

  @Override
  public Integer visit(ArrayReferenceNode arrayReferenceNode) {
    return null;
  }

  @Override
  public Integer visit(NotExpressionNode node) {
    String s = "not %vr" + node.getOperand().accept(this);
    int r = ExpressionTable.getInstance().check(s);
    body.append(s).append(" => %vr").append(r).append("\n");

    return r;
  }

  @Override
  public Integer visit(ParenthesisNode node) {
    return node.getExprNode().accept(this);
  }

  @Override
  public Integer visit(SubtractExpressionNode node) {
    String s = "sub %vr" + node.getLeftOperand().accept(this) + " %vr" + node.getRightOperand().accept(this);
    int r = ExpressionTable.getInstance().check(s);
    body.append(s).append(" => %vr").append(r).append("\n");

    return r;
  }

  @Override
  public Integer visit(AddExpressionNode node) {
    String s = "add %vr" + node.getLeftOperand().accept(this) + " %vr" + node.getRightOperand().accept(this);
    int r = ExpressionTable.getInstance().check(s);
    body.append(s).append(" => %vr").append(r).append("\n");

    return r;
  }

  @Override
  public Integer visit(ModExpressionNode node) {
    String s = "mod %vr" + node.getLeftOperand().accept(this) + " %vr" + node.getRightOperand().accept(this);
    int r = ExpressionTable.getInstance().check(s);
    body.append(s).append(" => %vr").append(r).append("\n");

    return r;
  }

  @Override
  public Integer visit(GreaterThanExpressionNode node) {
    int left = node.getLeftOperand().accept(this);
    int right = node.getRightOperand().accept(this);

    String s = "comp %vr" + left + " %vr" + right;
    int r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    body.append(s).append("\n");

    s = "testgt %vr" + r;
    r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    body.append(s).append("\n");

    return r;
  }

  @Override
  public Integer visit(NotEqualExpressionNode node) {
    int left = node.getLeftOperand().accept(this);
    int right = node.getRightOperand().accept(this);

    String s = "comp %vr" + left + " %vr" + right;
    int r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    body.append(s).append("\n");

    s = "testne %vr" + r;
    r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    body.append(s).append("\n");

    return r;
  }

  @Override
  public Integer visit(LessEqualExpressionNode node) {
    int left = node.getLeftOperand().accept(this);
    int right = node.getRightOperand().accept(this);

    String s = "comp %vr" + left + " %vr" + right;
    int r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    body.append(s).append("\n");

    s = "testle %vr" + r;
    r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    body.append(s).append("\n");

    return r;
  }

  @Override
  public Integer visit(LessThanExpressionNode node) {
    int left = node.getLeftOperand().accept(this);
    int right = node.getRightOperand().accept(this);

    String s = "comp %vr" + left + " %vr" + right;
    int r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    body.append(s).append("\n");

    s = "testlt %vr" + r;
    r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    body.append(s).append("\n");

    return r;
  }

  @Override
  public Integer visit(EqualExpressionNode node) {
    int left = node.getLeftOperand().accept(this);
    int right = node.getRightOperand().accept(this);

    String s = "comp %vr" + left + " %vr" + right;
    int r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    body.append(s).append("\n");

    s = "testeq %vr" + r;
    r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    body.append(s).append("\n");

    return r;
  }

  @Override
  public Integer visit(GreaterEqualExpressionNode node) {
    int left = node.getLeftOperand().accept(this);
    int right = node.getRightOperand().accept(this);

    String s = "comp %vr" + left + " %vr" + right;
    int r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    body.append(s).append("\n");

    s = "testge %vr" + r;
    r = ExpressionTable.getInstance().check(s);
    s += " => %vr" + r;
    body.append(s).append("\n");

    return r;
  }

  @Override
  public Integer visit(AndExpressionNode node) {
    String s = "and %vr" + node.getLeftOperand().accept(this) + " %vr" + node.getRightOperand().accept(this);
    int r = ExpressionTable.getInstance().check(s);
    body.append(s).append(" => %vr").append(r).append("\n");

    return r;
  }

  @Override
  public Integer visit(MultiplyExpressionNode node) {
    String s = "mult %vr" + node.getLeftOperand().accept(this) + " %vr" + node.getRightOperand().accept(this);
    int r = ExpressionTable.getInstance().check(s);
    body.append(s).append(" => %vr").append(r).append("\n");

    return r;
  }

  @Override
  public Integer visit(OrExpressionNode node) {
    String s = "or %vr" + node.getLeftOperand().accept(this) + " %vr" + node.getRightOperand().accept(this);
    int r = ExpressionTable.getInstance().check(s);
    body.append(s).append(" => %vr").append(r).append("\n");

    return r;
  }

  @Override
  public Integer visit(FunctionInvocationNode node) {
    return node.getInvocation().accept(this);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Integer visit(InvocationNode node) {
    Vector<ExpressionNode> params = node.getActualParameters();
    Vector<Integer> registers = new Vector<Integer>();
    StringBuilder s;

    for (ExpressionNode n : params) {
      if (!(n instanceof ScalarReferenceNode)) {
        int register = n.accept(this);
        symTable.add(n.toString(), new StandardTypeNode(n.getRealType()));

        s = new StringBuilder("loadI ");
        s.append(symTable.getEntry(n.toString()).getOffset());
        int r = ExpressionTable.getInstance().check(s.toString());
        body.append(s).append(" => %vr").append(r).append("\n");

        s = new StringBuilder("add %vr0 %vr").append(r);
        r = ExpressionTable.getInstance().check(s.toString());
        body.append(s).append(" => %vr").append(r).append("\n");

        body.append("store %vr").append(register).append(" => %vr").append(r).append("\n");

        registers.add(r);
      } else {
        registers.add(n.accept(this));
      }
    }

    s = new StringBuilder();
    switch (node.getConvertedType()) {
      case TypeTable.INT_TYPE:
        s.append("icall ");
        break;
      case TypeTable.FLOAT_TYPE:
        s.append("fcall ");
        break;
      default:
        s.append("call ");
        break;
    }

    s.append(node.getImage());
    for (Integer r : registers) {
      s.append(" %vr").append(r);
    }
    int r = ExpressionTable.getInstance().check(s.toString());
    body.append(s.append(" => %vr").append(r).toString()).append("\n");

    return r;
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
    body.append(s.append(" => %vr").append(r).toString()).append("\n");
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

    if (node.getParent() instanceof FunctionDeclNode) {
      for (String v : idList) {
        symTable.addWithoutOffset(v, idType);
        symTable.setInRegister(v);
      }
    } else {
      for (String v : idList) {
        symTable.add(v, idType);
      }
    }

    return idType.getSize() * idList.size();
  }

  private static class ExpressionTable
  {
    private Stack<HashMap<String, Integer>> tables;
    private Stack<AtomicInteger> regs;

    public Integer check(String e) {
      if (tables.peek().containsKey(e)) {
        return tables.peek().get(e);
      } else {
        int t = regs.peek().get();
        tables.peek().put(e, regs.peek().getAndIncrement());
        return t;
      }
    }

    public void beginScope() {
      tables.push(new HashMap<String, Integer>());
      regs.push(new AtomicInteger(4));
    }

    public void beginScope(int r) {
      tables.push(new HashMap<String, Integer>());
      regs.push(new AtomicInteger(r));
    }

    public void endScope() {
      tables.pop();
      regs.pop();
    }

    /* Singleton */
    private ExpressionTable() {
      tables = new Stack<HashMap<String, Integer>>();
      regs   = new Stack<AtomicInteger>();
    }

    private static class ExpressionTableHolder {
      private static final ExpressionTable INSTANCE = new ExpressionTable();
    }

    public static ExpressionTable getInstance() {
      return ExpressionTableHolder.INSTANCE;
    }
  }
}
