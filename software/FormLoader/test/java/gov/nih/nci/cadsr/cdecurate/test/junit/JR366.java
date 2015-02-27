package gov.nih.nci.cadsr.cdecurate.test.junit;

import static org.junit.Assert.assertTrue;
import gov.nih.nci.cadsr.formloader.domain.DomainObjectTranslator;
import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.cadsr.formloader.domain.ModuleDescriptor;
import gov.nih.nci.ncicb.cadsr.common.dto.FormV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ModuleTransferObject;

import org.junit.Test;

public class JR366 {

	@Test
	public void testNumberOfRepeats() {
		ModuleDescriptor module = new ModuleDescriptor();
		FormDescriptor form = new FormDescriptor();
		FormV2TransferObject formdto = new FormV2TransferObject();
		ModuleTransferObject moduledto = null;
		
		module.setMaximumModuleRepeat("2");
		moduledto = DomainObjectTranslator.translateIntoModuleDTO(module, form, formdto);
		assertTrue("Test NumberOfRepeats", Integer.valueOf(module.getMaximumModuleRepeat()) == moduledto.getNumberOfRepeats());

		module.setMaximumModuleRepeat("");
		moduledto = DomainObjectTranslator.translateIntoModuleDTO(module, form, formdto);
		assertTrue("Test NumberOfRepeats", moduledto.getNumberOfRepeats() == 0);
	}

}