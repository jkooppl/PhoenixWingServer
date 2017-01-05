/**
 *
 */
package com.pizza73.dao;

import com.pizza73.model.Coupon;
import com.pizza73.model.CouponRedemption;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.UniqueCoupon;

import java.util.List;

/**
 * @author chris
 *
 */
public interface CouponDao extends UniversalDao
{
    public Coupon forCode(String code);

    public List<CouponRedemption> couponRedeemed(OnlineCustomer oc, Integer couponId);

    public List<CouponRedemption> couponRedeemedForBusinessDate(OnlineCustomer oc, Integer couponId);

    public boolean exists(Integer id);

    public Coupon get(Integer id);

    public List<Coupon> getAll();

    public void remove(Integer id);

    public Coupon save(Coupon o);

    public UniqueCoupon findUniqueCoupon(Integer orderId);

    public UniqueCoupon findUniqueCouponForRedeemedOrder(Integer orderId);

    public UniqueCoupon saveUniqueCoupon(UniqueCoupon o);

    public UniqueCoupon findUniqueCouponForCode(String code);

    public UniqueCoupon couponUsedToday(String email);

    public void incrementCountForCouponType(Integer prizeId);
}
