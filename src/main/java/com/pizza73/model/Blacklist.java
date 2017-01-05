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
 * Blacklist.java
 * 
 * @author chris Jun3 6 2010
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_blacklist", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "blacklist_sequence", sequenceName = "iq2_blacklist_id_seq", allocationSize = 1)
public class Blacklist implements Serializable
{
    private static final long serialVersionUID = 4045439780318598291L;
    // FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blacklist_sequence")
    @Column(name = "id")
    private Integer id;
    @Column(name = "phone_number")
    private String phoneNumber;

    public Blacklist()
    {
    }

    public Blacklist(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
}