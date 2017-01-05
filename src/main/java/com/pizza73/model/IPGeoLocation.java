package com.pizza73.model;

import java.io.Serializable;

public class IPGeoLocation implements Serializable
{
    private static final long serialVersionUID = 890669040181689110L;

    private String city;
    private String province;
    private String country;
    private String host;
    private String lat;
    private String lng;
    private String countryShort;
    private String blank;

    public IPGeoLocation()
    {
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getLat()
    {
        return lat;
    }

    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public String getLng()
    {
        return lng;
    }

    public void setLng(String lng)
    {
        this.lng = lng;
    }

    public String getCountryShort()
    {
        return countryShort;
    }

    public void setCountryShort(String countryShort)
    {
        this.countryShort = countryShort;
    }

    public String getBlank()
    {
        return blank;
    }

    public void setBlank(String blank)
    {
        this.blank = blank;
    }

    /**
     * 
     * Edmonton|Alberta|Canada|Shaw Communications|53.5500|-113.5000|CA|blank
     */
    public static IPGeoLocation fromPipedString(String pipedString)
    {
        IPGeoLocation location = new IPGeoLocation();

        String[] split = pipedString.split("\\|");
        int splitLength = split.length;
        location.setCity(split[0]);
        if (splitLength > 1)
        {
            location.setProvince(split[1]);
        }
        if (splitLength > 2)
        {
            location.setCountry(split[2]);
        }
        if (splitLength > 3)
        {
            location.setHost(split[3]);
        }
        if (splitLength > 4)
        {
            location.setLat(split[4]);
        }
        if (splitLength > 5)
        {
        }
        if (splitLength > 6)
        {
            location.setCountryShort(split[6]);
        }
        if (splitLength > 7)
        {
            location.setBlank(split[7]);
        }

        return location;
    }
}
