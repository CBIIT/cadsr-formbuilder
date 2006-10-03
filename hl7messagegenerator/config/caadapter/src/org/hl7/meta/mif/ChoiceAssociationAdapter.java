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
*
* $Id: ChoiceAssociationAdapter.java,v 1.1 2006-10-03 17:39:01 marwahah Exp $
*/
package org.hl7.meta.mif;

import hl7OrgV3.mif.*;
import hl7OrgV3.mif.impl.*;
import java.io.*;
import org.hl7.meta.*;
import org.hl7.util.NullIterator;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Peter Hendler, Gunther Schadow
 */
public class ChoiceAssociationAdapter extends AssociationAdapter implements org.hl7.meta.ChoiceAssociation {
  
  private Map<String,org.hl7.meta.Association> _branchMap = new HashMap<String,org.hl7.meta.Association>();

  /*package*/ ChoiceAssociationAdapter(CloneClassAdapter owner, hl7OrgV3.mif.AssociationEndWithClass mifThing, String sortKey) throws LoaderException {
    super(owner, mifThing, sortKey);
    setupBranches();
  }
  
  private void setupBranches() throws LoaderException {
    hl7OrgV3.mif.AssociationEndSpecialization mifBranches[] = _mifThing.getParticipantClassSpecializationArray();
    for(int i = 0; i < mifBranches.length; i++) {
      String traversalName = mifBranches[i].getTraversalName();
      _branchMap.put(traversalName, new ChoiceBranchAssociationAdapter(this, traversalName, mifBranches[i].getClassName()));
    }
  }
  
  public Iterator<org.hl7.meta.Association> iterateChoices(){
    return _branchMap.values().iterator();
  }
}
