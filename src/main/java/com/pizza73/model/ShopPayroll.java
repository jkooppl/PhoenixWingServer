/**
 * 
 */
package com.pizza73.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author chris
 * 
 */
public class ShopPayroll implements Serializable
{
    // FIELDS
    private static final long serialVersionUID = 1589205041392258083L;
    private Shop shop;
    private List<Employee> employees = new ArrayList<Employee>();
    private Integer year;
    private Integer payPeriod;
    private Double totalOTHours = 0d;
    private Double totalRegHours = 0d;
    private Double totalStatHours = 0d;

    public ShopPayroll()
    {
    }

    public ShopPayroll(Shop shop)
    {
        this.shop = shop;
    }

    public void setShop(Shop shop)
    {
        this.shop = shop;
    }

    public Shop getShop()
    {
        return this.shop;
    }

    public void setEmployees(List<Employee> list)
    {
        this.employees = list;
    }

    public List<Employee> getEmployees()
    {
        return this.employees;
    }

    public void addEmployee(Employee emp)
    {
        this.employees.add(emp);
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public Integer getYear()
    {
        return this.year;
    }

    public void setPayPeriod(Integer period)
    {
        this.payPeriod = period;
    }

    public Integer getPayPeriod()
    {
        return this.payPeriod;
    }

    public Double getTotalOTHours()
    {
        return totalOTHours;
    }

    public void setTotalOTHours(Double totalOTHours)
    {
        this.totalOTHours = totalOTHours;
    }

    public Double getTotalRegHours()
    {
        return totalRegHours;
    }

    public void setTotalRegHours(Double totalRegHours)
    {
        this.totalRegHours = totalRegHours;
    }

    public Double getTotalStatHours()
    {
        return totalStatHours;
    }

    public void setTotalStatHours(Double totalStatHours)
    {
        this.totalStatHours = totalStatHours;
    }
}