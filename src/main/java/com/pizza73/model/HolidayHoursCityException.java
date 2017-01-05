package com.pizza73.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "holiday_hours_city_exception", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "holiday_hours_city_exception_sequence", sequenceName = "holiday_hours_city_exception_id_seq", allocationSize = 1)
public class HolidayHoursCityException implements Serializable
{

    private static final long serialVersionUID = -1372674038770672325L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holiday_hours_city_exception_sequence")
    @Column(name = "id")
    private Integer id = null;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private Municipality city;

    @ManyToOne(optional = true)
    @JoinColumn(name = "holiday_hours_id")
    private HolidayHours holiday;

    public HolidayHoursCityException()
    {
    }

    public Integer getId()
    {
        return this.id;
    }

    public Municipality getCity()
    {
        return this.city;
    }

    public void setCity(Municipality city)
    {
        this.city = city;
    }

    public HolidayHours getHoliday()
    {
        return holiday;
    }

    public void setHoliday(HolidayHours holiday)
    {
        this.holiday = holiday;
    }
}
