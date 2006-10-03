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

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.Arrays;
import org.hl7.types.ANY;
import org.hl7.types.QSET;
import org.hl7.types.DSET;
import org.hl7.types.SET;
import org.hl7.types.BAG;
import org.hl7.types.LIST;
import org.hl7.types.BL;
import org.hl7.types.ST;
import org.hl7.types.INT;
import org.hl7.types.BL;
import org.hl7.types.BIN;

/** The abstract Expression for HPath. HPath is the HL7 RIM graph path 
    language, similar to xpath, but made specifically to navigate HL7 RIM 
    object graphs.

    <p>Similar to XPath, in HPath expression results are generally 
    considered collections, to which filters can be applied. That's why
    the evaluation of expressions happens with iterators.

    <p>The LALR(1) form of the grammar for HPath expressions at present is
    this:
    
    <pre>
      expression : property     { return $1; }
                 | filter       { return $1; }
		 | variable     { return $1; }
		 | parenthesis  { return $1; }
		 | literal      { return $1; }
		 ;

      property : expression "." IDENT arguments { return new Property($1, $3, $4); }
               | IDENT arguments                { return new Property(null, $1, $2}; }
	       ;

      arguments : "(" argumentlist ")" { return $2; }
               | /empty/               { return null; }
	       ;

      argumentlist : argumentlist "," expression  { $1.add($3); return $1; }
                   | expression                   { List<Expression> args = new ArrayList<Expression>(); args.add($1); return args; }
		   ;

      filter : expression "[" expression "]" { return new Filter{$1, $3}; }
             ;

      parenthesis : "(" expression ")" { return $2; }
                  ;

      variable : "$" IDENT { return new Variable{$2}; }
               ; 

      literal : INTLIT  { return $1; }
              | REALLIT { return $1; }
              | STLIT   { return $1; }
              | BLLIT   { return $1; }
              | TSLIT   { return $1; }
              | PQLIT   { return $1; }
	      ;
    </pre>

    <p>This language is implemented as an extensible language, that is, 
    one can add additional expression types simply by extending the expression
    and registering it with the base expression class. The key is that 
    extensions constructs of the language are registered before the established
    language constructs so that the extended language overrides the established
    forms. For example, if we want to add a "some ... in ... satisfies ..." form,
    we would register this form last, that way "some" would become a keyword.
 */
public abstract class Expression {

	/** A cache of parsed expressions. */
	private static final Map<String, Expression> _expressionCache = new java.util.HashMap<String, Expression>();
	
  /** Parses a string into an expression. */
  public static Expression valueOf(String expressionString) throws SyntaxError {
		Expression expression = _expressionCache.get(expressionString);
		if(expression == null) {
			String expandedExpressionString = Expression.expandMacros(expressionString);
			expression = _expressionCache.get(expandedExpressionString);
			if(expression == null) {
				expression = Expression.parse(new StaticContext(expandedExpressionString));
				if(expression == null)
					throw new NullPointerException("expression parser returned null for " + expandedExpressionString);
				_expressionCache.put(expandedExpressionString,expression);
			}
			_expressionCache.put(expressionString,expression);
		}
		return expression;
	}
	
  /** Expands macros in an expression to normalize the expression. */
  public static String expandMacros(String text) throws SyntaxError {
    return Language.instance().macros().expandMacros(text);
  }

  /** Parses a token stream into an expression. */
  protected static Expression parse(StaticContext staticContext) throws SyntaxError {
  tokens:
    while(staticContext.tokenizer().peek() != null) {
			//      System.out.println(staticContext.tokenizer().peek().toString());
      for(Language.ExpressionForm expressionForm : Language.instance().expressionForm()) {
				Expression expression = expressionForm.parse(staticContext);
				if(expression != null)
					continue tokens;
      }
      break;
    }
    return staticContext.expression();
  }

  /** Evaluates the expression, always returning an iterator, even if there is only a single result. */
  public abstract HPathIterator iterate(Object context) throws EvaluationException;

	/** Updates the data which would be returned by this expression. 
			This is an optional method, that means, not all expression constructs need to (or even reasonably can) implement this method.
			Whether or not updates are possible should be communicated with the mightAllowsUpdates predicate.
			However, the it may turn out when updating that it doesn't work after all, in which case an exception is thown.
			
			@throws UnsupportedOperationException if mightAllowUpdate is false
			@throws UpdateException if even an unforseen problem occurs during update
			@see allowsUpdate
	*/
	public void update(Object context, Object newValue) throws UpdateException, UnsupportedOperationException {	
		throw new UnsupportedOperationException();	
	}

	/** Tells if this expression might allow updates given the supplied context. The decision may not be final and it may only turn out later when uch update is actually tried if it actually could be done or not. */
	public boolean mightAllowUpdate(Object context) {	return false; }

	/** An interpretation of an expression value as a boolean.
			The rules are: 
			null is false; 
			boolean, BL, are true if true, false otherwise; 
			strings are true if length greater than zero;
			integers are true if not 0 or null;
 			collections are true if they are not empty; -- collections might have been unrolled
			everything else is true if it is not null
	*/
  public boolean effectiveBooleanValue(Object context) {
    Iterator testIterator = iterate(context);
    while(testIterator.hasNext()) {
      Object value = testIterator.next();
			if(value == null)
				return false;
      else if(value instanceof Boolean)
				return ((Boolean)value).booleanValue();
      else if(value instanceof BL)
				return ((BL)value).isTrue();
      else if(value instanceof String)
				return ((String)value).length() > 0;
      else if(value instanceof ST)
				return ((ST)value).isEmpty().isFalse();
      else if(value instanceof Integer)
				return ((Integer)value).intValue() != 0;
      else if(value instanceof Long)
				return ((Long)value).longValue() != 0;
      else if(value instanceof INT)
				return ((INT)value).isZero().isFalse();
      else if(value instanceof Collection)
				return !((Collection)value).isEmpty();
      else if(value instanceof SET)
				return ((SET)value).isEmpty().isFalse();
      else if(value instanceof LIST)
				return ((LIST)value).isEmpty().isFalse();
      else if(value instanceof BAG)
				return ((BAG)value).isEmpty().isFalse();
      else if(value instanceof ANY)
				return ((ANY)value).nonNull().isTrue();
      else
				return true;
    }
    return false;
  }

  public static HPathIterator contextIterator(Object context) { 
    if(context == null) {
      return new EmptyIterator();
    } else if(context instanceof Collection) {
      return new HPathIteratorDecorator(((Collection)context).iterator(), context);
    } else if(context.getClass().isArray()) {
      return new ArrayIterator(context);
    } else if(context instanceof BIN) {
      return new SingletonIterator(context);
    } else if(context instanceof DSET) {
      return new HPathIteratorDecorator(((DSET)context).iterator(), context);
    } else if(context instanceof LIST) {
      return new HPathIteratorDecorator(((LIST)context).iterator(), context);
    } else if(context instanceof BAG) {
      return new HPathIteratorDecorator(((BAG)context).iterator(), context);    
    } else // especially: context instanceof QSET
      return new SingletonIterator(context);
  }
}
