package com.pizza73.model.contest;

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

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 * Blacklist.java
 * 
 * @author chris Jun3 6 2010
 * 
 * @Copyright Flying Pizza 73
 * 
 *            id integer DEFAULT nextval('iq2_contest_id_seq'::text) NOT NULL,
 *            name character varying(50) NOT NULL, start_date date not null,
 *            end_date date not null, cities character varying(256) default 0,
 *            id integer DEFAULT nextval('iq2_contest_entry_id_seq'::text) NOT
 *            NULL, contest_id integer not null, online_id integer not null,
 *            phone character varying(10) not null, entry_date date not null,
 *            PRIMARY KEY(id), CONSTRAINT contest_exists FOREIGN KEY
 *            (contest_id) REFERENCES iq2_contest (id), CONSTRAINT
 *            customer_exists FOREIGN KEY (online_id) REFERENCES
 *            iq2_online_customer (online_id)
 */
@Entity
@Table(name = "iq2_contest_entry", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "contest_entry_sequence", sequenceName = "iq2_contest_entry_id_seq", allocationSize = 1)
public class ContestEntry implements Serializable
{
    private static final long serialVersionUID = 4900794716638091329L;

    // FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contest_entry_sequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "online_id")
    private Integer onlineId;

    @Column(name = "contest_id")
    private Integer contestId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "phone")
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.INSERT)
    @Column(name = "entry_date", nullable = true, updatable = false, insertable = false)
    private Calendar entryDate;

    public ContestEntry()
    {
    }

    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getOnlineId()
    {
        return this.onlineId;
    }

    public void setOnlineId(Integer name)
    {
        this.onlineId = name;
    }

    public Integer getContestId()
    {
        return contestId;
    }

    public void setContestId(Integer contestId)
    {
        this.contestId = contestId;
    }

    public Integer getOrderId()
    {
        return this.orderId;
    }

    public void setOrderId(Integer name)
    {
        this.orderId = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Calendar getEntryDate()
    {
        return entryDate;
    }

    public void setEntryDate(Calendar endDate)
    {
        this.entryDate = endDate;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id).append("\n").append("contestId: ").append(contestId).append("\n").append("onlineId: ")
            .append(onlineId).append("\n").append("orderId: ").append(orderId).append("\n").append("phone: ").append(phone);

        return sb.toString();
    }
}