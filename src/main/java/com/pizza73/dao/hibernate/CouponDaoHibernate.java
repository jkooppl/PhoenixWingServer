/**
 *
 */
package com.pizza73.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.pizza73.dao.CouponDao;
import com.pizza73.model.Coupon;
import com.pizza73.model.CouponRedemption;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.UniqueCoupon;

/**
 * @author chris
 *
 */
@Repository("couponDao")
public class CouponDaoHibernate extends UniversalDaoHibernate implements CouponDao
{
    /**
     */
    public CouponDaoHibernate()
    {
    }

    @SuppressWarnings("unchecked")
    public List<CouponRedemption> couponRedeemed(OnlineCustomer oc, Integer couponId)
    {
        Query query = super.getCurrentSession()
            .createQuery("from CouponRedemption cr where cr.onlineId=:onlineId and " + "cr.couponId = :couponId")
            .setInteger("onlineId", oc.getId()).setInteger("couponId", couponId);
        return query.list();
    }

    public List<CouponRedemption> couponRedeemedForBusinessDate(OnlineCustomer oc, Integer couponId)
    {
        Query query = super.getCurrentSession()
            .createQuery("from CouponRedemption cr where cr.onlineId=:onlineId and " + "cr.couponId = :couponId and redemptionDate =: businessDate")
            .setInteger("onlineId", oc.getId()).setInteger("couponId", couponId).setCalendar("businessDate", this.businessDate());
        return query.list();
    }

    public Coupon forCode(String code)
    {
        Query query = super.getCurrentSession().createQuery("from Coupon c where c.code=:code").setString("code", code);
        Coupon coupon = (Coupon) query.uniqueResult();

        return coupon;
    }

    public boolean exists(Integer id)
    {
        Coupon entity = this.get(id);
        return entity != null;
    }

    public Coupon get(Integer id)
    {
        return (Coupon) this.get(Coupon.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Coupon> getAll()
    {
        return (List<Coupon>) this.getAll(Coupon.class);
    }

    public void remove(Integer id)
    {
        this.remove(this.get(id));
    }

    public Coupon save(Coupon c)
    {
        return (Coupon) super.save(c);
    }

    public UniqueCoupon findUniqueCoupon(Integer order_id)
    {
        Query query = super.getCurrentSession().createQuery("from UniqueCoupon c where c.orderId=:order_id")
            .setInteger("order_id", order_id);
        return (UniqueCoupon) query.uniqueResult();
    }

    public UniqueCoupon findUniqueCouponForRedeemedOrder(Integer order_id)
    {
        Query query = super.getCurrentSession().createQuery("from UniqueCoupon c where c.redeemedOrderId=:order_id")
            .setInteger("order_id", order_id);
        return (UniqueCoupon) query.uniqueResult();
    }

    public UniqueCoupon findUniqueCouponForCode(String code)
    {
        Query query = super.getCurrentSession().createQuery("from UniqueCoupon c where c.code=:code")
            .setString("code", code);
        return (UniqueCoupon) query.uniqueResult();
    }

    public UniqueCoupon saveUniqueCoupon(UniqueCoupon o)
    {
        return (UniqueCoupon) super.save(o);
    }

    public UniqueCoupon couponUsedToday(String email)
    {
        Query query = super.getCurrentSession().createQuery(
            "from UniqueCoupon c where c.email=:email and c.redemptionDate=:date");
        query.setString("email", email);

        query.setDate("date", businessDate().getTime());
        return (UniqueCoupon) query.uniqueResult();
    }

    @Override
    public void incrementCountForCouponType(Integer prizeId)
    {
        Query query = super.getCurrentSession()
            .createSQLQuery("update spin_to_win_prizes set count = count + 1 where id =:id").setParameter("id", prizeId);
        query.executeUpdate();
    }
}