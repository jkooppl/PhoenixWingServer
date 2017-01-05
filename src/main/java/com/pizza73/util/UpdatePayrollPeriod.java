package com.pizza73.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pizza73.service.LookupManager;

/**
 * NextBusinessDay - TODO comment me
 *
 * @author chris
 * Created on: 5-Mar-08
 * 
 * Copyright Pizza 73 2008
 */
public class UpdatePayrollPeriod
{
   protected ClassPathXmlApplicationContext ctx;
   private LookupManager mgr;
       
   public void setLookupManager(LookupManager mgr)
   {
       this.mgr = mgr;
   }
   
   public void setUp()
   {
       String[] paths = {
           "util-applicationContext-resources.xml",
           "classpath*:META-INF/applicationContext.xml" };
       ctx = new ClassPathXmlApplicationContext(paths);
       mgr = (LookupManager)ctx.getBean("lookupManager");
   }
   /**
    * @param args
    */
   public static void main(String[] args)
   {
      UpdatePayrollPeriod nbd = new UpdatePayrollPeriod();
      nbd.setUp();
      nbd.mgr.updatePayrollPeriod();
   }
}