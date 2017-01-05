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
 *         &lt;element name="GetMailingListResult" type="{http://www.mymailout.net/WebService/SubscriberManager}MailingList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getMailingListResult" })
@XmlRootElement(name = "GetMailingListResponse")
public class GetMailingListResponse
{

    @XmlElement(name = "GetMailingListResult")
    protected MailingList getMailingListResult;

    /**
     * Gets the value of the getMailingListResult property.
     * 
     * @return possible object is {@link MailingList }
     * 
     */
    public MailingList getGetMailingListResult()
    {
        return this.getMailingListResult;
    }

    /**
     * Sets the value of the getMailingListResult property.
     * 
     * @param value
     *            allowed object is {@link MailingList }
     * 
     */
    public void setGetMailingListResult(MailingList value)
    {
        this.getMailingListResult = value;
    }

}
