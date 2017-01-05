package com.pizza73.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Employee.java TODO comment me
 * 
 * @author chris 6-Nov-06
 * 
 * @Copyright Flying Pizza 73
 */

@Entity
@Table(name = "iq2_employee", schema = "public", uniqueConstraints = {})
@SequenceGenerator(name = "employee_sequence", sequenceName = "iq2_employee_id_seq", allocationSize = 1)
public class Employee extends User implements UserDetails, Comparable<Employee>, Serializable
{
    // FIELDS
    private static final long serialVersionUID = -6029938970181325899L;
    public static final double WAGE_THRESHOLD = 100.0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    @Column(name = "employee_id")
    private Integer id = null;

    // Base Employee information along with name
    @Type(type = "trim")
    @Column(name = "last_name")
    private String lastName = "";

    @Column(name = "middle_initial")
    private String middleInitial = "";

    @Column(name = "pin")
    private String email = "";

    @Column(name = "ot_agreement")
    private boolean signedOTAgreement = false;

    @Column(name = "compressed_work_week")
    private boolean compressedWorkWeek = false;

    @Column(name = "compressed_hours")
    private Double compressedHours = Double.valueOf(0);

    // !!-- gender is in use --!!//
    @Column(name = "sex")
    private char sex = ' ';

    @Type(type = "trim")
    @Column(name = "status")
    private String status = "";

    @Column(name = "password")
    private String password = "";

    // !!-- sin number is in use -- !!//
    @Column(name = "sin")
    private String sin = "";

    @Column(name = "primary_wage")
    private Double primaryWage = Double.valueOf(0.0);

    @Column(name = "salary")
    private boolean salariedEmployee = false;

    @Column(name = "direct_deposit")
    private boolean directDeposit = false;

    @Transient
    private Integer bankInstitutionCode = Integer.valueOf(0);

    @Transient
    private Integer bankTransitCode = Integer.valueOf(0);

    @Transient
    private Integer bankAccountNumber = Integer.valueOf(0);

    @Column(name = "tax_exemption")
    private char taxExemption = 'X';

    // !!-- date of birth is in use -- !!//
    @Temporal(TemporalType.DATE)
    @Column(name = "dob")
    private Date birthDay;

    // !!-- hire date (commence date ) is in use -- !!//
    @Temporal(TemporalType.DATE)
    @Column(name = "hire_date")
    private Date hireDate;

    @Embedded
    private EmployeeAddress address = new EmployeeAddress();

    @Transient
    private String confirmPassword = "";

    @Transient
    private String oldPassword = "";

    // UserDetails for security.
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "iq2_employee_role", joinColumns = { @JoinColumn(name = "employee_id") }, inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<Role>();

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean accountLocked = false;

    @Column(nullable = false)
    private boolean credentialsExpired = false;

    @Column(nullable = false)
    private boolean accountExpired = false;

    @Column(name = "shop_id")
    private Integer shopId = Integer.valueOf(0);

    @Column(name = "adp_id")
    private String payrollId;

    @Transient
    private Payroll currentPayroll;

    @Column(name = "type_of_employment")
    private String typeOfEmployment = "";

    @Column(name = "marital_status")
    private String maritalStatus = "";

    @Column(name = "position")
    private String position = "";

    @Column(name = "is_new_employee")
    private boolean newEmployee = true;

    /**
     * Default Constructor
     */
    public Employee()
    {
    }

    public Employee(String fName, String lName, String payrollId, Integer username, Integer shopId, double wage,
        boolean salary)
    {
        this(fName, lName, payrollId, shopId, wage, salary);
        this.email = username + "";
        this.password = this.email;
    }

    public Employee(String fName, String lName, String payrollId, Integer shopId, double wage, boolean salary)
    {
        this.setName(fName);
        this.lastName = lName;
        this.payrollId = payrollId;
        this.password = this.email;
        this.shopId = shopId;
        this.salariedEmployee = salary;
        this.primaryWage = Double.valueOf(wage);
    }

    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setMiddleInitial(String middleInitial)
    {
        this.middleInitial = middleInitial;
    }

    public void setSin(String sin)
    {
        this.sin = sin;
    }

    public void setPrimaryWage(Double primaryWage)
    {
        this.primaryWage = primaryWage;
    }

    public void setSignedOTAgreement(boolean signedOTAgreement)
    {
        this.signedOTAgreement = signedOTAgreement;
    }

    public void setCompressedWorkWeek(boolean signed)
    {
        this.compressedWorkWeek = signed;
    }

    public void setSalariedEmployee(boolean salariedEmployee)
    {
        this.salariedEmployee = salariedEmployee;
    }

    public void setDirectDeposit(boolean directDeposit)
    {
        this.directDeposit = directDeposit;
    }

    public void setBankInstitutionCode(Integer bankInstitutionCode)
    {
        this.bankInstitutionCode = bankInstitutionCode;
    }

    public void setBankTransitCode(Integer bankTransitCode)
    {
        this.bankTransitCode = bankTransitCode;
    }

    public void setBankAccountNumber(Integer bankAccountNumber)
    {
        this.bankAccountNumber = bankAccountNumber;
    }

    public void setSex(char sex)
    {
        this.sex = sex;
    }

    public void setTaxExemption(char taxExemption)
    {
        this.taxExemption = taxExemption;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setEmail(String email)
    {
        this.email = StringUtils.trimToEmpty(email);
        this.password = email;
    }

    public Date getBirthDay()
    {
        return birthDay;
    }

    public void setBirthDay(Date birthDay)
    {
        this.birthDay = birthDay;
    }

    public Date getHireDate()
    {
        return hireDate;
    }

    public void setHireDate(Date hireDate)
    {
        this.hireDate = hireDate;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getMiddleInitial()
    {
        return middleInitial;
    }

    public String getSin()
    {
        return sin;
    }

    public Double getPrimaryWage()
    {
        return primaryWage;
    }

    public boolean isSignedOTAgreement()
    {
        return signedOTAgreement;
    }

    public boolean isCompressedWorkWeek()
    {
        return this.compressedWorkWeek;
    }

    public boolean isSalariedEmployee()
    {
        return salariedEmployee;
    }

    public boolean isDirectDeposit()
    {
        return directDeposit;
    }

    public Integer getBankInstitutionCode()
    {
        return bankInstitutionCode;
    }

    public Integer getBankTransitCode()
    {
        return bankTransitCode;
    }

    public Integer getBankAccountNumber()
    {
        return bankAccountNumber;
    }

    public char getSex()
    {
        return sex;
    }

    public char getTaxExemption()
    {
        return taxExemption;
    }

    public String getStatus()
    {
        return status;
    }

    public String getUsername()
    {
        return StringUtils.trimToEmpty(this.password);
    }

    public String getPassword()
    {
        return StringUtils.trimToEmpty(this.password);
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password)
    {
        if (password != null)
        {
            password = password.trim();
        }
        this.password = password;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword()
    {
        return this.confirmPassword;
    }

    /**
     * @param confirmPassword
     *            the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword)
    {
        if (confirmPassword != null)
        {
            confirmPassword = confirmPassword.trim();
        }
        this.confirmPassword = confirmPassword;
    }

    /**
     * @return the confirmPassword
     */
    public String getOldPassword()
    {
        return this.oldPassword;
    }

    /**
     * @param oldPassword
     *            the confirmPassword to set
     */
    public void setOldPassword(String oldPassword)
    {
        if (oldPassword != null)
        {
            oldPassword = oldPassword.trim();
        }
        this.oldPassword = oldPassword;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled()
    {
        return this.enabled;
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    /**
     * @return the password
     */

    /**
     * @return the roles
     */
    public Set<Role> getRoles()
    {
        return this.roles;
    }

    /**
     * @param roles
     *            the roles to set
     */
    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.userdetails.UserDetails#getAuthorities()
     */
    public Collection<GrantedAuthority> getAuthorities()
    {
        Collection<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        if (!getRoles().isEmpty())
        {
            for (Role role : getRoles())
            {
                list.add(new GrantedAuthorityImpl(role.getName()));
            }
            return list;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.userdetails.UserDetails#isAccountNonExpired
     * ()
     */
    public boolean isAccountNonExpired()
    {
        return !accountExpired;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
     */
    public boolean isAccountNonLocked()
    {
        return !accountLocked;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired
     * ()
     */
    public boolean isCredentialsNonExpired()
    {
        return !credentialsExpired;
    }

    @Override
    public boolean equals(Object o)
    {
        if (super.equals(o))
        {
            return false;
        }
        if (this == o)
            return true;
        if (!(o instanceof Employee))
            return false;

        final Employee oc = (Employee) o;

        if (password != null ? !password.equals(oc.getPassword()) : oc.getPassword() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 29 * result + (password != null ? password.hashCode() : 0);

        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", this.getName()).toString();
    }

    public String getEmail()
    {
        return email;
    }

    public Integer getShopId()
    {
        return shopId;
    }

    public void setShopId(Integer shopId)
    {
        this.shopId = shopId;
    }

    public EmployeeAddress getAddress()
    {
        return this.address;
    }

    public void setAddress(EmployeeAddress address)
    {
        this.address = address;
    }

    public void setPayrollId(String payrollId)
    {
        this.payrollId = payrollId;
    }

    public String getPayrollId()
    {
        return this.payrollId;
    }

    public void setCurrentPayroll(Payroll payroll)
    {
        this.currentPayroll = payroll;
    }

    public Payroll getCurrentPayroll()
    {
        return this.currentPayroll;
    }

    public Double getCompressedHours()
    {
        return this.compressedHours;
    }

    public void setCompressedHours(Double hours)
    {
        this.compressedHours = hours;
    }

    public int compareTo(Employee emp)
    {

        int lastNameEqual = this.getLastName().compareTo(emp.getLastName());
        if (lastNameEqual == 0)
        {
            return this.getName().compareTo(emp.getName());
        }

        return lastNameEqual;
    }

    public String getTypeOfEmployment()
    {
        return typeOfEmployment;
    }

    public void setTypeOfEmployment(String typeOfEmployment)
    {
        this.typeOfEmployment = typeOfEmployment;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getMaritalStatus()
    {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus)
    {
        this.maritalStatus = maritalStatus;
    }

    public boolean isNewEmployee()
    {
        return newEmployee;
    }

    public void setNewEmployee(boolean newEmployee)
    {
        this.newEmployee = newEmployee;
    }

    public List<String> update(Employee excelEmp)
    {
        List<String> results = new ArrayList<String>();
        String excelPayrollId = excelEmp.getPayrollId();
        if (!this.enabled)
        {
            String result = "INFO: employee " + excelPayrollId + " RE-HIRED.";
            this.setEnabled(true);
            results.add(result);
        }
        if (!this.shopId.equals(excelEmp.getShopId()))
        {
            String result = "WARN: shop changed for employee " + excelPayrollId + " original shop:" + this.shopId
                + " new shop:" + excelEmp.getShopId() + ".  Double Check it!";
            results.add(result);
            this.setShopId(excelEmp.getShopId());
        }
        if (!this.getName().equals(excelEmp.getName()))
        {
            String result = "INFO: first name changed for employee " + excelPayrollId + " from " + this.getName() + " to "
                + excelEmp.getName();
            this.setName(excelEmp.getName());
            results.add(result);
        }
        if (!this.lastName.equals(excelEmp.getLastName()))
        {
            String result = "INFO: last name changed for employee " + excelPayrollId + " from " + this.lastName + " to "
                + excelEmp.getLastName();
            this.setLastName(excelEmp.getLastName());
            results.add(result);
        }
        if (!this.getAddress().getStreetAddress().equals(excelEmp.getAddress().getStreetAddress()))
        {
            String result = "INFO: streeAdress changed for employee " + excelPayrollId + " from "
                + this.getAddress().getStreetAddress() + " to " + excelEmp.getAddress().getStreetAddress();
            this.getAddress().setStreetAddress(excelEmp.getAddress().getStreetAddress());
            results.add(result);
        }
        if (!StringUtils.equals(this.getAddress().getCity(), excelEmp.getAddress().getCity()))
        {
            String result = "INFO: city changed for employee " + excelPayrollId + " from " + this.getAddress().getCity()
                + " to " + excelEmp.getAddress().getCity();
            this.getAddress().setCity(excelEmp.getAddress().getCity());
            results.add(result);
        }
        if (!StringUtils.equals(this.getAddress().getProvince(), excelEmp.getAddress().getProvince()))
        {
            String result = "INFO: province changed for employee " + excelPayrollId + " from "
                + this.getAddress().getProvince() + " to " + excelEmp.getAddress().getProvince();
            this.getAddress().setProvince(excelEmp.getAddress().getProvince());
            results.add(result);
        }
        if (!StringUtils.equals(this.getAddress().getPostalCode(), excelEmp.getAddress().getPostalCode()))
        {
            String result = "INFO: postal code changed for employee " + excelPayrollId + " from "
                + this.getAddress().getPostalCode() + " to " + excelEmp.getAddress().getPostalCode();
            this.getAddress().setPostalCode(excelEmp.getAddress().getPostalCode());
            results.add(result);
        }
        if ((this.getBirthDay() == null && excelEmp.getBirthDay() != null)
            || (this.getBirthDay() != null && excelEmp.getBirthDay() == null)
            || (this.getBirthDay() != null && excelEmp.getBirthDay() != null && !DateUtils.isSameDay(this.getBirthDay(),
                excelEmp.getBirthDay())))
        {
            String result = "INFO: date of birth changed for employee " + excelPayrollId + " from " + this.getBirthDay()
                + " to " + excelEmp.getBirthDay();
            this.setBirthDay(excelEmp.getBirthDay());
            results.add(result);
        }
        if (this.getSex() != excelEmp.getSex())
        {
            String result = "INFO: gendar changed for employee " + excelPayrollId + " from " + this.getSex() + " to "
                + excelEmp.getSex();
            this.setSex(excelEmp.getSex());
            results.add(result);
        }
        if (!StringUtils.equals(this.getMaritalStatus(), excelEmp.getMaritalStatus()))
        {
            String result = "INFO: marital status changed for employee " + excelPayrollId + " from "
                + this.getMaritalStatus() + " to " + excelEmp.getMaritalStatus();
            this.setMaritalStatus(excelEmp.getMaritalStatus());
            results.add(result);
        }
        if ((this.getHireDate() == null && excelEmp.getHireDate() != null)
            || (this.getHireDate() != null && excelEmp.getHireDate() == null)
            || (this.getHireDate() != null && excelEmp.getHireDate() != null && !DateUtils.isSameDay(this.getHireDate(),
                excelEmp.getHireDate())))
        {
            String result = "INFO: HireDate changed for employee " + excelPayrollId + " from " + this.getHireDate() + " to "
                + excelEmp.getHireDate();
            this.setHireDate(excelEmp.getHireDate());
            results.add(result);
        }
        if (this.getPrimaryWage().compareTo(excelEmp.getPrimaryWage()) != 0)
        {
            String result = "INFO: wage changed for employee " + excelPayrollId + " from " + this.getPrimaryWage() + " to "
                + excelEmp.getPrimaryWage();
            results.add(result);
            if (this.getPrimaryWage().compareTo(excelEmp.getPrimaryWage()) > 0)
            {
                result = "WARN: wage goes down for employee " + excelPayrollId + " from " + this.getPrimaryWage() + " to "
                    + excelEmp.getPrimaryWage();
                results.add(result);
            }
            this.setPrimaryWage(excelEmp.getPrimaryWage());
        }
        if (excelEmp.getPrimaryWage() > WAGE_THRESHOLD && !this.salariedEmployee)
        {
            String result = "WARN: employee " + excelPayrollId + " becomes a SALARY employee";
            this.setSalariedEmployee(true);
            results.add(result);
        }

        return results;
    }
}