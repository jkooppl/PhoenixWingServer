package com.pizza73.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * 
 * @author chris
 * 
 */
public class TrimmedString implements UserType
{
    public TrimmedString()
    {
        super();
    }

    public int[] sqlTypes()
    {
        return new int[] { Types.CHAR };
    }

    public Class<String> returnedClass()
    {
        return String.class;
    }

    public boolean equals(Object x, Object y) throws HibernateException
    {
        return (x == y) || (x != null && y != null && (x.equals(y)));
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
    {
        String val = rs.getString(names[0]);
        return val != null ? val.trim() : null;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
    {
        st.setString(index, (String) value);
    }

    public Object deepCopy(Object value) throws HibernateException
    {
        if (value == null)
            return null;
        return new String((String) value);
    }

    public boolean isMutable()
    {
        return false;
    }

    public Object assemble(Serializable arg0, Object arg1) throws HibernateException
    {
        return deepCopy(arg0);
    }

    public Serializable disassemble(Object value)
    {
        return (Serializable) deepCopy(value);
    }

    /*
     * (non-Javadoc) (at) see org (dot)
     * hibernate.usertype.UserType#hashCode(java.lang.Object)
     */
    public int hashCode(Object arg0) throws HibernateException
    {
        return arg0.hashCode();
    }

    /*
     * (non-Javadoc) (at) see org (dot)
     * hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object,
     * java.lang.Object)
     */
    public Object replace(Object arg0, Object arg1, Object arg2) throws HibernateException
    {
        return deepCopy(arg0);
    }
}
