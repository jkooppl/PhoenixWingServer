package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * ProductComposition.java TODO comment me
 * 
 * @author chris 5-Jan-07
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_product_composition", schema = "public", uniqueConstraints = {})
@JsonIgnoreProperties({ "product" })
public class ProductComposition implements Serializable
{

    // FIELDS
    private static final long serialVersionUID = -4507954147035254165L;

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "size_id")
    private ProductSize size;

    @Column(name = "sub_property")
    private String subProperty;

    @Column(name = "size_inherited")
    private boolean sizeInherited = false;

    @Column(name = "num_free", nullable = false)
    private int numFree = 0;

    @Column(name = "num_max", nullable = false)
    private int numMax = 0;

    @Column(name = "num_min", nullable = false)
    private int numMin = 0;

    public ProductComposition()
    {
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    /**
     * @return the id
     */
    public Integer getId()
    {
        return this.id;
    }

    /**
     * @return the numFree
     */
    public int getNumFree()
    {
        return this.numFree;
    }

    protected void setNumFree(int num)
    {
        this.numFree = num;
    }

    /**
     * @return the numMax
     */
    public int getNumMax()
    {
        return this.numMax;
    }

    protected void setNumMax(int num)
    {
        this.numMax = num;
    }

    /**
     * @return the numMin
     */
    public int getNumMin()
    {
        return this.numMin;
    }

    protected void setNumMin(int num)
    {
        this.numMin = num;
    }

    /**
     * @return the product
     */
    public Product getProduct()
    {
        return this.product;
    }

    /**
     * @return the sizeInherited
     */
    public boolean isSizeInherited()
    {
        return this.sizeInherited;
    }

    protected void setSizeInherited(boolean size)
    {
        this.sizeInherited = size;
    }

    /**
     * @return the size
     */
    public ProductSize getSize()
    {
        return this.size;
    }

    /**
     * @return the subProperty
     */
    public String getSubProperty()
    {
        return this.subProperty;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof ProductComposition))
            return false;

        final ProductComposition pc = (ProductComposition) o;

        // if (category != null ? !category.equals(p.getCategory()) :
        // p.getCategory() != null) return false;
        if (id != null ? !id.equals(pc.getId()) : pc.getId() != null)
            return false;
        if (numFree != pc.getNumFree())
            return false;
        if (numMin != pc.getNumMin())
            return false;
        if (numMax != pc.getNumMax())
            return false;
        if (subProperty != null ? !subProperty.equals(pc.getSubProperty()) : pc.getSubProperty() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = 27;
        result = 29 * result + (id != null ? id.hashCode() : 0);
        result = 29 * result + (subProperty != null ? subProperty.hashCode() : 0);
        result = 29 * result + numFree;
        result = 29 * result + numMax;
        result = 29 * result + numMin;

        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("sub property", this.subProperty)
            .append("size", this.size).append("numFree", this.numFree).toString();
    }
}
