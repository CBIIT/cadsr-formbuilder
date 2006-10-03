package org.hl7.meta.impl;

import org.hl7.meta.MessageType;
import org.hl7.meta.LoaderException;
import org.hl7.meta.DatatypeMetadataFactory;
import org.hl7.meta.UnknownDatatypeException;
import org.hl7.meta.Cardinality;
import org.hl7.meta.CodingStrength;
import org.hl7.meta.Conformance;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.List;
import java.util.Stack;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import net.sf.saxon.xpath.StandaloneContext;
import net.sf.saxon.xpath.XPathEvaluator;
import net.sf.saxon.xpath.XPathException;
import net.sf.saxon.xpath.XPathExpression;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.AbstractNode;
import net.sf.saxon.event.Builder;
import net.sf.saxon.Configuration;

import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.parsers.ParserConfigurationException;

/**
 * A message type loader that loads metadata from MIF files. Uses Saxon tree
 * model and XPath implementation to walk it.
 */
public class MifMessageTypeLoader extends MessageTypeLoaderImpl
{
  //-------------------------------------------------------------------------
  protected static final Logger LOGGER = Logger.getLogger("org.hl7.meta");

  //-------------------------------------------------------------------------
  /** RIM association lookup table. */
  private final RimAssociationLookups lookups_;
  private String rimFileName_;

  /* Should have one XPath evaluator per message type. */
  private final XPathEvaluator xpathEvaluator_;
  private Stack<String> messageTypeIds_ = new Stack<String>();

  private CmetCacheImpl cmetci = null;
  /**
   * Factory for parsing datatype literals.
   */
  private DatatypeMetadataFactory dmFactory_ =
    DatatypeMetadataFactoryImpl.instance();

  //-------------------------------------------------------------------------
  /**
   * Note: rimFileName is only needed to map an association RIM name into its
   * corresponding inverse association name. Probably a bug in MIF that we need
   * to do this at all. Once that is fixed, rimFileName parameter should be
   * removed.
   * 
   * @param mifFileName
   * @param rimFileName
   * @throws XmlLoaderException
   */
  public MifMessageTypeLoader(String mifFileName, String rimFileName)
    throws XmlLoaderException
  {
    
  	this.rimFileName_ = rimFileName;
  	cmetci = new CmetCacheImpl(this);
  	// Load RIM association lookups.
    try
    {
      RimAssociationLookupsParser ralp = new RimAssociationLookupsParser();
      lookups_ = ralp.parse(rimFileName);
      //lookups_.dump(System.out);
    }
    catch (IOException ex)
    {
      throw new XmlLoaderException(ex);
    }
    catch (ParserConfigurationException ex)
    {
      throw new XmlLoaderException(ex);
    }
    catch (SAXException ex)
    {
      throw new XmlLoaderException(ex);
    }

    // Now deal with MIF flat model proper.
    File mifFile = new File(mifFileName);

    if (!mifFile.exists())
    {
      throw new XmlLoaderException(new FileNotFoundException(mifFileName));
    }
    if (!mifFile.canRead())
    {
      throw new XmlLoaderException(new IOException("Cannot read: " +
        mifFileName));
    }

    try
    {
    	Source source = new SAXSource(new InputSource(new FileInputStream(
        mifFile)));
      NodeInfo node = Builder.build(source, null, new Configuration());
      xpathEvaluator_ = new XPathEvaluator(node);
      StandaloneContext context = new StandaloneContext();
      context.declareNamespace("mif", "urn:hl7-org:v3/mif");
      xpathEvaluator_.setStaticContext(context);
    }
    catch (IOException ex)
    {
      throw new XmlLoaderException(ex);
    }
    catch (XPathException ex)
    {
      throw new XmlLoaderException(ex);
    }
  }

  //-------------------------------------------------------------------------
  public MessageType loadMessageType(String id) throws LoaderException
  {
    
  	messageTypeIds_.push(id);
    validateMessageTypeId();

    MessageTypeImpl mti = new MessageTypeImpl(id);
    processRoot(mti);

    LOGGER.finest("----------------------------------------\n" +
      "Loaded message type " + id + ":\n" + mti.toString());

    messageTypeIds_.pop();
    return mti;
  }

  //-------------------------------------------------------------------------
  public MessageType loadCmet(String id) throws XmlLoaderException
  {
  	//TODO fix the hard coded stuff
  	String mifFileName = "data\\mif202\\" +id + ".mif";
  	MessageType mt = null;
	try {
		MifMessageTypeLoader mtl = new MifMessageTypeLoader( mifFileName,rimFileName_ );
		mt = mtl.loadMessageType(id);
	} catch (XmlLoaderException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (LoaderException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  	return mt;
  	// mtl can we destroy this somehow now
  }

  //-------------------------------------------------------------------------
  private void validateMessageTypeId() throws XmlLoaderException
  {
    String idInMif = evaluateSingleString("/mif:staticModel/@id");

    String id = messageTypeIds_.peek();
    if (!idInMif.equals(id))
    {
      throw new XmlLoaderException(id, "/mif:staticModel/@id",
        "Trying to load message type " + id +
        " but MIF file contains " + idInMif);
    }
  }

  //-------------------------------------------------------------------------
  private void processRoot(MessageTypeImpl mti) throws XmlLoaderException
  {
    String rootClassName = evaluateSingleString(
      "/mif:staticModel/mif:ownedEntryPoint/@className");
    mti.setName(rootClassName);
  	
  	
    CloneClassImpl cci = new CloneClassImpl(mti);
    cci.setName(rootClassName);

    AbstractNode classNode = (AbstractNode)evaluateSingle(
      "/mif:staticModel/mif:ownedClass/mif:class[@name = '" +
      rootClassName + "']");
    xpathEvaluator_.setContextNode(classNode);

    String rimClass = evaluateSingleString(
      "mif:derivationSupplier[@staticModelDerivationId = 1]/@className");
    cci.setRimClass(rimClass);
   
     mti.setRootClass(cci);

    processAttributesAndAssociations(cci);
  }

  //-------------------------------------------------------------------------
  private void processCloneClass(CloneClassImpl cci, String cloneName)
    throws XmlLoaderException
  {
    cci.setName(cloneName);

    // Do this after setting the clone class name, since it will be used
    // as a key inside MessageTypeImpl for caching.
    MessageTypeImpl mti = (MessageTypeImpl)cci.getMessageType();
    mti.addCloneClass(cci);

    AbstractNode classNode = (AbstractNode)evaluateSingle(
      "/mif:staticModel/mif:ownedClass/mif:class[@name = '" +
      cloneName + "'] | " +
    "/mif:staticModel/mif:ownedClass/mif:commonModelElementRef[@name = '" +
    cloneName + "']");
    xpathEvaluator_.setContextNode(classNode);

    String rimClass = evaluateSingleString(
      "mif:derivationSupplier[@staticModelDerivationId = 1]/@className");
    cci.setRimClass(rimClass);

    List choices = null;
	try {
		choices = xpathEvaluator_.evaluate(
				"/mif:staticModel/mif:ownedClass/mif:class/mif:specializationChild/@childClassName");
	} catch (XPathException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Object[] objChoiceArray = choices.toArray();
     String[] choiceArray = new String[objChoiceArray.length];
     for (int i = 0; i < choiceArray.length; i++) {
     	NodeInfo att = (NodeInfo)objChoiceArray[i];
     	choiceArray[i]= att.getStringValue();
     }
    cci.setChoices(choiceArray);

    processAttributesAndAssociations(cci);
  }

  //-------------------------------------------------------------------------
  private void processAttributesAndAssociations(CloneClassImpl cci)
    throws XmlLoaderException
  {
    for (Iterator it = iterateMatches("mif:attribute"); it.hasNext(); )
    {
      AbstractNode match = (AbstractNode)it.next();

      processAttribute(match, cci);
    }

    String matchXpath = "/mif:staticModel/mif:ownedAssociation[" +
      "mif:connections/mif:nonTraversableConnection/@participantClassName = '" +
      cci.getName() + "']/mif:connections/mif:traversableConnection";
// Sorting XPath matches doesn't work, and it is not clear why.
    for (Iterator it = iterateSortedMatches(matchXpath, "@sortKey");
      it.hasNext(); )
//    for (Iterator it = iterateMatches(matchXpath); it.hasNext(); )
    {
      AbstractNode match = (AbstractNode)it.next();

      processAssociation(match, cci);
    }
  }

  //-------------------------------------------------------------------------
  private void processAttribute(AbstractNode attributeNode,
    CloneClassImpl cci) throws XmlLoaderException
  {
    AttributeImpl ai = new AttributeImpl(cci);

    xpathEvaluator_.setContextNode(attributeNode);
    String attributeName = evaluateSingleString("@name");
    ai.setName(attributeName);

    String dt = evaluateSingleString(
      "if (mif:type/mif:supplierBindingArgumentDatatype) " +
      "then concat(mif:type/@name, '<', " +
        "mif:type/mif:supplierBindingArgumentDatatype/@name, '>') " +
      "else string(mif:type/@name)");
    try
    {
      ai.setDatatype(dmFactory_.create(dt));
    }
    catch (UnknownDatatypeException ex)
    {
      throw new XmlLoaderException("Unknown datatype: " + dt);
    }

    String rimClass = evaluateSingleString(
      "mif:derivationSupplier[@staticModelDerivationId = '1']/@className");
    ai.setRimClass(rimClass);
    String rimName = evaluateSingleString(
      "mif:derivationSupplier[@staticModelDerivationId = '1']/@attributeName");
    ai.setRimPropertyName(rimName);

    String cardinalitySpec = evaluateSingleString(
      "concat(@minimumMultiplicity, '..', @maximumMultiplicity)");
    ai.setCardinality(Cardinality.create(cardinalitySpec));

    String codingStrength = evaluateSingleOrNoneString(
      "mif:supplierDomainSpecification/@codingStrength");
    if (codingStrength != null)
    {
      ai.setCodingStrength(CodingStrength.create(codingStrength));
    }

    String mandatory = evaluateSingleOrNoneString("@isMandatory");
    if (mandatory != null)
    {
      ai.setMandatory(mandatory.equals("true"));
    }

    String conformance = evaluateSingleOrNoneString("@conformance");
    if (conformance != null)
    {
      ai.setConformance(Conformance.create(conformance));
    }

    String mnemonic = evaluateSingleOrNoneString(
      "mif:supplierDomainSpecification/@mnemonic");
    if (mnemonic != null)
    {
      ai.setFixedValues(new String[] { mnemonic });
    }

    String domainName = evaluateSingleOrNoneString(
      "mif:supplierDomainSpecification/@domainName");
    if (domainName != null)
    {
      ai.setDomains(new String[] { domainName });
    }

    String defaultValue = evaluateSingleOrNoneString("@defaultValue");
    if (defaultValue != null)
    {
      ai.setDefaultValue(defaultValue);
    }

    // TODO: Should handle multiple constraints with embedded markup.
    String constraint = evaluateSingleOrNoneString(
      "mif:annotations/mif:constraint/mif:text/text()");
    if (constraint != null)
    {
      ai.setConstraint(constraint);
    }

    cci.addAttribute(ai);
  }

  //-------------------------------------------------------------------------
  private void processAssociation(AbstractNode associationNode,
    CloneClassImpl cci) throws XmlLoaderException
  {
    xpathEvaluator_.setContextNode(associationNode);
    Long specCount = (Long)evaluateSingle(
      "count(mif:participantClassSpecialization)");
    switch (specCount.intValue())
    {
      case 0:
        processCloneAssociation(associationNode, cci);
        break;

      case 1:
        processCmetAssociation(associationNode, cci);
        break;

      default:  // >= 2
        processChoiceAssociation(associationNode, cci);
        break;
    }
  }

  //-------------------------------------------------------------------------
  private void processCloneAssociation(AbstractNode associationNode,
    CloneClassImpl cci) throws XmlLoaderException
  {
    CloneClassAssociationImpl ccai = new CloneClassAssociationImpl(cci);

    xpathEvaluator_.setContextNode(associationNode);
    String name = evaluateSingleString("@name");
    ccai.setName(name);

    String targetCloneName = evaluateSingleString("@participantClassName");

    String targetRimClass = evaluateSingleString(
      "/mif:staticModel/mif:ownedClass/mif:class[@name = '" +
      targetCloneName + "']/mif:derivationSupplier[" +
      "@staticModelDerivationId = '1']/@className");
    ccai.setRimClass(targetRimClass);

    String rimName = evaluateSingleString(
      "mif:derivationSupplier[@staticModelDerivationId = '1']/" +
      "@associationEndName");
    // Call to lookups_.correctAssociationName() will no longer be needed
    // once MIF conatins correct RIM association names.
    try
    {
      ccai.setRimPropertyName(lookups_.correctAssociationName(cci.getRimClass(),
        targetRimClass, rimName));
      String cciRimClass = cci.getRimClass();
      
     
    }
    catch (NoSuchElementException ex)
    {
      throw new XmlLoaderException(ex);
    }

//    ccai.setMetSource("???");

    String cardinalitySpec = evaluateSingleString(
      "concat(@minimumMultiplicity, '..', @maximumMultiplicity)");
    ccai.setCardinality(Cardinality.create(cardinalitySpec));

    String mandatory = evaluateSingleString("@isMandatory");
    ccai.setMandatory(mandatory.equals("true"));

   

    MessageTypeImpl mti = (MessageTypeImpl)cci.getMessageType();
    CloneClassImpl cci2 = (CloneClassImpl)mti.lookupCloneClass(targetCloneName);
    if (cci2 == null)
    {
      cci2 = new CloneClassImpl(mti);
      processCloneClass(cci2, targetCloneName);
    }
    ccai.setTarget(cci2);
    
    cci.addAssociation(ccai);
    mti.addCloneClass(cci);
  }

  //-------------------------------------------------------------------------
  private void processCmetAssociation(AbstractNode associationNode,
    CloneClassImpl cci) throws XmlLoaderException
  {
  	 xpathEvaluator_.setContextNode(associationNode);
  	NodeInfo node1 = (NodeInfo)evaluateSingle("@name");
    NodeInfo node2 = (NodeInfo)evaluateSingle(
      "mif:participantClassSpecialization/@artifactCode");
   
    NodeInfo node3 = (NodeInfo)evaluateSingle(
      "mif:participantClassSpecialization/@className");
   
    NodeInfo node4 = (NodeInfo)evaluateSingle(
      "mif:derivationSupplier[position()=1]/@associationEndName");
    

    LOGGER.finest("--- CMET association " + cci.getName() + '.' +
      node1.getStringValue() + " -> " + node2.getStringValue() + '.' +
      node3.getStringValue());

    List choices = null;
	try {
		choices = xpathEvaluator_.evaluate(
				"/mif:staticModel/mif:ownedClass/mif:class/mif:specializationChild/@childClassName");
	} catch (XPathException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Object[] objChoiceArray = choices.toArray();
     String[] choiceArray = new String[objChoiceArray.length];
     for (int i = 0; i < choiceArray.length; i++) {
     	NodeInfo att = (NodeInfo)objChoiceArray[i];
     	choiceArray[i]= att.getStringValue();
     }
    cci.setChoices(choiceArray);
    
    
    
    org.hl7.meta.impl.CmetAssociationImpl cmetasci = new org.hl7.meta.impl.CmetAssociationImpl(cci, cmetci);
    cmetasci.setName(node1.getStringValue());
    cmetasci.setCmetId(node2.getStringValue());
    String cardinalitySpec = evaluateSingleString(
    "concat(@minimumMultiplicity, '..', @maximumMultiplicity)");
    cmetasci.setCardinality(Cardinality.create(cardinalitySpec));
    
    cmetasci.setRimClass(node3.getStringValue());
   
    String rimName = node4.getStringValue();
    String targetCloneName = evaluateSingleString("@participantClassName");
   
    String targetRimClass = evaluateSingleString("/mif:staticModel/mif:ownedClass/mif:commonModelElementRef[@name = '" + 
    		targetCloneName + "']/mif:derivationSupplier[@staticModelDerivationId ='1']/@className" +
                                              " | /mif:staticModel/mif:ownedClass/mif:class[@name = '" + 
	        targetCloneName + "']/mif:derivationSupplier[@staticModelDerivationId ='1']/@className");  
    
    	    try
    	    {
    	    	cmetasci.setRimPropertyName(lookups_.correctAssociationName(cci.getRimClass(),
    	        targetRimClass, rimName));
    	    }
    	    catch (NoSuchElementException ex)
    	    {
    	      throw new XmlLoaderException(ex);
    	    }

    cci.addAssociation(cmetasci);
    MessageTypeImpl mti = (MessageTypeImpl)cci.getMessageType();
    mti.addCloneClass(cci);
 
  }

  //-------------------------------------------------------------------------
  private void processChoiceAssociation(AbstractNode associationNode,
    CloneClassImpl cci) throws XmlLoaderException
  {
  	
	   
  	xpathEvaluator_.setContextNode(associationNode);
  	
  	
  	String thisMessageId = evaluateSingleString("/mif:staticModel/@id");
  	
   
  	 List choices = null;
		try {
			choices = xpathEvaluator_.evaluate(
					"/mif:staticModel/mif:ownedClass/mif:class/mif:specializationChild/@childClassName");
		} catch (XPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] objChoiceArray = choices.toArray();
	     String[] choiceArray = new String[objChoiceArray.length];
	     for (int i = 0; i < choiceArray.length; i++) {
	     	NodeInfo att = (NodeInfo)objChoiceArray[i];
	     	choiceArray[i]= att.getStringValue();
	     }
	    cci.setChoices(choiceArray);
  	
  	ChoiceAssociationImpl cai = new ChoiceAssociationImpl(cci);
   
       String  name = evaluateSingleString("@name");
    
		String rimClass = evaluateSingleString(
				"mif:derivationSupplier[position()=1]/@className");
	
     cai.setName(name);
     cai.setRimClass(rimClass);
     String cardinalitySpec = evaluateSingleString(
     "concat(@minimumMultiplicity, '..', @maximumMultiplicity)");
     cai.setCardinality(Cardinality.create(cardinalitySpec));
     
     String targetCloneName1 = evaluateSingleString("@participantClassName");
     // this OR xPath will get both CMETs and regular
	    NodeInfo rmnm = (NodeInfo)evaluateSingle(
	      "mif:derivationSupplier[position()=1]/@associationEndName");
	    String rimName1 = rmnm.getStringValue();
	    String targetRimClass1 = evaluateSingleString("/mif:staticModel/mif:ownedClass/mif:class[@name = '"
	    		+ targetCloneName1 + "']/mif:derivationSupplier[@staticModelDerivationId ='1']/@className"  
	    		+ "| /mif:staticModel/mif:ownedClass/mif:commonModelElementRef[@name ='" 
	    		+ targetCloneName1 + "']/mif:derivationSupplier[@staticModelDerivationId ='1']/@className" 
	    		);  
	    String correctAssocName = null;
	    	    try
	    	    {
	    	    	correctAssocName = lookups_.correctAssociationName(cci.getRimClass(),
	    	    	        targetRimClass1, rimName1);
	    	    	cai.setRimPropertyName(correctAssocName);
	    	    	
	    	    }
	    	    catch (NoSuchElementException ex)
	    	    {
	    	      throw new XmlLoaderException(ex);
	    	    }

    
    // now get all the msg IDs, tnames and cnames
    List cmets = null;
    List tnames = null;
    List cnames = null;
  
	try {
		cmets = xpathEvaluator_.evaluate(
				"mif:participantClassSpecialization/@artifactCode");
		tnames = xpathEvaluator_.evaluate(
		"mif:participantClassSpecialization/@traversalName");
		cnames = xpathEvaluator_.evaluate(
		"mif:participantClassSpecialization/@className");
	} catch (XPathException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// set target
	  MessageTypeImpl mti = (MessageTypeImpl)cci.getMessageType();
	    String targetCloneName = evaluateSingleString("@participantClassName");
	    CloneClassImpl target = (CloneClassImpl)mti.lookupCloneClass(targetCloneName);
	    if (target == null)
	    {
	    	target = new CloneClassImpl(mti);
	      processCloneClass(target, targetCloneName);
	    }
	    cai.setTarget(target);    

	
	
	
	Object[] objCmetArray = cmets.toArray();
	Object[] objtNamesArray = tnames.toArray();
	Object[] objcNamesArray = cnames.toArray();
   
   
    for (int i = 0; i < objCmetArray.length; i++) {
    	
    	
    	NodeInfo name1 = (NodeInfo)objtNamesArray[i];
    	String tname = name1.getStringValue();
    	NodeInfo msgId = (NodeInfo)objCmetArray[i];
    	String msgIdstr = msgId.getStringValue();
    	NodeInfo cname = (NodeInfo)objcNamesArray[i];
    	String cnamestr = cname.getStringValue();
    	
    	
    	
    	if(msgIdstr.startsWith("COCT")) { 
    		makeCmetAssociation(cci,cai,tname,msgIdstr,rimClass, cardinalitySpec, correctAssocName);    	  	
    	}else {    		
    		makeCloneAssociation(cci,cai,tname,cnamestr,rimClass, cardinalitySpec, correctAssocName);
    	}
    	
    }//for loop
    
    //finally add the association and add to message
  
    cci.addAssociation(cai);
    mti.addCloneClass(cci);
  }
    
  // ---------------------------------------------------------------- 
    private void makeCmetAssociation(CloneClassImpl cci, ChoiceAssociationImpl cai,
    		String tname,String msgIdstr,String rimClass,
			String cardinalitySpec, String correctAssocName)
    throws XmlLoaderException
    {
    	org.hl7.meta.impl.CmetAssociationImpl cmetasci = new org.hl7.meta.impl.CmetAssociationImpl(cci, cmetci);
    	cmetasci.setName(tname);
    	cmetasci.setCmetId(msgIdstr);
    
    	cmetasci.setCardinality(Cardinality.create(cardinalitySpec));
    	cmetasci.setRimClass(rimClass);   	
    	cmetasci.setRimPropertyName(correctAssocName);
    	
    	
    	    	
    	cai.addChoice(cmetasci);
  
  }
   
   private void makeCloneAssociation(CloneClassImpl cci,ChoiceAssociationImpl cai, 
   		String tname, String cname, String rimClass,
		String cardinalitySpec , String correctAssocName) 
   throws XmlLoaderException   
   {
   	org.hl7.meta.impl.CloneClassAssociationImpl ca = new org.hl7.meta.impl.CloneClassAssociationImpl(cci);
     ca.setName(tname);  
     ca.setCardinality(Cardinality.create(cardinalitySpec));
     //TODO experiment with this xPath
     //String targetCloneName = evaluateSingleString("@participantClassName");
     String targetCloneName = cname;//wow this works
     String targetRimClass = evaluateSingleString(
       "/mif:staticModel/mif:ownedClass/mif:commonModelElementRef[@name = '" +
       targetCloneName + "']/mif:derivationSupplier[" +
       "@staticModelDerivationId = '1']/@className" +
	   "| /mif:staticModel/mif:ownedClass/mif:class[@name = '" + 
	   targetCloneName + "']/mif:derivationSupplier[" + 
	   "@staticModelDerivationId = '1']/@className");
     ca.setRimClass(targetRimClass);
     ca.setRimPropertyName(correctAssocName);//this sort of works
   
 
     
     MessageTypeImpl mti = (MessageTypeImpl)cci.getMessageType();
     CloneClassImpl cci2 = (CloneClassImpl)mti.lookupCloneClass(targetCloneName);
     if (cci2 == null)
     {
       cci2 = new CloneClassImpl(mti);
       processCloneClass(cci2, targetCloneName);
     }
     ca.setTarget(cci2);    
	 cai.addChoice(ca);  	         
   }

  //-------------------------------------------------------------------------
  private Object evaluateSingle(String xpath) throws XmlLoaderException
  {
    try
    {
      List list = xpathEvaluator_.evaluate(xpath);
      if (list.size() == 0)
      {
        String id = messageTypeIds_.peek();
        throw new XmlLoaderException(id, xpath, "No matching nodes found");
      }
      else if (list.size() > 1)
      {
        String id = messageTypeIds_.peek();
        throw new XmlLoaderException(id, xpath,
          "Too many matching nodes found");
      }
      else
      {
        return list.get(0);
      }
    }
    catch (XPathException ex)
    {
      throw new XmlLoaderException(ex);
    }
  }

  //-------------------------------------------------------------------------
  private String evaluateSingleString(String xpath) throws XmlLoaderException
  {
    Object result = evaluateSingle(xpath);
    if (result instanceof String) return (String)result;
    else if (result instanceof NodeInfo)
    {
      return ((NodeInfo)result).getStringValue();
    }
    else
    {
      String id = messageTypeIds_.peek();
      throw new XmlLoaderException(id, xpath, "Unexpected type: " +
        result.getClass().getName());
    }
  }

  //-------------------------------------------------------------------------
  private Object evaluateSingleOrNone(String xpath) throws XmlLoaderException
  {
    try
    {
      List list = xpathEvaluator_.evaluate(xpath);
      if (list.size() == 0)
      {
        return null;
      }
      else if (list.size() > 1)
      {
        String id = messageTypeIds_.peek();
        throw new XmlLoaderException(id, xpath,
          "Too many matching nodes found");
      }
      else
      {
        return list.get(0);
      }
    }
    catch (XPathException ex)
    {
      throw new XmlLoaderException(ex);
    }
  }

  //-------------------------------------------------------------------------
  private String evaluateSingleOrNoneString(String xpath)
    throws XmlLoaderException
  {
    Object result = evaluateSingleOrNone(xpath);

    if (result == null) return null;
    else if (result instanceof String) return (String)result;
    else if (result instanceof NodeInfo)
    {
      return ((NodeInfo)result).getStringValue();
    }
    else
    {
      String id = messageTypeIds_.peek();
      throw new XmlLoaderException(id, xpath, "Unexpected type: " +
        result.getClass().getName());
    }
  }

  //-------------------------------------------------------------------------
  private Iterator iterateMatches(String xpath) throws XmlLoaderException
  {
    try
    {
      return xpathEvaluator_.evaluate(xpath).iterator();
    }
    catch (XPathException ex)
    {
      throw new XmlLoaderException(ex);
    }
  }

  //-------------------------------------------------------------------------
  private Iterator iterateSortedMatches(String matchXpath, String sortXpath)
    throws XmlLoaderException
  {
    try
    {
      XPathExpression matchExpression = xpathEvaluator_.createExpression(
        matchXpath);
      XPathExpression sortExpression = xpathEvaluator_.createExpression(
        sortXpath);
      matchExpression.setSortKey(sortExpression);
      return matchExpression.evaluate().iterator();
    }
    catch (XPathException ex)
    {
      throw new XmlLoaderException(ex);
    }
  }
}
