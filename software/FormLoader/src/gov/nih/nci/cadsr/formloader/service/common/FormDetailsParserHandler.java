package gov.nih.nci.cadsr.formloader.service.common;

import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.ncicb.cadsr.common.dto.DefinitionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.dto.RefdocTransferObjectExt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

/**
 * @author yangs8
 *
 */
public class FormDetailsParserHandler extends ParserHandler {
	private static Logger logger = Logger.getLogger(FormParserHandler.class.getName());
	
	
	ArrayDeque<String> nodeQueue;
	int startFormIdx;
	int currFormIdx;
	
	List<RefdocTransferObjectExt> refdocs = new ArrayList<RefdocTransferObjectExt>();
	List<DefinitionTransferObject> definitions = new ArrayList<DefinitionTransferObject>();
	List<String> protocolIds = new ArrayList<String>();
	List<DesignationTransferObjectExt> designations = new ArrayList<DesignationTransferObjectExt>();
	
	DesignationTransferObjectExt currDesignation;
	RefdocTransferObjectExt currRefDoc;
	DefinitionTransferObject currDefinition;
	
	String currClassName;
	String methodName;
	
	public FormDetailsParserHandler(FormDescriptor form, int startFormIdx) {
		super(null);
		this.startFormIdx = startFormIdx;
		currFormIdx = 0;

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
		
		
		if (this.startFormIdx == this.currFormIdx) {
				

			if (localName.equals(StaXParser.DESIGNATION)) {
				if (nodeQueue.peek().equals(StaXParser.FORM)) {
					this.currDesignation = new DesignationTransferObjectExt();
					currClassName = "DesignationTransferObject";
				}
				
			} else if (localName.equals(StaXParser.DEFINITION)) {
				if (nodeQueue.peek().equals(StaXParser.FORM)) {
					this.currDefinition = new DefinitionTransferObject();
					currClassName = "DefinitionTransferObject";
				}
				
			} else if (localName.equals(StaXParser.REFERENCE_DOCUMENT)) {
				if (nodeQueue.peek().equals(StaXParser.FORM)) {
					this.currRefDoc = new RefdocTransferObjectExt();
					currClassName = "RefdocTransferObjectExt";
				}
			}  else if (localName.equals(StaXParser.NAME)) {
				if (nodeQueue.peek().equals(StaXParser.DESIGNATION))
					this.methodName = "setName";
				else if (nodeQueue.peek().equals(StaXParser.REFERENCE_DOCUMENT)) 
					this.methodName = "setDocName";
						
			} else if (localName.equals(StaXParser.TYPE)) {
				if (nodeQueue.peek().equals(StaXParser.DESIGNATION))
						this.methodName = "setType";
				else if (nodeQueue.peek().equals(StaXParser.REFERENCE_DOCUMENT)) 
					this.methodName = "setDocType";
					
			} else if (localName.equals(StaXParser.LANGUAGE_NAME) && nodeQueue.peek().equals(StaXParser.DESIGNATION)) {
				this.methodName = "setLanguage";
			} else if (localName.equals(StaXParser.CONTEXT)) {
				if (nodeQueue.peek().equals(StaXParser.DESIGNATION) || 
						nodeQueue.peek().equals(StaXParser.REFERENCE_DOCUMENT))
					this.methodName = "setContextName";
			} else if (localName.equals(StaXParser.URL) && nodeQueue.peek().equals(StaXParser.REFERENCE_DOCUMENT)) {
				this.methodName = "setUrl";
			} else if (localName.equals(StaXParser.DOCTEXT) && nodeQueue.peek().equals(StaXParser.REFERENCE_DOCUMENT)) {
				this.methodName = "setDocText";
			}
				
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
		
		if (this.startFormIdx == this.currFormIdx) {
			
			if (localName.equals(StaXParser.DESIGNATION)) {
				if ( this.currClassName != null && this.currClassName.startsWith("Designation")) {
					this.designations.add(this.currDesignation);
					this.currClassName = null;
				}
			} else if (localName.equals(StaXParser.DEFINITION)) {
				if ( this.currClassName != null && this.currClassName.equals("DefinitionTransferObject")) {
					this.definitions.add(this.currDefinition);
					this.currClassName = null;
				}
			} else if (localName.equals(StaXParser.REFERENCE_DOCUMENT)) {
				if ( this.currClassName != null && this.currClassName.equals("RefdocTransferObjectExt")) {
					this.refdocs.add(this.currRefDoc);
					this.currClassName = null;
				}				
			}
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
		
		if (this.startFormIdx == this.currFormIdx) {
			String peek = nodeQueue.peek();
			if (peek == null)
				return;
					
			if (peek.equals(StaXParser.PROTOCOL_ID)) 
				this.protocolIds.add(xmlreader.getText());
			else if (peek.equals(StaXParser.NAME) || peek.equals(StaXParser.TYPE) 
					|| peek.equals(StaXParser.LANGUAGE_NAME) || peek.equals(StaXParser.CONTEXT)
					||  peek.equals(StaXParser.URL) || peek.equals(StaXParser.DOCTEXT)) {
				if (this.currClassName != null && this.currClassName.equals("DesignationTransferObject") &&
						this.methodName != null) {
					setPropertyForObject(this.currDesignation, methodName, xmlreader.getText());
				} else if (this.currClassName != null && this.currClassName.equals("RefdocTransferObjectExt") &&
						this.methodName != null) {
					setPropertyForObject(this.currRefDoc, methodName, xmlreader.getText());
				}
			}
		}
		
	}


	public List<RefdocTransferObjectExt> getRefdocs() {
		return refdocs;
	}


	public void setRefdocs(List<RefdocTransferObjectExt> refdocs) {
		this.refdocs = refdocs;
	}


	public List<DefinitionTransferObject> getDefinitions() {
		return definitions;
	}


	public void setDefinitions(List<DefinitionTransferObject> definitions) {
		this.definitions = definitions;
	}


	public List<String> getProtocolIds() {
		return protocolIds;
	}


	public void setProtocolIds(List<String> protocolIds) {
		this.protocolIds = protocolIds;
	}


	public List<DesignationTransferObjectExt> getDesignations() {
		return designations;
	}


	public void setDesignations(List<DesignationTransferObjectExt> designations) {
		this.designations = designations;
	}
	
	
	/**
	 * Use this version is the method is in super class
	 * @param methodName
	 * @param value
	 */
	protected void setPropertyForObject(Object currObj, String methodName, String value) {	
		
		try {
			Class[] paramString = new Class[1];	
			paramString[0] = String.class;
			Class currClass = currObj.getClass();
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
			method.invoke(currObj, value);
			
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
