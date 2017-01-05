package com.pizza73.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.pizza73.model.enums.DeliveryMethod;
import com.pizza73.model.enums.PaymentMethod;

/**
 * OnlineCustomer.java TODO comment me
 *
 * @author chris 6-Nov-06
 *
 * @Copyright Flying Pizza 73
 */

@Entity
@Table(name = "iq2_online_customer", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "online_sequence", sequenceName = "iq2_online_customer_id", allocationSize = 1)
@JsonIgnoreProperties(value = { "id", "accountLocked", "accountExpired", "accountNonExpired", "accountNonLocked",
        "accountError", "authorities", "comment", "commentWellDone", "commentEasySauce", "commentEasyCheese",
        "commentPickupTime", "commentPickupTimeContent", "commentDeliveryTime", "commentDeliveryTimeContent",
        "commentRemoveTopping", "commentRemoveToppingContent", "confirmEmail", "contestEntry", "contestId",
        "confirmPassword", "contestOptIn", "createdOn", "creditCard", "credits", "credentialsExpired",
        "credentialsNonExpired", "lastOrder", "login", "oldPassword", "optOut", "roles", "repeatCount", "enabled" })
public class OnlineCustomer extends User implements UserDetails, Serializable
{
    // FIELDS
    private static final long serialVersionUID = -1137259299845879386L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "online_sequence")
    @Column(name = "online_id")
    @JsonProperty("customerId")
    private Integer id = null;

    @Embedded
    private Address address = new Address();

    @Type(type = "trim")
    @Column(unique = true, length = 64)
    private String email;

    @Transient
    private String confirmEmail;

    @Transient
    private CreditCard creditCard = new CreditCard();

    @Transient
    private PaymentMethod paymentMethod = PaymentMethod.DEBIT; // default

    @Transient
    private DeliveryMethod delivery = DeliveryMethod.DELIVER; // default

    @Transient
    private boolean login;

    @Transient
    private Integer contestId;

    @Transient
    private String comment;

    @Column(name = "shop_id")
    private Integer shopId = new Integer(0);

    @Column(name = "repeat_count")
    private Integer repeatCount = new Integer(0);

    @Type(type = "trim")
    @Column(name = "password")
    private String password = "";

    @Transient
    private String confirmPassword = "";

    @Transient
    private String oldPassword = "";

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "iq2_user_role", joinColumns = { @JoinColumn(name = "online_id") }, inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<Role>();

    @Column(nullable = false)
    private boolean subscribed = false;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private final boolean accountLocked = false;

    @Column(nullable = false)
    private boolean credentialsExpired = false;

    @Column(nullable = false)
    private final boolean accountExpired = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.INSERT)
    @Column(name = "created_on", nullable = true, updatable = false, insertable = false)
    private Calendar createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "opt_in_date")
    private Calendar optInDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "opt_out_date")
    private Calendar optOutDate;

    @Transient
    Integer lastOrder = null;

    @Transient
    private String couponCode = "";

    @Transient
    private List<Credit> credits;

    @Transient
    private boolean commentWellDone = false;

    @Transient
    private boolean commentEasySauce = false;

    @Transient
    private boolean commentEasyCheese = false;

    @Transient
    private boolean commentPickupTime = false;

    @Transient
    private String commentPickupTimeContent = "";

    @Transient
    private boolean commentDeliveryTime = false;

    @Transient
    private String commentDeliveryTimeContent = "";

    @Transient
    private boolean commentRemoveTopping = false;

    @Transient
    private String commentRemoveToppingContent = "";

    @Transient
    private String accountError = "";

    @Transient
    private boolean contestOptIn;

    /**
     * Default Constructor
     */
    public OnlineCustomer()
    {
    }

    @Override
    public boolean equals(final Object o)
    {
        if (super.equals(o))
        {
            return false;
        }
        if (this == o)
            return true;
        if (!(o instanceof OnlineCustomer))
            return false;

        final OnlineCustomer oc = (OnlineCustomer) o;

        if (this.email != null ? !this.email.equals(oc.getEmail()) : oc.getEmail() != null)
            return false;
        if (this.creditCard != null ? !this.creditCard.equals(oc.getCreditCard()) : oc.getCreditCard() != null)
            return false;
        if (this.password != null ? !this.password.equals(oc.getPassword()) : oc.getPassword() != null)
            return false;

        return true;
    }

    public String getAccountError()
    {
        return this.accountError;
    }

    public Address getAddress()
    {
        return this.address;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.security.userdetails.UserDetails#getAuthorities()
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities()
    {
        final Collection<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        if (!getRoles().isEmpty())
        {
            for (final Role role : getRoles())
            {
                list.add(new GrantedAuthorityImpl(role.getName()));
            }
            return list;
        }
        return null;
    }

    public String getComment()
    {
        return this.comment;
    }

    public String getCommentDeliveryTimeContent()
    {
        return this.commentDeliveryTimeContent;
    }

    public String getCommentPickupTimeContent()
    {
        return this.commentPickupTimeContent;
    }

    public String getCommentRemoveToppingContent()
    {
        return this.commentRemoveToppingContent;
    }

    /**
     * @return the confirmEmail
     */
    public String getConfirmEmail()
    {
        return this.confirmEmail;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword()
    {
        return this.confirmPassword;
    }

    public Integer getContestId()
    {
        return this.contestId;
    }

    public String getCouponCode()
    {
        return this.couponCode;
    }

    public Calendar getCreatedOn()
    {
        return this.createdOn;
    }

    public CreditCard getCreditCard()
    {
        return this.creditCard;
    }

    public List<Credit> getCredits()
    {
        return this.credits;
    }

    /**
     * @return the delivery
     */
    public DeliveryMethod getDelivery()
    {
        return this.delivery;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return this.email;
    }

    public Integer getId()
    {
        return this.id;
    }

    /**
     * @return the lastOrder
     */
    public Integer getLastOrder()
    {
        return this.lastOrder;
    }

    /**
     * @return the confirmPassword
     */
    public String getOldPassword()
    {
        return this.oldPassword;
    }

    public Calendar getOptOutDate()
    {
        return optOutDate;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    /**
     * @return the paymentMethod
     */
    public PaymentMethod getPaymentMethod()
    {
        return this.paymentMethod;
    }

    /**
     * @return the repeatCount
     */
    public Integer getRepeatCount()
    {
        return this.repeatCount;
    }

    /**
     * @return the roles
     */
    public Set<Role> getRoles()
    {
        return this.roles;
    }

    /**
     * @return the shopId
     */
    public Integer getShopId()
    {
        return this.shopId;
    }

    @Override
    public String getUsername()
    {
        return this.email;
    }

    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    public Calendar getOptInDate()
    {
        return optInDate;
    }

    public void setOptInDate(Calendar optInDate)
    {
        this.optInDate = optInDate;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (this.email != null ? this.email.hashCode() : 0);
        result = 29 * result + (this.creditCard != null ? this.creditCard.hashCode() : 0);
        result = 29 * result + (this.password != null ? this.password.hashCode() : 0);

        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.security.userdetails.UserDetails#isAccountNonExpired
     * ()
     */
    @Override
    public boolean isAccountNonExpired()
    {
        return !this.accountExpired;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
     */
    @Override
    public boolean isAccountNonLocked()
    {
        return !this.accountLocked;
    }

    public boolean isCommentDeliveryTime()
    {
        return this.commentDeliveryTime;
    }

    public boolean isCommentEasyCheese()
    {
        return this.commentEasyCheese;
    }

    public boolean isCommentEasySauce()
    {
        return this.commentEasySauce;
    }

    public boolean isCommentPickupTime()
    {
        return this.commentPickupTime;
    }

    public boolean isCommentRemoveTopping()
    {
        return this.commentRemoveTopping;
    }

    public boolean isCommentWellDone()
    {
        return this.commentWellDone;
    }

    public boolean isCredentialsExpired()
    {
        return this.credentialsExpired;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired
     * ()
     */
    @Override
    public boolean isCredentialsNonExpired()
    {
        return !this.credentialsExpired;
    }

    /**
     * @return the enabled
     */
    @Override
    public boolean isEnabled()
    {
        return this.enabled;
    }

    public boolean isLogin()
    {
        return this.login;
    }

    public boolean isSubscribed()
    {
        return this.subscribed;
    }

    public void setAccountError(final String accountError)
    {
        this.accountError = accountError;
    }

    public void setAddress(final Address address)
    {
        this.address = address;
    }

    public void setComment(final String comment)
    {
        this.comment = comment;
    }

    public void setCommentDeliveryTime(final boolean commentDeliveryTime)
    {
        this.commentDeliveryTime = commentDeliveryTime;
    }

    public void setCommentDeliveryTimeContent(final String commentDeliveryTimeContent)
    {
        this.commentDeliveryTimeContent = commentDeliveryTimeContent;
    }

    public void setCommentEasyCheese(final boolean commentEasyCheese)
    {
        this.commentEasyCheese = commentEasyCheese;
    }

    public void setCommentEasySauce(final boolean commentEasySauce)
    {
        this.commentEasySauce = commentEasySauce;
    }

    public void setCommentPickupTime(final boolean commentPickupTime)
    {
        this.commentPickupTime = commentPickupTime;
    }

    public void setCommentPickupTimeContent(final String commentPickupTimeContent)
    {
        this.commentPickupTimeContent = commentPickupTimeContent;
    }

    public void setCommentRemoveTopping(final boolean commentRemoveTopping)
    {
        this.commentRemoveTopping = commentRemoveTopping;
    }

    public void setCommentRemoveToppingContent(final String commentRemoveToppingContent)
    {
        this.commentRemoveToppingContent = commentRemoveToppingContent;
    }

    public void setCommentWellDone(final boolean commentWellDone)
    {
        this.commentWellDone = commentWellDone;
    }

    /**
     * @param confirmEmail
     *            the confirmEmail to set
     */
    public void setConfirmEmail(final String confirmEmail)
    {
        this.confirmEmail = confirmEmail.toLowerCase();
    }

    /**
     * @param confirmPassword
     *            the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword)
    {
        if (confirmPassword != null)
        {
            confirmPassword = confirmPassword.trim();
        }
        this.confirmPassword = confirmPassword;
    }

    public void setContestId(final Integer contestId)
    {
        this.contestId = contestId;
    }

    public void setCouponCode(final String code)
    {
        this.couponCode = code;
    }

    public void setCreatedOn(final Calendar timeOrdered)
    {
        this.createdOn = timeOrdered;
    }

    public void setCredentialsExpired(final boolean credentialsExpired)
    {
        this.credentialsExpired = credentialsExpired;
    }

    public void setCreditCartd(final CreditCard cc)
    {
        this.creditCard = cc;
    }

    public void setCredits(final List<Credit> credit)
    {
        this.credits = credit;
    }

    /**
     * @param delivery
     *            the delivery to set
     */
    public void setDelivery(final DeliveryMethod delivery)
    {
        this.delivery = delivery;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email)
    {
        if (email != null)
        {
            email = email.trim();
            email = email.toLowerCase();
        }

        this.email = email;
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public void setEnabled(final boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    /**
     * @param lastOrder
     *            the lastOrder to set
     */
    public void setLastOrder(final Integer lastOrder)
    {
        this.lastOrder = lastOrder;
    }

    public void setLogin(final boolean login)
    {
        this.login = login;
    }

    /**
     * @param oldPassword
     *            the confirmPassword to set
     */
    public void setOldPassword(String oldPassword)
    {
        if (oldPassword != null)
        {
            oldPassword = oldPassword.trim();
        }
        this.oldPassword = oldPassword;
    }

    public void setOptOutDate(Calendar optOutDate)
    {
        this.optOutDate = optOutDate;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password)
    {
        if (password != null)
        {
            password = password.trim();
        }
        this.password = password;
    }

    /**
     * @param paymentMethod
     *            the paymentMethod to set
     */
    public void setPaymentMethod(final PaymentMethod paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    /**
     * @param repeatCount
     *            the repeatCount to set
     */
    public void setRepeatCount(final Integer repeatCount)
    {
        this.repeatCount = repeatCount;
    }

    /**
     * @param roles
     *            the roles to set
     */
    public void setRoles(final Set<Role> roles)
    {
        this.roles = roles;
    }

    /**
     * @param shopId
     *            the shopId to set
     */
    public void setShopId(final Integer shop)
    {
        this.shopId = shop;
    }

    public void setSubscribed(final boolean subscribed)
    {
        this.subscribed = subscribed;
    }

    public boolean isContestOptIn()
    {
        return contestOptIn;
    }

    public void setContestOptIn(boolean contestOptIn)
    {
        this.contestOptIn = contestOptIn;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("email", this.email).toString();
    }
}