package com.pizza73.accounting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * BalanceSheetParser - TODO comment me
 *
 * @author chris
 * Created on: 16-Mar-08
 * 
 * Copyright Pizza 73 2008
 */
public class BalanceSheetParser
{
   private static final String FILE_PATH = "/BS/";
   private static final int[] FORGET_LINES = {2, 4, 5, 6};
   
   private String runDate;
   private String period;
      
   public BalanceSheetParser()
   {}
   
   public String getRunDate()
   {
      return this.runDate;
   }
   
   public String getPeriod()
   {
      return this.period;
   }
   
   public void setUp()
   {}
   /**
    * @param args
    */
   public static void main(String[] args)
   {
      BalanceSheetParser bsp = new BalanceSheetParser();
      bsp.setUp();
      bsp.parseBalanceSheets();
      
//      bsp.createHtml(bsList);
      
      System.out.println("DONE");
   }
   
   public List<BalanceSheet> parseBalanceSheets()
   {
      File dir = new File(FILE_PATH);
      File[] fileList = dir.listFiles();
      
//      File file = null;
      FileReader fis = null;
      BufferedReader reader = null;
      
      List<BalanceSheet> bsList = new ArrayList<BalanceSheet>();
      try
      {
         for(File file: fileList)
         {
            fis = new FileReader(file);
            reader = new BufferedReader(fis);
            
            String line = null;
            int lineNumber = 0;
            
            BalanceSheet bs = new BalanceSheet();
            while ((line = reader.readLine()) != null)
            {
               if(lineNumber == 0)
               {
//                  line = line.replace('"', ' ');
//                  line = line.replace("001 Balance Sheet","");
//                  line = line.replace("001 BALANCE SHEET","");
                  String fileName = file.getName();
                  fileName = fileName.replace("BS", "");
                  fileName = fileName.replace(".PRN", "");
                  fileName = fileName.replace(".csv", "");
                  bs.setShopId(StringUtils.trimToEmpty(fileName));
               }
               else if(lineNumber == 1 && this.runDate == null)
               {
                  this.runDate = StringUtils.trimToEmpty(line);
               }
               else if(lineNumber == 3 && this.period == null)
               {
                  this.period = StringUtils.trimToEmpty(line);
               }
               else if(ArrayUtils.contains(BalanceSheetParser.FORGET_LINES, lineNumber))
               {}
               else
               {
                  bs.parseLine(line, lineNumber);
               }
               lineNumber++;
            }
            reader.close();
            fis.close();
            
            bsList.add(bs);
         }
         
         File reportFile = new File(FILE_PATH, "bs.txt");
         PrintWriter writer = null;
        
         writer = new PrintWriter(reportFile);
         Iterator<BalanceSheet> iter = bsList.iterator();
         while (iter.hasNext())
         {
            writer.println(iter.next().toString());
         }
         writer.close();
      }
      catch (FileNotFoundException e)
      {
         System.err.println("AutomaticCallCenterReport: " + e);
      }
      catch (IOException e)
      {
         System.err.println("AutomaticCallCenterReport: " + e);
      }
      
      return bsList;
   }

   public List<BalanceSheet> getBalanceSheet()
   {
      return parseBalanceSheets();
   }
   
//   /**
//    * @param bsList
//    */
//   private void createHtml(List<BalanceSheet> bsList)
//   {
//      // TODO Auto-generated method stub
//      
//   }
   
//   headers = ['Account Name','Entry Date','Debit','Credit','Source','Reference','Document #','Journal #']
//
//   report.write('Content-type: text/html\n\n')
//   iqueso.format_report_header(report,title,"big","/big","Arial",1,0,0)
//   if period != 0 :
//       sub_title = 'Year %d, Period %d, Shop %s, Account(s) %s' % (year,period,shop_name,accounts)
//   else :
//       sub_title = 'Year %d, All Periods, Shop %s, Account(s) %s' % (year,shop_name,accounts)
//   report.write('<br><br><b>%s</b>' % sub_title)
//
//   report.write('<table border=0 cellpadding=0>')
//
//   report.write('<tr>')
//   for column in headers :
//       report.write('<td><b>%s</b></td>' % column)
//   report.write('</tr>')
//
//   for row in result_set :
//       if int(row['credit']) == 0 :
//           report.write('<tr %s>' % style1)
//       else :
//           report.write('<tr %s>' % style2)
//
//       report.write('<td>%s</td>' % row['name'])
//       report.write('<td>%s</td>' % row['entry_date'])
//       if int(row['debit']) != 0 :
//           report.write('<td align=right>%s</td>' % row['formatted_debit'])
//       else :
//           report.write('<td align=center>-</td>')
//       if int(row['credit']) != 0 :
//           report.write('<td align=right>%s</td>' % row['formatted_credit'])
//       else :
//           report.write('<td align=center>-</td>')
//       report.write('<td>%s</td>' % row['source'])
//       report.write('<td>%s</td>' % row['reference'])
//       report.write('<td>%s</td>' % row['doc_no'])
//       report.write('<td>%s</td>' % row['jrnl_no'])
//       report.write('</tr>')
//
//   report.write('</table>')
}
