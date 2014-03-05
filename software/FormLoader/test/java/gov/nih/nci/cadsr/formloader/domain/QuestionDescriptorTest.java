package gov.nih.nci.cadsr.formloader.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class QuestionDescriptorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testHasVDValueInDE() {
		
		QuestionDescriptor question = new QuestionDescriptor();
		assertFalse(question.hasVDValueInDE());
		
		question.setDatatypeName("CHARACTER");
		assertTrue(question.hasVDValueInDE());
	}
	
	@Test
	public void testAllPresentedVdFieldsMatched() {
		QuestionDescriptor question = new QuestionDescriptor();
		
		assertTrue(question.allPresentedVdFieldsMatched("sfafa", "sfafa", "sfafa", "sfafa", null, null, "sfafa", null));
		question.setDatatypeName("CHARACTE");
		assertFalse(question.allPresentedVdFieldsMatched("CHARACTER", "sfafa", "sfafa", "sfafa", null, null, "sfafa", null));
		
		question.setDatatypeName("CHARACTER");
		assertTrue(question.allPresentedVdFieldsMatched("CHARACTER", "sfafa", "sfafa", "sfafa", null, null, "sfafa", null));
		
		question.setDatatypeName("CHARACTER");
		assertFalse(question.allPresentedVdFieldsMatched(null, "sfafa", "sfafa", "sfafa", null, null, "sfafa", null));
		
		question.setMaximumLengthNumber("35");
		assertTrue(question.allPresentedVdFieldsMatched("CHARACTER", "sfafa", "sfafa", "sfafa", null, "35", "sfafa", null));
		
		question.setMinimumLengthNumber("");
		assertTrue(question.allPresentedVdFieldsMatched("CHARACTER", "sfafa", "sfafa", "sfafa", null, "35", "sfafa", null));
		
		question.setMinimumLengthNumber("1");
		assertTrue(question.allPresentedVdFieldsMatched("CHARACTER", "sfafa", "sfafa", "sfafa", null, "35", "1", null));
	}

}
