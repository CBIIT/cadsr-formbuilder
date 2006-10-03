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
* $Id: AssociationAdapter.java,v 1.1 2006-10-03 17:39:01 marwahah Exp $
*/
package org.hl7.meta.mif;

import org.hl7.meta.*;
import org.hl7.util.ForwardReferenceTool;

/**
 * @author Gunther Schadow, Peter Hendler
 */
public class AssociationAdapter extends FeatureAdapter implements org.hl7.meta.Association {
  protected hl7OrgV3.mif.AssociationEndWithClass _mifThing;

  protected CloneClass _target;
  protected boolean isReference = false;

  /*package*/
  static AssociationAdapter make(CloneClassAdapter owner, hl7OrgV3.mif.SerializedAssociationEnd mifThing) throws LoaderException {
		hl7OrgV3.mif.AssociationEndWithClass mifThingTarget = mifThing.getTargetConnection();
		
        hl7OrgV3.mif.AssociationEndSpecialization mifBranches[] = mifThingTarget.getParticipantClassSpecializationArray();
		if(mifBranches != null && mifBranches.length > 0)
			return new ChoiceAssociationAdapter(owner, mifThingTarget, mifThingTarget.getSortKey());
		else if(mifThingTarget.getParticipantClass().isSetCommonModelElementRef())
			return new CmetAssociationAdapter(owner, mifThingTarget, mifThingTarget.getSortKey());
		else
//            return new AssociationAdapter(owner, mifThingTarget, mifThing.getSortKey());
            return new AssociationAdapter(owner, mifThingTarget, mifThingTarget.getSortKey());
        }

  protected AssociationAdapter(CloneClassAdapter owner, hl7OrgV3.mif.AssociationEndWithClass mifThing, String sortKey) throws LoaderException {
    super(owner, mifThing, sortKey);
    _mifThing = mifThing;
    setupTarget();
  }

  /*package*/ AssociationAdapter copyTo(CloneClassAdapter newOwner) {
    return (AssociationAdapter)super.copyTo(newOwner);
  }

	private final ForwardReferenceTool.ReplacerFactory<CloneClass> REPLACER_FACTORY = new ForwardReferenceTool.ReplacerFactory<CloneClass>() {
		public ForwardReferenceTool.Replacer<CloneClass> createReplacer() {
			return new ForwardReferenceTool.Replacer<CloneClass>() { 
				public void replace(CloneClass target) { _target = target; }
			};
		}
	};
  
  protected void setupTarget() throws LoaderException {
    hl7OrgV3.mif.ClassOrReference mifThing = _mifThing.getParticipantClass();
    if(mifThing.isSetClass1())
      _target = new CloneClassAdapter(_owner.getMessageType(), mifThing.getClass1());
    else if(mifThing.isSetReference()) {
      setReference(true);
      _target = _owner.getMessageType().lookupCloneClass(mifThing.getReference().getName(), REPLACER_FACTORY);
    } else if(mifThing.isSetCommonModelElementRef()) {
      // XXX: what if the CMET's entry point is a choice? -- it might just still work
      String cmetName = mifThing.getCommonModelElementRef().getName();

      MessageTypeAdapter messageType = _owner.getMessageType();
      _target = MessageTypeLoaderAdapter.getInstance().loadCmet(cmetName).getRootClass();
      messageType.addCloneClass(cmetName,_target);
      //System.err.println("CMET:"+cmetName+":"+((CloneClassAdapter)_target).getMessageType()+":"+_owner.getMessageType());
    } else
      throw new Error("mif thing has neither class nor reference nor CMET: " + mifThing);
  }
  
  public String getName() {
    return _mifThing.getName();
  }

  // FIXME: remove from interface
  // @deprecated replaced by isRecursive
  public MetSource getMetSource() {
    // Actually we need it to identify  REUSED or RECURSIVE 
    throw new Error("don't use this method, will be scrapped");
  }

    public boolean isReference()
    {
        return isReference;
    }

    protected void setReference(boolean isReference)
    {
        this.isReference = isReference;
    }

    public Conformance getConformance() {
    throw new UnsupportedOperationException();
  }
  
  public boolean isMandatory() {
    return _mifThing.getIsMandatory();
  }
  
  public String getRimClass() {
    return _mifThing.getDerivationSupplierArray(0).getClassName();
  }

  public String getRimPropertyName() {
    return _mifThing.getDerivationSupplierArray(0).getAssociationEndName();
  }
  
  public Cardinality getCardinality() {
    try {
      String min = String.valueOf(_mifThing.getMinimumMultiplicity());
      String max = _mifThing.getMaximumMultiplicity().toString();
      return Cardinality.create(min + ".." + max);
    } catch (RuntimeException e) {
      System.err.println("WARNING: bad cardinality " + this.getName());
      return Cardinality.create("0..*");
    }
  }

  public String getConstraint() {
    throw new UnsupportedOperationException();
  }

  public CloneClass getTarget() {
    if(_target == null)
      throw new Error("target is null in " + _mifThing);
    return _target;
  }
}
