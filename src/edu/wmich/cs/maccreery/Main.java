package edu.wmich.cs.maccreery;

import edu.wmich.cs.maccreery.ast.AbstractSyntaxTree;
import edu.wmich.cs.maccreery.ast.ProgramNode;
import edu.wmich.cs.maccreery.parser.NolifeParser;
import edu.wmich.cs.maccreery.parser.ParseException;
import edu.wmich.cs.maccreery.visitor.TypeVisitor;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main
{
  public static String fileName;

  public static void main(String[] args) throws FileNotFoundException {
    if (args.length != 1) {
      System.out.println("Usage: nlc <file>");
      System.exit(-1);
    }

    fileName = args[0];

    FileReader nlFile = new FileReader(fileName);
    NolifeParser parser = new NolifeParser(nlFile);

    ProgramNode program = null;
    try {
      program = NolifeParser.program();
    } catch (ParseException e) {
      System.err.println("Syntax Error in " + fileName + ": " + e);
      System.exit(-1);
    }

    AbstractSyntaxTree ast = new AbstractSyntaxTree(program);

    TypeVisitor typeVisitor = new TypeVisitor();

    ast.getRoot().accept(typeVisitor);

    if (AbstractSyntaxTree.getError())
      System.exit(1);

    System.exit(0);
  }
}