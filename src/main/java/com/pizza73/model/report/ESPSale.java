package com.pizza73.model.report;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ESPSale implements Serializable
{

    private static final long serialVersionUID = 2460323412220878331L;

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm a");

    private String name;
    private BigDecimal netSales = BigDecimal.ZERO;
    private Integer orders;
    private BigDecimal largest = BigDecimal.ZERO;
    private BigDecimal smallest = BigDecimal.ZERO;
    private Calendar first;
    private Calendar last;
    private BigDecimal rate = BigDecimal.ZERO;
    private Integer errors;
    private BigDecimal costOfErrors = BigDecimal.ZERO;

    public ESPSale(String name)
    {
        this.name = name;
    }

    public void increasNetSales(BigDecimal sale)
    {
        if (null == this.netSales)
            this.netSales = BigDecimal.ZERO;
        this.netSales = this.netSales.add(sale);
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BigDecimal getNetSales()
    {
        return this.netSales;
    }

    public void setNetSales(BigDecimal netSales)
    {
        this.netSales = netSales;
    }

    public Integer getOrders()
    {
        return this.orders;
    }

    public void setOrders(Integer orders)
    {
        this.orders = orders;
    }

    public BigDecimal getLargest()
    {
        return this.largest;
    }

    public void setLargest(BigDecimal largest)
    {
        this.largest = largest;
    }

    public BigDecimal getSmallest()
    {
        return this.smallest;
    }

    public void setSmallest(BigDecimal smallest)
    {
        this.smallest = smallest;
    }

    public Calendar getFirst()
    {
        return this.first;
    }

    public void setFirst(Calendar date)
    {
        this.first = date;
    }

    public String firstTime()
    {
        return TIME_FORMAT.format(this.first.getTime());
    }

    public Calendar getLast()
    {
        return this.last;
    }

    public void setLast(Calendar date)
    {
        this.last = date;
    }

    public String lastTime()
    {
        return TIME_FORMAT.format(this.last.getTime());
    }

    public BigDecimal getRate()
    {
        return this.rate;
    }

    public void setRate(BigDecimal rate)
    {
        this.rate = rate;
    }

    public Integer getErrors()
    {
        return this.errors;
    }

    public void setErrors(Integer errors)
    {
        this.errors = errors;
    }

    public BigDecimal getCostOfErrors()
    {
        return null == this.costOfErrors ? BigDecimal.ZERO : this.costOfErrors;
    }

    public void setCostOfErrors(BigDecimal costOfErrors)
    {
        this.costOfErrors = costOfErrors;
    }

    @Override
    public String toString()
    {
        return "ESPSale [\ncostOfErrors=" + this.costOfErrors + "\nerrors=" + this.errors + "\nfirst="
            + TIME_FORMAT.format(this.first.getTime()) + "\nlargest=" + this.largest + "\nlast="
            + TIME_FORMAT.format(this.last.getTime()) + "\nname=" + this.name + "\nnetSales=" + this.netSales + "\norders="
            + this.orders + "\nrate=" + this.rate + "\nsmallest=" + this.smallest + "]";
    }
}
