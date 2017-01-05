package com.pizza73.accounting;

import java.text.NumberFormat;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

/**
 * BalanceSheet - TODO comment me
 *
 * @author chris
 * Created on: 16-Mar-08
 * 
 * Copyright Pizza 73 2008
 */
public class BalanceSheet implements Comparable<BalanceSheet>
{
   public static final int CASH_LINE = 7;
   public static final int ACCTS_REC_LINE = 8;
   public static final int INVENTORY_LINE = 9;
   public static final int PREPAID_EXP_LINE = 10;
   public static final int PRE_OPEN_EXP_LINE = 11;
   public static final int EQUIP_LINE = 12;
   public static final int UNIFORM_LINE = 13;
   public static final int LEASE_IMP_LINE = 14;
   public static final int FUTURE_INC_LINE = 15;
   public static final int INCORP_COSTS_LINE = 16;
   public static final int ACCTS_PAYABLE_LINE = 17;
   public static final int MNGMT_FEES_LINE = 18;
   public static final int ACCTS_PAYABLE_MANUAL_LINE = 19;
   public static final int ACCRUED_WAGES_LINE = 20;
   public static final int GST_PAYABLE_LINE = 21;
   public static final int CORP_TAX_LINE = 22;
   public static final int DEF_INCOME_TAX_LINE = 23;
   public static final int DEF_REV_LINE = 24;
   public static final int INTERCO_PAYABLE_LINE = 25;
   public static final int CURR_PORTION_LTD_LINE = 26;
   public static final int BANK_LOAN_LINE = 27;
   public static final int LEASE_INDUC_LINE = 28;
   public static final int SHARE_LOAN_LINE = 29;
   public static final int CAP_STOCK_LINE = 30;
   public static final int RETAINED_EARNINGS_LINE = 31;
   public static final int DIVIDENDS_PAID_LINE = 32;
   public static final int YTD_EARNINGS_LINE = 33;
 
   public static final String DELIMITER = "|";
   
   public static final NumberFormat nf = NumberFormat.getCurrencyInstance();
   
   public String shopId;
   public double cash;
   public double accountsReceivable;
   public double inventory;
   public double prePaidExpenses;
   public double preOpeningExpenses;
   public double equipment;
   public double uniforms;
   public double leaseholdImprovements;
   public double futureIncome;
   public double incorporationCosts;
   public double accountsPayable;
   public double managementFeesPayable;
   public double accountPayableManual;
   public double accruedWagesAndBenefits;
   public double gstPayable;
   public double corporateTaxPayable;
   public double deferredIncomeTax;
   public double deferredRevenue;
   public double intercoPayable73Inc;
   public double currentPortionOfLtd;
   public double bankLoan;
   public double leaseholdInducement;
   public double shareholdersLoan;
   public double capitalStock;
   public double retainedEarnings;
   public double dividendsPaid;
   public double ytdEarnings;
   
   public BalanceSheet()
   {}

   public BalanceSheet(String shopId)
   {
      this.shopId = shopId;
   }
   
   public String getShopId()
   {
      return this.shopId;
   }
   
   public void setShopId(String shopId)
   {
      this.shopId = shopId;
   }
   
   public double getCash()
   {
      return this.cash;
   }

   public void setCash(double cash)
   {
      this.cash = cash;
   }

   public double getAccountsReceivable()
   {
      return this.accountsReceivable;
   }

   public void setAccountsReceivable(double accountsReceivable)
   {
      this.accountsReceivable = accountsReceivable;
   }

   public double getInventory()
   {
      return this.inventory;
   }

   public void setInventory(double inventory)
   {
      this.inventory = inventory;
   }

   public double getPrePaidExpenses()
   {
      return this.prePaidExpenses;
   }

   public void setPrePaidExpenses(double prePaidExpenses)
   {
      this.prePaidExpenses = prePaidExpenses;
   }

   public double getPreOpeningExpenses()
   {
      return this.preOpeningExpenses;
   }

   public void setPreOpeningExpenses(double preOpeningExpenses)
   {
      this.preOpeningExpenses = preOpeningExpenses;
   }

   public double getEquipment()
   {
      return this.equipment;
   }

   public void setEquipment(double equipment)
   {
      this.equipment = equipment;
   }

   public double getUniforms()
   {
      return this.uniforms;
   }

   public void setUniforms(double uniforms)
   {
      this.uniforms = uniforms;
   }

   public double getLeaseholdImprovements()
   {
      return this.leaseholdImprovements;
   }

   public void setLeaseholdImprovements(double leaseholdImprovements)
   {
      this.leaseholdImprovements = leaseholdImprovements;
   }

   public double getFutureIncome()
   {
      return this.futureIncome;
   }

   public void setFutureIncome(double futureIncome)
   {
      this.futureIncome = futureIncome;
   }

   public double getIncorporationCosts()
   {
      return this.incorporationCosts;
   }

   public void setIncorporationCosts(double incorporationCosts)
   {
      this.incorporationCosts = incorporationCosts;
   }

   public double getAccountsPayable()
   {
      return this.accountsPayable;
   }

   public void setAccountsPayable(double accountsPayable)
   {
      this.accountsPayable = accountsPayable;
   }

   public double getManagementFeesPayable()
   {
      return this.managementFeesPayable;
   }

   public void setManagementFeesPayable(double managementFeesPayable)
   {
      this.managementFeesPayable = managementFeesPayable;
   }

   public double getAccountPayableManual()
   {
      return this.accountPayableManual;
   }

   public void setAccountPayableManual(double accountPayableManual)
   {
      this.accountPayableManual = accountPayableManual;
   }

   public double getAccruedWagesAndBenefits()
   {
      return this.accruedWagesAndBenefits;
   }

   public void setAccruedWagesAndBenefits(double accruedWagesAndBenefits)
   {
      this.accruedWagesAndBenefits = accruedWagesAndBenefits;
   }

   public double getGstPayable()
   {
      return this.gstPayable;
   }

   public void setGstPayable(double gstPayable)
   {
      this.gstPayable = gstPayable;
   }

   public double getCorporateTaxPayable()
   {
      return this.corporateTaxPayable;
   }

   public void setCorporateTaxPayable(double corporateTaxPayable)
   {
      this.corporateTaxPayable = corporateTaxPayable;
   }

   public double getDeferredIncomeTax()
   {
      return this.deferredIncomeTax;
   }

   public void setDeferredIncomeTax(double deferredIncomeTax)
   {
      this.deferredIncomeTax = deferredIncomeTax;
   }

   public double getDeferredRevenue()
   {
      return this.deferredRevenue;
   }

   public void setDeferredRevenue(double deferredRevenue)
   {
      this.deferredRevenue = deferredRevenue;
   }

   public double getIntercoPayable73Inc()
   {
      return this.intercoPayable73Inc;
   }

   public void setIntercoPayable73Inc(double intercoPayable73Inc)
   {
      this.intercoPayable73Inc = intercoPayable73Inc;
   }

   public double getCurrentPortionOfLtd()
   {
      return this.currentPortionOfLtd;
   }

   public void setCurrentPortionOfLtd(double currentPortionOfLtd)
   {
      this.currentPortionOfLtd = currentPortionOfLtd;
   }

   public double getBankLoan()
   {
      return this.bankLoan;
   }

   public void setBankLoan(double bankLoan)
   {
      this.bankLoan = bankLoan;
   }

   public double getLeaseholdInducement()
   {
      return this.leaseholdInducement;
   }

   public void setLeaseholdInducement(double leaseholdInducement)
   {
      this.leaseholdInducement = leaseholdInducement;
   }

   public double getShareholdersLoan()
   {
      return this.shareholdersLoan;
   }

   public void setShareholdersLoan(double shareholdersLoan)
   {
      this.shareholdersLoan = shareholdersLoan;
   }

   public double getCapitalStock()
   {
      return this.capitalStock;
   }

   public void setCapitalStock(double capitalStock)
   {
      this.capitalStock = capitalStock;
   }

   public double getRetainedEarnings()
   {
      return this.retainedEarnings;
   }

   public void setRetainedEarnings(double retainedEarnings)
   {
      this.retainedEarnings = retainedEarnings;
   }

   public double getDividendsPaid()
   {
      return this.dividendsPaid;
   }

   public void setDividendsPaid(double dividendsPaid)
   {
      this.dividendsPaid = dividendsPaid;
   }

   public double getYtdEarnings()
   {
      return this.ytdEarnings;
   }

   public void setYtdEarnings(double ytdEarnings)
   {
      this.ytdEarnings = ytdEarnings;
   }

   /**
    * @param line
    * @param lineNumber
    */
   public void parseLine(String line, int lineNumber)
   {
      StringTokenizer st = new StringTokenizer(line, ",");
      int count = 0;
      while(st.hasMoreElements())
      {
         String value = st.nextToken();
         count++;
         if(count == 3 && lineNumber == BalanceSheet.CASH_LINE)
         {
            this.cash =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.ACCTS_REC_LINE)
         {
            this.accountsReceivable =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.INVENTORY_LINE)
         {
            this.inventory =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.PREPAID_EXP_LINE)
         {
            this.prePaidExpenses =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.PRE_OPEN_EXP_LINE)
         {
            this.preOpeningExpenses =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.EQUIP_LINE)
         {
            this.equipment =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.UNIFORM_LINE)
         {
            this.uniforms =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.LEASE_IMP_LINE)
         {
            this.leaseholdImprovements =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.FUTURE_INC_LINE)
         {
            this.futureIncome =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.INCORP_COSTS_LINE)
         {
            this.incorporationCosts =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.ACCTS_PAYABLE_LINE)
         {
            this.accountsPayable =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.MNGMT_FEES_LINE)
         {
            this.managementFeesPayable =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.ACCTS_PAYABLE_MANUAL_LINE)
         {
            this.accountPayableManual =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.ACCRUED_WAGES_LINE)
         {
            this.accruedWagesAndBenefits =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.GST_PAYABLE_LINE)
         {
            this.gstPayable =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.CORP_TAX_LINE)
         {
            this.corporateTaxPayable =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.DEF_INCOME_TAX_LINE)
         {
            this.deferredIncomeTax =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.DEF_REV_LINE)
         {
            this.deferredRevenue =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.INTERCO_PAYABLE_LINE)
         {
            this.intercoPayable73Inc =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.CURR_PORTION_LTD_LINE)
         {
            this.currentPortionOfLtd =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.BANK_LOAN_LINE)
         {
            this.bankLoan =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.LEASE_INDUC_LINE)
         {
            this.leaseholdInducement =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.SHARE_LOAN_LINE)
         {
            this.shareholdersLoan =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.CAP_STOCK_LINE)
         {
            this.capitalStock =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.RETAINED_EARNINGS_LINE)
         {
            this.retainedEarnings =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.DIVIDENDS_PAID_LINE)
         {
            this.dividendsPaid =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
         else if(count == 3 && lineNumber == BalanceSheet.YTD_EARNINGS_LINE)
         {
            this.ytdEarnings =
               Double.parseDouble(StringUtils.trimToEmpty(value));
            break;
         }
      }
   }
   
   public double getTotalCurrentAssets()
   {
      double result = 
         this.cash + this.accountsReceivable + this.inventory 
         + this.prePaidExpenses;
      
      return result;
   }
   
   public double getTotalAssets()
   {
      double result = 
         getTotalCurrentAssets()+ this.preOpeningExpenses + this.equipment +  this.leaseholdImprovements +
         this.futureIncome + this.incorporationCosts;
      
      return result;
   }
   
   public double getCurrentLiabilities()
   {
      double result = 
         this.accountsPayable + this.accountPayableManual
         + this.accruedWagesAndBenefits + this.gstPayable + 
         this.corporateTaxPayable + this.intercoPayable73Inc;
      
      return result;
   }
   
   public double getTotalLiabilitiesAndEquity()
   {
      double result = 
         this.getCurrentLiabilities() + this.currentPortionOfLtd + this.bankLoan 
         + this.leaseholdInducement +    
         this.shareholdersLoan + this.capitalStock + this.retainedEarnings + 
         this.dividendsPaid + this.ytdEarnings;
      return result;
   }

   /* (non-Javadoc)
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(BalanceSheet bs)
   {
      if (this.getShopId() != null && bs.getShopId() != null)
      {
         return this.getShopId().compareTo(bs.getShopId());
      }
      return 0;
   }
   
   @Override
   public String toString()
   {
      return this.shopId + DELIMITER + nf.format(this.cash) + DELIMITER + nf.format(this.accountsReceivable)
      + DELIMITER + nf.format(this.inventory) + DELIMITER + nf.format(this.prePaidExpenses)
      + DELIMITER + nf.format(this.getTotalCurrentAssets()) + DELIMITER + nf.format(this.getPreOpeningExpenses())  
      + DELIMITER + nf.format(this.equipment)
      + DELIMITER + nf.format(this.leaseholdImprovements) + DELIMITER + nf.format(this.futureIncome)
      + DELIMITER + nf.format(this.incorporationCosts)
      + DELIMITER + nf.format(this.getTotalAssets()) + DELIMITER + nf.format(this.accountsPayable)
      + DELIMITER + nf.format(this.accountPayableManual) + DELIMITER + nf.format(this.accruedWagesAndBenefits)
      + DELIMITER + nf.format(this.gstPayable) + DELIMITER + nf.format(this.corporateTaxPayable)
      + DELIMITER + nf.format(this.intercoPayable73Inc) + DELIMITER + nf.format(this.getCurrentLiabilities())
      + DELIMITER + nf.format(this.shareholdersLoan)  
      + DELIMITER + nf.format(this.currentPortionOfLtd)+ DELIMITER + nf.format(this.bankLoan)
      + DELIMITER + nf.format(this.leaseholdInducement)
      + DELIMITER + nf.format(this.capitalStock)
      + DELIMITER + nf.format(this.retainedEarnings) + DELIMITER + nf.format(this.dividendsPaid)
      + DELIMITER + nf.format(this.ytdEarnings) + DELIMITER + nf.format(this.getTotalLiabilitiesAndEquity());
   }
   
   public String toPythonString()
   {
      return this.shopId + DELIMITER + nf.format(this.cash) + DELIMITER + nf.format(this.accountsReceivable)
      + DELIMITER + nf.format(this.inventory) + DELIMITER + nf.format(this.prePaidExpenses)
      + DELIMITER + nf.format(this.getTotalCurrentAssets()) + DELIMITER + nf.format(this.equipment)
      + DELIMITER + nf.format(this.leaseholdImprovements) + DELIMITER + nf.format(this.futureIncome)
      + DELIMITER + nf.format(this.getTotalAssets()) + DELIMITER + nf.format(this.accountsPayable)
      + DELIMITER + nf.format(this.accountPayableManual) + DELIMITER + nf.format(this.accruedWagesAndBenefits)
      + DELIMITER + nf.format(this.gstPayable) + DELIMITER + nf.format(this.corporateTaxPayable)
      + DELIMITER + nf.format(this.intercoPayable73Inc) + DELIMITER + nf.format(this.getCurrentLiabilities())
      + DELIMITER + nf.format(this.shareholdersLoan) + DELIMITER + nf.format(this.capitalStock)
      + DELIMITER + nf.format(this.retainedEarnings) + DELIMITER + nf.format(this.dividendsPaid)
      + DELIMITER + nf.format(this.ytdEarnings) + DELIMITER + nf.format(this.getTotalLiabilitiesAndEquity());
   }
}
