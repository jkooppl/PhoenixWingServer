package com.pizza73.service.dwr.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.Address;
import com.pizza73.model.Municipality;
import com.pizza73.model.Order;
import com.pizza73.model.OrderCustomer;
import com.pizza73.model.UniqueCoupon;
import com.pizza73.model.contest.SpinToWinPrize;
import com.pizza73.model.enums.SpinToWinType;
import com.pizza73.service.CouponManager;
import com.pizza73.service.LookupManager;
import com.pizza73.service.MailEngine;
import com.pizza73.service.OrderManager;

@Service("spinToWinManager")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class SpinToWinManagerImpl
{
    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(SpinToWinManagerImpl.class);
    private static final int UNIQUE_LENGTH = 10;
    private static final String CONTEST_EMAIL = "contests@pizza73.com";
    private CouponManager couponManager = null;
    private OrderManager orderManager = null;
    private LookupManager lookupManager = null;

    @Autowired
    private MailEngine mailEngine;
    @Autowired
    private SimpleMailMessage mailMessage;

    private String templateName;

    @Autowired
    public void setCouponManager(final CouponManager mgr)
    {
        this.couponManager = mgr;
    }

    @Autowired
    public void setOrderManager(final OrderManager mgr)
    {
        this.orderManager = mgr;
    }

    @Autowired
    public void setLookupManager(final LookupManager mgr)
    {
        this.lookupManager = mgr;
    }

    public void setTemplateName(String template)
    {
        this.templateName = template;
    }

    public String[] generatePrize(Integer entryId, String email)
    {
        Integer orderId = (entryId - 17) / 64;
        String[] result = { "", "" };

        Order order = (Order) this.orderManager.getOrder(orderId);
        if (order == null)
        {
            result[0] = "We are unable to process your request at this time (no order found with id: " + orderId + ").";
            result[1] = "-1";
            return result;
        }

        UniqueCoupon c = this.couponManager.findUniqueCoupon(order.getId());

        if (c != null)
        {
            result[0] = "You have played this game before. This game can only be played once per order. An confirmation email should have been sent to you already.";
            result[1] = "-1";
            return result;
        }

        String[] pizzaMessage = getRandomPrize(order, email);

        return pizzaMessage;
    }

    public boolean sendPrizeConfirmation(Integer entryId, String email)
    {
        Integer orderId = (entryId - 17) / 64;

        Order order = (Order) this.orderManager.getOrder(orderId);
        if (order == null)
        {
            return false;
        }

        UniqueCoupon coupon = this.couponManager.findUniqueCoupon(order.getId());

        if (coupon == null)
        {
            return false;
        }

        boolean result = false;
        if (email != null)
        {
            email = email.replace("\r", "");
        }
        if (null == coupon.getEmail() || coupon.getEmail().trim().length() == 0)
        {
            result = this.sendConfirmation(order, coupon, email);
        }

        if (result == true)
        {
            coupon.setEmail(email);
            this.lookupManager.save(coupon);
        }

        return result;
    }

    public UniqueCoupon generatePrizeForMarketing(Integer orderId, String email)
    {
        Order order = (Order) this.orderManager.getOrder(orderId);
        if (order == null)
        {
            return null;
        }

        UniqueCoupon coupon = this.couponManager.findUniqueCoupon(order.getId());

        if (coupon != null)
        {
            return null;
        }

        SpinToWinPrize prize = generatePrize();
        String couponCode = couponManager.generateCouponCode(UNIQUE_LENGTH);
        Integer prizeId = prize.getId();
        coupon = saveUniqueCoupon(order, email, couponCode, prizeId);

        return coupon;
    }

    private boolean sendConfirmation(Order order, UniqueCoupon uniqueCoupon, String email)
    {
        boolean result = true;
        String message = getPrizeMessage(uniqueCoupon.getCouponType(), uniqueCoupon.getCode());
        String emailMessage = message.replaceAll(
            "Please use the print button on the prize wheel to print your coupon before pressing OK.", "");

        this.setTemplateName("couponCreated.vm");
        mailMessage.setTo(email + "<" + email + ">");
        mailMessage.setSubject("Pizza 73 - Your Spin To Win coupon code has been created.");
        mailMessage.setFrom("noReply@pizza73.com");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("customer", order.getCustomer().getName());
        model.put("message", emailMessage);
        model.put("applicationURL", "http://www.pizza73.com");
        try
        {
            mailEngine.sendMessage(mailMessage, templateName, model);
            Integer couponType = uniqueCoupon.getCouponType();
            if (couponType > 6)
            {
                String[] emailAddresses = new String[] { CONTEST_EMAIL, "chris@ctrlspace.ca" };
                mailMessage.setTo(emailAddresses);
                mailMessage.setSubject("Pizza 73 - Prize redemption email.");
                mailMessage.setFrom("noReply@pizza73.com");

                String displayAddress = customerDisplayAddress(order);
                message = "Customer " + order.getCustomer().getName() + " has won the following prize:\n\n" + "email: "
                    + uniqueCoupon.getEmail() + "\nphone: " + order.getCustomer().getPhone() + "\naddress: "
                    + displayAddress + "\n\n" + emailMessage;
                Map<String, Object> newModel = new HashMap<String, Object>();
                newModel.put("customer", order.getCustomer().getName());
                newModel.put("message", message);
                newModel.put("applicationURL", "http://www.pizza73.com");
                mailEngine.sendMessage(mailMessage, templateName, newModel);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    private String customerDisplayAddress(Order order)
    {
        String displayAddress = "Pickup order";
        OrderCustomer customer = order.getCustomer();
        if (null != customer)
        {
            Address address = customer.getAddress();
            if (null != address)
            {
                displayAddress = address.getDisplayAddress();
                Municipality city = address.getCity();
                if (null != city)
                {
                    displayAddress += ", " + city.getDisplayName();
                }
            }
        }
        return displayAddress;
    }

    private String[] getRandomPrize(Order order, String email)
    {
        SpinToWinPrize prize = generatePrize();
        String couponCode = couponManager.generateCouponCode(UNIQUE_LENGTH);

        Integer prizeId = prize.getId();
        String message = getPrizeMessage(prizeId, couponCode);

        UniqueCoupon temp = saveUniqueCoupon(order, email, couponCode, prizeId);

        if (null != email && email.trim().length() > 0)
        {
            this.sendConfirmation(order, temp, email);
        }

        String[] finalResult = { message, String.valueOf(prizeId) };

        return finalResult;
    }

    private UniqueCoupon saveUniqueCoupon(Order order, String email, String couponCode, Integer prizeId)
    {
        UniqueCoupon temp = new UniqueCoupon();
        temp.setOrderId(order.getId());
        temp.setCode(couponCode);
        temp.setCouponType(prizeId);
        if (email != null)
        {
            temp.setEmail(email);
        }
        try
        {
            this.lookupManager.save(temp);
            this.couponManager.incrementCountForCouponType(prizeId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return temp;
    }

    private SpinToWinPrize generatePrize()
    {
        SpinToWinPrize prize = null;

        Double interval = getInterval();

        if (interval < 0.00055417)
        {
            prize = (SpinToWinPrize) orderManager.prizeForCouponType(SpinToWinType.EDD.getId());
        }
        else if (interval < 0.001939595)
        {
            prize = (SpinToWinPrize) orderManager.prizeForCouponType(SpinToWinType.GAS_COUPON.getId());
        }
        else if (interval < 0.1108)
        {
            prize = (SpinToWinPrize) orderManager.prizeForCouponType(SpinToWinType.POP.getId());
        }
        else if (interval < 0.5)
        {
            prize = getPrize(false, SpinToWinType.ONION_RINGS, SpinToWinType.POTATO_WEDGES);
        }
        // default to these if no prize
        if (null == prize)
        {
            prize = getPrize(false, SpinToWinType.DIP, SpinToWinType.DELIVERY, SpinToWinType.SMALL_WEDGIE);
        }

        return prize;
    }

    private Double getInterval()
    {
        UUID u = UUID.randomUUID();

        Random rn = new Random();
        rn.setSeed(u.hashCode());

        return rn.nextDouble();
    }

    private String getPrizeMessage(Integer prizeId, String couponeCode)
    {
        SpinToWinPrize thePrize = (SpinToWinPrize) orderManager.prizeForCouponType(prizeId);
        String message = "Congratulations!  You have a won " + (prizeId != 10 ? "a " : "") + thePrize.getPrize() + ".  ";

        if (prizeId > 6)
        {
            message = message
                + "  Your reference number is "
                + couponeCode
                + "  Please email contests@pizza73.com to redeem your prize, referencing your email address and winning reference number.";
        }
        else
        {
            message = message + "  Your coupon code is " + couponeCode
                + ".  You can use this coupon online only as of tommorrow and within 30 days.";
        }

        message += "  Please use the print button on the prize wheel to print your coupon before pressing OK.";

        return message;
    }

    private SpinToWinPrize getPrize(boolean checkMax, SpinToWinType... prizes)
    {
        SpinToWinPrize prize = null;
        double choices = prizes.length;
        Double interval = getInterval();
        for (int i = 1; i <= prizes.length; i++)
        {
            if (interval < (i / choices))
            {
                SpinToWinType type = prizes[i - 1];
                prize = (SpinToWinPrize) orderManager.prizeForCouponType(type.getId());
                if (checkMax)
                {
                    Integer totalPrizes = prize.getCount();
                    if (totalPrizes < prize.getMaxPrizes())
                    {
                        break;
                    }
                    prize = null;
                }
                break;
            }
        }

        return prize;
    }
}
