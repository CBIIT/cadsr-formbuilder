package gov.nih.nci.cadsr.formloader.service.common;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

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
	
	@Test
	public void testSortCollectionsByDate() {
		
		long millis = System.currentTimeMillis();
		FormCollection c1 = new FormCollection();
		c1.setDateCreated(new Date(millis - 10000));
		
		FormCollection c2 = new FormCollection();
		c2.setDateCreated(new Date(millis));
		
		FormCollection c3 = new FormCollection();
		c3.setDateCreated(new Date(millis + 20000));
		
		FormCollection c4 = new FormCollection();
		c4.setDateCreated(new Date(millis - 20000));
		
		List<FormCollection> colls = new ArrayList<FormCollection>();
		colls.add(c1);
		colls.add(c2);
		colls.add(c3);
		colls.add(c4);
		
		colls = FormLoaderHelper.sortCollectionsByDate(colls);
		
		assertTrue(colls.get(0) == c4);
		
	}
	@Test
	public void testGetPropertyFromAppPath() {
		
	}
	
//Test
	public void testGetPropertyFromClasspath() {
		String prop = FormLoaderHelper.getProperty("", "upload.file.path");
		
		assertNotNull(prop);
	}
	
	@Test
	public void testFormatVersion() {
		float ver = 1;
		String str = FormLoaderHelper.formatVersion(ver);
		
		assertTrue(str.indexOf(".") == str.length() - 2);
		
		double verd = 2.3;
		str = FormLoaderHelper.formatVersion((float)verd);
		assertTrue(str.indexOf(".") == str.length() - 2);
		
		ver = 0;
		str = FormLoaderHelper.formatVersion(ver);
		assertTrue(str.indexOf(".") == str.length() - 2);
		ver = 4;
	}
	
	@Test 
	public void testCheckInputFileNullPath() {
		try {
			FormLoaderHelper.checkInputFile(null, "afafa");
			fail("Should have thrown exceptio");
		} catch (FormLoaderServiceException fse) {
			assertTrue(fse.getErrorCode() == FormLoaderServiceException.ERROR_FILE_INVALID);
		}
	}
	
	@Test 
	public void testCheckInputFileEmptyPath() {
		try {
			FormLoaderHelper.checkInputFile("", "afafa");
			fail("Should have thrown exceptio");
		} catch (FormLoaderServiceException fse) {
			assertTrue(fse.getErrorCode() == FormLoaderServiceException.ERROR_FILE_INVALID);
		}
	}
	
	@Test 
	public void testCheckInputFileNullFileName() {
		try {
			FormLoaderHelper.checkInputFile("dfafa", null);
			fail("Should have thrown exceptio");
		} catch (FormLoaderServiceException fse) {
			assertTrue(fse.getErrorCode() == FormLoaderServiceException.ERROR_FILE_INVALID);
		}
	}
	
	@Test 
	public void testResolveWindowsPathNull() {
	
		String path = FormLoaderHelper.resolveWindowsPathIfNecessary(null);
		assertNull(path);
		
	}
	
	@Test 
	public void testResolveWindowsPathAbsolutePath() {
	
		String inpath = "c://developemnt/path";
		String path = FormLoaderHelper.resolveWindowsPathIfNecessary(inpath);
		assertTrue(path.equals(inpath));
		
	}
	
	@Test 
	public void testResolveWindowsPathPathWithDot() {
	
		String inpath = ".\\test\\data\\xmlvalidation\\3256357_v1_0_newform-partial.xml";
		String path = FormLoaderHelper.resolveWindowsPathIfNecessary(inpath);
		assertTrue(path.contains(":"));
		//assertTrue(!path.contains("."));
		
	}
	
	@Test
	public void testSamePublicIdVersion() {
		assertFalse(FormLoaderHelper.samePublicIdVersions(null, "2.0", 1234567, (float)2.0));
		assertFalse(FormLoaderHelper.samePublicIdVersions("", "2.0", 1234567, (float)2.0));
		
		assertFalse(FormLoaderHelper.samePublicIdVersions("1234567", null, 1234567, (float)2.0));
		assertFalse(FormLoaderHelper.samePublicIdVersions("1234567", "", 1234567, (float)2.0));
		
		assertFalse(FormLoaderHelper.samePublicIdVersions("1234567", "2.0", -1, (float)2.0));
		assertFalse(FormLoaderHelper.samePublicIdVersions("1234567", "2.0", 1234567, (float)0.0));
		
		assertTrue(FormLoaderHelper.samePublicIdVersions("1234567", "2.0", 1234567, (float)2.0));
	}
	
}
