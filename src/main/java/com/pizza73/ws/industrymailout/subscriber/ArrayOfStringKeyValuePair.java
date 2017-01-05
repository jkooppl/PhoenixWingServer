package com.pizza73.ws.industrymailout.subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfStringKeyValuePair complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfStringKeyValuePair">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StringKeyValuePair" type="{http://www.mymailout.net/WebService/SubscriberManager}StringKeyValuePair" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfStringKeyValuePair", propOrder = { "stringKeyValuePair" })
public class ArrayOfStringKeyValuePair
{

    @XmlElement(name = "StringKeyValuePair", nillable = true)
    protected List<StringKeyValuePair> stringKeyValuePair;

    /**
     * Gets the value of the stringKeyValuePair property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the stringKeyValuePair property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getStringKeyValuePair().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringKeyValuePair }
     * 
     * 
     */
    public List<StringKeyValuePair> getStringKeyValuePair()
    {
        if (this.stringKeyValuePair == null)
        {
            this.stringKeyValuePair = new ArrayList<StringKeyValuePair>();
        }
        return this.stringKeyValuePair;
    }

}
