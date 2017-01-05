package com.pizza73.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Menu.java TODO comment me
 * 
 * @author chris 12-Oct-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_menu", schema = "public", uniqueConstraints = {})
@JsonIgnoreProperties({ "menuId", "specialPrice", "imageFile", "displayPrice", "dropDownOptionText", "product", "categoryId" })
public class Menu implements Serializable, Comparable<Object>
{

    // FIELDS
    private static final long serialVersionUID = 3049300803066457243L;

    @Id
    private Integer id;

    @Column(name = "menu_id")
    private Integer menuId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "size_id")
    private ProductSize size;

    @Column(name = "menu_sub_id")
    private Integer categoryId;

    @Column(name = "price_base")
    private Integer price;

    @Column(name = "price_special")
    private Integer specialPrice;

    @Column(name = "price_modifier")
    private Integer priceModifier;

    @Column(name = "display_hint")
    private Integer displayHint;

    @Column(name = "description")
    private String description;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "display_name_full")
    private String fullDisplayName;

    @Column(name = "envirolevy")
    private Integer enviroLevy;

    @Column(name = "deposit")
    private Integer deposit;

    @Column(name = "image_filename")
    private String imageFile;

    @Column(name = "new_product")
    private boolean newProduct;

    @Column(name = "display_menu")
    private boolean displayMenu;

    @Column(name = "wings_allowed")
    private boolean wingsAllowed;

    @Column(name = "pizzas_allowed")
    private boolean pizzasAllowed;

    /**
     * Default Constructor
     * 
     */
    public Menu()
    {
    }

    public Integer getId()
    {
        return this.id;
    }

    public Integer getMenuId()
    {
        return this.menuId;
    }

    public Integer getCategoryId()
    {
        return this.categoryId;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public String getFullDisplayName()
    {
        return this.fullDisplayName;
    }

    /**
     * @return the displayHint
     */
    public Integer getDisplayHint()
    {
        return this.displayHint;
    }

    /**
     * @return the price
     */
    public Integer getPrice()
    {
        return this.price;
    }

    /**
     * @return the priceModifier
     */
    public Integer getPriceModifier()
    {
        return this.priceModifier;
    }

    /**
     * @return the product
     */
    public Product getProduct()
    {
        return this.product;
    }

    /**
     * @return the product size
     */
    public ProductSize getSize()
    {
        return this.size;
    }

    /**
     * @return the specialPrice
     */
    public Integer getSpecialPrice()
    {
        return this.specialPrice;
    }

    public Integer getEnviroLevy()
    {
        return this.enviroLevy;
    }

    public Integer getDeposit()
    {
        return this.deposit;
    }

    public String getImageFile()
    {
        return this.imageFile;
    }

    public boolean isNewProduct()
    {
        return this.newProduct;
    }

    public boolean isDisplayMenu()
    {
        return this.displayMenu;
    }

    public String getDisplayPrice()
    {
        BigDecimal cost = BigDecimal.valueOf(this.price + this.priceModifier);
        cost = cost.multiply(BigDecimal.valueOf(0.01));
        NumberFormat format = NumberFormat.getCurrencyInstance();

        return format.format(cost);
    }

    public boolean isWingsAllowed()
    {
        return wingsAllowed;
    }

    public boolean isPizzasAllowed()
    {
        return pizzasAllowed;
    }

    public int compareTo(Object o)
    {
        Menu m = (Menu) o;
        if (this.categoryId.compareTo(m.categoryId) == 0)
        {
            return this.displayHint.compareTo(m.getDisplayHint());
        }
        else
        {
            return this.categoryId.compareTo(m.categoryId);
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Menu))
            return false;

        final Menu menu = (Menu) o;

        if (id != null ? !id.equals(menu.getId()) : menu.getId() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result;
        result = (id != null ? id.hashCode() : 0);

        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("full display name", this.displayName)
            .append("menuId", this.menuId).append("size", this.size).append("product", this.product).toString();
    }

    public String getDropDownOptionText()
    {
        String text = this.getFullDisplayName();
        if (this.price > 0)
        {
            text += " (" + this.getDisplayPrice() + ")";
        }

        return text;
    }
}