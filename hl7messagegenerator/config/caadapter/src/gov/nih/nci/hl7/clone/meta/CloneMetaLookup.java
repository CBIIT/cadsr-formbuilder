/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/CloneMetaLookup.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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

import gov.nih.nci.hl7.common.MetaLookup;
import gov.nih.nci.hl7.common.MetaObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provides quick access when doing clone metadata lookups.
 *
 * @author OWNER: Matthew Giordano
 * @author LAST UPDATE $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $Date: 2006-10-03 17:38:25 $
 * @since caAdapter v1.2
 */

public class CloneMetaLookup implements MetaLookup
{

	//todo need enhance the cache management
	//  1) Add time stamp as a object instance at CloneMetaLookup
	//  2) Instance new thread to clean up CloneMetaLookup if its life is longer than 30 minutes
	// OR
	// 1) using SoftReference object to wrapper the key and value

	private java.util.Map<String, MetaObject> table = new HashMap<String, MetaObject>();
	private static java.util.Map<CloneMeta, CloneMetaLookup> instances = new HashMap<CloneMeta, CloneMetaLookup>();

	public CloneMetaLookup(HL7V3Meta meta)
	{
		initCloneMeta(meta.getRootCloneMeta());
	}

	public CloneMetaLookup(CloneMeta rootCloneMeta)
	{
		initCloneMeta(rootCloneMeta);
	}

	public static CloneMetaLookup getCloneMetaLookup(HL7V3Meta hl7v3meta)
	{
		final CloneMeta rootCloneMeta = hl7v3meta.getRootCloneMeta();
		CloneMetaLookup cloneMetaLookup = instances.get(rootCloneMeta);
		if (cloneMetaLookup == null)
		{
			cloneMetaLookup = new CloneMetaLookup(hl7v3meta);
			instances.put(rootCloneMeta, cloneMetaLookup);
		}
		return cloneMetaLookup;
	}

	public static CloneMetaLookup getCloneMetaLookup(CloneMeta cloneMeta)
	{
		CloneMeta childMeta = cloneMeta;
		CloneMeta parentMeta = childMeta.getParentMeta();

		while (parentMeta != null)
		{
			childMeta = parentMeta;
			parentMeta = childMeta.getParentMeta();
		}

		CloneMetaLookup cloneMetaLookup = instances.get(childMeta);
		if (cloneMetaLookup == null)
		{
			cloneMetaLookup = new CloneMetaLookup(childMeta);
			instances.put(childMeta, cloneMetaLookup);
		}
		return cloneMetaLookup;
	}


	private void initCloneMeta(CloneMeta meta)
	{
		table.put(meta.getUUID(), meta);
		// process the children
		List<CloneMeta> childClones = meta.getChildClones();
		for (int i = 0; i < childClones.size(); i++)
		{
			initCloneMeta(childClones.get(i));
		}
		// process the attributes
		List<CloneAttributeMeta> attributes = meta.getAttributes();
		for (int i = 0; i < attributes.size(); i++)
		{
			initAttributeMeta(attributes.get(i));
		}
	}

	private void initAttributeMeta(CloneAttributeMeta cloneAttributeMeta)
	{
		table.put(cloneAttributeMeta.getUUID(), cloneAttributeMeta);
		List<CloneAttributeMeta> attributes = cloneAttributeMeta.getChildAttributes();
		for (int i = 0; i < attributes.size(); i++)
		{
			initAttributeMeta(attributes.get(i));
		}
		List<CloneDatatypeFieldMeta> cloneDatatypeFieldMeta = cloneAttributeMeta.getDatatypeFields();
		for (int i = 0; i < cloneDatatypeFieldMeta.size(); i++)
		{
			initDatatypeFieldMeta(cloneDatatypeFieldMeta.get(i));
		}
	}

	private void initDatatypeFieldMeta(CloneDatatypeFieldMeta cloneDatatypeFieldMeta)
	{
		table.put(cloneDatatypeFieldMeta.getUUID(), cloneDatatypeFieldMeta);
	}

	public MetaObject lookup(String uuid)
	{
		return table.get(uuid);
	}

	/**
	 * Return all key elements.
	 *
	 * @return all key elements.
	 */
	public Set getAllKeys()
	{
		Set keySet = new HashSet(table.keySet());
		return keySet;
	}

}
