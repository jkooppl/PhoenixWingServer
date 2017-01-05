package com.pizza73.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pizza73.model.Employee;
import com.pizza73.model.Shop;
import com.pizza73.service.EmployeeManager;
import com.pizza73.service.LookupManager;

/**
 * ExcelOnlineCustomerConversion.java TODO comment me
 * 
 * @author chris 8-Mar-07
 * @Copyright Flying Pizza 73
 */
public class EmployeeConversion {
    protected ClassPathXmlApplicationContext ctx;
    private EmployeeManager employeeMgr;
    private LookupManager lookupMgr;

    // private static final short SHOP_COMPANY_NO = 0;
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
    private static final double WAGE_THRESHOLD = 100.0;

    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public static void main(String[] args) throws Exception
    {
        EmployeeConversion ec = new EmployeeConversion();
        ec.setUp();
        File file = new File(args[0]);
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        ec.importData(wb);
    }

    @SuppressWarnings("unchecked")
    public void setUp()
    {
        String[] paths = { "util-applicationContext-resources.xml",
                "applicationContext.xml" };
        ctx = new ClassPathXmlApplicationContext(paths);
        employeeMgr = (EmployeeManager) ctx.getBean("employeeManager");
        lookupMgr = (LookupManager) ctx.getBean("lookupManager");
        System.out.println("INFO: starting up");
    }

    public List<Object> importData(HSSFWorkbook wb)
    {
        List<Object> results = new ArrayList<Object>();
        List<String> activeADPid = new ArrayList<String>();

        HSSFSheet sheet = wb.getSheetAt(0);
        int rowCount = 1;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;
        Employee existingEmployee = null;
        while (rowCount <= lastRow)
        {
            row = sheet.getRow(rowCount);
            Employee emp = importEmployee(row, rowCount);

            if (emp != null)
            {
                try
                {
                    try
                    {
                        existingEmployee = null;
                        existingEmployee = employeeMgr.employeeForPayrollId(emp
                                .getPayrollId());
                        this.updateEmployee(existingEmployee, emp);
                    }
                    catch (UsernameNotFoundException e)
                    {
                        emp.setNewEmployee(false);
                        emp.setPhone("");
                        if (emp.getPrimaryWage() > WAGE_THRESHOLD)
                        {
                            emp.setSalariedEmployee(true);
                            System.out.println("WARN: new employee " + emp.getPayrollId()
                                    + " will be a salaried employee.");
                        }
                        emp = (Employee) employeeMgr.save(emp);
                        // System.out.println("Employee saved: " + emp.getId());
                        System.out.println("NEW : new employee: " + emp.getPayrollId());
                    }

                }
                catch (DataAccessException e)
                {
                    // notSaved.add(emp.getId());
                }
            }
            rowCount++;
            activeADPid.add(emp.getPayrollId());
        }
        List<Employee> activeEmployees = null;
        List<Shop> activeShops = this.lookupMgr.activeShops();
        int j = 0;
        String tempADPid = null;
        for (int i = 0; i < activeShops.size(); i++)
        {
            activeEmployees = this.employeeMgr.activeEmployeesForShop(activeShops.get(i)
                    .getId(), false);
            for (j = 0; j < activeEmployees.size(); j++)
            {
                tempADPid = activeEmployees.get(j).getPayrollId();
                if (StringUtils.isBlank(tempADPid))
                    continue;
                if (!activeADPid.contains(tempADPid.trim()))
                {
                    System.out.println("WARN: employee " + tempADPid.trim()
                            + " has been terminated!");
                    activeEmployees.get(j).setEnabled(false);
                    this.employeeMgr.save(activeEmployees.get(j));
                }
            }
        }
        System.out.println("INFO: finish");
        return results;
    }

    private void updateEmployee(Employee existingEmployee, Employee excelEmployee)
    {
        if (!existingEmployee.getShopId().equals(excelEmployee.getShopId()))
        {
            System.out.println("WARN: shop changed for employee "
                    + excelEmployee.getPayrollId() + " . Double Check it!");
            return;
        }
        if (!existingEmployee.getName().equals(excelEmployee.getName()))
        {
            System.out.println("INFO: first name changed for employee "
                    + excelEmployee.getPayrollId() + " from "
                    + existingEmployee.getName() + " to " + excelEmployee.getName());
            existingEmployee.setName(excelEmployee.getName());
        }
        if (!existingEmployee.getLastName().equals(excelEmployee.getLastName()))
        {
            System.out.println("INFO: last name changed for employee "
                    + excelEmployee.getPayrollId() + " from "
                    + existingEmployee.getLastName() + " to "
                    + excelEmployee.getLastName());
            existingEmployee.setLastName(excelEmployee.getLastName());
        }
        if (!existingEmployee.getAddress().getStreetAddress().equals(
                excelEmployee.getAddress().getStreetAddress()))
        {
            System.out.println("INFO: streeAdress changed for employee "
                    + excelEmployee.getPayrollId() + " from "
                    + existingEmployee.getAddress().getStreetAddress() + " to "
                    + excelEmployee.getAddress().getStreetAddress());
            existingEmployee.getAddress().setStreetAddress(
                    excelEmployee.getAddress().getStreetAddress());
        }
        if (!StringUtils.equals(existingEmployee.getAddress().getCity(), excelEmployee
                .getAddress().getCity()))
        {
            System.out.println("INFO: city changed for employee "
                    + excelEmployee.getPayrollId() + " from "
                    + existingEmployee.getAddress().getCity() + " to "
                    + excelEmployee.getAddress().getCity());
            existingEmployee.getAddress().setCity(excelEmployee.getAddress().getCity());
        }
        if (!StringUtils.equals(existingEmployee.getAddress().getProvince(),
                excelEmployee.getAddress().getProvince()))
        {
            System.out.println("INFO: province changed for employee "
                    + excelEmployee.getPayrollId() + " from "
                    + existingEmployee.getAddress().getProvince() + " to "
                    + excelEmployee.getAddress().getProvince());
            existingEmployee.getAddress().setProvince(
                    excelEmployee.getAddress().getProvince());
        }
        if (!StringUtils.equals(existingEmployee.getAddress().getPostalCode(),
                excelEmployee.getAddress().getPostalCode()))
        {
            System.out.println("INFO: postal code changed for employee "
                    + excelEmployee.getPayrollId() + " from "
                    + existingEmployee.getAddress().getPostalCode() + " to "
                    + excelEmployee.getAddress().getPostalCode());
            existingEmployee.getAddress().setPostalCode(
                    excelEmployee.getAddress().getPostalCode());
        }
        /*
         * if(!StringUtils.equals(existingEmployee.getPhone(),excelEmployee.getPhone
         * ())){
         * System.out.println("INFO: phone changed for employee "+excelEmployee
         * .getPayrollId
         * ()+" from "+existingEmployee.getPhone()+" to "+excelEmployee
         * .getPhone()); existingEmployee.setPhone(excelEmployee.getPhone()); }
         */
        if ((existingEmployee.getBirthDay() == null && excelEmployee.getBirthDay() != null)
                || (existingEmployee.getBirthDay() != null && excelEmployee.getBirthDay() == null)
                || (existingEmployee.getBirthDay() != null
                        && excelEmployee.getBirthDay() != null && !DateUtils.isSameDay(
                        existingEmployee.getBirthDay(), excelEmployee.getBirthDay())))
        {
            System.out.println("INFO: date of birth changed for employee "
                    + excelEmployee.getPayrollId() + " from "
                    + existingEmployee.getBirthDay() + " to "
                    + excelEmployee.getBirthDay());
            existingEmployee.setBirthDay(excelEmployee.getBirthDay());
        }
        if (existingEmployee.getSex() != excelEmployee.getSex())
        {
            System.out.println("INFO: gendar changed for employee "
                    + excelEmployee.getPayrollId() + " from " + existingEmployee.getSex()
                    + " to " + excelEmployee.getSex());
            existingEmployee.setSex(excelEmployee.getSex());
        }
        if (!StringUtils.equals(existingEmployee.getMaritalStatus(), excelEmployee
                .getMaritalStatus()))
        {
            System.out.println("INFO: marital status changed for employee "
                    + excelEmployee.getPayrollId() + " from "
                    + existingEmployee.getMaritalStatus() + " to "
                    + excelEmployee.getMaritalStatus());
            existingEmployee.setMaritalStatus(excelEmployee.getMaritalStatus());
        }
        if ((existingEmployee.getHireDate() == null && excelEmployee.getHireDate() != null)
                || (existingEmployee.getHireDate() != null && excelEmployee.getHireDate() == null)
                || (existingEmployee.getHireDate() != null
                        && excelEmployee.getHireDate() != null && !DateUtils.isSameDay(
                        existingEmployee.getHireDate(), excelEmployee.getHireDate())))
        {
            System.out.println("INFO: HireDate changed for employee "
                    + excelEmployee.getPayrollId() + " from "
                    + existingEmployee.getHireDate() + " to "
                    + excelEmployee.getHireDate());
            existingEmployee.setHireDate(excelEmployee.getHireDate());
        }
        if (existingEmployee.getPrimaryWage().compareTo(excelEmployee.getPrimaryWage()) != 0)
        {
            System.out.println("INFO: wage changed for employee "
                    + excelEmployee.getPayrollId() + " from "
                    + existingEmployee.getPrimaryWage() + " to "
                    + excelEmployee.getPrimaryWage());
            if (existingEmployee.getPrimaryWage().compareTo(
                    excelEmployee.getPrimaryWage()) > 0)
                System.out.println("WARN: wage goes down for employee "
                        + excelEmployee.getPayrollId() + " from "
                        + existingEmployee.getPrimaryWage() + " to "
                        + excelEmployee.getPrimaryWage());
            existingEmployee.setPrimaryWage(excelEmployee.getPrimaryWage());
        }
        if (excelEmployee.getPrimaryWage() > WAGE_THRESHOLD)
        {
            existingEmployee.setSalariedEmployee(true);
            System.out.println("WARN: employee " + excelEmployee.getPayrollId()
                    + " becomes a SALARY employee");
        }
        this.employeeMgr.save(existingEmployee);
    }

    private Employee importEmployee(HSSFRow row, int rowCount)
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
        String phone = "";
        Date birthDate = null;
        String gender = "";
        String maritalStatus = "";
        Date hireDate = null;
        double wage = 0d;

        HSSFCell cell = null;

        // cell = row.getCell(SHOP_COMPANY_NO);
        // if (cell != null)
        // {
        // newPayrollCompanyNo= cell.getStringCellValue().trim();
        // }

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
                System.out.println("ERROR: error in parsering date of birth for "
                        + payrollId + " " + lName + " " + fName);
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
            if (maritalStatus == null || maritalStatus.equalsIgnoreCase("S")
                    || maritalStatus.equalsIgnoreCase("W")
                    || maritalStatus.equalsIgnoreCase("D") || maritalStatus.equals(""))
                maritalStatus = "Single";
            else if (maritalStatus.equalsIgnoreCase("M"))
                maritalStatus = "Married";
            else if (maritalStatus.equalsIgnoreCase("C"))
                maritalStatus = "Common-law";
            else
            {
                System.out.println("ERROR: error in parsering martial status for "
                        + payrollId + " " + lName + " " + fName);
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
                System.out.println("ERROR: error in parsering hire date for " + payrollId
                        + " " + lName + " " + fName);
                System.exit(0);
            }
        }

        cell = row.getCell(WAGE);
        if (cell != null)
        {
            wage = cell.getNumericCellValue();
        }

        Shop shop = this.lookupMgr.getShopNewPayroll(newPayrollCompanyNo,
                newPayrollDepartmentNo);
        if (shop == null)
        {
            System.out.println("ERROR: no shop match for " + payrollId + " companyNo:_"
                    + newPayrollCompanyNo + "_ branchNo: _" + newPayrollDepartmentNo
                    + "_");
            return null;
        }
        // System.out.println("COMPANY NO: " + companyNo + "  BRANCH NO: " +
        // branchNo);
        // return new Employee(fName, lName, payrollId, USERNAME++,
        // shop.getId(),wage, salary);
        // return new Employee(fName, lName, payrollId,
        // Integer.valueOf(this.employeeMgr.generateNewPin()),
        // shop.getId(),wage,
        // salary);
        Employee employee = new Employee(fName, lName, payrollId, Integer
                .valueOf(this.employeeMgr.generateNewPin()), shop.getId(), wage, false);
        employee.getAddress().setStreetAddress(streetAddress);
        employee.getAddress().setCity(city);
        employee.getAddress().setProvince(province.toUpperCase());
        employee.getAddress().setPostalCode(postalCode);
        // employee.setPhone(phone);
        employee.setBirthDay(birthDate);
        employee.setSex(gender.charAt(0));
        employee.setMaritalStatus(maritalStatus);
        employee.setHireDate(hireDate);
        return employee;
    }
}
