package com.pizza73.webapp.viewResolver;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.pizza73.model.Shop;
import com.pizza73.model.ShopPayroll;
import com.pizza73.model.comparator.ShopEEComparator;

/**
 * ExportShopTotalsView
 * 
 * @author chris 7-Sept-06
 * 
 * @Copyright Flying Pizza 73
 */
public class ExportShopTotalsView extends AbstractExcelView
{  
   public ExportShopTotalsView()
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
         throws Exception
   {
      HSSFSheet sheet = null;
      List<ShopPayroll> payrolls = (List<ShopPayroll>) map.get("PAYROLL");
      Integer currentPayrollYear = (Integer)map.get("PAYROLL_YEAR"); 
      Integer currentPayrollPeriod = (Integer)map.get("PAYROLL_PERIOD");
            
      sheet = 
         wb.createSheet(
            "shopHoursTotal-" + currentPayrollPeriod + "-" + currentPayrollYear);
      buildDataList(sheet, payrolls);
   }

   /**
    * @param sheet
    * @param personList
    * @param report
    */
   @SuppressWarnings("unchecked")
   private void buildDataList(HSSFSheet sheet, List<ShopPayroll> payrolls)
   {  
      HSSFCell cell = null;
      int rowNum = 0;
      
      //Header row.
      cell = getCell(sheet, rowNum, 0);
      cell.setCellValue("Co Code");
      cell = getCell(sheet, rowNum, 1);
      cell.setCellValue("Batch ID");
      cell = getCell(sheet, rowNum, 2);
      cell.setCellValue("Reg hours");
      cell = getCell(sheet, rowNum, 3);
      cell.setCellValue("O/T Hours");
      cell = getCell(sheet, rowNum, 4);
      cell.setCellValue("Hours 3 Code");
      cell = getCell(sheet, rowNum, 5);
      cell.setCellValue("Hours 3 Amount");
      
      rowNum++;
      
      Double grandTotalRegHours = 0.0d;
      Double grandTotalOTHours = 0.0d;
      Double grandTotalStatHours = 0.0d;
      
      Collections.sort(payrolls, new ShopEEComparator());
      Iterator<ShopPayroll> iter = payrolls.iterator();
      ShopPayroll payroll = null;
      while(iter.hasNext())
      {
         int cellNum = 0;
         payroll = iter.next();
         if(payroll != null)
         {  
            Shop shop = payroll.getShop();
            
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue(shop.getNewPayrollCompanyNo());
            
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue(shop.getNewPayrollDepartmentNo());    
            
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue(payroll.getTotalRegHours());
            
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue(payroll.getTotalOTHours());
            
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue("H");
            
            cell = getCell(sheet, rowNum, cellNum++);
            cell.setCellValue(payroll.getTotalStatHours());
          
            grandTotalRegHours += payroll.getTotalRegHours();
            grandTotalOTHours += payroll.getTotalOTHours();
            grandTotalStatHours +=payroll.getTotalStatHours();
            
            
            rowNum++;
         }
      }
      rowNum++;
      cell = getCell(sheet, rowNum, 2);
      cell.setCellValue("Total Reg. Hours:");
      cell = getCell(sheet, rowNum++, 3);
      cell.setCellValue(grandTotalRegHours);
      cell = getCell(sheet, rowNum, 2);
      cell.setCellValue("Total OT Hours:");
      cell = getCell(sheet, rowNum++, 3);
      cell.setCellValue(grandTotalOTHours);
      cell = getCell(sheet, rowNum, 2);
      cell.setCellValue("Total Stat Hours:");
      cell = getCell(sheet, rowNum++, 3);
      cell.setCellValue(grandTotalStatHours);
   }
}

