package com.pizza73.model.theoretical;

import java.math.BigDecimal;

public class ProductComposition
{

    private TheoreticalProduct product;
    private Integer prepInvItemId;
    private BigDecimal prepInvItemPortionRequired;

    public ProductComposition(TheoreticalProduct product, Integer prepId, BigDecimal prepPortion)
    {
        this.product = product;
        this.prepInvItemId = prepId;
        this.prepInvItemPortionRequired = prepPortion;
    }

    public TheoreticalProduct getProduct()
    {
        return product;
    }

    public void setProduct(TheoreticalProduct product)
    {
        this.product = product;
    }

    public Integer getPrepInvItemId()
    {
        return prepInvItemId;
    }

    public void setPrepInvItemId(Integer prepInvItemId)
    {
        this.prepInvItemId = prepInvItemId;
    }

    public BigDecimal getPrepInvItemPortionRequired()
    {
        return prepInvItemPortionRequired;
    }

    public void setPrepInvItemPortionRequired(BigDecimal prepInvItemPortionRequired)
    {
        this.prepInvItemPortionRequired = prepInvItemPortionRequired;
    }
}
