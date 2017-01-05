package com.pizza73.webapp.util;

import com.pizza73.model.Payroll;

public interface ShopPaymentCalculator {
   public double calculate(Payroll payroll, Integer week);
   public double calculate(double[] dailyHours,double statHours,double dailyMaxRegHours,double wage);
}
