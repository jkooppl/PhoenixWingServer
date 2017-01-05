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
 *         &lt;element name="mailinglistid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sublistids" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfInt" minOccurs="0"/>
 *         &lt;element name="removeFromCurrentSublists" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ignoreHiddenSublists" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "email", "mailinglistid", "sublistids", "removeFromCurrentSublists",
        "ignoreHiddenSublists" })
@XmlRootElement(name = "AddSubscriberToSublists")
public class AddSubscriberToSublists
{

    protected String email;
    protected int mailinglistid;
    protected ArrayOfInt sublistids;
    protected boolean removeFromCurrentSublists;
    protected boolean ignoreHiddenSublists;

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
     * Gets the value of the mailinglistid property.
     * 
     */
    public int getMailinglistid()
    {
        return this.mailinglistid;
    }

    /**
     * Sets the value of the mailinglistid property.
     * 
     */
    public void setMailinglistid(int value)
    {
        this.mailinglistid = value;
    }

    /**
     * Gets the value of the sublistids property.
     * 
     * @return possible object is {@link ArrayOfInt }
     * 
     */
    public ArrayOfInt getSublistids()
    {
        return this.sublistids;
    }

    /**
     * Sets the value of the sublistids property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfInt }
     * 
     */
    public void setSublistids(ArrayOfInt value)
    {
        this.sublistids = value;
    }

    /**
     * Gets the value of the removeFromCurrentSublists property.
     * 
     */
    public boolean isRemoveFromCurrentSublists()
    {
        return this.removeFromCurrentSublists;
    }

    /**
     * Sets the value of the removeFromCurrentSublists property.
     * 
     */
    public void setRemoveFromCurrentSublists(boolean value)
    {
        this.removeFromCurrentSublists = value;
    }

    /**
     * Gets the value of the ignoreHiddenSublists property.
     * 
     */
    public boolean isIgnoreHiddenSublists()
    {
        return this.ignoreHiddenSublists;
    }

    /**
     * Sets the value of the ignoreHiddenSublists property.
     * 
     */
    public void setIgnoreHiddenSublists(boolean value)
    {
        this.ignoreHiddenSublists = value;
    }

}
