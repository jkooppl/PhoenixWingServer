package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for AutoResponseTrigger complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="AutoResponseTrigger">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DateCreated" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DateEffective" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="AutoResponseEventId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ListmemberId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Complete" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="StateData" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfAutoResponseTriggerState" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutoResponseTrigger", propOrder = { "id", "dateCreated", "dateEffective", "autoResponseEventId",
        "listmemberId", "complete", "stateData" })
public class AutoResponseTrigger
{

    @XmlElement(name = "Id")
    protected int id;
    @XmlElement(name = "DateCreated", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCreated;
    @XmlElement(name = "DateEffective", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateEffective;
    @XmlElement(name = "AutoResponseEventId")
    protected int autoResponseEventId;
    @XmlElement(name = "ListmemberId")
    protected int listmemberId;
    @XmlElement(name = "Complete")
    protected boolean complete;
    @XmlElement(name = "StateData")
    protected ArrayOfAutoResponseTriggerState stateData;

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
     * Gets the value of the dateCreated property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDateCreated()
    {
        return this.dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDateCreated(XMLGregorianCalendar value)
    {
        this.dateCreated = value;
    }

    /**
     * Gets the value of the dateEffective property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDateEffective()
    {
        return this.dateEffective;
    }

    /**
     * Sets the value of the dateEffective property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDateEffective(XMLGregorianCalendar value)
    {
        this.dateEffective = value;
    }

    /**
     * Gets the value of the autoResponseEventId property.
     * 
     */
    public int getAutoResponseEventId()
    {
        return this.autoResponseEventId;
    }

    /**
     * Sets the value of the autoResponseEventId property.
     * 
     */
    public void setAutoResponseEventId(int value)
    {
        this.autoResponseEventId = value;
    }

    /**
     * Gets the value of the listmemberId property.
     * 
     */
    public int getListmemberId()
    {
        return this.listmemberId;
    }

    /**
     * Sets the value of the listmemberId property.
     * 
     */
    public void setListmemberId(int value)
    {
        this.listmemberId = value;
    }

    /**
     * Gets the value of the complete property.
     * 
     */
    public boolean isComplete()
    {
        return this.complete;
    }

    /**
     * Sets the value of the complete property.
     * 
     */
    public void setComplete(boolean value)
    {
        this.complete = value;
    }

    /**
     * Gets the value of the stateData property.
     * 
     * @return possible object is {@link ArrayOfAutoResponseTriggerState }
     * 
     */
    public ArrayOfAutoResponseTriggerState getStateData()
    {
        return this.stateData;
    }

    /**
     * Sets the value of the stateData property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfAutoResponseTriggerState }
     * 
     */
    public void setStateData(ArrayOfAutoResponseTriggerState value)
    {
        this.stateData = value;
    }

}
