import org.joda.time.DateTime;

public class HoursTest
{
    public static void main(String[] args)
    {
        // Calendar bd = Calendar.getInstance();
        // DateTime dt = new DateTime(bd);
        DateTime dt = DateTime.now();
        // dt = dt.minusMonths(1);
        // dt = dt.plusDays();
        System.out.println("dt: " + dt.toString());
        dt = dt.plusDays(1);
        System.out.println("dt + 1 day: " + dt.toString());
        DateTime dt2 = new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), 2, 0, 0);

        System.out.println("dt2: " + dt2.toString());
        System.out.println("month of year: " + dt2.getMonthOfYear());
    }
}
