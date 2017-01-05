package com.pizza73.model;

import com.pizza73.model.enums.ProductCategoryEnum;
import com.pizza73.model.enums.ProductEnum;
import com.pizza73.model.enums.SpecialEnum;
import com.pizza73.util.ProductUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.pizza73.model.enums.ProductCategoryEnum.CRUST;
import static com.pizza73.model.enums.ProductCategoryEnum.GLUTEN_FREE_PIZZA;
import static com.pizza73.model.enums.ProductCategoryEnum.PIZZA;
import static com.pizza73.model.enums.ProductCategoryEnum.SAUCE;

/**
 * OrderItem.java TODO comment me
 *
 * @author chris 8-Sep-06
 *
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_sales_order_detail", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "order_detail_sequence", sequenceName = "iq2_sales_order_detail_id_seq", allocationSize = 1)
public class OrderItem implements Serializable, Comparable<OrderItem>
{

    private static final long serialVersionUID = -2033469067612466530L;

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_detail_sequence")
    @Column(name = "id")
    private Integer id = null;

    @Column(name = "parent_id")
    private Integer parentId = new Integer(0);

    @Transient
    private OrderItem parent;

    @Column(name = "order_id")
    private Integer orderId;

    @Transient
    private List<OrderItem> children = new ArrayList<OrderItem>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "size_id", nullable = true)
    private ProductSize size;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    private Integer price = Integer.valueOf(0);

    @Transient
    private Integer priceModifier = Integer.valueOf(0);

    @Transient
    private String displayName;

    @Transient
    private Integer enviroLevy;

    @Transient
    private Integer deposit;

    @Column(name = "item_id")
    private int itemId;

    @Transient
    private boolean bundle = false;

    @Transient
    private boolean completeBundle = false;

    @Transient
    private boolean maxEdd = false;

    @Transient
    private int sequenceId;

    @Transient
    private boolean drawTitle = true;

    @Transient
    private boolean noMoreDips = false;

    @Transient
    private Integer menuId = 0;

    @Transient
    private boolean wingsAllowed;

    @Transient
    private boolean pizzasAllowed;

    // needed for mobile app.
    @Transient
    private Integer productId;

    @Transient
    private Integer sizeId;

    /** default constructor */
    public OrderItem()
    {
    }

    public OrderItem(Product product, OrderItem parent, Integer menuId, int tempId)
    {
        this(product, menuId, tempId);
        this.setParent(parent);

    }

    public OrderItem(Product product, Integer menuId, int tempId)
    {
        this.product = product;
        this.menuId = menuId;
        this.itemId = tempId;
    }

    // Property accessors
    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public int getItemId()
    {
        return itemId;
    }

    public Product getProduct()
    {
        return this.product;
    }

    public void setProduct(Product products)
    {
        this.product = products;
    }

    public boolean isBundle()
    {
        return this.bundle;
    }

    public void setBundle(boolean bundle)
    {
        this.bundle = bundle;
    }

    public boolean isCompleteBundle()
    {
        return this.completeBundle;
    }

    public void setCompleteBundle(boolean complete)
    {
        this.completeBundle = complete;
    }

    public boolean isMaxEdd()
    {
        return this.maxEdd;
    }

    public void setMaxEdd(boolean maxEdd)
    {
        this.maxEdd = maxEdd;
    }

    // Business Methods
    public String getWebDisplayString()
    {
        String s = "";
        if (this.size != null)
        {
            s = this.size.getName();
        }
        return s;
    }

    public String getShopDisplayString()
    {
        String s = "";
        if (this.size != null)
        {
            s = this.size.getName();
        }
        return s;
    }

    public ProductSize getSize()
    {
        return this.size;
    }

    public void setSize(ProductSize sizeId)
    {
        this.size = sizeId;
    }

    public OrderItem getParent()
    {
        return this.parent;
    }

    public void setParent(OrderItem parent)
    {
        this.parent = parent;
        if (parent != null)
        {
            this.parentId = parent.getItemId();
        }
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public void setDisplayName(String name)
    {
        this.displayName = name;
    }

    public Integer getPrice()
    {
        return this.price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    public Integer getPriceModifier()
    {
        return this.priceModifier;
    }

    public void setPriceModifier(Integer price)
    {
        this.priceModifier = price;
    }

    public int getSequenceId()
    {
        return this.sequenceId;
    }

    public void setSequenceId(int sequenceId)
    {
        this.sequenceId = sequenceId;
    }

    // Business Method
    public String getSizeShortValue()
    {
        String s = "";
        if (this.size != null)
        {
            return this.size.getName();
        }

        return s;
    }

    public Integer getEnviroLevy()
    {
        return this.enviroLevy;
    }

    public void setEnviroLevy(Integer levy)
    {
        this.enviroLevy = levy;
    }

    public Integer getDeposit()
    {
        return this.deposit;
    }

    public void setDeposit(Integer deposit)
    {
        this.deposit = deposit;
    }

    public boolean isWingsAllowed()
    {
        return wingsAllowed;
    }

    public void setWingsAllowed(boolean wingsAllowed)
    {
        this.wingsAllowed = wingsAllowed;
    }

    public boolean isPizzasAllowed()
    {
        return pizzasAllowed;
    }

    public void setPizzasAllowed(boolean pizzasAllowed)
    {
        this.pizzasAllowed = pizzasAllowed;
    }

    public Integer getMenuId()
    {
        return this.menuId;
    }

    public void setMenuId(Integer menuId)
    {
        this.menuId = menuId;
    }

    /**
     * @return the children
     */
    public List<OrderItem> getChildren()
    {
        return this.children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(List<OrderItem> children)
    {
        this.children = children;
    }

    public void addChild(OrderItem child)
    {
        children.add(child);
        child.setParent(this);
    }

    public OrderItem getChild(String childId)
    {
        OrderItem item = null;
        if (StringUtils.isNotBlank(childId))
        {
            Iterator<OrderItem> iter = children.iterator();
            Integer pId = Integer.valueOf(childId);
            while (iter.hasNext())
            {
                OrderItem temp = iter.next();
                Integer itemId = temp.getId();
                if (itemId == null)
                {
                    itemId = temp.getItemId();
                }
                if (itemId.equals(pId))
                {
                    item = temp;
                    break;
                }
            }
        }

        return item;
    }

    public OrderItem getChildForProductId(Integer productId)
    {
        OrderItem item = null;

        if (null != productId)
        {

            for (OrderItem child : children)
            {
                if (child.getProduct().getId().equals(productId))
                {
                    item = child;
                    break;
                }
            }
        }

        return item;
    }

    public void removeChild(String productId)
    {
        if(null != productId)
        {
            children.remove(getChildForProductId(Integer.valueOf(productId)));
        }
    }

    public void removeChild(Integer productId)
    {
        if(null != productId)
        {
            children.remove(getChildForProductId(productId));
        }
    }

    public boolean removeChild(OrderItem child)
    {
        return children.remove(child);
    }

    /**
     * @param tempId2
     */
    public void setItemId(int tempId)
    {
        this.itemId = tempId;
    }

    /**
     * @return the drawTitle
     */
    public boolean isDrawTitle()
    {
        return this.drawTitle;
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
        if (!this.getChildren().isEmpty())
        {
            Iterator<OrderItem> iter = this.getChildren().iterator();
            while (iter.hasNext())
            {
                setChildOrder(iter.next(), orderId);
            }
        }
    }

    /**
     * @param order2
     */
    private void setChildOrder(OrderItem item, Integer orderId)
    {
        item.setOrderId(orderId);
        if (!item.getChildren().isEmpty())
        {
            Iterator<OrderItem> iter = item.getChildren().iterator();
            while (iter.hasNext())
            {
                setChildOrder(iter.next(), orderId);
            }
        }

    }

    public Integer getChildCount()
    {
        if (children == null || children.isEmpty())
        {
            return 0;
        }
        return this.children.size();
    }

    public OrderItem getCrust()
    {
        OrderItem crust = null;
        Integer categoryId = this.product.getCategory().getId();
        if (PIZZA.isEqualToCategoryId(categoryId) || GLUTEN_FREE_PIZZA.isEqualToCategoryId(categoryId))
        {
            Iterator<OrderItem> iter = this.children.iterator();
            while (iter.hasNext())
            {
                OrderItem child = iter.next();
                Integer childCategoryId = child.getProduct().getCategory().getId();
                if (CRUST.isEqualToCategoryId(childCategoryId))
                {
                    crust = child;
                    break;
                }
            }
        }

        return crust;
    }

    public OrderItem getSauce()
    {
        OrderItem sauce = null;
        Integer categoryId = this.product.getCategory().getId();
        if (PIZZA.isEqualToCategoryId(categoryId) || GLUTEN_FREE_PIZZA.isEqualToCategoryId(categoryId))
        {
            Iterator<OrderItem> iter = this.children.iterator();
            while (iter.hasNext())
            {
                OrderItem child = iter.next();
                Integer childCategoryId = child.getProduct().getCategory().getId();
                if (SAUCE.isEqualToCategoryId(childCategoryId))
                {
                    sauce = child;
                    break;
                }
            }
        }

        return sauce;
    }

    public Integer getFreeToppings()
    {
        Integer freeToppings = 0;
        final Integer prodId = this.product.getId();
        Integer categoryId = this.product.getCategory().getId();
        if (categoryId.equals(ProductCategoryEnum.PIZZA.getId())
            || ProductEnum.GLUTEN_FREE_TWO_TOPPER.isEqualToProductId(prodId))
        {
            int sizeId = 1;
            boolean isMediumSinglePizza = ProductUtil.isSingleMediumPizza(prodId);
            if (ProductEnum.SINGLE_DONAIR.isEqualToProductId(prodId))
            {
                sizeId = 3;
            }
            else if (isMediumSinglePizza)
            {
                sizeId = 2;
            }
            else if (ProductEnum.SLICES_FOR_SMILES_499_ID.isEqualToProductId(prodId))
            {
                sizeId = 1;
            }
            ProductComposition pc = this.product.compositionsForSize(sizeId, ProductCategoryEnum.TOPPING.getName());
            int numFree = pc.getNumFree();
            if (numFree > 0)
            {
                int count = 0;
                Iterator<OrderItem> iter = this.children.iterator();
                while (iter.hasNext())
                {
                    OrderItem child = iter.next();
                    if (child.getProduct().getCategory().getId().equals(ProductCategoryEnum.TOPPING.getId()))
                    {
                        if (child.getProduct().getId() != 82)
                        {
                            count = count + child.getQuantity();
                        }
                    }
                }
                if (count < pc.getNumFree())
                {
                    freeToppings = pc.getNumFree() - count;
                }
            }

        }

        return freeToppings;
    }

    public List<OrderItem> getToppings()
    {
        List<OrderItem> toppings = new ArrayList<OrderItem>();
        Integer catId = this.product.getCategory().getId();
        Integer prodId = this.product.getId();
        if (catId.equals(ProductCategoryEnum.PIZZA.getId()) || ProductEnum.GLUTEN_FREE_TWO_TOPPER.isEqualToProductId(prodId))
        {
            Iterator<OrderItem> iter = this.children.iterator();
            while (iter.hasNext())
            {
                OrderItem child = iter.next();
                Integer childCatId = child.getProduct().getCategory().getId();
                if(ProductCategoryEnum.TOPPING.isEqualToCategoryId(childCatId))
                {
                    toppings.add(child);
                }
            }
        }

        return toppings;
    }

    public Integer getFreeDips()
    {
        Integer freeDips = 0;
        ProductComposition pc = this.product.compositionsForSize(this.size.getId(), ProductCategoryEnum.WING_DIP.getName());
        if(null != pc)
        {
            if (pc.getNumFree() > 0)
            {
                int count = 0;
                Iterator<OrderItem> iter = this.children.iterator();
                while (iter.hasNext())
                {
                    OrderItem child = iter.next();
                    if (child.getProduct().getCategory().getId().equals(ProductCategoryEnum.WING_DIP.getId()))
                    {
                        count = count + child.getQuantity();
                    }
                }
                if (count < pc.getNumFree())
                {
                    freeDips = pc.getNumFree() - count;
                }
            }
        }

        return freeDips;
    }

    public List<OrderItem> getDips()
    {
        List<OrderItem> dips = new ArrayList<OrderItem>();
        Iterator<OrderItem> iter = this.children.iterator();
        while (iter.hasNext())
        {
            OrderItem child = iter.next();
            if (child.getProduct().getCategory().getId().equals(ProductCategoryEnum.WING_DIP.getId()))
            {
                dips.add(child);
            }
        }

        return dips;
    }

    public int getTotalDipCount()
    {
        int dipCount = 0;
        List<OrderItem> dips = getDips();
        Iterator<OrderItem> iter = dips.iterator();
        while (iter.hasNext())
        {
            dipCount += iter.next().getQuantity();
        }

        return dipCount;
    }

    public Integer getCompositionCount()
    {
        Integer compositionCount = 0;
        if (this.product.getCompositions() != null)
        {
            compositionCount = this.product.getCompositions().size();
        }

        return compositionCount;
    }

    public Integer maxQuantityForProductDetail(Integer productId)
    {
        int max = 0;
        Set<ProductDetail> details = this.product.getDetails();
        if (details != null)
        {
            for (ProductDetail detail : details)
            {
                if (detail.getSubProduct().getId().equals(productId))
                {
                    max = detail.getQuantity();
                }
            }
        }

        return max;
    }

    public boolean isDippedItem()
    {
        Integer catId = this.product.getCategory().getId();
        if (catId.equals(ProductCategoryEnum.WING_MEAL.getId()) || catId.equals(ProductCategoryEnum.WING_SIDE.getId())
            || catId.equals(ProductCategoryEnum.CHICKEN.getId()) || catId.equals(ProductCategoryEnum.CHICKEN_BITES.getId()))
        {
            return true;
        }

        return false;
    }

    /**
     * @param drawTitle
     *            the drawTitle to set
     */
    public void setDrawTitle(boolean drawTitle)
    {
        this.drawTitle = drawTitle;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof OrderItem))
            return false;

        final OrderItem item = (OrderItem) o;

        if (itemId != item.getItemId())
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = 29 * itemId;

        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("item id", this.itemId)
            .append("display name", this.displayName).append("product id", this.productId).append("size id", this.sizeId)
            .append("parent id", this.parentId).append("quantity", this.quantity).append("price", this.price).toString();
    }

    /**
     * @return the parentId
     */
    public Integer getParentId()
    {
        return this.parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(OrderItem o)
    {
        OrderItem oi1 = o;
        if (this.getProduct() != null && oi1.getProduct() != null)
        {
            return this.getProduct().getCategory().getId().compareTo(oi1.getProduct().getCategory().getId());
        }

        return 0;
    }

    /**
     * @return the noMoreDips
     */
    public boolean isNoMoreDips()
    {
        return this.noMoreDips;
    }

    /**
     * @param noMoreDips
     *            the noMoreDips to set
     */
    public void setNoMoreDips(boolean noMoreSauce)
    {
        this.noMoreDips = noMoreSauce;
    }

    public int getFreeOptionCount(String optionType)
    {
        Product p = this.getProduct();
        ProductSize size = this.getSize();
        Iterator<ProductComposition> iter = p.compositionsForSize(size.getId()).iterator();

        ProductComposition composition = null;
        while (iter.hasNext())
        {
            ProductComposition tempComp = iter.next();
            if (tempComp.getSubProperty().equalsIgnoreCase(optionType))
            {
                composition = tempComp;
                break;
            }
        }

        if (composition != null)
        {
            return composition.getNumFree();
        }

        return 0;
    }

    public void validate(List<String> errors)
    {
        Set<ProductComposition> compositions = this.getProduct().compositionsForSize(this.getSize());
        for (ProductComposition comp : compositions)
        {
            ProductCategoryEnum subPropertyCategory = ProductCategoryEnum.categoryForName(comp.getSubProperty());
            if (subPropertyCategory.equals(ProductCategoryEnum.WING_DIP))
            {
                if (!this.isNoMoreDips())
                {
                    if (!validateFreeItems(this, comp))
                    {
                        errors.add("You still have free dips available for " + this.getDisplayName()
                            + ".  Please either choose your " + "remaining dips or select the \"No More Dips\" check box.");
                    }
                }
            }
            else if (!validateFreeItems(this, comp))
            {
                if (subPropertyCategory.equals(ProductCategoryEnum.WING_FLAVOR))
                {
                    int numFree = comp.getNumFree();
                    String errorMessage = "Please add Wing Flavour selection to the " + this.getDisplayName();
                    String options = "options";
                    if (numFree == 1)
                    {
                        options = "option";
                    }
                    errorMessage += " (you must choose " + numFree + ", 20 piece flavor " + options + ").";
                    errors.add(errorMessage);
                }
                else
                {
                    errors.add("Please add free items to " + this.getDisplayName());
                }
            }
            comp = null;
        }

        List<OrderItem> children = this.getChildren();
        Iterator<OrderItem> cIter = children.iterator();
        for (OrderItem child : children)
        {
            child = cIter.next();
            if (child.getProduct().getCategory().isTopLevel())
            {
                child.validate(errors);
            }
        }
    }

    /**
     * @param item
     * @param comp
     */
    private boolean validateFreeItems(OrderItem item, ProductComposition comp)
    {
        boolean valid = true;

        int freeCount = 0;
        String property = comp.getSubProperty();
        for (OrderItem child : this.getChildren())
        {
            Product p = child.getProduct();
            if (p.getCategory().getName().equals(property))
            {
                // extra sauce is free.
                if (!p.getId().equals(82))
                {
                    freeCount += child.getQuantity();
                }
            }
        }
        if (comp.getNumFree() > freeCount)
        {
            valid = false;
        }

        return valid;
    }

    public String trackerString(Integer orderId)
    {
        String trackerString = "UTM:I|" + orderId + "|" + this.getProduct().getId() + "|" + this.getDisplayName() + "|"
            + this.getProduct().getCategory().getDisplayName() + "|" + (this.getPrice() * 0.01) + "|" + this.getQuantity()
            + "\n";
        return trackerString;
    }

    public int childCountForCategory(Integer categoryId)
    {
        int productCount = 0;
        if (null != children && !children.isEmpty())
        {
            for (OrderItem child : this.children)
            {
                Integer childProdCatId = child.productCategoryId();
                if (childProdCatId.equals(categoryId))
                {
                    productCount += child.getQuantity();
                }
            }
        }

        return productCount;
    }

    public int childCountForProduct(Integer productId)
    {
        int productCount = 0;
        if (null != children && !children.isEmpty())
        {
            for (OrderItem child : this.children)
            {
                final Integer childProdId = child.getProduct().getId();
                if (childProdId.equals(productId))
                {
                    productCount++;
                }
            }
        }

        return productCount;
    }

    // convenienceMethod
    public Integer productCategoryId()
    {
        return product.categoryId();
    }

    public Integer productId()
    {
        return product.getId();
    }

    // needed for mobile web app
    public Integer getProductId()
    {
        return productId;
    }

    public void setProductId(Integer productId)
    {
        this.productId = productId;
    }

    public Integer getSizeId()
    {
        return sizeId;
    }

    public void setSizeId(Integer sizeId)
    {
        this.sizeId = sizeId;
    }

    public int maxProductsAllowedForCategory(Integer categoryId)
    {
        int maxCount = 0;
        Integer productId = product.getId();
        if (ProductCategoryEnum.PIZZA.isEqualToCategoryId(categoryId) && SpecialEnum.EVERYDAY_DEAL.isEqualToSpecialId(productId))
        {
            maxCount = 2;
        }
        Set<ProductDetail> bundleDetails = product.getDetails();
        for (ProductDetail productDetail : bundleDetails)
        {
            final Integer pdCategoryId = productDetail.getSubProduct().getCategory().getId();
            if (categoryId.equals(pdCategoryId))
            {
                maxCount += productDetail.getQuantity();
            }
        }

        return maxCount;
    }

    public boolean hasChildForProductId(Integer productId)
    {
        for(OrderItem temp : this.children)
        {
            if (temp.getProduct().getId().equals(productId))
            {
                return true;
            }
        }

        return false;
    }

    public int freeProductsAllowedForCategoryByComposition(String subProperty)
    {
        int freeCount = 0;
        Set<ProductComposition> bundleComp = product.getCompositions();
        for (ProductComposition productComp : bundleComp)
        {
            final String pcSubProperty = productComp.getSubProperty();
            final Integer pcSizeId = productComp.getSize().getId();
            if (subProperty.equals(pcSubProperty))
            {
                if (this.size.getId().equals(pcSizeId))
                {
                    return productComp.getNumFree();
                }
            }
        }

        return freeCount;
    }
}