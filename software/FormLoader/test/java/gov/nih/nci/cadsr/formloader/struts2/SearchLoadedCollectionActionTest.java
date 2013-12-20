package gov.nih.nci.cadsr.formloader.struts2;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;
import gov.nih.nci.cadsr.formloader.service.common.FormLoaderHelper;

import java.util.List;

import org.apache.struts2.StrutsSpringTestCase;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
//import org.apache.struts2.StrutsJUnit4TestCase;

public class SearchLoadedCollectionActionTest extends StrutsSpringTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	 public void testGetActionMapping() {
	        ActionMapping mapping = getActionMapping("/search-form.action");
	        assertNotNull(mapping);
	        assertEquals("/", mapping.getNamespace());
	        assertEquals("createaccount", mapping.getName());
	    }

	@Test
	public void testExecute() {

    	ActionProxy proxy = getActionProxy("/searchFormCollections.action");

    	SearchLoadedCollectionAction accountAction = (SearchLoadedCollectionAction) proxy.getAction();

    	try {
    		List<FormCollection> colls = FormLoaderHelper.readCollectionListFromFile();
    		//TODO: manipulate forms in colls a bit for goot test
    		
    		accountAction.convertCollectionsToUnloadedFormList(colls);

        assertTrue("Problem There were no errors present in fieldErrors but there should have been one error present", accountAction.getFieldErrors().size() == 1);
		assertTrue("Problem field account.userName not present in fieldErrors but it should have been",
				accountAction.getFieldErrors().containsKey("accountBean.userName") );
    	} catch (Exception e) {
    		fail(e.getMessage());
    	}
	}

}
