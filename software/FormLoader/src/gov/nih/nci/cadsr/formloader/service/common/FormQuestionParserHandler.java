package gov.nih.nci.cadsr.formloader.service.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.ModuleDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;

import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

public class FormQuestionParserHandler extends ParserHandler {
	private static Logger logger = Logger
			.getLogger(FormQuestionParserHandler.class.getName());

	ArrayDeque<String> nodeQueue;

	FormDescriptor currForm;
	ModuleDescriptor currModule;
	QuestionDescriptor currQuestion;
	Object objToSetProperty;

	int form_idx = -1;
	int module_idx = -1;
	int question_idx = -1;

	String methodName;

	public FormQuestionParserHandler(List<FormDescriptor> forms) {
		super(forms);
		nodeQueue = new ArrayDeque<String>();
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
			if (++form_idx > this.formList.size() - 1) {
				logger.error("Parsing form idx is out of sync with form list to work on!!");
				return;
			}
			currForm = this.formList.get(form_idx);
			this.methodName = null;

		} else if (localName.equals(StaXParser.MODULE)) {
			module_idx++;
			if (module_idx > this.currForm.getModules().size() - 1) {
				logger.error("Parsing module idx is out of sync with module list of the form to work on!!");
				return;
			}
			currModule = currForm.getModules().get(module_idx);
		} else if (localName.equals(StaXParser.QUESTION)) {
			question_idx++;
			currQuestion = new QuestionDescriptor();
			currModule.getQuestions().add(currQuestion);
			
		} else if (localName.equalsIgnoreCase(StaXParser.PUBLIC_ID)
				|| localName.equalsIgnoreCase(StaXParser.VERSION)) {
			if (nodeQueue.peek().equals(StaXParser.MODULE)) {
				this.objToSetProperty = currModule;
				this.methodName = getMethodName(localName);
			} else if (nodeQueue.peek().equals(StaXParser.QUESTION)) {
				this.objToSetProperty = currQuestion;
				this.methodName = getMethodName(localName);
			} else if (nodeQueue.peek().equals(StaXParser.DATA_ELEMENT)) {
				this.objToSetProperty = currQuestion;
				this.methodName = getMethodName(StaXParser.PREFIX_CDE + localName);
			}

		} else if  (localName.equalsIgnoreCase(StaXParser.QUESTION_TEXT)
				|| localName.equalsIgnoreCase(StaXParser.DEFAULT_VALUE)) {
			if (nodeQueue.peek().equals(StaXParser.QUESTION)) {
				this.objToSetProperty = currQuestion;
				this.methodName = getMethodName(localName);
			}
		} else if  (localName.equalsIgnoreCase(StaXParser.VALID_VALUE)) {
			if (nodeQueue.peek().equals(StaXParser.QUESTION)) {
				QuestionDescriptor.ValidValue vValue = currQuestion.new ValidValue();
				currQuestion.getValidValues().add(vValue);
				this.objToSetProperty = vValue;
			}
		} else if (localName.equalsIgnoreCase(StaXParser.VALUE)
				|| localName.equalsIgnoreCase(StaXParser.MEANING_TEXT)) {
			if (nodeQueue.peek().equals(StaXParser.VALID_VALUE))
				this.methodName = getMethodName(localName);
		} else if (localName.equalsIgnoreCase(StaXParser.TEXT)) {
			if (nodeQueue.peek().equals(StaXParser.INSTRUCTION)) {
				this.objToSetProperty = currQuestion;
				this.methodName = getMethodName(StaXParser.INSTRUCTION);
			}
		}
		

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

		this.methodName = null;
		if (localName.equals(StaXParser.FORM)) {
			logger.debug("Finished with a form");
			module_idx = -1;
			nodeQueue.clear();
		} else {
			if (localName.equals(StaXParser.MODULE))
				question_idx = -1;
			
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

		String currNode = (nodeQueue.peek() != null) ? nodeQueue.peek()
				.toLowerCase() : null;

		if (currNode != null && methodName != null) {
			logger.debug("Queue head: " + nodeQueue.peek() + " | method name: "
					+ methodName + " | value to set: " + xmlreader.getText());
			setProperty(this.objToSetProperty, methodName, xmlreader.getText());
		}
		
		methodName = null;
	}

	protected void setProperty(Object targetObj, String methodName, String value) {

		try {
			Class[] paramString = new Class[1];
			paramString[0] = String.class;
			Class currClass = targetObj.getClass();
			Method method = currClass.getMethod(methodName, paramString);

			method.invoke(targetObj, value);

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
		} catch (Exception ex) {
			logger.debug(ex);
		} catch (Throwable t) {
			logger.debug(t);
		}

	}

}
