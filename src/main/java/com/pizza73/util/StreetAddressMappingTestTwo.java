package com.pizza73.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.CharUtils;
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
import com.pizza73.model.enums.StreetDirectionCode;
import com.pizza73.model.enums.StreetType;
import com.pizza73.service.LookupManager;

/**
 * StreetAddressMappingTest.java TODO comment me
 * 
 * @author chris 9-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public class StreetAddressMappingTestTwo
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
        StreetAddressMappingTestTwo sam = new StreetAddressMappingTestTwo();
        sam.setUp();
        Map<Integer, String> notMapped = new TreeMap<Integer, String>();
        Map<Integer, String> mapped = new TreeMap<Integer, String>();
        List<Integer> notFound = new ArrayList<Integer>();
        OnlineCustomer oc = null;
//        Integer[] ints = {538,592,610};
//        for (int x = 0; x < ints.length; x++)
//        {
//            Integer i = ints[x];
//            
        for(int i = 1; i < 6377; i++)
        {
            try
            {
                oc = 
                    (OnlineCustomer) sam.lookupMgr.get(
                        OnlineCustomer.class, Integer.valueOf(i));
                sam.sf.getCurrentSession().evict(oc);
                sam.parseAddress(oc.getAddress(), sam.codeMap);
                
//                String streetNumber = a.getStreetNumber().trim();
//                int diff = 6 - streetNumber.length();
//                for(int x = 0; x < diff; x++)
//                {
//                    streetNumber = "0" + streetNumber;
//                }
//                
//                String direction = a.getStreetDirection().trim();
//                if(StringUtils.isBlank(direction) &&  a.getCity().getId().equals(1))
//                {
//                    direction = "NW";
//                }
//                
//                String streetName = a.getStreetName().trim();
//                String streetType = a.getStreetType().trim();
//                
//                
//                
//                int seqCode = 0;
//                if(StringUtils.isNumeric(streetNumber))
//                {
//                    seqCode = Integer.valueOf(streetNumber.trim()) % 2;
//                    if(seqCode == 0)
//                    {
//                        seqCode = 2;
//                    }
//                }
                
                StreetAddress sa = new StreetAddress(oc.getAddress());
                
                StreetAddress foundSa = sam.orderDao.findShop(sa);
                if(foundSa == null)
                {
                    System.out.println("NOT MAPPED: " + oc.getId());
                    String streetAddress = oc.getAddress().getStreetAddress();
                    oc.getAddress().populateStreetAddress();
                    notMapped.put(oc.getId(), "StreetAddress: " + streetAddress + " parsed address: " + oc.getAddress().getStreetAddress());
                }
                else
                {
                    String streetAddress = oc.getAddress().getStreetAddress();
                    oc.getAddress().populateStreetAddress();
                    mapped.put(oc.getId(), "StreetAddress: " + streetAddress + " parsed address: " + oc.getAddress().getStreetAddress());
                }
                oc = null;
            } 
            catch(ObjectRetrievalFailureException e)
            {
                notFound.add(i);
            } 
            catch(NumberFormatException e)
            {
                System.out.println("NUMBER FORMAT EX: NOT MAPPED: " + oc.getId());
                notMapped.put(oc.getId(), oc.getAddress().getStreetAddress());
            }
            catch(Exception e)
            {
                System.out.println("error parsing customer for id: " + i);
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
        
        reportFile = 
            new File(f, "mapped-" + System.currentTimeMillis());
        writer = null;
        try
        {
            writer = new PrintWriter(reportFile);
            Iterator<Integer> iter2 = mapped.keySet().iterator();
            while (iter2.hasNext())
            {
                Integer id = iter2.next();
                writer.println(
                    "online_id: " + id + "\tADRESS: " + mapped.get(id));
            }
        }
        finally
        {
            writer.close();
        }
    }
    
    private void parseAddress(Address address, Map<String,String> codeMap)
    {
        
//        Address address = new Address();
        String streetNumber = "";
        String streetName = "";
        String streetSuffix = "";
        String streetType = "";
        String streetDirection = "";
        String suiteNumber = "";
        
//        sa.setCityId(address.getCity().getId());
//        
        String tempAddress = address.getStreetAddress().toUpperCase();
        // replace unwanted characters with spaces.
        tempAddress = tempAddress.replace("S. E.", "SE");
        tempAddress = tempAddress.replace("N. E.", "NE");
        tempAddress = tempAddress.replace("S. W.", "SW");
        tempAddress = tempAddress.replace("N. W.", "NW");
        tempAddress = tempAddress.replace(".E.", "E");
        tempAddress = tempAddress.replace(".W.", "W");
        tempAddress = tempAddress.replace("N.", "N");
        tempAddress = tempAddress.replace("S.", "S");
        tempAddress = tempAddress.replace('(', ' ');
        tempAddress = tempAddress.replace(')', ' ');
        tempAddress = tempAddress.replace('/', ' ');
        tempAddress = tempAddress.replace('\\', ' ');
        tempAddress = tempAddress.replace('-', ' ');
        tempAddress = tempAddress.replace(". ", " ");
        tempAddress = tempAddress.replace(".", " ");
        tempAddress = tempAddress.replace(',', ' ');
        tempAddress = tempAddress.replace(" ND"," ");
        tempAddress = tempAddress.replace(" TH "," ");
//        tempAddress = tempAddress.replace(" ST ST "," ST ");
//        tempAddress = tempAddress.replace(" RD RD "," RD ");
        tempAddress = tempAddress.replace(" NORTH EAST"," NE ");
        tempAddress = tempAddress.replace(" SOUTH EAST"," SE ");
        tempAddress = tempAddress.replace(" NORTH WEST"," NW ");
        tempAddress = tempAddress.replace(" SOUTH WEST"," SW ");
        tempAddress = tempAddress.replace(" RD RD "," RD ");
        
        for(int i = 0; i < 10; i++)
        {
            String replacee = i + " ";
            tempAddress = tempAddress.replace(i + "ND", replacee);
            tempAddress = tempAddress.replace(i + "TH", replacee);
            tempAddress = tempAddress.replace(i + "RD", replacee);
            tempAddress = tempAddress.replace(i + "STREET", replacee + "ST ");
            tempAddress = tempAddress.replace(i + "ST", replacee + "ST ");
            tempAddress = tempAddress.replace(i + "AVE", replacee + "AVE ");
            tempAddress = tempAddress.replace(i + "AV", replacee + "AVE ");
        }
        tempAddress = tempAddress.replace(" ST ST"," ST ");
        tempAddress = tempAddress.replace(" RD RD "," RD ");

        // remove unwanted spaces.
        StringTokenizer st = new StringTokenizer(tempAddress);
        StringBuffer buf = new StringBuffer();
        while (st.hasMoreTokens()) {
            buf.append(' ').append(st.nextToken());
        }
        tempAddress = buf.toString().trim();
        
        //split string
        String[] results = tempAddress.split(" ");
        
        // assume first result is street number.
        streetNumber = results[0];
        // unless it contains a #
        int streetNumberIndex = 1;
        if(streetNumber.indexOf('#') != -1)
        {
            suiteNumber = results[0];
            streetNumber = results[1];
            streetNumberIndex = 2;
        }
        
        // find street suffix if there is one.
        if(!StringUtils.isNumeric(streetNumber))
        {
            char[] charArray = streetNumber.toCharArray();
            String number = "";
            int count = 0;
            for (count = 0; count < charArray.length; count++)
            {
                char c = charArray[count];
                if(CharUtils.isAsciiNumeric(c))
                {
                    number += c;
                }
                else 
                {
                    break;
                }
                
            }
            streetSuffix = charArray[count] + "";
            streetNumber = number;
        }
        
        
        int diff = 6 - streetNumber.length();
        for(int i = 0; i < diff; i++)
        {
            streetNumber = "0" + streetNumber;
        }
        
        boolean directionFound = false;
        boolean typeFound = false;
        
        String[] streetTypes = StreetType.typeValues();
        String[] streetDirections = StreetDirectionCode.codeValues();
        String[] streetDirectionsTwo = {"NORTH", "SOUTH", "WEST", "EAST"}; 
        // use this to determine the address name (ie 91 or KINGSWOOD)
        int count = results.length;
        for(int i = results.length; i > streetNumberIndex; i--)
        {
            String result = "";
            result = results[i-1].toUpperCase();
            if(!directionFound && (ArrayUtils.contains(streetDirections, result)
                || ArrayUtils.contains(streetDirectionsTwo, result)))
            {
                //Street Direction found
                directionFound = true;
                count--;
                if(result.equals("NORTH"))
                {
                    streetDirection = "N";
                }
                else if(result.equals("SOUTH"))
                {
                    streetDirection = "S";
                }
                else if(result.equals("EAST"))
                {
                    streetDirection = "E";
                }
                else if(result.equals("WEST"))
                {
                    streetDirection = "W";
                }
                else
                {
                    streetDirection = result;    
                }
                
            }
            else if(!typeFound && 
                (ArrayUtils.contains(streetTypes, result) 
                        || codeMap.containsKey(result)))
            {
                String typeCodeValue = result;
                if(codeMap.containsKey(result))
                {
                    typeCodeValue = codeMap.get(result);
                }
                //street type found.
                typeFound = true;
                streetType = typeCodeValue;
                count--;
            }
        }
        
        for (int i = streetNumberIndex; i < count; i++)
        {
            streetName = streetName + results[i].toUpperCase() + " ";
        }
       
        
        address.setStreetNumber(streetNumber);
        address.setStreetName(StringUtils.trimToEmpty(streetName));
        address.setStreetType(streetType);
        address.setStreetDirection(streetDirection);
        address.setStreetSuffix(streetSuffix);
        if(StringUtils.isNotBlank(address.getSuiteNumber()))
        {
            address.setSuiteNumber(suiteNumber);
        }
    }
}
