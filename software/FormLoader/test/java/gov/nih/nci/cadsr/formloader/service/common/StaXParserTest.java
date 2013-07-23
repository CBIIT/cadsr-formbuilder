package gov.nih.nci.cadsr.formloader.service.common;

import static org.junit.Assert.*;

import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;

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
		
		assertTrue("2321860".equals(forms.get(2).getModules().get(1).getPublicId()));
		assertTrue("2322157".equals(forms.get(2).getModules().get(2).getQuestions().get(5).getPublicId()));
		assertTrue("Results".equals(forms.get(2).getModules().get(2).getQuestions()
				.get(5).getQuestionText()));
		
		assertTrue("2+".equals(forms.get(2).getModules().get(2).getQuestions()
				.get(8).getValidValues().get(2).getValue()));
		
	}

}
