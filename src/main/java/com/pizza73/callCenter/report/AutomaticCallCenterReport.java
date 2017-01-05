package com.pizza73.callCenter.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
public class AutomaticCallCenterReport
{
   private LookupManager mgr;
   private MailEngine mailEngine;
   
   protected ClassPathXmlApplicationContext ctx;

//   private static final String FILE_PATH = "C:\\var\\www\\html\\stats\\";
   private static final String FILE_PATH = "/var/www/html/stats/";
   private static final String FILE_EXTENSTION = ".stats.txt";
   
   private static final String FROM = "webadmin@pizza73.com";
   private static final String[] TO = {"chris.huisman@pizza73.com"};
   
   public static final String ACD_DN = "ACD DN";
   public static final String ACD_DN_1300 = ACD_DN + " 1300";
   public static final String ACD_DN_1301 = ACD_DN + " 1301";
   public static final String ACD_DN_1302 = ACD_DN + " 1302";
   public static final String ACD_DN_1307 = ACD_DN + " 1307";
   
   public AutomaticCallCenterReport()
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
   
   public static void main(String[] args)
   {
      AutomaticCallCenterReport acr = new AutomaticCallCenterReport();
      acr.setUp();
      SimpleDateFormat df = new SimpleDateFormat ("yyyyMMdd");
      Calendar businessDate = acr.mgr.businessDate();
      String date = df.format(businessDate.getTime());
//      date = "20080213";
      File file = new File(FILE_PATH, date + FILE_EXTENSTION);
      try
      {
         FileReader fis = new FileReader(file);
         BufferedReader reader = new BufferedReader(fis);
         Map<String, Map<String, ACDReport>> reports = 
            new TreeMap<String, Map<String, ACDReport>>();
         Map<String, ACDReport> map1300 = new TreeMap<String, ACDReport>();
         Map<String, ACDReport> map1301 = new TreeMap<String, ACDReport>();
         Map<String, ACDReport> map1302 = new TreeMap<String, ACDReport>();
         Map<String, ACDReport> map1307 = new TreeMap<String, ACDReport>();
         
         String line = null;

         while ((line = reader.readLine()) != null)
         {
            if(line.contains(ACD_DN_1300))
            {
               mapLines(reader, map1300);
            }
            if(line.contains(ACD_DN_1301))
            {
               mapLines(reader, map1301);
            }
            if(line.contains(ACD_DN_1307))
            {
               mapLines(reader, map1307);
            }
            if(line.contains(ACD_DN_1302))
            {
               mapLines(reader, map1302);
            }
         }
         reader.close();
         fis.close();
         
         reports.put(ACD_DN_1300, map1300);
         reports.put(ACD_DN_1301, map1301);
         reports.put(ACD_DN_1302, map1302);
         reports.put(ACD_DN_1307, map1307);
         
         //write excel file
         ACDExcelReport excelReport = new ACDExcelReport();
         
         File excelFile = new File(FILE_PATH + "ACDStats-" + date + ".xls");
         if(!excelFile.exists())
         {
            excelFile.createNewFile();
         }
         HSSFWorkbook wb = new HSSFWorkbook();
         excelReport.createExcelReport(reports, wb, date);
         FileOutputStream fileOut = new FileOutputStream(excelFile);
         wb.write(fileOut);
         fileOut.close();         
         
         String subject = "[Pizza 73] ACD Stats Summary for: " + date;

         if(args.length == 0)
         {
            args = AutomaticCallCenterReport.TO;
         }
         send(args, subject, excelFile, acr);
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

   private static void mapLines(BufferedReader reader,
         Map<String, ACDReport> map) throws IOException
   {
      String line;
      while(!(line = reader.readLine()).equals(""))
      {
         ACDReport reportLine = new ACDReport();
         reportLine.parseLine(line);
         String agentId = reportLine.getAgentId();
         if(StringUtils.isNotBlank(agentId))
         {
            if(map.containsKey(agentId))
            {
               map.get(agentId).addToSelf(reportLine);
            }
            else
            {
               map.put(reportLine.getAgentId(), reportLine);
            }
         }
         //get rid of Q / P
         reader.readLine();
      }
   }
   
   public static void send(String[] to, String subject, File excelFile, 
      AutomaticCallCenterReport acr)
   {
      try
      {
         acr.mailEngine.sendMessage(
            to, excelFile, "", subject, AutomaticCallCenterReport.FROM);
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }
}
