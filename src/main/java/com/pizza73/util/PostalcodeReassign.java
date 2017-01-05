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
public class PostalcodeReassign {
    protected ClassPathXmlApplicationContext ctx;
    private OrderManager orderManager;
    private LookupManager lookupMgr;

    private static final short POSTAL_CODE = 0;

    public static void main(String[] args) throws Exception
    {
        PostalcodeReassign pc = new PostalcodeReassign();
        pc.setUp();
        File file = new File(args[0]);
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        pc.update74(wb);
    }

    public void setUp()
    {
        String[] paths = { "util-applicationContext-resources.xml",
                "applicationContext.xml" };
        ctx = new ClassPathXmlApplicationContext(paths);
        orderManager = (OrderManager) ctx.getBean("orderManager");
        lookupMgr = (LookupManager) ctx.getBean("lookupManager");
        System.out.println("INFO: starting up");
    }

    public void check68(HSSFWorkbook wb)
    {
        HSSFSheet sheet = wb.getSheet("1068");
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
                else if (sa.getShopId() == 23)
                {
                    List<StreetAddress> saes = orderManager
                            .findAllStreetAddressesForPostalCode(postalCode);
                    for (int i = 0; i < saes.size(); i++)
                    {
                        StreetAddress tmp = saes.get(i);
                        tmp.setShopId(68);
                        this.lookupMgr.save(tmp);
                    }
                }

                // System.out.println("postalcode "+postalCode+" is currently mapped to  shop 23, but should be in  shop 68");
                else if (sa.getShopId() != 23 && sa.getShopId() != 68)
                    System.out.println("postalcode " + postalCode
                            + " is not mapped to shop 23&68 but shop " + sa.getShopId());
            }
            rowCount++;
        }

    }

    public void check74(HSSFWorkbook wb)
    {
        HSSFSheet sheet = wb.getSheet("1074");
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
                else if (sa.getShopId() == 51 || sa.getShopId() == 30
                        || sa.getShopId() == 47)
                    System.out.println("postalcode " + postalCode
                            + "is currently mapped to  shop " + sa.getShopId()
                            + ", but should be in  shop 74");
                else if (sa.getShopId() != 51 && sa.getShopId() != 30
                        && sa.getShopId() != 47)
                    System.out.println("postalcode " + postalCode
                            + " is not mapped to shop 51 & 30 & 47 but shop "
                            + sa.getShopId());
            }
            rowCount++;
        }

    }

    public void update74(HSSFWorkbook wb)
    {
        HSSFSheet sheet = wb.getSheet("1074");
        int rowCount = 1;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;
        String postalCode = null;
        int updateRowCount = 0;
        int updatePostalcodeCount = 0;
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
                else if (sa.getShopId() != 30 && sa.getShopId() != 51
                        && sa.getShopId() != 47)
                    System.out.println("postalcode " + postalCode
                            + " is not mapped to shop 30&51&74 but shop "
                            + sa.getShopId());
                else if (sa.getShopId() == 30 || sa.getShopId() == 51
                        || sa.getShopId() == 47)
                {
                    // System.out.println("postalcode "+postalCode+" is mapped from shop 8 to shop67");
                    List<StreetAddress> saes = orderManager
                            .findAllStreetAddressesForPostalCode(postalCode);
                    for (int i = 0; i < saes.size(); i++)
                    {
                        StreetAddress tmp = saes.get(i);
                        tmp.setShopId(74);
                        this.lookupMgr.save(tmp);
                        updateRowCount++;
                    }
                    updatePostalcodeCount++;
                }
            }
            rowCount++;
        }
        System.out.println("[INFO]: update " + updatePostalcodeCount + " postal codes, "
                + updateRowCount + " rows.");

    }

    public void update23(HSSFWorkbook wb)
    {
        HSSFSheet sheet = wb.getSheet("1013");
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
                else if (sa.getShopId() != 13 && sa.getShopId() != 64)
                    System.out.println("postalcode " + postalCode
                            + " is not mapped to shop 24&66 but shop " + sa.getShopId());
                else if (sa.getShopId() == 64)
                {
                    System.out.println("postalcode " + postalCode
                            + " is mapped from shop64 to shop13");
                    List<StreetAddress> saes = orderManager
                            .findAllStreetAddressesForPostalCode(postalCode);
                    for (int i = 0; i < saes.size(); i++)
                    {
                        StreetAddress tmp = saes.get(i);
                        tmp.setShopId(13);
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
