package gov.nih.nci.ncicb.cadsr.common.persistence.dao;


import gov.nih.nci.ncicb.cadsr.common.resource.ValueDomainV2;

/**
 * Mod. from  ValueDomainV2DAO
 * @author yangs8
 *
 */
public interface ValueDomainDAOV2 extends AdminComponentDAOV2 {
  
  public ValueDomainV2 getValueDomainV2ById(String vdId);
  
}
