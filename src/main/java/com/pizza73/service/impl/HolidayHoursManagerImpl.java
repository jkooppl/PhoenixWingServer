package com.pizza73.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza73.dao.HolidayHoursDao;
import com.pizza73.model.HolidayHours;
import com.pizza73.model.HolidayHoursCityException;
import com.pizza73.service.HolidayHoursManager;

@Service("holidayHoursManager")
public class HolidayHoursManagerImpl extends UniversalManagerImpl implements HolidayHoursManager
{

    @Autowired
    private HolidayHoursDao holidayDao;

    public HolidayHoursManagerImpl()
    {
    }

    @Override
    public HolidayHours forBusinessDay()
    {

        return this.holidayDao.forBusinessDay();
    }

    public List<HolidayHours> findAll()
    {
        return this.holidayDao.findAll();
    }

    public HolidayHours findForId(Integer holidayId)
    {
        return this.holidayDao.findForId(holidayId);
    }

    @Override
    public HolidayHoursCityException findExceptionForId(Integer exceptionId)
    {
        return this.holidayDao.findExceptionForId(exceptionId);
    }
}
