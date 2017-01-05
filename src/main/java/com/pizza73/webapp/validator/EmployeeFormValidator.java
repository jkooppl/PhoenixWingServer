package com.pizza73.webapp.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.pizza73.model.Employee;
import com.pizza73.webapp.base.validator.BaseDWRValidator;

/**
 * Performs validation of Employees.
 *
 * @author Chris Huisman
 */
public class EmployeeFormValidator extends BaseDWRValidator implements
   Validator
{
   @SuppressWarnings("unchecked")
   public boolean supports(Class clazz)
   {
      return Employee.class.isAssignableFrom(clazz);
   }

   public void validate(Object object, Errors errors)
   {
      Employee employee = (Employee)object;
      if(employee.getId() == null || employee.getId().equals(0))
      {
         validateEmail(employee.getEmail(), errors);
      }
      validateName(employee.getName(), errors);
      validateLastName(employee.getLastName(), errors);
      validatePhone(employee.getPhone(), errors);
//      validatePayrollId(employee.getPayrollId(), errors);
//      validateCompressedHours(employee.getCompressedHours(), errors);
   }

   public void validateAddress_suiteNumber(String suiteNumber, Errors errors)
   {
      super.validateStringLength(suiteNumber, 50, "address.suiteNumber",
         "Suite number", errors);
   }

   public void validateAddress_province(String province, Errors errors)
   {
      super.validateStringRequiredWithLengthStrict(province, 2,
         "address.province", "Province", errors);
   }

   public void validateAddress_postalCode(String postalCode, Errors errors)
   {
      super.validateStringRequiredWithLengthStrict(postalCode, 6,
         "address.postalCode", "Postal code", errors);
      super.validateRegex(postalCode, Pattern
         .compile("^[a-zA-z]\\d[a-zA-z]\\d[a-zA-z]\\d$"), "address.postalCode",
         "Postal Code", errors);
   }

   public void validateAddress_streetAddress(String streetAddress, Errors errors)
   {
      super.validateStringRequiredWithLength(streetAddress, 50,
         "address.streetAddress", "Address", errors);
   }

   public void validateSex(String sex, Errors errors){
      super.validateStringRequiredWithLengthStrict(sex, 1, "sex", "Gender", errors);
   }

   public void validateMaritalStatus(String maritalStatus, Errors errors){
      super.validateStringRequired(maritalStatus, "maritalStatus", "Marital Status", errors);
   }

   public void validatePosition(String position, Errors errors){
      super.validateStringRequired(position, "position", "position", errors);
   }

   public void validateTypeOfEmployment(String typeOfEmployment, Errors errors){
      super.validateStringRequired(typeOfEmployment, "typeOfEmployment", "Type of Employment", errors);
   }

   public void validateBirthDay(String birthDay, Errors errors){
      super.validateStringRequired(birthDay, "birthDay", "Date of Birth", errors);
   }

   public void validateHireDate(String hireDate, Errors errors){
      super.validateStringRequired(hireDate, "hireDate", "Commence Date", errors);
   }

   public void validateAddress_city(String city, Errors errors)
   {
      super.validateStringRequiredWithLength(city, 20, "address.city", "City",
         errors);
   }

   public void validatePrimaryWage(Double primaryWage, Errors errors)
   {
      super.validateDouble(primaryWage, "primaryWage", "Primary wage", errors);
   }

   public void validatePrimaryWage(String primaryWage, Errors errors)
   {
      super.validateDouble(primaryWage, "primaryWage", "Primary wage", errors);
   }

   public void validateEmail(String username, Errors errors)
   {
      super.validateStringRequiredWithLengthStrict(username, 4, "email",
         "Login Id", errors);
   }

   public void validateLastName(String name, Errors errors)
   {
      super.validateStringRequiredWithLength(name, 30, "lastName", "Last Name",
         errors);
   }

   public void validateMiddleInitial(String middleInitial, Errors errors)
   {
      super.validateStringLength(middleInitial, 1, "middleInitial",
         "Middle Initial", errors);
   }

   public void validateName(String name, Errors errors)
   {
      super.validateStringRequiredWithLength(name, 20, "name", "First Name",
         errors);
   }

   public void validatePhone(String phone, Errors errors)
   {
      String phoneRegex = "\\d{10}";
      Pattern pattern = Pattern.compile(phoneRegex);
      super.validateRegex(phone, pattern, "phone", "Phone Number", errors);
   }

   public void validateSin(String sin, Errors errors)
   {
      String sinRegex = "\\d{9}";
      Pattern pattern = Pattern.compile(sinRegex);
      if(super.validateStringRequired(sin, "sin", "SIN", errors))
         super.validateRegex(sin, pattern, "sin", "SIN", errors);
   }

   public void validatePayrollId(String payrollId, Errors errors)
   {
      super.validateStringRequiredWithLength(payrollId, 10, "payrollId",
         "Payroll Id", errors);
   }

   public void validateCompressedHours(String compressedHours, Errors errors)
   {
      super.validateDouble(compressedHours, "compressedHours",
         "Compressed Hours", errors);
   }

   public void validateCompressedHours(Double compressedHours, Errors errors)
   {
      super.validateDouble(compressedHours, "compressedHours",
         "Compressed Hours", errors);
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
