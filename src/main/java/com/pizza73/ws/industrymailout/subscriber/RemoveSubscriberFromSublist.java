package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sublistid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "email", "sublistid" })
@XmlRootElement(name = "RemoveSubscriberFromSublist")
public class RemoveSubscriberFromSublist
{

    protected String email;
    protected int sublistid;

    /**
     * Gets the value of the email property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEmail(String value)
    {
        this.email = value;
    }

    /**
     * Gets the value of the sublistid property.
     * 
     */
    public int getSublistid()
    {
        return this.sublistid;
    }

    /**
     * Sets the value of the sublistid property.
     * 
     */
    public void setSublistid(int value)
    {
        this.sublistid = value;
    }

}
