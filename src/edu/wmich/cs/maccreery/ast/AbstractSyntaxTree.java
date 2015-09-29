package edu.wmich.cs.maccreery.ast;

public class AbstractSyntaxTree
{
  private ProgramNode root;
  private static boolean error = false;

  public AbstractSyntaxTree(ProgramNode root) {
    this.setRoot(root);
  }

  public void setRoot(ProgramNode root) { this.root = root; }
  public ProgramNode getRoot() { return root; }

  public static void setError() { error = true; }
  public static boolean getError() { return error; }
}
