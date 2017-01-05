package com.pizza73.model.report;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ReportMailingList.java
 * 
 * @author chris June 6 2010
 * 
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_report_mailing_list", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "report_mailing_list_seq", sequenceName = "iq2_report_mailing_list_id_seq", allocationSize = 1)
public class ReportMailingList implements Serializable
{
    private static final long serialVersionUID = -9022733409724844773L;
    // FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_mailing_list_seq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "report_name")
    private String reportName;

    @Column(name = "email")
    private String email;

    @Column(name = "to_field")
    private boolean toField;

    public ReportMailingList()
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

    public String getReportName()
    {
        return this.reportName;
    }

    public void setReportName(String phoneNumber)
    {
        this.reportName = phoneNumber;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public boolean isToField()
    {
        return this.toField;
    }

    public void setToField(boolean toField)
    {
        this.toField = toField;
    }
}