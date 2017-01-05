package com.pizza73.webapp.base.property;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pizza73.model.HolidayHours;
import com.pizza73.service.LookupManager;

/**
 * HolidayHoursProperty.java TODO comment me
 * 
 * @author chris 28-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Component("holidayHoursProperty")
public class HolidayHoursProperty extends PropertyEditorSupport
{
    @Autowired
    private LookupManager mgr;

    public HolidayHoursProperty()
    {
    }

    @Override
    public String getAsText()
    {
        HolidayHours value = (HolidayHours) getValue();
        return value == null ? "" : value.getId().toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        HolidayHours hour = (HolidayHours) mgr.get(HolidayHours.class, Integer.valueOf(text));
        if (hour == null)
        {
            throw new IllegalArgumentException("invalid value for holiday hour: " + text);
        }
        setValue(hour);
    }
}