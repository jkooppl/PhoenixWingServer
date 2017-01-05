package com.pizza73.webapp.validator;

import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.pizza73.model.Driver;
import com.pizza73.webapp.base.validator.BaseDWRValidator;

/**
 * Performs validation of Drivers.
 *
 * @author Chris Huisman
 */
public class DriverFormValidator extends BaseDWRValidator implements Validator
{
   @SuppressWarnings("unchecked")
   public boolean supports(Class clazz)
   {
      return Driver.class.isAssignableFrom(clazz);
   }

   public void validate(Object object, Errors errors)
   {
      Driver driver = (Driver) object;

      validateVehicleDescription(driver.getVehicleDescription(), errors);
      validateNickname(driver.getNickname(), errors);
      validatePhone(driver.getPhone(), errors);
      validateName(driver.getName(), errors);
      validateAddress(driver.getAddress(), errors);
      validatePolicyNumber(driver.getPolicyNumber(), errors);
      validateInsurer(driver.getInsurer(), errors);
      validateInsExpiryDate(driver.getInsExpiryDate(), errors);
   }

   public void validateName(String name, Errors errors)
   {
      super.validateStringLength(name, 64, "name", "Full Name", errors);
   }

   public void validateAddress(String address, Errors errors)
   {
      super.validateStringLength(address, 64, "address", "Address", errors);
   }

   public void validateInsurer(String insurer, Errors errors)
   {
      super.validateStringLength(
         insurer, 32, "insurer", "Insuring Company", errors);
   }

   public void validatePolicyNumber(String policyNumber, Errors errors)
   {
      super.validateStringLength(
         policyNumber, 32, "policyNumber", "Policy Number", errors);
   }

   public void validateInsExpiryDate(String date, Errors errors)
   {
      if(super.validateStringRequired(date, "insExpiryDate", "Expiry Date", errors))
      {
         try
         {
            java.sql.Date.valueOf(date);
         }
         catch (IllegalArgumentException e)
         {
            errors.rejectValue(
               "insExpiryDate",
               "errors.invalid.format",
               new String[]{"Expiry Date", "must be in the form dd/mm/yyyy"},
               date + " must be of the form dd/mm/yyyy.");
         }
      }
   }

   public void validateInsExpiryDate(Date date, Errors errors)
   {

   }

   public void validateNickname(String nickname, Errors errors)
   {
      super.validateStringRequiredWithLength(
         nickname, 10, "nickname", "Nickname", errors);
   }

   public void validateVehicleDescription(String vehicleDescription, Errors errors)
   {
      super.validateStringRequiredWithLength(
         vehicleDescription, 32, "vehicleDescription", "Vehicle Description", errors);
   }

   public void validatePhone(String phone, Errors errors)
   {
      String phoneRegex = "\\d{10}";
      Pattern pattern = Pattern.compile(phoneRegex);
      super.validateRegex(phone, pattern, "phone", "Phone Number", errors);
   }

   public String getInputFieldValidationMessage(String formInputId,
         String formInputValue)
   {

      String validationMessage = "";

      Driver formBackingObject = new Driver();
      validationMessage =
         getValidationMessage(formBackingObject, formInputId, formInputValue);

      return validationMessage;
   }
}
