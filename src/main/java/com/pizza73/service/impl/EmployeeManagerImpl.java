package com.pizza73.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pizza73.dao.EmployeeDao;
import com.pizza73.model.Driver;
import com.pizza73.model.Employee;
import com.pizza73.model.Shop;
import com.pizza73.service.EmployeeManager;
import com.pizza73.service.LookupManager;

/**
 * UserManagerImpl.java TODO comment me
 * 
 * @author chris 9-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Service("employeeManager")
public class EmployeeManagerImpl extends UniversalManagerImpl implements EmployeeManager
{
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private LookupManager lookupMgr;

    public EmployeeManagerImpl()
    {
    }

    public Employee employeeForId(String id)
    {
        return this.employeeDao.get(Integer.valueOf(id));
    }

    public Employee employeeForUsername(String username)
    {
        return this.employeeDao.employeeForUsername(username);
    }

    public List<Employee> employeesForShop(Integer shopId)
    {
        return this.employeeDao.employeesForShop(shopId);
    }

    public List<Employee> hourlyEmployeesForShop(Integer shopId)
    {
        return this.employeeDao.employeesForShop(shopId, Boolean.FALSE);
    }

    public List<Employee> salariedEmployeesForShop(Integer shopId)
    {
        return this.employeeDao.employeesForShop(shopId, Boolean.FALSE);
    }

    public List<Driver> driversForShop(Integer shopId)
    {
        return this.employeeDao.driversForShop(shopId);
    }

    public List<Driver> driversForShop(Shop shop)
    {
        return this.employeeDao.driversForShop(shop);
    }

    public List<Employee> activeEmployeesForShop(Integer shopId, Boolean salaried)
    {
        return this.employeeDao.activeEmployeesForShop(shopId, salaried);
    }

    public List<Employee> hourlyActiveEmployeesForShop(Integer shopId)
    {
        return this.activeEmployeesForShop(shopId, Boolean.FALSE);
    }

    public List<Employee> salariedActiveEmployeesForShop(Integer shopId)
    {
        return this.activeEmployeesForShop(shopId, Boolean.TRUE);
    }

    public Employee employeeForPayrollId(String payrollId)
    {
        return this.employeeDao.employeeForPayrollId(payrollId);
    }

    public void toggleEnabled(Employee emp)
    {
        emp.setEnabled(!emp.isEnabled());
        this.employeeDao.save(emp);
    }

    public Driver driverForId(Integer id)
    {
        return this.employeeDao.driverForId(id);
    }

    public Driver driverForId(String id)
    {
        return this.driverForId(Integer.valueOf(id));
    }

    public void saveDriver(Driver driver)
    {
        this.employeeDao.saveDriver(driver);
    }

    public String generateNewPin()
    {
        String returnStr = null;
        while (true)
        {
            returnStr = RandomStringUtils.random(4, false, true);
            if (returnStr.charAt(0) == '0')
                continue;
            try
            {
                employeeDao.employeeForUsername(returnStr);
            }
            catch (UsernameNotFoundException e)
            {
                return returnStr;
            }
        }
    }

    public List<Employee> getAllNewEmployees()
    {
        List<Shop> activeShops = this.lookupMgr.activeShops();
        List<Employee> allNewEmployees = new ArrayList<Employee>();
        for (int i = 0; i < activeShops.size(); i++)
        {
            allNewEmployees.addAll(this.newEmployeesForShop(activeShops.get(i).getId()));
        }
        return allNewEmployees;
    }

    public List<Employee> newEmployeesForShop(Integer shopId)
    {
        List<Employee> returnList = this.employeeDao.newEmployeesForShop(shopId);
        return returnList;
    }

    public List<Employee> hourlyApprovedEmployeesForShop(Integer shopId)
    {
        List<Employee> hourlyApprovedEmployees = this.hourlyActiveEmployeesForShop(shopId);
        Employee temp = null;
        for (int i = (hourlyApprovedEmployees.size() - 1); i >= 0; i--)
        {
            temp = hourlyApprovedEmployees.get(i);
            if (StringUtils.isBlank(temp.getPayrollId()) || temp.isNewEmployee())
                hourlyApprovedEmployees.remove(i);
        }
        return hourlyApprovedEmployees;
    }

    @Override
    public List<Employee> employeeForFirstAndLastName(String first, String second)
    {
        return this.employeeDao.employeeForFirstAndLastName(first, second);
    }
}
