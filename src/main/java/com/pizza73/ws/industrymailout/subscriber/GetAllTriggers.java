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
 *         &lt;element name="subscriberEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailingListId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="excludeComplete" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "subscriberEmail", "mailingListId", "excludeComplete" })
@XmlRootElement(name = "GetAllTriggers")
public class GetAllTriggers
{

    protected String subscriberEmail;
    protected int mailingListId;
    protected boolean excludeComplete;

    /**
     * Gets the value of the subscriberEmail property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSubscriberEmail()
    {
        return this.subscriberEmail;
    }

    /**
     * Sets the value of the subscriberEmail property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSubscriberEmail(String value)
    {
        this.subscriberEmail = value;
    }

    /**
     * Gets the value of the mailingListId property.
     * 
     */
    public int getMailingListId()
    {
        return this.mailingListId;
    }

    /**
     * Sets the value of the mailingListId property.
     * 
     */
    public void setMailingListId(int value)
    {
        this.mailingListId = value;
    }

    /**
     * Gets the value of the excludeComplete property.
     * 
     */
    public boolean isExcludeComplete()
    {
        return this.excludeComplete;
    }

    /**
     * Sets the value of the excludeComplete property.
     * 
     */
    public void setExcludeComplete(boolean value)
    {
        this.excludeComplete = value;
    }

}
