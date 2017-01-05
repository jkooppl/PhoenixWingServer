package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for UploadError complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="UploadError">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Rownumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Errormsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UploadError", propOrder = { "rownumber", "email", "errormsg" })
public class UploadError
{

    @XmlElement(name = "Rownumber")
    protected int rownumber;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "Errormsg")
    protected String errormsg;

    /**
     * Gets the value of the rownumber property.
     * 
     */
    public int getRownumber()
    {
        return this.rownumber;
    }

    /**
     * Sets the value of the rownumber property.
     * 
     */
    public void setRownumber(int value)
    {
        this.rownumber = value;
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
     * Gets the value of the errormsg property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getErrormsg()
    {
        return this.errormsg;
    }

    /**
     * Sets the value of the errormsg property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setErrormsg(String value)
    {
        this.errormsg = value;
    }

}
