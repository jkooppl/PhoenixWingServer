package com.pizza73.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.Driver;
import com.pizza73.model.Employee;
import com.pizza73.model.Shop;

/**
 * UserManager.java TODO comment me
 * 
 * @author chris 9-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface EmployeeManager extends UniversalManager
{

    public Employee employeeForId(String id);

    public Employee employeeForUsername(String username);

    public Employee employeeForPayrollId(String payrollId);

    public List<Employee> employeeForFirstAndLastName(String first, String second);

    public List<Employee> employeesForShop(Integer shopId);

    public List<Employee> hourlyEmployeesForShop(Integer shopId);

    public List<Employee> salariedEmployeesForShop(Integer shopId);

    public List<Employee> activeEmployeesForShop(Integer shopId, Boolean salaried);

    public List<Employee> hourlyActiveEmployeesForShop(Integer shopId);

    public List<Employee> hourlyApprovedEmployeesForShop(Integer shopId);

    public List<Employee> salariedActiveEmployeesForShop(Integer shopId);

    public List<Driver> driversForShop(Integer shopId);

    public List<Driver> driversForShop(Shop shop);

    @Transactional(readOnly = false)
    public void toggleEnabled(Employee emp);

    public Driver driverForId(Integer id);

    public Driver driverForId(String id);

    @Transactional(readOnly = false)
    public void saveDriver(Driver driver);

    public String generateNewPin();

    public List<Employee> newEmployeesForShop(Integer shopId);

    public List<Employee> getAllNewEmployees();
}
