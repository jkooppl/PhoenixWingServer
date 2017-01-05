package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * ProductSizeCategory.java TODO comment me
 * 
 * @author chris 11-Dec-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_product_size_category", schema = "public", uniqueConstraints = {})
public class ProductSizeCategory implements Serializable
{
    // FIELDS
    private static final long serialVersionUID = 5562685268460513296L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "char(10)")
    private String name;

    @Column(name = "description_postscript", nullable = false, columnDefinition = "char(40)")
    private String postscriptDescription;

    @Column(name = "description_full")
    private String fullDescription;

    @Column(name = "display_hint")
    private Integer displayHint;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    /**
     * Default Constructor
     */
    public ProductSizeCategory()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the displayHint
     */
    public Integer getDisplayHint()
    {
        return this.displayHint;
    }

    /**
     * @return the fullDescription
     */
    public String getFullDescription()
    {
        return this.fullDescription;
    }

    /**
     * @return the fullDescription
     */
    public String getPostscriptDescription()
    {
        return this.postscriptDescription;
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

    public ProductCategory getCategory()
    {
        return this.category;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int result = super.hashCode();

        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", this.name).toString();
    }
}