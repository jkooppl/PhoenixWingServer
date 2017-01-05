package com.pizza73.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.pizza73.dao.HolidayHoursDao;
import com.pizza73.model.HolidayHours;
import com.pizza73.model.HolidayHoursCityException;

@Repository("HolidayHoursDao")
public class HolidayHoursDaoHibernate extends UniversalDaoHibernate implements HolidayHoursDao
{

    public HolidayHoursDaoHibernate()
    {
    }

    @Override
    public HolidayHours forBusinessDay()
    {
        Query query = super.getCurrentSession().createQuery("from HolidayHours c where c.holiday=:businessDate");
        query.setDate("businessDate", this.businessDate().getTime());
        HolidayHours holidayHours = (HolidayHours) query.uniqueResult();

        return holidayHours;
    }

    @SuppressWarnings("unchecked")
    public List<HolidayHours> findAll()
    {
        Query query = super.getCurrentSession().createQuery("from HolidayHours");
        return query.list();
    }

    public HolidayHours findForId(Integer holidayId)
    {
        Query query = super.getCurrentSession().createQuery("from HolidayHours c where c.id=:holidayId");
        query.setParameter("holidayId", holidayId);

        HolidayHours holidayHours = (HolidayHours) query.uniqueResult();

        return holidayHours;
    }

    @Override
    public HolidayHoursCityException findExceptionForId(Integer exceptionId)
    {
        Query query = super.getCurrentSession().createQuery("from HolidayHoursCityException c where c.id=:exceptionId");
        query.setParameter("exceptionId", exceptionId);

        HolidayHoursCityException exception = (HolidayHoursCityException) query.uniqueResult();

        return exception;
    }
}
