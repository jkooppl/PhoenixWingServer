package com.pizza73.ws.industrymailout.subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfAutoResponseTrigger complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAutoResponseTrigger">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AutoResponseTrigger" type="{http://www.mymailout.net/WebService/SubscriberManager}AutoResponseTrigger" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAutoResponseTrigger", propOrder = { "autoResponseTrigger" })
public class ArrayOfAutoResponseTrigger
{

    @XmlElement(name = "AutoResponseTrigger", nillable = true)
    protected List<AutoResponseTrigger> autoResponseTrigger;

    /**
     * Gets the value of the autoResponseTrigger property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the autoResponseTrigger property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAutoResponseTrigger().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AutoResponseTrigger }
     * 
     * 
     */
    public List<AutoResponseTrigger> getAutoResponseTrigger()
    {
        if (this.autoResponseTrigger == null)
        {
            this.autoResponseTrigger = new ArrayList<AutoResponseTrigger>();
        }
        return this.autoResponseTrigger;
    }

}
