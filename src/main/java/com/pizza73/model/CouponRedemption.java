package com.pizza73.model;

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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 * @author chris
 * 
 */
@Entity
@Table(name = "iq2_coupon_redemption", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "redemption_sequence", sequenceName = "iq2_coupon_redemption_id_seq", allocationSize = 1)
public class CouponRedemption extends BaseObject
{

    // FIELDS
    private static final long serialVersionUID = 1546637369169642480L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "redemption_sequence")
    @Column(name = "coupon_redemption_id")
    private Integer id;

    @Column(name = "coupon_id")
    private Integer couponId;

    @Column(name = "online_id")
    private Integer onlineId;

    @Column(name = "phone_number")
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.INSERT)
    @Column(name = "date_redeemed", insertable = false)
    private Calendar redemptionDate;

    public CouponRedemption()
    {
    }

    public CouponRedemption(Integer cId, String phone)
    {
        this.couponId = cId;
        this.phone = phone;
    }

    public CouponRedemption(Integer cId, OnlineCustomer oc)
    {
        this.couponId = cId;
        this.onlineId = oc.getId();
        this.phone = oc.getPhone();
    }

    public Integer getOnlineId()
    {
        return onlineId;
    }

    public void setOnlineId(Integer onlineId)
    {
        this.onlineId = onlineId;
    }

    public Integer getCouponId()
    {
        return couponId;
    }

    public void setCouponId(Integer couponId)
    {
        this.couponId = couponId;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Calendar getRedemptionDate()
    {
        return redemptionDate;
    }

    public void setRedemptionDate(Calendar redemptionDate)
    {
        this.redemptionDate = redemptionDate;
    }

    public Integer getId()
    {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pizza73.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof CouponRedemption))
            return false;

        final CouponRedemption cr = (CouponRedemption) o;
        if (this.onlineId != null ? !this.onlineId.equals(cr.getOnlineId()) : cr.getOnlineId() != null)
            return false;
        if (this.couponId != null ? !this.couponId.equals(cr.getCouponId()) : cr.getCouponId() != null)
            return false;
        if (this.phone != null ? !this.phone.equals(cr.getPhone()) : cr.getPhone() != null)
            return false;
        if (this.redemptionDate != null ? !this.redemptionDate.equals(cr.getRedemptionDate())
            : cr.getRedemptionDate() != null)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pizza73.model.BaseObject#hashCode()
     */
    @Override
    public int hashCode()
    {
        int result;
        result = (this.onlineId != null ? this.onlineId.hashCode() : 0);
        result = (this.couponId != null ? this.couponId.hashCode() : 0);
        result = (this.phone != null ? this.phone.hashCode() : 0);
        result = (this.redemptionDate != null ? this.redemptionDate.hashCode() : 0);

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pizza73.model.BaseObject#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", this.id)
            .append("phone number", this.phone).toString();
    }
}