/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/map/TransformationService.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
 *
 * ******************************************************************
 * COPYRIGHT NOTICE
 * ******************************************************************
 *
 * The caAdapter Software License, Version 1.3
 * Copyright Notice.
 * 
 * Copyright 2006 SAIC. This software was developed in conjunction with the National Cancer Institute. To the extent government employees are co-authors, any rights in such works are subject to Title 17 of the United States Code, section 105. 
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the Copyright Notice above, this list of conditions, and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * 
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 * 
 * 
 * "This product includes software developed by the SAIC and the National Cancer Institute."
 * 
 * 
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear. 
 * 
 * 3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or promote products derived from this software. 
 * 
 * 4. This license does not authorize the incorporation of this software into any third party proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or SAIC-Frederick. 
 * 
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT, THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <!-- LICENSE_TEXT_END -->
 */


package gov.nih.nci.hl7.map;

import gov.nih.nci.hl7.clone.data.HL7V3Data;
import gov.nih.nci.hl7.common.MetaException;
import gov.nih.nci.hl7.csv.CSVDataResult;
import gov.nih.nci.hl7.csv.SegmentedCSVParserImpl;
import gov.nih.nci.hl7.csv.data.CSVSegmentedFile;
import gov.nih.nci.hl7.util.*;
import gov.nih.nci.hl7.validation.Message;
import gov.nih.nci.hl7.validation.ValidatorResult;
import gov.nih.nci.hl7.validation.ValidatorResults;
import gov.nih.nci.hl7.validation.XMLValidator;
import org.hl7.meta.MessageType;
import org.hl7.meta.mif.CloneClassAdapter;
import org.hl7.rim.RimObject;
import org.hl7.xml.builder.RimGraphXMLSpeaker;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * By given csv file and mapping file, call generate method which will return the list of TransformationResult.
 *
 * @author OWNER: Matthew Giordano
 * @author LAST UPDATE $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $Date: 2006-10-03 17:38:42 $
 * @since caAdapter v1.2
 */

public class TransformationService
{
    public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/map/TransformationService.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $";
    private Stats statistics = null;

    private File mapfile = null;
    private File csvfile = null;
    private File scsfile = null;
    private Mapping mapping = null;
    private CSVSegmentedFile csvSegmentedFile = null;

    private ValidatorResults prepareValidatorResults = new ValidatorResults();

    private boolean isPrepared = false;

    public TransformationService(String mapfilename, String csvfilename)
    {
        if (mapfilename == null || csvfilename == null)
        {
            throw new IllegalArgumentException("Map File or csv File should not be null!");
        }

        this.mapfile = new File(mapfilename);
        this.csvfile = new File(csvfilename);
    }

    public TransformationService(File mapfile, File csvfile)
    {
        if (mapfile == null || csvfile == null)
        {
            throw new IllegalArgumentException("Map File or csv File should not be null!");
        }
        this.mapfile = mapfile;
        this.csvfile = csvfile;
    }

    private TransformationService()
    {
    }

    public Stats getStatistics()
    {
        return statistics;
    }

    /**
     * Return the estimated amount of time needed, in terms of seconds.
     *
     * @return the estimated amount of time needed, in terms of seconds.
     */

    public TransformationResult getEstimate() throws Exception
    {
        if (!isPrepared)
        {
            prepare();
            if (!prepareValidatorResults.isValid())
            {
                return new TransformationResult(-1, prepareValidatorResults);
            }
        }

        final int recordNumber = csvSegmentedFile.getLogicalRecords().size();

        if (recordNumber == 0)
        {
            return new TransformationResult(0, prepareValidatorResults);
        }

        long csvfileLength = csvfile.length();
        long mapfileLength = mapfile.length();
        long scsfileLength = scsfile.length();
        long h3sfileLength = new File(mapping.getTargetComponent().getFileAbsolutePath()).length();


        // maybe we could use algorithm to give a better totalfilesize
        long totalfilesize = (csvfileLength / recordNumber + mapfileLength + scsfileLength + h3sfileLength) * recordNumber;

        // based on 040001 example data
        final float quan = 2; // Assume GUI performance is quan of 040001 data
        int estimate = (int) (totalfilesize / 421943 * 3.8 * quan);

        return new TransformationResult(estimate, prepareValidatorResults);
    }

    private void prepare() throws Exception
    {
        // statistics.
        initStats();

        // parse the mapfile, if there are errors, return with validate results
        MappingResult mappingResult = parseMapfile();
        final ValidatorResults mappingValidatorResults = mappingResult.getValidatorResults();
        prepareValidatorResults.addValidatorResults(mappingValidatorResults);

        if (!mappingValidatorResults.isValid())
        {
            return;
        }

        mapping = mappingResult.getMapping();
        scsfile = new File(mapping.getSourceComponent().getFileAbsolutePath());

        // parse the datafile, if there are errors.. return.
        CSVDataResult csvDataResult = parseCsvfile();
        final ValidatorResults csvDataValidatorResults = csvDataResult.getValidatorResults();
        prepareValidatorResults.addValidatorResults(csvDataValidatorResults);

        if (!csvDataValidatorResults.isValid())
        {
            return;
        }

        csvSegmentedFile = csvDataResult.getCsvSegmentedFile();

        // set the statistics
        statistics.mapfilesize = mapfile.length();
        statistics.scsfilesize = scsfile.length();
        statistics.h3sfilesize = new File(mapping.getTargetComponent().getFileAbsolutePath()).length();
        statistics.csvfilesize = csvfile.length();

        isPrepared = true;
    }


    /**
     * @return list of MapGenerateRusult.
     */
    public List<TransformationResult> process()
    {
        return process(null);
    }

    /**
     * @return list of MapGenerateRusult.
     */
    public List<TransformationResult> process(GeneralTask task)
    {

        List<TransformationResult> v3messageResults = new ArrayList<TransformationResult>();

        try
        {
            if (!isPrepared)
            {
                prepare();
                if (!prepareValidatorResults.isValid())
                {
                    v3messageResults.add(new TransformationResult(MessageResources.getMessage("TRF2", new Object[]{}).toString(),
                        prepareValidatorResults));
                    return v3messageResults;
                }
            }

			notifyAndCheckTaskStatus(task, null);

			// process the mapfile + the datafile
            List<HL7V3DataResult> hl7V3DataResults = processMapfile(mapping, csvSegmentedFile);

			notifyAndCheckTaskStatus(task, null);

			// for each one...
            long begintime = System.currentTimeMillis();
            for (int i = 0; i < hl7V3DataResults.size(); i++)
            {
                try
                {
                    // if task is cancelled, return whatever processed so far
                    if (task != null && task.isCanceled())
                    {
						break;
//                        return v3messageResults;
                    }

                    HL7V3DataResult hl7V3DataResult = hl7V3DataResults.get(i);
                    ValidatorResults processValidatorResults = new ValidatorResults();


                    // copy over "processMapfile" validatorresults.
                    processValidatorResults.addValidatorResults(hl7V3DataResult.getValidatorResults());

					if(!notifyAndCheckTaskStatus(task, null))
					{
						break;
					}
					// generate the rim objects
                    RimGraphResult rimGraphResult = generateRimobjects(hl7V3DataResult.getHL7V3Data());
					if (!notifyAndCheckTaskStatus(task, null))
					{
						break;
					}
					RimObject rimObject = rimGraphResult.getRimGraph();
                    processValidatorResults.addValidatorResults(rimGraphResult.getValidatorResults());

                    // generate the v3 message
                    String v3message = "</>";
                    if (rimObject != null)
                    {
                        v3message = generateV3message(rimObject, HL7V3Util.getMessageType(hl7V3DataResult.getHL7V3Data().getMessageID()));
                    }
					if (!notifyAndCheckTaskStatus(task, null))
					{
						break;
					}

					XMLValidator xmlValidator = new XMLValidator(v3message, FileUtil.getSchemaFile(hl7V3DataResult.getHL7V3Data().getMessageID()));
                    long begintime2 = System.currentTimeMillis();
                    processValidatorResults.addValidatorResults(xmlValidator.validate());
                    statistics.xmlvalidate += System.currentTimeMillis() - begintime2;

                    TransformationResult oneResult = new TransformationResult(v3message, processValidatorResults);

                    synchronized (this)
                    {
                        // could add asyn object
                        if (task != null)
                        {
                            task.addTaskResult(oneResult);
                        }

                        v3messageResults.add(oneResult);
                        //System.out.println("v3message = " + v3message);
                    }
                }
                catch (Exception e)
                {
					TransformationResult oneResult = handleException(e);
					if (task != null)
					{
						task.addTaskResult(oneResult);
					}
					v3messageResults.add(oneResult);
				}
            }//end of for
            statistics.theloop += System.currentTimeMillis() - begintime;
        }
        catch (Exception e)
        {
			TransformationResult oneResult = handleException(e);
			if (task != null)
			{
				task.addTaskResult(oneResult);
			}
			v3messageResults.add(oneResult);
		}
		//do not need to set cancel here. Cancel is only set by the consumer side of the task, while done is set by the producer side.
//        finally
//        {
//            if (task != null)
//            {
//                task.setCanceled(true);
//            }
//        }

        // set the task status
        if (task != null)
        {
            task.setDone(true);
        }

        statistics.endtime = System.currentTimeMillis();
        statistics.messagecount = v3messageResults.size();
        return v3messageResults;
    }

	/**
	 * Return true if continue; otherwise, return false.
	 * @param task
	 * @param toNotifyObject could be null.
	 * @return true if continue; otherwise, return false.
	 */
	private boolean notifyAndCheckTaskStatus(GeneralTask task, Object toNotifyObject)
	{
		boolean toProceed = true;
		if(task!=null)
		{
			if(task.isCanceled() || task.isDone())
			{
				toProceed = false;
			}
			else
			{//just to notify so that the task receiver could update the UI information, if necessary
				task.addTaskResult(toNotifyObject);
			}
		}
		return toProceed;
	}

	private MappingResult parseMapfile() throws Exception
    {
        MapParserImpl parser = new MapParserImpl();
        long begintime = System.currentTimeMillis();
        MappingResult mappingResult = parser.parse(mapfile.getParent(), new FileReader(mapfile));
        statistics.mapParseTime += System.currentTimeMillis() - begintime;
        return mappingResult;
    }

    private CSVDataResult parseCsvfile() throws Exception
    {
        SegmentedCSVParserImpl parser = new SegmentedCSVParserImpl();
        String fullscmfilepath = FileUtil.filenameLocate(mapfile.getParent(), scsfile.getPath());
        long begintime = System.currentTimeMillis();
        CSVDataResult csvDataResult = parser.parse(csvfile, new File(fullscmfilepath));
        statistics.csvParseTime += System.currentTimeMillis() - begintime;
        return csvDataResult;
    }

    private List<HL7V3DataResult> processMapfile(Mapping mapping, CSVSegmentedFile csvSegmentedFile) throws Exception
    {
        MapProcessorImpl processor = new MapProcessorImpl();
        try
        {
            long begintime = System.currentTimeMillis();
            List<HL7V3DataResult> hl7V3DataResult = processor.process(mapping, csvSegmentedFile);
            statistics.mapProcessTime += System.currentTimeMillis() - begintime;
            return hl7V3DataResult;
        }
        catch (MetaException e)
        {
            Log.logException(this, e);
            throw new Exception(e);
        }
        catch (MappingException e)
        {
            Log.logException(this, e);
            throw new Exception(e);
        }
    }

    private RimGraphResult generateRimobjects(HL7V3Data hl7V3Data) throws Exception
    {
        RimGraphGen rimGraphGen = new RimGraphGen(hl7V3Data);
        long begintime = System.currentTimeMillis();
        RimGraphResult rimGraphResult = rimGraphGen.generate();
        statistics.rimGenTime += System.currentTimeMillis() - begintime;
        return rimGraphResult;
    }

    private String generateV3message(RimObject rimObject, MessageType messageType) throws Exception
    {
        long begintime = System.currentTimeMillis();
        if (rimObject == null || messageType == null)
        {
            throw new IllegalArgumentException("RimObject is null or MessageType is null");
        }

        // edit the messagType if necessary.
        if (HL7V3Util.isPrefixed(messageType.getId()))
        {
            CloneClassAdapter cc = (CloneClassAdapter) messageType.getRootClass();
            cc.setName(messageType.getId() + "." + cc.getName());
        }


        Transformer transformer =
            TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        RimGraphXMLSpeaker speaker = new RimGraphXMLSpeaker();

        Source source = new SAXSource(speaker,
            new RimGraphXMLSpeaker.InputSource(rimObject, messageType.getRootClass()));
        StringWriter sw = new StringWriter();

        try
        {
            transformer.transform(source, new StreamResult(sw));
        }
        catch (Exception e)
        {
            Log.logException(this, e);
        }
        statistics.buildTime += System.currentTimeMillis() - begintime;
        return sw.toString();
    }

    private TransformationResult handleException(Exception e) //List<TransformationResult> v3messageResults
    {
		String errorMessage = e.getMessage();
        if ((errorMessage == null) || errorMessage.equalsIgnoreCase("null"))
        {
            errorMessage = "";
        }
        Message msg = MessageResources.getMessage("GEN0", new Object[]{errorMessage});
        ValidatorResult validatorResult = new ValidatorResult(ValidatorResult.Level.FATAL, msg);
        ValidatorResults vrs = new ValidatorResults();
        vrs.addValidatorResult(validatorResult);
        TransformationResult oneResult = new TransformationResult(MessageResources.getMessage("TRF2", new Object[]{}).toString(),
            vrs);
//        v3messageResults.add(oneResult);
        Log.logException(this, e);
		return oneResult;
	}

    private void initStats()
    {
        statistics = new Stats();
        statistics.begintime = System.currentTimeMillis();
        statistics.mapfile = mapfile.getName();
        statistics.csvfile = csvfile.getName();
    }

}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.27  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.26  2006/05/04 19:41:58  chene
 * HISTORY      : Add 150003 test instance
 * HISTORY      :
 * HISTORY      : Revision 1.25  2006/01/04 18:12:57  giordanm
 * HISTORY      : remove some of the hard coded values from HL7V3Util and TransformationService - extract some of that logic to the message-types.properties file.
 * HISTORY      :
 * HISTORY      : Revision 1.24  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.23  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.22  2005/12/29 23:06:14  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.21  2005/12/29 15:39:05  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.20  2005/12/14 23:09:24  jiangsc
 * HISTORY      : Updated functionality.
 * HISTORY      :
 * HISTORY      : Revision 1.19  2005/12/07 22:00:21  jiangsc
 * HISTORY      : With enhanced functions.
 * HISTORY      :
 * HISTORY      : Revision 1.18  2005/12/07 16:43:07  jiangsc
 * HISTORY      : With enhanced functions.
 * HISTORY      :
 * HISTORY      : Revision 1.17  2005/12/06 22:55:59  chene
 * HISTORY      : Better Excepton handle for each message.
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/12/06 22:43:36  chene
 * HISTORY      : set task done status
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/12/06 15:18:01  chene
 * HISTORY      : Fix the compile error
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/12/05 23:36:34  chene
 * HISTORY      : Update estimate based on 040001 data
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/12/05 22:07:46  chene
 * HISTORY      : Integrate GeneralTask, improve the statics
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/12/05 20:52:54  jiangsc
 * HISTORY      : Added an overloaded version of process() method.
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/12/05 16:18:21  chene
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/12/05 16:14:27  chene
 * HISTORY      : Add todo list
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/12/03 16:03:15  chene
 * HISTORY      : Re-engineer TransformationService to support estimate record number
 * HISTORY      :
 */
