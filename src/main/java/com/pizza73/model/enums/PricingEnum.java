package com.pizza73.model.enums;

import java.math.BigDecimal;

/**
 * MenuId.java TODO comment me
 * 
 * @author chris 2-Feb-07
 * 
 * @Copyright Flying Pizza 73
 */
public enum PricingEnum
{
    GST(BigDecimal.valueOf(0.05)),
    HST(BigDecimal.valueOf(0.00)),
    ONE_HUNDREDTH(BigDecimal.valueOf(0.01));



    // private static final Logger log = Logger.getLogger(SpinToWinType.class);
    private BigDecimal value;

    private PricingEnum(BigDecimal value)
    {
        this.value = value;
    }

    public BigDecimal getValue()
    {
        return this.value;
    }
}
