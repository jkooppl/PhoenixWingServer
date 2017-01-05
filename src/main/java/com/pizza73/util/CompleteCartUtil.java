package com.pizza73.util;

import com.pizza73.model.Address;
import com.pizza73.model.CompleteCart;
import com.pizza73.model.Municipality;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.model.Order;
import com.pizza73.model.OrderItem;
import com.pizza73.model.Product;
import com.pizza73.model.ProductCategory;
import com.pizza73.model.ProductComposition;
import com.pizza73.model.enums.CityEnum;
import com.pizza73.model.enums.DeliveryMethod;
import com.pizza73.model.enums.ProductCategoryEnum;
import com.pizza73.model.enums.ProductEnum;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * CompleteCartUtil.java TODO comment me
 *
 * @author chris 12-Feb-07
 *
 * @Copyright Flying Pizza 73
 */
public class CompleteCartUtil
{

    private static NumberFormat formatter = DecimalFormat.getCurrencyInstance();
    private static final BigDecimal ONE_HUNDREDTH = BigDecimal.valueOf(0.01);
    private static final int[] LUNCH_CITIES = new int[] { 4, 12, 15, 19, 21, 22 };
    private static final int[] NON_LUNCH_CITIES = new int[] { 1, 2, 3, 20, 16, 23, 5, 6, 9 };
    private static final String LUNCH_CITY_MESSAGE = "Lunch Special! Order from 11am - 4pm and get FOUR 9\" 2 topper pizzas for $20. Add 4 cans of pop to this offer for only $2. Open for lunch daily.";
    private static final String NON_LUNCH_CITY_MESSAGE = "Lunch Special! Order from 11am - 4pm and get FOUR 9\" 2 topper pizzas for $20. Add 4 cans of pop to this offer for only $2. Open for lunch Thurs - Sun.";
    private static final String MIND_SHARE_SURVEY_MESSAGE = "*** FREE WEDGIES or ONION RINGS ***\n" +
        "Complete our brief survey & receive a free order of 9\" Wedgies or a Box of Onion Rings on your next online or walk in" +
        " order! Details of how to take the survey can be found on your receipt when your order arrives.";
    private static final String CINEPLEX_DISPLAY_NAME = "Cineplex 2 for 1 Movie Admission";
    private static final String LANDMARK_MOVIE = "2 for 1 Movie Admission from Landmark Cinemas";
    private static final String LLOYD_SPORTS = "2 for 1 Admission from Servus Sports Centre";

    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(CompleteCartUtil.class);

    /**
     * @param subProperty
     * @return
     */
    private static List<OrderItem> optionsForType(OrderItem item, String subProperty)
    {
        List<OrderItem> options = new ArrayList<OrderItem>();
        for (OrderItem child : item.getChildren())
        {
            if (child.getProduct().getCategory().getName().equals(subProperty))
            {
                options.add(child);
            }
        }

        return options;
    }

    public static String emailCart(CompleteCart cart, String ciniplex)
    {
        String message = "Cart is empty.";
        if (cart != null)
        {
            List<OrderItem> items = cart.getOrderItems();
            if (items != null && !items.isEmpty())
            {
                message = "";

                int count = 1;
                for (OrderItem item : items)
                {
                    if (item.isBundle())
                    {
                        if (item.isDrawTitle())
                        {
                            message += "Item " + count + ": " + item.getDisplayName();
                            Product p = item.getProduct();
                            if (p.getCategory().getId().equals(ProductCategoryEnum.SPECIAL.getId()))
                            {
                                message += " ( special )\n";
                                message += drawEmailChildren(item, count);
                            }
                            else
                            {
                                message += "\n" + drawEmailCompositions(item);
                            }

                        }
                        else
                        {
                            message += drawEmailChildren(item, count);
                            count++;
                        }
                    }
                    else
                    {
                        message += "Item " + count + "\n";
                        if (item.getQuantity() > 1)
                        {
                            message += item.getQuantity() + " x " + item.getDisplayName();
                        }
                        else
                        {
                            message += item.getDisplayName();
                        }
                        message += "\n" + drawEmailCompositions(item);
                    }
                    message += "\n";
                    count++;
                }
            }
            OnlineCustomer oc = cart.getCustomer();
            if(null != oc)
            {
                Address address = oc.getAddress();
                if(null != address)
                {
                    Municipality city = address.getCity();
                    Integer cityId = city.getId();
                    if(CityEnum.FORT_MCMURRAY.isEqualToCityId(cityId) || CityEnum.FORT_ST_JOHNS.isEqualToCityId(cityId))
                    {
                        message = message.replace(CINEPLEX_DISPLAY_NAME, LANDMARK_MOVIE);
                    }
                    else if(CityEnum.LLOYDMINSTER.isEqualToCityId(cityId))
                    {
                        message = message.replace(CINEPLEX_DISPLAY_NAME, LLOYD_SPORTS);
                    }
                }
            }
        }

        message += "\n\n" + CompleteCartUtil.emailTotal(cart);
        if (null != ciniplex)
        {
            message += "\n\n" + ciniplex;
        }

        message += "\n\n" + MIND_SHARE_SURVEY_MESSAGE;

        return message;
    }

    @SuppressWarnings("unused")
    private static String addExtraMessageToEmail(CompleteCart cart, String message)
    {
        OnlineCustomer customer = cart.getCustomer();
        if (null != customer)
        {
            Address address = customer.getAddress();
            if (null != address)
            {
                Municipality city = address.getCity();
                if (null != city)
                {
                    int cityId = city.getId();
                    if (ArrayUtils.contains(LUNCH_CITIES, cityId))
                        message += "\n\n" + LUNCH_CITY_MESSAGE;
                    else if (ArrayUtils.contains(NON_LUNCH_CITIES, cityId))
                        message += "\n\n" + NON_LUNCH_CITY_MESSAGE;
                }
            }
        }
        return message;
    }

    public static String emailTotal(CompleteCart cart)
    {
        String total = "";
        if (cart != null && !cart.getOrderItems().isEmpty())
        {
            total = "Subtotal:\t\t" + cart.getWebTotalPrice() + "\n";
            if (cart.getEnviroLevy() > 0)
            {
                total += "Enviro Levy:\t\t" + cart.getWebEnviroLevy() + "\n";
            }
            if (cart.getDeposit() > 0)
            {
                total += "Deposit:\t\t" + cart.getWebDeposit() + "\n";
            }
            if (cart.getDeliveryCharge() > 0)
            {
                total += "Delivery Charge:\t" + cart.getWebDeliveryCharge() + "\n";
            }
            if (cart.getDiscount() > 0)
            {
                total += "Discount:\t\t" + cart.getWebDiscount() + "\n";
            }
            String gstString = "GST:\t\t\t";
            // if (cart.getMenuId().equals(MenuId.BC_WEB) ||
            // cart.getMenuId().equals(MenuId.BC_MOBILE))
            // {
            // gstString = "GST/HST\t\t";
            // }
            total += gstString + cart.getWebGst() + "\n" + "Total:\t\t\t" + cart.getWebGrandTotal() + "\n";
        }

        return total;
    }

    private static String drawEmailChildren(OrderItem item, int count)
    {
        String message = "";
        boolean incrementCount = true;
        Product product = item.getProduct();
        if (product.getCategory().getId().equals(ProductCategoryEnum.SPECIAL.getId()))
        {
            incrementCount = false;
        }

        for (OrderItem child : item.getChildren())
        {
            Product childProduct = child.getProduct();
            ProductCategory childCategory = childProduct.getCategory();
            if (child.isDrawTitle() && childCategory.isTopLevel()
                || ProductCategoryEnum.WING_DIP.getId().equals(childCategory.getId()))
            {
                if (incrementCount)
                {
                    message += "Item " + count + "\n";
                    count++;
                }
                int quantity = child.getQuantity();
                String q = quantity > 1 ? quantity + " x " : "";
                if(ProductEnum.TWO_FOR_ONE_MOVIE.isEqualToProductId(childProduct.getId()))
                {

                }
                message += q + child.getDisplayName() + "\n";
                message += drawEmailCompositions(child);
            }
        }

        return message;
    }

    /**
     */
    private static String drawEmailCompositions(OrderItem item)
    {
        String message = "";
        Product p = item.getProduct();
        Integer productId = p.getId();
        Set<ProductComposition> comps = p.compositionsForSize(item.getSize());

        for (ProductComposition pc : comps)
        {
            String pcDispalyName = ProductCategoryEnum.categoryForName(pc.getSubProperty()).getDisplayName();
            List<OrderItem> options = optionsForType(item, pc.getSubProperty());
            String display = "";
            if (!options.isEmpty())
            {
                message += "\t" + pcDispalyName + ": ";
                if (options.size() == 1)
                {
                    OrderItem optionItem = options.get(0);
                    display = optionItem.getDisplayName();
                    boolean isDonair = ProductEnum.EDD_DONAIR.isEqualToProductId(productId)
                        || ProductEnum.SINGLE_DONAIR.isEqualToProductId(productId);
                    if (optionItem.getQuantity() > 1)
                    {
                        display = optionItem.getQuantity() + "x" + display;
                    }
                    else if (isDonair && pcDispalyName.equals(ProductCategoryEnum.SAUCE.getDisplayName()))
                    {
                        display = "Donair sauce";
                    }
                    message += display;
                }
                else
                {
                    int max = options.size();
                    int count = 1;
                    display = "";
                    for (OrderItem optionItem : options)
                    {
                        String name = optionItem.getDisplayName();
                        if (optionItem.getQuantity() > 1)
                        {
                            display += optionItem.getQuantity() + "x" + name;
                        }
                        else
                        {
                            ProductCategory category = optionItem.getProduct().getCategory();
                            Integer catId = category.getId();
                            if (ProductCategoryEnum.WING_FLAVOR.getId().equals(catId))
                            {
                                display += "20 x ";
                            }
                            display += name;
                        }
                        if (count != max)
                        {
                            display += ", ";
                        }

                    }
                    message += display;
                }
                message += "\n";
            }

        }

        return message;
    }

    public static String adminTotal(Order order)
    {
        String total = "<table id='totals'><tr id='subTotal'><td>Subtotal</td>" + "<td id='subTotalValue'>0</td></tr>"
            + "<tr id='gst'><td>GST</td><td id='gstValue'>0</td></tr>"
            + "<tr id='grandTotal'><td>Total</td><td id='grandTotalValue'>" + "0</td></tr></table>";
        if (order != null)
        {
            int deliveryCharge = order.getDeliveryCharge();
            if (order.getDeliveryMethod() == DeliveryMethod.PICK_UP.getShortValue())
            {
                deliveryCharge = 0;
            }
            int subTotal = order.getTotal() - deliveryCharge - order.getEnviroLevy() - order.getDeposit() - order.getGst()
                - order.getDiscountAmount();
            total = " <table id='totals'><tr id='subTotal'><td>Subtotal</td><td id='subTotalValue'>"
                + currencyFormat(subTotal) + "</td></tr>";
            if (order.getEnviroLevy() > 0)
            {
                total += "<tr id='enviro'><td>Enviro Levy</td><td id='enviroValue'>" + currencyFormat(order.getEnviroLevy())
                    + "</td></tr>";
            }
            if (order.getDeposit() > 0)
            {
                total += "<tr id='deposit'><td>Deposit</td><td id='depositValue'>" + currencyFormat(order.getDeposit())
                    + "</td></tr>";
            }

            total += "<tr id='delivery'><td>Delivery Charge</td><td id='deliveryValue'>" + currencyFormat(deliveryCharge)
                + "</td></tr>";
            if (order.getDiscountAmount() > 0)
            {
                total += "<tr id='discount'><td>Discount</td><td id='discountValue'>"
                    + currencyFormat(order.getDiscountAmount()) + "</td></tr>";
            }
            total += "<tr id='gst'><td>GST</td><td id='gstValue'>" + currencyFormat(order.getGst()) + "</td></tr>"
                + "<tr id='grandTotal'><td>Total</td><td id='grandTotalValue'>" + currencyFormat(order.getTotal())
                + "</td></tr></table>";
        }

        return total;
    }

    private static String currencyFormat(Integer intValue)
    {
        BigDecimal value = BigDecimal.valueOf(intValue);
        if (value.compareTo(BigDecimal.ZERO) > 0)
        {
            value = value.multiply(ONE_HUNDREDTH);
        }
        else
        {
            return formatter.format(0.00d);
        }
        return formatter.format(value);
    }
}
