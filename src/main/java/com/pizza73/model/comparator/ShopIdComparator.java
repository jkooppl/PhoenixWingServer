package com.pizza73.model.comparator;

import java.io.Serializable;
import java.util.Comparator;

import com.pizza73.model.Shop;

public class ShopIdComparator implements Comparator<Shop>, Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -785903755723482219L;

    public int compare(Shop s1, Shop s2)
    {
        return s1.getId().compareTo(s2.getId());
    }

}
