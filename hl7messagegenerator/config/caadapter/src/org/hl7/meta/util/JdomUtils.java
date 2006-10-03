/* Copyright 2002 Health Level Seven, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Health Level Seven, Inc.
 * Use is subject to license terms.
 */
package org.hl7.meta.util;

import java.util.Iterator;

import org.jdom.Element;

/**
 * A utility class that contains common functions dealing with JDOM.
 *
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class JdomUtils
{
  //-------------------------------------------------------------------------
  /**
   * Constructor is private, all methods are static; utility class pattern.
   */
  private JdomUtils()
  {
  }

  //-------------------------------------------------------------------------
  /**
   * Finds and returns a unique immediate child with the given tag.  Throws
   * an exception if no such children are found, or more than one of them
   * are found.
   *
   * @param elParent   parent element
   * @param tag        child tag to look for
   * @param path       parent element XPath (for exceptions only)
   *
   * @return  the child element found
   *
   * @throws  ElementNotFoundException  If no such child was found
   * @throws  MultipleElementsFoundException  If multiple children with
   *                 given tag were found
   */
  public static Element getUniqueChild(Element elParent, String tag,
    String path)
    throws ElementNotFoundException, MultipleElementsFoundException
  {
    Iterator/*<Element>*/ it = elParent.getChildren(tag).iterator();

    if (!it.hasNext())
    {
      throw new ElementNotFoundException(path + '/' + tag);
    }

    Element el = (Element)it.next();

    if (it.hasNext())
    {
      throw new MultipleElementsFoundException(path + '/' + tag);
    }

    return el;
  }

  //-------------------------------------------------------------------------
  /**
   * Recursively finds and returns a unique child within the given element.  The search
   * will include the element passed as well as *all* of it's children.  Throws
   * an exception if no such children are found, or more than one of them
   * are found.
   *
   * @param elParent   parent element
   * @param tag        child tag to look for
   *
   * @return  the child element found
   *
   * @throws  ElementNotFoundException  If no such child was found
   * @throws  MultipleElementsFoundException  If multiple children with
   *                 given tag were found
   */
  public static Element getUniqueChildRecursive(Element elParent, String tag)
    throws ElementNotFoundException, MultipleElementsFoundException
  {
    Element el = null;
    Iterator i = elParent.getDescendants();

      while(i.hasNext()){
          Object item = i.next();
          if(item instanceof Element){
            if(((Element)item).getName().equalsIgnoreCase(tag)){
                if(el != null)
                    throw new MultipleElementsFoundException("parent/tag = " + elParent.getName() + "/" + tag);
                else
                    el =(Element)item;
            }
          }
      }

    if(el==null) throw new ElementNotFoundException("parent/tag = " + elParent.getName() + "/" + tag);

    return el;
  }

  //-------------------------------------------------------------------------
  /**
   * Finds and returns a unique immediate child with the given tag and the
   * given value in a given attribute.  In other words, evaluates a dynamic
   * XPath expression:
   * <pre>
   * &lt;path&gt;/&lt;tag&gt;[@&lt;attributeName&gt;='&lt;AttributeValue&gt;']
   * </pre>
   * Throws an exception if no such children are found, or more than one of
   * them are found.
   *
   * @param elParent        parent element
   * @param tag             child tag to look for
   * @param attributeName   child attribute name to look for
   * @param attributeValue  child attribute value to look for
   * @param path            parent element XPath (for exceptions only)
   *
   * @return  the child element found
   *
   * @throws  ElementNotFoundException  If no such child was found
   * @throws  MultipleElementsFoundException  If multiple children with
   *                 given tag and given attribute value were found
   */
  public static Element getUniqueChildByAttribute(Element elParent, String tag,
    String attributeName, String attributeValue, String path)
    throws ElementNotFoundException, MultipleElementsFoundException
  {
    int i = 1;
    Element elFound = null;
    for (Iterator/*<Element>*/ it = elParent.getChildren(tag).iterator();
      it.hasNext(); ++i)
    {
      Element el = (Element)it.next();

      String value = el.getAttributeValue(attributeName);
      if (value != null && value.equals(attributeValue))
      {
        if (elFound != null)
        {
          throw new MultipleElementsFoundException(path + '/' + tag + "[@" +
             attributeName + "='" + attributeValue + "']");
        }
        else elFound = el;
      }
    }

    if (elFound == null)
    {
      throw new ElementNotFoundException(path + '/' + tag);
    }

    return elFound;
  }

  //-------------------------------------------------------------------------
  /**
   * Finds and returns a unique immediate child with the given tag, if it
   * exists; otherwise returns <code>null</code>.  Throws
   * an exception if more than one of such children are found.
   *
   * @param elParent   parent element
   * @param tag        child tag to look for
   * @param path       parent element XPath (for exceptions only)
   *
   * @return  the child element found, or <code>null</code> if not found
   *
   * @throws  MultipleElementsFoundException  If multiple children with
   *                 given tag were found
   */
  public static Element getOptionalChild(Element elParent, String tag,
    String path)
    throws MultipleElementsFoundException
  {
    Iterator/*<Element>*/ it = elParent.getChildren(tag).iterator();

    if (!it.hasNext()) return null;

    Element el = (Element)it.next();

    if (it.hasNext())
    {
      throw new MultipleElementsFoundException(path + '/' + tag);
    }

    return el;
  }
}
