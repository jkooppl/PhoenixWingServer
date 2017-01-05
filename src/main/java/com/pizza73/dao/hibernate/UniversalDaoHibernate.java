package com.pizza73.dao.hibernate;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import com.pizza73.dao.UniversalDao;
import com.pizza73.model.Info;

/**
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Repository("universalDao")
public class UniversalDaoHibernate implements UniversalDao
{
    protected final Logger log = Logger.getLogger(UniversalDaoHibernate.class);
    @Autowired
    private SessionFactory sf;

    public UniversalDaoHibernate()
    {
    }

    public SessionFactory getSessionFactory()
    {
        return sf;
    }

    public Session getCurrentSession()
    {
        return sf.getCurrentSession();
    }

    /**
     * @see com.pizza73.dao.UniversalDao#save(java.lang.Object)
     */
    public Object save(Object o)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Saving: " + o);
        }
        return this.getCurrentSession().merge(o);
    }

    /**
     * @see com.pizza73.dao.UniversalDao#update(java.lang.Object)
     */
    public void update(Object o)
    {
        this.getCurrentSession().update(o);
    }

    /**
     * @see com.pizza73.dao.GenericDao#get(java.lang.Class,
     *      java.io.Serializable)
     */
    public Object get(Class<?> clazz, Serializable id)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Retrieiving " + getClassIdString(clazz, id));
        }
        Object o = this.getCurrentSession().get(clazz, id);

        if (o == null)
        {
            log.warn("Could not retrieve " + getClassIdString(clazz, id));
            throw new ObjectRetrievalFailureException(clazz, id);
        }

        return o;
    }

    /**
     * @see com.pizza73.dao.GenericDao#getAll(java.lang.Class)
     */
    public List<?> getAll(Class<?> clazz)
    {
        return sf.getCurrentSession().createCriteria(clazz).list();
    }

    /**
     * @see com.pizza73.dao.GenericDao#removeObject(java.lang.Class,
     *      java.io.Serializable)
     */
    public void remove(Class<?> clazz, Serializable id)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Removing " + getClassIdString(clazz, id));
        }
        this.getCurrentSession().delete(get(clazz, id));
    }

    public void remove(Object o)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Removing " + o.toString());
        }
        this.getCurrentSession().delete(o);
    }

    /**
     * @param clazz
     * @param id
     * @return
     */
    private Object getClassIdString(Class<?> clazz, Serializable id)
    {
        return "object of class: " + clazz.getName() + " with id: " + id;
    }

    public Calendar businessDate()
    {
        Query query = sf.getCurrentSession().createQuery("from Info as i where i.variable = 'business_date'");
        Info info = (Info) query.uniqueResult();
        String businessDate = info.getValue();
        int year = Integer.parseInt(businessDate.substring(0, 4));
        int month = Integer.parseInt(businessDate.substring(4, 6));
        int day = Integer.parseInt(businessDate.substring(6, 8));
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);

        return c;
    }

    public String infoValueForVariable(String variable)
    {
        Query query = sf.getCurrentSession().createQuery("from Info as i where i.variable = :var")
            .setString("var", variable);
        Info info = (Info) query.uniqueResult();
        return StringUtils.trimToNull(info.getValue());
    }

    public Object uniqueResultForAttribute(String tableClass, String attribute, String value)
    {
        String queryString = "from " + tableClass + " x where x." + attribute + "=value";
        Query query = this.getCurrentSession().createQuery(queryString).setString("value", value);
        Object o = query.uniqueResult();
        // DataAccessUtils.uniqueResult(
        // this.getHibernateTemplate().find(query, value));

        return o;
    }

    public List<?> forAttribute(String tableClass, String attribute, String value)
    {
        String queryString = "from " + tableClass + " x where x." + attribute + "=value";
        Query query = this.getCurrentSession().createQuery(queryString).setString("value", value);

        List<?> list = query.list();

        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pizza73.dao.UniversalDao#sendEmail()
     */
    public boolean sendEmail()
    {
        Query query = sf.getCurrentSession().createQuery("from Info as i where i.variable = 'send_email'");
        Info info = (Info) query.uniqueResult();
        String sendEmail = info.getValue();

        return Boolean.parseBoolean(StringUtils.trimToEmpty(sendEmail));
    }

    public boolean onlineCashOnly()
    {
        Query query = sf.getCurrentSession().createQuery("from Info as i where i.variable = 'online_cash_only'");
        Info info = (Info) query.uniqueResult();
        String onlineCashOnly = info.getValue();

        return Boolean.parseBoolean(StringUtils.trimToEmpty(onlineCashOnly));
    }

    public boolean useIndustryMailoutWS()
    {
        Query query = sf.getCurrentSession().createQuery("from Info as i where i.variable = 'use_industry_mailout'");
        Info info = (Info) query.uniqueResult();
        String industryMailout = info.getValue();

        return Boolean.parseBoolean(StringUtils.trimToEmpty(industryMailout));
    }

    public boolean parkOrders()
    {
        Query query = sf.getCurrentSession().createQuery("from Info as i where i.variable = 'park_orders'");
        Info info = (Info) query.uniqueResult();
        String parkOrders = info.getValue();

        return Boolean.parseBoolean(StringUtils.trimToEmpty(parkOrders));
    }
}
