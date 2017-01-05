package com.pizza73.model.enums;

/**
 * MenuId.java TODO comment me
 *
 * @author chris 2-Feb-07
 *
 * @Copyright Flying Pizza 73
 */
public enum CityEnum
{

    EDMONTON(1), CALGARY(2), RED_DEER(3), FORT_MCMURRAY(4), LETHBRIDGE(5), GRANDE_PRAIRIE(6), PRINCE_GEORGE(9), SPRUCE_GROVE(
        10), STONY_PLAIN(11), MEDICINE_HAT(12), ST_ALBERT(13), SHERWOOD_PARK(14), LLOYDMINSTER(15), AIRDRIE(16), MORINVILLE(
        17), NISKU(18), SASKATOON(19), LEDUC(20), FORT_SASKATCHEWAN(21), CAMROSE(22), OKOTOKS(23), FORT_ST_JOHNS(24);

    private Integer id;

    private CityEnum(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return this.id;
    }

    public static CityEnum valueForId(Integer couponId)
    {
        CityEnum cityType = null;

        CityEnum[] cityTypes = CityEnum.values();
        for (CityEnum city : cityTypes)
        {
            if (city.getId().equals(couponId))
            {
                cityType = city;
                break;
            }
        }

        return cityType;
    }

    public boolean isEqualToCityId(Integer cityId)
    {
        return null != cityId ? this.id.equals(cityId) : false;
    }
}
