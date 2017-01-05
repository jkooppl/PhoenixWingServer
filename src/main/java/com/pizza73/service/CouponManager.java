/**
 * 
 */
package com.pizza73.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.Cart;
import com.pizza73.model.Coupon;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.UniqueCoupon;

/**
 * @author chris
 * 
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface CouponManager extends UniversalManager
{
    public Coupon forCode(String code);

    public Boolean validateCoupon(Coupon coupon, Cart cart);

    public Boolean couponRedeemed(OnlineCustomer oc, Integer couponId);

    public Boolean couponRedeemed(OnlineCustomer oc, String couponId);

    public UniqueCoupon findUniqueCoupon(Integer order_id);

    public UniqueCoupon findUniqueCouponForRedeemedOrder(Integer orderId);

    public UniqueCoupon saveUniqueCoupon(UniqueCoupon o);

    public UniqueCoupon findUniqueCouponForCode(String code);

    public Boolean validateUniqueCoupon(UniqueCoupon uc, Cart cart);

    public String generateCouponCode(int length);

    public void incrementCountForCouponType(Integer prizeId);
}
