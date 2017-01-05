import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.pizza73.model.mobile.StoreOpen;
import com.pizza73.service.impl.StoreHoursManagerImpl;

public class StoreHoursTest
{
    // protected ClassPathXmlApplicationContext ctx;
    // private SessionFactory sf = null;
    // private StoreHoursManager mgr;
    //
    // public void setStoreHoursManager(final StoreHoursManager mgr)
    // {
    // this.mgr = mgr;
    // }
    //
    // public void setUp()
    // {
    // final String[] paths = { "uapplicationContext-resources.xml",
    // "applicationContext.xml" };
    // this.ctx = new ClassPathXmlApplicationContext(paths);
    // this.sf = (SessionFactory) this.ctx.getBean("sessionFactory");
    // final Session s = this.sf.openSession();
    // TransactionSynchronizationManager.bindResource(this.sf, new
    // SessionHolder(s));
    // this.mgr = (StoreHoursManager) this.ctx.getBean("storeHoursManager");
    // }

    public static void main(final String[] args) throws Exception
    {
        final StoreHoursTest storeHoursTest = new StoreHoursTest();
        // storeHoursTest.setUp();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, 17);
        c.set(Calendar.HOUR_OF_DAY, 2);

        StoreOpen storeOpen = storeHoursTest.isStoreOpen(Integer.valueOf(10), c);
        System.out.println(storeOpen.getMessage());
    }

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

    private static final int NEW_YEARS_EVE = 31;
    private static final int[] HOLIDAY_EARLY_DAYS = new int[] { 20, 21, 22, 23 };

    private static final int[] WEEKDAY_EARLY_CITIES = new int[] { 22, 23, 15, 14, 13, 16, 20, 18, 21, 10, 11, 20 };
    private static final int[] WEEKEND_EARLY_CITIES = new int[] { 22, 21, 18, 15, 23, 20 };

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

    public StoreOpen isStoreOpen(Integer cityId, Calendar c)
    {
        StoreOpen storeOpen = checkForStoreOpen(cityId, c);

        return storeOpen;
    }

    private StoreOpen checkForStoreOpen(Integer cityId, Calendar c)
    {
        boolean open = false;

        TimeZone tzMT = TimeZone.getTimeZone("MST7MDT");
        Calendar now = c;
        int openingOffset = 0;

        Calendar businessDate = Calendar.getInstance();

        int year = businessDate.get(Calendar.YEAR);
        int month = businessDate.get(Calendar.MONTH);
        int dayOfMonth = 16; // businessDate.get(Calendar.DATE);
        boolean isDecember = Calendar.DECEMBER == month;

        Boolean lunchOpen = true;

        // Municipality city = new Municipality();
        // city.se
        // if (null != cityId && !cityId.equals(0))
        // {
        // city = (Municipality) this.get(Municipality.class, cityId);
        // }

        // if (null != city)
        // {
        // if (city.getProvince().equalsIgnoreCase("SK"))
        // {
        // if (!tzMT.inDaylightTime(now.getTime()))
        // now.add(Calendar.HOUR, 1);
        // }
        // else if (city.getProvince().equalsIgnoreCase("BC"))
        // {
        // openingOffset = 1;
        // }
        // }

        int closingDayOffset = 1;
        int openingHour = OPENING_TIME;
        int closingHour = CLOSING_TIME;
        int clossingMinute = 58;
        int businessDay = Calendar.FRIDAY;// businessDate.get(Calendar.DAY_OF_WEEK);

        // Arrays.sort(EARLY_OPENING_DAYS);
        // Arrays.sort(WEEKDAY_EARLY_CITIES);
        // Arrays.sort(WEEKEND_EARLY_CITIES);

        // String lunchCities = "1,2";
        //
        // Boolean lunchOnline = true;
        // if (null != city)
        // {
        // lunchOnline = AuthenticationUtil.checkForLunchOnline(city,
        // lunchCities);
        // }
        // boolean openForLunch = lunchOpen && lunchOnline;
        //
        if (ArrayUtils.contains(EARLY_OPENING_DAYS, businessDay))// ||
                                                                 // openForLunch)
        {
            openingHour = EARLY_OPENING_TIME;
        }

        // String lunchCities = this.infoValueForVariable("lunch_online");
        //
        // Boolean lunchOnline = false;
        // if (null != city)
        // {
        // lunchOnline = AuthenticationUtil.checkForLunchOnline(city,
        // lunchCities);
        // }
        // boolean openForLunch = lunchOpen && lunchOnline;

        // if (openForLunch)
        // {
        // openingHour = EARLY_OPENING_TIME;
        // }

        if ((businessDay == Calendar.FRIDAY || businessDay == Calendar.SATURDAY) && !earlyWeekendCity(cityId))
        {
            closingHour = LATE_CLOSING_TIME;
        }

        boolean earlyClosingException = (ArrayUtils.contains(EARLY_CLOSING_EXCEPTION_DAYS, businessDay))
            && earlyWeekdayCity(cityId);
        if (businessDay == Calendar.SUNDAY || earlyClosingException)
        {
            closingHour = EARLY_CLOSING_TIME;
            closingDayOffset = 0;
        }

        if (isDecember && isHolidayEarlyHours(dayOfMonth))
        {
            openingHour = 11;
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

        System.out.println("now: " + df.format(now.getTime()));
        System.out.println("open: " + df.format(openTime.getTime()));
        System.out.println("close: " + df.format(close.getTime()));

        // Closing time for new year's eve is 4 am so stop online orders at 3:58
        if (month == Calendar.DECEMBER && dayOfMonth == NEW_YEARS_EVE)
        {
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

        final Boolean isOnlineOrdering = true;
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

    private boolean earlyWeekdayCity(Integer city)
    {
        boolean earlyCity = true;
        if (null != city)
        {
            if (!ArrayUtils.contains(WEEKDAY_EARLY_CITIES, city))
            {
                earlyCity = false;
            }
        }

        return earlyCity;
    }

    private boolean earlyWeekendCity(Integer city)
    {
        boolean earlyCity = true;
        if (null != city)
        {
            if (!ArrayUtils.contains(WEEKEND_EARLY_CITIES, city))
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

    private boolean isHolidayEarlyHours(int dayOfMonth)
    {
        return ArrayUtils.indexOf(HOLIDAY_EARLY_DAYS, dayOfMonth) == ArrayUtils.INDEX_NOT_FOUND ? false : true;
    }

}
