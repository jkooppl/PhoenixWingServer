package com.pizza73.model.conversion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 * Iqq.java TODO comment me
 * 
 * @author chris 29-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
// @Entity
// @Table(name = "iqq", schema = "public", uniqueConstraints = {})
public class Iqq implements Serializable
{

    // FIELDS
    private static final long serialVersionUID = 4975055340972710357L;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "rec_type_id")
    private Integer recordTypeId;

    @Column(name = "sequence_id")
    private Integer sequenceId = Integer.valueOf(0);

    @Column(name = "shop_id")
    private Integer shopId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "iq_state_id")
    private String iqStateId = "NEW";

    @Column(name = "iq_status")
    private String iqStatus = "ACK";

    @Column(name = "error_msgs")
    private String errorMsgs = "";

    @Column(name = "text_image_path")
    private String textImagePath = "";

    @Column(name = "retry_attempts")
    private Integer retryAttempts = Integer.valueOf(0);

    @Column(name = "retry_interval")
    private Integer retryInterval = Integer.valueOf(30);

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.INSERT)
    @Column(name = "time_queued")
    private Integer timeQueued;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.ALWAYS)
    @Column(name = "last_update")
    private Integer lastUpdate;

    public Iqq()
    {
    }

    /**
     * @return the customerId
     */
    public String getCustomerId()
    {
        return this.customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    /**
     * @return the errorMsgs
     */
    public String getErrorMsgs()
    {
        return this.errorMsgs;
    }

    /**
     * @param errorMsgs
     *            the errorMsgs to set
     */
    public void setErrorMsgs(String errorMsgs)
    {
        this.errorMsgs = errorMsgs;
    }

    /**
     * @return the iqStateId
     */
    public String getIqStateId()
    {
        return this.iqStateId;
    }

    /**
     * @param iqStateId
     *            the iqStateId to set
     */
    public void setIqStateId(String iqStateId)
    {
        this.iqStateId = iqStateId;
    }

    /**
     * @return the iqStatus
     */
    public String getIqStatus()
    {
        return this.iqStatus;
    }

    /**
     * @param iqStatus
     *            the iqStatus to set
     */
    public void setIqStatus(String iqStatus)
    {
        this.iqStatus = iqStatus;
    }

    /**
     * @return the lastUpdate
     */
    public Integer getLastUpdate()
    {
        return this.lastUpdate;
    }

    /**
     * @param lastUpdate
     *            the lastUpdate to set
     */
    public void setLastUpdate(Integer lastUpdate)
    {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the orderId
     */
    public Integer getOrderId()
    {
        return this.orderId;
    }

    /**
     * @param orderId
     *            the orderId to set
     */
    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    /**
     * @return the recordTypeId
     */
    public Integer getRecordTypeId()
    {
        return this.recordTypeId;
    }

    /**
     * @param recordTypeId
     *            the recordTypeId to set
     */
    public void setRecordTypeId(Integer recordTypeId)
    {
        this.recordTypeId = recordTypeId;
    }

    /**
     * @return the retryAttempts
     */
    public Integer getRetryAttempts()
    {
        return this.retryAttempts;
    }

    /**
     * @param retryAttempts
     *            the retryAttempts to set
     */
    public void setRetryAttempts(Integer retryAttempts)
    {
        this.retryAttempts = retryAttempts;
    }

    /**
     * @return the retryInterval
     */
    public Integer getRetryInterval()
    {
        return this.retryInterval;
    }

    /**
     * @param retryInterval
     *            the retryInterval to set
     */
    public void setRetryInterval(Integer retryInterval)
    {
        this.retryInterval = retryInterval;
    }

    /**
     * @return the sequenceId
     */
    public Integer getSequenceId()
    {
        return this.sequenceId;
    }

    /**
     * @param sequenceId
     *            the sequenceId to set
     */
    public void setSequenceId(Integer sequenceId)
    {
        this.sequenceId = sequenceId;
    }

    /**
     * @return the shopId
     */
    public Integer getShopId()
    {
        return this.shopId;
    }

    /**
     * @param shopId
     *            the shopId to set
     */
    public void setShopId(Integer shopId)
    {
        this.shopId = shopId;
    }

    /**
     * @return the textImagePath
     */
    public String getTextImagePath()
    {
        return this.textImagePath;
    }

    /**
     * @param textImagePath
     *            the textImagePath to set
     */
    public void setTextImagePath(String textImagePath)
    {
        this.textImagePath = textImagePath;
    }

    /**
     * @return the timeQueued
     */
    public Integer getTimeQueued()
    {
        return this.timeQueued;
    }

    /**
     * @param timeQueued
     *            the timeQueued to set
     */
    public void setTimeQueued(Integer timeQueued)
    {
        this.timeQueued = timeQueued;
    }

}
