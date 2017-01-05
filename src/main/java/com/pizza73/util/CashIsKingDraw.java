package com.pizza73.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pizza73.callCenter.report.ACDReport;
import com.pizza73.callCenter.report.WeeklyACDExcelReport;
import com.pizza73.callCenter.report.WeeklyACDReport;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.Order;
import com.pizza73.service.LookupManager;
import com.pizza73.service.OrderManager;
import com.pizza73.service.MailEngine;
import com.pizza73.service.UserManager;

public class CashIsKingDraw
{
   private LookupManager mgr;
   private OrderManager oMgr;
   private UserManager uMgr;
   private MailEngine mailEngine;
   
   protected ClassPathXmlApplicationContext ctx;
   private static final String FILE_PATH="/usr/local/bin/cashIsKing/";
   private static final String FILE_PREFIX = "Gift_Card_Contest_";
   private static final SimpleDateFormat df = new SimpleDateFormat ("yyyyMMdd");
   
   private static final String FROM = "webadmin@pizza73.com";
   private static final String[] TO = {"junjie.zhu@pizza73.com","danderson@pizza73.com"};
   //private static final String[] TO ={"junjie.zhu@pizza73.com","junjie.canada@gmail.com"};   
   public CashIsKingDraw()
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
   {   System.out.println("Starting up at "+Calendar.getInstance().getTime().toLocaleString());  
       String[] paths = {
           "util-applicationContext-resources.xml", "applicationContext.xml" };
       ctx = new ClassPathXmlApplicationContext(paths);
       mgr = (LookupManager) ctx.getBean("lookupManager");
       oMgr = (OrderManager) ctx.getBean("orderManager");
       uMgr = (UserManager) ctx.getBean("userManager");
       mailEngine = (MailEngine)ctx.getBean("mailEngine");
   }
   
   public static void main(String[] args) throws ParseException
   {
      CashIsKingDraw cashIsKing = new CashIsKingDraw();
      cashIsKing.setUp();
      Calendar businessDate = cashIsKing.mgr.businessDate();
      if(args.length ==1){
         String[] patterns={"yyyyMMdd"};
         businessDate.setTime(DateUtils.parseDate(args[0], patterns));
         businessDate=DateUtils.truncate(businessDate, Calendar.DATE);
      }
      try
      {
         //WEEKLY REPORT
         HSSFWorkbook cashIsKingReport = new HSSFWorkbook();
         File dailyFile = null;
         
         dailyFile = new File(FILE_PATH + FILE_PREFIX + df.format(businessDate.getTime()) + ".xls");
         if(!dailyFile.exists())
         {
            dailyFile.createNewFile();
         }
         
         FileOutputStream fileOut = new FileOutputStream(dailyFile);
         getDailyData(cashIsKing,cashIsKingReport,businessDate);
         cashIsKingReport.write(fileOut);
         fileOut.close(); 
         
         String subject = "[Pizza 73] $50 GIFT CARD Participants for: " + df.format(businessDate.getTime());

         args = CashIsKingDraw.TO;
         send(args, subject, dailyFile, cashIsKing);
      }
      catch (FileNotFoundException e)
      {
         System.err.println("Cash Is King Report: " + e);
      }
      catch (IOException e)
      {
         System.err.println("Cash Is King Report: " + e);
      }
      System.out.println("DONE "+Calendar.getInstance().getTime().toLocaleString());
   }
   
   private static void getDailyData(CashIsKingDraw cashIsKing, HSSFWorkbook cashIsKingReport, Calendar businessDate)
   {
      HSSFSheet dailySheet = cashIsKingReport.createSheet();
      HSSFRow row = null;
      int rownum = 0;
      HSSFCell cell = null;
      short cellnum = 0;
      List<String> phones=cashIsKing.oMgr.allPhoneNumberForDate(businessDate);
      row = dailySheet.createRow(rownum++);
      cell = row.createCell(cellnum);
      cell.setCellValue("Phone");
      cellnum++;
      cell = row.createCell(cellnum);
      cell.setCellValue("Email");
      cellnum++;         
      cell = row.createCell(cellnum);
      cell.setCellValue("Name");
      cellnum++;
      cell = row.createCell(cellnum);
      cell.setCellValue("Address");
      cellnum++;         
      cell = row.createCell(cellnum);
      cell.setCellValue("address comment");
      cellnum++;
      cell = row.createCell(cellnum);
      cell.setCellValue("City");
      cellnum++;
      cell = row.createCell(cellnum);
      cell.setCellValue("Amount");
      cellnum++;
      cell = row.createCell(cellnum);
      cell.setCellValue("Purchase/Mail-in");
      cellnum++;
      int i=0;
      int j=0;
      Order temp=null;
      String name="";
      String email="";
      String phone="";
      String address="";
      String apartment="";
      String address_comment="";
      String city="";
      BigDecimal amount=BigDecimal.valueOf(0);
      String type="purchase";
      
      for(i=0;i<phones.size();i++){
         List<Order> orders=cashIsKing.oMgr.OrderForPhoneAndDate(phones.get(i), businessDate);
         j=0;
         cellnum=0;
         amount=BigDecimal.valueOf(0);
         email="";
         for(j=0;j<orders.size();j++){
            temp=orders.get(j);
            phone=temp.getCustomer().getPhone();
            if(temp.getCustomer().getOnlineId() > 0){
               OnlineCustomer tempCustomer = cashIsKing.uMgr.findCustomer(temp.getCustomer().getOnlineId());
               if(tempCustomer != null)
                  email=tempCustomer.getEmail();
            }
            name=temp.getCustomer().getName();
            address=temp.getCustomer().getAddress().getDisplayAddress();
            address_comment=temp.getCustomer().getAddress().getAddressComment();
            city=temp.getCustomer().getAddress().getCity().getDisplayName();
            amount=amount.add(BigDecimal.valueOf(temp.getTotal(),2));            
         }
         row = dailySheet.createRow(rownum++);
         cell = row.createCell(cellnum);
         cell.setCellValue(phone);
         cellnum++;
         cell = row.createCell(cellnum);
         cell.setCellValue(email);
         cellnum++;         
         cell = row.createCell(cellnum);
         cell.setCellValue(name);
         cellnum++;
         cell = row.createCell(cellnum);
         cell.setCellValue(address);
         cellnum++;         
         cell = row.createCell(cellnum);
         cell.setCellValue(address_comment);
         cellnum++;
         cell = row.createCell(cellnum);
         cell.setCellValue(city);
         cellnum++;
         cell = row.createCell(cellnum);
         cell.setCellValue(amount.toString());
         cellnum++;
         cell = row.createCell(cellnum);
         cell.setCellValue(type);
         cellnum++;
      }

      
      
   }

   public static void send(String[] to, String subject, File excelFile, 
      CashIsKingDraw cashIsKing)
   {
      try
      {  
         cashIsKing.mailEngine.sendMessage(
            to, excelFile, "", subject, CashIsKingDraw.FROM);
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }
}
