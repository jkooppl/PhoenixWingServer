package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Location.java TODO comment me
 * 
 * @author chris 22-Dec-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_location", schema = "public", uniqueConstraints = {})
public class Location implements Serializable, Comparable<Location>
{
    // FIELDS
    private static final long serialVersionUID = -5961842363122525501L;

    @Id
    @Column(name = "location_code")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "display_hint")
    private String displayHint;

    /**
    * 
    */
    public Location()
    {
    }

    /**
     * @return the id
     */
    public String getId()
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

    public String getDisplayName()
    {
        return this.displayName;
    }

    public String getDisplayHint()
    {
        return this.displayHint;
    }

    @Override
    public boolean equals(Object o)
    {
        return super.equals(o);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", this.name).toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Location loc)
    {
        if (this.getDisplayHint() != null && loc.getDisplayHint() != null)
        {
            return this.getDisplayHint().compareTo(loc.getDisplayHint());
        }
        return 0;
    }
}