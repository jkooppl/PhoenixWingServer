package com.pizza73.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Driver.java TODO comment me
 * 
 * @author chris 8-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_driver", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "driver_sequence", sequenceName = "iq2_driver_id_seq", allocationSize = 1)
public class Driver implements Serializable
{

    private static final long serialVersionUID = -8727406597630228803L;

    // Fields
    @Id
    @Column(name = "driver_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_sequence")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "name")
    private String name;

    @Column(name = "car_description")
    private String vehicleDescription;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "address")
    private String address = "";

    @Column(name = "phone_number")
    private String phone = "";

    @Column(name = "insuring_company")
    private String insurer = "";

    @Column(name = "policy_number")
    private String policyNumber = "";

    @Column(name = "expiry_date")
    private Date insExpiryDate;

    @Column(name = "form_signed")
    private boolean liabilitySigned = false;

    @Column(name = "dstatus_id")
    private char status = 'a';

    // Constructors
    /** default constructor */
    public Driver()
    {
    }

    // Property accessors
    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer driverId)
    {
        this.id = driverId;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getInsurer()
    {
        return insurer;
    }

    public void setInsurer(String insurer)
    {
        this.insurer = insurer;
    }

    public String getPolicyNumber()
    {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber)
    {
        this.policyNumber = policyNumber;
    }

    public Date getInsExpiryDate()
    {
        return insExpiryDate;
    }

    public void setInsExpiryDate(Date insExpiryDate)
    {
        this.insExpiryDate = insExpiryDate;
    }

    public boolean isLiabilitySigned()
    {
        return liabilitySigned;
    }

    public void setLiabilitySigned(boolean liabilitySigned)
    {
        this.liabilitySigned = liabilitySigned;
    }

    public char getStatus()
    {
        return status;
    }

    public void setStatus(char status)
    {
        this.status = status;
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

    /**
     * @return the shop
     */
    public Shop getShop()
    {
        return this.shop;
    }

    /**
     * @param shop
     *            the shop to set
     */
    public void setShop(Shop shop)
    {
        this.shop = shop;
    }

    /**
     * @return the vehicleDescription
     */
    public String getVehicleDescription()
    {
        return this.vehicleDescription;
    }

    /**
     * @param vehicleDescription
     *            the vehicleDescription to set
     */
    public void setVehicleDescription(String vehicleDescription)
    {
        this.vehicleDescription = vehicleDescription;
    }

    @Override
    public boolean equals(Object o)
    {
        return super.equals(o);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("nickname", this.nickname)
            .append("vehicleDescription", this.vehicleDescription).append("name", this.name).toString();
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        phone = StringUtils.deleteWhitespace(phone);
        phone = phone.replace("-", "");
        phone = phone.replace("(", "");
        phone = phone.replace(")", "");

        this.phone = phone;
    }
}