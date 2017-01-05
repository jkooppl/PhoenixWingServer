package com.pizza73.dao;

import java.util.List;

import com.pizza73.model.Driver;
import com.pizza73.model.Employee;
import com.pizza73.model.Shop;

/**
 * UserDao.java TODO comment me
 * 
 * @author chris 9-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
public interface EmployeeDao extends UniversalDao
{

    public Employee employeeForId(Integer id);

    public Employee employeeForUsername(String username);

    public Employee employeeForPayrollId(String payrollId);

    public List<Employee> employeesForShop(Integer shopId);

    public List<Employee> employeesForShop(Integer shopId, Boolean salaried);

    public List<Driver> driversForShop(Integer shopId);

    public List<Driver> driversForShop(Shop shop);

    public List<Employee> activeEmployeesForShop(Integer shopId);

    public List<Employee> activeEmployeesForShop(Integer shopId, Boolean salaried);

    public List<Employee> newEmployeesForShop(Integer shopId);

    public Driver driverForId(Integer id);

    public void saveDriver(Driver driver);

    public boolean exists(Integer id);

    public Employee get(Integer id);

    public List<Employee> getAll();

    public void remove(Integer id);

    public Employee save(Employee o);

    public List<Employee> employeeForFirstAndLastName(String first, String second);
}
