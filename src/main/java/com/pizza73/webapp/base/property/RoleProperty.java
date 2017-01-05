package com.pizza73.webapp.base.property;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pizza73.model.Role;
import com.pizza73.service.LookupManager;

/**
 * RoleProperty.java TODO comment me
 *
 * @author chris
 * 28-Sep-06
 *
 * @Copyright Flying Pizza 73 
 */
@Component("roleProperty")
public class RoleProperty extends PropertyEditorSupport {
   @Autowired
   private LookupManager lookupManager;

    public RoleProperty() {
	super();
    }

	/* (non-Javadoc)
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    @Override
    public String getAsText() {
        Role value = (Role)getValue();
        return value == null ? "" : value.getId().toString();
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Role Role = this.lookupManager.roleByName(text);
        if(Role == null) {
            throw new IllegalArgumentException(
                "invalid value for Role: " + text);
        }
        setValue(Role);
    }
    
    
}
