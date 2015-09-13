package edu.wmich.cs.maccreery.ast;

import java.util.*;

public final class TypeTable {

  private static Hashtable<String,Integer> table;

  public static final String ERROR_TYPE_STRING = "error";
  public static final String CHAR_TYPE_STRING = "char";
  public static final String INT_TYPE_STRING = "int";
  public static final String FLOAT_TYPE_STRING = "float";
  public static final String STRING_TYPE_STRING = "string";
  public static final String ANY_TYPE_STRING = "any";
  public static final String NO_TYPE_STRING = "none";

  public static final String ERROR_WFORMAT_STRING = "error";
  public static final String CHAR_WFORMAT_STRING = ".char_wformat";
  public static final String INT_WFORMAT_STRING = ".int_wformat";
  public static final String FLOAT_WFORMAT_STRING = ".float_wformat";
  public static final String STRING_WFORMAT_STRING = ".string_wformat";
  public static final String ERROR_FORMAT_STRING = "error";
  public static final String CHAR_RFORMAT_STRING = ".char_rformat";
  public static final String INT_RFORMAT_STRING = ".int_rformat";
  public static final String FLOAT_RFORMAT_STRING = ".float_rformat";
  public static final String STRING_RFORMAT_STRING = ".string_rformat";

  public static final int ERROR_TYPE = 0;
  public static final int CHAR_TYPE = 1;
  public static final int INT_TYPE = 2;
  public static final int FLOAT_TYPE = 3;
  public static final int STRING_TYPE = 4;
  public static final int ANY_TYPE = 5;
  public static final int NO_TYPE = 6;

  public static final int INT_SIZE = 4;
  public static final int CHAR_SIZE = 4;
  public static final int FLOAT_SIZE = 4;

  public static final int NUM_BASIC_TYPES = 7;

  private static final int[][] arithmeticTable = {
          {
          ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, INT_TYPE, FLOAT_TYPE, ERROR_TYPE, INT_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, FLOAT_TYPE, FLOAT_TYPE, ERROR_TYPE, FLOAT_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, INT_TYPE, FLOAT_TYPE, ERROR_TYPE, ANY_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
          ERROR_TYPE}
  };

  private static final int[][] assignmentTable = {
          {
                  ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
                  ERROR_TYPE}, {
          ERROR_TYPE, CHAR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, CHAR_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, INT_TYPE, INT_TYPE, ERROR_TYPE, INT_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, FLOAT_TYPE, FLOAT_TYPE, ERROR_TYPE, FLOAT_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ANY_TYPE, ANY_TYPE, ANY_TYPE, ERROR_TYPE, ANY_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
          ERROR_TYPE}
  };

  private static final int[][] booleanTable = {
          {
                  ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
                  ERROR_TYPE}, {
          ERROR_TYPE, CHAR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, CHAR_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, INT_TYPE, INT_TYPE, ERROR_TYPE, INT_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, INT_TYPE, FLOAT_TYPE, ERROR_TYPE, FLOAT_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, CHAR_TYPE, INT_TYPE, FLOAT_TYPE, ERROR_TYPE, ANY_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
          ERROR_TYPE}
  };

  private static final int[][] comparisonTable = {
          {
          ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, INT_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, INT_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, INT_TYPE, INT_TYPE, ERROR_TYPE, INT_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, INT_TYPE, INT_TYPE, ERROR_TYPE, INT_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, INT_TYPE, INT_TYPE, INT_TYPE, ERROR_TYPE, INT_TYPE,
          ERROR_TYPE}, {
          ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE, ERROR_TYPE,
          ERROR_TYPE}
  };

  private static int typeNumber = NUM_BASIC_TYPES;

  private static Vector<String> typeStringVec;

  public static void initTypeTable(int initialCapacity) {
    table = new Hashtable<String,Integer>(initialCapacity);

    typeStringVec = new Vector<String>();

    // the order of the calls must not change unless the TYPE values change
    // this order must match the order of TYPE values

    putTypeVal(ERROR_TYPE_STRING, ERROR_TYPE);
    putTypeVal(CHAR_TYPE_STRING, CHAR_TYPE);
    putTypeVal(INT_TYPE_STRING, INT_TYPE);
    putTypeVal(FLOAT_TYPE_STRING, FLOAT_TYPE);
    putTypeVal(STRING_TYPE_STRING, STRING_TYPE);
    putTypeVal(ANY_TYPE_STRING, ANY_TYPE);
    putTypeVal(NO_TYPE_STRING, NO_TYPE);
  }

  private static void putTypeVal(String typeString,
                                 int typeVal) {
    table.put(typeString, new Integer(typeVal));
    typeStringVec.add(typeString);
  }

  public static int getTypeVal(String typeString) {
    Integer typeVal = (Integer) table.get(typeString);

    if (typeVal == null) {
      typeVal = new Integer(typeNumber++);
      table.put(typeString, typeVal);
      typeStringVec.add(typeString);
    }

    return typeVal.intValue();
  }

  public static String getTypeString(int typeVal) {
    return (String) typeStringVec.elementAt(typeVal);
  }

  public static int getArrayBasicType(int arrayType) {

    String typeString = getTypeString(arrayType);

    if (typeString.indexOf("array::") != 0) {
      return ERROR_TYPE;
    }
    else {
      int index = typeString.lastIndexOf('$');

      if (index != -1) {
        return getBasicTypeFromString(typeString.substring(index + 1,
                typeString.length()));
      }
      else {
        return ERROR_TYPE;
      }
    }
  }

  public static int getArraySubscriptType(int arrayType) {
    String typeString = getTypeString(arrayType);

    if (typeString.indexOf("array::") != 0) {
      return ERROR_TYPE;
    }
    else {
      if (typeString.indexOf("%int%") != -1) {
        return INT_TYPE;
      }
      else if (typeString.indexOf("%char%") != -1) {
        return CHAR_TYPE;
      }
      else {
        return ERROR_TYPE;
      }
    }
  }

  public static int getArrayIntLowerBound(int arrayType) {
    String typeString = getTypeString(arrayType);

    if (typeString.indexOf("array::") != 0) {
      return Integer.MIN_VALUE;
    }
    else {
      if (typeString.indexOf("%int%") != -1) {
        int index = typeString.lastIndexOf('%');
        int rindex = typeString.indexOf("..");
        Integer lwb = new Integer(typeString.substring(index + 1, rindex));

        return lwb.intValue();
      }
      else {
        return Integer.MIN_VALUE;
      }
    }
  }

  public static int getArrayIntUpperBound(int arrayType) {
    String typeString = getTypeString(arrayType);

    if (typeString.indexOf("array::") != 0) {
      return Integer.MIN_VALUE;
    }
    else {
      if (typeString.indexOf("%int%") != -1) {
        int index = typeString.lastIndexOf("..");
        int rindex = typeString.indexOf("$$");
        Integer upb = new Integer(typeString.substring(index + 2, rindex));

        return upb.intValue();
      }
      else {
        return Integer.MIN_VALUE;
      }
    }
  }

  public static char getArrayCharLowerBound(int arrayType) {
    String typeString = getTypeString(arrayType);

    if (typeString.indexOf("array::") != 0) {
      return Character.MIN_VALUE;
    }
    else {
      if (typeString.indexOf("%char%") != -1) {
        int index = typeString.lastIndexOf('%');

        return typeString.charAt(index + 1);
      }
      else {
        return Character.MIN_VALUE;
      }
    }
  }

  public static char getArrayCharUpperBound(int arrayType) {
    String typeString = getTypeString(arrayType);

    if (typeString.indexOf("array::") != 0) {
      return Character.MIN_VALUE;
    }
    else {
      if (typeString.indexOf("%char%") != -1) {
        int index = typeString.lastIndexOf("..");

        return typeString.charAt(index + 2);
      }
      else {
        return Character.MIN_VALUE;
      }
    }
  }

  public static int getArrayLowerBoundValue(int type) {
    String typeString = getTypeString(type);
    if (typeString.indexOf("array::") != 0) {
      return Integer.MIN_VALUE;
    }
    else if (typeString.indexOf("%char%") != -1) {
      char c_lwb = getArrayCharLowerBound(type);
      return ((int)c_lwb);
    }
    else
      return getArrayIntLowerBound(type);
  }

  public static int getFunctionReturnType(int funcType) {

    String typeString = getTypeString(funcType);

    if (typeString.indexOf("func::") != 0) {
      return ERROR_TYPE;
    }
    else {
      int index = typeString.lastIndexOf('>');

      if (index != -1) {
        return getBasicTypeFromString(typeString.substring(index + 1,
                typeString.length()));
      }
      else {
        return ERROR_TYPE;
      }
    }
  }

  public static String getBasicTypeString(int type) {
    switch (type) {
      case INT_TYPE:
        return INT_TYPE_STRING;
      case CHAR_TYPE:
        return CHAR_TYPE_STRING;
      case FLOAT_TYPE:
        return FLOAT_TYPE_STRING;
      case STRING_TYPE:
        return STRING_TYPE_STRING;
      case ANY_TYPE:
        return ANY_TYPE_STRING;
      case NO_TYPE:
        return NO_TYPE_STRING;
      default:
        return ERROR_TYPE_STRING;
    }
  }

  public static String getWriteFormatString(int type) {
    switch (type) {
      case INT_TYPE:
        return INT_WFORMAT_STRING;
      case CHAR_TYPE:
        return CHAR_WFORMAT_STRING;
      case FLOAT_TYPE:
        return FLOAT_WFORMAT_STRING;
      case STRING_TYPE:
        return STRING_WFORMAT_STRING;
      default:
        return ERROR_FORMAT_STRING;
    }
  }

  public static String getReadFormatString(int type) {
    switch (type) {
      case INT_TYPE:
        return INT_RFORMAT_STRING;
      case CHAR_TYPE:
        return CHAR_RFORMAT_STRING;
      case FLOAT_TYPE:
        return FLOAT_RFORMAT_STRING;
      case STRING_TYPE:
        return STRING_RFORMAT_STRING;
      default:
        return ERROR_FORMAT_STRING;
    }
  }

  public static int getDataSize(int type) {
    switch (type) {
      case INT_TYPE:
        return INT_SIZE;
      case CHAR_TYPE:
        return CHAR_SIZE;
      case FLOAT_TYPE:
        return FLOAT_SIZE;
      default:
        return 0;
    }
  }

  public static int getBasicTypeFromString(String typeString) {
    if (typeString.compareTo(INT_TYPE_STRING) == 0) {
      return INT_TYPE;
    }
    else if (typeString.compareTo(CHAR_TYPE_STRING) == 0) {
      return CHAR_TYPE;
    }
    else if (typeString.compareTo(FLOAT_TYPE_STRING) == 0) {
      return FLOAT_TYPE;
    }
    else if (typeString.compareTo(STRING_TYPE_STRING) == 0) {
      return STRING_TYPE;
    }
    else if (typeString.compareTo(ANY_TYPE_STRING) == 0) {
      return ANY_TYPE;
    }
    else if (typeString.compareTo(NO_TYPE_STRING) == 0) {
      return NO_TYPE;
    }
    else {
      return ERROR_TYPE;
    }
  }

  public static int getResultAssignmentType(int lhsType,
                                            int rhsType) {
    if (lhsType >= NUM_BASIC_TYPES) {
      lhsType = NO_TYPE;
    }
    if (rhsType >= NUM_BASIC_TYPES) {
      rhsType = NO_TYPE;
    }

    return assignmentTable[lhsType][rhsType];
  }

  public static int getResultArithmeticType(int lhsType,
                                            int rhsType) {
    if (lhsType >= NUM_BASIC_TYPES) {
      lhsType = NO_TYPE;
    }
    if (rhsType >= NUM_BASIC_TYPES) {
      rhsType = NO_TYPE;
    }

    return arithmeticTable[lhsType][rhsType];
  }

  public static int getResultBooleanType(int lhsType,
                                         int rhsType) {
    if (lhsType >= NUM_BASIC_TYPES) {
      lhsType = NO_TYPE;
    }
    if (rhsType >= NUM_BASIC_TYPES) {
      rhsType = NO_TYPE;
    }

    return booleanTable[lhsType][rhsType];
  }

  public static int getResultComparisonType(int lhsType,
                                            int rhsType) {
    if (lhsType >= NUM_BASIC_TYPES) {
      lhsType = NO_TYPE;
    }
    if (rhsType >= NUM_BASIC_TYPES) {
      rhsType = NO_TYPE;
    }

    return comparisonTable[lhsType][rhsType];
  }
}
