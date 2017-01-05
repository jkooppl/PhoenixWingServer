package com.pizza73.dao;

import java.util.List;

import com.pizza73.model.HolidayHours;
import com.pizza73.model.HolidayHoursCityException;

public interface HolidayHoursDao extends UniversalDao
{
    public HolidayHours forBusinessDay();

    public HolidayHours findForId(Integer holidayId);

    public List<HolidayHours> findAll();

    public HolidayHoursCityException findExceptionForId(Integer exceptionId);
}
