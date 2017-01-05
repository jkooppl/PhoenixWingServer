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
 *         &lt;element name="mailinglistid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="origEmailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subscriber" type="{http://www.mymailout.net/WebService/SubscriberManager}Subscriber" minOccurs="0"/>
 *         &lt;element name="checkrequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="sublistids" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfInt" minOccurs="0"/>
 *         &lt;element name="clearOldSublists" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "mailinglistid", "origEmailAddress", "subscriber", "checkrequired", "sublistids",
        "clearOldSublists" })
@XmlRootElement(name = "UpdateSubscriberWithSublists")
public class UpdateSubscriberWithSublists
{

    protected int mailinglistid;
    protected String origEmailAddress;
    protected Subscriber subscriber;
    protected boolean checkrequired;
    protected ArrayOfInt sublistids;
    protected boolean clearOldSublists;

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
     * Gets the value of the origEmailAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOrigEmailAddress()
    {
        return this.origEmailAddress;
    }

    /**
     * Sets the value of the origEmailAddress property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setOrigEmailAddress(String value)
    {
        this.origEmailAddress = value;
    }

    /**
     * Gets the value of the subscriber property.
     * 
     * @return possible object is {@link Subscriber }
     * 
     */
    public Subscriber getSubscriber()
    {
        return this.subscriber;
    }

    /**
     * Sets the value of the subscriber property.
     * 
     * @param value
     *            allowed object is {@link Subscriber }
     * 
     */
    public void setSubscriber(Subscriber value)
    {
        this.subscriber = value;
    }

    /**
     * Gets the value of the checkrequired property.
     * 
     */
    public boolean isCheckrequired()
    {
        return this.checkrequired;
    }

    /**
     * Sets the value of the checkrequired property.
     * 
     */
    public void setCheckrequired(boolean value)
    {
        this.checkrequired = value;
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
     * Gets the value of the clearOldSublists property.
     * 
     */
    public boolean isClearOldSublists()
    {
        return this.clearOldSublists;
    }

    /**
     * Sets the value of the clearOldSublists property.
     * 
     */
    public void setClearOldSublists(boolean value)
    {
        this.clearOldSublists = value;
    }

}
