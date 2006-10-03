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

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.INT;
import org.hl7.types.ORD;
import org.hl7.types.PQ;
import org.hl7.types.QTY;
import org.hl7.types.TS;
import org.hl7.types.ValueFactory;
import org.regenstrief.ucum.Unit;
import org.regenstrief.ucum.UnitSystem;

/**
 * An implementation of TS that is based on java.util.Date. Note,
 * we are not encapsulating a Date object here since that is not
 * an immutable value object. So, this is not really a Date adapter
 * but rather an implementation based on the same conventions as
 * Date.
 *
 * To current implementation as of 05-22-2006, the expected input and output (via toString()) string
 * representation of the date value is in format of "yyyyMMddHHmmss.SSS".
 *
 * For example, "20020830185026.789" means "2002, August, 30th, 18:50:26, and milli-sec: 789".
 *
 * This class will neither preserve the string, Date, nor long values that were
 * passed in by calling valueOf() methods when an instance of this object was created.
 *
 * When the toString() method is called, the output will be expected to comply with the format aforementioned,
 * even though with several appended default values. 
 */
public final class TSjuDateAdapter extends ORDimpl implements TS {
	long _time; // according to java.util.Date, this is ms since epoch 1-1-1970
	int _precision = 0; // 0 means unknown precision

	public static final PQ ZERO_SECONDS = PQnull.NI; // PQimpl.valueOf(0,"s");

	// FIXME: where do I get the calendar OID from? I will add a bunch
	// basic UID constants later.
	public static final CS CAL_GREGORIAN = CSimpl.valueOf("GREG", "UID-Calendar");

	protected static final Logger LOGGER = Logger.getLogger("org.hl7.types");

	private TSjuDateAdapter(long time) {
		super(null);
		this._time = time;
	}

	private TSjuDateAdapter(long time, int precision)
	{
		super(null);
		this._time = time;
		this._precision = precision;
	}

	/**
	 * Get a TS object from a java.util.Date object.
	 */
	public static TS valueOf(Date date, int precision)
	{
		if (date == null)
		{
			return TSnull.NI;
		}
		else
		{
			return new TSjuDateAdapter(date.getTime(), precision);
		}
	}

	public static TS valueOf(Date date, INT precision)
	{
		return valueOf(date, intValueOfINT(precision));
	}

	/**
	 * Get a TS object from a java.util.Date object.
	 */
	/* package */
	static TS valueOf(Date date)
	{
		return valueOf(date.getTime(), 0);
	}

	/**
	 * Get a TS object from a time value that is the offset from
	 * java.util.Date epoch 1-1-1970 00:00:00.
	 */
	public static TS valueOf(long time)
	{
		return valueOf(time, 0);
	}

	// XXX: there are too many of those variances
	public static TS valueOf(long time, int precision)
	{
		return new TSjuDateAdapter(time, precision);
	}

	// XXX: there are too many of those variances
	public static TS valueOf(long time, INT precision)
	{
		return new TSjuDateAdapter(time, intValueOfINT(precision));
	}

	private static final int intValueOfINT(INT precision)
	{
		int p;
		if (precision.isNull().isTrue())
		{
			if (precision == INTnull.PINF) // FIXME: not correct to require the flyweight
			{
				return Integer.MAX_VALUE;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return precision.intValue();
		}
	}

	public static final String DATE_FORMAT_STRING = "yyyyMMddHHmmss.SSS";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING);

	private static Date parseDate(String s)
	{
		// TODO: use regex with the capture groups instead
		try
		{
			if (s.length() < 18)
			{
				StringBuffer sb = new StringBuffer(s);
				while (sb.length() < 14)
				{
					sb.append('0');
				}
				if (sb.length() < 15)
				{
					sb.append(".000");
				}
				while (sb.length() < 18)
				{
					sb.append('0');
				}
				return DATE_FORMAT.parse(sb.toString());
			}
			else
			{
				return DATE_FORMAT.parse(s.substring(0, 18));
			}
		}
		catch (java.text.ParseException ex)
		{
			LOGGER.log(Level.WARNING, "not a valid date: " + s, ex);
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Get a TS object from a time value expressed as a string that is
	 * the offset from java.util.Date epoch 1-1-1970 00:00:00.
	 * Supported format is: "yyyyMMddHHmmss.SSS".
	 */
	public static TS valueOf(String time)
	{
		if (time != null)
		{
			time = time.trim();
		}
		if (time == null || time.equals(""))
		{
			return TSnull.NA;
		}
		return new TSjuDateAdapter(parseDate(time).getTime(), time.length());
	}

	public String toString()
	{
		// FIXME: format should be thread-local
		// XXX: this is ridiculous
		String result = (new SimpleDateFormat(DATE_FORMAT_STRING))
				.format(new Date(_time), new StringBuffer(), new FieldPosition(SimpleDateFormat.YEAR_FIELD)).toString();
		if (_precision > 0)
		{
			return result.substring(0, _precision <= result.length() ? _precision : result.length());
		}
		else
		{
			return result;
		}
	}

	public Date toDate()
	{
		return new Date(_time);
	}

	/**
	 * Test for equality between two TS values. The trick is, to find
	 * a common denomination in which we can compare. In order to stay
	 * totally independent from any specific representation, we can
	 * simply subtract the two dates and test if we get zero in return.
	 * <p/>
	 * We could actually do this for all QTY types.
	 */
	public BL equal(ANY that)
	{
		if (that instanceof TS)
		{
			TS thatTS = (TS) that;
			return this.minus(thatTS).isZero();
		}
		else
		{
			return BLimpl.FALSE;
		}
	}

	public BL lessOrEqual(ORD that)
	{
		if (that instanceof TS)
		{
			TS thatTS = (TS) that;
			return this.minus(thatTS).lessOrEqual(PQimpl.valueOf("0", "ms"));
			//return this.minus(thatTS).lessOrEqual(ZERO_SECONDS);
		}
		else
		{
			return BLimpl.FALSE;
		}
	}

	public BL greaterOrEqual(ORD that)
	{
		if (that instanceof TS)
		{
			TS thatTS = (TS) that;
			if (this.equal(thatTS).isTrue())
			{
				return BLimpl.TRUE;
			}
			else if (this.lessOrEqual(thatTS).isTrue())
			{
				return BLimpl.FALSE;
			}
			else
			{
				return BLimpl.TRUE;
			}
		}
		else
		{
			return BLimpl.FALSE;
		}
	}

	public BL compares(ORD that)
	{
		return BLimpl.valueOf(that instanceof TS);
	}

	public QTY plus(QTY.diff that)
	{
		if (that instanceof PQ)
		{
			return this.plus((PQ) that);
		}
		else
		{
			if (that.isNullJ())
			{
				return QTYnull.NI;
			}
			throw new IllegalArgumentException();
		}
	}

	public static final PQ ONE_MS = PQimpl.valueOf(REALdoubleAdapter.ONE, "ms");

	public TS plus(PQ that)
	{
		if (that.nonZero().isTrue())
		{
			return TSjuDateAdapter.valueOf(this.offset().plus(that).expressedIn(ONE_MS).value().longValue(), _precision);
		}
		else if (that.isZero().isTrue())
		{
			return this;
		}
		else if (that.isNull().isTrue())
		{
			return TSnull.NI;
		}
		else
		{
			throw new Error("what other case is there?");
		}

	}

	public PQ minus(QTY that)
	{
		if (that.isNullJ())
		{
			return PQnull.NI;
		}
		if (that instanceof TS)
		{
			return minus((TS) that);
		}
		throw new UnsupportedOperationException();
	}

	public TS minus(QTY.diff that)
	{
		QTY.diff thatQ = (QTY.diff) that;
		if (thatQ.isNull().isTrue())
		{
			return TSnull.NI;
		}
		else if (thatQ.isZero().isTrue())
		{
			return this;
		}
		else if (thatQ instanceof PQ)
		{
			PQ thatPQ = (PQ) that;
			return TSjuDateAdapter.valueOf(this.offset().minus(thatPQ).expressedIn(ONE_MS).value().longValue(), _precision);
		}
		else
		{
			throw new UnsupportedOperationException("" + this.toString() + ".minus(" + that.toString() + ")");
		}
	}

	private static final PQ[] DIGIT_VALUES = {PQimpl.valueOf("1e3", "a"), PQimpl.valueOf("1e2", "a"), PQimpl.valueOf("1e1", "a"), PQimpl.valueOf("1", "a"), PQimpl.valueOf("10", "mo"), PQimpl.valueOf("1", "mo"), PQimpl.valueOf("10", "d"), PQimpl.valueOf("1", "d"), PQimpl.valueOf("10", "h"), PQimpl.valueOf("1", "h"), PQimpl.valueOf("10", "min"), PQimpl.valueOf("1", "min"), PQimpl.valueOf("10", "s"), PQimpl.valueOf("1", "s")};

	public PQ minus(TS that)
	{
		PQ diff = this.offset().minus(that.offset());
		if (diff.isNull().isTrue())
		{
			return diff;
		}

		int thatPrecision = that.precision().nonNull().isTrue() ? that.precision().intValue() : Integer.MAX_VALUE;
		int precision = this._precision <= thatPrecision ? this._precision : thatPrecision;
		PQ unit = _precision < 15 && _precision > 0 ? DIGIT_VALUES[precision - 1] : MILLISECOND;
		return PQimpl.valueOf(Long.toString(diff.expressedIn(unit).value().longValue()) + ".", (Unit) unit.unit());
	}

	/* package */ final static PQ MILLISECOND = PQimpl.valueOf("1", "ms");
	private final static UnitSystem UNIT_SYSTEM = UnitSystem.getInstance();
	private final static Unit MILLISECOND_UNIT = Unit.valueOf("ms");

	public PQ offset()
	{
		return PQimpl.valueOf(ValueFactory.getInstance().REALvalueOf((double) _time), MILLISECOND_UNIT);
	}

	public CS calendar()
	{
		return CAL_GREGORIAN;
	}

	public INT precision()
	{
		if (_precision <= 0)
		{
			return INTnull.NA;
		}
		else
		{
			return INTlongAdapter.valueOf(_precision);
		}
	}

	public PQ timezone()
	{
		throw new UnsupportedOperationException();
	}

	public int hashCode()	{
		return (int) _time;
	}

	// fix me
	public long getInternalTime()
	{
		return 0;  //To change body of created methods use File | Settings | File Templates.
	}

	public PQ epsilon()
	{
		return MILLISECOND;
	}
}
