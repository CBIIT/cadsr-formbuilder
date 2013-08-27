package gov.nih.nci.cadsr.formloader.service.common;

import static org.junit.Assert.*;

import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.QuestionDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObjectExt;

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
		String xmlPathName = ".\\test\\data\\forms-2.xml";
		List<FormDescriptor> forms = parser.parseFormHeaders(".\\test\\data\\forms-2.xml");
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
		
		assertTrue(forms.get(2).getModules().get(0).getQuestions().get(1).isEditable() == true);
		assertTrue(forms.get(2).getModules().get(0).getQuestions().get(1).isMandatory() == false);
	}
	
	@Test
	public void testParseFormDetails() {
		
		parser = new StaXParser();
		String xmlPathName = ".\\test\\data\\3256357_v1_0_multi-protocols.xml";
		List<FormDescriptor> forms = parser.parseFormHeaders(xmlPathName);
		assertNotNull(forms);
		assertTrue(forms.size() == 1);
		
		FormDescriptor form = parser.parseFormDetails(xmlPathName, forms.get(0), 0);
		assertNotNull(form);
		
		List<String> protoList = parser.getProtocolIds();
		assertNotNull(protoList);
		assertTrue(protoList.size() == 2);
		assertTrue(protoList.get(0).equals("01_C_0129F"));
		assertTrue(protoList.get(1).equals("02_C_0241E"));
		
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
	public void testParseFormQuestions() {
		parser = new StaXParser();
		String xmlPathName = ".\\test\\data\\3193449_has_valid_values.xml";
		List<FormDescriptor> forms = parser.parseFormHeaders(xmlPathName);
		assertNotNull(forms);
		assertTrue(forms.size() == 1);
		
		forms = parser.parseFormQuestions(xmlPathName, forms);
		assertNotNull(forms);
		
		List<QuestionDescriptor.ValidValue> vvs = forms.get(0).getModules().get(0).getQuestions().get(5).getValidValues();
		assertNotNull(vvs);
		assertTrue(vvs.size() == 4);
		assertTrue(vvs.get(0).getDescription().length() > 0);
		assertTrue(vvs.get(0).getInstruction().startsWith("SY"));
		
	}

}
