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
 * The Initial Developer of the Original Code is Gunther Schadow.
 * Portions created by Initial Developer are Copyright (C) 2002-2004 
 * Health Level Seven, Inc. All Rights Reserved.
 *
 * Contributor(s): Matthew Carlson
 */

package org.hl7.types.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TimeZone;

import org.hl7.meta.Datatype;
import org.hl7.types.ANY;
import org.hl7.types.BL;
import org.hl7.types.CS;
import org.hl7.types.Criterion;
import org.hl7.types.DSET;
import org.hl7.types.INT;
import org.hl7.types.IVL;
import org.hl7.types.PIVL;
import org.hl7.types.PQ;
import org.hl7.types.QSET;
import org.hl7.types.QTY;
import org.hl7.types.REAL;
import org.hl7.types.SET;
import org.hl7.types.TS;
import org.hl7.types.ValueFactory;
import org.hl7.types.QTY.diff;
import org.hl7.types.enums.CalendarCycle;

/**
 * @author Geoffry Roberts, Gunther Schadow, Matt Carlson
 */
public class PIVLimpl<T extends TS> extends ANYimpl implements PIVL<T> {

	IVL<T> _phase;
	QTY.diff _period;
	CS _alignment;
	BL _institutionSpecified;
	
	public String toString() {
		return _phase + "/(" + _period + ")"
		+ (_alignment.nonNullJ() ? "@" + _alignment : "") 
		+ (_institutionSpecified.isTrue() ? " IST" : "");
	}
	
	private PIVLimpl(IVL<T> phase, QTY.diff period, 
			CS alignment,
			BL institutionSpecified) {
		super(null);
		if(phase.isNull().isFalse() && period.isNull().isFalse() && phase.width().lessOrEqual(period).isFalse())
			throw new IllegalArgumentException("Phase (" + phase + ") cannot be greater than the period (" + period + ")");
		if (alignment == null)
			alignment = CSnull.NA;
		_phase = phase;
		_period = period;
		_alignment = alignment;
		_institutionSpecified = institutionSpecified;
	}
	
	public static PIVL valueOf(QTY low, QTY high, QTY.diff period, 
			CS alignment,
			BL institutionSpecified) {
		return new PIVLimpl(IVLimpl.valueOf(BLimpl.TRUE, low, high, BLimpl.TRUE),
				period, alignment, institutionSpecified);
	}
	
	public static PIVL valueOf(IVL ivl, QTY.diff period, 
			CS alignment,
			BL institutionSpecified){
		return new PIVLimpl(ivl, period, alignment, institutionSpecified);
	}
	
	public static PIVL valueOf(String literal, Datatype parameterType) {
		//XXX: PIVL does not yet deal with institutionSpecified in any of its
		// methods.  Until this changes, we assume institutionSpecified is false
		BL institutionSpecified=BLimpl.FALSE;
		CS alignment=null;
		
		// remove beginning and ending whitespace
		literal=literal.trim();
		
		int semiColonIndex=literal.indexOf("/");
		if (semiColonIndex<0) throw new Error("invalid PIVL literal: "+literal);
		// find the phase
		String phaseStr=literal.substring(0,semiColonIndex);
		IVL phase=IVLimpl.valueOf(phaseStr, parameterType);
		int leftParenIndex=literal.indexOf("(");
		int rightParenIndex=literal.indexOf(")");
		if ((leftParenIndex<0) || (rightParenIndex<0)) 
			throw new Error("invalid PIVL literal: "+literal);
		String periodStr=literal.substring(leftParenIndex+1, rightParenIndex);
		PQ period=ValueFactory.getInstance().PQvalueOfLiteral(periodStr);
		
		// check for alignment
		int alignmentIndex=literal.indexOf("@");
		if (!(alignmentIndex<0)) {
			String alignmentStr=literal.substring(alignmentIndex+1,literal.length());
			alignment=CalendarCycle.valueOf(STjlStringAdapter.valueOf(alignmentStr));
		} else 
			alignment=CSnull.NI;
		
		return new PIVLimpl(phase, period, alignment, institutionSpecified);
	}
	
	public BL equal(ANY that) {
		if(that instanceof PIVL) {
			if (that.isNull().isTrue())
				return BLimpl.NI;
			
			PIVL thatPIVL = (PIVL)that;			

			if(institutionSpecified().equal(thatPIVL.institutionSpecified())
				 .and(alignment().isNull().and(thatPIVL.alignment().isNull()).or(alignment().equal(thatPIVL.alignment()))).isTrue()) {
				
				BL e1 = period().equal(thatPIVL.period());
				if (e1.isFalse())
					return BLimpl.FALSE;
				else {
					BL e1_5 = phase().nonNull().and(thatPIVL.phase().nonNull());
					if (e1_5.isTrue()) {		
						BL e2 = phase().width().equal(thatPIVL.phase().width());
						if (e2.isFalse())
							return e2;
						else {
							BL e3 = e1.and(e2);
							if (e3.isTrue()) {
								return this.contains(thatPIVL.phase()).and(thatPIVL.contains(phase()));
							} else
								return e3;
						}
					} else
						return BLimpl.NI;
				}
			} else
				return BLimpl.FALSE;
		} else
			return BLimpl.FALSE;
	}
	
	public IVL<T> phase(){
		return (IVL<T>)_phase;
	}
	
	public QTY.diff period(){
		return _period;
	}
	
	public CS alignment(){
		return _alignment;
	}
	
	public BL institutionSpecified(){
		return _institutionSpecified;
	}
	
	public INT cardinality() {
		throw new UnsupportedOperationException();
	}

	public BL contains(T element) {
		if(element instanceof TS) {
			// for now we can assume this is always the case...

			PQ period = (PQ)_period;
			if (_alignment.isNull().isTrue()) {
				if (_phase.low().lessOrEqual(element).isTrue()) {
					
					PQ pqdiff = (PQ) (element.minus(_phase.low()));
					
					return element.minus(period.times(pqdiff.dividedBy(period).toREAL().floor()))
					.lessOrEqual(_phase.high());
				}		
				
				else { // element is less than phase.low()
					
					PQ pqdiff = (PQ) (_phase.high().minus(element));

					return element.plus(period.times(pqdiff.dividedBy(period).toREAL().floor()))
					.greaterOrEqual(_phase.low());
				}
			}	else { 	  
				/*
				 * Since there is an alignment, for now just leverage Java's
				 * GregorianCalendar class.  The idea is similar to no alignment,
				 * but let Java do all the work.
				 */

				// set up the calendar set to time element
				Date elementDate=element.toDate();
				// set up the calendar set to time _phase.low()
				Date lowDate=_phase.low().toDate();

				GregorianCalendar elementCal=new GregorianCalendar(TimeZone.getDefault());
				elementCal.setLenient(true);	  
				elementCal.setTime(elementDate);
				
				GregorianCalendar lowCal=new GregorianCalendar(TimeZone.getDefault());
				lowCal.setLenient(true);
				lowCal.setTime(lowDate);
				
				// this sets lowCal to the low of the interval of this that
				// is <= element
				lowCal=findMostRecent(lowCal,period,elementCal,false);

				IVL<TS> mostRecent = IVLimpl.valueOf(_phase.lowClosed(), 
																						 TSjuDateAdapter.valueOf(lowCal.getTime(), _phase.low().precision()), 
																						 TSjuDateAdapter.valueOf(lowCal.getTime(), _phase.high().precision()).plus((PQ)_phase.width()), 
																						 _phase.highClosed()); 
				
				return mostRecent.contains(element);
			}
		}
		else {
			throw new UnsupportedOperationException("can only support for PIVL<TS>, element class was: " + element.getClass());
		}	
	}
	
	/*
	 * elementCal is a calendar set to the date of element, and lowCal is
	 * a calendar set to the date of _phase.low().  Depending on the period,
	 * these methods will get the *aligned* occurence of this less than or
	 * containing element.  
	 */
	
	private void catchUpDays(GregorianCalendar lowCal, PQ period, GregorianCalendar elementCal) {
		int totalDays=0;
		int diff=0;
		for (int i=lowCal.get(Calendar.YEAR);i<elementCal.get(Calendar.YEAR);i++) {
			if(lowCal.isLeapYear(i)) totalDays+=366;
			else totalDays+=365;
		}
		lowCal.set(Calendar.DAY_OF_YEAR,
				lowCal.get(Calendar.DAY_OF_YEAR)+period.value().intValue() * (int)Math.floor(totalDays/(period.value().doubleValue())));
		
		if (lowCal.get(Calendar.YEAR) != elementCal.get(Calendar.YEAR))
			diff=lowCal.isLeapYear(lowCal.get(Calendar.YEAR)) ? 366 : 365
					-(elementCal.get(Calendar.DAY_OF_YEAR)-
							lowCal.get(Calendar.DAY_OF_YEAR));
		else
			diff=elementCal.get(Calendar.DAY_OF_YEAR)-
			lowCal.get(Calendar.DAY_OF_YEAR);
		
		// add on remaining days
		lowCal.set(Calendar.DAY_OF_YEAR,
				lowCal.get(Calendar.DAY_OF_YEAR) + period.value().intValue() * (int)Math.floor((elementCal.get(Calendar.DAY_OF_YEAR)-lowCal.get(Calendar.DAY_OF_YEAR))/(period.value().doubleValue())));
	}
	
	private void catchUpWeeks(GregorianCalendar lowCal, PQ period, GregorianCalendar elementCal) {
		int diff=0;
		lowCal.set(Calendar.WEEK_OF_YEAR,
				lowCal.get(Calendar.WEEK_OF_YEAR)+period.value().intValue() * (int)Math.floor(((elementCal.get(Calendar.YEAR)-lowCal.get(Calendar.YEAR))*52)/(period.value().doubleValue())));
		
		if (lowCal.get(Calendar.YEAR) != elementCal.get(Calendar.YEAR))
			diff=52-(elementCal.get(Calendar.WEEK_OF_YEAR)-
					lowCal.get(Calendar.WEEK_OF_YEAR));
		else
			diff=elementCal.get(Calendar.WEEK_OF_YEAR)-
			lowCal.get(Calendar.WEEK_OF_YEAR);
		
		// add on remaining weeks
		lowCal.set(Calendar.WEEK_OF_YEAR,
				lowCal.get(Calendar.WEEK_OF_YEAR) + period.value().intValue() * (int)Math.floor(diff/(period.value().doubleValue())));
		
	}
	
	private void catchUpHours(GregorianCalendar lowCal, PQ period, GregorianCalendar elementCal) {
		int totalDays=0;
		int diff=0;
		for (int i=lowCal.get(Calendar.YEAR);i<elementCal.get(Calendar.YEAR);i++) {
			if(lowCal.isLeapYear(i)) totalDays+=366;
			else totalDays+=365;
		}
		lowCal.set(Calendar.HOUR_OF_DAY,
				lowCal.get(Calendar.HOUR_OF_DAY)+period.value().intValue() * (int)Math.floor((totalDays*24)/(period.value().doubleValue())));
		
		if (lowCal.get(Calendar.YEAR) != elementCal.get(Calendar.YEAR))
			diff=24*(lowCal.isLeapYear(lowCal.get(Calendar.YEAR)) ? 366 : 365
					-(elementCal.get(Calendar.DAY_OF_YEAR)-
							lowCal.get(Calendar.DAY_OF_YEAR)));
		else
			diff=24*(elementCal.get(Calendar.DAY_OF_YEAR)-
					lowCal.get(Calendar.DAY_OF_YEAR));
		
		// add on remaining hours
		lowCal.set(Calendar.HOUR_OF_DAY,
				lowCal.get(Calendar.HOUR_OF_DAY) + period.value().intValue() * (int)Math.floor(diff/(period.value().doubleValue())));
		
	}
	
	private void catchUpYears(GregorianCalendar lowCal, PQ period, GregorianCalendar elementCal) {
		lowCal.set(Calendar.YEAR,
				lowCal.get(Calendar.YEAR)+period.value().intValue() * ((int)Math.floor((elementCal.get(Calendar.YEAR)-lowCal.get(Calendar.YEAR))/(period.value().doubleValue()))));
	}
	
	private void catchUpMonths(GregorianCalendar lowCal, PQ period, GregorianCalendar elementCal) {
		int diff=0;
		lowCal.set(Calendar.MONTH,
				lowCal.get(Calendar.MONTH)+period.value().intValue() * (int)Math.floor(((elementCal.get(Calendar.YEAR)-lowCal.get(Calendar.YEAR))*12)/(period.value().doubleValue())));
		
		if (lowCal.get(Calendar.YEAR) != elementCal.get(Calendar.YEAR))
			diff=12-(elementCal.get(Calendar.MONTH)-
					lowCal.get(Calendar.MONTH));
		else
			diff=elementCal.get(Calendar.MONTH)-
			lowCal.get(Calendar.MONTH);
		
		// add on remaining months
		lowCal.set(Calendar.MONTH,
				lowCal.get(Calendar.MONTH) + period.value().intValue() * (int)Math.floor(diff/(period.value().doubleValue())));
	}
	
	/*
	 * This method does most of the legwork for contains, nextTo, and
	 * nextAfter when alignment is set.  If getOneMore is specified,
	 * this will set lowCal to the first occurence of this > element.
	 * Otherwise, lowCal is set to the nearest occurence of
	 * this <= element
	 */
	private GregorianCalendar findMostRecent(GregorianCalendar lowCal, PQ period, GregorianCalendar elementCal, boolean getOneMore) {
		if (period.unit().toString().equals("a")) { // FIXME: these comparisons by unit string are not right
			// assuming element > low
			catchUpYears(lowCal, period, elementCal);
			if (getOneMore) lowCal.set(Calendar.YEAR,
					lowCal.get(Calendar.YEAR)+
					period.value().intValue());
		}
		else if (period.unit().toString().equals("d")) {
			//assuming element > low
			catchUpDays(lowCal, period, elementCal);
			if (getOneMore) lowCal.set(Calendar.DAY_OF_YEAR,
					lowCal.get(Calendar.DAY_OF_YEAR)+
					period.value().intValue());
		}
		else if (period.unit().toString().equals("mo")) {
			// assuming element > low
			catchUpMonths(lowCal, period, elementCal);
			if (getOneMore) lowCal.set(Calendar.MONTH,
					lowCal.get(Calendar.MONTH)+
					period.value().intValue());
		}
		else if (period.unit().toString().equals("wk")) {
			// assuming element > low
			catchUpWeeks(lowCal, period, elementCal);
			if (getOneMore) lowCal.set(Calendar.WEEK_OF_YEAR,
					lowCal.get(Calendar.WEEK_OF_YEAR)+
					period.value().intValue());
		}
		else if (period.unit().toString().equals("h")) {
			// assuming element > low
			catchUpHours(lowCal, period, elementCal);
			if (getOneMore) lowCal.set(Calendar.HOUR_OF_DAY,
					lowCal.get(Calendar.HOUR_OF_DAY)+
					period.value().intValue());
		}
		return lowCal;
	}

	
	public BL contains(SET<T> subset) {
		if (subset instanceof IVL) {
			PQ period = (PQ)_period;
			IVL<T> subsetInterval = (IVL<T>)subset;

			return this.nextTo(subsetInterval.low()).contains(subsetInterval)
				.and(this.nextTo(subsetInterval.high()).contains(subsetInterval))
				.and(subsetInterval.width().lessOrEqual(_phase.width()))
				.and(this.contains(subsetInterval.center()));
			
		}	else if (subset instanceof PIVL) {

			/*
				if both are PIVLs:
				
				if periods are same and superset.nextTo(subset.phase.low).contains(subset.phase) -> true
				
				if periods differ -> false
				if not superset.nextTo(subset.phase.low).contains(subset.phase) -> false
			  	if subset.phase.width is greater than superset.phase.width -> false
			  	if subset.phase.center is not in superset -> false
				  if subset.phase.low is not in superset -> false
				  if subset.phase.high is not in superset -> false
				
				else return unknown
			*/
			
			PQ period = (PQ)_period;
			PIVL subsetPivl = (PIVL)subset;

			REAL periodRatio = ((PQ)_period).dividedBy((PQ)subsetPivl.period()).toREAL();
			
			return periodRatio.equal(periodRatio.floor())
				.and(this.contains(subsetPivl.phase()))
				.and(_alignment.isNull().and(subsetPivl.alignment().isNull())
						 .or(_alignment.equal(subsetPivl.alignment())));
			
			// XXX: there may be cases with different alignment that still would be true
			// but this may be of little practical relevance
		} else if (subset instanceof DSET) {
			BL contains = BLimpl.TRUE;
			for (T element : (DSET<T>)subset) {
				contains = contains.and(this.contains(element));
				if (contains.isFalse()) break; // return contains;
			}
			return contains;
		} else
			throw new UnsupportedOperationException("can only support PIVL and IVL, subset class was: " + subset.getClass());
	}
	
	public BL isEmpty() {
		return _phase.isNull().or(_period.isNull());
	}
	
	public BL nonEmpty() {
		return isEmpty().not();
	}
	
	/* (non-Javadoc)
	 * @see org.hl7.types.SET#except(null)
	 */
	public QSET<T> except(T element) {
		return 
		QSETDifferenceImpl.valueOf(this,QSETSingularityImpl.valueOf(element));
	}
	
	/* (non-Javadoc)
	 * @see org.hl7.types.SET#except(null)
	 */
	public QSET<T> except(SET<T> otherset) {
		if (this.contains(otherset).isFalse()) // disjoint
			return this;
		else {
			throw new UnsupportedOperationException();
		}
	}
	
	/*
	 * @see org.hl7.types.QSET#except(null)
	 */
	public QSET<T> except(QSET<T> otherset) {
		return QSETDifferenceImpl.valueOf(this, otherset);
	}
	
	public QSET<T> intersection(IVL<T> otherset) {
		return QSETIntersectionImpl.valueOf(this, otherset);
	}
	
	public QSET<T> intersection(SET<T> otherset) {
		if (otherset instanceof PIVL) {
			return QSETIntersectionImpl.valueOf(this, (QSET<T>)otherset);
		}
		else
			throw new UnsupportedOperationException();
	}
	
	public QSET<T> intersection(QSET<T> otherset) {
		return QSETIntersectionImpl.valueOf(otherset, this);
		//throw new UnsupportedOperationException();
	}
	
	public QSET<T> union(SET<T> otherset) {
		throw new UnsupportedOperationException();
	}
	public QSET<T> union(QSET<T> otherset) {
		throw new UnsupportedOperationException();
	}
	
	public Iterator<ANY> iterator() {
		throw new UnsupportedOperationException();
	}
	
	private IVL<T> HULL = (IVL<T>)IVLimpl.valueOf(BLimpl.FALSE, TSnull.NINF, TSnull.PINF, BLimpl.FALSE);

	public IVL<T> hull() {
		return HULL;
	}

	/*
	 * this must have a nonNull phase 
	 */
	public IVL<T> nextTo(T element) {
		if (element instanceof TS) {
			
			T low;
			T high;
			PQ pqdiff;
			BL highClosed = _phase.highClosed();
			BL lowClosed = _phase.lowClosed();
			PQ period = (PQ)_period;
			
			// check if we're dealing with a pivl with a null phase
			// In reality, this should not happen
			if (_phase.isNull().isTrue()) {
				return IVLnull.NI;
			}
			if (_alignment.isNull().isTrue()) {
				if (_phase.low().lessOrEqual(element).isTrue()) {
					pqdiff = (PQ) (element.minus(_phase.low()));
					// if this contains element, just return the 
					// interval of this that started most recently
					
					if (this.contains(element).isTrue()) {
						
						low = (T)(_phase.low()).plus(period.times(pqdiff.dividedBy(period).toREAL().floor()));
						high = (T)(_phase.high()).plus(period.times(pqdiff.dividedBy(period).toREAL().floor()));
						return IVLimpl.valueOf(lowClosed, low, high, highClosed);
					}
				}
				else { // element is less than phase.low()
					pqdiff = (PQ) (_phase.low().minus(element));
					// if this contains element, just return the 
					// interval of this that started most recently
					if (this.contains(element).isTrue()) {
						low = (T)(_phase.low()).minus(period.times(pqdiff.dividedBy(period).toREAL().floor())).minus(period);
						high = (T)(_phase.high()).minus(period.times(pqdiff.dividedBy(period).toREAL().floor())).minus(period);
						return IVLimpl.valueOf(lowClosed, low, high, highClosed);
					}
				}
				
				// Since we know that this does not contain element, 
				// we can just return what nextAfter returns
				
				return this.nextAfter(element);
			}	else {// alignment is not null!

				if (this.contains(element).isTrue()) {

					// we have an alignment to deal with
					return makeIVLforAlignment(element, period, false);
				} else
					return this.nextAfter(element);
			}
		}
		throw new UnsupportedOperationException("can only support for TS, element class was: " + element.getClass());
	}

	private IVL makeIVLforAlignment(TS element, PQ period, boolean getOneMore) {
		Date elementDate=element.toDate();
		Date lowDate=_phase.low().toDate();

		GregorianCalendar elementCal=new GregorianCalendar(TimeZone.getDefault());
		elementCal.setLenient(true);
		elementCal.setTime(elementDate);

		GregorianCalendar lowCal=new GregorianCalendar(TimeZone.getDefault());
		lowCal.setLenient(true);
		lowCal.setTime(lowDate);

		lowCal=findMostRecent(lowCal,period,elementCal,getOneMore);
		
		return IVLimpl
			.valueOf(_phase.lowClosed(),
							 (T)TSjuDateAdapter.valueOf(lowCal.getTime(), _phase.low().precision()),
							 (T)TSjuDateAdapter.valueOf(lowCal.getTime(), _phase.high().precision()).plus((PQ)_phase.width()),
							 _phase.highClosed());
	}

	
	/*
	 * this must have a nonNull phase
	 */
	public IVL<T> nextAfter(T element) {  
		if (element instanceof TS) {
			
			T low;
			T high;
			PQ pqdiff;
			PQ period = (PQ)_period;
			BL highClosed = _phase.highClosed();
			BL lowClosed = _phase.lowClosed();
			
			// check if we're dealing with a pivl with a null phase
			// In reality, this should not happen
			if (_period.isNull().isTrue()) {
				return IVLnull.UNK;
			}
			if (_alignment.isNull().isTrue()) {
				if (_phase.low().lessOrEqual(element).isTrue()) {
					pqdiff = (PQ) (element.minus(_phase.low()));
					// if this contains element, just return the 
					// interval of this that will start next
					if (this.contains(element).isTrue()) {
						low = (T)(_phase.low()).plus(period.times(pqdiff.dividedBy(period).toREAL().floor())).plus(period);
						high = (T)(_phase.high()).plus(period.times(pqdiff.dividedBy(period).toREAL().floor())).plus(period);
						return IVLimpl.valueOf(lowClosed, low, high, highClosed);
					}
					else { // this does not contain element, so set low
						// and high to be next occurence of phase
						low = (T)(_phase.low()).plus(period.times(pqdiff.dividedBy(period).toREAL().floor())).plus(period);
						high = (T)(_phase.high()).plus(period.times(pqdiff.dividedBy(period).toREAL().floor())).plus(period);
					}
				}		
				else { // element is less than phase.low()
					pqdiff = (PQ) (_phase.high().minus(element));
					// if this contains element, just return the 
					// interval of this that will start next
					if (this.contains(element).isTrue()) {
						low = (T)(_phase.low()).minus(period.times(pqdiff.dividedBy(period).toREAL().floor())).plus(period);
						high = (T)(_phase.high()).minus(period.times(pqdiff.dividedBy(period).toREAL().floor())).plus(period);
						return IVLimpl.valueOf(lowClosed, low, high, highClosed);
					}
					else { // this does not contain element, so set low 
						// and high to be next occurence of phase
						low = (T)(_phase.low()).minus(period.times(pqdiff.dividedBy(period).toREAL().floor()));
						high = (T)(_phase.high()).minus(period.times(pqdiff.dividedBy(period).toREAL().floor()));
					}
				}
				
				return IVLimpl.valueOf(lowClosed, low, high, highClosed);
			}
			else { // alignment is not null 
				// we have an alignment to deal with
				return makeIVLforAlignment(element, period, true);
			}
		}
		throw new UnsupportedOperationException("can only support for TS, element class was: " + element.getClass());
		
	}
	
	
	
	public QSET<T> periodicHull(QSET<T> otherset) {
		return QSETPeriodicHullImpl.valueOf(this, otherset);
	}
	
	// NOTE: never called
	public BL interleaves(QSET<T> otherset) {
		if (otherset instanceof PIVL) {
			PIVL thatPIVL = (PIVL)otherset;
			T thatLow;
			T thatHigh;
			QTY.diff thatPeriod;
			T thisLow;
			T thisHigh;
			QTY.diff thisPeriod;
			
			// quickly see if the two are equal
			if(this.equal(otherset).isTrue()) return BLimpl.TRUE;
			
			if (this.phase().low().lessOrEqual(thatPIVL.phase().low()).isTrue()) {
				thatLow = (T)thatPIVL.phase().low();
				thatHigh = (T)thatPIVL.phase().high();
				thatPeriod = (diff)thatPIVL.period();
				thisLow = this.phase().low();
				thisHigh = this.phase().high();
				thisPeriod = this.period();
				
				//if (this.equal(thatPIVL).isFalse()) {
				if (this.contains(thatLow).and(this.contains(thatHigh)).isTrue()) {
					return BLimpl.FALSE;
				}
				if (thatPIVL.contains(thisLow).and(thatPIVL.contains(thisHigh)).isTrue()) {
					return BLimpl.FALSE;
				}
				//}
			} 
			
			else {
				// TODO: make this a bit less obfuscated.  
				
				thatLow = this.phase().low();
				thatHigh = this.phase().high();
				thatPeriod = this.period();
				thisLow = (T)thatPIVL.phase().low();
				thisHigh = (T)thatPIVL.phase().high();
				thisPeriod = (diff) thatPIVL.period();
				
				//if (this.equal(thatPIVL).isFalse()) {
				// NOTE: thisLow is NOT EQUAL to this.phase().low() in this case!!
				if (this.contains(thisLow).and(this.contains(thisHigh)).isTrue()) {
					return BLimpl.FALSE;
				}
				// NOTE: thatLow is NOT EQUAL to thatPIVL.phase().low() in this case!!
				if (thatPIVL.contains(thatLow).and(thatPIVL.contains(thatHigh)).isTrue()) {
					return BLimpl.FALSE;
				}
				//}
			}
			
			// this must start <= when that starts
			if (thisLow.lessOrEqual(thatLow).isFalse()) {
				return BLimpl.FALSE;
			}
			// that must end >= when this ends
			
			if (thatHigh.greaterOrEqual(thisHigh).isFalse()) {
				return BLimpl.FALSE;
			}
			// check if that is going outpace this
			
			if (thatLow.minus(thisLow).equal(thatLow.plus(thatPeriod).minus(thisLow.plus(thisPeriod))).isFalse()) {
				return BLimpl.FALSE;
			}
			
			// if we aren't false yet, must be true
			return BLimpl.TRUE;
		}
		throw new UnsupportedOperationException();
	}
	
	public T any() { 
		throw new UnsupportedOperationException();
	}
	
	public SET<T> select(Criterion c) {
		throw new UnsupportedOperationException();
	}
	
}
