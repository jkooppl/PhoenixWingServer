package com.pizza73.service;

import com.pizza73.model.Cart;
import com.pizza73.model.CompleteCart;
import com.pizza73.model.Credit;
import com.pizza73.model.Municipality;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.Order;
import com.pizza73.model.OrderComment;
import com.pizza73.model.Shop;
import com.pizza73.model.StreetAddress;
import com.pizza73.model.UniqueCoupon;
import com.pizza73.model.contest.SpinToWinPrize;
import com.pizza73.model.enums.OrderOrigin;
import com.pizza73.model.enums.OrderStatus;
import com.pizza73.model.report.OrderStats;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * OrderManager.java TODO comment me
 *
 * @author chris 9-Sep-06
 *
 * @Copyright Flying Pizza 73
 */
@Transactional(readOnly = true)
public interface OrderManager extends UniversalManager
{

    @Transactional(readOnly = false)
    public void updateOrder(Order order, OrderStatus status);

    public void cancelOrder(Order order);

    public Order findOrder(Integer id);

    public Order findOrder(String id);

    public List<Order> ordersForCustomer(OnlineCustomer customer);

    public List<Order> ordersForPhone(String phone);

    @Transactional(readOnly = false)
    public Order buildOrder(Cart cart, OnlineCustomer oc, OrderOrigin originatedFrom);//Map<String, String> codeMap,

    public Integer lastOrder(OnlineCustomer customer);

    @Transactional(readOnly = false)
    public void adminUpdate(Order order, UniqueCoupon uc);

    public StreetAddress findShopForAddress(StreetAddress sa);

    public StreetAddress findShopForPostalCode(String postalCode);

    public List<StreetAddress> findAllStreetAddressesForPostalCode(String postalCode);

    public void emailOrderConfirmation(CompleteCart completeCart, String email) throws Exception;

    public List<Credit> creditForCustomer(OnlineCustomer customer);

    @Transactional(readOnly = false)
    public void updateCredits(List<Credit> credits, Integer discount);

    public Map<Calendar, List<OrderStats>> orderStats(Integer days);

    public BigInteger totalSalesForHour(Calendar businessDay, int hour);

    public BigInteger totalSalesForHourInDateRange(Calendar start, Calendar end, int hour, int shopId);

    public BigDecimal totalSalesDollarForHourInDateRange(Calendar startDay, Calendar endDay, int hour, int shopId);

    public Long orderCountForBusinessDate(Calendar businessDate);

    public Long activeOrderCountForBusinessDate(Calendar businessDate);

    public Long internetOperatorCountForBusinessDate(Calendar businessDate);

    public Long totalSalesForBusinessDate(Calendar businessDate);

    public Long totalActiveSalesForBusinessDate(Calendar businessDate);

    public Long onlineOrderSumForBusinessDate(Calendar businessDate);

    public Long onlineOrderActiveSumForBusinessDate(Calendar businessDate);

    public Long onlineOrderGSTSumForBusinessDate(Calendar businessDate);

    public Long onlineOrderActiveGSTSumForBusinessDate(Calendar businessDate);

    public Long onlineOrderDeliverySumForBusinessDate(Calendar businessDate);

    public Long onlineOrderActiveDeliverySumForBusinessDate(Calendar businessDate);

    public OrderComment pickupComment(Order order);

    public OrderComment deliveryComment(Order order);

    public List<OrderComment> otherComments(Order order);

    public List<OrderComment> allComments(Order order);

    public List<String> allPhoneNumberForDate(Calendar businessDate);

    public List<Order> OrderForPhoneAndDate(String phone, Calendar businessDate);

    public StreetAddress findAllStreetAddresses(Integer id);

    public Order getOrder(Integer Id);

    public Long activeInternetOperatorCountForBusinessDate(Calendar businessDate);

    public void updateNesteaPromo(String nesteaPromotion);

    @Transactional(readOnly = false)
    public String cineplexCode(Integer id);

    public SpinToWinPrize prizeForCouponType(Integer couponType);

    public Long mobileOrderCountForBusinessDate(Calendar businessDate);

    public Long justOnlineOrderCountForBusinessDate(Calendar businessDate);

    public Long orderCountForBusinessDateAndOrigin(Calendar businessDate, OrderOrigin origin);

    public Long activeOrderCountForBusinessDateAndOrigin(Calendar businessDate, OrderOrigin origin);

    public Boolean countCustomerOrdersForBusinessDayWithCoupon(OnlineCustomer customer, String couponCode);

    public List<Shop> shopCountForCity(Municipality city);
}