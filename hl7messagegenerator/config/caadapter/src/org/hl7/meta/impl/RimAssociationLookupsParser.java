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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parser loads rim.xml via SAX, extracts nodes matching the following
 * XPath:
 * <pre>
 * /RIM/body/div1[@id='assoc-rimdiv']/assoc
 * </pre>
 * and creates the following object: (source RIM class, assoc ID) -&gt;
 * real association name.
 *
 * @author Skirmantas Kligys
 */
public class RimAssociationLookupsParser extends DefaultHandler
{
  //-------------------------------------------------------------------------
  /** The context stack. */
  private final Stack<Boolean> context_ = new Stack<Boolean>();
  /** The resulting lookup table. */
  private final RimAssociationLookups lookups_ =
    new RimAssociationLookups();

  //-------------------------------------------------------------------------
  /**
   * Runs the SAX parser on the given file.  Returns the loaded lookup table.
   *
   * @param inputFile  name of rim.xml file to parse.
   * @return  the lookup table.
   * @throws IOException  if file cannot be found or cannot be read
   * @throws ParserConfigurationException  if a SAX parser cannot be created
   * @throws SAXException  if there is a parsing error
   */
  public RimAssociationLookups parse(String inputFile)
    throws IOException, ParserConfigurationException, SAXException
  {
    File in = new File(inputFile);

    if (!in.exists())
    {
      throw new FileNotFoundException(inputFile);
    }
    if (!in.canRead())
    {
      throw new IOException("Cannot read: " + inputFile);
    }

    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setNamespaceAware(true);
    SAXParser parser = factory.newSAXParser();
    parser.parse(in, this);

    return lookups_;
  }

  //-------------------------------------------------------------------------
  /**
   * @see org.xml.sax.ContentHandler#startDocument()
   */
  public void startDocument() throws SAXException
  {
    super.startDocument();
    context_.clear();
    context_.push(Boolean.TRUE);
    lookups_.clear();
  }

  //-------------------------------------------------------------------------
  /**
   * @see org.xml.sax.ContentHandler#endDocument()
   */
  public void endDocument() throws SAXException
  {
    super.endDocument();
    context_.clear();
    lookups_.postprocessClasses();
  }

  //-------------------------------------------------------------------------
  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
   * java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String namespaceURI, String localName,
    String qName, Attributes atts) throws SAXException
  {
    Boolean needsProcessing = (Boolean)context_.peek();
    if (!needsProcessing.booleanValue())
    {
      context_.push(Boolean.FALSE);
      return;
    }

    switch (context_.size())
    {
      case 1:
      {
        boolean process = localName.equals("RIM");
        context_.push(Boolean.valueOf(process));
        break;
      }

      case 2:
      {
        boolean process = localName.equals("body");
        context_.push(Boolean.valueOf(process));
        break;
      }

      case 3:
      {
        String id = atts.getValue("", "id");
        boolean process = localName.equals("div1") && (id != null) &&
          (id.equals("assoc-rimdiv") || id.equals("cls-rimdiv"));
        context_.push(Boolean.valueOf(process));
        break;
      }

      case 4:
      {
        boolean process = localName.equals("div2");
        context_.push(Boolean.valueOf(process));
        if (localName.equals("assoc"))
        {
          handleAssociation(atts);
        }
        break;
      }

      case 5:
      {
        context_.push(Boolean.FALSE);
        if (localName.equals("cls"))
        {
          handleClass(atts);
        }
        break;
      }

      default:
      {
        context_.push(Boolean.FALSE);
        break;
      }
    }
  }

  //-------------------------------------------------------------------------
  /**
   * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
   * java.lang.String, java.lang.String)
   */
  public void endElement(String namespaceURI, String localName,
    String qName) throws SAXException
  {
    context_.pop();
  }

  //-------------------------------------------------------------------------
  /**
   * Handles a single node in the nodeset:
   * <pre>
   * /RIM/body/div1[@id='assoc-rimdiv']/assoc
   * </pre>
   *
   * @param atts  XML attributes of the node
   */
  private void handleAssociation(Attributes atts)
  {
    String id = atts.getValue("", "id");
    String forwardName = atts.getValue("", "fwdName");
    String inverseName = atts.getValue("", "invName");

    StringTokenizer st = new StringTokenizer(id, "-");
    if (st.countTokens() >= 3)
    {
      String sourceClass = st.nextToken();
      st.nextToken();
      String destinationClass = st.nextToken();
      lookups_.addAssociation(sourceClass, destinationClass, id, forwardName,
        inverseName);
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Handles a single node in the nodeset:
   * <pre>
   * /RIM/body/div1[@id='assoc-rimdiv']/assoc
   * </pre>
   *
   * @param atts  XML attributes of the node
   */
  private void handleClass(Attributes atts)
  {
    String id = atts.getValue("", "id");
    if (id.endsWith("-cls"))
    {
      id = id.substring(0, id.length() - "-cls".length());
    }
    String superType = atts.getValue("", "superType");
    if (superType == null || superType.equals("InfrastructureRoot-cls"))
    {
      return;
    }
    if (superType.endsWith("-cls"))
    {
      superType = superType.substring(0, superType.length() - "-cls".length());
    }

    lookups_.addClass(id, superType);
  }

  //-------------------------------------------------------------------------
  /**
   * Command line runner.
   *
   * @param args  the command line arguments
   */
  public static void main(String[] args)
  {
    System.out.println("RIM Association Lookup Parser, v.0.0.2, 2004-05-06");

    if (args.length != 1)
    {
      System.out.println("Usage: " +
        "java org.hl7.meta.impl.RimAssociationLookupParser <rim.xml file>");
      System.exit(1);
    }

    RimAssociationLookupsParser ralp = null;
    try
    {
      ralp = new RimAssociationLookupsParser();
      RimAssociationLookups model = ralp.parse(args[0]);

      model.dump(System.out);
    }
    catch (Throwable t)
    {
      t.printStackTrace();
    }
  }
}
