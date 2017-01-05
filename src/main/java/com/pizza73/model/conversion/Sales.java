package com.pizza73.model.conversion;

import com.pizza73.model.Address;
import com.pizza73.model.Municipality;
import com.pizza73.model.Order;
import com.pizza73.model.OrderCustomer;
import com.pizza73.model.enums.OrderOrigin;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Sales.java TODO comment me
 *
 * @author chris 22-Feb-07
 *
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "sales", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "sales_order_sequence", sequenceName = "order_id", allocationSize = 1)
public class Sales implements Serializable
{
    private static final Logger LOG = Logger.getLogger(Sales.class);

    // FIELDS
    private static final long serialVersionUID = 5851148115405693419L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_order_sequence")
    @Column(name = "order_id")
    private Integer id;

    @Column(name = "area_code")
    private String areaCode = "";

    @Column(name = "phone_number")
    private String phone = "";

    @Column(name = "delivery_charge")
    private BigDecimal deliveryCharge = new BigDecimal(0);

    @Column(name = "deposit_amount")
    private BigDecimal deposit = new BigDecimal(0);

    @Column(name = "sales_tax_1")
    private BigDecimal gst = new BigDecimal(0);

    @Column(name = "order_total")
    private BigDecimal total = new BigDecimal(0);

    @Temporal(TemporalType.DATE)
    @Column(name = "order_date")
    private Calendar orderDate;

    @Column(name = "order_hour")
    private String orderHour = "";

    @Column(name = "order_minute")
    private String orderMinute = "";

    @Column(name = "shop_id")
    private Integer shopId = new Integer(0);

    @Column(name = "operator_name")
    private String operator = "ONLINE";

    @Column(name = "delivery_method")
    private char deliveryMethod;

    @Column(name = "customer_short_name")
    private String customerName;

    @Column(name = "address_1")
    private String addressOne;

    @Column(name = "address_2")
    private String addressTwo = "";

    @Column(name = "address_3")
    private String addressThree = "";

    @Column(name = "location_type")
    private char locationType;

    @Column(name = "payment_method")
    private char paymentMethod;

    @Column(name = "card_customer_name")
    private final String ccName = "";

    @Column(name = "card_no")
    private final String ccNumber = "";

    @Column(name = "card_expiry_date")
    private final String ccExpiryDate = "";

    @Column(name = "card_authorization_no")
    private final String ccAuthNumber = "";

    @Column(name = "order_comment")
    private String comment = "";

    @Column(name = "repeat_count")
    private int repeatCount = 0;

    @Column(name = "order_status")
    private char status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "status_change_time")
    private Calendar statusChangeTime;

    @Column(name = "driver_id")
    private Integer driverId = new Integer(0);

    @Column(name = "caller_id_number")
    private String callerId = "";

    @Column(name = "discount_amount")
    private BigDecimal discount = BigDecimal.ZERO;

    public Sales()
    {
    }

    public Sales(Order order, String operatorName, Municipality city)
    {
        OrderCustomer oc = order.getCustomer();
        this.areaCode = oc.getPhone().substring(0, 3);
        this.phone = oc.getPhone().substring(3);
        this.deliveryCharge = convertToDecimal(order.getDeliveryCharge());
        this.discount = convertToDecimal(order.getDiscountAmount());
        this.deposit = convertToDecimal(order.getDeposit() + order.getEnviroLevy());
        this.gst = convertToDecimal(order.getGst());
        this.total = convertToDecimal(order.getTotal());
        this.orderDate = order.getBusinessDate();
        Calendar timeOrdered = Calendar.getInstance();
        int hour = timeOrdered.get(Calendar.HOUR_OF_DAY);
        if(hour <= 3)
        {
            hour = hour + 24;
        }
        this.orderHour = hour + "";
        String minute = timeOrdered.get(Calendar.MINUTE) + "";
        if(timeOrdered.get(Calendar.MINUTE) < 10)
        {
            minute = "0" + minute;
        }
        this.orderMinute = minute;
        this.shopId = order.getShopId();
        // FOR TESTING!
        // this.shopId = 99;
        String delivery = order.getDeliveryMethod() + "";
        this.deliveryMethod = CharUtils.toChar(delivery.toUpperCase());
        this.customerName = oc.getName().toLowerCase();
        if(this.customerName.length() > 20)
        {
            this.customerName = this.customerName.substring(0, 19);
        }

        Address address = oc.getAddress();
        this.addressOne = address.getStreetAddress().toUpperCase();
        if (this.addressOne.length() > 40)
        {
            this.addressOne = this.addressOne.substring(0, 39);
        }
        this.addressTwo = address.getSuiteNumber();
        String location = address.getLocationType().getId().charAt(0) + "";
        if (StringUtils.isBlank(location))
        {
            location = "H";
        }
        this.locationType = CharUtils.toChar(location.toUpperCase());

        String payment = order.getPaymentMethod() + "";
        this.paymentMethod = CharUtils.toChar(payment.toUpperCase());
        this.repeatCount = order.getRepeatCount();
        String status = order.getStatus() + "";
        this.status = CharUtils.toChar(status.toUpperCase());
        this.statusChangeTime = this.orderDate;
        String orderId = order.getId().toString();
        String originatedFrom = "IO";
        if (order.getOrigin() == OrderOrigin.MOBILE.getShortValue())
        {
            originatedFrom = "MO";
        }
        if (orderId.length() < 3)
        {
            orderId = "00" + orderId;
        }
        this.callerId = originatedFrom + orderId.substring(3);
        if (StringUtils.isNotBlank(operatorName))
        {
            if (operatorName.length() > 6)
            {
                operatorName = operatorName.substring(0, 6);
            }
            this.operator = operatorName.toUpperCase();
        }
    }

    /**
     * @param deliveryCharge
     * @return
     */
    private BigDecimal convertToDecimal(Integer deliveryCharge)
    {
        BigDecimal value = BigDecimal.valueOf(deliveryCharge);

        return value.multiply(BigDecimal.valueOf(0.01));
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    /**
     * @return the addressOne
     */
    public String getAddressOne()
    {
        return this.addressOne;
    }

    /**
     * @return the addressThree
     */
    public String getAddressThree()
    {
        return this.addressThree;
    }

    /**
     * @return the addressTwo
     */
    public String getAddressTwo()
    {
        return this.addressTwo;
    }

    /**
     * @return the areaCode
     */
    public String getAreaCode()
    {
        return this.areaCode;
    }

    /**
     * @return the callerId
     */
    public String getCallerId()
    {
        return this.callerId;
    }

    /**
     * @return the ccAuthNumber
     */
    public String getCcAuthNumber()
    {
        return this.ccAuthNumber;
    }

    /**
     * @return the ccExpiryDate
     */
    public String getCcExpiryDate()
    {
        return this.ccExpiryDate;
    }

    /**
     * @return the ccName
     */
    public String getCcName()
    {
        return this.ccName;
    }

    /**
     * @return the ccNumber
     */
    public String getCcNumber()
    {
        return this.ccNumber;
    }

    /**
     * @return the comment
     */
    public String getComment()
    {
        return this.comment;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName()
    {
        return this.customerName;
    }

    /**
     * @return the deliveryCharge
     */
    public BigDecimal getDeliveryCharge()
    {
        return this.deliveryCharge;
    }

    /**
     * @return the deliveryMethod
     */
    public char getDeliveryMethod()
    {
        return this.deliveryMethod;
    }

    /**
     * @return the deposit
     */
    public BigDecimal getDeposit()
    {
        return this.deposit;
    }

    /**
     * @return the discount
     */
    public BigDecimal getDiscount()
    {
        return this.discount;
    }

    /**
     * @return the driverId
     */
    public Integer getDriverId()
    {
        return this.driverId;
    }

    /**
     * @return the gst
     */
    public BigDecimal getGst()
    {
        return this.gst;
    }

    /**
     * @return the id
     */
    public Integer getId()
    {
        return this.id;
    }

    /**
     * @return the locationType
     */
    public char getLocationType()
    {
        return this.locationType;
    }

    /**
     * @return the operator
     */
    public String getOperator()
    {
        return this.operator;
    }

    /**
     * @return the orderDate
     */
    public Calendar getOrderDate()
    {
        return this.orderDate;
    }

    /**
     * @return the orderHour
     */
    public String getOrderHour()
    {
        return this.orderHour;
    }

    /**
     * @return the orderMinute
     */
    public String getOrderMinute()
    {
        return this.orderMinute;
    }

    /**
     * @return the paymentMethod
     */
    public char getPaymentMethod()
    {
        return this.paymentMethod;
    }

    /**
     * @return the phone
     */
    public String getPhone()
    {
        return this.phone;
    }

    /**
     * @return the repeatCount
     */
    public int getRepeatCount()
    {
        return this.repeatCount;
    }

    /**
     * @return the shopId
     */
    public Integer getShopId()
    {
        return this.shopId;
    }

    /**
     * @return the status
     */
    public char getStatus()
    {
        return this.status;
    }

    /**
     * @return the statusChangeTime
     */
    public Calendar getStatusChangeTime()
    {
        return this.statusChangeTime;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal()
    {
        return this.total;
    }

    /**
     * @param addressThree
     *            the addressThree to set
     */
    public void setAddressThree(String addressThree)
    {
        this.addressThree = addressThree;
    }

    public void setAddressTwo(String address)
    {
        this.addressTwo = address;
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public void setDiscount(BigDecimal disc)
    {
        this.discount = disc;
    }

    public void setDriverId(Integer driverId)
    {
        this.driverId = driverId;
    }

    public void setDeliveryCharge(BigDecimal deliveryCharge)
    {
        this.deliveryCharge = deliveryCharge;
    }

    @Override
    public String toString()
    {
        return "Sales [addressOne=" + this.addressOne + ", addressThree=" + this.addressThree + ", addressTwo="
            + this.addressTwo + ", areaCode=" + this.areaCode + ", callerId=" + this.callerId + ", comment=" + this.comment
            + ", customerName=" + this.customerName + ", deliveryCharge=" + this.deliveryCharge + ", id=" + this.id
            + ", operator=" + this.operator + ", orderDate=" + this.orderDate + ", phone=" + this.phone + ", total="
            + this.total + "]";
    }
}
