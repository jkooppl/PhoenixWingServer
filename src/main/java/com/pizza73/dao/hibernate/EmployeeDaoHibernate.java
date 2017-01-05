package com.pizza73.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.dao.EmployeeDao;
import com.pizza73.model.Driver;
import com.pizza73.model.Employee;
import com.pizza73.model.Shop;

/**
 * UserDaoHibernate.java TODO comment me
 * 
 * @author chris 9-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Repository("employeeDao")
public class EmployeeDaoHibernate extends UniversalDaoHibernate implements EmployeeDao, UserDetailsService
{
    private static final Logger log = Logger.getLogger(EmployeeDaoHibernate.class);

    public EmployeeDaoHibernate()
    {
    }

    public Employee employeeForId(Integer id)
    {
        Query query = super.getCurrentSession().createQuery("from Employee e where e.id=:eId").setInteger("eId", id);
        return (Employee) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
    {
        Query query = super.getCurrentSession().createQuery("from Employee where pin=:username")
            .setString("username", username);

        List<UserDetails> users = query.list();
        if (users == null || users.isEmpty())
        {
            throw new UsernameNotFoundException("Employee for pin: '" + username + "' not found...");
        }
        else
        {
            return users.get(0);
        }
    }

    public Employee employeeForUsername(String username) throws UsernameNotFoundException
    {
        Query query = super.getCurrentSession().createQuery("from Employee e where e.email=:email")
            .setString("email", username);
        Employee employee = (Employee) query.uniqueResult();
        if (employee == null)
        {
            throw new UsernameNotFoundException("Unable to find user for username: " + username);
        }
        return employee;
    }

    @SuppressWarnings("unchecked")
    public List<Driver> driversForShop(Integer shopId)
    {
        Query query = super.getCurrentSession().createQuery("from Driver d where d.shop.id=:sId").setInteger("sId", shopId);
        List<Driver> drivers = query.list();

        return drivers;
    }

    @SuppressWarnings("unchecked")
    public List<Driver> driversForShop(Shop shop)
    {
        Query query = super.getCurrentSession().createQuery("from Driver d where d.shop=:shop").setEntity("shop", shop);
        List<Driver> drivers = query.list();

        return drivers;
    }

    @SuppressWarnings("unchecked")
    public List<Employee> employeesForShop(Integer shopId)
    {
        Query query = super.getCurrentSession().createQuery("from Employee e where e.shopId=:sId").setInteger("sId", shopId);
        List<Employee> employees = query.list();

        return employees;
    }

    @SuppressWarnings("unchecked")
    public List<Employee> employeesForShop(Integer shopId, Boolean salaried)
    {
        Query query = super.getCurrentSession()
            .createQuery("from Employee e where e.shopId=:sId and e.salariedEmployee=:se").setInteger("sId", shopId)
            .setBoolean("se", salaried);
        List<Employee> employees = query.list();

        return employees;
    }

    @SuppressWarnings("unchecked")
    public List<Employee> activeEmployeesForShop(Integer shopId)
    {
        Query query = super.getCurrentSession().createQuery("from Employee e where e.shopId=:sId and e.enabled=:enabled")
            .setInteger("sId", shopId).setBoolean("enabled", Boolean.TRUE);
        List<Employee> employees = query.list();

        return employees;
    }

    @SuppressWarnings("unchecked")
    public List<Employee> activeEmployeesForShop(Integer shopId, Boolean salaried)
    {
        Query query = super.getCurrentSession()
            .createQuery("from Employee e where e.shopId=:sId and e.enabled=:enabled and e.salariedEmployee=:se")
            .setInteger("sId", shopId).setBoolean("enabled", Boolean.TRUE).setBoolean("se", salaried);
        List<Employee> employees = query.list();

        return employees;
    }

    public Employee employeeForPayrollId(String payrollId)
    {
        Query query = super.getCurrentSession().createQuery("from Employee e where e.payrollId=:pId")
            .setString("pId", payrollId);
        Employee employee = (Employee) query.uniqueResult();
        if (employee == null)
        {
            throw new UsernameNotFoundException("Unable to find user for payroll ID: " + payrollId);
        }
        return employee;
    }

    public Driver driverForId(Integer id)
    {
        Driver driver = (Driver) super.getCurrentSession().get(Driver.class, id);
        if (driver == null)
        {
            log.warn("Could not retrieve driver for id: " + id);
            throw new ObjectRetrievalFailureException(Driver.class, id);
        }
        return driver;
    }

    public void saveDriver(Driver driver)
    {
        super.getCurrentSession().save(driver);
    }

    public boolean exists(Integer id)
    {
        Employee entity = this.get(id);
        return entity != null;
    }

    public Employee get(Integer id)
    {
        return (Employee) this.get(Employee.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Employee> getAll()
    {
        return (List<Employee>) this.getAll(Employee.class);
    }

    public void remove(Integer id)
    {
        this.remove(Employee.class, id);
    }

    public Employee save(Employee e)
    {
        return (Employee) super.save(e);
    }

    public List<Employee> newEmployeesForShop(Integer shopId)
    {
        List<Employee> employees = this.employeesForShop(shopId);
        for (int i = employees.size() - 1; i >= 0; i--)
            if (!employees.get(i).isNewEmployee())
                employees.remove(i);
        return employees;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Employee> employeeForFirstAndLastName(String first, String second)
    {
        String query = "from Employee e where enabled=:enabled and lower(e.name) =:first and lower(e.lastName) =:second and newEmployee=:new";
        Query q = super.getCurrentSession().createQuery(query);
        q.setBoolean("enabled", Boolean.TRUE);
        q.setParameter("first", first.toLowerCase());
        q.setParameter("second", second.toLowerCase());
        q.setBoolean("new", Boolean.TRUE);

        return q.list();
    }
}