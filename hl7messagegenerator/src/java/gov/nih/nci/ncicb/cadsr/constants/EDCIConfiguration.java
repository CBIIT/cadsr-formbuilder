package gov.nih.nci.ncicb.cadsr.constants;

import java.io.InputStream;

import java.net.URL;

import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/**
 * A Singleton class for loading edci.properties file.
 */
public  class EDCIConfiguration {
    private static Logger logger  = LogManager.getLogger(EDCIConfiguration.class);
    private static final String resource = "/edci.properties";
    private static EDCIConfiguration instance;
    private Properties properties = null;
    
    private  EDCIConfiguration() {
    }
    
    /**
     * Load the properties file.
     */
    private void load(){
       try {
           URL resourceURL = getClass().getResource(resource);
           InputStream resourceStream = resourceURL.openStream();
           properties = new Properties();
           properties.load(resourceStream);
       }
       catch(Exception e) {
           logger.error("Error loading edci.properties file.", e);
       }
    }
    /**
     * 
     * @return
     */
    public static EDCIConfiguration getInstance() {
        if (instance == null) {
            instance = new EDCIConfiguration();
            instance.load();
        }
        return instance;
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    

}
