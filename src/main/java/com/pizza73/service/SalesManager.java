package com.pizza73.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.conversion.Sales;

/**
 * SalesManager.java TODO comment me
 * 
 * @author chris 29-July-10
 * 
 * @Copyright Flying Pizza 73
 */
@Transactional(propagation = Propagation.NEVER, readOnly = true)
public interface SalesManager extends UniversalManager
{
    public List<Sales> salesForEspOperatorsAndBusinessDate(Calendar date);

    public Object[] salesErrorsForEspOperator(String operator, Calendar date);
}
