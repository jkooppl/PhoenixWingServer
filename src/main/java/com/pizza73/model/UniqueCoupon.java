/**
 * 
 */
package com.pizza73.model;

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

import org.hibernate.annotations.Type;

/**
 * @author chris
 * 
 */

@Entity
@Table(name = "unique_coupon_list", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "unique_coupon_seq", sequenceName = "unique_coupon_list_id_seq", allocationSize = 1)
public class UniqueCoupon implements Serializable
{
    private static final long serialVersionUID = -6953715508039558254L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unique_coupon_seq")
    private Integer id;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "coupon_type_id")
    private Integer couponType;

    @Type(type = "trim")
    @Column(name = "coupon_code")
    private String code = "";

    @Column(name = "email")
    private String email = "";

    @Column(name = "redeemed_order_id")
    private Integer redeemedOrderId;

    @Temporal(TemporalType.DATE)
    @Column(name = "redemption_date")
    private Calendar redemptionDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Calendar createDate = Calendar.getInstance();

    public Integer getRedeemedOrderId()
    {
        return redeemedOrderId;
    }

    public void setRedeemedOrderId(Integer redeemedOrderId)
    {
        this.redeemedOrderId = redeemedOrderId;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getCouponType()
    {
        return couponType;
    }

    public void setCouponType(Integer couponType)
    {
        this.couponType = couponType;
    }

    public void setRedemptionDate(Calendar date)
    {
        this.redemptionDate = date;
    }

    public Calendar getRedemptionDate()
    {
        return this.redemptionDate;
    }

    public Calendar getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Calendar createDate)
    {
        this.createDate = createDate;
    }
}