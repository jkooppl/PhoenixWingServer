/**
 * 
 */
package com.pizza73.webapp.base.model;

import java.io.Serializable;

import com.pizza73.model.Shop;

/**
 * @author chris
 *
 */
public class PayrollSearch implements Serializable
{
   //FIELDS
   private static final long serialVersionUID = 3071274346518104019L;
   private Shop shop = null;
   private Integer payrollPeriod = Integer.valueOf(0);
   private Integer payrollYear = Integer.valueOf(0);
   
   public PayrollSearch(){}

   public Shop getShop()
   {
      return shop;
   }

   public void setShop(Shop shop)
   {
      this.shop = shop;
   }

   public Integer getPayrollPeriod()
   {
      return payrollPeriod;
   }

   public void setPayrollPeriod(Integer payrollPeriod)
   {
      this.payrollPeriod = payrollPeriod;
   }

   public Integer getPayrollYear()
   {
      return payrollYear;
   }

   public void setPayrollYear(Integer payrollYear)
   {
      this.payrollYear = payrollYear;
   }
}
