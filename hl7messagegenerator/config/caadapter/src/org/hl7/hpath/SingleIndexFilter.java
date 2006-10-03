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
import org.hl7.types.INT;

/** A Filter Expression which chooses only a single item by index. */
/*package protected*/ class SingleIndexFilter extends Expression {
  private Expression _previousExpression;
  private int _index;
	
  /*package*/ SingleIndexFilter(Expression previousExpression, int index) {
    _previousExpression = previousExpression;
    _index = index;
  }
  
  public String toString() {
    return "SingleIndexFilter[" + _previousExpression + " " + _index + "]";
  }

  /** Evaluates the expression, always returning an iterator, even if there is only a single result. */
  public HPathIterator iterate(Object context) {
		if(context == null)
			return EmptyIterator.INSTANCE;
		else {
			HPathIterator previousIterator = _previousExpression.iterate(context);
			Object resultElement = null;
			for(int i = 0; i < _index; i++) {
				if(previousIterator.hasNext())
					resultElement = previousIterator.next();
				else
					return EmptyIterator.INSTANCE;
			}
			if(resultElement == null)
				return null;
			else
				return new SingletonIterator(resultElement);
		}
  }  
}
