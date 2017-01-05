package com.pizza73.model.contest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Blacklist.java
 * 
 * @author chris Jun3 6 2010
 * 
 * @Copyright Flying Pizza 73
 * 
 *            id integer DEFAULT nextval('iq2_contest_id_seq'::text) NOT NULL,
 *            name character varying(50) NOT NULL, start_date date not null,
 *            end_date date not null, cities character varying(256) default 0,
 */
@Entity
@Table(name = "iq2_contest", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "contest_sequence", sequenceName = "iq2_contest_id_seq", allocationSize = 1)
public class Contest implements Serializable
{
    private static final long serialVersionUID = -4705101960510491767L;
    // FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contest_sequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "cities")
    private String cities;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Calendar startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Calendar endDate;

    public Contest()
    {
    }

    public Contest(String name)
    {
        this.name = name;
    }

    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCities()
    {
        return cities;
    }

    public void setCities(String cities)
    {
        this.cities = cities;
    }

    public Calendar getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Calendar startDate)
    {
        this.startDate = startDate;
    }

    public Calendar getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Calendar endDate)
    {
        this.endDate = endDate;
    }

    public List<Integer> citiesAsList()
    {
        String[] cityArray = this.cities.split(",");
        if (cityArray.length == 1 && cityArray[0].equals("0"))
            return null;

        List<Integer> cityList = new ArrayList<Integer>();
        for (int i = 0; i < cityArray.length; i++)
        {
            cityList.add(Integer.parseInt(cityArray[i]));
        }

        return cityList;
    }
}