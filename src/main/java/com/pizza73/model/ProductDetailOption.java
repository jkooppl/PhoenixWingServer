package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * ProductDetail.java TODO comment me
 * 
 * @author chris 11-Dec-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_product_detail_option", schema = "public", uniqueConstraints = {})
public class ProductDetailOption implements Serializable
{
    // FIELDS
    private static final long serialVersionUID = -7964921795345211921L;

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_detail_id")
    private Integer productDetailId;

    @Column(name = "special_id")
    private Integer specialId;

    @Column(name = "size_id")
    private Integer sizeId;

    /**
     * Default Constructor
     */
    public ProductDetailOption()
    {
    }

    public Integer getId()
    {
        return this.id;
    }

    public Product getProduct()
    {
        return this.product;
    }

    public Integer getSpecialId()
    {
        return this.specialId;
    }

    public Integer getSizeId()
    {
        return this.sizeId;
    }

    public Integer getProductDetailId()
    {
        return this.productDetailId;
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();

        return result;
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}