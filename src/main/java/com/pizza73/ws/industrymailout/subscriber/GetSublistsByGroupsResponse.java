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
 *         &lt;element name="GetSublistsByGroupsResult" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfSubListGroup" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getSublistsByGroupsResult" })
@XmlRootElement(name = "GetSublistsByGroupsResponse")
public class GetSublistsByGroupsResponse
{

    @XmlElement(name = "GetSublistsByGroupsResult")
    protected ArrayOfSubListGroup getSublistsByGroupsResult;

    /**
     * Gets the value of the getSublistsByGroupsResult property.
     * 
     * @return possible object is {@link ArrayOfSubListGroup }
     * 
     */
    public ArrayOfSubListGroup getGetSublistsByGroupsResult()
    {
        return this.getSublistsByGroupsResult;
    }

    /**
     * Sets the value of the getSublistsByGroupsResult property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfSubListGroup }
     * 
     */
    public void setGetSublistsByGroupsResult(ArrayOfSubListGroup value)
    {
        this.getSublistsByGroupsResult = value;
    }

}
