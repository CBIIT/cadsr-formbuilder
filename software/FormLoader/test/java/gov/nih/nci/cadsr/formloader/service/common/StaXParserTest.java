package gov.nih.nci.cadsr.formloader.service.common;

import static org.junit.Assert.*;
import gov.nih.nci.cadsr.formloader.service.common.StaXParser;

import org.junit.Before;
import org.junit.Test;

public class StaXParserTest {
	
	StaXParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new StaXParser();
	}

	@Test
	public void testParseFormCollection() {
		parser.parseFormHeaders(".\\test\\data\\forms-2.xml");
	}

}
