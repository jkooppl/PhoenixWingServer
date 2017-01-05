package com.pizza73.model.enums;

/**
 * ProductCategoryEnum.java TODO comment me
 * 
 * @author chris 20-Dec-06
 * 
 * @Copyright Flying Pizza 73
 */
public enum ProductCategoryEnum
{
    PIZZA(Integer.valueOf(1), "pizza", "Pizzas"), //
    CRUST(Integer.valueOf(2), "crust", "Crust"), //
    SAUCE(Integer.valueOf(3), "sauce", "Sauce"), //
    TOPPING(Integer.valueOf(4), "topping", "Topping"), //
    PIZZA_DIP(Integer.valueOf(5), "pizza_dip", "Pizza Dip"), //
    WING_MEAL(Integer.valueOf(6), "wing_meals", "Wing Meal"), //
    WING_SIDE(Integer.valueOf(7), "wing_side", "Side O'Wings"), //
    WING_BOX(Integer.valueOf(8), "wing_box", "Box O' Wings"), //
    WING_DIP(Integer.valueOf(9), "wing_dip", "Dips"), //
    POP(Integer.valueOf(10), "soft_drink", "Beverage"), //
    OTHER_DRINK(Integer.valueOf(11), "other_drink", "Other Drink"), //
    WEDGIE(Integer.valueOf(12), "wedgie", "Wedgies"), //
    EXTRA(Integer.valueOf(13), "extra", "Others"), //
    SPECIAL(Integer.valueOf(14), "special", "Specials"), //
    SALAD_EXTRA(Integer.valueOf(15), "salad_extras", "Salad Extras"), //
    MOVIE(Integer.valueOf(16), "movie", "Movies"), //
    NOTE(Integer.valueOf(16), "note", "Notes"), //
    PROMO(Integer.valueOf(17), "promo", "Promo"), //
    CHICKEN_SHRIMP(Integer.valueOf(18), "chicken_shrimp", "Chicken/Shrimp"), //
    FRIES_ONION(Integer.valueOf(19), "fries_onion_rings", "Fries/Onion Rings"), //
    CHICKEN_BITES(Integer.valueOf(20), "chicken_bites", "Chicken Bites"), //
    SHRIMP(Integer.valueOf(21), "shrimp", "Shrimp"), //
    CHICKEN(Integer.valueOf(22), "chicken", "Chicken"), //
    WING_FLAVOR(Integer.valueOf(24), "wing_flavors", "Wing Flavor"), //
    SALAD(Integer.valueOf(23), "salads", "Salads"), //
    GLUTEN_FREE_PIZZA(Integer.valueOf(25), "gluten_free_pizzas", "Gluten Free Pizzas"); //

    private Integer id;
    private String name;
    private String displayName;

    ProductCategoryEnum(Integer id, String name, String displayName)
    {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
    }

    public Integer getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public static ProductCategoryEnum categoryForId(Integer id)
    {
        ProductCategoryEnum pc = null;
        ProductCategoryEnum[] categories = ProductCategoryEnum.values();
        for (ProductCategoryEnum category : categories)
        {
            if (category.getId().equals(id))
            {
                pc = category;
                break;
            }
        }

        return pc;
    }

    public static ProductCategoryEnum categoryForName(String name)
    {
        ProductCategoryEnum pc = null;
        ProductCategoryEnum[] categories = ProductCategoryEnum.values();
        for (ProductCategoryEnum category : categories)
        {
            if (category.getName().equals(name))
            {
                pc = category;
                break;
            }
        }

        return pc;
    }

    public boolean isEqualToCategoryId(Integer categoryId)
    {
        return null != categoryId ? this.id.equals(categoryId) : false;
    }
}
