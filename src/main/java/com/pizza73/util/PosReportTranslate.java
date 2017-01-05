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

import com.pizza73.model.ReportPosDaily;
import com.pizza73.service.LookupManager;

public class PosReportTranslate
{
   protected ClassPathXmlApplicationContext ctx;
   private LookupManager lookupMgr;
   private static final SimpleDateFormat urlDateFormat=new SimpleDateFormat("yyyyMMdd");
   private static final short SHOP_ID = 0;
   private static final short NUMBER_OF_ORDERS = 2;
   private static final short NET_SALES = 3;
   private static final short GST = 4;
   
   public static void main(String[] args) throws Exception
   {
      PosReportTranslate prt = new PosReportTranslate();
      prt.setUp();
      File file = new File(
            "C:\\Users\\Junjie\\Documents\\iq2ReportPos.xls");
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
      startDate.set(2009, Calendar.MARCH, 3);
      Calendar currentDate=Calendar.getInstance();
      currentDate.set(2009, Calendar.MARCH, 8);
      int rowCount = 3;
      HSSFRow row = null;
      boolean lastShop=false;
      HSSFSheet sheet = null;
      ReportPosDaily reportPosDaily=null;      
      
      while(startDate.before(currentDate)){
         System.out.print("INFO: "+startDate.getTime().toLocaleString());
         sheet = wb.getSheet(urlDateFormat.format(startDate.getTime()));
         rowCount = 3;
         row = null;
         lastShop=false;        
         while(lastShop == false){
            row = sheet.getRow(rowCount);
            reportPosDaily=new ReportPosDaily();
            reportPosDaily.setBusinessDate(startDate);
            reportPosDaily.setShopId(this.getShopId(row));
            reportPosDaily.setNumberOfOrders(this.getNumOfOrders(row));
            reportPosDaily.setNetSales(this.getNetSales(row));
            reportPosDaily.setGst(this.getGst(row));            
            this.lookupMgr.save(reportPosDaily);
            rowCount++;
            if(reportPosDaily.getShopId() == 69)
               lastShop=true;
         }
         startDate.add(Calendar.DATE, 1);
      }
      
   }
   
   private Integer getShopId(HSSFRow row)
   {
      
      HSSFCell cell = null;

      cell = row.getCell(SHOP_ID);
      if (cell != null)
      {
         return Integer.valueOf(String.valueOf(Math.round(cell.getNumericCellValue())));
      }
         return null;      
   }
   
   private Integer getNumOfOrders(HSSFRow row)
   {
      
      HSSFCell cell = null;

      cell = row.getCell(NUMBER_OF_ORDERS);
      if (cell != null)
      {
         return Integer.valueOf(String.valueOf(Math.round(cell.getNumericCellValue())));
      }
         return null;      
   }   
   
   private BigDecimal getNetSales(HSSFRow row)
   {
      
      HSSFCell cell = null;

      cell = row.getCell(NET_SALES);
      if (cell != null)
      {
         return BigDecimal.valueOf(Math.round(cell.getNumericCellValue()*100),2);
      }
         return null;      
   }  
   
   private BigDecimal getGst(HSSFRow row)
   {
      
      HSSFCell cell = null;

      cell = row.getCell(GST);
      if (cell != null)
      {
         return BigDecimal.valueOf(Math.round(cell.getNumericCellValue()*100),2);
      }
         return null;      
   }    
   
}
