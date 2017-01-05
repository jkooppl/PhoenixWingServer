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
 *         &lt;element name="includeUnsubscribed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="includeActive" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="includeUnconfirmed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="utcStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ignoreNewCsvUploads" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ignoreNewWebServiceUploads" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "mailinglistid", "includeUnsubscribed", "includeActive", "includeUnconfirmed",
        "utcStartDate", "ignoreNewCsvUploads", "ignoreNewWebServiceUploads" })
@XmlRootElement(name = "GetSubscribersAsCSV")
public class GetSubscribersAsCSV
{

    protected int mailinglistid;
    protected boolean includeUnsubscribed;
    protected boolean includeActive;
    protected boolean includeUnconfirmed;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar utcStartDate;
    protected boolean ignoreNewCsvUploads;
    protected boolean ignoreNewWebServiceUploads;

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
     * Gets the value of the includeUnsubscribed property.
     * 
     */
    public boolean isIncludeUnsubscribed()
    {
        return this.includeUnsubscribed;
    }

    /**
     * Sets the value of the includeUnsubscribed property.
     * 
     */
    public void setIncludeUnsubscribed(boolean value)
    {
        this.includeUnsubscribed = value;
    }

    /**
     * Gets the value of the includeActive property.
     * 
     */
    public boolean isIncludeActive()
    {
        return this.includeActive;
    }

    /**
     * Sets the value of the includeActive property.
     * 
     */
    public void setIncludeActive(boolean value)
    {
        this.includeActive = value;
    }

    /**
     * Gets the value of the includeUnconfirmed property.
     * 
     */
    public boolean isIncludeUnconfirmed()
    {
        return this.includeUnconfirmed;
    }

    /**
     * Sets the value of the includeUnconfirmed property.
     * 
     */
    public void setIncludeUnconfirmed(boolean value)
    {
        this.includeUnconfirmed = value;
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
     * Gets the value of the ignoreNewCsvUploads property.
     * 
     */
    public boolean isIgnoreNewCsvUploads()
    {
        return this.ignoreNewCsvUploads;
    }

    /**
     * Sets the value of the ignoreNewCsvUploads property.
     * 
     */
    public void setIgnoreNewCsvUploads(boolean value)
    {
        this.ignoreNewCsvUploads = value;
    }

    /**
     * Gets the value of the ignoreNewWebServiceUploads property.
     * 
     */
    public boolean isIgnoreNewWebServiceUploads()
    {
        return this.ignoreNewWebServiceUploads;
    }

    /**
     * Sets the value of the ignoreNewWebServiceUploads property.
     * 
     */
    public void setIgnoreNewWebServiceUploads(boolean value)
    {
        this.ignoreNewWebServiceUploads = value;
    }

}
