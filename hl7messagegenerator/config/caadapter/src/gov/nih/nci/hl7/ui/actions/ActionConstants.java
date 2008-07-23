/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/ui/actions/ActionConstants.java,v 1.1 2006-10-03 17:38:42 marwahah Exp $
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


package gov.nih.nci.hl7.ui.actions;

import gov.nih.nci.hl7.util.Config;

/**
 * Define a list of constants that are used by menu and some of action definition.
 *
 * @author OWNER: Scott Jiang
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 *          revision    $Revision: 1.1 $
 *          date        $Date: 2006-10-03 17:38:42 $
 */
public interface ActionConstants
{
	//The naming convention:
	//NEW_MAP_FILE is used as the key to the text, while the NEW_MAP_FILE_TXT is used as the action name;
	//this is because the action name in display may be the same but we really need to distinguish them in a map
	//between a new and an open command.
	String NEW_MAP_FILE_TXT = Config.MAP_MODULE_NAME;
	String NEW_MAP_FILE = "New " + NEW_MAP_FILE_TXT;
	String NEW_CSV_SPEC_TXT = Config.CSV_MODULE_NAME;
	String NEW_CSV_SPEC = "New " + NEW_CSV_SPEC_TXT;
	String NEW_HL7_V3_MESSAGE_TXT = Config.HL7_V3_MESSAGE_MODULE_NAME;
	String NEW_HL7_V3_MESSAGE = "New " + NEW_HL7_V3_MESSAGE_TXT;
	String NEW_HSM_FILE_TXT = Config.HL7_V3_METADATA_MODULE_NAME;
	String NEW_HSM_FILE = "New " + NEW_HSM_FILE_TXT;

	String OPEN_MAP_FILE_TXT = NEW_MAP_FILE_TXT;
	String OPEN_MAP_FILE = "Open " + OPEN_MAP_FILE_TXT;
	String OPEN_CSV_SPEC_TXT = NEW_CSV_SPEC_TXT;
	String OPEN_CSV_SPEC = "Open " + OPEN_CSV_SPEC_TXT;
	String OPEN_HL7_V3_MESSAGE_TXT = NEW_HL7_V3_MESSAGE_TXT;
	String OPEN_HL7_V3_MESSAGE = "Open " + OPEN_HL7_V3_MESSAGE_TXT;
	String OPEN_HSM_FILE_TXT = NEW_HSM_FILE_TXT;
	String OPEN_HSM_FILE = "Open " + OPEN_HSM_FILE_TXT;

	String OPEN = "Open...";
	String CLOSE = "Close";
	String CLOSE_ALL = "Close All";
	String SAVE = "Save";
	String SAVE_AS = "Save As...";
	String EXIT = "Exit";
	String ABOUT = "About " + Config.PRODUCT_NAME + "...";
	String HELP = "Help - Contents and Index...";
	//String HELP_MANAGER = "Help Mgr";
	//String HELP_MANAGER2 = "Help Content Manager";   // For Tool Tip

	String GENERATE_REPORT = "Generate Report...";
	String VALIDATE = "Validate";
	String REFRESH = "Refresh";
}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.21  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.20  2006/01/25 16:45:48  chene
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.19  2006/01/25 16:33:46  chene
 * HISTORY      : Change the Help tooltip
 * HISTORY      :
 * HISTORY      : Revision 1.18  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.17  2006/01/03 18:26:16  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.16  2005/12/29 23:06:12  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.15  2005/12/14 21:37:16  jiangsc
 * HISTORY      : Updated license information
 * HISTORY      :
 * HISTORY      : Revision 1.14  2005/12/01 20:03:39  jiangsc
 * HISTORY      : Save point
 * HISTORY      :
 * HISTORY      : Revision 1.13  2005/11/29 16:23:56  jiangsc
 * HISTORY      : Updated License
 * HISTORY      :
 * HISTORY      : Revision 1.12  2005/11/16 16:29:42  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.11  2005/11/15 19:39:47  jiangsc
 * HISTORY      : Changed HL7 to caAdapter
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/10/26 17:21:09  umkis
 * HISTORY      : #156
 * HISTORY      : a)Help manager Menu is droped down
 * HISTORY      : b) Change the "About HL7SDK..." option to "About caAdapter..."
 * HISTORY      : c) Change the "Help..." option to "Contents and Index..."
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/10/12 17:21:55  jiangsc
 * HISTORY      : Constant value changed.
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/10/04 15:30:03  jiangsc
 * HISTORY      : Added VALIDATE constant.
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/09/29 21:13:56  jiangsc
 * HISTORY      : Added Generate Report action support
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/09/12 21:45:04  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/09/07 22:27:37  umkis
 * HISTORY      : no message
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/08/02 22:23:09  jiangsc
 * HISTORY      : Newly enhanced context-sensitive menus and toolbar.
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/07/27 22:41:16  jiangsc
 * HISTORY      : Consolidated context sensitive menu implementation.
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/07/27 13:57:42  jiangsc
 * HISTORY      : Added the first round of HSMPanel.
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/07/15 18:58:28  jiangsc
 * HISTORY      : 1) Reconstucted Menu bars;
 * HISTORY      : 2) Integrated FunctionPane to display property;
 * HISTORY      : 3) Enabled drag and drop functions to mapping panel.
 * HISTORY      :
 * HISTORY      : Add The Constant of HELP_MANAGER by Kisung 09/12/05
 * HISTORY      : Add The Constant of HELP_MANAGER2 by Kisung 09/12/05
 */