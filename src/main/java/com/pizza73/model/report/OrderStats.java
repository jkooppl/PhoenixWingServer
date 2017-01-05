package com.pizza73.model.report;

import java.math.BigDecimal;
import java.math.BigInteger;

public class OrderStats
{
    private BigInteger onlineCount = java.math.BigInteger.ZERO;
    private BigInteger totalCount = java.math.BigInteger.ZERO;
    private BigDecimal ratio = java.math.BigDecimal.ZERO;
    private Integer hour;

    public OrderStats()
    {
    }

    public OrderStats(BigInteger oCount, BigInteger tCount, BigDecimal ratio)
    {
        this.onlineCount = oCount;
        this.totalCount = tCount;
        this.ratio = ratio;
    }

    public OrderStats(Integer hour, BigInteger oCount, BigInteger tCount, BigDecimal ratio)
    {
        this(oCount, tCount, ratio);
        this.hour = hour;
    }

    public BigInteger getOnlineCount()
    {
        return onlineCount;
    }

    public void setOnlineCount(BigInteger onlineCount)
    {
        this.onlineCount = onlineCount;
    }

    public BigInteger getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount)
    {
        this.totalCount = totalCount;
    }

    public BigDecimal getRatio()
    {
        return ratio;
    }

    public void setRatio(BigDecimal ratio)
    {
        this.ratio = ratio;
    }

    public Integer getHour()
    {
        return this.hour;
    }

    public void setHour(Integer hour)
    {
        this.hour = hour;
    }
}
