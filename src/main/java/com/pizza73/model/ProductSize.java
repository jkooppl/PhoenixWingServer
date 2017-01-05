package com.pizza73.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * ProductSize.java TODO comment me
 * 
 * @author chris 11-Dec-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_product_size", schema = "public", uniqueConstraints = {})
@JsonIgnoreProperties({ "categorySizes", "name" })
public class ProductSize implements Serializable
{

    // FIELDS
    private static final long serialVersionUID = 1938814765333259311L;
    @Id
    @Column(name = "size_id")
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "char(10)")
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "size_id")
    private final Set<ProductSizeCategory> categorySizes = new HashSet<ProductSizeCategory>();

    /**
    * 
    */
    public ProductSize()
    {
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return the id
     */
    public Integer getId()
    {
        return this.id;
    }

    public Set<ProductSizeCategory> getCategorySizes()
    {
        return this.categorySizes;
    }

    public ProductSizeCategory getSizeForCategory(ProductCategory category)
    {
        ProductSizeCategory categorySize = null;

        Iterator<ProductSizeCategory> iter = categorySizes.iterator();
        while (iter.hasNext())
        {
            ProductSizeCategory temp = iter.next();
            if (temp.equals(category))
            {
                categorySize = temp;
                break;
            }
        }

        return categorySize;
    }

    public ProductSizeCategory getSizeForCategory(Integer categoryId)
    {
        ProductSizeCategory categorySize = null;

        Iterator<ProductSizeCategory> iter = categorySizes.iterator();
        while (iter.hasNext())
        {
            ProductSizeCategory temp = iter.next();
            if (temp.getCategory().getId().equals(categoryId))
            {
                categorySize = temp;
                break;
            }
        }

        return categorySize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof ProductSize))
        {
            return false;
        }

        final ProductSize size = (ProductSize) obj;
        if (name != null ? !name.equals(size.getName()) : size.getName() != null)
            return false;

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int result = (name != null ? name.hashCode() : 0);

        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", this.name).toString();
    }
}