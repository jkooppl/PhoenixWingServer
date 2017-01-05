package com.pizza73.webapp.util;

import com.pizza73.model.Payroll;

public class SKShopPaymentCalculator implements ShopPaymentCalculator {

	public double calculate(Payroll payroll, Integer week) {
        if(week == 1)
        	return this.getTotalpayByFirstWeek(payroll);
        else if (week ==2)
            return this.getTotalpayBySecondWeek(payroll);
        else
        	return 0;
	}

	private double getTotalpayBySecondWeek(Payroll payroll) {
	       double NormalPay=0;
	       double OTPay=0;
	       double StatPay=0;
	       if(payroll == null)
	     	   return 0;
	       //stat Pay
	       StatPay=payroll.getWeekTwoStat()*1.5*payroll.getPayrollWage().doubleValue();
	       //OT
	       OTPay=payroll.getOvertimeHoursWeekTwo()*1.5*payroll.getPayrollWage().doubleValue();
	       //reg
	       NormalPay=payroll.getTotalHoursWeekTwo()*payroll.getPayrollWage().doubleValue();
	       return StatPay+OTPay+NormalPay;
	}

	private double getTotalpayByFirstWeek(Payroll payroll) {
	       double NormalPay=0;
	       double OTPay=0;
	       double StatPay=0;
	       if(payroll == null)
	     	   return 0;
	       //stat Pay
	       StatPay=payroll.getWeekOneStat()*1.5*payroll.getPayrollWage().doubleValue();
	       //OT
	       OTPay=payroll.getOvertimeHoursWeekOne()*1.5*payroll.getPayrollWage().doubleValue();
	       //reg
	       NormalPay=payroll.getTotalHoursWeekOne()*payroll.getPayrollWage().doubleValue();
	       return StatPay+OTPay+NormalPay;
	}

	public double calculate(double[] dailyHours,double statHours,double dailyMaxRegHours,double wage) {
		double normalPay=0;
		double normalHours=0;
		double OTPay=0;
		double OTHours=0;
		double statPay=0;
		double weeklyMaxRegHours=40;
		for(int i=0;i<dailyHours.length;i++){
			if(dailyHours[i]>dailyMaxRegHours){
				normalHours+=dailyMaxRegHours;
				OTHours+=dailyHours[i]-dailyMaxRegHours;
			}
			else
				normalHours+=dailyHours[i];
		}

	  if(normalHours>weeklyMaxRegHours){
		  OTHours+=normalHours-weeklyMaxRegHours;
		  normalHours=weeklyMaxRegHours;
	  }
	  normalPay=normalHours*wage;
	  OTPay=OTHours*1.5*wage;
	  statPay=statHours*1.5*wage;
      return normalPay+OTPay+statPay;
	}
}
