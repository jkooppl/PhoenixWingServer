package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

/**
 * SecureUser.java TODO comment me
 * 
 * @author chris 6-Nov-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_role", schema = "public", uniqueConstraints = {})
public class Role implements Serializable, GrantedAuthority
{
    private static final long serialVersionUID = 3690197650654049848L;
    @Id
    @Column(name = "role_id", insertable = false, updatable = false)
    private Integer id;
    @Column(name = "name", insertable = false, updatable = false, length = 16, unique = true)
    private String name;
    @Column(name = "description", insertable = false, updatable = false, length = 64)
    private String description;

    public Role()
    {
    }

    public Integer getId()
    {
        return this.id;
    }

    protected void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.acegisecurity.GrantedAuthority#getAuthority()
     */
    public String getAuthority()
    {
        return this.name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Role))
            return false;

        final Role role = (Role) o;

        return !(name != null ? !name.equals(role.name) : role.name != null);

    }

    @Override
    public int hashCode()
    {
        return (name != null ? name.hashCode() : 0);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(this.name).toString();
    }

    public int compareTo(Object o)
    {
        return this.getId().compareTo(((Role) o).getId());
    }
}