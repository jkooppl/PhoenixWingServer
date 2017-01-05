package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for CsvUploadDiagnosticInfo complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="CsvUploadDiagnosticInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RowCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Listmemberuploadid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ParseDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlagSpamTrapDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlagQuestionableAddressesDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlagDuplicateRowsDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlagDuplicateEmailsDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlagSuppressedEmailsDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlagUpdateExistingDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlagResubscribesDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlagAlreadyUnsubscribedDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlagInvalidLocationsDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FixStatesWithMissingCountriesDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FlagInsertNewDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CountListmembersToBeDeletedDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ProcessSublistsDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CsvUploadDiagnosticInfo", propOrder = { "rowCount", "listmemberuploadid", "parseDuration",
        "flagSpamTrapDuration", "flagQuestionableAddressesDuration", "flagDuplicateRowsDuration",
        "flagDuplicateEmailsDuration", "flagSuppressedEmailsDuration", "flagUpdateExistingDuration",
        "flagResubscribesDuration", "flagAlreadyUnsubscribedDuration", "flagInvalidLocationsDuration",
        "fixStatesWithMissingCountriesDuration", "flagInsertNewDuration", "countListmembersToBeDeletedDuration",
        "processSublistsDuration" })
public class CsvUploadDiagnosticInfo
{

    @XmlElement(name = "RowCount")
    protected int rowCount;
    @XmlElement(name = "Listmemberuploadid", required = true, type = Integer.class, nillable = true)
    protected Integer listmemberuploadid;
    @XmlElement(name = "ParseDuration")
    protected int parseDuration;
    @XmlElement(name = "FlagSpamTrapDuration")
    protected int flagSpamTrapDuration;
    @XmlElement(name = "FlagQuestionableAddressesDuration")
    protected int flagQuestionableAddressesDuration;
    @XmlElement(name = "FlagDuplicateRowsDuration")
    protected int flagDuplicateRowsDuration;
    @XmlElement(name = "FlagDuplicateEmailsDuration")
    protected int flagDuplicateEmailsDuration;
    @XmlElement(name = "FlagSuppressedEmailsDuration")
    protected int flagSuppressedEmailsDuration;
    @XmlElement(name = "FlagUpdateExistingDuration")
    protected int flagUpdateExistingDuration;
    @XmlElement(name = "FlagResubscribesDuration")
    protected int flagResubscribesDuration;
    @XmlElement(name = "FlagAlreadyUnsubscribedDuration")
    protected int flagAlreadyUnsubscribedDuration;
    @XmlElement(name = "FlagInvalidLocationsDuration")
    protected int flagInvalidLocationsDuration;
    @XmlElement(name = "FixStatesWithMissingCountriesDuration")
    protected int fixStatesWithMissingCountriesDuration;
    @XmlElement(name = "FlagInsertNewDuration")
    protected int flagInsertNewDuration;
    @XmlElement(name = "CountListmembersToBeDeletedDuration")
    protected int countListmembersToBeDeletedDuration;
    @XmlElement(name = "ProcessSublistsDuration")
    protected int processSublistsDuration;

    /**
     * Gets the value of the rowCount property.
     * 
     */
    public int getRowCount()
    {
        return this.rowCount;
    }

    /**
     * Sets the value of the rowCount property.
     * 
     */
    public void setRowCount(int value)
    {
        this.rowCount = value;
    }

    /**
     * Gets the value of the listmemberuploadid property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getListmemberuploadid()
    {
        return this.listmemberuploadid;
    }

    /**
     * Sets the value of the listmemberuploadid property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setListmemberuploadid(Integer value)
    {
        this.listmemberuploadid = value;
    }

    /**
     * Gets the value of the parseDuration property.
     * 
     */
    public int getParseDuration()
    {
        return this.parseDuration;
    }

    /**
     * Sets the value of the parseDuration property.
     * 
     */
    public void setParseDuration(int value)
    {
        this.parseDuration = value;
    }

    /**
     * Gets the value of the flagSpamTrapDuration property.
     * 
     */
    public int getFlagSpamTrapDuration()
    {
        return this.flagSpamTrapDuration;
    }

    /**
     * Sets the value of the flagSpamTrapDuration property.
     * 
     */
    public void setFlagSpamTrapDuration(int value)
    {
        this.flagSpamTrapDuration = value;
    }

    /**
     * Gets the value of the flagQuestionableAddressesDuration property.
     * 
     */
    public int getFlagQuestionableAddressesDuration()
    {
        return this.flagQuestionableAddressesDuration;
    }

    /**
     * Sets the value of the flagQuestionableAddressesDuration property.
     * 
     */
    public void setFlagQuestionableAddressesDuration(int value)
    {
        this.flagQuestionableAddressesDuration = value;
    }

    /**
     * Gets the value of the flagDuplicateRowsDuration property.
     * 
     */
    public int getFlagDuplicateRowsDuration()
    {
        return this.flagDuplicateRowsDuration;
    }

    /**
     * Sets the value of the flagDuplicateRowsDuration property.
     * 
     */
    public void setFlagDuplicateRowsDuration(int value)
    {
        this.flagDuplicateRowsDuration = value;
    }

    /**
     * Gets the value of the flagDuplicateEmailsDuration property.
     * 
     */
    public int getFlagDuplicateEmailsDuration()
    {
        return this.flagDuplicateEmailsDuration;
    }

    /**
     * Sets the value of the flagDuplicateEmailsDuration property.
     * 
     */
    public void setFlagDuplicateEmailsDuration(int value)
    {
        this.flagDuplicateEmailsDuration = value;
    }

    /**
     * Gets the value of the flagSuppressedEmailsDuration property.
     * 
     */
    public int getFlagSuppressedEmailsDuration()
    {
        return this.flagSuppressedEmailsDuration;
    }

    /**
     * Sets the value of the flagSuppressedEmailsDuration property.
     * 
     */
    public void setFlagSuppressedEmailsDuration(int value)
    {
        this.flagSuppressedEmailsDuration = value;
    }

    /**
     * Gets the value of the flagUpdateExistingDuration property.
     * 
     */
    public int getFlagUpdateExistingDuration()
    {
        return this.flagUpdateExistingDuration;
    }

    /**
     * Sets the value of the flagUpdateExistingDuration property.
     * 
     */
    public void setFlagUpdateExistingDuration(int value)
    {
        this.flagUpdateExistingDuration = value;
    }

    /**
     * Gets the value of the flagResubscribesDuration property.
     * 
     */
    public int getFlagResubscribesDuration()
    {
        return this.flagResubscribesDuration;
    }

    /**
     * Sets the value of the flagResubscribesDuration property.
     * 
     */
    public void setFlagResubscribesDuration(int value)
    {
        this.flagResubscribesDuration = value;
    }

    /**
     * Gets the value of the flagAlreadyUnsubscribedDuration property.
     * 
     */
    public int getFlagAlreadyUnsubscribedDuration()
    {
        return this.flagAlreadyUnsubscribedDuration;
    }

    /**
     * Sets the value of the flagAlreadyUnsubscribedDuration property.
     * 
     */
    public void setFlagAlreadyUnsubscribedDuration(int value)
    {
        this.flagAlreadyUnsubscribedDuration = value;
    }

    /**
     * Gets the value of the flagInvalidLocationsDuration property.
     * 
     */
    public int getFlagInvalidLocationsDuration()
    {
        return this.flagInvalidLocationsDuration;
    }

    /**
     * Sets the value of the flagInvalidLocationsDuration property.
     * 
     */
    public void setFlagInvalidLocationsDuration(int value)
    {
        this.flagInvalidLocationsDuration = value;
    }

    /**
     * Gets the value of the fixStatesWithMissingCountriesDuration property.
     * 
     */
    public int getFixStatesWithMissingCountriesDuration()
    {
        return this.fixStatesWithMissingCountriesDuration;
    }

    /**
     * Sets the value of the fixStatesWithMissingCountriesDuration property.
     * 
     */
    public void setFixStatesWithMissingCountriesDuration(int value)
    {
        this.fixStatesWithMissingCountriesDuration = value;
    }

    /**
     * Gets the value of the flagInsertNewDuration property.
     * 
     */
    public int getFlagInsertNewDuration()
    {
        return this.flagInsertNewDuration;
    }

    /**
     * Sets the value of the flagInsertNewDuration property.
     * 
     */
    public void setFlagInsertNewDuration(int value)
    {
        this.flagInsertNewDuration = value;
    }

    /**
     * Gets the value of the countListmembersToBeDeletedDuration property.
     * 
     */
    public int getCountListmembersToBeDeletedDuration()
    {
        return this.countListmembersToBeDeletedDuration;
    }

    /**
     * Sets the value of the countListmembersToBeDeletedDuration property.
     * 
     */
    public void setCountListmembersToBeDeletedDuration(int value)
    {
        this.countListmembersToBeDeletedDuration = value;
    }

    /**
     * Gets the value of the processSublistsDuration property.
     * 
     */
    public int getProcessSublistsDuration()
    {
        return this.processSublistsDuration;
    }

    /**
     * Sets the value of the processSublistsDuration property.
     * 
     */
    public void setProcessSublistsDuration(int value)
    {
        this.processSublistsDuration = value;
    }

}
