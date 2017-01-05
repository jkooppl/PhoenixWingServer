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
 *         &lt;element name="RegisterSubscriptionRequestResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "registerSubscriptionRequestResult" })
@XmlRootElement(name = "RegisterSubscriptionRequestResponse")
public class RegisterSubscriptionRequestResponse
{

    @XmlElement(name = "RegisterSubscriptionRequestResult")
    protected String registerSubscriptionRequestResult;

    /**
     * Gets the value of the registerSubscriptionRequestResult property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRegisterSubscriptionRequestResult()
    {
        return this.registerSubscriptionRequestResult;
    }

    /**
     * Sets the value of the registerSubscriptionRequestResult property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRegisterSubscriptionRequestResult(String value)
    {
        this.registerSubscriptionRequestResult = value;
    }

}
