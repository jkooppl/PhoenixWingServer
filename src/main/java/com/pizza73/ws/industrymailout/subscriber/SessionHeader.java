package com.pizza73.ws.industrymailout.subscriber;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * <p>
 * Java class for SessionHeader complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="SessionHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SessionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServerUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Locale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SessionHeader", propOrder = { "sessionID", "serverUrl", "locale" })
public class SessionHeader
{

    @XmlElement(name = "SessionID")
    protected String sessionID;
    @XmlElement(name = "ServerUrl")
    protected String serverUrl;
    @XmlElement(name = "Locale")
    protected String locale;
    @XmlAnyAttribute
    private final Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the sessionID property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSessionID()
    {
        return this.sessionID;
    }

    /**
     * Sets the value of the sessionID property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSessionID(String value)
    {
        this.sessionID = value;
    }

    /**
     * Gets the value of the serverUrl property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getServerUrl()
    {
        return this.serverUrl;
    }

    /**
     * Sets the value of the serverUrl property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setServerUrl(String value)
    {
        this.serverUrl = value;
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLocale()
    {
        return this.locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setLocale(String value)
    {
        this.locale = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed
     * property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and the value is the string
     * value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute by
     * updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return always non-null
     */
    public Map<QName, String> getOtherAttributes()
    {
        return this.otherAttributes;
    }

}
