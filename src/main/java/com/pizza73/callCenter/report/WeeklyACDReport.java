package com.pizza73.callCenter.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.pizza73.service.LookupManager;
import com.pizza73.service.MailEngine;

/**
 * 
 * @author chris
 * 
 */
@Component
public class WeeklyACDReport
{
   private LookupManager mgr;
   private MailEngine mailEngine;
   
   protected ClassPathXmlApplicationContext ctx;

   private static final String FILE_PATH = "/var/www/html/stats/";
   private static final String FILE_EXTENSTION = ".xls";
   private static final String FILE_PREFIX = "ACDStats-";
   private static final SimpleDateFormat df = new SimpleDateFormat ("yyyyMMdd");
   
   private static final String FROM = "webadmin@pizza73.com";
   private static final String[] TO = {"chris.huisman@pizza73.com"};
      
   public WeeklyACDReport()
   {}
   
   public void setLookupManager(LookupManager mgr)
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
       mgr = (LookupManager) ctx.getBean("lookupManager");
       mailEngine = (MailEngine)ctx.getBean("mailEngine");
   }
   
   public static void main(String[] args) throws ParseException
   {
      WeeklyACDReport war = new WeeklyACDReport();
      war.setUp();
      Calendar businessDate = war.mgr.businessDate();
      try
      {
         //WEEKLY REPORT
         HSSFWorkbook weeklyReport = new HSSFWorkbook();
         File weeklyFile = null;
         
         Calendar weekStart = Calendar.getInstance();
         weekStart.set(Calendar.YEAR, businessDate.get(Calendar.YEAR));
         weekStart.set(Calendar.MONTH, businessDate.get(Calendar.MONTH));
         weekStart.set(Calendar.WEEK_OF_YEAR, businessDate.get(Calendar.WEEK_OF_YEAR));
         weekStart.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
         String wStart = df.format(weekStart.getTime());
         
         Calendar weekEnd = Calendar.getInstance();
         weekEnd.set(Calendar.YEAR, businessDate.get(Calendar.YEAR));
         weekEnd.set(Calendar.MONTH, businessDate.get(Calendar.MONTH));
         weekEnd.set(Calendar.WEEK_OF_YEAR, businessDate.get(Calendar.WEEK_OF_YEAR));
         weekEnd.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
         String wEnd = df.format(weekEnd.getTime());
         
         String weekRange = wStart + "-" + wEnd;
         
         Map<String, Map<String, List<ACDReport>>> data = 
            war.getWeeklyData(weekStart);
         
         weeklyFile = new File(FILE_PATH + "WeeklyACDStats-" + weekRange + ".xls");
         if(!weeklyFile.exists())
         {
            weeklyFile.createNewFile();
         }
         
         FileOutputStream fileOut = new FileOutputStream(weeklyFile);
         
         if(businessDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
         {
            WeeklyACDExcelReport warExcel = new WeeklyACDExcelReport();
            weeklyReport = warExcel.createExcelReport(data);
         }
         weeklyReport.write(fileOut);
         fileOut.close(); 
         
         String subject = "[Pizza 73] ACD Weekly Stats Summary for: " + weekRange;

         if(args.length == 0)
         {
            args = WeeklyACDReport.TO;
         }
         send(args, subject, weeklyFile, war);
      }
      catch (FileNotFoundException e)
      {
         System.err.println("AutomaticCallCenterReport: " + e);
      }
      catch (IOException e)
      {
         System.err.println("AutomaticCallCenterReport: " + e);
      }
      System.out.println("DONE");
   }
   
   private Map<String, Map<String, List<ACDReport>>> getWeeklyData(Calendar day)
   {
      Map<String, Map<String, List<ACDReport>>> report = 
         new TreeMap<String, Map<String, List<ACDReport>>>();
      
      FileInputStream fs = null;
      POIFSFileSystem pfs = null;
      File dailyFile = null;
      boolean done = false;
      while(!done)
      {
         if(day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
         {
            done = true;
         }
         String date = df.format(day.getTime()); 
         dailyFile = 
            new File(
               FILE_PATH + FILE_PREFIX + date + FILE_EXTENSTION);
         try
         {
            fs = new FileInputStream(dailyFile);
            pfs = new POIFSFileSystem(fs);
            HSSFWorkbook wb = new HSSFWorkbook(pfs);
            WeeklyACDExcelReport.parseSheet(wb.getSheetAt(0), report, date);
         }
         catch (FileNotFoundException e)
         {
            e.printStackTrace();
         }
         catch (IOException e) {
            e.printStackTrace();
         }
         day.add(Calendar.DAY_OF_WEEK, 1);
      }
      
      return report;
   }
   
   public static void send(String[] to, String subject, File excelFile, 
      WeeklyACDReport acr)
   {
      try
      {
         acr.mailEngine.sendMessage(
            to, excelFile, "", subject, WeeklyACDReport.FROM);
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }
}
