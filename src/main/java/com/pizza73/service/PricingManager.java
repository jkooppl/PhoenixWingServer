package com.pizza73.service;

import com.pizza73.model.Cart;
import com.pizza73.model.OrderItem;

/**
 * PricingManager.java TODO comment me
 * 
 * @author chris 11-Oct-06
 * 
 * @Copyright Flying Pizza 73
 */
public interface PricingManager
{
    /**
     * @param cart
     */
    public void calculateCost(Cart cart);

    public int getFreeOptionCount(OrderItem item, String optionType);
}
