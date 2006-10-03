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
* Portions created by Initial Developer are Copyright (C) 2002-2004 
* Health Level Seven, Inc. All Rights Reserved.
*
* Contributor(s): 
*/
package org.hl7.hibernate;

import java.util.Set;
import org.hl7.types.SET;
import org.hl7.types.impl.SETjuSetAdapter;
import org.hl7.types.impl.SETnull;

import java.lang.reflect.Method;

/**
 * Access a SET property and wrap/unwrap the underlying set.
 *
 * @author Gunther Schadow
 */
public class WrappingSetAccessor extends WrappingAccessor {
  
  public final class WrappingSetter extends WrappingAccessor.WrappingSetter {
    private WrappingSetter(Class clazz, Method method, String propertyName) {
      super(clazz, method, propertyName);
    }

    protected Object wrap(Object value) { 
      if(value == null)
	return SETnull.NI;
      else 
	return SETjuSetAdapter._hibernateWrap((Set)value);
    }
  }

  public final class UnwrappingGetter extends WrappingAccessor.UnwrappingGetter {
    private UnwrappingGetter(Class clazz, Method method, String propertyName) {
      super(clazz, method, propertyName);
    }

    protected Object unwrap(Object value) { 
      if(value == null)
	return null;
      else if(value instanceof SET) {
	SET setValue = (SET)value;
	if(setValue.isNull().isTrue())
	  return null;
	else if(setValue instanceof SETjuSetAdapter)
	  return ((SETjuSetAdapter)setValue)._hibernateUnwrap();
      }

      throw new Error("class not supported here: " + value.getClass());
    }
  }

  protected WrappingSetter newWrappingSetter(Class clazz, Method method, String propertyName) {
    return new WrappingSetter(clazz, method, propertyName);
  }
  
  protected UnwrappingGetter newUnwrappingGetter(Class clazz, Method method, String propertyName) {
    return new UnwrappingGetter(clazz, method, propertyName);
  }
}
