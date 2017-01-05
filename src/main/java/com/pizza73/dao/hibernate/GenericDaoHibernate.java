package com.pizza73.dao.hibernate;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.pizza73.dao.GenericDao;
import com.pizza73.model.Info;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * 
 * <p>
 * To register this class in your Spring context file, use the following XML.
 * 
 * <pre>
 *      &lt;bean id=&quot;fooDao&quot; class=&quot;com.mycompany.app.dao.hibernate.GenericDaoHibernate&quot;&gt;
 *          &lt;constructor-arg value=&quot;com.mycompany.app.model.Foo&quot;/&gt;
 *          &lt;property name=&quot;sessionFactory&quot; ref=&quot;sessionFactory&quot;/&gt;
 *      &lt;/bean&gt;
 * </pre>
 * 
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
// @Repository
public class GenericDaoHibernate<T, PK extends Serializable>
// extends HibernateDaoSupport
    implements GenericDao<T, PK>
{
    // @Autowired
    private SessionFactory sf;
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass())
     * from Commons Logging
     */
    protected final Logger log = Logger.getLogger(getClass());
    private final Class<T> persistentClass;

    /**
     * Constructor that takes in a class to see which type of entity to persist
     * 
     * @param persistentClass
     *            the class type you'd like to persist
     */
    // @Autowired
    public GenericDaoHibernate(final Class<T> persistentClass)
    {
        this.persistentClass = persistentClass;
    }

    public SessionFactory getSessionFactory()
    {
        return this.sf;
    }

    public Session getCurrentSession()
    {
        return this.sf.getCurrentSession();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll()
    {
        return sf.getCurrentSession().createCriteria(this.persistentClass).list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(PK id)
    {
        T entity = (T) sf.getCurrentSession().get(this.persistentClass, id);

        if (entity == null)
        {
            log.warn(this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean exists(PK id)
    {
        T entity = (T) sf.getCurrentSession().get(this.persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T save(T object)
    {
        return (T) sf.getCurrentSession().merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id)
    {
        sf.getCurrentSession().delete(this.get(id));
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
}