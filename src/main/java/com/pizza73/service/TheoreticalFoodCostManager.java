package com.pizza73.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.WeeklySales;
import com.pizza73.model.theoretical.ProductTotalCost;
import com.pizza73.model.theoretical.TheoreticalWeeklySales;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface TheoreticalFoodCostManager extends UniversalManager
{

    public Map<Integer, Map<Integer, ProductTotalCost>> allProductCosts();

    public WeeklySales weeklySalesForShopAndWeek(final Integer shopId, final Calendar sundayOfWeek);

    public List<TheoreticalWeeklySales> weeklySalesForShopStartingOn(final int shopId, final Calendar businessDate);

    public Map<Integer, Integer> productTypes();

    public Map<Integer, BigDecimal> packagingCosts();
}
