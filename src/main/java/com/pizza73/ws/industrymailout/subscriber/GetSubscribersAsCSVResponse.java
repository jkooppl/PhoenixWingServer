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
 *         &lt;element name="GetSubscribersAsCSVResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getSubscribersAsCSVResult" })
@XmlRootElement(name = "GetSubscribersAsCSVResponse")
public class GetSubscribersAsCSVResponse
{

    @XmlElement(name = "GetSubscribersAsCSVResult")
    protected String getSubscribersAsCSVResult;

    /**
     * Gets the value of the getSubscribersAsCSVResult property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getGetSubscribersAsCSVResult()
    {
        return this.getSubscribersAsCSVResult;
    }

    /**
     * Sets the value of the getSubscribersAsCSVResult property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setGetSubscribersAsCSVResult(String value)
    {
        this.getSubscribersAsCSVResult = value;
    }

}
