package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class CaseElementNode extends ASTNode implements Visitable
{
  private Vector caseLabelList;
  private StatementNode stmtNode;

  public CaseElementNode(Vector caseLabelList, StatementNode stmtNode) {
    children = new Vector();
    this.setCaseLabelList(caseLabelList);
    this.setStmtNode(stmtNode);
  }

  public void setCaseLabelList(Vector caseList) { this.caseLabelList = caseList; children.add(caseList); }
  public Vector getCaseLabelList() { return caseLabelList; }

  public void setStmtNode(StatementNode stmtNode) { this.stmtNode = stmtNode; children.add(stmtNode); }
  public StatementNode getStmtNode() { return stmtNode; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
