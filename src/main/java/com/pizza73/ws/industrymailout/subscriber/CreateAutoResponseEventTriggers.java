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
 *         &lt;element name="autoresponseid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="extraSubscriberData" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfStringKeyValuePair" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "subscriberEmail", "autoresponseid", "extraSubscriberData" })
@XmlRootElement(name = "CreateAutoResponseEventTriggers")
public class CreateAutoResponseEventTriggers
{

    protected String subscriberEmail;
    protected int autoresponseid;
    protected ArrayOfStringKeyValuePair extraSubscriberData;

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
     * Gets the value of the autoresponseid property.
     * 
     */
    public int getAutoresponseid()
    {
        return this.autoresponseid;
    }

    /**
     * Sets the value of the autoresponseid property.
     * 
     */
    public void setAutoresponseid(int value)
    {
        this.autoresponseid = value;
    }

    /**
     * Gets the value of the extraSubscriberData property.
     * 
     * @return possible object is {@link ArrayOfStringKeyValuePair }
     * 
     */
    public ArrayOfStringKeyValuePair getExtraSubscriberData()
    {
        return this.extraSubscriberData;
    }

    /**
     * Sets the value of the extraSubscriberData property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfStringKeyValuePair }
     * 
     */
    public void setExtraSubscriberData(ArrayOfStringKeyValuePair value)
    {
        this.extraSubscriberData = value;
    }

}
