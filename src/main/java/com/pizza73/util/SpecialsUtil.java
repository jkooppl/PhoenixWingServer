package com.pizza73.util;

import com.pizza73.model.OrderItem;
import com.pizza73.model.enums.SpecialEnum;


public class SpecialsUtil
{

    public static boolean isFourPak(final Integer id)
    {
        boolean isFourPak = SpecialEnum.FOUR_PAK.isEqualToSpecialId(id)
            || SpecialEnum.STAMPEDE_FOUR_PAK.isEqualToSpecialId(id) || SpecialEnum.POP_FOUR_PAK.isEqualToSpecialId(id);
        return isFourPak;
    }

    public static boolean hasChildProduct(final Integer id, final OrderItem specialItem)
    {
        boolean hasChildProduct = false;
        for(OrderItem child : specialItem.getChildren())
        {
            if(child.getProduct().getId().equals(id))
            {
                hasChildProduct = true;
                break;
            }
        }

        return hasChildProduct;
    }
}
