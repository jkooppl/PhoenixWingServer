/**
 *
 */
package com.pizza73.dao.hibernate;

import com.pizza73.dao.PayrollDao;
import com.pizza73.model.Payroll;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

/**
 * Hibernate DAO for accessing payroll information.
 *
 * @author chris
 */
@Repository("payrollDao")
public class PayrollDaoHibernate extends UniversalDaoHibernate implements PayrollDao
{

    public PayrollDaoHibernate()
    {
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.PayrollDao#lastPayrollSaved(java.lang.String)
     */
    public Payroll lastPayroll(Integer employeeId)
    {

        Query query = super
            .getCurrentSession()
            .createQuery(
                "from Payroll p where p.employeeId=:eId and p.id = "
                    + "(select max(p2.id) from Payroll p2 where p2.employeeId=:eId)").setInteger("eId", employeeId);
        return (Payroll) query.uniqueResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.PayrollDao#lastPayrollSubmitted(java.lang.String)
     */
    public Payroll lastPayrollUnsubmitted(Integer employeeId)
    {
        Query query = super
            .getCurrentSession()
            .createQuery(
                "from Payroll p where p.employeeId=:eId and p.submitted = false "
                    + "and p.id = (select max(p2.id) from Payroll p2 where p2.employeeId=:eId "
                    + "and p2.submitted = false)").setInteger("eId", employeeId);
        return (Payroll) query.uniqueResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.PayrollDao#lastPayrollSubmitted(java.lang.String)
     */
    public Payroll lastPayrollSubmitted(Integer employeeId)
    {
        Query query = super
            .getCurrentSession()
            .createQuery(
                "from Payroll p where p.employeeId:=eId and "
                    + "p.submitted=true and p.id=(select max(p2.id) from Payroll p2 where p2.submitted=true and p2.employeeId:=eId)")
            .setInteger("eId", employeeId);
        return (Payroll) query.uniqueResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.PayrollDao#payrollForEmployee(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Payroll> payrollForEmployee(Integer employeeId)
    {
        Query query = super.getCurrentSession().createQuery("from Payroll p where p.employeeId:=eId")
            .setInteger("eId", employeeId);
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.PayrollDao#payrollForEmployee(java.lang.String,
     * java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Payroll> payrollForEmployee(Integer employeeId, Integer year)
    {
        Query query = super.getCurrentSession()
            .createQuery("from Payroll p where p.employeeId=:eId and p.payrollYear=:pYear").setInteger("eId", employeeId)
            .setInteger("pYear", year);
        return query.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pizza73.dao.PayrollDao#payrollForPeriod(java.lang.String)
     */
    public Payroll payrollForPeriod(Integer employeeId, Integer period, Integer year)
    {
        Query query = super.getCurrentSession()
            .createQuery("from Payroll p where p.employeeId=:eId and p.payrollYear=:pYear and p.payrollPeriod=:pPeriod")
            .setInteger("eId", employeeId).setInteger("pYear", year).setInteger("pPeriod", period);
        return (Payroll) query.uniqueResult();
    }

    public List<Payroll> statPayrollForShop(Integer shopId, Integer year)
    {
        Query query = super.getCurrentSession()
            .createQuery("from Payroll p where p.payrollYear >=:year and (p.weekOneStat > 0 or p.weekTwoStat > 0) and p.employeeId in (select e.id from Employee e where e.shopId =:shopId)")
            .setInteger("year", year)
            .setInteger("shopId", shopId);

        return query.list();
    }

    public List<Payroll> statPayrollForShopYearAndPeriods(Integer shopId, Integer year, List<Integer> periods)
    {
        Query query = super.getCurrentSession()
            .createQuery("from Payroll p where p.payrollYear >=:year and p.payrollPeriod in (:periods) and p.employeeId in (select e.id from Employee e where e.shopId =:shopId)")
            .setInteger("year", year)
            .setInteger("shopId", shopId)
            .setParameterList("periods", periods);

        return query.list();
    }

    public boolean exists(Integer id)
    {
        Payroll entity = this.get(id);
        return entity != null;
    }

    public Payroll get(Integer id)
    {
        return (Payroll) this.get(Payroll.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Payroll> getAll()
    {
        return (List<Payroll>) this.getAll(Payroll.class);
    }

    public void remove(Integer id)
    {
        this.remove(Payroll.class, id);
    }

    public Payroll save(Payroll p)
    {
        return (Payroll) super.save(p);
    }

    public Payroll payrollForDate(Integer employeeId, Calendar requestDate)
    {
        return (Payroll) super.getCurrentSession().createCriteria(Payroll.class)
            .add(Restrictions.eq("employeeId", employeeId)).add(Restrictions.eq("payPeriodStartDate", requestDate))
            .uniqueResult();
    }
}
