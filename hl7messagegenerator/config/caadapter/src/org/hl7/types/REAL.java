/* The contents of this file are subject to the Health Level-7 Public
* License Version 1.0 (the "License"); you may not use this file
* except in compliance with the License. You may obtain a copy of the
* License at http://www.hl7.org/HPL/
*
* Software distributed under the License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
* the License for the specific language governing rights and
* limitations under the License.
*
* The Original Code is all this file.
*
* The Initial Developer of the Original Code is .
* Portions created by Initial Developer are Copyright (C) 2002-2004 
* Health Level Seven, Inc. All Rights Reserved.
*
* Contributor(s): 
*/
package org.hl7.types;

public interface REAL extends QTY.diff, Numeric {
  /** This is a constrained form of QTY.plus(QTY.diff) that we defined
      such that we can tell the compiler that our return type is
      actually REAL if the compiler knows that the argument is REAL.  
  */
  REAL   plus(REAL that);
  REAL   plus(int that, INT precision);
  REAL   plus(long that, INT precision);
  REAL   plus(float that, INT precision);
  REAL   plus(double that, INT precision);

  /** This is a constrained form of both QTY.minus(QTY.diff) and
      QTY.minus(QTY) that we defined such that we can tell the
      compiler that our return type is actually REAL if the compiler
      knows that the argument is REAL. 
  */
  REAL   minus(REAL that);
  REAL   minusReverse(int that, INT precision);
  REAL   minusReverse(long that, INT precision);
  REAL   minusReverse(float that, INT precision);
  REAL   minusReverse(double that, INT precision);

  
  /** This is a constrained form of QTY.diff.times(QTY.diff) that we
      defined such that we can tell the compiler that our return type
      is actually REAL if the compiler knows that the argument is REAL.  
  */
  REAL   times(REAL that);
  REAL   times(int that, INT precision);
  REAL   times(long that, INT precision);
  REAL   times(float that, INT precision);
  REAL   times(double that, INT precision);

  /** The neutral element of multiplication. */
  BL     isOne();

  /** The inverse element with respect to multiplication. */
  REAL   inverted();

  /** This is a constrained form of QTY.diff.dividedBy(QTY.diff) that
      we defined such that we can tell the compiler that our return
      type is actually REAL if the compiler knows that the argument is
      REAL.
  */
  REAL   dividedBy(REAL that);
  REAL   dividedByReverse(int that, INT precision);
  REAL   dividedByReverse(long that, INT precision);
  REAL   dividedByReverse(float that, INT precision);
  REAL   dividedByReverse(double that, INT precision);

  REAL   power(REAL that);
  REAL   powerReverse(int that, INT precision);
  REAL   powerReverse(long that, INT precision);
  REAL   powerReverse(float that, INT precision);
  REAL   powerReverse(double that, INT precision);

  REAL   negated();

  /* More math that we need in practice (even some in the definition
     of the data types. */
  /** Natural logarithm. */
  REAL   log();
  /** Power of e. */
  REAL   exp();
  REAL   floor();
  String toString();
  int intValue();
  long longValue();
  float floatValue();
  double doubleValue();

  INT precision();
}
