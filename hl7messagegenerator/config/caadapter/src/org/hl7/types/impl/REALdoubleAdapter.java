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

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.INT;
import org.hl7.types.ORD;
import org.hl7.types.QTY;
import org.hl7.types.REAL;
import org.hl7.types.ValueFactory;
import org.regenstrief.util.RegexTokenizer;

/**
 * REAL adapter to Java builtin long. We will have BigInteger as well.
 */
public final class REALdoubleAdapter extends ORDimpl implements REAL {
	double _value;

	public INT _precision;

	/**
	 * Some flyweight's for common constants. Why not? Would be interesting to know how much they end up being used. If we
	 * have those and a byte-size wrapper, we can move them there. But I assume int is the smallest wrapper.
	 */
	public static REALdoubleAdapter MINUS_ONE = new REALdoubleAdapter(-1, INTnull.PINF);

	public static REALdoubleAdapter ZERO = new REALdoubleAdapter(0, INTnull.PINF);

	public static REALdoubleAdapter ONE_TENTH = new REALdoubleAdapter(0.1, INTnull.PINF);

	public static REALdoubleAdapter HALF = new REALdoubleAdapter(0.5, INTnull.PINF);

	public static REALdoubleAdapter ONE = new REALdoubleAdapter(1, INTnull.PINF);

	public static REALdoubleAdapter TWO = new REALdoubleAdapter(2, INTnull.PINF);

	public static REALdoubleAdapter TEN = new REALdoubleAdapter(10, INTnull.PINF);

	public static INT DOUBLE_PRECISION = ValueFactory.getInstance().INTvalueOf(15); // To avoid "1.9999 Issue"

	public static INT FLOAT_PRECISION = ValueFactory.getInstance().INTvalueOf(7); // To avoid "1.9999 Issue"

	private static final DecimalFormat NOPOINT_FORMAT = new DecimalFormat(".###############E0");

	private REALdoubleAdapter(double value, INT precision) {
		super(null);
		this._value = value;
		this._precision = precision;
	}

	/**
	 * We should probably have a small array of reuseable objects of this type such that we can avoid object creation. How
	 * do we know, though, whether someone's still holding on to those? I guess weak references are the thing to use ...
	 * later.
	 */

	public static REAL valueOf(double x, INT precision) {
		if (precision.equal(INTnull.PINF).isTrue()) {
			if (x == 0) {
				return REALdoubleAdapter.ZERO;
			} else if (x == 1) {
				return REALdoubleAdapter.ONE;
			} else if (x == 2) {
				return REALdoubleAdapter.TWO;
			} else if (x == -1.0) {
				return REALdoubleAdapter.MINUS_ONE;
			} else if (x == 10) {
				return REALdoubleAdapter.TEN;
			} else if (x == 0.1) {
				return REALdoubleAdapter.ONE_TENTH;
			} else if (x == 0.5) {
				return REALdoubleAdapter.HALF;
			}
		}
		return new REALdoubleAdapter(x, precision);
	}

	public static REAL valueOf(String str) {
		double value = Double.parseDouble(str);
		INT precision = countSigFigs(str);
		if (precision.nonNull().and(DOUBLE_PRECISION.lessOrEqual(precision)).isTrue()) {
			precision = DOUBLE_PRECISION;
		}
		return valueOf(value, precision);
	}

	public static REAL valueOf(int x) {
		return valueOf(x, INTnull.PINF);
	}

	public static REAL valueOf(long x) {
		return valueOf(x, INTnull.PINF);
	}

	public static REAL valueOf(float x) {
		return valueOf(x, FLOAT_PRECISION);
	}

	public static REAL valueOf(double x) {
		return valueOf(x, DOUBLE_PRECISION);
	}

	public INT precision() {
		return _precision;
	}

	/**
	 * Test for equality.
	 */
	public BL equal(ANY that) {
		if (that instanceof QTY) {
			if (that instanceof REAL) {
				if (that instanceof REALdoubleAdapter) {
					double thatValue = ((REALdoubleAdapter) that)._value;
					INT thatPrecision = ((REAL) that).precision();
					if (this.precision().isNull().isTrue() && thatPrecision.isNull().isTrue())
						return BLimpl.valueOf(_value == thatValue);
					else if (this.precision().equal(thatPrecision).isFalse())
						return BLimpl.FALSE;
					else { // XXX: this is a nifty and cool hack: we use the IEEE floating point format to round off extraneous digits in comparison
						int p = _precision.intValue() * 33219 / 10000;
						long xbits = Double.doubleToLongBits(_value);
						long ybits = Double.doubleToLongBits(thatValue);
						if (p > 0 && p < 52) {
							long mask = ~((0x1L << (52 - p)) - 1);
							// System.out.println("p=" + p);
							long maskedx = xbits & mask;
							long maskedy = ybits & mask;
							// System.out.println("" + _value + " bits=" + Long.toHexString(xbits) + " mask=" + Long.toHexString(mask)
							// + " masked=" + Long.toHexString(maskedx) + " r=" + Double.longBitsToDouble(maskedx));
							// System.out.println("" + thatValue + " bits=" + Long.toHexString(ybits) + " mask=" +
							// Long.toHexString(mask) + " masked=" + Long.toHexString(maskedy) + " r=" +
							// Double.longBitsToDouble(maskedy));

							return BLimpl.valueOf(maskedx == maskedy);
						} else {
							return BLimpl.valueOf(_value == thatValue);
						}
					}
				} else {
					return this.minus((REAL) that).isZero().and(
							this.precision().equal(((REAL) that).precision()).or(
									this.precision().isNull().and(((REAL) that).precision().isNull())));
				}
			} else {
				return this.minus((QTY) that).isZero();
			}
		} else {
			return BLimpl.FALSE;
		}
	}

	public BL equal(int x) {
		return BLimpl.valueOf(this._value == x);
	}

	public BL equal(long x) {
		return BLimpl.valueOf((long) this._value == x);
	}

	public BL equal(float x) {
		return BLimpl.valueOf((float) this._value == x);
	}

	public BL equal(double x) {
		return BLimpl.valueOf((double) this._value == x);
	}

	/**
	 * QTY interface
	 */
	public BL lessOrEqual(ORD that) {
		if (that instanceof REALpinf) {
			return BLimpl.FALSE;
		} else if (that instanceof REALninf) {
			return BLimpl.TRUE;
		} else if (that instanceof REALdoubleAdapter) {
			REALdoubleAdapter xthat = (REALdoubleAdapter) that;
			return lessOrEqual(xthat.doubleValue());
		} else {
			throw new IllegalArgumentException();
		}
	}

	public BL lessOrEqual(int that) {
		return BLimpl.valueOf(this._value <= that);
	}

	public BL lessOrEqual(long that) {
		return BLimpl.valueOf((long) this._value <= that);
	}

	public BL lessOrEqual(float that) {
		return BLimpl.valueOf((float) this._value <= that);
	}

	public BL lessOrEqual(double that) {
		return BLimpl.valueOf((double) this._value <= that);
	}

	public BL compares(ORD that) {
		throw new UnsupportedOperationException(that.getClass().getName() + ":" + that.toString());
	}

	public BL compares(long that) {
		return BLimpl.TRUE;
	}

	public BL isZero() {
		// System.out.println("IZERO: " + this + ": " + (Double.longBitsToDouble(Double.doubleToLongBits(_value) &
		// 0x800fffffffffffffL)));
		// this is more difficult than we think, because we can't consider differences beyond the precision
		return BLimpl.valueOf(_value == 0);
	}

	public BL nonZero() {
		return isZero().not();
	}

	public QTY plus(QTY.diff that) {
		throw new UnsupportedOperationException(that.getClass().getName() + ":" + that.toString());
	}

	public REAL plus(REAL that) {
		return that.plus(this._value, _precision);
	}

	public REAL plus(int that, INT precision) {
		return REALdoubleAdapter.valueOf(this._value + that, fixPrecision(precision));
	}

	public REAL plus(long that, INT precision) {
		return REALdoubleAdapter.valueOf(this._value + that, fixPrecision(precision));
	}

	public REAL plus(float that, INT precision) {
		return REALdoubleAdapter.valueOf(this._value + that, fixPrecision(precision));
	}

	public REAL plus(double that, INT precision) {
		return REALdoubleAdapter.valueOf(this._value + that, fixPrecision(precision));
	}

	public REAL plus(int that) {
		return REALdoubleAdapter.valueOf(this._value + that, _precision);
	}

	public REAL plus(long that) {
		return REALdoubleAdapter.valueOf(this._value + that, _precision);
	}

	public REAL plus(float that) {
		return REALdoubleAdapter.valueOf(this._value + that, fixPrecision(DOUBLE_PRECISION));
	}

	public REAL plus(double that) {
		return REALdoubleAdapter.valueOf(this._value + that, fixPrecision(DOUBLE_PRECISION));
	}

	public REAL minus(REAL that) {
		return that.minusReverse(this._value, _precision);
	}

	public REAL minusReverse(int that, INT precision) {
		return REALdoubleAdapter.valueOf(that - this._value, fixPrecision(precision));
	}

	public REAL minusReverse(long that, INT precision) {
		return REALdoubleAdapter.valueOf(that - this._value, fixPrecision(precision));
	}

	public REAL minusReverse(float that, INT precision) {
		return REALdoubleAdapter.valueOf(that - this._value, fixPrecision(precision));
	}

	public REAL minusReverse(double that, INT precision) {
		return REALdoubleAdapter.valueOf(that - this._value, fixPrecision(precision));
	}

	public REAL minusReverse(int that) {
		return REALdoubleAdapter.valueOf(that - this._value, _precision);
	}

	public REAL minusReverse(long that) {
		return REALdoubleAdapter.valueOf(that - this._value, _precision);
	}

	public REAL minusReverse(float that) {
		return REALdoubleAdapter.valueOf(that - this._value, fixPrecision(FLOAT_PRECISION));
	}

	public REAL minusReverse(double that) {
		return REALdoubleAdapter.valueOf(that - this._value, fixPrecision(DOUBLE_PRECISION));
	}

	public QTY minus(QTY.diff that) {
		throw new UnsupportedOperationException(that.getClass().getName() + ":" + that.toString());
	}

	public QTY.diff minus(QTY that) {
		throw new UnsupportedOperationException(that.getClass().getName() + ":" + that.toString());
	}

	public REAL negated() {
		return REALdoubleAdapter.valueOf(-this._value, _precision);
	}

	public REAL inverted() {
		return REALdoubleAdapter.valueOf(1.0 / this._value, _precision);
	}

	public BL isOne() {
		return BLimpl.valueOf(this._value == 1);
	}

	public REAL times(REAL that) {
		return that.times(this._value, _precision);
	}

	public REAL times(int that, INT precision) {
		return REALdoubleAdapter.valueOf(that * this._value, fixPrecision(precision));
	}

	public REAL times(long that, INT precision) {
		return REALdoubleAdapter.valueOf(that * this._value, fixPrecision(precision));
	}

	public REAL times(float that, INT precision) {
		return REALdoubleAdapter.valueOf(that * this._value, fixPrecision(precision));
	}

	public REAL times(double that, INT precision) {
		return REALdoubleAdapter.valueOf(that * this._value, fixPrecision(precision));
	}

	public REAL times(int that) {
		return REALdoubleAdapter.valueOf(that * this._value, _precision);
	}

	public REAL times(long that) {
		return REALdoubleAdapter.valueOf(that * this._value, _precision);
	}

	public REAL times(float that) {
		return REALdoubleAdapter.valueOf(that * this._value, fixPrecision(FLOAT_PRECISION));
	}

	public REAL times(double that) {
		return REALdoubleAdapter.valueOf(that * this._value, fixPrecision(DOUBLE_PRECISION));
	}

	public REAL dividedBy(REAL that) {
		return that.dividedByReverse(this._value, _precision);
	}

	public REAL dividedByReverse(int that, INT precision) {
		return REALdoubleAdapter.valueOf(that / this._value, fixPrecision(precision));
	}

	public REAL dividedByReverse(long that, INT precision) {
		return REALdoubleAdapter.valueOf(that / this._value, fixPrecision(precision));
	}

	public REAL dividedByReverse(float that, INT precision) {
		return REALdoubleAdapter.valueOf(that / this._value, fixPrecision(precision));
	}

	public REAL dividedByReverse(double that, INT precision) {
		return REALdoubleAdapter.valueOf(that / this._value, fixPrecision(precision));
	}

	public REAL dividedByReverse(int that) {
		return REALdoubleAdapter.valueOf(that / this._value, _precision);
	}

	public REAL dividedByReverse(long that) {
		return REALdoubleAdapter.valueOf(that / this._value, _precision);
	}

	public REAL dividedByReverse(float that) {
		return REALdoubleAdapter.valueOf(that / this._value, fixPrecision(FLOAT_PRECISION));
	}

	public REAL dividedByReverse(double that) {
		return REALdoubleAdapter.valueOf(that / this._value, fixPrecision(DOUBLE_PRECISION));
	}

	public REAL power(REAL that) {
		return that.powerReverse(this._value, _precision);
	}

	public REAL powerReverse(int that, INT precision) {
		return REALdoubleAdapter.valueOf(Math.pow(that, (double) this._value), fixPrecision(precision));
	}

	public REAL powerReverse(long that, INT precision) {
		return REALdoubleAdapter.valueOf(Math.pow(that, (double) this._value), fixPrecision(precision));
	}

	public REAL powerReverse(float that, INT precision) {
		return REALdoubleAdapter.valueOf(Math.pow(that, (double) this._value), fixPrecision(precision));
	}

	public REAL powerReverse(double that, INT precision) {
		return REALdoubleAdapter.valueOf(Math.pow(that, this._value), fixPrecision(precision));
	}

	public REAL powerReverse(int that) {
		return REALdoubleAdapter.valueOf(Math.pow(that, (double) this._value), _precision);
	}

	public REAL powerReverse(long that) {
		return REALdoubleAdapter.valueOf(Math.pow(that, (double) this._value), _precision);
	}

	public REAL powerReverse(float that) {
		return REALdoubleAdapter.valueOf(Math.pow(that, (double) this._value), fixPrecision(FLOAT_PRECISION));
	}

	public REAL powerReverse(double that) {
		return REALdoubleAdapter.valueOf(Math.pow(that, this._value), fixPrecision(DOUBLE_PRECISION));
	}

	/**
	 * Natural logarithm.
	 */
	public REAL log() {
		return REALdoubleAdapter.valueOf(Math.log(this._value), _precision);
	}

	/**
	 * Power of e. FIXME: needs to check for overflow.
	 */
	public REAL exp() {
		return REALdoubleAdapter.valueOf(Math.exp(this._value), _precision);
	}

	public REAL floor() {
		return REALdoubleAdapter.valueOf(Math.floor(this._value), _precision);
	}

	private static final Pattern DECIMAL_VALUE_OUTPUT_PATTERN = 
		Pattern.compile("(?i)([+-]?\\d++)\\.?+(\\d*+(?:e[+-]?\\d+)?)");

	private static final int PRE_POINT_DIGITS_GROUP = 1;

	private static final int POST_POINT_DIGITS_GROUP = 2;

	// FIXME: precision output may still not match what the input oringally was. ex- (40e3 ---> 4e4) need to fix this..
	public String toString() {
		if (_precision.isNull().isFalse()) {
			String result = "";

			try {
				result = String.format("%." + _precision + "g", _value);
			} catch (ArrayIndexOutOfBoundsException e) {
				return "0"; // FIXME: is this correct???
			} // This is a Formatter bug workaround

			RegexTokenizer t = new RegexTokenizer(DECIMAL_VALUE_OUTPUT_PATTERN, result);

			if (t.seek()) {
				if (t.group(POST_POINT_DIGITS_GROUP).equals("")) {
					return t.group(PRE_POINT_DIGITS_GROUP) + "."; // Add the point at the end to signify that we have precision
																												// here
				} else {
					return t.group(PRE_POINT_DIGITS_GROUP) + "." + t.group(POST_POINT_DIGITS_GROUP);
				}
			} else {
				return result; // Shouldn't get here but just in case
			}

		} else { // Must return a value with no decimal point. That is our standard as of Nov '05 for INF precision
			if (_value == 0) {
				return "0";
			}
			StringBuffer sb = new StringBuffer();
			String s = NOPOINT_FORMAT.format(_value); // Standardizes number for parsing
			int len = s.length();
			int exp = 0;
			int sig = 1;
			int i = 1;
			int nfigs = 0;
			char c;
			while ((c = s.charAt(i++)) != 'E') {
				sb.append(c);
				nfigs++;
			}
			c = s.charAt(i);
			if (c == '-') {
				sig = -1;
				i++;
			}
			while (i < len) {
				c = s.charAt(i++);
				exp = exp * 10 + (c - '0');
			}
			exp = sig * exp;
			exp = exp - nfigs;
			while (exp > 0) {
				sb.append('0');
				--exp;
				if (exp == 0) {
					return sb.toString();
				}
			}
			if (exp != 0) {
				sb.append("E");
				sb.append(exp);
			}
			return sb.toString();
		}
	}

	// FIXME: overflow!
	public int intValue() {
		return (int) Math.round(this._value);
	}

	public long longValue() {
		return (long) Math.round(this._value);
	}

	public float floatValue() {
		return (float) this._value;
	}

	public double doubleValue() {
		return this._value;
	}

	private static final Pattern SIGFIGS_PATTERN = Pattern
			.compile("(?i)[0]*([0])\\.([0]*+)(?:e[+-]?\\d+)?$|[0]*(\\d*+)\\.(\\d*+)(?:e[+-]?\\d+)?$");

	private static final int LEADING_ZERO_GROUP = 1;

	private static final int TRAILING_ZEROS_GROUP = 2;

	private static final int LEADING_DIGITS_GROUP = 3;

	private static final int TRAILING_DIGITS_GROUP = 4;

	private static INT countSigFigs(String val) {
		RegexTokenizer t = new RegexTokenizer(SIGFIGS_PATTERN, val);
		if (t.seek()) {
			if (t.group(LEADING_ZERO_GROUP) != null) {
				return ValueFactory.getInstance().INTvalueOf(
						t.group(LEADING_ZERO_GROUP).length() + t.group(TRAILING_ZEROS_GROUP).length());
			} else if (t.group(LEADING_DIGITS_GROUP) != null) {
				return ValueFactory.getInstance().INTvalueOf(
						t.group(LEADING_DIGITS_GROUP).length() + t.group(TRAILING_DIGITS_GROUP).length());
			}
		}
		return INTnull.PINF;
	}

	/*
	 * new version, untested, incomplete yet
	 * 
	 * private static final Pattern SIGFIGS_PATTERN =
	 * Pattern.compile("(?i)(0+)?([1-9][0-9]*)?(?:\\.(([0-9]*[1-9])?(0*)?))?(?:e[+-]?[0-9]+)?$"); private static final int
	 * LEADING_ZERO_GROUP = 1; private static final int LEADING_DIGITS_GROUP = 2; private static final int
	 * FRACTIONAL_GROUP = 3; private static final int TRAILING_DIGITS_GROUP = 4; private static final int
	 * TRAILING_ZEROS_GROUP = 5;
	 * 
	 * private static INT countSigFigs(String val) { RegexTokenizer t = new RegexTokenizer(SIGFIGS_PATTERN, val); if
	 * (t.seek()) { String leadingZeros = t.group(LEADING_ZEROS_GROUP); String leadingDigits =
	 * t.group(LEADING_DIGITS_GROUP); String fractionalPart = t.group(FRACTIONAL_GROUP); String trailingDigits =
	 * t.group(TRAILING_DIGITS_GROUP); String trailingZeros = t.group(TRAILING_ZEROS_GROUP); int precision;
	 * 
	 * if(fractionalPart != null) { if(leadingZeros != null && leadingDigits == null && trailingDigits == null) precision =
	 * 1 + (trailingZeros != null ? trailingZeros.length() : 0); else if(leadingDigits != null) precision =
	 * leadingDigits.length() + fractionalPart.length();
	 *  } else { if(infiniteInteger) return INTnull.PINF; else { if(leadingZeros != null && leadingDigits == null)
	 * precision = 1; else if(leadingDigits != null) precision = (leadingDigits != null ? trailingZeros.length()
	 * leadingDigits
	 * 
	 * return ValueFactory.getInstance().INTvalueOf(precision)
	 *  } else throw new NumberFormatException(val); }
	 */

	private INT fixPrecision(INT thatPrecision) {
		if (!_precision.lessOrEqual(thatPrecision).isFalse()) {
			return _precision;
		} else {
			return thatPrecision;
		}
	}

	public diff epsilon() {
		return ONE;
	}

	public int hashCode() {
		int result;
		long temp;
		temp = _value != +0.0d ? Double.doubleToLongBits(_value) : 0L;
		result = (int) (temp ^ (temp >>> 32));
		result = 29 * result + (_precision != null ? _precision.hashCode() : 0);
		return result;
	}
}

/**
 * HISTORY : $Log: not supported by cvs2svn $
 * HISTORY : Revision 1.53  2006/08/03 20:43:09  gschadow
 * HISTORY : *** empty log message ***
 * HISTORY :
 * HISTORY : Revision 1.52  2006/07/28 22:11:21  gschadow
 * HISTORY : *** empty log message ***
 * HISTORY : HISTORY : Revision 1.51 2006/07/17 19:23:19 gschadow HISTORY : import
 * Cleanup HISTORY : HISTORY : Revision 1.50 2006/05/23 20:32:24 sjiang HISTORY : Formatted the code structure. HISTORY :
 * HISTORY : Revision 1.49 2006/04/28 22:46:02 gschadow HISTORY : new epsilon() HISTORY : HISTORY : Revision 1.48
 * 2006/04/26 23:04:18 gschadow HISTORY : taking another stab at the problem comparing two REALs with properly HISTORY :
 * considering their precision so as to avoid false negatives. For two HISTORY : REALdoubleAdapters we can now remove
 * precision from the mantissa HISTORY : doing direct manipulation of the IEEE floating point bits. This works HISTORY :
 * kind of. Not sure if we will have false positives now. HISTORY : HISTORY : Revision 1.47 2006/04/26 04:02:48 gschadow
 * HISTORY : finally implemented special units, such as degree Celsius and Fahrenheit. HISTORY : This is not all
 * complete, for example, kilo-degree Celsius are not HISTORY : handled properly, even though SI allows it. Also, there
 * is some big HISTORY : problem with REAL arithmetic as the double adapter sucks in crap at the HISTORY : end of the
 * mantissa and that causes equals to fail. HISTORY : HISTORY : Revision 1.46 2006/04/24 22:10:48 gschadow HISTORY : ***
 * empty log message *** HISTORY : HISTORY : Revision 1.45 2006/04/23 03:18:10 gschadow HISTORY : fixed a problem in
 * REAL where we were limiting the precision to HISTORY : DOUBLE_PRECISION even though we consider the input precise.
 * HISTORY : HISTORY : Revision 1.44 2006/04/21 04:52:29 gschadow HISTORY : various minor fixes (actually I'm not sure
 * if Chris earlier check in of HISTORY : PQ, REAL amd EN isn't better) HISTORY : SelectionIterator: improved factoring
 * to have optional transformer and HISTORY : make everything generic type safe again. HISTORY : HISTORY : Revision 1.43
 * 2006/04/20 13:11:57 gschadow HISTORY : *** empty log message *** HISTORY : HISTORY : Revision 1.42 2006/04/19
 * 20:43:49 gschadow HISTORY : CR: REAL's that have precision always have a decimal point, even if there is no value
 * after the decimal point. (ex. 180 with precision 3 --> 180.) HISTORY : HISTORY : Revision 1.41 2006/01/24 15:09:01
 * gschadow HISTORY : *** empty log message *** HISTORY : HISTORY : Revision 1.40 2005/12/05 20:41:05 gschadow HISTORY :
 * *** empty log message *** HISTORY : HISTORY : Revision 1.39 2005/11/23 22:19:08 gschadow HISTORY : small but
 * effective bug fix on INT.lessOrEqual(PINF) HISTORY : in the message element content handler I am adding handling of
 * an HISTORY : extension attribute j:class, which allows the inbound message to HISTORY : set the implementation class
 * for the RIM class to create. Like HISTORY : all my advanced features, this is protected and has to be turned HISTORY :
 * on explicitly to use, otherwise that attribute is ignored. HISTORY : HISTORY : Revision 1.38 2005/11/22 19:45:15
 * gschadow HISTORY : New toString() algorithm for numbers without precision. HISTORY : HISTORY : Revision 1.37
 * 2005/11/18 20:27:36 gschadow HISTORY : Now it should be good.. HISTORY : HISTORY : Revision 1.36 2005/11/18 20:10:06
 * gschadow HISTORY : I think I've fixed this... HISTORY : HISTORY : Revision 1.35 2005/11/18 19:20:32 gschadow HISTORY :
 * *** empty log message *** HISTORY : HISTORY : Revision 1.34 2005/11/14 20:36:54 gschadow HISTORY : *** empty log
 * message *** HISTORY : HISTORY : Revision 1.33 2005/11/14 16:26:50 gschadow HISTORY : *** empty log message ***
 * HISTORY : HISTORY : Revision 1.32 2005/11/01 22:59:36 gschadow HISTORY : a minor bug was fixed, had to do with some
 * return types or use of QTYnull HISTORY : or something like that. May be an NPE. Can't remember HISTORY : HISTORY :
 * Revision 1.31 2005/11/01 19:32:09 echen HISTORY : Add the history comment HISTORY :
 */

