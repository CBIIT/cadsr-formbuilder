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
 * Contributor(s): Karthikeyan Perumal, Peter Hendler
 *
 * This is an example of making a RIM graph from scratch and then generating
 * an XML message from it
 */

package org.hl7.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.hl7.meta.ChoiceAssociation;
import org.hl7.meta.CloneClass;
import org.hl7.meta.Datatype;
import org.hl7.meta.MessageType;
import org.hl7.meta.impl.SimpleDatatypeImpl;
import org.hl7.meta.mif.ChoiceAssociationAdapter;
import org.hl7.meta.mif.MessageTypeLoaderAdapter;
import org.hl7.rim.LanguageCommunication;
import org.hl7.rim.LivingSubject;
import org.hl7.rim.NonPersonLivingSubject;
import org.hl7.rim.Organization;
import org.hl7.rim.Participation;
import org.hl7.rim.Patient;
import org.hl7.rim.PatientEncounter;
import org.hl7.rim.Person;
import org.hl7.rim.Place;
import org.hl7.rim.RimObject;
import org.hl7.rim.Role;
import org.hl7.rim.impl.AssociationSetImpl;
import org.hl7.rim.impl.LanguageCommunicationImpl;
import org.hl7.rim.impl.LivingSubjectImpl;
import org.hl7.rim.impl.OrganizationImpl;
import org.hl7.rim.impl.ParticipationImpl;
import org.hl7.rim.impl.PatientEncounterImpl;
import org.hl7.rim.impl.PatientImpl;
import org.hl7.rim.impl.PersonImpl;
import org.hl7.rim.impl.PlaceImpl;
import org.hl7.rim.impl.RoleImpl;
import org.hl7.types.AD;
import org.hl7.types.ADXP;
import org.hl7.types.ANY;
import org.hl7.types.BAG;
import org.hl7.types.CE;
import org.hl7.types.CS;
import org.hl7.types.CV;
import org.hl7.types.EN;
import org.hl7.types.ENXP;
import org.hl7.types.II;
import org.hl7.types.IVL;
import org.hl7.types.PQ;
import org.hl7.types.SET;
import org.hl7.types.TEL;
import org.hl7.types.TS;
import org.hl7.types.URL;
import org.hl7.types.enums.EntityClass;
import org.hl7.types.impl.ADXPimpl;
import org.hl7.types.impl.ADimpl;
import org.hl7.types.impl.BAGjuListAdapter;
import org.hl7.types.impl.BLimpl;
import org.hl7.types.impl.CEimpl;
import org.hl7.types.impl.CEnull;
import org.hl7.types.impl.CSimpl;
import org.hl7.types.impl.CVimpl;
import org.hl7.types.impl.ENXPimpl;
import org.hl7.types.impl.ENimpl;
import org.hl7.types.impl.IIimpl;
import org.hl7.types.impl.IVLimpl;
import org.hl7.types.impl.PQimpl;
import org.hl7.types.impl.SETjuSetAdapter;
import org.hl7.types.impl.TELimpl;
import org.hl7.types.impl.TSjuDateAdapter;
import org.hl7.util.FactoryException;
import org.hl7.xml.builder.RimGraphXMLSpeaker;
import org.hl7.xml.parser.MessageContentHandler;
import org.xml.sax.helpers.DefaultHandler;
import sun.awt.image.URLImageSource;
public class PatientEncounterRIM {
    
    public static void main(String[] args) {
        PatientEncounterRIM vm = new PatientEncounterRIM();
        vm.buildM();
    }
    
    public  void buildMessage(Object obj) {
        try {
            RimGraphXMLSpeaker speaker = new RimGraphXMLSpeaker();
            
            MessageTypeLoaderAdapter mLoader =
                    MessageTypeLoaderAdapter.getInstance();
            MessageType messageType = mLoader
                    .loadMessageType("PRPA_MT402002");
            
            System.out.println(messageType.getRootClass().getName()) ;
            //Object obj= getAll(messageType);
            
            Transformer transformer =
                    TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            
            Source source = new SAXSource(speaker,new
                    RimGraphXMLSpeaker.InputSource((RimObject)obj,messageType.getRootClass()));
            
            //StringWriter sw = new StringWriter();
            transformer.transform(source,new StreamResult(System.out));
        } catch(Exception x) {
            x.printStackTrace();
            System.out.println("Error"+x.getMessage());
        }
        
    }
    public void buildM() {
        
        System.out.println("PATIENT ENCOUNTER EVENT") ;
        PatientEncounter pe=new PatientEncounterImpl();
        
        Participation part=new ParticipationImpl();
        
        Role patient=new RoleImpl();
        Person person=new PersonImpl();
        
        Set<II> idSet = new HashSet<II>();
        idSet.add(IIimpl.valueOf("125555","","","true"));
        SET<II> id = SETjuSetAdapter.valueOf(idSet);
        pe.setId(id);
        Collection col=new ArrayList();
        col.add(TSjuDateAdapter.valueOf("222223233"));
        pe.setActivityTime(SETjuSetAdapter.valueOf(col));
        pe.setCloneCode(CSimpl.valueOf("EncounterEvent","EncounterEvent"));
        
        
        
        part.setCloneCode(CSimpl.valueOf("subject","subject"));
        patient.setCloneCode(CSimpl.valueOf("patient","patientperson"));
        Set<II> idSet1 = new HashSet<II>();
        idSet1.add(IIimpl.valueOf("Abcd1234","","","true"));
        SET<II> id1 = SETjuSetAdapter.valueOf(idSet1);
        patient.setId(id1);
        
        List<Participation> pa=new ArrayList<Participation>();
        pa.add(part);
        
        person.setCloneCode(CSimpl.valueOf("patientperson","person"));
        patient.setScoper(person);
        part.setRole(patient);
        pe.setParticipation(pa);
        buildMessage(pe);
        
    }
}
