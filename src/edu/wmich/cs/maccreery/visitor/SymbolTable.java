package edu.wmich.cs.maccreery.visitor;

import edu.wmich.cs.maccreery.ast.TypeNode;

import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

public class SymbolTable extends Hashtable<String,Stack<SymbolTableEntry>>
{

  private static final long serialVersionUID = 1230;

  private final static int initialCapacity = 100;
  private int currentLevel = -1;
  private int currentOffset;

  private Stack<Vector<Stack<SymbolTableEntry>>> scopeList;

  public SymbolTable() {
    super(initialCapacity);

    scopeList = new Stack<Vector<Stack<SymbolTableEntry>>>();

  }

  public int getCurrentLevel() {
    return currentLevel;
  }

  public void beginScope() {
    scopeList.push(new Vector<Stack<SymbolTableEntry>>());
    currentLevel++;
    currentOffset = 0;
  }

  public void endScope(String name) {
    Vector<Stack<SymbolTableEntry>> scope = scopeList.pop();

    for (int i = 0; i < scope.size(); i++) {
      Stack<SymbolTableEntry> nameStack = scope.elementAt(i);
      nameStack.pop();
//      if (!entry.isReferenced()) {
//        System.err.println("In function "+name+": Variable "+entry.getName()+
//                           " never referenced");
//      }
    }

    currentLevel--;
  }

  public SymbolTableEntry add(String name, TypeNode type) {
    Stack<SymbolTableEntry> nameStack = get(name);

    if (nameStack != null) {
      if (!nameStack.isEmpty()) {
        SymbolTableEntry entry = (SymbolTableEntry) nameStack.peek();
        if (entry != null) {
          if (entry.getNestingLevel() == currentLevel)
            return null;
        }
      }
    }
    else {
      nameStack = new Stack<SymbolTableEntry>();
      put(name,nameStack);
    }
    SymbolTableEntry entry = new SymbolTableEntry(name);
    entry.setNestingLevel(currentLevel);
    int s = type.getSize();
    entry.setOffset(currentOffset - s);
    currentOffset = currentOffset - s;
    nameStack.push(entry);
    Vector<Stack<SymbolTableEntry>> scope = scopeList.peek();

    scope.add(nameStack);

    return entry;
  }

  public SymbolTableEntry getEntry(String name) {
    SymbolTableEntry entry = null;

    Stack<SymbolTableEntry> nameStack = get(name);

    if (nameStack != null && !nameStack.isEmpty())
      entry = (SymbolTableEntry)nameStack.peek();

    return entry;
  }

}