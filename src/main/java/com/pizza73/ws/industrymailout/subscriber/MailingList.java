package com.pizza73.ws.industrymailout.subscriber;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for MailingList complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="MailingList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ListName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Active" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Culture" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Culture2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Encoding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WebSite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CanSpamAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RewriteUrls" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MaxBounceRating" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RequireDoubleOptIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SubscriberCanEdit" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SubscriberFormAvailable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="RewriteBaseUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UrlEncodeStrict" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="RewriteWebBug" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SubscribeFormRequiresSublistSelection" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SubscribeFormSublistIsMultiple" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ObfuscateIds" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DaysToArchiveMailouts" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MailingList", propOrder = { "id", "listName", "fromName", "fromEmail", "active", "culture", "culture2",
        "encoding", "webSite", "canSpamAddress", "rewriteUrls", "maxBounceRating", "requireDoubleOptIn",
        "subscriberCanEdit", "subscriberFormAvailable", "rewriteBaseUrl", "urlEncodeStrict", "rewriteWebBug",
        "subscribeFormRequiresSublistSelection", "subscribeFormSublistIsMultiple", "obfuscateIds", "daysToArchiveMailouts" })
public class MailingList
{

    @XmlElement(name = "Id")
    protected int id;
    @XmlElement(name = "ListName")
    protected String listName;
    @XmlElement(name = "FromName")
    protected String fromName;
    @XmlElement(name = "FromEmail")
    protected String fromEmail;
    @XmlElement(name = "Active")
    protected boolean active;
    @XmlElement(name = "Culture")
    protected String culture;
    @XmlElement(name = "Culture2")
    protected String culture2;
    @XmlElement(name = "Encoding")
    protected String encoding;
    @XmlElement(name = "WebSite")
    protected String webSite;
    @XmlElement(name = "CanSpamAddress")
    protected String canSpamAddress;
    @XmlElement(name = "RewriteUrls")
    protected boolean rewriteUrls;
    @XmlElement(name = "MaxBounceRating")
    protected int maxBounceRating;
    @XmlElement(name = "RequireDoubleOptIn")
    protected boolean requireDoubleOptIn;
    @XmlElement(name = "SubscriberCanEdit")
    protected boolean subscriberCanEdit;
    @XmlElement(name = "SubscriberFormAvailable")
    protected boolean subscriberFormAvailable;
    @XmlElement(name = "RewriteBaseUrl")
    protected String rewriteBaseUrl;
    @XmlElement(name = "UrlEncodeStrict")
    protected boolean urlEncodeStrict;
    @XmlElement(name = "RewriteWebBug")
    protected boolean rewriteWebBug;
    @XmlElement(name = "SubscribeFormRequiresSublistSelection")
    protected boolean subscribeFormRequiresSublistSelection;
    @XmlElement(name = "SubscribeFormSublistIsMultiple")
    protected boolean subscribeFormSublistIsMultiple;
    @XmlElement(name = "ObfuscateIds")
    protected boolean obfuscateIds;
    @XmlElement(name = "DaysToArchiveMailouts")
    protected int daysToArchiveMailouts;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value)
    {
        this.id = value;
    }

    /**
     * Gets the value of the listName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getListName()
    {
        return this.listName;
    }

    /**
     * Sets the value of the listName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setListName(String value)
    {
        this.listName = value;
    }

    /**
     * Gets the value of the fromName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFromName()
    {
        return this.fromName;
    }

    /**
     * Sets the value of the fromName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setFromName(String value)
    {
        this.fromName = value;
    }

    /**
     * Gets the value of the fromEmail property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFromEmail()
    {
        return this.fromEmail;
    }

    /**
     * Sets the value of the fromEmail property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setFromEmail(String value)
    {
        this.fromEmail = value;
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
     * Gets the value of the culture property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCulture()
    {
        return this.culture;
    }

    /**
     * Sets the value of the culture property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCulture(String value)
    {
        this.culture = value;
    }

    /**
     * Gets the value of the culture2 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCulture2()
    {
        return this.culture2;
    }

    /**
     * Sets the value of the culture2 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCulture2(String value)
    {
        this.culture2 = value;
    }

    /**
     * Gets the value of the encoding property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEncoding()
    {
        return this.encoding;
    }

    /**
     * Sets the value of the encoding property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEncoding(String value)
    {
        this.encoding = value;
    }

    /**
     * Gets the value of the webSite property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getWebSite()
    {
        return this.webSite;
    }

    /**
     * Sets the value of the webSite property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setWebSite(String value)
    {
        this.webSite = value;
    }

    /**
     * Gets the value of the canSpamAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCanSpamAddress()
    {
        return this.canSpamAddress;
    }

    /**
     * Sets the value of the canSpamAddress property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCanSpamAddress(String value)
    {
        this.canSpamAddress = value;
    }

    /**
     * Gets the value of the rewriteUrls property.
     * 
     */
    public boolean isRewriteUrls()
    {
        return this.rewriteUrls;
    }

    /**
     * Sets the value of the rewriteUrls property.
     * 
     */
    public void setRewriteUrls(boolean value)
    {
        this.rewriteUrls = value;
    }

    /**
     * Gets the value of the maxBounceRating property.
     * 
     */
    public int getMaxBounceRating()
    {
        return this.maxBounceRating;
    }

    /**
     * Sets the value of the maxBounceRating property.
     * 
     */
    public void setMaxBounceRating(int value)
    {
        this.maxBounceRating = value;
    }

    /**
     * Gets the value of the requireDoubleOptIn property.
     * 
     */
    public boolean isRequireDoubleOptIn()
    {
        return this.requireDoubleOptIn;
    }

    /**
     * Sets the value of the requireDoubleOptIn property.
     * 
     */
    public void setRequireDoubleOptIn(boolean value)
    {
        this.requireDoubleOptIn = value;
    }

    /**
     * Gets the value of the subscriberCanEdit property.
     * 
     */
    public boolean isSubscriberCanEdit()
    {
        return this.subscriberCanEdit;
    }

    /**
     * Sets the value of the subscriberCanEdit property.
     * 
     */
    public void setSubscriberCanEdit(boolean value)
    {
        this.subscriberCanEdit = value;
    }

    /**
     * Gets the value of the subscriberFormAvailable property.
     * 
     */
    public boolean isSubscriberFormAvailable()
    {
        return this.subscriberFormAvailable;
    }

    /**
     * Sets the value of the subscriberFormAvailable property.
     * 
     */
    public void setSubscriberFormAvailable(boolean value)
    {
        this.subscriberFormAvailable = value;
    }

    /**
     * Gets the value of the rewriteBaseUrl property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRewriteBaseUrl()
    {
        return this.rewriteBaseUrl;
    }

    /**
     * Sets the value of the rewriteBaseUrl property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRewriteBaseUrl(String value)
    {
        this.rewriteBaseUrl = value;
    }

    /**
     * Gets the value of the urlEncodeStrict property.
     * 
     */
    public boolean isUrlEncodeStrict()
    {
        return this.urlEncodeStrict;
    }

    /**
     * Sets the value of the urlEncodeStrict property.
     * 
     */
    public void setUrlEncodeStrict(boolean value)
    {
        this.urlEncodeStrict = value;
    }

    /**
     * Gets the value of the rewriteWebBug property.
     * 
     */
    public boolean isRewriteWebBug()
    {
        return this.rewriteWebBug;
    }

    /**
     * Sets the value of the rewriteWebBug property.
     * 
     */
    public void setRewriteWebBug(boolean value)
    {
        this.rewriteWebBug = value;
    }

    /**
     * Gets the value of the subscribeFormRequiresSublistSelection property.
     * 
     */
    public boolean isSubscribeFormRequiresSublistSelection()
    {
        return this.subscribeFormRequiresSublistSelection;
    }

    /**
     * Sets the value of the subscribeFormRequiresSublistSelection property.
     * 
     */
    public void setSubscribeFormRequiresSublistSelection(boolean value)
    {
        this.subscribeFormRequiresSublistSelection = value;
    }

    /**
     * Gets the value of the subscribeFormSublistIsMultiple property.
     * 
     */
    public boolean isSubscribeFormSublistIsMultiple()
    {
        return this.subscribeFormSublistIsMultiple;
    }

    /**
     * Sets the value of the subscribeFormSublistIsMultiple property.
     * 
     */
    public void setSubscribeFormSublistIsMultiple(boolean value)
    {
        this.subscribeFormSublistIsMultiple = value;
    }

    /**
     * Gets the value of the obfuscateIds property.
     * 
     */
    public boolean isObfuscateIds()
    {
        return this.obfuscateIds;
    }

    /**
     * Sets the value of the obfuscateIds property.
     * 
     */
    public void setObfuscateIds(boolean value)
    {
        this.obfuscateIds = value;
    }

    /**
     * Gets the value of the daysToArchiveMailouts property.
     * 
     */
    public int getDaysToArchiveMailouts()
    {
        return this.daysToArchiveMailouts;
    }

    /**
     * Sets the value of the daysToArchiveMailouts property.
     * 
     */
    public void setDaysToArchiveMailouts(int value)
    {
        this.daysToArchiveMailouts = value;
    }

}
