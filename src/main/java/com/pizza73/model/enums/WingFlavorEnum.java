package com.pizza73.model.enums;

public enum WingFlavorEnum
{
    SCREAMIN_HOT(Integer.valueOf(208)),
    GOLDEN_CRISPY(Integer.valueOf(209)),
    BBQ(Integer.valueOf(210)),
    HONEY_GARLIC(Integer.valueOf(211)),
    LEMON_PEPPER(Integer.valueOf(212)),
    TERIYAKI(Integer.valueOf(213)),
    CHIPOTLE_BREADED(Integer.valueOf(436)),
    CHIPOTLE(Integer.valueOf(437)),
    BUFFALO(Integer.valueOf(463)),
    BUFFALO_BREADED(Integer.valueOf(464)),
    NAKED(Integer.valueOf(552)),
    SWEET_CHILI_THAI(Integer.valueOf(553));

    private Integer id;

    WingFlavorEnum(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return this.id;
    }

    public static WingFlavorEnum productForId(Integer id)
    {
        WingFlavorEnum pc = null;
        WingFlavorEnum[] products = WingFlavorEnum.values();
        for (WingFlavorEnum product : products)
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
