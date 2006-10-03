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
package org.hl7.xml.validator;

import org.hl7.meta.Association;
import org.hl7.meta.Feature;
import org.hl7.xml.builder.AssociationBuilderException;
import org.hl7.xml.builder.FeatureException;

/**
 * Exception that is thrown when an feature cardinality specified in HMD
 * and links present in RIM object are incompatible.  May happen in the
 * following cases:
 * <ol>
 *   <li>HMD specifies cardinality 1..1 or 1..*, but link is not present
 *     in RIM object.</li>
 *   <li>HMD specifies cardinality 0..1 or 0..*, but multiple links are
 *     present in RIM object.</li>
 * </ol>
 * 
 * @author Eric Chen
 */
public class FeatureCardinalityException extends FeatureException
{
  //-------------------------------------------------------------------------
  /** String representation of the number of links present in RIM object. */
  private final String actualCardinality_;

  //-------------------------------------------------------------------------
  public FeatureCardinalityException(Feature feature,
    String actualCardinality)
  {
    super(feature, FeatureCardinalityException.createMessage(feature, actualCardinality));

    actualCardinality_ = actualCardinality;
  }

  //-------------------------------------------------------------------------
  protected static String createMessage(Feature feature,
    String actualCardinality)
  {
    //return FeatureException.createMessage(feature) +
    return   "HMD specifies cardinality " + feature.getCardinality() +
      ", RIM object has " + actualCardinality;
  }

  //-------------------------------------------------------------------------
  /**
   * @return Returns the actual cardinality.
   */
  public String getActualCardinality() { return actualCardinality_; }
}
