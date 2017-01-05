package com.pizza73.webapp.base.property;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pizza73.model.Product;
import com.pizza73.service.LookupManager;

/**
 * ProductPropertyEditor.java TODO comment me
 * 
 * @author chris 28-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Component("productProperty")
public class ProductPropertyEditor extends PropertyEditorSupport
{
    @Autowired
    private LookupManager mgr;

    public ProductPropertyEditor()
    {
    }

    @Override
    public String getAsText()
    {
        Object value = getValue();
        return value == null ? "" : ((Product) value).getId().toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        Product product = (Product) mgr.get(Product.class, Integer.valueOf(text));
        if (product == null)
        {
            throw new IllegalArgumentException("invalid value for product: " + text);
        }
        setValue(product);
    }
}
