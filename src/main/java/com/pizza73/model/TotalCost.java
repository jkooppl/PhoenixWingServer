package com.pizza73.model;

import java.io.Serializable;

public class TotalCost implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String subtotal;
    private String delivery;
    private String enviroLevy;
    private String deposit;
    private String discount;
    private String gst;
    private String total;

    public TotalCost()
    {
    }

    public TotalCost(String subtotal, String delivery, String enviroLevy, String deposit, String discount, String gst,
        String total)
    {
        this.subtotal = subtotal;
        this.delivery = delivery;
        this.enviroLevy = enviroLevy;
        this.deposit = deposit;
        this.discount = discount;
        this.gst = gst;
        this.total = total;
    }

    public String getSubtotal()
    {
        return subtotal;
    }

    public void setSubtotal(String subtotal)
    {
        this.subtotal = subtotal;
    }

    public String getDelivery()
    {
        return delivery;
    }

    public void setDelivery(String delivery)
    {
        this.delivery = delivery;
    }

    public String getEnviroLevy()
    {
        return enviroLevy;
    }

    public void setEnviroLevy(String enviroLevy)
    {
        this.enviroLevy = enviroLevy;
    }

    public String getDeposit()
    {
        return deposit;
    }

    public void setDeposit(String deposit)
    {
        this.deposit = deposit;
    }

    public String getGst()
    {
        return gst;
    }

    public void setGst(String gst)
    {
        this.gst = gst;
    }

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }

    public String getDiscount()
    {
        return this.discount;
    }
}
