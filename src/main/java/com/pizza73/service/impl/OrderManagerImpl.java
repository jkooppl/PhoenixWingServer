package com.pizza73.service.impl;

import com.pizza73.dao.OrderDao;
import com.pizza73.model.Address;
import com.pizza73.model.Cart;
import com.pizza73.model.CompleteCart;
import com.pizza73.model.Coupon;
import com.pizza73.model.CouponRedemption;
import com.pizza73.model.Credit;
import com.pizza73.model.Location;
import com.pizza73.model.Municipality;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.Order;
import com.pizza73.model.OrderComment;
import com.pizza73.model.OrderCustomer;
import com.pizza73.model.OrderItem;
import com.pizza73.model.ProductConstants;
import com.pizza73.model.Shop;
import com.pizza73.model.StreetAddress;
import com.pizza73.model.UniqueCoupon;
import com.pizza73.model.contest.SpinToWinPrize;
import com.pizza73.model.enums.DeliveryMethod;
import com.pizza73.model.enums.OrderOrigin;
import com.pizza73.model.enums.OrderStatus;
import com.pizza73.model.enums.PaymentMethod;
import com.pizza73.model.enums.ProductCategoryEnum;
import com.pizza73.model.enums.ProductEnum;
import com.pizza73.model.report.OrderStats;
import com.pizza73.service.LookupManager;
import com.pizza73.service.MailEngine;
import com.pizza73.service.OrderManager;
import com.pizza73.util.CityUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * OrderManagerImpl.java TODO comment me
 *
 * @author chris 9-Sep-06
 * @Copyright Flying Pizza 73
 */
@Service("orderManager")
public class OrderManagerImpl extends UniversalManagerImpl implements OrderManager
{
    private static final Logger log = Logger.getLogger(OrderManagerImpl.class);
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private LookupManager lMgr;

    // EMAIL
    @Autowired
    private MailEngine mailEngine;
    @Autowired
    private SimpleMailMessage mailMessage;
    private final String templateName = "orderConfirmation.vm";

    @Value("#{codeMap}")
    private Map<String, String> codeMap;

    private static final String COMMENT = "01:";
    private static final String NOT_MAPPED = "02:";
    private static final String LARGE_ORDER = "03:";
    private static final String DUPLICATE = "04:";
    private static final String SHOP_ERROR = "05:";
    private static final String BLACK_LIST = "07:";
    private static final String SHOP_59 = "59:";
    private static final Integer LARGE_ORDER_COST = Integer.valueOf(10000);

    private static final String PEPSI = "PEPSI";
    private static final String DIET_PEPSI = "DPEPSI";
    private static final String SEVEN_UP = "7UP";
    private static final String ROOT_BEER = "RB";
    private static final String BRISK_ICE_T = "BRISK";

    private static final String COKE = "COKE";
    private static final String DIET_COKE = "DCOKE";
    private static final String COKE_ZERO = "CKZERO";
    private static final String SPRITE = "SPRITE";
    private static final String NESTEA = "NESTEA";
    private static final String GINGER_ALE = "GA";

    private static final String BONELESS_WINGS = "BLWINGS";
    private static final String GIANT_COOKIE = "COOKI";
    private static final String CHK_BITES = "CHKBITES";
    private static final String SHRIMP = "SHRIMP";
    private static final String MM = "MM";
    private static final String BROWNIES = "BROWNIES";
    private static final String PLUM = "PLUM";
    private static final String CHEJA = "CHEJA";
    private static final String THAI = "THAI";
    private static final String RANCH = "RANCH";
    private static final String HOT = "DYNA";
    private static final String SEAFS = "SEAFS";
    private static final String HM = "HM";
    private static final String MARIN = "MARIN";
    private static final String GARLIC = "GARL";
    private static final String MILD = "MILDD";
    private static final String BBQ = "BBQ";
    private static final String BLUCH = "BLUCH";
    private static final String HNGAR = "HNGAR";
    private static final String DILL = "DILL";
    private static final String CHEDI = "CHEDI";
    private static final String SFEDI = "SFEDI";
    private static final String DORITOS = "DORIT";
    private static final String LAYS = "LAYS";
    private static final String MUNCH = "MUNCH";
    private static final String TICKET = "TICKET";
    private static final String PICKUPS = "PICKUPS";
    private static final String BBQSTK = "BBQSTK";
    private static final String BB_VOUCHER = "BBVCH";
    private static final String WWMG = "WWMG";
    private static final String WWMG9 = "9WWMG";
    private static final String WWMG12 = "12WWMG";
    private static final String WWMG14 = "14WWMG";
    private static final String SALAD = "SALAD";
    private static final String HTSA = "HTSA";
    private static final String BACNMLT = "BACNMLT";
    private static final String SOCCER = "SBALL";
    private static final String CINNAMIN_POPPERS = "CINNA";
    private static final String POTATO_WEDGES = "WEDGE";
    private static final String ONION_RINGS = "RINGS";
    private static final String FANTA = "FANTA";
    private static final String CHIP_STK = "CHIPSTK";
    private static final String CHIP_PORK = "CHIPORK";
    private static final String CHIP_CHK = "CHIPCHK";
    private static final String CHIP_WINGS = "CHIPWING";
    private static final String CAKE = "CAKE";
    private static final String FILETS = "FILETS";
    private static final String APPLE_PIE = "PIE";
    private static final String CHILI = "CHILI";
    private static final String FIESTA = "FIESTA";

    private static final String DEBIT = "DEBIT";

    private static final String SHOP_MESSAGE_PREFIX_FOOD = "F:";
    private static final String SHOP_MESSAGE_PREFIX_POP = "P:";
    private static final String SHOP_MESSAGE_PREFIX_SPECIAL = "S:";
    private static final String SHOP_MESSAGE_PREFIX_MISC = "M:";
    private static final String SHOP_MESSAGE_PREFIX_WEATHER = "W:";

    public OrderManagerImpl()
    {
        super.universalDao = this.orderDao;
    }

    @Override
    public Long activeInternetOperatorCountForBusinessDate(final Calendar businessDate)
    {
        return this.orderDao.activeInternetOperatorCountForBusinessDate(businessDate);
    }

    @Override
    public Long activeOrderCountForBusinessDate(final Calendar businessDate)
    {
        return this.orderDao.activeOrderCountForBusinessDate(businessDate);
    }

    @Override
    public void adminUpdate(final Order order, UniqueCoupon uc)
    {
        this.orderDao.update(order);
    }

    @Override
    public List<OrderComment> allComments(final Order order)
    {
        return this.orderDao.allComments(order);
    }

    @Override
    public List<String> allPhoneNumberForDate(final Calendar businessDate)
    {
        return this.orderDao.allPhoneNumberForDate(businessDate);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.OrderManager#buildOrder(com.pizza73.model.Cart)
     */
    @Override
    public Order buildOrder(final Cart cart, final OnlineCustomer oc, OrderOrigin originatedFrom)
    {
        Order order = new Order();

        final Integer saleRepeatCount = this.orderDao.salesRepeatCount(oc.getPhone());
        order.setRepeatCount(saleRepeatCount + 1);

        final Calendar businessDate = this.orderDao.businessDate();
        order.setBusinessDate(businessDate);

        order.setLunchDiscountable(cart.isLunchDiscount());

        final Address address = oc.getAddress();
        final Municipality city = address.getCity();

        if (codeMap != null)
        {
            address.parseAddress(codeMap);
        }

        if (oc.getDelivery().equals(DeliveryMethod.PICK_UP))
        {
            order.setShopId(oc.getShopId());
            order.setDeliveryCharge(0);
        }
        else
        {
            StreetAddress sa = null;
            final StreetAddress saSearch = new StreetAddress(oc.getAddress());
            sa = this.orderDao.findShop(saSearch);

            // MAPPED Address
            if (sa != null)
            {
                order.setShopId(sa.getShopId());
                order.setDeliveryCharge(sa.getDeliveryCharge());
                address.populateStreetAddress();
            }
            // address not mapped.
            else
            {
                Integer shopId = 0;
                Integer deliveryCharge = 350;
                if (StringUtils.isNotBlank(address.getPostalCode()))
                {
                    sa = this.orderDao.findShopForPostalCode(address.getPostalCode().toUpperCase());
                    if (sa != null)
                    {
                        log.warn("USING postal code (" + address.getPostalCode() + ") to map " + "address: " + address.getStreetAddress());
                        shopId = sa.getShopId();
                        deliveryCharge = sa.getDeliveryCharge();
                    }
                }
                // check for single shop for city
                if (shopId == 0)
                {
                    // Municipality city = address.getCity();
                    final List<Shop> shops = this.orderDao.shopCountForCity(city);
                    if (shops != null && shops.size() == 1)
                    {
                        final Shop shop = shops.get(0);
                        shopId = shop.getId();
                        deliveryCharge = shop.getDeliveryCharge();
                        log.warn("SINGLE shop for city: " + city.getName() + " SHOP ID: " + shopId);
                    }
                    else if (CityUtil.is450Delivery(city.getId()))
                    {
                        deliveryCharge = 450;
                    }
                }
                order.setShopId(shopId);
                order.setDeliveryCharge(deliveryCharge);
            }
        }
        if (null == address.getLocationType())
        {
            System.out.println("location code is null setting it to house.");
            Location location = (Location) this.lMgr.get(Location.class, "h");
            oc.getAddress().setLocationType(location);
        }

        order.setCustomer(new OrderCustomer(oc));
        order.getCustomer().setAddress(oc.getAddress());
        if (oc.getId() != null)
        {
            order.getCustomer().setOnlineId(oc.getId());
        }
        else
        {
            order.getCustomer().setOnlineId(0);
        }

        order.setStatus(OrderStatus.UNPRINTED.getShortValue());
        String message = getParkedMessage(cart, oc, order);

        Integer shopId = order.getShopId();
        if (originatedFrom.equals(OrderOrigin.MOBILE))
        {
            if (parkMobileOrder(oc, order))
            {
                order.setStatus(OrderStatus.PARKED.getShortValue());
            }

            if (null != shopId && shopId.equals(Integer.valueOf(59)))
            {
                LocalDateTime elevenPM = new LocalDateTime(businessDate.get(Calendar.YEAR),
                    businessDate.get(Calendar.MONTH) + 1, businessDate.get(Calendar.DATE), 23, 0, 0);

                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(elevenPM))
                {
                    if (cart.getGrandTotal().compareTo(Integer.valueOf(5000)) > 0)
                    {
                        order.setStatus(OrderStatus.PARKED.getShortValue());
                        if (!StringUtils.isBlank(message))
                        {
                            message += " ";
                        }
                        message += SHOP_59;
                    }
                }
            }
        }

        order.setOperatorId(originatedFrom.name());
        order.setOrigin(originatedFrom.getShortValue());

        if (message.length() > 39)
        {
            message = message.substring(0, 39);
        }
        order.setConfidential(message);
        order.setSequence(1);

        order.setDeliveryMethod(oc.getDelivery().getShortValue());
        order.setDriverId(0);
        order.setShopDeliveryId(0);
        order.setShopPickupId(0);
        order.setShopRedirectId(0);
        order.setChangeOrder(0);

        Integer totalDiscount = cart.getTotalDiscount();
        Integer grandTotal = cart.getGrandTotal();
        Integer gst = cart.getGst();
        Integer enviroLevy = cart.getEnviroLevy();
        Integer deposit = cart.getDeposit();

        if(totalDiscount > grandTotal)
        {
            totalDiscount = cart.getTotalPrice();
            grandTotal = 0;
            gst = 0;
            enviroLevy = 0;
            deposit = 0;
        }
        order.setTotal(grandTotal);
        order.setGst(gst);
        order.setDiscountAmount(totalDiscount);
        order.setEnviroLevy(enviroLevy);
        order.setDeposit(deposit);


        order.setPaymentMethod(oc.getPaymentMethod().getShortValue());
        order.setTid(order.getShopId());

        if (oc.isContestOptIn())
        {
            order.setContestOptIn(true);
        }

        order = (Order) save(order);

        for(OrderItem item : cart.getItems())
        {
            item.setOrderId(order.getId());
            saveItems(item);
        }

        buildOrderComment(order, oc);

        // redeemCoupon
        redeemCoupon(cart, oc, order);

        return order;
    }

    private boolean parkMobileOrder(final OnlineCustomer oc, Order order)
    {
        boolean parkMobileOrder = Boolean.parseBoolean(lMgr.infoValueForVariable(ProductConstants.PARK_MOBILE_ORDERS));
        if (parkMobileOrder)
        {
            log.warn("Mobile order will be parked.");
            return true;
        }
        String parkOnlineCustomers = lMgr.infoValueForVariable(ProductConstants.PARK_ONLINE_CUSTOMER);
        String[] customers = parkOnlineCustomers.split(",");
        if (ArrayUtils.contains(customers, oc.getId() + ""))
        {
            log.warn("mobile order for customer with id: " + oc.getId() + " will be parked");
            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.OrderManager#cancelOrder(com.pizza73.model.Order)
     */
    @Override
    public void cancelOrder(final Order order)
    {
        order.setStatus(OrderStatus.CANCELLED.getShortValue());
        save(order);
    }

    @Override
    public String cineplexCode(final Integer orderId)
    {
        return this.orderDao.cineplexCode(orderId);
    }

    @Override
    public List<Credit> creditForCustomer(final OnlineCustomer customer)
    {
        return this.orderDao.creditForCustomer(customer);
    }

    @Override
    public OrderComment deliveryComment(final Order order)
    {
        return this.orderDao.deliveryComment(order);
    }

    @Override
    @Async
    public void emailOrderConfirmation(final CompleteCart completeCart, final String email) throws Exception
    {
        final OnlineCustomer oc = completeCart.getCustomer();
        this.mailMessage.setTo(oc.getName() + "<" + oc.getEmail() + ">");
        this.mailMessage.setSubject("Your Pizza 73 Order Confirmation. (# " + completeCart.getOrderConfirmation() + ")");
        this.mailMessage.setFrom("noReply@pizza73.com");
        final Map<String, Object> model = new HashMap<String, Object>();

        model.put("EMAIL_COMPLETE_CART", email);
        model.put("DELIVERY_DETAILS", completeCart.getDeliveryDetails());
        model.put("CONFIRMATION_NUMBER", completeCart.getOrderConfirmation());
        model.put("ORDER_COMMENT", buildOrderComments(oc));
        model.put("APPLICATION_URL", "http://www.pizza73.com");

        this.mailEngine.sendOrderMessage(this.mailMessage, this.templateName, model);

    }

    @Override
    public StreetAddress findAllStreetAddresses(final Integer id)
    {
        return this.orderDao.findAllStreetAddresses(id);
    }

    @Override
    public List<StreetAddress> findAllStreetAddressesForPostalCode(final String postalCode)
    {
        return this.orderDao.findAllStreetAddressesForPostalCode(postalCode);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.OrderManager#findOrder(java.lang.Integer)
     */
    @Override
    public Order findOrder(final Integer id)
    {
        final Order order = this.orderDao.get(id);

        if (order == null)
        {
            log.warn("order '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(Order.class, id);
        }

        return order;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.OrderManager#findOrder(java.lang.String)
     */
    @Override
    public Order findOrder(final String id)
    {
        return this.findOrder(Integer.valueOf(id));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.OrderManager#findShopForAddress(com.pizza73.model.
     * StreetAddress)
     */
    @Override
    public StreetAddress findShopForAddress(final StreetAddress sa)
    {
        return this.orderDao.findShop(sa);
    }

    @Override
    public StreetAddress findShopForPostalCode(final String postalCode)
    {
        return this.orderDao.findShopForPostalCode(postalCode);
    }

    @Override
    public Order getOrder(final Integer Id)
    {
        return this.orderDao.getOrder(Id);
    }

    @Override
    public Long internetOperatorCountForBusinessDate(final Calendar businessDate)
    {
        return this.orderDao.internetOperatorCountForBusinessDate(businessDate);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.OrderManager#lastOrder(com.pizza73.model.OnlineCustomer
     * )
     */
    @Override
    public Integer lastOrder(final OnlineCustomer customer)
    {
        return this.orderDao.lastOrder(customer);
    }

    @Override
    public Long onlineOrderActiveDeliverySumForBusinessDate(final Calendar businessDate)
    {
        return this.orderDao.onlineOrderActiveDeliverySumForBusinessDate(businessDate);
    }

    @Override
    public Long onlineOrderActiveGSTSumForBusinessDate(final Calendar businessDate)
    {
        return this.orderDao.onlineOrderActiveGSTSumForBusinessDate(businessDate);
    }

    @Override
    public Long onlineOrderActiveSumForBusinessDate(final Calendar businessDate)
    {
        return this.orderDao.onlineOrderActiveSumForBusinessDate(businessDate);
    }

    @Override
    public Long onlineOrderDeliverySumForBusinessDate(final Calendar businessDate)
    {
        return this.orderDao.onlineOrderDeliverySumForBusinessDate(businessDate);
    }

    @Override
    public Long onlineOrderGSTSumForBusinessDate(final Calendar businessDate)
    {
        return this.orderDao.onlineOrderGSTSumForBusinessDate(businessDate);
    }

    @Override
    public Long onlineOrderSumForBusinessDate(final Calendar businessDate)
    {
        return this.orderDao.onlineOrderSumForBusinessDate(businessDate);
    }

    @Override
    public Long orderCountForBusinessDate(final Calendar businessDate)
    {
        return this.orderDao.orderCountForBusinessDate(businessDate);
    }

    @Override
    public List<Order> OrderForPhoneAndDate(final String phone, final Calendar businessDate)
    {
        return this.orderDao.OrderForPhoneAndDate(phone, businessDate);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.OrderManager#ordersForCustomer(com.pizza73.model.
     * Customer )
     */
    @Override
    public List<Order> ordersForCustomer(final OnlineCustomer customer)
    {
        return this.orderDao.forCustomer(customer);
    }

    @Override
    public List<Order> ordersForPhone(final String phone)
    {
        return this.orderDao.forPhone(phone);
    }

    @Override
    public Map<Calendar, List<OrderStats>> orderStats(final Integer days)
    {
        final Map<Calendar, List<OrderStats>> statsMap = new TreeMap<Calendar, List<OrderStats>>();

        final Calendar businessDate = businessDate();

        final Integer[] earlyDays = new Integer[] { 1, 6, 7 };
        int openingHour = 16;
        final int closingHour = 27;

        final Calendar start = (Calendar) businessDate.clone();
        start.add(Calendar.DATE, Integer.valueOf("-" + days));

        int hour = openingHour;

        while (start.compareTo(businessDate) < 1)
        {
            boolean checkForWeekendHours = false;
            if (!checkForWeekendHours)
            {
                final int businessDayOfWeek = start.get(Calendar.DAY_OF_WEEK);
                Arrays.sort(earlyDays);
                if (Arrays.binarySearch(earlyDays, businessDayOfWeek) >= 0)
                {
                    openingHour = 11;
                    // if(businessDayOfWeek == Calendar.FRIDAY
                    // || businessDayOfWeek == Calendar.SATURDAY)
                    // {
                    // closingHour = closingHour + 3;
                    // }
                }
                checkForWeekendHours = true;
            }
            hour = openingHour;
            OrderStats stat = null;
            final List<OrderStats> stats = new ArrayList<OrderStats>();
            for (; hour < closingHour; hour++)
            {
                final int h = hour % 24;
                final BigInteger ordersForHour = this.orderDao.ordersForHour(start, h);
                final BigInteger totalOrders = this.orderDao.totalSalesForHour(start, hour);
                BigDecimal ratio = BigDecimal.ZERO;
                try
                {
                    Double.valueOf(totalOrders.doubleValue());
                    final double ratiod = ordersForHour.doubleValue() / totalOrders.doubleValue();
                    ratio = new BigDecimal(ratiod, new MathContext(4));
                }
                catch (final NumberFormatException e)
                {
                    System.out.println(e.getMessage());
                }

                stat = new OrderStats(h, ordersForHour, totalOrders, ratio);
                stats.add(stat);
            }
            statsMap.put((Calendar) start.clone(), stats);
            start.add(Calendar.DAY_OF_YEAR, 1);
            // reset hours
            openingHour = 16;
            // closingHour = 24;
        }

        return statsMap;
    }

    @Override
    public List<OrderComment> otherComments(final Order order)
    {
        return this.orderDao.otherComments(order);
    }

    @Override
    public OrderComment pickupComment(final Order order)
    {
        return this.orderDao.pickupComment(order);
    }

    @Override
    public Long totalActiveSalesForBusinessDate(final Calendar businessDate)
    {
        return this.orderDao.totalActiveSalesForBusinessDate(businessDate);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.OrderManager#totalSalesDollarForHourInDateRange(java
     * .util.Calendar, java.util.Calendar, int, int)
     */
    @Override
    public BigDecimal totalSalesDollarForHourInDateRange(final Calendar startDay, final Calendar endDay, final int hour,
        final int shopId)
    {
        return this.orderDao.totalSalesDollarForHourInDateRange(startDay, endDay, hour, shopId);
    }

    @Override
    public Long totalSalesForBusinessDate(final Calendar businessDate)
    {
        return this.orderDao.totalSalesForBusinessDate(businessDate);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.OrderManager#ordersForTimeFrame(java.sql.Date,
     * java.sql.Date)
     */
    @Override
    public BigInteger totalSalesForHour(final Calendar businessDay, final int hour)
    {
        return this.orderDao.totalSalesForHour(businessDay, hour);
    }

    @Override
    public BigInteger totalSalesForHourInDateRange(final Calendar start, final Calendar end, final int hour, final int shopId)
    {
        return this.orderDao.totalSalesForHourInDateRange(start, end, hour, shopId);
    }

    @Override
    public void updateCredits(final List<Credit> credits, Integer discount)
    {
        final Iterator<Credit> iter = credits.iterator();
        Credit credit = null;
        while (iter.hasNext())
        {
            credit = iter.next();
            final Integer amount = credit.getAmount();
            if (discount > amount)
            {
                credit.setAmount(0);
                discount = discount - amount;
            }
            else
            {
                credit.setAmount(amount - discount);
                break;
            }
            this.lMgr.save(credit);
        }
    }

    @Override
    public void updateNesteaPromo(final String nesteaPromotion)
    {
        this.orderDao.updateNesteaPromo(nesteaPromotion);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.OrderManager#updateOrder(com.pizza73.model.Order,
     * com.pizza73.model.enums.OrderStatus)
     */
    @Override
    public void updateOrder(final Order order, final OrderStatus status)
    {
        order.setStatus(status.getShortValue());
        save(order);
    }

    private void buildOrderComment(final Order order, final OnlineCustomer oc)
    {
        OrderComment orderComment = null;
        if (oc.isCommentWellDone())
        {
            orderComment = new OrderComment();
            orderComment.setOrderId(order.getId());
            orderComment.setCommentType(1);
            orderComment.setContent("Well done");
            this.lMgr.save(orderComment);
        }
        if (oc.isCommentEasySauce())
        {
            orderComment = new OrderComment();
            orderComment.setOrderId(order.getId());
            orderComment.setCommentType(2);
            orderComment.setContent("Easy sauce");
            this.lMgr.save(orderComment);
        }
        if (oc.isCommentEasyCheese())
        {
            orderComment = new OrderComment();
            orderComment.setOrderId(order.getId());
            orderComment.setCommentType(3);
            orderComment.setContent("Easy cheese");
            this.lMgr.save(orderComment);
        }
        if (oc.isCommentPickupTime())
        {
            orderComment = new OrderComment();
            orderComment.setOrderId(order.getId());
            orderComment.setCommentType(4);
            orderComment.setContent("PU @ " + StringUtils.trimToEmpty(oc.getCommentPickupTimeContent()));
            this.lMgr.save(orderComment);
        }
        if (oc.isCommentDeliveryTime())
        {
            orderComment = new OrderComment();
            orderComment.setOrderId(order.getId());
            orderComment.setCommentType(5);
            orderComment.setContent("DEL @ " + StringUtils.trimToEmpty(oc.getCommentDeliveryTimeContent()));
            this.lMgr.save(orderComment);
        }
        if (oc.isCommentRemoveTopping())
        {
            orderComment = new OrderComment();
            orderComment.setOrderId(order.getId());
            orderComment.setCommentType(6);
            orderComment.setContent("No " + reomveToppingContentPerser(oc.getCommentRemoveToppingContent()));
            this.lMgr.save(orderComment);
        }
    }

    private String buildOrderComments(final OnlineCustomer oc)
    {
        String comments = "";
        if (oc.isCommentWellDone())
            comments += "Well done\n";
        if (oc.isCommentEasySauce())
            comments += "Easy sauce\n";
        if (oc.isCommentEasyCheese())
            comments += "Easy cheese\n";
        if (oc.isCommentPickupTime())
            comments += "Pick up at " + oc.getCommentPickupTimeContent() + "\n";
        if (oc.isCommentDeliveryTime())
            comments += "Delivery at " + oc.getCommentDeliveryTimeContent() + "\n";
        if (oc.isCommentRemoveTopping())
            comments += "Remove topping: " + oc.getCommentRemoveToppingContent() + "\n";
        return comments;
    }

    private boolean checkFor12WWMGOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + WWMG12), ' '))))
            return false;
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.CRUST);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 328)
                {
                    if (extraItem.getParent() != null && extraItem.getParent().getSize().getId() == 2)
                    {
                        park = true;
                        break;
                    }
                }
            }
        }
        return park;
    }

    private boolean checkFor14WWMGOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + WWMG14), ' '))))
            return false;
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.CRUST);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 328)
                {
                    if (extraItem.getParent() != null && extraItem.getParent().getSize().getId() == 3)
                    {
                        park = true;
                        break;
                    }
                }
            }
        }
        return park;
    }

    private boolean checkFor9WWMGOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + WWMG9), ' '))))
            return false;
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.CRUST);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 328)
                {
                    if (extraItem.getParent() != null && extraItem.getParent().getSize().getId() == 1)
                    {
                        park = true;
                        break;
                    }
                }
            }
        }
        return park;
    }

    private boolean checkForBACNMLTOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + BACNMLT), ' '))))
            return false;
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.PIZZA);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 355)
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkForChiliFiestaPizzaOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + FIESTA), ' '))))
        {
            return false;
        }
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.PIZZA);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (ProductEnum.CHILI_FIESTA_PIZZA.isEqualToProductId(extraId))
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkForBBQSTKOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + BBQSTK), ' '))))
            return false;
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.PIZZA);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 321)
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkForBonelessWingsOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + BONELESS_WINGS), ' '))))
            return false;
        boolean park = false;
        Set<OrderItem> wingOrders = null;
        final Set<OrderItem> wingMeals = cart.itemsForCategory(ProductCategoryEnum.WING_MEAL);
        // final Set<OrderItem> wingBoxs =
        // cart.itemsForCategory(ProductCategoryEnum.WING_BOX);
        final Set<OrderItem> bonelessBoxes = cart.itemsForCategory(ProductCategoryEnum.CHICKEN_BITES);
        final Set<OrderItem> wingSides = cart.itemsForCategory(ProductCategoryEnum.WING_SIDE);
        Iterator<OrderItem> iter = null;
        OrderItem wingItem = null;
        Integer wingId = null;

        if (wingMeals != null && !wingMeals.isEmpty())
        {
            wingOrders = wingMeals;
        }
        if (bonelessBoxes != null && !bonelessBoxes.isEmpty())
        {
            if (wingOrders == null)
                wingOrders = bonelessBoxes;
            else
                wingOrders.addAll(bonelessBoxes);
        }
        if (wingSides != null && !wingSides.isEmpty())
        {
            if (wingOrders == null)
                wingOrders = wingSides;
            else
                wingOrders.addAll(wingSides);
        }
        if (wingOrders != null && !wingOrders.isEmpty())
        {
            iter = wingOrders.iterator();
            while (iter.hasNext())
            {
                wingItem = iter.next();
                wingId = wingItem.getProduct().getId();
                if (wingId == 244 || wingId == 245 || wingId == 246 || wingId == 247)
                {
                    park = true;
                    break;
                }
            }
        }

        return park;
    }

    private boolean checkForCheddarDipOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + CHEJA), ' '))))
            return false;
        boolean park = false;
        Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.WING_DIP);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 290)
                {
                    park = true;
                    break;
                }
            }
        }

        return park;
    }

    private boolean checkForChkBitesOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + CHK_BITES), ' '))))
            return false;
        boolean park = false;
        Iterator<OrderItem> iter = null;
        Set<OrderItem> wingOrders = null;

        final Set<OrderItem> wingMeals = cart.itemsForCategory(ProductCategoryEnum.WING_MEAL);
        final Set<OrderItem> wingBoxs = cart.itemsForCategory(ProductCategoryEnum.CHICKEN_BITES);
        final Set<OrderItem> wingSides = cart.itemsForCategory(ProductCategoryEnum.WING_SIDE);
        OrderItem wingItem = null;
        Integer wingId = null;
        if (park != true)
        {
            if (wingMeals != null && !wingMeals.isEmpty())
                wingOrders = wingMeals;
            if (wingBoxs != null && !wingBoxs.isEmpty())
            {
                if (wingOrders == null)
                    wingOrders = wingBoxs;
                else
                    wingOrders.addAll(wingBoxs);
            }
            if (wingSides != null && !wingSides.isEmpty())
            {
                if (wingOrders == null)
                    wingOrders = wingSides;
                else
                    wingOrders.addAll(wingSides);
            }
            if (wingOrders != null && !wingOrders.isEmpty())
            {
                iter = wingOrders.iterator();
                while (iter.hasNext())
                {
                    wingItem = iter.next();
                    wingId = wingItem.getProduct().getId();
                    if (wingId == 244 || wingId == 245 || wingId == 246 || wingId == 247)
                    {
                        park = true;
                        break;
                    }
                }
            }
        }
        return park;
    }

    private boolean checkForCreamyDillDipOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + DILL), ' '))))
            return false;
        boolean park = false;
        Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.WING_DIP);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 348)
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkForGarlicDipOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + GARLIC), ' '))))
            return false;
        boolean park = false;
        Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.WING_DIP);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 268)
                {
                    park = true;
                    break;
                }
            }
        }

        return park;
    }

    private boolean checkForApplePieOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        String outageString = StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + APPLE_PIE), ' ');
        if (!temp1.contains(outageString))
        {
            return false;
        }
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.EXTRA);
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            for(OrderItem extraItem : extras)
            {
                extraId = extraItem.getProduct().getId();
                if (ProductEnum.APPLE_PIE.isEqualToProductId(extraId))
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkForChiliOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        String outageString = StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + CHILI), ' ');
        if (!temp1.contains(outageString))
        {
            return false;
        }
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.EXTRA);
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            for(OrderItem extraItem : extras)
            {
                extraId = extraItem.getProduct().getId();
                if (ProductEnum.CHILLI.isEqualToProductId(extraId))
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkForDoritosOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + DORITOS), ' '))))
            return false;
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.EXTRA);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 307)
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkForGiantCookieOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + GIANT_COOKIE), ' '))))
            return false;
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.EXTRA);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        String extraName = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraName = extraItem.getProduct().getPrintableName().trim().toLowerCase();
                if (extraName.contains("cookie"))
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkForHMDipOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + HM), ' '))))
            return false;
        boolean park = false;
        Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.WING_DIP);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 292)
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkForHotDipOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + HOT), ' '))))
            return false;
        boolean park = false;
        Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.WING_DIP);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 293)
                {
                    park = true;
                    break;
                }
            }
        }

        return park;
    }

    private boolean checkForHTSAOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + HTSA), ' '))))
            return false;
        boolean park = false;
        Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.TOPPING);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 356)
                {
                    if (extraItem.getParent() != null)
                    {
                        park = true;
                        return park;
                    }
                }
            }
        }
        extras = cart.itemsForCategory(ProductCategoryEnum.PIZZA);
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 119)
                {
                    park = true;
                    return park;
                }
            }
        }

        return park;
    }

    private boolean checkForlaysOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + LAYS), ' '))))
            return false;
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.EXTRA);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 306)
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkForMarinDipOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + MARIN), ' '))))
            return false;
        boolean park = false;
        Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.WING_DIP);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 294)
                {
                    park = true;
                    break;
                }
            }
        }

        return park;
    }

    private boolean checkForPlumDipOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + PLUM), ' '))))
            return false;
        boolean park = false;
        Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.WING_DIP);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 295)
                {
                    park = true;
                    break;
                }
            }
        }

        return park;
    }

    private boolean checkForPopProductOutage(final Cart cart, final String shopMessage)
    {
        boolean park = false;
        final Set<OrderItem> softDrinks = cart.itemsForCategory(ProductCategoryEnum.POP);
        if (!softDrinks.isEmpty())
        {
            // check to see if there is any other shop message
            // if there is we still have to park the order.
            if (shopMessage.contains(COKE) || shopMessage.contains(DIET_COKE) || shopMessage.contains(COKE_ZERO)
                || shopMessage.contains(SPRITE) || shopMessage.contains(NESTEA) || shopMessage.contains(GINGER_ALE)
                || shopMessage.contains(ROOT_BEER) || shopMessage.contains(PEPSI) || shopMessage.contains(DIET_PEPSI)
                || shopMessage.contains(SEVEN_UP) || shopMessage.contains(BRISK_ICE_T))
            {
                park = false;
                final Iterator<OrderItem> iter = softDrinks.iterator();
                while (iter.hasNext())
                {
                    final OrderItem drinkItem = iter.next();
                    String drinkName = drinkItem.getProduct().getName().trim().toUpperCase();
                    final Integer sizeId = drinkItem.getSize().getId();
                    final Integer productId = drinkItem.getProduct().getId();
                    if (drinkName.equalsIgnoreCase("7-Up"))
                    {
                        drinkName = SEVEN_UP;
                    }
                    else if (drinkName.equalsIgnoreCase("Mug R/B"))
                    {
                        drinkName = ROOT_BEER;
                    }
                    else if (productId == 254)
                    {
                        drinkName = COKE;
                    }
                    else if (productId == 255)
                    {
                        drinkName = DIET_COKE;
                    }
                    else if (productId == 256)
                    {
                        drinkName = SPRITE;
                    }
                    else if (productId == 270)
                    {
                        drinkName = COKE_ZERO;
                    }
                    else if (productId == 257)
                    {
                        drinkName = NESTEA;
                    }
                    else if (productId == 258)
                    {
                        drinkName = ROOT_BEER;
                    }
                    else if (productId == 271)
                    {
                        drinkName = GINGER_ALE;
                    }
                    if (shopMessage.contains("2L-") && sizeId == 3
                        && shopMessage.substring(shopMessage.indexOf("2L-")).contains("-" + drinkName))
                    {
                        if (shopMessage.substring(shopMessage.indexOf("2L-")).contains(":") == false)
                        {
                            park = true;
                            break;
                        }
                        else if (shopMessage.substring(shopMessage.indexOf("2L-"),
                            shopMessage.substring(shopMessage.indexOf("2L-")).indexOf(":")).contains("-" + drinkName))
                        {
                            park = true;
                            break;
                        }
                    }
                    if (shopMessage.contains("CAN-") && (sizeId == 1 || sizeId == 2)
                        && shopMessage.substring(shopMessage.indexOf("CAN-")).contains("-" + drinkName))
                    {
                        if (shopMessage.substring(shopMessage.indexOf("CAN-")).contains(":") == false)
                        {
                            park = true;
                            break;
                        }
                        else if (shopMessage.substring(shopMessage.indexOf("CAN-"),
                            shopMessage.substring(shopMessage.indexOf("CAN-")).indexOf(":")).contains("-" + drinkName))
                        {
                            park = true;
                            break;
                        }
                    }
                    // if(shopMessage.contains("2L-" + drinkName)
                    // || shopMessage.contains("CAN-" + drinkName))
                    // {
                    // park = true;
                    // break;
                    // }
                }
            }
        }
        return park;
    }

    private boolean checkForRanchDipOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + RANCH), ' '))))
            return false;
        boolean park = false;
        Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.WING_DIP);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 291)
                {
                    park = true;
                    break;
                }
            }
        }

        return park;
    }

    private boolean checkForSaladOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + SALAD), ' '))))
            return false;
        boolean park = false;
        final Set<OrderItem> salads = cart.itemsForCategory(ProductCategoryEnum.SALAD);
        Integer extraId = null;
        for (OrderItem item : salads)
        {
            extraId = item.getProduct().getId();
            if (extraId == 15 || extraId == 189)
            {
                park = true;
                break;
            }
        }
        return park;
    }

    private boolean checkForSeafoodSouceOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + SEAFS), ' '))))
            return false;
        boolean park = false;
        Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.WING_DIP);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 269)
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkForShrimpOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + SHRIMP), ' '))))
            return false;
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.SHRIMP);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 264 || extraId == 324)
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkForThaiDipOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + THAI), ' '))))
            return false;
        boolean park = false;
        Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.WING_DIP);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 267)
                {
                    park = true;
                    break;
                }
            }
        }

        return park;
    }

    private boolean checkForTicketOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_MISC + TICKET), ' '))))
            return false;
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.EXTRA);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 285)
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkChipotleOutage(Cart cart, String shopMessageOne, String shopMessageTwo)
    {
        final String shopMessage = StringUtils.remove(shopMessageOne + shopMessageTwo, ' ');
        final String outageMessage = StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + CHIP_CHK + SHOP_MESSAGE_PREFIX_FOOD
            + CHIP_PORK + SHOP_MESSAGE_PREFIX_FOOD + CHIP_STK + SHOP_MESSAGE_PREFIX_FOOD + CHIP_WINGS), ' ');
        if (!outageMessage.contains(shopMessage))
        {
            return false;
        }
        boolean park = cart.hasProducts(ProductConstants.CHIPOTLE_OUTAGES);

        return park;
    }

    private boolean checkFiltetsOutage(Cart cart, String shopMessageOne, String shopMessageTwo)
    {
        final String shopMessage = StringUtils.remove(shopMessageOne + shopMessageTwo, ' ');
        final String outageMessage = StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + FILETS), ' ');
        if (!outageMessage.contains(shopMessage))
        {
            return false;
        }
        boolean park = cart.hasProduct(ProductEnum.CHICKEN_FILLETS.getId());

        return park;
    }

    private boolean checkForWWMGOutage(final Cart cart, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_FOOD + WWMG), ' '))))
            return false;
        boolean park = false;
        final Set<OrderItem> extras = cart.itemsForCategory(ProductCategoryEnum.CRUST);
        Iterator<OrderItem> iter = null;
        OrderItem extraItem = null;
        Integer extraId = null;
        if (extras != null & !extras.isEmpty())
        {
            iter = extras.iterator();
            while (iter.hasNext())
            {
                extraItem = iter.next();
                extraId = extraItem.getProduct().getId();
                if (extraId == 328)
                {
                    park = true;
                    break;
                }
            }
        }
        return park;
    }

    private boolean checkOtherFoodsAndMessages(final Cart cart, final String shopMessage, final String shopMessage2,
        final String stringForBonless, final String stringForGiantCookie, final String stringForChkbites,
        final String stringForShrimp, final String stringForMM, final String stringForBrownies,
        final String stringForGarlicDip, final String stringForDebit, final String stringForPlum, final String string1,
        final String string2, final String string3, final String string4, final String string5, final String string6,
        final String string7, final String string8, final String string9, final String string10, final String string11,
        final String string12, final String string13, final String string14, final String string15, final String string16,
        final String string17, final String string18, final String string19, final String string20, final String string21,
        final String string22, final String string23, final String string24, final String string25, final String string26,
        final String string27, final String string28, final String string29, final String string30, final String string31,
        final String string34, final String string35, final String string36, final String string37, final String string32,
        final String string33, final String cake, final String filets, final String applePie)
    {
        String temp = StringUtils.remove(shopMessage + shopMessage2, ' ');
        temp = StringUtils.remove(temp, stringForBonless);
        temp = StringUtils.remove(temp, stringForGiantCookie);
        temp = StringUtils.remove(temp, stringForChkbites);
        temp = StringUtils.remove(temp, stringForShrimp);
        temp = StringUtils.remove(temp, stringForMM);
        temp = StringUtils.remove(temp, stringForBrownies);
        temp = StringUtils.remove(temp, stringForGarlicDip);
        temp = StringUtils.remove(temp, stringForDebit);
        temp = StringUtils.remove(temp, stringForPlum);
        temp = StringUtils.remove(temp, string1);
        temp = StringUtils.remove(temp, string2);
        temp = StringUtils.remove(temp, string3);
        temp = StringUtils.remove(temp, string4);
        temp = StringUtils.remove(temp, string5);
        temp = StringUtils.remove(temp, string6);
        temp = StringUtils.remove(temp, string7);
        temp = StringUtils.remove(temp, string8);
        temp = StringUtils.remove(temp, string9);
        temp = StringUtils.remove(temp, string10);
        temp = StringUtils.remove(temp, string11);
        temp = StringUtils.remove(temp, string12);
        temp = StringUtils.remove(temp, string13);
        temp = StringUtils.remove(temp, string14);
        temp = StringUtils.remove(temp, string15);
        temp = StringUtils.remove(temp, string16);
        temp = StringUtils.remove(temp, string17);
        temp = StringUtils.remove(temp, string18);
        temp = StringUtils.remove(temp, string19);
        temp = StringUtils.remove(temp, string20);
        temp = StringUtils.remove(temp, string21);
        temp = StringUtils.remove(temp, string22);
        temp = StringUtils.remove(temp, string23);
        temp = StringUtils.remove(temp, string24);
        temp = StringUtils.remove(temp, string25);
        temp = StringUtils.remove(temp, string26);
        temp = StringUtils.remove(temp, string27);
        temp = StringUtils.remove(temp, string28);
        temp = StringUtils.remove(temp, string29);
        temp = StringUtils.remove(temp, string30);
        temp = StringUtils.remove(temp, string31);
        temp = StringUtils.remove(temp, string32);
        temp = StringUtils.remove(temp, string33);
        temp = StringUtils.remove(temp, string34);
        temp = StringUtils.remove(temp, string35);
        temp = StringUtils.remove(temp, string36);
        temp = StringUtils.remove(temp, string37);
        temp = StringUtils.remove(temp, cake);
        temp = StringUtils.remove(temp, filets);
        temp = StringUtils.remove(temp, applePie);
        if (temp == null)
        {
            return false;
        }
        else
        {
            return (temp.contains(SHOP_MESSAGE_PREFIX_FOOD) || temp.contains(SHOP_MESSAGE_PREFIX_MISC));
        }
    }

    private boolean checkPaymentMessage(final OnlineCustomer oc, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_MISC + DEBIT), ' '))))
            return false;
        boolean park = false;
        if (oc.getPaymentMethod().getShortValue() == PaymentMethod.DEBIT.getShortValue()
            || oc.getPaymentMethod().getShortValue() == PaymentMethod.PIZZACARD.getShortValue())
            park = true;
        return park;
    }

    private boolean checkPickupMessage(final OnlineCustomer oc, final String shopMessage, final String shopMessage2)
    {
        final String temp1 = StringUtils.remove(shopMessage + shopMessage2, ' ');
        if (!temp1.contains((StringUtils.remove((SHOP_MESSAGE_PREFIX_MISC + PICKUPS), ' '))))
            return false;
        boolean park = false;
        if (oc.getDelivery().getShortValue() == DeliveryMethod.PICK_UP.getShortValue())
            park = true;
        return park;
    }

    /**
     * Determine whether order needs to be parked or not for the following
     * reasons: COMMENT = "01"; NOT_MAPPED = "02"; LARGE_ORDER = "03"; DUPLICATE
     * = "04"; SHOP_ERROR = "05"; SMALL_ORDER = "06";
     *
     * @param cart
     * @param oc
     * @param order
     * @return
     */
    private String getParkedMessage(final Cart cart, final OnlineCustomer oc, final Order order)
    {
        String message = "";
        if (order.getShopId() == null || order.getShopId().equals(0))
        {
            if (order.getShopId() == null)
            {
                order.setShopId(0);
            }
            Integer deliveryCharge = 350;
            if (CityUtil.is450Delivery(oc.getAddress().getCity().getId()))
            {
                deliveryCharge = 450;
            }
            order.setDeliveryCharge(deliveryCharge);
            order.setStatus(OrderStatus.PARKED.getShortValue());
            message = " " + NOT_MAPPED;
        }
        if (cart.getTaxableTotal() > LARGE_ORDER_COST)
        {
            order.setStatus(OrderStatus.PARKED.getShortValue());
            message += " " + LARGE_ORDER;
        }

        if (this.lMgr.isBlacklisted(order.getCustomer().getPhone()))
        {
            order.setStatus(OrderStatus.PARKED.getShortValue());
            message += " " + BLACK_LIST;
        }
        if (order.getShopId() > 0)
        {
            final Shop shop = (Shop) this.lMgr.get(Shop.class, order.getShopId());
            final String shopMessage = StringUtils.trimToEmpty(shop.getMessage());
            if (!shopMessage.equals("X"))
            {
                boolean park = true;
                final String shopMessageTwo = StringUtils.trimToEmpty(shop.getMessageTwo());
                final String shopMessageCat = shopMessage + " " + shopMessageTwo;
                // contains correct data format
                if (shopMessageCat.contains(SHOP_MESSAGE_PREFIX_FOOD) || shopMessageCat.contains(SHOP_MESSAGE_PREFIX_MISC)
                    || shopMessageCat.contains(SHOP_MESSAGE_PREFIX_WEATHER)
                    || shopMessageCat.contains(SHOP_MESSAGE_PREFIX_SPECIAL)
                    || shopMessageCat.contains(SHOP_MESSAGE_PREFIX_POP))
                {
                    park = false;
                    // check for foods and messages
                    if (shopMessageCat.contains(SHOP_MESSAGE_PREFIX_FOOD)
                        || shopMessageCat.contains(SHOP_MESSAGE_PREFIX_MISC))
                    {
                        String sMessage = StringUtils.remove(shopMessage + shopMessageTwo, ' ');

                        if (checkForBBQSTKOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForlaysOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForDoritosOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForTicketOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForBonelessWingsOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForGiantCookieOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForChkBitesOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForShrimpOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkPaymentMessage(oc, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForPlumDipOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForCheddarDipOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForRanchDipOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForHMDipOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForHotDipOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForMarinDipOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForThaiDipOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForSeafoodSouceOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForGarlicDipOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForCreamyDillDipOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForWWMGOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkFor9WWMGOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkFor12WWMGOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkFor14WWMGOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForSaladOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForHTSAOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkForBACNMLTOutage(cart, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkPickupMessage(oc, shopMessage, shopMessageTwo))
                            park = true;
                        else if (checkChipotleOutage(cart, shopMessage, shopMessageTwo))
                        {
                            park = true;
                        }
                        else if (checkFiltetsOutage(cart, shopMessage, shopMessageTwo))
                        {
                            park = true;
                        }
                        else if (checkForApplePieOutage(cart, shopMessage, shopMessageTwo))
                        {
                            park = true;
                        }else if (checkForChiliFiestaPizzaOutage(cart, shopMessage, shopMessageTwo))
                        {
                            park = true;
                        }else if (checkForChiliOutage(cart, shopMessage, shopMessageTwo))
                        {
                            park = true;
                        }
                        else if (checkOtherFoodsAndMessages(cart, shopMessage, shopMessageTwo,
                            (SHOP_MESSAGE_PREFIX_FOOD + BONELESS_WINGS), (SHOP_MESSAGE_PREFIX_FOOD + GIANT_COOKIE),
                            (SHOP_MESSAGE_PREFIX_FOOD + CHK_BITES), (SHOP_MESSAGE_PREFIX_FOOD + SHRIMP),
                            (SHOP_MESSAGE_PREFIX_FOOD + MM), (SHOP_MESSAGE_PREFIX_FOOD + BROWNIES),
                            (SHOP_MESSAGE_PREFIX_FOOD + GARLIC), (SHOP_MESSAGE_PREFIX_MISC + DEBIT),
                            (SHOP_MESSAGE_PREFIX_FOOD + PLUM), (SHOP_MESSAGE_PREFIX_FOOD + CHEJA),
                            (SHOP_MESSAGE_PREFIX_FOOD + THAI), (SHOP_MESSAGE_PREFIX_FOOD + RANCH),
                            (SHOP_MESSAGE_PREFIX_FOOD + HOT), (SHOP_MESSAGE_PREFIX_FOOD + SEAFS),
                            (SHOP_MESSAGE_PREFIX_FOOD + HM), (SHOP_MESSAGE_PREFIX_FOOD + MARIN),
                            (SHOP_MESSAGE_PREFIX_FOOD + CHEDI), (SHOP_MESSAGE_PREFIX_FOOD + SFEDI),
                            (SHOP_MESSAGE_PREFIX_FOOD + LAYS), (SHOP_MESSAGE_PREFIX_FOOD + DORITOS),
                            (SHOP_MESSAGE_PREFIX_FOOD + MUNCH), (SHOP_MESSAGE_PREFIX_MISC + TICKET),
                            (SHOP_MESSAGE_PREFIX_MISC + PICKUPS), (SHOP_MESSAGE_PREFIX_MISC + BB_VOUCHER),
                            (SHOP_MESSAGE_PREFIX_FOOD + WWMG), (SHOP_MESSAGE_PREFIX_FOOD + WWMG9),
                            (SHOP_MESSAGE_PREFIX_FOOD + WWMG12), (SHOP_MESSAGE_PREFIX_FOOD + WWMG14),
                            (SHOP_MESSAGE_PREFIX_FOOD + SALAD), (SHOP_MESSAGE_PREFIX_FOOD + BBQSTK),
                            (SHOP_MESSAGE_PREFIX_FOOD + MILD), (SHOP_MESSAGE_PREFIX_FOOD + BBQ),
                            (SHOP_MESSAGE_PREFIX_FOOD + BLUCH), (SHOP_MESSAGE_PREFIX_FOOD + HNGAR),
                            (SHOP_MESSAGE_PREFIX_FOOD + BACNMLT), (SHOP_MESSAGE_PREFIX_FOOD + HTSA),
                            (SHOP_MESSAGE_PREFIX_FOOD + DILL), (SHOP_MESSAGE_PREFIX_FOOD + CINNAMIN_POPPERS),
                            (SHOP_MESSAGE_PREFIX_FOOD + ONION_RINGS), (SHOP_MESSAGE_PREFIX_MISC + SOCCER),
                            (SHOP_MESSAGE_PREFIX_FOOD + CHIP_CHK), (SHOP_MESSAGE_PREFIX_FOOD + CHIP_STK),
                            (SHOP_MESSAGE_PREFIX_FOOD + CHIP_WINGS), (SHOP_MESSAGE_PREFIX_FOOD + CHIP_PORK),
                            (SHOP_MESSAGE_PREFIX_FOOD + POTATO_WEDGES), (SHOP_MESSAGE_PREFIX_FOOD + FANTA),
                            (SHOP_MESSAGE_PREFIX_FOOD + CAKE), (SHOP_MESSAGE_PREFIX_FOOD + FILETS), (SHOP_MESSAGE_PREFIX_FOOD + APPLE_PIE)))
                        {
                            park = true;
                        }
                    }
                    // check for movies and pops
                    if (!park)
                    {
                        if (shopMessageCat.contains(SHOP_MESSAGE_PREFIX_POP) && !park)
                        {
                            park = checkForPopProductOutage(cart, shopMessageCat);
                        }
                    }
                }
                else
                { // error message format
                    park = true;
                }
                if (park)
                {
                    // if(parkOrders())
                    order.setStatus(OrderStatus.PARKED.getShortValue());
                    message += " " + SHOP_ERROR;
                }
            }
        }

        final List<Integer> duplicateOrder = this.orderDao.ordersForBusinessDate(oc);
        if (!duplicateOrder.isEmpty())
        {
            // if(parkOrders())
            order.setStatus(OrderStatus.PARKED.getShortValue());
            message += " " + DUPLICATE + "-id: " + duplicateOrder.get(0);
        }

        return message;
    }

    private boolean checkForProductOutage(final Cart cart, final Integer productId, final String shopMessage,
        final String outageString)
    {
        boolean hasSpecial = cart.hasProduct(productId);
        if (hasSpecial)
        {
            if (outageString.contains(shopMessage))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Redeem the coupon entered in the checkout form.
     *
     * @param cart
     * @param oc
     * @param order
     */
    private void redeemCoupon(final Cart cart, final OnlineCustomer oc, final Order order)
    {
        final Coupon coupon = cart.getCoupon();
        final UniqueCoupon uc = cart.getUc();
        if (coupon != null)
        {
            if (StringUtils.isNotBlank(coupon.getCode()))
            {
                final CouponRedemption cr = new CouponRedemption(coupon.getId(), oc);
                this.lMgr.save(cr);
                order.setCouponCode(coupon.getCode());
            }
        }
        else if (uc != null && uc.getCouponType() <= 6)
        {
            if (StringUtils.isNotBlank(uc.getCode()))
            {
                uc.setRedeemedOrderId(order.getId());
                uc.setRedemptionDate(this.lMgr.businessDate());
            }
            this.lMgr.save(uc);
            order.setCouponCode(uc.getCode());
        }

    }

    private String reomveToppingContentPerser(String content)
    {
        content = StringUtils.trimToEmpty(content);
        if (content.length() <= 3)
            return content;
        if (content.substring(0, 3).equalsIgnoreCase("No "))
            return content.substring(3);
        else
            return content;
    }

    /**
     * @param item
     */
    private void saveItems(final OrderItem item)
    {
        this.lMgr.save(item);
        if (!item.getChildren().isEmpty())
        {
            final Iterator<OrderItem> iter = item.getChildren().iterator();
            while (iter.hasNext())
            {
                final OrderItem child = iter.next();
                if (child.getProduct().getId().equals(82))
                {
                    child.setQuantity(1);
                }
                saveItems(child);
            }
        }
    }

    public SpinToWinPrize prizeForCouponType(Integer couponType)
    {
        return this.orderDao.prizeForCouponType(couponType);
    }

    public Long mobileOrderCountForBusinessDate(Calendar businessDate)
    {
        return this.orderDao.orderCountForBusinessDateAndOrigin(businessDate, OrderOrigin.MOBILE);
    }

    public Long justOnlineOrderCountForBusinessDate(Calendar businessDate)
    {
        return this.orderDao.orderCountForBusinessDateAndOrigin(businessDate, OrderOrigin.ONLINE);
    }

    public Long orderCountForBusinessDateAndOrigin(Calendar businessDate, OrderOrigin origin)
    {
        return this.orderDao.orderCountForBusinessDateAndOrigin(businessDate, origin);
    }

    public Long activeOrderCountForBusinessDateAndOrigin(Calendar businessDate, OrderOrigin origin)
    {
        return this.orderDao.activeOrderCountForBusinessDateAndOrigin(businessDate, origin);
    }

    public Boolean countCustomerOrdersForBusinessDayWithCoupon(OnlineCustomer customer, String couponCode)
    {
        return this.orderDao.countCustomerOrdersForBusinessDayWithCoupon(customer, couponCode);
    }

    public List<Shop> shopCountForCity(Municipality city)
    {
        return this.orderDao.shopCountForCity(city);
    }
}
