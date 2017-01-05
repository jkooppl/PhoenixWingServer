package com.pizza73.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza73.dao.SalesDao;
import com.pizza73.model.conversion.Sales;
import com.pizza73.service.SalesManager;

@Service("salesManager")
public class SalesManagerImpl extends UniversalManagerImpl implements SalesManager
{

    @Autowired
    private SalesDao dao;

    public SalesManagerImpl()
    {
    }

    public List<Sales> salesForEspOperatorsAndBusinessDate(Calendar date)
    {
        return this.dao.salesForEspOperatorsAndBusinessDate(date);
    }

    public Object[] salesErrorsForEspOperator(String operator, Calendar date)
    {
        return this.dao.salesErrorsForEspOperator(operator, date);
    }

}
