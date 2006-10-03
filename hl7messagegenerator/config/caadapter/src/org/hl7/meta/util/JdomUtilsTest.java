/* Copyright 2002 Health Level Seven, Inc. All Rights Reserved. 
 * 
 * This software is the proprietary information of Health Level Seven, Inc. 
 * Use is subject to license terms. 
 */
package org.hl7.meta.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jdom.Document;
import org.jdom.Element;

/**
 * A test suite that exercises {@link JdomUtils JdomUtils} class.
 * 
 * @author Skirmantas Kligys (skirmis@hotmail.com)
 */
public class JdomUtilsTest extends TestCase
{
  //-------------------------------------------------------------------------
  /**
   * Constructor for <code>JdomUtilsTest</code>.
   *
   * @param name  the test name
   */
  public JdomUtilsTest(String name)
  {
    super(name);
  }

  //-------------------------------------------------------------------------
  /**
   * Constructs a test suite from testXXX methods found in this class.
   * 
   * @return  the test suite
   */
  public static Test suite()
  {
    TestSuite suite = new TestSuite(JdomUtilsTest.class);
    suite.setName("JDOM utilities tests");
    return suite;
  }

  //-------------------------------------------------------------------------
  /**
   * Tests the method
   * {@link JdomUtils#getUniqueChild(org.jdom.Element, String, String)
   *   JDomUtils.getUniqueChild()}
   * on a successful search.
   * 
   * @throws ElementNotFoundException  if search fails
   * @throws MultipleElementsFoundException  if search fails
   */
  public void testGetUniqueChild1()
    throws ElementNotFoundException, MultipleElementsFoundException
  {
    Element elRoot = new Element("tag1");
    new Document(elRoot);
    elRoot.addContent(new Element("child1"));
    Element child2 = new Element("child2");
    elRoot.addContent(child2);
    elRoot.addContent(new Element("child3"));
    
    Element elFound = JdomUtils.getUniqueChild(elRoot, "child2", "/tag1");
    assertNotNull("JdomUtils.getUniqueChild() returned null", elFound);
    assertEquals("JdomUtils.getUniqueChild() returned wrong child", child2,
      elFound);
  }

  //-------------------------------------------------------------------------
  /**
   * Tests the method
   * {@link JdomUtils#getUniqueChild(org.jdom.Element, String, String)
   *   JDomUtils.getUniqueChild()}
   * on a failed search.
   * 
   * @throws MultipleElementsFoundException  if search fails unexpectedly
   */
  public void testGetUniqueChild2()
    throws MultipleElementsFoundException
  {
    Element elRoot = new Element("tag1");
    new Document(elRoot);
    elRoot.addContent(new Element("child1"));
    elRoot.addContent(new Element("child2b"));
    elRoot.addContent(new Element("child3"));
    
    try
    {
      JdomUtils.getUniqueChild(elRoot, "child2", "/tag1");
      fail("JdomUtils.getUniqueChild() should throw an ElementNotFoundException");
    }
    catch (ElementNotFoundException ex)
    {
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Tests the method
   * {@link JdomUtils#getUniqueChild(org.jdom.Element, String, String)
   *   JDomUtils.getUniqueChild()}
   * on a search with multiple matches.
   * 
   * @throws ElementNotFoundException  if search fails unexpectedly
   */
  public void testGetUniqueChild3()
    throws ElementNotFoundException
  {
    Element elRoot = new Element("tag1");
    new Document(elRoot);
    elRoot.addContent(new Element("child1"));
    elRoot.addContent(new Element("child2"));
    elRoot.addContent(new Element("child3"));
    elRoot.addContent(new Element("child2"));
    
    try
    {
      JdomUtils.getUniqueChild(elRoot, "child2", "/tag1");
      fail("JdomUtils.getUniqueChild() should throw a MultipleElementsFoundException");
    }
    catch (MultipleElementsFoundException ex)
    {
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Tests the method
   * {@link JdomUtils#getUniqueChildByAttribute(org.jdom.Element, String,
   *   String, String, String) JDomUtils.getUniqueChildByAttribute()}
   * on a successful search, purely by tag name.
   * 
   * @throws ElementNotFoundException  if search fails
   * @throws MultipleElementsFoundException  if search fails
   */
  public void testGetUniqueChildByAttribute1()
    throws ElementNotFoundException, MultipleElementsFoundException
  {
    Element elRoot = new Element("tag1");
    new Document(elRoot);
    elRoot.addContent(new Element("child1"));
    Element child2 = new Element("child2");
    child2.setAttribute("attr", "value");
    elRoot.addContent(child2);
    elRoot.addContent(new Element("child3"));
    
    Element elFound = JdomUtils.getUniqueChildByAttribute(elRoot, "child2",
      "attr", "value", "/tag1");
    assertNotNull("JdomUtils.getUniqueChildByAttribute() returned null",
      elFound);
    assertEquals("JdomUtils.getUniqueChildByAttribute() returned wrong child",
      child2, elFound);
  }

  //-------------------------------------------------------------------------
  /**
   * Tests the method
   * {@link JdomUtils#getUniqueChildByAttribute(org.jdom.Element, String,
   *   String, String, String) JDomUtils.getUniqueChildByAttribute()}
   * on a successful search, purely by attribute.
   * 
   * @throws ElementNotFoundException  if search fails
   * @throws MultipleElementsFoundException  if search fails
   */
  public void testGetUniqueChildByAttribute2()
    throws ElementNotFoundException, MultipleElementsFoundException
  {
    Element elRoot = new Element("tag1");
    new Document(elRoot);

    Element child1 = new Element("child2");
    child1.setAttribute("attr", "anotherValue");
    elRoot.addContent(child1);

    Element child2 = new Element("child2");
    child2.setAttribute("attr", "value");
    elRoot.addContent(child2);

    elRoot.addContent(new Element("child2"));
    
    Element elFound = JdomUtils.getUniqueChildByAttribute(elRoot, "child2",
      "attr", "value", "/tag1");
    assertNotNull("JdomUtils.getUniqueChildByAttribute() returned null",
      elFound);
    assertEquals("JdomUtils.getUniqueChildByAttribute() returned wrong child",
      child2, elFound);
  }

  //-------------------------------------------------------------------------
  /**
   * Tests the method
   * {@link JdomUtils#getUniqueChildByAttribute(org.jdom.Element, String,
   *   String, String, String) JDomUtils.getUniqueChildByAttribute()}
   * on a failed search.
   * 
   * @throws MultipleElementsFoundException  if search fails unexpectedly
   */
  public void testGetUniqueChildByAttribute3()
    throws MultipleElementsFoundException
  {
    Element elRoot = new Element("tag1");
    new Document(elRoot);
    
    Element child1 = new Element("child1");
    child1.setAttribute("attr", "value");
    elRoot.addContent(child1);
    
    Element child2 = new Element("child2");
    child2.setAttribute("attr", "anotherValue");
    elRoot.addContent(child2);

    elRoot.addContent(new Element("child2"));
    
    try
    {
      JdomUtils.getUniqueChildByAttribute(elRoot, "child2", "attr", "value",
        "/tag1");
      fail("JdomUtils.getUniqueChild() should throw an ElementNotFoundException");
    }
    catch (ElementNotFoundException ex)
    {
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Tests the method
   * {@link JdomUtils#getUniqueChildByAttribute(org.jdom.Element, String,
   *   String, String, String) JDomUtils.getUniqueChildByAttribute()}
   * on a search with multiple matches.
   * 
   * @throws ElementNotFoundException  if search fails unexpectedly
   */
  public void testGetUniqueChildByAttribute4()
    throws ElementNotFoundException
  {
    Element elRoot = new Element("tag1");
    new Document(elRoot);
    
    Element child1 = new Element("child2");
    child1.setAttribute("attr", "value");
    elRoot.addContent(child1);
    
    Element child2 = new Element("child2");
    child2.setAttribute("attr", "anotherValue");
    elRoot.addContent(child2);

    elRoot.addContent(new Element("child2"));
    
    Element child4 = new Element("child2");
    child4.setAttribute("attr", "value");
    elRoot.addContent(child4);
    
    try
    {
      JdomUtils.getUniqueChildByAttribute(elRoot, "child2", "attr", "value",
        "/tag1");
      fail("JdomUtils.getUniqueChildByAttribute() should throw a MultipleElementsFoundException");
    }
    catch (MultipleElementsFoundException ex)
    {
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Tests the method
   * {@link JdomUtils#getOptionalChild(org.jdom.Element, String, String)
   *   JDomUtils.getOptionalChild()}
   * on a successful search.
   * 
   * @throws MultipleElementsFoundException  if search fails
   */
  public void testGetOptionalChild1()
    throws MultipleElementsFoundException
  {
    Element elRoot = new Element("tag1");
    new Document(elRoot);
    elRoot.addContent(new Element("child1"));
    Element child2 = new Element("child2");
    elRoot.addContent(child2);
    elRoot.addContent(new Element("child3"));
    
    Element elFound = JdomUtils.getOptionalChild(elRoot, "child2", "/tag1");
    assertNotNull("JdomUtils.getOptionalChild() returned null", elFound);
    assertEquals("JdomUtils.getOptionalChild() returned wrong child", child2,
      elFound);
  }

  //-------------------------------------------------------------------------
  /**
   * Tests the method
   * {@link JdomUtils#getOptionalChild(org.jdom.Element, String, String)
   *   JDomUtils.getOptionalChild()}
   * on a failed search.
   * 
   * @throws MultipleElementsFoundException  if search fails unexpctedly
   */
  public void testGetOptionalChild2()
    throws MultipleElementsFoundException
  {
    Element elRoot = new Element("tag1");
    new Document(elRoot);
    elRoot.addContent(new Element("child1"));
    elRoot.addContent(new Element("child2b"));
    elRoot.addContent(new Element("child3"));
    
    Element elFound = JdomUtils.getOptionalChild(elRoot, "child2", "/tag1");
    assertNull("JdomUtils.getOptionalChild() returned null", elFound);
  }

  //-------------------------------------------------------------------------
  /**
   * Tests the method
   * {@link JdomUtils#getOptionalChild(org.jdom.Element, String, String)
   *   JDomUtils.getOptionalChild()}
   * on a search with multiple matches.
   */
  public void testGetOptionalChild3()
  {
    Element elRoot = new Element("tag1");
    new Document(elRoot);
    elRoot.addContent(new Element("child1"));
    elRoot.addContent(new Element("child2"));
    elRoot.addContent(new Element("child3"));
    elRoot.addContent(new Element("child2"));
    
    try
    {
      JdomUtils.getOptionalChild(elRoot, "child2", "/tag1");
      fail("JdomUtils.getOptionalChild() should throw a MultipleElementsFoundException");
    }
    catch (MultipleElementsFoundException ex)
    {
    }
  }

  //-----------------------------------------------------------------------
  /**
   * Runs the test suite in console mode.
   * 
   * @param args  command line parameters
   */
  public static void main(String args[])
  {
    TestRunner.run(suite());
  }
}
