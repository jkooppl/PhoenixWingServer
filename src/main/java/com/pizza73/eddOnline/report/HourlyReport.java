package com.pizza73.eddOnline.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pizza73.model.report.OrderStats;
import com.pizza73.service.MailEngine;
import com.pizza73.service.OrderManager;

public class HourlyReport
{

   private static final String FILE_PATH = "/var/stats/";
   private static final String FILE_EXTENSTION = ".xls";
   private static final String FILE_PREFIX = "OnlineStats";
   private static final SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
   
   private static final String[] TO = {"chris.huisman@pizza73.com"};
   private static final String FROM = "webadmin@pizza73.com";
   
   private OrderManager mgr;
   private MailEngine mailEngine;
   
   protected ClassPathXmlApplicationContext ctx;
   
   public HourlyReport(){}
   
   public void setOrderManager(OrderManager mgr)
   {
       this.mgr = mgr;
   }
   
   public void setMailEngine(MailEngine engine)
   {
       this.mailEngine = engine;
   }
   
   public void setUp()
   {
       String[] paths = {
           "util-applicationContext-resources.xml", "applicationContext.xml" };
       ctx = new ClassPathXmlApplicationContext(paths);
       mgr = (OrderManager) ctx.getBean("orderManager");
       mailEngine = (MailEngine)ctx.getBean("mailEngine");
   }
   /**
    * @param args
    */
   public static void main(String[] args)
   {
      HourlyReport hr = new HourlyReport();
      hr.setUp();
      
      Integer days = 21;
      if(args.length != 0)
      {
         days = Integer.valueOf(args[0]);
      }
      
      Map<Calendar, List<OrderStats>> stats = hr.mgr.orderStats(days);
      
      try
      {
         File dirFile = new File(FILE_PATH);
         if(!dirFile.exists())
         {
            dirFile.mkdirs();
         }
         File excelFile = new File(dirFile, FILE_PREFIX + FILE_EXTENSTION);
         if(!excelFile.exists())
         {
            excelFile.createNewFile();
         }
         HSSFWorkbook wb = new HSSFWorkbook();
         hr.createExcelReport(stats, wb);
         FileOutputStream fileOut = new FileOutputStream(excelFile);
         wb.write(fileOut);
         fileOut.close();         
         
         String subject = "[Pizza 73] Oonline Stats: ";

         if(args.length == 0)
         {
            args = HourlyReport.TO;
         }
         send(args, subject, excelFile, hr);
      }
      catch (FileNotFoundException e)
      {
         System.err.println("HourlyReport: " + e);
      }
      catch (IOException e)
      {
         System.err.println("HourlyReport: " + e);
      }
      System.out.println("DONE");
      
   }

   private void createExcelReport(Map<Calendar, List<OrderStats>> stats,
         HSSFWorkbook wb)
   {
      String FORMAT_STYLE = "0.00%";
      HSSFCellStyle style;
      style = wb.createCellStyle();
      style.setDataFormat(HSSFDataFormat.getBuiltinFormat(FORMAT_STYLE));
      
      HSSFSheet sheet = wb.createSheet("Online Stats");
      HSSFRow row = null;
      HSSFCell cell = null;
      int rownum = 0;
      short cellnum_one = 0;
      short cellnum_two = 1;
      short cellnum_three = 2;
      short cellnum_four = 3;
      
      Set<Calendar> keySet = stats.keySet();
      Iterator<Calendar> iter = keySet.iterator();
      Calendar key = null;
      while(iter.hasNext())
      {
         key = iter.next();
         
         row = sheet.createRow(rownum++);
         cell = row.createCell(cellnum_one);
         cell.setCellValue(df.format(key.getTime()));
         
         row = sheet.createRow(rownum++);
         cell = row.createCell(cellnum_one);
         cell.setCellValue("HOUR");
         cell = row.createCell(cellnum_two);
         cell.setCellValue("ONLINE");
         cell = row.createCell(cellnum_three);
         cell.setCellValue("TOTAL");
         cell = row.createCell(cellnum_four);
         cell.setCellValue("RATIO");
         
         List<OrderStats> statList = stats.get(key);
         Iterator<OrderStats> statIter = statList.iterator();
         OrderStats stat = null;
         int onlineTotal = 0;
         int total = 0;
         while(statIter.hasNext())
         {
            stat = statIter.next();
            row = sheet.createRow(rownum++);
            cell = row.createCell(cellnum_one);
            int hour = stat.getHour() % 24;
            int hourTwo = hour + 1;
            cell.setCellValue(hour + " - " +  hourTwo);
            cell = row.createCell(cellnum_two);
            cell.setCellValue(stat.getOnlineCount().intValue());
            cell = row.createCell(cellnum_three);
            cell.setCellValue(stat.getTotalCount().intValue());
            cell = row.createCell(cellnum_four);
            cell.setCellValue(stat.getRatio().doubleValue());
            cell.setCellStyle(style);
            
            onlineTotal += stat.getOnlineCount().intValue();
            total += stat.getTotalCount().intValue();
         }
         
         row = sheet.createRow(rownum++);
         cell = row.createCell(cellnum_one);
         cell.setCellValue("TOTAL");
         cell = row.createCell(cellnum_two);
         cell.setCellValue(onlineTotal);
         cell = row.createCell(cellnum_three);
         cell.setCellValue(total);
         cell = row.createCell(cellnum_four);
         double ratiod = Double.valueOf(onlineTotal + "") / total;
         BigDecimal ratio = new BigDecimal(ratiod, new MathContext(4));
         cell.setCellValue(ratio.doubleValue());
         cell.setCellStyle(style);
         
         rownum++;
      }
      
   }

   public static void send(String[] to, String subject, File excelFile, 
      HourlyReport hr)
   {
      try
      {
         hr.mailEngine.sendMessage(
            to, excelFile, "", subject, HourlyReport.FROM);
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }
}
