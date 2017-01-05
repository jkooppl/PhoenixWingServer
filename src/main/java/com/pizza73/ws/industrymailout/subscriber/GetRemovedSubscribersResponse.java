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
 *         &lt;element name="GetRemovedSubscribersResult" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfSubscriber" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getRemovedSubscribersResult" })
@XmlRootElement(name = "GetRemovedSubscribersResponse")
public class GetRemovedSubscribersResponse
{

    @XmlElement(name = "GetRemovedSubscribersResult")
    protected ArrayOfSubscriber getRemovedSubscribersResult;

    /**
     * Gets the value of the getRemovedSubscribersResult property.
     * 
     * @return possible object is {@link ArrayOfSubscriber }
     * 
     */
    public ArrayOfSubscriber getGetRemovedSubscribersResult()
    {
        return this.getRemovedSubscribersResult;
    }

    /**
     * Sets the value of the getRemovedSubscribersResult property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfSubscriber }
     * 
     */
    public void setGetRemovedSubscribersResult(ArrayOfSubscriber value)
    {
        this.getRemovedSubscribersResult = value;
    }

}
