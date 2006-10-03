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

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;

/**
 * Two lookup tables to help with looking up real RIM association names.
 * The first table maps a source RIM class name and an association ID to
 * the RIM association name.  The second table maps a source RIM class
 * name and a target RIM class name to the set of association names defined
 * between them.
 *
 * @author Skirmantas Kligys
 */
public class RimAssociationLookups
{
  //-------------------------------------------------------------------------
  /** Maps a RIM class to its parent top level RIM class, e.g.
   * PublicHealthCase -> Act. */
  private final Map<String, String> classToTop_ =
    new HashMap<String, String>();
  /** Maps a source RIM class name and an association ID to
   * the real RIM association name. */
  private final Map<TwoStrings, String> sourceIdToName_ =
    new HashMap<TwoStrings, String>();
  /** Maps a pair of (source RIM class, target RIM class) to a set
   * of valid associations between them. */
  private final Map<TwoStrings, Set<TwoStrings>> validAssociations_ =
    new HashMap<TwoStrings, Set<TwoStrings>>();

  //-------------------------------------------------------------------------
  /** A simple class to hold Two strings. Cannot hold nulls. */
  private static class TwoStrings
  {
    //.......................................................................
    /** First string. */
    private final String first_;
    /** Second string. */
    private final String second_;

    //.......................................................................
    /**
     * The only constructor.
     *
     * @param first  first string
     * @param second  second string
     */
    /*default*/ TwoStrings(String first, String second)
    {
      if (first == null || second == null)
      {
        throw new NullPointerException();
      }
      first_ = first;
      second_ = second;
    }

    //.......................................................................
    public String getFirst() { return first_; }
    public String getSecond() { return second_; }

    //.......................................................................
    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object ob2)
    {
      if (ob2 == null || !(ob2 instanceof TwoStrings))
      {
        return false;
      }

      TwoStrings ts2 = (TwoStrings)ob2;
      return first_.equals(ts2.first_) && second_.equals(ts2.second_);
    }

    //.......................................................................
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
      return first_.hashCode() + 37 * first_.hashCode();
    }

    //.......................................................................
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return "(" + first_ + ", " + second_ + ")";
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Clears the lookup table.
   */
  public void clear()
  {
    sourceIdToName_.clear();
    validAssociations_.clear();
  }

  //-------------------------------------------------------------------------
  public void addClass(String id, String superType)
  {
    classToTop_.put(id, superType);
  }

  //-------------------------------------------------------------------------
  public void postprocessClasses()
  {
    for (Iterator<Map.Entry<String, String>> it =
      classToTop_.entrySet().iterator(); it.hasNext(); )
    {
      Map.Entry<String, String> entry = it.next();
      String key = entry.getKey();
      String value = entry.getValue();

      String parent = classToTop_.get(value);
      while (parent != null)
      {
        value = parent;
        parent = classToTop_.get(value);
      }

      if (!value.equals(entry.getValue()))
      {
        classToTop_.put(key, value);
      }
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Adds a new association and its inverse to the lookup table.
   *
   * @param sourceClass  RIM source class
   * @param destinationClass  RIM destination class
   * @param id  association ID in rim.xml and *.hmd
   * @param forwardName  RIM association name in forward direction
   * @param inverseName  RIM association name in inverse direction
   */
  public void addAssociation(String sourceClass, String destinationClass,
    String id, String forwardName, String inverseName)
  {
    sourceIdToName_.put(new TwoStrings(sourceClass, id), forwardName);
    sourceIdToName_.put(new TwoStrings(destinationClass, id), inverseName);

    {
      TwoStrings forwardKey = new TwoStrings(sourceClass, destinationClass);
      Set<TwoStrings> forwardSet = validAssociations_.get(forwardKey);
      if (forwardSet == null)
      {
        forwardSet = new HashSet<TwoStrings>();
        validAssociations_.put(forwardKey, forwardSet);
      }
      forwardSet.add(new TwoStrings(forwardName, inverseName));
    }

    {
      TwoStrings inverseKey = new TwoStrings(destinationClass, sourceClass);
      Set<TwoStrings> inverseSet = validAssociations_.get(inverseKey);
      if (inverseSet == null)
      {
        inverseSet = new HashSet<TwoStrings>();
        validAssociations_.put(inverseKey, inverseSet);
      }
      inverseSet.add(new TwoStrings(inverseName, forwardName));
    }
  }

  //-------------------------------------------------------------------------
  public String lookupAssociation(String sourceClass, String id)
    throws NoSuchElementException
  {
    TwoStrings key = new TwoStrings(sourceClass, id);
    String value = sourceIdToName_.get(key);
    if (value == null)
    {
      throw new NoSuchElementException(sourceClass + ", " + id);
    }
    else return value;
  }

  //-------------------------------------------------------------------------
  public String correctAssociationName(String sourceClass, String targetClass,
    String associationName) throws NoSuchElementException
  {
    String sourceClassTop = classToTop_.get(sourceClass);
    if (sourceClassTop != null) sourceClass = sourceClassTop;

    String targetClassTop = classToTop_.get(targetClass);
    if (targetClassTop != null) targetClass = targetClassTop;

    TwoStrings forwardKey = new TwoStrings(sourceClass, targetClass);
    Set<TwoStrings> forwardSet = validAssociations_.get(forwardKey);
    if (forwardSet == null)
    {
      throw new NoSuchElementException(sourceClass + ", " + targetClass);
    }

    for (Iterator<TwoStrings> it = forwardSet.iterator(); it.hasNext(); )
    {
      TwoStrings ts = it.next();

      if (ts.getFirst().equals(associationName)) return associationName;
    }

    // Could not find forward association name; try inverse names.
    TwoStrings inverseKey = new TwoStrings(targetClass, sourceClass);
    Set<TwoStrings> inverseSet = validAssociations_.get(inverseKey);
    if (inverseSet == null)
    {
      throw new NoSuchElementException(targetClass + ", " + sourceClass);
    }

    for (Iterator<TwoStrings> it = inverseSet.iterator(); it.hasNext(); )
    {
      TwoStrings ts = it.next();

      if (ts.getFirst().equals(associationName)) return ts.getSecond();
    }

    throw new NoSuchElementException(targetClass + ", " + sourceClass +
      ", " + associationName);
  }

  //-------------------------------------------------------------------------
  /**
   * Dumps the lookup table for inspection.
   *
   * @param out  output stream
   */
  public void dump(PrintStream out)
  {
    out.println("Class to top:");
    for (Iterator<Map.Entry<String, String>> it =
      classToTop_.entrySet().iterator(); it.hasNext(); )
    {
      Map.Entry<String, String> entry = it.next();

      out.println(entry.getKey() + " -> " + entry.getValue());
    }

    out.println();
    out.println("Source ID maps:");
    for (Iterator<Map.Entry<TwoStrings, String>> it =
      sourceIdToName_.entrySet().iterator(); it.hasNext(); )
    {
      Map.Entry<TwoStrings, String> entry = it.next();

      out.println(entry.getKey() + " -> " + entry.getValue());
    }

    out.println();
    out.println("Source target valid associations:");
    for (Iterator<Map.Entry<TwoStrings, Set<TwoStrings>>> it =
      validAssociations_.entrySet().iterator(); it.hasNext(); )
    {
      Map.Entry<TwoStrings, Set<TwoStrings>> entry = it.next();

      out.println(entry.getKey() + " -> " + entry.getValue());
    }
  }
}
