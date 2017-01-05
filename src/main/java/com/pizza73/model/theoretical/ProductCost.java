package com.pizza73.model.theoretical;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ProductCost implements Serializable
{
    private static final long serialVersionUID = -8933151379426399795L;

    private TheoreticalProduct product;
    private BigDecimal cost = BigDecimal.ZERO;

    public ProductCost(TheoreticalProduct product)
    {
        this.product = product;
    }

    public void calculateProductCost(List<ProductComposition> productComp, Map<Integer, PrepItem> prepItems)
    {
        for (ProductComposition pc : productComp)
        {
            PrepItem prepItem = prepItems.get(pc.getPrepInvItemId());
            this.cost = cost.add(prepItem.getUnitCost().multiply(pc.getPrepInvItemPortionRequired()));
        }
    }

    public TheoreticalProduct getProduct()
    {
        return product;
    }

    public void setProduct(TheoreticalProduct product)
    {
        this.product = product;
    }

    public BigDecimal getCost()
    {
        return cost;
    }

    public void setCost(BigDecimal cost)
    {
        this.cost = cost;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cost == null) ? 0 : cost.hashCode());
        result = prime * result + ((product == null) ? 0 : product.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductCost other = (ProductCost) obj;
        if (cost == null)
        {
            if (other.cost != null)
                return false;
        }
        else if (!cost.equals(other.cost))
            return false;
        if (product == null)
        {
            if (other.product != null)
                return false;
        }
        else if (!product.equals(other.product))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "ProductCost [product=" + product.toString() + ", cost=" + cost + "]";
    }

    public String csv()
    {
        return product.csv() + "," + cost;
    }
}
