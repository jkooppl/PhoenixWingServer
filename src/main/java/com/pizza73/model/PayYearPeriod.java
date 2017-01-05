package com.pizza73.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "iq2_pay_period", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "pay_period_sequence", sequenceName = "iq2_pay_period_id", allocationSize = 1)
public class PayYearPeriod implements Serializable
{
    private static final long serialVersionUID = -8641122917706888448L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pay_period_sequence")
    private Integer id;
    @Column(name = "year")
    private Integer year;
    @Column(name = "num_of_pay_period_year")
    private Integer numOfPayPeriodYear;

    @Column(name = "payroll_year_start_date")
    @Temporal(TemporalType.DATE)
    private Calendar payrollYearStartDate;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public Integer getNumOfPayPeriodYear()
    {
        return numOfPayPeriodYear;
    }

    public void setNumOfPayPeriodYear(Integer numOfPayPeriodYear)
    {
        this.numOfPayPeriodYear = numOfPayPeriodYear;
    }

    public Calendar getPayrollYearStartDate()
    {
        return payrollYearStartDate;
    }

    public void setPayrollYearStartDate(Calendar payrollYearStartDate)
    {
        this.payrollYearStartDate = payrollYearStartDate;
    }

}
