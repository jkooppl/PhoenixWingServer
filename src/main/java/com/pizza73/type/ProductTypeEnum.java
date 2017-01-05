package com.pizza73.type;


public enum ProductTypeEnum
{
    TYPE_PIZZA(1), TYPE_WING(2), TYPE_ADDON(3), TYPE_SALAD(7), TYPE_SIDE2(10);

    private Integer id;

    ProductTypeEnum(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return this.id;
    }
    
    public static ProductTypeEnum productTypeForId(Integer id)
    {
        ProductTypeEnum type = null;
        for (ProductTypeEnum productType : ProductTypeEnum.values())
        {
            if(productType.getId().equals(id))
            {
                type = productType;
                break;
            }
        }
        
        return type;
    }
}
