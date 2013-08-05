/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.formbuilder.ejb;
import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;

public interface FormBuilderLocalHome extends EJBLocalHome  {
  FormBuilderLocal create() throws CreateException;
}