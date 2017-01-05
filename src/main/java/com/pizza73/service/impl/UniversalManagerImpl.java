package com.pizza73.service.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pizza73.dao.UniversalDao;
import com.pizza73.service.UniversalManager;

@Service("universalManager")
public class UniversalManagerImpl implements UniversalManager
{
    /**
     * UniversalDao instance, ready to charge forward and persist to the
     * database
     */
    @Resource(name = "universalDao")
    protected UniversalDao universalDao;

    public UniversalManagerImpl()
    {
    }

    /**
     * {@inheritDoc}
     */
    public Object get(Class<?> clazz, Serializable id)
    {
        return universalDao.get(clazz, id);
    }

    /**
     * {@inheritDoc}
     */
    public List<?> getAll(Class<?> clazz)
    {
        return universalDao.getAll(clazz);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(Class<?> clazz, Serializable id)
    {
        universalDao.remove(clazz, id);
    }

    /**
     * {@inheritDoc}
     */
    public Object save(Object o)
    {
        return universalDao.save(o);
    }

    /**
     * {@inheritDoc}
     */
    public void update(Object o)
    {
        universalDao.update(o);
    }

    public Calendar businessDate()
    {
        return this.universalDao.businessDate();
    }

    public String infoValueForVariable(String variable)
    {
        return this.universalDao.infoValueForVariable(variable);
    }

    public Object uniqueResultForAttribute(String tableClass, String attribute, String value)
    {
        return universalDao.uniqueResultForAttribute(tableClass, attribute, value);
    }

    public List<?> forAttribute(String tableClass, String attribute, String value)
    {
        return universalDao.forAttribute(tableClass, attribute, value);
    }

    public boolean sendEmail()
    {
        return universalDao.sendEmail();
    }

    public boolean onlineCashOnly()
    {
        return this.universalDao.onlineCashOnly();
    }

    public boolean useIndustryMailoutWS()
    {
        return this.universalDao.useIndustryMailoutWS();
    }

    public boolean parkOrders()
    {
        return this.universalDao.parkOrders();
    }
}
