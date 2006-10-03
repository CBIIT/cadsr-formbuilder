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
import java.util.Map;
import java.util.HashMap;
import org.hl7.types.TS;
import org.hl7.types.ST;
import org.hl7.types.ValueFactory;
import java.text.ParsePosition;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;

/** A Conversion expression form can be used to turn business objects from and to string representations, for example.
		But any type conversions are possible. For the parser, conversions are syntactically indistinguishable from properties.
		However, the names of conversions is statically defined in the Language, and hence, 
		every token that looks like a property but which has the name of a conversion will be interpreted as a conversiom.

		@author Gunther Schadow
		@version $Id: Conversion.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
*/ 
public abstract class Conversion<S,T> extends Expression {
  Expression _previousExpression = null;
  List<Expression> _arguments = null;

  public interface Definition {
		public String name();
		public Conversion instantiate(Expression previousExpression, List<Expression> arguments);
	}

	public abstract String name();

	public abstract T convertForward(S object, Object... args);

	public abstract S convertBackward(T object, Object... args);

	protected Conversion(Expression previousExpression, List<Expression> arguments) {
    _previousExpression = previousExpression;
    _arguments = arguments;
  }

	private static final Map<String, Definition> _DEFINITIONS = new HashMap<String, Definition>();
	public static void define(Definition definition) {
		_DEFINITIONS.put(definition.name(), definition);
	}
	public static Conversion instance(Expression previousExpression, String name, List<Expression> arguments) {
		Definition definition = _DEFINITIONS.get(name);
		if(definition == null)
			return null;
		else
			return definition.instantiate(previousExpression, arguments);
	}

  public String toString() {
    return "Conversion[" + _previousExpression + " " + name() + " " + _arguments + "]";
  }
  
  /** Evaluates the expression, always returning an iterator, even if there is only a single result. */
  public HPathIterator iterate(Object context) {
    HPathIterator previousIterator;
		if(context == null)
			return EmptyIterator.INSTANCE;

    if(_previousExpression == null)
      previousIterator = new SingletonIterator(context);
    else
      previousIterator = _previousExpression.iterate(context);

		return new InnerIterator(previousIterator);
  }

	private static final Object EMPTY_ARGUMENTS[] = new Object[0];

  class InnerIterator extends IteratorDecorator implements HPathIterator {
    private Object _nextElement;
    private Object _source;
		private boolean _defaultNullPending = true; // make sure that at least one converted item is returned before hasNext is false;

    InnerIterator(HPathIterator parentIterator) {
      super(parentIterator);
    }

		public boolean hasOnlySingleValue() {
			return super.hasOnlySingleValue();
		}

		public boolean hasNext() {
			return _defaultNullPending || super.hasNext();
		}
    
    public Object next() {
			S context;
			if(super.hasNext())
				context = (S)super.next();
			else if(_defaultNullPending)
				context = null;
			else
				throw new NoSuchElementException();
	
			_defaultNullPending = false;

			Object[] argvals = EMPTY_ARGUMENTS;
			if(_arguments != null && _arguments.size() > 0)
				argvals = Property.evaluateArguments(_arguments, context); // arguments are evaluated in the parent context

      return convertForward(context, argvals);
    }
    
    public Object source() { return _source; } 
  }

	/** A conversion should always be 2-ways, but whether we can actually update depends on the previous step. */
	public boolean mightAllowUpdate(Object context) {
		return _previousExpression == null || _previousExpression.mightAllowUpdate(context);
	}

	public void update(Object context, Object newValue) throws UpdateException {
		if(!mightAllowUpdate(context))
			throw new UnsupportedOperationException(this.toString());
		/*FALLTHROUGH*/

		Object[] argvals = EMPTY_ARGUMENTS;
		if(_arguments != null && _arguments.size() > 0)
			argvals = Property.evaluateArguments(_arguments, context); // arguments are evaluated in the parent context
		
		_previousExpression.update(context, convertBackward((T)newValue, argvals));
	}
}
