package gov.nih.nci.ncicb.cadsr.dao.impl;

import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

public abstract class EDCIDAOImpl {

    protected ServiceLocator serviceLocator;

    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public ServiceLocator getServiceLocator() {
        return serviceLocator;
    }
}
