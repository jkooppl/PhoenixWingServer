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
 *         &lt;element name="GetSpecialFieldValuesResult" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfSpecialFieldEnumValue" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getSpecialFieldValuesResult" })
@XmlRootElement(name = "GetSpecialFieldValuesResponse")
public class GetSpecialFieldValuesResponse
{

    @XmlElement(name = "GetSpecialFieldValuesResult")
    protected ArrayOfSpecialFieldEnumValue getSpecialFieldValuesResult;

    /**
     * Gets the value of the getSpecialFieldValuesResult property.
     * 
     * @return possible object is {@link ArrayOfSpecialFieldEnumValue }
     * 
     */
    public ArrayOfSpecialFieldEnumValue getGetSpecialFieldValuesResult()
    {
        return this.getSpecialFieldValuesResult;
    }

    /**
     * Sets the value of the getSpecialFieldValuesResult property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfSpecialFieldEnumValue }
     * 
     */
    public void setGetSpecialFieldValuesResult(ArrayOfSpecialFieldEnumValue value)
    {
        this.getSpecialFieldValuesResult = value;
    }

}
