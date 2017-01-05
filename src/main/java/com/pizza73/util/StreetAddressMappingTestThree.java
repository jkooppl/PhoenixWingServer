package com.pizza73.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.pizza73.model.StreetTypeCodeMap;
import com.pizza73.service.LookupManager;

/**
 * StreetAddressMappingTest.java TODO comment me
 * 
 * @author chris 9-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public class StreetAddressMappingTestThree
{
    protected FileSystemXmlApplicationContext ctx;
    private SessionFactory sf = null;
    private LookupManager lookupMgr;
    private Map<String, String> codeMap;
    
    public void setLookupManager(LookupManager mgr)
    {
        this.lookupMgr = mgr;
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
        StreetAddressMappingTestThree sam = new StreetAddressMappingTestThree();
        sam.setUp();
        Map<Integer, String> notMapped = new TreeMap<Integer, String>();
        List<Integer> notFound = new ArrayList<Integer>();
        for(int i = 6377; i < 6616; i++)
        {
            try
            {

                
            }
            catch(DataAccessException e)
            {
                System.out.println(e.getMessage());
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
