package com.pizza73.service.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.pizza73.dao.GenericDao;
import com.pizza73.service.GenericManager;

/**
 * Base class for Business Services - use this class for utility methods and
 * generic CRUD methods.
 * 
 * <p>
 * <a href="BaseManager.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
// @Service("genericManager")
public class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK>
{
    protected final Logger log = Logger.getLogger(getClass());
    protected GenericDao<T, PK> genericDao = null;

    // @Autowired
    public GenericManagerImpl(final GenericDao<T, PK> dao)
    {
        this.genericDao = dao;
    }

    /**
     * @see com.pizza73.service.GenericManager#get(java.lang.Class,
     *      java.io.Serializable)
     */
    public T get(PK id)
    {
        return this.genericDao.get(id);
    }

    /**
     * @see com.pizza73.service.GenericManager#getAll(java.lang.Class)
     */
    public List<T> getAll()
    {
        return this.genericDao.getAll();
    }

    /**
     * @see com.pizza73.service.GenericManager#remove(java.lang.Class,
     *      java.io.Serializable)
     */
    public void remove(PK id)
    {
        this.genericDao.remove(id);
    }

    /**
     * @see com.pizza73.service.GenericManager#save(java.lang.Object)
     */
    public T save(T o)
    {
        return this.genericDao.save(o);
    }

    public boolean exists(PK id)
    {
        return genericDao.exists(id);
    }

    public Calendar businessDate()
    {
        return this.genericDao.businessDate();
    }

    public String infoValueForVariable(String variable)
    {
        return this.genericDao.infoValueForVariable(variable);
    }
}