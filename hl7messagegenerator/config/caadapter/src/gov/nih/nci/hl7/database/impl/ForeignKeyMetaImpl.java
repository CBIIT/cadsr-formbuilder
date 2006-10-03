/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/database/impl/ForeignKeyMetaImpl.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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


package gov.nih.nci.hl7.database.impl;

import gov.nih.nci.hl7.common.MetaObjectImpl;
import gov.nih.nci.hl7.database.ForeignKeyMeta;
import gov.nih.nci.hl7.util.PropertiesResult;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ForeignKeyMeta.
 *
 * @author OWNER: Eric Chen  Date: Sep 15, 2005
 * @author LAST UPDATE: $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $$Date: 2006-10-03 17:38:25 $
 * @since caAdapter v1.2
 */


public class ForeignKeyMetaImpl extends MetaObjectImpl implements ForeignKeyMeta
{
    private String master;
    private String detail;
    private String formula;


    public String getMaster()
    {
        return master;
    }

    public void setMaster(String master)
    {
        this.master = master;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public String getFormula()
    {
        return formula;
    }

    public void setFormula(String formula)
    {
        this.formula = formula;
    }

    public String toString()
    {
        return getName() + "[FK]";
    }

    /**
	 * This functions will return an array of PropertyDescriptor that would
	 * help reflection and/or introspection to figure out what information would be
	 * presented to the user.
	 * <p/>
	 * descendant classes are free to override to provide additional information.
	 */
    public PropertiesResult getPropertyDescriptors() throws Exception
    {
        Class beanClass = this.getClass();

        PropertyDescriptor _name = new PropertyDescriptor("Name", beanClass, "getName", null);
        PropertyDescriptor _master = new PropertyDescriptor("Master", beanClass, "getMaster", null);
        PropertyDescriptor _detail = new PropertyDescriptor("Detail", beanClass, "getDetail", null);
        PropertyDescriptor _formula = new PropertyDescriptor("Formula", beanClass, "getFormula", null);

        List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>();
		propList.add(_name);
		propList.add(_master);
		propList.add(_detail);
		propList.add(_formula);

        PropertiesResult result = new PropertiesResult();
        result.addPropertyDescriptors(this, propList);

        return result;
    }

}

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.8  2006/08/02 18:44:20  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.7  2006/01/03 19:16:52  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.6  2006/01/03 18:54:25  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/12/29 23:06:15  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/12/29 15:39:05  chene
 * HISTORY      : Optimize imports
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/10/06 20:40:40  giordanm
 * HISTORY      : prettying the code in the database package.  javadoc, license headers, etc.
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/09/20 15:38:09  chene
 * HISTORY      : Add the foreign key java property support
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/09/19 20:49:56  chene
 * HISTORY      : Add the foreign key java beans
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/09/15 20:57:01  chene
 * HISTORY      : Database prototype
 * HISTORY      :
 */
