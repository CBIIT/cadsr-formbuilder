package gov.nih.nci.ncicb.cadsr.serviceimpl;

import gov.nih.nci.ncicb.cadsr.constants.EDCIConfiguration;
import gov.nih.nci.ncicb.cadsr.edci.domain.Instrument;

import gov.nih.nci.ncicb.cadsr.service.QueryMetadataService;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import java.net.URL;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;

import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;

import org.exolab.castor.xml.Unmarshaller;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import org.w3c.dom.Document;

public class CSVFileGenerator {
    Logger logger = LogManager.getLogger(CSVFileGenerator.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    Transformer eDCIToCSV = null;
    public CSVFileGenerator() {
    }
    
    public Document getIntermediateDocument(Instrument instrument) throws Exception {
      try {
          DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
          Document document = documentBuilder.newDocument();
          Mapping mapping = new Mapping();
          URL mpurl = mapping.getClass().getClassLoader().getResource("mapping.xml");
          mapping.loadMapping(mpurl);          
          Marshaller marshaller = new Marshaller(document);
          marshaller.setMapping(mapping);
          marshaller.marshal(instrument);
          return document;
      }
      catch(Exception e) {
          logger.error(e);
          throw e;
      }
        
    }
    
  public Transformer geteDCIToCSVTransformer() throws Exception {
      try  {
       if (eDCIToCSV == null)
       {
          EDCIConfiguration config = EDCIConfiguration.getInstance();
          URL stlurl = getClass().getResource(config.getProperty("eDCIToCSVStyleSheet"));
          if (stlurl == null) {
              File styleSheet= new File(config.getProperty("eDCIToCSVStyleSheet"));
              if (styleSheet.exists()) {
                 stlurl = styleSheet.toURL();          
              }
              else {
                  throw new Exception("Stylesheet to convert eDCI to csv not found. "+config.getProperty("eDCIToCSVStyleSheet"));
              }
          }  
          Source source = new StreamSource(stlurl.openStream());
          eDCIToCSV = TransformerFactory.newInstance().newTransformer(source);          
       }
       return eDCIToCSV;
      } catch (Exception ex)  {
          logger.error(ex);
          throw (ex);
      } 
   }
    
public File getCSVFile(Instrument instrument) throws Exception {
     try {
         Document intermediateDocument = getIntermediateDocument(instrument);
         File file = applyStyleSheet(intermediateDocument, instrument);
         return file;
     }
     catch(Exception e) {
        logger.error(e);
        throw e;
    }

}
    
public String getCSVFileName(Instrument instrument) {
    Date date = new Date();
    String  guid = instrument.getGUID();
    StringTokenizer st = new StringTokenizer(guid,"^");
    String formId ="";
    if (st.countTokens()>1) {
        st.nextToken();
        formId = st.nextToken();
    }
    
    return formId+"_"+sdf.format(date)+".csv";
}
    
public File applyStyleSheet(Document document, Instrument instrument)    throws Exception {
  try{
   Transformer trns= geteDCIToCSVTransformer();
   Document odoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
   Source ds = new DOMSource(document);
   EDCIConfiguration config = EDCIConfiguration.getInstance();
   File file= new File(config.getProperty("csvFileLocation")+getCSVFileName(instrument));
   trns.transform (ds, new StreamResult(file));
   return file;
  }     
  catch(Exception e) {
          logger.error(e);
          throw e;
      }

}
    
public static void main(String[] args){
       try {

           String formIdSeq="1B4FBBDD-9FD4-5F94-E044-0003BA0B1A09";
           ClassPathResource resource = new ClassPathResource("beans.xml");
           BeanFactory beanFactory = new XmlBeanFactory(resource);           
           QueryMetadataService queryMetadataService = (QueryMetadataService)beanFactory.getBean("queryMetadataService");
           CSVFileGenerator csvFileGenerator = new CSVFileGenerator();
           
           Instrument instrument =queryMetadataService.getInstrumentMetaData(formIdSeq);
           System.out.println(csvFileGenerator.getCSVFile(instrument));
            
           
                     
       }
       catch (Exception e){
           e.printStackTrace();
       }
        
    }
}
