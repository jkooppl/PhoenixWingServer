/**
 * 
 */
package com.pizza73.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.pizza73.model.Address;
import com.pizza73.model.Location;
import com.pizza73.model.Municipality;
import com.pizza73.model.OnlineCustomer;

/**
 * @author chris
 *
 */
@Configurable
public abstract class BaseDaoTest
{
   @Autowired
   private SessionFactory sf;
   
   @Autowired
   protected LookupDao lookupDao;
   
   public void clearHibernateFirstLevelCache()
   {
      this.sf.getCurrentSession().flush();
      this.sf.getCurrentSession().clear();
   }
   
   public OnlineCustomer getSampleOnlineCustomer()
   {
      OnlineCustomer tempOnlineCustomerVar = new OnlineCustomer(); 
      Address address = new Address();
      address.setPostalCode("");
      Municipality city = 
         (Municipality)this.lookupDao.get(Municipality.class, Integer.valueOf(1));
      Location location = 
         (Location)this.lookupDao.get(Location.class, "b");
      address.setLocationType(location);
      address.setCity(city);
      
      tempOnlineCustomerVar.setName("New Tester");
      tempOnlineCustomerVar.setPhone("78055555");
      tempOnlineCustomerVar.setEmail("testerOrderDao@pizza73.com");
      tempOnlineCustomerVar.setPassword("test");
      tempOnlineCustomerVar.setAddress(address);
      
      return tempOnlineCustomerVar;
   }
   
   abstract public void testGetAll();
   abstract public void testGet();
   abstract public void testExists();
   abstract public void testSave();
   abstract public void testRemove();
}
