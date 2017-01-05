package com.pizza73.model.enums;

public enum SalesDetailCompositionEnum
{
    DIP(1), WING_FLAVOR(2), NO_DIP(3);

    private Integer code;

    SalesDetailCompositionEnum(Integer code)
    {
        this.code = code;
    }

    /**
     * @return the code
     */
    public Integer getCode()
    {
        return this.code;
    }

    public static Integer compositionForCode(Integer code)
    {
        for (SalesDetailCompositionEnum comp : SalesDetailCompositionEnum.values())
        {
            if (comp.code.equals(code))
            {
                return comp.code;
            }
        }

        return null;
    }
}
