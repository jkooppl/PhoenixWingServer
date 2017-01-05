package com.pizza73.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "GL", schema = "public", uniqueConstraints = {})
public class GL
{
    @Id
    @Column(name = "oid")
    private Long oID;

    @Column(name = "year")
    private Integer year;

    @Column(name = "period")
    private Integer period;

    @Column(name = "shop_id")
    private Integer shop_id;

    @Column(name = "account")
    private Integer account;

    @Column(name = "entry_date")
    @Temporal(TemporalType.DATE)
    private Calendar entryDate;

    @Column(name = "name")
    private String name;

    @Column(name = "debit")
    private BigDecimal debit = BigDecimal.valueOf(0, 2);

    @Column(name = "credit")
    private BigDecimal credit = BigDecimal.valueOf(0, 2);

    @Column(name = "source")
    private String source;

    @Column(name = "reference")
    private String reference;

    @Column(name = "doc_no")
    private String doc_no;

    @Column(name = "jrnl_no")
    private String jrnl_no;

    @Column(name = "post_date")
    @Temporal(TemporalType.DATE)
    private Calendar post_date;

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod(Integer period)
    {
        this.period = period;
    }

    public Integer getShop_id()
    {
        return shop_id;
    }

    public void setShop_id(Integer shop_id)
    {
        this.shop_id = shop_id;
    }

    public Integer getAccount()
    {
        return account;
    }

    public void setAccount(Integer account)
    {
        this.account = account;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BigDecimal getDebit()
    {
        return debit;
    }

    public void setDebit(BigDecimal debit)
    {
        this.debit = debit;
    }

    public BigDecimal getCredit()
    {
        return credit;
    }

    public void setCredit(BigDecimal credit)
    {
        this.credit = credit;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }

    public String getDoc_no()
    {
        return doc_no;
    }

    public void setDoc_no(String doc_no)
    {
        this.doc_no = doc_no;
    }

    public String getJrnl_no()
    {
        return jrnl_no;
    }

    public void setJrnl_no(String jrnl_no)
    {
        this.jrnl_no = jrnl_no;
    }

    public Calendar getPost_date()
    {
        return post_date;
    }

    public void setPost_date(Calendar post_date)
    {
        this.post_date = post_date;
    }

    public Long getOID()
    {
        return oID;
    }

    public void setOID(Long oid)
    {
        oID = oid;
    }

    public Calendar getEntryDate()
    {
        return entryDate;
    }

    public void setEntryDate(Calendar entryDate)
    {
        this.entryDate = entryDate;
    }

}
