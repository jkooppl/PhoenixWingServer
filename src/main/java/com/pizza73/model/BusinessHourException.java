/**
 * 
 */
package com.pizza73.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chris
 * 
 */
@Entity
@Table(name = "iq2_business_hour_exception", schema = "public", uniqueConstraints = {})
public class BusinessHourException implements Serializable
{
    // FIELDS
    private static final long serialVersionUID = -7783535870543803576L;

    @Id
    @Column(name = "id")
    private Integer id;

    // date for business hour exception
    @Column(name = "date")
    private Calendar date;

    // open hour. If openHour is zero do nothing, else use the new value for the
    // openHour
    @Column(name = "open_hour")
    private Integer openHour;

    // close hour. If closeHour is zero do nothing, else use the new value for
    // the closeHour
    @Column(name = "close_hour")
    private Integer closeHour;

    // message for change in time if required
    // for example Christmas day let users know we are closed on Christmas day
    @Column(name = "message")
    private String message;

    // whether or not this business day exception is active.
    @Column(name = "active")
    private boolean active;

    public BusinessHourException()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public Calendar getDate()
    {
        return date;
    }

    public Integer getOpenHour()
    {
        return openHour;
    }

    public Integer getCloseHour()
    {
        return closeHour;
    }

    public String getMessage()
    {
        return this.message;
    }

    public boolean isActive()
    {
        return this.active;
    }
}
