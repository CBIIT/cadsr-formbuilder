package org.hl7.xml;

public class Operator {

  private static String name_;

  private Operator(String op) { name_=op; }

  public static String evalOp(String op) {
    if (op.equals(I)) return I;
    else if (op.equals(E)) return E;
    else if (op.equals(A)) return A;
    else if (op.equals(H)) return H;
    else if (op.equals(P)) return P;
    else return null;
  }
  
  public static final String E = "E";
  public static final String A = "A";
  public static final String P = "P";
  public static final String H = "H";
  public static final String I = "I";
}
