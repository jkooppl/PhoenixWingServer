package com.pizza73.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "iq2_batch_record", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "batch_record_sequence", sequenceName = "iq2_batch_record_id", allocationSize = 1)
public class BatchRecord implements Serializable
{

    private static final long serialVersionUID = -4645531147946627545L;
    @Id
    @Column(name = "batch_record_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batch_record_sequence")
    private Integer id;

    @Column(name = "debit")
    private BigDecimal debit = BigDecimal.valueOf(0, 2);
    @Column(name = "visa")
    private BigDecimal visa = BigDecimal.valueOf(0, 2);
    @Column(name = "mastercard")
    private BigDecimal mastercard = BigDecimal.valueOf(0, 2);
    @Column(name = "amex")
    private BigDecimal amex = BigDecimal.valueOf(0, 2);
    @Column(name = "giftcard")
    private BigDecimal giftcard = BigDecimal.valueOf(0, 2);
    @Column(name = "batch_number")
    private String batchNumber;
    @Column(name = "is_wireless")
    private boolean wireless = true;

    public boolean isWireless()
    {
        return wireless;
    }

    public void setWireless(boolean wireless)
    {
        this.wireless = wireless;
    }

    public BatchRecord(Integer id, BigDecimal debit, BigDecimal visa, BigDecimal mastercard, BigDecimal amex,
        BigDecimal giftcard, String batchNumber, boolean wireless)
    {
        super();
        this.id = id;
        this.debit = debit;
        this.visa = visa;
        this.mastercard = mastercard;
        this.amex = amex;
        this.giftcard = giftcard;
        this.batchNumber = batchNumber;
        this.wireless = wireless;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public BigDecimal getDebit()
    {
        return debit;
    }

    public void setDebit(BigDecimal debit)
    {
        this.debit = debit;
    }

    public BigDecimal getVisa()
    {
        return visa;
    }

    public void setVisa(BigDecimal visa)
    {
        this.visa = visa;
    }

    public BigDecimal getMastercard()
    {
        return mastercard;
    }

    public void setMastercard(BigDecimal mastercard)
    {
        this.mastercard = mastercard;
    }

    public BigDecimal getAmex()
    {
        return amex;
    }

    public void setAmex(BigDecimal amex)
    {
        this.amex = amex;
    }

    public String getBatchNumber()
    {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber)
    {
        this.batchNumber = batchNumber;
    }

    public BigDecimal getBatchRecordTotal()
    {
        return this.visa.add(this.mastercard).add(this.amex).add(this.debit).add(this.giftcard);
    }

    public void restoreDefault()
    {
        this.setAmex(BigDecimal.valueOf(0, 2));
        this.setDebit(BigDecimal.valueOf(0, 2));
        this.setMastercard(BigDecimal.valueOf(0, 2));
        this.setVisa(BigDecimal.valueOf(0, 2));
        this.setGiftcard(BigDecimal.valueOf(0, 2));
        this.setBatchNumber("");
        this.setWireless(true);
    }

    public BatchRecord()
    {
        super();
    }

    public BigDecimal getGiftcard()
    {
        return giftcard;
    }

    public void setGiftcard(BigDecimal giftcard)
    {
        this.giftcard = giftcard;
    }

}