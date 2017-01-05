package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ResultMessage complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ResultMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Success" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ErrorMessages" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultMessage", propOrder = { "success", "errorMessages" })
@XmlSeeAlso({ UpdateResult.class, LoginResult.class })
public abstract class ResultMessage
{

    @XmlElement(name = "Success")
    protected boolean success;
    @XmlElement(name = "ErrorMessages")
    protected ArrayOfString errorMessages;

    /**
     * Gets the value of the success property.
     * 
     */
    public boolean isSuccess()
    {
        return this.success;
    }

    /**
     * Sets the value of the success property.
     * 
     */
    public void setSuccess(boolean value)
    {
        this.success = value;
    }

    /**
     * Gets the value of the errorMessages property.
     * 
     * @return possible object is {@link ArrayOfString }
     * 
     */
    public ArrayOfString getErrorMessages()
    {
        return this.errorMessages;
    }

    /**
     * Sets the value of the errorMessages property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfString }
     * 
     */
    public void setErrorMessages(ArrayOfString value)
    {
        this.errorMessages = value;
    }

}
