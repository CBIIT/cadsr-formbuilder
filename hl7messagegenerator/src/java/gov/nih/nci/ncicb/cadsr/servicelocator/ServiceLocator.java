package gov.nih.nci.ncicb.cadsr.servicelocator;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.applicationservice.ApplicationServiceProvider;

public class ServiceLocator {
    private String caCOREUrl;
    
    public ServiceLocator() {
    }

    /**
     * @return caDSR Public API ApplicationService
     * @throws ServiceLocatorException
     */
    public ApplicationService getCaDSRPublicApiService() throws ServiceLocatorException {
       ApplicationService appService = null;
       try
       {
          appService = ApplicationServiceProvider.getRemoteInstance(getCaCOREUrl());
       }
       catch (Exception e){
           throw new ServiceLocatorException("Error getting application service", e);
       }
        return appService;
    }

    public void setCaCOREUrl(String caCOREUrl) {
        this.caCOREUrl = caCOREUrl;
    }

    public String getCaCOREUrl() {
        return caCOREUrl;
    }
}
