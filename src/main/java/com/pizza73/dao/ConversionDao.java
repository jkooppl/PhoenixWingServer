package com.pizza73.dao;

import com.pizza73.model.conversion.Iqq;
import com.pizza73.model.conversion.SalesDetail;

/**
 * ConversionJdbcDaoImpl.java TODO comment me
 * 
 * @author chris 27-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
public interface ConversionDao
{
    // public void saveSale(Sales sales);
    // public void saveDetail(SalesDetail detail);

    /**
     * @param iqq
     */
    public void saveIqq(Iqq iqq);

    public int shopOrderSequence(Integer shopId);
}
