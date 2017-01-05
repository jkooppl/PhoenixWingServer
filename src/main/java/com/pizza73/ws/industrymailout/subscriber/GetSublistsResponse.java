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
 *         &lt;element name="GetSublistsResult" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfSubList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getSublistsResult" })
@XmlRootElement(name = "GetSublistsResponse")
public class GetSublistsResponse
{

    @XmlElement(name = "GetSublistsResult")
    protected ArrayOfSubList getSublistsResult;

    /**
     * Gets the value of the getSublistsResult property.
     * 
     * @return possible object is {@link ArrayOfSubList }
     * 
     */
    public ArrayOfSubList getGetSublistsResult()
    {
        return this.getSublistsResult;
    }

    /**
     * Sets the value of the getSublistsResult property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfSubList }
     * 
     */
    public void setGetSublistsResult(ArrayOfSubList value)
    {
        this.getSublistsResult = value;
    }

}
