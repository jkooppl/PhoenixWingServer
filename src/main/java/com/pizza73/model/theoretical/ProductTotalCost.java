package com.pizza73.model.theoretical;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductTotalCost implements Serializable
{
    private static final long serialVersionUID = -8933151379426399795L;

    private Integer productId;
    private Integer sizeId;
    private String name;
    private BigDecimal cost = BigDecimal.ZERO;

    public ProductTotalCost(final Integer productId, final Integer sizeId, final String name, final BigDecimal cost)
    {
        this.productId = productId;
        this.sizeId = sizeId;
        this.name = name;
        this.cost = cost;
    }

    public Integer getProductId()
    {
        return productId;
    }

    public Integer getSizeId()
    {
        return sizeId;
    }

    public String getName()
    {
        return name;
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
        result = prime * result + ((sizeId == null) ? 0 : sizeId.hashCode());
        result = prime * result + ((productId == null) ? 0 : productId.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }

        ProductTotalCost other = (ProductTotalCost) obj;
        if (cost == null)
        {
            if (other.cost != null)
            {
                return false;
            }
        }
        else if (!cost.equals(other.cost))
        {
            return false;
        }

        if (sizeId == null)
        {
            if (other.sizeId != null)
            {
                return false;
            }
        }
        else if (!sizeId.equals(other.sizeId))
        {
            return false;
        }

        if (productId == null)
        {
            if (other.productId != null)
            {
                return false;
            }
        }
        else if (!productId.equals(other.productId))
        {
            return false;
        }

        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals(other.name))
        {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        return "ProductCost [productId=" + productId.toString() + ", cost=" + cost + "]";
    }

    // public String csv()
    // {
    // return product.csv() + "," + cost;
    // }
}
