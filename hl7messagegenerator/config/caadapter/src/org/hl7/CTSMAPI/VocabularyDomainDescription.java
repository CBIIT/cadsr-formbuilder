package org.hl7.CTSMAPI;


/**
* org/hl7/CTSMAPI/VocabularyDomainDescription.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from idl/CTSMAPI.idl
* Monday, April 21, 2003 10:17:09 AM EDT
*/


/** 
 *<PRE>Vocabulary Domain - a complete description of a vocabulary domain for browsing purposes
 *	vocabularyDomain_name	- the unique name of the domain
 *	description		- a description of the intent and purpose of the domain
 *	restrictsDomain_name 	- the vocabulary domain, if any, that is the superclass of this domain
 *	basisOfDomains		- the vocabulay domains, if any, that are subclasses of this domain
 *	constrainsAttributes 	- the attributes, if any, that are constrained directly by this domain
 *	representedByValueSets 	- list of links to value sets
 </PRE>
 */
public final class VocabularyDomainDescription implements org.omg.CORBA.portable.IDLEntity
{
  public org.hl7.types.ST vocabularyDomain_name = null;
  public org.hl7.types.ST description = null;
  public org.hl7.types.ST restrictsDomain_name = null;
  public org.hl7.types.ST basisOfDomains[] = null;
  public org.hl7.CTSMAPI.RIMCodedAttribute constrainsAttributes[] = null;
  public org.hl7.CTSMAPI.VocabularyDomainValueSet representedByValueSets[] = null;

  public VocabularyDomainDescription ()
  {
  } // ctor

  public VocabularyDomainDescription (org.hl7.types.ST _vocabularyDomain_name, org.hl7.types.ST _description, org.hl7.types.ST _restrictsDomain_name, org.hl7.types.ST[] _basisOfDomains, org.hl7.CTSMAPI.RIMCodedAttribute[] _constrainsAttributes, org.hl7.CTSMAPI.VocabularyDomainValueSet[] _representedByValueSets)
  {
    vocabularyDomain_name = _vocabularyDomain_name;
    description = _description;
    restrictsDomain_name = _restrictsDomain_name;
    basisOfDomains = _basisOfDomains;
    constrainsAttributes = _constrainsAttributes;
    representedByValueSets = _representedByValueSets;
  } // ctor

} // class VocabularyDomainDescription