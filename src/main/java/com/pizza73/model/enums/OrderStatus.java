package com.pizza73.model.enums;

/**
 * OrderStatus.java TODO comment me
 * 
 * @author chris 11-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
public enum OrderStatus
{
    TRAINING('t', "Training"), PARKED('r', "Parked"), UNPRINTED('u', "Send Order To Shop"), PRINTED('p', "Printed"), OVEN(
        'o', "Oven"), PICKUP('c', "Pickup"), DRIVER('d', "Driver"), CANCELLED('x', "Cancel");

    private final char shortValue;
    private final String longValue;

    OrderStatus(char shortValue, String longValue)
    {
        this.shortValue = shortValue;
        this.longValue = longValue;
    }

    public char getShortValue()
    {
        return this.shortValue;
    }

    public String getValue()
    {
        return this.longValue;
    }

    public static OrderStatus statusForShortValue(char value)
    {
        String stringValue = value + "";
        stringValue = stringValue.toLowerCase();
        OrderStatus s = null;
        OrderStatus[] statuses = OrderStatus.values();
        for (OrderStatus status : statuses)
        {
            if (stringValue.equals(status.getShortValue() + ""))
            {
                s = status;
                break;
            }
        }

        return s;
    }
}
