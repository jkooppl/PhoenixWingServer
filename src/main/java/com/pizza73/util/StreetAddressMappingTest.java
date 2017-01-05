package com.pizza73.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.pizza73.dao.OrderDao;
import com.pizza73.model.Address;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.StreetAddress;
import com.pizza73.model.StreetTypeCodeMap;
import com.pizza73.service.LookupManager;

/**
 * StreetAddressMappingTest.java TODO comment me
 * 
 * @author chris 9-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public class StreetAddressMappingTest
{
    protected FileSystemXmlApplicationContext ctx;
    private SessionFactory sf = null;
    private LookupManager lookupMgr;
    private OrderDao orderDao;
    private Map<String, String> codeMap;
    
    public void setLookupManager(LookupManager mgr)
    {
        this.lookupMgr = mgr;
    }

    public void setOrderDao(OrderDao dao)
    {
        this.orderDao = dao;
    }

    @SuppressWarnings("unchecked")
   public void setUp()
    {
        String[] paths = {
            "C:\\Documents and Settings\\chris\\My Documents\\dev\\eddOnline\\test\\service\\com\\pizza73\\service\\applicationContext-resources.xml",
            "C:\\Documents and Settings\\chris\\My Documents\\dev\\eddOnline\\test\\service\\com\\pizza73\\service\\applicationContext-hibernate.xml",
            "C:\\Documents and Settings\\chris\\My Documents\\dev\\eddOnline\\test\\service\\com\\pizza73\\service\\applicationContext-service.xml" };
        ctx = new FileSystemXmlApplicationContext(paths);
        sf = (SessionFactory) ctx.getBean("sessionFactory");
        Session s = sf.openSession();
        TransactionSynchronizationManager
                .bindResource(sf, new SessionHolder(s));
        lookupMgr = (LookupManager) ctx.getBean("lookupManager");
        orderDao = (OrderDao) ctx.getBean("orderDao");

        List<StreetTypeCodeMap> codes = 
            (List<StreetTypeCodeMap>) lookupMgr.getAll(StreetTypeCodeMap.class);
        codeMap = new TreeMap<String, String>();

        Iterator<StreetTypeCodeMap> codeIter = codes.iterator();
        while (codeIter.hasNext())
        {
            StreetTypeCodeMap code = codeIter.next();
            codeMap.put(code.getAlternateTypeCode().trim(), code.getTypeCode()
                    .trim());
        }
    }

    public static void main(String[] args) throws Exception
    {
        StreetAddressMappingTest sam = new StreetAddressMappingTest();
        sam.setUp();
        Map<Integer, String> notMapped = new TreeMap<Integer, String>();
        List<Integer> notFound = new ArrayList<Integer>();
        OnlineCustomer oc = null;
        for(int i = 6377; i < 6616; i++)
        {
            try
            {
                oc = 
                    (OnlineCustomer) sam.lookupMgr.get(
                        OnlineCustomer.class, Integer.valueOf(i));
                Address a = oc.getAddress();
                
                String streetNumber = a.getStreetNumber().trim();
                int diff = 6 - streetNumber.length();
                for(int x = 0; x < diff; x++)
                {
                    streetNumber = "0" + streetNumber;
                }
                
                String direction = a.getStreetDirection().trim();
                if(StringUtils.isBlank(direction) &&  a.getCity().getId().equals(1))
                {
                    direction = "NW";
                }
                
                String streetName = a.getStreetName().trim();
                String streetType = a.getStreetType().trim();
                
                
                
                int seqCode = 0;
                if(StringUtils.isNumeric(streetNumber))
                {
                    seqCode = Integer.valueOf(streetNumber.trim()) % 2;
                    if(seqCode == 0)
                    {
                        seqCode = 2;
                    }
                }
                
                StreetAddress sa = 
                    new StreetAddress(streetNumber, streetName, streetType, 
                        direction, seqCode + "", a.getCity().getId());
                
                StreetAddress foundSa = sam.orderDao.findShop(sa);
                if(foundSa == null)
                {
                    System.out.println(
                        "OC id: " + oc.getId() + " address: " + a.getSuiteNumber() 
                        + " " + streetNumber + " " + streetName + " " 
                        + streetType + " " + direction + " "
                        + a.getCity().getDisplayName());
                    System.out.println("NOT MAPPED: " + oc.getId());
                    a.populateStreetAddress();
                    notMapped.put(oc.getId(), oc.getAddress().getStreetAddress());
                }
            } 
            catch(ObjectRetrievalFailureException e)
            {
                notFound.add(i);
            } 
            catch(NumberFormatException e)
            {
                System.out.println("NUMBER FORMAT EX: NOT MAPPED: " + oc.getId());
                oc.getAddress().populateStreetAddress();
                notMapped.put(oc.getId(), oc.getAddress().getStreetAddress());
            }
        }
        
        File f = new File("importReports");
        if (!f.exists())
        {
            f.mkdirs();
        }
        File reportFile = 
            new File(f, "notMapped-" + System.currentTimeMillis());
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(reportFile);
            Iterator<Integer> iter2 = notMapped.keySet().iterator();
            while (iter2.hasNext())
            {
                Integer id = iter2.next();
                writer.println(
                    "online_id: " + id + "\tADRESS: " + notMapped.get(id));
            }
        }
        finally
        {
            writer.close();
        }
        
        reportFile = 
            new File(f, "notFound-" + System.currentTimeMillis());
        writer = null;
        try
        {
            writer = new PrintWriter(reportFile);
            Iterator<Integer> iter2 = notFound.iterator();
            while (iter2.hasNext())
            {
                Integer id = iter2.next();
                writer.println("online_id: " + id);
            }
        }
        finally
        {
            writer.close();
        }
    }
}
