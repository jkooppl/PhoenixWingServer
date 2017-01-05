package com.pizza73.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza73.dao.TheoreticalFoodCostDao;
import com.pizza73.model.WeeklySales;
import com.pizza73.model.theoretical.ProductTotalCost;
import com.pizza73.model.theoretical.TheoreticalWeeklySales;
import com.pizza73.service.TheoreticalFoodCostManager;

@Service("theoreticalFoodCostManager")
public class TheoreticalFoodCostManagerImpl extends UniversalManagerImpl implements TheoreticalFoodCostManager
{
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(LookupManagerImpl.class);

    @Autowired
    private TheoreticalFoodCostDao dao;

    public TheoreticalFoodCostManagerImpl()
    {
    }

    @Override
    public Map<Integer, Map<Integer, ProductTotalCost>> allProductCosts()
    {
        final Map<Integer, Map<Integer, ProductTotalCost>> costMap = new TreeMap<Integer, Map<Integer, ProductTotalCost>>();
        final List<Object[]> productCosts = this.dao.allProductCosts();
        Integer productId = null;
        Integer sizeId = null;
        String name = null;
        BigDecimal cost = null;

        for (final Object[] productCost : productCosts)
        {
            productId = (Integer) productCost[0];
            sizeId = (Integer) productCost[1];
            name = (String) productCost[2];
            cost = (BigDecimal) productCost[3];

            final ProductTotalCost totalCost = new ProductTotalCost(productId, sizeId, name, cost);
            if (costMap.containsKey(productId))
            {
                Map<Integer, ProductTotalCost> sizeMap = costMap.get(productId);
                sizeMap.put(sizeId, totalCost);
            }
            else
            {
                Map<Integer, ProductTotalCost> sizeMap = new HashMap<Integer, ProductTotalCost>();
                sizeMap.put(sizeId, totalCost);
                costMap.put(productId, sizeMap);
            }
        }

        return costMap;
    }

    @Override
    public WeeklySales weeklySalesForShopAndWeek(final Integer shopId, final Calendar sundayOfWeek)
    {
        return this.dao.weeklySalesForShopAndWeek(shopId, sundayOfWeek);
    }

    /**
     * sent_shop_id | group_id | size | crust | sauce | sum
     */
    @Override
    public List<TheoreticalWeeklySales> weeklySalesForShopStartingOn(final int shopId, final Calendar businessDate)
    {
        final List<TheoreticalWeeklySales> salesDetails = new ArrayList<TheoreticalWeeklySales>();
        final List<Object[]> weeklySalesForProduct = this.dao.weeklySalesForShopStartingOn(shopId, businessDate);
        Integer productId = null;
        Integer sizeId = null;
        Integer crustId = null;
        Integer sauceId = null;
        Integer storeId = null;
        Integer sum = null;
        for (final Object[] weeklySaleForProduct : weeklySalesForProduct)
        {
            storeId = (Integer) weeklySaleForProduct[0];
            productId = (Integer) weeklySaleForProduct[1];
            sizeId = (Integer) weeklySaleForProduct[2];
            // crustId = (Integer) weeklySaleForProduct[3];
            // sauceId = (Integer) weeklySaleForProduct[4];
            sum = (Integer) weeklySaleForProduct[3];
            final TheoreticalWeeklySales tws = new TheoreticalWeeklySales(productId, sizeId, crustId, sauceId, storeId, sum);
            salesDetails.add(tws);
        }

        return salesDetails;
    }

    @Override
    public Map<Integer, Integer> productTypes()
    {
        final Map<Integer, Integer> productTypes = new HashMap<Integer, Integer>();
        final List<Object[]> pTypes = this.dao.productTypes();
        Integer productId = null;
        Integer typeId = null;

        for (final Object[] product : pTypes)
        {
            productId = (Integer) product[0];
            typeId = (Integer) product[1];

            productTypes.put(productId, typeId);
        }

        return productTypes;
    }

    @Override
    public Map<Integer, BigDecimal> packagingCosts()
    {
        final Map<Integer, BigDecimal> packagingCosts = new HashMap<Integer, BigDecimal>();
        final List<Object[]> pCosts = this.dao.packagingCosts();
        Integer packagingCostId = null;
        BigDecimal cost = null;

        for (final Object[] pCost : pCosts)
        {
            packagingCostId = (Integer) pCost[0];
            cost = (BigDecimal) pCost[1];

            packagingCosts.put(packagingCostId, cost);
        }

        return packagingCosts;
    }
}
