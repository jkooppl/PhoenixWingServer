package com.pizza73.webapp.viewResolver;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.pizza73.model.Employee;
import com.pizza73.model.Payroll;
import com.pizza73.model.ShopPayroll;

/**
 * ExportPayrollView
 * 
 * @author chris 7-Sept-06
 * 
 * @Copyright Flying Pizza 73
 */
public class ExportPayrollView extends AbstractExcelView
{
   private static final String REGULAR_PAY_CODE = "01";
   private static final String OT_PAY_CODE = "02";
   private static final String STAT_PAY_CODE = "04";   
   private static final String GEN_FLAG = "N";

   public ExportPayrollView()
   {

   }

   /*
    * (non-Javadoc)
    * 
    * @see org.springframework.web.servlet.view.document.AbstractExcelView#buildExcelDocument(java.util.Map,
    *      org.apache.poi.hssf.usermodel.HSSFWorkbook,
    *      javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse)
    */
   @SuppressWarnings("unchecked")
   @Override
   protected void buildExcelDocument(Map map, HSSFWorkbook wb,
         HttpServletRequest request, HttpServletResponse response)
   {
      HSSFSheet sheet = null;
      List<ShopPayroll> payrolls = (List<ShopPayroll>) map.get("PAYROLL");
      Integer currentPayrollYear = (Integer)map.get("PAYROLL_YEAR"); 
      Integer currentPayrollPeriod = (Integer)map.get("PAYROLL_PERIOD");
            
      ShopPayroll payroll = null;
      int rowNum = 0;
      
      if(payrolls.size() == 1)
      {
         payroll = payrolls.get(0);
         sheet = 
            wb.createSheet("payroll-" + payroll.getShop().getId()
            + "-" + currentPayrollPeriod + "-" + currentPayrollYear);
         rowNum=buildDataHeader(sheet,rowNum);
         buildDataList(sheet, payroll, rowNum);
      }
      else
      {
         sheet = 
            wb.createSheet(
               "payroll-" + currentPayrollPeriod + "-" + currentPayrollYear);
         rowNum=buildDataHeader(sheet,rowNum);
         Iterator<ShopPayroll> iter = payrolls.iterator();
         while(iter.hasNext())
         {
            payroll = iter.next();
            if(payroll != null)
            {
               rowNum = buildDataList(sheet, payroll, rowNum);
            }
         }
      }
   }

   /**
    * @param sheet
    * @param personList
    * @param report
    */
   
   private int buildDataHeader(HSSFSheet sheet,int rowNum){
      HSSFCell cell = null;
      
      cell = getCell(sheet, rowNum, 0);
      cell.setCellValue("Co Code");
      cell = getCell(sheet, rowNum, 1);
      cell.setCellValue("Batch ID");
      cell = getCell(sheet, rowNum, 2);
      cell.setCellValue("File #");
      cell = getCell(sheet, rowNum, 3);      
      cell.setCellValue("Reg hours");
      cell = getCell(sheet, rowNum, 4);
      cell.setCellValue("O/T Hours");
      cell = getCell(sheet, rowNum, 5);
      cell.setCellValue("Hours 3 Code");
      cell = getCell(sheet, rowNum, 6);
      cell.setCellValue("Hours 3 Amount");
      
      rowNum++;
      
      return rowNum;
      
   }
   @SuppressWarnings("unchecked")
   private int buildDataList(HSSFSheet sheet, ShopPayroll payroll, int rowNum)
   {  
      HSSFCell cell = null;
      Iterator<Employee> iter = payroll.getEmployees().iterator();
      Employee employee = null;
      while (iter.hasNext())
      {
         employee = iter.next();
         Payroll currentPayroll = employee.getCurrentPayroll();
         int cellNum = 0;

         double hours = 
            currentPayroll.getTotalHoursWeekOne() 
            + currentPayroll.getTotalHoursWeekTwo();
         if(hours > 0)
         {
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue(payroll.getShop().getNewPayrollCompanyNo());
            
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue(payroll.getShop().getNewPayrollDepartmentNo());
            
            String payrollId = employee.getPayrollId();
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue(payrollId);
            
            //regular pay code
        //    cell = getCell(sheet, rowNum, cellNum++);
        //    cell.setCellValue(REGULAR_PAY_CODE);

            
            double regHours = 
               currentPayroll.getTotalRegHours();            
            
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue(regHours);
            
        //    cell = getCell(sheet, rowNum, cellNum++);
        //    cell.setCellValue(GEN_FLAG);
            
            double otHours = 
               currentPayroll.getOvertimeHoursWeekOne() 
               + currentPayroll.getOvertimeHoursWeekTwo();
            
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue(otHours);
            //if this employee has overtime pay calculate it.
      /*  if(otHours > 0)
           {
               cellNum = 0;
               rowNum++;
               cell = getCell(sheet, rowNum, cellNum++);
               cell.setCellValue(payrollId);
               
               cell = getCell(sheet, rowNum, cellNum++);
               cell.setCellValue(OT_PAY_CODE);
               
               cell = getCell(sheet, rowNum, cellNum++);
               cell.setCellValue(otHours);
               
               cell = getCell(sheet, rowNum, cellNum++);
               cell.setCellValue(GEN_FLAG);
            }*/
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue("H");
            
            double statHours = 
               currentPayroll.getWeekOneStat() + currentPayroll.getWeekTwoStat();
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue(statHours);
        /*    if(statHours > 0)
            {
               cellNum = 0;
               rowNum++;
               cell = getCell(sheet, rowNum, cellNum++);
               cell.setCellValue(payrollId);
               
               cell = getCell(sheet, rowNum, cellNum++);
               cell.setCellValue(STAT_PAY_CODE);
               
               cell = getCell(sheet, rowNum, cellNum++);
               cell.setCellValue(statHours);
               
               cell = getCell(sheet, rowNum, cellNum++);
               cell.setCellValue(GEN_FLAG);
            }
        */    
            
            rowNum++;
         }
      }
      
      return rowNum;
   }
}
