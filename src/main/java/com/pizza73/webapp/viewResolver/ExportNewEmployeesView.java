package com.pizza73.webapp.viewResolver;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.pizza73.model.Employee;
import com.pizza73.model.Shop;

public class ExportNewEmployeesView extends AbstractExcelView
{
   @SuppressWarnings("unchecked")
   @Override
   protected void buildExcelDocument(Map map, HSSFWorkbook wb,
      HttpServletRequest request, HttpServletResponse Response) throws Exception {
      List<Employee> allNewEmployees=(List<Employee>)map.get("ALL_NEW_EMPLOYEES");
      List<Shop> activeShops=(List<Shop>)map.get("ACTIVE_SHOPS");
      HSSFSheet sheet=wb.createSheet("New employees");
      HSSFCellStyle cellStyle=wb.createCellStyle();
      cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
      int row=0;
      for(int i=0;i<allNewEmployees.size();i++){
         row=writeNewEmployee(sheet, row,allNewEmployees.get(i),activeShops,cellStyle);
      }
   }

   private int writeNewEmployee(HSSFSheet sheet, int row, Employee employee,List<Shop> activeShops, HSSFCellStyle cellStyle)
   {  Shop shop=this.getEmployeeShop(employee, activeShops);
      super.getCell(sheet, row, 0).setCellValue(new HSSFRichTextString(shop.getCompanyNumber()));
      super.getCell(sheet, row,1).setCellValue(shop.getBranch());
      super.getCell(sheet, row, 2).setCellValue(HSSFCell.CELL_TYPE_BLANK);
      super.getCell(sheet,row,3).setCellType(HSSFCell.CELL_TYPE_BLANK);
      super.getCell(sheet, row, 4).setCellValue(new HSSFRichTextString(employee.getLastName()));
      super.getCell(sheet, row, 5).setCellValue(new HSSFRichTextString(employee.getName()));
      super.getCell(sheet, row, 6).setCellValue(new HSSFRichTextString(employee.getMiddleInitial()));
      super.getCell(sheet, row, 7).setCellValue(new HSSFRichTextString(StringUtils.upperCase(String.valueOf(employee.getSex()))));
      super.getCell(sheet, row, 8).setCellValue(new HSSFRichTextString(this.getMaritalStatus(employee.getMaritalStatus())));
      super.getCell(sheet, row, 9).setCellValue(employee.getBirthDay());
      super.getCell(sheet, row, 9).setCellStyle(cellStyle);
      super.getCell(sheet, row, 10).setCellValue(employee.getHireDate());
      super.getCell(sheet, row, 10).setCellStyle(cellStyle);
      super.getCell(sheet, row, 11).setCellValue(new HSSFRichTextString((employee.isSalariedEmployee()?"S":"H")));
      if(employee.isSalariedEmployee())
         super.getCell(sheet,row,12).setCellType(HSSFCell.CELL_TYPE_BLANK);
      else
      super.getCell(sheet, row, 12).setCellValue(Integer.valueOf(80));
      super.getCell(sheet, row, 13).setCellValue(employee.getPrimaryWage());
      super.getCell(sheet, row, 14).setCellValue(Math.round(employee.getPrimaryWage()*150)/100.0);
      if(employee.isSalariedEmployee())
         super.getCell(sheet, row, 15).setCellValue(employee.getPrimaryWage());
      else
         super.getCell(sheet,row,15).setCellType(HSSFCell.CELL_TYPE_BLANK);
      super.getCell(sheet, row, 16).setCellValue(new HSSFRichTextString(this.getProvinceCode(employee.getAddress().getProvince())));
      super.getCell(sheet, row, 17).setCellValue(new HSSFRichTextString(this.getProvinceCode(employee.getAddress().getProvince())));
      super.getCell(sheet, row, 18).setCellValue(new HSSFRichTextString("C"));
      super.getCell(sheet, row, 19).setCellValue(new HSSFRichTextString("A"));
      super.getCell(sheet, row, 20).setCellValue(new HSSFRichTextString("N"));
      super.getCell(sheet, row, 21).setCellValue(new HSSFRichTextString(employee.getSin()));
      super.getCell(sheet, row,22).setCellValue(new HSSFRichTextString(employee.getAddress().getStreetAddress()));
      super.getCell(sheet, row, 23).setCellValue(new HSSFRichTextString(employee.getAddress().getCity()));
      super.getCell(sheet, row, 24).setCellValue(new HSSFRichTextString(employee.getAddress().getProvince()));
      super.getCell(sheet, row, 25).setCellValue(new HSSFRichTextString(employee.getAddress().getPostalCode()));
      super.getCell(sheet, row, 26).setCellValue(new HSSFRichTextString(employee.getPhone()));
      super.getCell(sheet, row, 27).setCellValue(shop.getBranch());
      return (row+1);
   }
   
   private String getProvinceCode(String province){
      if(province.equalsIgnoreCase("BC"))
         return "03";
      else if(province.equalsIgnoreCase("AB"))
         return "04";
      else
         return "05";      
   }
   
   private String getMaritalStatus(String maritalStatus){
      if(maritalStatus.equalsIgnoreCase("Single"))
         return "S";
      else if(maritalStatus.equalsIgnoreCase("Married"))
         return "M";
      else
         return "C";
   }
   
   private Shop getEmployeeShop(Employee employee,List<Shop> activeShops){
      for(int i=0;i<activeShops.size();i++)
         if(activeShops.get(i).getId().compareTo(employee.getShopId())== 0)
            return activeShops.get(i);
      return null;
   }

}
