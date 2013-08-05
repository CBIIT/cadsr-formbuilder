/*L
 * Copyright Oracle Inc, ScenPro Inc, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
 */

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
