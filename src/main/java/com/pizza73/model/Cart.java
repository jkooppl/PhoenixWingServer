package com.pizza73.model;

import com.pizza73.model.enums.DeliveryMethod;
import com.pizza73.model.enums.MenuId;
import com.pizza73.model.enums.OrderOrigin;
import com.pizza73.model.enums.ProductCategoryEnum;
import com.pizza73.model.enums.ProductEnum;
import com.pizza73.model.enums.SizeEnum;
import com.pizza73.model.enums.SpecialEnum;
import com.pizza73.model.enums.SpinToWinType;
import com.pizza73.util.CityUtil;
import com.pizza73.util.SpecialsUtil;
import org.apache.commons.lang.ArrayUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.pizza73.model.ProductConstants.CHIPS;
import static com.pizza73.model.ProductConstants.FOUR_PAK_MAX_QUANTITY;
import static com.pizza73.model.ProductConstants.FREE_12_EDD;
import static com.pizza73.model.enums.MenuId.BC_MOBILE;
import static com.pizza73.model.enums.MenuId.BC_WEB;
import static com.pizza73.model.enums.PricingEnum.GST;
import static com.pizza73.model.enums.PricingEnum.HST;
import static com.pizza73.model.enums.PricingEnum.ONE_HUNDREDTH;
import static com.pizza73.model.enums.ProductCategoryEnum.PIZZA;
import static com.pizza73.model.enums.ProductCategoryEnum.POP;
import static com.pizza73.model.enums.ProductCategoryEnum.WEDGIE;
import static com.pizza73.model.enums.ProductCategoryEnum.WING_DIP;
import static com.pizza73.model.enums.ProductCategoryEnum.WING_MEAL;

/**
 * Cart.java TODO comment me
 *
 * @author chris 13-Oct-06
 * @Copyright Flying Pizza 73
 */

public class Cart implements Serializable
{
    private static final long serialVersionUID = 1557022147129095541L;

    protected static final NumberFormat formatter = DecimalFormat.getCurrencyInstance();

    private OnlineCustomer customer = new OnlineCustomer();

    private List<OrderItem> items = new ArrayList<OrderItem>();

    private Integer totalPrice = Integer.valueOf(0);

    private Integer enviroLevy = Integer.valueOf(0);

    private Integer deposit = Integer.valueOf(0);

    private Integer discount = Integer.valueOf(0);

    private Integer deliveryCharge = Integer.valueOf(350);

    private MenuId menuId = MenuId.AB_WEB;

    private Coupon coupon = null;

    private UniqueCoupon uc = null;

    private String errorMessage = "";

    private boolean valid = false;

    private boolean lunchDiscount = false;

    private int tempId = 1;

    private boolean storeOpen = false;

    private boolean lunchOrderCheck = false;

    private boolean lunchDelivery = false;

    private boolean lunchPickup = false;

    private boolean lunchOnline = false;

    private boolean earlyWeek = false;

    private String lunchOrderTimeMessage = "";

    private String postalCode;

    private OrderOrigin orderOrigin = OrderOrigin.ONLINE;

    public Cart getCart()
    {
        return this;
    }

    public Integer getCityId()
    {
        Integer cityId = null;
        if(null != this.customer)
        {
            final Address address = this.customer.getAddress();
            if(null != address)
            {
                final Municipality city = address.getCity();
                if(null != city)
                {
                   cityId = city.getId();
                }
            }
        }

        return cityId;
    }

    public boolean isStoreOpen()
    {
        return storeOpen;
    }

    public void setStoreOpen(boolean storeOpen)
    {
        this.storeOpen = storeOpen;
    }

    public boolean isLunchOrderCheck()
    {
        return lunchOrderCheck;
    }

    public void setLunchOrderCheck(boolean lunchOrderCheck)
    {
        this.lunchOrderCheck = lunchOrderCheck;
    }

    public boolean isLunchDelivery()
    {
        return lunchDelivery;
    }

    public void setLunchDelivery(boolean lunchDelivery)
    {
        this.lunchDelivery = lunchDelivery;
    }

    public boolean isLunchPickup()
    {
        return lunchPickup;
    }

    public void setLunchPickup(boolean lunchPickup)
    {
        this.lunchPickup = lunchPickup;
    }

    public boolean isLunchOnline()
    {
        return lunchOnline;
    }

    public void setLunchOnline(boolean lunchOnline)
    {
        this.lunchOnline = lunchOnline;
    }

    public boolean isEarlyWeek()
    {
        return earlyWeek;
    }

    public void setEarlyWeek(boolean earlyWeek)
    {
        this.earlyWeek = earlyWeek;
    }

    public String getLunchOrderTimeMessage()
    {
        return lunchOrderTimeMessage;
    }

    public void setLunchOrderTimeMessage(String lunchOrderTimeMessage)
    {
        this.lunchOrderTimeMessage = lunchOrderTimeMessage;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public void resetLunchAttributes()
    {
        this.lunchOrderCheck = false;
        this.lunchDelivery = false;
        this.lunchPickup = false;
        this.lunchOrderTimeMessage = null;
    }

    public void setLunchDetails(Shop shop, boolean isMessage)
    {
        boolean lunchCheck = false;
        boolean open = false;
        boolean pickup = false;
        boolean delivery = false;
        String message = null;
        if(null != shop)
        {
            pickup = shop.isLunchPickup();
            delivery = shop.isLunchDelivery();
            final boolean isLunchtimeOrderAvailable = pickup || delivery;

            if(!isLunchtimeOrderAvailable)
            {
                lunchCheck = true;
                message = "Your closest location will open at 4pm today.  Or if you would like to pick-up your order, " +
                    "please call us to find out the closest location open for pick-up orders.";
            }
            else if(!pickup && delivery)
            {
                message = "Based on the postal code entered, delivery service is available at this time to your location.  If you would like to " +
                    "pick-up your order, please call us to find out the closest location open for pick-up orders.";
                open = true;
            }
            else
            {
                open = true;
            }
        }
        else
        {
            lunchCheck = true;
            message = "Your closest location will open at 4pm today.  Or if you would like to pick-up your order, " +
                "please call us to find out the closest location open for pick-up orders.";
        }

        this.lunchPickup = pickup;
        this.lunchDelivery = delivery;
        this.lunchOrderCheck = lunchCheck;
        this.storeOpen = open;

        this.lunchOrderTimeMessage = isMessage ? message : null;
    }

    public void clearLunchDetails()
    {
        resetLunchAttributes();
        this.postalCode = null;
        this.storeOpen = false;
    }

    public void setOrderOrigin(OrderOrigin origin)
    {
        this.orderOrigin = origin;
    }

    public OrderOrigin getOrderOrigin()
    {
        return this.orderOrigin;
    }

    /**
     * @return the customer
     */
    public OnlineCustomer getCustomer()
    {
        return this.customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(OnlineCustomer customer, OrderOrigin orderOrigin)
    {
        if(null != customer && customer.getAddress().getCity().getProvince().equalsIgnoreCase("BC"))
        {
            if(orderOrigin.equals(OrderOrigin.MOBILE))
            {
                menuId = MenuId.BC_MOBILE;
            }
            else
            {
                menuId = MenuId.BC_WEB;
            }
        }
        else
        {
            if(orderOrigin.equals(OrderOrigin.MOBILE))
            {
                menuId = MenuId.AB_MOBILE;
            }
            else
            {
                menuId = MenuId.AB_WEB;
            }
        }

        this.customer = customer;
    }

    /**
     * @return the items
     */
    public List<OrderItem> getItems()
    {
        return this.items;
    }

    // Business logic
    public boolean isEmpty()
    {
        return items.isEmpty();
    }

    public void setValid()
    {
        if(items.isEmpty())
        {
            this.valid = false;
        }
        else
        {
            this.valid = true;
            for(OrderItem item : this.items)
            {
                if(item.isBundle())
                {
                    if(!item.isCompleteBundle())
                    {
                        this.valid = false;
                        break;
                    }
                }
            }
        }

    }

    public void setMenuId(MenuId id)
    {
        this.menuId = id;
    }

    public MenuId getMenuId()
    {
        return this.menuId;
    }

    /**
     * @param item
     */
    public Cart addItem(OrderItem item)
    {
        this.items.add(item);
        setValid();

        return this;
    }

    public void empty()
    {
        this.items.clear();
        this.enviroLevy = Integer.valueOf(0);
        this.deposit = Integer.valueOf(0);
        this.totalPrice = Integer.valueOf(0);
        this.discount = Integer.valueOf(0);
        setValid();
    }

    public boolean subtractItem(Product product)
    {
        boolean updated = false;

        for(OrderItem item : this.items)
        {
            if(item.getProduct().getId().equals(product.getId()))
            {
                item.setQuantity(item.getQuantity() - 1);
                return true;
            }
            // TODO: drill deeper.
        }

        return updated;
    }

    /**
     * @param product
     */
    public void removeItem(Product product)
    {
        OrderItem tempItem = null;
        for(OrderItem item : this.items)
        {
            if(item.getProduct().getId().equals(product.getId()))
            {
                tempItem = item;
                break;
            }
        }
        this.items.remove(tempItem);
    }
    public void removeItem(Integer productId)
    {
        OrderItem tempItem = null;
        for(OrderItem item : this.items)
        {
            if(item.getProduct().getId().equals(productId))
            {
                tempItem = item;
                break;
            }
        }
        this.items.remove(tempItem);
    }

    public void removeItemFromSpecial(Integer specialId, Integer itemToBeRemovedId)
    {
        for(OrderItem item : this.items)
        {
            Integer productId = item.getProduct().getId();
            if(productId.equals(specialId))
            {
                item.removeChild(itemToBeRemovedId);
            }
        }
    }

    public void removeItem(OrderItem item)
    {
        items.remove(item);
    }

    /**
     * @return the tempId
     */
    public int getTempId()
    {
        return this.tempId++;
    }

    public Integer getDiscount()
    {
        return this.discount;
    }

    public void setDiscount(Integer discount)
    {
        this.discount = discount;
    }

    public Integer getTotalPrice()
    {
        return this.totalPrice;
    }

    public void setTotalPrice(Integer price)
    {
        this.totalPrice = price;
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

    public void setDeposit(Integer dep)
    {
        this.deposit = dep;
    }

    public Integer getTaxableTotal()
    {
        Integer total = this.totalPrice;
        if((total > 100 || lunchDiscount) && this.getTotalDiscount() > 0)
        {
            total = total - this.getTotalDiscount();
        }
        if(this.customer.getDelivery().equals(DeliveryMethod.DELIVER))
        {
            total = total + getDeliveryCharge();
        }

        return total;
    }

    public Integer getTotalBeforeLunchDiscount()
    {
        Integer total = this.totalPrice;
        total = total + getEnviroLevy();
        return total;
    }

    /**
     * @return
     */
    public Integer getGst()
    {
        BigDecimal tempGst = BigDecimal.ZERO;

        if(this.totalPrice.compareTo(Integer.valueOf(0)) > 0)
        {
            // For rounding when converting to Integer
            BigDecimal cost = BigDecimal.valueOf(getTaxableTotal());
            for(OrderItem item : this.items)
            {
                // donation does not get taxed
                Integer productId = item.getProduct().getId();
                if(ProductEnum.SMILES_DONATION.isEqualToProductId(productId))
                {
                    cost = cost.subtract(BigDecimal.valueOf(item.getPrice()).multiply(BigDecimal.valueOf(item.getQuantity())));
                }
            }
            BigDecimal totalTax = GST.getValue();
            if(menuId.equals(BC_WEB) || menuId.equals(BC_MOBILE))
            {
                totalTax = totalTax.add(HST.getValue());
            }
            cost = cost.multiply(totalTax).add(BigDecimal.valueOf(0.5));
            tempGst = cost;
        }

        return tempGst.intValue();
    }

    /**
     * @return
     */
    public Integer getDeliveryCharge()
    {
        return this.deliveryCharge;
    }

    public void setDeliveryCharge(Integer cost)
    {
        this.deliveryCharge = cost;
    }

    public String getWebDeliveryCharge()
    {
        BigDecimal value = BigDecimal.valueOf(getDeliveryCharge());
        value = value.multiply(ONE_HUNDREDTH.getValue());
        return formatter.format(value);
    }

    public String getWebDiscount()
    {
        BigDecimal value = BigDecimal.valueOf(getTotalDiscount());
        value = value.multiply(ONE_HUNDREDTH.getValue());
        return formatter.format(value);
    }

    public String getWebEnviroLevy()
    {
        return currencyFormat(getEnviroLevy());
    }

    public String getWebDeposit()
    {
        return currencyFormat(getDeposit());
    }

    public String getWebTotalPrice()
    {
        return currencyFormat(getTotalPrice());
    }

    public String getWebGst()
    {
        return currencyFormat(getGst());
    }

    public Integer getGrandTotal()
    {
        Integer grandTotal = getDeposit() + getTaxableTotal() + getEnviroLevy() + getGst();

        return grandTotal;
    }

    public String getWebGrandTotal()
    {
        return currencyFormat(getGrandTotal());
    }

    public String getPreAuthGrandTotal()
    {
        NumberFormat format = new DecimalFormat("###,###.00");
        BigDecimal value = BigDecimal.valueOf(getGrandTotal());
        if(value.compareTo(BigDecimal.ZERO) > 0)
        {
            value = value.multiply(ONE_HUNDREDTH.getValue());
        }
        return format.format(value);
    }

    private String currencyFormat(Integer intValue)
    {
        BigDecimal value = BigDecimal.valueOf(intValue);
        if(value.compareTo(BigDecimal.ZERO) > 0)
        {
            value = value.multiply(ONE_HUNDREDTH.getValue());
        }
        else
        {
            return formatter.format(0.00d);
        }
        return formatter.format(value);
    }

    /**
     * @param itemId
     * @return
     */
    public OrderItem getItem(String itemId)
    {
        OrderItem item = null;
        Iterator<OrderItem> iter = this.items.iterator();
        while (iter.hasNext())
        {
            OrderItem next = iter.next();
            item = getItem(itemId, next);
            if(item != null)
            {
                return item;
            }
        }

        return item;
    }

    /**
     * Retreive the item represented by itemId from the cart.
     *
     * @param itemId
     * @param item
     * @return item represented by itemId or null if not found.
     */
    public OrderItem getItem(String itemId, OrderItem item)
    {
        if(item.getItemId() == Integer.parseInt(itemId))
        {
            return item;
        }
        else if(!item.getChildren().isEmpty())
        {
            List<OrderItem> children = item.getChildren();
            Iterator<OrderItem> iter = children.iterator();
            OrderItem temp = null;
            while (iter.hasNext())
            {
                OrderItem child = iter.next();
                temp = getItem(itemId, child);
                if(temp != null)
                {
                    return temp;
                }
            }
        }

        return null;
    }

    /**
     * Find an unfinished bundle corresponding to the product size passed in.
     *
     * @return
     */
    public OrderItem findUnfinishedBundle(Integer sizeId)
    {
        OrderItem bundle = null;
        if(!this.isEmpty())
        {
            List<OrderItem> candidates = this.topLevelItemsForProductWithSize(SpecialEnum.EVERYDAY_DEAL.getId(), sizeId);
            for(OrderItem candidate : candidates)
            {
                if(!candidate.isCompleteBundle())
                {
                    bundle = candidate;
                    break;
                }
            }
        }

        return bundle;
    }

    /**
     * Get all items for the product category passed in.
     *
     * @param category
     * @return
     */
    public Set<OrderItem> itemsForCategory(ProductCategoryEnum category)
    {
        Set<OrderItem> itemsForCategory = new HashSet<OrderItem>();
        for(OrderItem item : this.items)
        {
            ProductCategory cat = item.getProduct().getCategory();
            if(category.getId().equals(cat.getId()))
            {
                itemsForCategory.add(item);
            }
            itemsForCategory(category, item, itemsForCategory);
        }

        return itemsForCategory;
    }

    private void itemsForCategory(ProductCategoryEnum category, OrderItem item, Set<OrderItem> itemsForCategory)
    {
        if(!item.getChildren().isEmpty())
        {
            for(OrderItem child : item.getChildren())
            {
                ProductCategory cat = child.getProduct().getCategory();
                if(category.getId().equals(cat.getId()))
                {
                    itemsForCategory.add(child);
                }
                itemsForCategory(category, child, itemsForCategory);
            }

        }
    }

    public List<OrderItem> topLevelItemsForCategoryWithSize(ProductCategoryEnum category, Integer searchSizeId)
    {
        List<OrderItem> itemsForCategory = new ArrayList<OrderItem>();
        Integer searchCategoryId = category.getId();
        for(OrderItem item : this.items)
        {
            Integer categoryId = item.getProduct().getCategory().getId();
            Integer sizeId = item.getSize().getId();
            if(searchCategoryId.equals(categoryId) && searchSizeId.equals(sizeId))
            {
                itemsForCategory.add(item);
            }
        }

        return itemsForCategory;
    }

    /**
     * Returns a map of items within the cart by product category.
     *
     * @return TODO: figure out better way to handle specials.
     */
    public Map<ProductCategoryEnum, Set<OrderItem>> pricingMap()
    {
        Map<ProductCategoryEnum, Set<OrderItem>> map = new HashMap<ProductCategoryEnum, Set<OrderItem>>();

        Set<OrderItem> specials = new HashSet<OrderItem>();
        Set<OrderItem> meals = new HashSet<OrderItem>();
        Set<OrderItem> pizzas = new HashSet<OrderItem>();
        Set<OrderItem> pops = new HashSet<OrderItem>();
        Set<OrderItem> everyThingElse = new HashSet<OrderItem>();
        Set<OrderItem> promos = new HashSet<OrderItem>();

        String special = ProductCategoryEnum.SPECIAL.getName();
        String meal = ProductCategoryEnum.WING_MEAL.getName();
        String pizza = ProductCategoryEnum.PIZZA.getName();
        String pop = ProductCategoryEnum.POP.getName();
        if(!(null == this.items && this.items.isEmpty()))
        {
            for(OrderItem item : this.items)
            {
                ProductCategory cat = item.getProduct().getCategory();
                if(special.equalsIgnoreCase(cat.getName()))
                {
                    Product p = item.getProduct();
                    Integer productId = p.getId();
                    if(SpecialsUtil.isFourPak(productId))
                    {
                        specials.add(item);
                    }
                    else
                    {
                        Integer productCategoryId = p.getCategory().getId();
                        if(productCategoryId.equals(ProductCategoryEnum.SPECIAL.getId()))
                        {
                            for(OrderItem child : item.getChildren())
                            {
                                ProductCategory childPc = child.getProduct().getCategory();
                                if(childPc.getName().equals(meal))
                                {
                                    meals.add(child);
                                }
                                else if(childPc.getName().equals(pizza))
                                {
                                    pizzas.add(child);
                                }
                                else if(childPc.getId().equals(ProductCategoryEnum.POP.getId()))
                                {
                                    pops.add(child);
                                }
                                else
                                {
                                    everyThingElse.add(child);
                                }
                            }
                            if(!productId.equals(SpecialEnum.EVERYDAY_DEAL.getId()))
                            {
                                promos.add(item);
                            }
                        }
                    }
                }
                else if(pop.equalsIgnoreCase(cat.getName()))
                {
                    pops.add(item);
                }
                else
                {
                    everyThingElse.add(item);
                }
            }
        }
        map.put(ProductCategoryEnum.SPECIAL, specials);
        map.put(ProductCategoryEnum.PIZZA, pizzas);
        map.put(ProductCategoryEnum.WING_MEAL, meals);
        map.put(ProductCategoryEnum.EXTRA, everyThingElse);
        map.put(ProductCategoryEnum.POP, pops);
        map.put(ProductCategoryEnum.PROMO, promos);

        return map;
    }

    public void resetTempId()
    {
        tempId = 1;
    }

    /**
     * @param items2
     */
    public void setItems(List<OrderItem> items2)
    {
        this.items = items2;
    }

    public Coupon getCoupon()
    {
        return this.coupon;
    }

    public void setCoupon(Coupon coupon)
    {
        if(coupon != null)
        {
            coupon.calculateDiscount();
            customer.setCouponCode(coupon.getCode());
        }
        else
        {
            customer.setCouponCode("");
        }
        this.coupon = coupon;
    }

    public Integer getTotalDiscount()
    {
        Integer tDiscount = 0;
        if(coupon != null)
        {
            tDiscount = coupon.getCalculatedDiscount();
            if(coupon.getCode().equalsIgnoreCase(FREE_12_EDD))
            {
                tDiscount += deliveryCharge;
            }
        }
        if(uc != null)
        {
            Integer couponType = uc.getCouponType();
            tDiscount += SpinToWinType.discount(couponType);
            if(couponType.equals(SpinToWinType.DELIVERY.getId()))
            {
                // Grande Prairie and Llyod
                if(getDeliveryCharge() > 350)
                {
                    tDiscount += 100;
                }
            }
        }
        Integer discountableCost = this.getTotalBeforeLunchDiscount();
        if(lunchDiscount)
        {
            tDiscount += discountableCost / 10;
        }

//        tDiscount += largeEDDDiscountedPrice();

        tDiscount += this.discount;

        return tDiscount;
    }

    private Integer largeEDDDiscountedPrice()
    {
        Integer hhPopCount = this.countForProduct(SpecialEnum.HH_FOUR_CAN_POP.getId());
        Integer hhBonelessWingCount = this.countForProduct(ProductEnum.HH_SWEET_CHILI_BONELESS_ID.getId());
        Integer maxHHCount = hhPopCount > hhBonelessWingCount ? hhPopCount : hhBonelessWingCount;
        List<OrderItem> largeEddCount = this.topLevelItemsForProductWithSize(SpecialEnum.EVERYDAY_DEAL.getId(), 3);
        int maxDiscount = (largeEddCount.size() - maxHHCount) > 0 ? (largeEddCount.size() - maxHHCount) : 0;

        List<Integer> eddCosts = new ArrayList<Integer>();
        List<Integer> pizzaCosts = new ArrayList<Integer>();
        for(OrderItem item : largeEddCount)
        {
            List<OrderItem> children = item.getChildren();
            for(OrderItem childItem : children)
            {
                pizzaCosts.add(childItem.getPrice());
            }
        }

        Collections.sort(pizzaCosts);
        Collections.reverse(pizzaCosts);
        for(int i = 0; i < pizzaCosts.size(); i = i + 2)
        {
            eddCosts.add(pizzaCosts.get(i));
        }

        int largeEddDiscount = 0;
        for(int eddItemIndex = 0; eddItemIndex < maxDiscount; eddItemIndex++)
        {
            int eddCost = eddCosts.get(eddItemIndex);
            if(eddCost > 2500)
            {
                largeEddDiscount += eddCost - 2500;
            }
        }

        return largeEddDiscount;
    }

    public String getErrorMessage()
    {
        return this.errorMessage;
    }

    public void setErrorMessage(String eMessage)
    {
        this.errorMessage = eMessage;
    }

    public List<String> validate()
    {
        List<String> errors = new ArrayList<String>();
        List<OrderItem> items = this.getItems();
        if(items.isEmpty())
        {
            errors.add("There are no items in your cart.");
        }
        else
        {
            for(OrderItem item : items)
            {
                Product product = item.getProduct();
                if(SpecialEnum.SINGLE_TWO_TOPPER_PLUS_ONE_LITRE.isEqualToSpecialId(product.getId()))
                {
                    if(SizeEnum.LARGE.isEqualToSizeId(item.getSize().getId()))
                    {
                        Municipality city = customer.getAddress().getCity();

                        if(!CityUtil.singleCityPizza(city.getId()))
                        {
                            errors.add("The Small/Medium single pizza and 2 L pop deal is only for certain cities " + "(Okotoks Camrose, " +
                                "Saskatoon, Lloydminster, Fort Saskatchewan, Leduc).");
                        }
                    }
                }
                if(item.isBundle())
                {
                    if(!item.isCompleteBundle())
                    {
                        Integer catId = product.getCategory().getId();
                        if(ProductCategoryEnum.WING_BOX.isEqualToCategoryId(catId))
                        {
                            bundleComplete(item);
                            if(item.isCompleteBundle())
                            {
                                continue;
                            }
                        }
                        String productName = item.getDisplayName();
                        errors.add("The " + productName + " is not complete.  Please add the necessary items prior to ordering."); //
                    }
                }

                item.validate(errors);
            }

            if(hasProduct(ProductEnum.HH_SWEET_CHILI_BONELESS_ID.getId()))
            {
                int requiredProductCount = countForProductWithSize(SpecialEnum.EVERYDAY_DEAL.getId(), 2);
                requiredProductCount += countForProductWithSize(SpecialEnum.EVERYDAY_DEAL.getId(), 3);
                int dealProductCount = countForProduct(ProductEnum.HH_SWEET_CHILI_BONELESS_ID.getId());
                if(requiredProductCount <= 0)
                {
                    errors.add("In order to purchase the $2 Apps deal your order must include a a Medium or Large 2-Topper Everyday " + "deal");//
                }
                else if(dealProductCount > requiredProductCount)
                {
                    errors.add("A maximum of one $2 Drinks and one $2 Apps can be added per Medium or Large 2-Topper Everyday Deal on " + "your " +
                        "order. Please edit your order before proceeding to checkout.");//
                }
            }
            if(hasProduct(SpecialEnum.HH_FOUR_CAN_POP.getId()))
            {
                int requiredProductCount = countForProductWithSize(SpecialEnum.EVERYDAY_DEAL.getId(), 2);
                requiredProductCount += countForProductWithSize(SpecialEnum.EVERYDAY_DEAL.getId(), 3);
                int dealProductCount = countForProduct(SpecialEnum.HH_FOUR_CAN_POP.getId());
                if(requiredProductCount <= 0)
                {
                    errors.add("In order to purchase the $2 Drinks deal your order must include a a Medium or Large 2-Topper Everyday " + "deal");//
                }
                else if(dealProductCount > requiredProductCount)
                {
                    errors.add("A maximum of one $2 Drinks and one $2 Apps can be added per Medium or Large 2-Topper Everyday Deal on " + "your " +
                        "order. Please edit your order before proceeding to checkout.");//
                }
            }
            if(hasProduct(ProductEnum.SLICES_FOR_SMILES_499_ID.getId()))
            {

                final boolean isDelivery = this.customer.getDelivery().equals(DeliveryMethod.DELIVER);
                if(isDelivery)
                {
                    int taxableTotal = this.getTaxableTotal() - this.getDeliveryCharge();
//                    taxableTotal -= this.getDeliveryCharge();

//                int requiredProductCount = itemsForCategory(PIZZA).size();
//                requiredProductCount += itemsForCategory(GLUTEN_FREE_PIZZA).size();
                    int dealProductCount = countForProduct(ProductEnum.SLICES_FOR_SMILES_499_ID.getId());
                    int subtract = dealProductCount * 499;

                    int differential = taxableTotal - subtract;

                    if(differential < 1000)
                    {
                        errors.add("Slices for Smiles Pizza is only available for delivery if the total cost excluding any smile pizza(s) exceeds $9.99.");//
                    }
                }
//                requiredProductCount -= dealProductCount;
//                if(DeliveryMethod.DELIVER.equals(this.getCustomer().getDelivery()))
//                {
//                    if(requiredProductCount <= 0)
//                    {
//                        errors.add("Slices for Smiles Pizza is only available for delivery with purchase of another menu pizza.");//
//                    }
//                }
            }
            if(hasProduct(SpecialEnum.TWO_DIPS.getId()))
            {
                int requiredProductCount = countForProduct(SpecialEnum.WING_IT_BOX.getId());
                int dealProductCount = countForProduct(SpecialEnum.TWO_DIPS.getId());
                if(requiredProductCount <= 0)
                {
                    errors.add("The 2 dips for $0.99 special can only be purchased with an Wing it Box Special.");
                }
                else if(dealProductCount > requiredProductCount)
                {
                    errors.add("There are more 2 dips for $0.99 special in your cart than Wing it Box Specials.");
                }
            }
            if(hasProduct(SpecialEnum.POUTINE_MOVIE.getId(), SpecialEnum.CHILI_MOVIE.getId(), SpecialEnum.WEDGIE_MOVIE.getId()))
            {
                int addOnCount = countForProduct(SpecialEnum.POUTINE_MOVIE.getId());
                addOnCount += countForProduct(SpecialEnum.CHILI_MOVIE.getId());
                addOnCount += countForProduct(SpecialEnum.WEDGIE_MOVIE.getId());

                int eddCount = countForProduct(SpecialEnum.EVERYDAY_DEAL.getId());
                if(eddCount == 0)
                {
                    errors.add("Requirements not met: The Lunch Special is only available with the purchase of an Everyday Deal at regular price. " +
                        "Please add an Everyday Deal to your order.");
                }
                else if((eddCount * 2) < addOnCount)
                {
                    errors.add("Lunch Special limit exceeded: A maxium of 2 Lunch Specials can be added per Everyday Deal purchase. Please add " +
                        "Everyday Deal(s) or remove Lunch Special(s).");
                }
            }
        }

        return errors;
    }

    public void bundleComplete(final OrderItem bundleItem)
    {
        Integer bundleId = bundleItem.getProduct().getId();
        if(SpecialsUtil.isFourPak(bundleId))
        {
            boolean actualFourPackCount = fourPakCount(bundleItem);

            if(actualFourPackCount)
            {
                bundleItem.setCompleteBundle(true);
            }
            else
            {
                bundleItem.setCompleteBundle(false);
            }
            return;
        }

        final List<OrderItem> subItems = bundleItem.getChildren();
        int pizzas = 0;
        int pops = 0;
        int wingFlavors = 0;
        int dips = 0;
        int chips = 0;

        for(OrderItem bundleChild : subItems)
        {
            final Product product = bundleChild.getProduct();
            final Integer productId = product.getId();
            final Integer categoryId = product.getCategory().getId();
            if(PIZZA.isEqualToCategoryId(categoryId))
            {
                pizzas += bundleChild.getQuantity();
            }
            else if(WING_MEAL.isEqualToCategoryId(categoryId))
            {
                pizzas += bundleChild.getQuantity();
            }
            else if(POP.isEqualToCategoryId(categoryId))
            {
                pops += bundleChild.getQuantity();
            }
            else if(ProductCategoryEnum.WING_FLAVOR.isEqualToCategoryId(categoryId))
            {
                wingFlavors += bundleChild.getQuantity();
            }
            else if(WING_DIP.isEqualToCategoryId(categoryId))
            {
                dips += bundleChild.getQuantity();
            }
            else if(ArrayUtils.contains(CHIPS, productId))
            {
                chips += bundleChild.getQuantity();
            }
        }

        int validWingFlavors = bundleItem.freeProductsAllowedForCategoryByComposition(ProductCategoryEnum.WING_FLAVOR.getName());
        int validPizzas = bundleItem.maxProductsAllowedForCategory(PIZZA.getId());
        int validPops = bundleItem.maxProductsAllowedForCategory(POP.getId());
        int validDips = bundleItem.maxProductsAllowedForCategory(WING_DIP.getId());
        int validChips = bundleItem.maxQuantityForProductDetail(ProductEnum.LAYS.getId());
        if(SpecialEnum.WING_BOX.isEqualToSpecialId(bundleId) || SpecialEnum.CHICKEN_BITES.isEqualToSpecialId(bundleId))
        {
            validDips = bundleItem.freeProductsAllowedForCategoryByComposition(ProductCategoryEnum.WING_DIP.getName());
            boolean dipsOk = dips >= validDips || bundleItem.isNoMoreDips();
            if(wingFlavors != validWingFlavors || !dipsOk)
            {
                bundleItem.setCompleteBundle(false);
                return;
            }
        }
        else
        {
            if(pizzas != validPizzas)
            {
                bundleItem.setMaxEdd(false);
                bundleItem.setCompleteBundle(false);
                return;
            }
            else
            {
                bundleItem.setMaxEdd(true);
            }

            if(pops != validPops || dips != validDips || chips != validChips)
            {
                bundleItem.setCompleteBundle(false);
                return;
            }
        }

        bundleItem.setCompleteBundle(true);
    }

    private boolean fourPakCount(OrderItem bundleItem)
    {
        int pops = 0;
        int fourPakItems = 0;
        int validPops = bundleItem.maxProductsAllowedForCategory(POP.getId());
        for(OrderItem bundleChild : bundleItem.getChildren())
        {
            final Product product = bundleChild.getProduct();
            final Integer categoryId = product.getCategory().getId();
            if(POP.isEqualToCategoryId(categoryId))
            {
                pops += bundleChild.getQuantity();
            }
            else if(PIZZA.isEqualToCategoryId(categoryId))
            {
                fourPakItems += bundleChild.getQuantity();
            }
            else if(WING_MEAL.isEqualToCategoryId(categoryId))
            {
                fourPakItems += bundleChild.getQuantity();
            }
            else if(WEDGIE.isEqualToCategoryId(categoryId))
            {
                fourPakItems += bundleChild.getQuantity();
            }
            else if(ProductCategoryEnum.EXTRA.isEqualToCategoryId(categoryId))
            {
                Integer productId = product.getId();
                boolean nonFourPakItem = ProductEnum.TWO_DOLLAR_OFF.isEqualToProductId(productId) || ProductEnum.STAMPEDE_ADMISIONS_COUPON.isEqualToProductId(productId) || ProductEnum.SASKATOON_EX_ADMISSION.isEqualToProductId(productId);
                if(!nonFourPakItem)
                {
                    fourPakItems += bundleChild.getQuantity();
                }
            }
        }

        return pops == validPops && fourPakItems == FOUR_PAK_MAX_QUANTITY;
    }

    public boolean hasOneTopper()
    {
        if(this.items.isEmpty())
        {
            return false;
        }
        for(OrderItem item : this.getItems())
        {
            if(item.getProduct().getId() == 322)
            {
                return true;
            }
        }
        return false;
    }

    public boolean hasProducts(Integer... productIds)
    {
        boolean hasProduct = false;
        if(this.items.isEmpty())
        {
            return false;
        }

        for(OrderItem item : this.getItems())
        {
            Integer id = item.getProduct().getId();
            for(Integer productId : productIds)
            {
                if(productId.equals(id))
                {
                    hasProduct = true;
                    break;
                }
                hasProduct = this.hasProduct(item.getChildren(), productIds);
                if(hasProduct)
                {
                    break;
                }
            }
            if(hasProduct)
            {
                break;
            }
        }
        return hasProduct;
    }

    private boolean hasProduct(List<OrderItem> items, Integer[] productIds)
    {
        boolean hasProduct = false;
        if(null != items && !items.isEmpty())
        {
            for(OrderItem item : items)
            {
                Integer id = item.getProduct().getId();
                for(Integer productId : productIds)
                {
                    if(productId.equals(id))
                    {
                        hasProduct = true;
                        break;
                    }
                    hasProduct = this.hasProduct(item.getChildren(), productIds);
                    if(hasProduct)
                    {
                        break;
                    }
                }
                if(hasProduct)
                {
                    break;
                }
            }
        }

        return hasProduct;
    }

    public boolean hasProduct(Integer... productIds)
    {
        for(OrderItem item : this.items)
        {
            if(ArrayUtils.contains(productIds, item.getProduct().getId()))
            {
                return true;
            }
        }

        return false;
    }

    public boolean hasProduct(Integer productId)
    {
        for(OrderItem item : this.items)
        {
            if(productId.equals(item.getProduct().getId()))
            {
                return true;
            }
        }

        return false;
    }

    public Integer getPizzaCount()
    {
        Set<OrderItem> items = itemsForCategory(ProductCategoryEnum.PIZZA);

        return items.size();
    }

    public List<OrderItem> topLevelItemsForProductId(Integer productId)
    {
        List<OrderItem> items = new ArrayList<OrderItem>();
        for(OrderItem item : this.items)
        {
            if(productId.equals(item.getProduct().getId()))
            {
                items.add(item);
            }
        }

        return items;
    }

    public List<OrderItem> topLevelItemsForProductId(OrderItem specialItem, Integer productId)
    {
        List<OrderItem> returnItems = new ArrayList<OrderItem>();
        for(OrderItem item : specialItem.getChildren())
        {
            if(productId.equals(item.getProduct().getId()))
            {
                returnItems.add(item);
            }
        }

        return returnItems;
    }

    public List<OrderItem> topLevelItemsForProductIdSearchSpecials(Integer productId)
    {
        List<OrderItem> items = new ArrayList<OrderItem>();
        for(OrderItem item : this.items)
        {
            Product product = item.getProduct();
            if(productId.equals(product.getId()))
            {
                items.add(item);
            }
            else if(ProductCategoryEnum.SPECIAL.isEqualToCategoryId(product.getCategory().getId()))
            {
                items.addAll(topLevelItemsForProductId(item, productId));
            }
        }

        return items;
    }

    public List<OrderItem> topLevelItemsForProductWithSize(Integer productId, Integer sizeId)
    {
        List<OrderItem> items = new ArrayList<OrderItem>();
        for(OrderItem item : this.items)
        {
            if(item.getProduct().getId().equals(productId))
            {
                if(item.getSize().getId().equals(sizeId))
                {
                    items.add(item);
                }
            }
        }

        return items;
    }

    public void clear()
    {
        this.empty();
        this.resetTempId();
        this.getCustomer().setShopId(0);
        this.setErrorMessage("");
        this.setDiscount(0);
        this.setCoupon(null);
    }

    public boolean isValid()
    {
        return valid;
    }

    public void setValid(boolean valid)
    {
        this.valid = valid;
    }

    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    public static NumberFormat getFormatter()
    {
        return formatter;
    }

    public void setTempId(int tempId)
    {
        this.tempId = tempId;
    }

    public boolean isLunchDiscount()
    {
        return lunchDiscount;
    }

    public void setLunchDiscount(boolean lunchDiscount)
    {
        this.lunchDiscount = lunchDiscount;
    }

    public UniqueCoupon getUc()
    {
        return uc;
    }

    public void setUc(UniqueCoupon uc)
    {
        this.uc = uc;
    }

    public int countForProduct(Integer productId)
    {
        int count = 0;
        for(final OrderItem item : this.getItems())
        {
            final Product product = item.getProduct();
            if(product.getId().equals(productId))
            {
                count += item.getQuantity();
                if(item.getQuantity() == 0)
                {
                    count++;
                }
            }
        }

        return count;
    }

    public int countForProductWithSize(Integer productId, Integer sizeId)
    {
        int count = 0;
        for(final OrderItem item : this.getItems())
        {
            final Product product = item.getProduct();
            if(product.getId().equals(productId))
            {
                if(item.getSize().getId().equals(sizeId))
                {

                    count += item.getQuantity();
                    if(item.getQuantity() == 0)
                    {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public boolean isCalgaryAndArea()
    {
        boolean isCalgary = true;
        if(null != customer)
        {
            Address address = customer.getAddress();
            if(null != address)
            {
                Municipality city = address.getCity();
                if(null != city)
                {
                    if(!CityUtil.calgaryAndArea(city.getId()))
                    {
                        isCalgary = false;
                    }
                }
            }
        }
        return isCalgary;
    }
}