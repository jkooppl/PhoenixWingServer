package com.pizza73.model.enums;

/**
 * OrderOrigin.java TODO comment me
 * 
 * @author chris 11-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
public enum OrderOrigin
{
    CALL_CENTER('c'), ONLINE('i'), MOBILE('m'), STORE('s');

    private final char shortValue;

    OrderOrigin(char shortValue)
    {
        this.shortValue = shortValue;
    }

    public char getShortValue()
    {
        return this.shortValue;
    }

    public static OrderOrigin originForValue(char origin)
    {
        OrderOrigin mi = null;

        OrderOrigin[] orderOrigins = OrderOrigin.values();
        for (OrderOrigin orderOrigin : orderOrigins)
        {
            if (orderOrigin.getShortValue() == origin)
            {
                mi = orderOrigin;
                break;
            }
        }

        return mi;
    }
}
