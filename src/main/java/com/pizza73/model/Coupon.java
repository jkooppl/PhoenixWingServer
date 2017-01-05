/**
 * 
 */
package com.pizza73.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Type;

/**
 * @author chris
 * 
 */

@Entity
@Table(name = "iq2_coupon", schema = "public", uniqueConstraints = {})
public class Coupon implements Serializable
{
    // FIELDS
    private static final long serialVersionUID = -6454510584648925258L;

    @Id
    @Column(name = "coupon_id")
    private Integer id;

    @Type(type = "trim")
    @Column(name = "coupon_code")
    private String code = "";

    @Column(name = "description")
    private String description = "";

    @Column(name = "name")
    private String name = "";

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Calendar startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Calendar endDate;

    @Column(name = "municipality_id")
    private String city = "0";

    @Column(name = "discount")
    private Integer discount = Integer.valueOf(0);

    @Column(name = "error_message")
    private String errorMessage = "";

    @Column(name = "online_redeemable")
    private boolean onlineRedeemable = true;

    @OneToMany(mappedBy = "coupon", fetch = FetchType.EAGER)
    private List<CouponDetail> details;

    @Column(name = "product_requirement")
    private Integer requiredProductId;

    @Column(name = "size_requirement")
    private Integer requiredSizeId;

    // determines whether productRequirement is based on category id or product
    // id.
    @Column(name = "use_product")
    private boolean useProduct;

    @Transient
    private Integer calculatedDiscount = Integer.valueOf(0);

    public Coupon()
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

    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Calendar getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Calendar startDate)
    {
        this.startDate = startDate;
    }

    public Calendar getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Calendar endDate)
    {
        this.endDate = endDate;
    }

    public Integer getDiscount()
    {
        return this.discount;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public void setDiscount(Integer discount)
    {
        this.discount = discount;
    }

    public List<CouponDetail> getDetails()
    {
        return details;
    }

    public void setDetails(List<CouponDetail> details)
    {
        this.details = details;
    }

    public Integer getCalculatedDiscount()
    {
        return this.calculatedDiscount;
    }

    public void setCalculatedDiscount(Integer cDiscount)
    {
        this.calculatedDiscount = cDiscount;
    }

    public Integer getRequiredProductId()
    {
        return requiredProductId;
    }

    public void setRequiredProductId(Integer requiredProductId)
    {
        this.requiredProductId = requiredProductId;
    }

    public Integer getRequiredSizeId()
    {
        return requiredSizeId;
    }

    public void setRequiredSizeId(Integer requiredSizeId)
    {
        this.requiredSizeId = requiredSizeId;
    }

    public boolean isUseProduct()
    {
        return this.useProduct;
    }

    public void calculateDiscount()
    {
        if (this.details.isEmpty())
        {
            this.calculatedDiscount = this.discount;
        }
        else
        {
            this.calculatedDiscount = 0;
            Iterator<CouponDetail> iter = this.details.iterator();
            CouponDetail detail = null;
            while (iter.hasNext())
            {
                detail = iter.next();
                this.calculatedDiscount += detail.getQuantity() * detail.getDiscount();
            }
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", this.id)
            .append("description", this.description).append("start date", this.startDate).append("end date", this.endDate)
            .toString();
    }

    public boolean isOnlineRedeemable()
    {
        return onlineRedeemable;
    }

    public void setOnlineRedeemable(boolean onlineRedeemable)
    {
        this.onlineRedeemable = onlineRedeemable;
    }
}