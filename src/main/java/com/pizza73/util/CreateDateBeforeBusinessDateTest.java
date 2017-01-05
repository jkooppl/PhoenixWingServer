package com.pizza73.util;

import java.util.Calendar;

public class CreateDateBeforeBusinessDateTest
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {

        final Calendar newCal = Calendar.getInstance();
        newCal.set(Calendar.YEAR, 2011);
        newCal.set(Calendar.MONTH, Calendar.AUGUST);
        newCal.set(Calendar.DATE, 5);

        final Calendar businessDate = Calendar.getInstance();
        newCal.add(Calendar.DATE, 32);

        if (businessDate.before(newCal))
        {
            System.out.println("true");
        }

        else
        {
            System.out.println("false");
        }
    }

}
