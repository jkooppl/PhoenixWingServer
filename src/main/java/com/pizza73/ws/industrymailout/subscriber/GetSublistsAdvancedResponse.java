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
 *         &lt;element name="GetSublistsAdvancedResult" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfSubList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getSublistsAdvancedResult" })
@XmlRootElement(name = "GetSublistsAdvancedResponse")
public class GetSublistsAdvancedResponse
{

    @XmlElement(name = "GetSublistsAdvancedResult")
    protected ArrayOfSubList getSublistsAdvancedResult;

    /**
     * Gets the value of the getSublistsAdvancedResult property.
     * 
     * @return possible object is {@link ArrayOfSubList }
     * 
     */
    public ArrayOfSubList getGetSublistsAdvancedResult()
    {
        return this.getSublistsAdvancedResult;
    }

    /**
     * Sets the value of the getSublistsAdvancedResult property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfSubList }
     * 
     */
    public void setGetSublistsAdvancedResult(ArrayOfSubList value)
    {
        this.getSublistsAdvancedResult = value;
    }

}
