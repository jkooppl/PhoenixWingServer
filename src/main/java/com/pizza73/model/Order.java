package com.pizza73.model;

// Generated 19-Sep-2006 3:07:03 PM by Hibernate Tools 3.2.0.beta7
import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.pizza73.model.enums.DeliveryMethod;
import com.pizza73.model.enums.OrderOrigin;
import com.pizza73.model.enums.OrderStatus;
import com.pizza73.model.enums.PaymentMethod;

/**
 * Order.java TODO: foriegn key mapping to business date
 * 
 * @author chris 8-Oct-06
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_sales_order", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "order_sequence", sequenceName = "iq2_sales_order_id", allocationSize = 1)
public class Order implements Serializable
{

    private static final long serialVersionUID = 3393657130548202562L;

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @Column(name = "order_id")
    private Integer id = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "business_date", nullable = false)
    private Calendar businessDate;

    @Column(name = "origin_code", columnDefinition = "char(1)")
    private char origin = OrderOrigin.CALL_CENTER.getShortValue();

    @Column(name = "seq", nullable = false)
    private Integer sequence;

    @Column(name = "operator_id", nullable = true)
    private String operatorId;

    // Comments
    @Column(name = "repeat_count", nullable = false)
    private Integer repeatCount = new Integer(0);

    @Column(name = "comment", nullable = false, columnDefinition = "char(120)")
    private String comment = "";

    @Column(name = "confidential", nullable = false, columnDefinition = "char(40)")
    private String confidential = " ";

    // Shop and Delivery info
    @Column(name = "driver_id", nullable = false)
    private Integer driverId;

    @Column(name = "shop_id", nullable = false)
    private Integer shopId = Integer.valueOf(0);

    // default to delivery
    @Column(name = "delivery_code", columnDefinition = "char(1)")
    private char deliveryMethod = DeliveryMethod.DELIVER.getShortValue();

    @Column(name = "shop_id_delivery", nullable = false)
    private Integer shopDeliveryId;

    @Column(name = "shop_id_pickup", nullable = false)
    private Integer shopPickupId;

    @Column(name = "shop_id_redir", nullable = false)
    private Integer shopRedirectId;

    // Customer Identification fields.
    // Embedded in OrderCustomer are the fields:
    // - onlineId, phoneNumber, phoneNumberTwo, callerId, name, and address
    @Embedded
    private OrderCustomer customer;

    // Cost / Pricing
    @Column(name = "total", nullable = false)
    private Integer total;

    @Column(name = "gst", nullable = false)
    private Integer gst;

    @Column(name = "pst", nullable = false)
    private Integer pst = new Integer(0);

    @Column(name = "discount_amount", nullable = false)
    private Integer discountAmount = new Integer(0);

    @Column(name = "credit_available", nullable = false)
    private Integer creditAvailable = new Integer(0);

    @Column(name = "credit_used", nullable = false)
    private Integer creditUsed = new Integer(0);

    @Column(name = "envirolevy", nullable = false)
    private Integer enviroLevy = new Integer(0);

    @Column(name = "net", nullable = false)
    private Integer net = new Integer(0);

    @Column(name = "deposit", nullable = false)
    private Integer deposit = new Integer(0);

    @Column(name = "delivery_charge", nullable = false)
    private Integer deliveryCharge = Integer.valueOf(0);

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.INSERT)
    @Column(name = "time_ordered", nullable = true, updatable = false, insertable = false)
    private Calendar timeOrdered;

    // STATUS FIELDS
    @Column(name = "status", columnDefinition = "char(1)")
    private char status = OrderStatus.UNPRINTED.getShortValue(); // default to
    // unprinted
    @Column(name = "status_sub_code", nullable = false, columnDefinition = "char(1)")
    private String statusSubCode = " ";

    @Column(name = "order_id_replaced", nullable = false, updatable = false)
    private Integer changeOrder;

    @Column(name = "cashed_out")
    private Integer cashedOut = Integer.valueOf(0);

    @Column(name = "payment_method", columnDefinition = "char(1)")
    private char paymentMethod = PaymentMethod.DEBIT.getShortValue();

    // Replication fields
    @Column(name = "tid")
    private Integer tid;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.INSERT)
    @Column(name = "ts", insertable = false, updatable = false)
    private Calendar ts;

    @Column(name = "rs")
    private Integer rs = new Integer(0);

    @Column(name = "barcode")
    private String barcode = " ";

    @Column(name = "blacklisted")
    private String blackListed = " ";

    @Column(name = "coupon_code")
    private String couponCode = "";

    @Transient
    private Integer version;

    @Column(name = "contest_opt_in")
    private boolean contestOptIn = false;

    @Column(name = "launch_discountable")
    private boolean lunchDiscountable = false;

    public boolean isLunchDiscountable()
    {
        return lunchDiscountable;
    }

    public void setLunchDiscountable(boolean lunchDiscountable)
    {
        this.lunchDiscountable = lunchDiscountable;
    }

    // Constructors
    /** default constructor */
    public Order()
    {
    }

    /**
     * @param orderCustomer
     */
    public Order(OrderCustomer orderCustomer)
    {
        this.customer = orderCustomer;
    }

    // Property accessors
    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Calendar getBusinessDate()
    {
        return this.businessDate;
    }

    public void setBusinessDate(Calendar date)
    {
        this.businessDate = date;
    }

    public Integer getSequence()
    {
        return this.sequence;
    }

    public void setSequence(Integer seq)
    {
        this.sequence = seq;
    }

    public String getOperatorId()
    {
        return this.operatorId;
    }

    public void setOperatorId(String callCenterAgent)
    {
        this.operatorId = callCenterAgent;
    }

    public Integer getDriverId()
    {
        return this.driverId;
    }

    public void setDriverId(Integer driver)
    {
        this.driverId = driver;
    }

    public OrderCustomer getCustomer()
    {
        return this.customer;
    }

    public void setCustomer(OrderCustomer customer)
    {
        this.customer = customer;
    }

    public Integer getShopId()
    {
        return this.shopId;
    }

    public void setShopId(Integer shop)
    {
        this.shopId = shop;
    }

    public Integer getTotal()
    {
        return this.total;
    }

    public void setTotal(Integer total)
    {
        this.total = total;
    }

    public Integer getGst()
    {
        return this.gst;
    }

    public void setGst(Integer gst)
    {
        this.gst = gst;
    }

    public Integer getPst()
    {
        return this.pst;
    }

    public void setPst(Integer pst)
    {
        this.pst = pst;
    }

    public Integer getDiscountAmount()
    {
        return this.discountAmount;
    }

    public void setDiscountAmount(Integer discount)
    {
        this.discountAmount = discount;
    }

    public Integer getDeposit()
    {
        return this.deposit;
    }

    public void setDeposit(Integer deposit)
    {
        this.deposit = deposit;
    }

    public char getPaymentMethod()
    {
        return this.paymentMethod;
    }

    public void setPaymentMethod(char paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public char getDeliveryMethod()
    {
        return this.deliveryMethod;
    }

    public void setDeliveryMethod(char deliveryMethod)
    {
        this.deliveryMethod = deliveryMethod;
    }

    public Calendar getTimeOrdered()
    {
        return this.timeOrdered;
    }

    public void setTimeOrdered(Calendar deliveryTime)
    {
        this.timeOrdered = deliveryTime;
    }

    public String getComment()
    {
        return StringUtils.trimToEmpty(this.comment);
    }

    // comment can be no longer than 120 characters.
    public void setComment(String comment)
    {
        String tempComment = comment;
        if (comment.length() > 119)
        {
            tempComment = comment.substring(0, 119);
        }
        this.comment = tempComment;
    }

    public char getStatus()
    {
        return this.status;
    }

    public void setStatus(char status)
    {
        this.status = status;
    }

    public char getOrigin()
    {
        return this.origin;
    }

    public void setOrigin(char origin)
    {
        this.origin = origin;
    }

    /**
     * @return the cashedOut
     */
    public Integer isCashedOut()
    {
        return this.cashedOut;
    }

    /**
     * @param cashedOut
     *            the cashedOut to set
     */
    public void setCashedOut(Integer cashedOut)
    {
        this.cashedOut = cashedOut;
    }

    /**
     * @return the changeOrder
     */
    public Integer getChangeOrder()
    {
        return this.changeOrder;
    }

    /**
     * @param changeOrder
     *            the changeOrder to set
     */
    public void setChangeOrder(Integer changeOrder)
    {
        this.changeOrder = changeOrder;
    }

    /**
     * @return the confidential
     */
    public String getConfidential()
    {
        return this.confidential;
    }

    /**
     * @param confidential
     *            the confidential to set
     */
    public void setConfidential(String confidential)
    {
        this.confidential = confidential;
    }

    /**
     * @return the creditAvailable
     */
    public Integer getCreditAvailable()
    {
        return this.creditAvailable;
    }

    /**
     * @param creditAvailable
     *            the creditAvailable to set
     */
    public void setCreditAvailable(Integer credit)
    {
        this.creditAvailable = credit;
    }

    // /**
    // * @return the creditCard
    // */
    // public CreditCard getCreditCard()
    // {
    // return this.creditCard;
    // }
    //
    // /**
    // * @param creditCard
    // * the creditCard to set
    // */
    // public void setCreditCard(CreditCard creditCard)
    // {
    // this.creditCard = creditCard;
    // }

    /**
     * @return the deliveryCharge
     */
    public Integer getDeliveryCharge()
    {
        return this.deliveryCharge;
    }

    /**
     * @param deliveryCharge
     *            the deliveryCharge to set
     */
    public void setDeliveryCharge(Integer deliveryCharge)
    {
        this.deliveryCharge = deliveryCharge;
    }

    /**
     * @return the enviroLevy
     */
    public Integer getEnviroLevy()
    {
        return this.enviroLevy;
    }

    /**
     * @param enviroLevy
     *            the enviroLevy to set
     */
    public void setEnviroLevy(Integer enviroLevy)
    {
        this.enviroLevy = enviroLevy;
    }

    /**
     * @return the repeatCount
     */
    public Integer getRepeatCount()
    {
        return this.repeatCount;
    }

    /**
     * @param repeatCount
     *            the repeatCount to set
     */
    public void setRepeatCount(Integer repeatCount)
    {
        this.repeatCount = repeatCount;
    }

    /**
     * @return the rs
     */
    public Integer getRs()
    {
        return this.rs;
    }

    /**
     * @param rs
     *            the rs to set
     */
    public void setRs(Integer rs)
    {
        this.rs = rs;
    }

    /**
     * @return the shopDeliveryId
     */
    public Integer getShopDeliveryId()
    {
        return this.shopDeliveryId;
    }

    /**
     * @param shopDeliveryId
     *            the shopDeliveryId to set
     */
    public void setShopDeliveryId(Integer shopDelivery)
    {
        this.shopDeliveryId = shopDelivery;
    }

    /**
     * @return the shopPickupId
     */
    public Integer getShopPickupId()
    {
        return this.shopPickupId;
    }

    /**
     * @param shopPickupId
     *            the shopPickupId to set
     */
    public void setShopPickupId(Integer shopPickup)
    {
        this.shopPickupId = shopPickup;
    }

    /**
     * @return the shopRedirectId
     */
    public Integer getShopRedirectId()
    {
        return this.shopRedirectId;
    }

    /**
     * @param shopRedirectId
     *            the shopRedirectId to set
     */
    public void setShopRedirectId(Integer shopRedirect)
    {
        this.shopRedirectId = shopRedirect;
    }

    /**
     * @return the statusSubCode
     */
    public String getStatusSubCode()
    {
        return this.statusSubCode;
    }

    /**
     * @param statusSubCode
     *            the statusSubCode to set
     */
    public void setStatusSubCode(String statusSubCode)
    {
        this.statusSubCode = statusSubCode;
    }

    /**
     * @return the tid
     */
    public Integer getTid()
    {
        return this.tid;
    }

    /**
     * @param tid
     *            the tid to set
     */
    public void setTid(Integer tid)
    {
        this.tid = tid;
    }

    /**
     * @return the ts
     */
    public Calendar getTs()
    {
        return this.ts;
    }

    /**
     * @param ts
     *            the ts to set
     */
    public void setTs(Calendar ts)
    {
        this.ts = ts;
    }

    public Integer getVersion()
    {
        return this.version;
    }

    protected void setVersion(Integer version)
    {
        this.version = version;
    }

    /**
     * @return the net
     */
    public Integer getNet()
    {
        return this.net;
    }

    /**
     * @param net
     *            the net to set
     */
    public void setNet(Integer net)
    {
        this.net = net;
    }

    /**
     * @return the creditUsed
     */
    public Integer getCreditUsed()
    {
        return this.creditUsed;
    }

    /**
     * @param creditUsed
     *            the creditUsed to set
     */
    public void setCreditUsed(Integer creditUsed)
    {
        this.creditUsed = creditUsed;
    }

    /**
     * @return the barcode
     */
    public String getBarcode()
    {
        return this.barcode.replace('\0', ' ');
    }

    /**
     * @param barcode
     *            the barcode to set
     */
    public void setBarcode(String barcode)
    {
        this.barcode = barcode;
    }

    /**
     * @return the blackListed
     */
    public String getBlackListed()
    {
        return this.blackListed.replace('\0', ' ');
    }

    /**
     * @param blackListed
     *            the blackListed to set
     */
    public void setBlackListed(String blackListed)
    {
        this.blackListed = blackListed;
    }

    public void setCouponCode(String code)
    {
        this.couponCode = code;
    }

    public String getCouponCode()
    {
        return this.couponCode;
    }

    public String getOrderOriginValue()
    {
        return OrderOrigin.originForValue(this.origin).name();
    }

    /**
     * @return the cashedOut
     */
    public Integer getCashedOut()
    {
        return this.cashedOut;
    }

    public boolean isCreditCardOrder()
    {
        if (paymentMethod == PaymentMethod.VISA.getShortValue() || paymentMethod == PaymentMethod.MASTERCARD.getShortValue()
            || paymentMethod == PaymentMethod.AMEX.getShortValue())
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object o)
    {
        return super.equals(o);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    public boolean isContestOptIn()
    {
        return this.contestOptIn;
    }

    public void setContestOptIn(boolean contestOptIn)
    {
        this.contestOptIn = contestOptIn;
    }
}