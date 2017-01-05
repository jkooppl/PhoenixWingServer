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
@Table(name = "iq2_product_detail", schema = "public", uniqueConstraints = {})
public class ProductDetail implements Serializable
{
    // FIELDS
    private static final long serialVersionUID = -7964921795345211921L;

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "sub_product_id")
    private Product subProduct;

    @OneToOne
    @JoinColumn(name = "sub_product_size_id")
    private ProductSize subProductSize;

    @Column(name = "quantity", nullable = false)
    private int quantity = 0;

    // @OneToMany(fetch = FetchType.EAGER)
    // @JoinColumn(name = "product_detail_id")
    // private Set<ProductDetailOption> options = new
    // HashSet<ProductDetailOption>();

    /**
     * Default Constructor
     */
    public ProductDetail()
    {
    }

    public Integer getId()
    {
        return this.id;
    }

    /**
     * @return the quantity
     */
    public int getQuantity()
    {
        return this.quantity;
    }

    protected void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public Product getSubProduct()
    {
        return this.subProduct;
    }

    /**
     * @return the subProductSize
     */
    public ProductSize getSubProductSize()
    {
        return this.subProductSize;
    }

    // public Set<ProductDetailOption> getOptions() {
    // return options;
    // }
    //
    // public void setOptions(Set<ProductDetailOption> options) {
    // this.options = options;
    // }

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