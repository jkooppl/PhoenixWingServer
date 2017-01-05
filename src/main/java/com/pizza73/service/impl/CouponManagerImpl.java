package com.pizza73.service.impl;

import com.pizza73.dao.CouponDao;
import com.pizza73.model.Address;
import com.pizza73.model.Cart;
import com.pizza73.model.Coupon;
import com.pizza73.model.CouponDetail;
import com.pizza73.model.CouponRedemption;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.OrderItem;
import com.pizza73.model.Product;
import com.pizza73.model.ProductConstants;
import com.pizza73.model.UniqueCoupon;
import com.pizza73.model.enums.MindShareCouponEnum;
import com.pizza73.model.enums.ProductCategoryEnum;
import com.pizza73.model.enums.ProductEnum;
import com.pizza73.model.enums.SpinToWinType;
import com.pizza73.service.CouponManager;
import com.pizza73.service.OrderManager;
import com.pizza73.service.PricingManager;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Service("couponManager")
public class CouponManagerImpl extends UniversalManagerImpl implements CouponManager
{
    private static final String SAFE_CHARS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    @Qualifier("couponDao")
    @Autowired
    private CouponDao dao;

    @Qualifier("orderManager")
    @Autowired
    private OrderManager orderMgr;

    @Qualifier("pricingManager")
    @Autowired
    private PricingManager pricingMgr;

    public CouponManagerImpl()
    {
    }

    @SuppressWarnings(value = {"unused"})
    private int countFourPaks(Cart cart)
    {
        int count = 0;
        Set<OrderItem> specials = cart.itemsForCategory(ProductCategoryEnum.SPECIAL);
        for(OrderItem orderItem : specials)
        {
            if(orderItem.getProduct().getId() == 37)
            {
                count++;
            }
        }
        return count;
    }

    public Boolean couponRedeemedForBusinessDate(OnlineCustomer oc, Integer couponId)
    {
        List<CouponRedemption> redeemedList = this.dao.couponRedeemedForBusinessDate(oc, couponId);

        return !redeemedList.isEmpty();
    }

    public Coupon forCode(String code)
    {
        return this.dao.forCode(code);
    }

    /**
     * NOTE:This method for the moment assumes that a Coupon will have at most
     * one CouponDetail associated with it. October 24, 2007:COH
     */
    public Boolean validateCoupon(Coupon coupon, Cart cart)
    {
        List<OrderItem> items = cart.getItems();
        boolean validCoupon = false;

        // Check dates
        Calendar startDate = coupon.getStartDate();
        Calendar endDate = coupon.getEndDate();
        Calendar now = this.dao.businessDate();
        if(now.before(startDate))
        {
            coupon.setErrorMessage("This coupon will be valid in the near future.");
        }
        else if(now.after(endDate))
        {
            coupon.setErrorMessage("This coupon has expired.");
        }
        else
        {
            if(!validCity(coupon, cart))
            {
                return false;
            }
            // verify required products for coupon to be used are in Cart
            Integer productId = coupon.getRequiredProductId();
            if(!productId.equals(Integer.valueOf(0)))
            {
                for(OrderItem item : items)
                {
                    if(validate(item, coupon))
                    {
                        validCoupon = true;
                        break;
                    }
                }
            }
            if(validCoupon)
            {
                if(ProductConstants.FREE_DIP_COUPON.equals(coupon.getCode()))
                {
                    if(cart.getTotalPrice() < 1000)
                    {
                        coupon.setErrorMessage("In order to redeam your Free Dip coupon your order total must be greater than $9.99.");
                        return false;
                    }
                    else
                    {
                        validCoupon = true;
                    }
                }
                else if(ProductConstants.FUNNEL_CAKE_COUPON.equals(coupon.getCode()))
                {
                    if(cart.getTotalPrice() < 1599)
                    {
                        coupon.setErrorMessage("In order to redeem your Free Funnel Cake Stix coupon, your order total must be greater than $10.99 without the Funnel Cake Stix.");
                        return false;
                    }
                    else
                    {
                        validCoupon = true;
                    }
                }
                else if(ProductConstants.CASL_COUPON.equals(coupon.getCode()))
                {
                    if(cart.getTotalPrice() < 1000)
                    {
                        coupon.setErrorMessage("In order to redeam your coupon your order total must be greater than $9.99.");
                        return false;
                    }
                    else
                    {
                        validCoupon = true;
                    }
                }
            }

            boolean isMindShare = ArrayUtils.contains(MindShareCouponEnum.MIND_SHARE_COUPONS, coupon.getId());
            if(isMindShare)
            {
                final Boolean duplicateOrder = this.orderMgr.countCustomerOrdersForBusinessDayWithCoupon(cart.getCustomer(),coupon.getCode());
                Integer couponDiscount = coupon.getDiscount();
                Integer totalMinusDiscount = cart.getTotalPrice() - couponDiscount;
                if(duplicateOrder)
                {
                    coupon.setErrorMessage("Only one Mind Share coupon can be redeemed per business day.");
                    return false;
                }
                else if(totalMinusDiscount < 999)
                {
                    coupon.setErrorMessage(coupon.getErrorMessage());
                    return false;
                }
            }

            // if coupon is for a free product this block will be used. If it is
            // simply a monetary discount this block is not required.
            List<CouponDetail> details = coupon.getDetails();
            if(validCoupon && !details.isEmpty())
            {
                // Assuming a coupon has only one CouponDetail - October
                // 24,2007:COH
                // changed to support multiple couponDetails with an OR relation
                // among all the couponDetails
                int i = 0;
                for(i = 0; i < details.size(); i++)
                {
                    CouponDetail detail = details.get(i);
                    Integer[] validCount = new Integer[1];
                    validCount[0] = Integer.valueOf(0);
                    for(OrderItem item : items)
                    {
                        if(validate(item, detail, validCount))
                        {
                            return true;
                        }
                    }
                }
            }
            else if(validCoupon)
            {
                return true;
            }
        }

        return false;
    }

    public Boolean couponRedeemed(OnlineCustomer oc, Integer couponId)
    {
        List<CouponRedemption> redeemedList = this.dao.couponRedeemed(oc, couponId);

        return !redeemedList.isEmpty();
    }

    public Boolean couponRedeemed(OnlineCustomer oc, String couponId)
    {
        return this.couponRedeemed(oc, Integer.valueOf(couponId));
    }

    public UniqueCoupon findUniqueCoupon(Integer order_id)
    {
        return this.dao.findUniqueCoupon(order_id);
    }

    public UniqueCoupon findUniqueCouponForRedeemedOrder(Integer orderId)
    {
        return this.dao.findUniqueCouponForRedeemedOrder(orderId);
    }

    public UniqueCoupon saveUniqueCoupon(UniqueCoupon o)
    {
        return this.dao.saveUniqueCoupon(o);
    }

    public UniqueCoupon findUniqueCouponForCode(String code)
    {
        return this.dao.findUniqueCouponForCode(code);
    }

    public Boolean validateUniqueCoupon(UniqueCoupon uc, Cart cart)
    {
        boolean validCoupon = false;
        Integer couponType = uc.getCouponType();

        OnlineCustomer oc = cart.getCustomer();
        if(couponUsedToday(oc))
        {
            cart.setErrorMessage("You can only use one coupon per business day.");
            return false;
        }

        if(couponUsedOnSameDayAsGenerated(uc))
        {
            cart.setErrorMessage("You cannnot use a coupon on the same day it was generated.");
            return false;
        }
        if(couponType > 8)
        {
            return validCoupon;
        }
        // Free delivery
        if(couponType.equals(SpinToWinType.DELIVERY.getId()))
        {
            if(cart.getCustomer().getDelivery().getShortValue() == 'd')
            {
                cart.setErrorMessage("");
                return true;
            }
            else
            {
                cart.setErrorMessage("To redeem the Free delivery coupon, your order must be a delivery order.");
                return false;
            }
        }
        // Free 2L pop
        else if(couponType.equals(SpinToWinType.POP.getId()))
        {
            Set<OrderItem> items = cart.itemsForCategory(ProductCategoryEnum.POP);
            int popCount = 0;
            for(OrderItem item : items)
            {
                Integer categoryId = item.getProduct().getCategory().getId();
                if(ProductCategoryEnum.POP.getId().equals(categoryId))
                {
                    if(item.getSize().getId().equals(Integer.valueOf(3)))
                    {
                        popCount++;
                    }
                }
            }
            System.out.println("popCount: " + popCount);
            if(popCount == 0)
            {
                cart.setErrorMessage("To redeem the Free 2L coupon, your order must have a 2L pop in your order.");
            }
            else
            {
                return true;
            }

            return false;
        }
        // Free dip
        else if(couponType.equals(SpinToWinType.DIP.getId()))
        {
            cart.setErrorMessage("");
            int dipCount = 0;
            Set<OrderItem> wingDips = cart.itemsForCategory(ProductCategoryEnum.WING_DIP);
            for(OrderItem item : wingDips)
            {
                dipCount += item.getQuantity();
            }

            int freeDips = 0;
            Set<OrderItem> meals = cart.itemsForCategory(ProductCategoryEnum.WING_MEAL);
            Set<OrderItem> wingBox = cart.itemsForCategory(ProductCategoryEnum.WING_BOX);
            Set<OrderItem> bonelessBox = cart.itemsForCategory(ProductCategoryEnum.CHICKEN_BITES);
            Set<OrderItem> wingSide = cart.itemsForCategory(ProductCategoryEnum.WING_SIDE);
            Set<OrderItem> chicken = cart.itemsForCategory(ProductCategoryEnum.CHICKEN);
            meals.addAll(bonelessBox);
            meals.addAll(wingBox);
            meals.addAll(wingSide);
            meals.addAll(chicken);
            for(OrderItem item : meals)
            {
                freeDips += pricingMgr.getFreeOptionCount(item, ProductCategoryEnum.WING_DIP.getName());
            }

            if(dipCount == 0)
            {
                cart.setErrorMessage("To redeem the Free Dip coupon, your order must contain at least one dipping sauce.");
            }
            else if(dipCount <= freeDips)
            {
                cart.setErrorMessage("To redeem the Free Dip coupon, your order must contain at " + "least one non free dipping sauce " +
                    "(free dipping sauces are " + "included in wing meals and wing boxes).");
            }
            else
            {
                return true;
            }

            return false;
        }
        // Free 12pc wedgies
        else if(couponType.equals(SpinToWinType.SMALL_WEDGIE.getId()))
        {
            int fourPakWedgies = countFourPakWedgies(cart);
            Set<OrderItem> items = cart.itemsForCategory(ProductCategoryEnum.WEDGIE);
            int wedgieCount = 0;
            for(OrderItem item : items)
            {
                if(item.getProduct().getId() == 193 && item.getSize().getId() == 1)
                {
                    wedgieCount++;
                }
            }
            if(wedgieCount > fourPakWedgies)
            {
                cart.setErrorMessage("");
                return true;
            }
            else if(wedgieCount == 0)
            {
                cart.setErrorMessage("To redeem the Free 12pc wediges coupon, you must have a 12pc wedgies in your order.");
            }
            else
            {
                cart.setErrorMessage("Spin to win coupons cannot be used with other promotions (i.e - The Four Pak).");
            }

            return false;
        }
        else
        {
            boolean hasProduct = cart.hasProduct(ProductEnum.ONION_RING.getId()) || cart.hasProduct(ProductEnum.WEDGES.getId());
            String productName = "Potato Wedges";
            if(couponType.equals(SpinToWinType.ONION_RINGS.getId()))
            {
                productName = "Onion Rings";
            }
            if(!hasProduct)
            {
                cart.setErrorMessage("To redeem the Free " + productName + " coupon, you must have a " + productName + " in your order.");
            }
            else
            {
                return true;
            }
        }

        return false;
    }

    private boolean couponUsedToday(OnlineCustomer oc)
    {
        if(null != oc)
        {
            String email = oc.getEmail();
            if(null != email)
            {
                if(null != dao.couponUsedToday(email))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean couponUsedOnSameDayAsGenerated(UniqueCoupon uc)
    {
        boolean sameDate = false;
        // orders with an id of 73 are generic orders used for assigned coupon
        // codes via customer support.
        if(!uc.getOrderId().equals(Integer.valueOf(73)))
        {
            Calendar orderBusinessDate = uc.getCreateDate();
            int orderYear = orderBusinessDate.get(Calendar.YEAR);
            int orderMonth = orderBusinessDate.get(Calendar.MONTH);
            int orderDay = orderBusinessDate.get(Calendar.DATE);

            Calendar businessDate = this.businessDate();
            int year = businessDate.get(Calendar.YEAR);
            int month = businessDate.get(Calendar.MONTH);
            int day = businessDate.get(Calendar.DATE);

            sameDate = year == orderYear;
            sameDate &= month == orderMonth;
            sameDate &= day == orderDay;
        }
        return sameDate;
    }

    private int countFourPakWedgies(Cart cart)
    {
        int count = 0;
        Set<OrderItem> specials = cart.itemsForCategory(ProductCategoryEnum.SPECIAL);
        for(OrderItem orderItem : specials)
        {
            if(orderItem.getProduct().getId() == 37)
            {
                List<OrderItem> children = orderItem.getChildren();
                for(OrderItem childItem : children)
                {
                    if(childItem.getProduct().getId() == 193)
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public String generateCouponCode(int length)
    {
        UUID u = UUID.randomUUID();

        Random random = new Random();
        random.setSeed(u.hashCode());

        final int size = SAFE_CHARS.length();
        final StringBuilder buf = new StringBuilder(length);
        for(int i = 0; i < length; i++)
        {
            buf.append(SAFE_CHARS.charAt(random.nextInt(size)));
        }
        return buf.toString();
    }

    @Override
    public void incrementCountForCouponType(Integer prizeId)
    {
        this.dao.incrementCountForCouponType(prizeId);
    }

    private boolean validCity(Coupon coupon, Cart cart)
    {
        String cities = coupon.getCity();
        if(cities.equals("0"))
        {
            return true;
        }
        String[] cityArray = cities.split(",");
        OnlineCustomer oc = cart.getCustomer();
        if(null != oc)
        {
            Address address = oc.getAddress();
            if(null != address)
            {
                Integer cityId = address.getCity().getId();
                for(String sCouponCityId : cityArray)
                {
                    Integer couponCityId = Integer.valueOf(sCouponCityId);
                    if(cityId.equals(couponCityId))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean validate(OrderItem item, CouponDetail detail, Integer[] validCount)
    {
        Product product = item.getProduct();
        Integer productCat = product.getCategory().getId();
        Integer productSizeId = item.getSize().getId();

        Integer quantity = detail.getQuantity();
        Integer reqSizeId = detail.getSizeId();
        Integer reqProdCatId = detail.getProductCategoryId();

        if(detail.isUseProductId())
        {
            productCat = product.getId();
        }

        if(reqProdCatId.equals(productCat) && reqSizeId.equals(productSizeId))
        {
            validCount[0] += item.getQuantity();
        }
        if(validCount[0] < quantity)
        {
            if(!item.getChildren().isEmpty())
            {
                for(OrderItem child : item.getChildren())
                {
                    validate(child, detail, validCount);
                    if(validCount[0] >= quantity)
                    {
                        return true;
                    }
                }
            }
        }
        else
        {
            return true;
        }

        return false;
    }

    private boolean validate(OrderItem item, Coupon coupon)
    {
        Integer couponProductReq = coupon.getRequiredProductId();
        Integer couponSizeReq = coupon.getRequiredSizeId();

        Product product = item.getProduct();
        Integer sizeId = item.getSize().getId();
        // try to validate coupon product requirement
        Integer comparisonId = product.getCategory().getId();
        if(coupon.isUseProduct())
        {
            comparisonId = product.getId();
        }
        if(couponProductReq.equals(comparisonId))
        {
            if(couponSizeReq.equals(sizeId) || couponSizeReq.equals(0))
            {
                return true;
            }
        }
        if(!item.getChildren().isEmpty())
        {
            for(OrderItem child : item.getChildren())
            {
                if(validate(child, coupon))
                {
                    return true;
                }
            }
        }

        return false;
    }
}
