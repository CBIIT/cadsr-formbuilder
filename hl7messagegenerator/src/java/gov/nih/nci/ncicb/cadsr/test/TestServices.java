package gov.nih.nci.ncicb.cadsr.test;

import gov.nih.nci.cadsr.domain.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;
import gov.nih.nci.ncicb.cadsr.dao.GlobalDefinitionsDAO;
import gov.nih.nci.ncicb.cadsr.dao.InstrumentDAO;
import gov.nih.nci.ncicb.cadsr.dto.ReferenceDocumentAttachment;
import gov.nih.nci.ncicb.cadsr.service.*;


import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import java.util.Collection;

import java.util.Date;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;

import junit.framework.TestSuite;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Define unit tests for eDCI message generator service layer.
 */
public class TestServices extends TestCase {
    BeanFactory beanFactory;

    protected static final String testInstrumentMessage ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
    "<GlobalDefinitions xmlns=\"urn:hl7-org:v3/mif\" xmlns:sch=\"http://www.ascc.net/xml/schematron\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:hl7-org:v3/mif\n" + 
    "C:\\castor-1.0.2\\eDciGlobalDefs.xsd\">\n" + 
    "    <ValueDomain Datatype=\"NUMBER\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"Numeric value to represent a course (cycle) of treatment.\"\n" + 
    "        GUID=\"A56E8150-8EB1-1CC3-E034-080020C9C0E0\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"5\"\n" + 
    "        Name=\"Course Number Number\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"DATE\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"Date as required for Clinical Trials Monitoring Service submissions.  Exchange format is YYYYMMDD.  Display format is DDMMMYYYY, with the month display the first three letters of each month (Jan, Feb, Mar, etc.).\"\n" + 
    "        GUID=\"A6545B3B-0FC3-40BF-E034-0003BA0B1A09\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"8\" Name=\"CTMS Date\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"CHARACTER\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"Four character value to represent the arm of the protocol to which a patient/participant was assigned.\"\n" + 
    "        GUID=\"A56E9E7B-DAAD-1DF9-E034-080020C9C0E0\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"4\"\n" + 
    "        Name=\"Arm Assignment Value\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"ALPHANUMERIC\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"The Value Domain of the CDUS Treatment Assignment Code at Enrollment\"\n" + 
    "        GUID=\"A55B8F01-7D07-6305-E034-080020C9C0E0\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"10\"\n" + 
    "        Name=\"CDUS Treatment Assignment Code at Enrollment\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"ALPHANUMERIC\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"CTEP-owned set of alphanumeric codes to represent institutions (that participate in clinical trial conduct or contribute patients).\"\n" + 
    "        GUID=\"A7908F69-143B-3A03-E034-0003BA0B1A09\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"6\"\n" + 
    "        Name=\"CDUS institution Text Code\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"DATE\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"Date as required for Clinical Trials Monitoring Service submissions.  Exchange format is YYYYMMDD.  Display format is DDMMMYYYY, with the month display the first three letters of each month (Jan, Feb, Mar, etc.).\"\n" + 
    "        GUID=\"A6545B3B-0FC3-40BF-E034-0003BA0B1A09\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"8\" Name=\"CTMS Date\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"DATE\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"Date as required for Clinical Trials Monitoring Service submissions.  Exchange format is YYYYMMDD.  Display format is DDMMMYYYY, with the month display the first three letters of each month (Jan, Feb, Mar, etc.).\"\n" + 
    "        GUID=\"A6545B3B-0FC3-40BF-E034-0003BA0B1A09\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"8\" Name=\"CTMS Date\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"TIME\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"Time in HR(24):Mn\"\n" + 
    "        GUID=\"A56E8150-8EAA-1CC3-E034-080020C9C0E0\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"4\" Name=\"Time\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"NUMBER\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"the measurement of an individual's height in centimeters.\"\n" + 
    "        GUID=\"B309574A-8412-0C90-E034-0003BA12F5E7\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"3\"\n" + 
    "        Name=\"Height Measurement\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"NUMBER\" DecimalPlaces=\"1\"\n" + 
    "        Description=\"the measurement of an individual's weight in kilograms.\"\n" + 
    "        GUID=\"B3094BC7-DCDF-0C8E-E034-0003BA12F5E7\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"5\"\n" + 
    "        Name=\"Weight Measurement\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"NUMBER\" DecimalPlaces=\"1\"\n" + 
    "        Description=\"the measurement of an individual's weight in kilograms.\"\n" + 
    "        GUID=\"B3094BC7-DCDF-0C8E-E034-0003BA12F5E7\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"5\"\n" + 
    "        Name=\"Weight Measurement\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"NUMBER\" DecimalPlaces=\"2\"\n" + 
    "        Description=\"the calculated measurement of the surface area of the body of a person or object.\"\n" + 
    "        GUID=\"B43A8864-FF41-21DC-E034-0003BA12F5E7\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"4\"\n" + 
    "        Name=\"Body Surface Area Measurement\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"CHARACTER\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"The functional level of the patient based on the Zubrod performance scale.\"\n" + 
    "        GUID=\"F169098A-E3A6-306E-E034-0003BA3F9857\"\n" + 
    "        IsEnumeratedFlag=\"true\" MaximumLength=\"1\"\n" + 
    "        Name=\"Zubrod Performance Status Score\" Namespace=\"NCI\">\n" + 
    "        <EVDElement value=\"0\">\n" + 
    "            <EVDElementText Language=\"en\" ValueMeaning=\"Normal Activity\" ValueMeaningDescription=\"Normal Activity\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"1\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Symptoms, but nearly fully ambulatory\" ValueMeaningDescription=\"Symptoms, but nearly fully ambulatory\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"2\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Some bed time, but needs to be in bed &lt;50% of normal daytime\" ValueMeaningDescription=\"Some bed time, but needs to be in bed &lt;50% of normal daytime\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"3\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Needs to be in bed > 50% of normal daytime\" ValueMeaningDescription=\"Needs to be in bed > 50% of normal daytime\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"4\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Unable to get out of bed\" ValueMeaningDescription=\"Unable to get out of bed\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDSubset SubsetId=\"1C331856-8725-1EB4-E044-0003BA0B1A09\">\n" + 
    "            <ElementInSubset value=\"0\" SequenceNumber=\"0\"/>\n" + 
    "            <ElementInSubset value=\"1\" SequenceNumber=\"1\"/>\n" + 
    "            <ElementInSubset value=\"2\" SequenceNumber=\"2\"/>\n" + 
    "            <ElementInSubset value=\"3\" SequenceNumber=\"3\"/>\n" + 
    "            <ElementInSubset value=\"4\" SequenceNumber=\"4\"/>\n" + 
    "        </EVDSubset>\n" + 
    "    </ValueDomain>\n" + 
    "    <ValueDomain Datatype=\"CHARACTER\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"A numeric score measure of the Karnofsky Performance status scale, representing the functional capabilities of a person.\"\n" + 
    "        GUID=\"F169098A-E270-306E-E034-0003BA3F9857\"\n" + 
    "        IsEnumeratedFlag=\"true\" MaximumLength=\"3\"\n" + 
    "        Name=\"Karnofsky Performance Status Score\" Namespace=\"NCI\">\n" + 
    "        <EVDElement value=\"100\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Normal, no complaints, no evidence of disease\" ValueMeaningDescription=\"Normal, no complaints, no evidence of disease\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"90\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Able to carry on normal activity; minor signs or symptoms of disease\" ValueMeaningDescription=\"Able to carry on normal activity; minor signs or symptoms of disease\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"80\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Normal activity with effort; some signs or symptoms of disease\" ValueMeaningDescription=\"Normal activity with effort; some signs or symptoms of disease\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"70\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Cares for self, unable to carry on normal activity or to do active work\" ValueMeaningDescription=\"Cares for self, unable to carry on normal activity or to do active work\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"60\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Requires occasional assistance, but is able to care for most of his/her needs\" ValueMeaningDescription=\"Requires occasional assistance, but is able to care for most of his/her needs\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"50\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Requires considerable assistance and frequent medical care\" ValueMeaningDescription=\"Requires considerable assistance and frequent medical care\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"40\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Disabled, requires special care and assistance\" ValueMeaningDescription=\"Disabled, requires special care and assistance\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"30\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Severely disabled, hospitalization indicated. Death not imminent\" ValueMeaningDescription=\"Severely disabled, hospitalization indicated. Death not imminent\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"20\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Very sick, hospitalization indicated. Death not imminent\" ValueMeaningDescription=\"Very sick, hospitalization indicated. Death not imminent\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"10\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Moribund, fatal processes progressing rapidly\" ValueMeaningDescription=\"Moribund, fatal processes progressing rapidly\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"0\">\n" + 
    "            <EVDElementText Language=\"en\" ValueMeaning=\"Dead\" ValueMeaningDescription=\"Dead\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDSubset SubsetId=\"1C3318D1-685E-1EC2-E044-0003BA0B1A09\">\n" + 
    "            <ElementInSubset value=\"100\" SequenceNumber=\"2\"/>\n" + 
    "            <ElementInSubset value=\"90\" SequenceNumber=\"10\"/>\n" + 
    "            <ElementInSubset value=\"80\" SequenceNumber=\"9\"/>\n" + 
    "            <ElementInSubset value=\"70\" SequenceNumber=\"8\"/>\n" + 
    "            <ElementInSubset value=\"60\" SequenceNumber=\"7\"/>\n" + 
    "            <ElementInSubset value=\"50\" SequenceNumber=\"6\"/>\n" + 
    "            <ElementInSubset value=\"40\" SequenceNumber=\"5\"/>\n" + 
    "            <ElementInSubset value=\"30\" SequenceNumber=\"4\"/>\n" + 
    "            <ElementInSubset value=\"20\" SequenceNumber=\"3\"/>\n" + 
    "            <ElementInSubset value=\"10\" SequenceNumber=\"1\"/>\n" + 
    "            <ElementInSubset value=\"0\" SequenceNumber=\"0\"/>\n" + 
    "        </EVDSubset>\n" + 
    "    </ValueDomain>\n" + 
    "    <ValueDomain Datatype=\"CHARACTER\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"Enumerated set of values representing performance status of subjects under 12 years of age according to the Lansky scale.\"\n" + 
    "        GUID=\"CFC6E87F-39DB-6F4D-E034-0003BA12F5E7\"\n" + 
    "        IsEnumeratedFlag=\"true\" MaximumLength=\"3\"\n" + 
    "        Name=\"Lansky Performance Status Score\" Namespace=\"NCI\">\n" + 
    "        <EVDElement value=\"90\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Minor restrictions in physically strenuous activity\" ValueMeaningDescription=\"Minor restrictions in physically strenuous activity\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"80\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Active, but tires more quickly\" ValueMeaningDescription=\"Active, but tires more quickly\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"70\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Both greater restriction of and less time spent in play activity\" ValueMeaningDescription=\"Both greater restriction of and less time spent in play activity\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"60\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Up and around, but minimal active play; keeps busy with quieter activities.\" ValueMeaningDescription=\"Up and around, but minimal active play; keeps busy with quieter activities.\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"50\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Gets dressed, but lies around much of the day; no active play, able to participate in all quiet play and activities.\" ValueMeaningDescription=\"Gets dressed, but lies around much of the day; no active play, able to participate in all quiet play and activities.\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"40\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Mostly in bed; participates in quiet activities.  \" ValueMeaningDescription=\"Mostly in bed; participates in quiet activities.  \"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"30\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"In bed; needs assistance even for quiet play.\" ValueMeaningDescription=\"In bed; needs assistance even for quiet play.\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"20\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Often sleeping; play entirely limited to very passive activities. \" ValueMeaningDescription=\"Often sleeping; play entirely limited to very passive activities. \"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"10\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"No play; does not get out of bed.\" ValueMeaningDescription=\"No play; does not get out of bed.\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"0\">\n" + 
    "            <EVDElementText Language=\"en\" ValueMeaning=\"Unresponsive\" ValueMeaningDescription=\"Unresponsive\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDElement value=\"100\">\n" + 
    "            <EVDElementText Language=\"en\"\n" + 
    "                ValueMeaning=\"Fully active, normal\" ValueMeaningDescription=\"Fully active, normal\"/>\n" + 
    "        </EVDElement>\n" + 
    "        <EVDSubset SubsetId=\"1C331968-FA97-1F1E-E044-0003BA0B1A09\">\n" + 
    "            <ElementInSubset value=\"90\" SequenceNumber=\"10\"/>\n" + 
    "            <ElementInSubset value=\"80\" SequenceNumber=\"9\"/>\n" + 
    "            <ElementInSubset value=\"70\" SequenceNumber=\"8\"/>\n" + 
    "            <ElementInSubset value=\"60\" SequenceNumber=\"7\"/>\n" + 
    "            <ElementInSubset value=\"50\" SequenceNumber=\"6\"/>\n" + 
    "            <ElementInSubset value=\"40\" SequenceNumber=\"5\"/>\n" + 
    "            <ElementInSubset value=\"30\" SequenceNumber=\"4\"/>\n" + 
    "            <ElementInSubset value=\"20\" SequenceNumber=\"3\"/>\n" + 
    "            <ElementInSubset value=\"10\" SequenceNumber=\"1\"/>\n" + 
    "            <ElementInSubset value=\"0\" SequenceNumber=\"0\"/>\n" + 
    "            <ElementInSubset value=\"100\" SequenceNumber=\"2\"/>\n" + 
    "        </EVDSubset>\n" + 
    "    </ValueDomain>\n" + 
    "    <ValueDomain Datatype=\"DATE\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"Date as required for Clinical Trials Monitoring Service submissions.  Exchange format is YYYYMMDD.  Display format is DDMMMYYYY, with the month display the first three letters of each month (Jan, Feb, Mar, etc.).\"\n" + 
    "        GUID=\"A6545B3B-0FC3-40BF-E034-0003BA0B1A09\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"8\" Name=\"CTMS Date\" Namespace=\"NCI\"/>\n" + 
    "    <ValueDomain Datatype=\"CHARACTER\" DecimalPlaces=\"0\"\n" + 
    "        Description=\"a free text field for general notes.\"\n" + 
    "        GUID=\"B1ED9CA0-A59B-348E-E034-0003BA12F5E7\"\n" + 
    "        IsEnumeratedFlag=\"false\" MaximumLength=\"200\"\n" + 
    "        Name=\"Comments Text\" Namespace=\"NCI\"/>\n" + 
    "    <DataElementConcept Name=\"Course  Number\"\n" + 
    "        GUID=\"A56E8150-8EB2-1CC3-E034-080020C9C0E0\" Definition=\"Number assigned to represent the progression of (sometimes repeated) periods of protocol-directed activity.\"/>\n" + 
    "    <DataElementConcept Name=\"COURSE BEGIN DATE\"\n" + 
    "        GUID=\"99BA9DC8-44F0-4E69-E034-080020C9C0E0\" Definition=\"COURSE_BEGIN_DATE\"/>\n" + 
    "    <DataElementConcept Name=\"Arm Assignment\"\n" + 
    "        GUID=\"A56E9E7B-DAAE-1DF9-E034-080020C9C0E0\" Definition=\"Protocol arm or assignment made to a person enrolled on a clinical trial.\"/>\n" + 
    "    <DataElementConcept Name=\"CDUS Treatment Assignment Code\"\n" + 
    "        GUID=\"A55B8F01-7D08-6305-E034-080020C9C0E0\" Definition=\"Code delivered by CDUS at patient enrollment onto a clinical trial, to denote treatment assignment.\"/>\n" + 
    "    <DataElementConcept Name=\"CDUS Treating Institution\"\n" + 
    "        GUID=\"A56E9E7B-DAC3-1DF9-E034-080020C9C0E0\" Definition=\"Reference to the institution in which a person is treated, using CDUS assigned values.\"/>\n" + 
    "    <DataElementConcept Name=\"Stop Date\"\n" + 
    "        GUID=\"A55B8F01-7DB4-6305-E034-080020C9C0E0\" Definition=\"End or final date of an event.\"/>\n" + 
    "    <DataElementConcept Name=\"Evaluation Date\"\n" + 
    "        GUID=\"A808AB9C-C249-398C-E034-0003BA0B1A09\" Definition=\"Date that an evaluation such as a procedure or examination is performed.\"/>\n" + 
    "    <DataElementConcept Name=\"Lab Test Time\"\n" + 
    "        GUID=\"A56E8150-8EAB-1CC3-E034-080020C9C0E0\" Definition=\"Time at which a laboratory specimen was collected.\"/>\n" + 
    "    <DataElementConcept Name=\"Height\"\n" + 
    "        GUID=\"A55B8F01-7CA2-6305-E034-080020C9C0E0\" Definition=\"Height of an individual.\"/>\n" + 
    "    <DataElementConcept Name=\"Body Weight\"\n" + 
    "        GUID=\"A55B8F01-7C99-6305-E034-080020C9C0E0\" Definition=\"Weight of a person at a specific point in time.\"/>\n" + 
    "    <DataElementConcept Name=\"Body Weight\"\n" + 
    "        GUID=\"A55B8F01-7C99-6305-E034-080020C9C0E0\" Definition=\"Weight of a person at a specific point in time.\"/>\n" + 
    "    <DataElementConcept Name=\"Patient Body Surface Area\"\n" + 
    "        GUID=\"99BA9DC8-4438-4E69-E034-080020C9C0E0\" Definition=\"the surface area of the body of the patient.\"/>\n" + 
    "    <DataElementConcept Name=\"Performance Status Assessment\"\n" + 
    "        GUID=\"F2C45BCA-74E0-7026-E034-0003BA3F9857\" Definition=\"The final result of a determination of  how well a patient is able to perform ordinary tasks and carry out daily activities.\"/>\n" + 
    "    <DataElementConcept Name=\"Performance Status Assessment\"\n" + 
    "        GUID=\"F2C45BCA-74E0-7026-E034-0003BA3F9857\" Definition=\"The final result of a determination of  how well a patient is able to perform ordinary tasks and carry out daily activities.\"/>\n" + 
    "    <DataElementConcept Name=\"Performance Status Assessment\"\n" + 
    "        GUID=\"F2C45BCA-74E0-7026-E034-0003BA3F9857\" Definition=\"The final result of a determination of  how well a patient is able to perform ordinary tasks and carry out daily activities.\"/>\n" + 
    "    <DataElementConcept Name=\"Note Date\"\n" + 
    "        GUID=\"A7350DC1-93B0-3DAE-E034-0003BA0B1A09\" Definition=\"A particular day, month and year with which a note is dated.\"/>\n" + 
    "    <DataElementConcept Name=\"Research Comments\"\n" + 
    "        GUID=\"B1ECCB2D-44AB-3496-E034-0003BA12F5E7\" Definition=\"general notes.\"/>\n" + 
    "    <DataElement Name=\"Course Number\"\n" + 
    "        GUID=\"A56E8150-8EB3-1CC3-E034-080020C9C0E0\"\n" + 
    "        DataElementConceptGUID=\"A56E8150-8EB2-1CC3-E034-080020C9C0E0\"\n" + 
    "        Definition=\"Number of the course (cycle) of treatment the patient received.\"\n" + 
    "        ValueDomainGUID=\"A56E8150-8EB1-1CC3-E034-080020C9C0E0\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Course (Cycle) #\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Course Start Date\"\n" + 
    "        GUID=\"A56E9E7B-DAA8-1DF9-E034-080020C9C0E0\"\n" + 
    "        DataElementConceptGUID=\"99BA9DC8-44F0-4E69-E034-080020C9C0E0\"\n" + 
    "        Definition=\"The begin date for a course (cycle) of a protocol.\"\n" + 
    "        ValueDomainGUID=\"A6545B3B-0FC3-40BF-E034-0003BA0B1A09\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Start Date of Course (Cycle)\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Arm\" GUID=\"A56E9E7B-DAAF-1DF9-E034-080020C9C0E0\"\n" + 
    "        DataElementConceptGUID=\"A56E9E7B-DAAE-1DF9-E034-080020C9C0E0\"\n" + 
    "        Definition=\"Protocol-specific arm assignment identified in a formal communication.\"\n" + 
    "        ValueDomainGUID=\"A56E9E7B-DAAD-1DF9-E034-080020C9C0E0\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Arm\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Treatment Assignment CDUS Code\"\n" + 
    "        GUID=\"A55B8F01-7D09-6305-E034-080020C9C0E0\"\n" + 
    "        DataElementConceptGUID=\"A55B8F01-7D08-6305-E034-080020C9C0E0\"\n" + 
    "        Definition=\"Unique CDUS numeric code identifying the type of treatment assigned for a clinical trial.\"\n" + 
    "        ValueDomainGUID=\"A55B8F01-7D07-6305-E034-080020C9C0E0\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"CDUS Treatment Assignment Code at Enrollment\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Treating Institution Text Code\"\n" + 
    "        GUID=\"A7908A05-D96D-3943-E034-0003BA0B1A09\"\n" + 
    "        DataElementConceptGUID=\"A56E9E7B-DAC3-1DF9-E034-080020C9C0E0\"\n" + 
    "        Definition=\"The unique CTEP institution code (as listed on the CTEP Internet Web site) where the patient was originally registered on study (e.g., institution where the patient signed the Informed Consent).\"\n" + 
    "        ValueDomainGUID=\"A7908F69-143B-3A03-E034-0003BA0B1A09\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"CTEP Treating Institution Code\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Stop Date\"\n" + 
    "        GUID=\"A55B8F01-7DB5-6305-E034-080020C9C0E0\"\n" + 
    "        DataElementConceptGUID=\"A55B8F01-7DB4-6305-E034-080020C9C0E0\"\n" + 
    "        Definition=\"The end date of a measure or agent.\"\n" + 
    "        ValueDomainGUID=\"A6545B3B-0FC3-40BF-E034-0003BA0B1A09\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Stop Date\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Evaluation Date\"\n" + 
    "        GUID=\"A808AB9C-C24A-398C-E034-0003BA0B1A09\"\n" + 
    "        DataElementConceptGUID=\"A808AB9C-C249-398C-E034-0003BA0B1A09\"\n" + 
    "        Definition=\"The date of the evaluation, procedure or visit that yielded data.\"\n" + 
    "        ValueDomainGUID=\"A6545B3B-0FC3-40BF-E034-0003BA0B1A09\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Eval Date\"/>\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Procedure Date\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Lab Collection Time\"\n" + 
    "        GUID=\"A56E8150-8EAC-1CC3-E034-080020C9C0E0\"\n" + 
    "        DataElementConceptGUID=\"A56E8150-8EAB-1CC3-E034-080020C9C0E0\"\n" + 
    "        Definition=\"The time when  a sample for a lab test was collected.\"\n" + 
    "        ValueDomainGUID=\"A56E8150-8EAA-1CC3-E034-080020C9C0E0\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Time\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Height in Centimeters\"\n" + 
    "        GUID=\"A55B8F01-7CA3-6305-E034-080020C9C0E0\"\n" + 
    "        DataElementConceptGUID=\"A55B8F01-7CA2-6305-E034-080020C9C0E0\"\n" + 
    "        Definition=\"Patient height measured in centimeters.\"\n" + 
    "        ValueDomainGUID=\"B309574A-8412-0C90-E034-0003BA12F5E7\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Height (cm)\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Body Weight in Kilograms\"\n" + 
    "        GUID=\"A55B8F01-7C9A-6305-E034-080020C9C0E0\"\n" + 
    "        DataElementConceptGUID=\"A55B8F01-7C99-6305-E034-080020C9C0E0\"\n" + 
    "        Definition=\"Measurement of an individual's weight in kilograms.\"\n" + 
    "        ValueDomainGUID=\"B3094BC7-DCDF-0C8E-E034-0003BA12F5E7\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Body Wt (Kg)\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Body Weight in Kilograms\"\n" + 
    "        GUID=\"A55B8F01-7C9A-6305-E034-080020C9C0E0\"\n" + 
    "        DataElementConceptGUID=\"A55B8F01-7C99-6305-E034-080020C9C0E0\"\n" + 
    "        Definition=\"Measurement of an individual's weight in kilograms.\"\n" + 
    "        ValueDomainGUID=\"B3094BC7-DCDF-0C8E-E034-0003BA12F5E7\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Body Wt (Kg)\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Patient Body Surface Area Measurement\"\n" + 
    "        GUID=\"B43B671F-EF36-2158-E034-0003BA12F5E7\"\n" + 
    "        DataElementConceptGUID=\"99BA9DC8-4438-4E69-E034-080020C9C0E0\"\n" + 
    "        Definition=\"the numeric value for the surface area of the body of the patient expressed in square meters, calculated from height and weight measurements using a standard formula.\"\n" + 
    "        ValueDomainGUID=\"B43A8864-FF41-21DC-E034-0003BA12F5E7\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"BSA\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Zubrod Performance Status Score\"\n" + 
    "        GUID=\"F2C45BCA-7A30-7026-E034-0003BA3F9857\"\n" + 
    "        DataElementConceptGUID=\"F2C45BCA-74E0-7026-E034-0003BA3F9857\"\n" + 
    "        Definition=\"The functional level of the patient based on the Zubrod performance scale.\"\n" + 
    "        ValueDomainGUID=\"F169098A-E3A6-306E-E034-0003BA3F9857\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Performance Status (Zubrod)\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Karnofsky Performance Status Score\"\n" + 
    "        GUID=\"F2C45BCA-7920-7026-E034-0003BA3F9857\"\n" + 
    "        DataElementConceptGUID=\"F2C45BCA-74E0-7026-E034-0003BA3F9857\"\n" + 
    "        Definition=\"Score from the Karnofsky Performance status scale, representing the functional capabilities of a person.\"\n" + 
    "        ValueDomainGUID=\"F169098A-E270-306E-E034-0003BA3F9857\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Performance Status (Karnofsky)\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Lansky Performance Status Score\"\n" + 
    "        GUID=\"F2C45BCA-79A4-7026-E034-0003BA3F9857\"\n" + 
    "        DataElementConceptGUID=\"F2C45BCA-74E0-7026-E034-0003BA3F9857\"\n" + 
    "        Definition=\"Numeric score representing performance status of a subject under 12 years of age according to the Lansky scale.\"\n" + 
    "        ValueDomainGUID=\"CFC6E87F-39DB-6F4D-E034-0003BA12F5E7\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Performance Status (Lansky)\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Note Date\"\n" + 
    "        GUID=\"A7350F3A-74F8-3DAC-E034-0003BA0B1A09\"\n" + 
    "        DataElementConceptGUID=\"A7350DC1-93B0-3DAE-E034-0003BA0B1A09\"\n" + 
    "        Definition=\"The date of a note captured as/on a source document.\"\n" + 
    "        ValueDomainGUID=\"A6545B3B-0FC3-40BF-E034-0003BA0B1A09\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Notes Date\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElement Name=\"Research Comments Text\"\n" + 
    "        GUID=\"B4C0EC25-415F-730B-E034-0003BA12F5E7\"\n" + 
    "        DataElementConceptGUID=\"B1ECCB2D-44AB-3496-E034-0003BA12F5E7\"\n" + 
    "        Definition=\"the free text field for general notes.\"\n" + 
    "        ValueDomainGUID=\"B1ED9CA0-A59B-348E-E034-0003BA12F5E7\" Namespace=\"NCI\">\n" + 
    "        <DataElementText Language=\"ENGLISH\" Prompt=\"Comments\"/>\n" + 
    "    </DataElement>\n" + 
    "    <DataElementGroup Name=\"COURSE INITIATION\"\n" + 
    "        GUID=\"1C331766-011D-1E93-E044-0003BA0B1A09\" Namespace=\"NCI\" Description=\"COURSE INITIATION\">\n" + 
    "        <Member DataElementGUID=\"A56E8150-8EB3-1CC3-E034-080020C9C0E0\"/>\n" + 
    "        <Member DataElementGUID=\"A56E9E7B-DAA8-1DF9-E034-080020C9C0E0\"/>\n" + 
    "        <Member DataElementGUID=\"A56E9E7B-DAAF-1DF9-E034-080020C9C0E0\"/>\n" + 
    "        <Member DataElementGUID=\"A55B8F01-7D09-6305-E034-080020C9C0E0\"/>\n" + 
    "        <Member DataElementGUID=\"A7908A05-D96D-3943-E034-0003BA0B1A09\"/>\n" + 
    "        <Member DataElementGUID=\"A55B8F01-7DB5-6305-E034-080020C9C0E0\"/>\n" + 
    "    </DataElementGroup>\n" + 
    "    <DataElementGroup Name=\"VITAL SIGNS\"\n" + 
    "        GUID=\"1C3317E6-BB93-1E99-E044-0003BA0B1A09\" Namespace=\"NCI\" Description=\"VITAL SIGNS\">\n" + 
    "        <Member DataElementGUID=\"A808AB9C-C24A-398C-E034-0003BA0B1A09\"/>\n" + 
    "        <Member DataElementGUID=\"A56E8150-8EAC-1CC3-E034-080020C9C0E0\"/>\n" + 
    "        <Member DataElementGUID=\"A55B8F01-7CA3-6305-E034-080020C9C0E0\"/>\n" + 
    "        <Member DataElementGUID=\"A55B8F01-7C9A-6305-E034-080020C9C0E0\"/>\n" + 
    "        <Member DataElementGUID=\"A55B8F01-7C9A-6305-E034-080020C9C0E0\"/>\n" + 
    "        <Member DataElementGUID=\"B43B671F-EF36-2158-E034-0003BA12F5E7\"/>\n" + 
    "        <Member DataElementGUID=\"F2C45BCA-7A30-7026-E034-0003BA3F9857\"/>\n" + 
    "        <Member DataElementGUID=\"F2C45BCA-7920-7026-E034-0003BA3F9857\"/>\n" + 
    "        <Member DataElementGUID=\"F2C45BCA-79A4-7026-E034-0003BA3F9857\"/>\n" + 
    "    </DataElementGroup>\n" + 
    "    <DataElementGroup Name=\"COMMENTS\"\n" + 
    "        GUID=\"1C331A02-45FE-1F32-E044-0003BA0B1A09\" Namespace=\"NCI\" Description=\"COMMENTS\">\n" + 
    "        <Member DataElementGUID=\"A7350F3A-74F8-3DAC-E034-0003BA0B1A09\"/>\n" + 
    "        <Member DataElementGUID=\"B4C0EC25-415F-730B-E034-0003BA12F5E7\"/>\n" + 
    "    </DataElementGroup>\n" + 
    "</GlobalDefinitions>"; 
    
    protected static String formIdSeq="1B4FBBDD-9FD4-5F94-E044-0003BA0B1A09";
    protected static String instrumentRefDocIdSeq="1DF7D85C-CF48-36EB-E044-0003BA0B1A09";
    protected static String refDocName = "2353509:v1.0:20060921:10:43:08";
    public TestServices() {
    }
    
    public TestServices(String method) {
      super(method);
    }    
    
    public void tearDown() {
      beanFactory = null;    
    }
    
    public void setUp() {
       ClassPathResource resource = new ClassPathResource("beans.xml");
       beanFactory = new XmlBeanFactory(resource);
    }
    
    
    public void testQueryMetadataService() {
      try {
        QueryMetadataService queryMetadataService = (QueryMetadataService)beanFactory.getBean("queryMetadataService");
        queryMetadataService.getInstrumentMetaData(formIdSeq);
      }
      catch(Exception e){
          e.printStackTrace();
          fail(e.getMessage());
      }
    }
    
    public void testQueryInstrumentMetadata() {
      try {
        QueryMetadataService queryMetadataService = (QueryMetadataService)beanFactory.getBean("queryMetadataService");
        queryMetadataService.getInstrumentMetaData(formIdSeq);
      }
      catch(Exception e){
          e.printStackTrace();
          fail(e.getMessage());
      }
    }
    
    public void testQueryGlobalDefinitionMetadata() {
      try {
        QueryMetadataService queryMetadataService = (QueryMetadataService)beanFactory.getBean("queryMetadataService");
        queryMetadataService.getGlobalDefinitions(formIdSeq);
      }
      catch(Exception e){
          e.printStackTrace();
          fail(e.getMessage());
      }
    }
    
    public void testStoreInstrumentMessage() {
        try {
            EDCIDAOFactory daoFactory = (EDCIDAOFactory)beanFactory.getBean("daoFactory");
            InstrumentDAO instrumentDAO = daoFactory.getInstrumentDAO();
            String rfIdSeq = instrumentDAO.storeInstrumentHL7Message(formIdSeq,testInstrumentMessage, new Date(),"user");
            System.out.println("Reference Document idSeq ->"+rfIdSeq);
        }
        catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    public void testQueryInstrumentMessage() {
        try {
            EDCIDAOFactory daoFactory = (EDCIDAOFactory)beanFactory.getBean("daoFactory");
            InstrumentDAO instrumentDAO = daoFactory.getInstrumentDAO();
            ReferenceDocumentAttachment rda = instrumentDAO.queryInstrumentHL7Message(instrumentRefDocIdSeq);
            System.out.println(rda.toString());
        }
        catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    public void testQueryReferenceDocuments() {
         try {
             EDCIDAOFactory daoFactory = (EDCIDAOFactory)beanFactory.getBean("daoFactory");
             InstrumentDAO instrumentDAO = daoFactory.getInstrumentDAO();
             Collection refDocs =instrumentDAO.queryFormMessageReferenceDocuments(formIdSeq);
             for (Iterator i= refDocs.iterator(); i.hasNext();) {
                 ReferenceDocument rd = (ReferenceDocument)i.next();
                 System.out.println("Id -> "+rd.getId());
                 System.out.println("Type -> "+rd.getType());
                 System.out.println("Name -> "+rd.getName());
             }
         }
        catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }         
    }
    
    public void testGenerateDCIDefMessage() {
      try {
        GenerateMessageService generateMessageService = new ServiceLocator().getGenerateMessageService();
        String message = generateMessageService.generateMessage(formIdSeq,"user", generateMessageService.GLOBAL_DEFINITIONS_MIF);
        System.out.println("Message :\n"+message);
      }
      catch(Exception e){
          e.printStackTrace();
          fail(e.getMessage());
      }
    }

    public void testStoreGlobalDefinitionMIFMessage() {
        try {
            EDCIDAOFactory daoFactory = (EDCIDAOFactory)beanFactory.getBean("daoFactory");
            GlobalDefinitionsDAO gdDAO = daoFactory.getGlobalDefinitionsDAO();
            String rdIdSeq = gdDAO.storeGlobalDefinitionsMIFMessage(formIdSeq,testInstrumentMessage,new Date(),"user");
            System.out.println("Reference Document idseq ->"+rdIdSeq);
        }
        catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }    
    
    public void testGeteDCIHL7Message() {
        try {
            GenerateMessageService generateMessageService = new ServiceLocator().getGenerateMessageService();
            String message = generateMessageService.getMessage(formIdSeq,refDocName, generateMessageService.EDCI);
            System.out.println("eDCI HL7 message "+message);
            }
            catch(Exception e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
    }
    
    public void testGenerateDCIHL7Message() {
      try {
        GenerateMessageService generateMessageService = new ServiceLocator().getGenerateMessageService();
        //String message = generateMessageService.generateMessage(formIdSeq,"user", generateMessageService.EDCI);
        //form for QA
         String message = generateMessageService.generateMessage("A68C74F0-9B90-58E6-E034-0003BA0B1A09","user", generateMessageService.EDCI);
        System.out.println("Message :\n"+message);
      }
      catch(Exception e){
          e.printStackTrace();
          fail(e.getMessage());
      }
    }    
    
    public static Test suite() {
      TestSuite suite = new TestSuite();
      //suite.addTest(new TestServices("testGenerateDCIDefMessage"));
      //suite.addTest(new TestServices("testQueryGlobalDefinitionMetadata"));
      //suite.addTest(new TestServices("testQueryInstrumentMetadata"));
       suite.addTest(new TestServices("testStoreGlobalDefinitionMIFMessage"));
      return suite;
    }

    public static void main(String args[]) {
      junit.textui.TestRunner.run(suite());
    }    
}
