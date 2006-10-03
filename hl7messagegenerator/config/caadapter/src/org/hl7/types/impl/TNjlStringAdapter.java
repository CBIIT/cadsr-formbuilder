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
package org.hl7.types.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.DSET;
import org.hl7.types.ENXP;
import org.hl7.types.INT;
import org.hl7.types.IVL;
import org.hl7.types.LIST;
import org.hl7.types.ST;
import org.hl7.types.TN;
import org.hl7.types.TS;

public class TNjlStringAdapter extends ANYimpl implements TN
{
  String _data;

  protected TNjlStringAdapter(String data)
    throws IllegalArgumentException
  {
    super(null);
	  if (data == null)
	  {
		  throw new IllegalArgumentException();
	  }
	  else
	  {
		  this._data = data;
	  }
  }

  public static TN valueOf(String data) {
	  if (data == null)
	  // FIXME: should this sort of convenience be one level up?
	  {
		  return TNnull.NA;
	  }
	  else
	  {
		  return new TNjlStringAdapter(data);
	  }
  }

  public BL equal(ANY that) {
    if(that instanceof TNjlStringAdapter){
      return BLimpl.valueOf(this._data
			    .equals(((TNjlStringAdapter)that)._data));
    }
    else if (!(that instanceof TN))
	{
		return BLimpl.FALSE;
	}
	else
	{
		return BLimpl.valueOf(this._data.equals(that.toString()));
	}
  }
  
  // XXX: a crazy hack to get around strong type checking
  private static final Object emptySet = EmptySet.VALUE;

  // Interface EN implemented.
  public DSET<CS> use() {
    return (DSET<CS>)emptySet;
  }

  public IVL<TS> validTime() {
    return (IVL<TS>)emptySet;
  }

  public ST formatted() {
    return STjlStringAdapter.valueOf(_data);
  }

  // Interface LIST<ENXP> implemented.
  public ENXP head() {
    return ENXPimpl.valueOf(_data);
  }

  public LIST<ENXP> tail() {
    return LISTnull.NIL;
  }

  public BL isEmpty() {
    return BLimpl.FALSE;
  }

  public BL nonEmpty() {
    return BLimpl.TRUE;
  }

  public INT length() {
    return INTlongAdapter.ONE;
  }

  public Iterator<ENXP> iterator() {
    return new Iterator<ENXP>()
    {
      private boolean _beforeFirst = true;

      public boolean hasNext() {
        return _beforeFirst;
      }

      public ENXP next() {
		  if (_beforeFirst)
		  {
			  _beforeFirst = false;
			  return ENXPimpl.valueOf(_data);
		  }
		  else
		  {
			  throw new NoSuchElementException();
		  }
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

	public int hashCode()
	{
		return (_data != null ? _data.hashCode() : 0);
	}
}
