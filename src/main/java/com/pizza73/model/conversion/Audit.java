package com.pizza73.model.conversion;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Audit.java TODO comment me
 * 
 * @author chris 25-Apr-07
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "audit", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "audit_sequence", sequenceName = "audit_id", allocationSize = 1)
public class Audit implements Serializable
{
    private static final long serialVersionUID = -9198545445939298259L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_sequence")
    @Column(name = "audit_id")
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "business_date", nullable = false)
    private Calendar businessDate;

    @Column(name = "operator_id")
    private String operator;

    private final char type = 'i';

    @Column(name = "message")
    private String message;

    public Audit()
    {
    }

    public Audit(Calendar date, String operator)
    {
        this.businessDate = date;
        if (operator.length() > 6)
        {
            operator = operator.substring(0, 6);
        }
        this.operator = operator;
    }

    public Audit(Calendar date, String operator, String message)
    {
        this(date, operator);
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage()
    {
        return this.message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * @return the businessDate
     */
    public Calendar getBusinessDate()
    {
        return this.businessDate;
    }

    /**
     * @return the id
     */
    public Integer getId()
    {
        return this.id;
    }

    /**
     * @return the operator
     */
    public String getOperator()
    {
        return this.operator;
    }

    /**
     * @return the type
     */
    public char getType()
    {
        return this.type;
    }
}
