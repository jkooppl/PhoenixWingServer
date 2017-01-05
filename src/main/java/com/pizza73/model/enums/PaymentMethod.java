package com.pizza73.model.enums;

/**
 * PaymentMethod.java TODO comment me
 * 
 * @author chris 11-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
public enum PaymentMethod
{

    CASH('c', "Cash"), VISA('v', "Visa"), AMEX('a', "American Express"), MASTERCARD('m', "Master Card"), DEBIT('d',
        "Debit Card"), PIZZACARD('g', "Pizza Card");

    private final char shortValue;
    private final String longValue;

    PaymentMethod(char shortValue, String longValue)
    {
        this.shortValue = shortValue;
        this.longValue = longValue;
    }

    public char getShortValue()
    {
        return this.shortValue;
    }

    public String getLongValue()
    {
        return this.longValue;
    }

    public String getValue()
    {
        return this.longValue;
    }

    public static PaymentMethod methodForShorValue(char value)
    {
        PaymentMethod pm = null;

        PaymentMethod[] methods = PaymentMethod.values();
        for (PaymentMethod method : methods)
        {
            if (method.getShortValue() == value)
            {
                pm = method;
                break;
            }
        }

        return pm;
    }
}
