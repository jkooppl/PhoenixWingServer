package com.pizza73.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pizza73.model.StreetAddress;
import com.pizza73.service.LookupManager;
import com.pizza73.service.OrderManager;

/**
 * ExcelOnlineCustomerConversion.java TODO comment me
 * 
 * @author chris 8-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public class PostalcodeCheck {
    protected ClassPathXmlApplicationContext ctx;
    // private final SessionFactory sf = null;
    private OrderManager orderManager;
    private LookupManager lookupMgr;

    private static final short POSTAL_CODE = 0;

    public static void main(String[] args) throws Exception
    {
        PostalcodeCheck pc = new PostalcodeCheck();
        pc.setUp();
        File file = new File("/pizza73/utilapps/data/Pcodes-10081067.xls");
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        // pc.check67(wb);
        // pc.check8(wb);
        pc.update67(wb);
        pc.update8(wb);
    }

    @SuppressWarnings("unchecked")
    public void setUp()
    {
        String[] paths = { "util-applicationContext-resources.xml",
                "applicationContext.xml" };
        ctx = new ClassPathXmlApplicationContext(paths);
        orderManager = (OrderManager) ctx.getBean("orderManager");
        lookupMgr = (LookupManager) ctx.getBean("lookupManager");
        System.out.println("INFO: starting up");
    }

    public void check67(HSSFWorkbook wb)
    {
        HSSFSheet sheet = wb.getSheet("Pcodes-1067");
        int rowCount = 1;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;
        String postalCode = null;
        while (rowCount <= lastRow)
        {
            row = sheet.getRow(rowCount);
            postalCode = getPostalCode(row);
            if (postalCode != null)
            {
                StreetAddress sa = orderManager.findShopForPostalCode(postalCode);
                if (sa == null)
                    System.out.println("postalcode " + postalCode
                            + " is not in the database.");
                else if (sa.getShopId() == 8)
                    System.out.println("postalcode " + postalCode
                            + " is mapped to shop 8");
                else if (sa.getShopId() == 0)
                    System.out.println("will sign postalcode " + postalCode
                            + " to shop 67");
                else if (sa.getShopId() != 8 && sa.getShopId() != 67)
                    System.out.println("postalcode " + postalCode
                            + " is not mapped to shop 8&67 but shop " + sa.getShopId());
            }
            rowCount++;
        }

    }

    public void check8(HSSFWorkbook wb)
    {
        HSSFSheet sheet = wb.getSheet("Pcodes-1008leftover");
        int rowCount = 1;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;
        String postalCode = null;
        while (rowCount <= lastRow)
        {
            row = sheet.getRow(rowCount);
            postalCode = getPostalCode(row);
            if (postalCode != null)
            {
                StreetAddress sa = orderManager.findShopForPostalCode(postalCode);
                if (sa == null)
                    System.out.println("postalcode " + postalCode
                            + " is not in the database.");
                else if (sa.getShopId() == 67)
                    System.out.println("postalcode " + postalCode
                            + " is mapped to shop 67");
                else if (sa.getShopId() == 0)
                    System.out.println("will sign postalcode " + postalCode
                            + " to shop 8");
                else if (sa.getShopId() != 8 && sa.getShopId() != 67)
                    System.out.println("postalcode " + postalCode
                            + " is not mapped to shop 8&67 but shop " + sa.getShopId());
            }
            rowCount++;
        }

    }

    public void update67(HSSFWorkbook wb)
    {
        HSSFSheet sheet = wb.getSheet("Pcodes-1067");
        int rowCount = 1;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;
        String postalCode = null;
        while (rowCount <= lastRow)
        {
            row = sheet.getRow(rowCount);
            postalCode = getPostalCode(row);
            if (postalCode != null)
            {
                StreetAddress sa = orderManager.findShopForPostalCode(postalCode);
                if (sa == null)
                    System.out.println("postalcode " + postalCode
                            + " is not in the database.");
                else if (sa.getShopId() != 8 && sa.getShopId() != 67
                        && sa.getShopId() != 0)
                    System.out.println("postalcode " + postalCode
                            + " is not mapped to shop 8&67 but shop " + sa.getShopId());
                else if (sa.getShopId() == 0)
                {
                    System.out.println("Assigning postalcode " + postalCode
                            + " to shop 67");
                    List<StreetAddress> saes = orderManager
                            .findAllStreetAddressesForPostalCode(postalCode);
                    for (int i = 0; i < saes.size(); i++)
                    {
                        StreetAddress tmp = saes.get(i);
                        tmp.setShopId(67);
                        this.lookupMgr.save(tmp);
                    }
                }
                else if (sa.getShopId() == 8)
                {
                    System.out.println("postalcode " + postalCode
                            + " is mapped from shop 8 to shop67");
                    List<StreetAddress> saes = orderManager
                            .findAllStreetAddressesForPostalCode(postalCode);
                    for (int i = 0; i < saes.size(); i++)
                    {
                        StreetAddress tmp = saes.get(i);
                        tmp.setShopId(67);
                        this.lookupMgr.save(tmp);
                    }
                }
            }
            rowCount++;
        }

    }

    public void update8(HSSFWorkbook wb)
    {
        HSSFSheet sheet = wb.getSheet("Pcodes-1008leftover");
        int rowCount = 1;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;
        String postalCode = null;
        while (rowCount <= lastRow)
        {
            row = sheet.getRow(rowCount);
            postalCode = getPostalCode(row);
            if (postalCode != null)
            {
                StreetAddress sa = orderManager.findShopForPostalCode(postalCode);
                if (sa == null)
                    System.out.println("postalcode " + postalCode
                            + " is not in the database.");
                else if (sa.getShopId() != 8 && sa.getShopId() != 67
                        && sa.getShopId() != 0)
                    System.out.println("postalcode " + postalCode
                            + " is not mapped to shop 8&67 but shop " + sa.getShopId());
                else if (sa.getShopId() == 0)
                {
                    System.out.println("Assigning postalcode " + postalCode
                            + " to shop 8");
                    List<StreetAddress> saes = orderManager
                            .findAllStreetAddressesForPostalCode(postalCode);
                    for (int i = 0; i < saes.size(); i++)
                    {
                        StreetAddress tmp = saes.get(i);
                        tmp.setShopId(8);
                        this.lookupMgr.save(tmp);
                    }
                }
                else if (sa.getShopId() == 67)
                {
                    System.out.println("postalcode " + postalCode
                            + " is mapped from shop67 to shop 8");
                    List<StreetAddress> saes = orderManager
                            .findAllStreetAddressesForPostalCode(postalCode);
                    for (int i = 0; i < saes.size(); i++)
                    {
                        StreetAddress tmp = saes.get(i);
                        tmp.setShopId(8);
                        this.lookupMgr.save(tmp);
                    }
                }
            }
            rowCount++;
        }
    }

    private String getPostalCode(HSSFRow row)
    {

        HSSFCell cell = null;

        cell = row.getCell(POSTAL_CODE);
        if (cell != null)
        {
            return cell.getRichStringCellValue().getString().trim();
        }
        return null;
    }
}
