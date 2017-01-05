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
 *         &lt;element name="GetMailingListsResult" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfMailingList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getMailingListsResult" })
@XmlRootElement(name = "GetMailingListsResponse")
public class GetMailingListsResponse
{

    @XmlElement(name = "GetMailingListsResult")
    protected ArrayOfMailingList getMailingListsResult;

    /**
     * Gets the value of the getMailingListsResult property.
     * 
     * @return possible object is {@link ArrayOfMailingList }
     * 
     */
    public ArrayOfMailingList getGetMailingListsResult()
    {
        return this.getMailingListsResult;
    }

    /**
     * Sets the value of the getMailingListsResult property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfMailingList }
     * 
     */
    public void setGetMailingListsResult(ArrayOfMailingList value)
    {
        this.getMailingListsResult = value;
    }

}
