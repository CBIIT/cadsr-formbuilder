package gov.nih.nci.cadsr.formloader.service.common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FormLoaderHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNormalizeSpace() {
		String in = "Unrelated; not connected or   associated e.g. by kinship.: Taken from different individuals of the same species.  Also called allogeneic  ";
		String expected = "Unrelated; not connected or associated e.g. by kinship.: Taken from different individuals of the same species. Also called allogeneic";
		String out = FormLoaderHelper.normalizeSpace(in);
		System.out.println(">" + out + "<");
		assertTrue(out.equals(expected));
	}

	@Test
	public void testNormalizeSpaceWithNullInput() {
		String out = FormLoaderHelper.normalizeSpace(null);
		assertNull(out);
	}
}
