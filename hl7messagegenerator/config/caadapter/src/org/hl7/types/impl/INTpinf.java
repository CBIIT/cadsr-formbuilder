package org.hl7.types.impl;

import org.hl7.types.BL;
import org.hl7.types.INT;
import org.hl7.types.ORD;
import org.hl7.types.REAL;

public class INTpinf extends INTnull {
	
	static final INTpinf VALUE = new INTpinf();	

	private INTpinf(){
		super(NullFlavorImpl.PINF);	
	}
	
	public BL lessOrEqual(ORD x) { 
		if (this.compares(x).isTrue())
			return BLimpl.FALSE;
		else
			return BLimpl.NA; 
	}
	
  public BL lessOrEqual(int x) { return BLimpl.FALSE; }
  public BL lessOrEqual(long x) { return BLimpl.FALSE; }
  public BL lessOrEqual(float x) { return BLimpl.FALSE; }
  public BL lessOrEqual(double x) { return BLimpl.FALSE; }

  public BL compares(ORD x) { 
  	return BLimpl.valueOf(x instanceof REAL || x instanceof INT).and(x.nonNull());
  }
  public BL compares(long x) { return BLimpl.TRUE; }

  public BL isZero() { return BLimpl.FALSE; }
  public BL nonZero() { return BLimpl.TRUE; }

  public BL isOne() { return BLimpl.FALSE; }

  public INT negated() { return INTnull.NINF; }
  public BL isNegative() { return BLimpl.FALSE; }
  public BL nonNegative() { return BLimpl.TRUE; }
}
