package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "iq2_coupon_detail", schema = "public", uniqueConstraints = {})
public class CouponDetail implements Serializable
{

    // FIELDS
    private static final long serialVersionUID = 3157566209325560004L;

    @Id
    @Column(name = "coupon_detail_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "product_category_id")
    private Integer productCategoryId;

    @Column(name = "size_id")
    private Integer sizeId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "discount")
    private Integer discount = Integer.valueOf(0);

    @Column(name = "use_product_id")
    private boolean useProductId = false;

    public CouponDetail()
    {
        super();
    }

    public Integer getProductCategoryId()
    {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId)
    {
        this.productCategoryId = productCategoryId;
    }

    public Integer getSizeId()
    {
        return sizeId;
    }

    public void setSizeId(Integer sizeId)
    {
        this.sizeId = sizeId;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public Integer getDiscount()
    {
        return discount;
    }

    public void setDiscount(Integer discount)
    {
        this.discount = discount;
    }

    public Integer getId()
    {
        return id;
    }

    public Coupon getCoupon()
    {
        return coupon;
    }

    public void setCoupon(Coupon coupon)
    {
        this.coupon = coupon;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", this.id)
            .append("Product Category", this.productCategoryId).append("size", this.sizeId)
            .append("quantity", this.quantity).append("discount", this.discount)
            .append("useProductId", Boolean.valueOf(this.useProductId)).toString();
    }

    public boolean isUseProductId()
    {
        return useProductId;
    }

    public void setUseProductId(boolean useProductId)
    {
        this.useProductId = useProductId;
    }
}