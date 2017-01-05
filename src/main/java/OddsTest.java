import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class OddsTest
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // Random rn = new Random();

        int grandPrizeCount = 0;
        int eddCount = 0;
        int maxOdds = 0;
        int popCount = 0;
        int theRestCount = 0;
        for (int i = 0; i < 240001; i++)
        {
            UUID u = UUID.randomUUID();

            Random rn = new Random();
            rn.setSeed(u.hashCode());

            Double interval = rn.nextDouble();

            if (interval < 0.000126)
            {
                grandPrizeCount++;
            }
            else if (interval < 0.00083)
            {
                eddCount++;
            }
            else if (interval < 0.083)
            {
                popCount++;
            }
            else if (interval < 0.5)
            {
                maxOdds++;
            }
            else
            {
                theRestCount++;
            }
        }

        System.out.println("GrandPrizes: " + grandPrizeCount);
        System.out.println("EDD count: " + eddCount);
        System.out.println("Pops: " + popCount);
        System.out.println("Max odds: " + maxOdds);
        System.out.println("The Rest: " + theRestCount);
        double d = 3;
        System.out.println(1 / d);

        double choices = 3;
        int one = 0;
        int two = 0;
        int three = 0;
        for (int j = 0; j < 240001; j++)
        {
            Double interval = getInterval();
            for (int i = 1; i <= 3; i++)
            {
                if (interval < (i / choices))
                {
                    if (i == 1)
                    {
                        one++;
                        break;
                    }
                    else if (i == 2)
                    {
                        two++;
                        break;
                    }
                    else
                    {
                        three++;
                        break;
                    }
                }
            }
        }
        System.out.println("ONE: " + one);
        System.out.println("TWO: " + two);
        System.out.println("THREE: " + three);
    }

    private static Double getInterval()
    {
        UUID u = UUID.randomUUID();

        Random rn = new Random();
        rn.setSeed(u.hashCode());

        return rn.nextDouble();
    }

}
