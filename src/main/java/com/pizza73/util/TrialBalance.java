package com.pizza73.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pizza73.model.GL;
import com.pizza73.model.Shop;
import com.pizza73.service.LookupManager;

public class TrialBalance
{ protected ClassPathXmlApplicationContext ctx;
  private LookupManager lookupManager;
  private BigDecimal cash=BigDecimal.ZERO;
  private BigDecimal accountsReceivable=BigDecimal.ZERO;
  private BigDecimal inventory=BigDecimal.ZERO;
  private BigDecimal prepaid=BigDecimal.ZERO;
  private BigDecimal equipment=BigDecimal.ZERO;
  private BigDecimal leaseholdImproments=BigDecimal.ZERO;
  private BigDecimal preoperatingExpenses=BigDecimal.ZERO;
  private BigDecimal futureIncomeTax=BigDecimal.ZERO;
  private BigDecimal accountsPayable=BigDecimal.ZERO;
  private BigDecimal managementFeePayable=BigDecimal.ZERO;
  private BigDecimal accruedPayrable=BigDecimal.ZERO;
  private BigDecimal accruedWagesAndBenefits=BigDecimal.ZERO;
  private BigDecimal gstPayable=BigDecimal.ZERO;
  private BigDecimal inputTaxCredit=BigDecimal.ZERO;
  private BigDecimal corporationTaxesPayable=BigDecimal.ZERO;
  private BigDecimal deferredRevenue=BigDecimal.ZERO;
  private BigDecimal intercoPayable=BigDecimal.ZERO;
  private BigDecimal currentPortionOfBankLoans=BigDecimal.ZERO;
  private BigDecimal bankLoans=BigDecimal.ZERO;
  private BigDecimal leaseholdInducement=BigDecimal.ZERO;
  private BigDecimal shareholderLoanJVPartners=BigDecimal.ZERO;
  private BigDecimal shareholderLoanPPL=BigDecimal.ZERO;
  private BigDecimal capitalStock=BigDecimal.ZERO;
  private BigDecimal retainedEarnings=BigDecimal.ZERO;
  private BigDecimal DividendsPaid=BigDecimal.ZERO;
  private BigDecimal yearToDateEarnings=BigDecimal.ZERO;
  static int count=0;
  
  private BigDecimal getCheckPoint(){
     return this.getTotalAssets().add(this.getTotalLiabilitiesAndEquity());
  }
  
  private BigDecimal getTotalLiabilitiesAndEquity(){
     return this.currentPortionOfBankLoans.add(this.bankLoans).add(this.leaseholdInducement).add(this.shareholderLoanJVPartners).add(this.shareholderLoanPPL).add(this.capitalStock).add(this.retainedEarnings).add(this.DividendsPaid).add(this.yearToDateEarnings).add(this.getTotalCurrentLiabilities());
  }
  
  
  private BigDecimal getTotalCurrentLiabilities(){
     return this.accountsPayable.add(this.managementFeePayable).add(this.accruedPayrable).add(this.accruedWagesAndBenefits).add(this.gstPayable).add(this.inputTaxCredit).add(this.corporationTaxesPayable).add(this.deferredRevenue).add(this.intercoPayable);
  }
  
  private BigDecimal getTotalAssets(){
     return this.getTotalCurrentAssets().add(this.equipment).add(this.leaseholdImproments).add(this.preoperatingExpenses).add(this.futureIncomeTax);
  }
  
  private BigDecimal getTotalCurrentAssets(){
     return this.cash.add(this.accountsReceivable).add(this.inventory).add(this.prepaid);
  }

public void setUp() throws IOException
{
   String[] paths = {
      "util-applicationContext-resources.xml", "applicationContext.xml" };
   ctx = new ClassPathXmlApplicationContext(paths);
   lookupManager =(LookupManager) ctx.getBean("lookupManager");
}



public static void main(String[] args) throws Exception{
   TrialBalance trialBalance=new TrialBalance();
   trialBalance.setUp();
   System.out.println("<table><tr>");
   for(int i=63;i<64;i++){
      trialBalance.calculateForm(2009, 1, i);
   }
   System.out.println("</tr></table>");
}

private void writeEnd(){
   System.out.println("</table>");
}

private void writeHeader(int year, int period){
   System.out.println("<h1>Balance Sheet - Pizza 73 Period "+period+", "+year+"</h1>");
   System.out.println("<table style=\"text-align:right\">");
   System.out.println("<tr style=\"background-color:#D8D8DA;text-align:center\">");
   System.out.println("<td>Impact #</td>");
   System.out.println("<td>Shop Name</td>");
   System.out.println("<td>Cash</td>");
   System.out.println("<td>Accounts Receivable</td>");
   System.out.println("<td>Inventory</td>");
   System.out.println("<td>Prepaid</td>");
   System.out.println("<td>Total Current Assets</td>");

   System.out.println("<td>Equipment</td>");
   System.out.println("<td>Leasehold Improvements</td>");
   System.out.println("<td>Pre-operating Expenses</td>");
   System.out.println("<td>Future Income Tax</td>");
   System.out.println("<td>Total Assets</td>");
   System.out.println("<td>Accounts Payable</td>");
   System.out.println("<td>Management Fee Payable</td>");
   System.out.println("<td>Accrued Payable</td>");

   System.out.println("<td>Accrued Wages & Benefits</td>");

   System.out.println("<td>GST Payable</td>");
   System.out.println("<td>Income Tax Credit</td>");
   System.out.println("<td>Corporation Taxes Payable</td>");
   System.out.println("<td>Deferred Revenue</td>");
   System.out.println("<td>Inter-co Payable</td>");
   System.out.println("<td>Total Current Liabilities</td>");

   System.out.println("<td>Current Portion Of Bank Loans</td>");
   System.out.println("<td>Bank Loans</td>");

   System.out.println("<td>Leasehold Inducement</td>");
   System.out.println("<td>shareholder's Loan-JV Partner</td>");

   System.out.println("<td>shareholder's Loan-PPL</td>");
   System.out.println("<td>Capital Stock</td>");
   System.out.println("<td>Retained Earnings</td>");

   System.out.println("<td>Devidends Paid</td>");

   System.out.println("<td>Year-to-Date Earnings</td>");
   System.out.println("<td>Total Liabilitites & Equity</td>");

   System.out.println("<td>Check Point</td>");
   System.out.println("</tr>");   
}

public void calculateForm(Integer year, Integer period, Integer shopId) throws IOException{
     if(this.lookupManager.GLRecordsForPeriodAndShop(year, 12, shopId).size() ==0)
        return ;
     System.out.println("<td style='vertical-align:top;'>");
     System.out.println("<h1>Shop#"+String.valueOf(1000+shopId)+ ": "+((Shop)this.lookupManager.get(Shop.class, shopId)).getName()+"</h1>");
     System.out.println("<table border='1'>");
     System.out.println("<tr><th>Account</th><th>Description</th><th>Beginning Balance</th><th>Total Debit</th><th>Total Credit</th><th>Closing Balance</th>");
     List<Integer> accounts=this.lookupManager.GLaccountsForYearAndShop(year,shopId);
     for(int i=0;i<accounts.size();i++){
        Integer account=accounts.get(i);
        System.out.println("<tr><td>"+account+"</td>");
        List<GL> records = this.lookupManager.GLRecordsForYearAndShop(year,account, shopId);
        BigDecimal opening=getBeginningBalance(records);
        BigDecimal totalDebit=getTotalDebit(records);
        BigDecimal totalCredit=getTotalCredit(records);
        BigDecimal ending=opening.add(totalDebit).add(totalCredit);
        System.out.println("<td>"+getAccountName(records)+"</td>");
        System.out.println("<td>"+opening+"</td>");
        System.out.println("<td>"+totalDebit+"</td>");
        System.out.println("<td>"+totalCredit+"</td>");
        System.out.println("<td>"+ending+"</td>");
        System.out.println("</tr>");
     }
     System.out.println("</table>");
     System.out.println("</td>");
}

public String getAccountName(List<GL> GLRecords){
   return GLRecords.get(0).getName();
}

public BigDecimal getTotalCredit(List<GL> GLRecords) throws IOException{
   BigDecimal result=BigDecimal.valueOf(0,2);
   Iterator<GL> iter = GLRecords.iterator();
   GL temp=null;
   while(iter.hasNext()){
      temp=iter.next();
      if (temp.getSource().trim().equalsIgnoreCase("BEG-BAL")){
         continue;
      }
      else{
         result=result.subtract(temp.getCredit());
      }
   }
   return result;
}

public BigDecimal getTotalDebit(List<GL> GLRecords) throws IOException{
   BigDecimal result=BigDecimal.valueOf(0,2);
   Iterator<GL> iter = GLRecords.iterator();
   GL temp=null;
   while(iter.hasNext()){
      temp=iter.next();
      if (temp.getSource().trim().equalsIgnoreCase("BEG-BAL")){
         continue;
      }
      else{
         result=result.add(temp.getDebit());
      }
   }
   return result;
}

public BigDecimal getBeginningBalance(List<GL> GLRecords) throws IOException{
   BigDecimal result=BigDecimal.valueOf(0,2);
   boolean passFirstBeginingBalance=false;
   Iterator<GL> iter = GLRecords.iterator();
   GL temp=null;
   while(iter.hasNext()){
      temp=iter.next();
      if (temp.getSource().trim().equalsIgnoreCase("BEG-BAL")){
         if(!passFirstBeginingBalance){
         passFirstBeginingBalance=true;
         result=temp.getDebit().subtract(temp.getCredit());
      }
     }
   }
   return result;
}

public BigDecimal calcuateEndingBalance(List<GL> GLRecords) throws IOException{
   BigDecimal endingBalance=BigDecimal.valueOf(0,2);
   boolean passFirstBeginingBalance=false;
   int i=0;
   Iterator<GL> iter = GLRecords.iterator();
   GL temp=null;
   while(iter.hasNext()){
      temp=iter.next();
      if (temp.getSource().trim().equalsIgnoreCase("BEG-BAL")){
         if(!passFirstBeginingBalance){
         passFirstBeginingBalance=true;
         endingBalance=endingBalance.add(temp.getDebit()).subtract(temp.getCredit());
         }else
            continue;
      }else{
         endingBalance=endingBalance.add(temp.getDebit()).subtract(temp.getCredit());
      }
   }
   for(i=0;i<GLRecords.size();i++){
         
         endingBalance=endingBalance.add(GLRecords.get(i).getDebit()).subtract(GLRecords.get(i).getCredit());
   }   
   return endingBalance;
}

}
