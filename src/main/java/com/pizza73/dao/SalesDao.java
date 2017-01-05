/**
 * 
 */
package com.pizza73.dao;

import java.util.Calendar;
import java.util.List;

import com.pizza73.model.conversion.Sales;

/**
 * @author chris
 * 
 */
public interface SalesDao extends UniversalDao
{
    public List<Sales> salesForEspOperatorsAndBusinessDate(Calendar date);

    public Object[] salesErrorsForEspOperator(String operator, Calendar date);
}
