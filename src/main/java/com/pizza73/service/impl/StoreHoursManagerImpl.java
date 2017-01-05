package com.pizza73.service.impl;

import com.pizza73.model.HolidayHours;
import com.pizza73.model.Municipality;
import com.pizza73.model.enums.CityEnum;
import com.pizza73.model.mobile.StoreOpen;
import com.pizza73.service.HolidayHoursManager;
import com.pizza73.service.StoreHoursManager;
import com.pizza73.util.CityUtil;
import com.pizza73.util.CoreStoreHoursUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

@Service("storeHoursManager")
public class StoreHoursManagerImpl extends UniversalManagerImpl implements StoreHoursManager
{
    Logger log = Logger.getLogger(StoreHoursManagerImpl.class);

    private static final int[] EARLY_OPENING_DAYS = new int[] { Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY,
            Calendar.SUNDAY };
    private static final int[] EARLY_CLOSING_EXCEPTION_DAYS = new int[] { Calendar.MONDAY, Calendar.TUESDAY,
            Calendar.WEDNESDAY, Calendar.THURSDAY };
    private static final int OPENING_TIME = 16;
    private static final int EARLY_OPENING_TIME = 11;
    private static final int CLOSING_TIME = 2;
    private static final int EARLY_CLOSING_TIME = 24;
    private static final int LATE_CLOSING_TIME = 3;

    private static final int CHRISTMAS_EVE = 24;
    private static final int CHRISTMAS_DAY = 25;

    @Autowired
    private HolidayHoursManager holidayMgr;

    public StoreOpen isStoreOpen(Integer cityId)
    {
        StoreOpen storeOpen = checkForStoreOpen(cityId);

        return storeOpen;
    }

    private StoreOpen checkForStoreOpen(Integer cityId)
    {
        boolean open = false;

        TimeZone tzMT = TimeZone.getTimeZone("America/Edmonton");
        Calendar now = Calendar.getInstance(tzMT);
        int openingOffset = 0;

        Calendar businessDate = this.businessDate();

        int hour = now.get(Calendar.HOUR_OF_DAY);
        final int year = businessDate.get(Calendar.YEAR);
        final int month = businessDate.get(Calendar.MONTH);
        final int dayOfMonth = businessDate.get(Calendar.DATE);
        final boolean isDecember = Calendar.DECEMBER == month;

        final String openForLunchInfo = this.infoValueForVariable("open_for_lunch");
        final Boolean lunchOpen = Boolean.valueOf(openForLunchInfo);

        Municipality city = null;
        if (null != cityId && !cityId.equals(0))
        {
            city = (Municipality) this.get(Municipality.class, cityId);
        }

        if (null != city)
        {
            boolean bc = CityEnum.PRINCE_GEORGE.isEqualToCityId(cityId) || CityEnum.FORT_ST_JOHNS.isEqualToCityId(cityId);
            if (CityEnum.SASKATOON.isEqualToCityId(cityId))
            {
                if (!tzMT.inDaylightTime(now.getTime()))
                {
                    now.add(Calendar.HOUR_OF_DAY, 1);
                    hour = now.get(Calendar.HOUR_OF_DAY);
                }
            }
            else if (bc)
            {
                openingOffset = 1;
            }
        }

        if (isDecember)
        {

            if (dayOfMonth == CHRISTMAS_DAY)
            {
                log.warn("CHRISTMAS DAY");
                return new StoreOpen(open, "Merry Christmas!  We are closed today but will be open normal hours tomorrow.");
            }
            if (dayOfMonth == CHRISTMAS_EVE && (hour >= 20 || hour <= 10))
            {
                log.warn("CHRISTMAS EVE");
                return new StoreOpen(open,
                    "Merry Christmas!  We are closing early Christmas Eve and will re-open normal hours on boxing day.  ");
            }
        }

        int closingDayOffset = 1;
        int openingHour = OPENING_TIME;
        int closingHour = CLOSING_TIME;
        int clossingMinute = 58;
        int businessDay = businessDate.get(Calendar.DAY_OF_WEEK);

        String lunchCities = this.infoValueForVariable("lunch_online");

        Boolean lunchOnline = false;
        if (null != city)
        {
            lunchOnline = CoreStoreHoursUtil.checkForLunchOnline(city, lunchCities);
        }
        boolean openForLunch = lunchOpen && lunchOnline;

        if (ArrayUtils.contains(EARLY_OPENING_DAYS, businessDay) || openForLunch)
        {
            openingHour = EARLY_OPENING_TIME;
        }

        if ((businessDay == Calendar.FRIDAY || businessDay == Calendar.SATURDAY) && !earlyWeekendCity(city))
        {
            closingHour = LATE_CLOSING_TIME;
        }

        boolean earlyClosingDayException = Arrays.binarySearch(EARLY_CLOSING_EXCEPTION_DAYS, businessDay) >= 0;
        boolean earlyClosingException = earlyClosingDayException && earlyWeekdayCity(city);
        boolean okotoksEarlyWeek = CityEnum.OKOTOKS.isEqualToCityId(cityId) && earlyClosingDayException;

        if (businessDay == Calendar.SUNDAY || earlyClosingException)
        {
            closingHour = EARLY_CLOSING_TIME;
            closingDayOffset = 0;
        }
        else if(okotoksEarlyWeek)
        {
            closingHour = 1;
            closingDayOffset = 1;
        }

        if (CoreStoreHoursUtil.isChristmasEarlyHours(month, dayOfMonth))
        {
            openingHour = 11;
        }

        HolidayHours holiday = holidayMgr.forBusinessDay();
        if (null != holiday)
        {
            log.debug("holiday not null");
            boolean useHoliday = false;
            if (null == holiday.getCityException() || holiday.getCityException().isEmpty())
            {
                log.debug("holiday has NO city exception");
                useHoliday = true;
                if (!holiday.isStrict())
                {
                    int holidayOpen = holiday.getOpen().intValue();
                    if (openingHour < holidayOpen)
                    {
                        useHoliday = false;
                    }
                }
            }
            else if (null != city)
            {
                log.debug("city is not null: " + city.getId());
                useHoliday = !holiday.hasCityException(city.getId());
            }
            if (useHoliday)
            {
                openingHour = holiday.getOpen();
                log.debug("opening hour: " + openingHour);
                closingHour = holiday.getClose();
                log.debug("closing hour: " + closingHour);
                if (closingHour > 4 && closingHour <= 24)
                {
                    closingDayOffset = 0;
                }
                else
                {
                    closingDayOffset = 1;
                }
                log.debug("closing day offset: " + closingDayOffset);
            }
        }

        openingHour += openingOffset;
        Calendar openTime = Calendar.getInstance();
        openTime.set(Calendar.YEAR, year);
        openTime.set(Calendar.MONTH, month);
        openTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        openTime.set(Calendar.HOUR_OF_DAY, openingHour);
        openTime.set(Calendar.MINUTE, 0);
        openTime.set(Calendar.SECOND, 0);
        openTime.set(Calendar.MILLISECOND, 0);

        Calendar close = Calendar.getInstance();
        int closingDayWithOffset = dayOfMonth + closingDayOffset;
        close.set(Calendar.YEAR, year);
        close.set(Calendar.MONTH, month);
        close.set(Calendar.DAY_OF_MONTH, closingDayWithOffset);
        close.set(Calendar.HOUR_OF_DAY, closingHour - 1);
        close.set(Calendar.MINUTE, clossingMinute);
        close.set(Calendar.SECOND, 0);
        close.set(Calendar.MILLISECOND, 0);

        // Closing time for new year's eve is 4 am so stop online orders at 3:58
        if (CoreStoreHoursUtil.isNewYearsEve(month, dayOfMonth))
        {
            if (closingDayOffset == 0)
            {
                closingDayWithOffset = dayOfMonth + 1;
                close.set(Calendar.DAY_OF_MONTH, closingDayWithOffset);
            }
            closingHour = 3;
            close.set(Calendar.HOUR_OF_DAY, closingHour);
        }

        if (now.compareTo(openTime) > 0 && now.compareTo(close) < 0)
        {
            open = true;
        }
        String message = "";
        if (!open)
        {
            message = closedMessage(now, openTime, close, businessDay);
        }
        final String onlineOrdering = this.infoValueForVariable("online_ordering");
        final Boolean isOnlineOrdering = Boolean.valueOf(onlineOrdering);
        if (!isOnlineOrdering)
        {
            log.warn("online ordering turned off");
            message = "We are currently experiencing server issues, however, "
                + "you can still order by phone.  Online ordering will re-open shortly.";
            open = false;
        }
        log.warn("Store open message: " + message);
        StoreOpen storeOpen = new StoreOpen(open, message);
        return storeOpen;
    }

    private boolean earlyWeekdayCity(Municipality city)
    {
        boolean earlyCity = true;
        if (null != city)
        {
            if (!CityUtil.weekDayEarlClose(city.getId()))
            {
                earlyCity = false;
            }
        }

        return earlyCity;
    }

    private boolean earlyWeekendCity(Municipality city)
    {
        boolean earlyCity = true;
        if (null != city)
        {
            if (!CityUtil.weekEndEarlClose(city.getId()))
            {
                earlyCity = false;
            }
        }

        return earlyCity;
    }

    private String closedMessage(Calendar now, Calendar open, Calendar close, int businessDay)
    {
        int minute = now.get(Calendar.MINUTE);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int openingHour = open.get(Calendar.HOUR_OF_DAY);
        int closingHour = close.get(Calendar.HOUR_OF_DAY);

        if (now.after(close))
        {
            switch (businessDay)
            {
            // opening hour of the next day
            case Calendar.SUNDAY:
                openingHour = 16;
                break;
            case Calendar.MONDAY:
                openingHour = 16;
                break;
            case Calendar.TUESDAY:
                openingHour = 16;
                break;
            case Calendar.WEDNESDAY:
                openingHour = 11;
                break;
            case Calendar.THURSDAY:
                openingHour = 11;
                break;
            case Calendar.FRIDAY:
                openingHour = 11;
                break;
            case Calendar.SATURDAY:
                openingHour = 11;
                break;
            default:
                openingHour = 16;
                break;
            }
            final int month = now.get(Calendar.MONTH);
            final boolean isDecember = (Calendar.DECEMBER == month);
            if(isDecember)
            {
                final int day = now.get(Calendar.DATE);
                if(CoreStoreHoursUtil.isChristmasEarlyHours(month, day))
                {
                    openingHour = 11;
                }
            }
        }

        String countDown = "The mobile store is now closed.  We'll be open in ";
        final int hourDiff = openingHour - hour;
        final int minuteDiff = 60 - minute;
        if (hourDiff > 1)
        {
            int countDownHour = hourDiff - 1;
            if (minuteDiff == 60)
            {
                countDownHour = hourDiff;
            }
            countDown += countDownHour + "";
            if (countDownHour > 1)
            {
                countDown += " hours ";
            }
            else
            {
                countDown += " hour ";
            }
        }
        if (minuteDiff < 60)
        {
            if (hourDiff > 1)
            {
                countDown += "and ";
            }
            if (minuteDiff > 1)
            {
                countDown += minuteDiff + " minutes.";
            }
            else
            {
                countDown += minuteDiff + " minute.";
            }
        }
        if (hourDiff == 1 && minuteDiff == 60)
        {
            countDown += "1 hour.";
        }
        if (hour == closingHour - 1)
        {
            countDown = "The online store is now closed.  We are open at "
                + "4pm Monday to Wednesday and 11am Thursday to Sunday.";
        }
        countDown += "  Please feel free to browse the menu in the meantime.";
        return countDown;
    }
}
