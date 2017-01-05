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
 *         &lt;element name="GetAllSubscribersAsCSVResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getAllSubscribersAsCSVResult" })
@XmlRootElement(name = "GetAllSubscribersAsCSVResponse")
public class GetAllSubscribersAsCSVResponse
{

    @XmlElement(name = "GetAllSubscribersAsCSVResult")
    protected String getAllSubscribersAsCSVResult;

    /**
     * Gets the value of the getAllSubscribersAsCSVResult property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getGetAllSubscribersAsCSVResult()
    {
        return this.getAllSubscribersAsCSVResult;
    }

    /**
     * Sets the value of the getAllSubscribersAsCSVResult property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setGetAllSubscribersAsCSVResult(String value)
    {
        this.getAllSubscribersAsCSVResult = value;
    }

}
