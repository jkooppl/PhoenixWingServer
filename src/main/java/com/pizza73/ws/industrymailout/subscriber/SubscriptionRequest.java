package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for SubscriptionRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="SubscriptionRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Confirmationtoken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Xmldata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dateexpires" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubscriptionRequest", propOrder = { "confirmationtoken", "email", "xmldata", "dateexpires" })
public class SubscriptionRequest
{

    @XmlElement(name = "Confirmationtoken")
    protected String confirmationtoken;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "Xmldata")
    protected String xmldata;
    @XmlElement(name = "Dateexpires", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateexpires;

    /**
     * Gets the value of the confirmationtoken property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConfirmationtoken()
    {
        return this.confirmationtoken;
    }

    /**
     * Sets the value of the confirmationtoken property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConfirmationtoken(String value)
    {
        this.confirmationtoken = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEmail(String value)
    {
        this.email = value;
    }

    /**
     * Gets the value of the xmldata property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmldata()
    {
        return this.xmldata;
    }

    /**
     * Sets the value of the xmldata property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setXmldata(String value)
    {
        this.xmldata = value;
    }

    /**
     * Gets the value of the dateexpires property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDateexpires()
    {
        return this.dateexpires;
    }

    /**
     * Sets the value of the dateexpires property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDateexpires(XMLGregorianCalendar value)
    {
        this.dateexpires = value;
    }

}
