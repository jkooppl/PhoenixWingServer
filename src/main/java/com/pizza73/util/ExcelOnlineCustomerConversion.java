package com.pizza73.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.pizza73.dao.OrderDao;
import com.pizza73.model.Address;
import com.pizza73.model.Location;
import com.pizza73.model.Municipality;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.StreetAddress;
import com.pizza73.model.StreetTypeCodeMap;
import com.pizza73.model.enums.StreetDirectionCode;
import com.pizza73.model.enums.StreetType;
import com.pizza73.service.LookupManager;

/**
 * ExcelOnlineCustomerConversion.java TODO comment me
 * 
 * @author chris 8-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public class ExcelOnlineCustomerConversion
{
    protected FileSystemXmlApplicationContext ctx;
    private SessionFactory sf = null;
    private LookupManager lookupMgr;
    private OrderDao orderDao;
    private Map<String, String> codeMap;
    private Map<String, String> directionMap;

    private static final short ONLINE_ID = 0;
    private static final short PHONE = 1;
    private static final short NAME = 3;
    private static final short STREET_ADDRESS = 4;
    private static final short SUITE_NUMBER = 9;
    private static final short ADDRESS_COMMENT = 10;
    private static final short MUNICIPALITY_ID = 11;
    private static final short POSTAL_CODE = 13;
    private static final short LOCATION_CODE = 14;
    private static final short REPEAT_COUNT = 15;
    private static final short EMAIL = 17;
    private static final short PASSWORD = 18;
    private static final short SUBSCRIBED = 23;

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
        // "C:\\Documents and Settings\\chris\\My
        // Documents\\dev\\eddOnline\\test\\dao\\com\\pizza73\\dao\\applicationContext-resources.xml",
        // "C:\\Documents and Settings\\chris\\My
        // Documents\\dev\\eddOnline\\test\\dao\\com\\pizza73\\dao\\applicationContext-hibernate.xml"};
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

    public List<Object> importData(HSSFWorkbook wb)
    {
        List<Object> results = new ArrayList<Object>();
        Map<Integer, String> notMapped = new HashMap<Integer, String>();
        List<Integer> notSaved = new ArrayList<Integer>();

        HSSFSheet sheet = wb.getSheetAt(0);
        // Assume has header row
        int rowCount = 1;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;

        while (rowCount <= lastRow)
        {
            row = sheet.getRow(rowCount);
            OnlineCustomer oc = importCustomer(notMapped, row, rowCount);
            if (oc != null)
            {
                try
                {
                    lookupMgr.save(oc);
                    System.out.println(
                        "OnlineCustomerTransfer saved: " + oc.getId());

                }
                catch (DataAccessException e)
                {
                    notSaved.add(oc.getId());
                }
            }
            rowCount++;
        }
        results.add(notMapped);
        results.add(notSaved);

        return results;
    }

    private OnlineCustomer importCustomer(
            Map<Integer, String> notMapped, HSSFRow row, int rowCount)
    {
        OnlineCustomer oc = new OnlineCustomer();
        HSSFCell cell = null;

        cell = row.getCell(ONLINE_ID);
        if (cell != null)
        {
            double i = cell.getNumericCellValue();
            BigDecimal id = BigDecimal.valueOf(i);
            oc.setId(id.intValue());
        }

        cell = row.getCell(PHONE);
        if (cell != null)
        {
            double i = cell.getNumericCellValue();
            BigDecimal id = BigDecimal.valueOf(i);
            oc.setPhone(id.longValue() + "");
        }

        cell = row.getCell(NAME);
        if (cell != null)
        {
            String s = cell.getStringCellValue();
            oc.setName(s.trim());
        }

        cell = row.getCell(STREET_ADDRESS);
        if (cell != null)
        {
            String s = cell.getStringCellValue();
            Address address = this.parseAddress(s);

            oc.setAddress(address);
            cell = row.getCell(MUNICIPALITY_ID);
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
                oc.getAddress().setCity(city);
            }

            String direction = address.getStreetDirection();
            if (StringUtils.isBlank(direction) && id.equals(1))
            {
                direction = "NW";
            }
            StreetAddress sa = new StreetAddress();
            sa.setCityId(Integer.valueOf(id.intValue()));
            sa.setFromAddress(address.getStreetNumber());
            sa.setToAddress(address.getStreetNumber());
            sa.setStreetDirectionCode(direction);
            sa.setStreetTypeCode(address.getStreetType());
            sa.setStreetName(address.getStreetName());

            StreetAddress foundSa = orderDao.findShop(sa);
            if (foundSa == null)
            {
                notMapped.put(oc.getId(), address.getStreetAddress());
            }
        }

        cell = row.getCell(SUITE_NUMBER);
        if (cell != null)
        {
            String s = "";
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
            {
                double i = cell.getNumericCellValue();
                BigDecimal id = BigDecimal.valueOf(i);
                s = id.intValue() + "";
            }
            else
            {
                s = cell.getStringCellValue();
            }

            oc.getAddress().setSuiteNumber(s.trim());
        }
        cell = row.getCell(ADDRESS_COMMENT);
        if (cell != null)
        {
            String s = "";
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
            {
                double i = cell.getNumericCellValue();
                BigDecimal id = BigDecimal.valueOf(i);
                s = id.intValue() + "";
            }
            else
            {
                s = cell.getStringCellValue();
            }
            s = s.replace("\\r\\n", " ");
            oc.getAddress().setAddressComment(s.trim());
        }

        // cell = row.getCell(PROVINCE_CODE);
        // if(cell != null) {
        // String s = cell.getStringCellValue();
        // oc.getAddress().set(s.trim());
        // }
        cell = row.getCell(POSTAL_CODE);
        if (cell != null)
        {
            String s = cell.getStringCellValue();
            oc.getAddress().setPostalCode(s.trim());
        }
        cell = row.getCell(LOCATION_CODE);
        if (cell != null)
        {
            String s = cell.getStringCellValue();
            Location type = (Location) lookupMgr.get(Location.class, s);
            oc.getAddress().setLocationType(type);
        }
        // cell = row.getCell(REPEAT_COUNT);
        // if(cell != null) {
        // double i = cell.getNumericCellValue();
        // BigDecimal count = BigDecimal.valueOf(i);
        // oc.ser(count);
        // }
        // cell = row.getCell(PHONE);
        // if(cell != null) {
        // String s = cell.getStringCellValue();
        // oc.setPhone(s.trim());
        // }
        cell = row.getCell(EMAIL);
        if (cell != null)
        {
            String s = cell.getStringCellValue();
            oc.setEmail(s.trim());
        }
        cell = row.getCell(PASSWORD);
        if (cell != null)
        {
            String s = "";
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
            {
                double i = cell.getNumericCellValue();
                BigDecimal id = BigDecimal.valueOf(i);
                s = id.intValue() + "";
            }
            else
            {
                s = cell.getStringCellValue();
            }
            oc.setPassword(s.trim());
        }
        // cell = row.getCell(PHONE);
        // if(cell != null) {
        // String s = cell.getStringCellValue();
        oc.setEnabled(true);
        // }
        cell = row.getCell(SUBSCRIBED);
        if (cell != null)
        {
            String s = cell.getStringCellValue();
            if (s.equalsIgnoreCase("t"))
            {
                oc.setSubscribed(true);
            }
            else
            {
                oc.setSubscribed(false);
            }
        }
         cell = row.getCell(REPEAT_COUNT);
         if(cell != null) {
         double d = cell.getNumericCellValue();
         oc.setRepeatCount(BigDecimal.valueOf(d).intValue());
         }
        // cell = row.getCell(PHONE);
        // if(cell != null) {
        // String s = cell.getStringCellValue();
        // oc.setPhone(s.trim());
        // }
        // cell = row.getCell(PHONE);
        // if(cell != null) {
        // String s = cell.getStringCellValue();
        // oc.setPhone(s.trim());
        // }
        // cell = row.getCell(PHONE);
        // if(cell != null) {
        // String s = cell.getStringCellValue();
        // oc.setPhone(s.trim());
        // }

        return oc;
    }

    /**
     * @param s
     */
    private Address parseAddress(String s)
    {
        Address address = new Address();

        String tempAddress = s;
        // replace unwanted characters with spaces.
        tempAddress = tempAddress.replace('-', ' ');
        tempAddress = tempAddress.replace(". ", " ");
        tempAddress = tempAddress.replace(". ", " ");
        tempAddress = tempAddress.replace(',', ' ');
        tempAddress = tempAddress.replace('"', ' ');

        // remove unwanted spaces.
        StringTokenizer st = new StringTokenizer(tempAddress);
        StringBuffer buf = new StringBuffer();
        while (st.hasMoreTokens())
        {
            buf.append(' ').append(st.nextToken());
        }
        tempAddress = buf.toString().trim();

        String[] results = tempAddress.split(" ");

        // assume first result is street number.
        String streetNumber = results[0];
        String suiteNumber = "";
        // unless it contains a #
        int streetNumberIndex = 1;
        if (streetNumber.indexOf('#') != -1)
        {
            suiteNumber = results[0];
            streetNumber = results[1];
            streetNumberIndex = 2;
        }
        
        streetNumber = streetNumber.trim();
        String suffix = "";
        if(!StringUtils.isNumeric(streetNumber))
        {
            int length = streetNumber.length();
            suffix = streetNumber.substring(length -1);
            streetNumber = streetNumber.substring(0, length -2);
        }
        address.setStreetSuffix(suffix);
        address.setStreetNumber(streetNumber.trim());
        address.setSuiteNumber(suiteNumber.trim());
        streetNumber = streetNumber.trim();
        int diff = 6 - streetNumber.length();
        for (int i = 0; i < diff; i++)
        {
            streetNumber = "0" + streetNumber;
        }
//        address.setSearchNumber(streetNumber);

        boolean directionFound = false;
        boolean typeFound = false;

        String[] streetTypes = StreetType.typeValues();
        String[] streetDirections = StreetDirectionCode.codeValues();

        String streetName = "";

        // use this to determine the address name (ie 91 or KINGSWOOD)
        int count = results.length;
        for (int i = results.length; i > 0; i--)
        {
            String result = "";
            result = results[i - 1].toUpperCase();
            if (!directionFound
                    && (ArrayUtils.contains(streetDirections, result)
                    || directionMap.containsKey(result)))
            {
                // Street Direction found
                directionFound = true;
                count--;
                String direction = result.toUpperCase();
                direction = result.trim();
                if (directionMap.containsKey(direction))
                {
                    direction = directionMap.get(direction);
                }
                address.setStreetDirection(direction);
            }
            else if (!typeFound
                    && (ArrayUtils.contains(streetTypes, result) || codeMap
                            .containsKey(result)))
            {
                String typeCodeValue = result.toUpperCase();
                typeCodeValue = typeCodeValue.trim();
                if (codeMap.containsKey(typeCodeValue))
                {
                    typeCodeValue = codeMap.get(typeCodeValue);
                }
                // street type found.
                typeFound = true;
                address.setStreetType(typeCodeValue);
                count--;
            }
        }
        if (address.getStreetType() == null)
        {
            address.setStreetType("");
        }
        if (address.getStreetDirection() == null)
        {
            address.setStreetDirection("");
        }

        for (int i = streetNumberIndex; i < count; i++)
        {
            streetName = streetName + results[i].toUpperCase() + " ";
        }
        address.setStreetName(streetName.trim());
        address.setStreetAddress(s);

        return address;
    }
}
