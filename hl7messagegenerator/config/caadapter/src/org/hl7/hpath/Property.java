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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** The Property Expression of HPath can follow any expression after the property operator. 

@author Gunther Schadow
@version $Id: Property.java,v 1.1 2006-10-03 17:38:45 marwahah Exp $

TODO: Support RIM special properties e.g. consumable instead of participation[typeCode.implies(CS:ParticipationType:Consumable)]

TODO: Support defauly values for properties which exist in Role and also in the player Entity
*/ 
/*package protected*/ class Property extends Expression {
  Expression _previousExpression = null;
  List<Expression> _arguments = null;
  String _name; // this name is intern()-ed, so we can compare by identity
  boolean _isPropertyOfSource; // if the property is from the source of the iterator or from the next element

  public static final Language.ExpressionForm FORM = new Language.ExpressionForm() {
      public Expression parse(StaticContext staticContext) throws SyntaxError {
				return Property.parse(staticContext);
      }
    };
  
  public static Expression parse(StaticContext staticContext) throws SyntaxError {
    Token token = staticContext.tokenizer().peek();
    switch(token.type()) {
    case IDENT:
      if(staticContext.expression() == null) {
				staticContext.tokenizer().next();
				List<Expression> arguments = parseArguments(staticContext);
				return staticContext.expression(makePropertyOrConversion(null, token.string(), arguments, false));
      } else 
				throw new SyntaxError("in " + staticContext.expressionString() + " at '" + token.string() + "'");
    case CPROPIDENT:
      if(staticContext.expression() == null) {
				staticContext.tokenizer().next();
				List<Expression> arguments = parseArguments(staticContext);
				return staticContext.expression(makePropertyOrConversion(null, (String)token.value(), arguments, true));
      } else 
				throw new SyntaxError("in " + staticContext.expressionString() + " at '" + token.string() + "'");
    case PROP:
      Expression previousExpression = staticContext.expression();
      if(previousExpression != null) {
				staticContext.tokenizer().next();
				Token ident = staticContext.tokenizer().peek();
				switch(ident.type()) {
				case IDENT: {
					staticContext.tokenizer().next();
					List<Expression> arguments = parseArguments(staticContext);
					return staticContext.expression(makePropertyOrConversion(staticContext.expression(), ident.string(), arguments, false));	  
				}
				case CPROPIDENT: {
					staticContext.tokenizer().next();
					List<Expression> arguments = parseArguments(staticContext);
					return staticContext.expression(makePropertyOrConversion(staticContext.expression(), (String)ident.value(), arguments, true));
				}
				default:
					throw new SyntaxError("ident or @ident expected in " + staticContext.expressionString() + " at '" + ident.string() + "'");
				}
      } else 
				throw new SyntaxError("in " + staticContext.expressionString() + " at '" + token.string() + "'");
    default:
      return null;
    }
  }

  private static List<Expression> parseArguments(StaticContext staticContext) {
    if(staticContext.tokenizer().peek().type() == TokenType.LPAR) {
      Expression savedStaticContextExpression = staticContext.expression();
      staticContext.tokenizer().next();
      List<Expression> arguments = new ArrayList<Expression>();
      boolean isFresh = true;
      while(staticContext.tokenizer().peek().type() != TokenType.RPAR) {
				if(isFresh)
					isFresh = false;
				else
					staticContext.tokenizer().next(TokenType.COMMA);
				staticContext.expression(null);
				arguments.add(Expression.parse(staticContext));
      }
      staticContext.tokenizer().next(TokenType.RPAR);
      staticContext.expression(savedStaticContextExpression);
      return arguments;
    } else 
      return null;
  }
  
	private static Expression makePropertyOrConversion(Expression previousExpression, String name, List<Expression> arguments, boolean isPropertyOfSource) {		
		Conversion conversion = null;
		if(! isPropertyOfSource)
			conversion = Conversion.instance(previousExpression, name, arguments);
		if(conversion != null)
			return conversion;
		else
			return new Property(previousExpression, name, arguments, isPropertyOfSource);
	}

  private Property(Expression previousExpression, String name, List<Expression> arguments, boolean isPropertyOfSource) {
    _name = name.intern();
    _previousExpression = previousExpression;
    _arguments = arguments;
    _isPropertyOfSource = isPropertyOfSource;
  }

  public String toString() {
    return "Property[" + _previousExpression + " " + (_isPropertyOfSource ? "@" : "" ) + _name + " " + _arguments + "]";
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

  /*package*/ static Object[] evaluateArguments(List<Expression> arguments, Object context) {
		if(context == null)
			context = EmptyIterator.INSTANCE;

    Object argvals[] = new Object[arguments.size()];
    for(int i = 0; i < argvals.length; i++) {
      Iterator iter = arguments.get(i).iterate(context);
      if(iter.hasNext()) {
				argvals[i] = iter.next();
				// XXX: what if the iterator has more than one element?
      } 
      else {
				argvals[i] = null;
				// XXX: what if we need HL7 nulls here?
      }
    }
    return argvals;
  }
  
  class InnerIterator extends IteratorDecorator implements HPathIterator {
    private HPathIterator _expressionIterator = EmptyIterator.INSTANCE;
    private Object _nextElement;
    private Object _source;
    private Object _originOfSource;
    private boolean _propertyOfSourceUsed = false;

    InnerIterator(HPathIterator parentIterator) {
      super(parentIterator);
      seek();
    }

		public boolean hasOnlySingleValue() {
			return (super.hasOnlySingleValue() || _isPropertyOfSource) && _expressionIterator.hasOnlySingleValue();
		}
    
    private void seek() {
      if(_expressionIterator != null && _expressionIterator.hasNext()) {
				_nextElement = _expressionIterator.next();
      } 
      else if(_isPropertyOfSource) {
				if(!_propertyOfSourceUsed) {
					_propertyOfSourceUsed = true;
					_originOfSource = ((HPathIterator)parentIterator()).source();
					_expressionIterator = valueIterator(_originOfSource);
					if(_expressionIterator != null && _expressionIterator.hasNext())
						_nextElement = _expressionIterator.next();
				}	else
					_nextElement = null;
				// Don't think this can actually work. Although it seems that it does.
				// Problem is that the source property will change when the previous
				// iterator changes to of a new source. So, there would have to be
				// something like nextSource() to get to that.

      } 
      else {
				while(super.hasNext()) {
					_originOfSource = super.next();
					_expressionIterator = valueIterator(_originOfSource);
	  
					if(_expressionIterator != null && _expressionIterator.hasNext()) {
						_nextElement = _expressionIterator.next();
						return;
					}
					// loop and try the next item on the previous iterator
				}
				_nextElement = null;
      }
    }

    public boolean hasNext() {
      return _nextElement != null;
    }
    
    public Object next() {
      Object result = _nextElement;
      seek();
      return result;  
    } 

    private HPathIterator valueIterator(Object context) {
			if(context == null)
				return EmptyIterator.INSTANCE;

      Class clazz = context.getClass();
      
      if(_arguments == null) { // this part we know works, so won't mess it up
				Method method = null;
				
				try { // check for name()
					method = clazz.getMethod(_name);
				} catch(NoSuchMethodException ex) { /*FALLTHROUGH*/ }
	
				try { // check for getName()
					method = clazz.getMethod("get" + _name.substring(0,1).toUpperCase() + _name.substring(1));
				} catch(NoSuchMethodException ex) { /*FALLTHROUGH*/ }
	
				if(method != null) {
					try {
						_source = method.invoke(context);
						return contextIterator(_source);
					} catch(IllegalAccessException ex) {
						throw new EvaluationException(Property.this, context, method.toString(), ex);
					} catch(InvocationTargetException ex) {
						throw new EvaluationException(Property.this, context, method.toString(), ex);
					}
				} else if(method == null && (_name == "item" || _name == "part" || _name == "element")) {
					// this is the implicit property for anonymous collections, i.e., Collection<Collectio<T>> to 
					// distinguish the outer from the inner part. If a property returns a collection, it will always
					// be turned into an iterator by the contextIterator function. But then, if the elements are
					// collections again, we can use this to handle.
	  
					// instead of asking for any particular interface, Collection or Iterable or whatever, we
					// simply try to find an iterator() method, and if we do we proceed, else we say no such
					// property.
					try {
						method = clazz.getMethod("iterator");
						_source = context;
						if(Iterator.class.isAssignableFrom(method.getReturnType())) {
							Iterator iter = (Iterator)method.invoke(context);
							if(iter instanceof HPathIterator)
								return (HPathIterator)iter;
							else
								return new HPathIteratorDecorator(iter, context);
						}
	    
						// FALLTHROUGH if the method doesn't meet this requirement
						// we have returned here right away because we do not want the context iterator.
					} catch(NoSuchMethodException ex) {	
						/*FALLTHROUGH*/	
					} catch(IllegalAccessException ex) {
						throw new EvaluationException(Property.this, context, method.toString(), ex);
					} catch(InvocationTargetException ex) {
						throw new EvaluationException(Property.this, context, method.toString(), ex);
					}
				}
	
				// FALLTHROUGH
				throw new PropertyNotFoundException(Property.this, context, _name + " in " + clazz);
	
				// TODO: handle fields!
	
      } else { // with arguments we have to dynamically find the best matching method
	
				Method methods[] = clazz.getMethods();
				List<Method> candidates = new ArrayList<Method>();
				Object[] argvals = null; // defer evaluation to the very latest possible point
	
				for(int i = 0; i<methods.length; i++) {
					Method method = methods[i];
					if(method.getName().equals(_name) || method.getName().equals("get" + _name.substring(0,1).toUpperCase() + _name.substring(1))) {
						Class parameterTypes[] = method.getParameterTypes();
						if(parameterTypes.length == _arguments.size()) { // o.k. matching number of arguments 
							if(argvals == null) // deferred evaluation now
								argvals = evaluateArguments(_arguments, context); // arguments are evaluated in the parent context
							for(int argi = 0; argi < parameterTypes.length; argi++) // now check the types 
								if(! parameterTypes[argi].isAssignableFrom(argvals[argi].getClass()))
									continue; // next method
							// if we made it until here:
							candidates.add(method);
						}
					}
				}
	
				//if(candidates.size() > 1)
				//throw new EvaluationException("ambiguous method for property: " + _name + " in " + context + " and arguments " + argsToString(argvals) + ": " + candidates);
				if(candidates.size() == 0)
					throw new PropertyNotFoundException(Property.this, context, _name + "(" + argsToString(argvals) + ")");
	
				Method method = candidates.get(0);
				
				try {
					_source = method.invoke(context, argvals);
					return contextIterator(_source);
				} catch(IllegalAccessException ex) {
					throw new EvaluationException(Property.this, context, method.toString(), ex);
				} catch(InvocationTargetException ex) {
					throw new EvaluationException(Property.this, context, method.toString(), ex);
				}
      }      
    }
    
    public Object source() { return _source; } 
  }

	/** Let's say as a start, the property must be the first one (i.e., no previous expression step, and it must have no arguments. */
	public boolean mightAllowUpdate(Object context) {
		return _previousExpression == null && _arguments == null;
	}

	public void update(Object context, Object newValue) throws UpdateException {
		if(!mightAllowUpdate(context))
			throw new UnsupportedOperationException(this.toString());
		/*FALLTHROUGH*/
		
    // Find the method, this is simple, it must be a setter only, and it can't be a funky property such as item or part, etc.
		Class clazz = context.getClass();
		List<Method> candidates = new ArrayList<Method>();
    for(Method method : clazz.getMethods()) {
			if((method.getName().equals(_name) || 
					method.getName().equals("set" + _name.substring(0,1).toUpperCase() + _name.substring(1)))
				 &&  ((method.getModifiers() & Modifier.STATIC) == 0)) {
				Class parameterTypes[] = method.getParameterTypes();
				if(parameterTypes.length == 1 && parameterTypes[0].isAssignableFrom(newValue.getClass()))
					candidates.add(method);
			}
		}      
    
    if(candidates.size() > 1)
			throw new UpdateException(this, context, "ambiguous property " + _name + " matching " + newValue);
    if(candidates.size() == 0)
			throw new PropertyNotFoundException(this, context, _name + " matching " + newValue);
		
    Method method = candidates.get(0);
		
    try {
			method.invoke(context, newValue);
		} catch(IllegalAccessException ex) {
			throw new EvaluationException(Property.this, context, method.toString(), ex);
		} catch(InvocationTargetException ex) {
			throw new EvaluationException(Property.this, context, method.toString(), ex);
		}
	}

	public static class PropertyNotFoundException extends EvaluationException {
		/*package*/ PropertyNotFoundException(Expression expression, Object context, String propertyName) { super(expression, context, propertyName); }
		/*package*/ PropertyNotFoundException(Expression expression, Object context, String propertyName, Throwable ex) { super(expression, context, propertyName, ex); }		
	}


  /*package*/ String argsToString(Iterable args) {
    if(args == null)
      return "";
    else {
      StringBuffer sb = new StringBuffer();
      boolean isFresh = true;
      sb.append("(");
      for(Object arg : args) {
				if(isFresh)
					isFresh = false;
				else
					sb.append(", ");
				if(arg != null)
					sb.append(arg.toString());
				else
					sb.append("null");
      }
      sb.append(")");
      return sb.toString();
    }
  }

  /*package*/ String argsToString(Object[] args) {
    if(args == null)
      return "";
    else {
      StringBuffer sb = new StringBuffer();
      boolean isFresh = true;
      sb.append("(");
      for(Object arg : args) {
				if(isFresh)
					isFresh = false;
				else
					sb.append(", ");
				if(arg != null)
					sb.append(arg.toString());
				else
					sb.append("null");
      }
      sb.append(")");
      return sb.toString();
    }
  }
}
