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
 *         &lt;element name="SendConfirmationEmailResult" type="{http://www.mymailout.net/WebService/SubscriberManager}UpdateResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "sendConfirmationEmailResult" })
@XmlRootElement(name = "SendConfirmationEmailResponse")
public class SendConfirmationEmailResponse
{

    @XmlElement(name = "SendConfirmationEmailResult")
    protected UpdateResult sendConfirmationEmailResult;

    /**
     * Gets the value of the sendConfirmationEmailResult property.
     * 
     * @return possible object is {@link UpdateResult }
     * 
     */
    public UpdateResult getSendConfirmationEmailResult()
    {
        return this.sendConfirmationEmailResult;
    }

    /**
     * Sets the value of the sendConfirmationEmailResult property.
     * 
     * @param value
     *            allowed object is {@link UpdateResult }
     * 
     */
    public void setSendConfirmationEmailResult(UpdateResult value)
    {
        this.sendConfirmationEmailResult = value;
    }

}
