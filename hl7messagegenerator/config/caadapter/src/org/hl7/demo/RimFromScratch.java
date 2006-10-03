/*
 * RimFromScratch.java
 *
 * Created on March 17, 2006, 3:39 PM
 *
 * This class demonstrates the manual creation of a small RIM
 * graph entirely manually in java.  The RIM graph is then run 
 * through the javaSIG API message builder and results in an
 * XML message instance.
 */

/* The contents of this file are subject to the Health Level-7 Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the
 * License at http://www.hl7.org/HPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * The Original Code is all this file.
 *
 * The Initial Developer of the Original Code is .
 * Portions created by Initial Developer are Copyright (C) 2002-2004
 * Health Level Seven, Inc. All Rights Reserved.
 *
 * Contributor(s):
 */

package org.hl7.demo;


  import org.hl7.meta.mif.*;
  import org.hl7.meta.MessageType;
  import org.hl7.meta.util.*; 
  import org.hl7.rim.*;
  import org.hl7.rim.impl.*;
  import org.hl7.types.*;
  import org.hl7.types.impl.*;
  import org.hl7.xml.parser.*;
  import org.hl7.util.*; 
  import org.hl7.xml.*; 
  import org.hl7.xml.builder.*; 
  import org.hl7.xml.builder.impl.*;
    
  import java.util.*;
  import java.io.*;
  
  import javax.xml.transform.OutputKeys;
  import javax.xml.transform.Source;
  import javax.xml.transform.Transformer;
  import javax.xml.transform.TransformerFactory;
  import javax.xml.transform.sax.SAXSource;
  import javax.xml.transform.stream.StreamResult;
  
 
/**
 *
 * @author Mohamad Norouzee and Peter Hendler
 */
public class RimFromScratch {
    
    /** Creates a new instance of RimFromScratch */
    public RimFromScratch() {
        
    }
    
    public static void main(String[] args){
        RimFromScratch rfs = new RimFromScratch();
        rfs.run();
    }
    
    public void run(){
        Patient patient = new PatientImpl();
        Person person = new PersonImpl();
//set the name
        List<ENXP> nameParts = new ArrayList<ENXP>();
        nameParts.add(ENXPimpl.valueOf("Morgan",CSimpl.valueOf("GIV","10655")));
        nameParts.add(ENXPimpl.valueOf("Freeman",CSimpl.valueOf("FAM","10654")));
        List<EN> l = new ArrayList<EN>();
        l.add(ENimpl.valueOf(nameParts));
        person.setName(BAGjuListAdapter.valueOf(l));
        person.setAdministrativeGenderCode(CEimpl.valueOf(CVimpl.valueOf("M","10173"),null));
        person.setBirthTime(TSjuDateAdapter.valueOf(new Date(1987,5,5),8));
        person.setEducationLevelCode(CEimpl.valueOf(CVimpl.valueOf("GD","19183"),null));
        Set<II> idSet = new HashSet<II>();
        idSet.add(IIimpl.valueOf("125555","","","true"));
        SET<II> id = SETjuSetAdapter.valueOf(idSet);
        person.setId(id);
        // uncomment the following line.For some reason it is really OK but breaks unit test
       // person.setStatusCode(CSimpl.valueOf("active","16007"));
        person.setClassCode(CSimpl.valueOf("PSN","10887"));
        person.setDeterminerCode(CSimpl.valueOf("INSTANCE","10881"));
        Set<PQ> qset = new HashSet<PQ>();
        qset.add(PQimpl.valueOf("1",""));
        SET<PQ> quantity = SETjuSetAdapter.valueOf(qset);
        person.setQuantity(quantity);
        List<ADXP> patientAddrParts = new ArrayList<ADXP>();
        patientAddrParts.add(ADXPimpl.valueOf("NY",CSimpl.valueOf("125","1.2.2.2")));
        patientAddrParts.add(ADXPimpl.valueOf("23556",CSimpl.valueOf("135","1.2.2.3")));
        
        //define patient
        Set<II> idPatientSet = new HashSet<II>();
        idPatientSet.add(IIimpl.valueOf("722292","",""));
        SET<II> idPatient = SETjuSetAdapter.valueOf(idPatientSet);
        patient.setId(idPatient);
         // uncomment the following line.For some reason it is really OK but breaks unit test
       // patient.setStatusCode(CSimpl.valueOf("active","16007"));
        List<ADXP> addrParts = new ArrayList<ADXP>();
        addrParts.add(ADXPimpl.valueOf("U.S.A",CSimpl.valueOf("CNT","10644")));
        addrParts.add(ADXPimpl.valueOf("NY",CSimpl.valueOf("STA","10645")));
        addrParts.add(ADXPimpl.valueOf("23556",CSimpl.valueOf("ZIP","10647")));
        addrParts.add(ADXPimpl.valueOf("bertrand",CSimpl.valueOf("STR","10648")));
        addrParts.add(ADXPimpl.valueOf("49",CSimpl.valueOf("BNR","10649")));
        addrParts.add(ADXPimpl.valueOf("4th.      floor",CSimpl.valueOf("ADL","10651")));
        patient.setAddr(BAGjuListAdapter.valueOf(addrParts));
        patient.setClassCode(CSimpl.valueOf("PAT","11577"));
        IVL<TS> effectiveTime =  IVLimpl.valueOf(BLimpl.valueOf(true),TSjuDateAdapter.valueOf(new Date(2006,01,01),8),TSjuDateAdapter.valueOf(new Date(2006,02,01),8),BLimpl.valueOf(true));
        patient.setEffectiveTime(effectiveTime);
        
//[**]
        person.setCloneCode(CSimpl.valueOf("patientPerson","Person"));
        
//define organization
        Organization organization = new OrganizationImpl();
        Set<II> idOrgSet = new HashSet<II>();
        idOrgSet.add(IIimpl.valueOf("12345689","","Sun Microsystems Inc."));
        idOrgSet.add(IIimpl.valueOf("722292","","43534"));
        SET<II> idOrg = SETjuSetAdapter.valueOf(idOrgSet);
        organization.setId(idOrg);
        organization.setClassCode(CSimpl.valueOf("ORG","10882"));
        organization.setDeterminerCode(CSimpl.valueOf("INSTANCE","10881"));
//[**]
        organization.setCloneCode(CSimpl.valueOf("providerOrganization","Organization"));
        
        Role contactParty = new RoleImpl();
        contactParty.setClassCode(CSimpl.valueOf("CON","10882"));
        contactParty.setAddr(BAGjuListAdapter.valueOf(addrParts));
        contactParty.setCloneCode(CSimpl.valueOf("contactParty","Role"));
        List<Role> scopedRoles = new ArrayList<Role>();
        scopedRoles.add(contactParty);
        organization.setScopedRole(scopedRoles);
        patient.setScoper(organization);
        patient.setPlayer(person);
        
//[****]
        buildMessage(patient);
        
    }


public void buildMessage(Object o) {
    try {
        RimGraphXMLSpeaker speaker = new RimGraphXMLSpeaker();
        MessageTypeLoaderAdapter jmtl = MessageTypeLoaderAdapter.getInstance();
        MessageType messageType = jmtl.loadMessageType("Patient_Activate");
        FileOutputStream fos = new  FileOutputStream("out/demoRimFromScratch.xml");
//ObjectAnalyzer analyzer = new ObjectAnalyzer();
//System.out.println(analyzer.toString(o));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        Source source = new SAXSource(speaker,new RimGraphXMLSpeaker.InputSource((RimObject)o,messageType.getRootClass()));
        transformer.transform(source,new StreamResult(fos));
    }catch(Exception x) {
        x.printStackTrace();
        System.out.println("Error: "+x.getMessage());
    }
}


}
