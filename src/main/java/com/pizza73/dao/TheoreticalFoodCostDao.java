package com.pizza73.dao;

import java.util.Calendar;
import java.util.List;

import com.pizza73.model.WeeklySales;

public interface TheoreticalFoodCostDao extends UniversalDao
{

    public WeeklySales weeklySalesForShopAndWeek(final Integer shopId, final Calendar sundayOfWeek);

    List<Object[]> weeklySalesForShopStartingOn(final int shopId, final Calendar startDate);

    public List<Object[]> allProductCosts();

    public List<Object[]> productTypes();

    public List<Object[]> packagingCosts();

}
