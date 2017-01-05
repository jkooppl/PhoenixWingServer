package com.pizza73.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
      "/test-applicationContext-blank.xml"})
public class AddressTest
{

   @Test
   public void testAddressEdmontonStreetDirection()
   {
//      OnlineCustomer oc = new OnlineCustomer();
      Address address = new Address();
      address.setStreetAddress("12424 91 ST");
      address.appendStreetDirection(null);
      assertEquals(address.getStreetAddress(), "12424 91 ST NW");
   }
   
   @Test
   public void testPopulateStreetAddress()
   {
      Address address = new Address();
      address.setStreetNumber("12424");
      address.setStreetName("91");
      address.setStreetType("ST");
      address.setStreetSuffix(" ");
      address.setStreetDirection("NW");
      address.populateStreetAddress();
      assertEquals(address.getStreetAddress(), "12424 91 ST NW");
   }
}
