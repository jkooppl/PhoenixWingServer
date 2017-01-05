package com.pizza73.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

@Entity
@Table(name = "holiday_hours", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "holiday_sequence", sequenceName = "holiday_hours_id_seq", allocationSize = 1)
public class HolidayHours implements Serializable
{

    private static final long serialVersionUID = 2723548169552211582L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holiday_sequence")
    @Column(name = "id")
    private Integer id = null;

    @DateTimeFormat(pattern = "MM/dd/yyyy", style = "MM")
    @Column(name = "holiday", nullable = false)
    @NotNull
    private Calendar holiday;

    @Column(name = "open")
    @NotNull
    private Integer open;

    @Column(name = "close")
    @NotNull
    private Integer close;

    @Column(name = "strict")
    private boolean strict;

    @OneToMany(mappedBy = "holiday", fetch = FetchType.EAGER)
    private List<HolidayHoursCityException> cityException = new ArrayList<HolidayHoursCityException>();

    public HolidayHours()
    {
    }

    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Calendar getHoliday()
    {
        return holiday;
    }

    public void setHoliday(Calendar holiday)
    {
        this.holiday = holiday;
    }

    public Integer getOpen()
    {
        return open;
    }

    public void setOpen(Integer open)
    {
        this.open = open;
    }

    public Integer getClose()
    {
        return close;
    }

    public void setClose(Integer close)
    {
        this.close = close;
    }

    public boolean isStrict()
    {
        return strict;
    }

    public void setStrict(boolean strict)
    {
        this.strict = strict;
    }

    public List<HolidayHoursCityException> getCityException()
    {
        return cityException;
    }

    public void setCityException(List<HolidayHoursCityException> cityException)
    {
        this.cityException = cityException;
    }

    public void addCityException(HolidayHoursCityException cityException)
    {
        this.cityException.add(cityException);
    }

    public boolean hasCityException(Integer cityId)
    {
        if (null != this.cityException)
        {
            for (HolidayHoursCityException cityException : this.cityException)
            {
                if (cityId.equals(cityException.getCity().getId()))
                {
                    return true;
                }
            }
        }

        return false;
    }
}
