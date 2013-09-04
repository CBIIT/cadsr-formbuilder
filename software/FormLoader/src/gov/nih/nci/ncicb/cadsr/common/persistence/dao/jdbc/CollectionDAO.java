package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.cadsr.formloader.domain.FormCollection;

import java.util.List;

public interface CollectionDAO {
	
	public String createCollectionRecord(String name, String desc, String fileName, String filePath, String createdBy);
	public int createCollectionFormMappingRecord(String collectionseqid, String formseqid, 
			int formpublicid, float formversion, String loadType);
	
	public List<FormCollection> getAllLoadedCollections();
}
