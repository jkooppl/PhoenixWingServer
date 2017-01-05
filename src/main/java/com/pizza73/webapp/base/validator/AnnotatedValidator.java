package com.pizza73.webapp.base.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import org.apache.commons.lang.StringUtils;

import com.pizza73.model.annotation.DoubleConstraint;
import com.pizza73.model.annotation.IntegerConstraint;
import com.pizza73.model.annotation.StringConstraint;

public class AnnotatedValidator implements Validator {
	private MessageSource messageSource;
    private Set<Class<? extends Annotation>> validAnnotations = null;
    private String[] errorCodes = new String[] {"error.required", "error.regexp", "error.min", "error.max", "error.strict"};
    
	public AnnotatedValidator() {
		super();
        validAnnotations = new HashSet<Class<? extends Annotation>>();
        validAnnotations.add(StringConstraint.class);
        validAnnotations.add(DoubleConstraint.class);
        validAnnotations.add(IntegerConstraint.class);
	}
    
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		boolean supports = false;
        if ((clazz != null) & (validAnnotations != null))
        {
            for (Field field : clazz.getDeclaredFields())
                if (!supports) supports = supports ||(this.getValidationAnnotation(field) != null);
        }
        return supports;
	}
    	
	public void validate(Object object, Errors errors) {
        if ((object == null) | (errors == null)) throw new IllegalArgumentException();
        if (!this.supports(object.getClass())) throw new IllegalArgumentException();
        for (Field field : object.getClass().getDeclaredFields()) 
        	     try{
        	     validateField(field, object, errors);
        	     }catch(Exception e){
        	    	 e.printStackTrace();
        	     }
	}
	
	 @SuppressWarnings("unchecked")
	 public String getValidationMessage(Object formBackingObject,Class formBackingClass,String commandObjectName,
	         String formInputId, String formInputValue)
	   {String validationMessage = "";
	      try
	      {Errors errors = new BindException(formBackingObject, commandObjectName);
	         if(formInputId.indexOf(commandObjectName+".") ==0)
	         { 
	            formInputId =formInputId.substring((commandObjectName+".").length());
	         }	         
	         Field f=formBackingClass.getDeclaredField(formInputId);
	         f.setAccessible(true);
	         f.set(formBackingObject,this.getValueObject(f.getType(), formInputValue));
             this.validate(formBackingObject, errors);
	         validationMessage = getValidationMessage(errors, formInputId);
	      }
	      catch (Exception e)
	      {
	         System.out.println("New code exception: " + e);
	      }
	      return validationMessage;
	   }
	
    protected Class<? extends Annotation> getValidationAnnotation(Field field)
    {   
        Class<? extends Annotation> validAnnotation = null;
        if (field != null)
            for (Annotation annotation: field.getAnnotations()){
                if (validAnnotation == null)
                    if (validAnnotations.contains(annotation.annotationType()))
                        validAnnotation = annotation.annotationType();
            }
        return validAnnotation;
    }
        
    protected void validateField(Field field, Object object, Errors errors) throws IllegalArgumentException, IllegalAccessException
    {
        if (field != null)
        {
            Class<? extends Annotation> annotation = this.getValidationAnnotation(field);
            if (annotation != null)
            {   
                if (StringConstraint.class.equals(annotation)) validateFieldAsString(field, object, errors);
                if (IntegerConstraint.class.equals(annotation)) validateFieldAsInteger(field, object, errors);
                if (DoubleConstraint.class.equals(annotation)) validateFieldAsDouble(field, object, errors);
            }
        }
    }
    
	protected String getValidationMessage(Errors errors, String fieldName)
	   {  
	      String message = "";
	      FieldError fieldError = errors.getFieldError(fieldName);
	      if (fieldError != null)
	      {
	         String code = fieldError.getCode();
	         message = messageSource.getMessage(code, fieldError.getArguments(),
	               fieldError.getDefaultMessage(), Locale.ENGLISH);
	      }
	      return message;
	   }
	
    @SuppressWarnings("unchecked")
	private Object getValueObject(Class clazz, String fieldValue)
    {
        String type = clazz.getSimpleName();
        if (("byte".equals(type)) | ("Byte".equals(type))) return new Byte(fieldValue);
        if (("int".equals(type)) | ("Integer".equals(type))) return new Integer(fieldValue);
        if (("float".equals(type)) | ("Float".equals(type))) return new Float(fieldValue);
        if (("double".equals(type)) | ("Double".equals(type))) return new Double(fieldValue);
        return fieldValue;
    }

	private void validateFieldAsDouble(Field field, Object object, Errors errors) throws IllegalArgumentException, IllegalAccessException {
		DoubleConstraint req = field.getAnnotation(DoubleConstraint.class);
        boolean required=req.required();
        boolean hasMax=req.hasMax();
        double max=req.maxValue();
        boolean hasMin=req.hasMin();
        double min=req.minValue();
        field.setAccessible(true);
        Double doubleField=(Double) field.get(object);
        //null validation
        if(doubleField == null){
        	if(required)
        		errors.rejectValue(field.getName(), errorCodes[0],"This field is required.");
        	return;
        }
        double value=doubleField.intValue();
        //max validation
        if(hasMax && value > max){
        	errors.rejectValue(field.getName(), errorCodes[2], "This field's value can't be bigger than "+max+".");
        	return;
        }
        //min validation
        if(hasMin && value< min){
        	errors.rejectValue(field.getName(), errorCodes[3], "This field's value can't be smaller than "+max+".");
        	return;        	
        }		
	}

	private void validateFieldAsInteger(Field field, Object object,
			Errors errors) throws IllegalArgumentException, IllegalAccessException {
        IntegerConstraint req = field.getAnnotation(IntegerConstraint.class);
        boolean required=req.required();
        boolean hasMax=req.hasMax();
        int max=req.maxValue();
        boolean hasMin=req.hasMin();
        int min=req.minValue();
        field.setAccessible(true);
        Integer intField=null;
        if(field.get(object) != null)
            intField = Integer.valueOf(field.get(object).toString());	
        //null validation
        if(intField == null){
        	if(required)
        		errors.rejectValue(field.getName(), errorCodes[0],"This field is required.");
        	return;
        }
        int value=intField.intValue();
        //max validation
        if(hasMax && value > max){
        	errors.rejectValue(field.getName(), errorCodes[2], "This field's value can't be bigger than "+max+".");
        	return;
        }
        //min validation
        if(hasMin && value< min){
        	errors.rejectValue(field.getName(), errorCodes[3], "This field's value can't be smaller than "+max+".");
        	return;        	
        }
	}

	private void validateFieldAsString(Field field, Object object, Errors errors) throws IllegalArgumentException, IllegalAccessException {
        StringConstraint req = field.getAnnotation(StringConstraint.class);
        boolean required=req.required();
        boolean strict=req.strict();
        int maxLength=req.maxLength();
        String rexgre=req.regexp();
        field.setAccessible(true);
    	String stringField=null;
        if(field.get(object) != null)
            stringField = field.get(object).toString();	
        //Null validation
        if (StringUtils.isNotBlank(stringField)== false){
        	if(required)
        		errors.rejectValue(field.getName(), errorCodes[0],"This field is required.");
        	return;
        }
        //strict length validation
        if(strict){
        	if(stringField.length() != maxLength){
        		errors.rejectValue(field.getName(), errorCodes[4],"This filed's length must be"+maxLength+".");
        	    return;
        	}
        }
        //maxLength validation
        if(maxLength >0 && stringField.length() > maxLength){
    		errors.rejectValue(field.getName(), errorCodes[3],"This filed exceeds max length "+maxLength+".");
    	    return;        	        	
        }
        
        //regexp validation
        if(StringUtils.isNotBlank(rexgre)){
        	Pattern pattern = Pattern.compile(req.regexp());
            Matcher matcher = pattern.matcher(stringField);
            if (!matcher.matches()) 
                errors.rejectValue(field.getName(), errorCodes[1], "This filed's value is invalid.");
        }		
	}
	   
	   public void setMessageSource(MessageSource messageSource)
	   {
	      this.messageSource = messageSource;
	   }
}
