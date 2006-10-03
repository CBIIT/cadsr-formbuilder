/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/common/VocabConstants.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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


package gov.nih.nci.hl7.common;

/**
 * Contains constants for all the HL7 vocabulary domains.
 * SQL Source: "select distinct vocDomain from RIM_vocabulary_domain;"
 *
 * @author OWNER: Matthew Giordano
 * @author LAST UPDATE $Author: marwahah $
 * @version $Revision: 1.1 $
 * @since caAdapter v1.2
 */

public class VocabConstants
{
	public static final String ACKNOWLEDGEMENTCONDITION = "AcknowledgementCondition";
	public static final String ACKNOWLEDGEMENTDETAILCODE = "AcknowledgementDetailCode";
	public static final String ACKNOWLEDGEMENTDETAILTYPE = "AcknowledgementDetailType";
	public static final String ACKNOWLEDGEMENTMESSAGECODE = "AcknowledgementMessageCode";
	public static final String ACKNOWLEDGEMENTTYPE = "AcknowledgementType";
	public static final String ACKNOWLEDGMENTMESSAGETYPE = "AcknowledgmentMessageType";
	public static final String ACTADJUDICATIONINFORMATIONCODE = "ActAdjudicationInformationCode";
	public static final String ACTCLAIMATTACHMENTCODE = "ActClaimAttachmentCode";
	public static final String ACTCLASS = "ActClass";
	public static final String ACTCLASSCOMPOSITION = "ActClassComposition";
	public static final String ACTCLASSENTRY = "ActClassEntry";
	public static final String ACTCLASSEXTRACT = "ActClassExtract";
	public static final String ACTCLASSORGANIZER = "ActClassOrganizer";
	public static final String ACTCODE = "ActCode";
	public static final String ACTCONTAINER = "ActContainer";
	public static final String ACTINJURYCODE = "ActInjuryCode";
	public static final String ACTINVOICEELEMENTMODIFIER = "ActInvoiceElementModifier";
	public static final String ACTMOOD = "ActMood";
	public static final String ACTPAYMENTREASON = "ActPaymentReason";
	public static final String ACTPRIORITY = "ActPriority";
	public static final String ACTPROCEDURECODE = "ActProcedureCode";
	public static final String ACTREASON = "ActReason";
	public static final String ACTREGISTRYCODE = "ActRegistryCode";
	public static final String ACTRELATIONSHIPCHECKPOINT = "ActRelationshipCheckpoint";
	public static final String ACTRELATIONSHIPENTRY = "ActRelationshipEntry";
	public static final String ACTRELATIONSHIPENTRYRELATIONSHIP = "ActRelationshipEntryRelationship";
	public static final String ACTRELATIONSHIPJOIN = "ActRelationshipJoin";
	public static final String ACTRELATIONSHIPRELATEDORDER = "ActRelationshipRelatedOrder";
	public static final String ACTRELATIONSHIPSPLIT = "ActRelationshipSplit";
	public static final String ACTRELATIONSHIPTYPE = "ActRelationshipType";
	public static final String ACTSITE = "ActSite";
	public static final String ACTSTATUS = "ActStatus";
	public static final String ACTUNCERTAINTY = "ActUncertainty";
	public static final String ADDRESSPARTTYPE = "AddressPartType";
	public static final String ADDRESSUSE = "AddressUse";
	public static final String ADMINISTRATIVEGENDER = "AdministrativeGender";
	public static final String ALGORITHMICDECISIONOBSERVATIONMETHOD = "AlgorithmicDecisionObservationMethod";
	public static final String AMERICANINDIANALASKANATIVELANGUAGES = "AmericanIndianAlaskaNativeLanguages";
	public static final String ATTENTIONKEYWORD = "AttentionKeyword";
	public static final String BATCHNAME = "BatchName";
	public static final String CALENDAR = "Calendar";
	public static final String CALENDARCYCLE = "CalendarCycle";
	public static final String CALENDARCYCLEONELETTER = "CalendarCycleOneLetter";
	public static final String CALENDARCYCLETWOLETTER = "CalendarCycleTwoLetter";
	public static final String CALENDARTYPE = "CalendarType";
	public static final String CANADIANACTPROCEDURECODE = "CanadianActProcedureCode";
	public static final String CASEDETECTIONMETHOD = "CaseDetectionMethod";
	public static final String CASEDISEASEIMPORTED = "CaseDiseaseImported";
	public static final String CASETRANSMISSIONMODE = "CaseTransmissionMode";
	public static final String CHARSET = "Charset";
	public static final String CODESYSTEM = "CodeSystem";
	public static final String CODESYSTEMTYPE = "CodeSystemType";
	public static final String CODINGRATIONALE = "CodingRationale";
	public static final String COMMUNICATIONFUNCTIONTYPE = "CommunicationFunctionType";
	public static final String COMPRESSIONALGORITHM = "CompressionAlgorithm";
	public static final String CONCEPTGENERALITY = "ConceptGenerality";
	public static final String CONCEPTPROPERTYID = "ConceptPropertyId";
	public static final String CONCEPTRELATIONSHIPCODE = "ConceptRelationshipCode";
	public static final String CONCEPTSTATUS = "ConceptStatus";
	public static final String CONFIDENTIALITY = "Confidentiality";
	public static final String CONTAINERCAP = "ContainerCap";
	public static final String CONTAINERSEPARATOR = "ContainerSeparator";
	public static final String CONTEXTCONTROL = "ContextControl";
	public static final String CONTEXTCONTROLACTRELATIONSHIP = "ContextControlActRelationship";
	public static final String CONTEXTCONTROLPARTICIPATION = "ContextControlParticipation";
	public static final String COUNTRY = "Country";
	public static final String CURRENCY = "Currency";
	public static final String DATATYPE = "DataType";
	public static final String DECISIONOBSERVATIONMETHOD = "DecisionObservationMethod";
	public static final String DEDICATEDSERVICEDELIVERYLOCATIONROLETYPE = "DedicatedServiceDeliveryLocationRoleType";
	public static final String DEVICEALERTLEVEL = "DeviceAlertLevel";
	public static final String DIAGNOSIS = "Diagnosis";
	public static final String DIAGNOSISVALUE = "DiagnosisValue";
	public static final String DOCUMENTCOMPLETION = "DocumentCompletion";
	public static final String DOCUMENTSTORAGE = "DocumentStorage";
	public static final String EDITSTATUS = "EditStatus";
	public static final String EDUCATIONLEVEL = "EducationLevel";
	public static final String ELEMENTNAME = "ElementName";
	public static final String EMPLOYEEJOB = "EmployeeJob";
	public static final String EMPLOYEEJOBCLASS = "EmployeeJobClass";
	public static final String EMPLOYEESALARYTYPE = "EmployeeSalaryType";
	public static final String EMPLOYMENTSTATUS = "EmploymentStatus";
	public static final String ENCOUNTERACCIDENT = "EncounterAccident";
	public static final String ENCOUNTERACUITY = "EncounterAcuity";
	public static final String ENCOUNTERADMISSIONSOURCE = "EncounterAdmissionSource";
	public static final String ENCOUNTERDISCHARGEDISPOSITION = "EncounterDischargeDisposition";
	public static final String ENCOUNTERREFERRALSOURCE = "EncounterReferralSource";
	public static final String ENCOUNTERSPECIALCOURTESY = "EncounterSpecialCourtesy";
	public static final String ENTITYCLASS = "EntityClass";
	public static final String ENTITYCODE = "EntityCode";
	public static final String ENTITYDETERMINER = "EntityDeterminer";
	public static final String ENTITYHANDLING = "EntityHandling";
	public static final String ENTITYNAMEPARTQUALIFIER = "EntityNamePartQualifier";
	public static final String ENTITYNAMEPARTTYPE = "EntityNamePartType";
	public static final String ENTITYNAMEUSE = "EntityNameUse";
	public static final String ENTITYRISK = "EntityRisk";
	public static final String ENTITYSTATUS = "EntityStatus";
	public static final String EQUIPMENTALERTLEVEL = "EquipmentAlertLevel";
	public static final String ETHNICITY = "Ethnicity";
	public static final String GENDERSTATUS = "GenderStatus";
	public static final String GTSABBREVIATION = "GTSAbbreviation";
	public static final String HEALTHCAREPROVIDERTAXONOMYHIPAA = "HealthcareProviderTaxonomyHIPAA";
	public static final String HL7COMMITTEEIDINRIM = "HL7CommitteeIDInRIM";
	public static final String HL7CONFORMANCEINCLUSION = "HL7ConformanceInclusion";
	public static final String HL7DEFINEDACTCODES = "HL7DefinedActCodes";
	public static final String HL7DEFINEDROSEPROPERTY = "HL7DefinedRoseProperty";
	public static final String HL7ITSVERSIONCODE = "HL7ITSVersionCode";
	public static final String HL7STANDARDVERSIONCODE = "HL7StandardVersionCode";
	public static final String HL7TRIGGEREVENTCODE = "HL7TriggerEventCode";
	public static final String HL7UPDATEMODE = "HL7UpdateMode";
	public static final String HTMLLINKTYPE = "HtmlLinkType";
	public static final String HUMANLANGUAGE = "HumanLanguage";
	public static final String IMAGINGSUBJECTORIENTATION = "ImagingSubjectOrientation";
	public static final String INCIDENTALSERVICEDELIVERYLOCATIONROLETYPE = "IncidentalServiceDeliveryLocationRoleType";
	public static final String INDUSTRYCLASSIFICATIONSYSTEM = "IndustryClassificationSystem";
	public static final String INJURYACTSITE = "InjuryActSite";
	public static final String INJURYOBSERVATIONVALUE = "InjuryObservationValue";
	public static final String INTEGRITYCHECKALGORITHM = "IntegrityCheckAlgorithm";
	public static final String INVOICEELEMENTMODIFIER = "InvoiceElementModifier";
	public static final String JOBTITLENAME = "JobTitleName";
	public static final String LANGUAGEABILITYMODE = "LanguageAbilityMode";
	public static final String LANGUAGEABILITYPROFICIENCY = "LanguageAbilityProficiency";
	public static final String LISTOWNERSHIPLEVEL = "ListOwnershipLevel";
	public static final String LIVINGARRANGEMENT = "LivingArrangement";
	public static final String LOCALMARKUPIGNORE = "LocalMarkupIgnore";
	public static final String LOCALREMOTECONTROLSTATE = "LocalRemoteControlState";
	public static final String MANAGEDPARTICIPATIONSTATUS = "ManagedParticipationStatus";
	public static final String MANUFACTURERMODELNAME = "ManufacturerModelName";
	public static final String MAPRELATIONSHIP = "MapRelationship";
	public static final String MARITALSTATUS = "MaritalStatus";
	public static final String MATERIALENTITYCLASSTYPE = "MaterialEntityClassType";
	public static final String MATERIALFORM = "MaterialForm";
	public static final String MATERIALTYPE = "MaterialType";
	public static final String MDFATTRIBUTETYPE = "MDFAttributeType";
	public static final String MDFHMDMETSOURCETYPE = "MdfHmdMetSourceType";
	public static final String MDFHMDROWTYPE = "MdfHmdRowType";
	public static final String MDFRMIMROWTYPE = "MdfRmimRowType";
	public static final String MDFSUBJECTAREAPREFIX = "MDFSubjectAreaPrefix";
	public static final String MEDADMINISTRATIONROUTE = "MedAdministrationRoute";
	public static final String MEDIATYPE = "MediaType";
	public static final String MESSAGECONDITION = "MessageCondition";
	public static final String MESSAGEWAITINGPRIORITY = "MessageWaitingPriority";
	public static final String MODIFYINDICATOR = "ModifyIndicator";
	public static final String NULLFLAVOR = "NullFlavor";
	public static final String OBSERVATIONINTERPRETATION = "ObservationInterpretation";
	public static final String OBSERVATIONMETHOD = "ObservationMethod";
	public static final String OBSERVATIONVALUE = "ObservationValue";
	public static final String ORDERABLEDRUGFORM = "OrderableDrugForm";
	public static final String ORGANIZATIONINDUSTRYCLASS = "OrganizationIndustryClass";
	public static final String ORGANIZATIONNAMETYPE = "OrganizationNameType";
	public static final String PARAMETERIZEDDATATYPE = "ParameterizedDataType";
	public static final String PARTICIPATIONFUNCTION = "ParticipationFunction";
	public static final String PARTICIPATIONMODE = "ParticipationMode";
	public static final String PARTICIPATIONSIGNATURE = "ParticipationSignature";
	public static final String PARTICIPATIONTYPE = "ParticipationType";
	public static final String PATIENTIMPORTANCE = "PatientImportance";
	public static final String PAYMENTTERMS = "PaymentTerms";
	public static final String PERSONDISABILITYTYPE = "PersonDisabilityType";
	public static final String PERSONNAMEPURPOSE = "PersonNamePurpose";
	public static final String POSTALADDRESSUSE = "PostalAddressUse";
	public static final String PROBABILITYDISTRIBUTIONTYPE = "ProbabilityDistributionType";
	public static final String PROCEDUREMETHOD = "ProcedureMethod";
	public static final String PROCESSINGID = "ProcessingID";
	public static final String PROCESSINGMODE = "ProcessingMode";
	public static final String PROVIDERCODES = "ProviderCodes";
	public static final String QUERYEVENTSTATUS = "QueryEventStatus";
	public static final String QUERYPRIORITY = "QueryPriority";
	public static final String QUERYQUANTITYUNIT = "QueryQuantityUnit";
	public static final String QUERYREQUESTLIMIT = "QueryRequestLimit";
	public static final String QUERYRESPONSE = "QueryResponse";
	public static final String QUERYSTATUS = "QueryStatus";
	public static final String QUERYSTATUSCODE = "QueryStatusCode";
	public static final String RACE = "Race";
	public static final String REALM = "Realm";
	public static final String RELATIONALNAME = "RelationalName";
	public static final String RELATIONALOPERATOR = "RelationalOperator";
	public static final String RELATIONSHIPCONJUNCTION = "RelationshipConjunction";
	public static final String RELIGIOUSAFFILIATION = "ReligiousAffiliation";
	public static final String RESEARCHSUBJECTROLEBASIS = "ResearchSubjectRoleBasis";
	public static final String RESPONSELEVEL = "ResponseLevel";
	public static final String RESPONSEMODALITY = "ResponseModality";
	public static final String ROLECLASS = "RoleClass";
	public static final String ROLECODE = "RoleCode";
	public static final String ROLELINKTYPE = "RoleLinkType";
	public static final String ROLESTATUS = "RoleStatus";
	public static final String ROUTEOFADMINISTRATION = "RouteOfAdministration";
	public static final String SEQUENCING = "Sequencing";
	public static final String SETOPERATOR = "SetOperator";
	public static final String SOFTWARENAME = "SoftwareName";
	public static final String SPECIALACCOMMODATION = "SpecialAccommodation";
	public static final String SPECIMENTYPE = "SpecimenType";
	public static final String SQLCONJUNCTION = "SQLConjunction";
	public static final String SUBSTANCEADMINSUBSTITUTIONREASON = "SubstanceAdminSubstitutionReason";
	public static final String SUBSTITUTIONCONDITION = "SubstitutionCondition";
	public static final String TABLECELLHORIZONTALALIGN = "TableCellHorizontalAlign";
	public static final String TABLECELLSCOPE = "TableCellScope";
	public static final String TABLECELLVERTICALALIGN = "TableCellVerticalAlign";
	public static final String TABLEFRAME = "TableFrame";
	public static final String TABLERULES = "TableRules";
	public static final String TARGETAWARENESS = "TargetAwareness";
	public static final String TELECOMMUNICATIONADDRESSUSE = "TelecommunicationAddressUse";
	public static final String TIMINGEVENT = "TimingEvent";
	public static final String TRIBALENTITYUS = "TribalEntityUS";
	public static final String UNITOFMEASUREPREFIXINSENS = "UnitOfMeasurePrefixInsens";
	public static final String UNITOFMEASUREPREFIXSENS = "UnitOfMeasurePrefixSens";
	public static final String UNITSOFMEASURECASEINSENSITIVE = "UnitsOfMeasureCaseInsensitive";
	public static final String UNITSOFMEASURECASESENSITIVE = "UnitsOfMeasureCaseSensitive";
	public static final String URLSCHEME = "URLScheme";
	public static final String VACCINEMANUFACTURER = "VaccineManufacturer";
	public static final String VACCINETYPE = "VaccineType";
	public static final String VALUESETOPERATOR = "ValueSetOperator";
	public static final String VALUESETPROPERTYID = "ValueSetPropertyId";
	public static final String VALUESETSTATUS = "ValueSetStatus";
	public static final String VOCABULARYDOMAINQUALIFIER = "VocabularyDomainQualifier";
	public static final String X_ACTENCOUNTERREASON = "x_ActEncounterReason";
	public static final String X_ACTRELATIONSHIPPERTINENTINFO = "x_ActRelationshipPertinentInfo";
	public static final String X_DOCUMENTACTMOOD = "x_DocumentActMood";
	public static final String X_DOCUMENTENCOUNTERMOOD = "x_DocumentEncounterMood";
	public static final String X_DOCUMENTPROCEDUREMOOD = "x_DocumentProcedureMood";
	public static final String X_DOCUMENTSUBSTANCEMOOD = "x_DocumentSubstanceMood";
	public static final String X_ENCOUNTERADMISSIONURGENCY = "x_EncounterAdmissionUrgency";
	public static final String X_ENTITYCLASSDOCUMENTRECEIVING = "x_EntityClassDocumentReceiving";
	public static final String X_ENTITYCLASSPERSONORORGRECEIVING = "x_EntityClassPersonOrOrgReceiving";
	public static final String X_ORGANIZATIONNAMEPARTTYPE = "x_OrganizationNamePartType";
	public static final String X_PERSONNAMEPARTTYPE = "x_PersonNamePartType";
}
