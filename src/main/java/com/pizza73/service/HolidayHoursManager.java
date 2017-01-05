package com.pizza73.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.HolidayHours;
import com.pizza73.model.HolidayHoursCityException;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface HolidayHoursManager extends UniversalManager
{

    public HolidayHours forBusinessDay();

    public HolidayHours findForId(Integer holidayId);

    public List<HolidayHours> findAll();

    public HolidayHoursCityException findExceptionForId(Integer exceptionId);
}
