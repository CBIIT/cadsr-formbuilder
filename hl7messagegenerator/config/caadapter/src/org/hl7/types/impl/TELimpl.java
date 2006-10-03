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

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.hl7.types.CS;
import org.hl7.types.DSET;
import org.hl7.types.QSET;
import org.hl7.types.TEL;
import org.hl7.types.TS;
import org.hl7.types.URL;
import org.hl7.types.enums.TelecommunicationAddressUse;

public final class TELimpl extends URLjnURLAdapter implements TEL {
  
  QSET<TS> _validTime;
  DSET<CS> _use;
  
  private TELimpl(QSET<TS> validtime, DSET<CS> use, String data){
    super(data);
    // specifically don't require that validtime or use != null  
    // because it's checking this on the way out
    this._validTime = validtime;   
    this._use = use;      
  } 

  public static  TELimpl valueOf(String address, String useType, QSET<TS> timeSet) {
    EnumSet<TelecommunicationAddressUse> telecomUseSet =  EnumSet.noneOf(TelecommunicationAddressUse.class);
    DSET<CS> useCSSET = null;
		
    if(useType != null) {
      StringTokenizer useTypeTokens = new StringTokenizer(useType);
      while (useTypeTokens.hasMoreTokens())  {
				TelecommunicationAddressUse telecomUse = TelecommunicationAddressUse.valueOf(STjlStringAdapter.valueOf(useTypeTokens.nextToken()));
				telecomUseSet.add(telecomUse);
      }
      useCSSET = SETjuSetAdapter.valueOf(telecomUseSet);
    }
		
    return new TELimpl(timeSet,useCSSET,address);
  }
  
  public String spaceDelimitedListOfUseCode(){
    StringBuffer spaceDelimitedListofUses = new StringBuffer("");
    Iterator itCS=_use.iterator();
    while( itCS.hasNext() ) {
      CS useEntry = (CS) itCS.next();
      spaceDelimitedListofUses.append(useEntry.toString()+ " ");
    }
    
    return spaceDelimitedListofUses.toString();
  }
  
  // and now for a simple one to use for our message
  // which doesn't specify "valid time" or "use", or even "schema" 
  // we must make parameters to feed our private constructor
  // even if they are empty of real info
  public static TELimpl valueOf(String data){	  
    QSET<TS> tsSN = QSETnull.NI;
    DSET<CS> csSN = DSETnull.NI;	 	
    
    return new TELimpl(tsSN, csSN, data); 	
  } //the simple valueOF method
	
  public static TELimpl valueOf(URL data){	  
    QSET<TS> tsSN = QSETnull.NI;
    DSET<CS> csSN = DSETnull.NI;	
    
    return new TELimpl(tsSN, csSN, data.address().toString()); 		
  } //the simple valueOF method
    
  public static TELimpl valueOf(URL data, CS cs) {	  
    QSET<TS> tsSN = QSETnull.NI;
    HashSet uSet = new HashSet();
    uSet.add(cs);
    DSET<CS> useCSSET = SETjuSetAdapter.valueOf(uSet);
    
    return new TELimpl(tsSN, useCSSET,data.address().toString()); 		
  } //the simple valueOF method

  public static TELimpl valueOf(QSET<TS> timeSet, DSET<CS> useSet, String data)  {
    return new TELimpl(timeSet, useSet, data);
  } // simplest if you have the types already
  
  /* marked for deletion, no longer needed

  public static TELimpl valueOf(String use, String useCodesystem, String data){
    QSET<TS> tsSN = QSETnull.NI;
    
    CS useCS = CSimpl.valueOf(use, useCodesystem);
    HashSet uSet = new HashSet();
    uSet.add(useCS);
    DSET<CS> useCSSET = SETjuSetAdapter.valueOf(uSet);
    
    return new TELimpl(tsSN, useCSSET, data);
  }	
  */

  public QSET<TS> validTime(){
    if(this._validTime != null){
      return this._validTime;
    } else {
      return QSETnull.NI;
    } //if else
  }
  
  public DSET<CS> use(){
    if(this._use != null){
      return this._use;
    } else {
      return DSETnull.NI;
    }
  }
  
  // we don't have to do equal()  we only care if the address is equal, and 
  // that is already taken care of by the super.equal() which we inherit
    
}// class
