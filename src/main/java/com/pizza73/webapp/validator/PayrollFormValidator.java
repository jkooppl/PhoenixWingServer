package com.pizza73.webapp.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.pizza73.model.Shop;
import com.pizza73.model.ShopPayroll;
import com.pizza73.webapp.base.validator.BaseDWRValidator;

/**
 * Performs validation of Credits.
 *
 * @author Chris Huisman
 */
public class PayrollFormValidator extends BaseDWRValidator implements Validator
{
   @SuppressWarnings("unchecked")
   public boolean supports(Class clazz)
   {
      return ShopPayroll.class.isAssignableFrom(clazz);
   }

   public void validate(Object object, Errors errors)
   {
      ShopPayroll shopPayroll = (ShopPayroll) object;
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
