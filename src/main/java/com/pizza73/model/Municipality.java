package com.pizza73.model;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Municipality.java TODO comment me
 *
 * @author chris 22-Dec-06
 *
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_municipality", schema = "public", uniqueConstraints = {})
@FilterDef(name = "displayWeb", parameters = { @ParamDef(name = "isDisplayWeb", type = "boolean") })
@Filters({ @Filter(name = "displayWeb", condition = ":isDisplayWeb") })
@JsonIgnoreProperties(value = { "id", "name", "displayWeb", "regionId", "webMenuId", "phone", "displayHint", "province",
        "canadaPostName" })
public class Municipality implements Serializable
{
    // FIELDS
    private static final long serialVersionUID = -5961842363122525501L;

    @Id
    @Column(name = "municipality_id")
    @JsonProperty("cityId")
    private final Integer id = null;

    @Column(name = "display_name", columnDefinition = "char(40)")
    private String name;

    @Column(name = "province_code", columnDefinition = "char(2)")
    private String province;

    @Column(name = "canadapost_name", columnDefinition = "char(40)")
    private String canadaPostName;

    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "web_menu_id")
    private Integer webMenuId;

    @OrderBy
    @Column(name = "display_hint")
    private Integer displayHint;

    @Column(name = "display_web")
    private boolean displayWeb;

    @Column(name = "phone_number")
    private String phone;

    @OneToMany(mappedBy = "municipality", fetch = FetchType.EAGER)
    @OrderBy("dayOfWeek ASC")
    private Set<MunicipalityBusinessHours> municipalityBusinessHours = new HashSet<MunicipalityBusinessHours>();

    @Transient
    private List<Shop> shops = new ArrayList<Shop>();

    /**
    *
    */
    public Municipality()
    {
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Municipality))
            return false;

        final Municipality city = (Municipality) o;

        if (this.id != null ? !this.id.equals(city.getId()) : city.getId() != null)
            return false;

        return true;
    }

    /**
     * @return the canadaPostName
     */
    public String getCanadaPostName()
    {
        return this.canadaPostName;
    }

    /**
     * @return the displayHint
     */
    public Integer getDisplayHint()
    {
        return this.displayHint;
    }

    public String getDisplayName()
    {
        if (StringUtils.isBlank(this.name))
        {
            return "-- select your city --";
        }
        String provinceFull = "Alberta";
        if(this.province.equalsIgnoreCase("SK"))
        {
            provinceFull = "Saskatchewan";
        }
        else if(this.province.equalsIgnoreCase("BC"))
        {
            provinceFull= "British Columbia";
        }
        return this.name + ", " + provinceFull;
    }

    public String getDisplayPhone()
    {
        String displayPhone = "";
        if (StringUtils.isNotBlank(this.phone))
        {
            displayPhone = "(" + this.phone.substring(0, 3) + ") " + this.phone.substring(3, 6) + "-"
                + this.phone.substring(6);
        }

        return displayPhone;
    }

    /**
     * @return the id
     */
    public Integer getId()
    {
        return this.id;
    }

    public Set<MunicipalityBusinessHours> getMunicipalityBusinessHours()
    {
        return this.municipalityBusinessHours;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    public String getPhone()
    {
        return this.phone;
    }

    /**
     * @return the province
     */
    public String getProvince()
    {
        return this.province;
    }

    /**
     * @return the regionId
     */
    public Integer getRegionId()
    {
        return this.regionId;
    }

    public List<Shop> getShops()
    {
        return this.shops;
    }

    public Integer getWebMenuId()
    {
        return this.webMenuId;
    }

    @Override
    public int hashCode()
    {
        int result;
        result = (this.id != null ? this.id.hashCode() : 0);

        return result;
    }

    /**
     * @return the displayWeb
     */
    public boolean isDisplayWeb()
    {
        return this.displayWeb;
    }

    public void setMunicipalityBusinessHours(final Set<MunicipalityBusinessHours> municipalityBusinessHours)
    {
        this.municipalityBusinessHours = municipalityBusinessHours;
    }

    public void setShops(final List<Shop> shops)
    {
        this.shops = shops;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.NO_FIELD_NAMES_STYLE).append("canadaPostName", this.canadaPostName)
            .append("province", this.province).toString();
    }
}