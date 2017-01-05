package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for UploadReport complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="UploadReport">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Diagnostics" type="{http://www.mymailout.net/WebService/SubscriberManager}CsvUploadDiagnosticInfo" minOccurs="0"/>
 *         &lt;element name="Fatalerrormessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sourcename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Downloadurl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Totaluploaded" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Totalinvalid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Totalduplicates" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Totalunsubscribed" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Totalupdated" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Totalinserted" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Totalreactivated" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Totalsuppressed" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Totalothererrors" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Totaloldrecordsdeleted" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Totaladdedtosublist" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Totalspamtraps" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Totalquestionable" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalBounced" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalRemovedByImport" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalRemovedByManager" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalRemovedByManualUnsubscribe" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalRemovedByAdministrator" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalUnknown" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sublistid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SublistName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Listmemberuploadid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalAlreadyInSublist" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Invalidemailerrors" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfUploadError" minOccurs="0"/>
 *         &lt;element name="Othererrors" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfUploadError" minOccurs="0"/>
 *         &lt;element name="Duplicateemailerrors" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfUploadError" minOccurs="0"/>
 *         &lt;element name="Alreadyunsubscribederrors" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfUploadError" minOccurs="0"/>
 *         &lt;element name="Suppressedaddresserrors" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfUploadError" minOccurs="0"/>
 *         &lt;element name="Spamtraperrors" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfUploadError" minOccurs="0"/>
 *         &lt;element name="Questionableaddresswarnings" type="{http://www.mymailout.net/WebService/SubscriberManager}ArrayOfUploadError" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UploadReport", propOrder = { "diagnostics", "fatalerrormessage", "sourcename", "downloadurl",
        "totaluploaded", "totalinvalid", "totalduplicates", "totalunsubscribed", "totalupdated", "totalinserted",
        "totalreactivated", "totalsuppressed", "totalothererrors", "totaloldrecordsdeleted", "totaladdedtosublist",
        "totalspamtraps", "totalquestionable", "totalBounced", "totalRemovedByImport", "totalRemovedByManager",
        "totalRemovedByManualUnsubscribe", "totalRemovedByAdministrator", "totalUnknown", "sublistid", "sublistName",
        "listmemberuploadid", "totalAlreadyInSublist", "invalidemailerrors", "othererrors", "duplicateemailerrors",
        "alreadyunsubscribederrors", "suppressedaddresserrors", "spamtraperrors", "questionableaddresswarnings" })
public class UploadReport
{

    @XmlElement(name = "Diagnostics")
    protected CsvUploadDiagnosticInfo diagnostics;
    @XmlElement(name = "Fatalerrormessage")
    protected String fatalerrormessage;
    @XmlElement(name = "Sourcename")
    protected String sourcename;
    @XmlElement(name = "Downloadurl")
    protected String downloadurl;
    @XmlElement(name = "Totaluploaded")
    protected int totaluploaded;
    @XmlElement(name = "Totalinvalid")
    protected int totalinvalid;
    @XmlElement(name = "Totalduplicates")
    protected int totalduplicates;
    @XmlElement(name = "Totalunsubscribed")
    protected int totalunsubscribed;
    @XmlElement(name = "Totalupdated")
    protected int totalupdated;
    @XmlElement(name = "Totalinserted")
    protected int totalinserted;
    @XmlElement(name = "Totalreactivated")
    protected int totalreactivated;
    @XmlElement(name = "Totalsuppressed")
    protected int totalsuppressed;
    @XmlElement(name = "Totalothererrors")
    protected int totalothererrors;
    @XmlElement(name = "Totaloldrecordsdeleted")
    protected int totaloldrecordsdeleted;
    @XmlElement(name = "Totaladdedtosublist")
    protected int totaladdedtosublist;
    @XmlElement(name = "Totalspamtraps")
    protected int totalspamtraps;
    @XmlElement(name = "Totalquestionable")
    protected int totalquestionable;
    @XmlElement(name = "TotalBounced")
    protected int totalBounced;
    @XmlElement(name = "TotalRemovedByImport")
    protected int totalRemovedByImport;
    @XmlElement(name = "TotalRemovedByManager")
    protected int totalRemovedByManager;
    @XmlElement(name = "TotalRemovedByManualUnsubscribe")
    protected int totalRemovedByManualUnsubscribe;
    @XmlElement(name = "TotalRemovedByAdministrator")
    protected int totalRemovedByAdministrator;
    @XmlElement(name = "TotalUnknown")
    protected int totalUnknown;
    @XmlElement(name = "Sublistid")
    protected int sublistid;
    @XmlElement(name = "SublistName")
    protected String sublistName;
    @XmlElement(name = "Listmemberuploadid")
    protected int listmemberuploadid;
    @XmlElement(name = "TotalAlreadyInSublist")
    protected int totalAlreadyInSublist;
    @XmlElement(name = "Invalidemailerrors")
    protected ArrayOfUploadError invalidemailerrors;
    @XmlElement(name = "Othererrors")
    protected ArrayOfUploadError othererrors;
    @XmlElement(name = "Duplicateemailerrors")
    protected ArrayOfUploadError duplicateemailerrors;
    @XmlElement(name = "Alreadyunsubscribederrors")
    protected ArrayOfUploadError alreadyunsubscribederrors;
    @XmlElement(name = "Suppressedaddresserrors")
    protected ArrayOfUploadError suppressedaddresserrors;
    @XmlElement(name = "Spamtraperrors")
    protected ArrayOfUploadError spamtraperrors;
    @XmlElement(name = "Questionableaddresswarnings")
    protected ArrayOfUploadError questionableaddresswarnings;

    /**
     * Gets the value of the diagnostics property.
     * 
     * @return possible object is {@link CsvUploadDiagnosticInfo }
     * 
     */
    public CsvUploadDiagnosticInfo getDiagnostics()
    {
        return this.diagnostics;
    }

    /**
     * Sets the value of the diagnostics property.
     * 
     * @param value
     *            allowed object is {@link CsvUploadDiagnosticInfo }
     * 
     */
    public void setDiagnostics(CsvUploadDiagnosticInfo value)
    {
        this.diagnostics = value;
    }

    /**
     * Gets the value of the fatalerrormessage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFatalerrormessage()
    {
        return this.fatalerrormessage;
    }

    /**
     * Sets the value of the fatalerrormessage property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setFatalerrormessage(String value)
    {
        this.fatalerrormessage = value;
    }

    /**
     * Gets the value of the sourcename property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSourcename()
    {
        return this.sourcename;
    }

    /**
     * Sets the value of the sourcename property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSourcename(String value)
    {
        this.sourcename = value;
    }

    /**
     * Gets the value of the downloadurl property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDownloadurl()
    {
        return this.downloadurl;
    }

    /**
     * Sets the value of the downloadurl property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDownloadurl(String value)
    {
        this.downloadurl = value;
    }

    /**
     * Gets the value of the totaluploaded property.
     * 
     */
    public int getTotaluploaded()
    {
        return this.totaluploaded;
    }

    /**
     * Sets the value of the totaluploaded property.
     * 
     */
    public void setTotaluploaded(int value)
    {
        this.totaluploaded = value;
    }

    /**
     * Gets the value of the totalinvalid property.
     * 
     */
    public int getTotalinvalid()
    {
        return this.totalinvalid;
    }

    /**
     * Sets the value of the totalinvalid property.
     * 
     */
    public void setTotalinvalid(int value)
    {
        this.totalinvalid = value;
    }

    /**
     * Gets the value of the totalduplicates property.
     * 
     */
    public int getTotalduplicates()
    {
        return this.totalduplicates;
    }

    /**
     * Sets the value of the totalduplicates property.
     * 
     */
    public void setTotalduplicates(int value)
    {
        this.totalduplicates = value;
    }

    /**
     * Gets the value of the totalunsubscribed property.
     * 
     */
    public int getTotalunsubscribed()
    {
        return this.totalunsubscribed;
    }

    /**
     * Sets the value of the totalunsubscribed property.
     * 
     */
    public void setTotalunsubscribed(int value)
    {
        this.totalunsubscribed = value;
    }

    /**
     * Gets the value of the totalupdated property.
     * 
     */
    public int getTotalupdated()
    {
        return this.totalupdated;
    }

    /**
     * Sets the value of the totalupdated property.
     * 
     */
    public void setTotalupdated(int value)
    {
        this.totalupdated = value;
    }

    /**
     * Gets the value of the totalinserted property.
     * 
     */
    public int getTotalinserted()
    {
        return this.totalinserted;
    }

    /**
     * Sets the value of the totalinserted property.
     * 
     */
    public void setTotalinserted(int value)
    {
        this.totalinserted = value;
    }

    /**
     * Gets the value of the totalreactivated property.
     * 
     */
    public int getTotalreactivated()
    {
        return this.totalreactivated;
    }

    /**
     * Sets the value of the totalreactivated property.
     * 
     */
    public void setTotalreactivated(int value)
    {
        this.totalreactivated = value;
    }

    /**
     * Gets the value of the totalsuppressed property.
     * 
     */
    public int getTotalsuppressed()
    {
        return this.totalsuppressed;
    }

    /**
     * Sets the value of the totalsuppressed property.
     * 
     */
    public void setTotalsuppressed(int value)
    {
        this.totalsuppressed = value;
    }

    /**
     * Gets the value of the totalothererrors property.
     * 
     */
    public int getTotalothererrors()
    {
        return this.totalothererrors;
    }

    /**
     * Sets the value of the totalothererrors property.
     * 
     */
    public void setTotalothererrors(int value)
    {
        this.totalothererrors = value;
    }

    /**
     * Gets the value of the totaloldrecordsdeleted property.
     * 
     */
    public int getTotaloldrecordsdeleted()
    {
        return this.totaloldrecordsdeleted;
    }

    /**
     * Sets the value of the totaloldrecordsdeleted property.
     * 
     */
    public void setTotaloldrecordsdeleted(int value)
    {
        this.totaloldrecordsdeleted = value;
    }

    /**
     * Gets the value of the totaladdedtosublist property.
     * 
     */
    public int getTotaladdedtosublist()
    {
        return this.totaladdedtosublist;
    }

    /**
     * Sets the value of the totaladdedtosublist property.
     * 
     */
    public void setTotaladdedtosublist(int value)
    {
        this.totaladdedtosublist = value;
    }

    /**
     * Gets the value of the totalspamtraps property.
     * 
     */
    public int getTotalspamtraps()
    {
        return this.totalspamtraps;
    }

    /**
     * Sets the value of the totalspamtraps property.
     * 
     */
    public void setTotalspamtraps(int value)
    {
        this.totalspamtraps = value;
    }

    /**
     * Gets the value of the totalquestionable property.
     * 
     */
    public int getTotalquestionable()
    {
        return this.totalquestionable;
    }

    /**
     * Sets the value of the totalquestionable property.
     * 
     */
    public void setTotalquestionable(int value)
    {
        this.totalquestionable = value;
    }

    /**
     * Gets the value of the totalBounced property.
     * 
     */
    public int getTotalBounced()
    {
        return this.totalBounced;
    }

    /**
     * Sets the value of the totalBounced property.
     * 
     */
    public void setTotalBounced(int value)
    {
        this.totalBounced = value;
    }

    /**
     * Gets the value of the totalRemovedByImport property.
     * 
     */
    public int getTotalRemovedByImport()
    {
        return this.totalRemovedByImport;
    }

    /**
     * Sets the value of the totalRemovedByImport property.
     * 
     */
    public void setTotalRemovedByImport(int value)
    {
        this.totalRemovedByImport = value;
    }

    /**
     * Gets the value of the totalRemovedByManager property.
     * 
     */
    public int getTotalRemovedByManager()
    {
        return this.totalRemovedByManager;
    }

    /**
     * Sets the value of the totalRemovedByManager property.
     * 
     */
    public void setTotalRemovedByManager(int value)
    {
        this.totalRemovedByManager = value;
    }

    /**
     * Gets the value of the totalRemovedByManualUnsubscribe property.
     * 
     */
    public int getTotalRemovedByManualUnsubscribe()
    {
        return this.totalRemovedByManualUnsubscribe;
    }

    /**
     * Sets the value of the totalRemovedByManualUnsubscribe property.
     * 
     */
    public void setTotalRemovedByManualUnsubscribe(int value)
    {
        this.totalRemovedByManualUnsubscribe = value;
    }

    /**
     * Gets the value of the totalRemovedByAdministrator property.
     * 
     */
    public int getTotalRemovedByAdministrator()
    {
        return this.totalRemovedByAdministrator;
    }

    /**
     * Sets the value of the totalRemovedByAdministrator property.
     * 
     */
    public void setTotalRemovedByAdministrator(int value)
    {
        this.totalRemovedByAdministrator = value;
    }

    /**
     * Gets the value of the totalUnknown property.
     * 
     */
    public int getTotalUnknown()
    {
        return this.totalUnknown;
    }

    /**
     * Sets the value of the totalUnknown property.
     * 
     */
    public void setTotalUnknown(int value)
    {
        this.totalUnknown = value;
    }

    /**
     * Gets the value of the sublistid property.
     * 
     */
    public int getSublistid()
    {
        return this.sublistid;
    }

    /**
     * Sets the value of the sublistid property.
     * 
     */
    public void setSublistid(int value)
    {
        this.sublistid = value;
    }

    /**
     * Gets the value of the sublistName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSublistName()
    {
        return this.sublistName;
    }

    /**
     * Sets the value of the sublistName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSublistName(String value)
    {
        this.sublistName = value;
    }

    /**
     * Gets the value of the listmemberuploadid property.
     * 
     */
    public int getListmemberuploadid()
    {
        return this.listmemberuploadid;
    }

    /**
     * Sets the value of the listmemberuploadid property.
     * 
     */
    public void setListmemberuploadid(int value)
    {
        this.listmemberuploadid = value;
    }

    /**
     * Gets the value of the totalAlreadyInSublist property.
     * 
     */
    public int getTotalAlreadyInSublist()
    {
        return this.totalAlreadyInSublist;
    }

    /**
     * Sets the value of the totalAlreadyInSublist property.
     * 
     */
    public void setTotalAlreadyInSublist(int value)
    {
        this.totalAlreadyInSublist = value;
    }

    /**
     * Gets the value of the invalidemailerrors property.
     * 
     * @return possible object is {@link ArrayOfUploadError }
     * 
     */
    public ArrayOfUploadError getInvalidemailerrors()
    {
        return this.invalidemailerrors;
    }

    /**
     * Sets the value of the invalidemailerrors property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfUploadError }
     * 
     */
    public void setInvalidemailerrors(ArrayOfUploadError value)
    {
        this.invalidemailerrors = value;
    }

    /**
     * Gets the value of the othererrors property.
     * 
     * @return possible object is {@link ArrayOfUploadError }
     * 
     */
    public ArrayOfUploadError getOthererrors()
    {
        return this.othererrors;
    }

    /**
     * Sets the value of the othererrors property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfUploadError }
     * 
     */
    public void setOthererrors(ArrayOfUploadError value)
    {
        this.othererrors = value;
    }

    /**
     * Gets the value of the duplicateemailerrors property.
     * 
     * @return possible object is {@link ArrayOfUploadError }
     * 
     */
    public ArrayOfUploadError getDuplicateemailerrors()
    {
        return this.duplicateemailerrors;
    }

    /**
     * Sets the value of the duplicateemailerrors property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfUploadError }
     * 
     */
    public void setDuplicateemailerrors(ArrayOfUploadError value)
    {
        this.duplicateemailerrors = value;
    }

    /**
     * Gets the value of the alreadyunsubscribederrors property.
     * 
     * @return possible object is {@link ArrayOfUploadError }
     * 
     */
    public ArrayOfUploadError getAlreadyunsubscribederrors()
    {
        return this.alreadyunsubscribederrors;
    }

    /**
     * Sets the value of the alreadyunsubscribederrors property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfUploadError }
     * 
     */
    public void setAlreadyunsubscribederrors(ArrayOfUploadError value)
    {
        this.alreadyunsubscribederrors = value;
    }

    /**
     * Gets the value of the suppressedaddresserrors property.
     * 
     * @return possible object is {@link ArrayOfUploadError }
     * 
     */
    public ArrayOfUploadError getSuppressedaddresserrors()
    {
        return this.suppressedaddresserrors;
    }

    /**
     * Sets the value of the suppressedaddresserrors property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfUploadError }
     * 
     */
    public void setSuppressedaddresserrors(ArrayOfUploadError value)
    {
        this.suppressedaddresserrors = value;
    }

    /**
     * Gets the value of the spamtraperrors property.
     * 
     * @return possible object is {@link ArrayOfUploadError }
     * 
     */
    public ArrayOfUploadError getSpamtraperrors()
    {
        return this.spamtraperrors;
    }

    /**
     * Sets the value of the spamtraperrors property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfUploadError }
     * 
     */
    public void setSpamtraperrors(ArrayOfUploadError value)
    {
        this.spamtraperrors = value;
    }

    /**
     * Gets the value of the questionableaddresswarnings property.
     * 
     * @return possible object is {@link ArrayOfUploadError }
     * 
     */
    public ArrayOfUploadError getQuestionableaddresswarnings()
    {
        return this.questionableaddresswarnings;
    }

    /**
     * Sets the value of the questionableaddresswarnings property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfUploadError }
     * 
     */
    public void setQuestionableaddresswarnings(ArrayOfUploadError value)
    {
        this.questionableaddresswarnings = value;
    }

}
