package com.pizza73.webapp.base.property;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pizza73.model.Location;
import com.pizza73.service.LookupManager;

/**
 * LocationProperty.java TODO comment me
 * 
 * @author chris 28-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Component("locationProperty")
public class LocationProperty extends PropertyEditorSupport
{
   @Autowired
   private LookupManager mgr;

   public LocationProperty(){}
//   public LocationProperty(LookupManager mgr)
//   {
//      this.mgr = mgr;
//   }

   /*
    * (non-Javadoc)
    * 
    * @see java.beans.PropertyEditorSupport#getAsText()
    */
   @Override
   public String getAsText()
   {
      Object value = getValue();
      return value == null ? "" : ((Location) value).getId();
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
    */
   @Override
   public void setAsText(String text) throws IllegalArgumentException
   {
      Location location = (Location) mgr.get(Location.class, text);
      if (location == null)
      {
         throw new IllegalArgumentException(
            "invalid value for location: " + text);
      }
      setValue(location);
   }

}
