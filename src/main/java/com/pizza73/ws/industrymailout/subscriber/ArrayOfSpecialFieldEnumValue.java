package com.pizza73.ws.industrymailout.subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfSpecialFieldEnumValue complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSpecialFieldEnumValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SpecialFieldEnumValue" type="{http://www.mymailout.net/WebService/SubscriberManager}SpecialFieldEnumValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSpecialFieldEnumValue", propOrder = { "specialFieldEnumValue" })
public class ArrayOfSpecialFieldEnumValue
{

    @XmlElement(name = "SpecialFieldEnumValue", nillable = true)
    protected List<SpecialFieldEnumValue> specialFieldEnumValue;

    /**
     * Gets the value of the specialFieldEnumValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the specialFieldEnumValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getSpecialFieldEnumValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SpecialFieldEnumValue }
     * 
     * 
     */
    public List<SpecialFieldEnumValue> getSpecialFieldEnumValue()
    {
        if (this.specialFieldEnumValue == null)
        {
            this.specialFieldEnumValue = new ArrayList<SpecialFieldEnumValue>();
        }
        return this.specialFieldEnumValue;
    }

}
