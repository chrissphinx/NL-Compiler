package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitable;
import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.Vector;

public class CompoundStatementNode extends StatementNode implements Visitable
{
  private Vector stmtList;

  public CompoundStatementNode(Vector stmtList) {
    children = new Vector();
    this.setStmtList(stmtList);
  }

  @SuppressWarnings("unchecked")
  public void setStmtList(Vector stmtList) { this.stmtList = stmtList; children.add(stmtList); }
  public Vector getStmtList() { return stmtList; }

  @Override
  public <T> T accept(Visitor<T> v) { return v.visit(this); }
}
