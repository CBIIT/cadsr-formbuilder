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
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/** Any of the Literal Expressions of HPath

  @author Gunther Schadow
  @version $Id: Literal.java,v 1.1 2006-10-03 17:38:45 marwahah Exp $
*/ 
/*package protected*/ class Literal extends Expression {
  Object _value;
	
  public static final Language.ExpressionForm FORM = new Language.ExpressionForm() {
      public Expression parse(StaticContext staticContext) throws SyntaxError {
				return Literal.parse(staticContext);
      }
    };
  
  public static Expression parse(StaticContext staticContext) throws SyntaxError {
    if(staticContext.expression() == null && staticContext.tokenizer().peek().type().isLiteral())
      return staticContext.expression(new Literal(staticContext.tokenizer().next().value()));
    else 
      return null;
  }
	
  private Literal(Object value) {
    _value = value;
  }

	/*package*/ Object getValue() {
		return _value;
	}
	
  public String toString() {
    return "Literal[" + _value + "]";
  }
  
  /** Evaluates the expression, always returning an iterator, even if there is only a single result. */
  public HPathIterator iterate(Object context) {
    return new SingletonIterator(_value);
  }  
}
