/* The contents of this file are subject to the Health Level-7 Public
* License Version 1.0 (the "License"); you may not use this file
* except in compliance with the License. You may obtain a copy of the
* License at http://www.hl7.org/HPL/
*
* Software distributed under the License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
* the License for the specific language governing rights and
* limitations under the License.
*
* The Original Code is all this file.
*
* The Initial Developer of the Original Code is .
* Portions created by Initial Developer are Copyright (C) 2002-2004 
* Health Level Seven, Inc. All Rights Reserved.
*
* Contributor(s): 
*/
package org.hl7.types.impl;

import java.util.UUID;

import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.NullFlavor;

/**
 * Root class for all types in this implementation.
 */
public abstract class ANYimpl implements ANY {
    private NullFlavor _nullFlavor = null;

    protected ANYimpl(NullFlavor nf) {
        // XXX: we assume that nf is NullFlavorImpl, that may not be true always
        this._nullFlavor = (NullFlavorImpl) nf;
    }

    public NullFlavor nullFlavor() {
        if (_nullFlavor == null)
            _nullFlavor = NullFlavorImpl.NOT_A_NULL_FLAVOR;
        return _nullFlavor;
    }

    public boolean isNullJ() {
        return isNull().isTrue();
    }

    public boolean nonNullJ() {
        return nonNull().isTrue();
    }

    public boolean notApplicableJ() {
        return notApplicable().isTrue();
    }

    public boolean unknownJ() {
        return unknown().isTrue();
    }

    public boolean otherJ() {
        return other().isTrue();
    }

    public BL isNull() {
        return nullFlavor().implies(NullFlavorImpl.NI);
    }

    public BL nonNull() {
        return nullFlavor().isNull();
    }

    public BL notApplicable() {
        return nullFlavor().implies(NullFlavorImpl.NA);
    }

    public BL unknown() {
        return nullFlavor().implies(NullFlavorImpl.UNK);
    }

    public BL other() {
        return nullFlavor().implies(NullFlavorImpl.OTH);
    }

    /**
     * We require that all inheritors of ANYimpl have their own
     * equal function for all of its compatible types.
     * <p/>
     * The equals function is not a no-brainer. THINK!
     * <p/>
     * Equal must first test for type compatibility (not necessarily
     * type-equality.) Be sure to test for the interface, not the
     * implementation type, as the same type may have different
     * implementations.
     */
    public abstract BL equal(ANY x);

    /**
     * Unique object identifier for persistence. This is an assigned UUID, so that it will always work.
     */
    private String _internalId = null;

    public String getInternalId() {
        if (_internalId == null)
            _internalId = UUID.randomUUID().toString();
        return _internalId;
    }

    protected void setInternalId(String value) {
        _internalId = value;
    }


    /**
     * Returns true is this.equal(that) is true.
     */
    public boolean equals(Object that) {
        if (that instanceof ANY) {
            return this.equal((ANY) that).isTrue();
        } else {
            return false;
        }
    }

    /* FIXME: also need hashCode! */
}
