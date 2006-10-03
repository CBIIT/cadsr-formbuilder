/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/CloneAttributeMetaComparator.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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

import java.util.Comparator;

/**
 * This class defines a customized comparator to compare two CloneAttributeMeta and determine
 * their sequence base on sortKey. If it is CloneAttributeMultipleMeta, because sortKey is same,
 * so it will base on multipleSequenceNumber.
 *
 * @author OWNER: Eric Chen
 * @author LAST UPDATE $Author: marwahah $
 * @version Since caAdapter v1.2
 * @revision $Revision: 1.1 $
 * @date $Date: 2006-10-03 17:38:25 $
 */
public class CloneAttributeMetaComparator implements Comparator
{
    /**
     * Logging constant used to identify source of log entry, that could be later used to create
     * logging mechanism to uniquely identify the logged class.
     */
    private static final String LOGID = "$RCSfile: CloneAttributeMetaComparator.java,v $";

    /**
     * String that identifies the class version and solves the serial version UID problem.
     * This String is for informational purposes only and MUST not be made final.
     *
     * @see <a href="http://www.visi.com/~gyles19/cgi-bin/fom.cgi?file=63">JBuilder vice javac serial version UID</a>
     */
    public static String RCSID = "$Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/meta/CloneAttributeMetaComparator.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $";

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p/>
     * The implementor must ensure that <tt>sgn(compare(x, y)) ==
     * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>compare(x, y)</tt> must throw an exception if and only
     * if <tt>compare(y, x)</tt> throws an exception.)<p>
     * <p/>
     * The implementor must also ensure that the relation is transitive:
     * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
     * <tt>compare(x, z)&gt;0</tt>.<p>
     * <p/>
     * Finally, the implementer must ensure that <tt>compare(x, y)==0</tt>
     * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
     * <tt>z</tt>.<p>
     * <p/>
     * It is generally the case, but <i>not</i> strictly required that
     * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."
     *
     * @param o  the first object to be compared.
     * @param o1 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
     * @throws ClassCastException if the arguments' types prevent them from
     *                            being compared by this Comparator.
     */
    public int compare(Object o, Object o1)
    {

        if (!(o instanceof CloneAttributeMeta) && !(o1 instanceof CloneAttributeMeta))
        {//unknown how to compare, so assume they are equal.
            return 0;
        }
        else if (!(o instanceof CloneAttributeMeta) && (o1 instanceof CloneAttributeMeta))
        {
            return 1;
        }
        else if ((o instanceof CloneAttributeMeta) && !(o1 instanceof CloneAttributeMeta))
        {
            return -1;
        }
        CloneAttributeMeta thisObj = (CloneAttributeMeta) o;
        CloneAttributeMeta thatObj = (CloneAttributeMeta) o1;
        if (thisObj.getSortKey() != thatObj.getSortKey())
        {
            return thisObj.getSortKey() - thatObj.getSortKey();
        }
        else
        {
            if (!(o instanceof CloneAttributeMultipleMeta) && !(o1 instanceof CloneAttributeMultipleMeta))
            {
                return 0;
            }
            else if (!(o instanceof CloneAttributeMultipleMeta) && (o1 instanceof CloneAttributeMultipleMeta))
            {
                return 1;
            }
            else if ((o instanceof CloneAttributeMultipleMeta) && !(o1 instanceof CloneAttributeMultipleMeta))
            {
                return -1;
            }
            CloneAttributeMultipleMeta aObj = (CloneAttributeMultipleMeta) o;
            CloneAttributeMultipleMeta bObj = (CloneAttributeMultipleMeta) o1;
            return aObj.getMultipleSequenceNumber() - bObj.getMultipleSequenceNumber();
        }
    }
}

/**
 * HISTORY      : Revision 1.1  2005/08/03 22:07:56  jiangsc
 * HISTORY      : Save Point.
 * HISTORY      :
 */
