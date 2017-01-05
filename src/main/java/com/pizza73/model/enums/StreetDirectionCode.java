package com.pizza73.model.enums;

/**
 * StreetDirectionCode.java TODO comment me
 * 
 * @author chris 23-Jan-07
 * 
 * @Copyright Flying Pizza 73
 */
public enum StreetDirectionCode
{
    NONE(""), NORTH_WEST("NW"), SOUTH_WEST("SW"), SOUTH_EAST("SE"), NORTH_EAST("NE"), SOUTH("S"), NORTH("N"), WEST("W"), EAST(
        "E");

    private String code;

    StreetDirectionCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return this.code;
    }

    public static String[] codeValues()
    {
        StreetDirectionCode[] values = StreetDirectionCode.values();
        String[] codeValues = new String[values.length];
        for (int i = 0; i < values.length; i++)
        {
            codeValues[i] = values[i].getCode();
        }

        return codeValues;
    }
}
