package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * ProductCategoryEnum.java TODO comment me
 * 
 * @author chris 8-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_product_category", schema = "public", uniqueConstraints = {})
public class ProductCategory implements Serializable
{

    private static final long serialVersionUID = 3959550919365706265L;

    // Fields
    @Id
    @Column(name = "category_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "display_order")
    private int displayOrder;

    @Column(name = "display_hint")
    private int displayHint;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "top_level")
    private boolean topLevel;

    @Column(name = "parent_category_id")
    private Integer parentId;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "parent_id")
    // private ProductCategory parent;

    // Constructors
    /** default constructor */
    public ProductCategory()
    {
    }

    // Property accessors
    public Integer getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public int getDisplayOrder()
    {
        return this.displayOrder;
    }

    public int getDisplayHint()
    {
        return this.displayHint;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public boolean isTopLevel()
    {
        return this.topLevel;
    }

    public Integer getParentId()
    {
        return this.parentId;
    }

    // public ProductCategory getParent()
    // {
    // return this.parent;
    // }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof ProductCategory))
            return false;

        final ProductCategory pc = (ProductCategory) o;

        if (name != null ? !name.equals(pc.getName()) : pc.getName() != null)
            return false;
        if (displayName != null ? !displayName.equals(pc.getDisplayName()) : pc.getDisplayName() != null)
            return false;
        if (displayHint != pc.getDisplayHint())
            return false;
        if (displayOrder != pc.getDisplayOrder())
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result;
        result = (name != null ? name.hashCode() : 0);
        result = 29 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 29 * result + displayHint;
        result = 29 * result + displayOrder;
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", this.displayName).toString();
    }
}
