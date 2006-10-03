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

import java.util.List;

import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.DSET;
import org.hl7.types.EN;
import org.hl7.types.ENXP;
import org.hl7.types.IVL;
import org.hl7.types.ST;
import org.hl7.types.TS;

public class ENimpl extends LISTjuListAdapter<ENXP> implements EN
{
  private final DSET<CS> _use;
  private final IVL<TS> _validTime;

  protected ENimpl(List<ENXP> list, DSET<CS> use, IVL<TS> validTime) {
    super(list);

    if (use == null)
      this._use = DSETnull.NI;
    else
      this._use = use;

    if (validTime == null)
      this._validTime = IVLnull.NI;
    else
      this._validTime = validTime;
  }

  /** Get the a null value according to the null flavor code.
   */
  public static EN valueOf(List<ENXP> list, DSET<CS> use, IVL<TS> validTime) {
    return (list == null) ? (EN)ENnull.NI :
      (EN)new ENimpl(list, use, validTime);
  }

  public static EN valueOf(List<ENXP> list, DSET<CS> use) {
    return (list == null) ? (EN)ENnull.NI :
      (EN)new ENimpl(list, use, (IVL<TS>)IVLnull.NI);
  }

  public static EN valueOf(List<ENXP> list, IVL<TS> validTime) {
    return (list == null) ? (EN)ENnull.NI :
      (EN)new ENimpl(list, (DSET<CS>)DSETnull.NI, validTime);
  }

  public static EN valueOf(List<ENXP> list) {
    return (list == null) ? (EN)ENnull.NI :
      (EN)new ENimpl(list, (DSET<CS>)DSETnull.NI, (IVL<TS>)IVLnull.NI);
  }
  
  public BL equal(ANY x) {
    if(!(x instanceof ENimpl)) {
      throw new IllegalArgumentException();
    }
    ENimpl that = (ENimpl)x;
    return ( super.equal(that).
      and(this._use.equal(that.use())).
      and(this._validTime.equal(that.validTime())) );
  }

  public DSET<CS> use() { return _use; }
  public IVL<TS> validTime() { return _validTime; }
  public ST formatted() { throw new UnsupportedOperationException(); }

}
