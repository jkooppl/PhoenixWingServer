package com.pizza73.webapp.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.pizza73.model.Employee;
import com.pizza73.webapp.base.validator.BaseDWRValidator;

/**
 * Performs validation of Employees.
 *
 * @author Chris Huisman
 */
public class LoginIdChangeValidator extends BaseDWRValidator implements Validator
{
   @SuppressWarnings("unchecked")
   public boolean supports(Class clazz)
   {
      return Employee.class.isAssignableFrom(clazz);
   }

   public void validate(Object object, Errors errors)
   {
      Employee employee = (Employee) object;

      if(validateOldPassword(employee.getOldPassword(), errors)
        && validateEmail(employee.getEmail(), errors))
      {
         validateConfirmLoginId(
               employee.getEmail(), employee.getConfirmPassword(), errors);
      }
   }

   public boolean validateEmail(String username, Errors errors)
   {
      return super.validateStringRequiredWithLengthStrict(
         username, 4, "email", "Login Id", errors);
   }

   public boolean validateOldPassword(String username, Errors errors)
   {
      return super.validateStringRequiredWithLengthStrict(
         username, 4, "oldPassword", "Current Login Id", errors);
   }

   public void validateConfirmLoginId(String username, String confirmPassword, Errors errors)
   {
      if(super.validateStringRequiredWithLengthStrict(
         confirmPassword, 4, "confirmPassword", "Confirm Login Id", errors))
      {
         if(!username.equals(confirmPassword))
         {
            errors.reject("", "Confirm Login Id is not the same as Login Id.");
         }
      }
   }

   public String getInputFieldValidationMessage(String formInputId,
         String formInputValue)
   {
      String validationMessage = "";

      Employee formBackingObject = new Employee();
      validationMessage =
         getValidationMessage(formBackingObject, formInputId, formInputValue);

      return validationMessage;
   }
}

