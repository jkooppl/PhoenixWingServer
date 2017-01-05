package com.pizza73.util;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.pizza73.dao.UserDao;
import com.pizza73.model.report.OrderStats;

/**
 * 
 * @author chris 9-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public class OnlinOrderStats
{
    protected ClassPathXmlApplicationContext ctx;
    private SessionFactory sf = null;
    private UserDao uDao;

    public void setUserDao(UserDao dao)
    {
        this.uDao = dao;
    }

    public void setUp()
    {
        String[] paths = { "util-applicationContext-resources.xml", "applicationContext.xml" };
        ctx = new ClassPathXmlApplicationContext(paths);
        sf = (SessionFactory) ctx.getBean("sessionFactory");
        Session s = sf.openSession();
        TransactionSynchronizationManager.bindResource(sf, new SessionHolder(s));
        uDao = (UserDao) ctx.getBean("userDao");
    }

    public static void main(String[] args) throws Exception
    {
        OnlinOrderStats oce = new OnlinOrderStats();
        oce.setUp();
        Map<String, OrderStats> orderCount = new TreeMap<String, OrderStats>();

        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 2007);
        date.set(Calendar.MONTH, Calendar.JANUARY);
        date.set(Calendar.DAY_OF_MONTH, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(Calendar.YEAR, 2007);
        endDate.set(Calendar.MONTH, Calendar.JANUARY);
        endDate.set(Calendar.DAY_OF_MONTH, 31);

        Query query = null;
        BigInteger count = null;
        BigInteger totalCount = null;

        System.out.println(oce.uDao.loadUserByUsername("chris.huisman@shaw.ca").getUsername());
        while (endDate.after(date))
        {
            String year = date.get(Calendar.YEAR) + "";
            String month = "1";
            String day = date.get(Calendar.DAY_OF_MONTH) + "";

            try
            {
                System.out.println(year + "-" + month + "-" + day);
                if (month.length() == 1)
                {
                    month = "0" + month;
                }
                if (day.length() == 1)
                {
                    day = "0" + day;
                }

                String dateString = "" + year + "-" + month + "-" + day;
                System.out.println("DateString: " + dateString);
                query = oce.sf.getCurrentSession().createSQLQuery(
                    "select count(*) from iq2_sales_order where business_date= '" + dateString + "'");
                count = (BigInteger) query.list().get(0);
                System.out.println("COUNT: " + count);

                query = oce.sf.getCurrentSession().createSQLQuery(
                    "select count(*) from sales where order_date = '" + dateString + "'");
                totalCount = (BigInteger) query.list().get(0);
                System.out.println("TOTAL COUNT: " + totalCount);

                double ratiod = count.doubleValue() / totalCount.doubleValue() * 100;
                BigDecimal ratio = new BigDecimal(ratiod, new MathContext(4));
                System.out.println("RATIO: " + ratio);

                OrderStats orderStats = new OrderStats(count, totalCount, ratio);

                orderCount.put(dateString, orderStats);

                date.add(Calendar.DATE, 1);
            }
            catch (Exception e)
            {
                date.add(Calendar.DATE, 1);
                // do nothing
            }
        }

        File f = new File("orderStats");
        if (!f.exists())
        {
            f.mkdirs();
        }
        File reportFile = new File(f, "onlineOrderStats-" + System.currentTimeMillis());
        PrintWriter writer = null;

        try
        {
            writer = new PrintWriter(reportFile);
            writer.println("Date, Orders");
            Iterator<String> iter = orderCount.keySet().iterator();
            String key = null;
            while (iter.hasNext())
            {
                key = iter.next();
                OrderStats stats = orderCount.get(key);
                writer.println(key + "," + stats.getOnlineCount() + "," + stats.getTotalCount() + ","
                    + stats.getRatio());
            }
        }
        finally
        {
            writer.close();
        }

    }
}
