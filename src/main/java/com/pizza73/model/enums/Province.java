package com.pizza73.model.enums;

/**
 * Province.java TODO comment me
 * 
 * @author chris 11-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
public enum Province
{

    ALBERTA("AB", "Alberta"), BC("BC", "British Columbia"), MANITOBA("MB", "British Columbia"), NEW_BRUNSWICK("NB",
        "New Brunswick"), NEWFOUNDLAND("NL", "Newfoundland"), NORTHWEST_TERRITORIES("NT", "Northwest Territories"), NOVA_SCOTIA(
        "NS", "Nova Scotia"), NUNAVUT("NU", "Nunavut"), ONTARIO("ON", "Ontario"), PRINCE_EDWARD_ISLAND("PE",
        "Prince Edward Island"), QUEBEC("QC", "British Columbia"), SASKATCHEWAN("SK", "Saskatchewan"), YUKON("YT", "Yukon");

    private final String shortValue;
    private final String longValue;

    Province(String shortValue, String longValue)
    {
        this.shortValue = shortValue;
        this.longValue = longValue;
    }

    public String getShortValue()
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

    public String toString()
    {
        return this.shortValue;
    }
}
