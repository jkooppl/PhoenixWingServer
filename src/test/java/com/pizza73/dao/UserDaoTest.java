package com.pizza73.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.OnlineCustomer;

/*
 * No setter methods in Municipality class, Tests are based on preset data.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = 
   {"/test-applicationContext-resources.xml",
    "/applicationContext.xml"})
public class UserDaoTest extends BaseDaoTest
{
   @Autowired
   private UserDao userDao;

   @Test
   public void testCustomerForEmail()
   {
      OnlineCustomer tempOnlineCustomerVar = this.getSampleOnlineCustomer();
      OnlineCustomer savedOnlineCustomer = 
         this.userDao.save(tempOnlineCustomerVar);
      clearHibernateFirstLevelCache();
      try
      {
         OnlineCustomer retrivedOnlinecustomer = 
            this.userDao.customerForEmail(savedOnlineCustomer.getEmail());
         assertEquals(savedOnlineCustomer, retrivedOnlinecustomer);
         assertNotSame(savedOnlineCustomer, retrivedOnlinecustomer);
      }
      finally
      {
         this.userDao.remove(savedOnlineCustomer.getId());
      }
   }

   @Test
   public void testcustomerForId()
   {
      OnlineCustomer tempOnlineCustomerVar = this.getSampleOnlineCustomer();
      OnlineCustomer savedOnlineCustomer = 
         this.userDao.save(tempOnlineCustomerVar);
      clearHibernateFirstLevelCache();
      try
      {
         OnlineCustomer retrivedOnlinecustomer = 
            this.userDao.customerForId(savedOnlineCustomer.getId());
         assertEquals(savedOnlineCustomer, retrivedOnlinecustomer);
         assertNotSame(savedOnlineCustomer, retrivedOnlinecustomer);
      }
      finally
      {
         this.userDao.remove(savedOnlineCustomer.getId());
      }
   }

   @Test
   public void testUpdateRepeatCount()
   {
      OnlineCustomer tempOnlineCustomerVar = this.getSampleOnlineCustomer();
      OnlineCustomer savedOnlineCustomer = 
         this.userDao.save(tempOnlineCustomerVar);
      int count = savedOnlineCustomer.getRepeatCount();
      savedOnlineCustomer.setRepeatCount(count + 1);
      try
      {
         this.userDao.updateRepeatCount(savedOnlineCustomer);
         assertEquals(count + 1, savedOnlineCustomer.getRepeatCount().intValue());
      }
      finally
      {
         this.userDao.remove(savedOnlineCustomer.getId());
      }
   }

   @Override
   @Test
   public void testExists()
   {
      OnlineCustomer tempOnlineCustomerVar = this.getSampleOnlineCustomer();
      OnlineCustomer savedOnlineCustomer = this.userDao.save(tempOnlineCustomerVar);
      try
      {
         assertTrue(this.userDao.exists(savedOnlineCustomer.getId()));
      }
      finally
      {
         this.userDao.remove(savedOnlineCustomer.getId());
      }
   }

   @Override
   @Test
   public void testGet()
   {
      OnlineCustomer tempOnlineCustomerVar = this.getSampleOnlineCustomer();
      OnlineCustomer savedOnlineCustomer = this.userDao.save(tempOnlineCustomerVar);
      clearHibernateFirstLevelCache();
      try
      {
         OnlineCustomer retrivedOnlinecustomer = 
            this.userDao.customerForId(savedOnlineCustomer.getId());
         assertEquals(savedOnlineCustomer, retrivedOnlinecustomer);
         assertNotSame(savedOnlineCustomer, retrivedOnlinecustomer);
      }
      finally
      {
         this.userDao.remove(savedOnlineCustomer.getId());
      }
   }

   @Override
   @Test
   public void testGetAll()
   {
      OnlineCustomer tempOnlineCustomerVar = this.getSampleOnlineCustomer();
      int numOfRecords = this.userDao.getAll().size();
      OnlineCustomer savedOnlineCustomer = this.userDao.save(tempOnlineCustomerVar);
      try
      {
         assertEquals(numOfRecords + 1, this.userDao.getAll().size());
      }
      finally
      {
         this.userDao.remove(savedOnlineCustomer.getId());
      }
   }

   @Override
   @Test
   public void testRemove()
   {
      OnlineCustomer tempOnlineCustomerVar = this.getSampleOnlineCustomer();
      int numOfRecords = this.userDao.getAll().size();
      OnlineCustomer savedOnlineCustomer = this.userDao.save(tempOnlineCustomerVar);
      assertEquals(numOfRecords + 1, this.userDao.getAll().size());
      this.userDao.remove(savedOnlineCustomer.getId());
   }

   // FIXED not pass org.hibernate.MappingException: Unknown
   // entity:com.pizza73.model.User
   @Override
   @Test
   public void testSave()
   {
      OnlineCustomer tempOnlineCustomerVar = this.getSampleOnlineCustomer();
      OnlineCustomer savedOnlineCustomer = this.userDao.save(tempOnlineCustomerVar);
      clearHibernateFirstLevelCache();
      try
      {
         OnlineCustomer retrivedOnlinecustomer = 
            this.userDao.get(savedOnlineCustomer.getId());
         assertEquals(savedOnlineCustomer, retrivedOnlinecustomer);
         assertNotSame(savedOnlineCustomer, retrivedOnlinecustomer);
      }
      finally
      {
         this.userDao.remove(savedOnlineCustomer.getId());
      }
   }
}