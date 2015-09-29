package edu.wmich.cs.maccreery.ast;

import edu.wmich.cs.maccreery.visitor.Visitor;

import java.util.*;

/** * * * * *
 *
 * Reimplementation of java.util.Vector as an ASTNode
 *
 * NOTE: Some methods are not fully implemented
 *
 */
public class ASTVectorNode<E> extends ASTNode implements List<E>
{
  private Object elementData[];
  private int elementCount;
  private int capacityIncrement;

  public ASTVectorNode(int initialCapacity, int capacityIncrement) {
    if (initialCapacity < 0)
      throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
    this.elementData = new Object[initialCapacity];
    this.capacityIncrement = capacityIncrement;
  }

  public ASTVectorNode(int initialCapacity) {
    this(initialCapacity, 0);
  }

  public ASTVectorNode() {
    this(10);
  }

  @Override
  public int size() {
    return elementCount;
  }

  @Override
  public boolean isEmpty() {
    return elementCount == 0;
  }

  @Override
  public boolean contains(Object o) {
    return indexOf(o, 0) >= 0;
  }

  public E elementAt(int index) {
    if (index >= elementCount) {
      throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
    }

    return elementData(index);
  }

  @SuppressWarnings("unchecked")
  E elementData(int index) {
    return (E) elementData[index];
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(elementData, elementCount);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T[] toArray(T[] a) {
    if (a.length < elementCount)
      return (T[]) Arrays.copyOf(elementData, elementCount, a.getClass());

    System.arraycopy(elementData, 0, a, 0, elementCount);

    if (a.length > elementCount)
      a[elementCount] = null;

    return a;
  }

  @Override
  public boolean add(E e) {
    ensureCapacity(elementCount + 1);
    elementData[elementCount++] = e;
    return true;
  }

  @Override
  public boolean remove(Object o) {
    return removeElement(o);
  }

  public boolean removeElement(Object o) {
    int i = indexOf(o);

    if (i >= 0) {
      removeElementAt(i);
      return true;
    }

    return false;
  }

  public void removeElementAt(int index) {
    if (index >= elementCount) {
      throw new ArrayIndexOutOfBoundsException(index + " >= " +
              elementCount);
    }
    else if (index < 0) {
      throw new ArrayIndexOutOfBoundsException(index);
    }

    int j = elementCount - index - 1;
    if (j > 0) {
      System.arraycopy(elementData, index + 1, elementData, index, j);
    }

    elementCount--;
    elementData[elementCount] = null;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    return false;
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  @Override
  public void clear() {
    removeAllElements();
  }

  public void removeAllElements() {
    for (int i = 0; i < elementCount; i++)
      elementData[i] = null;

    elementCount = 0;
  }

  @Override
  public E get(int index) {
    if (index >= elementCount)
      throw new ArrayIndexOutOfBoundsException(index);

    return elementData(index);
  }

  @Override
  public E set(int index, E element) {
    if (index >= elementCount)
      throw new ArrayIndexOutOfBoundsException(index);

    E oldValue = elementData(index);
    elementData[index] = element;
    return oldValue;
  }

  @Override
  public void add(int index, E element) {
    insertElementAt(element, index);
  }

  @Override
  public E remove(int index) {
    if (index >= elementCount)
      throw new ArrayIndexOutOfBoundsException(index);
    E oldValue = elementData(index);

    int numMoved = elementCount - index - 1;
    if (numMoved > 0)
      System.arraycopy(elementData, index + 1, elementData, index, numMoved);
    elementData[--elementCount] = null;

    return oldValue;
  }

  @Override
  public int indexOf(Object o) {
    return indexOf(o, 0);
  }

  public int indexOf(Object o, int index) {
    if (o == null) {
      for (int i = index ; i < elementCount ; i++)
        if (elementData[i]==null)
          return i;
      } else {
        for (int i = index ; i < elementCount ; i++)
          if (o.equals(elementData[i]))
            return i;
      }
    return -1;
  }

  public void insertElementAt(E obj, int index) {
    if (index > elementCount)
      throw new ArrayIndexOutOfBoundsException(index + " > " + elementCount);

    ensureCapacity(elementCount + 1);
    System.arraycopy(elementData, index, elementData, index + 1, elementCount - index);
    elementData[index] = obj;
    elementCount++;
  }

  @Override
  public int lastIndexOf(Object o) {
    return lastIndexOf(o, elementCount - 1);
  }

  public int lastIndexOf(Object o, int index) {
    if (index >= elementCount)
      throw new IndexOutOfBoundsException(index + " >= "+ elementCount);

    if (o == null) {
      for (int i = index; i >= 0; i--)
        if (elementData[i]==null)
          return i;
    } else {
      for (int i = index; i >= 0; i--)
        if (o.equals(elementData[i]))
          return i;
    }

    return -1;
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    return null;
  }

  @Override
  public Iterator<E> iterator() { return null; }

  @Override
  public ListIterator<E> listIterator() {
    return null;
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    return null;
  }

  @Override
  public <T> T accept(Visitor<T> v) { return null; }

  private void ensureCapacity(int minCapacity) {
    int oldCapacity = elementData.length;
    if (minCapacity > oldCapacity) {
      int newCapacity = (capacityIncrement > 0) ?
        (oldCapacity + capacityIncrement) : (oldCapacity * 2);
      if (newCapacity < minCapacity) {
        newCapacity = minCapacity;
      }
      elementData = Arrays.copyOf(elementData, newCapacity);
    }
  }
}
