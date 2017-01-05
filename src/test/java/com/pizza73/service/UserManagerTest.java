package com.pizza73.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pizza73.model.OnlineCustomer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = 
   {"/test-applicationContext-resources.xml",
    "/applicationContext.xml"})
public class UserManagerTest
{
   @Autowired
   private UserManager userManager;

   @Test
   public void testGet()
   {
      OnlineCustomer oc = 
         (OnlineCustomer)this.userManager.get(OnlineCustomer.class, Integer.valueOf(1));
      
      assertNotNull(oc);
   } 
}
