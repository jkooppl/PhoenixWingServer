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
 *         &lt;element name="SignupNewSubscriberToSublistResult" type="{http://www.mymailout.net/WebService/SubscriberManager}UpdateResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "signupNewSubscriberToSublistResult" })
@XmlRootElement(name = "SignupNewSubscriberToSublistResponse")
public class SignupNewSubscriberToSublistResponse
{

    @XmlElement(name = "SignupNewSubscriberToSublistResult")
    protected UpdateResult signupNewSubscriberToSublistResult;

    /**
     * Gets the value of the signupNewSubscriberToSublistResult property.
     * 
     * @return possible object is {@link UpdateResult }
     * 
     */
    public UpdateResult getSignupNewSubscriberToSublistResult()
    {
        return this.signupNewSubscriberToSublistResult;
    }

    /**
     * Sets the value of the signupNewSubscriberToSublistResult property.
     * 
     * @param value
     *            allowed object is {@link UpdateResult }
     * 
     */
    public void setSignupNewSubscriberToSublistResult(UpdateResult value)
    {
        this.signupNewSubscriberToSublistResult = value;
    }

}
