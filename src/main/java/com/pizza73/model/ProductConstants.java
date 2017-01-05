package com.pizza73.model;

import com.pizza73.model.enums.ProductEnum;
import com.pizza73.model.enums.ToppingMappingEnum;
import com.pizza73.model.enums.WingFlavorEnum;

public class ProductConstants
{
    public static final Integer[] ADD_ON_FREE_ITEMS = new Integer[]{ProductEnum.WEDGES.getId(), ProductEnum.ONION_RING.getId(),
        ProductEnum.FRIES.getId()};

    public static final Integer APPLE_CHEESE_CAKE = Integer.valueOf(451);

    public static final String CART = "CART";

    public static final Integer[] CHICKEN_BITE_FLAVORS = new Integer[]{WingFlavorEnum.BBQ.getId(), WingFlavorEnum.HONEY_GARLIC.getId(),
        WingFlavorEnum.CHIPOTLE.getId(), WingFlavorEnum.NAKED.getId(), WingFlavorEnum.SWEET_CHILI_THAI.getId()};

    public static final Integer[] CHICKEN_BITE_IPHONE_BOX = new Integer[]{245, 548, 549, 550, 551, 554, 555, 556, 557, 558, 561, 562,
        563, 564, 565};

    public static final Integer[] CHICKEN_BITE_MEALS = new Integer[]{244, 544, 545, 546, 547, 195};

    public static final Integer[] CHICKEN_BITE_SIDES = new Integer[]{245, 548, 549, 550, 551};

    public static final Integer[] CHICKEN_BITE_SIDES_10 = new Integer[]{515,643,644,645,646};

    public static final Integer[] CHIPOTLE_OUTAGES = new Integer[]{ProductEnum.CHIPOTLE_CHICKEN_PIZZA.getId(),
        ProductEnum.CHIPOTLE_PORK_PIZZA.getId(), ProductEnum.CHIPOTLE_STEAK_PIZZA.getId(), WingFlavorEnum.CHIPOTLE_BREADED.getId(),
        ProductEnum.CHIPOTLE_BREADED_WING_MEAL.getId(), ProductEnum.CHIPOTLE_BREADED_WING_SO.getId(),
        ProductEnum.CHIPOTLE_WING_MEAL.getId(), WingFlavorEnum.CHIPOTLE.getId(), ProductEnum.CHIPOTLE_WING_SO.getId()};

    public static final Integer[] CHIPS = new Integer[]{ProductEnum.LAYS.getId(), ProductEnum.DORITOS.getId()};

    public static final String CINIPLEX_MESSAGE = "Redeem this unique code for your $1 Digital Movie rental at www.cineplex" + "" +
        ".com/pizza73: ";

    public static final String CINIPLEX_MOBILE_MESSAGE = "Redeem this unique code for your $1 Digital Movie rental at www" + ".cineplex" +
        ".com/pizza73: ";

    public static final String DEFAULT_CHICKEN_BITE_ID = "552";

    public static final Integer DEFAULT_DIP_ID = Integer.valueOf(295);

    public static final String DEFAULT_WING_ID = "208";

    public static final Integer DIP_COST = 75;

    public static final Integer[] DISCOUNTINUED_DIPS = new Integer[]{344, 345, 346, 347, 218, 350, 349, 351, 352, 295};

    public static final Integer FOUR_PAK_COKE_MAX_QUANTITY = 5;

    public static final Integer FOUR_PAK_MAX_QUANTITY = 4;

    public static final String FREE_12_EDD = "free12ed";

    public static final String FT_MAC_COUPON = "4FTMAC5";

    public static final Integer[] GLUTEN_PIZZAS = new Integer[]{486, 487, 488, 489, 543};

    public static final String HH_2013_COUPON = "4386729";

    public static final String HOL_HELP_COUPON = "6798119";

    public static final Integer INFO_FIELD_LENGTH = 32;

    public static final String MOBILE = "MOBILE";

    public static final Integer[] NON_GLUTEN_FREE_SAUCE = new Integer[]{ToppingMappingEnum.DONAIR_SAUCE.getProductId(),
        ToppingMappingEnum.BUFFALO_SAUCE.getProductId(), ToppingMappingEnum.BBQ_SAUCE.getProductId(), ToppingMappingEnum.CHILI_SAUCE.getProductId()};

    public static final Integer[] NON_GLUTEN_FREE_TOPPINGS = new Integer[]{ToppingMappingEnum.STEAK.getProductId(),
        ToppingMappingEnum.DONAIR.getProductId()};

    public static final Integer[] NOT_WING_FLAVORS = new Integer[]{WingFlavorEnum.NAKED.getId(), WingFlavorEnum.SWEET_CHILI_THAI.getId()};

    public static final String[] ONE_OFF_COUPONS = new String[]{"M26J3J", "3QHFQE", "QJ8JD2", "GWPFN5", "4RS3YX", "BSGPSE", "9FWD7N",
        "YM9V3D", "CTRVZK", "UPRWKZ"};

    public static final String ONLINE = "ONLINE";

    // info variables
    public static final String PARK_MOBILE_ORDERS = "park_mobile_orders";

    public static final String PARK_ONLINE_CUSTOMER = "park_online_customer";

    public static final String SHRIMP_ID = "232";

    public static final Integer STAMPEDE_FOUR_PAK_MAX_QUANTITY = 6;

    public static final Integer[] WINTER_FREEZE_FREE_ITEMS = new Integer[]{379, 393, ProductEnum.BREAD_STICK.getId()};

    public static final String WTTN_COUPON = "9118976";
    public static final String FIVE_DOLLAR_OFF_COUPON  = "4386729";
    public static final String FUNNEL_CAKE_COUPON  = "73BVJ25";
    public static final String FREE_DIP_COUPON  = "FP73DP";
    public static final String CASL_COUPON  = "FW69738";
}
