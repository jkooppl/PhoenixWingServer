package com.pizza73.service.dwr.impl;

import com.pizza73.model.Cart;
import com.pizza73.model.Coupon;
import com.pizza73.model.Menu;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.OrderItem;
import com.pizza73.model.Product;
import com.pizza73.model.ProductCategory;
import com.pizza73.model.ProductComposition;
import com.pizza73.model.ProductConstants;
import com.pizza73.model.ProductDetail;
import com.pizza73.model.ProductSize;
import com.pizza73.model.Shop;
import com.pizza73.model.UniqueCoupon;
import com.pizza73.model.enums.DeliveryMethod;
import com.pizza73.model.enums.MenuId;
import com.pizza73.model.enums.MindShareCouponEnum;
import com.pizza73.model.enums.OrderOrigin;
import com.pizza73.model.enums.PizzaSauceEnum;
import com.pizza73.model.enums.ProductCategoryEnum;
import com.pizza73.model.enums.ProductEnum;
import com.pizza73.model.enums.SpecialEnum;
import com.pizza73.model.enums.WingFlavorEnum;
import com.pizza73.service.CouponManager;
import com.pizza73.service.LookupManager;
import com.pizza73.service.OrderManager;
import com.pizza73.service.PricingManager;
import com.pizza73.service.UserManager;
import com.pizza73.service.dwr.CartManager;
import com.pizza73.util.CityUtil;
import com.pizza73.util.CoreStoreHoursUtil;
import com.pizza73.util.ProductUtil;
import com.pizza73.util.SpecialsUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.pizza73.model.ProductConstants.CART;
import static com.pizza73.model.ProductConstants.CHIPS;
import static com.pizza73.model.ProductConstants.DEFAULT_CHICKEN_BITE_ID;
import static com.pizza73.model.ProductConstants.DEFAULT_WING_ID;
import static com.pizza73.model.ProductConstants.GLUTEN_PIZZAS;
import static com.pizza73.model.ProductConstants.SHRIMP_ID;
import static com.pizza73.model.enums.OrderOrigin.MOBILE;
import static com.pizza73.model.enums.ProductCategoryEnum.GLUTEN_FREE_PIZZA;
import static com.pizza73.model.enums.ProductCategoryEnum.PIZZA;
import static com.pizza73.model.enums.ProductCategoryEnum.POP;
import static com.pizza73.model.enums.ProductCategoryEnum.TOPPING;
import static com.pizza73.model.enums.ProductCategoryEnum.WING_DIP;
import static com.pizza73.model.enums.ProductCategoryEnum.WING_MEAL;

@Service("cartManager")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CartManagerImpl implements CartManager
{
    private static Logger log = Logger.getLogger(CartManagerImpl.class);

    private CouponManager couponMgr = null;

    private LookupManager lookupMgr = null;

    @SuppressWarnings("unused")
    private OrderManager orderMgr = null;

    private PricingManager priceMgr = null;

    private UserManager userMgr = null;

    private void addItemToCart(final Cart cart, final Menu menuItem, final OrderItem item, final ServletContext context, final boolean shrimp) throws ServletException, IOException
    {
        Integer categoryId = categoryId(menuItem);
        if(PIZZA.isEqualToCategoryId(categoryId) || GLUTEN_FREE_PIZZA.isEqualToCategoryId(categoryId))
        {
            addPizzaToCart(context, menuItem, cart, item, OrderOrigin.ONLINE);
        }
        else if(ProductCategoryEnum.WING_MEAL.getId().equals(categoryId))
        {
            OrderItem bundle = cart.findUnfinishedBundle(item.getSize().getId());
            // if bundle is null it means that the cart has expired.
            if(bundle != null)
            {
                bundle.addChild(item);
                cart.bundleComplete(bundle);
            }
        }
        else if(ProductCategoryEnum.WING_BOX.getId().equals(categoryId) || SpecialEnum.CHICKEN_BITES.isEqualToSpecialId(menuItem.getProduct().getId()))
        {
            addWingBoxToCart(shrimp, cart, item, true);
        }
        else if(ProductCategoryEnum.SPECIAL.getId().equals(categoryId))
        {
            item.setBundle(true);
            final Product product = menuItem.getProduct();
            cart.bundleComplete(item);
            populateBundle(context, cart, item, product);
            cart.bundleComplete(item);
            Integer productId = product.getId();
            if(SpecialEnum.HOLIDAY_HELPER.isEqualToSpecialId(productId))
            {
                if(CoreStoreHoursUtil.isLunchtimeForSpecial(CityUtil.isBC(menuItem.getMenuId())))
                {
                    addTwoDollarOff(cart, item);
                }
            }
            else if(SpecialEnum.FOUR_PAK.isEqualToSpecialId(productId))
            {
                if(CoreStoreHoursUtil.isLunchtimeForSpecial(CityUtil.isBC(menuItem.getMenuId())))
                {
                    addTwoDollarOff(cart, item);
                }

                final Menu twoTopper = this.lookupMgr.menuItem(cart.getMenuId().getId(), ProductEnum.TWO_TOPPER.getId(), 1);
                final OrderItem twoTopperItem = populateOrderItem(twoTopper, cart);
                addPizzaDefaults(cart, context, menuItem.getMenuId(), twoTopperItem);
                item.addChild(twoTopperItem);
            }

            Collections.sort(item.getChildren());
            cart.addItem(item);
        }
        else
        {
            cart.addItem(item);
        }
    }

    private void addItemToParent(Menu menuItem, final String parentId, Cart cart, ServletContext context) throws ServletException, IOException
    {
        final OrderItem parent = cart.getItem(parentId);

        Integer categoryId = menuItem.getCategoryId();
        Integer productId = menuItem.getProduct().getId();

        final OrderItem item = populateOrderItem(menuItem, cart);
        Integer pId = parent.getProduct().getId();
        if(PIZZA.isEqualToCategoryId(categoryId) || WING_MEAL.isEqualToCategoryId(categoryId))
        {
            // verify max pizzas has not been met.
            boolean addItem = true;
            if(!SpecialsUtil.isFourPak(Integer.valueOf(pId)))
            {
                int validChildItemCount = parent.maxProductsAllowedForCategory(ProductCategoryEnum.PIZZA.getId());
                Integer childItemCount = parent.childCountForCategory(categoryId);
                if(childItemCount >= validChildItemCount)
                {
                    addItem = false;
                }
            }
            if(addItem)
            {
                parent.addChild(item);
                if(ProductCategoryEnum.PIZZA.getId().equals(categoryId(menuItem)))
                {
                    // add defaults
                    addPizzaDefaults(cart, context, menuItem.getMenuId(), item);
                }
            }
        }
        else if(POP.isEqualToCategoryId(categoryId) || WING_DIP.isEqualToCategoryId(categoryId) || ArrayUtils.contains(CHIPS, productId))
        {
            OrderItem tempItem = parent.getChildForProductId(productId);
            if(null != tempItem)
            {
                tempItem.setQuantity(tempItem.getQuantity() + 1);
            }
            else
            {
                final OrderItem newItem = populateOrderItem(menuItem, cart);
                parent.addChild(newItem);
            }
        }
        else
        {
            parent.addChild(item);
        }

        cart.bundleComplete(parent);
    }

    @Override
    public String addOption(final HttpSession session, final String itemId, final String childId, final String menuId) throws ServletException, IOException
    {
        final Cart cart = getCart(session);
        if(!cart.isEmpty())
        {
            OrderItem orderItem = cart.getItem(itemId);
            OrderItem child = orderItem.getChild(childId);
            if(child != null)
            {
                child.setQuantity(child.getQuantity() + 1);
            }
            else
            {
                // check to see if they used the drop down
                final Menu menuItem = this.lookupMgr.getMenuItem(menuId);
                child = orderItem.getChildForProductId(menuItem.getProduct().getId());
                if(child != null)
                {
                    child.setQuantity(child.getQuantity() + 1);
                }
                else
                {
                    child = this.populateOrderItem(menuItem, cart);
                    // override size to parent
                    child.setSize(orderItem.getSize());
                    orderItem.addChild(child);
                }
            }
            if(orderItem.isBundle())
            {
                cart.bundleComplete(orderItem);
            }
        }

        session.setAttribute(CART, cart);

        return htmlCart(session);
    }

    @Override
    public String addOrderItem(final HttpSession session, String menuId, final String parentId) throws ServletException, IOException
    {
        final ServletContext context = session.getServletContext();
        final Cart cart = getCart(session);

        boolean shrimp = false;
        final int index = menuId.indexOf(':');
        if(index > 0)
        {
            shrimp = true;
            menuId = menuId.substring(0, index);
        }

        final Menu menuItem = this.lookupMgr.getMenuItem(menuId);

        if(null != parentId)
        {
            addItemToParent(menuItem, parentId, cart, context);
        }
        else
        {
            final OrderItem item = populateOrderItem(menuItem, cart);
            addItemToCart(cart, menuItem, item, context, shrimp);
        }

        session.setAttribute(CART, cart);

        return htmlCart(session);
    }

    @Override
    public void addGenericOrderItem(Cart cart, Menu menuItem)
    {
        final OrderItem item = populateOrderItem(menuItem, cart);
        cart.addItem(item);
    }

    // TODO: not used? remove if not.
    @Override
    public Cart calculateCost(final HttpSession session)
    {
        final Cart cart = getCart(session);
        calculateCosts(cart);

        return cart;
    }

    @Override
    public String deleteOrderItem(final HttpSession session, final String itemId) throws ServletException, IOException
    {
        final Cart cart = getCart(session);
        if(!cart.isEmpty())
        {
            final OrderItem itemForDeletion = cart.getItem(itemId);
            if(null != itemForDeletion)
            {
                final OrderItem parent = itemForDeletion.getParent();
                // means we have an every day deal bundle.
                if(null != parent)
                {
                    Integer parentProductId = parent.getProduct().getId();
                    if(SpecialEnum.EVERYDAY_DEAL.isEqualToSpecialId(parentProductId))
                    {
                        int pizzaCount = parent.childCountForCategory(PIZZA.getId());
                        if(pizzaCount == 1)
                        {
                            cart.removeItem(parent);
                        }
                        else
                        {
                            parent.removeChild(itemForDeletion);
                            OrderItem unfinishedBundle = cart.findUnfinishedBundle(parent.getSize().getId());
                            if(null != unfinishedBundle)
                            {
                                parent.addChild(unfinishedBundle.getChildren().get(0));
                                cart.removeItem(unfinishedBundle);
                            }
                        }
                        Collections.sort(parent.getChildren());
                    }
                    else
                    {
                        // remove the item
                        parent.removeChild(itemForDeletion);
                        Collections.sort(parent.getChildren());
                    }

                    cart.bundleComplete(parent);
                }
                else
                {
                    cart.removeItem(itemForDeletion);
                }
            }
        }

        session.setAttribute(CART, cart);

        return htmlCart(session);
    }

    @Override
    public String deliveryCharge(final HttpSession session, final String deliveryMethod, final String cityId) throws ServletException, IOException
    {
        final Cart cart = getCart(session);

        if(!cart.isEmpty())
        {
            if(deliveryMethod.equals(DeliveryMethod.PICK_UP.getShortValue() + ""))
            {
                cart.getCustomer().setDelivery(DeliveryMethod.PICK_UP);
                cart.setDeliveryCharge(0);
            }
            else
            {
                cart.getCustomer().setDelivery(DeliveryMethod.DELIVER);
                final Integer delivery = updateDeliveryCharge(cityId);
                cart.setDeliveryCharge(delivery);
            }
        }

        session.setAttribute(CART, cart);

        return htmlTotal(session);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pizza73.service.dwr.CartManager#emptyCart(javax.servlet.http.HttpSession
     * )
     */
    // TODO: not used? check and remove.
    @Override
    public Cart emptyCart(final HttpSession session)
    {
        final Cart cart = getCart(session);
        if(!cart.isEmpty())
        {
            cart.empty();
        }
        calculateCosts(cart);

        return cart;
    }

    @Override
    public Cart getCart(final HttpSession session)
    {
        Cart cart = (Cart) session.getAttribute(CART);

        if(cart == null)
        {
            cart = new Cart();
        }

        if(cart.getMenuId() == null)
        {
            cart.setMenuId(MenuId.AB_WEB);
        }

        return cart;
    }

    @Override
    public String getDisplayShop(final HttpSession session)
    {
        final Cart cart = getCart(session);
        if(!cart.isEmpty())
        {
            if(cart.getCustomer().getShopId() != null)
            {
                return cart.getCustomer().getShopId().toString();
            }
        }

        return null;
    }

    @Override
    public String htmlCart(final HttpSession session) throws ServletException, IOException
    {
        final WebContext wctx = WebContextFactory.get();

        final Cart cart = getCart(session);

        calculateCosts(cart);

        return wctx.forwardToString("/WEB-INF/pages/order/orderItems.jsp");
    }

    private void calculateCosts(final Cart cart)
    {
        this.priceMgr.calculateCost(cart);
    }

    @Override
    public String htmlOrderButtons(final HttpSession session) throws ServletException, IOException
    {
        final WebContext wctx = WebContextFactory.get();

        return wctx.forwardToString("/WEB-INF/pages/cartButtons.jsp");
    }

    @Override
    public String htmlTotal(final HttpSession session) throws ServletException, IOException
    {
        final WebContext wctx = WebContextFactory.get();

        final Cart cart = getCart(session);
        calculateCosts(cart);

        return wctx.forwardToString("/WEB-INF/pages/order/total.jsp");
    }

    @Override
    public boolean isCartEmpty(final HttpSession session)
    {
        final Cart cart = getCart(session);
        return cart.isEmpty();
    }

    @Override
    public OrderItem itemForId(final HttpSession session, final String id)
    {
        OrderItem item = null;

        final Cart cart = getCart(session);
        if(!cart.isEmpty())
        {
            item = cart.getItem(id);
        }

        return item;
    }

    @Override
    public String removeOption(final HttpSession session, final String itemId, final String optionId) throws ServletException, IOException
    {
        final Cart cart = getCart(session);
        OrderItem orderItem = null;
        OrderItem optionItem = null;
        if(!cart.isEmpty())
        {
            orderItem = cart.getItem(itemId);
            optionItem = orderItem.getChild(optionId);
            if(optionItem != null)
            {
                final int quantity = optionItem.getQuantity();
                if(quantity > 1)
                {
                    optionItem.setQuantity(quantity - 1);
                }
                else
                {
                    orderItem.removeChild(optionItem);
                }

                if(orderItem.isBundle())
                {
                    cart.bundleComplete(orderItem);
                }
            }
        }

        session.setAttribute(CART, cart);

        return htmlCart(session);
    }

    @Override
    @Autowired
    public void setLookupManager(@Qualifier("lookupManager") final LookupManager mgr)
    {
        this.lookupMgr = mgr;
    }

    @Override
    public void setNoMoreDips(final HttpSession session, final String itemId)
    {
        final Cart cart = getCart(session);
        if(!cart.isEmpty())
        {
            final OrderItem oi = cart.getItem(itemId);
            oi.setNoMoreDips(!oi.isNoMoreDips());
        }
        session.setAttribute(CART, cart);
    }

    @Override
    public void setParkedOrderSalt(final Cart cart, final Integer orderId, final ServletContext context)
    {
        this.setSalt(cart, orderId, true, context);
        UniqueCoupon uc = couponMgr.findUniqueCouponForRedeemedOrder(orderId);
        if(null != uc)
        {
            cart.setUc(uc);
        }

        calculateCosts(cart);
    }

    @Override
    public void setOrderCommentDeliveryTime(final HttpSession session, final boolean value)
    {
        final Cart cart = getCart(session);
        final OnlineCustomer oc = cart.getCustomer();
        oc.setCommentDeliveryTime(value);
    }

    @Override
    public void setOrderCommentDeliveryTimeContent(final HttpSession session, final String content)
    {
        final Cart cart = getCart(session);
        final OnlineCustomer oc = cart.getCustomer();
        oc.setCommentDeliveryTimeContent(content);
    }

    @Override
    public void setOrderCommentEasyCheese(final HttpSession session, final boolean value)
    {
        final Cart cart = getCart(session);
        final OnlineCustomer oc = cart.getCustomer();
        oc.setCommentEasyCheese(value);
    }

    @Override
    public void setOrderCommentEasySauce(final HttpSession session, final boolean value)
    {
        final Cart cart = getCart(session);
        final OnlineCustomer oc = cart.getCustomer();
        oc.setCommentEasySauce(value);
    }

    @Override
    public void setOrderCommentPickupTime(final HttpSession session, final boolean value)
    {
        final Cart cart = getCart(session);
        final OnlineCustomer oc = cart.getCustomer();
        oc.setCommentPickupTime(value);
    }

    @Override
    public void setOrderCommentPickupTimeContent(final HttpSession session, final String content)
    {
        final Cart cart = getCart(session);
        final OnlineCustomer oc = cart.getCustomer();
        oc.setCommentPickupTimeContent(content);
    }

    @Override
    public void setOrderCommentRemoveTopping(final HttpSession session, final boolean value)
    {
        final Cart cart = getCart(session);
        final OnlineCustomer oc = cart.getCustomer();
        oc.setCommentRemoveTopping(value);
    }

    @Override
    public void setOrderCommentRemoveToppingContent(final HttpSession session, final String content)
    {
        final Cart cart = getCart(session);
        final OnlineCustomer oc = cart.getCustomer();
        oc.setCommentRemoveToppingContent(content);
    }

    @Override
    public void setOrderCommentWellDone(final HttpSession session, final boolean value)
    {
        final Cart cart = getCart(session);
        final OnlineCustomer oc = cart.getCustomer();
        oc.setCommentWellDone(value);
    }

    @Override
    public void setSalt(final Cart cart, final Integer orderId, boolean parked, final ServletContext context)
    {
        cart.empty();
        cart.resetTempId();
        Integer menuId = cart.getMenuId().getId();
        if(null == menuId) // should only happen when it's an EDD
        {
            menuId = MenuId.AB_MOBILE.getId();
        }
        final List<OrderItem> parentItems = this.lookupMgr.parentOrderItemsForOrder(orderId);
        Boolean lunchTime = CoreStoreHoursUtil.isLunchtimeForSpecial(CityUtil.isBC(menuId));
        for(OrderItem item : parentItems)
        {
            final Product product = item.getProduct();
            Integer id = product.getId();
            final ProductCategory pCat = product.getCategory();

            final Integer categoryId = pCat.getId();
            if(ProductCategoryEnum.SPECIAL.isEqualToCategoryId(categoryId))
            {

                Menu menuItem = dbMenu(item, menuId);
                if(null != menuItem)
                {
                    item.setPizzasAllowed(menuItem.isPizzasAllowed());
                    item.setWingsAllowed(menuItem.isWingsAllowed());
                }
                // Deal with 4 pak
                if(SpecialEnum.FOUR_PAK.isEqualToSpecialId(id) || SpecialEnum.HOLIDAY_HELPER.isEqualToSpecialId(id))
                {
                    populateChildren(orderId, item, cart);
                    final Integer twoDollarOffCouponId = ProductEnum.TWO_DOLLAR_OFF.getId();
                    log.warn("IS Lunchtime: " + lunchTime);
                    boolean hasTwoDollar = item.hasChildForProductId(twoDollarOffCouponId);

                    log.warn("has two dollar: " + hasTwoDollar);
                    if(lunchTime)
                    {
                        if(!hasTwoDollar)
                        {
                            log.warn("adding two dollar coupon");
                            addTwoDollarOff(cart, item);
                        }
                    }
                    else
                    {
                        if(hasTwoDollar)
                        {
                            log.warn("removing two dollar coupon");
                            item.removeChild(twoDollarOffCouponId);
                        }
                    }

                    if(product.isActive())
                    {
                        item.setBundle(true);
                        cart.addItem(item);
                    }
                }
                else
                {
                    int dayOfWeek = this.lookupMgr.businessDate().get(Calendar.DAY_OF_WEEK);
                    boolean earlyWeek = (Calendar.MONDAY == dayOfWeek || Calendar.TUESDAY == dayOfWeek || Calendar.SUNDAY == dayOfWeek);
                    populateChildren(orderId, item, cart);
                    item.setBundle(true);
                    if(!product.isActive())
                    {
                        final Product everyDayDeal = (Product) context.getAttribute("EVERY_DAY_DEAL");
                        refreshEDD(item, cart, everyDayDeal);
                        id = SpecialEnum.EVERYDAY_DEAL.getId();
                    }
                    if(SpecialEnum.EVERYDAY_DEAL.isEqualToSpecialId(id))
                    {
                        item.setDrawTitle(false);
                    }

//                    if(SpecialEnum.AFTER_SCHOOL.isEqualToSpecialId(id))
//                    {
//                        if(!item.hasChildForProductId(ProductEnum.FIVE_DOLLAR_GIFT_CARD.getId()))
//                        {
//                            final Menu childMenuItem = this.lookupMgr.menuItem(cart.getMenuId().getId(),
//                                ProductEnum.FIVE_DOLLAR_GIFT_CARD.getId(), 0);
//                            final OrderItem childItem = populateOrderItem(childMenuItem, cart);
//                            item.addChild(childItem);
//                        }
//                    }

                    cart.addItem(item);
                }
                cart.bundleComplete(item);
            }
            else if(!product.isActive())
            {
                // do nothing.
            }
            else if(SpecialEnum.WING_BOX.isEqualToSpecialId(id) || SpecialEnum.CHICKEN_BITES.isEqualToSpecialId(id))
            {
                populateChildren(orderId, item, cart);
                item.setBundle(true);
                cart.bundleComplete(item);
                cart.addItem(item);
            }
            else
            {
                populateChildren(orderId, item, cart);
                item.setCompleteBundle(true);
                cart.addItem(item);
            }
        }

        calculateCosts(cart);
    }

    private void addTwoDollarOff(Cart cart, OrderItem item)
    {
        final Menu twoDollarCoupon = this.lookupMgr.menuItem(cart.getMenuId().getId(), ProductEnum.TWO_DOLLAR_OFF.getId(), 1);
        final OrderItem twoDollarItem = populateOrderItem(twoDollarCoupon, cart);
        item.addChild(twoDollarItem);
    }

    public void addTopLevelProduct(Cart cart, OrderItem item, Integer productId)
    {
        final Menu menu = this.lookupMgr.menuItem(cart.getMenuId().getId(), productId, 0);
        final OrderItem orderItem = populateOrderItem(menu, cart);
        item.addChild(orderItem);
    }

    @Override
    public Map<String, String> shopsForMunicipality(final HttpSession session, final String municipalityId)
    {
        final Map<String, String> shopMap = new TreeMap<String, String>();
        boolean roleSupreme = false;
        if(!municipalityId.equals("0"))
        {
            List<Shop> shops = this.lookupMgr.activeShopsForMunicipality(municipalityId);
            final Cart cart = (Cart) session.getAttribute("CART");
            if(cart != null && cart.getCustomer() != null)
            {
                final OnlineCustomer oc = cart.getCustomer();
                final Collection<GrantedAuthority> auths = oc.getAuthorities();
                if(null != auths)
                {
                    for(final GrantedAuthority grantedAuthority : auths)
                    {
                        if(grantedAuthority.getAuthority().equals("ROLE_SUPREME"))
                        {
                            roleSupreme = true;
                            shops = this.lookupMgr.allShopsForMunicipality(municipalityId);
                            shops.addAll(this.lookupMgr.allShopsForMunicipality("0"));
                            break;
                        }
                    }
                }
            }
            for(Shop shop : shops)
            {
                Integer shopId = shop.getId();
                boolean shop97 = shopId.equals(Integer.parseInt("97"));
                if(!roleSupreme &&  shop97)
                {
                    //don't do anything.
                }
                else if(shopId.equals(Integer.parseInt("99")) || shopId.equals(Integer.parseInt("98")) || shopId.equals(Integer.parseInt("96")) || shop97)
                {
                    shopMap.put(shop.getId() + "", shop.getShortName());
                }
                else
                {
                    shopMap.put(shop.getId() + "", shop.getWebAddress());
                }
            }
            shopMap.put("0", "-- select shop --");
        }

        return shopMap;
    }

    @Override
    public Integer updateDeliveryCharge(final String cityId)
    {
        Integer delivery = Integer.valueOf(350);
        if(null != cityId)
        {
            final List<Integer> deliveryCharges = this.lookupMgr.distinctDeliveryCostsForCity(cityId);
            if(deliveryCharges != null && deliveryCharges.size() == 1)
            {
                delivery = deliveryCharges.get(0);
            }
        }
        return delivery;
    }

    @Override
    public String updateOption(final HttpSession session, final String itemId, final String menuId) throws ServletException, IOException
    {
        final Cart cart = getCart(session);
        if(!cart.isEmpty())
        {
            OrderItem orderItem = cart.getItem(itemId);
            final Menu menuItem = this.lookupMgr.getMenuItem(menuId);
            if(orderItem.getChildren().isEmpty())
            {
                orderItem.setDisplayName(menuItem.getDisplayName());
                orderItem.setProduct(menuItem.getProduct());
                orderItem.setMenuId(menuItem.getId());
                orderItem.setSize(menuItem.getSize());
                Integer prodId = orderItem.getProduct().getId();
                boolean isExtra = ProductEnum.BREAD_STICK.isEqualToProductId(prodId) || ProductEnum.WEDGES.isEqualToProductId(prodId) ||
                    ProductEnum.ONION_RING.isEqualToProductId(prodId) || ProductEnum.FRIES.isEqualToProductId(prodId) || ProductEnum.POUTINE.isEqualToProductId(prodId) ||
                    ProductEnum.CHILLI.isEqualToProductId(prodId) || ProductEnum.WEDGIE_12.isEqualToProductId(prodId);
                if(isExtra)
                {
                    orderItem.setPrice(menuItem.getPrice());
                }
                orderItem.setPriceModifier(menuItem.getPriceModifier());
                orderItem.setEnviroLevy(menuItem.getEnviroLevy());
                orderItem.setDeposit(menuItem.getDeposit());
            }
            else if(orderItem.getProduct().getCategory().getId().equals(ProductCategoryEnum.WING_SIDE.getId()))
            {
                orderItem.setDisplayName(menuItem.getDisplayName());
                orderItem.setProduct(menuItem.getProduct());
                orderItem.setMenuId(menuItem.getId());
                orderItem.setPriceModifier(menuItem.getPriceModifier());
            }
            else
            {
                final OrderItem optionItem = getSubItem(orderItem, menuItem.getProduct().getCategory().getName());

                optionItem.setProduct(menuItem.getProduct());
                optionItem.setDisplayName(menuItem.getDisplayName());
                optionItem.setPriceModifier(menuItem.getPriceModifier());
            }
            calculateCosts(cart);
        }

        session.setAttribute(CART, cart);

        return htmlTotal(session);
    }

    private OrderItem getSubItem(final OrderItem orderItem, final String categoryName)
    {
        OrderItem subItem = null;

        if(orderItem != null)
        {
            for(OrderItem temp : orderItem.getChildren())
            {

                final Product p = temp.getProduct();
                if(categoryName.equals(p.getCategory().getName()))
                {
                    subItem = temp;
                    break;
                }
            }
        }
        else
        {
            if(log.isDebugEnabled())
            {
                log.debug("can't find subItem for category: " + categoryName);
            }
        }
        return subItem;
    }

    @Override
    public String updatePrice(final HttpSession session, final String cityId) throws ServletException, IOException
    {
        final Cart cart = getCart(session);
        if(!cityId.equals("0"))
        {
            if(!cart.isEmpty())
            {
                // update delivery charge
                if(cart.getCustomer().getDelivery().equals(DeliveryMethod.DELIVER))
                {
                    final Integer delivery = updateDeliveryCharge(cityId);
                    cart.setDeliveryCharge(delivery);
                }
            }
            if(cityId.equals("9"))
            {
                cart.setMenuId(MenuId.BC_WEB);
            }
            else
            {
                cart.setMenuId(MenuId.AB_WEB);
            }
        }

        session.setAttribute(CART, cart);

        return htmlTotal(session);
    }

    @Override
    public String updateQuantity(final HttpSession session, final String itemId, final String quantity) throws ServletException, IOException
    {
        final Cart cart = getCart(session);
        if(!cart.isEmpty())
        {
            final OrderItem oi = cart.getItem(itemId);
            oi.setQuantity(Integer.valueOf(quantity));
        }

        session.setAttribute(CART, cart);

        return htmlTotal(session);
    }

    @Override
    public String validateCoupon(final HttpSession session, final String couponCode)
    {
        final Cart cart = getCart(session);

        validateCouponServer(couponCode, cart);

        String errorMessage = cart.getErrorMessage();

        return errorMessage;
    }

    @Override
    public String validateCouponServer(final String couponCode, final Cart cart)
    {
        final OnlineCustomer oc = cart.getCustomer();

        if(oc.getId() == null)
        {
            cart.setCoupon(null);
            cart.setUc(null);
            oc.setCouponCode("");
            cart.setErrorMessage("*In order to redeem online coupons you must have an online account.");
        }
        else
        {
            if(StringUtils.isNotBlank(couponCode))
            {
                Coupon coupon = this.couponMgr.forCode(StringUtils.upperCase(couponCode));
                if(coupon != null)
                {
                    final Integer couponId = coupon.getId();
                    if(ArrayUtils.contains(MindShareCouponEnum.MIND_SHARE_COUPONS, couponId))
                    {
                        String errorMessage = "";
                        if(!this.couponMgr.validateCoupon(coupon, cart))
                        {
                            errorMessage = coupon.getErrorMessage();
                            coupon = null;
                        }
                        else if(ArrayUtils.contains(MindShareCouponEnum.MIND_SHARE_ONION_RING_COUPONS, couponId))
                        {
                            boolean hasOnionRing = cart.hasProduct(ProductEnum.ONION_RING.getId());
                            if(!hasOnionRing)
                            {
                                errorMessage = coupon.getErrorMessage();
                                coupon = null;
                            }
                        }
                        cart.setCoupon(coupon);
                        cart.setErrorMessage(errorMessage);
                        oc.setCouponCode(couponCode);
                    }
                    else
                    {
                        String errorMessage = "";
                        if(!this.couponMgr.couponRedeemed(oc, coupon.getId()))
                        {
                            if(!coupon.isOnlineRedeemable())
                            {
                                errorMessage = "This coupon(" + couponCode + ") must be redeemed in person instead " //
                                    + "online.  Please show it to the driver/shop of cashier."; //
                                coupon = null;
                            }
                            else if(!this.couponMgr.validateCoupon(coupon, cart))
                            {
                                errorMessage = coupon.getErrorMessage();
                                coupon = null;
                            }
                            cart.setCoupon(coupon);
                            cart.setErrorMessage(errorMessage);
                            oc.setCouponCode(couponCode);
                        }
                        else
                        {
                            boolean multiUseCoupon = ProductConstants.FIVE_DOLLAR_OFF_COUPON.equals(couponCode) || ProductConstants.FREE_DIP_COUPON.equals(couponCode)
                                || ProductConstants.FUNNEL_CAKE_COUPON.equals(couponCode);
                            if(!multiUseCoupon)
                            {
                                cart.setErrorMessage("*Our records indicate that you have already used this coupon " //
                                    + "(COUPON ID: " + couponCode + ").  It can only be used once."); //
                            }
                            else
                            {
                                if(!this.couponMgr.validateCoupon(coupon, cart))
                                {
                                    errorMessage = coupon.getErrorMessage();
                                    coupon = null;
                                }
                                cart.setCoupon(coupon);
                                cart.setErrorMessage(errorMessage);
                                oc.setCouponCode(couponCode);
                            }
                        }
                    }
                }
                else
                {
                    cart.setCoupon(null);

                    final UniqueCoupon uc = this.couponMgr.findUniqueCouponForCode(couponCode);
                    if(isCouponDateValid(uc))
                    {
                        if(this.couponMgr.validateUniqueCoupon(uc, cart))
                        {
                            cart.setUc(uc);
                            cart.setErrorMessage("");
                            oc.setCouponCode(couponCode);
                            // this.calculateCosts(cart);
                        }
                        else
                        {
                            cart.setCoupon(null);
                            cart.setUc(null);
                            oc.setCouponCode(couponCode);
                        }
                    }
                    else
                    {
                        cart.setErrorMessage("*The coupon code you entered does not exist in the system, " +
                            "has expired or it is not available for online/mobile orders (COUPON ID: " //
                            + couponCode + ")."); //
                        cart.setUc(null);
                        oc.setCouponCode(couponCode);
                    }
                }

            }
            else
            {
                cart.setCoupon(null);
                cart.setUc(null);
                oc.setCouponCode(couponCode);
                cart.setErrorMessage("");
            }

        }

        calculateCosts(cart);

        return cart.getErrorMessage();
    }

    @Override
    public void populatePhoneOrder(Cart cart, List<OrderItem> allItems, ServletContext context) throws ServletException, IOException
    {
        log.warn("populatePhoneOrder");
        populateParentItems(allItems, cart, context);

        if(cart.getCustomer().getDelivery().equals(DeliveryMethod.DELIVER))
        {
            Integer deliveryCharge = updateDeliveryCharge(cart.getCityId());
            cart.setDeliveryCharge(deliveryCharge);
        }
        else
        {
            cart.setDeliveryCharge(0);
        }

        this.priceMgr.calculateCost(cart);
    }

    public OrderItem populateOrderItem(Menu menuItem, Cart cart)
    {
        final OrderItem item = new OrderItem(menuItem.getProduct(), menuItem.getId(), cart.getTempId());

        item.setSize(menuItem.getSize());
        populateBaseOrderItem(item, menuItem);

        return item;
    }

    private void populateBaseOrderItem(OrderItem item, Menu menuItem)
    {
        Integer quantity = item.getQuantity();
        if(quantity.equals(0))
        {
            quantity = 1;
        }
        item.setQuantity(quantity);
        item.setDisplayName(menuItem.getFullDisplayName());
        item.setPrice(menuItem.getPrice());
        item.setPriceModifier(menuItem.getPriceModifier());
        item.setEnviroLevy(menuItem.getEnviroLevy());
        item.setDeposit(menuItem.getDeposit());
        item.setWingsAllowed(menuItem.isWingsAllowed());
        item.setPizzasAllowed(menuItem.isPizzasAllowed());
    }

    private boolean isCouponDateValid(final UniqueCoupon uc)
    {
        if(uc != null && uc.getRedeemedOrderId() == null)
        {
            final Calendar c = uc.getCreateDate();

            final Calendar newCal = Calendar.getInstance();
            newCal.set(Calendar.YEAR, c.get(Calendar.YEAR));
            newCal.set(Calendar.MONTH, c.get(Calendar.MONTH));
            newCal.set(Calendar.DATE, c.get(Calendar.DATE));

            final Calendar businessDate = this.lookupMgr.businessDate();
            newCal.add(Calendar.DATE, 32);

            if(businessDate.before(newCal))
            {
                return true;
            }
        }

        return false;
    }

    private void addPhoneItemToCart(final Cart cart, final Menu menuItem, final OrderItem item, final ServletContext context) throws ServletException, IOException
    {
        Integer categoryId = categoryId(menuItem);
        if(PIZZA.getId().equals(categoryId) || GLUTEN_FREE_PIZZA.getId().equals(categoryId))
        {
            addPizzaToCart(context, menuItem, cart, item, MOBILE);
        }
        else if(WING_MEAL.getId().equals(categoryId))
        {
            OrderItem bundle = cart.findUnfinishedBundle(item.getSize().getId());
            // if bundle is null it means that the cart has expired.
            if(bundle != null)
            {
                bundle.addChild(item);
                cart.bundleComplete(bundle);
            }
        }
        else if(ProductCategoryEnum.WING_BOX.getId().equals(categoryId))
        {
            addWingBoxToCart(false, cart, item, false);
        }
        else if(ProductCategoryEnum.SPECIAL.getId().equals(categoryId))
        {
            item.setBundle(true);
            cart.bundleComplete(item);

            Collections.sort(item.getChildren());
            cart.addItem(item);
        }
        else
        {
            cart.addItem(item);
        }
    }

    private void addPizzaDefaults(final Cart cart, final ServletContext context, final Integer menuId, final OrderItem item)
    {
        final Integer productId = item.getProduct().getId();
        // sauce
        Menu sauce = (Menu) context.getAttribute(PizzaSauceEnum.DEFAULT_PIZZA_SAUCE.getCode());
        boolean isDonair = ProductEnum.SINGLE_DONAIR.isEqualToProductId(productId) || ProductEnum.DONAIR_PIZZA.isEqualToProductId(productId);
        if(ProductEnum.CHICKEN_MELT.isEqualToProductId(productId))
        {
            sauce = (Menu) context.getAttribute(PizzaSauceEnum.GARLIC_PIZZA_SAUCE.getCode());
        }
        else if(isDonair)
        {
            sauce = (Menu) context.getAttribute(PizzaSauceEnum.DONAIR_PIZZA_SAUCE.getCode());
        }
        else if(ProductEnum.BUFFALO_PIZZA.isEqualToProductId(productId))
        {
            sauce = (Menu) context.getAttribute(PizzaSauceEnum.BUFFALO_PIZZA_SAUCE.getCode());
        }
        else if(ProductEnum.PESTO_CHICKEN.isEqualToProductId(productId) || ProductEnum.PESTO_GARDEN.isEqualToProductId(productId))
        {
            sauce = (Menu) context.getAttribute(PizzaSauceEnum.PESTO_PIZZA_SAUCE.getCode());
        }
        else if(ProductEnum.CHILI_FIESTA_PIZZA.isEqualToProductId(productId))
        {
            sauce = (Menu) context.getAttribute(PizzaSauceEnum.CHILI_PIZZA_SAUCE.getCode());
        }

        Product sauceProduct = sauce.getProduct();
        Menu m = this.lookupMgr.menuItem(menuId + "", sauceProduct.getId() + "", "0");
        OrderItem child = populateOrderItem(m, cart, item.getSize());
        item.addChild(child);

        // crust
        Product crust = (Product) context.getAttribute("DEFAULT_CRUST");
        m = this.lookupMgr.menuItem(menuId + "", crust.getId() + "", "0");
        if(ArrayUtils.contains(GLUTEN_PIZZAS, productId))
        {
            m = (Menu) context.getAttribute("GLUTEN_FREE_CRUST");
            crust = m.getProduct();
        }
        child = populateOrderItem(m, cart, item.getSize());
        item.addChild(child);
    }

    private void addPizzaToCart(final ServletContext context, final Menu menuItem, final Cart cart, final OrderItem item, OrderOrigin orderOrigin)
    {
        final ProductSize newItemSize = item.getSize();
        OrderItem bundle = null;
        Integer productId = item.getProduct().getId();

        boolean singlePizza = ProductUtil.isSinglePizza(productId);
        boolean containsGlutenFree = ArrayUtils.contains(GLUTEN_PIZZAS, productId);
        singlePizza |= containsGlutenFree;
        if(singlePizza)
        {
            cart.bundleComplete(item);
            cart.addItem(item);
        }
        else
        {
            bundle = cart.findUnfinishedBundle(newItemSize.getId());

            if(null != bundle)
            {
                bundle.addChild(item);
            }
            else
            {
                final Integer tempId = item.getItemId();
                final Product everyDayDeal = (Product) context.getAttribute("EVERY_DAY_DEAL");

                bundle = populateEDDOrderItem(everyDayDeal, menuItem, tempId);
                item.setItemId(cart.getTempId());

                if(orderOrigin.equals(OrderOrigin.MOBILE))
                {
                    maintainChildrenForMobile(item);
                }
                bundle.addChild(item);

                cart.addItem(bundle);
            }
        }
        // MOBILE orders do not need the defaults populated except for gluten
        // free.
        if(orderOrigin.equals(OrderOrigin.ONLINE) || (containsGlutenFree && !ProductEnum.GLUTEN_FREE_TWO_TOPPER.isEqualToProductId(productId)))
        {
            addPizzaDefaults(cart, context, menuItem.getMenuId(), item);
        }
        else if(orderOrigin.equals(OrderOrigin.MOBILE) && containsGlutenFree && ProductEnum.GLUTEN_FREE_TWO_TOPPER.isEqualToProductId(productId))
        {
            OrderItem crust = item.getCrust();
            Menu m = (Menu) context.getAttribute("GLUTEN_FREE_CRUST");
            crust.setProduct(m.getProduct());
            crust.setProductId(m.getProduct().getId());
            crust.setDisplayName(m.getFullDisplayName());
        }

        if(null != bundle)
        {
            cart.bundleComplete(bundle);
            Collections.sort(bundle.getChildren());
        }
    }

    private void addWingBoxToCart(final boolean shrimp, final Cart cart, final OrderItem item, final boolean defaults)
    {
        item.setBundle(true);

        if(defaults)
        {
            String defaultProductId = DEFAULT_WING_ID;
            if(SpecialEnum.CHICKEN_BITES.isEqualToSpecialId(item.getProduct().getId()))
            {
                defaultProductId = DEFAULT_CHICKEN_BITE_ID;
            }
            String defaultProductSizeId = "0";
            if(shrimp)
            {
                defaultProductId = SHRIMP_ID;
            }
            if(null == cart.getMenuId())
            {
                cart.setMenuId(MenuId.AB_WEB);
            }
            Integer menuId = cart.getMenuId().getId();

            final Menu defaultMenu = this.lookupMgr.menuItem(menuId + "", defaultProductId, defaultProductSizeId);
            final ProductSize defaultProductSize = defaultMenu.getSize();

            final Product itemProduct = item.getProduct();
            ProductSize itemSize = item.getSize();
            final ProductComposition comp = itemProduct.compositionsForSize(itemSize, defaultMenu.getProduct().getCategory());

            OrderItem child = null;
            for(int i = 0; i < comp.getNumMax(); i++)
            {
                child = new OrderItem(defaultMenu.getProduct(), defaultMenu.getId(), cart.getTempId());
                if(comp.isSizeInherited())
                {
                    child.setSize(itemSize);
                }
                else
                {
                    child.setSize(defaultProductSize);
                }
                child.setDisplayName(defaultMenu.getDisplayName());
                child.setQuantity(1);
                child.setDrawTitle(false);
                child.setPriceModifier(defaultMenu.getPriceModifier());
                item.addChild(child);
            }
            cart.bundleComplete(item);
        }
        else
        {
            cart.bundleComplete(item);
        }

        cart.addItem(item);
    }

    private Integer categoryId(final Menu menuItem)
    {
        return menuItem.getCategoryId();
    }

    private boolean checkForUnsizedSpecial(Integer productId)
    {
        return SpecialEnum.LUNCH_COMBO.isEqualToSpecialId(productId) || SpecialEnum.CHILLI_COMBO.isEqualToSpecialId(productId) || SpecialEnum.WING_IT_BOX.isEqualToSpecialId
            (productId) || SpecialEnum.POUTINE_MOVIE.isEqualToSpecialId(productId) || SpecialEnum.CHILI_MOVIE.isEqualToSpecialId(productId) || SpecialEnum.WEDGIE_MOVIE
            .isEqualToSpecialId(productId) || SpecialEnum.TWO_DIPS.isEqualToSpecialId(productId);
    }

    private void convertBonelessWings(List<OrderItem> allItems, Cart cart)
    {
        for(OrderItem item : allItems)
        {
            Integer productId = item.getProductId();// item.getProduct().getId();
            log.warn("boneless wings order with product id: " + productId);
            if(null == productId)
            {
                productId = item.getProduct().getId();
                log.warn("boneless wings order with product id (second try): " + productId);
            }
            Menu menuItem = null;
            Menu flavor = null;
            Integer flavorCount = 1;

            if(ProductEnum.NAKED_20_BITE.isEqualToProductId(productId) || ProductEnum.NAKED_40_BITE.isEqualToProductId(productId))
            {
                flavor = lookupMgr.menuItem(MenuId.AB_MOBILE.getId(), WingFlavorEnum.NAKED.getId(), Integer.valueOf(0));
                if(ProductEnum.NAKED_40_BITE.isEqualToProductId(productId))
                {
                    flavorCount = 2;
                }
            }
            else if(ProductEnum.SCT_20_BITE.isEqualToProductId(productId) || ProductEnum.SCT_40_BITE.isEqualToProductId(productId))
            {
                flavor = lookupMgr.menuItem(MenuId.AB_MOBILE.getId(), WingFlavorEnum.SWEET_CHILI_THAI.getId(), Integer.valueOf(0));
                if(ProductEnum.SCT_40_BITE.isEqualToProductId(productId))
                {
                    flavorCount = 2;
                }
            }
            else if(ProductEnum.HG_20_BITE.isEqualToProductId(productId) || ProductEnum.HG_40_BITE.isEqualToProductId(productId))
            {
                flavor = lookupMgr.menuItem(MenuId.AB_MOBILE.getId(), WingFlavorEnum.HONEY_GARLIC.getId(), Integer.valueOf(0));
                if(ProductEnum.HG_40_BITE.isEqualToProductId(productId))
                {
                    flavorCount = 2;
                }
            }
            else if(ProductEnum.BBQ_20_BITE.isEqualToProductId(productId) || ProductEnum.BBQ_40_BITE.isEqualToProductId(productId))
            {
                flavor = lookupMgr.menuItem(MenuId.AB_MOBILE.getId(), WingFlavorEnum.BBQ.getId(), Integer.valueOf(0));
                if(ProductEnum.BBQ_40_BITE.isEqualToProductId(productId))
                {
                    flavorCount = 2;
                }
            }
            else if(ProductEnum.CHIPOTLE_20_BITE.isEqualToProductId(productId) || ProductEnum.CHIPOTLE_40_BITE.isEqualToProductId(productId))
            {
                flavor = lookupMgr.menuItem(MenuId.AB_MOBILE.getId(), WingFlavorEnum.CHIPOTLE.getId(), Integer.valueOf(0));
                if(ProductEnum.CHIPOTLE_40_BITE.isEqualToProductId(productId))
                {
                    flavorCount = 2;
                }
            }

            if(null != flavor)
            {

                if(flavorCount == 1)
                {
                    menuItem = lookupMgr.menuItem(MenuId.AB_MOBILE.getId(), SpecialEnum.CHICKEN_BITES.getId(), 1);
                }
                else
                {
                    menuItem = lookupMgr.menuItem(MenuId.AB_MOBILE.getId(), SpecialEnum.CHICKEN_BITES.getId(), 2);
                }
                Product product = menuItem.getProduct();
                item.setProduct(product);
                item.setProductId(product.getId());
                item.setDisplayName(menuItem.getDisplayName());
                item.setPrice(menuItem.getPrice());
                item.setSize(menuItem.getSize());
                item.setSizeId(menuItem.getSize().getId());
                item.setBundle(true);
                item.setCompleteBundle(true);

                OrderItem child = new OrderItem(flavor.getProduct(), item, MenuId.AB_MOBILE.getId(), cart.getTempId());
                child.setQuantity(1);
                child.setDisplayName(flavor.getDisplayName());
                child.setSize(flavor.getSize());
                child.setSizeId(flavor.getSize().getId());
                child.setProductId(flavor.getProduct().getId());
                item.addChild(child);
                if(flavorCount == 2)
                {
                    child = new OrderItem(flavor.getProduct(), item, MenuId.AB_MOBILE.getId(), cart.getTempId());
                    child.setQuantity(1);
                    child.setDisplayName(flavor.getDisplayName());
                    child.setSize(flavor.getSize());
                    child.setSizeId(flavor.getSize().getId());
                    child.setProductId(flavor.getProduct().getId());
                    item.addChild(child);
                }
            }
        }
    }

    private Menu dbMenu(final OrderItem item, Integer menuId)
    {
        if(menuId.equals(Integer.valueOf(0)))
        {
            menuId = MenuId.AB_WEB.getId();
        }
        String sMenuId = menuId + "";
        Menu menu = this.lookupMgr.menuItem(sMenuId, item.getProduct().getId() + "", item.getSize().getId() + "");
        if(menu == null)
        {
            menu = this.lookupMgr.menuItem(sMenuId, item.getProduct().getId() + "", "0");
        }
        return menu;
    }

    private void maintainChildrenForMobile(final OrderItem item)
    {
        List<OrderItem> mainItemChildren = item.getChildren();
        if(null != mainItemChildren && !mainItemChildren.isEmpty())
        {
            for(OrderItem childOrderItem : mainItemChildren)
            {
                childOrderItem.setParent(item);
            }
        }
    }

    private void populateBundle(final ServletContext context, final Cart cart, final OrderItem item, final Product product)
    {
        final Set<ProductDetail> details = product.getDetails();
        if(null == cart.getMenuId())
        {
            cart.setMenuId(MenuId.AB_WEB);
        }
        String abMenuId = cart.getMenuId().getId() + "";
        for(final ProductDetail detail : details)
        {
            final Product subProduct = detail.getSubProduct();
            Integer detailSubProductCatId = subProduct.getCategory().getId();

            Integer prodId = product.getId();
            Integer sizeId = item.getSize().getId();

            // DEAL with size mismatch for wedgies.
            if(ProductEnum.WEDGIE_16.isEqualToProductId(prodId) && ProductEnum.WEDGIE_12.isEqualToProductId(subProduct.getId()))
            {
                sizeId = sizeId - 1;
            }

            Integer subProductSizeId = detail.getSubProductSize().getId();
            if(subProductSizeId != 0)
            {
                sizeId = subProductSizeId;
            }

            String subProductId = subProduct.getId() + "";
            Menu childMenuItem = this.lookupMgr.menuItem(abMenuId, subProductId, sizeId + "");
            if(childMenuItem == null)
            {
                childMenuItem = this.lookupMgr.menuItem(abMenuId, subProductId, subProductSizeId + "");
            }

            boolean digDeeper = true;
            final int quantity = detail.getQuantity();
            OrderItem comboItem = null;

            if(POP.isEqualToCategoryId(detailSubProductCatId) || WING_DIP.isEqualToCategoryId(detailSubProductCatId) || ArrayUtils.contains(CHIPS, subProduct.getId()))
            {
                comboItem = populateOrderItem(childMenuItem, cart);
                comboItem.setQuantity(quantity);
                item.addChild(comboItem);
            }
            else
            {
                int pizzaCount = 0;
                boolean fanFavouriteDealtWith = false;
                for(int i = 0; i < quantity; i++)
                {
                    pizzaCount++;
                    if(SpecialEnum.FAN_FAVOURITE.isEqualToSpecialId(prodId) && pizzaCount > 1)
                    {
                        childMenuItem = this.lookupMgr.menuItem(abMenuId, ProductEnum.SCREAMING_HOT_WING_MEAL.getId() + "", sizeId + "");
                        detailSubProductCatId = ProductCategoryEnum.WING_MEAL.getId();
                        digDeeper = false;
                        fanFavouriteDealtWith =true;
                        comboItem = populateOrderItem(childMenuItem, cart);
                        item.addChild(comboItem);
                    }
                    else if(!SpecialsUtil.isFourPak(product.getId()) && !fanFavouriteDealtWith)
                    {
                        comboItem = populateOrderItem(childMenuItem, cart);
                        item.addChild(comboItem);
                        if(detailSubProductCatId.equals(ProductCategoryEnum.PIZZA.getId()))
                        {
                            addPizzaDefaults(cart, context, childMenuItem.getMenuId(), comboItem);
                            // pizza defaults already added don't need to do it
                            // again.
                            digDeeper = false;
                        }
                    }
                    else
                    {
                        comboItem = populateOrderItem(childMenuItem, cart);
                    }
                }
            }
            if(digDeeper && !subProduct.getDetails().isEmpty())
            {
                comboItem.setBundle(true);
                cart.bundleComplete(comboItem);
                populateBundle(context, cart, comboItem, subProduct);
                Collections.sort(comboItem.getChildren());
            }
        }
    }

    private void populateChildren(final Integer orderId, final OrderItem item, final Cart cart)
    {
        final Integer oldId = item.getItemId();
        Product p = item.getProduct();
        final Integer prodId = p.getId();
        if(!p.isActive())
        {
            if(prodId.equals(196))
            {
                p = this.lookupMgr.productById(34);
            }
            else if(ProductCategoryEnum.POP.isEqualToCategoryId(p.getCategory().getId()))
            {
                p = this.lookupMgr.productById(ProductEnum.COKE_CAN.getId());
            }
            item.setProduct(p);
        }

        refreshItem(item, cart);
        final List<OrderItem> children = this.lookupMgr.orderItemsForParent(orderId, oldId);
        if(!children.isEmpty())
        {
            final List<OrderItem> removeList = new ArrayList<OrderItem>();
            for(OrderItem temp : children)
            {
                Product tempProduct = temp.getProduct();
                Boolean tempActive = tempProduct.isActive();
                if(!tempActive)
                {
                    removeList.add(temp);
                }
            }
            if(removeList.size() > 0)
            {
                children.removeAll(removeList);
            }

            item.setChildren(children);

            for(OrderItem child : children)
            {
                Product prod = child.getProduct();
                final ProductCategory prodCat = prod.getCategory();
                if(!prod.isActive())
                {
                    // convert shrimp product to wing product unless shrimp deal is active
                    Integer childProdId = prod.getId();
                    if(ProductEnum.SHRIMP.isEqualToProductId(childProdId))
                    {
                        prod = this.lookupMgr.productById(WingFlavorEnum.SCREAMIN_HOT.getId());
                    }
                    // convert shrimp meal product to wing meal product unless shrimp deal is active
                    else if(ProductEnum.SHRIMP_MEAL.isEqualToProductId(childProdId))
                    {
                        prod = this.lookupMgr.productById(10);
                    }
                    else if(POP.isEqualToCategoryId(prodCat.getId()))
                    {
                        prod = this.lookupMgr.productById(254);
                    }
                    child.setProduct(prod);
                    refreshItem(child, cart);
                }

                child.setParent(item);
                if(prodCat.isTopLevel())
                {
                    populateChildren(orderId, child, cart);
                }
                else
                {
                    refreshItem(child, cart);
                }
            }
        }
    }

    private OrderItem populateEDDOrderItem(Product everyDayDeal, Menu menuItem, Integer tempId)
    {
        final OrderItem bundle = new OrderItem(everyDayDeal, 0, tempId);
        populateBaseOrderItem(bundle, menuItem);
        bundle.setDisplayName(everyDayDeal.getFullDescription());
        bundle.setSize(menuItem.getSize());
        bundle.setBundle(true);
        bundle.setDrawTitle(false);

        return bundle;
    }

    private OrderItem populateOrderItem(Menu menuItem, Cart cart, ProductSize size)
    {
        final OrderItem item = new OrderItem(menuItem.getProduct(), menuItem.getId(), cart.getTempId());

        item.setSize(size);
        populateBaseOrderItem(item, menuItem);

        return item;
    }

    private void populateParentItems(List<OrderItem> allItems, Cart cart, ServletContext context) throws ServletException, IOException
    {
        allItems = removeParent(allItems, SpecialEnum.SINGLE_73_DELUXE.getId());
        allItems = removeParent(allItems, SpecialEnum.SLICES_FOR_SMILES_499.getId());
        allItems = removeParent(allItems, SpecialEnum.SLICES_FOR_SMILES_DONATION.getId());
        allItems = removeParent(allItems, SpecialEnum.HH_SWEET_CHILI_BONELESS.getId());
        allItems = removeParent(allItems, SpecialEnum.SINGLE_MOTHERS_DAY.getId());
        allItems = removeParent(allItems, SpecialEnum.SINGLE_VALENTINES.getId());

        allItems = updateGlutenFreeSpecial(allItems, cart);
        allItems = updateGlutenFreeTwoTopperSpecial(allItems, cart);

        convertBonelessWings(allItems, cart);

        for(OrderItem item : allItems)
        {
            if(null == item.getParentId() || item.getParentId().equals(Integer.valueOf(0)))
            {
                Integer productId = item.getProductId();
                Product product = this.productForId(productId);
                if(product.isActive())
                {
                    Integer sizeId = item.getSizeId();
                    boolean isUnsizedSpecial = checkForUnsizedSpecial(productId);
                    if(isUnsizedSpecial)
                    {
                        sizeId = 0;
                    }
                    ProductSize size = this.sizeForId(sizeId);
                    item.setProduct(product);
                    item.setSize(size);
                    refreshItem(item, cart);
                    populatePhoneChildren(item, cart, item.getChildren());
                    Menu menu = dbMenu(item, cart.getMenuId().getId());

                    addPhoneItemToCart(cart, menu, item, context);
                }
            }
        }
    }

    private void populatePhoneChildren(final OrderItem item, final Cart cart, final List<OrderItem> children)
    {
        if(!children.isEmpty())
        {
            for(OrderItem child : children)
            {

                Product childProduct = this.productForId(child.getProductId());
                ProductSize childProductSize = this.sizeForId(child.getSizeId());
                child.setProduct(childProduct);
                child.setSize(childProductSize);
                child.setQuantity(child.getQuantity());
                child.setParent(item);

                final ProductCategory prodCat = childProduct.getCategory();
                Integer categoryId = prodCat.getId();
                if(prodCat.isTopLevel())
                {
                    refreshItem(child, cart);
                    populatePhoneChildren(child, cart, child.getChildren());
                }
                else
                {
                    if(categoryId.equals(TOPPING.getId()))
                    {
                        child.setSize(item.getSize());
                    }
                    refreshItem(child, cart);
                }
            }
        }

        Product parentProduct = item.getProduct();
        Integer parentProductId = parentProduct.getId();
        if(SpecialEnum.FOUR_PAK.isEqualToSpecialId(parentProductId) || SpecialEnum.HOLIDAY_HELPER.isEqualToSpecialId(parentProductId))
        {
            if(CoreStoreHoursUtil.isLunchtimeForSpecial(CityUtil.isBC(cart.getMenuId().getId())))
            {
                addTwoDollarOff(cart, item);
            }
        }
    }

    public Product productForId(Integer productId)
    {
        return lookupMgr.productById(productId);
    }

    private void refreshEDD(final OrderItem item, final Cart cart, final Product edd)
    {
        final List<OrderItem> inactiveProducts = new ArrayList<OrderItem>();
        final List<OrderItem> orphanProducts = new ArrayList<OrderItem>();
        final List<OrderItem> children = item.getChildren();

        item.setProduct(edd);
        item.setDisplayName(edd.getName());
        item.setPrice(0);
        item.setPriceModifier(0);
        for(OrderItem child : children)
        {
            final Product childProd = child.getProduct();
            final ProductCategory prodCat = childProd.getCategory();
            if(!ProductCategoryEnum.PIZZA.isEqualToCategoryId(prodCat.getId()) && !ProductCategoryEnum.WING_MEAL.isEqualToCategoryId(prodCat.getId()))
            {
                if(!childProd.isActive())
                {
                    inactiveProducts.add(child);
                }
                else
                {
                    orphanProducts.add(child);
                }
            }
        }

        if(!inactiveProducts.isEmpty())
        {
            children.removeAll(inactiveProducts);
        }
        if(!orphanProducts.isEmpty())
        {
            for(OrderItem toBeOrphaned : orphanProducts)
            {
                toBeOrphaned.setParentId(0);
                toBeOrphaned.setParent(null);
                item.removeChild(toBeOrphaned);
                cart.addItem(toBeOrphaned);
            }
        }
    }

    private void refreshItem(final OrderItem item, final Cart cart)
    {
        item.setId(null);
        item.setItemId(cart.getTempId());
        if(cart.getMenuId() == null)
        {
            cart.setMenuId(MenuId.AB_WEB);
        }

        Menu menu = dbMenu(item, cart.getMenuId().getId());
        if(menu != null)
        {
            item.setMenuId(menu.getId());
            item.setDisplayName(menu.getFullDisplayName());
            item.setPriceModifier(menu.getPriceModifier());
            item.setPrice(menu.getPrice());
            final Product p = item.getProduct();

            Integer catId = p.getCategory().getId();
            if(catId.equals(ProductCategoryEnum.WING_FLAVOR.getId()))
            {
                item.setDrawTitle(false);
                item.setPrice(0);
            }
            else if(!SpecialEnum.EVERYDAY_DEAL.isEqualToSpecialId(p.getId()))
            {
                item.setPrice(menu.getPrice());
                item.setEnviroLevy(menu.getEnviroLevy());
                item.setDeposit(menu.getDeposit());

                final OrderItem parent = item.getParent();
                boolean inheritParentSize = shouldInheritParentSize(parent, catId);
                if(inheritParentSize)
                {
                    final ProductSize parentProductSize = parent.getSize();
                    item.setSize(parentProductSize);
                }
            }
        }
    }

    private boolean shouldInheritParentSize(OrderItem parent, Integer catId)
    {
        Integer parentId = null != parent ? parent.getProduct().getId() : null;
        return (catId.equals(ProductCategoryEnum.CRUST.getId()) || catId.equals(ProductCategoryEnum.SAUCE.getId()) ||
            (ProductCategoryEnum.WING_MEAL.isEqualToCategoryId(catId) && SpecialEnum.FAN_FAVOURITE.isEqualToSpecialId(parentId)));
    }

    private void removeParent(Cart cart, Integer productId)
    {
        List<OrderItem> newList = new ArrayList<OrderItem>();
        OrderItem removeItem = null;

        for(OrderItem item : cart.getItems())
        {
            Integer candidateProdId = item.getProduct().getId();
            if(candidateProdId.equals(productId))
            {
                removeItem = item;
                for(OrderItem childItem : item.getChildren())
                {
                    childItem.setParentId(0);
                    newList.add(childItem);
                }
            }
        }
        for(OrderItem item : newList)
        {
            cart.addItem(item);
        }
        cart.removeItem(removeItem);
    }

    @Autowired
    public void setCouponManager(@Qualifier("couponManager") final CouponManager mgr)
    {
        this.couponMgr = mgr;
    }

    @Autowired
    public void setOrderMgr(@Qualifier("orderManager") final OrderManager orderMgr)
    {
        this.orderMgr = orderMgr;
    }

    @Autowired
    public void setPricingManager(@Qualifier("pricingManager") final PricingManager mgr)
    {
        this.priceMgr = mgr;
    }

    @Autowired
    public void setUserManager(@Qualifier("userManager") final UserManager mgr)
    {
        this.userMgr = mgr;
    }

    public ProductSize sizeForId(Integer sizeId)
    {
        return (ProductSize) lookupMgr.get(ProductSize.class, sizeId);
    }

    public Integer updateDeliveryCharge(final Integer cityId)
    {
        return updateDeliveryCharge(cityId + "");
    }

    private List<OrderItem> updateGlutenFreeSpecial(List<OrderItem> allItems, Cart cart)
    {
        return this.removeParent(allItems, SpecialEnum.GLUTEN_FREE.getId());
    }

    private List<OrderItem> updateGlutenFreeTwoTopperSpecial(List<OrderItem> allItems, Cart cart)
    {
        return this.removeParent(allItems, SpecialEnum.TWO_TOPPER_GLUTTEN_FREE_SP.getId());
    }

    private List<OrderItem> updateTwoDipsSpecial(List<OrderItem> allItems, Cart cart)
    {
        return this.removeParent(allItems, SpecialEnum.TWO_DIPS.getId());
    }

    private List<OrderItem> removeParent(List<OrderItem> allItems, Integer productId)
    {
        List<OrderItem> newList = new ArrayList<OrderItem>();
        for(OrderItem item : allItems)
        {
            if(item.getProductId().equals(productId))
            {
                for(OrderItem childItem : item.getChildren())
                {
                    childItem.setParentId(0);
                    newList.add(childItem);
                }
            }
            else
            {
                newList.add(item);
            }
        }

        return newList;
    }
}