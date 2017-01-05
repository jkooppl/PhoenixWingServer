package com.pizza73.model.admin;

import java.io.Serializable;

import com.pizza73.model.Order;
import com.pizza73.model.Shop;

/**
 * AdminOrder.java TODO comment me
 * 
 * @author chris 11-Apr-07
 * 
 * @Copyright Flying Pizza 73
 */
public class AdminOrder implements Serializable
{

    /**
    * 
    */
    private static final long serialVersionUID = -4364831773569983384L;

    private Order order = null;
    private Shop shop;

    private boolean commentConfirmed = true;
    private boolean largeOrderConfirmed = true;
    private boolean duplicateConfirmed = true;
    private boolean smallOrderConfirmed = true;
    private boolean shopErrorConfirmed = true;
    private boolean pickupTime = false;
    private boolean deliveryTime = false;
    private String pickupTimeContent = "";
    private String deliveryTimeContent = "";

    public AdminOrder()
    {
    }

    public AdminOrder(Order order)
    {
        this.order = order;
    }

    public boolean isCommentConfirmed()
    {
        return this.commentConfirmed;
    }

    public void setCommentConfirmed(boolean confirmed)
    {
        this.commentConfirmed = confirmed;
    }

    public boolean isDuplicateConfirmed()
    {
        return this.duplicateConfirmed;
    }

    public void setDuplicateConfirmed(boolean confirmed)
    {
        this.duplicateConfirmed = confirmed;
    }

    public boolean isLargeOrderConfirmed()
    {
        return this.largeOrderConfirmed;
    }

    public void setLargeOrderConfirmed(boolean confirmed)
    {
        this.largeOrderConfirmed = confirmed;
    }

    /**
     * @return the shopErrorConfirmed
     */
    public boolean isShopErrorConfirmed()
    {
        return this.shopErrorConfirmed;
    }

    /**
     * @param shopErrorConfirmed
     *            the shopErrorConfirmed to set
     */
    public void setShopErrorConfirmed(boolean shopErrorConfirmed)
    {
        this.shopErrorConfirmed = shopErrorConfirmed;
    }

    /**
     * @return the smallOrderConfirmed
     */
    public boolean isSmallOrderConfirmed()
    {
        return this.smallOrderConfirmed;
    }

    /**
     * @param smallOrderConfirmed
     *            the smallOrderConfirmed to set
     */
    public void setSmallOrderConfirmed(boolean smallOrderConfirmed)
    {
        this.smallOrderConfirmed = smallOrderConfirmed;
    }

    /**
     * @return the order
     */
    public Order getOrder()
    {
        return this.order;
    }

    /**
     * @return the shop
     */
    public Shop getShop()
    {
        return this.shop;
    }

    /**
     * @param shop
     *            the shop to set
     */
    public void setShop(Shop shop)
    {
        this.shop = shop;
    }

    public String getPickupTimeContent()
    {
        return pickupTimeContent;
    }

    public void setPickupTimeContent(String pickupTimeContent)
    {
        this.pickupTimeContent = pickupTimeContent;
    }

    public String getDeliveryTimeContent()
    {
        return deliveryTimeContent;
    }

    public void setDeliveryTimeContent(String deliveryTimeContent)
    {
        this.deliveryTimeContent = deliveryTimeContent;
    }

    public boolean isPickupTime()
    {
        return pickupTime;
    }

    public void setPickupTime(boolean pickupTime)
    {
        this.pickupTime = pickupTime;
    }

    public boolean isDeliveryTime()
    {
        return deliveryTime;
    }

    public void setDeliveryTime(boolean deliveryTime)
    {
        this.deliveryTime = deliveryTime;
    }
}
