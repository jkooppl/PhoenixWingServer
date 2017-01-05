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
 *         &lt;element name="ProcessCsvUploadResult" type="{http://www.mymailout.net/WebService/SubscriberManager}UploadReport" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "processCsvUploadResult" })
@XmlRootElement(name = "ProcessCsvUploadResponse")
public class ProcessCsvUploadResponse
{

    @XmlElement(name = "ProcessCsvUploadResult")
    protected UploadReport processCsvUploadResult;

    /**
     * Gets the value of the processCsvUploadResult property.
     * 
     * @return possible object is {@link UploadReport }
     * 
     */
    public UploadReport getProcessCsvUploadResult()
    {
        return this.processCsvUploadResult;
    }

    /**
     * Sets the value of the processCsvUploadResult property.
     * 
     * @param value
     *            allowed object is {@link UploadReport }
     * 
     */
    public void setProcessCsvUploadResult(UploadReport value)
    {
        this.processCsvUploadResult = value;
    }

}
