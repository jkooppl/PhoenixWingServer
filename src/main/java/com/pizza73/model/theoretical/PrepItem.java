package com.pizza73.model.theoretical;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrepItem
{
    private Integer prepItemId;
    private Integer yield;
    private BigDecimal unitCost;
    private String description;
    private String portion;
    private List<PrepItemDetail> prepItemDetails = new ArrayList<PrepItemDetail>();

    public PrepItem(Integer prepId, String desc, Integer yield, String portion, List<PrepItemDetail> prepItemDetails)
    {
        this.prepItemId = prepId;
        this.description = desc;
        this.yield = yield;
        this.portion = portion;
        this.prepItemDetails = prepItemDetails;
    }

    public Integer getPrepItemId()
    {
        return prepItemId;
    }

    public void setPrepItemId(Integer prepItemId)
    {
        this.prepItemId = prepItemId;
    }

    public Integer getYield()
    {
        return yield;
    }

    public void setYield(Integer yield)
    {
        this.yield = yield;
    }

    public BigDecimal getUnitCost()
    {
        return unitCost;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getPortion()
    {
        return this.portion;
    }

    public void setUnitCost(BigDecimal unitCost)
    {
        this.unitCost = unitCost;
    }

    public void calculateCost(Map<Integer, RawItemUnitCost> rawCosts)
    {
        BigDecimal cost = BigDecimal.ZERO;
        for (PrepItemDetail prepItemDetail : this.prepItemDetails)
        {
            BigDecimal rawPortion = prepItemDetail.getRawPortions();
            RawItemUnitCost rawCost = rawCosts.get(prepItemDetail.getRawInvId());
            BigDecimal rawUnitCost = rawCost.getUnitCost();
            cost = cost.add(rawUnitCost.multiply(rawPortion));
        }
        unitCost = cost.divide(BigDecimal.valueOf(this.yield), 2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString()
    {
        return "PrepItem [prepItemId=" + prepItemId + ", yield=" + yield + "]";
    }

    public String csv()
    {
        return prepItemId + "," + description + "," + yield + "," + portion + "," + unitCost;
    }
}
