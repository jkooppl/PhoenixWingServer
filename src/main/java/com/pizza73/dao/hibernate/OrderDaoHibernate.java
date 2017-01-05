package com.pizza73.dao.hibernate;

import com.pizza73.dao.OrderDao;
import com.pizza73.model.Credit;
import com.pizza73.model.Municipality;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.Order;
import com.pizza73.model.OrderComment;
import com.pizza73.model.Shop;
import com.pizza73.model.StreetAddress;
import com.pizza73.model.contest.SpinToWinPrize;
import com.pizza73.model.conversion.Sales;
import com.pizza73.model.enums.OrderOrigin;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * OrderDaoHibernate.java TODO comment me
 *
 * @author chris 9-Sep-06
 * @Copyright Flying Pizza 73
 */
@Repository("orderDao")
public class OrderDaoHibernate extends UniversalDaoHibernate implements OrderDao
{

    public OrderDaoHibernate()
    {
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.OrderDao#forCustomer(com.pizza73.model.Customer)
     */
    @SuppressWarnings("unchecked")
    public List<Order> forCustomer(OnlineCustomer customer)
    {
        Query query = super.getCurrentSession().createQuery("from Order o where o.customer=:customer")
            .setEntity("customer", customer);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Order> forPhone(String phone)
    {
        Query query = super.getCurrentSession().createQuery("from Order o where o.customer.phone=:phone")
            .setString("phone", phone);
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.OrderDao#lastOrder(com.pizza73.model.OnlineCustomer)
     */
    public Integer lastOrder(OnlineCustomer customer)
    {
        Integer ocId = customer.getId();
        Query query = super
            .getCurrentSession()
            .createQuery(
                "select o.id from Order as o where o.customer.onlineId=:oId "
                    + "and o.timeOrdered = (select max(o2.timeOrdered) " + "from Order o2 where o2.customer.onlineId=:oId)")
            .setInteger("oId", ocId);
        Integer orderId = (Integer) query.uniqueResult();

        return orderId;
    }

    public Order lastOrder(String phone)
    {
        Query query = super
            .getCurrentSession()
            .createQuery(
                "from Order as o where o.customer.phone=:phone " + "and o.timeOrdered = (select max(o2.timeOrdered) "
                    + "from Order o2 where o2.customer.phone=:phone)").setString("phone", phone);
        Order order = (Order) query.uniqueResult();

        return order;
    }

    @SuppressWarnings("unchecked")
    public List<Integer> ordersForBusinessDate(OnlineCustomer oc)
    {
        List<Integer> duplicateOrders = new ArrayList<Integer>();

        Calendar c = this.businessDate();

        String queryString = "select o.id from Order as o where o.businessDate=:bDate and ";
        Query query = null;
        if(oc.getId() != null && oc.getId() > 0)
        {
            queryString += "o.customer.onlineId=:oId";
            query = super.getCurrentSession().createQuery(queryString).setCalendarDate("bDate", c)
                .setInteger("oId", oc.getId());
        }
        else
        {
            // NO ONLINE ACCOUNT SO SEARCH VIA PHONE NUMBER.
            queryString += "o.customer.phone=:phone";
            query = super.getCurrentSession().createQuery(queryString).setCalendarDate("bDate", c)
                .setString("phone", oc.getPhone());
        }

        duplicateOrders = query.list();

        return duplicateOrders;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.OrderDao#findShopForPostalCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public StreetAddress findShopForPostalCode(String postalCode)
    {
        Query query = super.getCurrentSession().createQuery("from StreetAddress as sa where sa.postalCode=:pCode)")
            .setString("pCode", postalCode);
        List<StreetAddress> result = query.list();

        if(result != null && !result.isEmpty())
        {
            return result.get(0);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public StreetAddress findShop(StreetAddress streetAddress)
    {
        Criteria criteria = super.getCurrentSession().createCriteria(StreetAddress.class);

        criteria.add(Restrictions.eq("cityId", streetAddress.getCityId()));
        criteria.add(Restrictions.le("fromAddress", streetAddress.getFromAddress()));
        criteria.add(Restrictions.ge("toAddress", streetAddress.getToAddress()));
        criteria.add(Restrictions.eq("streetName", streetAddress.getStreetName()));
        criteria.add(Restrictions.eq("streetTypeCode", streetAddress.getStreetTypeCode()));
        if(StringUtils.isNotBlank(streetAddress.getSideOfStreet()))
        {
            if(Integer.valueOf(streetAddress.getSideOfStreet()) > 0)
            {
                criteria.add(Restrictions.eq("sideOfStreet", streetAddress.getSideOfStreet()));
            }
        }
        if(StringUtils.isNotBlank(streetAddress.getStreetDirectionCode()))
        {
            criteria.add(Restrictions.eq("streetDirectionCode", streetAddress.getStreetDirectionCode()));
        }

        List<StreetAddress> result = criteria.list();
        if(result != null && !result.isEmpty())
        {
            return result.get(0);
        }

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.OrderDao#nextMonerisId()
     */
    public String nextMonerisId()
    {
        Query query = super.getCurrentSession().createSQLQuery("select nextval('iq2_moneris_id')");
        BigInteger monerisId = (BigInteger) query.list().get(0);
        return monerisId.toString();

    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.OrderDao#save(com.pizza73.model.conversion.Sales)
     */
    public void save(Sales sale)
    {
        super.getCurrentSession().save(sale);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.OrderDao#salesRepeatCount(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Integer salesRepeatCount(String phone)
    {
        Integer repeatCount = 0;
        Query query = super.getCurrentSession().createSQLQuery(
            "select repeat_count from sales s where s.area_code='" + phone.substring(0, 3) + "' and s.phone_number='"
                + phone.substring(3) + "' and s.order_date = ("
                + "select max(s2.order_date) from sales s2 where s2.area_code='" + phone.substring(0, 3)
                + "' and s2.phone_number='" + phone.substring(3) + "')");
        List<Integer> repeatCountList = query.list();
        if(repeatCountList != null && !repeatCountList.isEmpty())
        {
            repeatCount = repeatCountList.get(0);
        }
        return repeatCount;
    }

    @SuppressWarnings("unchecked")
    public List<Shop> shopCountForCity(Municipality city)
    {
        Query query = super.getCurrentSession().createQuery("from Shop s where s.city=:city").setEntity("city", city);
        List<Shop> shops = query.list();

        return shops;
    }

    @SuppressWarnings("unchecked")
    public Long orderCountForBusinessDate(Calendar businessDate)
    {
        Query query = super.getCurrentSession().createQuery("select count(*) from Order where businessDate=:bDate)")
            .setCalendarDate("bDate", businessDate);
        List<Long> count = query.list();

        return count.get(0);
    }

    @SuppressWarnings("unchecked")
    public Long activeOrderCountForBusinessDate(Calendar businessDate)
    {
        Query query = super.getCurrentSession()
            .createQuery("select count(*) from Order where businessDate=:bDate and status!='x')")
            .setCalendarDate("bDate", businessDate);
        List<Long> count = query.list();

        return count.get(0);
    }

    @SuppressWarnings("unchecked")
    public Long onlineOrderSumForBusinessDate(Calendar businessDate)
    {
        Query query = super.getCurrentSession().createQuery("select sum(total) from Order where businessDate=:bDate)")
            .setCalendarDate("bDate", businessDate);
        List<Long> sum = query.list();

        return sum.get(0);
    }

    @SuppressWarnings("unchecked")
    public Long onlineOrderGSTSumForBusinessDate(Calendar businessDate)
    {
        Query query = super.getCurrentSession().createQuery("select sum(gst) from Order where businessDate=:bDate)")
            .setCalendarDate("bDate", businessDate);
        List<Long> sum = query.list();

        return sum.get(0);
    }

    @SuppressWarnings("unchecked")
    public Long onlineOrderDeliverySumForBusinessDate(Calendar businessDate)
    {
        Query query = super.getCurrentSession()
            .createQuery("select sum(deliveryCharge) from Order where businessDate=:bDate)")
            .setCalendarDate("bDate", businessDate);
        List<Long> sum = query.list();

        return sum.get(0);
    }

    @SuppressWarnings("unchecked")
    public Long onlineOrderActiveSumForBusinessDate(Calendar businessDate)
    {
        Query query = super.getCurrentSession()
            .createQuery("select sum(total) from Order where businessDate=:bDate and status !='x')")
            .setCalendarDate("bDate", businessDate);
        List<Long> sum = query.list();

        return sum.get(0);
    }

    @SuppressWarnings("unchecked")
    public Long onlineOrderActiveGSTSumForBusinessDate(Calendar businessDate)
    {
        Query query = super.getCurrentSession()
            .createQuery("select sum(gst) from Order where businessDate=:bDate and status !='x')")
            .setCalendarDate("bDate", businessDate);
        List<Long> sum = query.list();

        return sum.get(0);
    }

    @SuppressWarnings("unchecked")
    public Long onlineOrderActiveDeliverySumForBusinessDate(Calendar businessDate)
    {
        Query query = super.getCurrentSession()
            .createQuery("select sum(deliveryCharge) from Order where businessDate=:bDate and status !='x')")
            .setCalendarDate("bDate", businessDate);
        List<Long> sum = query.list();

        return sum.get(0);
    }

    @SuppressWarnings("unchecked")
    public Long internetOperatorCountForBusinessDate(Calendar businessDate)
    {
        Query query = super.getCurrentSession()
            .createQuery("select count(*) from Sales where orderDate =:bDate and operator = 'ONLINE'")
            .setCalendarDate("bDate", businessDate);
        List<Long> count = query.list();

        return count.get(0);
    }

    @SuppressWarnings("unchecked")
    public Long activeInternetOperatorCountForBusinessDate(Calendar businessDate)
    {
        Query query = super
            .getCurrentSession()
            .createQuery(
                "select count(*) from Sales where orderDate =:bDate and status !='x' and (operator =:online or operator " +
                    "=:mobile)")
            .setCalendarDate("bDate", businessDate).setString("online", OrderOrigin.ONLINE.name())
            .setString("mobile", OrderOrigin.MOBILE.name());
        List<Long> count = query.list();

        return count.get(0);
    }

    @SuppressWarnings("unchecked")
    public Long totalSalesForBusinessDate(Calendar businessDate)
    {
        Query query = super.getCurrentSession()
            .createQuery("select count(*) from Sales where orderDate =:bDate and order_total != 0.00")
            .setCalendarDate("bDate", businessDate);
        List<Long> count = query.list();

        return count.get(0);
    }

    @SuppressWarnings("unchecked")
    public Long totalActiveSalesForBusinessDate(Calendar businessDate)
    {
        Query query = super.getCurrentSession()
            .createQuery("select count(*) from Sales where orderDate =:bDate and order_total != 0.00 and status !='x'")
            .setCalendarDate("bDate", businessDate);
        List<Long> count = query.list();

        return count.get(0);
    }

    @SuppressWarnings("unchecked")
    public BigDecimal totalSalesSumForBusinessDate(Calendar businessDate)
    {
        Query query = super.getCurrentSession()
            .createQuery("select sum(total) from Sales where orderDate =:bDate and order_total != 0.00")
            .setCalendarDate("bDate", businessDate);
        List<BigDecimal> sum = query.list();

        return sum.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Credit> creditForCustomer(OnlineCustomer customer)
    {
        Query query = super.getCurrentSession().createQuery("from Credit as c where c.customerId =:cId")
            .setInteger("cId", customer.getId());
        List<Credit> credits = query.list();

        if(credits == null)
        {
            // try via phone number;
            query = super.getCurrentSession().createQuery("from Credit as c where c.phone =:cId")
                .setString("cId", customer.getPhone());
            credits = query.list();
        }

        return credits;
    }

    public boolean exists(Integer id)
    {
        Order entity = this.get(id);
        return entity != null;
    }

    public Order get(Integer id)
    {
        return (Order) this.get(Order.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Order> getAll()
    {
        return (List<Order>) this.getAll(Order.class);
    }

    public void remove(Integer id)
    {
        this.remove(Order.class, id);
    }

    public Order save(Order o)
    {
        return (Order) super.save(o);
    }

    @SuppressWarnings("unchecked")
    public BigInteger ordersForHour(Calendar day, int hour)
    {
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);

        Calendar start = (Calendar) day.clone();
        start.set(Calendar.HOUR_OF_DAY, hour);
        Calendar end = (Calendar) start.clone();
        end.set(Calendar.HOUR_OF_DAY, hour + 1);
        if(hour >= 0 && hour < 3)
        {
            start.set(Calendar.DATE, day.get(Calendar.DATE) + 1);
            end.set(Calendar.DATE, day.get(Calendar.DATE) + 1);
        }

        Query query = super
            .getCurrentSession()
            .createQuery(
                "select count(*) from Order where businessDate=:bDate "
                    + "and timeOrdered >= :startHour and timeOrdered < :endHour)").setCalendarDate("bDate", day)
            .setCalendar("startHour", start).setCalendar("endHour", end);
        List<Long> count = query.list();

        return BigInteger.valueOf(count.get(0));
    }

    @SuppressWarnings("unchecked")
    public BigInteger totalSalesForHour(Calendar day, int hour)
    {
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);

        Query query = super
            .getCurrentSession()
            .createSQLQuery(
                "select count(*) from sales where order_date=:bDate "
                    + "and order_hour >= :startHour and order_hour < :endHour").setCalendarDate("bDate", day)
            .setInteger("startHour", Integer.valueOf(hour)).setInteger("endHour", Integer.valueOf(hour + 1));
        List<BigInteger> count = query.list();

        return count.get(0);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.dao.OrderDao#totalSalesForHourInDateRange(java.util.Calendar,
     * java.util.Calendar, int)
     */
    @SuppressWarnings("unchecked")
    public BigInteger totalSalesForHourInDateRange(Calendar start, Calendar end, int hour, int shopId)
    {
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        Query query = super
            .getCurrentSession()
            .createSQLQuery(
                "select count(*) from sales where order_date>=:startDate "
                    + "and order_date <=:endDate and shop_id =:shopId and "
                    + "order_hour >= :startHour and order_hour < :endHour").setCalendarDate("startDate", start)
            .setCalendarDate("endDate", end).setInteger("shopId", Integer.valueOf(shopId))
            .setInteger("startHour", Integer.valueOf(hour)).setInteger("endHour", Integer.valueOf(hour + 1));
        List<BigInteger> count = query.list();

        return count.get(0);
    }

    @SuppressWarnings("unchecked")
    public BigDecimal totalSalesDollarForHourInDateRange(Calendar start, Calendar end, int hour, int shopId)
    {
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        Query query = super
            .getCurrentSession()
            .createSQLQuery(
                "select sum(order_total) from sales where order_date>=:startDate "
                    + "and order_date <=:endDate and shop_id =:shopId and "
                    + "order_hour >= :startHour and order_hour < :endHour").setCalendarDate("startDate", start)
            .setCalendarDate("endDate", end).setInteger("shopId", Integer.valueOf(shopId))
            .setInteger("startHour", Integer.valueOf(hour)).setInteger("endHour", Integer.valueOf(hour + 1));
        List<BigDecimal> count = query.list();

        return count.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<StreetAddress> findAllStreetAddressesForPostalCode(String postalCode)
    {
        Query query = super.getCurrentSession().createQuery("from StreetAddress as sa where sa.postalCode=:pCode)")
            .setString("pCode", postalCode);
        return query.list();

    }

    public StreetAddress findAllStreetAddresses(Integer id)
    {
        return (StreetAddress) super.getCurrentSession().createCriteria(StreetAddress.class).add(Restrictions.eq("id", id))
            .uniqueResult();
    }

    public OrderComment pickupComment(Order order)
    {
        Criteria criteria = super.getCurrentSession().createCriteria(OrderComment.class);
        criteria.add(Restrictions.eq("orderId", order.getId()));
        criteria.add(Restrictions.eq("commentType", 4));
        return (OrderComment) criteria.uniqueResult();
    }

    public OrderComment deliveryComment(Order order)
    {
        Criteria criteria = super.getCurrentSession().createCriteria(OrderComment.class);
        criteria.add(Restrictions.eq("orderId", order.getId()));
        criteria.add(Restrictions.eq("commentType", 5));
        return (OrderComment) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<OrderComment> otherComments(Order order)
    {
        Criteria criteria = super.getCurrentSession().createCriteria(OrderComment.class);
        Integer[] indexList = {1, 2, 3, 6};
        criteria.add(Restrictions.eq("orderId", order.getId()));
        criteria.add(Restrictions.in("commentType", indexList));
        criteria.addOrder(org.hibernate.criterion.Order.asc("commentType"));
        return (List<OrderComment>) criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<OrderComment> allComments(Order order)
    {
        Criteria criteria = super.getCurrentSession().createCriteria(OrderComment.class);
        criteria.add(Restrictions.eq("orderId", order.getId()));
        criteria.addOrder(org.hibernate.criterion.Order.asc("commentType"));
        return (List<OrderComment>) criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<String> allPhoneNumberForDate(Calendar businessDate)
    {
        Query query = super
            .getCurrentSession()
            .createQuery(
                "select distinct o.customer.phone from Order as o where o.businessDate =:oDate " + "and o.optOut = false")
            .setCalendarDate("oDate", businessDate);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Order> OrderForPhoneAndDate(String phone, Calendar businessDate)
    {
        Query query = super.getCurrentSession()
            .createQuery("from Order as o where o.businessDate =:oDate " + "and o.customer.phone =:oPhone")
            .setCalendarDate("oDate", businessDate).setString("oPhone", phone);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Order> todayOrders(Integer yesterdayLastId)
    {
        Criteria criteria = super.getCurrentSession().createCriteria(Order.class);
        criteria.add(Restrictions.gt("id", yesterdayLastId));
        return (List<Order>) criteria.list();
    }

    public Order getOrder(Integer Id)
    {
        Criteria criteria = super.getCurrentSession().createCriteria(Order.class);
        criteria.add(Restrictions.eq("id", Id));
        return (Order) criteria.uniqueResult();
    }

    @Override
    public void updateNesteaPromo(String nesteaPromotion)
    {
        Query query = super.getCurrentSession()
            .createSQLQuery("update nestea_promo set count = count + 1 where name =:name")
            .setParameter("name", nesteaPromotion);
        query.executeUpdate();
    }

    @Override
    public String cineplexCode(Integer orderId)
    {
        Query selectQuery = super.getCurrentSession().createSQLQuery(
            "select code from cineplex_promo where id = (select min(id) from cineplex_promo where order_id is null)");
        String promoCode = (String) selectQuery.uniqueResult();
        log.warn("Cineplex promo code: " + promoCode);
        Query query = super.getCurrentSession()
            .createSQLQuery("update cineplex_promo set order_id =:orderId  where code =:code")
            .setParameter("orderId", orderId).setParameter("code", promoCode);
        query.executeUpdate();

        return promoCode;
    }

    @Override
    public SpinToWinPrize prizeForCouponType(Integer couponType)
    {
        Query query = super.getCurrentSession().createQuery("from SpinToWinPrize where id=:couponType")
            .setInteger("couponType", couponType);
        SpinToWinPrize spinToWin = (SpinToWinPrize) query.uniqueResult();

        return spinToWin;
    }

    public Long mobileOrderCountForBusinessDate(Calendar businessDate)
    {
        return orderCountForBusinessDateAndOrigin(businessDate, OrderOrigin.MOBILE);
    }

    public Long justOnlineOrderCountForBusinessDate(Calendar businessDate)
    {
        return orderCountForBusinessDateAndOrigin(businessDate, OrderOrigin.ONLINE);
    }

    @SuppressWarnings("unchecked")
    public Long orderCountForBusinessDateAndOrigin(Calendar businessDate, OrderOrigin origin)
    {
        Query query = super.getCurrentSession()
            .createQuery("select count(*) from Order as o where o.businessDate=:bDate and o.origin=:originCode)")
            .setCalendarDate("bDate", businessDate).setCharacter("originCode", origin.getShortValue());
        List<Long> sum = query.list();

        return sum.get(0);
    }

    @SuppressWarnings("unchecked")
    public Long activeOrderCountForBusinessDateAndOrigin(Calendar businessDate, OrderOrigin origin)
    {
        Query query = super.getCurrentSession()
            .createQuery("select count(*) from Order as o where o.businessDate=:bDate and o.origin=:originCode and o.status!='x')")
            .setCalendarDate("bDate", businessDate).setCharacter("originCode", origin.getShortValue());
        List<Long> sum = query.list();

        return sum.get(0);
    }

    public Boolean countCustomerOrdersForBusinessDayWithCoupon(OnlineCustomer oc, String couponCode)
    {
        List<Integer> duplicateOrders = new ArrayList<Integer>();

        Calendar c = this.businessDate();

        String queryString = "select o.id from Order as o where o.businessDate=:bDate and ";
        Query query = null;
        if(oc.getId() != null && oc.getId() > 0)
        {
            queryString += "o.customer.onlineId=:oId and o.couponCode =:couponCode";
            query = super.getCurrentSession().createQuery(queryString).setCalendarDate("bDate", c)
                .setInteger("oId", oc.getId()).setString("couponCode", couponCode);
        }
        else
        {
            // NO ONLINE ACCOUNT SO SEARCH VIA PHONE NUMBER.
            queryString += "o.customer.phone=:phone and o.couponCode =:couponCode";
            query = super.getCurrentSession().createQuery(queryString).setCalendarDate("bDate", c)
                .setString("phone", oc.getPhone()).setString("couponCode", couponCode);
        }

        duplicateOrders = query.list();

        return !duplicateOrders.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public List<OnlineCustomer> caslOrders(Date startDate, Date endDate)
    {
        List<OnlineCustomer> orders;

        String queryString = "select distinct oc from OnlineCustomer as oc, Order as o where (oc.subscribed = true and o.businessDate >= :startDate and o.businessDate <= :endDate and oc.id = o.customer.onlineId) ";
        Query query = super.getCurrentSession().createQuery(queryString).setDate("startDate", startDate).setDate("endDate", endDate);
        orders = query.list();
        return orders;
    }

    @Override
    public List<OnlineCustomer> expressedConsentUsers(Date startDate)
    {
        List<OnlineCustomer> orders;

        String queryString = "select oc from OnlineCustomer as oc where (oc.createdOn >= :startDate and oc.subscribed = true and oc.optInDate is not null) ";
        Query query = super.getCurrentSession().createQuery(queryString).setDate("startDate", startDate);
        orders = query.list();
        return orders;
    }
}