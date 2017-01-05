package com.pizza73.dao.hibernate;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.pizza73.dao.SalesDao;
import com.pizza73.model.conversion.Sales;

@Repository("salesDao")
public class SalesDaoHibernate extends UniversalDaoHibernate implements SalesDao
{

    @SuppressWarnings("unchecked")
    public List<Sales> salesForEspOperatorsAndBusinessDate(Calendar date)
    {
        Query query = super.getCurrentSession()
            .createQuery("from Sales s where s.orderDate=:date and " + "s.operator like 'ESP%' order by s.operator")
            .setCalendarDate("date", date);
        return query.list();
    }

    public Object[] salesErrorsForEspOperator(String operator, Calendar date)
    {
        String queryString = "select count(*) as errors, sum(food_cost) as cost_of_errors " //
            + "from orders a, sales_memo b " //
            + "where b.business_date = :date " //
            + "and a.business_date = b.business_date " //
            + "and a.id = b.order_id " //
            + "and a.operator_id = :operator " //
            + "and b.responsible_party_id = 2";
        Query query = super.getCurrentSession().createSQLQuery(queryString).setCalendar("date", date)
            .setString("operator", operator);

        return (Object[]) query.uniqueResult();
    }

}
