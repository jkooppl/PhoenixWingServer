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
@Table(name = "iq2_weekly_sales", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "weekly_sales_sequence", sequenceName = "iq2_weekly_sales_id", allocationSize = 1)
public class WeeklySales implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -3096679522809065973L;
    @Id
    @Column(name = "weekly_sales_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weekly_sales_sequence")
    private Integer id;
    @Column(name = "shop_id")
    private Integer shopId;
    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "sunday_of_sales_week")
    @Temporal(TemporalType.DATE)
    private Calendar sundayOfSalesWeek;

    @Column(name = "opening_inventory")
    private BigDecimal openingInventory = BigDecimal.valueOf(0, 2);

    // PURCHASES
    @Column(name = "purchase_commissary_purchases")
    private BigDecimal commissaryPurchases = BigDecimal.valueOf(0, 2);
    @Column(name = "purchase_sysco")
    private BigDecimal sysco = BigDecimal.valueOf(0, 2);
    @Column(name = "purchase_lilydale")
    private BigDecimal lilydale = BigDecimal.valueOf(0, 2);
    @Column(name = "purchase_pepsi")
    private BigDecimal pepsi = BigDecimal.valueOf(0, 2);
    @Column(name = "purchase_petty_cash")
    private BigDecimal pettyCash = BigDecimal.valueOf(0, 2);
    @Column(name = "purchase_others")
    private BigDecimal others = BigDecimal.valueOf(0, 2);

    @Column(name = "closing_inventory")
    private BigDecimal closingInventory = BigDecimal.valueOf(0, 2);

    @Column(name = "paper_closing_inventory")
    private BigDecimal paperClosingInventory = BigDecimal.valueOf(0, 2);

    @Column(name = "cleaning_closing_inventory")
    private BigDecimal cleaningClosingInventory = BigDecimal.valueOf(0, 2);

    // FOOD AND BEVERAGE SALES = NET OF GST(WEEKLY)
    @Column(name = "food_and_beverage_sales")
    private BigDecimal foodAndBeverageSales = BigDecimal.valueOf(0, 2);

    @Column(name = "labour_cost")
    private BigDecimal labourCost = BigDecimal.valueOf(0, 2);

    // FLAG
    @Column(name = "submitted")
    private boolean submitted = false;

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

    public Integer getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId)
    {
        this.employeeId = employeeId;
    }

    public Calendar getSundayOfSalesWeek()
    {
        return sundayOfSalesWeek;
    }

    public void setSundayOfSalesWeek(Calendar sundayOfSalesWeek)
    {
        this.sundayOfSalesWeek = sundayOfSalesWeek;
    }

    public BigDecimal getOpeningInventory()
    {
        return openingInventory;
    }

    public void setOpeningInventory(BigDecimal openingInventory)
    {
        this.openingInventory = openingInventory;
    }

    public BigDecimal getCommissaryPurchases()
    {
        return commissaryPurchases;
    }

    public void setCommissaryPurchases(BigDecimal commissaryPurchases)
    {
        this.commissaryPurchases = commissaryPurchases;
    }

    public BigDecimal getSysco()
    {
        return sysco;
    }

    public void setSysco(BigDecimal sysco)
    {
        this.sysco = sysco;
    }

    public BigDecimal getLilydale()
    {
        return lilydale;
    }

    public void setLilydale(BigDecimal lilydale)
    {
        this.lilydale = lilydale;
    }

    public BigDecimal getPepsi()
    {
        return pepsi;
    }

    public void setPepsi(BigDecimal pepsi)
    {
        this.pepsi = pepsi;
    }

    public BigDecimal getOthers()
    {
        return others;
    }

    public void setOthers(BigDecimal others)
    {
        this.others = others;
    }

    public BigDecimal getClosingInventory()
    {
        return closingInventory;
    }

    public void setClosingInventory(BigDecimal closingInventory)
    {
        this.closingInventory = closingInventory;
    }

    public BigDecimal getFoodAndBeverageSales()
    {
        return foodAndBeverageSales;
    }

    public void setFoodAndBeverageSales(BigDecimal foodAndBeverageSales)
    {
        this.foodAndBeverageSales = foodAndBeverageSales;
    }

    public BigDecimal getTotalPurchases()
    {
        return this.getCommissaryPurchases().add(this.getSysco()).add(this.getLilydale()).add(this.getPepsi())
            .add(this.getPettyCash()).add(this.getOthers());
    }

    public BigDecimal getCostOfSales()
    {
        return this.getOpeningInventory().add(this.getTotalPurchases()).subtract(this.closingInventory);
    }

    public BigDecimal getFoodCost()
    {
        if (this.getFoodAndBeverageSales().compareTo(BigDecimal.valueOf(0)) != 0)
            return this.getCostOfSales().divide(this.getFoodAndBeverageSales(), 4, BigDecimal.ROUND_HALF_UP);
        else
            return BigDecimal.valueOf(0, 4);
    }

    public boolean isSubmitted()
    {
        return submitted;
    }

    public void setSubmitted(boolean submitted)
    {
        this.submitted = submitted;
    }

    public BigDecimal getPettyCash()
    {
        return pettyCash;
    }

    public void setPettyCash(BigDecimal pettyCash)
    {
        this.pettyCash = pettyCash;
    }

    public BigDecimal getLabourCost()
    {
        return labourCost;
    }

    public void setLabourCost(BigDecimal labourCost)
    {
        this.labourCost = labourCost;
    }

    public BigDecimal getPaperClosingInventory()
    {
        return paperClosingInventory;
    }

    public void setPaperClosingInventory(BigDecimal paperClosingInventory)
    {
        this.paperClosingInventory = paperClosingInventory;
    }

    public BigDecimal getCleaningClosingInventory()
    {
        return cleaningClosingInventory;
    }

    public void setCleaningClosingInventory(BigDecimal cleaningClosingInventory)
    {
        this.cleaningClosingInventory = cleaningClosingInventory;
    }

}
