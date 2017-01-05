package com.pizza73.webapp.base.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.pizza73.model.Address;
import com.pizza73.model.BaseObject;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.util.validator.ValidatorUtils;

/**
 * Performs validation of Addresses.
 *
 * @author Chris Huisman
 */
public class AddressValidator implements Validator {

    private static final String sp = "\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~"; 
    private static final String atext = "[a-zA-Z0-9" + sp + "]"; 
    private static final String atom = atext + "+"; 
    //one or more atext chars 
    private static final String dotAtom = "\\." + atom; 
    private static final String localPart = atom + "(" + dotAtom + ")*"; 
    //one atom followed by 0 or more dotAtoms. 
    //RFC 1035 tokens for domain names: 
    private static final String letter = "[a-zA-Z]"; 
    private static final String letDig = "[a-zA-Z0-9]"; 
    private static final String letDigHyp = "[a-zA-Z0-9-]"; 
    public static final String rfcLabel = letDig + letDigHyp + "{0,61}" + letDig; 
    private static final String domain = rfcLabel + "(\\." + rfcLabel + ")*\\." + letter + "{2,6}"; 
    //Combined together, these form the allowed email regexp allowed by RFC 2822: 
    private static final String addrSpec = "^" + localPart + "@" + domain + "$"; 
    //now compile it: 
    public static final Pattern VALID_PATTERN = Pattern.compile( addrSpec ); 
    
   public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
        return BaseObject.class.isAssignableFrom(clazz);
    }

    public void validate(Object object, Errors errors) {

        OnlineCustomer oc = (OnlineCustomer) object;
        Address address = oc.getAddress();
        
        if (StringUtils.isNotBlank(oc.getEmail()))
        {
            if(!AddressValidator.VALID_PATTERN.matcher(oc.getEmail()).matches())
            {
                errors.rejectValue("email", null, oc.getEmail()
                    + " is an invalid email address");
            }
            if(oc.getEmail().length() > 64)
            {
                errors.rejectValue("email", null, 
                    "Email cannot be longer than 64 characters.");
            }
            //if user doesn't have an account confirm the email address.
            if(oc.getId() == null || oc.getId().equals(0))
            {
                if(!oc.getEmail().equals(oc.getConfirmEmail()))
                {
                    errors.rejectValue("confirmEmail", null, 
                        "Email and confirm email are not the same.");
                }
            }
        }
        
        if(StringUtils.isBlank(oc.getName()))
        {
            errors.rejectValue("name", null, "Name is a required field.");
        }
        else if(oc.getName().length() > 40)
        {
            errors.rejectValue("name", null, oc.getName()
                + " is to long, we only allow for 40 characters for this field.");
        }

        String phoneRegex = "\\d{10}";
        Pattern pattern = Pattern.compile(phoneRegex);
        if (StringUtils.isBlank(oc.getPhone()))
        {
            errors.rejectValue("phone", null, "Phone Number is required");
        }
        else if (!pattern.matcher(oc.getPhone()).matches())
        {
            errors.rejectValue(
                "phone", null,
                 "Phone Number is invalid.  Must be in the format xxx-xxx-xxxx");
        }
        
        if (oc.getAddress().getCity().getId().compareTo(Integer.valueOf(0)) == 0)
        {
            errors.rejectValue(
                "address.city", null, "Please choose your city from the drop down list");
        }
        
        if(StringUtils.isNotBlank(address.getStreetAddress())) {
            
            if(address.getStreetAddress().length() > 40)
            {
                errors.rejectValue("address.streetAddress", null,
                    "Street Address cannot be longer than 40 characters." +
                    "  If you need more space please use the Address Comment field.");
            }
            if(address.getStreetAddress().indexOf('#') >= 0)
            {
                errors.rejectValue("address.streetAddress", null,
                    "The # character is not allowed in the street address field." +
                    "  If you would like to enter an apt or suite number please use the Apt/Suite Number field.");
            }
            if(StringUtils.isAlphaSpace(address.getStreetAddress()))
            {
                errors.rejectValue("address.streetAddress", null,
                    "The street address should start with a number, ie 10303 " +
                    "Jasper Ave NW or 12345 67 ST NW.");
            }
        }
        
        if(StringUtils.isNotBlank(address.getSuiteNumber()) 
            && address.getSuiteNumber().length() > 8)
        {
            errors.rejectValue("address.suiteNumber", null,
                "Suite number cannot be more than 8 characters.  " +
                "If you need more room, please use the Address Comment field.");                   
        }

        if(StringUtils.isNotBlank(address.getAddressComment()) 
            && address.getAddressComment().length() > 30)
        {
            errors.rejectValue("address.addressComment", null,
                "Address comment cannot be more than 30 characters.");                   
        }
    
        if (!ValidatorUtils.validPostalCodeNotRequired(address.getPostalCode()))
        {
            errors.rejectValue("address.postalCode", null,
                    "Postal code is invalid.  Must be in the form a1a1a1");
        }
    }
}
