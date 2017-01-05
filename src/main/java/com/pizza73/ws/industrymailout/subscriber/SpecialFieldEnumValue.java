package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for SpecialFieldEnumValue complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="SpecialFieldEnumValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TheValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExtraData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpecialFieldEnumValue", propOrder = { "name", "theValue", "extraData" })
public class SpecialFieldEnumValue
{

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "TheValue")
    protected String theValue;
    @XmlElement(name = "ExtraData")
    protected String extraData;

    /**
     * Gets the value of the name property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setName(String value)
    {
        this.name = value;
    }

    /**
     * Gets the value of the theValue property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTheValue()
    {
        return this.theValue;
    }

    /**
     * Sets the value of the theValue property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTheValue(String value)
    {
        this.theValue = value;
    }

    /**
     * Gets the value of the extraData property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExtraData()
    {
        return this.extraData;
    }

    /**
     * Sets the value of the extraData property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setExtraData(String value)
    {
        this.extraData = value;
    }

}
