package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="csvUploadId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="maxErrors" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "csvUploadId", "maxErrors" })
@XmlRootElement(name = "ProcessCsvUpload")
public class ProcessCsvUpload
{

    protected int csvUploadId;
    protected int maxErrors;

    /**
     * Gets the value of the csvUploadId property.
     * 
     */
    public int getCsvUploadId()
    {
        return this.csvUploadId;
    }

    /**
     * Sets the value of the csvUploadId property.
     * 
     */
    public void setCsvUploadId(int value)
    {
        this.csvUploadId = value;
    }

    /**
     * Gets the value of the maxErrors property.
     * 
     */
    public int getMaxErrors()
    {
        return this.maxErrors;
    }

    /**
     * Sets the value of the maxErrors property.
     * 
     */
    public void setMaxErrors(int value)
    {
        this.maxErrors = value;
    }

}
