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
 *         &lt;element name="activeonly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="subscriberformvarsonly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "mailinglistid", "activeonly", "subscriberformvarsonly" })
@XmlRootElement(name = "GetSublistsAdvanced")
public class GetSublistsAdvanced
{

    protected int mailinglistid;
    protected boolean activeonly;
    protected boolean subscriberformvarsonly;

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
     * Gets the value of the activeonly property.
     * 
     */
    public boolean isActiveonly()
    {
        return this.activeonly;
    }

    /**
     * Sets the value of the activeonly property.
     * 
     */
    public void setActiveonly(boolean value)
    {
        this.activeonly = value;
    }

    /**
     * Gets the value of the subscriberformvarsonly property.
     * 
     */
    public boolean isSubscriberformvarsonly()
    {
        return this.subscriberformvarsonly;
    }

    /**
     * Sets the value of the subscriberformvarsonly property.
     * 
     */
    public void setSubscriberformvarsonly(boolean value)
    {
        this.subscriberformvarsonly = value;
    }

}
