package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="AddSubscriberToSublistResult" type="{http://www.mymailout.net/WebService/SubscriberManager}UpdateResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "addSubscriberToSublistResult" })
@XmlRootElement(name = "AddSubscriberToSublistResponse")
public class AddSubscriberToSublistResponse
{

    @XmlElement(name = "AddSubscriberToSublistResult")
    protected UpdateResult addSubscriberToSublistResult;

    /**
     * Gets the value of the addSubscriberToSublistResult property.
     * 
     * @return possible object is {@link UpdateResult }
     * 
     */
    public UpdateResult getAddSubscriberToSublistResult()
    {
        return this.addSubscriberToSublistResult;
    }

    /**
     * Sets the value of the addSubscriberToSublistResult property.
     * 
     * @param value
     *            allowed object is {@link UpdateResult }
     * 
     */
    public void setAddSubscriberToSublistResult(UpdateResult value)
    {
        this.addSubscriberToSublistResult = value;
    }

}
