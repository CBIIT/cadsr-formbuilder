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


public interface PQ extends QTY.diff {

  /** Some number that together with the unit() represent this physical quantity. The number 
      should never be separated from the unit and no computations can reasonably be made with
      the value() property alone, because the number can be anything depending on the unit.
      
      @deprecated use the toREAL method instead or at least use canonical().value()
   */
  REAL  value();
  CS    unit();

  PQ    canonical();

  /** Return a REAL if the quantity is dimensionless. Otherwise throw an exception. Even though
      this method might through an IllegalArgumentException, it should be used instead of the
      value() property to fetch the real value that results from any computation that would have
      cancelled out the units.
  */
  REAL toREAL();

  PQ  minus(PQ x);
  PQ  plus(PQ x);
  PQ  negated();
  PQ  times(REAL x);
  PQ  times(PQ x);
  PQ  inverted();
  PQ  power(INT x);
  PQ  dividedBy(PQ x);
  PQ  dividedBy(REAL x);

  SET<PQR> translation();
  
  PQ expressedIn(PQ that);
}
