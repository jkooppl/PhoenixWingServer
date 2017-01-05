package com.pizza73.webapp.base.property;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pizza73.model.Shop;
import com.pizza73.service.LookupManager;

/**
 * ShopProperty.java TODO comment me
 * 
 * @author chris 28-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Component("shopProperty")
public class ShopProperty extends PropertyEditorSupport
{
   @Autowired
   private LookupManager mgr;

   public ShopProperty()
   {}

   /*
    * (non-Javadoc)
    * 
    * @see java.beans.PropertyEditorSupport#getAsText()
    */
   @Override
   public String getAsText()
   {
      Object value = getValue();
      return value == null ? "" : ((Shop) value).getId().toString();
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
    */
   @Override
   public void setAsText(String text) throws IllegalArgumentException
   {
      Shop shop = (Shop) mgr.get(Shop.class, Integer.valueOf(text));
      if (shop == null)
      {
         throw new IllegalArgumentException("invalid value for shop: " + text);
      }
      setValue(shop);
   }
}