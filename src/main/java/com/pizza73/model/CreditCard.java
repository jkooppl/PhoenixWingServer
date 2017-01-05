package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.pizza73.model.enums.CreditCardEnum;

/**
 * CreditCard
 * 
 * @author c
 * 
 *         8-Dec-2005 Copyright 2005 ctrlSpace (1150894 Alberta Ltd.)
 */
public class CreditCard implements Serializable
{

    private static final long serialVersionUID = 2538412231230599597L;

    @Column(name = "card_number", nullable = false, columnDefinition = "char(25)")
    private String number = "";

    @Column(name = "card_expiry_date", nullable = false, columnDefinition = "char(6)")
    private String expiration = "";

    @Transient
    private String expirationMonth;
    @Transient
    private String expirationYear = "";

    @Column(name = "card_customer_name", nullable = false, columnDefinition = "char(40)")
    private String cardHolderName = "";

    @Column(name = "card_authorization_no", nullable = false, columnDefinition = "char(25)")
    private String authorizationNumber = "";

    public CreditCard(String expiration, String number, CreditCardEnum type, String cardHolderName)
    {
        super();
        this.expiration = expiration;
        this.number = number;
        // this.type = type;
        this.cardHolderName = cardHolderName;
    }

    public CreditCard(int month, int year, String number, CreditCardEnum type, String cardHolderName)
    {
        super();
        this.expiration = year + month + "";
        this.number = number;
        this.cardHolderName = cardHolderName;
    }

    public CreditCard()
    {
        super();
    }

    public String getNumber()
    {
        return StringUtils.trimToEmpty(this.number);
    }

    /**
     * @return the authorizationNumber
     */
    public String getAuthorizationNumber()
    {
        return StringUtils.trimToEmpty(this.authorizationNumber);
    }

    /**
     * @param authorizationNumber
     *            the authorizationNumber to set
     */
    public void setAuthorizationNumber(String authorizationNumber)
    {
        this.authorizationNumber = StringUtils.trimToEmpty(authorizationNumber);
    }

    public void setNumber(String creditCardNumber)
    {
        this.number = creditCardNumber;
    }

    public String getExpiration()
    {
        return this.expiration;
    }

    public void setExpiration(String creditCardExpiration)
    {
        this.expiration = creditCardExpiration;
    }

    public String getExpirationYear()
    {
        return this.expirationYear;
    }

    public void setExpirationYear(String expirationTwo)
    {
        this.expirationYear = expirationTwo;
    }

    public String getExpirationMonth()
    {
        return this.expirationMonth;
    }

    public void setExpirationMonth(String expirationOne)
    {
        this.expirationMonth = expirationOne;
    }

    public String getCardHolderName()
    {
        return this.cardHolderName;
    }

    public void setCardHolderName(String cardHolderName)
    {
        this.cardHolderName = cardHolderName;
    }

    public void obscureCCNum()
    {
        if (this.number != null)
        {
            StringBuffer varBuffer = new StringBuffer(this.number);
            int varBegIndex = 0;
            int varEndIndex = varBuffer.length() - 5;
            for (int varIndex = varBegIndex; varIndex <= varEndIndex; varIndex++)
            {
                varBuffer.setCharAt(varIndex, '*');
            }
            this.number = varBuffer.toString();
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof CreditCard))
            return false;

        final CreditCard cc = (CreditCard) o;

        if (number != null ? !number.equals(cc.getNumber()) : cc.getNumber() != null)
            return false;
        if (expiration != null ? !expiration.equals(cc.getExpiration()) : cc.getExpiration() != null)
            return false;
        if (cardHolderName != null ? !cardHolderName.equals(cc.getCardHolderName()) : cc.getCardHolderName() != null)
            return false;
        if (authorizationNumber != null ? !authorizationNumber.equals(cc.getAuthorizationNumber()) : cc
            .getAuthorizationNumber() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result;
        result = (number != null ? number.hashCode() : 0);
        result = 29 * result + (expiration != null ? expiration.hashCode() : 0);
        result = 29 * result + (cardHolderName != null ? cardHolderName.hashCode() : 0);
        result = 29 * result + (authorizationNumber != null ? authorizationNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("number", this.number)
            .append("cardHolderName", this.cardHolderName).append("expiration", this.expiration).toString();
    }
}