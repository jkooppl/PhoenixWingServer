package com.pizza73.model;

import static com.pizza73.model.enums.MenuId.BC_MOBILE;
import static com.pizza73.model.enums.MenuId.BC_WEB;
import static com.pizza73.model.enums.PricingEnum.GST;
import static com.pizza73.model.enums.PricingEnum.HST;
import static com.pizza73.model.enums.PricingEnum.ONE_HUNDREDTH;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.pizza73.model.enums.DeliveryMethod;
import com.pizza73.model.enums.MenuId;
import com.pizza73.model.enums.ProductEnum;

/**
 * CompleteCart.java TODO comment me
 * 
 * @author chris 8-Jun-07
 * 
 * @Copyright Flying Pizza 73
 */
@JsonIgnoreProperties({ "orderItems", "emailMessage", "totalCost", "enviroLevy", "deposit", "discount", "deliveryCharge",
        "lunchDiscount", "menuId", "webTotalPrice", "webDeliveryCharge", "webEnviroLevy", "webDeposit", "webGst",
        "webGrandTotal", "totalPrice", "deliveryCharge", "enviroLevy", "deposit", "gst", "grandTotal", "htmlCart",
        "customer", "taxableTotal", "webDiscount" })
public class CompleteCart implements Serializable
{
    private static final long serialVersionUID = 6731792700134253385L;

    protected static final NumberFormat formatter = DecimalFormat.getCurrencyInstance();

    private final List<OrderItem> orderItems = new ArrayList<OrderItem>();
    private String deliveryDetails;
    private Integer orderConfirmation;
    private String emailMessage;
    private final Integer totalCost;
    private final Integer enviroLevy;
    private final Integer deposit;
    private final Integer discount;
    private final Integer deliveryCharge;
    private final boolean lunchDiscount;
    private MenuId menuId = MenuId.AB_WEB;
    private OnlineCustomer customer = new OnlineCustomer();

    private TotalCost cost;
    private List<String> errorMessages = new ArrayList<String>();

    private String htmlCart;

    public CompleteCart(Cart cart)
    {
        for (OrderItem item : cart.getItems())
        {
            orderItems.add(item);
        }
        this.totalCost = cart.getTotalPrice();
        this.deliveryCharge = cart.getDeliveryCharge();
        this.enviroLevy = cart.getEnviroLevy();
        this.deposit = cart.getDeposit();
        this.menuId = cart.getMenuId();
        this.customer = cart.getCustomer();
        this.discount = cart.getTotalDiscount();
        this.lunchDiscount = cart.isLunchDiscount();
    }

    public CompleteCart(Cart cart, List<String> errors)
    {
        this(cart);
        this.errorMessages = errors;
        cost = new TotalCost(getWebTotalPrice(), getWebDeliveryCharge(), getWebEnviroLevy(), getWebDeposit(),
            getWebDiscount(), getWebGst(), getWebGrandTotal());
    }

    public TotalCost getCost()
    {
        return this.cost;
    }

    public List<String> getErrorMessages()
    {
        return this.errorMessages;
    }

    public List<OrderItem> getOrderItems()
    {
        return this.orderItems;
    }

    /**
     * @return the deliveryDetails
     */
    public String getDeliveryDetails()
    {
        return this.deliveryDetails;
    }

    /**
     * @param deliveryDetails
     *            the deliveryDetails to set
     */
    public void setDeliveryDetails(String deliveryDetails)
    {
        this.deliveryDetails = deliveryDetails;
    }

    /**
     * @return the emailMessage
     */
    public String getEmailMessage()
    {
        return this.emailMessage;
    }

    /**
     * @param emailMessage
     *            the emailMessage to set
     */
    // public void setEmailMessage(String emailMessage)
    // {
    // this.emailMessage = "You can expect a confirmation email to be sent to: "
    // + emailMessage + " shortly.  ";
    // }

    public void setEmailMessage(String emailMessage, String cineplex)
    {
        this.emailMessage = "You can expect a confirmation email to be sent to: " + emailMessage + " shortly.  ";
        if (null != cineplex)
        {
            this.emailMessage += "\n\n" + cineplex;
        }
    }

    /**
     * @return the orderConfirmation
     */
    public Integer getOrderConfirmation()
    {
        return this.orderConfirmation;
    }

    /**
     * @param orderConfirmation
     *            the orderConfirmation to set
     */
    public void setOrderConfirmation(Integer orderConfirmation)
    {
        this.orderConfirmation = orderConfirmation;
    }

    /**
     * @return the htmlCart
     */
    public String getHtmlCart()
    {
        return this.htmlCart;
    }

    /**
     * @param htmlCart
     *            the htmlCart to set
     */
    public void setHtmlCart(String htmlCart)
    {
        this.htmlCart = htmlCart;
    }

    /**
     * @return the totalCost
     */
    public Integer getTotalCost()
    {
        return this.totalCost;
    }

    /**
     * @return the customer
     */
    public OnlineCustomer getCustomer()
    {
        return this.customer;
    }

    /**
     * @return the deliveryCharge
     */
    public Integer getDeliveryCharge()
    {
        return this.deliveryCharge;
    }

    /**
     * @return the deposit
     */
    public Integer getDeposit()
    {
        return this.deposit;
    }

    /**
     * @return the enviroLevy
     */
    public Integer getEnviroLevy()
    {
        return this.enviroLevy;
    }

    public Integer getDiscount()
    {
        return this.discount;
    }

    /**
     * @return the menuId
     */
    public MenuId getMenuId()
    {
        return this.menuId;
    }

    public String getWebDeliveryCharge()
    {
        return currencyFormat(getDeliveryCharge());
    }

    public String getWebEnviroLevy()
    {
        return currencyFormat(getEnviroLevy());
    }

    public String getWebDeposit()
    {
        return currencyFormat(getDeposit());
    }

    public String getWebTotalPrice()
    {
        return currencyFormat(getTotalCost());
    }

    public String getWebGst()
    {
        return currencyFormat(getGst());
    }

    public Integer getGrandTotal()
    {
        Integer grandTotal = getDeposit() + getTaxableTotal() + getEnviroLevy() + getGst();

        return grandTotal;
    }

    public String getWebGrandTotal()
    {
        return currencyFormat(getGrandTotal());
    }

    public Integer getTaxableTotal()
    {
        Integer total = this.totalCost;

        if ((total > 100 || this.lunchDiscount) && this.getDiscount() > 0)
        {
            total = total - this.getDiscount();
        }
        if (this.customer.getDelivery().equals(DeliveryMethod.DELIVER))
        {
            total = total + getDeliveryCharge();
        }

        return total;
    }

    public String getWebDiscount()
    {
        return currencyFormat(getDiscount());
    }

    /**
     * @return
     */
    public Integer getGst()
    {
        BigDecimal tempGst = BigDecimal.ZERO;

        if (this.totalCost.compareTo(Integer.valueOf(0)) > 0)
        {
            // For rounding when converting to Integer
            BigDecimal cost = BigDecimal.valueOf(getTaxableTotal());
            Iterator<OrderItem> iter = this.orderItems.iterator();
            OrderItem item = null;
            while (iter.hasNext())
            {
                item = iter.next();
                // donation does not get taxed
                Integer productId = item.getProduct().getId(); 
                if (ProductEnum.SMILES_DONATION.isEqualToProductId(productId))
                {
                    cost = cost.subtract(BigDecimal.valueOf(item.getPrice())
                        .multiply(BigDecimal.valueOf(item.getQuantity())));
                }
            }
            BigDecimal totalTax = GST.getValue();
            if (menuId.equals(BC_WEB) || menuId.equals(BC_MOBILE))
            {
                totalTax = totalTax.add(HST.getValue());
            }
            cost = cost.multiply(totalTax).add(BigDecimal.valueOf(0.5));
            tempGst = cost;
        }

        return tempGst.intValue();
    }

    private String currencyFormat(Integer intValue)
    {
        BigDecimal value = BigDecimal.valueOf(intValue);
        if (value.compareTo(BigDecimal.ZERO) > 0)
        {
            value = value.multiply(ONE_HUNDREDTH.getValue());
        }
        else
        {
            return formatter.format(0.00d);
        }
        return formatter.format(value);
    }

    public boolean isOrderPlaced()
    {
        return this.errorMessages.isEmpty();
    }
}