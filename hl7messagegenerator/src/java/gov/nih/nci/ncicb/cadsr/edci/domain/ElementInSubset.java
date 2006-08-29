package gov.nih.nci.ncicb.cadsr.edci.domain;

import java.io.Serializable;

public interface ElementInSubset extends Serializable
{
   public  String getValue();
   public  void setValue(String value);
   
   public  int getSequenceNumber();
   public void setSequenceNumber(int sequenceNumber);
}
