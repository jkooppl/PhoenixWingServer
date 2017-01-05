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
 *         &lt;element name="SubmitCsvFileResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "submitCsvFileResult" })
@XmlRootElement(name = "SubmitCsvFileResponse")
public class SubmitCsvFileResponse
{

    @XmlElement(name = "SubmitCsvFileResult")
    protected int submitCsvFileResult;

    /**
     * Gets the value of the submitCsvFileResult property.
     * 
     */
    public int getSubmitCsvFileResult()
    {
        return this.submitCsvFileResult;
    }

    /**
     * Sets the value of the submitCsvFileResult property.
     * 
     */
    public void setSubmitCsvFileResult(int value)
    {
        this.submitCsvFileResult = value;
    }

}
