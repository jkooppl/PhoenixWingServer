package com.pizza73.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * ExcelOnlineCustomerConversion.java TODO comment me
 * 
 * @author chris 8-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public class MapDataConversion
{

    private static final short ID = 0;
    private static final short LAT = 1;
    private static final short LNG = 2;

    public static void main(String[] args) throws Exception
    {
        String city = "St. Albert";
        MapDataConversion mdc = new MapDataConversion();
        File file = new File("C:\\Documents and Settings\\chris\\My Documents\\dev\\eddOnline\\mapData\\" + city + ".xls");
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        // no header row
        int rowCount = 0;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;
        
        File f = new File("mapData\\marker");
        PrintWriter writer = null;
        try
        {
            if (!f.exists())
            {
                f.mkdirs();
            }
            File reportFile = null;
            reportFile = new File(f, city + ".xml");   
            writer = new PrintWriter(reportFile);
            writer.println("<markers>");
            while (rowCount <= lastRow)
            {
                row = sheet.getRow(rowCount);
                String marker = mdc.createMarker(row, rowCount);
                writer.println(marker);
                rowCount++;
            }
            writer.println("</markers>");
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            writer.close();
        }
    }

    private String createMarker(HSSFRow row, int rowCount)
    {
        int id = 0;
        double lat = 0;
        double lng = 0;
        
        HSSFCell cell = row.getCell(ID);
        if (cell != null)
        {
            double i = cell.getNumericCellValue();
            BigDecimal x = BigDecimal.valueOf(i);
            id = x.intValue();
        }

        cell = row.getCell(LAT);
        if (cell != null)
        {
            double i = cell.getNumericCellValue();
            BigDecimal x = BigDecimal.valueOf(i);
            lat = x.doubleValue();
        }
        
        cell = row.getCell(LNG);
        if (cell != null)
        {
            double i = cell.getNumericCellValue();
            BigDecimal x = BigDecimal.valueOf(i);
            lng = x.doubleValue();
        }
        
        String marker = "<marker id=\"" + id + "\" lat=\"" + lat + "\" lng=\"" + lng + "\"/>";
        return marker;
    }
}
