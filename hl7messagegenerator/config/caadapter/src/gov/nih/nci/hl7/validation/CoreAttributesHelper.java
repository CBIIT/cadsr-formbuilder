/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/validation/CoreAttributesHelper.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
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


package gov.nih.nci.hl7.validation;

import gov.nih.nci.hl7.common.ValidationException;
import gov.nih.nci.hl7.common.VocabularyException;
import gov.nih.nci.hl7.util.FileUtil;
import gov.nih.nci.hl7.util.Log;
import org.hl7.CTSMAPI.RuntimeOperations;
import org.hl7.CTSMAPI.UnknownConceptCode;
import org.hl7.CTSMAPI.ValidateCodeReturn;
import org.hl7.factories.CtsFactory;
import org.hl7.meta.*;
import org.hl7.meta.impl.AssociationImpl;
import org.hl7.rim.*;
import org.hl7.types.BL;
import org.hl7.types.CD;
import org.hl7.types.CS;
import org.hl7.types.impl.CDimpl;
import org.hl7.types.impl.DomainMapImpl;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Helper class for the Vocabulary Service.
 * <p/>
 */

public class CoreAttributesHelper {
   //  TODO : see if there is duplicate code here.  I believe I saw functions similar to
   // locateCloneClass() and/or locateRimAttribute() in the XmlItsBallot3Impl.

    protected static final Logger LOGGER = Logger.getLogger("gov.nih.nci.hl7.validation");
    private static XmlIts _xmlItsHelper = new org.hl7.meta.impl.XmlItsBallot3Impl();
    RuntimeOperations ctsRuntimeOps = null;
    DomainMapImpl oidLoader = null;
    XmlIts xmlItsHelper = null;
    ArrayList errors = null;
    ArrayList warnings = null;
    int successes = 0;
    int ignored = 0;

    HashSet validatedSet = new HashSet();

    public int indent = 0;  // used for formatting output
    StringBuffer debugTraversal = new StringBuffer("Rim object being validated (clonenames)\n");
    boolean debug = true;

    public CoreAttributesHelper() throws org.hl7.util.FactoryException, Exception {
        CtsFactory factory = new CtsFactory();
        ctsRuntimeOps = factory.getMessageRuntimeOperations();
        oidLoader = DomainMapImpl.getInstance();
        xmlItsHelper = new org.hl7.meta.impl.XmlItsBallot3Impl();
        errors = new ArrayList();
        warnings = new ArrayList();
    }

    public void processRimObjectCoreAttributes(RimObject rimObject, CloneClass clone) {
        debugTraversal(rimObject.getCloneCode());
        Iterator structuralIterator = clone.iterateStructuralAttributes();

        while (structuralIterator.hasNext()) {
            Attribute metaAttribute = (Attribute) structuralIterator.next();
            try {
                CD coreAttFromRim = locateRimAttribute(rimObject, metaAttribute.getRimPropertyName());
                validateAttribute(coreAttFromRim, metaAttribute);
            } catch (VocabularyException ve) {
                error(ve);//e.printStackTrace();
            } catch (ClassCastException e) {
                // Sometimes BL will be found, just swallow these errors.
                ignore(e);
            }
        }
    }

    public void processAct(Act a, CloneClass clone) {//throws VocabularyException{
        if(hasBeenValidated(a))return;
        //AssociationSet aSet = null;
        List aSet = null;
        Iterator i = null;
        //println("* Process Act : " + a.getCloneCode());
        LOGGER.finer("* Process Act : " + a.getCloneCode());
        indent++;

        // Validate the Core Attributes.
        processRimObjectCoreAttributes(a, clone);

        // process Inbound Act Relationships
        aSet = a.getInboundRelationship();
        if (aSet != null) {
            i = aSet.iterator();
            while (i.hasNext()) {
                try {
                    ActRelationship ar = (ActRelationship) i.next();
                    CloneClass arcc = locateCloneClass(clone, ar.getCloneCode().code().toString());
                    processActRelationship(ar, arcc);
                } catch (VocabularyException e) {
                    LOGGER.fine(e.getMessage());
//                    e.printStackTrace();
                }
            }
        }

        // process Oubound Act Relationships
        aSet = a.getOutboundRelationship();
        if (aSet != null) {
            i = aSet.iterator();
            while (i.hasNext()) {
                try {
                    ActRelationship ar = (ActRelationship) i.next();
                    CloneClass arcc = locateCloneClass(clone, ar.getCloneCode().code().toString());
                    processActRelationship(ar, arcc);
                } catch (VocabularyException e) {
                    LOGGER.fine(e.getMessage());
                }
            }
        }

        //process Participations
        aSet = a.getParticipation();
        if (aSet != null) {
            i = aSet.iterator();
            while (i.hasNext()) {
                try {
                    Participation p = (Participation) i.next();
                    String cloneName = p.getCloneCode().code().toString();
                    CloneClass pcc = locateCloneClass(clone, cloneName);
                    processParticipation(p, pcc);
                } catch (VocabularyException e) {
                    LOGGER.fine(e.getMessage());
//                    e.printStackTrace();
                }

            }
        }

        indent--;
    }

    public void processActRelationship(ActRelationship ar, CloneClass clone) throws VocabularyException {
        if(hasBeenValidated(ar))return;
        //println("* Process ActRelationship : " + ar.getCloneCode());
        LOGGER.finer("* Process ActRelationship : " + ar.getCloneCode());
        indent++;

        // Validate the Core Attributes.
        processRimObjectCoreAttributes(ar, clone);

        // process Act Target.
        Act target = ar.getTarget();
        if (target != null) {
            try {
                CloneClass cc = locateCloneClass(clone, target.getCloneCode().code().toString());
                processAct(target, cc);
            } catch (VocabularyException e) {
                LOGGER.fine(e.getMessage());
            }
        }

        // process Act Source.
        Act source = ar.getSource();
        if (source != null) {
            try {
                CloneClass cc = locateCloneClass(clone, source.getCloneCode().code().toString());
                processAct(source, cc);
            } catch (VocabularyException e) {
                LOGGER.fine(e.getMessage());
            }
        }

        indent--;
    }

    public void processParticipation(Participation p, CloneClass clone) {
        if(hasBeenValidated(p))return;
        //println("* Validate Participation : " + p.getCloneCode());
        LOGGER.finer("* Validate Participation : " + p.getCloneCode());
        indent++;

        // Validate the Core Attributes.
        processRimObjectCoreAttributes(p, clone);

        // Validate the Associated Role.
        Role r = p.getRole();
        if (r != null) {
            try {
                CloneClass cc = locateCloneClass(clone, r.getCloneCode().code().toString());
                processRole(r, cc);
            } catch (VocabularyException e) {
                LOGGER.fine(e.getMessage());
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        // Validate the Associated Act.
        Act a = p.getAct();
        if (a != null) {
            try {
                CloneClass cc = locateCloneClass(clone, a.getCloneCode().code().toString());
                processAct(a, cc);
            } catch (VocabularyException e) {
                LOGGER.fine(e.getMessage());
            }
        }
        indent--;
    }

    public void processRole(Role r, CloneClass clone) {
        if(hasBeenValidated(r))return;
        //println("* Validate Role : " + r.getCloneCode());
        LOGGER.finer("* Validate Role : " + r.getCloneCode());
        indent++;
        List aSet = null;
        processRimObjectCoreAttributes(r, clone);

        //validate the Player
        Entity player = r.getPlayer();
        if (player != null) {
            try {
                CloneClass cc = locateCloneClass(clone, player.getCloneCode().code().toString());
                processEntity(player, cc);
            } catch (VocabularyException e) {
                LOGGER.fine(e.getMessage());
//                e.printStackTrace();
            }
        }

        //validate the Scoper
        Entity scoper = r.getScoper();
        if (scoper != null) {
            try {
                CloneClass cc = locateCloneClass(clone, scoper.getCloneCode().code().toString());
                processEntity(scoper, cc);
            } catch (VocabularyException e) {
                LOGGER.fine(e.getMessage());
                //e.printStackTrace();
            }
        }

        //process Participations
        aSet = r.getParticipation();
        if (aSet != null) {
            Iterator i = aSet.iterator();
            while (i.hasNext()) {
                try {
                    Participation p = (Participation) i.next();
                    CloneClass cc = locateCloneClass(clone, p.getCloneCode().code().toString());
                    processParticipation(p, cc);
                } catch (VocabularyException e) {
                    LOGGER.fine(e.getMessage());
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        // process Inbound RoleLinks.
        aSet = r.getInboundLink();
        if (aSet != null) {
            Iterator i = aSet.iterator();
            while (i.hasNext()) {
                try {
                    RoleLink rl = (RoleLink) i.next();
                    CloneClass cc = locateCloneClass(clone, rl.getCloneCode().code().toString());
                    processRoleLink(rl, cc);
                } catch (VocabularyException e) {
                    LOGGER.fine(e.getMessage());
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        // process Inbound RoleLinks.
        aSet = r.getOutboundLink();
        if (aSet != null) {
            Iterator i = aSet.iterator();
            while (i.hasNext()) {
                try {
                    RoleLink rl = (RoleLink) i.next();
                    CloneClass cc = locateCloneClass(clone, rl.getCloneCode().code().toString());
                    processRoleLink(rl, cc);
                } catch (VocabularyException e) {
                    LOGGER.fine(e.getMessage());
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        indent--;
    }

    public void processEntity(Entity e, CloneClass clone) {
        if(hasBeenValidated(e))return;
        //println("* Validate Entity : " + e.getCloneCode());
        LOGGER.finer("* Validate Entity : " + e.getCloneCode());
        indent++;
        List aSet = null;
        // Validate the Core Attributes.
        processRimObjectCoreAttributes(e, clone);

        // process Played Roles.
        aSet = e.getPlayedRole();
        if (aSet != null) {
            Iterator i = aSet.iterator();
            while (i.hasNext()) {
                try {
                    Role r = (Role) i.next();
                    CloneClass cc = locateCloneClass(clone, r.getCloneCode().code().toString());
                    processRole(r, cc);
                } catch (VocabularyException e1) {
                    LOGGER.fine(e1.getMessage());
//                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        // process Scoped Roles.
        aSet = e.getScopedRole();
        if (aSet != null) {
            Iterator i = aSet.iterator();
            while (i.hasNext()) {
                try {
                    Role r = (Role) i.next();
                    CloneClass cc = locateCloneClass(clone, r.getCloneCode().code().toString());
                    processRole(r, cc);
                } catch (VocabularyException e1) {
                    LOGGER.fine(e1.getMessage());
//                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        indent--;
    }


    public void processRoleLink(RoleLink rl, CloneClass clone) {
        if(hasBeenValidated(rl))return;
        //println("* Validate RoleLink : " + rl.getCloneCode());
        LOGGER.finer("* Validate RoleLink : " + rl.getCloneCode());
        indent++;
        // Validate the Core Attributes.
        processRimObjectCoreAttributes(rl, clone);

        // process Source Role
        Role source = rl.getSource();
        if (source != null) {
            try {
                CloneClass cc = locateCloneClass(clone, source.getCloneCode().code().toString());
                processRole(source, cc);
            } catch (VocabularyException e) {
                LOGGER.fine(e.getMessage());
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        // process Target Role
        Role target = rl.getSource();
        if (target != null) {
            try {
                CloneClass cc = locateCloneClass(clone, target.getCloneCode().code().toString());
                processRole(target, cc);
            } catch (VocabularyException e) {
                LOGGER.fine(e.getMessage());
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        indent--;
    }

    public void validateAttribute(CD code, Attribute metaAttr) {
        String loggingPrefix = null;
        
        // if the code is null, log it and move on.
        if (code == null) {
            warning("Code is null (not found in instance), could not validate : " +
                    metaAttr.getParent().getName() + "." + metaAttr.getName());
            return;
        }

        loggingPrefix = metaAttr.getParent().getName() + "." + metaAttr.getName();

        if (metaAttr.getFixedValues() == null) {
            //call validateCode
            ValidateCodeReturn vcr = null;
            try {
                vcr = ctsRuntimeOps.validateCode(null, code, null, null, null);
            } catch (Exception e) {
                Log.logException(this, e);
            }
            if (vcr != null) {
                if (vcr.nErrors.intValue() > 0) {
                    error("validateCode() " + loggingPrefix + " -->  " + vcr.detail[0].errorText.toString());
                } else {
                    success("validateCode() " + loggingPrefix + " --> true");
                }
            } else {
                error("validateCode() " + loggingPrefix + " --> ERROR : ctsRuntimeOps.validateCode() should not return null!");
            }
        } else {
            //call subsumes
            BL result = null;
            try {
                String mnumonic = metaAttr.getFixedValues()[0];
                String codeSystemId = oidLoader.get(metaAttr.getDomains()[0]).toString();
                CD parentCode = CDimpl.valueOf(mnumonic, codeSystemId);
                result = ctsRuntimeOps.subsumes(parentCode, code);

                if (result != null) {
                    if (result.isTrue()) {
                        success(loggingPrefix + " --> " + result.toString());
                    } else {
                        error("subsumes() " + loggingPrefix + " --> " + result.toString() +
                                " -  parent : " + codeSystemId + "-" + mnumonic +
                                ", child : " + code.codeSystem().toString() + "-" + code.code().toString());
                    }
                } else {
                    error("subsumes() " + loggingPrefix + " --> ERROR : ctsRuntimeOps.subsumes() should not return null!");
                }

            } catch (UnknownConceptCode e) {
                error("subsumes() " + loggingPrefix + " - Unknown Concept Code : " + e.getMessage());
            } catch (Exception e) {
                Log.logException(this, e);
            }
        }
    }

    /**
     * Look within the Rim object for a particular attribute.  Invokes 'get'AttributeName()
     * on the Rim Object
     *
     * @param rimObject
     * @param attributeName
     * @return A CD if found, null if not.
     * @throws VocabularyException If the methodname could not be found or invoked.
     */
    public CD locateRimAttribute(RimObject rimObject, String attributeName) throws VocabularyException, ClassCastException {
        CD rimAtt = null;
        String firstLetter = attributeName.substring(0, 1);
        String methodName = "get" + firstLetter.toUpperCase() + attributeName.substring(1);

        Method[] allMethods = rimObject.getClass().getMethods();
        Method theMethod = null;
        for (int i = 0; i < allMethods.length; i++) {
            if (allMethods[i].getName().equalsIgnoreCase(methodName)) {
                theMethod = allMethods[i];
                break;
            }
        }

        if (theMethod == null)
            throw new VocabularyException(methodName + "() could not be found on "
                    + rimObject.getClass().getName(), null);

        try {
            //rimAtt = (CD) theMethod.invoke(rimObject, null);
            rimAtt = (CD) theMethod.invoke(rimObject, new Object[0]);
        } catch (ClassCastException cce) {
            throw new ClassCastException("CD was not found when calling  " +
                    methodName + "() on " + rimObject.getClass().getName());

        } catch (Exception e) {
            throw new VocabularyException("Could not invoke " + methodName + "() on " +
                    rimObject.getClass().getName(), e);
        }

        return rimAtt;
    }

    public CloneClass locateCloneClass(CloneClass parentClone, String cloneName) throws VocabularyException {
        Feature f = xmlItsHelper.lookupMetadataByTag(parentClone, cloneName);
        CloneClass childCloneClass = null;
        try {
            childCloneClass = ((AssociationImpl) f).getTarget();
        } catch (Exception e) {
        }

        if (childCloneClass == null){
            throw new VocabularyException("child clone class couldnt' be found", null);
        }
        return childCloneClass;

        /*CloneClass childCloneClass = null;
        Iterator<Feature> i = parentClone.iterateChildren();
        Feature f = null;
        while (i.hasNext()) {
            f = (Feature) i.next();
            if (f instanceof CloneClassAssociationImpl || f instanceof ChoiceAssociationImpl || f instanceof CmetAssociationImpl) {
                if (cloneName.equals(f.getName())) {
                    try{childCloneClass = ((AssociationImpl) f).getTarget();}catch(Exception e){}
                }
            }
        }

        return childCloneClass;*/
    }

    private boolean hasBeenValidated(RimObject r){
        boolean found = validatedSet.contains(r);
        if(!found)validatedSet.add(r);
        return found;
    }

    public boolean validate(Object o, MessageType mt) {
        try {
            CloneClass cloneRoot = mt.getRootClass();
            if (o instanceof Act) {
                processAct((Act) o, cloneRoot);
            } else if (o instanceof Role) {
                processRole((Role) o, cloneRoot);
            } else if (o instanceof Entity) {
                processEntity((Entity) o, cloneRoot);
            } else if (o instanceof ActRelationship) {
                processActRelationship((ActRelationship) o, cloneRoot);
            } else if (o instanceof Participation) {
                processParticipation((Participation) o, cloneRoot);
            } else if (o instanceof RoleLink) {
                processRoleLink((RoleLink) o, cloneRoot);
            } else {
                error("Root of Graph not understood");
            }
        } catch (ValidationException e) {
            Log.logException(this, e);
        }
        if (debug) printTraversal();

        //if (errors.size() != 0) {
            LOGGER.warning("There were " + errors.size() + " vocabulary errors.");
            LOGGER.warning("There were " + warnings.size() + " vocabulary warnings.");
            //LOGGER.warning("There were " + ignored + " vocabulary core attributes ignored.");
            LOGGER.warning("There were " + successes + " vocabulary successes.");
        //}

        return (errors.size() == 0);
    }

    private void success(String s) {
        //LOGGER.warning(s);
        LOGGER.fine(s);
        successes++;
    }

    private void error(VocabularyException ve) {
        LOGGER.warning(ve.getMessage());
        errors.add(ve.getMessage());
    }

    private void error(String e) {
        LOGGER.warning(e);
        errors.add(e);
    }

    private void warning(String w) {
        LOGGER.fine(w);
        warnings.add(w);
    }

    private void ignore(ClassCastException e) {
        //LOGGER.warning(e.getMessage());
        //warnings.add(e.getMessage());
        ignored++;
    }

    private void println(String message) {
        this.print(message + "\n");
    }

    private void print(String message) {
        String in = "";
        for (int i = 0; i < indent; i++) {
            in += "   ";
        }
        System.out.print(indent + in + message);
    }

    private void debugTraversal(CS cloneCode) {
        String cloneName = "UNKNOWN";
        try {
            cloneName = cloneCode.code().toString();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        String in = "";
        for (int i = 0; i < indent; i++) {
            in += "   ";
        }
        debugTraversal.append(indent + in + cloneName + "\n");
    }

    private void printTraversal() {
        try {
            FileOutputStream out;
            PrintStream p;
            out = new FileOutputStream(FileUtil.getOutputDir().getAbsolutePath() + "//CoreAttTraversal.log");

            p = new PrintStream(out);
            p.println(debugTraversal.toString());
            p.close();
        } catch (Exception e) {
            System.err.println("Error writing to file");
        }
    }
}
