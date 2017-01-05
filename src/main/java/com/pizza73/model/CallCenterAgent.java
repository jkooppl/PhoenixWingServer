package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * CallCenterAgent.java TODO comment me
 * 
 * @author chris 8-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "csc_agent_500", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "csc_agent_sequence", sequenceName = "csc_agent_500_id_seq")
public class CallCenterAgent implements Serializable
{
    private static final long serialVersionUID = -1513425984604698929L;
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "csc_agent_sequence")
    @Column(name = "agent_id")
    private Integer id;

    @Column(name = "employee_no", length = 32)
    private String employeeNumber;

    @Column(name = "name", length = 128)
    private String name;

    // Constructors
    /** default constructor */
    public CallCenterAgent()
    {
    }

    // Property accessors
    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer agentId)
    {
        this.id = agentId;
    }

    /**
     * @return the employeeNumber
     */
    public String getEmployeeNumber()
    {
        return this.employeeNumber;
    }

    /**
     * @param employeeNumber
     *            the employeeNumber to set
     */
    public void setEmployeeNumber(String employeeNumber)
    {
        this.employeeNumber = employeeNumber;
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
}