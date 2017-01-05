package com.pizza73.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pizza73.model.CompleteCart;
import com.pizza73.model.Employee;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.Shop;
import com.pizza73.service.AsyncProcessorService;
import com.pizza73.service.EmployeeManager;
import com.pizza73.service.LookupManager;
import com.pizza73.service.MailEngine;
import com.pizza73.service.OrderManager;
import com.pizza73.util.EmployeeUpdateFileConverter;

@Service
public class AsyncProcessorServiceImpl implements AsyncProcessorService
{
    private static final Logger log = Logger.getLogger(AsyncProcessorServiceImpl.class);
    private static final String[] EMPLOYEE_EMAIL_ADDRESS_LIST = new String[] { "dshubert@pizzapizza.ca",
            "sestrada@pizzapizza.ca" };
    private static final String EMAIL_POSTFIX = "@pizzapizza.ca";
    private static final String EMAIL_INFO_VARIABLE = "ee_updload_email";

    @Autowired
    private EmployeeManager empMgr;

    @Autowired
    private LookupManager lookupMgr;

    @Autowired
    private OrderManager orderMgr;

    @Autowired
    private EmployeeUpdateFileConverter converter;

    // EMAIL
    @Autowired
    private MailEngine mailEngine;
    @Autowired
    private SimpleMailMessage mailMessage;

    private final String templateName = "employeeUpdateResults.vm";

    public AsyncProcessorServiceImpl()
    {
    }

    @Override
    // @Transactional( propagation = Propagation.REQUIRES_NEW)
    @Async
    public void updateEmployees(File excelFile)
    {
        log.warn("Employee update started");
        boolean persisted = true;
        String errorMessage = null;
        List<String> results = new ArrayList<String>();
        try
        {
            List<Shop> shops = lookupMgr.activeShops();
            List<Employee> employees = converter.parseEmployees(excelFile, shops);
            log.warn("Parsed " + employees.size() + " employees");
            results = persistEmployees(employees, shops);
        }
        catch (FileNotFoundException e)
        {
            persisted = false;
            errorMessage = "Unable to find file: " + e.getMessage();
            e.printStackTrace();
        }
        catch (IOException e)
        {
            errorMessage = "Error reading update file: " + e.getMessage();
            persisted = false;
            e.printStackTrace();
        }

        if (!persisted)
        {
            results.add(errorMessage);
        }

        emailResults(results);

        log.warn("Employee update complete");
    }

    private List<String> persistEmployees(List<Employee> employees, List<Shop> shops)
    {
        List<String> results = new ArrayList<String>();
        List<String> activeADPid = new ArrayList<String>();
        for (Employee employee : employees)
        {
            String pin = this.empMgr.generateNewPin();
            employee.setEmail(pin);
            employee.setPassword(pin);
            activeADPid.add(employee.getPayrollId());
            results.addAll(importData(employee, activeADPid));
        }

        results.addAll(checkForTerminated(shops, activeADPid));
        return results;
    }

    private List<String> importData(Employee employee, List<String> activeADPid)
    {
        List<String> results = new ArrayList<String>();

        try
        {
            Employee existingEmployee = this.empMgr.employeeForPayrollId(employee.getPayrollId());
            results.addAll(updateExistingEmployee(employee, existingEmployee));
        }
        catch (UsernameNotFoundException e)
        {
            List<Employee> employeesWithNames = this.empMgr.employeeForFirstAndLastName(employee.getName(),
                employee.getLastName());
            if (null != employeesWithNames && employeesWithNames.size() > 1)
            {
                Employee emp = employeesWithNames.get(0);
                String result = "There are multiple records for employee with name: " + emp.getName() + " "
                    + emp.getLastName() + " nothing saved.  CHECK IT.";
                log.warn(result);
                results.add(result);
            }
            // else if(employeesWithNames.size() == 1)
            // {
            // Employee emp = employeesWithNames.get(0);
            // String result =
            // "Employee matched by name not ADP_ID: " + emp.getName() + " "
            // + emp.getLastName() + " for shop:" + emp.getShopId();
            // results.add(result);
            // log.warn(result);
            // results.addAll(updateExistingEmployee(employee, emp));
            // }
            else
            {
                employee.setNewEmployee(false);
                employee.setPhone("");
                if (employee.getPrimaryWage() > Employee.WAGE_THRESHOLD)
                {
                    employee.setSalariedEmployee(true);
                    String result = "WARN: new employee " + employee.getPayrollId() + " will be a salaried employee.";
                    results.add(result);
                }
                employee = (Employee) this.empMgr.save(employee);
                String result = "INFO:NEW employee: " + employee.getPayrollId();
                results.add(result);
                log.warn(result);
            }
        }
        catch (NonUniqueResultException e)
        {
            String result = "There are multiple records for employee with adp_id: " + employee.getPayrollId()
                + " nothing saved.  CHECK IT.";
            log.warn(result);
            results.add(result);
        }

        return results;
    }

    private List<String> updateExistingEmployee(Employee employee, Employee existingEmployee)
    {
        List<String> results = new ArrayList<String>();
        if (existingEmployee.isNewEmployee())
        {
            existingEmployee.setNewEmployee(false);
            existingEmployee.setPayrollId(employee.getPayrollId());
            String result = "WARN: shop: " + existingEmployee.getShopId() //
                + " existing employee encountered for employee " //
                + existingEmployee.getPayrollId();
            results.add(result);
        }
        List<String> employeeResults = existingEmployee.update(employee);
        if (null != employeeResults && !employeeResults.isEmpty())
        {
            results.addAll(employeeResults);
            this.empMgr.save(existingEmployee);
        }

        return results;
    }

    private List<String> checkForTerminated(List<Shop> activeShops, List<String> activeADPid)
    {
        List<String> results = new ArrayList<String>();
        String tempADPid = null;
        for (Shop activeShop : activeShops)
        {
            List<Employee> activeEmployees = this.empMgr.activeEmployeesForShop(activeShop.getId(), false);
            for (Employee activeEmployee : activeEmployees)
            {
                tempADPid = activeEmployee.getPayrollId();
                if (StringUtils.isBlank(tempADPid))
                    continue;
                if (!activeADPid.contains(tempADPid.trim()) && activeEmployee.isEnabled())
                {
                    String result = "WARN: employee " + tempADPid.trim() + " has been terminated!";
                    System.out.println(result);
                    activeEmployee.setEnabled(false);
                    this.empMgr.save(activeEmployee);
                    results.add(result);
                }
            }
        }

        return results;
    }

    private void emailResults(List<String> results)
    {
        String[] emails = emailList();
        mailMessage.setTo(emails);
        mailMessage.setSubject("Employee Update Results");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("RESULTS", results);

        mailEngine.sendOrderMessage(mailMessage, templateName, model);
        String emailString = "";
        for (int i = 0; i < emails.length; i++)
        {
            if (i > 0)
            {
                emailString += ",";
            }
            emailString += emails[i];
        }
        log.warn("emailed " + emailString + " employee update results.");
    }

    private String[] emailList()
    {
        String dbEmails = lookupMgr.infoValueForVariable(EMAIL_INFO_VARIABLE);
        String emails = "";
        if (null != dbEmails)
        {
            String[] emailList = dbEmails.split(",");
            for (int i = 0; i < emailList.length; i++)
            {
                if (i > 0)
                {
                    emails += ",";
                }
                emails += emailList[i] + EMAIL_POSTFIX;
            }

        }

        return emails.split(",");
    }

    @Async
    public void copyOrderToDisk(String fileName, String textOrder)
    {
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(textOrder);
            out.close();
        }
        catch (IOException e)
        {
            log.error("IO EXCEPTION: " + e.getMessage());
        }
    }

    /**
     * @param cart
     * @param id
     */
    @Async
    public void sendEmailConfirmation(CompleteCart completeCart, String email)
    {
        try
        {
            orderMgr.emailOrderConfirmation(completeCart, email);
        }
        catch (Exception e)
        {
            OnlineCustomer oc = completeCart.getCustomer();
            log.error("Unable to send order receipt to: " + oc.getEmail());
            log.error("error while sending order confirmation email:" + e);
        }
    }

}
