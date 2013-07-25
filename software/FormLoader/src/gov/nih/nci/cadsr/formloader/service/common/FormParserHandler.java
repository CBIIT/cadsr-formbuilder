package gov.nih.nci.cadsr.formloader.service.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.ModuleDescriptor;

import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

/**
 * @author yangs8
 *
 */
public class FormParserHandler extends ParserHandler {
	private static Logger logger = Logger.getLogger(FormParserHandler.class.getName());
	
	FormDescriptor tempForm;
	int moduleCountForForm = 0;	
	String methodName;
	ArrayDeque<String> nodeQueue;
	int formCount = 0;
	
	//List<FormDescriptor> formsToWorkon;
	
	public FormParserHandler(List<FormDescriptor> forms) {
		super(forms);
		
		formCount = 0;
		moduleCountForForm = 0;
		methodName = null;
		nodeQueue = new ArrayDeque<String>();
		//generatedForms = new ArrayList<FormDescriptor>();
	}
	

	@Override
	public void handleStartElement(XMLStreamReader xmlreader) {
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
			tempForm = new FormDescriptor();
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
			tempForm.getModules().add(new ModuleDescriptor());
		} 
		
		//logger.debug("Pushing to node queue: " + localName);
		this.nodeQueue.push(localName);
		
	}

	@Override
	public void handleEndElement(XMLStreamReader xmlreader) {
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
			 
			 //TODO
			 this.formList.add(tempForm);
			 
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

	@Override
	public void handleCharacterElement(XMLStreamReader xmlreader) {
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
		
		try {
			Class[] paramString = new Class[1];	
			paramString[0] = String.class;
			Class currClass = tempForm.getClass();
			Method method = currClass.getMethod(methodName, paramString);
			method.invoke(tempForm, value);
			
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
	
	/**
	 * Use this version is the method is in super class
	 * @param methodName
	 * @param value
	 */
	protected void setFormPropertyV2(String methodName, String value) {	
		
		try {
			Class[] paramString = new Class[1];	
			paramString[0] = String.class;
			Class currClass = tempForm.getClass();
			Method method = null;
			
			while (currClass != Object.class) {
			     try {
			          method = currClass.getDeclaredMethod(methodName, paramString);
			          break;
			     } catch (NoSuchMethodException ex) {
			    	 currClass = currClass.getSuperclass();
			     }
			}
			
			if (methodName.contains("Public")) {
				int i = 0;
				i++;
			}
			//Method method = tempForm.getClass().getSuperclass().getDeclaredMethod(methodName, paramString);
			method.invoke(tempForm, value);
			
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
	
}