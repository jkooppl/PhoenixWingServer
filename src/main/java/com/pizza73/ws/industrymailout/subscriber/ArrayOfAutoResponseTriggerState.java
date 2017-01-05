package com.pizza73.ws.industrymailout.subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfAutoResponseTriggerState complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAutoResponseTriggerState">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AutoResponseTriggerState" type="{http://www.mymailout.net/WebService/SubscriberManager}AutoResponseTriggerState" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAutoResponseTriggerState", propOrder = { "autoResponseTriggerState" })
public class ArrayOfAutoResponseTriggerState
{

    @XmlElement(name = "AutoResponseTriggerState", nillable = true)
    protected List<AutoResponseTriggerState> autoResponseTriggerState;

    /**
     * Gets the value of the autoResponseTriggerState property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the autoResponseTriggerState property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAutoResponseTriggerState().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AutoResponseTriggerState }
     * 
     * 
     */
    public List<AutoResponseTriggerState> getAutoResponseTriggerState()
    {
        if (this.autoResponseTriggerState == null)
        {
            this.autoResponseTriggerState = new ArrayList<AutoResponseTriggerState>();
        }
        return this.autoResponseTriggerState;
    }

}
