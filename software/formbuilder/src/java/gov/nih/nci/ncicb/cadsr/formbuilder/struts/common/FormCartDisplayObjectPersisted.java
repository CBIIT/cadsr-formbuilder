/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

public class FormCartDisplayObjectPersisted extends FormCartDisplayObject{
	protected boolean isPersisted;
	
	public FormCartDisplayObjectPersisted(FormCartDisplayObject displayObject, boolean inDB)
	{
		super(displayObject);
		isPersisted = inDB;
	}
	
	public boolean getIsPersisted() {
		return isPersisted;
	}

	public void setIsPersisted(boolean isPersisted) {
		this.isPersisted = isPersisted;
	}
}
