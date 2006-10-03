/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/DatatypeFieldCardinality.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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


package gov.nih.nci.hl7.clone.meta;

import gov.nih.nci.hl7.util.Log;
import org.hl7.meta.Cardinality;
import org.hl7.meta.Datatype;
import org.hl7.meta.UnknownDatatypeException;
import org.hl7.meta.impl.DatatypeMetadataFactoryDatatypes;
import org.hl7.meta.impl.DatatypeMetadataFactoryImpl;
import org.hl7.xml.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describle the HL7 v3 datatype field cardinality
 *
 * @author OWNER: Eric Chen  Date: Nov 7, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $$Date: 2006-10-03 17:38:25 $
 * @since caAdapter v1.2
 */


public class DatatypeFieldCardinality
{
    private static final Map<Datatype, List<String>> REQUIRED_DATATYPE_FIELD_DICTIONARY = new HashMap<Datatype, List<String>>();

    static
    {
        // II
        REQUIRED_DATATYPE_FIELD_DICTIONARY.put(
            DatatypeMetadataFactoryDatatypes.instance().IITYPE,
            Arrays.asList(new String[]{IIPresenter.ATTR_ROOT}));

        //PQ
        REQUIRED_DATATYPE_FIELD_DICTIONARY.put(
            DatatypeMetadataFactoryDatatypes.instance().PQTYPE,
            Arrays.asList(new String[]{PQPresenter.ATTR_VALUE, PQPresenter.ATTR_UNIT}));

        //CS
        REQUIRED_DATATYPE_FIELD_DICTIONARY.put(
            DatatypeMetadataFactoryDatatypes.instance().CSTYPE,
            Arrays.asList(new String[]{CSPresenter.ATTR_CODE})); // lose the codeSystem cardinality because we could find codeSystem
//            Arrays.asList(new String[]{CSPresenter.ATTR_CODE, CSPresenter.ATTR_CODE_SYSTEM}));

        //CE
        REQUIRED_DATATYPE_FIELD_DICTIONARY.put(
            DatatypeMetadataFactoryDatatypes.instance().CETYPE,
            Arrays.asList(new String[]{CEPresenter.ATTR_CODE, CEPresenter.ATTR_CODE_SYSTEM}));

        //CD
        REQUIRED_DATATYPE_FIELD_DICTIONARY.put(DatatypeMetadataFactoryDatatypes.instance().CDTYPE,
            Arrays.asList(new String[]{CDPresenter.ATTR_CODE, CDPresenter.ATTR_CODE_SYSTEM}));

        //CV
        REQUIRED_DATATYPE_FIELD_DICTIONARY.put(DatatypeMetadataFactoryDatatypes.instance().CVTYPE,
            Arrays.asList(new String[]{CVPresenter.ATTR_CODE, CVPresenter.ATTR_CODE_SYSTEM}));

    }


    /**
     *
     * @param datatypeName
     * @param datatypeFieldName
     * @return
     */
    public static Cardinality getCardinality(String datatypeName, String datatypeFieldName)
    {
        try
        {
            Datatype dataType = DatatypeMetadataFactoryImpl.instance().create(datatypeName);
            List<String> requiredFields = REQUIRED_DATATYPE_FIELD_DICTIONARY.get(dataType);
            if (requiredFields != null && requiredFields.contains(datatypeFieldName))
            {
                return Cardinality.create("1..1");
            }
            else
            {
                return Cardinality.create("0..1");
            }

        }
        catch (UnknownDatatypeException e)
        {
            Log.logException(null, e);
            return Cardinality.create("0..1");
        }
    }

//    public static void main(String[] args)
//    {
//        System.out.println(DatatypeFieldCardinality.getCardinality("II", "root"));
//    }

}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.11  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.10  2006/06/02 22:18:50  chene
 * HISTORY      : 040002 improvement
 * HISTORY      :
 * HISTORY      : Revision 1.9  2006/01/06 22:04:18  chene
 * HISTORY      : Fix some of cardinality issue
 * HISTORY      :
 * HISTORY      : Revision 1.8  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.7  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/12/29 15:39:04  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/12/02 18:04:25  chene
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/11/07 21:23:06  chene
 * HISTORY      : Add datatype cardinality support
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/11/07 20:13:57  chene
 * HISTORY      : Rename sub_datatypes_map to abstract_datatypes_map
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/11/07 19:55:56  chene
 * HISTORY      : no message
 * HISTORY      :
 */
