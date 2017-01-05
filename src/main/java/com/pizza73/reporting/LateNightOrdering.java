package com.pizza73.reporting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pizza73.service.OrderManager;

/**
 * LateNightOrdering - TODO comment me
 *
 * @author chris
 * Created on: 27-Mar-08
 * 
 * Copyright Pizza 73 2008
 */
public class LateNightOrdering
{
   private static final String FILE_PATH = "reports/sales/";
   private static final String BASE_FILE_NAME = "hourlySalesReportBAK-";
   private static final String FILE_EXTENSION = ".xls";
   
   private OrderManager orderMgr;
   
   private final Calendar startDay;
   private final Calendar endDay;
   private final int hour;
   
   protected ClassPathXmlApplicationContext ctx;

   private static final SimpleDateFormat df = new SimpleDateFormat ("yyyyMMdd");
         
   public LateNightOrdering(Calendar start, Calendar end, int hour)
   {
      this.startDay = start;
      this.endDay = end;
      this.hour = hour;
   }
   
   public void setUp()
   {
       String[] paths = {
           "util-applicationContext-resources.xml", "applicationContext.xml" };
       ctx = new ClassPathXmlApplicationContext(paths);
       orderMgr = (OrderManager)ctx.getBean("orderManager");
   }

   /**
    * @param args
    */
   public static void main(String[] args)
   {
      Calendar start = Calendar.getInstance();
      Calendar end = Calendar.getInstance();
      if(args.length == 0)
      {
         start.set(Calendar.YEAR, 2007);
         start.set(Calendar.MONTH, Calendar.JANUARY);
         start.set(Calendar.DAY_OF_MONTH, 1);
         
         end.set(Calendar.YEAR, 2007);
         end.set(Calendar.MONTH, Calendar.MARCH);
         end.set(Calendar.DAY_OF_MONTH, 31);
      }
      LateNightOrdering lno = new LateNightOrdering(start, end, 26);
      
//      Map<Integer, List<BigInteger>> shopMap = new TreeMap<Integer, List<BigInteger>>();
//      for(int shopId = 1; shopId <= 56; shopId++)
//      {  
//         lno.startDay.set(Calendar.YEAR, 2007);
//         lno.endDay.set(Calendar.YEAR, 2007);
//         List<BigInteger> shopSalesList = new ArrayList<BigInteger>();
//         lno.setUp();
//         BigInteger sales1 = 
//            lno.orderMgr.totalSalesForHourInDateRange(lno.startDay, lno.endDay, lno.hour, shopId);
//         
//         BigInteger sales2 = 
//            lno.orderMgr.totalSalesForHourInDateRange(lno.startDay, lno.endDay, 27, shopId);
//         
//         lno.startDay.set(Calendar.YEAR, 2008);
//         lno.endDay.set(Calendar.YEAR, 2008);
//         BigInteger sales3 = 
//            lno.orderMgr.totalSalesForHourInDateRange(lno.startDay, lno.endDay, lno.hour, shopId);
//         shopSalesList.add(0, sales1);
//         shopSalesList.add(1, sales2);
//         shopSalesList.add(2, sales3);
//         
//         shopMap.put(Integer.valueOf(shopId), shopSalesList);
//         System.out.println("Shop: " + shopId);
//      }
//      lno.writeReport(shopMap, start, end);
      
      Map<Integer, List<BigDecimal>> shopMapDollar = new TreeMap<Integer, List<BigDecimal>>();
      for(int shopId = 1; shopId <= 56; shopId++)
      {  
         lno.startDay.set(Calendar.YEAR, 2007);
         lno.endDay.set(Calendar.YEAR, 2007);
         List<BigDecimal> shopSalesList = new ArrayList<BigDecimal>();
         lno.setUp();
         BigDecimal sales1 = 
            lno.orderMgr.totalSalesDollarForHourInDateRange(lno.startDay, lno.endDay, lno.hour, shopId);
         
         BigDecimal sales2 = 
            lno.orderMgr.totalSalesDollarForHourInDateRange(lno.startDay, lno.endDay, 27, shopId);
         
         lno.startDay.set(Calendar.YEAR, 2008);
         lno.endDay.set(Calendar.YEAR, 2008);
         BigDecimal sales3 = 
            lno.orderMgr.totalSalesDollarForHourInDateRange(lno.startDay, lno.endDay, lno.hour, shopId);
         if(sales1 == null)
         {
            sales1 = BigDecimal.ZERO;
         }
         if(sales2 == null)
         {
            sales2 = BigDecimal.ZERO;
         }
         if(sales3 == null)
         {
            sales3 = BigDecimal.ZERO;
         }
         shopSalesList.add(0, sales1);
         shopSalesList.add(1, sales2);
         shopSalesList.add(2, sales3);
         
         shopMapDollar.put(Integer.valueOf(shopId), shopSalesList);
         System.out.println("Shop: " + shopId);
      }
      lno.writeDollarReport(shopMapDollar, start, end);
   }

   public void writeReport(Map<Integer, List<BigInteger>> shopMap, Calendar start, Calendar end)
   {
      String startString = df.format(start.getTime());
      String endString = df.format(end.getTime());
      File f = new File(FILE_PATH);
      if (!f.exists())
      {
          f.mkdirs();
      }
      File excelFile = 
         new File(f, BASE_FILE_NAME + "Jan. 01 - March 31" + FILE_EXTENSION);
      try
      {
         if(!excelFile.exists())
         {
            excelFile.createNewFile();
         }
         HSSFWorkbook wb = new HSSFWorkbook();
         reportOutput(wb,shopMap, startString, endString);
         FileOutputStream fileOut = new FileOutputStream(excelFile);
         wb.write(fileOut);
         fileOut.close();
      }
      catch(IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * @param shopMap
    * @param startString
    * @param endString
    */
   private void reportOutput(HSSFWorkbook wb, Map<Integer, List<BigInteger>> shopMap,
      String startString, String endString)
   {
      HSSFSheet sheet = wb.createSheet("Hourly Report");
      int rownum = 0;
      short cellnum = 0;
      HSSFRow row = sheet.createRow(rownum++);
      HSSFCell cell = row.createCell(cellnum++);
      cell.setCellValue("Shop ID");
      cell = row.createCell(cellnum++);
      cell.setCellValue("2007 2AM-3AM");
      cell = row.createCell(cellnum++);
      cell.setCellValue("2007 3AM-4AM");
      cell = row.createCell(cellnum);
      cell.setCellValue("2008 2AM-3AM");
      
      
      Set<Integer> keySet = shopMap.keySet();
      Iterator<Integer> iter = keySet.iterator();
      while(iter.hasNext())
      {
         Integer shopId = iter.next(); 
         row = sheet.createRow(rownum++);  
         cellnum = 0;
         cell = row.createCell(cellnum++);
         cell.setCellValue(shopId);
         List<BigInteger> salesList = shopMap.get(shopId);
         cell = row.createCell(cellnum++);
         cell.setCellValue(salesList.get(0).intValue());
         cell = row.createCell(cellnum++);
         cell.setCellValue(salesList.get(1).intValue());
         cell = row.createCell(cellnum++);
         cell.setCellValue(salesList.get(2).intValue());
      }
   }
   
   public void writeDollarReport(Map<Integer, List<BigDecimal>> shopMap, Calendar start, Calendar end)
   {
      String startString = df.format(start.getTime());
      String endString = df.format(end.getTime());
      File f = new File(FILE_PATH);
      if (!f.exists())
      {
          f.mkdirs();
      }
      File excelFile = 
         new File(f, BASE_FILE_NAME + "Jan. 01 - March 31-Dollar" + FILE_EXTENSION);
      try
      {
         if(!excelFile.exists())
         {
            excelFile.createNewFile();
         }
         HSSFWorkbook wb = new HSSFWorkbook();
         reportDollarOutput(wb,shopMap, startString, endString);
         FileOutputStream fileOut = new FileOutputStream(excelFile);
         wb.write(fileOut);
         fileOut.close();
      }
      catch(IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   private void reportDollarOutput(HSSFWorkbook wb, Map<Integer, List<BigDecimal>> shopMap,
      String startString, String endString)
   {
      HSSFSheet sheet = wb.createSheet("Hourly Report");
      int rownum = 0;
      short cellnum = 0;
      HSSFRow row = sheet.createRow(rownum++);
      HSSFCell cell = row.createCell(cellnum++);
      cell.setCellValue("Shop ID");
      cell = row.createCell(cellnum++);
      cell.setCellValue("2007 2AM-3AM");
      cell = row.createCell(cellnum++);
      cell.setCellValue("2007 3AM-4AM");
      cell = row.createCell(cellnum);
      cell.setCellValue("2008 2AM-3AM");
      
      
      Set<Integer> keySet = shopMap.keySet();
      Iterator<Integer> iter = keySet.iterator();
      while(iter.hasNext())
      {
         Integer shopId = iter.next(); 
         row = sheet.createRow(rownum++);  
         cellnum = 0;
         cell = row.createCell(cellnum++);
         cell.setCellValue(shopId);
         List<BigDecimal> salesList = shopMap.get(shopId);
         cell = row.createCell(cellnum++);
         cell.setCellValue(salesList.get(0).doubleValue());
         cell = row.createCell(cellnum++);
         cell.setCellValue(salesList.get(1).doubleValue());
         cell = row.createCell(cellnum++);
         cell.setCellValue(salesList.get(2).doubleValue());
      }
   }
}
