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
 *         &lt;element name="RemoveAutoresponseEventTriggersResult" type="{http://www.mymailout.net/WebService/SubscriberManager}UpdateResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "removeAutoresponseEventTriggersResult" })
@XmlRootElement(name = "RemoveAutoresponseEventTriggersResponse")
public class RemoveAutoresponseEventTriggersResponse
{

    @XmlElement(name = "RemoveAutoresponseEventTriggersResult")
    protected UpdateResult removeAutoresponseEventTriggersResult;

    /**
     * Gets the value of the removeAutoresponseEventTriggersResult property.
     * 
     * @return possible object is {@link UpdateResult }
     * 
     */
    public UpdateResult getRemoveAutoresponseEventTriggersResult()
    {
        return this.removeAutoresponseEventTriggersResult;
    }

    /**
     * Sets the value of the removeAutoresponseEventTriggersResult property.
     * 
     * @param value
     *            allowed object is {@link UpdateResult }
     * 
     */
    public void setRemoveAutoresponseEventTriggersResult(UpdateResult value)
    {
        this.removeAutoresponseEventTriggersResult = value;
    }

}
