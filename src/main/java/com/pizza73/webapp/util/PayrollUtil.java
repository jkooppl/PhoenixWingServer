package com.pizza73.webapp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.pizza73.model.PayYearPeriod;

public class PayrollUtil
{
    private static final Logger log = Logger.getLogger(PayrollUtil.class);
    private static final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    
    public static String payrollEndDate(final PayYearPeriod payPeriod, final Integer period)
    {
        String returnDate = "";
        Calendar startDate = payPeriod.getPayrollYearStartDate();
        if(null != startDate)
        {
            Calendar endDate = (Calendar) startDate.clone();
            endDate.add(Calendar.WEEK_OF_YEAR, period *2);        
            endDate.add(Calendar.DATE, -1);
            returnDate = df.format(endDate.getTime());
        }
        else
        {
            log.warn("start date is null");
        }
        return returnDate;
    }
}
