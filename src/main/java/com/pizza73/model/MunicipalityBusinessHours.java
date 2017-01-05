package com.pizza73.model;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@Entity
@Table(name = "iq2_municipality_business_hours", schema = "public", uniqueConstraints = {})
@JsonIgnoreProperties({ "id", "displayString", "municipality" })
public class MunicipalityBusinessHours implements Serializable
{
    private static final long serialVersionUID = 6014389703831651638L;
    private static final Format formatter = new SimpleDateFormat("h:mm a");

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "day_of_week")
    private Integer dayOfWeek;

    @Column(name = "open")
    @Temporal(TemporalType.TIME)
    private Date open;

    @Column(name = "close")
    @Temporal(TemporalType.TIME)
    private Date close;

    @ManyToOne
    @JoinColumn(name = "municipality_id")
    private Municipality municipality;

    public Date getClose()
    {
        return this.close;
    }

    public Integer getDayOfWeek()
    {
        return this.dayOfWeek;
    }

    @JsonProperty(value = "hours")
    public String getDisplayString()
    {
        String day = "Sun:    ";
        switch (this.dayOfWeek)
        {
        case Calendar.SUNDAY:
            day = "Sun:    ";
            break;
        case Calendar.MONDAY:
            day = "Mon:   ";
            break;
        case Calendar.TUESDAY:
            day = "Tues:  ";
            break;
        case Calendar.WEDNESDAY:
            day = "Wed:   ";
            break;
        case Calendar.THURSDAY:
            day = "Thurs: ";
            break;
        case Calendar.FRIDAY:
            day = "Fri:    ";
            break;
        default:
            day = "Sat:    ";
        }
        String closeTime = formatter.format(this.close);
        if ("12:00 AM".equals(closeTime))
            closeTime = "Midnight";

        final String openTime = formatter.format(this.open);
        return day += openTime + " - " + closeTime;
    }

    public Integer getId()
    {
        return this.id;
    }

    public Municipality getMunicipality()
    {
        return this.municipality;
    }

    public Date getOpen()
    {
        return this.open;
    }

    public String getTimeOpen()
    {
        return formatter.format(this.open);
    }

    public String getTimeClose()
    {
        return formatter.format(this.close);
    }

    public String getDay()
    {
        String day = "Sun";
        switch (this.dayOfWeek)
        {
        case Calendar.SUNDAY:
            day = "Sun";
            break;
        case Calendar.MONDAY:
            day = "Mon";
            break;
        case Calendar.TUESDAY:
            day = "Tues";
            break;
        case Calendar.WEDNESDAY:
            day = "Wed";
            break;
        case Calendar.THURSDAY:
            day = "Thurs";
            break;
        case Calendar.FRIDAY:
            day = "Fri";
            break;
        default:
            day = "Sat";
        }

        return day;
    }

    public void setClose(final Date endDate)
    {
        this.close = endDate;
    }

    public void setDayOfWeek(final Integer day)
    {
        this.dayOfWeek = day;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public void setMunicipality(final Municipality municipality)
    {
        this.municipality = municipality;
    }

    public void setOpen(final Date openDate)
    {
        this.open = openDate;
    }
}
