package com.pizza73.model.enums;

public enum PizzaSauceEnum
{
    DEFAULT_PIZZA_SAUCE(Integer.valueOf(203),"DEFAULT_PIZZA_SAUCE"),
    BBQ_PIZZA_SAUCE(Integer.valueOf(205),"BBQ_PIZZA_SAUCE"),
    SALSA_PIZZA_SAUCE(Integer.valueOf(206),"SALSA_PIZZA_SAUCE"),
    GARLIC_PIZZA_SAUCE(Integer.valueOf(331),"GARLIC_PIZZA_SAUCE"),
    DONAIR_PIZZA_SAUCE(Integer.valueOf(383),"DONAIR_PIZZA_SAUCE"),
    CHIPOTLE_PIZZA_SAUCE(Integer.valueOf(438),"CHIPOTLE_PIZZA_SAUCE"),
    BUFFALO_PIZZA_SAUCE(Integer.valueOf(459),"BUFFALO_PIZZA_SAUCE"),
    PESTO_PIZZA_SAUCE(Integer.valueOf(529),"PESTO_PIZZA_SAUCE"),
    CHILI_PIZZA_SAUCE(Integer.valueOf(601),"CHILI_PIZZA_SAUCE");

    private Integer id;
    private String code;

    PizzaSauceEnum(Integer id, String code)
    {
        this.id = id;
        this.code = code;
    }

    public Integer getId()
    {
        return this.id;
    }

    public String getCode()
    {
        return this.code;
    }

    public static PizzaSauceEnum specialForId(Integer id)
    {
        PizzaSauceEnum pc = null;
        PizzaSauceEnum[] specials = PizzaSauceEnum.values();
        for (PizzaSauceEnum special : specials)
        {
            if (special.getId().equals(id))
            {
                pc = special;
                break;
            }
        }

        return pc;
    }

    public boolean isEqualToSpecialId(Integer specialId)
    {
        return null != specialId ? this.id.equals(specialId) : false;
    }
}
