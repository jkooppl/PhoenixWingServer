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
 *         &lt;element name="AddNewSublistResult" type="{http://www.mymailout.net/WebService/SubscriberManager}SubList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "addNewSublistResult" })
@XmlRootElement(name = "AddNewSublistResponse")
public class AddNewSublistResponse
{

    @XmlElement(name = "AddNewSublistResult")
    protected SubList addNewSublistResult;

    /**
     * Gets the value of the addNewSublistResult property.
     * 
     * @return possible object is {@link SubList }
     * 
     */
    public SubList getAddNewSublistResult()
    {
        return this.addNewSublistResult;
    }

    /**
     * Sets the value of the addNewSublistResult property.
     * 
     * @param value
     *            allowed object is {@link SubList }
     * 
     */
    public void setAddNewSublistResult(SubList value)
    {
        this.addNewSublistResult = value;
    }

}
