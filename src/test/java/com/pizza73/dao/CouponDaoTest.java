/**
 * 
 */
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

import com.pizza73.model.Coupon;
import com.pizza73.model.CouponRedemption;
import com.pizza73.model.OnlineCustomer;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations={
      "/test-applicationContext-resources.xml", 
      "/applicationContext.xml"})
public class CouponDaoTest extends BaseDaoTest 
{
   
   @Autowired
	private CouponDao couponDao;
   @Autowired
	private LookupDao lookupDao;
   @Autowired
	private UserDao userDao;

	@Test
	public void testCouponRedeemed(){
		Coupon tempCouponVar = this.getSampleCoupon();
		OnlineCustomer tempOnlineCustomerVar=this.getSampleOnlineCustomer();
		OnlineCustomer savedOnlineCustomer=this.userDao.save(tempOnlineCustomerVar);
		Coupon savedCoupon = this.couponDao.save(tempCouponVar);
		CouponRedemption cr=new CouponRedemption(1,savedOnlineCustomer);
		CouponRedemption savedcr=(CouponRedemption) this.lookupDao.save(cr);
		try {
			List<CouponRedemption> retrivedCouponRedemptionList = this.couponDao.couponRedeemed(savedOnlineCustomer,1);
			assertEquals(retrivedCouponRedemptionList.size(), 1);
		} finally {
			this.couponDao.remove(savedCoupon.getId());
			this.lookupDao.remove(CouponRedemption.class,savedcr.getId());
			this.userDao.remove(savedOnlineCustomer.getId());
		 }		
	}
	
	@Test
	public void testForCode(){
		Coupon tempCouponVar = this.getSampleCoupon();
		Coupon savedCoupon = this.couponDao.save(tempCouponVar);
		clearHibernateFirstLevelCache();
		try {
			Coupon retrivedCoupon = this.couponDao.forCode(savedCoupon.getCode());
			assertTrue(this.objectMatch(savedCoupon, retrivedCoupon));
			assertNotSame(savedCoupon, retrivedCoupon);
		} finally {
			this.couponDao.remove(savedCoupon.getId());
		 }
	}
	
   @Override
   @Test
	public void testExists() {
		Coupon tempCouponVar = this.getSampleCoupon();
		Coupon savedCoupon = this.couponDao.save(tempCouponVar);
		try {
			assertTrue(this.couponDao.exists(savedCoupon.getId()));
		} finally {
			this.couponDao.remove(savedCoupon.getId());
		 }	
	}

	/* (non-Javadoc)
	 * @see testbase.DAOTestCase#testGet()
	 */
   @Override
   @Test
	public void testGet() {
		Coupon tempCouponVar = this.getSampleCoupon();
		Coupon savedCoupon = this.couponDao.save(tempCouponVar);
		clearHibernateFirstLevelCache();
		try {
			Coupon retrivedCoupon = this.couponDao.get(savedCoupon.getId());
			assertTrue(this.objectMatch(savedCoupon, retrivedCoupon));
			assertNotSame(savedCoupon, retrivedCoupon);
		} finally {
			this.couponDao.remove(savedCoupon.getId());
		 }		

	}

	/* (non-Javadoc)
	 * @see testbase.DAOTestCase#testGetAll()
	 */
   @Override
   @Test
	public void testGetAll() {
		Coupon tempCouponVar = this.getSampleCoupon();
		int numOfRecords=this.couponDao.getAll().size();
		Coupon savedCoupon = this.couponDao.save(tempCouponVar);
		try{
		assertEquals(numOfRecords+1, this.couponDao.getAll().size());
		}finally{
		this.couponDao.remove(savedCoupon.getId());
		}
	}

	/* (non-Javadoc)
	 * @see testbase.DAOTestCase#testRemove()
	 */
   @Override
   @Test
	public void testRemove() {
		Coupon tempCouponVar = this.getSampleCoupon();
		int numOfRecords=this.couponDao.getAll().size();
		Coupon savedCoupon = this.couponDao.save(tempCouponVar);
		assertEquals(numOfRecords+1, this.couponDao.getAll().size());
		this.couponDao.remove(savedCoupon.getId());
		assertEquals(numOfRecords, this.couponDao.getAll().size());

	}

	/* (non-Javadoc)
	 * @see testbase.DAOTestCase#testSave()
	 */
	//FIXME generic save doesn't work since Id is not auto-generated and there is no id setter available.
	//temporarily add setid in Coupon.java
   @Override
   @Test
	public void testSave() {

		Coupon tempCouponVar=this.getSampleCoupon();
		Coupon savedCoupon=this.couponDao.save(tempCouponVar);
		clearHibernateFirstLevelCache();
		try{
			Coupon retrivedCoupon=this.couponDao.get(savedCoupon.getId());
			assertTrue(this.objectMatch(savedCoupon, retrivedCoupon));
			assertNotSame(savedCoupon,retrivedCoupon);
		}
		finally{
			this.couponDao.remove(savedCoupon.getId());
		}
	}

	public Coupon getSampleCoupon(){
		Coupon couponFromDB=this.couponDao.get(2);
		Coupon tempCouponVar=new Coupon();
		tempCouponVar.setId(12345);
		tempCouponVar.setCode("55555");
		tempCouponVar.setDescription("testing");
		tempCouponVar.setName("test sample");
		tempCouponVar.setStartDate(couponFromDB.getStartDate());
		tempCouponVar.setEndDate(couponFromDB.getEndDate());
		tempCouponVar.setCity(couponFromDB.getCity());
		tempCouponVar.setDiscount(couponFromDB.getDiscount());
		tempCouponVar.setErrorMessage(couponFromDB.getErrorMessage());
		tempCouponVar.setDetails(couponFromDB.getDetails());
		tempCouponVar.setRequiredProductId(couponFromDB.getRequiredProductId());
		tempCouponVar.setRequiredSizeId(couponFromDB.getRequiredSizeId());
		tempCouponVar.setCalculatedDiscount(couponFromDB.getCalculatedDiscount());
		return tempCouponVar;
	}
	
	public boolean objectMatch(Coupon c1, Coupon c2){
		if(c1.getId().equals(c2.getId()) &&
		   c1.getCode().equals(c2.getCode()) )
		   return true;
		else
		   return false;
	}
	
	public OnlineCustomer getSampleOnlineCustomer() {
		OnlineCustomer tempOnlineCustomerVar = this.userDao.customerForId(8783);
		OnlineCustomer oc = new OnlineCustomer();
		
		oc.setAddress(tempOnlineCustomerVar.getAddress());
		oc.setName("New Tester");
		oc.setPhone("78055555");
		oc.setEmail("testCouponCustomer@pizza73.com");
		oc.setPassword("test");
		
		return oc;
	}
}