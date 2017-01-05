package com.pizza73.model.enums;

/**
 * CreditCardEnum
 * 
 * @author c
 * 
 *         7-Dec-2005 Copyright 2005 ctrlSpace (1150894 Alberta Ltd.)
 */
public enum CreditCardEnum
{
    VISA("Visa"), AMEX("American Express"), MASTERCARD("Master Card");

    private String value;

    private CreditCardEnum(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }
}
