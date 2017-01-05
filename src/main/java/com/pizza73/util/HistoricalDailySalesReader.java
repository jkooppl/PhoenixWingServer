package com.pizza73.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pizza73.model.IqHistoricalDailySalesAccountingData;
import com.pizza73.model.ReportPosDaily;
import com.pizza73.service.LookupManager;

public class HistoricalDailySalesReader
{
   protected ClassPathXmlApplicationContext ctx;
   private LookupManager lookupMgr;
   private static final SimpleDateFormat urlDateFormat=new SimpleDateFormat("yyyyMMdd");
   private static final short SHOP_NAME = 0;
   private static final short SHOP_ID = 1;
   private static final short SUNDAY=2;
   
   public static void main(String[] args) throws Exception
   {
      HistoricalDailySalesReader prt = new HistoricalDailySalesReader();
      prt.setUp();
      File file = new File(
           // "C:\\Users\\Junjie\\Desktop\\lastyearsales.xls");
            "/pizza73/utilapps/lastyearsales.xls");
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      prt.translate(wb);
      System.out.println("INFO: finish");
   }

   @SuppressWarnings("unchecked")
   public void setUp()
   {
      String[] paths = {
         "util-applicationContext-resources.xml", "applicationContext.xml" };
      ctx = new ClassPathXmlApplicationContext(paths);
      lookupMgr = (LookupManager) ctx.getBean("lookupManager");
      System.out.println("INFO: starting up");
   }
   
   private void translate(HSSFWorkbook wb)
   {  Calendar startDate=Calendar.getInstance();
      Calendar currentDate=null;
      startDate.set(2009, Calendar.FEBRUARY, 15);
      for (int i=0;i<5;i++){
      startDate.add(Calendar.DATE, 7);
      int rowCount = 2; 
      HSSFRow row = null;
      HSSFSheet sheet = null;
      int day=0;
      IqHistoricalDailySalesAccountingData tempDailySales=null;      
      sheet = wb.getSheet(urlDateFormat.format(startDate.getTime()));
      while(day<7){
         currentDate=(Calendar)startDate.clone();
         currentDate.add(Calendar.DATE, day);
         System.out.println("INFO: "+currentDate.getTime().toLocaleString());
         rowCount = 2;
         row = null;        
         while(true){
            row = sheet.getRow(rowCount);
            tempDailySales=new IqHistoricalDailySalesAccountingData();
            if(this.getShopName(row) != null && this.getShopName(row).equalsIgnoreCase("END"))
               break;
            tempDailySales.setShopId(this.getShopId(row));
            tempDailySales.setBusinessDate(currentDate);
            tempDailySales.setNetSales(this.getNetSales(row, SUNDAY+day));          
            this.lookupMgr.save(tempDailySales);
            rowCount++;
         }
         day++;
      }
      }
      
   }

   private String getShopName(HSSFRow row)
   {
      
      HSSFCell cell = null;

      cell = row.getCell(SHOP_NAME);
      if (cell != null)
      {
         return cell.getStringCellValue();
      }
         return null;      
   }   
   
   private Integer getShopId(HSSFRow row)
   {
      
      HSSFCell cell = null;

      cell = row.getCell(SHOP_ID);
      if (cell != null)
      {
         return (Integer.valueOf(String.valueOf(Math.round(cell.getNumericCellValue())))-1000);
      }
         return null;      
   }
   
   private BigDecimal getNetSales(HSSFRow row, int dayCell)
   {
      
      HSSFCell cell = null;

      cell = row.getCell((short)dayCell);
      if (cell != null)
      {
         return BigDecimal.valueOf(cell.getNumericCellValue());
      }
         return null;      
   }      
   
}
