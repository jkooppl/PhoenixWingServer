package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * ProductProperty.java TODO comment me
 * 
 * @author chris 11-Dec-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_product_property", schema = "public", uniqueConstraints = {})
public class ProductProperty implements Serializable
{

    // FIELDS
    private static final long serialVersionUID = -7956344450894313315L;

    @Id
    @Column(name = "id")
    private Integer id;
    private String name;
    private String value;

    public Integer getId()
    {
        return this.id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    public String getValue()
    {
        return this.value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof ProductProperty))
        {
            return false;
        }

        final ProductProperty prop = (ProductProperty) obj;

        if (name != null ? !name.equals(prop.getName()) : prop.getName() != null)
            return false;
        if (value != null ? !value.equals(prop.getValue()) : prop.getValue() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result;
        result = (name != null ? name.hashCode() : 0);
        result = 29 * result + (value != null ? value.hashCode() : 0);

        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", this.name)
            .append("value", this.value).toString();
    }
}