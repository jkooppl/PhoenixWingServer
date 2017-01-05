package com.pizza73.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pizza73.model.Employee;
import com.pizza73.service.EmployeeManager;
import com.pizza73.service.LookupManager;

/**
 * ExcelOnlineCustomerConversion.java TODO comment me
 * 
 * @author chris 8-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public class ADPConversion {
    protected ClassPathXmlApplicationContext ctx;
    private EmployeeManager employeeMgr;
    private LookupManager lookupMgr;

    private static final short OLD_ADP_ID = 2;
    private static final short NEW_ADP_ID = 3;

    public static void main(String[] args) throws Exception
    {
        ADPConversion ec = new ADPConversion();
        ec.setUp();
        File file = new File(
        // "C:\\Users\\Junjie\\Documents\\EmployeeNumberChange-SHSJSK.xls"
                args[0]);
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        ec.importData(wb);
    }

    public void setUp()
    {
        String[] paths = { "util-applicationContext-resources.xml",
                "applicationContext.xml" };
        ctx = new ClassPathXmlApplicationContext(paths);
        employeeMgr = (EmployeeManager) ctx.getBean("employeeManager");
        lookupMgr = (LookupManager) ctx.getBean("lookupManager");
        System.out.println("INFO: starting up");
    }

    public void importData(HSSFWorkbook wb)
    {
        HSSFSheet sheet = wb.getSheet("Sheet1");
        int rowCount = 1;
        int lastRow = sheet.getLastRowNum();
        HSSFRow row = null;
        Employee employee = null;
        String oldADPID = null;
        String newADPID = null;
        while (rowCount < lastRow)
        {
            employee = null;
            row = sheet.getRow(rowCount);
            if (row.getCell(NEW_ADP_ID).getCellType() == 1)
            {
                oldADPID = StringUtils
                        .strip(row.getCell(OLD_ADP_ID).getStringCellValue());
                newADPID = StringUtils
                        .strip(row.getCell(NEW_ADP_ID).getStringCellValue());
            }
            else
            {
                oldADPID = StringUtils
                        .strip(row.getCell(OLD_ADP_ID).getStringCellValue());
                newADPID = String.valueOf(Math.round(row.getCell(NEW_ADP_ID)
                        .getNumericCellValue()));
            }
            try
            {
                employee = employeeMgr.employeeForPayrollId(oldADPID);
                System.out.println("update iq2_employee set apd_id='" + newADPID
                        + "' where employee_id=" + employee.getId() + ";");
                System.out.println("update iq2_payroll set apd_id='" + newADPID
                        + "' where employee_id=" + employee.getId() + ";");
            }
            catch (UsernameNotFoundException e)
            {
                System.err.println("warning:" + oldADPID);
            }
            catch (IncorrectResultSizeDataAccessException e2)
            {
                System.err.println("duplicate:" + oldADPID);
            }

            rowCount++;
        }

    }

}