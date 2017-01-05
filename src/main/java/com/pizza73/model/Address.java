package com.pizza73.model;

import com.pizza73.model.enums.CityEnum;
import com.pizza73.model.enums.StreetDirectionCode;
import com.pizza73.model.enums.StreetType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Address.java TODO comment me TODO: foriegn key reference to municipality,
 * location type, and province.
 *
 * @author chris 8-Sep-06
 *
 * @Copyright Flying Pizza 73
 */

@Embeddable
@JsonIgnoreProperties(value = { "displayAddress", "streetDirection", "streetName", "streetNumber", "streetSuffix",
        "streetType", "unmappedDisplayAddress", "province" })
public class Address extends BaseObject implements Serializable
{
    private static final long serialVersionUID = 3617859655330969141L;
    public static final Integer EDMONTON_ID = Integer.valueOf(1);

    @Type(type = "trim")
    @Column(name = "street_address", columnDefinition = "varchar(50)")
    private String streetAddress = "";

    @Type(type = "trim")
    @Column(name = "suite_number", nullable = false, columnDefinition = "varchar(8)")
    private String suiteNumber = "";

    @Type(type = "trim")
    @Column(name = "address_comment")
    private String addressComment = "";

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_code")
    private Location locationType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "municipality_id")
    private Municipality city;

    @Type(type = "trim")
    @Column(name = "postal_code", nullable = true, columnDefinition = "char(6)")
    private String postalCode;

    @Type(type = "trim")
    @Column(name = "street_number")
    private String streetNumber = "";

    @Type(type = "trim")
    @Column(name = "street_name")
    private String streetName = "";

    @Type(type = "trim")
    @Column(name = "street_type")
    private String streetType = "";

    @Type(type = "trim")
    @Column(name = "street_direction")
    private String streetDirection = "";

    @Type(type = "trim")
    @Column(name = "street_suffix")
    private String streetSuffix = "";

    public String getStreetAddress()
    {
        return this.streetAddress;
    }

    public void setStreetAddress(String street)
    {
        this.streetAddress = street.replaceAll("  ", " ");
    }

    public String getSuiteNumber()
    {
        return this.suiteNumber;
    }

    public void setSuiteNumber(String streetTwo)
    {
        this.suiteNumber = streetTwo;
    }

    public String getAddressComment()
    {
        return this.addressComment;
    }

    public void setAddressComment(String comment)
    {
        if (addressComment != null)
        {
            comment = comment.replace("\\r\\n", " ");
            comment = StringUtils.trimToEmpty(comment);
        }
        this.addressComment = comment;
    }

    public Municipality getCity()
    {
        return this.city;
    }

    public void setCity(Municipality city)
    {
        this.city = city;
    }

    public String getProvince()
    {
        return this.city.getProvince();
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

    public Location getLocationType()
    {
        return this.locationType;
    }

    public void setLocationType(Location type)
    {
        this.locationType = type;
    }

    /**
     * @return the streetDirection
     */
    public String getStreetDirection()
    {
        return this.streetDirection;
    }

    /**
     * @param streetDirection
     *            the streetDirection to set
     */
    public void setStreetDirection(String streetDirection)
    {
        this.streetDirection = streetDirection;
    }

    /**
     * @return the streetName
     */
    public String getStreetName()
    {
        return this.streetName;
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
     * @return the streetNumber
     */
    public String getStreetNumber()
    {
        return this.streetNumber;
    }

    /**
     * @param streetNumber
     *            the streetNumber to set
     */
    public void setStreetNumber(String streetNumber)
    {
        String number = StringUtils.trimToEmpty(streetNumber);
        number = StringUtils.deleteWhitespace(number);
        if (number.length() > 8)
        {
            number = number.substring(0, 8);
        }
        this.streetNumber = number;
    }

    /**
     * @return the streetType
     */
    public String getStreetType()
    {
        return this.streetType;
    }

    /**
     * @param streetType
     *            the streetType to set
     */
    public void setStreetType(String streetType)
    {
        this.streetType = streetType;
    }

    public String getStreetSuffix()
    {
        return this.streetSuffix;
    }

    public void setStreetSuffix(String suffix)
    {
        this.streetSuffix = suffix;
    }

    public void populateStreetAddress()
    {
        this.streetAddress = StringUtils.capitalize(this.getStreetNumber().toLowerCase())
            + this.getStreetSuffix().toUpperCase() + " " + StringUtils.capitalize(this.getStreetName().toLowerCase()) + " "
            + this.getStreetType() + " " + this.getStreetDirection();
        // REPLACE any double spaces with single spaces
        this.streetAddress = this.streetAddress.replaceAll("  ", " ");
    }

    public String getDisplayAddress()
    {
        String displayAddress = this.streetAddress;
        if (StringUtils.isNotBlank(this.suiteNumber))
        {
            displayAddress = "#" + this.suiteNumber + " " + displayAddress;
        }

        return displayAddress;
    }

    public String getUnmappedDisplayAddress()
    {
        String displayAddress = this.streetAddress;
        if (StringUtils.isNotBlank(this.suiteNumber))
        {
            displayAddress = "#" + this.suiteNumber + " " + displayAddress;
        }

        return displayAddress;
    }

    /**
     * Parse the address into Canada post form. When the address is in Canada
     * post form the canada post data can be used to map the address to a store
     * for delivery.
     *
     * @param codeMap
     */
    public void parseAddress(Map<String, String> codeMap)
    {
        String streetNumber = "";
        String streetName = "";
        String streetSuffix = "";
        String streetType = "";
        String streetDirection = "";
        String suiteNumber = "";

        String tempAddress = this.getStreetAddress().toUpperCase();
        // replace unwanted characters sequences.
        tempAddress = tempAddress.replace("S. E.", "SE");
        tempAddress = tempAddress.replace("N. E.", "NE");
        tempAddress = tempAddress.replace("S. W.", "SW");
        tempAddress = tempAddress.replace("N. W.", "NW");
        tempAddress = tempAddress.replace(".E.", "E");
        tempAddress = tempAddress.replace(".W.", "W");
        tempAddress = tempAddress.replace("N.", "N");
        tempAddress = tempAddress.replace("S.", "S");
        tempAddress = tempAddress.replace('(', ' ');
        tempAddress = tempAddress.replace(')', ' ');
        tempAddress = tempAddress.replace('/', ' ');
        tempAddress = tempAddress.replace('\\', ' ');
        tempAddress = tempAddress.replace('-', ' ');
        tempAddress = tempAddress.replace(". ", " ");
        tempAddress = tempAddress.replace(".", " ");
        tempAddress = tempAddress.replace(',', ' ');
        tempAddress = tempAddress.replace(" ND", " ");
        tempAddress = tempAddress.replace(" TH ", " ");
        tempAddress = tempAddress.replace(" NORTH EAST", " NE ");
        tempAddress = tempAddress.replace(" SOUTH EAST", " SE ");
        tempAddress = tempAddress.replace(" NORTH WEST", " NW ");
        tempAddress = tempAddress.replace(" SOUTH WEST", " SW ");
        tempAddress = tempAddress.replace(" RD RD ", " RD ");

        for (int i = 0; i < 10; i++)
        {
            String replacee = i + " ";
            tempAddress = tempAddress.replace(i + "ND", replacee);
            tempAddress = tempAddress.replace(i + "TH", replacee);
            tempAddress = tempAddress.replace(i + "RD", replacee);
            tempAddress = tempAddress.replace(i + "STREET", replacee + "ST ");
            tempAddress = tempAddress.replace(i + "ST", replacee + "ST ");
            tempAddress = tempAddress.replace(i + "AVE", replacee + "AVE ");
            tempAddress = tempAddress.replace(i + "AV", replacee + "AVE ");
        }
        tempAddress = tempAddress.replace(" ST ST", " ST ");
        tempAddress = tempAddress.replace(" ST  ST", " ST ");
        tempAddress = tempAddress.replace(" RD RD ", " RD ");
        tempAddress = tempAddress.replace(" RD  RD ", " RD ");

        // this.setStreetAddress(tempAddress);
        // remove unwanted spaces.
        StringTokenizer st = new StringTokenizer(tempAddress);
        StringBuffer buf = new StringBuffer();
        while (st.hasMoreTokens())
        {
            buf.append(' ').append(st.nextToken());
        }
        tempAddress = buf.toString().trim();

        // split string
        String[] results = tempAddress.split(" ");

        // assume first result is street number.
        streetNumber = results[0];
        // unless it contains a #
        int streetNumberIndex = 1;
        if (streetNumber.indexOf('#') != -1)
        {
            suiteNumber = results[0];
            streetNumber = results[1];
            streetNumberIndex = 2;
        }

        // find street suffix if there is one.
        if (!StringUtils.isNumeric(streetNumber) && !StringUtils.isAlphaSpace(streetNumber))
        {
            char[] charArray = streetNumber.toCharArray();
            String number = "";
            int count = 0;
            for (count = 0; count < charArray.length; count++)
            {
                char c = charArray[count];
                if (CharUtils.isAsciiNumeric(c))
                {
                    number += c;
                }
                else
                {
                    break;
                }

            }
            streetSuffix = charArray[count] + "";
            streetNumber = number;
        }

        boolean directionFound = false;
        boolean typeFound = false;

        String[] streetTypes = StreetType.typeValues();
        String[] streetDirections = StreetDirectionCode.codeValues();
        String[] streetDirectionsTwo = { "NORTH", "SOUTH", "WEST", "EAST" };

        // use this to determine the address name (ie 91 or KINGSWOOD)
        int count = results.length;
        for (int i = results.length; i > streetNumberIndex; i--)
        {
            String result = "";
            result = results[i - 1].toUpperCase();
            if (!directionFound && (ArrayUtils.contains(streetDirections, result) || ArrayUtils.contains(streetDirectionsTwo, result)))
            {
                // Street Direction found
                directionFound = true;
                count--;
                if (result.equals("NORTH"))
                {
                    streetDirection = "N";
                }
                else if (result.equals("SOUTH"))
                {
                    streetDirection = "S";
                }
                else if (result.equals("EAST"))
                {
                    streetDirection = "E";
                }
                else if (result.equals("WEST"))
                {
                    streetDirection = "W";
                }
                else
                {
                    streetDirection = result;
                }

            }
            else if (!typeFound && (ArrayUtils.contains(streetTypes, result) || codeMap.containsKey(result)))
            {
                String typeCodeValue = result;
                if (codeMap.containsKey(result))
                {
                    typeCodeValue = codeMap.get(result);
                }
                // street type found.
                typeFound = true;
                streetType = typeCodeValue;
                count--;
            }
        }

        for (int i = streetNumberIndex; i < count; i++)
        {
            streetName = streetName + results[i].toUpperCase() + " ";
        }

        Integer cityId = this.getCity().getId();
        if (CityEnum.EDMONTON.isEqualToCityId(cityId) && StringUtils.isBlank(streetDirection))
        {
            this.setStreetDirection("NW");
        }

        if (streetNumber.length() > 8)
        {
            streetNumber = streetNumber.substring(0, 8);
        }
        if (streetType.length() > 10)
        {
            streetType = streetType.substring(0, 10);
        }
        if (streetName.length() > 30)
        {
            streetName = streetName.substring(0, 30);
        }

        this.setStreetNumber(streetNumber);
        this.setStreetName(streetName);
        this.setStreetType(streetType);
        this.setStreetDirection(streetDirection);
        this.setStreetSuffix(streetSuffix);
        if (StringUtils.isBlank(this.getSuiteNumber()))
        {
            this.setSuiteNumber(suiteNumber);
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Address))
            return false;

        final Address address1 = (Address) o;

        if (streetAddress != null ? !streetAddress.equals(address1.getStreetAddress()) : address1.getStreetAddress() != null)
            return false;
        if (city != null ? !city.equals(address1.getCity()) : address1.getCity() != null)
            return false;
        if (postalCode != null ? !postalCode.equals(address1.getPostalCode()) : address1.getPostalCode() != null)
            return false;
        if (locationType != null ? !locationType.equals(address1.getLocationType()) : address1.getLocationType() != null)
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
        result = 29 * result + (locationType != null ? locationType.hashCode() : 0);
        result = 29 * result + (suiteNumber != null ? suiteNumber.hashCode() : 0);

        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("streetAddress", this.streetAddress)
            .append("postalCode", this.postalCode).append("city", this.city).toString();
    }

    public boolean valid(Map<String, String> codeMap)
    {
        boolean valid = false;
        if (StringUtils.isNotBlank(this.streetAddress))
        {
            Set<String> keySet = codeMap.keySet();

            Iterator<String> iter = keySet.iterator();
            while (iter.hasNext())
            {
                String streetType = iter.next();
                if (StringUtils.contains(streetAddress.toUpperCase(), streetType))
                {
                    valid = true;
                    break;
                }
            }
        }
        return valid;
    }

    public void appendStreetDirection(String direction)
    {
        if (StringUtils.isNotEmpty(direction))
        {
            this.streetAddress = this.streetAddress + " " + direction;
        }
        else
        {
            this.streetAddress = this.streetAddress + " NW";
        }
    }
}
