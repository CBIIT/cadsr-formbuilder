package org.hl7.hibernate;

import org.hibernate.cfg.NamingStrategy;
import java.io.Serializable;
import org.hibernate.util.StringHelper;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

/**
 * An improved naming strategy that just adds an "_" to everything :)
 * @see DefaultNamingStrategy the default strategy
 * @author Matt Carlson
 */
public class OracleNamingStrategy extends ReservedWordAvoidanceNamingStrategy implements NamingStrategy, Serializable {
  /**
   * The singleton instance
   */
  public static final NamingStrategy INSTANCE = new OracleNamingStrategy();

  protected OracleNamingStrategy() {}

	protected String avoidReservedWord(String s) {
		s = super.avoidReservedWord(StringHelper.unqualify(s));
		if(s.length() > 30)
			return s.substring(0,29) + Math.abs(s.substring(29).hashCode() % 10);
		else 
			return s;
	}
}
