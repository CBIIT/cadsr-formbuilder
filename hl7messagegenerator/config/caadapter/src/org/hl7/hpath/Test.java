package org.hl7.hpath;

import java.util.Iterator;
import java.util.Arrays;

public class Test {
  public static void main(String args[]) throws Exception {
    for(String exprString : Arrays.asList(args)) {
      System.out.println(exprString);
      Expression expr = Expression.valueOf(exprString);
      System.out.println(expr.toString());
      Iterator iter = expr.iterate(new Foo());
      while(iter.hasNext()) {
				System.out.println(exprString + " = " + iter.next());
      }
    }
  }
}

class Foo {
  Bar _boo = null;
  public Bar boo() { return _boo; }
  public Foo() { }
  public Foo(Bar boo) { _boo = boo; }
  public int one() { return 1;  }
  public int[] two() { return new int[] { 2, 3 }; }
  public Bar bar() { return new Bar(); }
  public Foo glue(Bar bar) {
    return new Foo(bar);
  }
}

class Bar {
  public int one() { return 3; }
  public Foo[] foo() { return new Foo[] {new Foo(), new Foo(this), new Foo() }; }
}
