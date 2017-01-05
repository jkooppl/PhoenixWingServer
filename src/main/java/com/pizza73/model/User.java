package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Type;

/**
 * User.java TODO comment me
 * 
 * @author chris 8-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */

@MappedSuperclass
public class User implements Serializable
{
    private static final long serialVersionUID = 2158113780994368641L;

    // Fields
    @Column(name = "phone_number", columnDefinition = "char(10)")
    private String phone;

    @Type(type = "trim")
    @Column(name = "name", columnDefinition = "char(40)")
    private String name;

    // Constructors
    public User()
    {
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return this.phone;
    }

    public void setPhone(String phone)
    {
        if (phone != null)
        {
            phone = StringUtils.deleteWhitespace(phone);
            phone = phone.replace("-", "");
            phone = phone.replace("(", "");
            phone = phone.replace(")", "");
        }

        this.phone = phone;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;

        final User customer = (User) o;

        if (name != null ? !name.equals(customer.getName()) : customer.getName() != null)
            return false;
        if (phone != null ? !phone.equals(customer.getPhone()) : customer.getPhone() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (name != null ? name.hashCode() : 0);
        result = 29 * result + (phone != null ? phone.hashCode() : 0);

        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", this.name)
            .append("phone", this.phone).toString();
    }
}