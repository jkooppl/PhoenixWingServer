package com.pizza73.model.conversion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * SalesDetail.java TODO comment me
 * 
 * @author chris 22-Feb-07
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "sales_detail_composition", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "sales_detail_comp_sequence", sequenceName = "sales_detail_composition_id_seq", allocationSize = 1)
public class SalesDetailComposition implements Serializable
{

    private static final long serialVersionUID = -6101875059800122611L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_detail_comp_sequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "sales_detail_id")
    private Integer salesDetailId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "quantity")
    private int quantity = 1;

    @Column(name = "composition_type")
    private int type = 1;

    public SalesDetailComposition()
    {
    }

    /**
     * @param orderId2
     * @param i
     */
    public SalesDetailComposition(Integer salesDetailId, int id)
    {
        this.salesDetailId = salesDetailId;
        this.productId = id;
    }

    public SalesDetailComposition(Integer productId, int quantity, Integer type)
    {
        this.productId = productId;
        this.quantity = quantity;
        this.type = type;
    }

    /**
     * @return the orderId
     */
    public Integer getSalesDetailId()
    {
        return this.salesDetailId;
    }

    /**
     * @param orderId
     *            the orderId to set
     */
    public void setSalesDetailId(Integer salesDetailId)
    {
        this.salesDetailId = salesDetailId;
    }

    /**
     * @return the productId
     */
    public Integer getProductId()
    {
        return this.productId;
    }

    /**
     * @param productId
     *            the productId to set
     */
    public void setProductId(Integer productId)
    {
        this.productId = productId;
    }

    /**
     * @return the quantity
     */
    public int getQuantity()
    {
        return this.quantity;
    }

    /**
     * @param quantity
     *            the quantity to set
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public Integer getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("salesDetailId", this.salesDetailId)
            .append("productId", this.productId).append("quantity", this.quantity).append("type", this.type).toString();
    }
}
