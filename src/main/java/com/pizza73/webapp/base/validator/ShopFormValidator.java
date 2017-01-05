package com.pizza73.webapp.base.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.pizza73.model.Shop;

/**
 * Performs validation of Credits.
 * 
 * @author Chris Huisman
 */
public class ShopFormValidator extends BaseDWRValidator implements Validator
{
   @SuppressWarnings("unchecked")
   public boolean supports(Class clazz)
   {
      return Shop.class.isAssignableFrom(clazz);
   }

   public void validate(Object object, Errors errors)
   {
      Shop shop = (Shop) object;

      validateClosedReason(shop.getClosedReason(), errors);
      validateExtendedDelivery(shop.getExtendedDelivery(), errors);
      validateExtendedDebit(shop.getExtendedDebit(), errors);
      validateExtendedPickup(shop.getExtendedPickup(), errors);
      validateMessage(shop.getMessage(), errors);
      validateMessageTwo(shop.getMessageTwo(), errors);
   }

   public void validateMessage(String message, Errors errors)
   {
      if (message.length() > 35)
      {
         String[] params = {"Message", "35"};
         errors.rejectValue("message", "errors.length", params,
               "Message is to long, we only allow for 35 characters for this field.");
      }
   }

   public void validateMessageTwo(String message, Errors errors)
   {
      if (message.length() > 35)
      {
         String[] params = {"Message Two", "35"};
         errors.rejectValue("messageTwo", "errors.length", params,
               "Message Two is to long, we only allow for 35 characters for this field.");
      }
   }
   
   public void validateClosedReason(String closedReason, Errors errors)
   {
      if (closedReason.length() > 64)
      {
         String[] params = {"Closed Reason", "64"};
         errors.rejectValue("closedReason", "errors.length", params,
               "Closed Reason is to long, we only allow for 64 characters for this field.");
      }
   }

   public void validateExtendedDelivery(Integer time, Errors errors)
   {
      try
      {
         Integer.valueOf(time);
      }
      catch (NumberFormatException e) 
      {
         errors.rejectValue("extendedDelivery", "errors.integer", new String[]{"Extended Delivery"},
            "Extended Delivery must be an integer.");
      }
   }

   public void validateExtendedDelivery(String time, Errors errors)
   {
      try
      {
         Integer.valueOf(time);
      }
      catch (NumberFormatException e) 
      {
         errors.rejectValue("extendedDelivery", "errors.integer", new String[]{"Extended Delivery"},
            "Extended Delivery must be an integer.");
      }
   }
   
   public void validateExtendedDebit(Integer time, Errors errors)
   {
      try
      {
         Integer.valueOf(time);
      }
      catch (NumberFormatException e) 
      {
         errors.rejectValue("extendedDebit", "errors.integer", new String[]{"Extended Debit"},
            "Extended Debit must be an integer.");
      }
   }

   public void validateExtendedDebit(String time, Errors errors)
   {
      try
      {
         Integer.valueOf(time);
      }
      catch (NumberFormatException e) 
      {
         errors.rejectValue("extendedDebit", "errors.integer", new String[]{"Extended Debit"},
            "Extended Debit must be an integer.");
      }
   }
   
   public void validateExtendedPickup(Integer time, Errors errors)
   {
      try
      {
         Integer.valueOf(time);
      }
      catch (NumberFormatException e) 
      {
         errors.rejectValue("extendedPickup", "errors.integer", new String[]{"Extended Pickup"},
            "Extended Pickup must be an integer.");
      }
   }

   public void validateExtendedPickup(String time, Errors errors)
   {
      try
      {
         Integer.valueOf(time);
      }
      catch (NumberFormatException e) 
      {
         errors.rejectValue("extendedPickup", "errors.integer", new String[]{"Extended Pickup"},
            "Extended Pickup must be an integer.");
      }
   }

   public String getInputFieldValidationMessage(String formInputId,
         String formInputValue)
   {

      String validationMessage = "";

      Shop formBackingObject = new Shop();
      validationMessage = 
         getValidationMessage(formBackingObject, formInputId, formInputValue);
      
      return validationMessage;
   }
}
