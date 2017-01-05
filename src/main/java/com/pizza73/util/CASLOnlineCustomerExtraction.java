package com.pizza73.util;

import com.pizza73.dao.OrderDao;
import com.pizza73.model.Municipality;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.service.LookupManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDateTime;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by chris on 2014-06-26.
 */
public class CASLOnlineCustomerExtraction
{
    protected ClassPathXmlApplicationContext ctx;
    private SessionFactory sf = null;
    private LookupManager mgr;
    private OrderDao dao;

    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void setLookupManager(final LookupManager mgr)
    {
        this.mgr = mgr;
    }

    public void setOrderDao(final OrderDao dao)
    {
        this.dao = dao;
    }

    public void setUp()
    {
        final String[] paths = { "util-applicationContext-resources.xml", "applicationContext.xml" };
        this.ctx = new ClassPathXmlApplicationContext(paths);
        this.sf = (SessionFactory) this.ctx.getBean("sessionFactory");
        final Session s = this.sf.openSession();
        TransactionSynchronizationManager.bindResource(this.sf, new SessionHolder(s));
        this.mgr = (LookupManager) this.ctx.getBean("lookupManager");
        this.dao = (OrderDao) this.ctx.getBean("orderDao");
    }

    public static void main(final String[] args) throws Exception
    {
        final CASLOnlineCustomerExtraction oce = new CASLOnlineCustomerExtraction();
        oce.setUp();

        Calendar businessDay = (Calendar) oce.dao.businessDate().clone();
        LocalDateTime businessDate = new LocalDateTime(businessDay);

        final File f = new File("/pizza73/utilapps/mailingList");
        if (!f.exists())
        {
            f.mkdirs();
        }

int count = 0;
        int month = 18;
        for(int i=0; i <= 18; i=i+2)
        {
            LocalDateTime queryStartDate = businessDate.minusMonths(month);
            LocalDateTime queryEndDate = businessDate.minusMonths(month - 2);
//            if(i == 0)
//            {
//                System.out.println("initial query");
//                queryEndDate = businessDate.minusMonths(18 - 2);
//            }
            System.out.println("startDate: " + queryStartDate.toString() + " end date: " + queryEndDate.toString());
            List<OnlineCustomer> customers = oce.dao.caslOrders(queryStartDate.toDate(), queryEndDate.toDate());
            System.out.println("customer count: " + customers.size());

            writeList(customers, f, i, businessDate);
            count++;
            if(count>10)
            {
                System.out.println("breaking");
                break;
            }
            month = month -2;
        }
    }

    private static void writeList( List<OnlineCustomer> customers, File f, int index, LocalDateTime date)
    {
        final File reportFile = new File(f, "caslCustomers-" + index + "-" + date.getYear() + date.getMonthOfYear() + date.getDayOfMonth());
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(reportFile);
            writer.println("E-Mail Address,Name,City,Inbound Phone Number,OPT In Date, comment,");
            for(OnlineCustomer customer : customers)
            {
                final Municipality city = customer.getAddress().getCity();

                String inboundPhone = "";
                if (city.getPhone() != null)
                {
                    inboundPhone = city.getPhone().substring(3, 6) + "-" + city.getPhone().substring(6);

                }
                String optInDate = "";
                if(null != customer.getOptInDate())
                {
                    optInDate = DF.format(customer.getOptInDate().getTime());
                }
                writer.println(customer.getEmail().trim() + "," + customer.getName().trim() + ","
                    + customer.getAddress().getCity().getName() + "," + inboundPhone + "," + optInDate + ",customer order,");
            }
        }
        catch(IOException io)
        {
            System.out.println(io.getMessage());
        }
        finally
        {
            writer.close();
        }
    }
}
