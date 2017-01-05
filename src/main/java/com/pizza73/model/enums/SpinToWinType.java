package com.pizza73.model.enums;

import com.pizza73.model.ProductConstants;

/**
 * MenuId.java TODO comment me
 *
 * @author chris 2-Feb-07
 *
 * @Copyright Flying Pizza 73
 */
public enum SpinToWinType
{

    ONION_RINGS(Integer.valueOf(1)), POTATO_WEDGES(Integer.valueOf(2)), SMALL_WEDGIE(Integer.valueOf(3)), DIP(Integer
        .valueOf(4)), DELIVERY(Integer.valueOf(5)), POP(Integer.valueOf(6)), EDD(Integer.valueOf(7)), BBQ(Integer.valueOf(8)), BIKE(
        Integer.valueOf(9)), MOVIES(Integer.valueOf(10)), GAS_COUPON(Integer.valueOf(11));

    // private static final Logger log = Logger.getLogger(SpinToWinType.class);
    private Integer id;

    private SpinToWinType(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return this.id;
    }

    public static SpinToWinType valueForId(Integer couponId)
    {
        SpinToWinType stwType = null;

        SpinToWinType[] stwTypes = SpinToWinType.values();
        for (SpinToWinType stw : stwTypes)
        {
            if (stw.getId().equals(couponId))
            {
                stwType = stw;
                break;
            }
        }

        return stwType;
    }

    public static Integer discount(Integer couponType)
    {
        Integer discount = 0;
        if (SpinToWinType.ONION_RINGS.getId().equals(couponType) || SpinToWinType.POTATO_WEDGES.getId().equals(couponType))
        {
            discount = 299;
        }
        if (SpinToWinType.DELIVERY.getId().equals(couponType))
        {
            discount = 350;
        }
        else if (SpinToWinType.DIP.getId().equals(couponType))
        {
            discount = ProductConstants.DIP_COST;
        }
        else if (SpinToWinType.SMALL_WEDGIE.getId().equals(couponType))
        {
            discount = 575;
        }
        else if (SpinToWinType.POP.getId().equals(couponType))
        {
            discount = 299;
        }
        return discount;
    }
}
