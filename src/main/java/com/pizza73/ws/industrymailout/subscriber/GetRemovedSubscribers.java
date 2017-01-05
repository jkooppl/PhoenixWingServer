package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

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
 *         &lt;element name="mailinglistid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="utcStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="removalreasonid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "mailinglistid", "utcStartDate", "removalreasonid" })
@XmlRootElement(name = "GetRemovedSubscribers")
public class GetRemovedSubscribers
{

    protected int mailinglistid;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar utcStartDate;
    protected int removalreasonid;

    /**
     * Gets the value of the mailinglistid property.
     * 
     */
    public int getMailinglistid()
    {
        return this.mailinglistid;
    }

    /**
     * Sets the value of the mailinglistid property.
     * 
     */
    public void setMailinglistid(int value)
    {
        this.mailinglistid = value;
    }

    /**
     * Gets the value of the utcStartDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getUtcStartDate()
    {
        return this.utcStartDate;
    }

    /**
     * Sets the value of the utcStartDate property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setUtcStartDate(XMLGregorianCalendar value)
    {
        this.utcStartDate = value;
    }

    /**
     * Gets the value of the removalreasonid property.
     * 
     */
    public int getRemovalreasonid()
    {
        return this.removalreasonid;
    }

    /**
     * Sets the value of the removalreasonid property.
     * 
     */
    public void setRemovalreasonid(int value)
    {
        this.removalreasonid = value;
    }

}
