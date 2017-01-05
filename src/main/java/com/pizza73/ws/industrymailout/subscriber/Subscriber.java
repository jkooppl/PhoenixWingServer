package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Subscriber complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Subscriber">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GivenName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FamilyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Externalid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="City" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Stateid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Countryid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SpecialField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpecialField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpecialField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IPAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TextOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Active" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DateCreated" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DateModified" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RemovalReason" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Subscriber", propOrder = { "email", "givenName", "familyName", "companyName", "phoneNumber", "title",
        "postalCode", "externalid", "city", "address", "address2", "address3", "state", "country", "stateid", "countryid",
        "specialField1", "specialField2", "specialField3", "ipAddress", "textOnly", "active", "dateCreated", "dateModified",
        "removalReason" })
public class Subscriber
{

    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "GivenName")
    protected String givenName;
    @XmlElement(name = "FamilyName")
    protected String familyName;
    @XmlElement(name = "CompanyName")
    protected String companyName;
    @XmlElement(name = "PhoneNumber")
    protected String phoneNumber;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "PostalCode")
    protected String postalCode;
    @XmlElement(name = "Externalid")
    protected String externalid;
    @XmlElement(name = "City")
    protected String city;
    @XmlElement(name = "Address")
    protected String address;
    @XmlElement(name = "Address2")
    protected String address2;
    @XmlElement(name = "Address3")
    protected String address3;
    @XmlElement(name = "State")
    protected String state;
    @XmlElement(name = "Country")
    protected String country;
    @XmlElement(name = "Stateid")
    protected int stateid;
    @XmlElement(name = "Countryid")
    protected int countryid;
    @XmlElement(name = "SpecialField1")
    protected String specialField1;
    @XmlElement(name = "SpecialField2")
    protected String specialField2;
    @XmlElement(name = "SpecialField3")
    protected String specialField3;
    @XmlElement(name = "IPAddress")
    protected String ipAddress;
    @XmlElement(name = "TextOnly")
    protected boolean textOnly;
    @XmlElement(name = "Active")
    protected boolean active;
    @XmlElement(name = "DateCreated", required = true, nillable = true)
    protected String dateCreated;
    @XmlElement(name = "DateModified", required = true, nillable = true)
    protected String dateModified;
    @XmlElement(name = "RemovalReason")
    protected int removalReason;

    /**
     * Gets the value of the email property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEmail(String value)
    {
        this.email = value;
    }

    /**
     * Gets the value of the givenName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getGivenName()
    {
        return this.givenName;
    }

    /**
     * Sets the value of the givenName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setGivenName(String value)
    {
        this.givenName = value;
    }

    /**
     * Gets the value of the familyName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFamilyName()
    {
        return this.familyName;
    }

    /**
     * Sets the value of the familyName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setFamilyName(String value)
    {
        this.familyName = value;
    }

    /**
     * Gets the value of the companyName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCompanyName()
    {
        return this.companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCompanyName(String value)
    {
        this.companyName = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setPhoneNumber(String value)
    {
        this.phoneNumber = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTitle()
    {
        return this.title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTitle(String value)
    {
        this.title = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPostalCode()
    {
        return this.postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setPostalCode(String value)
    {
        this.postalCode = value;
    }

    /**
     * Gets the value of the externalid property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExternalid()
    {
        return this.externalid;
    }

    /**
     * Sets the value of the externalid property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setExternalid(String value)
    {
        this.externalid = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCity()
    {
        return this.city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCity(String value)
    {
        this.city = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAddress()
    {
        return this.address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAddress(String value)
    {
        this.address = value;
    }

    /**
     * Gets the value of the address2 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAddress2()
    {
        return this.address2;
    }

    /**
     * Sets the value of the address2 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAddress2(String value)
    {
        this.address2 = value;
    }

    /**
     * Gets the value of the address3 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAddress3()
    {
        return this.address3;
    }

    /**
     * Sets the value of the address3 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAddress3(String value)
    {
        this.address3 = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getState()
    {
        return this.state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setState(String value)
    {
        this.state = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCountry()
    {
        return this.country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCountry(String value)
    {
        this.country = value;
    }

    /**
     * Gets the value of the stateid property.
     * 
     */
    public int getStateid()
    {
        return this.stateid;
    }

    /**
     * Sets the value of the stateid property.
     * 
     */
    public void setStateid(int value)
    {
        this.stateid = value;
    }

    /**
     * Gets the value of the countryid property.
     * 
     */
    public int getCountryid()
    {
        return this.countryid;
    }

    /**
     * Sets the value of the countryid property.
     * 
     */
    public void setCountryid(int value)
    {
        this.countryid = value;
    }

    /**
     * Gets the value of the specialField1 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSpecialField1()
    {
        return this.specialField1;
    }

    /**
     * Sets the value of the specialField1 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSpecialField1(String value)
    {
        this.specialField1 = value;
    }

    /**
     * Gets the value of the specialField2 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSpecialField2()
    {
        return this.specialField2;
    }

    /**
     * Sets the value of the specialField2 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSpecialField2(String value)
    {
        this.specialField2 = value;
    }

    /**
     * Gets the value of the specialField3 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSpecialField3()
    {
        return this.specialField3;
    }

    /**
     * Sets the value of the specialField3 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSpecialField3(String value)
    {
        this.specialField3 = value;
    }

    /**
     * Gets the value of the ipAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIPAddress()
    {
        return this.ipAddress;
    }

    /**
     * Sets the value of the ipAddress property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIPAddress(String value)
    {
        this.ipAddress = value;
    }

    /**
     * Gets the value of the textOnly property.
     * 
     */
    public boolean isTextOnly()
    {
        return this.textOnly;
    }

    /**
     * Sets the value of the textOnly property.
     * 
     */
    public void setTextOnly(boolean value)
    {
        this.textOnly = value;
    }

    /**
     * Gets the value of the active property.
     * 
     */
    public boolean isActive()
    {
        return this.active;
    }

    /**
     * Sets the value of the active property.
     * 
     */
    public void setActive(boolean value)
    {
        this.active = value;
    }

    /**
     * Gets the value of the dateCreated property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDateCreated()
    {
        return this.dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDateCreated(String value)
    {
        this.dateCreated = value;
    }

    /**
     * Gets the value of the dateModified property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDateModified()
    {
        return this.dateModified;
    }

    /**
     * Sets the value of the dateModified property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDateModified(String value)
    {
        this.dateModified = value;
    }

    /**
     * Gets the value of the removalReason property.
     * 
     */
    public int getRemovalReason()
    {
        return this.removalReason;
    }

    /**
     * Sets the value of the removalReason property.
     * 
     */
    public void setRemovalReason(int value)
    {
        this.removalReason = value;
    }

}
