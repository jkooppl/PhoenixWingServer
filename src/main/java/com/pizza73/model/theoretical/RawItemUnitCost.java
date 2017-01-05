package com.pizza73.model.theoretical;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * create or replace view raw_item_unit_cost as select raw_inv_item_id,
 * (raw_inv_cost_1*raw_inv_markup_1/100 + raw_inv_cost_1)/portions_per_inv_item
 * as unit_cost from raw_inv_item;
 * 
 * @author chris
 * 
 */
public class RawItemUnitCost implements Serializable
{
    private static final long serialVersionUID = -4380966804401605607L;

    private Integer rawItemId;
    private BigDecimal unitCost;

    public RawItemUnitCost(Integer rawId, BigDecimal cost)
    {
        this.rawItemId = rawId;
        this.unitCost = cost;
    }

    public Integer getRawItemId()
    {
        return rawItemId;
    }

    public void setRawItemId(Integer rawItemId)
    {
        this.rawItemId = rawItemId;
    }

    public BigDecimal getUnitCost()
    {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost)
    {
        this.unitCost = unitCost;
    }

    @Override
    public String toString()
    {
        return "RawItemUnitCost [rawItemId=" + rawItemId + ", unitCost=" + unitCost + "]";
    }

    public String csv()
    {
        return rawItemId + ", " + unitCost;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rawItemId == null) ? 0 : rawItemId.hashCode());
        result = prime * result + ((unitCost == null) ? 0 : unitCost.hashCode());
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
        RawItemUnitCost other = (RawItemUnitCost) obj;
        if (rawItemId == null)
        {
            if (other.rawItemId != null)
                return false;
        }
        else if (!rawItemId.equals(other.rawItemId))
            return false;
        if (unitCost == null)
        {
            if (other.unitCost != null)
                return false;
        }
        else if (!unitCost.equals(other.unitCost))
            return false;
        return true;
    }
}
