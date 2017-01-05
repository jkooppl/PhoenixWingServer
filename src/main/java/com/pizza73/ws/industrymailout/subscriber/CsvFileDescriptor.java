package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for CsvFileDescriptor complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="CsvFileDescriptor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HasHeader" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="EmailColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="GivenNameColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FamilyNameColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TextOnlyColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SexColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TitleColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CompanyColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MiddleNameColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PhoneNumberColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CellPhoneColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TollFreeColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AddressColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Address2Column" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Address3Column" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CityColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="StateColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CountryColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PostalCodeColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SpecialField1Column" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SpecialField2Column" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SpecialField3Column" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ExternalIdColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DeactivateIfNotInUpload" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ResubscribeUploaded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ResubscribeBounced" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ResubscribeListmanagerRemoved" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CsvFileDescriptor", propOrder = { "hasHeader", "emailColumn", "givenNameColumn", "familyNameColumn",
        "textOnlyColumn", "sexColumn", "titleColumn", "companyColumn", "middleNameColumn", "phoneNumberColumn",
        "cellPhoneColumn", "tollFreeColumn", "addressColumn", "address2Column", "address3Column", "cityColumn",
        "stateColumn", "countryColumn", "postalCodeColumn", "specialField1Column", "specialField2Column",
        "specialField3Column", "externalIdColumn", "deactivateIfNotInUpload", "resubscribeUploaded", "resubscribeBounced",
        "resubscribeListmanagerRemoved" })
public class CsvFileDescriptor
{

    @XmlElement(name = "HasHeader")
    protected boolean hasHeader;
    @XmlElement(name = "EmailColumn")
    protected int emailColumn;
    @XmlElement(name = "GivenNameColumn")
    protected int givenNameColumn;
    @XmlElement(name = "FamilyNameColumn")
    protected int familyNameColumn;
    @XmlElement(name = "TextOnlyColumn")
    protected int textOnlyColumn;
    @XmlElement(name = "SexColumn")
    protected int sexColumn;
    @XmlElement(name = "TitleColumn")
    protected int titleColumn;
    @XmlElement(name = "CompanyColumn")
    protected int companyColumn;
    @XmlElement(name = "MiddleNameColumn")
    protected int middleNameColumn;
    @XmlElement(name = "PhoneNumberColumn")
    protected int phoneNumberColumn;
    @XmlElement(name = "CellPhoneColumn")
    protected int cellPhoneColumn;
    @XmlElement(name = "TollFreeColumn")
    protected int tollFreeColumn;
    @XmlElement(name = "AddressColumn")
    protected int addressColumn;
    @XmlElement(name = "Address2Column")
    protected int address2Column;
    @XmlElement(name = "Address3Column")
    protected int address3Column;
    @XmlElement(name = "CityColumn")
    protected int cityColumn;
    @XmlElement(name = "StateColumn")
    protected int stateColumn;
    @XmlElement(name = "CountryColumn")
    protected int countryColumn;
    @XmlElement(name = "PostalCodeColumn")
    protected int postalCodeColumn;
    @XmlElement(name = "SpecialField1Column")
    protected int specialField1Column;
    @XmlElement(name = "SpecialField2Column")
    protected int specialField2Column;
    @XmlElement(name = "SpecialField3Column")
    protected int specialField3Column;
    @XmlElement(name = "ExternalIdColumn")
    protected int externalIdColumn;
    @XmlElement(name = "DeactivateIfNotInUpload")
    protected boolean deactivateIfNotInUpload;
    @XmlElement(name = "ResubscribeUploaded")
    protected boolean resubscribeUploaded;
    @XmlElement(name = "ResubscribeBounced")
    protected boolean resubscribeBounced;
    @XmlElement(name = "ResubscribeListmanagerRemoved")
    protected boolean resubscribeListmanagerRemoved;

    /**
     * Gets the value of the hasHeader property.
     * 
     */
    public boolean isHasHeader()
    {
        return this.hasHeader;
    }

    /**
     * Sets the value of the hasHeader property.
     * 
     */
    public void setHasHeader(boolean value)
    {
        this.hasHeader = value;
    }

    /**
     * Gets the value of the emailColumn property.
     * 
     */
    public int getEmailColumn()
    {
        return this.emailColumn;
    }

    /**
     * Sets the value of the emailColumn property.
     * 
     */
    public void setEmailColumn(int value)
    {
        this.emailColumn = value;
    }

    /**
     * Gets the value of the givenNameColumn property.
     * 
     */
    public int getGivenNameColumn()
    {
        return this.givenNameColumn;
    }

    /**
     * Sets the value of the givenNameColumn property.
     * 
     */
    public void setGivenNameColumn(int value)
    {
        this.givenNameColumn = value;
    }

    /**
     * Gets the value of the familyNameColumn property.
     * 
     */
    public int getFamilyNameColumn()
    {
        return this.familyNameColumn;
    }

    /**
     * Sets the value of the familyNameColumn property.
     * 
     */
    public void setFamilyNameColumn(int value)
    {
        this.familyNameColumn = value;
    }

    /**
     * Gets the value of the textOnlyColumn property.
     * 
     */
    public int getTextOnlyColumn()
    {
        return this.textOnlyColumn;
    }

    /**
     * Sets the value of the textOnlyColumn property.
     * 
     */
    public void setTextOnlyColumn(int value)
    {
        this.textOnlyColumn = value;
    }

    /**
     * Gets the value of the sexColumn property.
     * 
     */
    public int getSexColumn()
    {
        return this.sexColumn;
    }

    /**
     * Sets the value of the sexColumn property.
     * 
     */
    public void setSexColumn(int value)
    {
        this.sexColumn = value;
    }

    /**
     * Gets the value of the titleColumn property.
     * 
     */
    public int getTitleColumn()
    {
        return this.titleColumn;
    }

    /**
     * Sets the value of the titleColumn property.
     * 
     */
    public void setTitleColumn(int value)
    {
        this.titleColumn = value;
    }

    /**
     * Gets the value of the companyColumn property.
     * 
     */
    public int getCompanyColumn()
    {
        return this.companyColumn;
    }

    /**
     * Sets the value of the companyColumn property.
     * 
     */
    public void setCompanyColumn(int value)
    {
        this.companyColumn = value;
    }

    /**
     * Gets the value of the middleNameColumn property.
     * 
     */
    public int getMiddleNameColumn()
    {
        return this.middleNameColumn;
    }

    /**
     * Sets the value of the middleNameColumn property.
     * 
     */
    public void setMiddleNameColumn(int value)
    {
        this.middleNameColumn = value;
    }

    /**
     * Gets the value of the phoneNumberColumn property.
     * 
     */
    public int getPhoneNumberColumn()
    {
        return this.phoneNumberColumn;
    }

    /**
     * Sets the value of the phoneNumberColumn property.
     * 
     */
    public void setPhoneNumberColumn(int value)
    {
        this.phoneNumberColumn = value;
    }

    /**
     * Gets the value of the cellPhoneColumn property.
     * 
     */
    public int getCellPhoneColumn()
    {
        return this.cellPhoneColumn;
    }

    /**
     * Sets the value of the cellPhoneColumn property.
     * 
     */
    public void setCellPhoneColumn(int value)
    {
        this.cellPhoneColumn = value;
    }

    /**
     * Gets the value of the tollFreeColumn property.
     * 
     */
    public int getTollFreeColumn()
    {
        return this.tollFreeColumn;
    }

    /**
     * Sets the value of the tollFreeColumn property.
     * 
     */
    public void setTollFreeColumn(int value)
    {
        this.tollFreeColumn = value;
    }

    /**
     * Gets the value of the addressColumn property.
     * 
     */
    public int getAddressColumn()
    {
        return this.addressColumn;
    }

    /**
     * Sets the value of the addressColumn property.
     * 
     */
    public void setAddressColumn(int value)
    {
        this.addressColumn = value;
    }

    /**
     * Gets the value of the address2Column property.
     * 
     */
    public int getAddress2Column()
    {
        return this.address2Column;
    }

    /**
     * Sets the value of the address2Column property.
     * 
     */
    public void setAddress2Column(int value)
    {
        this.address2Column = value;
    }

    /**
     * Gets the value of the address3Column property.
     * 
     */
    public int getAddress3Column()
    {
        return this.address3Column;
    }

    /**
     * Sets the value of the address3Column property.
     * 
     */
    public void setAddress3Column(int value)
    {
        this.address3Column = value;
    }

    /**
     * Gets the value of the cityColumn property.
     * 
     */
    public int getCityColumn()
    {
        return this.cityColumn;
    }

    /**
     * Sets the value of the cityColumn property.
     * 
     */
    public void setCityColumn(int value)
    {
        this.cityColumn = value;
    }

    /**
     * Gets the value of the stateColumn property.
     * 
     */
    public int getStateColumn()
    {
        return this.stateColumn;
    }

    /**
     * Sets the value of the stateColumn property.
     * 
     */
    public void setStateColumn(int value)
    {
        this.stateColumn = value;
    }

    /**
     * Gets the value of the countryColumn property.
     * 
     */
    public int getCountryColumn()
    {
        return this.countryColumn;
    }

    /**
     * Sets the value of the countryColumn property.
     * 
     */
    public void setCountryColumn(int value)
    {
        this.countryColumn = value;
    }

    /**
     * Gets the value of the postalCodeColumn property.
     * 
     */
    public int getPostalCodeColumn()
    {
        return this.postalCodeColumn;
    }

    /**
     * Sets the value of the postalCodeColumn property.
     * 
     */
    public void setPostalCodeColumn(int value)
    {
        this.postalCodeColumn = value;
    }

    /**
     * Gets the value of the specialField1Column property.
     * 
     */
    public int getSpecialField1Column()
    {
        return this.specialField1Column;
    }

    /**
     * Sets the value of the specialField1Column property.
     * 
     */
    public void setSpecialField1Column(int value)
    {
        this.specialField1Column = value;
    }

    /**
     * Gets the value of the specialField2Column property.
     * 
     */
    public int getSpecialField2Column()
    {
        return this.specialField2Column;
    }

    /**
     * Sets the value of the specialField2Column property.
     * 
     */
    public void setSpecialField2Column(int value)
    {
        this.specialField2Column = value;
    }

    /**
     * Gets the value of the specialField3Column property.
     * 
     */
    public int getSpecialField3Column()
    {
        return this.specialField3Column;
    }

    /**
     * Sets the value of the specialField3Column property.
     * 
     */
    public void setSpecialField3Column(int value)
    {
        this.specialField3Column = value;
    }

    /**
     * Gets the value of the externalIdColumn property.
     * 
     */
    public int getExternalIdColumn()
    {
        return this.externalIdColumn;
    }

    /**
     * Sets the value of the externalIdColumn property.
     * 
     */
    public void setExternalIdColumn(int value)
    {
        this.externalIdColumn = value;
    }

    /**
     * Gets the value of the deactivateIfNotInUpload property.
     * 
     */
    public boolean isDeactivateIfNotInUpload()
    {
        return this.deactivateIfNotInUpload;
    }

    /**
     * Sets the value of the deactivateIfNotInUpload property.
     * 
     */
    public void setDeactivateIfNotInUpload(boolean value)
    {
        this.deactivateIfNotInUpload = value;
    }

    /**
     * Gets the value of the resubscribeUploaded property.
     * 
     */
    public boolean isResubscribeUploaded()
    {
        return this.resubscribeUploaded;
    }

    /**
     * Sets the value of the resubscribeUploaded property.
     * 
     */
    public void setResubscribeUploaded(boolean value)
    {
        this.resubscribeUploaded = value;
    }

    /**
     * Gets the value of the resubscribeBounced property.
     * 
     */
    public boolean isResubscribeBounced()
    {
        return this.resubscribeBounced;
    }

    /**
     * Sets the value of the resubscribeBounced property.
     * 
     */
    public void setResubscribeBounced(boolean value)
    {
        this.resubscribeBounced = value;
    }

    /**
     * Gets the value of the resubscribeListmanagerRemoved property.
     * 
     */
    public boolean isResubscribeListmanagerRemoved()
    {
        return this.resubscribeListmanagerRemoved;
    }

    /**
     * Sets the value of the resubscribeListmanagerRemoved property.
     * 
     */
    public void setResubscribeListmanagerRemoved(boolean value)
    {
        this.resubscribeListmanagerRemoved = value;
    }

}
