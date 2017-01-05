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
public class PostalcodeRemap {
    protected ClassPathXmlApplicationContext ctx;
    // private final SessionFactory sf = null;
    private OrderManager orderManager;
    private LookupManager lookupMgr;

    private static final short POSTAL_CODE = 0;

    public static void main(String[] args) throws Exception
    {
        PostalcodeRemap pc = new PostalcodeRemap();
        pc.setUp();
        File file = new File(args[0]);
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        pc.check(wb, args[1]);
        pc.update(wb, args[1]);
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

    public void check(HSSFWorkbook wb, String sheetname)
    {
        HSSFSheet sheet = wb.getSheet(sheetname);
        int rowCount = 1;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;
        String postalCode = null;
        System.out.println("INFO:checking postalcode in sheet " + sheetname);
        int validCount = 0;
        while (rowCount <= lastRow)
        {
            row = sheet.getRow(rowCount);
            postalCode = getPostalCode(row);
            if (postalCode != null)
            {
                StreetAddress sa = orderManager.findShopForPostalCode(postalCode);
                if (sa == null)
                    System.out.println("WARN: postalcode " + postalCode
                            + " is not in the database.");
                else if (sa.getShopId() != 24)
                    System.out.println("WARN: postalcode " + postalCode
                            + " is not mapped to shop 24 but shop " + sa.getShopId()
                            + "\n");
                else
                    validCount++;
            }
            rowCount++;
        }
        System.out.println("INFO: finish checking, " + validCount
                + " valid postal codes found.");
    }

    public void update(HSSFWorkbook wb, String sheetname)
    {
        HSSFSheet sheet = wb.getSheet(sheetname);
        int rowCount = 1;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;
        String postalCode = null;
        int validCount = 0;
        System.out.println("INFO: start mapping");
        while (rowCount <= lastRow)
        {
            row = sheet.getRow(rowCount);
            postalCode = getPostalCode(row);
            if (postalCode != null)
            {
                StreetAddress sa = orderManager.findShopForPostalCode(postalCode);
                if (sa == null)
                    System.out.println("WARN: postalcode " + postalCode
                            + " is not in the database.");
                else if (sa.getShopId() != 24)
                    System.out.println("WARN: postalcode " + postalCode
                            + " is not mapped to shop 24 but shop " + sa.getShopId()
                            + "\n");
                else
                {
                    List<StreetAddress> saes = orderManager
                            .findAllStreetAddressesForPostalCode(postalCode);
                    for (int i = 0; i < saes.size(); i++)
                    {
                        StreetAddress tmp = saes.get(i);
                        tmp.setShopId(66);
                        this.lookupMgr.save(tmp);
                    }
                    validCount++;
                    System.out.println("INFO: postalcode " + postalCode
                            + " has been mapped to shop 66.");
                }
            }
            rowCount++;
        }
        System.out.println("INFO: finish mapping, " + validCount
                + " valid postal codes mapped.");
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
