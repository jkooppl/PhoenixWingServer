package com.pizza73.model.enums;

/**
 * DeliveryMethod.java TODO comment me
 *
 * @author chris 11-Sep-06
 *
 * @Copyright Flying Pizza 73
 */
public enum DeliveryMethod
{

    DELIVER('d', "Delivery"), PICK_UP('p', "Customer Pick Up");

    private final char shortValue;
    private final String longValue;

    DeliveryMethod(char shortValue, String longValue)
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
}
