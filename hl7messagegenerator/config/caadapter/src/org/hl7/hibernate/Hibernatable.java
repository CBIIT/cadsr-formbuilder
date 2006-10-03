package org.hl7.hibernate;

public interface Hibernatable {
  /** Unique object identifier for persistence. This is an assigned UUID, so that it will always work. 
			The setter should also be implemented, but with protected access (sorry, can't be done!). */
  String getInternalId();
  void setInternalId(String value);
  /** Version number for optimistic locking.
			The setter should also be implemented, but with protected access (sorry, can't be done!). */
  long getInternalVersionNumber();
  void setInternalVersionNumber(long value);
}
