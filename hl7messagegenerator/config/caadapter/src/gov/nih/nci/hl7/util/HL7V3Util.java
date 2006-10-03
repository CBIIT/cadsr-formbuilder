/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/util/HL7V3Util.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
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


package gov.nih.nci.hl7.util;

import org.hl7.meta.LoaderException;
import org.hl7.meta.MessageType;
import org.hl7.meta.mif.MessageTypeLoaderAdapter;
import org.hl7.meta.impl.JdomMessageTypeLoader;
import org.hl7.meta.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Arrays;

/**
 * HL7 v3 Related utility class.
 *
 * @author OWNER: Eric Chen  Date: Jun 4, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $$Date: 2006-10-03 17:38:44 $
 * @since caAdapter v1.2
 */


public class HL7V3Util {

    static {
        Properties properties = new Properties();
        InputStream fi = null;
        try {
            fi = ClassLoader.getSystemResourceAsStream("message-types.properties");
            properties.load(fi);

            if (properties != null) {
                SUPPORTED_MESSAGE_TYPES = properties.getProperty("supported").split(",");
                NONPREFIXED_MESSAGE_TYPES = properties.getProperty("nonprefixed").split(",");
            }
        } catch (Exception ex) {
            Log.logException(HL7V3Util.class, "message-types.properties is not found", ex);
        } finally {
            if (fi != null) try {
                fi.close();
            } catch (IOException ignore) {
            }
        }
    }

    private static String[] SUPPORTED_MESSAGE_TYPES;
    private static String[] NONPREFIXED_MESSAGE_TYPES;
    private static final String[] HL7_DEFINED_VALUE_STRUCTURE_ATTRIBUTES = new String[] {
        "moodCode", "classCode", "typeCode", "determinerCode", "contextControlCode"
    };

    // getters.
    public static final String[] getAllSupportedMessageTypes() {
        return SUPPORTED_MESSAGE_TYPES;
    }
    public static String[] getNonPrefixedMessageTypes() {
        return NONPREFIXED_MESSAGE_TYPES;
    }
    public static final String[] getHL7DefinedValueStructureAttributes(){
        return HL7_DEFINED_VALUE_STRUCTURE_ATTRIBUTES;
    }

    public static MessageType getMessageType(Object messageIdentifier) throws FileNotFoundException, LoaderException {
        if (messageIdentifier instanceof String) {
            return getMessageType((String) messageIdentifier);
        } else if (messageIdentifier instanceof MessageType) {
            return (MessageType) messageIdentifier;
        } else if (messageIdentifier != null) {
            return getMessageType(messageIdentifier.toString());
        } else {
            System.out.println("Received a null messageIdentifier!");
            return null;
        }
    }

    /**
     * Since caAdapter version 1.3, parse MIF file instead of HMD file
     * @param messageId
     * @return Message Type
     * @throws FileNotFoundException
     * @throws LoaderException
     */
    public static MessageType getMessageType(String messageId) throws FileNotFoundException, LoaderException {

// Use HMD loader
//        String rimFile = Config.RIM_FILE;
//        String hmdFileName = messageId.substring(0, 5) + "HD" + messageId.substring(7, 13) + ".hmd";
//        String fullPathToHmdFile = FileUtil.filenameLocate(Config.HMD_LOCATION, hmdFileName);
//
//        JdomMessageTypeLoader jmtl = new JdomMessageTypeLoader(fullPathToHmdFile, rimFile);

        // Use MIF Loader
        MessageTypeLoaderAdapter jmtl = MessageTypeLoaderAdapter.getInstance();
        MessageType messageType = jmtl.loadMessageType(messageId);
        return messageType;
    }
    public static boolean isPrefixed(String messageIdentifier){
        if(Arrays.asList(NONPREFIXED_MESSAGE_TYPES).contains(messageIdentifier)){
            return false;
        }else{
            return true;
        }
    }


}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.17  2006/08/02 18:44:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.16  2006/06/02 22:18:50  chene
 * HISTORY      : 040002 improvement
 * HISTORY      :
 * HISTORY      : Revision 1.15  2006/05/03 21:26:43  chene
 * HISTORY      : Saved Point
 * HISTORY      :
 * HISTORY      : Revision 1.14  2006/01/12 22:20:23  chene
 * HISTORY      : caAdapter generic enhancement
 * HISTORY      :
 * HISTORY      : Revision 1.13  2006/01/04 18:12:57  giordanm
 * HISTORY      : remove some of the hard coded values from HL7V3Util and TransformationService - extract some of that logic to the message-types.properties file.
 * HISTORY      :
 * HISTORY      : Revision 1.12  2006/01/03 19:16:53  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.11  2006/01/03 18:56:26  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.10  2006/01/03 17:12:23  chene
 * HISTORY      : Messages types changed from from Code config to property files
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/12/30 16:12:55  chene
 * HISTORY      : Update JavaDoc
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/12/29 23:06:16  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/09/30 19:56:30  chene
 * HISTORY      : User Defined Structure Supported
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/09/08 19:37:04  chene
 * HISTORY      : Saved point
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/08/22 17:32:45  giordanm
 * HISTORY      : change the file attribute within BaseComponent from a String to a File,  this checkin also contains some refactor work to the FileUtil.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/07/07 16:40:44  chene
 * HISTORY      : Fixed message type error
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/07/06 21:46:12  jiangsc
 * HISTORY      : Save point
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/07/05 16:25:46  jiangsc
 * HISTORY      : Added new Functionality.
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/06/05 04:22:47  chene
 * HISTORY      : Firstcut of Meta Object Parser
 * HISTORY      :
 */
