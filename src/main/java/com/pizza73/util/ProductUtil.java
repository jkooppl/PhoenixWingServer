package com.pizza73.util;

import com.pizza73.model.enums.ProductEnum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProductUtil
{

    public static final Map<Integer, Integer> POP_SIZE_MAPPING = createtPopSizeMap();
    public static final Map<Integer, Integer> OKOTOKS_PRICING_MAP = createtOkotoksPricingMap();

    private static Map<Integer, Integer> createtPopSizeMap()
    {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(Integer.valueOf(19), Integer.valueOf(22));
        // DIET PEPSI
        map.put(Integer.valueOf(20), Integer.valueOf(23));
        // 7-UP
        map.put(Integer.valueOf(60), Integer.valueOf(24));
        // ROOT BEER
        map.put(Integer.valueOf(61), Integer.valueOf(25));
        // BRISK
        map.put(Integer.valueOf(62), Integer.valueOf(200));
        // Coke
        map.put(Integer.valueOf(254), Integer.valueOf(259));
        // Diet Coke
        map.put(Integer.valueOf(255), Integer.valueOf(260));
        // Sprite
        map.put(Integer.valueOf(256), Integer.valueOf(261));

        return Collections.unmodifiableMap(map);
    }

    private static Map<Integer, Integer> createtOkotoksPricingMap()
    {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 3);
        map.put(2, -2);
        map.put(3, 1);
        map.put(4, 1);
        map.put(6, 3);
        map.put(7, 1);
        map.put(8, 5);
        map.put(9, 0);
        map.put(48, 1);
        map.put(49, 3);
        map.put(81, 1);
        map.put(118, 2);
        map.put(119, 2);
        map.put(120, 2);
        map.put(121, 3);
        map.put(281, 1);
        map.put(321, 3);
        map.put(355, 3);
        map.put(360, 1);
        map.put(374, 2);
        map.put(455, 2);

        return Collections.unmodifiableMap(map);
    }

    public static boolean isLitrePop(Integer productId)
    {
        return ProductEnum.LITRE_COKE.isEqualToProductId(productId)
            || ProductEnum.LITRE_DIET_COKE.isEqualToProductId(productId);
    }

    public static boolean isSinglePizza(Integer productId)
    {
        return ProductEnum.SINGLE_ONE_TOPPER.isEqualToProductId(productId)
            || ProductEnum.SINGLE_DONAIR.isEqualToProductId(productId)
            || ProductEnum.SINGLE_MOTHERS_DAY.isEqualToProductId(productId)
            || ProductEnum.SINGLE_73_DELUXE.isEqualToProductId(productId)
            || ProductEnum.SLICES_FOR_SMILES_499_ID.isEqualToProductId(productId)
            || ProductEnum.SINGLE_VALENTINES_DAY.isEqualToProductId(productId);
    }

    public static boolean isSingleMediumPizza(Integer productId)
    {
        return ProductEnum.SINGLE_MOTHERS_DAY.isEqualToProductId(productId)
            || ProductEnum.SINGLE_73_DELUXE.isEqualToProductId(productId)
            || ProductEnum.SLICES_FOR_SMILES_499_ID.isEqualToProductId(productId)
            || ProductEnum.SINGLE_VALENTINES_DAY.isEqualToProductId(productId);
    }
}
