package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
 *         &lt;element name="SubmitCsvFileWithSublistResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "submitCsvFileWithSublistResult" })
@XmlRootElement(name = "SubmitCsvFileWithSublistResponse")
public class SubmitCsvFileWithSublistResponse
{

    @XmlElement(name = "SubmitCsvFileWithSublistResult")
    protected int submitCsvFileWithSublistResult;

    /**
     * Gets the value of the submitCsvFileWithSublistResult property.
     * 
     */
    public int getSubmitCsvFileWithSublistResult()
    {
        return this.submitCsvFileWithSublistResult;
    }

    /**
     * Sets the value of the submitCsvFileWithSublistResult property.
     * 
     */
    public void setSubmitCsvFileWithSublistResult(int value)
    {
        this.submitCsvFileWithSublistResult = value;
    }

}
