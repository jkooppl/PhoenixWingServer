package com.pizza73.util;

import com.pizza73.model.enums.CityEnum;
import com.pizza73.model.enums.MenuId;
import org.apache.commons.lang.ArrayUtils;

import static com.pizza73.model.enums.CityEnum.AIRDRIE;
import static com.pizza73.model.enums.CityEnum.CAMROSE;
import static com.pizza73.model.enums.CityEnum.FORT_MCMURRAY;
import static com.pizza73.model.enums.CityEnum.FORT_SASKATCHEWAN;
import static com.pizza73.model.enums.CityEnum.GRANDE_PRAIRIE;
import static com.pizza73.model.enums.CityEnum.LEDUC;
import static com.pizza73.model.enums.CityEnum.LLOYDMINSTER;
import static com.pizza73.model.enums.CityEnum.MEDICINE_HAT;
import static com.pizza73.model.enums.CityEnum.NISKU;
import static com.pizza73.model.enums.CityEnum.OKOTOKS;
import static com.pizza73.model.enums.CityEnum.PRINCE_GEORGE;
import static com.pizza73.model.enums.CityEnum.SASKATOON;
import static com.pizza73.model.enums.CityEnum.SPRUCE_GROVE;
import static com.pizza73.model.enums.CityEnum.STONY_PLAIN;
import static com.pizza73.model.enums.CityEnum.ST_ALBERT;

/**
 * Created by chris on 2014-06-09.
 */
public class CityUtil
{

    public static final int[] DELIVERY_CITIES_450 = new int[]{6, 15};

    public static final Integer[] SINGLE_PIZZA_CITIES = new Integer[]{CityEnum.LLOYDMINSTER.getId(), CityEnum.SASKATOON.getId(), CityEnum.LEDUC.getId(),
        CityEnum.FORT_SASKATCHEWAN.getId(), CityEnum.CAMROSE.getId(), CityEnum.OKOTOKS.getId()};

    public static final Integer[] CALGARY_AND_AREA = new Integer[]{CityEnum.CALGARY.getId(), CityEnum.AIRDRIE.getId(),
        CityEnum.OKOTOKS.getId(), CityEnum.RED_DEER.getId(), CityEnum.LETHBRIDGE.getId(), CityEnum.MEDICINE_HAT.getId()};

    public static final Integer[] TOYSR_US_EXCEPTION_CITIES = new Integer[]{FORT_MCMURRAY.getId(), GRANDE_PRAIRIE.getId(),
        PRINCE_GEORGE.getId(), LLOYDMINSTER.getId(), MEDICINE_HAT.getId()};

    public static final Integer[] WEEKDAY_EARLY_CLOSE = new Integer[]{SPRUCE_GROVE.getId(), STONY_PLAIN.getId(), ST_ALBERT.getId(),
        CityEnum.SHERWOOD_PARK.getId(), LLOYDMINSTER.getId(), AIRDRIE.getId(), NISKU.getId(), LEDUC.getId(), FORT_SASKATCHEWAN.getId(),
        CAMROSE.getId(), SASKATOON.getId()};

    public static final Integer[] WEEKEND_EARLY_CLOSE = new Integer[]{LLOYDMINSTER.getId(), NISKU.getId(), LEDUC.getId(),
        FORT_SASKATCHEWAN.getId(), CAMROSE.getId(), OKOTOKS.getId()};

    public static boolean is450Delivery(Integer cityId)
    {
        return cityId != null ? ArrayUtils.contains(CityUtil.DELIVERY_CITIES_450, cityId) : false;
    }

    public static boolean weekDayEarlClose(Integer cityId)
    {
        return cityId != null ? ArrayUtils.contains(WEEKDAY_EARLY_CLOSE, cityId) : false;
    }

    public static boolean weekEndEarlClose(Integer cityId)
    {
        return cityId != null ? ArrayUtils.contains(WEEKEND_EARLY_CLOSE, cityId) : false;
    }

    public static boolean singleCityPizza(Integer cityId)
    {
        return cityId != null ? ArrayUtils.contains(CityUtil.SINGLE_PIZZA_CITIES, cityId) : false;
    }

    public static boolean calgaryAndArea(Integer cityId)
    {
        return cityId != null ? ArrayUtils.contains(CityUtil.CALGARY_AND_AREA, cityId) : false;
    }

    public static boolean saskatoon(Integer cityId)
    {
        return cityId != null ? CityEnum.SASKATOON.isEqualToCityId(cityId) : false;
    }

    public static Boolean isBC(Integer menuId)
    {
        return MenuId.BC_WEB.isEqualToMenuId(menuId) || MenuId.BC_MOBILE.isEqualToMenuId(menuId);
    }
}
