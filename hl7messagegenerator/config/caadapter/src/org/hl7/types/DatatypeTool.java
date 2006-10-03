package org.hl7.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hl7.types.enums.EntityNamePartType;
import org.hl7.types.impl.ADXPimpl;
import org.hl7.types.impl.ADimpl;
import org.hl7.types.impl.BAGjuListAdapter;
import org.hl7.types.impl.DSETnull;
import org.hl7.types.impl.ENXPimpl;
import org.hl7.types.impl.ENXPnull;
import org.hl7.types.impl.ENimpl;

public class DatatypeTool {
	
	public static class EntityNameTool
	{
		public static String getGivenName(BAG<EN> name)
		{
			Iterator<ANY> it = name.iterator();
			while (it.hasNext())
			{
				Iterator<ENXP> itt = ((EN) it.next()).iterator();
				while (itt.hasNext())
				{
					ENXP part = itt.next();
					if (part.type().equal(EntityNamePartType.Given).isTrue())
					{
						return part.toString();
					}
				}
			}
			return null;
		}
		
		public static String getFamilyName(BAG<EN> name)
		{
			Iterator<ANY> it = name.iterator();
			while (it.hasNext())
			{
				Iterator<ENXP> itt = ((EN) it.next()).iterator();
				while (itt.hasNext())
				{
					ENXP part = itt.next();
					if (part.type().equal(EntityNamePartType.Family).isTrue())
					{
						return part.toString();
					}
				}
			}
			return null;
		}
		
		public static String getTrivialName(BAG<EN> name)
		{
			Iterator<ANY> it = name.iterator();
			while (it.hasNext())
			{
				Iterator<ENXP> itt = ((EN) it.next()).iterator();
				while (itt.hasNext())
				{
					ENXP part = itt.next();
					if (part.type().isNull().isTrue())
					{
						return part.toString();
					}
				}
			}
			return null;
		}
		
		public static BAG<EN> setGivenName(BAG<EN> name, String givenName)
		{
			List<ENXP> enxpList = new ArrayList<ENXP>();
			Collection<EN> data = new ArrayList<EN>();
			
			if (name == null)
			{
				enxpList.add(ENXPimpl.valueOf(givenName, EntityNamePartType.Given, DSETnull.NA));
			}
			else
			{
				boolean foundNamePart = false;
				Iterator<ANY> it = name.iterator();
				while (it.hasNext())
				{
					EN next = (EN) it.next();
					Iterator<ENXP> itt = next.iterator();
					while (itt.hasNext())
					{
						ENXP part = itt.next();
						if (part.type().equal(EntityNamePartType.Given).isTrue())
						{
							enxpList.add(ENXPimpl.valueOf(givenName, part.type(), part.qualifier()));
							foundNamePart = true;
						}
						else
							enxpList.add(part);
					}
					if (!foundNamePart)
						enxpList.add(ENXPimpl.valueOf(givenName, EntityNamePartType.Given, DSETnull.NA));
				}
			}
			data.add(ENimpl.valueOf(enxpList));
			return BAGjuListAdapter.valueOf(data);
		}
		
		public static BAG<EN> setFamilyName(BAG<EN> name, String familyName)
		{
			List<ENXP> enxpList = new ArrayList<ENXP>();
			Collection<EN> data = new ArrayList<EN>();
			
			if (name == null)
			{
				enxpList.add(ENXPimpl.valueOf(familyName, EntityNamePartType.Family, DSETnull.NA));
			}
			else
			{
				boolean foundNamePart = false;
				Iterator<ANY> it = name.iterator();
				while (it.hasNext())
				{
					EN next = (EN) it.next();
					Iterator<ENXP> itt = next.iterator();
					while (itt.hasNext())
					{
						ENXP part = itt.next();
						if (part.type().equal(EntityNamePartType.Family).isTrue())
						{
							enxpList.add(ENXPimpl.valueOf(familyName, part.type(), part.qualifier()));
							foundNamePart = true;
						}
						else
							enxpList.add(part);
					}
					if (!foundNamePart)
						enxpList.add(ENXPimpl.valueOf(familyName, EntityNamePartType.Family, DSETnull.NA));
				}
			}
			data.add(ENimpl.valueOf(enxpList));
			return BAGjuListAdapter.valueOf(data);
		}
	}
	
	public static class ENTool { 
		public static ENXP getGivenName(EN name) {
			Iterator<ENXP> iter = name.iterator();
			while(iter.hasNext()) {
				ENXP part = iter.next();
				if(part.type().equal(EntityNamePartType.Given).isTrue())
					return part;
			}
			return ENXPnull.NI;
			
		}
		
		public static ENXP getFamilyName(EN name) {
			Iterator<ENXP> iter = name.iterator();
			while(iter.hasNext()) {
				ENXP part = iter.next();
				if(part.type().equal(EntityNamePartType.Family).isTrue())
					return part;
			}
			return ENXPnull.NI;
		}
		
		
		public static ENXP getTrivialName(EN name) {
			Iterator<ENXP> iter = name.iterator();
			while(iter.hasNext()) {
				ENXP part = iter.next();
				if(part.type().isNull().isTrue())
					return part;
			}
			return ENXPnull.NI;
		}
		
		// those are used for the hybernate XMLWithIndexableColumns ...
		
		public static ST getFAMILYNAME(EN name) {
			ENXP result = getFamilyName(name);
			if(result.nonNull().isTrue())
				return org.hl7.types.impl.STjlStringAdapter.valueOf(result.toString().toUpperCase());
			else
				return result;
		}
		public static ST getGIVENNAME(EN name) {
			ENXP result = getGivenName(name);
			if(result.nonNull().isTrue())
				return org.hl7.types.impl.STjlStringAdapter.valueOf(result.toString().toUpperCase());
			else
				return result;
		}
		public static ST getTRIVIALNAME(EN name) {
			ENXP result = getTrivialName(name);
			if(result.nonNull().isTrue())
				return org.hl7.types.impl.STjlStringAdapter.valueOf(result.toString().toUpperCase());
			else
				return result;
		}
	}
	
	public static class ANYTool {
		public static ST getDisplayName(ANY value) {
			if(value instanceof CD) {
				CD cd = (CD)value;
				ST displayname = cd.displayName();
				return displayname;
			} else {
				return null;
			}
		}
		
		public static ST getCode(ANY value) {
			if(value instanceof CD) {
				CD cd = (CD)value;
				ST code = cd.code();
				return code;
			} else {
				return null;
			}
		}
		
		public static ST getCodeSystem(ANY value) {
			if(value instanceof CD) {
				CD cd = (CD)value;
				ST codesys = cd.codeSystem();
				return codesys;
			} else {
				return null;
			}
		}
		
		public static ST getPQValue(ANY value) {
			if(value instanceof PQ) {
				PQ pq = (PQ)value;
				REAL val = pq.value();
				if(val instanceof org.hl7.types.impl.REALnull || val == null) {
					return null;
				}
				ST st = org.hl7.types.impl.STjlStringAdapter.valueOf(val.toString());
				return st;
			} else {
				return null;
			}
		}
		
		public static ST getPQUnit(ANY value) {
			if(value instanceof PQ) {
				PQ pq = (PQ)value;
				CS cs = pq.unit();
				// this isn't what I want yet
				ST unit = cs.code();
				return unit;
			}else {
				return null;
			}
		}
		
		public static ST getPQCanonicalValue(ANY value) {
			if(value instanceof PQ) {
				PQ pq = ((PQ)value).canonical();
				REAL val = pq.value();
				if(val instanceof org.hl7.types.impl.REALnull || val == null) {
					return null;
				}
				ST st = org.hl7.types.impl.STjlStringAdapter.valueOf(val.toString());
				return st;
			} else {
				return null;
			}
		}
		
		public static ST getPQCanonicalUnit(ANY value) {
			if(value instanceof PQ) {
				PQ pq = ((PQ)value).canonical();
				CS cs = pq.unit();
				// this isn't what I want yet
				ST unit = cs.code();
				return unit;
			}else {
				return null;
			}
		}
		
		public static ST getRTONumeratorPQCanonicalValue(ANY value) {
			if(value instanceof RTO) {
				return getPQCanonicalValue(((RTO)value).numerator());
			} else {
				return null;
			}
		}
		public static ST getRTONumeratorPQCanonicalUnit(ANY value) {
			if(value instanceof RTO) {
				return getPQCanonicalUnit(((RTO)value).numerator());
			} else {
				return null;
			}
		}
		public static ST getRTODenominatorPQCanonicalValue(ANY value) {
			if(value instanceof RTO) {
				return getPQCanonicalValue(((RTO)value).denominator());
			} else {
				return null;
			}
		}
		public static ST getRTODenominatorPQCanonicalUnit(ANY value) {
			if(value instanceof RTO) {
				return getPQCanonicalUnit(((RTO)value).denominator());
			} else {
				return null;
			}
		}
		
	}
	
	public static class GTSTool {
		public static TS getLowBoundaryOfHull(SET<TS> value) {
			if(value != null && value.nonNull().isTrue() && value instanceof QSET) {
				QTY result = ((QSET<TS>)value).hull().low();
				if(result instanceof TS)
					return (TS)result;
				else
					return null;
			} else
				return null;
		}
		
		public static TS getHighBoundaryOfHull(SET<TS> value) {
			if(value != null && value.nonNull().isTrue() && value instanceof QSET) {
				QTY result = ((QSET<TS>)value).hull().high();
				if(result instanceof TS)
					return (TS)result;
				else
					return null;
			} else
				return null;
		}
	}
	
	public static class AddressTool {
		public static String getAll(BAG<AD> address) {
			String string="";
			if (address==null) {
				return string;
			}
			Iterator<ANY> itt = address.iterator();
			while(itt.hasNext()) {
				AD element = (AD)itt.next();
				Iterator<ADXP> it2 = element.iterator();
				while(it2.hasNext()) {
					ADXP element2 = it2.next();
					if(element2.nonNull().isTrue()) {
						string+=element2+" ";
					}
				}
			}
			return string; 
		}
		
		public static BAG<AD> setAll(BAG<AD> address, String newAddress) {
			String[] addresses=newAddress.split(" ");
			int numAddresses=addresses.length;
			Collection<AD> adCollection=new ArrayList(); 
			List<ADXP> adxpList=new ArrayList();
			for (int i=0;i<numAddresses;i++) {
				adxpList.add(ADXPimpl.valueOf(addresses[i]));
			}
			adCollection.add(ADimpl.valueOf(adxpList));
			return BAGjuListAdapter.valueOf(adCollection);
		}
	}
}



