package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Info.java TODO comment me
 * 
 * @author chris 12-Feb-07
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_info", schema = "public", uniqueConstraints = {})
public class Info implements Serializable
{
    // FIELDS
    private static final long serialVersionUID = 4842490438335143818L;
    @Id
    @Column(name = "info_variable")
    private String variable;
    @Column(name = "info_value")
    private String value;

    public Info()
    {
    }

    public Info(String variable, String value)
    {
        this.variable = variable;
        this.value = value;
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return this.value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * @return the variable
     */
    public String getVariable()
    {
        return this.variable;
    }

    public void setVariable(String variable)
    {
        this.variable = variable;
    }
}