package com.pizza73.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.pizza73.dao.OrderDao;
import com.pizza73.model.Address;
import com.pizza73.model.Municipality;
import com.pizza73.model.StreetAddress;
import com.pizza73.model.StreetTypeCodeMap;
import com.pizza73.service.LookupManager;

/**
 * PostalCodeMappingPercentage.java TODO comment me
 * 
 * @author chris 8-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public class PostalCodeMappingPercentage
{
    protected FileSystemXmlApplicationContext ctx;
    private SessionFactory sf = null;
    private LookupManager lookupMgr;
    private OrderDao orderDao;
    private Map<String, String> codeMap;
    private Map<String, String> directionMap;

//    private static final short ORDER_ID = 0;
    private static final short ADDRESS = 1;
    private static final short SUITE = 2;
    private static final short NUMBER = 3;
    private static final short NAME = 4;
    private static final short TYPE = 5;
    private static final short DIRECTION = 6;
    private static final short POSTAL_CODE = 8;
    private static final short CITY = 7;

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

        List<StreetTypeCodeMap> codes = (List<StreetTypeCodeMap>) lookupMgr
                .getAll(StreetTypeCodeMap.class);
        codeMap = new TreeMap<String, String>();

        Iterator<StreetTypeCodeMap> codeIter = codes.iterator();
        while (codeIter.hasNext())
        {
            StreetTypeCodeMap code = codeIter.next();
            codeMap.put(code.getAlternateTypeCode().trim(), code.getTypeCode()
                    .trim());
        }

        directionMap = new TreeMap<String, String>();
        directionMap.put("SE", "SE");
        directionMap.put("S.E", "SE");
        directionMap.put("S.E.", "SE");
        directionMap.put("S E", "SE");
        directionMap.put("S", "S");
        directionMap.put("SOUTH", "S");
        directionMap.put("NE", "NE");
        directionMap.put("N.E", "NE");
        directionMap.put("N.E.", "NE");
        directionMap.put("N E", "NE");
        directionMap.put("N", "N");
        directionMap.put("NORTH", "N");
        directionMap.put("SW", "SW");
        directionMap.put("S.W", "SW");
        directionMap.put("S.W.", "SW");
        directionMap.put("S W", "SW");
        directionMap.put("NW", "NW");
        directionMap.put("N.W", "NW");
        directionMap.put("N.W.", "NW");
        directionMap.put("N W", "NW");
        directionMap.put("W", "W");
        directionMap.put("WEST", "W");
        directionMap.put("E", "E");
        directionMap.put("EAST", "E");
    }

    
    public static void main(String[] args) throws Exception
    {
        PostalCodeMappingPercentage eocc = new PostalCodeMappingPercentage();
        eocc.setUp();
        File file = new File("C:\\Documents and Settings\\chris\\My Documents\\dev\\eddOnline\\metadata\\excel\\postalCodeTest4.xls");
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        eocc.importData(wb);
        
        
    }
    
    public Map<String, String> importData(HSSFWorkbook wb) throws FileNotFoundException
    {
        Map<String, String> notMatched = new HashMap<String, String>();
        Map<String, String> pcOnlyMapped = new HashMap<String, String>();
        HSSFSheet sheet = wb.getSheetAt(0);
        // Assume has header row
        int rowCount = 1;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;

        while (rowCount <= lastRow)
        {
            row = sheet.getRow(rowCount);
            Address a = importCustomer(row, rowCount);
            if (a != null)
            {
                if(StringUtils.isNotBlank(a.getPostalCode()))
                {
                    try
                    {
                        a.parseAddress(codeMap);
                        StreetAddress sa = new StreetAddress(a);
                        StreetAddress addressMapping = orderDao.findShop(sa);
                        StreetAddress pcMapping = orderDao.findShopForPostalCode(a.getPostalCode());
                        if(addressMapping != null && pcMapping != null)
                        {
                            if(!addressMapping.getShopId().equals(pcMapping.getShopId()))
                            {
                                notMatched.put(a.getPostalCode(), a.getStreetAddress());
                            }
                        }
                        else 
                        {
                            pcOnlyMapped.put(a.getPostalCode(), 
                                a.getStreetAddress() + " - " + a.getStreetNumber() 
                                + " " + a.getStreetName() + " " + a.getStreetType() 
                                + " " + a.getStreetDirection());
                        }
                    }
                    catch (DataAccessException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
            }
            rowCount++;
        }

        writeToFile(notMatched, "notMatched");
        writeToFile(pcOnlyMapped, "postalCodeOnly");
        return notMatched;
    }

    /**
     * @param notMatched
     * @throws FileNotFoundException 
     */
    private void writeToFile(Map<String, String> notMatched, String fileName) throws FileNotFoundException
    {
        File f = new File("importReports");
        if (!f.exists())
        {
            f.mkdirs();
        }
        File reportFile = new File(f, fileName + "-"
                + System.currentTimeMillis());
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(reportFile);
            Iterator<String> iter = notMatched.keySet().iterator();
            while (iter.hasNext())
            {
                String postalCode = iter.next();
                writer.println(
                    "Postal code: " + postalCode + "\tnot mapped to ADRESS: " 
                    + notMatched.get(postalCode));
            }
        }
        finally
        {
            writer.close();
        }
        
    }


    private Address importCustomer(HSSFRow row, int rowCount)
    {
        HSSFCell cell = null;

        Address a = new Address();
        cell = row.getCell(ADDRESS);
        if (cell != null)
        {
            String s = "";
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
            {
                s = cell.getNumericCellValue() + "";
            }
            else
            {
                s = cell.getStringCellValue();
            }
            a.setStreetAddress(s.trim());
        }

        cell = row.getCell(SUITE);
        if (cell != null)
        {
            String s = "";
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
            {
                s = cell.getNumericCellValue() + "";
            }
            else
            {
                s = cell.getStringCellValue();
            }
            
            a.setSuiteNumber(s.trim());
        }

        cell = row.getCell(NUMBER);
        if (cell != null)
        {
            String s = "";
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
            {
                s = cell.getNumericCellValue() + "";
            }
            else
            {
                s = cell.getStringCellValue();
            }
            
            a.setStreetNumber(s.trim());
        }

        cell = row.getCell(NAME);
        if (cell != null)
        {
            String s = "";
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
            {
                s = cell.getNumericCellValue() + "";
            }
            else
            {
                s = cell.getStringCellValue();
            }
            
            a.setStreetName(s.trim());
        }
        cell = row.getCell(TYPE);
        if (cell != null)
        {
            String s = "";
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
            {
                s = cell.getNumericCellValue() + "";
            }
            else
            {
                s = cell.getStringCellValue();
            }
            
            a.setStreetType(s.trim());
        }

        cell = row.getCell(POSTAL_CODE);
        if (cell != null)
        {
            String s = cell.getStringCellValue();
            a.setPostalCode(s.trim());
        }
        
        cell = row.getCell(DIRECTION);
        if (cell != null)
        {
            String s = cell.getStringCellValue();
            a.setStreetDirection(s);
        }
        
        cell = row.getCell(CITY);
        BigDecimal id = null;
        if (cell != null)
        {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
            {
                double i = cell.getNumericCellValue();
                id = BigDecimal.valueOf(i);
            }
            else
            {
                String i = cell.getStringCellValue();
                id = BigDecimal.valueOf(Integer.valueOf(i));
            }
            
            Municipality city = (Municipality) lookupMgr.get(
                    Municipality.class, Integer.valueOf(id.intValue()));
            a.setCity(city);
        }
   
        return a;
    }
}
