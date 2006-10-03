/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/database/DatabaseMetaParserImpl.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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


package gov.nih.nci.hl7.database;

import gov.nih.nci.hl7.common.MetaException;
import gov.nih.nci.hl7.common.MetaParser;
import gov.nih.nci.hl7.database.impl.*;
import gov.nih.nci.hl7.util.Log;
import gov.nih.nci.hl7.util.MessageResources;
import gov.nih.nci.hl7.validation.Message;
import gov.nih.nci.hl7.validation.ValidatorResult;
import gov.nih.nci.hl7.validation.ValidatorResults;

import java.io.FileReader;

/**
 * Parses database meta files.
 *
 * @author OWNER: Matthew Giordano
 * @author LAST UPDATE $Author: marwahah $
 * @version $Revision: 1.1 $
 * @since caAdapter v1.2
 */
public class DatabaseMetaParserImpl implements MetaParser{
    private static final String LOGID = "$RCSfile: DatabaseMetaParserImpl.java,v $";
    public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/database/DatabaseMetaParserImpl.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $";

    public DatabaseMetaResult parse(FileReader metafile) throws MetaException {
        C_database c = null;
        DatabaseMetaResult databaseMetaResult = new DatabaseMetaResult();
        try {
            c = (C_database)C_database.unmarshalC_database(metafile);
            databaseMetaResult.setDatabaseMeta(processDatabase(c));
        } catch (Exception e) {
            Log.logException(this, e);
            Message msg = MessageResources.getMessage("GEN0", new Object[]{e.getMessage()});
            ValidatorResults validatorResults = new ValidatorResults();
            validatorResults.addValidatorResult(new ValidatorResult(ValidatorResult.Level.ERROR, msg));
            databaseMetaResult.setValidatorResults(validatorResults);
        }
        return databaseMetaResult;
    }

    private DatabaseMeta processDatabase(C_database c){
        DatabaseMeta database = new DatabaseMetaImpl();
        database.setName(c.getName());
        database.setUUID(c.getUuid());
        // process the root table.
        C_table[] cTables = c.getC_table();
        for (int i = 0; i < cTables.length; i++) {
            C_table c_table = cTables[i];
            database.addTable(processTable(c_table));
        }

        return database;
    }

    private TableMeta processTable(C_table t){
        TableMeta table = new TableMetaImpl();
        table.setName(t.getName());
        table.setUUID(t.getUuid());
        // process the columns.
        C_column[] cColumns = t.getC_column();
        for (int i = 0; i < cColumns.length; i++) {
            C_column cColumn = cColumns[i];
            table.addColumn(processColumn(cColumn));
        }
        C_foreignKey[] cForeignKeys= t.getC_foreignKey();
        for (int i = 0; i < cForeignKeys.length; i++) {
            C_foreignKey cForeignKey = cForeignKeys[i];
            table.addForeignKey(processForeignKey(cForeignKey));
        }

        return table;
    }

    private ColumnMeta processColumn(C_column c){
        ColumnMeta column = new ColumnMetaImpl();
        column.setName(c.getName());
        column.setUUID(c.getUuid());
        return column;
    }

    private ForeignKeyMeta processForeignKey(C_foreignKey c){
        ForeignKeyMetaImpl f = new ForeignKeyMetaImpl();
        f.setDetail(c.getDetail());
        f.setFormula((String)c.getFormula());
        f.setMaster(c.getMaster());
        f.setName(c.getName());
        f.setUUID(c.getUuid());
        return f;
    }
}
