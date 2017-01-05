package com.pizza73.dao.hibernate;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.pizza73.dao.TheoreticalFoodCostDao;
import com.pizza73.model.WeeklySales;

@Repository("theoreticalFoodCostDao")
public class TheoreticalFoodCostDaoHibernate extends UniversalDaoHibernate implements TheoreticalFoodCostDao
{

    public WeeklySales weeklySalesForShopAndWeek(final Integer shopId, final Calendar sundayOfWeek)
    {
        return (WeeklySales) super.getCurrentSession().createCriteria(WeeklySales.class)
            .add(Restrictions.eq("shopId", shopId)).add(Restrictions.eq("sundayOfSalesWeek", sundayOfWeek)).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> weeklySalesForShopStartingOn(final int shopId, final Calendar startDate)
    {
        final Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.DATE, 6);
        final int startMonth = startDate.get(Calendar.MONTH) + 1;
        final int endMonth = endDate.get(Calendar.MONTH) + 1;
        final String sDate = "'" + startDate.get(Calendar.YEAR) + "-" + startMonth + "-" + startDate.get(Calendar.DATE)
            + "'";
        final String eDate = "'" + endDate.get(Calendar.YEAR) + "-" + endMonth + "-" + endDate.get(Calendar.DATE) + "'";
        System.out.println("Start: " + sDate + " to End: " + eDate);
        final String queryString = "select s.sent_shop_id as shopId, p.group_id as prodId, "
            + "p.size as sizeId, sum(d.quantity) as quantity "
            + "from iq_order_final_status s, orders o, orders_detail d, iq_product p " + "where s.order_id = o.id "
            + "and s.sent_shop_id =:shopId " + "and o.id = d.order_id " + "and d.product_id = p.id "
            + "and s.final_confirmed_date >=:startDate " + "and s.final_confirmed_date <=:endDate " + "and o.status <> 'x' "
            + "group by shopId, prodId, sizeId " + "order by shopId, prodId, sizeId";
        final Query query = super.getCurrentSession().createSQLQuery(queryString).addScalar("shopId", Hibernate.INTEGER)
            .addScalar("prodId", Hibernate.INTEGER).addScalar("sizeId", Hibernate.INTEGER)
            .addScalar("quantity", Hibernate.INTEGER);
        query.setParameter("shopId", shopId);
        query.setParameter("startDate", sDate);
        query.setParameter("endDate", eDate);

        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> allProductCosts()
    {
        final String queryString = "select product_id, product_modifier_1, product_descr_full, total_cost from product_total_cost";
        final Query query = super.getCurrentSession().createSQLQuery(queryString).addScalar("product_id", Hibernate.INTEGER)
            .addScalar("product_modifier_1", Hibernate.INTEGER).addScalar("product_descr_full", Hibernate.STRING)
            .addScalar("total_cost", Hibernate.BIG_DECIMAL);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> productTypes()
    {
        final String queryString = "select product_id as prodId, product_type as productType from product order by product_id";
        final Query query = super.getCurrentSession().createSQLQuery(queryString).addScalar("prodId", Hibernate.INTEGER)
            .addScalar("productType", Hibernate.INTEGER);

        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> packagingCosts()
    {
        final String queryString = "select id as packagingId, cost as pCost from packaging_cost order by id";
        final Query query = super.getCurrentSession().createSQLQuery(queryString)
            .addScalar("packagingId", Hibernate.INTEGER).addScalar("pCost", Hibernate.BIG_DECIMAL);

        return query.list();
    }
}
