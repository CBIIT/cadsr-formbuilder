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

/** The Filter Expression will select only those values that meet the filter criterion . */
/*package protected*/ class Filter extends Expression {
  private Expression _previousExpression;
  private Expression _criterionExpression;

  public static final Language.ExpressionForm FORM = new Language.ExpressionForm() {
      public Expression parse(StaticContext staticContext) throws SyntaxError {
				return Filter.parse(staticContext);
      }
    };
  
  public static Expression parse(StaticContext staticContext) throws SyntaxError {
    Expression previousExpression = staticContext.expression();
    if(previousExpression != null && staticContext.tokenizer().peek().type() == TokenType.LBRACK) {
      staticContext.tokenizer().next();
      staticContext.expression(null);
      Expression criterionExpression = Expression.parse(staticContext);
      staticContext.tokenizer().next(TokenType.RBRACK);
			if(criterionExpression instanceof Literal) {
				Object value = ((Literal)criterionExpression).getValue();
				if(value instanceof INT)
					return staticContext.expression(new SingleIndexFilter(previousExpression, ((INT)value).intValue()));
				else 
					throw new SyntaxError("literal criterion must be an integer, but was: " + value);
			} else 					
				return staticContext.expression(new Filter(previousExpression, criterionExpression));
    } else 
      return null;
  }

  private Filter(Expression previousExpression, Expression criterionExpression) {
    _previousExpression = previousExpression;
    _criterionExpression = criterionExpression;
  }
  
  public String toString() {
    return "Filter[" + _previousExpression + " " + _criterionExpression + "]";
  }

  /** Evaluates the expression, always returning an iterator, even if there is only a single result. */
  public HPathIterator iterate(Object context) {
		if(context == null)
			return EmptyIterator.INSTANCE;
		else
			return new InnerIterator(_previousExpression.iterate(context));
	}  
  
  class InnerIterator extends IteratorDecorator implements HPathIterator {
    private Object _nextElement;
    private Object _source;
    private Object _originOfSource;
    private Property _propertyOfOriginWhoseValueWasSource; // FIXME: not set properly

    InnerIterator(HPathIterator parentIterator) {
      super(parentIterator);
      seek();
    }

    private void seek() {
      while(super.hasNext()) {
				_nextElement = super.next();
				if(_criterionExpression.effectiveBooleanValue(_nextElement))
					return;
      }
      _nextElement = null;
    }
   
    public boolean hasNext() {
      return _nextElement != null;
    }
    
    public Object next() {
      Object result = _nextElement;
      seek();
      return result;  
    } 

    public Object source() { return _source; } 
  }
}
