package com.pizza73.util;

import com.pizza73.model.Municipality;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import java.util.Calendar;

public class CoreStoreHoursUtil
{
    private static Logger log = Logger.getLogger(CoreStoreHoursUtil.class);

    public static final int[] HOLIDAY_EARLY_DAYS = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};

    public static final Integer[] XMAS_SHOP_PICKUP_EXCEPTIONS = new Integer[]{10, 30, 36, 42, 50, 53, 58, 61, 63};

    public static final int NEW_YEARS_EVE = 31;

    public static final int CHRISTMAS_EVE = 24;

    public static boolean checkForLunchOnline(Municipality city, String lunchCities)
    {
        lunchCities = lunchCities.replaceFirst(" ", "");
        String cities[] = lunchCities.split(",");
        for(String lunchCity : cities)
        {
            Integer lCity = Integer.parseInt(lunchCity);
            if(city.getId().equals(lCity))
            {
                log.warn("ONLINE_LUNCH FOR: " + city.getDisplayName());
                return true;
            }
        }
        return false;
    }

    public static boolean isChristmasEarlyHours(int month, int day)
    {
        final boolean isDecember = Calendar.DECEMBER == month;
        if(isDecember)
        {
            return ArrayUtils.indexOf(HOLIDAY_EARLY_DAYS, day) == ArrayUtils.INDEX_NOT_FOUND ? false : true;
        }

        return false;
    }

    public static boolean isNewYearsEve(int month, int day)
    {
        final boolean isDecember = Calendar.DECEMBER == month;
        if(isDecember)
        {
            return day == NEW_YEARS_EVE;
        }
        return false;
    }

    public static boolean isChristmasEve(int month, int day)
    {
        final boolean isDecember = Calendar.DECEMBER == month;
        if(isDecember)
        {
            return day == CHRISTMAS_EVE;
        }
        return false;
    }

    public static boolean isLunchtimeForSpecial(boolean bc)
    {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        final int min = now.get(Calendar.MINUTE);
        if(bc)
        {
            hour = hour - 1;
        }
        if(hour >= 11)
        {
            if(hour < 14)
            {
                return true;
            }
            if(hour == 14 && min <= 5)
            {
                return true;
            }
        }

        return false;
    }

    public static Boolean isEarlyWeek(int businessDay)
    {
        final boolean earlyWeek = businessDay == Calendar.MONDAY || businessDay == Calendar.TUESDAY || businessDay == Calendar.WEDNESDAY;
        log.debug("is early Week: " + earlyWeek);
        return earlyWeek;
    }
}
