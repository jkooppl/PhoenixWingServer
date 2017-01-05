package com.pizza73.webapp.base.validator;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class BaseDWRValidator
{
   private MessageSource messageSource;

   /**
    * Set the MessageSource.
    * 
    * @param messageSource
    *           The MessageSource.
    */
   public void setMessageSource(MessageSource messageSource)
   {
      this.messageSource = messageSource;
   }
   
   /**
    * Get the validation message for an individual input field of a model
    * object.
    * 
    * @param modelObject
    *           The object to validate against.
    * @param formInputId
    *           The id attribute associated with the input field.
    * @param formInputValue
    *           The current input value of the input filed to be validated.
    * @return The validation message.
    */
   @SuppressWarnings("unchecked")
   public String getValidationMessage(Object formBackingObject,
         String formInputId, String formInputValue)
   {

      String validationMessage = "";

      try
      {
//         Object formBackingObject = new Shop();
         Errors errors = new BindException(formBackingObject, "command");

         // Ignore the preceding "command." portion of the id
         if(formInputId.indexOf("command.") ==0)
         { 
            formInputId =formInputId.substring("command.".length());
         }
         String capitalizedFormInputId = StringUtils.capitalize(formInputId);

         // Invoke the set[formInputId] method 
//         String methodName = "set" + capitalizedFormInputId;
//         Field field = formBackingObject.getClass().getField(formInputId);
//         Class setterArgs[] = new Class[] { field.getType() };
//         Method accountMethod = 
//            formBackingObject.getClass().getMethod(methodName, setterArgs);
//         if(field.getType().equals(String.class))
//         {
//            accountMethod.invoke(formBackingObject,new Object[] { formInputValue });
//         }
//         else if(field.getType().equals(Integer.class))
//         {
//            try
//            {
//               accountMethod.invoke(formBackingObject,new Object[] { Integer.valueOf(formInputValue) });
//            }
//            catch (NumberFormatException e) 
//            {
//               errors.rejectValue(formInputId, "errors.integer", new String[]{capitalizedFormInputId},
//                  capitalizedFormInputId + " must be an integer.");
//            }
//         }

         // Invoke the validate[formInputId] method of the AccountValidator
         // instance
         String validationMethodName = "validate" + capitalizedFormInputId.replaceAll("[.]", "_");
         Class validationArgs[] = new Class[] { String.class, Errors.class };
         Method validationMethod = 
            getClass().getMethod(validationMethodName,validationArgs);
         validationMethod.invoke(this, new Object[] { formInputValue, errors });

         validationMessage = getValidationMessage(errors, formInputId);
      }
      catch (Exception e)
      {
         // Handle appropriately for your application
         System.out.println("New code exception: " + e);
      }

      return validationMessage;
   }

   /**
    * Get the FieldError validation message from the underlying MessageSource
    * for the given fieldName.
    * 
    * @param errors
    *           The validation errors.
    * @param fieldName
    *           The fieldName to retrieve the error message from.
    * @return The validation message or an empty String.
    */
   protected String getValidationMessage(Errors errors, String fieldName)
   {
      String message = "";

      FieldError fieldError = errors.getFieldError(fieldName);

      if (fieldError != null)
      {
         String code = fieldError.getCode();
//         formInputId = formInputId.split("\\.")[1];
         message = messageSource.getMessage(code, fieldError.getArguments(),
               fieldError.getDefaultMessage(), Locale.ENGLISH);
      }

      return message;
   }
   
   protected void validateIntegerWithLength(
      String toValidate, int length, String formName, String label, Errors errors)
   {
      if(this.validateInteger(toValidate, formName, label, errors));
      {
         if(toValidate.toString().length() > length)
         {
            String[] params = {label, length + ""};
            errors.rejectValue(formName, "errors.length", params,
               label + " is to long, we only allow for " + length 
               + " characters for this field.");
         }
      }
   }
   
   protected void validateIntegerWithLength(
      Integer toValidate, int length, String formName, String label, Errors errors)
   {
      if(this.validateInteger(toValidate, formName, label, errors));
      {
         if(toValidate.toString().length() > length)
         {
            String[] params = {label, length + ""};
            errors.rejectValue(formName, "errors.length", params,
               label + " is to long, we only allow for " + length 
               + " characters for this field.");
         }
      }
   }
   
   protected boolean validateInteger(
      String toValidate, String formName, String label, Errors errors)
   {
      boolean valid = true;
      try
      {
         Integer.valueOf(toValidate);
      }
      catch (NumberFormatException e) 
      {
         errors.rejectValue(formName, "errors.integer", new String[]{label},
            label + " must be an integer.");
         valid = false;
      }
      
      return valid;
   }
   
   protected boolean validateInteger(
      Integer toValidate, String formName, String label, Errors errors)
   {
      boolean valid = true;
      try
      {
         Integer.valueOf(toValidate);
      }
      catch (NumberFormatException e) 
      {
         errors.rejectValue(formName, "errors.integer", new String[]{label},
            label + " must be an integer.");
         valid = false;
      }
      
      return valid;
   }
   
   protected void validateNonNegativeBigDecimal(BigDecimal toValidate, String formName, String label, Errors errors){
		   if(toValidate.compareTo(BigDecimal.ZERO)<0){
			    String[] params = { label, "0" };
				errors.rejectValue(formName, "errors.value", params, label
						+ " must be greater than 0");			   
		   }
   }

   protected void validateBigDecimalWithScale(String toValidate, int scale,
			String formName, String label, Errors errors) {
		if (validateBigDecimal(toValidate, formName, label, errors))
			this.validateBigDecimalWithScale(new BigDecimal(toValidate), scale,
					formName, label, errors);
	}

	protected void validateBigDecimalWithScale(BigDecimal toValidate,
			int scale, String formName, String label, Errors errors) {
		if (toValidate.scale() > scale) {
			String[] params = { label, scale + "" };
			errors.rejectValue(formName, "errors.scale", params, label
					+ " is too long, we only allow for " + scale
					+ " digits after decimal points.");
		}
	}

	protected boolean validateBigDecimal(String toValidate, String formName,
			String label, Errors errors) {
		boolean valid = true;
		try {
			new BigDecimal(toValidate);
		} catch (NumberFormatException e) {
			errors.rejectValue(formName, "errors.BigDecimal",
					new String[] { label }, label
							+ " must be in valid decimal format.");
			valid = false;
		}

		return valid;
	}
		   
   protected boolean validateStringRequired(
      String toValidate, String formName, String label, Errors errors)
   {
      boolean valid = true;
      if(StringUtils.isBlank(toValidate))
      {
         errors.rejectValue(
            formName, "errors.required", new String[]{label}, 
            label + " is required");
         valid = false;
      }
      
      return valid;
   }
   
   protected boolean validateStringLength(
      String toValidate, int length, String formName, String label, Errors errors)
   {
      if(toValidate.toString().length() > length)
      {
         String[] params = {label, length + ""};
         errors.rejectValue(formName, "errors.length", params,
            label + " is to long, we only allow for " + length 
            + " characters for this field.");
         return false;
      }
      
      return true;
   }
   
   protected boolean validateStringLengthStrict(
      String toValidate, int length, String formName, String label, Errors errors)
   {
      if(toValidate.toString().length() != length)
      {
         String[] params = {label, length + ""};
         errors.rejectValue(formName, "errors.length.strict", params,
            label + " is not " + length 
            + " characters.  This field requires " + label + " to be " 
            + length + " chraracters.");
         return false;
      }
      
      return true;
   }
   
   protected boolean validateStringRequiredWithLength(
      String toValidate, int length, String formName, String label, Errors errors)
   {

      if(this.validateStringRequired(toValidate, formName, label, errors))
      {
         return this.validateStringLength(toValidate, length, formName, label, errors);
      }
      
      return true;
   }
   
   protected boolean validateStringRequiredWithLengthStrict(
      String toValidate, int length, String formName, String label, Errors errors)
   {

      if(this.validateStringRequired(toValidate, formName, label, errors))
      {
         return this.validateStringLengthStrict(toValidate, length, formName, label, errors);
      }
      
      return true;
   }
   
   public void validateRegex(
      String toValidate, Pattern pattern, String formName, String label, 
      Errors errors)
   {
//      if(this.validateStringRequired(toValidate, formName, label, errors))
      if(StringUtils.isNotBlank(toValidate))
      {
         if (!pattern.matcher(toValidate).matches())
         {
            errors.rejectValue(formName, null, label + " is invalid.");
         }
      }
   }
   
   protected boolean validateDouble(
      String toValidate, String formName, String label, Errors errors)
   {
      boolean valid = true;
      try
      {
         Double.valueOf(toValidate);
      }
      catch (NumberFormatException e) 
      {
         errors.rejectValue(formName, "errors.integer", new String[]{label},
            label + " must be an integer.");
         valid = false;
      }
      
      return valid;
   }
   
   protected boolean validateDouble(
      Double toValidate, String formName, String label, Errors errors)
   {
      boolean valid = true;
      try
      {
         Double.valueOf(toValidate);
      }
      catch (NumberFormatException e) 
      {
         errors.rejectValue(formName, "errors.integer", new String[]{label},
            label + " must be an integer.");
         valid = false;
      }
      
      return valid;
   }
}
