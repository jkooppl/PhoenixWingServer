package com.pizza73.util;

import com.pizza73.dao.OrderDao;
import com.pizza73.model.Municipality;
import com.pizza73.model.OnlineCustomer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;


/**
 * StreetAddressMappingTest.java TODO comment me
 *
 * @author chris 9-Mar-07
 *
 * @Copyright Flying Pizza 73
 */
//		final Query query = oce.sf.getCurrentSession().createSQLQuery("select max(online_id) from iq2_online_customer");
//		final Integer maxId = (Integer) query.list().get(0);
//		System.out.println("Row Count: " + maxId);
//        261355
//		for (int i = 256925; i < maxId + 1; i++)
//		{
//			try
//			{
//				oc = (OnlineCustomer) oce.mgr.get(OnlineCustomer.class, Integer.valueOf(i));
//				if (oc.isSubscribed() && oc.isEnabled())
//				{
//					emails.add(oc);
//					System.out.println("Adding: " + oc.getEmail());
//				}
//			}
//			catch (final ObjectRetrievalFailureException e)
//			{
//				System.out.println("Couldn't find object for: " + i);
//			}
//		}
//				final Integer lo = oce.dao.lastOrder(ocEmail);
//				String lastOrder = "";
//				if (lo != null)
//				{
//					lastOrder = lo + "";
//				}

public class OnlineCustomerEmailExtraction
{
	protected ClassPathXmlApplicationContext ctx;
	private SessionFactory sf = null;
//	private LookupManager mgr;
	private OrderDao dao;

//	public void setLookupManager(final LookupManager mgr)
//	{
//		this.mgr = mgr;
//	}

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
		this.dao = (OrderDao) this.ctx.getBean("orderDao");
	}

	public static void main(final String[] args)
	{
		final OnlineCustomerEmailExtraction oce = new OnlineCustomerEmailExtraction();
		oce.setUp();
		System.out.println("Started");
		final File f = new File("/pizza73/utilapps/mailingList");
		if (!f.exists())
		{
			f.mkdirs();
		}
		final File reportFile = new File(f, "emails-" + System.currentTimeMillis());
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(reportFile);
			writer.println("E-Mail Address,Name,City,Inbound Phone Number");


            //last date: 2015-06-25
            LocalDate start = new LocalDate(2015, 6, 25);

            List<OnlineCustomer> customers = oce.dao.expressedConsentUsers(start.toDate());
			for(OnlineCustomer oc : customers)
			{
                String email = oc.getEmail();
                System.out.println(email);
				final Municipality city = oc.getAddress().getCity();

				String inboundPhone = "";
				if (city.getPhone() != null)
				{
					inboundPhone = city.getPhone().substring(3, 6) + "-" + city.getPhone().substring(6);

				}
				writer.println(email.trim() + "," + oc.getName().trim() + ","
						+ oc.getAddress().getCity().getName() + "," + inboundPhone);
			}
		}
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
		finally
		{
            if(null != writer)
            {
                writer.close();
            }
		}
	}
}
