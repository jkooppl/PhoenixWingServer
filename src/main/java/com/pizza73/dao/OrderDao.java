package com.pizza73.dao;

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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * OrderDao.java TODO comment me
 *
 * @author chris 9-Sep-06
 *
 * @Copyright Flying Pizza 73
 */
public interface OrderDao extends UniversalDao
{


    public List<Order> forCustomer(OnlineCustomer customer);

    public List<Order> forPhone(String phone);

    public Integer lastOrder(OnlineCustomer customer);

    public Order lastOrder(String phone);

    public List<Integer> ordersForBusinessDate(OnlineCustomer oc);

    public StreetAddress findShopForPostalCode(String postalCode);

    public StreetAddress findShop(StreetAddress sa);

    public String nextMonerisId();

    public void save(Sales sales);

    public Integer salesRepeatCount(String phone);

    public List<Shop> shopCountForCity(Municipality city);

    public Long orderCountForBusinessDate(Calendar businessDate);

    public Long activeOrderCountForBusinessDate(Calendar businessDate);

    public Long onlineOrderSumForBusinessDate(Calendar businessDate);

    public Long onlineOrderGSTSumForBusinessDate(Calendar budsinessDate);

    public Long onlineOrderDeliverySumForBusinessDate(Calendar budsinessDate);

    public Long onlineOrderActiveSumForBusinessDate(Calendar businessDate);

    public Long onlineOrderActiveGSTSumForBusinessDate(Calendar budsinessDate);

    public Long onlineOrderActiveDeliverySumForBusinessDate(Calendar budsinessDate);

    public Long internetOperatorCountForBusinessDate(Calendar businessDate);

    public Long activeInternetOperatorCountForBusinessDate(Calendar businessDate);

    public Long totalSalesForBusinessDate(Calendar businessDate);

    public Long totalActiveSalesForBusinessDate(Calendar businessDate);

    public BigDecimal totalSalesSumForBusinessDate(Calendar businessDate);

    public List<Credit> creditForCustomer(OnlineCustomer customer);

    public boolean exists(Integer id);

    public Order get(Integer id);

    public List<Order> getAll();

    public void remove(Integer id);

    public Order save(Order o);

    public BigInteger ordersForHour(Calendar businessDate, int hour);

    public BigInteger totalSalesForHour(Calendar day, int hour);

    public BigInteger totalSalesForHourInDateRange(Calendar start, Calendar end, int hour, int shopId);

    public BigDecimal totalSalesDollarForHourInDateRange(Calendar start, Calendar end, int hour, int shopId);

    public List<StreetAddress> findAllStreetAddressesForPostalCode(String postalCode);

    public StreetAddress findAllStreetAddresses(Integer id);

    public OrderComment pickupComment(Order order);

    public OrderComment deliveryComment(Order order);

    public List<OrderComment> otherComments(Order order);

    public List<OrderComment> allComments(Order order);

    public List<String> allPhoneNumberForDate(Calendar businessDate);

    public List<Order> OrderForPhoneAndDate(String phone, Calendar businessDate);

    public List<Order> todayOrders(Integer yesterdayLastId);

    public Order getOrder(Integer Id);

    public void updateNesteaPromo(String nesteaPromotion);

    public String cineplexCode(Integer orderId);

    public SpinToWinPrize prizeForCouponType(Integer couponType);

    public Long mobileOrderCountForBusinessDate(Calendar businessDate);

    public Long justOnlineOrderCountForBusinessDate(Calendar businessDate);

    public Long orderCountForBusinessDateAndOrigin(Calendar businessDate, OrderOrigin origin);

    public Long activeOrderCountForBusinessDateAndOrigin(Calendar businessDate, OrderOrigin origin);

    public Boolean countCustomerOrdersForBusinessDayWithCoupon(OnlineCustomer customer, String couponCode);
    public List<OnlineCustomer> caslOrders(Date startDate, Date endDate);
    public List<OnlineCustomer> expressedConsentUsers(Date startDate);
}
