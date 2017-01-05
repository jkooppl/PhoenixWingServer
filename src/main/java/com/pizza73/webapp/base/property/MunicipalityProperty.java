package com.pizza73.webapp.base.property;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pizza73.model.Municipality;
import com.pizza73.service.LookupManager;

/**
 * MunicipalityProperty.java TODO comment me
 * 
 * @author chris 28-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Component("cityProperty")
public class MunicipalityProperty extends PropertyEditorSupport
{
    @Autowired
    private LookupManager mgr;

    public MunicipalityProperty()
    {
    }

    @Override
    public String getAsText()
    {
        Municipality value = (Municipality) getValue();
        return value == null ? "" : value.getId().toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        Municipality municipality = (Municipality) mgr.get(Municipality.class, Integer.valueOf(text));
        if (municipality == null)
        {
            throw new IllegalArgumentException("invalid value for municipality: " + text);
        }
        setValue(municipality);
    }
}