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
@Table(name = "iq_historical_daily_sales_accounting_data", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "iq_historical_daily_sales_accounting_data_sequence", sequenceName = "iq_historical_daily_sales_accounting_data_id_seq", allocationSize = 1)
public class IqHistoricalDailySalesAccountingData implements Serializable
{
    /**
    * 
    */
    private static final long serialVersionUID = 243734246937377592L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iq_historical_daily_sales_accounting_data_sequence")
    private Integer Id;

    @Column(name = "shop_id")
    private Integer shopId;

    @Column(name = "business_date")
    @Temporal(TemporalType.DATE)
    private Calendar businessDate;

    @Column(name = "net_sales")
    private BigDecimal netSales = BigDecimal.valueOf(0, 2);

    public Integer getId()
    {
        return Id;
    }

    public void setId(Integer id)
    {
        Id = id;
    }

    public Integer getShopId()
    {
        return shopId;
    }

    public void setShopId(Integer shopId)
    {
        this.shopId = shopId;
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
}
