package com.pizza73.model.enums;

public enum ProductEnum
{
    TWO_TOPPER(Integer.valueOf(1)),
    NSB_PIZZA_ID(Integer.valueOf(8)),
    SCREAMING_HOT_WING_MEAL(Integer.valueOf(10)),
    BREAD_STICK(Integer.valueOf(17)),
    FRIES(Integer.valueOf(18)),
    COUPON_ID(Integer.valueOf(29)),
    SHRIMP_MEAL(Integer.valueOf(195)),
    LAYS_CHIPS_ID(Integer.valueOf(230)),
    DORITOS_ID(Integer.valueOf(231)),
    SHRIMP(Integer.valueOf(232)),
    CHICKEN_BITE_MEAL(Integer.valueOf(244)),
    COKE_CAN(Integer.valueOf(254)),
    EIGHT_PIECE_SHRIMP(Integer.valueOf(264)),
    CHICKEN_MELT(Integer.valueOf(281)),
    CINEPLEX(Integer.valueOf(285)),
    LAYS(Integer.valueOf(306)),
    DORITOS(Integer.valueOf(307)),
    SINGLE_ONE_TOPPER(Integer.valueOf(322)),
    SMILES_DONATION(Integer.valueOf(372)),
    EDD_DONAIR(Integer.valueOf(374)),
    SINGLE_DONAIR(Integer.valueOf(375)),
    ONION_RING(Integer.valueOf(379)),
    FLAMES_CUP(Integer.valueOf(382)),
    TWO_FOR_ONE_MOVIE(Integer.valueOf(385)),
    WEDGES(Integer.valueOf(393)),
    SLICES_FOR_SMILES(Integer.valueOf(394)),
    WEDGIE_173(Integer.valueOf(522)),
    CEASAR_SALAD(Integer.valueOf(15)),
    CHICKEN_FILLETS(Integer.valueOf(137)),
    CHKN_CEASAR_SALAD(Integer.valueOf(189)),
    WEDGIE_16(Integer.valueOf(194)),
    WEDGIE_12(Integer.valueOf(193)),
    LITRE_COKE(Integer.valueOf(361)),
    LITRE_DIET_COKE(Integer.valueOf(362)),
    DONAIR_PIZZA(Integer.valueOf(374)),
    APPLE_PIE(Integer.valueOf(395)),
    CHOCOLATE_CHIP_COOKIE(Integer.valueOf(396)),
    CHIPOTLE_CHICKEN_PIZZA(Integer.valueOf(427)),
    CHIPOTLE_STEAK_PIZZA(Integer.valueOf(428)),
    CHIPOTLE_PORK_PIZZA(Integer.valueOf(429)),
    CHIPOTLE_BREADED_WING_MEAL(Integer.valueOf(425)),
    CHIPOTLE_BREADED_WING_SO(Integer.valueOf(434)),
    CHIPOTLE_WING_MEAL(Integer.valueOf(426)),
    CHIPOTLE_WING_SO(Integer.valueOf(435)),
    BUFFALO_PIZZA(Integer.valueOf(455)),
    MOTHERS_DAY(Integer.valueOf(469)),
    SINGLE_VALENTINES_DAY(Integer.valueOf(470)),
    STAMPEDE_ADMISIONS_COUPON(Integer.valueOf(473)),
    SINGLE_MOTHERS_DAY(Integer.valueOf(535)),
    GLUTEN_CRUST(Integer.valueOf(490)),
    NO_DIPS_PRODUCT_ID(Integer.valueOf(491)),
    TOYS_R_US(Integer.valueOf(495)),
    FIVE_DOLLAR_GIFT_CARD(Integer.valueOf(502)),
    FREE_DIP_COUPON(Integer.valueOf(503)),
    SINGLE_73_DELUXE(Integer.valueOf(506)),
    PESTO_GARDEN(Integer.valueOf(527)),
    PESTO_CHICKEN(Integer.valueOf(528)),
    HH_SWEET_CHILI_BONELESS_ID(Integer.valueOf(533)),
    SLICES_FOR_SMILES_499_ID(Integer.valueOf(534)),
    GLUTEN_FREE_TWO_TOPPER(Integer.valueOf(543)),
    TWENTY_PIECE_WING_BOX(Integer.valueOf(136)),
    FORTY_PIECE_WING_BOX(Integer.valueOf(12)),
    EIGHTY_PIECE_WING_BOX(Integer.valueOf(190)),
    TWENTY_PIECE_CHICKEN_BITES(Integer.valueOf(246)),
    MOVIE_COUPON(Integer.valueOf(242)),
    FORTY_PIECE_CHICKEN_BITES(Integer.valueOf(247)),
    NAKED_20_BITE(Integer.valueOf(554)),
    NAKED_40_BITE(Integer.valueOf(561)),
    SCT_20_BITE(Integer.valueOf(555)),
    SCT_40_BITE(Integer.valueOf(562)),
    HG_20_BITE(Integer.valueOf(556)),
    HG_40_BITE(Integer.valueOf(563)),
    BBQ_20_BITE(Integer.valueOf(557)),
    BBQ_40_BITE(Integer.valueOf(564)),
    CHIPOTLE_20_BITE(Integer.valueOf(558)),
    CHIPOTLE_40_BITE(Integer.valueOf(565)),
    FIVE_DOLLAR_FB_COUPON(Integer.valueOf(573)),
    CHILLI(Integer.valueOf(579)),
    POUTINE(Integer.valueOf(580)),
    CHOCOLATE_CAKE(Integer.valueOf(581)),
    CHILI_FIESTA_PIZZA(Integer.valueOf(599)),
    TWO_DOLLAR_OFF(Integer.valueOf(603)),
    GLUTTEN_FREE_CRACKERS(Integer.valueOf(605)),
    SASKATOON_EX_ADMISSION(Integer.valueOf(615)),
    RED_BOX(Integer.valueOf(625)),
    SLICES_FOR_SMILES_9(Integer.valueOf(650)),
    FUNNEL_CAKE(Integer.valueOf(656)),
    FUNNEL_CAKE_COUPON(Integer.valueOf(657))
    ;

    private Integer id;

    ProductEnum(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return this.id;
    }

    public static ProductEnum productForId(Integer id)
    {
        ProductEnum pc = null;
        ProductEnum[] products = ProductEnum.values();
        for (ProductEnum product : products)
        {
            if (product.getId().equals(id))
            {
                pc = product;
                break;
            }
        }

        return pc;
    }

    public boolean isEqualToProductId(Integer productId)
    {
        return null != productId ? this.id.equals(productId) : false;
    }
}
