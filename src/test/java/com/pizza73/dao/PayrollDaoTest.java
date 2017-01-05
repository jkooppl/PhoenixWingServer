package com.pizza73.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.Payroll;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = 
   {"/test-applicationContext-resources.xml",
    "/applicationContext.xml"})
public class PayrollDaoTest extends BaseDaoTest
{
   @Autowired
   private PayrollDao payrollDao;

   @Test
   public void testLastPayroll()
   {
      Payroll tempPayrollVar = this.getSamplePayroll();
      Payroll savedPayroll = this.payrollDao.save(tempPayrollVar);
      clearHibernateFirstLevelCache();
      try
      {
         Payroll retrivedPayroll = 
            this.payrollDao.lastPayroll(savedPayroll.getEmployeeId());
         assertTrue(this.ojbectMatch(savedPayroll, retrivedPayroll));
         assertNotSame(savedPayroll, retrivedPayroll);
      }
      finally
      {
         this.payrollDao.remove(savedPayroll.getId());
      }
   }

   @Test
   public void testLastPayrollUnsubmitted()
   {
      Payroll tempPayrollVar = this.getSamplePayroll();
      tempPayrollVar.setSubmitted(false);
      Payroll savedPayroll = this.payrollDao.save(tempPayrollVar);
      clearHibernateFirstLevelCache();
      try
      {
         Payroll retrivedPayroll = 
            this.payrollDao.lastPayrollUnsubmitted(savedPayroll.getEmployeeId());
         assertTrue(this.ojbectMatch(savedPayroll, retrivedPayroll));
         assertNotSame(savedPayroll, retrivedPayroll);
      }
      finally
      {
         this.payrollDao.remove(savedPayroll.getId());
      }
   }

   @Test
   public void testLastPayrollSubmitted()
   {

      Payroll tempPayrollVar = this.getSamplePayroll();
      tempPayrollVar.setSubmitted(true);
      Payroll savedPayroll = this.payrollDao.save(tempPayrollVar);
      clearHibernateFirstLevelCache();
      try
      {
         Payroll retrivedPayroll = 
            this.payrollDao.lastPayrollSubmitted(savedPayroll.getEmployeeId());
         assertTrue(this.ojbectMatch(savedPayroll, retrivedPayroll));
         assertNotSame(savedPayroll, retrivedPayroll);
      }
      finally
      {
         this.payrollDao.remove(savedPayroll.getId());
      }

   }

   @Test
   public void testPayrollForEmployeeWithPayrollId()
   {
      Payroll tempPayrollVar1 = this.getSamplePayroll();
      tempPayrollVar1.setPayrollPeriod(20);
      Payroll tempPayrollVar2 = this.getSamplePayroll();
      tempPayrollVar2.setPayrollPeriod(21);
      Payroll savedPayroll1 = this.payrollDao.save(tempPayrollVar1);
      Payroll savedPayroll2 = this.payrollDao.save(tempPayrollVar2);
      try
      {
         List<Payroll> retrivedPayrollList = 
            this.payrollDao.payrollForEmployee(tempPayrollVar1.getEmployeeId());
         assertEquals(retrivedPayrollList.size(), 2);
         assertFalse(
            this.ojbectMatch(retrivedPayrollList.get(0),
            retrivedPayrollList.get(1)));
         if (this.ojbectMatch(savedPayroll1, retrivedPayrollList.get(0)))
         {
            assertTrue(this.ojbectMatch(savedPayroll2, retrivedPayrollList.get(1)));
         }
         else
         {
            assertTrue(this.ojbectMatch(savedPayroll2, retrivedPayrollList.get(0)));
         }
         assertFalse(this.ojbectMatch(
            retrivedPayrollList.get(0),retrivedPayrollList.get(1)));
      }
      finally
      {
         this.payrollDao.remove(savedPayroll1.getId());
         this.payrollDao.remove(savedPayroll2.getId());
      }
   }

   @Test
   public void testPayrollForEmployeeWithPayrollId_Year()
   {
      Payroll tempPayrollVar1 = this.getSamplePayroll();
      tempPayrollVar1.setPayrollPeriod(20);
      tempPayrollVar1.setPayrollYear(2005);
      Payroll tempPayrollVar2 = this.getSamplePayroll();
      tempPayrollVar2.setPayrollPeriod(21);
      tempPayrollVar2.setPayrollYear(2006);
      Payroll savedPayroll1 = this.payrollDao.save(tempPayrollVar1);
      Payroll savedPayroll2 = this.payrollDao.save(tempPayrollVar2);
      try
      {
         List<Payroll> retrivedPayrollList = 
            this.payrollDao.payrollForEmployee(tempPayrollVar1.getEmployeeId(), 2006);
         assertEquals(retrivedPayrollList.size(), 1);
         assertTrue(this.ojbectMatch(savedPayroll2, retrivedPayrollList.get(0)));
      }
      finally
      {
         this.payrollDao.remove(savedPayroll1.getId());
         this.payrollDao.remove(savedPayroll2.getId());
      }
   }

   @Test
   public void testPayrollForPeriod()
   {
      Payroll tempPayrollVar1 = this.getSamplePayroll();
      tempPayrollVar1.setPayrollPeriod(20);
      tempPayrollVar1.setPayrollYear(2005);
      Payroll tempPayrollVar2 = this.getSamplePayroll();
      tempPayrollVar2.setPayrollPeriod(21);
      tempPayrollVar2.setPayrollYear(2006);
      Payroll savedPayroll1 = this.payrollDao.save(tempPayrollVar1);
      Payroll savedPayroll2 = this.payrollDao.save(tempPayrollVar2);
      try
      {
         Payroll retrivedPayroll = 
            this.payrollDao.payrollForPeriod(tempPayrollVar1.getEmployeeId(), 21, 2006);
         assertTrue(this.ojbectMatch(savedPayroll2, retrivedPayroll));
      }
      finally
      {
         this.payrollDao.remove(savedPayroll1.getId());
         this.payrollDao.remove(savedPayroll2.getId());
      }
   }

   @Override
   @Test
   public void testExists()
   {
      Payroll tempPayrollVar = this.getSamplePayroll();
      Payroll savedPayroll = this.payrollDao.save(tempPayrollVar);
      try
      {
         assertTrue(this.payrollDao.exists(savedPayroll.getId()));
      }
      finally
      {
         this.payrollDao.remove(savedPayroll.getId());
      }

   }

   @Override
   @Test
   public void testGet()
   {
      Payroll tempPayrollVar = this.getSamplePayroll();
      Payroll savedPayroll = this.payrollDao.save(tempPayrollVar);
      clearHibernateFirstLevelCache();
      try
      {
         Payroll retrivedPayroll = this.payrollDao.get(savedPayroll.getId());
         assertTrue(this.ojbectMatch(savedPayroll, retrivedPayroll));
         assertNotSame(savedPayroll, retrivedPayroll);
      }
      finally
      {
         this.payrollDao.remove(savedPayroll.getId());
      }
   }

   @Override
   @Test
   public void testGetAll()
   {
      Payroll tempPayrollVar = this.getSamplePayroll();
      int numOfRecords = this.payrollDao.getAll().size();
      Payroll savedPayroll = this.payrollDao.save(tempPayrollVar);
      try
      {
         assertEquals(numOfRecords + 1, this.payrollDao.getAll().size());
      }
      finally
      {
         this.payrollDao.remove(savedPayroll.getId());
      }
   }

   @Override
   @Test
   public void testRemove()
   {
      Payroll tempPayrollVar = this.getSamplePayroll();
      int numOfRecords = this.payrollDao.getAll().size();
      Payroll savedPayroll = this.payrollDao.save(tempPayrollVar);
      assertEquals(numOfRecords + 1, this.payrollDao.getAll().size());
      this.payrollDao.remove(savedPayroll.getId());
      assertEquals(numOfRecords, this.payrollDao.getAll().size());
   }

   @Override
   @Test
   public void testSave()
   {
      Payroll tempPayrollVar = this.getSamplePayroll();
      Payroll savedPayroll = this.payrollDao.save(tempPayrollVar);
      clearHibernateFirstLevelCache();
      try
      {
         Payroll retrivedPayroll = this.payrollDao.get(savedPayroll.getId());
         assertTrue(this.ojbectMatch(savedPayroll, retrivedPayroll));
         assertNotSame(savedPayroll, retrivedPayroll);
      }
      finally
      {
         this.payrollDao.remove(savedPayroll.getId());
      }
   }

   private Payroll getSamplePayroll()
   {
      Payroll tempPayrollVar = new Payroll();
      tempPayrollVar.setPayrollId("UT001100");
      tempPayrollVar.setPayrollPeriod(24);
      tempPayrollVar.setPayrollYear(2007);
      tempPayrollVar.setWeekOneMon(7.0);
      tempPayrollVar.setWeekOneTues(7.0);
      tempPayrollVar.setWeekOneWed(7.0);
      tempPayrollVar.setWeekOneThurs(7.0);
      tempPayrollVar.setWeekOneFri(7.0);
      tempPayrollVar.setWeekOneSat(7.0);
      tempPayrollVar.setWeekOneSun(4.0);
      tempPayrollVar.setWeekTwoMon(7.0);
      tempPayrollVar.setWeekTwoTues(7.0);
      tempPayrollVar.setWeekTwoWed(7.0);
      tempPayrollVar.setWeekTwoThurs(7.0);
      tempPayrollVar.setWeekTwoFri(7.0);
      tempPayrollVar.setWeekTwoSat(7.0);
      tempPayrollVar.setWeekTwoSun(3.0);
      tempPayrollVar.setOvertimeHoursWeekOne(3.0);
      tempPayrollVar.setOvertimeHoursWeekTwo(4.0);
      tempPayrollVar.setTotalHoursWeekOne(42.0);
      tempPayrollVar.setTotalHoursWeekTwo(42.0);
      tempPayrollVar.setSubmitted(false);
      return tempPayrollVar;
   }

   private boolean ojbectMatch(Payroll p1, Payroll p2)
   {
      if (p1.getId().equals(p2.getId())
            && p1.getPayrollId().equals(p2.getPayrollId())
            && p1.getPayrollPeriod().equals(p2.getPayrollPeriod())
            && p1.getPayrollYear().equals(p2.getPayrollYear())
            && Math.round(p1.getWeekOneMon() * 10) == Math.round(p2.getWeekOneMon() * 10)
            && Math.round(p1.getWeekOneTues() * 10) == Math.round(p2.getWeekOneTues() * 10)
            && Math.round(p1.getWeekOneWed() * 10) == Math.round(p2.getWeekOneWed() * 10)
            && Math.round(p1.getWeekOneThurs() * 10) == Math.round(p2.getWeekOneThurs() * 10)
            && Math.round(p1.getWeekOneFri() * 10) == Math.round(p2.getWeekOneFri() * 10)
            && Math.round(p1.getWeekOneSat() * 10) == Math.round(p2.getWeekOneSat() * 10)
            && Math.round(p1.getWeekOneSun() * 10) == Math.round(p2.getWeekOneSun() * 10)
            && Math.round(p1.getWeekTwoMon() * 10) == Math.round(p2.getWeekTwoMon() * 10)
            && Math.round(p1.getWeekTwoTues() * 10) == Math.round(p2.getWeekTwoTues() * 10)
            && Math.round(p1.getWeekTwoWed() * 10) == Math.round(p2.getWeekTwoWed() * 10)
            && Math.round(p1.getWeekTwoThurs() * 10) == Math.round(p2.getWeekTwoThurs() * 10)
            && Math.round(p1.getWeekTwoFri() * 10) == Math.round(p2.getWeekTwoFri() * 10)
            && Math.round(p1.getWeekTwoSat() * 10) == Math.round(p2.getWeekTwoSat() * 10)
            && Math.round(p1.getWeekTwoSun() * 10) == Math.round(p2.getWeekTwoSun() * 10)
            && Math.round(p1.getTotalHoursWeekOne() * 10) == Math.round(p2.getTotalHoursWeekOne() * 10)
            && Math.round(p1.getTotalHoursWeekTwo() * 10) == Math.round(p2.getTotalHoursWeekTwo() * 10)
            && Math.round(p1.getOvertimeHoursWeekOne() * 10) == Math.round(p2.getOvertimeHoursWeekOne() * 10)
            && Math.round(p1.getOvertimeHoursWeekTwo() * 10) == Math.round(p2.getOvertimeHoursWeekTwo() * 10)
            && p1.isSubmitted() == p2.isSubmitted())
      {
         return true;
      }
      else
      {
         return false;
      }
   }
}
