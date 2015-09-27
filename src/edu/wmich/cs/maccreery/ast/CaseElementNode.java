package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

public class CaseElementNode extends ASTNode implements Visitable
{
  private ASTVectorNode<ASTNode> caseLabelList;
  private StatementNode stmtNode;

  public CaseElementNode(ASTVectorNode<ASTNode> caseLabelList, StatementNode stmtNode) {
    children = new ASTVectorNode<ASTNode>();
    this.setCaseLabelList(caseLabelList);
    this.setStmtNode(stmtNode);
  }

  public void setCaseLabelList(ASTVectorNode<ASTNode> caseList) { this.caseLabelList = caseList; children.add(caseList); }
  public ASTVectorNode<ASTNode> getCaseLabelList() { return caseLabelList; }

  public void setStmtNode(StatementNode stmtNode) { this.stmtNode = stmtNode; children.add(stmtNode); }
  public StatementNode getStmtNode() { return stmtNode; }

  @Override
  public void accept(Visitor v) { v.visit(this); }
}
