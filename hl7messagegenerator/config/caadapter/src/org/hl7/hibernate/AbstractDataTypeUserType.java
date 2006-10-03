package org.hl7.hibernate;

import org.hibernate.usertype.UserType;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** An abstract Hibernate UserType that minimally implements the UserType interface appropriate 
    for all HL7 data types.

    @author Gunther Schadow
    @version $Id: AbstractDataTypeUserType.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
 */
public abstract class AbstractDataTypeUserType implements UserType {
          
  /** The class returned by nullSafeGet(). */
  public abstract Class returnedClass();

  /** Return the SQL type codes for the columns mapped by this type. */
  public abstract int[] sqlTypes();

  /** Retrieve an instance of the mapped class from a JDBC resultset. */
  public abstract Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException;

  /** Write an instance of the mapped class to a prepared statement. */
  public abstract void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException;


  /** Reconstruct an object from the cacheable representation, UNSUPPORTED. 
      
      @deprecated this operation is currently not supported
   */
  public Object assemble(Serializable cached, Object owner) throws HibernateException { throw new UnsupportedOperationException(); }

  /** Transform the object into its cacheable representation. 

      @deprecated this operation is currently not supported
   */
  public Serializable disassemble(Object value) throws HibernateException { throw new UnsupportedOperationException(); }

  /** Return a deep copy of the persistent state, stopping at entities and at collections. */
  public Object deepCopy(Object value) throws HibernateException { return value; }

  /** Compare two instances of the class mapped by this type for persistence "equality". */
  public boolean equals(Object x, Object y) throws HibernateException {
    // all HL7 data types have this method
    return x!=null && (x==y || x.equals(y));
  }

  /** Get a hashcode for the instance, consistent with persistence "equality". */
  public int hashCode(Object x) throws HibernateException  {
    // all HL7 data types should have this method
    return x.hashCode();
  }
          
  /** Are objects of this type mutable? No never! */
  public boolean isMutable() { return false; }

  /** What does this do? 
      @deprecated not implemented yet
   */
  public Object replace(Object original, Object target, Object owner) throws HibernateException { 
    return original;
  }
}

