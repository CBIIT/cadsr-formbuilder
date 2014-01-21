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
	public void testValidateFile() {
		//String xml = "C:\\development\\workspace-formloader-git\\cadsr-formbuilder\\software\\FormLoader\\test\\data\\xmlvalidation\\FourTheradexMedidataRaveFormsMinimumFields_01-11-2014.xml";
		String xsd = "C:\\development\\doc\\FormLoader\\xsds\\FormLoaderv12-0120.xsd";
		
		String xml = "C:\\development\\workspace-formloader-git\\cadsr-formbuilder\\software\\FormLoader\\test\\data\\xmlvalidation\\load_forms-5.xml";
		
		try {
			List<XmlValidationError> errors = FormLoaderHelper.validateFile(new File(xml), new File(xsd));
			
			if (errors != null) {
				for (XmlValidationError error : errors) 
					System.out.println(error.getMessage());
			}
		} catch (SAXException s) {
			System.out.println(s.getStackTrace());
		} catch (IOException s) {
			System.out.println(s.getStackTrace());
		}
	}
}
