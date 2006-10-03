package org.hl7.types.impl;

import org.hl7.types.BL;
import org.hl7.types.INT;
import org.hl7.types.ORD;
import org.hl7.types.REAL;

public class INTninf extends INTnull {
	
	static final INTninf VALUE = new INTninf();	

	private INTninf(){
		super(NullFlavorImpl.NINF);	
	}	

	public BL lessOrEqual(ORD x) { 
		if (this.compares(x).isTrue())
			return BLimpl.TRUE;
		else
			return BLimpl.NA; 
	}
	
  public BL lessOrEqual(int x) { return BLimpl.TRUE; }
  public BL lessOrEqual(long x) { return BLimpl.TRUE; }
  public BL lessOrEqual(float x) { return BLimpl.TRUE; }
  public BL lessOrEqual(double x) { return BLimpl.TRUE; }

  public BL compares(ORD x) { 
  	return BLimpl.valueOf(x instanceof REAL || x instanceof INT).and(x.nonNull());
  }
  public BL compares(long x) { return BLimpl.TRUE; }

  public BL isZero() { return BLimpl.FALSE; }
  public BL nonZero() { return BLimpl.FALSE; }

  public BL isOne() { return BLimpl.FALSE; }

  public INT negated() { return INTnull.PINF; }
  public BL isNegative() { return BLimpl.TRUE; }
  public BL nonNegative() { return BLimpl.FALSE; }	
}