/**
*	The caAdapter Software License, Version 1.0
*
*	Copyright 2001 SAIC. This software was developed in conjunction with the National Cancer 
*	Institute, and so to the extent government employees are co-authors, any rights in such works 
*	shall be subject to Title 17 of the United States Code, section 105.
* 
*	Redistribution and use in source and binary forms, with or without modification, are permitted 
*	provided that the following conditions are met:
*	 
*	1. Redistributions of source code must retain the above copyright notice, this list of conditions 
*	and the disclaimer of Article 3, below.  Redistributions in binary form must reproduce the above 
*	copyright notice, this list of conditions and the following disclaimer in the documentation and/or 
*	other materials provided with the distribution.
* 
*	2.  The end-user documentation included with the redistribution, if any, must include the 
*	following acknowledgment:
*	
*	"This product includes software developed by the SAIC and the National Cancer 
*	Institute."
*	
*	If no such end-user documentation is to be included, this acknowledgment shall appear in the 
*	software itself, wherever such third-party acknowledgments normally appear.
*	 
*	3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or 
*	promote products derived from this software.
*	 
*	4. This license does not authorize the incorporation of this software into any proprietary 
*	programs.  This license does not authorize the recipient to use any trademarks owned by either 
*	NCI or SAIC-Frederick.
*	 
*	
*	5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED 
*	WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
*	MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE 
*	DISCLAIMED.  IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR 
*	THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
*	EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
*	PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
*	PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY 
*	OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
*	NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
*	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*	
 */
 
package org.hl7.factories;

import org.hl7.util.FactoryException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class CtsFactory {
    protected static final Logger LOGGER = Logger.getLogger("org.hl7.factories");
    private Properties props_ = new Properties();

    public CtsFactory() throws FactoryException {
        InputStream fi = null;
        try {
            fi = ClassLoader.getSystemResourceAsStream("cts-map.properties");
            props_.load(fi);
        } catch (IOException ex) {
            throw new FactoryException("CtsFactory properties file could " +
                    "not be loaded", ex);
        } finally {
            if (fi != null) try {
                fi.close();
            } catch (IOException ignore) {
            }
        }
    }

    public org.hl7.CTSMAPI.RuntimeOperations getMessageRuntimeOperations()
            throws FactoryException {
        String implName = (String) props_.get("RuntimeOperations");
        org.hl7.CTSMAPI.RuntimeOperations runtimeOps = null;
        try {
            runtimeOps = (org.hl7.CTSMAPI.RuntimeOperations)
                    Class.forName(implName).newInstance();
        } catch (Exception e) {
            throw new FactoryException("Could not instantiate " +
                    "MessageRuntime Operations : " + implName);
        } 
        return runtimeOps;
    }

    public boolean validateCoreAttributes(){
        String implName = (String) props_.get("validate.core.attributes");
        if(implName!=null)
            return true;
        else
            return false;
    }
}
