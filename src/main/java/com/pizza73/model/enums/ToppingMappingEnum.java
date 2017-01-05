package com.pizza73.model.enums;


/**
 * ToppingMappingEnum.java TODO comment me
 *
 * @author chris 22-Feb-07
 *
 * @Copyright Flying Pizza 73
 */
public enum ToppingMappingEnum
{
    UNKNOWN("0", 0), //
    EXTRA_SAUCE("a", 82), //
    HAM("b", 83), //
    PEPPERONI("c", 84), //
    MUSHROOM("d", 85), //
    PINEAPPLE("e", 86), //
    GREEN_PEPPER("f", 87), //
    TOMATO("g", 88),//
    EXTRA_CHEESE("h", 89), //
    GROUND_BEEF("i", 90), //
    SALAMI("j", 91), //
    SHRIMP("k", 92), //
    SAUSAGE("l", 93), //
    ONION("m", 94), //
    BLACK_OLIVE("n", 95), //
    ANCHOVIES("o", 96), //
    CRUSHED_BACON("p", 97), //
    JALAPENO("q", 98), //
    FETA("r", 99), //
    SPINACH("s", 132), //
    CHICKEN("t", 101), //
    BANANA_PEPPER("u", 149), //
    PAN_CRUST("1", 201), //
    SUPER_CRUST("2", 202), //
    WWMG("4", 328), //
    GLUTEN_FREE_CRUST("5", ProductEnum.GLUTEN_CRUST.getId()), //
    MILD_SAUCE("1", 203), //
    TANGY_SAUCE("2", 204), //
    GARLIC_SAUCE("3",PizzaSauceEnum.GARLIC_PIZZA_SAUCE.getId()), //
    DONAIR_SAUCE("4", PizzaSauceEnum.DONAIR_PIZZA_SAUCE.getId()), //
    SALSA_SAUCE("5", 206), //
    CHIPOTLE_SAUCE("5", PizzaSauceEnum.CHIPOTLE_PIZZA_SAUCE.getId()), //
    BUFFALO_SAUCE("6",PizzaSauceEnum.BUFFALO_PIZZA_SAUCE.getId()), //
    BBQ_SAUCE("7", PizzaSauceEnum.BBQ_PIZZA_SAUCE.getId()), //
    STEAK("y", 325), //
    HOT_SAUSAGE("H", 356), //
    DONAIR("D", 384), //
    ROASTED_RED_PEPPER("R", 458), //
    PESTO_SAUCE("8", PizzaSauceEnum.PESTO_PIZZA_SAUCE.getId()),
    CHILI_SAUCE("11", PizzaSauceEnum.CHILI_PIZZA_SAUCE.getId()),
    MEAT_BALL("M", 623);

    private String code;
    private int productId;

    ToppingMappingEnum(String code, int productId)
    {
        this.code = code;
        this.productId = productId;
    }

    /**
     * @return the code
     */
    public String getCode()
    {
        return this.code;
    }

    /**
     * @return the productId
     */
    public int getProductId()
    {
        return this.productId;
    }

    public static String codeForId(int id)
    {
        for (ToppingMappingEnum topping : ToppingMappingEnum.values())
        {
            if (topping.productId == id)
            {
                return topping.code;
            }
        }

        return UNKNOWN.code;
    }
}
