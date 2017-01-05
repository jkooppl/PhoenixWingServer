package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for LoginResult complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="LoginResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.mymailout.net/WebService/SubscriberManager}ResultMessage">
 *       &lt;sequence>
 *         &lt;element name="ServerUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SessionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginResult", propOrder = { "serverUrl", "sessionID" })
public class LoginResult extends ResultMessage
{

    @XmlElement(name = "ServerUrl")
    protected String serverUrl;
    @XmlElement(name = "SessionID")
    protected String sessionID;

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

}
