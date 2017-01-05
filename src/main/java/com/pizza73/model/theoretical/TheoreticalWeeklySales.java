package com.pizza73.model.theoretical;

import java.io.Serializable;

public class TheoreticalWeeklySales implements Serializable
{

    private static final long serialVersionUID = 5411401778961980670L;

    private TheoreticalProduct product;
    private Integer productId;

    public Integer getProductId()
    {
        return productId;
    }

    public Integer getSizeId()
    {
        return sizeId;
    }

    private Integer sizeId;
    private Integer shopId;
    private Integer quantity;

    public TheoreticalWeeklySales(Integer productId, Integer sizeId, Integer crust, Integer sauce, Integer shopId,
        Integer sum)
    {
        this.product = new TheoreticalProduct(productId, sizeId, crust, sauce);
        this.productId = productId;
        this.sizeId = sizeId;
        this.shopId = shopId;
        this.quantity = sum;
    }

    public TheoreticalProduct getProduct()
    {
        return product;
    }

    public Integer getShopId()
    {
        return shopId;
    }

    public Integer getQuantity()
    {
        return quantity;
    }
}
