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
 *         &lt;element name="GetAllTriggersResult" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfAutoResponseTrigger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getAllTriggersResult" })
@XmlRootElement(name = "GetAllTriggersResponse")
public class GetAllTriggersResponse
{

    @XmlElement(name = "GetAllTriggersResult")
    protected ArrayOfAutoResponseTrigger getAllTriggersResult;

    /**
     * Gets the value of the getAllTriggersResult property.
     * 
     * @return possible object is {@link ArrayOfAutoResponseTrigger }
     * 
     */
    public ArrayOfAutoResponseTrigger getGetAllTriggersResult()
    {
        return this.getAllTriggersResult;
    }

    /**
     * Sets the value of the getAllTriggersResult property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfAutoResponseTrigger }
     * 
     */
    public void setGetAllTriggersResult(ArrayOfAutoResponseTrigger value)
    {
        this.getAllTriggersResult = value;
    }

}
