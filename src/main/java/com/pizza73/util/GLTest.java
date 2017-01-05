package com.pizza73.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pizza73.model.GL;
import com.pizza73.service.LookupManager;

public class GLTest
{ protected ClassPathXmlApplicationContext ctx;
  private LookupManager lookupManager;
  private FileWriter fstream;
  private BufferedWriter out;
  private Calendar entryDates[];
  
  
  public void setUp() throws IOException
  {
     String[] paths = {
        "util-applicationContext-resources.xml", "applicationContext.xml" };
     ctx = new ClassPathXmlApplicationContext(paths);
     lookupManager =(LookupManager) ctx.getBean("lookupManager");
     fstream = new FileWriter("c:\\Users\\Junjie\\result.txt");
     out = new BufferedWriter(fstream);
     this.LoadEntryDates();
     System.out.println("finish starting up");
  }
  
  private void LoadEntryDates(){
     this.entryDates= new Calendar[10];
     entryDates[0]=Calendar.getInstance();
     entryDates[0].set(2007,Calendar.AUGUST , 25);
     entryDates[1]=Calendar.getInstance();
     entryDates[1].set(2007,Calendar.SEPTEMBER , 29);     
     entryDates[2]=Calendar.getInstance();
     entryDates[2].set(2007,Calendar.OCTOBER , 27);
     entryDates[3]=Calendar.getInstance();
     entryDates[3].set(2007,Calendar.NOVEMBER , 24);
     entryDates[4]=Calendar.getInstance();
     entryDates[4].set(2007,Calendar.DECEMBER , 29);
     entryDates[5]=Calendar.getInstance();
     entryDates[5].set(2008,Calendar.JANUARY , 26);
     entryDates[6]=Calendar.getInstance();
     entryDates[6].set(2008,Calendar.FEBRUARY , 23);
     entryDates[7]=Calendar.getInstance();
     entryDates[7].set(2008,Calendar.MARCH , 29);
     entryDates[8]=Calendar.getInstance();
     entryDates[8].set(2008,Calendar.APRIL , 26);
     entryDates[9]=Calendar.getInstance();
     entryDates[9].set(2008,Calendar.MAY , 24);          
  }
  
  public static void main(String[] args) throws Exception{
     GLTest glTest=new GLTest();
     glTest.setUp();
     glTest.calcuatePeriod(2008, 9);
     System.out.println("finish");
  }
  
  public void calcuatePeriod(Integer year, Integer period) throws IOException{
     List<Integer> shops=this.lookupManager.GLshopsForYear(year);
     Integer shopId=null;
     List<Integer> accounts=null;
     int i=0;
     int j=0;
     out.write("INSERT INTO gl(\"year\", period, shop_id, account, name, entry_date, debit, credit,source, reference, doc_no, jrnl_no, post_date) values\n");
     for(i=0;i<shops.size();i++){
        shopId=shops.get(i);
        accounts=this.lookupManager.GLaccountsForPeriodAndShop(year, period, shopId);
        for(j=0;j<accounts.size();j++){
           calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, accounts.get(j), shopId));
        }
     }
     out.close();
  }
  
  public void calcuateEndingBalance(List<GL> GLRecords) throws IOException{
     BigDecimal endingBalance=BigDecimal.valueOf(0,2);
     int i=0;
     GL tempGL=null;
     for(i=0;i<GLRecords.size();i++){
           endingBalance=endingBalance.add(GLRecords.get(i).getDebit()).subtract(GLRecords.get(i).getCredit());
           if(GLRecords.get(i).getSource().trim().equalsIgnoreCase("BEG-BAL"))
              tempGL=GLRecords.get(i);
     }
     this.insertOpeningEndingBalance(tempGL, endingBalance);
     
  }
  
  public void insertOpeningEndingBalance(GL currentPeriodOpeningRecord,BigDecimal endingBalance) throws IOException{
      GL temp = new GL();
      temp.setYear(currentPeriodOpeningRecord.getYear());
      temp.setPeriod(currentPeriodOpeningRecord.getPeriod()+1);
      temp.setShop_id(currentPeriodOpeningRecord.getShop_id());
      temp.setAccount(currentPeriodOpeningRecord.getAccount());
      temp.setEntryDate(entryDates[currentPeriodOpeningRecord.getPeriod()]);
      if(endingBalance.signum() == -1){
         temp.setDebit(BigDecimal.ZERO);
         temp.setCredit(endingBalance.abs());
      }
      else{
         temp.setDebit(endingBalance);
         temp.setCredit(BigDecimal.ZERO);
      }
      temp.setName(currentPeriodOpeningRecord.getName());
      temp.setSource(currentPeriodOpeningRecord.getSource());
      temp.setReference(currentPeriodOpeningRecord.getReference());
      temp.setDoc_no(currentPeriodOpeningRecord.getDoc_no());
      temp.setJrnl_no(currentPeriodOpeningRecord.getJrnl_no());
      temp.setPost_date(currentPeriodOpeningRecord.getPost_date());
      out.write("("+temp.getYear()+", "+temp.getPeriod()+", "+temp.getShop_id()+", "+temp.getAccount()+", '"+StringEscapeUtils.escapeSql(temp.getName())+"', '"+ temp.getEntryDate().getTime().toGMTString()+"', "+temp.getDebit()+", "+temp.getCredit()+", '"+StringEscapeUtils.escapeSql(temp.getSource())+"', '"+StringEscapeUtils.escapeSql(temp.getReference())+"', '"+StringEscapeUtils.escapeSql(temp.getDoc_no())+"', '"+StringEscapeUtils.escapeSql(temp.getJrnl_no())+"', '"+temp.getPost_date().getTime().toGMTString()+"'),"+"\n");
  }
      
}
