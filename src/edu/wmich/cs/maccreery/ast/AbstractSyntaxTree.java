package edu.wmich.cs.maccreery.ast;

public class AbstractSyntaxTree
{
  private ProgramNode root;

  public AbstractSyntaxTree(ProgramNode root) {
    this.setRoot(root);
  }

  public void setRoot(ProgramNode root) { this.root = root; }
  public ProgramNode getRoot() { return root; }
}