package com.pizza73.util;

import com.pizza73.model.GL;
import com.pizza73.model.Shop;
import com.pizza73.service.LookupManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class BalanceSheet
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
  private BigDecimal advanceToShareholders=BigDecimal.ZERO;
  private BigDecimal accountsPayable=BigDecimal.ZERO;
  private BigDecimal managementFeePayable=BigDecimal.ZERO;
  private BigDecimal accruedPayrable=BigDecimal.ZERO;
  private BigDecimal pizzaCardClearing=BigDecimal.ZERO;
  private BigDecimal accruedWagesAndBenefits=BigDecimal.ZERO;
  private BigDecimal gstPayable=BigDecimal.ZERO;
  private BigDecimal inputTaxCredit=BigDecimal.ZERO;
  private BigDecimal corporationTaxesPayable=BigDecimal.ZERO;
  private BigDecimal deferredRevenue=BigDecimal.ZERO;
  private BigDecimal intercoPayable=BigDecimal.ZERO;
  private BigDecimal currentPortionOfBankLoans=BigDecimal.ZERO;
  private BigDecimal bankLoans=BigDecimal.ZERO;
  private BigDecimal leaseholdInducement=BigDecimal.ZERO;
  private BigDecimal promissoryNotePayable=BigDecimal.ZERO;
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
     return this.currentPortionOfBankLoans.add(this.bankLoans).add(this.leaseholdInducement).add(this.promissoryNotePayable).add(this.shareholderLoanJVPartners).add(this.shareholderLoanPPL).add(this.capitalStock).add(this.retainedEarnings).add(this.DividendsPaid).add(this.yearToDateEarnings).add(this.getTotalCurrentLiabilities());
  }


  private BigDecimal getTotalCurrentLiabilities(){
     return this.accountsPayable.add(this.managementFeePayable).add(this.accruedPayrable).add(this.pizzaCardClearing).add(this.accruedWagesAndBenefits).add(this.gstPayable).add(this.inputTaxCredit).add(this.corporationTaxesPayable).add(this.deferredRevenue).add(this.intercoPayable);
  }

  private BigDecimal getTotalAssets(){
     return this.getTotalCurrentAssets().add(this.equipment).add(this.leaseholdImproments).add(this.preoperatingExpenses).add(this.futureIncomeTax).add(this.advanceToShareholders);
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

public void zeorFill() {
  cash=BigDecimal.ZERO;
  accountsReceivable=BigDecimal.ZERO;
  inventory=BigDecimal.ZERO;
  prepaid=BigDecimal.ZERO;
  equipment=BigDecimal.ZERO;
  leaseholdImproments=BigDecimal.ZERO;
  preoperatingExpenses=BigDecimal.ZERO;
  futureIncomeTax=BigDecimal.ZERO;
  advanceToShareholders=BigDecimal.ZERO;
  accountsPayable=BigDecimal.ZERO;
  managementFeePayable=BigDecimal.ZERO;
  accruedPayrable=BigDecimal.ZERO;
  pizzaCardClearing=BigDecimal.ZERO;
  accruedWagesAndBenefits=BigDecimal.ZERO;
  gstPayable=BigDecimal.ZERO;
  inputTaxCredit=BigDecimal.ZERO;
  corporationTaxesPayable=BigDecimal.ZERO;
  deferredRevenue=BigDecimal.ZERO;
  intercoPayable=BigDecimal.ZERO;
  currentPortionOfBankLoans=BigDecimal.ZERO;
  bankLoans=BigDecimal.ZERO;
  leaseholdInducement=BigDecimal.ZERO;
  promissoryNotePayable=BigDecimal.ZERO;
  shareholderLoanJVPartners=BigDecimal.ZERO;
  shareholderLoanPPL=BigDecimal.ZERO;
  capitalStock=BigDecimal.ZERO;
  retainedEarnings=BigDecimal.ZERO;
  DividendsPaid=BigDecimal.ZERO;
  yearToDateEarnings=BigDecimal.ZERO;
}

public static void main(String[] args) throws Exception{
   BalanceSheet balanceSheet=new BalanceSheet();
   balanceSheet.setUp();
   Integer year=Integer.valueOf(args[0]);
   Integer period=Integer.valueOf(args[1]);
   balanceSheet.writeHeader(year, period);
   for(int i=1;i<80;i++){
   balanceSheet.calculateForm(year, period, i);
   balanceSheet.zeorFill();
   }
   balanceSheet.writeEnd();
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
   System.out.println("<td>Advance To Shareholders</td>");
   System.out.println("<td>Total Assets</td>");
   System.out.println("<td>Accounts Payable</td>");
   System.out.println("<td>Management Fee Payable</td>");
   System.out.println("<td>Accrued Payable</td>");
   System.out.println("<td>Pizza Card Clearing</td>");

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
   System.out.println("<td>Promissory Note Payable/PPL Construction</td>");
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
     if(this.lookupManager.GLRecordsForPeriodAndShop(year, period, shopId).size() ==0)
        return ;
     BalanceSheet.count++;
     if(BalanceSheet.count %2 == 0){
     System.out.println("<tr style='background-color:#EDF5FF'>");
     }
     else
     System.out.println("<tr>");
     Shop shop=(Shop)this.lookupManager.get(Shop.class,shopId);
     System.out.println("<td>"+String.valueOf(1000+shopId)+"</td>");
     System.out.println("<td>"+shop.getName()+"</td>");
     this.cash=this.cash.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1000, shopId)));
     this.cash=this.cash.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1110, shopId)));
     System.out.println("<td title='gl1100+gl1110'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=1100,1110'>"+this.cash+"</a></td>");
     this.accountsReceivable=this.accountsReceivable.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1200, shopId)));
     System.out.println("<td title='gl1200'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=1200'>"+this.accountsReceivable+"</a></td>");
     this.inventory=this.inventory.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1300, shopId)));
     this.inventory=this.inventory.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1301, shopId)));
     System.out.println("<td title='gl1300+gl1301'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=1300,1301'>"+this.inventory+"</a></td>");
     this.prepaid=this.prepaid.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1400, shopId)));
     System.out.println("<td title='gl1400'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=1400'>"+this.prepaid+"</a></td>");
     System.out.println("<td >"+this.getTotalCurrentAssets()+"</td>");
     this.equipment=this.equipment.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1600, shopId)));
     this.equipment=this.equipment.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1602, shopId)));
     this.equipment=this.equipment.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1610, shopId)));
     this.equipment=this.equipment.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1603, shopId)));
     this.equipment=this.equipment.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1611, shopId)));
     this.equipment=this.equipment.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1650, shopId)));
     System.out.println("<td title='gl1600+gl1602+gl1610+gl1603+gl1611+gl1650'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=1600,1602,1610,1603,1611,1650'>"+this.equipment+"</a></td>");
     this.leaseholdImproments=this.leaseholdImproments.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1700, shopId)));
     this.leaseholdImproments=this.leaseholdImproments.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1703, shopId)));
     System.out.println("<td title='gl1700+gl1703'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=1700,1703'>"+this.leaseholdImproments+"</a></td>");
     this.preoperatingExpenses=this.preoperatingExpenses.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1500, shopId)));
     this.preoperatingExpenses=this.preoperatingExpenses.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1501, shopId)));
     this.preoperatingExpenses=this.preoperatingExpenses.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1800, shopId)));
     System.out.println("<td title='gl1500+gl1501+gl1800'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=1500,1501,1800'>"+this.preoperatingExpenses+"</a></td>");
     this.futureIncomeTax=this.futureIncomeTax.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1722, shopId)));
     System.out.println("<td title='gl1722'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=1722'>"+this.futureIncomeTax+"</a></td>");
     this.advanceToShareholders=this.advanceToShareholders.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 1900, shopId)));
     System.out.println("<td title='gl1900'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=1900'>"+this.advanceToShareholders+"</a></td>");
     System.out.println("<td>"+this.getTotalAssets()+"</td>");
     this.accountsPayable=this.accountsPayable.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2000, shopId)));
     System.out.println("<td title='gl2000'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2000'>"+this.accountsPayable+"</a></td>");
     this.managementFeePayable=this.managementFeePayable.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2001, shopId)));
     this.managementFeePayable=this.managementFeePayable.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2003, shopId)));
     System.out.println("<td title='gl2001+gl2003'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2001,2003'>"+this.managementFeePayable+"</a></td>");
     this.accruedPayrable=this.accruedPayrable.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2002, shopId)));
     System.out.println("<td title='gl2002'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2002'>"+this.accruedPayrable+"</a></td>");
     this.pizzaCardClearing=this.pizzaCardClearing.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2004, shopId)));
     System.out.println("<td title='gl2004'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2004'>"+this.pizzaCardClearing+"</a></td>");
     this.accruedWagesAndBenefits=this.accruedWagesAndBenefits.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2100, shopId)));
     this.accruedWagesAndBenefits=this.accruedWagesAndBenefits.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2200, shopId)));
     this.accruedWagesAndBenefits=this.accruedWagesAndBenefits.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2210, shopId)));
     this.accruedWagesAndBenefits=this.accruedWagesAndBenefits.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2220, shopId)));
     System.out.println("<td title='gl2100+2200+2210+2220'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2100,2200,2210,2220'>"+this.accruedWagesAndBenefits+"</a></td>");
     this.gstPayable=this.gstPayable.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2250, shopId)));
     System.out.println("<td title='gl2250'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2250'>"+this.gstPayable+"</a></td>");
     this.inputTaxCredit=this.inputTaxCredit.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2251, shopId)));
     System.out.println("<td title='gl2251'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2251'>"+this.inputTaxCredit+"</a></td>");
     this.corporationTaxesPayable=this.corporationTaxesPayable.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2275, shopId)));
     System.out.println("<td title='gl2275'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2275'>"+this.corporationTaxesPayable+"</a></td>");
     this.deferredRevenue=this.deferredRevenue.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2300, shopId)));
     System.out.println("<td title='gl2300'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2300'>"+this.deferredRevenue+"</a></td>");
     this.intercoPayable=this.intercoPayable.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2400, shopId)));
     this.intercoPayable=this.intercoPayable.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2410, shopId)));
     this.intercoPayable=this.intercoPayable.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2420, shopId)));
     System.out.println("<td title='gl2400+gl2410+gl2420'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2400,2410,2420'>"+this.intercoPayable+"</a></td>");
     System.out.println("<td>"+this.getTotalCurrentLiabilities()+"</td>");
     this.currentPortionOfBankLoans=this.currentPortionOfBankLoans.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2501, shopId)));
     System.out.println("<td title='gl2501'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2501'>"+this.currentPortionOfBankLoans+"</a></td>");
     this.bankLoans=this.bankLoans.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2450, shopId)));
     this.bankLoans=this.bankLoans.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2500, shopId)));
     System.out.println("<td title='gl2450+gl2500'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2450,2500'>"+this.bankLoans+"</a></td>");
     this.leaseholdInducement=this.leaseholdInducement.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2299, shopId)));
     System.out.println("<td title='gl2299'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2299'>"+this.leaseholdInducement+"</a></td>");
     this.promissoryNotePayable=this.promissoryNotePayable.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2520, shopId)));
     System.out.println("<td title='gl2520'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2520'>"+this.promissoryNotePayable+"</a></td>");
     this.shareholderLoanJVPartners=this.shareholderLoanJVPartners.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2550, shopId)));
     System.out.println("<td title='gl2550'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2550'>"+this.shareholderLoanJVPartners+"</a></td>");
     this.shareholderLoanPPL=this.shareholderLoanPPL.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2551, shopId)));
     System.out.println("<td title='gl2551'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2551'>"+this.shareholderLoanPPL+"</a></td>");
     this.capitalStock=this.capitalStock.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2600, shopId)));
     System.out.println("<td title='gl2600'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2600'>"+this.capitalStock+"</a></td>");
     this.retainedEarnings=this.retainedEarnings.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2610, shopId)));
     System.out.println("<td title='gl2610'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2610'>"+this.retainedEarnings+"</a></td>");
     this.DividendsPaid=this.DividendsPaid.add(this.calcuateEndingBalance(this.lookupManager.GLRecordsForPeriodAndShop(year, period, 2612, shopId)));
     System.out.println("<td title='gl2612'><a href='/iqueso-bin/balance.py?function=detail&year="+year+"&period="+period+"&shop_id="+shopId+"&accounts=2612'>"+this.DividendsPaid+"</a></td>");
     this.yearToDateEarnings=this.yearToDateEarnings.add(this.calcuateEndingBalance(this.lookupManager.yearToDateEarningsForPeriodAndShop(year, period, shopId)));
     System.out.println("<td>"+this.yearToDateEarnings+"</td>");
     System.out.println("<td>"+this.getTotalLiabilitiesAndEquity()+"</td>");
     System.out.println("<td>"+this.getCheckPoint()+"</td>");
     System.out.println("</tr>");
}

public BigDecimal calcuateEndingBalance(List<GL> GLRecords) throws IOException{
   BigDecimal endingBalance=BigDecimal.valueOf(0,2);
   int i=0;
   for(i=0;i<GLRecords.size();i++){
         endingBalance=endingBalance.add(GLRecords.get(i).getDebit()).subtract(GLRecords.get(i).getCredit());
   }
   return endingBalance;
}

}
