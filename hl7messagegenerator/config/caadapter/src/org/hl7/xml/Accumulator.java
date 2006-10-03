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

package org.hl7.xml;

import org.hl7.types.impl.*;
import org.hl7.types.*;

/** A thread-local resource used for parsing QSET representations in XML 

  @MC todo: For instance, Operator should be its own class.
*/
public class Accumulator {

  private static ThreadLocal<Accumulator> _instance = new ThreadLocal();
  
  public static Accumulator getInstance() {
    Accumulator accum = _instance.get();
    if(accum == null) {
      accum = new Accumulator();
      _instance.set(accum);
    }
    return accum;
  }

  private QSET _value = null;
  private boolean _shouldSet = true;
        
  public boolean shouldSet() { return _shouldSet; }

  public void setShouldSet(boolean bool) { _shouldSet=bool; }
  
  public void setValue(QSET q) { _value = q; }

  public void clear() { _value = null; }

  public QSET getValue() { return _value; }
  
  public boolean isEmpty() { return _value == null; }
}
