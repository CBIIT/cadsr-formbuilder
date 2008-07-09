/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/csv/CSVMetaBuilder.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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


package gov.nih.nci.hl7.csv;

import gov.nih.nci.hl7.common.MetaBuilderBase;
import gov.nih.nci.hl7.common.MetaException;
import gov.nih.nci.hl7.common.MetaObject;
import gov.nih.nci.hl7.csv.meta.CSVFieldMeta;
import gov.nih.nci.hl7.csv.meta.CSVMeta;
import gov.nih.nci.hl7.csv.meta.CSVSegmentMeta;
import gov.nih.nci.hl7.csv.meta.impl.C_csvMetadata;
import gov.nih.nci.hl7.csv.meta.impl.C_field;
import gov.nih.nci.hl7.csv.meta.impl.C_segment;
import gov.nih.nci.hl7.csv.meta.impl.C_segmentItem;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;

/**
 * Builds a csv specification from a csv meta object graph.  Converts meta
 * objects to castor objects and then marshals them.
 *
 * @author OWNER: Matthew Giordano
 * @author LAST UPDATE $Author: marwahah $
 * @since     caAdapter v1.2
 * @version    $Revision: 1.1 $
 * @date        $Date: 2006-10-03 17:38:25 $
 */

public class CSVMetaBuilder extends MetaBuilderBase {

    private static CSVMetaBuilder metaBuilder = null;

    private static final String LOGID = "$RCSfile: CSVMetaBuilder.java,v $";
    public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/csv/CSVMetaBuilder.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $";

    private CSVMetaBuilder()
    {
    }


    public void build(OutputStream outputStream, MetaObject meta) throws MetaException {
        try {

            if (!(meta instanceof CSVMeta)) {
                throw new MetaException("Meta object should be an instance of CSVMeta", null);
            } else {
                CSVMeta csvMeta = (CSVMeta) meta;

                // setup the castor objects.
                C_csvMetadata c = new C_csvMetadata();
                c.setC_segment(processSegment(csvMeta.getRootSegment()));
                c.setVersion(new BigDecimal("1.2"));
                c.setUuid(meta.getUUID());

                // set up the Source.
                StringWriter marshalString = new StringWriter();
                //c.marshal(marshalString);
                Marshaller marshaller = new Marshaller(marshalString);
                marshaller.setSuppressXSIType(true);
                marshaller.marshal(c);

                ByteArrayInputStream is = new ByteArrayInputStream(marshalString.toString().getBytes());
                // transform it.
                Transformer t = TransformerFactory.newInstance().newTransformer();
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.transform(new StreamSource(is), new StreamResult(outputStream));
            }
        } catch (IOException e) {
            throw new MetaException(e.getMessage(), e);
        }catch (MarshalException e) {
            throw new MetaException(e.getMessage(), e);
        } catch (ValidationException e) {
            throw new MetaException(e.getMessage(), e);
        } catch (TransformerException e) {
            throw new MetaException(e.getMessage(), e);
        }
    }

    private C_segment processSegment(CSVSegmentMeta metaSegment) {
        C_segment castorSegment = new C_segment();
        castorSegment.setName(metaSegment.getName());
        castorSegment.setUuid(metaSegment.getUUID());

        List<CSVSegmentMeta> metaChildSegments = metaSegment.getChildSegments();
        for (int i = 0; i < metaChildSegments.size(); i++) {
            CSVSegmentMeta csvSegmentMeta = metaChildSegments.get(i);
            C_segmentItem si = new C_segmentItem();
            si.setC_segment(processSegment(csvSegmentMeta));
            castorSegment.addC_segmentItem(si);
        }

        List<CSVFieldMeta> metaFields = metaSegment.getFields();
        for (int i = 0; i < metaFields.size(); i++) {
            CSVFieldMeta csvFieldMeta = metaFields.get(i);
            C_segmentItem si = new C_segmentItem();
            si.setC_field(processField(csvFieldMeta));
            castorSegment.addC_segmentItem(si);
        }
        return castorSegment;
    }

    private C_field processField(CSVFieldMeta metaField) {
        C_field castorField = new C_field();
        castorField.setColumn(metaField.getColumn());
        castorField.setName(metaField.getName());
        castorField.setUuid(metaField.getUUID());
        return castorField;
    }

    public static CSVMetaBuilder getInstance()
    {

        if (metaBuilder == null)
        {
            metaBuilder = new CSVMetaBuilder();
        }
        return metaBuilder;
    }
}