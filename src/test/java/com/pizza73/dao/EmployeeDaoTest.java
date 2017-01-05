package com.pizza73.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.Driver;
import com.pizza73.model.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = 
   {"/test-applicationContext-resources.xml",
    "/applicationContext.xml"})
public class EmployeeDaoTest extends BaseDaoTest
{
   @Autowired
   private EmployeeDao employeeDao;

   
   
   @Test
   public void testEmployeeForId()
   {
      Employee tempEmployeeVar = this.getSampleEmployee();
      Employee savedEmployee = this.employeeDao.save(tempEmployeeVar);
      clearHibernateFirstLevelCache();
      try
      {
         
         Employee retrivedEmployee = 
            this.employeeDao.get(savedEmployee.getId());
         assertTrue(this.objectMatch(savedEmployee, retrivedEmployee));
         assertNotSame(savedEmployee, retrivedEmployee);
      }
      finally
      {
         this.employeeDao.remove(savedEmployee.getId());
      }
   }

   @Test
   public void testEmployeeForUsername()
   {
      Employee tempEmployeeVar = this.getSampleEmployee();
      Employee savedEmployee = this.employeeDao.save(tempEmployeeVar);
      clearHibernateFirstLevelCache();
      try
      {
         Employee retrivedEmployee = 
            this.employeeDao.employeeForUsername(savedEmployee.getEmail());
         assertTrue(this.objectMatch(savedEmployee, retrivedEmployee));
         assertNotSame(savedEmployee, retrivedEmployee);
      }
      finally
      {
         this.employeeDao.remove(savedEmployee.getId());
      }
   }

   @Test
   public void testEmployeeForPayrollId()
   {
      Employee tempEmployeeVar = this.getSampleEmployee();
      Employee savedEmployee = this.employeeDao.save(tempEmployeeVar);
      clearHibernateFirstLevelCache();
      try
      {
         Employee retrivedEmployee = 
            this.employeeDao.employeeForPayrollId(savedEmployee.getPayrollId());
         assertTrue(this.objectMatch(savedEmployee, retrivedEmployee));
         assertNotSame(savedEmployee, retrivedEmployee);
      }
      finally
      {
         this.employeeDao.remove(savedEmployee.getId());
      }
   }

   @Test
   public void testEmployeesForShopWithShopId()
   {
      Employee tempEmployeeVar = this.getSampleEmployee();
      Employee savedEmployee = this.employeeDao.save(tempEmployeeVar);
      clearHibernateFirstLevelCache();
      try
      {
         List<Employee> retrivedEmployeeList = 
            this.employeeDao.employeesForShop(savedEmployee.getShopId());
         assertEquals(retrivedEmployeeList.size(), 1);
         assertTrue(this.objectMatch(savedEmployee, retrivedEmployeeList.get(0)));
         assertNotSame(savedEmployee, retrivedEmployeeList.get(0));
      }
      finally
      {
         this.employeeDao.remove(savedEmployee.getId());
      }
   }

   @Test
   public void testEmployeesForShopWithShopId_salaried()
   {
      Employee tempEmployeeVar = this.getSampleEmployee();
      Employee savedEmployee = this.employeeDao.save(tempEmployeeVar);
      clearHibernateFirstLevelCache();
      try
      {
         List<Employee> retrivedEmployeeList = 
            this.employeeDao.employeesForShop(
               savedEmployee.getShopId(), savedEmployee.isSalariedEmployee());
         assertEquals(retrivedEmployeeList.size(), 1);
         assertTrue(this.objectMatch(savedEmployee, retrivedEmployeeList.get(0)));
         assertNotSame(savedEmployee, retrivedEmployeeList.get(0));
      }
      finally
      {
         this.employeeDao.remove(savedEmployee.getId());
      }
   }

   @Test
   public void testActiveEmployeesForShopWithShopId()
   {
      Employee tempEmployeeVar1 = this.getSampleEmployee();
      Employee tempEmployeeVar2 = this.getSampleEmployee();
      tempEmployeeVar1.setShopId(55555);
      tempEmployeeVar1.setEnabled(true);
      tempEmployeeVar1.setName("test1");
      tempEmployeeVar1.setEmail("1@1");
      tempEmployeeVar2.setShopId(5555);
      tempEmployeeVar2.setEnabled(false);
      tempEmployeeVar1.setName("test2");
      tempEmployeeVar1.setEmail("2@2");
      Employee savedEmployee1 = this.employeeDao.save(tempEmployeeVar1);
      Employee savedEmployee2 = this.employeeDao.save(tempEmployeeVar2);
      
      clearHibernateFirstLevelCache();
      try
      {
         List<Employee> retrivedEmployeeList = 
            this.employeeDao.activeEmployeesForShop(savedEmployee1.getShopId());
         assertEquals(retrivedEmployeeList.size(), 1);
         assertTrue(this.objectMatch(savedEmployee1, retrivedEmployeeList.get(0)));
         assertNotSame(savedEmployee1, retrivedEmployeeList.get(0));
      }
      finally
      {
         this.employeeDao.remove(savedEmployee1.getId());
         this.employeeDao.remove(savedEmployee2.getId());
      }
   }

   @Test
   public void testActiveEmployeesForShopWithShopId_salaried()
   {
      Employee tempEmployeeVar1 = this.getSampleEmployee();
      Employee tempEmployeeVar2 = this.getSampleEmployee();
      tempEmployeeVar1.setShopId(55555);
      tempEmployeeVar1.setEnabled(true);
      tempEmployeeVar1.setName("test1");
      tempEmployeeVar1.setEmail("1@1");
      tempEmployeeVar2.setShopId(5555);
      tempEmployeeVar2.setEnabled(false);
      tempEmployeeVar1.setName("test2");
      tempEmployeeVar1.setEmail("2@2");
      Employee savedEmployee1 = this.employeeDao.save(tempEmployeeVar1);
      Employee savedEmployee2 = this.employeeDao.save(tempEmployeeVar2);
      clearHibernateFirstLevelCache();
      try
      {
         List<Employee> retrivedEmployeeList = 
            this.employeeDao.activeEmployeesForShop(
               savedEmployee1.getShopId(),savedEmployee1.isSalariedEmployee());
         assertEquals(retrivedEmployeeList.size(), 1);
         assertTrue(this.objectMatch(savedEmployee1, retrivedEmployeeList.get(0)));
         assertNotSame(savedEmployee1, retrivedEmployeeList.get(0));
      }
      finally
      {
         this.employeeDao.remove(savedEmployee1.getId());
         this.employeeDao.remove(savedEmployee2.getId());
      }
   }

   // TODO saveDriver() return void, so unsafe to delete sample data after
   // testing.
   @Test
   public void testSaveDriver()
   {

   }

   // FIXME hibernate.LazyInitializationException
   @Test
   public void testDriverForShopWithShopId()
   {
      Driver tempDriverVar = this.getSampleDriver();
      assertTrue(this.employeeDao.driversForShop(
            tempDriverVar.getShop().getId()).contains(tempDriverVar));
   }

   // FIXME assertFails
   @Test
   public void testDriverForShopWithShopOjbect()
   {
      Driver tempDriverVar = this.getSampleDriver();
      assertTrue(
         this.employeeDao.driversForShop(tempDriverVar.getShop()).contains(tempDriverVar));
   }

   @Test
   public void testDriverforId()
   {
      Driver tempDriverVar = this.getSampleDriver();
      assertTrue(this.objectMatch(
         tempDriverVar, this.employeeDao.driverForId(tempDriverVar.getId())));
   }

   @Override
   @Test
   public void testExists()
   {
      Employee tempEmployeeVar = this.getSampleEmployee();
      Employee savedEmployee = this.employeeDao.save(tempEmployeeVar);
      try
      {
         assertTrue(this.employeeDao.exists(savedEmployee.getId()));
      }
      finally
      {
         this.employeeDao.remove(savedEmployee.getId());
      }
   }

   @Override
   @Test
   public void testGet()
   {
      Employee tempEmployeeVar = this.getSampleEmployee();
      Employee savedEmployee = this.employeeDao.save(tempEmployeeVar);
      clearHibernateFirstLevelCache();
      try
      {
         Employee retrivedEmployee = this.employeeDao.get(savedEmployee.getId());
         assertTrue(this.objectMatch(savedEmployee, retrivedEmployee));
         assertNotSame(savedEmployee, retrivedEmployee);
      }
      finally
      {
         this.employeeDao.remove(savedEmployee.getId());
      }
   }

   @Override
   @Test
   public void testGetAll()
   {
      Employee tempEmployeeVar = this.getSampleEmployee();
      int numOfRecords = this.employeeDao.getAll().size();
      Employee savedEmployee = this.employeeDao.save(tempEmployeeVar);
      try
      {
         assertEquals(numOfRecords + 1, this.employeeDao.getAll().size());
      }
      finally
      {
         this.employeeDao.remove(savedEmployee.getId());
      }
   }

   @Override
   @Test
   public void testRemove()
   {
      Employee tempEmployeeVar = this.getSampleEmployee();
      int numOfRecords = this.employeeDao.getAll().size();
      Employee savedEmployee = this.employeeDao.save(tempEmployeeVar);
      assertEquals(numOfRecords + 1, this.employeeDao.getAll().size());
      this.employeeDao.remove(savedEmployee.getId());
      assertEquals(numOfRecords, this.employeeDao.getAll().size());
   }

   // FIXME testfails when pasword set to null in the sample data;
   // error info:
   // Caused by: org.postgresql.util.PSQLException: ERROR: value too long for
   // type character(6)
   // a few places in Employee.java set to the email, longer than 6 chars.
   @Override
   @Test
   public void testSave()
   {
      Employee tempEmployeeVar = this.getSampleEmployee();
      Employee savedEmployee = this.employeeDao.save(tempEmployeeVar);
      clearHibernateFirstLevelCache();
      try
      {
         Employee retrivedEmployee = 
            this.employeeDao.employeeForId(savedEmployee.getId());
         assertTrue(this.objectMatch(savedEmployee, retrivedEmployee));
         assertNotSame(savedEmployee, retrivedEmployee);
      }
      finally
      {
         this.employeeDao.remove(savedEmployee.getId());
      }
   }

   public Employee getSampleEmployee()
   {
      Employee tempEmployeeVar = new Employee();
      tempEmployeeVar.setLastName("Tester");
      tempEmployeeVar.setName("Tester");
      tempEmployeeVar.setEmail("test@pizza73.com");
      tempEmployeeVar.setPassword("12345");
      tempEmployeeVar.setPayrollId("WH001100");
      tempEmployeeVar.setShopId(12345);
      tempEmployeeVar.getAddress().setPostalCode("T6G6G5");
      tempEmployeeVar.getAddress().setSuiteNumber("32");
      return tempEmployeeVar;
   }

   public Driver getSampleDriver()
   {
      Driver tempDriverVar = this.employeeDao.driverForId(2);
      return tempDriverVar;
   }

   public boolean objectMatch(Employee e1, Employee e2)
   {
      if (e1.getId().equals(e2.getId())
            && e1.getLastName().trim().equals(e2.getLastName().trim())
            && e1.getName().trim().equals(e2.getName().trim())
            && e1.getEmail().trim().equals(e2.getEmail().trim())
            && e1.getPassword().equals(e2.getPassword())
            && e1.getShopId().equals(e2.getShopId()))
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   public boolean objectMatch(Driver d1, Driver d2)
   {
      if (d1.getId().equals(d2.getId()) && d1.getName().equals(d2.getName()))
      {
         return true;
      }
      else
      {
         return false;
      }
   }
}