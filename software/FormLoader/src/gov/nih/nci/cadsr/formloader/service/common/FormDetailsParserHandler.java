package gov.nih.nci.cadsr.formloader.service.common;

import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.ncicb.cadsr.common.dto.DefinitionTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.dto.ProtocolTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.dto.RefdocTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
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
	List<DefinitionTransferObjectExt> definitions = new ArrayList<DefinitionTransferObjectExt>();
	//List<String> protocolIds = new ArrayList<String>();
	List<ProtocolTransferObjectExt> protocols = new ArrayList<ProtocolTransferObjectExt>();
	List<DesignationTransferObjectExt> designations = new ArrayList<DesignationTransferObjectExt>();
	
	DesignationTransferObjectExt currDesignation;	
	RefdocTransferObjectExt currRefDoc;
	DefinitionTransferObjectExt currDefinition;
	ProtocolTransferObjectExt currProtocol;
	
	List<String> csPublicIdVersionPairs;
	
	String csPublicId;
	String csVersion;
	
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
					currClassName = "DesignationTransferObjectExt";
		
					//ClassSchemeItem csi = currDesignation.getCsCsis().get(0);
					//csi.get
				}
				
			} else if (localName.equals(StaXParser.DEFINITION)) {
				if (nodeQueue.peek().equals(StaXParser.FORM)) {
					this.currDefinition = new DefinitionTransferObjectExt();
					currClassName = "DefinitionTransferObjectExt";
				}
				
			} else if (localName.equals(StaXParser.REFERENCE_DOCUMENT)) {
				if (nodeQueue.peek().equals(StaXParser.FORM)) {
					this.currRefDoc = new RefdocTransferObjectExt();
					currClassName = "RefdocTransferObjectExt";
				}
			}  else if (localName.equals(StaXParser.PROTOCOL)) {
				if (nodeQueue.peek().equals(StaXParser.FORM)) {
					this.currProtocol = new ProtocolTransferObjectExt();
					currClassName = "ProtocolTransferObjectExt";
				}
				
			} else if (localName.equals(StaXParser.NAME)) {
				if (nodeQueue.peek().equals(StaXParser.DESIGNATION))
					this.methodName = "setName";
				else if (nodeQueue.peek().equals(StaXParser.REFERENCE_DOCUMENT)) 
					this.methodName = "setDocName";
						
			} else if (localName.equals(StaXParser.TYPE)) {
				if (nodeQueue.peek().equals(StaXParser.DESIGNATION) || nodeQueue.peek().equals(StaXParser.DEFINITION))
						this.methodName = "setType";
				else if (nodeQueue.peek().equals(StaXParser.REFERENCE_DOCUMENT)) 
					this.methodName = "setDocType";
					
			} else if (localName.equals(StaXParser.LANGUAGE_NAME) && nodeQueue.peek().equals(StaXParser.DESIGNATION)) {
				this.methodName = "setLanguage";
			} else if (localName.equals(StaXParser.CONTEXT)) {
				if (nodeQueue.peek().equals(StaXParser.DESIGNATION) || 
						nodeQueue.peek().equals(StaXParser.REFERENCE_DOCUMENT) ||
								nodeQueue.peek().equals(StaXParser.PROTOCOL) ||
								nodeQueue.peek().equals(StaXParser.DEFINITION))
					this.methodName = "setContextName";
			} else if (localName.equals(StaXParser.URL) && nodeQueue.peek().equals(StaXParser.REFERENCE_DOCUMENT)) {
				this.methodName = "setUrl";
			} else if (localName.equals(StaXParser.DOCTEXT) && nodeQueue.peek().equals(StaXParser.REFERENCE_DOCUMENT)) {
				this.methodName = "setDocText";
			}  else if (localName.equals(StaXParser.SHORT_NAME)) {
				if (nodeQueue.peek().equals(StaXParser.PROTOCOL))
					this.methodName = "setPreferredName";						
			} else if (localName.equals(StaXParser.TEXT)) {
				if (nodeQueue.peek().equals(StaXParser.DEFINITION))
					this.methodName = "setDefinition";
			} else if (localName.equals(StaXParser.CLASSFINICATION)) {
				if (this.currDefinition != null)
					this.csPublicIdVersionPairs = this.currDefinition.getClassficationPublicIdVersionPairs();
				else if (this.currDesignation != null)
					this.csPublicIdVersionPairs = this.currDesignation.getClassficationPublicIdVersionPairs();
					
			} 
			
//			else if (localName.equals(StaXParser.PUBLIC_ID) || 
//					localName.equals(StaXParser.VALID_VALUE)) {
//				
//				if ((nodeQueue.peek().equals(StaXParser.CLASSFINICATION))) {
//					if (this.currDefinition != null)
//						this.csPublicIdVersionPairs = this.currDefinition.getClassficationPublicIdVersionPairs();
//					else if (this.currDesignation != null)
//						this.csPublicIdVersionPairs = this.currDesignation.getClassficationPublicIdVersionPairs();
//				}
//			}		
						
					
				
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
				//if ( this.currClassName != null && this.currClassName.startsWith("Designation")) {
				if (this.currDesignation != null) {
					this.designations.add(this.currDesignation);
					this.currClassName = null;
					this.currDesignation = null;
				}
			} else if (localName.equals(StaXParser.DEFINITION)) {
				//if ( this.currClassName != null && this.currClassName.equals("DefinitionTransferObjectExt")) {
				if (this.currDefinition != null) {
					this.definitions.add(this.currDefinition);
					this.currClassName = null;
					this.currDefinition = null;
				}
			} else if (localName.equals(StaXParser.REFERENCE_DOCUMENT)) {
				//if ( this.currClassName != null && this.currClassName.equals("RefdocTransferObjectExt")) {
				if (this.currRefDoc != null) {
					this.refdocs.add(this.currRefDoc);
					this.currClassName = null;
					this.currRefDoc = null;
				}				
			} else if (localName.equals(StaXParser.PROTOCOL)) {				
				if (this.currProtocol != null) {
					this.protocols.add(this.currProtocol);
					this.currClassName = null;
					this.currProtocol = null;
				}				
			} else if ((localName.equals(StaXParser.CLASSFINICATION))) {
				if (this.currDefinition != null) {
					this.currDefinition.setClassficationPublicIdVersionPairs(this.csPublicIdVersionPairs);
					this.csPublicIdVersionPairs = null;
				}
			    else if (this.currDesignation != null) {
					this.currDesignation.setClassficationPublicIdVersionPairs(this.csPublicIdVersionPairs);
					this.csPublicIdVersionPairs = null;
			    }
				 
			} else if (localName.equals(StaXParser.PUBLIC_ID) ||
					localName.equals(StaXParser.VERSION)) {
				if (this.csPublicIdVersionPairs != null && 
						this.csPublicId != null && this.csPublicId.length() > 0 &&
						this.csVersion != null && this.csVersion.length() > 0) {
					this.csPublicIdVersionPairs.add(this.csPublicId + "," + this.csVersion);
					this.csPublicId = null;
					this.csVersion = null;
						
				}
			} 
		
		}
		this.methodName = null;
		if (localName.equals(StaXParser.FORM)) {
			nodeQueue.clear();this.currFormIdx++;
		} else {
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
					
			if (peek.equals(StaXParser.SHORT_NAME)) {
				if (this.currClassName != null && this.currClassName.equals("ProtocolTransferObjectExt") &&
						this.methodName != null) {
					setPropertyForObject(this.currProtocol, methodName, xmlreader.getText());
				}
			} else if (peek.equals(StaXParser.NAME) || peek.equals(StaXParser.TYPE) 
					|| peek.equals(StaXParser.LANGUAGE_NAME) || peek.equals(StaXParser.CONTEXT)
					||  peek.equals(StaXParser.URL) || peek.equals(StaXParser.DOCTEXT)) {
				if (this.currClassName != null && this.currClassName.equals("DesignationTransferObjectExt") &&
						this.methodName != null) {
					setPropertyForObject(this.currDesignation, methodName, xmlreader.getText());
				} else if (this.currClassName != null && this.currClassName.equals("RefdocTransferObjectExt") &&
						this.methodName != null) {
					setPropertyForObject(this.currRefDoc, methodName, xmlreader.getText());
				} else if (this.currClassName != null && this.currClassName.equals("ProtocolTransferObjectExt") &&
						this.methodName != null) {
					setPropertyForObject(this.currProtocol, methodName, xmlreader.getText());
				} else if (this.currClassName != null && this.currClassName.equals("DefinitionTransferObjectExt") &&
						this.methodName != null) {
					setPropertyForObject(this.currDefinition, methodName, xmlreader.getText());
				}
			} else if (peek.equals(StaXParser.TEXT)) {
				if (this.currClassName != null && this.currClassName.equals("DefinitionTransferObjectExt") &&
						this.methodName != null) {
					setPropertyForObject(this.currDefinition, methodName, xmlreader.getText());
				} 
			} else if (peek.equals(StaXParser.PUBLIC_ID)) {
				if (this.csPublicIdVersionPairs != null)
					this.csPublicId = xmlreader.getText().trim();
			} else if (peek.equals(StaXParser.VERSION)) {
				if (this.csPublicIdVersionPairs != null)
					this.csVersion = xmlreader.getText().trim();
			} 
		}
		
	}


	public List<RefdocTransferObjectExt> getRefdocs() {
		return refdocs;
	}


	public void setRefdocs(List<RefdocTransferObjectExt> refdocs) {
		this.refdocs = refdocs;
	}


	public List<DefinitionTransferObjectExt> getDefinitions() {
		return definitions;
	}


	public void setDefinitions(List<DefinitionTransferObjectExt> definitions) {
		this.definitions = definitions;
	}

	public List<ProtocolTransferObjectExt> getProtocols() {
		return protocols;
	}


	public void setProtocols(List<ProtocolTransferObjectExt> protocols) {
		this.protocols = protocols;
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
