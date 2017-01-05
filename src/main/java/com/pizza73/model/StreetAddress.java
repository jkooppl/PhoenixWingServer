package com.pizza73.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

/**
 * StreetAddress.java TODO comment me
 * 
 * @author chris 23-Jan-07
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_street_address", schema = "public", uniqueConstraints = {})
public class StreetAddress
{
    @Id
    private Integer id;

    @Column(name = "municipality_id")
    private Integer cityId;

    @Column(name = "shop_id")
    private Integer shopId;

    @Column(name = "street_address_from_number")
    private String fromAddress;

    @Column(name = "street_address_to_number")
    private String toAddress;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_type_code")
    private String streetTypeCode;

    @Column(name = "street_direction_code")
    private String streetDirectionCode;

    @Column(name = "street_address_sequence_code")
    private String sideOfStreet;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "delivery_charge")
    private Integer deliveryCharge;

    public StreetAddress()
    {
    }

    /**
     * @param searchNumber
     * @param streetName2
     * @param streetType
     * @param streetDirection
     * @param string
     */
    public StreetAddress(String streetNumber, String streetName, String streetType, String streetDirection,
        String sideOfStreet, Integer city)
    {
        int diff = 6 - streetNumber.length();
        for (int i = 0; i < diff; i++)
        {
            streetNumber = "0" + streetNumber;
        }
        this.fromAddress = streetNumber.toUpperCase();
        this.toAddress = streetNumber.toUpperCase();
        this.streetName = streetName.toUpperCase();
        this.streetTypeCode = streetType.toUpperCase();
        this.streetDirectionCode = streetDirection.toUpperCase();
        this.sideOfStreet = sideOfStreet;
        this.cityId = city;
    }

    public StreetAddress(Address address)
    {
        String sNumber = address.getStreetNumber();
        int diff = 6 - sNumber.length();
        for (int i = 0; i < diff; i++)
        {
            sNumber = "0" + sNumber;
        }
        if (StringUtils.isNotBlank(address.getStreetSuffix()))
        {
            sNumber += address.getStreetSuffix();
        }
        this.fromAddress = sNumber;
        this.toAddress = sNumber;
        this.streetName = address.getStreetName().toUpperCase();
        this.streetTypeCode = address.getStreetType().toUpperCase();
        this.streetDirectionCode = address.getStreetDirection().toUpperCase();
        this.cityId = address.getCity().getId();

        this.sideOfStreet = "1";
        String streetNumber = address.getStreetNumber();
        try
        {
            Integer number = Integer.valueOf(streetNumber);
            if (number % 2 == 0)
            {
                this.sideOfStreet = "2";
            }
        }
        catch (NumberFormatException e)
        {
            this.sideOfStreet = "0";
        }
    }

    public Integer getId()
    {
        return this.id;
    }

    /**
     * @return the cityId
     */
    public Integer getCityId()
    {
        return this.cityId;
    }

    /**
     * @return the fromAddress
     */
    public String getFromAddress()
    {
        return this.fromAddress;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode()
    {
        return this.postalCode;
    }

    /**
     * @return the shopId
     */
    public Integer getShopId()
    {
        return this.shopId;
    }

    /**
     * @return the sideOfStreet
     */
    public String getSideOfStreet()
    {
        return this.sideOfStreet;
    }

    /**
     * @return the streetDirectionCode
     */
    public String getStreetDirectionCode()
    {
        return this.streetDirectionCode;
    }

    /**
     * @return the streetName
     */
    public String getStreetName()
    {
        return this.streetName;
    }

    /**
     * @return the streetTypeCode
     */
    public String getStreetTypeCode()
    {
        return this.streetTypeCode;
    }

    /**
     * @return the toAddress
     */
    public String getToAddress()
    {
        return this.toAddress;
    }

    public Integer getDeliveryCharge()
    {
        return this.deliveryCharge;
    }

    /**
     * @param cityId
     *            the cityId to set
     */
    public void setCityId(Integer cityId)
    {
        this.cityId = cityId;
    }

    /**
     * @param fromAddress
     *            the fromAddress to set
     */
    public void setFromAddress(String fromAddress)
    {
        this.fromAddress = fromAddress;
    }

    /**
     * @param postalCode
     *            the postalCode to set
     */
    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    /**
     * @param shopId
     *            the shopId to set
     */
    public void setShopId(Integer shopId)
    {
        this.shopId = shopId;
    }

    /**
     * @param sideOfStreet
     *            the sideOfStreet to set
     */
    public void setSideOfStreet(String sideOfStreet)
    {
        this.sideOfStreet = sideOfStreet;
    }

    /**
     * @param streetDirectionCode
     *            the streetDirectionCode to set
     */
    public void setStreetDirectionCode(String streetDirectionCode)
    {
        this.streetDirectionCode = streetDirectionCode;
    }

    /**
     * @param streetName
     *            the streetName to set
     */
    public void setStreetName(String streetName)
    {
        this.streetName = streetName;
    }

    /**
     * @param streetTypeCode
     *            the streetTypeCode to set
     */
    public void setStreetTypeCode(String streetTypeCode)
    {
        this.streetTypeCode = streetTypeCode;
    }

    /**
     * @param toAddress
     *            the toAddress to set
     */
    public void setToAddress(String toAddress)
    {
        this.toAddress = toAddress;
    }
}
