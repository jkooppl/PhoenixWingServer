package com.pizza73.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "iq2_payroll", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "payroll_sequence", sequenceName = "iq2_payroll_id_seq", allocationSize = 1)
public class Payroll implements Serializable
{
    // FIELDS
    private static final long serialVersionUID = -1405109014560375175L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payroll_sequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "adp_id")
    private String payrollId = "";

    @Column(name = "period")
    private Integer payrollPeriod;

    @Column(name = "year")
    private Integer payrollYear;

    @Column(name = "week_one_mon")
    private Double weekOneMon = Double.valueOf("0");
    @Column(name = "week_one_tues")
    private Double weekOneTues = Double.valueOf("0");
    @Column(name = "week_one_wed")
    private Double weekOneWed = Double.valueOf("0");
    @Column(name = "week_one_thurs")
    private Double weekOneThurs = Double.valueOf("0");
    @Column(name = "week_one_fri")
    private Double weekOneFri = Double.valueOf("0");
    @Column(name = "week_one_sat")
    private Double weekOneSat = Double.valueOf("0");
    @Column(name = "week_one_sun")
    private Double weekOneSun = Double.valueOf("0");

    @Column(name = "week_two_mon")
    private Double weekTwoMon = Double.valueOf("0");
    @Column(name = "week_two_tues")
    private Double weekTwoTues = Double.valueOf("0");
    @Column(name = "week_two_wed")
    private Double weekTwoWed = Double.valueOf("0");
    @Column(name = "week_two_thurs")
    private Double weekTwoThurs = Double.valueOf("0");
    @Column(name = "week_two_fri")
    private Double weekTwoFri = Double.valueOf("0");
    @Column(name = "week_two_sat")
    private Double weekTwoSat = Double.valueOf("0");
    @Column(name = "week_two_sun")
    private Double weekTwoSun = Double.valueOf("0");

    @Column(name = "total_hours_week_one")
    private Double totalHoursWeekOne = Double.valueOf("0");

    @Column(name = "overtime_hours_week_one")
    private Double overtimeHoursWeekOne = Double.valueOf("0");

    @Column(name = "week_one_stat")
    private Double weekOneStat = Double.valueOf("0");

    @Column(name = "total_hours_week_two")
    private Double totalHoursWeekTwo = Double.valueOf("0");

    @Column(name = "overtime_hours_week_two")
    private Double overtimeHoursWeekTwo = Double.valueOf("0");

    @Column(name = "week_two_stat")
    private Double weekTwoStat = Double.valueOf("0");

    @Column(name = "submitted")
    private boolean submitted = false;

    @Column(name = "pay_period_start_date")
    @Temporal(TemporalType.DATE)
    private Calendar payPeriodStartDate;

    @Column(name = "payroll_wage")
    private BigDecimal payrollWage = BigDecimal.valueOf(0, 2);

    @Column(name = "week_one_sun_start")
    private String weekOneSunStart = "";

    @Column(name = "week_one_sun_end")
    private String weekOneSunEnd = "";

    @Column(name = "week_one_mon_start")
    private String weekOneMonStart = "";

    @Column(name = "week_one_mon_end")
    private String weekOneMonEnd = "";

    @Column(name = "week_one_tues_start")
    private String weekOneTuesStart = "";

    @Column(name = "week_one_tues_end")
    private String weekOneTuesEnd = "";

    @Column(name = "week_one_wed_start")
    private String weekOneWedStart = "";

    @Column(name = "week_one_wed_End")
    private String weekOneWedEnd = "";

    @Column(name = "week_one_thurs_start")
    private String weekOneThursStart = "";

    @Column(name = "week_one_thurs_end")
    private String weekOneThursEnd = "";

    @Column(name = "week_one_fri_start")
    private String weekOneFriStart = "";

    @Column(name = "week_one_fri_end")
    private String weekOneFriEnd = "";

    @Column(name = "week_one_sat_start")
    private String weekOneSatStart = "";

    @Column(name = "week_one_sat_end")
    private String weekOneSatEnd = "";

    @Column(name = "week_two_sun_start")
    private String weekTwoSunStart = "";

    @Column(name = "week_two_sun_end")
    private String weekTwoSunEnd = "";

    @Column(name = "week_two_mon_start")
    private String weekTwoMonStart = "";

    @Column(name = "week_two_mon_end")
    private String weekTwoMonEnd = "";

    @Column(name = "week_two_tues_start")
    private String weekTwoTuesStart = "";

    @Column(name = "week_two_tues_end")
    private String weekTwoTuesEnd = "";

    @Column(name = "week_two_wed_start")
    private String weekTwoWedStart = "";

    @Column(name = "week_two_wed_end")
    private String weekTwoWedEnd = "";

    @Column(name = "week_two_thurs_start")
    private String weekTwoThursStart = "";

    @Column(name = "week_two_thurs_end")
    private String weekTwoThursEnd = "";

    @Column(name = "week_two_fri_start")
    private String weekTwoFriStart = "";

    @Column(name = "week_two_fri_end")
    private String weekTwoFriEnd = "";

    @Column(name = "week_two_sat_start")
    private String weekTwoSatStart = "";

    @Column(name = "week_two_sat_end")
    private String weekTwoSatEnd = "";

    @Column(name = "employee_id")
    private Integer employeeId = null;

    public Integer getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId)
    {
        this.employeeId = employeeId;
    }

    public String getWeekOneSunStart()
    {
        return weekOneSunStart;
    }

    public void setWeekOneSunStart(String weekOneSunStart)
    {
        this.weekOneSunStart = weekOneSunStart;
    }

    public String getWeekOneSunEnd()
    {
        return weekOneSunEnd;
    }

    public void setWeekOneSunEnd(String weekOneSunEnd)
    {
        this.weekOneSunEnd = weekOneSunEnd;
    }

    public String getWeekOneMonStart()
    {
        return weekOneMonStart;
    }

    public void setWeekOneMonStart(String weekOneMonStart)
    {
        this.weekOneMonStart = weekOneMonStart;
    }

    public String getWeekOneMonEnd()
    {
        return weekOneMonEnd;
    }

    public void setWeekOneMonEnd(String weekOneMonEnd)
    {
        this.weekOneMonEnd = weekOneMonEnd;
    }

    public String getWeekOneTuesStart()
    {
        return weekOneTuesStart;
    }

    public void setWeekOneTuesStart(String weekOneTuesStart)
    {
        this.weekOneTuesStart = weekOneTuesStart;
    }

    public String getWeekOneTuesEnd()
    {
        return weekOneTuesEnd;
    }

    public void setWeekOneTuesEnd(String weekOneTuesEnd)
    {
        this.weekOneTuesEnd = weekOneTuesEnd;
    }

    public String getWeekOneWedStart()
    {
        return weekOneWedStart;
    }

    public void setWeekOneWedStart(String weekOneWedStart)
    {
        this.weekOneWedStart = weekOneWedStart;
    }

    public String getWeekOneWedEnd()
    {
        return weekOneWedEnd;
    }

    public void setWeekOneWedEnd(String weekOneWedEnd)
    {
        this.weekOneWedEnd = weekOneWedEnd;
    }

    public String getWeekOneThursStart()
    {
        return weekOneThursStart;
    }

    public void setWeekOneThursStart(String weekOneThursStart)
    {
        this.weekOneThursStart = weekOneThursStart;
    }

    public String getWeekOneThursEnd()
    {
        return weekOneThursEnd;
    }

    public void setWeekOneThursEnd(String weekOneThursEnd)
    {
        this.weekOneThursEnd = weekOneThursEnd;
    }

    public String getWeekOneFriStart()
    {
        return weekOneFriStart;
    }

    public void setWeekOneFriStart(String weekOneFriStart)
    {
        this.weekOneFriStart = weekOneFriStart;
    }

    public String getWeekOneFriEnd()
    {
        return weekOneFriEnd;
    }

    public void setWeekOneFriEnd(String weekOneFriEnd)
    {
        this.weekOneFriEnd = weekOneFriEnd;
    }

    public String getWeekOneSatStart()
    {
        return weekOneSatStart;
    }

    public void setWeekOneSatStart(String weekOneSatStart)
    {
        this.weekOneSatStart = weekOneSatStart;
    }

    public String getWeekOneSatEnd()
    {
        return weekOneSatEnd;
    }

    public void setWeekOneSatEnd(String weekOneSatEnd)
    {
        this.weekOneSatEnd = weekOneSatEnd;
    }

    public String getWeekTwoSunStart()
    {
        return weekTwoSunStart;
    }

    public void setWeekTwoSunStart(String weekTwoSunStart)
    {
        this.weekTwoSunStart = weekTwoSunStart;
    }

    public String getWeekTwoSunEnd()
    {
        return weekTwoSunEnd;
    }

    public void setWeekTwoSunEnd(String weekTwoSunEnd)
    {
        this.weekTwoSunEnd = weekTwoSunEnd;
    }

    public String getWeekTwoMonStart()
    {
        return weekTwoMonStart;
    }

    public void setWeekTwoMonStart(String weekTwoMonStart)
    {
        this.weekTwoMonStart = weekTwoMonStart;
    }

    public String getWeekTwoMonEnd()
    {
        return weekTwoMonEnd;
    }

    public void setWeekTwoMonEnd(String weekTwoMonEnd)
    {
        this.weekTwoMonEnd = weekTwoMonEnd;
    }

    public String getWeekTwoTuesStart()
    {
        return weekTwoTuesStart;
    }

    public void setWeekTwoTuesStart(String weekTwoTuesStart)
    {
        this.weekTwoTuesStart = weekTwoTuesStart;
    }

    public String getWeekTwoTuesEnd()
    {
        return weekTwoTuesEnd;
    }

    public void setWeekTwoTuesEnd(String weekTwoTuesEnd)
    {
        this.weekTwoTuesEnd = weekTwoTuesEnd;
    }

    public String getWeekTwoWedStart()
    {
        return weekTwoWedStart;
    }

    public void setWeekTwoWedStart(String weekTwoWedStart)
    {
        this.weekTwoWedStart = weekTwoWedStart;
    }

    public String getWeekTwoWedEnd()
    {
        return weekTwoWedEnd;
    }

    public void setWeekTwoWedEnd(String weekTwoWedEnd)
    {
        this.weekTwoWedEnd = weekTwoWedEnd;
    }

    public String getWeekTwoThursStart()
    {
        return weekTwoThursStart;
    }

    public void setWeekTwoThursStart(String weekTwoThursStart)
    {
        this.weekTwoThursStart = weekTwoThursStart;
    }

    public String getWeekTwoThursEnd()
    {
        return weekTwoThursEnd;
    }

    public void setWeekTwoThursEnd(String weekTwoThursEnd)
    {
        this.weekTwoThursEnd = weekTwoThursEnd;
    }

    public String getWeekTwoFriStart()
    {
        return weekTwoFriStart;
    }

    public void setWeekTwoFriStart(String weekTwoFriStart)
    {
        this.weekTwoFriStart = weekTwoFriStart;
    }

    public String getWeekTwoFriEnd()
    {
        return weekTwoFriEnd;
    }

    public void setWeekTwoFriEnd(String weekTwoFriEnd)
    {
        this.weekTwoFriEnd = weekTwoFriEnd;
    }

    public String getWeekTwoSatStart()
    {
        return weekTwoSatStart;
    }

    public void setWeekTwoSatStart(String weekTwoSatStart)
    {
        this.weekTwoSatStart = weekTwoSatStart;
    }

    public String getWeekTwoSatEnd()
    {
        return weekTwoSatEnd;
    }

    public void setWeekTwoSatEnd(String weekTwoSatEnd)
    {
        this.weekTwoSatEnd = weekTwoSatEnd;
    }

    public BigDecimal getPayrollWage()
    {
        return payrollWage;
    }

    public void setPayrollWage(BigDecimal payrollWage)
    {
        this.payrollWage = payrollWage;
    }

    public Calendar getPayPeriodStartDate()
    {
        return payPeriodStartDate;
    }

    public void setPayPeriodStartDate(Calendar payPeriodStartDate)
    {
        this.payPeriodStartDate = payPeriodStartDate;
    }

    public Payroll()
    {
    }

    public String getPayrollId()
    {
        return payrollId;
    }

    public void setPayrollId(String payrollId)
    {
        this.payrollId = payrollId;
    }

    public Integer getPayrollPeriod()
    {
        return payrollPeriod;
    }

    public void setPayrollPeriod(Integer payrollPeriod)
    {
        this.payrollPeriod = payrollPeriod;
    }

    public Integer getPayrollYear()
    {
        return payrollYear;
    }

    public void setPayrollYear(Integer payrollYear)
    {
        this.payrollYear = payrollYear;
    }

    public Double getWeekOneMon()
    {
        return this.weekOneMon;
    }

    public void setWeekOneMon(Double hours)
    {
        this.weekOneMon = hours;
    }

    public Double getWeekOneTues()
    {
        return this.weekOneTues;
    }

    public void setWeekOneTues(Double hours)
    {
        this.weekOneTues = hours;
    }

    public Double getWeekOneWed()
    {
        return this.weekOneWed;
    }

    public void setWeekOneWed(Double hours)
    {
        this.weekOneWed = hours;
    }

    public Double getWeekOneThurs()
    {
        return this.weekOneThurs;
    }

    public void setWeekOneThurs(Double hours)
    {
        this.weekOneThurs = hours;
    }

    public Double getWeekOneFri()
    {
        return this.weekOneFri;
    }

    public void setWeekOneFri(Double hours)
    {
        this.weekOneFri = hours;
    }

    public Double getWeekOneSat()
    {
        return this.weekOneSat;
    }

    public void setWeekOneSat(Double hours)
    {
        this.weekOneSat = hours;
    }

    public Double getWeekOneSun()
    {
        return this.weekOneSun;
    }

    public void setWeekOneSun(Double hours)
    {
        this.weekOneSun = hours;
    }

    //

    public Double getWeekTwoMon()
    {
        return this.weekTwoMon;
    }

    public void setWeekTwoMon(Double hours)
    {
        this.weekTwoMon = hours;
    }

    public Double getWeekTwoTues()
    {
        return this.weekTwoTues;
    }

    public void setWeekTwoTues(Double hours)
    {
        this.weekTwoTues = hours;
    }

    public Double getWeekTwoWed()
    {
        return this.weekTwoWed;
    }

    public void setWeekTwoWed(Double hours)
    {
        this.weekTwoWed = hours;
    }

    public Double getWeekTwoThurs()
    {
        return this.weekTwoThurs;
    }

    public void setWeekTwoThurs(Double hours)
    {
        this.weekTwoThurs = hours;
    }

    public Double getWeekTwoFri()
    {
        return this.weekTwoFri;
    }

    public void setWeekTwoFri(Double hours)
    {
        this.weekTwoFri = hours;
    }

    public Double getWeekTwoSat()
    {
        return this.weekTwoSat;
    }

    public void setWeekTwoSat(Double hours)
    {
        this.weekTwoSat = hours;
    }

    public Double getWeekTwoSun()
    {
        return this.weekTwoSun;
    }

    public void setWeekTwoSun(Double hours)
    {
        this.weekTwoSun = hours;
    }

    public Double getTotalHoursWeekOne()
    {
        return totalHoursWeekOne;
    }

    public void setTotalHoursWeekOne(Double totalHours)
    {
        this.totalHoursWeekOne = totalHours;
    }

    public Double getOvertimeHoursWeekOne()
    {
        return overtimeHoursWeekOne;
    }

    public void setOvertimeHoursWeekOne(Double overtimeHours)
    {
        this.overtimeHoursWeekOne = overtimeHours;
    }

    public Double getWeekOneStat()
    {
        return weekOneStat;
    }

    public void setWeekOneStat(Double stat)
    {
        this.weekOneStat = stat;
    }

    public Double getTotalHoursWeekTwo()
    {
        return totalHoursWeekTwo;
    }

    public void setTotalHoursWeekTwo(Double totalHours)
    {
        this.totalHoursWeekTwo = totalHours;
    }

    public Double getOvertimeHoursWeekTwo()
    {
        return overtimeHoursWeekTwo;
    }

    public void setOvertimeHoursWeekTwo(Double overtimeHours)
    {
        this.overtimeHoursWeekTwo = overtimeHours;
    }

    public Double getWeekTwoStat()
    {
        return weekTwoStat;
    }

    public void setWeekTwoStat(Double stat)
    {
        this.weekTwoStat = stat;
    }

    public Integer getId()
    {
        return id;
    }

    public void setSubmitted(boolean submitted)
    {
        this.submitted = submitted;
    }

    public boolean isSubmitted()
    {
        return this.submitted;
    }

    public Double getTotalRegHours()
    {
        Double totalRegHours = Double.valueOf(0);
        if (this.totalHoursWeekOne != null)
        {
            totalRegHours += this.totalHoursWeekOne;
        }
        if (this.totalHoursWeekTwo != null)
        {
            totalRegHours += this.totalHoursWeekTwo;
        }
        return totalRegHours;
    }

    public Double getTotalOTHours()
    {
        Double totalOTHours = Double.valueOf(0);
        if (this.overtimeHoursWeekOne != null)
        {
            totalOTHours += this.overtimeHoursWeekOne;
        }
        if (this.overtimeHoursWeekTwo != null)
        {
            totalOTHours += this.overtimeHoursWeekTwo;
        }
        return totalOTHours;
    }

    public Double getTotalStatHours()
    {
        Double totalStatHours = Double.valueOf(0);
        if (this.weekOneStat != null)
        {
            totalStatHours += this.weekOneStat;
        }
        if (this.weekTwoStat != null)
        {
            totalStatHours += this.weekTwoStat;
        }
        return totalStatHours;
    }
}