package com.pizza73.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pizza73.model.DailySales;
import com.pizza73.model.GL;
import com.pizza73.service.LookupManager;

public class DailySalesRecovery
{ protected ClassPathXmlApplicationContext ctx;
  private LookupManager lookupManager;
  private FileWriter fstream;
  private BufferedWriter out;
  private Calendar entryDates[];
  private SimpleDateFormat urlDateFormat=new SimpleDateFormat("yyyy-MM-dd");
  
  
  public void setUp() throws IOException
  {
     String[] paths = {
        "util-applicationContext-resources.xml", "applicationContext.xml" };
     ctx = new ClassPathXmlApplicationContext(paths);
     lookupManager =(LookupManager) ctx.getBean("lookupManager");
    fstream = new FileWriter("c:\\Users\\Junjie\\result.txt");
    out = new BufferedWriter(fstream);
     System.out.println("finish starting up");
  }
  
  public static void main(String[] args) throws Exception{
     DailySalesRecovery glTest=new DailySalesRecovery();
     glTest.setUp();
     glTest.calcuatePeriod(2008, 9);
     System.out.println("finish");
  }
  
  public void calcuatePeriod(Integer year, Integer period) throws IOException{

     Integer i=0;
     int j=0;
     int count=0;
     for(i=22;i<63;i++){
        Calendar startdate=Calendar.getInstance();
        startdate.set(Calendar.MONTH, Calendar.MARCH);
        startdate.set(Calendar.DAY_OF_MONTH, 1);
        for(j=0;j<180;j++){
          startdate.add(Calendar.DATE, 1);
          DailySales temp=this.lookupManager.dailySalesForShopAndDate(i, startdate);
          if(temp != null){
             count++;
             System.out.println(count);
             out.write("update iq2_daily_sales set sales_date='"+this.urlDateFormat.format(temp.getSalesDate().getTime())+"' where daily_sales_id="+temp.getId()+";\n");
             out.flush();
          }
        }
     }
   out.close();
  }

}