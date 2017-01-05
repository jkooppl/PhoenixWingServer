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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

/**
 * Product.java TODO comment me
 * 
 * @author chris 8-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_product", schema = "public", uniqueConstraints = {})
@FilterDef(name = "active", parameters = { @ParamDef(name = "isActive", type = "boolean") })
@JsonIgnoreProperties({ "name", "fullDescription", "printableName", "reportDescription", "specialInstructionsAllowed",
        "details", "compositions", "category", "properties", "reportId" })
public class Product implements Serializable
{
    private static final long serialVersionUID = -5876756485026988130L;

    // Fields
    @Id
    @Column(name = "product_id")
    private Integer id = null;

    @Column(name = "name", columnDefinition = "char(10)")
    private String name;

    @Column(name = "description_full", length = 250)
    private String fullDescription;

    @Column(name = "description_postscript", length = 128)
    private String printableName;

    @Column(name = "description_report", length = 40)
    private String reportDescription;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "instruction_line", nullable = false)
    private Boolean specialInstructionsAllowed;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Set<ProductDetail> details = new HashSet<ProductDetail>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @OrderBy("numMax asc")
    private Set<ProductComposition> compositions = new HashSet<ProductComposition>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Set<ProductProperty> properties = new HashSet<ProductProperty>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @Column(name = "report_id", nullable = false)
    private Integer reportId;

    // Constructors
    /** default constructor */
    public Product()
    {
    }

    public Product(Integer id)
    {
        this.id = id;
    }

    // Property accessors
    /**
     * Getter and setter for Id
     */
    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * Getter for active state. A product may only be sold for a limited period
     * of time. This allows for easily changing the product availability.
     * 
     * @return the active
     */
    public Boolean isActive()
    {
        return this.active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    /**
     * Getter for Description
     * 
     * @return the fullDescription
     */
    public String getFullDescription()
    {
        return this.fullDescription;
    }

    /**
     * Getter for the name
     * 
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return the printableName
     */
    public String getPrintableName()
    {
        return this.printableName;
    }

    /**
     * @return the reportDescription
     */
    public String getReportDescription()
    {
        return this.reportDescription;
    }

    /**
     * @return the specialInstructionsAllowed
     */
    public Boolean getSpecialInstructionsAllowed()
    {
        return this.specialInstructionsAllowed;
    }

    public Set<ProductProperty> getProperties()
    {
        return this.properties;
    }

    protected void setProperties(Set<ProductProperty> value)
    {
        this.properties = value;
    }

    public Set<ProductDetail> getDetails()
    {
        return this.details;
    }

    protected void setDetails(Set<ProductDetail> value)
    {
        this.details = value;
    }

    public Set<ProductComposition> getCompositions()
    {
        return this.compositions;
    }

    protected void setCompositions(Set<ProductComposition> value)
    {
        this.compositions = value;
    }

    public ProductCategory getCategory()
    {
        return this.category;
    }

    public Integer getReportId()
    {
        return this.reportId;
    }

    protected void setReportId(Integer reportId)
    {
        this.reportId = reportId;
    }

    // BUSINESS METHODS
    public String propertyValueForName(String name)
    {
        String value = "";

        Iterator<ProductProperty> iter = properties.iterator();
        ProductProperty prop = null;
        while (iter.hasNext())
        {
            prop = iter.next();
            if (prop.getName().equals(name))
            {
                value = prop.getValue();
                break;
            }
        }

        return value;
    }

    public Set<ProductComposition> compositionsForSize(ProductSize size)
    {
        Set<ProductComposition> compositionsForSize = new HashSet<ProductComposition>();
        Iterator<ProductComposition> iter = this.compositions.iterator();
        ProductComposition temp = null;
        while (iter.hasNext())
        {
            temp = iter.next();
            if (temp.getSize().getId().equals(size.getId()))
            {
                compositionsForSize.add(temp);
            }
        }

        return compositionsForSize;
    }

    public Set<ProductComposition> compositionsForSize(Integer sizeId)
    {
        Set<ProductComposition> compositionsForSize = new HashSet<ProductComposition>();
        Iterator<ProductComposition> iter = this.compositions.iterator();
        ProductComposition temp = null;
        while (iter.hasNext())
        {
            temp = iter.next();
            if (temp.getSize().getId().equals(sizeId))
            {
                compositionsForSize.add(temp);
            }
        }

        return compositionsForSize;
    }

    /**
     * @param size
     * @param category
     * @return
     */
    public ProductComposition compositionsForSize(ProductSize size, ProductCategory category)
    {
        ProductComposition compositionForSize = null;
        for (ProductComposition productComp : this.compositions)
        {
            Integer sizeId = productComp.getSize().getId();
            if (sizeId.equals(size.getId())
                && productComp.getSubProperty().equals(category.getName()))
            {
                compositionForSize = productComp;
                break;
            }
        }

        return compositionForSize;
    }

    public ProductComposition compositionsForSize(Integer sizeId, String categoryName)
    {
        ProductComposition compositionForSize = null;
        Iterator<ProductComposition> iter = this.compositions.iterator();
        ProductComposition temp = null;
        while (iter.hasNext())
        {
            temp = iter.next();
            if (temp.getSize().getId().equals(sizeId) && temp.getSubProperty().equals(categoryName))
            {
                compositionForSize = temp;
                break;
            }
        }

        return compositionForSize;
    }

    public Integer categoryId()
    {
        return this.category.getId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;

        final Product p = (Product) o;

        // if (category != null ? !category.equals(p.getCategory()) :
        // p.getCategory() != null) return false;
        if (name != null ? !name.equals(p.getName()) : p.getName() != null)
            return false;
        if (fullDescription != null ? !fullDescription.equals(p.getFullDescription()) : p.getFullDescription() != null)
            return false;
        if (printableName != null ? !printableName.equals(p.getPrintableName()) : p.getPrintableName() != null)
            return false;
        if (reportDescription != null ? !reportDescription.equals(p.getReportDescription())
            : p.getReportDescription() != null)
            return false;
        if (active != p.isActive())
            return false;
        if (specialInstructionsAllowed != p.getSpecialInstructionsAllowed())
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
        int result = 27;
        // result = 29 * result + (category != null ? category.hashCode() : 0);
        result = 29 * result + (name != null ? name.hashCode() : 0);
        result = 29 * result + (fullDescription != null ? fullDescription.hashCode() : 0);
        result = 29 * result + (printableName != null ? printableName.hashCode() : 0);
        result = 29 * result + (reportDescription != null ? reportDescription.hashCode() : 0);
        result = 29 * result + (active != null ? active.hashCode() : 0);
        result = 29 * result + (specialInstructionsAllowed != null ? specialInstructionsAllowed.hashCode() : 0);

        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", this.name)
            .append("active", this.active).append("category", this.category.toString()).toString();
    }

    // public String toJson() {
    // return new JSONSerializer().exclude("*.class").serialize(this);
    // }
    //
    // public static Product fromJsonToProduct(String json) {
    // return new JSONDeserializer<Account>().use(null,
    // Product.class).deserialize(json);
    // }
    //
    // public static String toJsonArray(Collection<Product> collection) {
    // return new JSONSerializer().exclude("*.class").serialize(collection);
    // }
    //
    // public static Collection<Product> fromJsonArrayToProducts(String json) {
    // return new JSONDeserializer<List<Product>>().use(null,
    // ArrayList.class).use("values", Product.class).deserialize(json);
    // }
    // JSON
}