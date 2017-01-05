package com.pizza73.model.conversion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "sales_detail", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "sales_detail_sequence", sequenceName = "sales_detail_id_seq", allocationSize = 1)
public class SalesDetail implements Serializable
{

    // FIELDS
    private static final long serialVersionUID = 1671180337670145910L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_detail_sequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "quantity")
    private int quantity = 1;

    @Column(name = "price")
    private BigDecimal price = BigDecimal.ZERO;

    // size
    @Column(name = "product_modifier_1")
    private int modifierOne = 0;

    // crust type for pizza
    @Column(name = "product_modifier_2")
    private int modifierTwo = 0;

    // sauce type for pizza
    @Column(name = "product_modifier_3")
    private int modifierThree = 0;

    @Column(name = "product_modifier_4")
    private int modifierFour = 0;

    // pizza toppings - see ToppingMappingEnum
    @Column(name = "product_addons")
    private String addOns = "";

    @Column(name = "product_info")
    private String info = "";

    @Transient
    private List<SalesDetailComposition> dipDetailCompositions = new ArrayList<SalesDetailComposition>();
    @Transient
    private List<SalesDetailComposition> wingDetailCompositions = new ArrayList<SalesDetailComposition>();

    public SalesDetail()
    {
    }

    /**
     * @param orderId2
     * @param i
     */
    public SalesDetail(Integer orderId, int id)
    {
        this.orderId = orderId;
        this.productId = id;
    }

    public Integer getId()
    {
        return this.id;
    }

    /**
     * @return the addOns
     */
    public String getAddOns()
    {
        return this.addOns;
    }

    /**
     * @param addOns
     *            the addOns to set
     */
    public void setAddOns(String addOns)
    {
        this.addOns = addOns;
    }

    /**
     * @return the info
     */
    public String getInfo()
    {
        return this.info;
    }

    /**
     * @param info
     *            the info to set
     */
    public void setInfo(String info)
    {
        this.info = info;
    }

    /**
     * @return the modifierFour
     */
    public int getModifierFour()
    {
        return this.modifierFour;
    }

    /**
     * @param modifierFour
     *            the modifierFour to set
     */
    public void setModifierFour(int modifierFour)
    {
        this.modifierFour = modifierFour;
    }

    /**
     * @return the modifierOne
     */
    public int getModifierOne()
    {
        return this.modifierOne;
    }

    /**
     * @param modifierOne
     *            the modifierOne to set
     */
    public void setModifierOne(int modifierOne)
    {
        this.modifierOne = modifierOne;
    }

    /**
     * @return the modifierThree
     */
    public int getModifierThree()
    {
        return this.modifierThree;
    }

    /**
     * @param modifierThree
     *            the modifierThree to set
     */
    public void setModifierThree(int modifierThree)
    {
        this.modifierThree = modifierThree;
    }

    /**
     * @return the modifierTwo
     */
    public int getModifierTwo()
    {
        return this.modifierTwo;
    }

    /**
     * @param modifierTwo
     *            the modifierTwo to set
     */
    public void setModifierTwo(int modifierTwo)
    {
        this.modifierTwo = modifierTwo;
    }

    /**
     * @return the orderId
     */
    public Integer getOrderId()
    {
        return this.orderId;
    }

    /**
     * @param orderId
     *            the orderId to set
     */
    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice()
    {
        return this.price;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(BigDecimal price)
    {
        this.price = price;
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

    public List<SalesDetailComposition> getDipDetailComposition()
    {
        return this.dipDetailCompositions;
    }

    public void setDipDetailComposition(List<SalesDetailComposition> dipCompositions)
    {
        this.dipDetailCompositions = dipCompositions;
    }

    public void addDipDetailComposition(SalesDetailComposition dipComposition)
    {
        this.dipDetailCompositions.add(dipComposition);
    }

    public void addAllDipDetailCompositions(List<SalesDetailComposition> dipCompositions)
    {
        this.dipDetailCompositions.addAll(dipCompositions);
    }

    public List<SalesDetailComposition> getWingDetailComposition()
    {
        return this.wingDetailCompositions;
    }

    public void setWingDetailComposition(List<SalesDetailComposition> wingCompositions)
    {
        this.wingDetailCompositions = wingCompositions;
    }

    public void addWingDetailComposition(SalesDetailComposition wingComposition)
    {
        this.wingDetailCompositions.add(wingComposition);
    }

    public void addAllWingDetailCompositions(List<SalesDetailComposition> wingCompositions)
    {
        this.wingDetailCompositions.addAll(wingCompositions);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("orderId", this.orderId)
            .append("productId", this.productId).append("quantity", this.quantity).append("price", this.price)
            .append("modifierOne", this.modifierOne).append("modifierTwo", this.modifierTwo)
            .append("modifierThree", this.modifierThree).append("modifierFour", this.modifierFour)
            .append("addOns", this.addOns).append("info", this.info).toString();
    }
}
