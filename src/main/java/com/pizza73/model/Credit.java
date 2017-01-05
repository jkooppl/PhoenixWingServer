package com.pizza73.model;

import java.io.Serializable;
import java.text.DateFormat;
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

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 * Credit.java TODO comment me
 * 
 * @author chris 3-Jul-07
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_credit", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "credit_sequence", sequenceName = "iq2_credit_id", allocationSize = 1)
public class Credit implements Serializable
{

    // FIELDS
    private static final long serialVersionUID = -2471412990325897989L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_sequence")
    @Column(name = "credit_id")
    private Integer id;

    @Column(name = "phone_number", nullable = false)
    private String phone;

    @Column(name = "comment")
    private String comment;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private boolean expired;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.INSERT)
    @Column(name = "date_issued", nullable = true, updatable = false, insertable = false)
    private Calendar dateIssued;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.ALWAYS)
    @Column(name = "last_used", insertable = false, updatable = false)
    private Calendar lastUsed;

    @Column(name = "online_id", updatable = false)
    private Integer customerId = 0;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "operator_id")
    private final Integer operatorId = 0;

    @Column(name = "operator_name", updatable = false)
    private String operatorName;

    @Column(name = "last_order_id")
    private Integer lastOrder;

    public Credit()
    {
    }

    /**
     * @return the amount
     */
    public Integer getAmount()
    {
        return this.amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }

    /**
     * @return the lastUsed
     */
    public Calendar getLastUsed()
    {
        return this.lastUsed;
    }

    /**
     * @param lastUsed
     *            the lastUsed to set
     */
    public void setLastUsed(Calendar dateUsed)
    {
        this.lastUsed = dateUsed;
    }

    /**
     * @return the expired
     */
    public boolean isExpired()
    {
        return this.expired;
    }

    /**
     * @param expired
     *            the expired to set
     */
    public void setExpired(boolean expired)
    {
        this.expired = expired;
    }

    /**
     * @return the phone
     */
    public String getPhone()
    {
        return this.phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * @return the dateIssued
     */
    public Calendar getDateIssued()
    {
        return this.dateIssued;
    }

    /**
     * @return the id
     */
    public Integer getId()
    {
        return this.id;
    }

    /**
     * @return the comment
     */
    public String getComment()
    {
        return this.comment;
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getOperatorName()
    {
        return operatorName;
    }

    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    public Integer getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Integer id)
    {
        this.customerId = id;
    }

    public Integer getOperatorId()
    {
        return this.operatorId;
    }

    public Integer getLastOrder()
    {
        return lastOrder;
    }

    public void setLastOrder(Integer lastOrder)
    {
        this.lastOrder = lastOrder;
    }

    public String getWebDateIssued()
    {
        DateFormat df = DateFormat.getInstance();
        return df.format(this.dateIssued.getTime());
    }

    public String getWebLastUsed()
    {
        DateFormat df = DateFormat.getInstance();
        return df.format(this.lastUsed.getTime());
    }
}