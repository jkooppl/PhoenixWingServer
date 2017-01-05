package com.pizza73.util;

import com.pizza73.model.Employee;
import com.pizza73.model.Shop;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class EmployeeUpdateFileConverterImpl implements EmployeeUpdateFileConverter
{
    private static final short BRANCH_COMPANY_NO = 0;
    private static final short PAYROLL_ID = 1;
    private static final short LAST_NAME = 2;
    private static final short FIRST_NAME = 3;
    private static final short STREET_ADDRESS = 4;
    private static final short CITY = 5;
    private static final short PROVINCE = 6;
    private static final short POSTAL_CODE = 7;
    // private static final short PHONE=9;
    private static final short BIRTH_DATE = 8;
    private static final short GENDER = 9;
    private static final short MARITAL_STATUS = 10;
    private static final short HIRE_DATE = 11;
    private static final short WAGE = 12;

    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public List<Employee> parseEmployees(File excelFile, List<Shop> shops) throws FileNotFoundException, IOException
    {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(excelFile));
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);

        List<Employee> employees = new ArrayList<Employee>();

        HSSFSheet sheet = wb.getSheetAt(0);
        int rowCount = 1;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;
        while (rowCount <= lastRow)
        {
            System.out.println("ROW_COUNT: " + rowCount);
            row = sheet.getRow(rowCount);
            Employee emp = importEmployee(row, shops);

            if (emp != null)
            {
                System.out.println("EmployeUpdateFileConverter: " + emp.getPayrollId());
                employees.add(emp);
            }
            rowCount++;
        }

        return employees;
    }

    @SuppressWarnings("deprecation")
    private Employee importEmployee(HSSFRow row, List<Shop> shops)
    {
        String newPayrollCompanyNo = "XHK";
        String newPayrollDepartmentNo = "";
        String payrollId = "";
        String fName = "";
        String lName = "";
        String streetAddress = "";
        String city = "";
        String province = "";
        String postalCode = "";
        // String phone = "";
        Date birthDate = null;
        String gender = "";
        String maritalStatus = "";
        Date hireDate = null;
        double wage = 0d;

        HSSFCell cell = null;

        cell = row.getCell(BRANCH_COMPANY_NO);
        if (cell != null)
        {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
                newPayrollDepartmentNo = cell.getStringCellValue().trim();
            else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
            {
                BigDecimal temp = new BigDecimal(cell.getNumericCellValue());
                newPayrollDepartmentNo = temp.intValue() + "";
            }
        }

        cell = row.getCell(PAYROLL_ID);
        if (cell != null)
        {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
                payrollId = cell.getStringCellValue().trim();
            else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
            {
                BigDecimal temp = new BigDecimal(cell.getNumericCellValue());
                payrollId = temp.intValue() + "";
            }
        }

        cell = row.getCell(LAST_NAME);
        if (cell != null)
        {
            lName = cell.getStringCellValue().trim();
        }

        cell = row.getCell(FIRST_NAME);
        if (cell != null)
        {
            fName = cell.getStringCellValue().trim();
        }

        cell = row.getCell(STREET_ADDRESS);
        if (cell != null)
        {
            streetAddress = cell.getStringCellValue().trim();
        }

        cell = row.getCell(CITY);
        if (cell != null)
        {
            city = cell.getStringCellValue().trim();
        }

        cell = row.getCell(PROVINCE);
        if (cell != null)
        {
            province = cell.getStringCellValue().trim();
        }

        cell = row.getCell(POSTAL_CODE);
        if (cell != null)
        {
            postalCode = cell.getStringCellValue().trim();
        }

        // cell=row.getCell(PHONE);
        /*
         * if(cell !=null){ Double temp=cell.getNumericCellValue();
         * if(temp.intValue() == 780) phone=""; else
         * phone=Integer.valueOf(temp.intValue()).toString(); }
         */
        // phone=" "
        cell = row.getCell(BIRTH_DATE);
        if (cell != null)
        {
            try
            {
                String temp = cell.getStringCellValue().trim();
                birthDate = df.parse(temp);
            }
            catch (Exception e)
            {
                System.out.println("ERROR: error in parsering date of birth for " + payrollId + " " + lName + " " + fName);
                System.exit(0);
            }
        }

        cell = row.getCell(GENDER);
        if (cell != null)
        {
            String tempGendar = cell.getStringCellValue();
            if (null == tempGendar || tempGendar.equals(""))
                gender = "M";
            else
                gender = tempGendar.toLowerCase().trim();
        }

        cell = row.getCell(MARITAL_STATUS);
        if (cell != null)
        {
            maritalStatus = cell.getStringCellValue().trim();
            if (maritalStatus == null || maritalStatus.equalsIgnoreCase("S") || maritalStatus.equalsIgnoreCase("W")
                || maritalStatus.equalsIgnoreCase("D") || maritalStatus.equals(""))
                maritalStatus = "Single";
            else if (maritalStatus.equalsIgnoreCase("M"))
                maritalStatus = "Married";
            else if (maritalStatus.equalsIgnoreCase("C"))
                maritalStatus = "Common-law";
            else
            {
                System.out.println("ERROR: error in parsering martial status for " + payrollId + " " + lName + " " + fName);
                System.exit(0);
            }
        }

        cell = row.getCell(HIRE_DATE);
        if (cell != null)
        {
            try
            {
                String temp = cell.getStringCellValue().trim();
                hireDate = df.parse(temp);
            }
            catch (Exception e)
            {
                System.out.println("ERROR: error in parsing hire date for " + payrollId + " " + lName + " " + fName);
                System.exit(0);
            }
        }

        cell = row.getCell(WAGE);
        if (cell != null)
        {
            wage = cell.getNumericCellValue();
        }

        Shop shop = shopForDepartentNumber(newPayrollDepartmentNo, shops);
        if (shop == null)
        {
            System.out.println("ERROR: no shop match for " + payrollId + " companyNo:_" + newPayrollCompanyNo
                + "_ branchNo: _" + newPayrollDepartmentNo + "_");
            return null;
        }

        Employee employee = new Employee(fName, lName, payrollId, shop.getId(), wage, false);
        employee.getAddress().setStreetAddress(streetAddress);
        employee.getAddress().setCity(city);
        employee.getAddress().setProvince(province.toUpperCase());
        employee.getAddress().setPostalCode(postalCode);
        employee.setPhone("");
        employee.setBirthDay(birthDate);
        employee.setSex(gender.charAt(0));
        employee.setMaritalStatus(maritalStatus);
        employee.setHireDate(hireDate);
        return employee;
    }

    private Shop shopForDepartentNumber(String newPayrollDepartmentNo, List<Shop> shops)
    {
        for (Shop shop : shops)
        {
            if (shop.getNewPayrollDepartmentNo().equals(newPayrollDepartmentNo))
                return shop;
        }
        return null;
    }
}
