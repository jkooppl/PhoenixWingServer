package com.pizza73.service.dwr;

import com.pizza73.model.Cart;
import com.pizza73.model.Menu;
import com.pizza73.model.OrderItem;
import com.pizza73.service.LookupManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * CartManager.java TODO comment me
 *
 * @author chris 18-Oct-06
 *
 * @Copyright Flying Pizza 73
 */
public interface CartManager
{
    public String addOption(HttpSession session, String itemId, String childId, String menuId) throws ServletException,
        IOException;

    public String addOrderItem(HttpSession session, String menuId, final String parentId) throws ServletException, IOException;

    public Cart calculateCost(HttpSession session);

    public String deleteOrderItem(HttpSession session, String itemId) throws ServletException, IOException;

    public String deliveryCharge(HttpSession session, String deliveryMethod, String cityId) throws ServletException,
        IOException;

    public Cart emptyCart(HttpSession session);

    public Cart getCart(HttpSession session);

    public String getDisplayShop(HttpSession session);

    public String htmlCart(HttpSession session) throws ServletException, IOException;

    public String htmlOrderButtons(HttpSession session) throws ServletException, IOException;

    public String htmlTotal(HttpSession session) throws ServletException, IOException;

    public boolean isCartEmpty(HttpSession session);

    public OrderItem itemForId(HttpSession session, String id);

    public String removeOption(HttpSession session, String itemId, String childId) throws ServletException, IOException;

    public void setLookupManager(LookupManager mgr);

    public void setNoMoreDips(HttpSession session, String itemId);

    public void setParkedOrderSalt(Cart cart, Integer id, ServletContext servletContext);

    public void setOrderCommentDeliveryTime(HttpSession session, boolean value);

    public void setOrderCommentDeliveryTimeContent(HttpSession session, String content);

    public void setOrderCommentEasyCheese(HttpSession session, boolean value);

    public void setOrderCommentEasySauce(HttpSession session, boolean value);

    public void setOrderCommentPickupTime(HttpSession session, boolean value);

    public void setOrderCommentPickupTimeContent(HttpSession session, String content);

    public void setOrderCommentRemoveTopping(HttpSession session, boolean value);

    public void setOrderCommentRemoveToppingContent(HttpSession session, String content);

    public void setOrderCommentWellDone(HttpSession session, boolean value);

    public void setSalt(final Cart cart, final Integer orderId, boolean parked, final ServletContext context);

    public Map<String, String> shopsForMunicipality(HttpSession session, String municipalityId);

    public Integer updateDeliveryCharge(String cityId);

    public String updateOption(HttpSession session, String itemId, String productId) throws ServletException, IOException;

    public String updatePrice(HttpSession session, String cityId) throws ServletException, IOException;

    public String updateQuantity(HttpSession session, String itemId, String quantity) throws ServletException, IOException;

    public String validateCoupon(HttpSession session, String couponCode);

    public String validateCouponServer(String couponCode, Cart cart);

    public void populatePhoneOrder(Cart cart, List<OrderItem> allItems, ServletContext context) throws ServletException,
        IOException;

    public OrderItem populateOrderItem(Menu menuItem, Cart cart);
    public void addTopLevelProduct(Cart cart, OrderItem item, Integer productId);

    public void addGenericOrderItem(Cart cart, Menu menuItem);
}
