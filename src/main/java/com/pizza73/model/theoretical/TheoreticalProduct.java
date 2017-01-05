package com.pizza73.model.theoretical;

import java.io.Serializable;

public class TheoreticalProduct implements Serializable
{

    private static final long serialVersionUID = -1350833514661556960L;

    private Integer productId;
    private String description;
    private Integer modifierOne = 0;
    private Integer modifierTwo = 0;
    private Integer modifierThree = 0;

    public TheoreticalProduct(Integer productId, Integer one, Integer two, Integer three)
    {
        this.productId = productId;
        this.modifierOne = one;
        this.modifierTwo = two;
        this.modifierThree = three;
    }

    public Integer getProductId()
    {
        return productId;
    }

    public void setProductId(Integer productId)
    {
        this.productId = productId;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String desc)
    {
        this.description = desc;
    }

    public Integer getModifierOne()
    {
        return modifierOne;
    }

    public void setModifierOne(Integer modifierOne)
    {
        this.modifierOne = modifierOne;
    }

    public Integer getModifierTwo()
    {
        return modifierTwo;
    }

    public void setModifierTwo(Integer modifierTwo)
    {
        this.modifierTwo = modifierTwo;
    }

    public Integer getModifierThree()
    {
        return modifierThree;
    }

    public void setModifierThree(Integer modifierThree)
    {
        this.modifierThree = modifierThree;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((modifierOne == null) ? 0 : modifierOne.hashCode());
        result = prime * result + ((modifierThree == null) ? 0 : modifierThree.hashCode());
        result = prime * result + ((modifierTwo == null) ? 0 : modifierTwo.hashCode());
        result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
        TheoreticalProduct other = (TheoreticalProduct) obj;
        if (productId == null)
        {
            if (other.productId != null)
                return false;
        }
        else if (!productId.equals(other.productId))
            return false;
        if (modifierOne == null)
        {
            if (other.modifierOne != null)
                return false;
        }
        else if (!modifierOne.equals(other.modifierOne))
            return false;
        if (modifierThree == null)
        {
            if (other.modifierThree != null)
                return false;
        }
        else if (!modifierThree.equals(other.modifierThree))
            return false;
        if (modifierTwo == null)
        {
            if (other.modifierTwo != null)
                return false;
        }
        else if (!modifierTwo.equals(other.modifierTwo))
            return false;

        return true;
    }

    @Override
    public String toString()
    {
        return "TheoreticalProduct [productId=" + productId + ", modifierOne=" + modifierOne + ", modifierTwo="
            + modifierTwo + ", modifierThree=" + modifierThree + "]";
    }

    public String csv()
    {
        return productId + "," + description + "," + modifierOne + "," + modifierTwo + "," + modifierThree;
    }

}
