package com.pizza73.service.impl;

import com.pizza73.dao.ConversionDao;
import com.pizza73.dao.CouponDao;
import com.pizza73.dao.LookupDao;
import com.pizza73.dao.OrderDao;
import com.pizza73.dao.UniversalDao;
import com.pizza73.model.Coupon;
import com.pizza73.model.Menu;
import com.pizza73.model.Order;
import com.pizza73.model.OrderComment;
import com.pizza73.model.OrderItem;
import com.pizza73.model.Product;
import com.pizza73.model.ProductCategory;
import com.pizza73.model.ProductConstants;
import com.pizza73.model.Shop;
import com.pizza73.model.UniqueCoupon;
import com.pizza73.model.contest.SpinToWinPrize;
import com.pizza73.model.conversion.Iqq;
import com.pizza73.model.conversion.Sales;
import com.pizza73.model.conversion.SalesDetail;
import com.pizza73.model.conversion.SalesDetailComposition;
import com.pizza73.model.enums.MenuId;
import com.pizza73.model.enums.MindShareCouponEnum;
import com.pizza73.model.enums.ProductCategoryEnum;
import com.pizza73.model.enums.ProductEnum;
import com.pizza73.model.enums.SalesDetailCompositionEnum;
import com.pizza73.model.enums.SpecialEnum;
import com.pizza73.model.enums.SpinToWinType;
import com.pizza73.model.enums.ToppingMappingEnum;
import com.pizza73.service.ReplicationManager;
import com.pizza73.util.ProductUtil;
import com.pizza73.util.SpecialsUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static com.pizza73.model.ProductConstants.GLUTEN_PIZZAS;
import static com.pizza73.model.ProductConstants.INFO_FIELD_LENGTH;
import static com.pizza73.model.enums.ProductCategoryEnum.POP;
import static com.pizza73.model.enums.ProductCategoryEnum.WING_DIP;
import static com.pizza73.model.enums.ProductCategoryEnum.WING_FLAVOR;
import static com.pizza73.model.enums.SalesDetailCompositionEnum.DIP;
import static com.pizza73.model.enums.SalesDetailCompositionEnum.NO_DIP;
import static com.pizza73.model.enums.SpecialEnum.DINNER_MOVIE_LARGE;
import static com.pizza73.model.enums.SpecialEnum.DINNER_MOVIE_MEDIUM;
import static com.pizza73.model.enums.SpecialEnum.DINNER_MOVIE_SMALL;
import static com.pizza73.model.enums.SpecialEnum.HOLIDAY_HELPER;
import static com.pizza73.model.enums.SpecialEnum.MEGA_MEAL_12;
import static com.pizza73.model.enums.SpecialEnum.MEGA_MEAL_14;

/**
 * ReplicationManagerImpl.java Replicates orders from the iq2 schema to the
 * original schema the call center application and sre (shop replication engine)
 * are using.
 *
 * @author chris 27-Mar-07
 * @Copyright Flying Pizza 73
 */
@Service("replicationManager")
public class ReplicationManagerImpl extends UniversalManagerImpl implements ReplicationManager, Serializable
{
    private static final long serialVersionUID = 9074332618804137237L;

    private static Logger log = Logger.getLogger(ReplicationManagerImpl.class);

    // JDBC data access object - writes data to the sales and sales_detail table
    @Autowired
    private ConversionDao convDao;

    @Autowired
    @Qualifier("couponDao")
    private CouponDao couponDao;

    @Autowired
    @Qualifier("orderDao")
    private OrderDao orderDao;

    @Autowired
    @Qualifier("lookupDao")
    private LookupDao lookupDao;

    @Resource(name = "universalDao")
    private UniversalDao repDao;

    public ReplicationManagerImpl()
    {
    }

    private List<String> buildComment(String[] words, int size)
    {
        List<String> shortenedComments = new ArrayList<String>();
        String tempComment = "";
        int count = 0;
        while (count < words.length)
        {
            if(tempComment.length() + words[count].length() < size)
            {
                if(count != words.length)
                {
                    tempComment += words[count] + " ";
                }
                else
                {
                    tempComment += words[count];
                }
            }
            else
            {
                shortenedComments.add(tempComment);
                tempComment = words[count];
            }
            count++;
        }
        shortenedComments.add(tempComment);

        return shortenedComments;
    }

    /**
     * Replicate a bundle
     */
    private int bundleSalesDetailItem(List<SalesDetail> details, OrderItem item)
    {
        int discount = 0;
        Product bundle = item.getProduct();
        Integer productId = bundle.getId();
        // Replicate a wing box.
        Integer bundleCatId = bundle.getCategory().getId();
        if(ProductCategoryEnum.WING_BOX.isEqualToCategoryId(bundleCatId) || SpecialEnum.CHICKEN_BITES.isEqualToSpecialId(productId))
        {
            replicateWingBox(details, item);
        }
        // Replicate every day deal.
        else if(SpecialEnum.EVERYDAY_DEAL.isEqualToSpecialId(productId))
        {
            discount += replicateEveryDayDeal(details, item, null);
        }
        else
        {
            Integer modifierFour = bundle.getReportId();
            Integer sizeId = item.getSize().getId();
            SalesDetail detail = null;
//            if(!SpecialEnum.TWO_DIPS.isEqualToSpecialId(productId))
//            {
                if(SpecialEnum.TASTY_20.isEqualToSpecialId(productId))
                {
                    bundle = repackageSpecial(item);
                }

                detail = populateSalesDetail(bundle, sizeId);
                details.add(detail);
//            }

            // FOUR PAK
            if(SpecialsUtil.isFourPak(productId))
            {
                // override modifier One
                detail.setModifierOne(0);
                discount += replicateFourPak(item.getChildren(), details, modifierFour);
            }
            else if(SpecialEnum.SINGLE_TWO_TOPPER_PLUS_ONE_LITRE.isEqualToSpecialId(productId)
                || SpecialEnum.SINGLE_PESTO_GARDEN_VEGGIE_PLUS_ONE_LITRE.isEqualToSpecialId(productId)
                || SpecialEnum.SINGLE_HAWAIIAN_PLUS_ONE_LITRE.isEqualToSpecialId(productId)
                || SpecialEnum.BIG_TASTE.isEqualToSpecialId(productId)
                || SpecialEnum.SINGLE_CHILI_PLUS_ONE_LITRE.isEqualToSpecialId(productId))
            {
                replicateSinglePopPack(details, item);
            }
//            else if(SpecialEnum.TWO_DIPS.isEqualToSpecialId(productId))
//            {
//                replicateTwoDipsSpecial(details, item);
//            }
            else if(ProductCategoryEnum.SPECIAL.isEqualToCategoryId(bundleCatId))
            {

                if(SpecialEnum.MEGA_MEAL_12.isEqualToSpecialId(productId) || SpecialEnum.MEGA_MEAL_14.isEqualToSpecialId(productId) ||
                    SpecialEnum.POUTINE_COMBO.isEqualToSpecialId(productId))// || SpecialEnum.HOLIDAY_PARTY_PACK.isEqualToSpecialId(productId))
                {
                    // override modifier One
                    detail.setModifierOne(0);
                }

                discount += replicateEveryDayDeal(details, item, modifierFour);
            }
        }

        return discount;
    }

    private Product repackageSpecial(OrderItem item)
    {
        Menu tasty22 = lookupDao.getMenuItemForProduct(MenuId.AB_WEB.getId(), SpecialEnum.TASTY_22.getId(), 2);
        Product bundle = tasty22.getProduct();
        item.setProduct(bundle);
        item.setPrice(tasty22.getPrice());
        item.setPriceModifier(tasty22.getPriceModifier());
        item.setDisplayName(tasty22.getDisplayName());

        Menu twoDollarMenuItem = lookupDao.getMenuItemForProduct(MenuId.AB_WEB.getId(), ProductEnum.TWO_DOLLAR_OFF.getId(), 1);
        Product twoDollarOffProduct = twoDollarMenuItem.getProduct();
        OrderItem newItem = new OrderItem(twoDollarOffProduct, item, MenuId.AB_WEB.getId(), 999);
        newItem.setSize(twoDollarMenuItem.getSize());
        newItem.setPriceModifier(twoDollarMenuItem.getPriceModifier());
        newItem.setPrice(twoDollarMenuItem.getPrice());
        newItem.setQuantity(1);
        item.addChild(newItem);

        return bundle;
    }

    private BigDecimal convertToDecimal(Integer deliveryCharge)
    {
        BigDecimal value = BigDecimal.valueOf(deliveryCharge);

        return value.multiply(BigDecimal.valueOf(-0.01));
    }

    public CouponDao getCouponDao()
    {
        return couponDao;
    }

    public void setCouponDao(CouponDao couponDao)
    {
        this.couponDao = couponDao;
    }

    /**
     * @param sales
     * @param order
     * @return
     */
    private List<SalesDetail> handleOrderComments(Sales sales, Order order)
    {
        List<SalesDetail> extraComments = new ArrayList<SalesDetail>();
        String orderAddressComment = StringUtils.trimToEmpty(order.getCustomer().getAddress().getAddressComment());

        // remove unwanted spaces.
        StringTokenizer st = new StringTokenizer(orderAddressComment);
        StringBuffer buf = new StringBuffer();
        while (st.hasMoreTokens())
        {
            buf.append(' ').append(st.nextToken());
        }
        orderAddressComment = buf.toString();
        orderAddressComment = orderAddressComment.trim();
        if(StringUtils.isNotBlank(orderAddressComment))
        {
            if(orderAddressComment.length() <= 20)
            {
                sales.setAddressThree(orderAddressComment);
            }
            else
            {
                // address comment is to large so we need to parse it and
                // split it up so it fits in address two and three.
                String aTwo = orderAddressComment.substring(0, 10);
                aTwo.trim();
                if(StringUtils.isNotBlank(order.getCustomer().getAddress().getSuiteNumber()))
                {
                    aTwo = order.getCustomer().getAddress().getSuiteNumber() + " " + aTwo;
                }
                sales.setAddressTwo(aTwo);
                String aThree = orderAddressComment.substring(10);
                if(aThree.length() > 20)
                {
                    sales.setAddressThree(orderAddressComment.substring(10, 29));
                }
                else
                {
                    sales.setAddressThree(aThree);
                }
            }
        }

        String orderComment = StringUtils.trimToEmpty(order.getComment());
        st = new StringTokenizer(orderComment);
        buf = new StringBuffer();
        // Try to insert pickup and delivery comment on the top of the bill.
        OrderComment iq2Comment = this.orderDao.pickupComment(order);
        if(iq2Comment != null)
        {
            buf.append(' ').append(iq2Comment.getContent());
        }
        iq2Comment = this.orderDao.deliveryComment(order);
        if(iq2Comment != null)
        {
            buf.append(' ').append(iq2Comment.getContent());
        }
        while (st.hasMoreTokens())
        {
            buf.append(' ').append(st.nextToken());
        }
        orderComment = buf.toString().trim();

        if(StringUtils.isNotBlank(orderComment))
        {
            if(orderComment.length() <= 35)
            {
                sales.setComment(orderComment);
            }
            else
            {
                // order comment is to large so we need to parse it.
                String[] words = orderComment.split(" ");
                List<String> oComments = buildComment(words, INFO_FIELD_LENGTH);
                for(String comment : oComments)
                {
                    SalesDetail detail = new SalesDetail();
                    detail.setProductId(ProductEnum.COUPON_ID.getId());
                    detail.setInfo(comment);
                    extraComments.add(detail);
                }
            }
        }

        List<OrderComment> otherIq2Comments = this.orderDao.otherComments(order);
        if(otherIq2Comments != null)
        {
            for(OrderComment otherComment : otherIq2Comments)
            {
                SalesDetail detail = new SalesDetail();
                detail.setProductId(ProductEnum.COUPON_ID.getId());
                detail.setInfo(otherComment.getContent());
                extraComments.add(detail);
            }
        }

        return extraComments;
    }

    private void parseProductComments(List<SalesDetail> comments, SalesDetail itemDetail, String comment, int size)
    {
        String tempComment = StringUtils.trimToEmpty(comment);
        String words[] = tempComment.split(" ");
        List<String> shortenedComments = buildComment(words, size);
        SalesDetail detail = new SalesDetail();
        for(String infoComment : shortenedComments)
        {
            detail.setProductId(ProductEnum.COUPON_ID.getId());
            detail.setInfo(infoComment);
            comments.add(detail);
            detail = new SalesDetail();
        }
    }

    private SalesDetail populateSalesDetail(Product product, Integer sizeId)
    {
        SalesDetail detail = new SalesDetail();
        Integer productId = product.getId();
        if(SpecialsUtil.isFourPak(productId))
        {
            productId = 37;
        }
        detail.setProductId(productId);
        detail.setModifierOne(sizeId);
        detail.setQuantity(1);
        detail.setModifierFour(product.getReportId());

        return detail;
    }

    private SalesDetail populateSalesDetailForInfo(Integer productId, String info, Integer salesId)
    {
        SalesDetail detail = new SalesDetail();
        detail.setProductId(productId);
        detail.setInfo(info);
        detail.setOrderId(salesId);

        return detail;
    }

    private SalesDetail populateSalesDetailWithQuantity(Product product, Integer sizeId, Integer quantity)
    {
        SalesDetail detail = populateSalesDetail(product, sizeId);
        detail.setQuantity(quantity);

        return detail;
    }

    /**
     * Replicates the actual order. Iterates over all items
     * (iq2_sales_order_details) in the order (iq2_sales_order) and creates a
     * sales object and a sales_detail object to be replicated.
     */
    private Map<Sales, List<SalesDetail>> replicate(Order order, List<OrderItem> items, String operator) throws DataAccessException
    {
        Map<Sales, List<SalesDetail>> salesMap = new HashMap<Sales, List<SalesDetail>>();
        // deal with long 120 char comments.
        Shop shop = (Shop) lookupDao.get(Shop.class, order.getShopId());
        Sales sales = new Sales(order, operator, shop.getCity());
        List<SalesDetail> details = new ArrayList<SalesDetail>();
        List<SalesDetail> commentDetails = handleOrderComments(sales, order);

        final String couponCode = order.getCouponCode();
        final Coupon coupon = couponDao.forCode(couponCode);
        Integer couponId = 0;
        if(null != coupon)
        {
            couponId = coupon.getId();
        }

        boolean hasMindShareOnionRingCoupon = false;
        boolean hasMindShareWedgieCoupon = false;
        boolean hasMindShareCouponBeenDealtWith = false;
        MindShareCouponEnum mindShareCoupon = null;
        if(ArrayUtils.contains(MindShareCouponEnum.MIND_SHARE_COUPONS, couponId))
        {
            mindShareCoupon = MindShareCouponEnum.valueForCouponId(couponId);
            if(mindShareCoupon.getRequiredProductId().equals(ProductEnum.ONION_RING.getId()))
            {
                hasMindShareOnionRingCoupon = true;
            }
            else
            {
                hasMindShareWedgieCoupon = true;
            }
        }

        int discount = 0;
        for(OrderItem item : items)
        {
            Product product = item.getProduct();
            Integer productId = product.getId();
            boolean singlePizza = ProductUtil.isSinglePizza(productId);
            singlePizza |= ArrayUtils.contains(GLUTEN_PIZZAS, productId);

            if(item.isBundle())
            {
                // get the discount associated with the bundle and create a new
                // detail for each item in the bundle.
                discount += bundleSalesDetailItem(details, item);
            }
            else
            {
                if(singlePizza)
                {
                    this.replicateSinglePizza(details, item);
                }
                else
                {
                    Integer quantity = item.getQuantity();
                    Integer sizeId = 0;
                    ProductCategory cat = product.getCategory();

                    SalesDetail salesDetail = populateSalesDetailWithQuantity(product, sizeId, quantity);
                    if(cat.getId().equals(ProductCategoryEnum.POP.getId()))
                    {
                        if(item.getSize().getId().equals(3))
                        {
                            log.debug("Initial prod id: " + productId + " new id: " + ProductUtil.POP_SIZE_MAPPING.get(productId));
                            productId = ProductUtil.POP_SIZE_MAPPING.get(productId);
                            // override productId
                            salesDetail.setProductId(productId);
                        }
                        else if(item.getSize().getId().equals(2))
                        {
                            quantity = item.getQuantity() * 6;
                            // override quantity
                            salesDetail.setQuantity(quantity);
                        }
                    }
                    // wedgie size is different
                    if(cat.getId().equals(ProductCategoryEnum.WEDGIE.getId()))
                    {
                        sizeId = item.getSize().getId();
                        // override size id
                        salesDetail.setModifierOne(sizeId);
                        //override product modifier 4 if mindshare coupon available and not dealt with yet
                        if(!hasMindShareCouponBeenDealtWith && hasMindShareWedgieCoupon)
                        {
                            if(sizeId.equals(1))
                            {
                                salesDetail.setModifierFour(null != mindShareCoupon ? mindShareCoupon.getReportId() : 75);
                                hasMindShareCouponBeenDealtWith = true;
                            }
                        }
                    }
                    else if(ProductEnum.ONION_RING.getId().equals(productId))
                    {
                        if(!hasMindShareCouponBeenDealtWith && hasMindShareOnionRingCoupon)
                        {
                            salesDetail.setModifierFour(null != mindShareCoupon ? mindShareCoupon.getReportId() : 74);
                            hasMindShareCouponBeenDealtWith = true;
                        }
                    }

                    if(!item.getChildren().isEmpty())
                    {
                        Map<Integer, Integer> dips = new HashMap<Integer, Integer>();
                        String info = "";
                        for(OrderItem child : item.getChildren())
                        {
                            Product cp = child.getProduct();
                            Integer cpId = cp.getId();
                            Integer cpCatId = cp.categoryId();
                            if(cpCatId.equals(ProductCategoryEnum.WING_DIP.getId()))
                            {
                                int childQuantity = child.getQuantity();
                                if(dips.containsKey(cpId))
                                {
                                    Integer count = dips.get(cpId) + childQuantity;
                                    dips.put(cpId, count);
                                }
                                else
                                {
                                    dips.put(cpId, child.getQuantity());
                                }
                            }
                            else
                            {
                                String temp = StringUtils.trimToEmpty(cp.getName());
                                if(child.getQuantity() > 1)
                                {
                                    temp = child.getQuantity() + "x" + temp;
                                }
                                info += " " + temp;
                            }
                        }
                        for(Integer dipId : dips.keySet())
                        {
                            Integer dipQuantity = dips.get(dipId);
                            Integer compCode = SalesDetailCompositionEnum.DIP.getCode();
                            salesDetail.addDipDetailComposition(new SalesDetailComposition(dipId, dipQuantity, compCode));
                        }

                        if(StringUtils.isNotBlank(info))
                        {
                            if(info.length() > INFO_FIELD_LENGTH)
                            {
                                parseProductComments(details, salesDetail, info, INFO_FIELD_LENGTH);
                            }
                            else
                            {
                                salesDetail.setInfo(info);
                            }
                        }
                    }
                    details.add(salesDetail);
                }
            }
        }

        if(discount > order.getTotal())
        {
            discount = order.getTotal();
        }
        sales.setDiscount(sales.getDiscount().add(convertToDecimal(discount)));
        UniqueCoupon uc = null;
        if(StringUtils.isNotBlank(couponCode))
        {
            uc = this.couponDao.findUniqueCouponForCode(couponCode);
            if(null != uc)
            {
                final Integer couponType = uc.getCouponType();

                if(couponType.equals(SpinToWinType.DELIVERY.getId()))
                {
                    BigDecimal calculatedDiscount = sales.getDiscount().add(convertToDecimal(order.getDeliveryCharge()));
                    sales.setDiscount(calculatedDiscount);
                    sales.setDeliveryCharge(BigDecimal.valueOf(2));
                }
                else
                {
                    sales.setDiscount(sales.getDiscount().add(convertToDecimal(SpinToWinType.discount(couponType))));
                }
            }
            else if(ArrayUtils.contains(MindShareCouponEnum.MIND_SHARE_COUPONS, couponId))
            {
                sales.setDiscount(BigDecimal.ZERO);
            }
        }
        details.addAll(commentDetails);

        salesMap.put(sales, details);

        return salesMap;
    }

    /**
     * Replicate the every day deal..
     *
     * @param details
     * @param item
     */
    private int replicateEveryDayDeal(List<SalesDetail> details, OrderItem item, Integer modifierFour)
    {
        int discount = 0;
        SalesDetail detail = null;
        for(OrderItem meal : item.getChildren())
        {
            Product mealProduct = meal.getProduct();

            detail = new SalesDetail();
            detail.setProductId(mealProduct.getId());
            detail.setQuantity(meal.getQuantity());
            detail.setModifierOne(meal.getSize().getId());
            if(null != modifierFour)
            {
                detail.setModifierFour(modifierFour);
            }
            if(ProductEnum.TWO_DOLLAR_OFF.isEqualToProductId(mealProduct.getId()))
            {
                detail.setModifierOne(1);
            }
            replicateProductCompositions(details, detail, meal);

            // replicating bundle could have other objects aside from pizzas and
            // wing meals.
            Integer mealCategoryId = mealProduct.getCategory().getId();
            if(POP.isEqualToCategoryId(mealCategoryId))
            {
                if(meal.getSize().getId().equals(3))
                {
                    detail.setProductId(ProductUtil.POP_SIZE_MAPPING.get(mealProduct.getId()));
                    detail.setModifierOne(0);
                    log.warn("Initial prod id: " + mealProduct.getId() + " new id: " + detail.getProductId());
                }
                else
                {
                    final Integer itemSizeId = item.getSize().getId();
                    final Integer itemProdId = item.getProduct().getId();
                    boolean sixPackExceptionSpecials = !(MEGA_MEAL_12.isEqualToSpecialId(itemProdId) || MEGA_MEAL_14.isEqualToSpecialId(itemProdId)
                        || DINNER_MOVIE_SMALL.isEqualToSpecialId(itemProdId) || DINNER_MOVIE_MEDIUM.isEqualToSpecialId(itemProdId)
                        || DINNER_MOVIE_LARGE.isEqualToSpecialId(itemProdId) || HOLIDAY_HELPER.isEqualToSpecialId(itemProdId)
                        || SpecialEnum.FAN_FAVOURITE.isEqualToSpecialId(itemProdId) || SpecialEnum.D_AND_M.isEqualToSpecialId(itemProdId) || SpecialEnum.PLENTY_FOR_20.isEqualToSpecialId(itemProdId));
                    if(itemSizeId.equals(2) && sixPackExceptionSpecials)
                    {
                        detail.setQuantity(item.getQuantity() * 6);
                    }
                }
                detail.setModifierOne(0);
            }

            details.add(detail);
        }

        return discount;
    }

    /**
     * @param children
     * @return
     */
    private int replicateFourPak(List<OrderItem> children, List<SalesDetail> details, int modifierFour)
    {
        int discount = 0;
        for(OrderItem item : children)
        {
            Product p = item.getProduct();
            Integer productId = p.getId();
            ProductCategory cat = p.getCategory();
            Integer catId = cat.getId();
            SalesDetail salesDetail = new SalesDetail();
            salesDetail.setProductId(p.getId());
            salesDetail.setQuantity(item.getQuantity());

            salesDetail.setModifierFour(modifierFour);

            boolean isPizzaMealOrWedgie = ProductCategoryEnum.WEDGIE.isEqualToCategoryId(catId) || ProductCategoryEnum.PIZZA.isEqualToCategoryId(catId) || ProductCategoryEnum
                .WING_MEAL.isEqualToCategoryId(catId);
            if(isPizzaMealOrWedgie)
            {
                salesDetail.setModifierOne(item.getSize().getId());
            }
            else if(ProductEnum.CHOCOLATE_CHIP_COOKIE.isEqualToProductId(productId))
            {
                salesDetail.setModifierTwo(0);
                salesDetail.setModifierThree(0);
            }
            else if(ProductEnum.TWO_DOLLAR_OFF.isEqualToProductId(productId))
            {
                salesDetail.setModifierOne(1);
            }

            if(!item.getChildren().isEmpty())
            {
                replicateProductCompositions(details, salesDetail, item);
            }
            details.add(salesDetail);
        }

        return discount;
    }

    public void replicateOrder(Order order, List<OrderItem> items, String operator)
    {
        // replicate the order
        Map<Sales, List<SalesDetail>> replicatedOrders = replicate(order, items, operator);
        Iterator<Sales> repIter = replicatedOrders.keySet().iterator();
        Sales sales = null;
        while (repIter.hasNext())
        {
            sales = repIter.next();
            List<SalesDetail> details = replicatedOrders.get(sales);
            sales = this.saveSales(sales);
            this.saveSalesDetails(details, sales.getId());
        }
        log.warn("Order replicated: " + sales.getId());
        // write to iqq.
        Iqq iqq = new Iqq();
        iqq.setOrderId(sales.getId());
        iqq.setCustomerId(order.getCustomer().getPhone());
        iqq.setShopId(sales.getShopId());
        iqq.setRecordTypeId(Integer.valueOf(1));
        // populate iqq
        this.convDao.saveIqq(iqq);
    }

    public void replicateOrder(Order order, List<OrderItem> items, String operator, Coupon coupon, UniqueCoupon uc)
    {
        final String couponCode = order.getCouponCode();
        Map<Sales, List<SalesDetail>> replicatedOrders = replicate(order, items, operator);
        Sales sales = null;

        for(Sales theSale : replicatedOrders.keySet())
        {
            List<SalesDetail> details = replicatedOrders.get(theSale);
            sales = this.saveSales(theSale);
            this.saveSalesDetails(details, sales.getId());
        }

        Integer salesId = sales.getId();

        if(StringUtils.isNotBlank(couponCode) && coupon != null)
        {
            Integer couponId = coupon.getId();
            if(ArrayUtils.contains(MindShareCouponEnum.MIND_SHARE_COUPONS, couponId))
            {
                MindShareCouponEnum msCoupon = MindShareCouponEnum.valueForCouponId(couponId);
                Product msProduct = (Product) this.orderDao.get(Product.class, msCoupon.getProductId());
                SalesDetail mindShareDetail = populateSalesDetailForInfo(msCoupon.getProductId(), coupon.getName(), salesId);
                mindShareDetail.setModifierFour(msProduct.getReportId());
                this.repDao.save(mindShareDetail);
            }
            else if(ArrayUtils.contains(ProductConstants.ONE_OFF_COUPONS, couponCode))
            {
                Calendar businessDate = this.orderDao.businessDate();
                Calendar endDate = Calendar.getInstance();
                endDate.set(Calendar.YEAR, businessDate.get(Calendar.YEAR));
                endDate.set(Calendar.MONTH, businessDate.get(Calendar.MONTH));
                endDate.set(Calendar.DATE, businessDate.get(Calendar.DATE));
                endDate.add(Calendar.DATE, -1);
                coupon.setEndDate(endDate);
                this.couponDao.save(coupon);
            }
            else
            {
                Integer couponProductId = ProductEnum.COUPON_ID.getId();
                if(couponCode.equals("FB73C513"))
                {
                    couponProductId = ProductEnum.FIVE_DOLLAR_FB_COUPON.getId();
                    sales.setDiscount(BigDecimal.ZERO);
                }
                else if(couponCode.equals(ProductConstants.FIVE_DOLLAR_OFF_COUPON))
                {
                    couponProductId = ProductEnum.FIVE_DOLLAR_GIFT_CARD.getId();
                    sales.setDiscount(BigDecimal.ZERO);
                }
                else if(couponCode.equals(ProductConstants.FREE_DIP_COUPON))
                {
                    couponProductId = ProductEnum.FREE_DIP_COUPON.getId();
                    sales.setDiscount(BigDecimal.ZERO);
                }
                else if(couponCode.equals(ProductConstants.FUNNEL_CAKE_COUPON))
                {
                    couponProductId = ProductEnum.FUNNEL_CAKE_COUPON.getId();
                    sales.setDiscount(BigDecimal.ZERO);
                }
                SalesDetail detail = populateSalesDetailForInfo(couponProductId, coupon.getName(), salesId);
                this.repDao.save(detail);
            }
        }
        if(StringUtils.isNotBlank(couponCode) && uc != null)
        {
            String info = null;
            Integer couponId = uc.getCouponType();
            SpinToWinPrize prize = this.orderDao.prizeForCouponType(couponId);
            if(StringUtils.isNotEmpty(prize.getCouponDescription()))
            {
                info = uc.getCode() + " " + prize.getCouponDescription();
            }
            if(prize.getId().equals(SpinToWinType.DELIVERY.getId()))
            {
                sales.setDeliveryCharge(BigDecimal.valueOf(2.00));
            }

            sales = this.saveSales(sales);
            salesId = sales.getId();
            SalesDetail detail = populateSalesDetailForInfo(ProductEnum.COUPON_ID.getId(), info, salesId);
            this.repDao.save(detail);
        }

        if(order.isLunchDiscountable())
        {
            SalesDetail detail = populateSalesDetailForInfo(ProductEnum.COUPON_ID.getId(), "10% LUNCH DISCOUNT", salesId);
            this.repDao.save(detail);
        }
        log.warn("Order replicated: " + sales.getId());
        // write to iqq.
        Iqq iqq = new Iqq();
        iqq.setOrderId(sales.getId());
        iqq.setCustomerId(order.getCustomer().getPhone());
        iqq.setShopId(sales.getShopId());
        iqq.setRecordTypeId(Integer.valueOf(1));
        // populate iqq
        this.convDao.saveIqq(iqq);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.service.ReplicationManager#isReplicate()
     */
    public boolean isReplicate()
    {
        String value = StringUtils.trimToEmpty(this.repDao.infoValueForVariable("replicate"));
        if(value.equals("1"))
        {
            return true;
        }

        return false;
    }

    /**
     * Replicates a pizza's composition (sauce, crust, toppings).
     *
     * @param details
     * @param detail
     * @param meal
     */
    private void replicateProductCompositions(List<SalesDetail> details, SalesDetail detail, OrderItem meal)
    {
        String toppings = "";

        Map<Integer, Integer> dips = new HashMap<Integer, Integer>();
        for(OrderItem child : meal.getChildren())
        {
            Product childProduct = child.getProduct();
            Integer productId = childProduct.getId();
            Integer childCatId = childProduct.getCategory().getId();
            if(childCatId.equals(ProductCategoryEnum.CRUST.getId()))
            {
                detail.setModifierTwo(Integer.valueOf(ToppingMappingEnum.codeForId(productId)));
            }
            else if(childCatId.equals(ProductCategoryEnum.SAUCE.getId()))
            {
                if(childProduct.getId().equals(207))
                {
                    detail.setInfo("NO SAUCE");
                    detail.setModifierThree(1);
                }
                else
                {
                    detail.setModifierThree(Integer.valueOf(ToppingMappingEnum.codeForId(productId)));
                }
            }
            else if(childCatId.equals(ProductCategoryEnum.TOPPING.getId()))
            {
                for(int i = 0; i < child.getQuantity(); i++)
                {
                    toppings += ToppingMappingEnum.codeForId(productId);
                }
            }
            else if(childCatId.equals(ProductCategoryEnum.WING_DIP.getId()))
            {
                int quantity = child.getQuantity();
                if(dips.containsKey(productId))
                {
                    Integer count = dips.get(productId) + quantity;
                    dips.put(productId, count);
                }
                else
                {
                    dips.put(productId, child.getQuantity());
                }
            }
        }
        if(StringUtils.isNotBlank(toppings))
        {
            char[] topArray = toppings.toCharArray();
            Arrays.sort(topArray);

            detail.setAddOns(new String(topArray));
        }
        Integer compositionCode = DIP.getCode();
        Integer mealCategoryId = meal.getProduct().getCategory().getId();
        if(dips.isEmpty() && ProductCategoryEnum.WING_MEAL.isEqualToCategoryId(mealCategoryId))
        {
            dips.put(ProductEnum.NO_DIPS_PRODUCT_ID.getId(), 1);
            compositionCode = NO_DIP.getCode();
        }
        for(Integer dipId : dips.keySet())
        {
            Integer quantity = dips.get(dipId);
            detail.addDipDetailComposition(new SalesDetailComposition(dipId, quantity, compositionCode));
        }
    }

    private void replicateSinglePizza(List<SalesDetail> details, OrderItem meal)
    {
        SalesDetail detail = null;
        Product product = meal.getProduct();
        detail = populateSalesDetailWithQuantity(product, meal.getSize().getId(), meal.getQuantity());
        replicateProductCompositions(details, detail, meal);

        details.add(detail);
    }

    private void replicateSinglePopPack(List<SalesDetail> details, OrderItem item)
    {
        SalesDetail detail = null;
        Integer modifierFour = item.getProduct().getReportId();
        for(OrderItem meal : item.getChildren())
        {
            Product mealProduct = meal.getProduct();
            detail = populateSalesDetailWithQuantity(mealProduct, meal.getSize().getId(), meal.getQuantity());
            // override modifier four.
            detail.setModifierFour(modifierFour);
            replicateProductCompositions(details, detail, meal);
            details.add(detail);
        }
    }

    private void replicateTwoDipsSpecial(List<SalesDetail> details, OrderItem item)
    {
        SalesDetail detail = null;
        Integer modifierFour = item.getProduct().getReportId();
        for(OrderItem dip : item.getChildren())
        {
            Product dipProduct = dip.getProduct();
            detail = populateSalesDetailWithQuantity(dipProduct, dip.getSize().getId(), dip.getQuantity());
            // override modifier four.
            detail.setModifierFour(modifierFour);
            replicateProductCompositions(details, detail, dip);
            details.add(detail);
        }
    }

    /**
     * Replicate Box of wings.
     *
     * @param details
     * @param item
     */
    private void replicateWingBox(List<SalesDetail> details, OrderItem item)
    {
        SalesDetail detail = new SalesDetail();
        Integer size = item.getSize().getId();
        // determine size of wing box.
        boolean isWingMeal = SpecialEnum.WING_BOX.isEqualToSpecialId(item.getProduct().getId());
        Integer updatedProductId = ProductEnum.EIGHTY_PIECE_WING_BOX.getId();
        ;
        if(size == 1)
        {
            if(isWingMeal)
            {
                updatedProductId = ProductEnum.TWENTY_PIECE_WING_BOX.getId();
            }
            else
            {
                updatedProductId = ProductEnum.TWENTY_PIECE_CHICKEN_BITES.getId();
            }
        }
        else if(size == 2)
        {
            if(isWingMeal)
            {
                updatedProductId = ProductEnum.FORTY_PIECE_WING_BOX.getId();
            }
            else
            {
                updatedProductId = ProductEnum.FORTY_PIECE_CHICKEN_BITES.getId();
            }
        }

        detail.setProductId(updatedProductId);
        detail.setQuantity(1);
        // a map of wing flavors and the amount.
        Map<Integer, Integer> flavors = new HashMap<Integer, Integer>();
        List<SalesDetailComposition> wingFlavorDetails = new ArrayList<SalesDetailComposition>();
        List<SalesDetailComposition> dipDetails = new ArrayList<SalesDetailComposition>();

        for(OrderItem child : item.getChildren())
        {
            Product cp = child.getProduct();
            Integer childCatId = cp.getCategory().getId();
            Integer cpId = cp.getId();
            if(WING_DIP.isEqualToCategoryId(childCatId))
            {
                Integer compositionCode = DIP.getCode();
                dipDetails.add(new SalesDetailComposition(cpId, child.getQuantity(), compositionCode));
            }
            else if(WING_FLAVOR.isEqualToCategoryId(childCatId))
            {
                Integer quantity = child.getQuantity() * 20;
                // if flavor has already been seen add interval of 20 to it.
                if(flavors.containsKey(cpId))
                {
                    Integer count = flavors.get(cpId) + quantity;
                    flavors.put(cpId, count);
                }
                else
                {
                    // initiall flavor size is 20
                    flavors.put(cpId, quantity);
                }
            }
        }
        for(Integer productId : flavors.keySet())
        {
            Integer quantity = flavors.get(productId);
            Integer compositionCode = SalesDetailCompositionEnum.WING_FLAVOR.getCode();
            wingFlavorDetails.add(new SalesDetailComposition(productId, quantity, compositionCode));
        }
        if(dipDetails.isEmpty())
        {
            dipDetails.add(new SalesDetailComposition(ProductEnum.NO_DIPS_PRODUCT_ID.getId(), 1, NO_DIP.getCode()));
        }
        detail.addAllDipDetailCompositions(dipDetails);
        detail.addAllWingDetailCompositions(wingFlavorDetails);

        details.add(detail);
    }

    private Sales saveSales(Sales sale)
    {
        return (Sales) this.repDao.save(sale);
    }

    /**
     * Saves the sales detail record(s)
     */
    private void saveSalesDetails(List<SalesDetail> details, Integer orderId)
    {
        for(SalesDetail detail : details)
        {
            detail.setOrderId(orderId);
            SalesDetail dbSalesDetail = (SalesDetail) this.repDao.save(detail);

            Integer salesDetailId = dbSalesDetail.getId();

            for(SalesDetailComposition wingComp : detail.getWingDetailComposition())
            {
                wingComp.setSalesDetailId(salesDetailId);
                this.repDao.save(wingComp);
            }
            for(SalesDetailComposition dipComp : detail.getDipDetailComposition())
            {
                dipComp.setSalesDetailId(salesDetailId);
                this.repDao.save(dipComp);
            }
        }
    }
}