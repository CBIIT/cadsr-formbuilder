/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

package gov.nih.nci.cadsr.formloader.service.common;

import gov.nih.nci.cadsr.formloader.domain.FormHeader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

public class StaXParser {
	
	private static Logger logger = Logger.getLogger(StaXParser.class.getName());

	//Mapping node name in xml
	private static final String FORM = "form";
	private static final String PUBLIC_ID = "publicID";
	private static final String LONG_NAME = "longName";
	private static final String VERSION = "version";
	private static final String CONTEXT = "context";
	private static final String WORKFLOW_STATUS = "workflowStatusName";
	private static final String TYPE = "type";
	private static final String PROTOCOL = "protocol";
	private static final String MODULE = "module";

	List<FormHeader> generatedForms = new ArrayList<FormHeader>();
	
	FormHeader tempForm;
	int formCount = 0;
	int moduleCountForForm = 0;	
	String methodName;
	
	ArrayDeque<String> nodeQueue;
	
	private void reset() {
		generatedForms.clear();
		formCount = 0;
		moduleCountForForm = 0;
		
		nodeQueue = new ArrayDeque<String>();
	}

	public List<FormHeader> parseFormHeaders(String xmlPathName) {
		
		reset();
		
		FileReader fReader = null;
		BufferedReader bReader = null;
		XMLStreamReader xmlreader = null;
		
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			bReader = new BufferedReader(new FileReader(xmlPathName)); 
			xmlreader = inputFactory.createXMLStreamReader(bReader);
			
			while(xmlreader.hasNext()){
				
			      readNext(xmlreader);
			      xmlreader.next();
			}
			      
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fReader != null)
					fReader.close();
				if (xmlreader != null)
					xmlreader.close();
			} catch (IOException e) {
				logger.error("Error while closing FileReader handle in parseFormCollection()");
			} catch (XMLStreamException xmle) {
				logger.error("Error while closing xml reader handle in parseFormCollection()");
			}
		}
		
		
		for (int i = 0; i < generatedForms.size(); i++) 
			logger.debug(generatedForms.get(i).toString());
		return generatedForms;
	}
	
	protected void readNext(XMLStreamReader xmlreader) {
		if (xmlreader == null) {
			logger.debug("xmlread is null - this should never happen.");
			return;
		}

		switch (xmlreader.getEventType()) {

		case XMLStreamConstants.START_ELEMENT:
			handleStartElement(xmlreader);
			break;
		case XMLStreamConstants.END_ELEMENT:
			handleEndElement(xmlreader);
			break;
		case XMLStreamConstants.CHARACTERS:
			handleCharacterElement(xmlreader);
			break;
		case XMLStreamConstants.PROCESSING_INSTRUCTION:
			System.out.print("Processing Instrcutions\n");
			break;
		case XMLStreamConstants.CDATA:
			System.out.print("CDATA\n");
			break;
		case XMLStreamConstants.COMMENT:
			System.out.print("Comment\n");
			break;
		case XMLStreamConstants.DTD:
			System.out.print("DTD\n");
			break;
		case XMLStreamConstants.ENTITY_REFERENCE:
			System.out.print("Entity Reference\n");
			break;
		case XMLStreamConstants.ENTITY_DECLARATION:
			System.out.print("Entity Declaration\n");
			break;
		case XMLStreamConstants.START_DOCUMENT:
			System.out.print("Start Document\n");
			break;
		case XMLStreamConstants.END_DOCUMENT:
			System.out.print("End Document\n");
			break;
		case XMLStreamConstants.SPACE:
			System.out.print("Space\n");
			break;
		}


	}


	protected void handleStartElement(XMLStreamReader xmlreader) {
		
		if (xmlreader == null) {
			logger.debug("xmlreader is null!!!! Do nothing");
			return;
		}
			
		if (!xmlreader.hasName()) {
			logger.debug("Got an elem without name. Do nothing");
			return;
		}
		
		String localName = xmlreader.getLocalName();
		
		if (localName.equals(StaXParser.FORM)) {
			
			int lineNum = xmlreader.getLocation().getLineNumber();
			logger.debug("Got a form at line: " + lineNum);
			tempForm = new FormHeader();
			tempForm.setXml_line_begin(lineNum);
			this.methodName = null;
			formCount++;
		} else if (localName.equals(StaXParser.LONG_NAME) || 
				localName.equals(StaXParser.CONTEXT) || 
				localName.equals(StaXParser.VERSION)  ||
				localName.equals(StaXParser.TYPE) ||
				localName.equals(StaXParser.WORKFLOW_STATUS) ||
				localName.equalsIgnoreCase(StaXParser.PUBLIC_ID)) {
			if (nodeQueue.peek().equals(StaXParser.FORM)) {
				this.methodName = getMethodName(localName);	
			} else if (nodeQueue.peek().equals(StaXParser.PROTOCOL) &&
					localName.equals(StaXParser.LONG_NAME)) {
				this.methodName = getMethodName(StaXParser.PROTOCOL) + "Name"; 
				
			}
		
		} else if (localName.equals(StaXParser.MODULE)) {
			tempForm.setNumberOfModules(tempForm.getNumberOfModules() + 1);
		} 
		
		//logger.debug("Pushing to node queue: " + localName);
		this.nodeQueue.push(localName);
	}
	
	protected void handleCharacterElement(XMLStreamReader xmlreader) {
		if (xmlreader == null) {
			logger.debug("xmlreader is null!!!! Do nothing");
			return;
		}
		
		String currNode = (nodeQueue.peek() != null) ? nodeQueue.peek().toLowerCase() : null;
		
		if (currNode != null && methodName != null) {
			logger.debug("Queue head: " + nodeQueue.peek() + " | method name: " + methodName + " | value to set: " + xmlreader.getText());
			setFormProperty(methodName, xmlreader.getText());
		}
	}
	
	protected void setFormProperty(String methodName, String value) {
		//String parameter
		try {
			Class[] paramString = new Class[1];	
			paramString[0] = String.class;
			Method method = tempForm.getClass().getSuperclass().getDeclaredMethod(methodName, paramString);
			method.invoke(tempForm, value);
			
		} catch (NoSuchMethodException nsme) {
			logger.debug(nsme);
		} catch (SecurityException se) {
			logger.debug(se);
		} catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
            return;
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            return;
        } catch(Exception ex) {
			logger.debug(ex);
    	} catch (Throwable t) {
    		logger.debug(t);
    	}

	}
	
	protected void handleEndElement(XMLStreamReader xmlreader) {
		
		if (xmlreader == null) {
			logger.debug("xmlreader is null!!!! Do nothing");
			return;
		}
			
		if (!xmlreader.hasName()) {
			logger.debug("Got an elem without name. Do nothing");
			return;
		}
		
		String localName = xmlreader.getLocalName();
		
		if (localName.equals(StaXParser.FORM)) {
			int lineNum = xmlreader.getLocation().getLineNumber();
			logger.debug("End parsing a form at line: " + lineNum);
			 tempForm.setXml_line_end(lineNum);
			 logger.debug(tempForm.toString());
			 generatedForms.add(tempForm);
			 
			 logger.debug("peak: " + (String)nodeQueue.peek());
			 logger.debug("peak first: " + (String)nodeQueue.peekFirst());
			 logger.debug("peak last: " + (String)nodeQueue.peekLast());
			 
		} 
		
		this.methodName = null;
		if (localName.equals(StaXParser.FORM))
			nodeQueue.clear();
		else {
			if (!nodeQueue.isEmpty())
				nodeQueue.pop();
		}
	}
	
	/**
	 * 
	 * @param xmlElemName assumption for xmlElemName format: Camel case with 1st letter in first word in lower case
	 * @return
	 */
	protected String getMethodName(String xmlElemName) {
					
		char[] stringArray = xmlElemName.toCharArray();
		stringArray[0] = Character.toUpperCase(stringArray[0]);
		return "set" + new String(stringArray);
	}
}
