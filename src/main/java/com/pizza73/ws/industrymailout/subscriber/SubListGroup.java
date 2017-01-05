package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for SubListGroup complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="SubListGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Isrequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Issingle" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Ordering" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SubLists" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfSubList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubListGroup", propOrder = { "id", "name", "isrequired", "issingle", "ordering", "subLists" })
public class SubListGroup
{

    @XmlElement(name = "Id")
    protected int id;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Isrequired")
    protected boolean isrequired;
    @XmlElement(name = "Issingle")
    protected boolean issingle;
    @XmlElement(name = "Ordering")
    protected int ordering;
    @XmlElement(name = "SubLists")
    protected ArrayOfSubList subLists;

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
     * Gets the value of the isrequired property.
     * 
     */
    public boolean isIsrequired()
    {
        return this.isrequired;
    }

    /**
     * Sets the value of the isrequired property.
     * 
     */
    public void setIsrequired(boolean value)
    {
        this.isrequired = value;
    }

    /**
     * Gets the value of the issingle property.
     * 
     */
    public boolean isIssingle()
    {
        return this.issingle;
    }

    /**
     * Sets the value of the issingle property.
     * 
     */
    public void setIssingle(boolean value)
    {
        this.issingle = value;
    }

    /**
     * Gets the value of the ordering property.
     * 
     */
    public int getOrdering()
    {
        return this.ordering;
    }

    /**
     * Sets the value of the ordering property.
     * 
     */
    public void setOrdering(int value)
    {
        this.ordering = value;
    }

    /**
     * Gets the value of the subLists property.
     * 
     * @return possible object is {@link ArrayOfSubList }
     * 
     */
    public ArrayOfSubList getSubLists()
    {
        return this.subLists;
    }

    /**
     * Sets the value of the subLists property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfSubList }
     * 
     */
    public void setSubLists(ArrayOfSubList value)
    {
        this.subLists = value;
    }

}
