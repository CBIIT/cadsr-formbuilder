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

import java.util.Iterator;

public interface QSET<T extends QTY> extends SET<T> {
  QSET<T> union(QSET<T> otherset);
  QSET<T> except(T element);
  QSET<T> except(QSET<T> otherset);
  QSET<T> intersection(QSET<T> otherset);
  IVL<T>  hull();
  IVL<T>  nextTo(T element);
  IVL<T>  nextAfter(T element);
  QSET<T> periodicHull(QSET<T> otherset);

  // GS/MC: this is commented out because it is a particular challenging
  // operation which has very little practical value besides being used
  // in an assertion about the definition of the periodic hull.
  //
  // BL      interleaves(QSET<T> otherset);

	/** An iterator over partitions or elements of the QSET such that this qset would be 
			the union of the partitions.
			
			<p>FIXME: this hasn't been sorted out yet. Something like this is clearly
			needed, but it's not clear whether to return partitions or subterms	
	**/
	Iterator<ANY> iterator();
}

