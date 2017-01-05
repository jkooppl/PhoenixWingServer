package com.pizza73.model.theoretical;

import java.math.BigDecimal;

public class PrepItemDetail
{
    private Integer prepItemId;
    private Integer rawInvId;
    private BigDecimal rawPortions = BigDecimal.ZERO;

    public PrepItemDetail(Integer prepId, Integer rawId, BigDecimal rawPortion)
    {
        this.prepItemId = prepId;
        this.rawInvId = rawId;
        this.rawPortions = rawPortion;
    }

    public Integer getPrepItemId()
    {
        return prepItemId;
    }

    public void setPrepItemId(Integer prepItemId)
    {
        this.prepItemId = prepItemId;
    }

    public Integer getRawInvId()
    {
        return rawInvId;
    }

    public void setRawInvId(Integer rawInvId)
    {
        this.rawInvId = rawInvId;
    }

    public BigDecimal getRawPortions()
    {
        return rawPortions;
    }

    public void setRawPortions(BigDecimal rawPortions)
    {
        this.rawPortions = rawPortions;
    }

    @Override
    public String toString()
    {
        return "PrepItem [prepItemId=" + prepItemId + ", rawInvId=" + rawInvId + ", rawPortions=" + rawPortions + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((prepItemId == null) ? 0 : prepItemId.hashCode());
        result = prime * result + ((rawInvId == null) ? 0 : rawInvId.hashCode());
        result = prime * result + ((rawPortions == null) ? 0 : rawPortions.hashCode());
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
        PrepItemDetail other = (PrepItemDetail) obj;
        if (prepItemId == null)
        {
            if (other.prepItemId != null)
                return false;
        }
        else if (!prepItemId.equals(other.prepItemId))
            return false;
        if (rawInvId == null)
        {
            if (other.rawInvId != null)
                return false;
        }
        else if (!rawInvId.equals(other.rawInvId))
            return false;
        if (rawPortions == null)
        {
            if (other.rawPortions != null)
                return false;
        }
        else if (!rawPortions.equals(other.rawPortions))
            return false;
        return true;
    }
}
