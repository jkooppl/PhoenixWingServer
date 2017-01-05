package com.pizza73;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PeriodEndDateTest
{
    public static void main(String[] args)
    {
        int a = 1 / 2;
        int b = 2 / 2;
        int c = 3 / 2;
        int d = 4 / 2;
        
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Calendar now = Calendar.getInstance();
//        2010-12-26
        now.set(Calendar.YEAR, 2010);
        now.set(Calendar.MONTH, Calendar.DECEMBER);
        now.set(Calendar.DATE, 26);
        Calendar endDate = (Calendar) now.clone();
        endDate.add(Calendar.WEEK_OF_YEAR, 17 *2);        
        endDate.add(Calendar.DATE, -1);

        System.out.println("now: " + df.format(now.getTime()));
        System.out.println("endDate: " + df.format(endDate.getTime()));
    }
}
