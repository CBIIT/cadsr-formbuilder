/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/map/MapReportGenerator.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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

import gov.nih.nci.hl7.clone.meta.*;
import gov.nih.nci.hl7.common.MetaLookup;
import gov.nih.nci.hl7.common.MetaObject;
import gov.nih.nci.hl7.csv.CSVMetaLookup;
import gov.nih.nci.hl7.csv.meta.CSVFieldMeta;
import gov.nih.nci.hl7.csv.meta.CSVMeta;
import gov.nih.nci.hl7.csv.meta.CSVSegmentMeta;
import gov.nih.nci.hl7.function.FunctionMetaLookup;
import gov.nih.nci.hl7.function.ParameterMeta;
import gov.nih.nci.hl7.map.components.BaseComponent;
import gov.nih.nci.hl7.map.components.FunctionComponent;
import gov.nih.nci.hl7.map.impl.BaseMapElementImpl;
import gov.nih.nci.hl7.util.Config;
import gov.nih.nci.hl7.util.Log;
import gov.nih.nci.hl7.util.MessageResources;
import gov.nih.nci.hl7.validation.Message;
import gov.nih.nci.hl7.validation.ValidatorResult;
import gov.nih.nci.hl7.validation.ValidatorResults;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * This class defines functions to generate a basic map report in Excel format.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:42 $
 */
public class MapReportGenerator
{
	/**
	 * Logging constant used to identify source of log entry, that could be later used to create
	 * logging mechanism to uniquely identify the logged class.
	 */
	private static final String LOGID = "$RCSfile: MapReportGenerator.java,v $";

	/**
	 * String that identifies the class version and solves the serial version UID problem.
	 * This String is for informational purposes only and MUST not be made final.
	 *
	 * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
	 */
	public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/map/MapReportGenerator.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $";

	/**
	 * Terminology used within this class:
	 * Origination: means the source of a map link, could be a Source, a Function's output;
	 * Destination: means the target of a map link, could be a Target, a Function's input;
	 *
	 * Source: refer to the data source that the most map is generated from;
	 * Target: refer to the data source that the most map is pointed to;
	 */

	public static final String MAPPED_WORKSHEET_TITLE = "Mapped";
	public static final String UNMAPPED_WORKSHEET_TITLE = "Unmapped";

	public static final String SOURCE_TITLE = "Source";
	public static final String TARGET_TITLE = "Target";
	public static final String FUNCTION_TITLE = "Function";

	public static final String MAPPED_SOURCE_TO_FUNCTION_TYPE = "(" + SOURCE_TITLE + "_" + FUNCTION_TITLE + ")";
	public static final String MAPPED_SOURCE_TO_TARGET_TYPE = "(" + SOURCE_TITLE + "_" + TARGET_TITLE + ")";
	public static final String MAPPED_FUNCTION_TO_TARGET_TYPE = "(" + FUNCTION_TITLE + "_" + TARGET_TITLE + ")";
	public static final String MAPPED_FUNCTION_TO_FUNCTION_TYPE = "(" + FUNCTION_TITLE + "_" + FUNCTION_TITLE + ")";

	public static final String UNMAPPED_SOURCE_TITLE = UNMAPPED_WORKSHEET_TITLE + "_" + SOURCE_TITLE;
	public static final String UNMAPPED_TARGET_TITLE = UNMAPPED_WORKSHEET_TITLE + "_" + TARGET_TITLE;

//	public static final String MAPPED_SOURCE_TO_TARGET_TYPE = "(Source-Target)";
//	public static final String MAPPED_FUNCTION_TO_TARGET_TYPE = "(Function-Target)";
//	public static final String MAPPED_FUNCTION_TO_FUNCTION_TYPE = "(Function-Function)";

	private HSSFWorkbook workbook = null;

	private HSSFSheet sourceToTargetWorksheet = null;
	private HSSFSheet sourceToFunctionWorksheet = null;
	private HSSFSheet functionToFunctionWorksheet = null;
	private HSSFSheet functionToTargetWorksheet = null;

	private HashMap<String, Integer> sheetRowCount;

//	private int sourceToTargetRowCount = 0;
//	private int sourceToFunctionRowCount = 0;
//	private int functionToFunctionRowCount = 0;
//	private int functionToTargetRowCount = 0;


	//so far those MetaLookup class will use UUID as the key, and the metaObject as the value.
	//key: the uuid refers to a source file, such as a CSV file, value: the CSV segment/field lookup utitlity object
	private HashMap<String, MetaLookup> sourceMetaResourceMap;
	//key: the uuid refers to a target file, such as an H3S file, value: the HL7 Clone/Attribute/Datatype Field lookup utitlity object
	private HashMap<String, MetaLookup> targetMetaResourceMap;
	//key: the uuid refers to a function meta, value: the function parameter lookup utitlity object for the given function meta
	private HashMap<String, FunctionMetaLookup> functionResourceMap;

//	//remember the mapped results by different types, so that we could figure out unmapped ones at later time.
//	private HashSet sourceToTargetMappedResult;
//	private HashSet sourceToFunctionMappedResult;
//	private HashSet functionToFunctionMappedResult;
//	private HashSet functionToTargetMappedResult;

	//remember a set of mapped source and target items, thus, the complement of them from the set from lookup would provide a list of unmapped items.
	private HashSet mappedSourceItemSet;
	private HashSet mappedTargetItemSet;

	public MapReportGenerator()
	{
	}

	public ValidatorResults generate(File file, Mapping mappingMeta) //throws Exception
	{
		ValidatorResults validatorResults = new ValidatorResults();
		FileOutputStream fo = null;
		BufferedOutputStream bo = null;
		try
		{
			initResultStructure();
			initializeLookupMaps(mappingMeta);
			workbook = new HSSFWorkbook();
			fo = new FileOutputStream(file);
			bo = new BufferedOutputStream(fo);

			//keep this sequence intact, since unmapped generation will depend on result from mapped object generation.
			generatedMappedReport(mappingMeta);
			generateUnmappedReport(mappingMeta);

			workbook.write(bo);
			bo.flush();
		}
		catch (Exception e)
		{
			Log.logException(this, e);
			Message msg = MessageResources.getMessage("GEN0", new Object[]{e.getMessage()});
			validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.FATAL, msg));
		}
		finally
		{
			try
			{
				if(bo!=null)
				{
					bo.close();
				}
			}
			catch(Exception e)
			{//intentionally ignore
			}
		}
		return validatorResults;
	}

	private void initResultStructure()
	{
//		sourceToFunctionMappedResult = new HashSet();
//		sourceToTargetMappedResult = new HashSet();
//		functionToFunctionMappedResult = new HashSet();
//		functionToTargetMappedResult = new HashSet();

		sheetRowCount = new HashMap<String, Integer>();
		mappedSourceItemSet = new HashSet();
		mappedTargetItemSet = new HashSet();
	}

	private HashMap<String, MetaLookup> constructResourceMap(BaseComponent component) throws Exception
	{
		HashMap<String, MetaLookup> hashMap = new HashMap<String, MetaLookup>();
		String kind = component.getKind();
		if (Config.CSV_DEFINITION_DEFAULT_KIND.equalsIgnoreCase(kind))
		{
			hashMap.put(component.getUUID(), new CSVMetaLookup((CSVMeta) component.getMeta()));
		}
		else if(Config.HL7_V3_DEFINITION_DEFAULT_KIND.equals(kind))
		{
			hashMap.put(component.getUUID(), new CloneMetaLookup((HL7V3Meta) component.getMeta()));
		}
		else
		{
			Log.logWarning(this, "constructResourceMap() Don't understand the kind='" + kind + "'!");
		}
		return hashMap;
	}

	private void initializeLookupMaps(Mapping mappingMeta) throws Exception
	{
		BaseComponent component = null;
		component = mappingMeta.getSourceComponent();
		sourceMetaResourceMap = constructResourceMap(component);

		component = mappingMeta.getTargetComponent();
		targetMetaResourceMap = constructResourceMap(component);

		List functionList = mappingMeta.getFunctionComponent();
		int size = functionList==null ? 0 : functionList.size();
		for(int i=0; i<size; i++)
		{
			FunctionComponent comp = (FunctionComponent) functionList.get(i);
			functionResourceMap = new HashMap<String, FunctionMetaLookup>();
			functionResourceMap.put(comp.getUUID(), new FunctionMetaLookup(comp.getMeta()));
		}
	}

	private void generatedMappedReport(Mapping mappingMeta)
	{
		List<Map> mappingLinkList = mappingMeta.getMaps();
		int size = mappingLinkList==null ? 0 : mappingLinkList.size();
		for(int i=0; i<size; i++)
		{
			Map oneLink = mappingLinkList.get(i);
			BaseMapElement sourceMapElement = oneLink.getSourceMapElement();
			BaseMapElement targetMapElement = oneLink.getTargetMapElement();

			HashSet actualMappedOriginationSet = null;
			HashSet actualMappedDestinationSet = null;
//			int referenceToActualRowCount = 0;
			HSSFSheet worksheet = null;
			String firstRowText = null;
			boolean toPrintHeader = false;
			if(sourceMapElement.isComponentOfSourceType() && targetMapElement.isComponentOfTargetType())
			{//direct mapping
//				resultSet = sourceToTargetMappedResult;
				firstRowText = MAPPED_WORKSHEET_TITLE + MAPPED_SOURCE_TO_TARGET_TYPE;
				if (sourceToTargetWorksheet == null)
				{
					sourceToTargetWorksheet = workbook.createSheet(firstRowText);
					toPrintHeader = true;
				}
				actualMappedOriginationSet = mappedSourceItemSet;
				actualMappedDestinationSet = mappedTargetItemSet;
				worksheet = sourceToTargetWorksheet;
			}
			else if(sourceMapElement.isComponentOfSourceType() && targetMapElement.isComponentOfFunctionType())
			{
//				resultSet = sourceToFunctionMappedResult;
				firstRowText = MAPPED_WORKSHEET_TITLE + MAPPED_SOURCE_TO_FUNCTION_TYPE;
				if (sourceToFunctionWorksheet == null)
				{
					sourceToFunctionWorksheet = workbook.createSheet(firstRowText);
					toPrintHeader = true;
				}
				actualMappedOriginationSet = mappedSourceItemSet;
				actualMappedDestinationSet = null;
				worksheet = sourceToFunctionWorksheet;
			}
			else if(sourceMapElement.isComponentOfFunctionType() && targetMapElement.isComponentOfFunctionType())
			{
//				resultSet = functionToFunctionMappedResult;
				firstRowText = MAPPED_WORKSHEET_TITLE + MAPPED_FUNCTION_TO_FUNCTION_TYPE;
				if (functionToFunctionWorksheet == null)
				{
					functionToFunctionWorksheet = workbook.createSheet(firstRowText);
					toPrintHeader = true;
				}
				actualMappedOriginationSet = null;
				actualMappedDestinationSet = null;
				worksheet = functionToFunctionWorksheet;
			}
			else
			{//by default, the function to target mapping
//				resultSet = functionToTargetMappedResult;
				firstRowText = MAPPED_WORKSHEET_TITLE + MAPPED_FUNCTION_TO_TARGET_TYPE;
				if (functionToTargetWorksheet == null)
				{
					functionToTargetWorksheet = workbook.createSheet(firstRowText);
					toPrintHeader = true;
				}
				actualMappedOriginationSet = null;
				actualMappedDestinationSet = mappedTargetItemSet;
				worksheet = functionToTargetWorksheet;
			}

			if(toPrintHeader)
			{//first time creation.
				printHeading(worksheet, firstRowText, true);
			}
			printMappedContent(worksheet, actualMappedOriginationSet, actualMappedDestinationSet, sourceMapElement, targetMapElement, firstRowText);
		}
	}

	/**
	 * Do not generate unmapped function parameter here, since the structure is different.
	 */
	private void generateUnmappedReport(Mapping mappingMeta)
	{
		BaseComponent component = null;
		BaseMapElement mapElement = null;

		//handle source.
		component = mappingMeta.getSourceComponent();
		//just for query purpose by utilizing its internal built utility functions
		mapElement = new BaseMapElementImpl(component, null);
		printUnmappedContent(UNMAPPED_SOURCE_TITLE, UNMAPPED_SOURCE_TITLE, mapElement, sourceMetaResourceMap, mappedSourceItemSet);

		//handle target.
		component = mappingMeta.getTargetComponent();
		//just for query purpose by utilizing its internal built utility functions
		mapElement = new BaseMapElementImpl(component, null);
		printUnmappedContent(UNMAPPED_TARGET_TITLE, UNMAPPED_TARGET_TITLE, mapElement, targetMetaResourceMap, mappedTargetItemSet);
	}

	private void printUnmappedContent(String worksheetTitle, String firstRowText, BaseMapElement mapElement, HashMap<String, MetaLookup> resourceMap, HashSet mappedSet)
	{
		BaseComponent component = mapElement.getComponent();
		MetaLookup metaLookup = resourceMap.get(component.getUUID());
		HashSet allSet = null;
		if(metaLookup==null)
		{
			allSet = new HashSet();
		}
		else
		{
			allSet = new HashSet(metaLookup.getAllKeys());
		}
		allSet.removeAll(mappedSet);
		if(!allSet.isEmpty())
		{
			HSSFSheet worksheet = workbook.createSheet(worksheetTitle);
			printHeading(worksheet,  firstRowText, false);
			Iterator it = allSet.iterator();
			int rowCount = sheetRowCount.get(firstRowText).intValue();
			HSSFRow row = null;
			while (it.hasNext())
			{
				String uuid = (String) it.next();
				MetaObject metaObject = findMetaObject(uuid, mapElement);
				if (metaObject != null)
				{
					String pathName = convertToPathName(getPathToRoot(metaObject));
					row = worksheet.createRow(rowCount);
					HSSFCell cell = row.createCell((short) 0);
					cell.setCellValue(pathName);
					rowCount++;
				}
			}
		}
	}

	/**
	 * Print once and only once when the worksheet is created.
	 * @param worksheet
	 */
	private void printHeading(HSSFSheet worksheet, String firstRowText, boolean mappedHeading)
	{
		HSSFRow row = worksheet.createRow(0);
		HSSFCell cell = null;
		cell = row.createCell((short) 0);
		cell.setCellValue(firstRowText);
		if(mappedHeading)
		{
			row = worksheet.createRow(1);
			cell = row.createCell((short) 0);
			cell.setCellValue("Path of Origination");
			cell = row.createCell((short) 1);
			cell.setCellValue("Path of Destination");
			sheetRowCount.put(firstRowText, new Integer(2));
		}
		else
		{
			sheetRowCount.put(firstRowText, new Integer(1));
		}
	}

	private void printMappedContent(HSSFSheet worksheet, HashSet mappedOriginationSet, HashSet mappedDestinationSet, BaseMapElement originationMapElement, BaseMapElement destinationMapElement, String keyToRowCount)
	{
		MetaObject originationMetaObject = originationMapElement.getMetaObject();
		MetaObject destinationMetaObject = destinationMapElement.getMetaObject();
		String originationPathName = convertToPathName(getPathToRoot(originationMetaObject));
		String destinationPathName = convertToPathName(getPathToRoot(destinationMetaObject));

		Integer rowCount = sheetRowCount.get(keyToRowCount);
		HSSFRow row = worksheet.createRow(rowCount.intValue());
		HSSFCell cell = null;
		cell = row.createCell((short) 0);
		cell.setCellValue(originationPathName);
		cell = row.createCell((short) 1);
		cell.setCellValue(destinationPathName);

		//update the row count
		Integer updatedRowCount = new Integer(rowCount.intValue() + 1);
		sheetRowCount.put(keyToRowCount, updatedRowCount);

		if(mappedOriginationSet!=null)
		{
			mappedOriginationSet.add(originationMetaObject.getUUID());
		}

		if(mappedDestinationSet!=null)
		{
			mappedDestinationSet.add(destinationMetaObject.getUUID());
		}
	}

	/**
	 * Will return in format of "GrandParent.Parent.Child.GrandChild" format, if it is a root, just return itself.
	 * @param metaObjectList
	 * @return the path name.
	 */
	private String convertToPathName(List<MetaObject> metaObjectList)
	{
		int size = metaObjectList==null?0 : metaObjectList.size();
		StringBuffer buf = new StringBuffer();
		for(int i=0; i<size; i++)
		{
			//use the toString() instead of getName()
			buf.append(metaObjectList.get(i).toString());
			if(i!=size-1)
			{
				buf.append(".");
			}
		}
		return buf.toString();
	}

	/**
	 *
	 * @param metaObject
	 * @return a list of metaobject, root is the first object.
	 */
	private List<MetaObject> getPathToRoot(MetaObject metaObject)
	{
		List<MetaObject> resultList = new ArrayList<MetaObject>();

		if(metaObject==null)
		{//immediately return
			return resultList;
		}
		MetaObject parentObject = null;
		if(metaObject instanceof CSVSegmentMeta)
		{
			parentObject = ((CSVSegmentMeta) metaObject).getParent();
		}
		else if(metaObject instanceof CSVFieldMeta)
		{
			parentObject = ((CSVFieldMeta) metaObject).getSegment();
		}
		else if(metaObject instanceof CloneMeta)
		{
			parentObject = ((CloneMeta) metaObject).getParentMeta();
		}
		else if(metaObject instanceof CloneAttributeMeta)
		{
			parentObject = ((CloneAttributeMeta) metaObject).getParentMeta();
		}
		else if(metaObject instanceof CloneDatatypeFieldMeta)
		{
			parentObject = ((CloneDatatypeFieldMeta) metaObject).getParentMeta();
		}
		else if(metaObject instanceof ParameterMeta)
		{
			parentObject = ((ParameterMeta) metaObject).getFunctionMeta();
		}
		resultList.addAll(getPathToRoot(parentObject));
		resultList.add(metaObject);
		return resultList;
	}


	/**
	 * This is called by unmapped search. The uuid given should be used as the key to locate the meta object,
	 * the mapElement given is just to locate the type of resource map to look up into.
	 * @param uuid
	 * @param mapElement
	 * @return the meta object, could be null if nothing is found.
	 */
	private MetaObject findMetaObject(String uuid, BaseMapElement mapElement)
	{
		MetaObject metaObject = null;
		BaseComponent component = mapElement.getComponent();
		if(component==null)
		{
			return null;
		}
		String uuidKey = component.getUUID();
		MetaLookup lookup = null;
		if(mapElement.isComponentOfSourceType())
		{
			lookup = sourceMetaResourceMap.get(uuidKey);
		}
		else if(mapElement.isComponentOfTargetType())
		{
			lookup = targetMetaResourceMap.get(uuidKey);
		}
		else if(mapElement.isComponentOfFunctionType())
		{
			lookup = functionResourceMap.get(uuidKey);
		}

		if(lookup!=null)
		{
			metaObject = lookup.lookup(uuid);
		}

		return metaObject;
	}
}
/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.9  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.8  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.7  2006/01/03 18:56:24  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/12/29 23:06:13  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/12/19 22:44:16  jiangsc
 * HISTORY      : Feature enhancement
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/12/16 21:02:22  jiangsc
 * HISTORY      : Enhanced code on 
 * HISTORY      : 0) changes from using getName() to toString() to be more illustrative;
 * HISTORY      : 1) removal of duplications;
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/12/09 20:12:07  jiangsc
 * HISTORY      : implemented the functionality.
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/12/08 23:52:32  jiangsc
 * HISTORY      : Updated.
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/12/08 23:22:43  jiangsc
 * HISTORY      : Upgrade the handleValidatorResults() function.
 * HISTORY      :
 */
