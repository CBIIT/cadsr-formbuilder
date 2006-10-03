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
package org.hl7.meta.impl;

import java.util.ArrayList;

import org.hl7.meta.util.StringUtils;

/**
 * A silly little helper class for encapsulating both fixed values and domains
 * as they appear together in a string.
 * 
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
/*default*/ class FixedValuesDomains
{
  //-------------------------------------------------------------------------
  /**
   * List of fixed values.
   */
  private String[] fixedValues_;

  /**
   * List of vocabulary domains.
   */
  private String[] domains_;

  //-------------------------------------------------------------------------
  /**
   * Parses the string and saves resulting components in member variables.
   * Assumes that components are delimited by character '+' with possibly
   * extra whitespace around it.
   * 
   * @param s  the string to parse
   */
  /*default*/ FixedValuesDomains(String s)
  {
    String[] components = StringUtils.split(s, '+');
    if (components == null) return;
    
    ArrayList/*<String>*/ fvs = new ArrayList/*<String>*/();
    ArrayList/*<String>*/ doms = new ArrayList/*<String>*/();
    for (int i = 0; i < components.length; ++i)
    {
      if (isFixedValue(components[i])) fvs.add(components[i]);
      else doms.add(components[i]);
    }
    
    if (!fvs.isEmpty())
    {
      fixedValues_ = (String[])fvs.toArray(new String[fvs.size()]);
    }
    
    if (!doms.isEmpty())
    {
      domains_ = (String[])doms.toArray(new String[doms.size()]);
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the list of fixed values.
   * 
   * @return  the list of fixed values
   */
  /*default*/ String[] getFixedValues()
  {
    return fixedValues_;
  }
  
  /**
   * Returns the list of vocabulary domains.
   * 
   * @return  the list of vocabulary domains
   */
  /*default*/ String[] getDomains()
  {
    return domains_;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns a flag used to decide if the passed string is a fixed value
   * or a vocabulary code domain.  So far it seems that fixed values are
   * always all in capitals, and code domains are always mixed case.
   * 
   * @return  the flag
   */
  private static boolean isFixedValue(String s)
  {
    return StringUtils.isAllCaps(s);
  }
}
