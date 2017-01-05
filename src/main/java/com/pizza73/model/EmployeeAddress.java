package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.pizza73.type.TrimmedString;

/**
 * Address.java TODO comment me TODO: foriegn key reference to municipality,
 * location type, and province.
 * 
 * @author chris 8-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@TypeDefs({ @TypeDef(name = "trim", typeClass = TrimmedString.class) })
@Embeddable
public class EmployeeAddress extends BaseObject implements Serializable
{
    private static final long serialVersionUID = 143918603838411742L;

    @Type(type = "trim")
    @Column(name = "street_address", columnDefinition = "varchar(50)")
    private String streetAddress = "";

    @Type(type = "trim")
    @Column(name = "suite_number", nullable = false, columnDefinition = "varchar(8)")
    private String suiteNumber = "";

    @Type(type = "trim")
    @Column(name = "city")
    private String city;

    @Column(name = "postal_code", nullable = true, columnDefinition = "char(6)")
    private String postalCode;

    @Type(type = "trim")
    @Column(name = "province")
    private String province;

    public String getStreetAddress()
    {
        return this.streetAddress;
    }

    public void setStreetAddress(String street)
    {
        this.streetAddress = street;
    }

    public String getSuiteNumber()
    {
        return this.suiteNumber;
    }

    public void setSuiteNumber(String streetTwo)
    {
        this.suiteNumber = streetTwo;
    }

    public String getCity()
    {
        return this.city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getProvince()
    {
        return this.province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getPostalCode()
    {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        if (postalCode != null)
        {
            postalCode = StringUtils.deleteWhitespace(postalCode);
            this.postalCode = postalCode.toUpperCase();
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof EmployeeAddress))
            return false;

        final EmployeeAddress address1 = (EmployeeAddress) o;

        if (streetAddress != null ? !streetAddress.equals(address1.getStreetAddress()) : address1.getStreetAddress() != null)
            return false;
        if (city != null ? !city.equals(address1.getCity()) : address1.getCity() != null)
            return false;
        if (postalCode != null ? !postalCode.equals(address1.getPostalCode()) : address1.getPostalCode() != null)
            return false;
        if (suiteNumber != null ? !suiteNumber.equals(address1.getSuiteNumber()) : address1.getSuiteNumber() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result;
        result = (streetAddress != null ? streetAddress.hashCode() : 0);
        result = 29 * result + (city != null ? city.hashCode() : 0);
        result = 29 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 29 * result + (suiteNumber != null ? suiteNumber.hashCode() : 0);

        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("streetAddress", this.streetAddress)
            .append("postalCode", this.postalCode).append("city", this.city).toString();
    }
}