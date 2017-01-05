package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for AutoResponseTriggerState complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="AutoResponseTriggerState">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AutoResponseTriggerId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutoResponseTriggerState", propOrder = { "id", "autoResponseTriggerId", "name", "value" })
public class AutoResponseTriggerState
{

    @XmlElement(name = "Id")
    protected int id;
    @XmlElement(name = "AutoResponseTriggerId")
    protected int autoResponseTriggerId;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Value")
    protected String value;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value)
    {
        this.id = value;
    }

    /**
     * Gets the value of the autoResponseTriggerId property.
     * 
     */
    public int getAutoResponseTriggerId()
    {
        return this.autoResponseTriggerId;
    }

    /**
     * Sets the value of the autoResponseTriggerId property.
     * 
     */
    public void setAutoResponseTriggerId(int value)
    {
        this.autoResponseTriggerId = value;
    }

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
     * Gets the value of the value property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setValue(String value)
    {
        this.value = value;
    }

}
