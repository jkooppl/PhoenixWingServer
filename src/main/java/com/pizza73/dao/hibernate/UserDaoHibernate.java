package com.pizza73.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.dao.UserDao;
import com.pizza73.model.Employee;
import com.pizza73.model.OnlineCustomer;

/**
 * UserDaoHibernate.java TODO comment me
 * 
 * @author chris 9-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Repository("userDao")
public class UserDaoHibernate extends UniversalDaoHibernate implements UserDao, UserDetailsService
{

    public UserDaoHibernate()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pizza73.dao.UserDao#customerForEmail(java.lang.String)
     */
    public OnlineCustomer customerForEmail(String email) throws UsernameNotFoundException
    {
        email = email.toLowerCase();
        Query query = super.getCurrentSession().createQuery("from OnlineCustomer oc where oc.email=:email")
            .setString("email", email);

        OnlineCustomer customer = (OnlineCustomer) query.uniqueResult();
        if (customer == null)
        {
            throw new UsernameNotFoundException("Unable to find user for email: " + email);
        }
        return customer;
    }

    public OnlineCustomer customerForPhone(String phone) throws UsernameNotFoundException
    {
        Query query = super.getCurrentSession().createQuery("from OnlineCustomer oc where oc.phone=:phone")
            .setString("phone", phone);
        OnlineCustomer customer = (OnlineCustomer) query.uniqueResult();
        if (customer == null)
        {
            throw new UsernameNotFoundException("Unable to find user for phone number: " + phone);
        }
        return customer;
    }

    public Employee employeeForId(Integer id)
    {
        Query query = super.getCurrentSession().createQuery("from Employee e where e.id=:eId").setInteger("eId", id);
        return (Employee) query.uniqueResult();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pizza73.dao.UserDao#customerForId(java.lang.Integer)
     */
    public OnlineCustomer customerForId(Integer id)
    {
        Query query = super.getCurrentSession().createQuery("from OnlineCustomer oc where oc.id=:ocId")
            .setInteger("ocId", id);
        return (OnlineCustomer) query.uniqueResult();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
    {
        Session session = super.getCurrentSession();
        // set username ie email to lowercase
        username = username.toLowerCase();
        Query query = session.createQuery("from OnlineCustomer oc where oc.email=:email").setString("email", username);
        List<UserDetails> users = query.list();
        if (users == null || users.isEmpty())
        {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        }
        else
        {
            return users.get(0);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pizza73.dao.UserDao#updateRepeatCount(com.pizza73.model.OnlineCustomer
     * )
     */
    public void updateRepeatCount(OnlineCustomer oc)
    {
        Query query = super.getCurrentSession().createSQLQuery(
            "update iq2_online_customer set repeat_count=" + oc.getRepeatCount() + " where online_id=" + oc.getId());
        query.executeUpdate();
    }

    public boolean exists(Integer id)
    {
        OnlineCustomer entity = this.get(id);
        return entity != null;
    }

    public OnlineCustomer get(Integer id)
    {
        return (OnlineCustomer) this.get(OnlineCustomer.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<OnlineCustomer> getAll()
    {
        return (List<OnlineCustomer>) this.getAll(OnlineCustomer.class);
    }

    public void remove(Integer id)
    {
        this.remove(OnlineCustomer.class, id);
    }

    public OnlineCustomer save(OnlineCustomer c)
    {
        return (OnlineCustomer) super.save(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pizza73.dao.UserDao#cscEmployees()
     */
    @SuppressWarnings("unchecked")
    public List<OnlineCustomer> cscEmployees()
    {
        Session session = super.getCurrentSession();
        Query query = session.createQuery("from OnlineCustomer oc inner join oc.roles role where role.id=:role_id")
            .setInteger("role_id", Integer.valueOf(2));
        List<OnlineCustomer> users = query.list();

        // This might work for you:
        // select distinct p from Promotion p inner join p.reviewCategories cat
        // where cat='Children'
        // I've never tried it with elements but give it a try.
        //
        // New postPosted: Tue Apr 01, 2008 3:19 pm Post subject: Reply with
        // quote
        //
        // Well what do you know?
        // from Promotion p inner join p.reviewCategories cat where
        // cat='Children'
        // works. But
        // from Promotion p inner join p.reviewCategories where
        // p.reviewCategories ='Children'

        return users;
    }

    public OnlineCustomer userForEmailAndPassword(String username, String password)
    {
        Session session = super.getCurrentSession();
        // set username ie email to lowercase
        username = username.toLowerCase();
        Query query = session.createQuery("from OnlineCustomer oc where oc.email=:email and oc.password=:password")
            .setString("email", username).setString("password", password);
        OnlineCustomer oc = (OnlineCustomer) query.uniqueResult();
        return oc;
    }
}
