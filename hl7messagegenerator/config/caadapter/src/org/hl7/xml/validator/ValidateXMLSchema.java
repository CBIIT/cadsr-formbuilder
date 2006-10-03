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
* Contributor(s):  Jayfus T. Doswell (05/4/2005)
*/

/*
CREATED BY: UNKNOWN             CREATION DATE: UNKNOWN
UPDATED BY: Jayfus T. Doswell   UPDATE DATE:   05/04/2005

DESCRIPTION:
The ValidateXMLSchema provides functionality for validating an XML file and XML string checking
to see if it is syntatically correct (i.e., well-formed) and valid against one specific schema (i.e., valid).
A Wrapper class is included in this package that includes one method for calling.

*/

package org.hl7.xml.validator;

import java.io.*;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;


public class ValidateXMLSchema
{
    //Constants when using XML Schema for SAX parsing.
    static final String JAXP_SCHEMA_LANGUAGE =
        "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA =
        "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_SOURCE =
        "http://java.sun.com/xml/jaxp/properties/schemaSource";

    protected static final Logger LOGGER = Logger.getLogger("org.hl7.xml");

    private StringBuffer errors = new StringBuffer();;

    public String getErrors()
    {
        return errors.toString();
    }

    /**
     * If the exception throws, the source is not valid against the schema
     */
    public boolean isValidSAX(String source, String schema) throws Exception
    {
        StringReader sr = null;
        InputSource is = null;

        try
        {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            spf.setValidating(true);
            SAXParser sp = spf.newSAXParser();
            sp.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
            sp.setProperty(JAXP_SCHEMA_SOURCE, schema);

            MyDefaultHandler dh = new MyDefaultHandler();

            //Check the source path to see if the input is a fill path or an XML string
            //If it is a XML file send it directly to the XML parser, else validate an
            //XML snippet against the XMLSchema.

            if (isValidPath(source))
            {
                sp.parse(source, dh);
            }
            else
            {
                //System.err.println("###"+source+"###");

                sr = new StringReader(source);
                is = new InputSource(sr);
                sp.parse(is, dh);
            }
            return (dh.isValid) ? true : false;  //If the default handler is valid, return true.  Otherwise, return false.

        }

            //Catch the exceptions
            //TODO Add better Error Handling.
        catch (SAXNotRecognizedException snr)
        {
//            snr.printStackTrace();
            throw snr;
        }
        catch (SAXException se)
        {
//            se.printStackTrace();
            throw se;
        }
        catch (Exception e)
        {
//            e.printStackTrace();
            throw e;
        }
//   return false;
    }

    /**
     * Check the passing parameter strpath is xml file name or xml string
     * @param strpath
     * @return  boolean
     */
    public static boolean isValidPath(String strpath)
    {
        //TODO Add better logic for identifying if the file exist and handling the error.

//        if (strpath.indexOf(File.separator) > 0)
        File file = new File(strpath);

        if (file.exists())
        {
            return true; //This is a valid path
        }
        else
        {
            return false; //This is not a valid path
        }
    }

    /**
     * If the exception throws out, the source is not well formed
     * @param source
     * @return
     * @throws Exception
     */
    public boolean isWellFormedSAX(String source) throws Exception
    {
        StringReader sr = null;
        InputSource is = null;

        try
        {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            spf.setValidating(false);
            SAXParser sp = spf.newSAXParser();
            sp.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
//     sp.setProperty(JAXP_SCHEMA_SOURCE, schema);
            MyDefaultHandler dh = new MyDefaultHandler();

            if (isValidPath(source))
            {
                sp.parse(source, dh);
            }
            else
            {
                sr = new StringReader(source);
                is = new InputSource(sr);
                sp.parse(is, dh);
            }
            return (dh.isValid) ? true : false;
        }
        catch (SAXNotRecognizedException snr)
        {
            throw snr;
        }
        catch (SAXException se)
        {
            throw se;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public static void main(String args[]) throws Exception
    {
        new ValidateXMLSchema().isValidSAX(args[0], args[1]);
    }


//Custom error hanler to print errors and return isValid

class MyDefaultHandler extends DefaultHandler
{

    private PrintWriter out_;
    private Locator locator_;
    private int errors_ = 0;

    public MyDefaultHandler() throws Exception
    {
/*
   String errorfile = "errorsfile";
   OutputStream fout = new FileOutputStream(errorfile + ".txt");
   OutputStream bout = new BufferedOutputStream(fout);
   out = new OutputStreamWriter(bout, "8859_1");
*/

    }

    public void setDocumentLocator(Locator locator)
    {
        locator_ = locator;
    }

    public void endDocument()
    {
        LOGGER.fine("  " + errors_ + " error(s) found");
    }

    //flag to check if the xml document was valid
    public boolean isValid = true;

    //Receive notification of a recoverable error.
    public void error(SAXParseException se) throws SAXException
    {
        ++errors_;
        setValidity(se, "INVALID Recoverable Error:");
    }

    //Receive notification of a non-recoverable error.
    public void fatalError(SAXParseException se) throws SAXException
    {
        ++errors_;
        setValidity(se, "Fatal Error:");
    }

    //Receive notification of a warning.
    public void warning(SAXParseException se) throws SAXException
    {
        setValidity(se, "Warning !");
    }

    private void setValidity(SAXParseException se, String errortype) throws SAXException
    {
        isValid = false;
        String saxerror = se.toString();
        errors.append(errorWriter(saxerror, errortype)).append("\n");
    }

    private String errorWriter(String errorstring, String errortype)
    {
        StringBuffer e = new StringBuffer();
        e.append(errortype);

        if (locator_ != null)
        {
            e.append(" " + locator_.getSystemId() + ", " +
                "line:"  + locator_.getLineNumber() + " cloumn:" + locator_.getColumnNumber());
        }
        e.append("  " + errorstring);
        return e.toString();
    }
}

}