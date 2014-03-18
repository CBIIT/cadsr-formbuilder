package gov.nih.nci.cadsr.formloader.service.common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.dto.ProtocolTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.dto.RefdocTransferObjectExt;

import org.junit.Before;
import org.junit.Test;

public class StaXParserTest {
	
	StaXParser parser;

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testParseFormCollection() {
		parser = new StaXParser();
		String xmlPathName = ".\\test\\data\\xmlvalidation\\forms-2.xml";
		
		FormCollection formCollection = new FormCollection();
		formCollection.setXmlFileName("forms-2.xml");
		formCollection.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
		formCollection = parser.parseCollectionAndForms(formCollection, ".\\test\\data\\xmlvalidation\\forms-2.xml");
		List<FormDescriptor> forms = formCollection.getForms();
		assertNotNull(forms);
		assertTrue(forms.size() == 3);
		
		forms = parser.parseFormQuestions(xmlPathName, forms);
		assertNotNull(forms);
		assertTrue(forms.size() == 3);
		assertTrue(forms.get(1).getModules().size() == 5);
		assertTrue("2321850".equals(forms.get(1).getPublicId()));
		
		assertTrue(forms.get(0).getPreferredDefinition().startsWith("CALGB"));
		assertTrue(forms.get(0).getHeaderInstruction().startsWith("INSTRUCTIONS: Complete"));
		
		assertTrue("2321860".equals(forms.get(2).getModules().get(1).getPublicId()));
		assertTrue("2322157".equals(forms.get(2).getModules().get(2).getQuestions().get(5).getPublicId()));
		assertTrue("Results".equals(forms.get(2).getModules().get(2).getQuestions()
				.get(5).getQuestionText()));
		
		assertTrue("2+".equals(forms.get(2).getModules().get(2).getQuestions()
				.get(8).getValidValues().get(2).getValue()));
		
		assertTrue("Unnamed2".equals(forms.get(2).getModules().get(1).getPreferredDefinition()));
		assertTrue("Unnamed2".equals(forms.get(2).getModules().get(1).getLongName()));
		
		assertTrue(forms.get(0).getModules().get(0).getQuestions().get(3).getValidValues().get(0).getInstruction().startsWith("Last"));
		
		assertTrue(forms.get(2).getModules().get(0).getQuestions().get(1).isEditable() == true);
		assertTrue(forms.get(2).getModules().get(0).getQuestions().get(1).isMandatory() == false);
		
		assertTrue("0".equals(forms.get(2).getModules().get(0).getMaximumModuleRepeat()));
	}
	
	@Test
	public void testParseFormDetails() {
		
		parser = new StaXParser();
		String xmlPathName = ".\\test\\data\\xmlvalidation\\3256357_v1_0_multi-protocols.xml";
		
		FormCollection formCollection = new FormCollection();
		formCollection.setXmlFileName("3256357_v1_0_multi-protocols.xml");
		formCollection.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
		formCollection = parser.parseCollectionAndForms(formCollection, xmlPathName);
		List<FormDescriptor> forms = formCollection.getForms();
		
		
		//List<FormDescriptor> forms = parser.parseFormHeaders(xmlPathName);
		assertNotNull(forms);
		assertTrue(forms.size() == 1);
		
		FormDescriptor form = parser.parseFormDetails(xmlPathName, forms.get(0), 0);
		assertNotNull(form);
		
		List<ProtocolTransferObjectExt> protoList = parser.getProtocols();
		assertNotNull(protoList);
		assertTrue(protoList.size() == 2);
		assertTrue(protoList.get(0).getContextName().equals("CCR"));
		assertTrue(protoList.get(1).getPreferredName().equals("02_C_0241E"));
		
		List<DesignationTransferObjectExt> desObjs = parser.getDesignations();
		assertNotNull(desObjs);
		assertTrue(desObjs.size() == 1);
		
		DesignationTransferObjectExt desig = desObjs.get(0);
		assertTrue(desig.getName() == null || desig.getName().length() == 0);
		assertTrue(desig.getType().equals("ABBREVIATION"));
		assertTrue(desig.getLanguage().equals("ENGLISH"));
		assertTrue(desig.getContextName().equals("caBIG"));
	}
	
	@Test
	public void testParseFormDetailsWithNullForm() {
		parser = new StaXParser();
		FormDescriptor form = parser.parseFormDetails("afafa", null, 0);
		assertNull(form);
	}
	
	
	@Test
	public void testParseFormDetailsFor5Forms() {
		
		parser = new StaXParser();
		String xmlPathName = ".\\test\\data\\xmlvalidation\\load_forms-5.xml";
		
		FormCollection formCollection = new FormCollection();
		formCollection.setXmlFileName("load_forms-5.xml");
		formCollection.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
		formCollection = parser.parseCollectionAndForms(formCollection, xmlPathName);
		List<FormDescriptor> forms = formCollection.getForms();
		
		//List<FormDescriptor> forms = parser.parseFormHeaders(xmlPathName);
		assertNotNull(forms);
		assertTrue(forms.size() == 5);
		
		FormDescriptor form = parser.parseFormDetails(xmlPathName, forms.get(0), 0);
		assertNotNull(form);
	
		List<DesignationTransferObjectExt> desObjs = parser.getDesignations();
		assertNotNull(desObjs);
		assertTrue(desObjs.size() == 1);
		
		DesignationTransferObjectExt desig = desObjs.get(0);
		assertTrue(desig.getName() == null || desig.getName().length() == 0);
		assertTrue(desig.getType().equals("ABBREVIATION"));
		assertTrue(desig.getLanguage().equals("ENGLISH"));
		assertTrue(desig.getContextName().equals("caBIG"));
		
		List<RefdocTransferObjectExt> refdocs = parser.getRefdocs();
		assertNotNull(refdocs);
		assertTrue("Expecting 2 but get " + refdocs.size(), refdocs.size() == 2);
		
		RefdocTransferObjectExt refdoc = refdocs.get(1);
		assertTrue("Image for the form".equals(refdoc.getDocName()));
		assertTrue("IMAGE_FILE".equals(refdoc.getDocType()));
		assertTrue("SY testing another refdoc".equals(refdoc.getDocText()));
		assertTrue(refdoc.getUrl() == null || refdoc.getUrl().length() == 0);
		
		assertTrue(refdocs.get(0).getUrl().startsWith("https://"));
		
		parser = new StaXParser();
		form = parser.parseFormDetails(xmlPathName, forms.get(2), 2);
		assertNotNull(form);
		assertTrue(parser.getRefdocs().size() == 0);
		assertTrue(parser.getDesignations().size() == 1);
		
	}
	
	@Test
	public void testParseFormQuestions() {
		parser = new StaXParser();
		String xmlPathName = ".\\test\\data\\xmlvalidation\\3193449_has_valid_values.xml";
		
		FormCollection formCollection = new FormCollection();
		formCollection.setXmlFileName("3193449_has_valid_values.xml");
		formCollection.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
		formCollection = parser.parseCollectionAndForms(formCollection, xmlPathName);
		List<FormDescriptor> forms = formCollection.getForms();
		
		//List<FormDescriptor> forms = parser.parseFormHeaders(xmlPathName);
		assertNotNull(forms);
		assertTrue(forms.size() == 1);
		
		forms = parser.parseFormQuestions(xmlPathName, forms);
		assertNotNull(forms);
		
		List<QuestionDescriptor.ValidValue> vvs = forms.get(0).getModules().get(0).getQuestions().get(5).getValidValues();
		assertNotNull(vvs);
		assertTrue(vvs.size() == 4);
		assertTrue(vvs.get(0).getDescription().length() > 0);
		assertTrue(vvs.get(0).getInstruction().startsWith("SY"));
		
		String cdevdPublicId = forms.get(0).getModules().get(2).getQuestions().get(1).getCdeVdPublicId();
		String cdevdversion = forms.get(0).getModules().get(2).getQuestions().get(1).getCdeVdVersion();
		assertTrue("2018550".equals(cdevdPublicId));
		assertTrue("1.0".equals(cdevdversion));
	}
	
	@Test
	public void testParseFormQuestionsWithNullForm() {
		parser = new StaXParser();
		List<FormDescriptor> forms = parser.parseFormQuestions("afaf", null);
		assertNull(forms);
	}

	@Test
	public void testParseFormQuestionsWith0Form() {
		parser = new StaXParser();
		List<FormDescriptor> forms = parser.parseFormQuestions("afaf", new ArrayList<FormDescriptor>());
		assertNull(forms);
	}
	
	@Test
	public void testParseFormQuestionsWithValueDomainFields() {
		
		parser = new StaXParser();
		String xmlPathName = ".\\test\\data\\xmlvalidation\\3193449_has_valueDomain_values.xml";
		
		FormCollection formCollection = new FormCollection();
		formCollection.setXmlFileName("3193449_has_valueDomain_values.xml");
		formCollection.setXmlPathOnServer(".\\test\\data\\xmlvalidation");
		formCollection = parser.parseCollectionAndForms(formCollection, xmlPathName);
		List<FormDescriptor> forms = formCollection.getForms();
		
		//List<FormDescriptor> forms = parser.parseFormHeaders(xmlPathName);
		assertNotNull(forms);
		assertTrue(forms.size() == 1);
		
		forms = parser.parseFormQuestions(xmlPathName, forms);
		assertNotNull(forms);
		
		QuestionDescriptor question = forms.get(0).getModules().get(0).getQuestions().get(0);
		String vdPublicId = question.getCdeVdPublicId();
		String vdversion = question.getCdeVdVersion();
		assertTrue("2018389".equals(vdPublicId));
		assertTrue("1.0".equals(vdversion));
		
		assertTrue(question.getDatatypeName().equals("CHARACTER"));
		assertTrue(question.getDecimalPlace() == null || question.getDecimalPlace().length() == 0);
		assertTrue(question.getMaximumLengthNumber().equals("35"));
	}
}
