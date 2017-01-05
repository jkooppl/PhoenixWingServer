package com.pizza73.webapp.util;

import com.pizza73.model.Payroll;

public class BCShopPaymentCalculator implements ShopPaymentCalculator
{

    public double calculate(Payroll payroll, Integer week)
    {
        if (week == 1)
            return this.getTotalpayByFirstWeek(payroll);
        else if (week == 2)
            return this.getTotalpayBySecondWeek(payroll);
        else
            return 0;
    }

    private double getTotalpayByFirstWeek(Payroll payroll)
    {
        double NormalPay = 0;
        double OTPay = 0;
        double StatPay = 0;

        Double OT1_5Hours = Double.valueOf(0);
        Double OT2Hours = Double.valueOf(0);
        if (payroll == null)
            return 0;
        // statPay
        if (payroll.getWeekOneStat() > 12)
            StatPay = (payroll.getWeekOneStat() - 12) * 2 * payroll.getPayrollWage().doubleValue();
        StatPay += (payroll.getWeekOneStat() - 12 >= 0 ? 12 : payroll.getWeekOneStat()) * 1.5
            * payroll.getPayrollWage().doubleValue();
        // OTPay
        this.calcualteBCOverTime(payroll.getWeekOneSun(), OT2Hours, OT1_5Hours);
        this.calcualteBCOverTime(payroll.getWeekOneMon(), OT2Hours, OT1_5Hours);
        this.calcualteBCOverTime(payroll.getWeekOneTues(), OT2Hours, OT1_5Hours);
        this.calcualteBCOverTime(payroll.getWeekOneWed(), OT2Hours, OT1_5Hours);
        this.calcualteBCOverTime(payroll.getWeekOneThurs(), OT2Hours, OT1_5Hours);
        this.calcualteBCOverTime(payroll.getWeekOneFri(), OT2Hours, OT1_5Hours);
        this.calcualteBCOverTime(payroll.getWeekOneSat(), OT2Hours, OT1_5Hours);
        OTPay += OT2Hours * 2 * payroll.getPayrollWage().doubleValue();
        OTPay += OT1_5Hours * 1.5 * payroll.getPayrollWage().doubleValue();
        // Reg
        NormalPay = payroll.getTotalHoursWeekOne() * payroll.getPayrollWage().doubleValue();
        return StatPay + OTPay + NormalPay;
    }

    private double getTotalpayBySecondWeek(Payroll payroll)
    {
        double NormalPay = 0;
        double OTPay = 0;
        double StatPay = 0;
        Double OT1_5Hours = Double.valueOf(0);
        Double OT2Hours = Double.valueOf(0);
        if (payroll == null)
            return 0;
        // statPay
        if (payroll.getWeekTwoStat() > 12)
            StatPay = (payroll.getWeekTwoStat() - 12) * 2 * payroll.getPayrollWage().doubleValue();
        StatPay += (payroll.getWeekTwoStat() - 12 >= 0 ? 12 : payroll.getWeekTwoStat()) * 1.5
            * payroll.getPayrollWage().doubleValue();
        // OTPay
        this.calcualteBCOverTime(payroll.getWeekTwoSun(), OT2Hours, OT1_5Hours);
        this.calcualteBCOverTime(payroll.getWeekTwoMon(), OT2Hours, OT1_5Hours);
        this.calcualteBCOverTime(payroll.getWeekTwoTues(), OT2Hours, OT1_5Hours);
        this.calcualteBCOverTime(payroll.getWeekTwoWed(), OT2Hours, OT1_5Hours);
        this.calcualteBCOverTime(payroll.getWeekTwoThurs(), OT2Hours, OT1_5Hours);
        this.calcualteBCOverTime(payroll.getWeekTwoFri(), OT2Hours, OT1_5Hours);
        this.calcualteBCOverTime(payroll.getWeekTwoSat(), OT2Hours, OT1_5Hours);
        OTPay += OT2Hours * 2 * payroll.getPayrollWage().doubleValue();
        OTPay += OT1_5Hours * 1.5 * payroll.getPayrollWage().doubleValue();
        // Reg
        NormalPay = payroll.getTotalHoursWeekTwo() * payroll.getPayrollWage().doubleValue();
        return StatPay + OTPay + NormalPay;
    }

    private void calcualteBCOverTime(double hours, Double OT2Hours, Double OT1_5Hours)
    {
        if (hours > 12)
        {
            OT2Hours = OT2Hours + hours - 12;
            hours = 12;
        }
        if (hours > 8)
            OT1_5Hours = OT1_5Hours + hours - 8;
    }

    public double calculate(double[] dailyHours, double statHours, double dailyMaxRegHours, double wage)
    {
        double normalPay = 0;
        double normalHours = 0;
        double OTPay = 0;
        double OTHours1_5 = 0;
        double OTHours2 = 0;
        double statPay = 0;
        double dailyBoundFor2XOTHours = 12;
        double weeklyMaxRegHours = 40;
        for (int i = 0; i < dailyHours.length; i++)
        {
            if (dailyHours[i] > dailyBoundFor2XOTHours)
            {
                normalHours += dailyMaxRegHours;
                OTHours1_5 += dailyBoundFor2XOTHours - dailyMaxRegHours;
                OTHours2 = dailyHours[i] - dailyBoundFor2XOTHours;
            }
            else if (dailyHours[i] > dailyMaxRegHours)
            {
                OTHours1_5 += dailyHours[i] - dailyMaxRegHours;
                normalHours += dailyMaxRegHours;
            }
            else
                normalHours += dailyHours[i];
        }
        if (normalHours > weeklyMaxRegHours)
        {
            OTHours1_5 += normalHours - weeklyMaxRegHours;
            normalHours = weeklyMaxRegHours;
        }
        normalPay = normalHours * wage;
        OTPay = OTHours1_5 * 1.5 * wage + OTHours2 * 2 * wage;
        statPay = statHours * 1.5 * wage;
        return normalPay + OTPay + statPay;
    }

}
