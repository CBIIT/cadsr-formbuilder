package gov.nih.nci.ncicb.cadsr.serviceimpl;


import gov.nih.nci.ncicb.cadsr.edci.domain.GlobalDefinitions;
import gov.nih.nci.ncicb.cadsr.edci.domain.Instrument;
import gov.nih.nci.ncicb.cadsr.edci.domain.impl.GlobalDefinitionsImpl;
import gov.nih.nci.ncicb.cadsr.service.CaAdapterService;
import gov.nih.nci.ncicb.cadsr.service.GenerateMessageService;

import gov.nih.nci.ncicb.cadsr.service.QueryMetadataService;
import gov.nih.nci.ncicb.cadsr.service.ServiceException;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;

import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.apache.xerces.parsers.DOMParser;

import org.exolab.castor.util.LocalConfiguration;
import org.exolab.castor.xml.Marshaller;

import org.w3c.dom.Document;




public class GenerateMessageServiceImpl implements GenerateMessageService
{
    private QueryMetadataService queryMetadataService = null;
    private CaAdapterService caAdapterService = null;
    private Transformer gdToMifTransformer = null;
    private static Logger logger = LogManager.getLogger(GenerateMessageServiceImpl.class);

    
    public GenerateMessageServiceImpl() {
    }
    
    /**
     * Generates the eDCI HL7 message for the give formIdSeq.
     * @param formIdSeq
     * @return
     * @throws ServiceException
     */
    public String geteDCIHL7Message(String formIdSeq) throws ServiceException
    {
        Instrument instrument = queryMetadataService.getInstrumentMetaData(formIdSeq);
        File csvFile = generateCSVFile(instrument);
        String eDCIHL7Message = caAdapterService.generateeDCIHL7Message(csvFile);
        
        return eDCIHL7Message;
    }
    
    public String getDCIDefsMessage(String formIdSeq ) throws ServiceException {
         try {
             GlobalDefinitions globalDefinitions = queryMetadataService.getGlobalDefinitions(formIdSeq);
             return getDCIDefsMessage(globalDefinitions);
         }
         catch(Exception e) {
             logger.error("Error generating DCI def message.",e);
             throw new ServiceException("Error generating DCI def message.",e);
         }
    }

    
    protected File generateCSVFile(Instrument instrument) throws ServiceException
    {
       return null;    
    }
    
    protected Transformer getGlobalDefinitionsToMifStyleSheetTransformer() throws ServiceException
    {
       try {
          if (gdToMifTransformer == null)
          {
             TransformerFactory transformerFactory = TransformerFactory.newInstance();
             //transformerFactory.setURIResolver(new EDCIURIResolver());
             URL styleSheetUrl = getClass().getResource("/stylesheets/gd2mif.xsl");
             InputStream styleSheetStream = styleSheetUrl.openStream();
             Source styleSheet = new StreamSource(styleSheetStream);
             System.out.println("System id "+styleSheet.getSystemId());
             System.out.println("URL path "+styleSheetUrl.getPath().substring(0,styleSheetUrl.getPath().lastIndexOf("/")));
             System.out.println("URI "+styleSheetUrl.toURI().toString());
             styleSheet.setSystemId(styleSheetUrl.toURI().toString());
             Templates templates = transformerFactory.newTemplates(styleSheet);
             gdToMifTransformer = templates.newTransformer();
          }
          return gdToMifTransformer;
       }
       catch(Exception e) {
           logger.error("Error getting the GlobalDefinitionsToMIF stylesheet.", e);
           throw new ServiceException("Error getting the GlobalDefinitionsToMIF stylesheet.", e);
       }
    }
    
    protected String getDCIDefsMessage(GlobalDefinitions globalDefinitions) throws ServiceException {
        try {
            //Create the GlobalDefinitions document
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Marshaller.marshal(globalDefinitions, document);
            //Apply the stylesheet to transform the document to MIF format.
            Transformer transformer = getGlobalDefinitionsToMifStyleSheetTransformer();
            Source source = new DOMSource(document);
            transformer.transform (source, new StreamResult(System.out));

            return "";
        }
        catch (Exception e){
            logger.error("Error marshalling GlobalDefinitions.",e);
            throw new ServiceException("Error marshalling GlobalDefinitions.",e);
        }
    }
    
    public void setQueryMetadataService(QueryMetadataService queryMetadataService) {
        this.queryMetadataService = queryMetadataService;
    }

    public QueryMetadataService getQueryMetadataService() {
        return queryMetadataService;
    }

    public void setCaAdapterService(CaAdapterService caAdapterService) {
        this.caAdapterService = caAdapterService;
    }

    public CaAdapterService getCaAdapterService() {
        return caAdapterService;
    }
}
