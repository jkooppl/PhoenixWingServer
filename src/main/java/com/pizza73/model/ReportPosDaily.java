package com.pizza73.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "iq2_report_pos_daily", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "report_pos_daily_sequence", sequenceName = "iq2_report_pos_daily_id_seq", allocationSize = 1)
public class ReportPosDaily implements Serializable
{
    private static final long serialVersionUID = 3165885782668630241L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_pos_daily_sequence")
    private Integer id;

    @Column(name = "shop_id")
    private Integer shopId;

    @Column(name = "number_of_orders")
    private Integer numberOfOrders;

    @Column(name = "business_date")
    @Temporal(TemporalType.DATE)
    private Calendar businessDate;

    @Column(name = "net_sales")
    private BigDecimal netSales = BigDecimal.valueOf(0, 2);

    @Column(name = "gst")
    private BigDecimal gst = BigDecimal.valueOf(0, 2);

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getShopId()
    {
        return shopId;
    }

    public void setShopId(Integer shopId)
    {
        this.shopId = shopId;
    }

    public Integer getNumberOfOrders()
    {
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders)
    {
        this.numberOfOrders = numberOfOrders;
    }

    public Calendar getBusinessDate()
    {
        return businessDate;
    }

    public void setBusinessDate(Calendar businessDate)
    {
        this.businessDate = businessDate;
    }

    public BigDecimal getNetSales()
    {
        return netSales;
    }

    public void setNetSales(BigDecimal netSales)
    {
        this.netSales = netSales;
    }

    public BigDecimal getGst()
    {
        return gst;
    }

    public void setGst(BigDecimal gst)
    {
        this.gst = gst;
    }

}
