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
 * The Initial Developer of the Original Code is Gunther Schadow.
 * Portions created by Initial Developer are Copyright (C) 2002-2005 
 * Health Level Seven, Inc. All Rights Reserved.
 *
 * Contributor(s): 
 */
package org.hl7.hpath;

import java.util.Iterator;

/** A decorator to turn any Iterator into a (limited function) HPathIterator.

    @author Gunther Schadow
    @version $Id: HPathIteratorDecorator.java,v 1.1 2006-10-03 17:38:45 marwahah Exp $
*/
/*package protected*/ class HPathIteratorDecorator extends IteratorDecorator implements HPathIterator {
  private Object _source;

  /*package*/ HPathIteratorDecorator(Iterator parent, Object source) {
    super(parent);
    _source = source;
  }

	public boolean hasOnlySingleValue() { 
		return false;
	}

  public Object source() {
    return _source;
  }

  public void replaceSourceInOrigin(Object newValue) {
    throw new UnsupportedOperationException();
  }
}
