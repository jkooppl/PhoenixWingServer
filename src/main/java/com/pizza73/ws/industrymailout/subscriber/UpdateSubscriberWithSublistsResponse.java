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
 *         &lt;element name="UpdateSubscriberWithSublistsResult" type="{http://www.mymailout.net/WebService/SubscriberManager}UpdateResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "updateSubscriberWithSublistsResult" })
@XmlRootElement(name = "UpdateSubscriberWithSublistsResponse")
public class UpdateSubscriberWithSublistsResponse
{

    @XmlElement(name = "UpdateSubscriberWithSublistsResult")
    protected UpdateResult updateSubscriberWithSublistsResult;

    /**
     * Gets the value of the updateSubscriberWithSublistsResult property.
     * 
     * @return possible object is {@link UpdateResult }
     * 
     */
    public UpdateResult getUpdateSubscriberWithSublistsResult()
    {
        return this.updateSubscriberWithSublistsResult;
    }

    /**
     * Sets the value of the updateSubscriberWithSublistsResult property.
     * 
     * @param value
     *            allowed object is {@link UpdateResult }
     * 
     */
    public void setUpdateSubscriberWithSublistsResult(UpdateResult value)
    {
        this.updateSubscriberWithSublistsResult = value;
    }

}
