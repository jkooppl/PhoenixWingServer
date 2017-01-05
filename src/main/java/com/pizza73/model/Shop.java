package com.pizza73.model;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Shop.java TODO comment me
 *
 * @author chris 8-Sep-06
 *
 * @Copyright Flying Pizza 73
 */
@Entity
@Table(name = "iq2_shop", schema = "public", uniqueConstraints = {})
@JsonIgnoreProperties({ "redirectShop", "name", "shop", "city", "webAddress", "shortName", "phone", "province", "drivers",
        "extendedDelivery", "extendedPickup", "extendedDebit", "message", "messageTwo", "closedDelivery", "closedPickup",
        "closedReason", "currentPayPeriod", "payrollYear", "companyNumber", "branch", "quantityOfFrontCounterMachines",
        "quantityOfWirelessMachines", "newPayrollCompanyNo", "newPayrollDepartmentNo", "active", "lunchPickup", "lunchDelivery" })
public class Shop implements Serializable, Comparable<Shop>
{

    private static final long serialVersionUID = 2527834057382954581L;

    // Fields
    @Id
    @Column(name = "shop_id")
    @JsonProperty(value = "shopId")
    private Integer id;

    @Column(name = "redir_shop_id")
    private Integer redirectShop;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "shop_address")
    private String address;

    @Column(name = "phone_number")
    private String phone;

    @OneToOne
    @JoinColumn(name = "municipality_id")
    private Municipality city;

    @Column(name = "province_code")
    private String province;

    @Transient
    private final Set<Driver> drivers = new HashSet<Driver>(0);

    @Column(name = "extended_delivery_time")
    private Integer extendedDelivery;

    @Column(name = "extended_pickup_time")
    private Integer extendedPickup;

    @Column(name = "extended_debit_time")
    private Integer extendedDebit;

    @Column(name = "message")
    private String message;

    @Column(name = "message2")
    private String messageTwo;

    @Column(name = "delivery_charge")
    private Integer deliverCharge;

    @Column(name = "closed_delivery")
    private boolean closedDelivery;

    @Column(name = "closed_pickup")
    private boolean closedPickup;

    @Column(name = "closed_reason")
    private String closedReason;

    @Column(name = "current_pay_period")
    private Integer currentPayPeriod;

    @Column(name = "payroll_year")
    private Integer payrollYear;

    @Column(name = "ee_company_no")
    private String companyNumber;

    @Column(name = "ee_branch_no")
    private Integer branch;

    @Column(name = "quantity_of_front_counter_machines")
    private Integer quantityOfFrontCounterMachines = 1;

    @Column(name = "quantity_of_wireless_machines")
    private Integer quantityOfWirelessMachines = 12;

    @Column(name = "new_payroll_company_no")
    private String newPayrollCompanyNo;

    @Column(name = "new_payroll_department_no")
    private String newPayrollDepartmentNo;

    @Column(name = "shop_active")
    private char active;

    @Column(name = "lat")
    private Float latitude;

    @Column(name = "long")
    private Float longitude;

    @Column(name= "lunch_delivery")
    private boolean lunchDelivery;

    @Column(name= "lunch_pickup")
    private boolean lunchPickup;

    // Constructors
    /** default constructor */
    public Shop()
    {
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Shop o)
    {
        final Shop s1 = o;
        if (this.shortName != null && s1.getShortName() != null)
        {
            return getShortName().compareTo(s1.getShortName());
        }
        return 0;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Shop))
            return false;

        final Shop shop = (Shop) o;

        if (this.id != null ? !this.id.equals(shop.getId()) : shop.getId() != null)
            return false;

        return true;
    }

    public char getActive()
    {
        return this.active;
    }

    public Integer getBranch()
    {
        return this.branch;
    }

    /**
     * @return the city
     */
    public Municipality getCity()
    {
        return this.city;
    }

    public String getAddress()
    {
        return this.address.trim();
    }

    public String getClosedReason()
    {
        return this.closedReason;
    }

    public String getCompanyNumber()
    {
        return this.companyNumber;
    }

    public Integer getCurrentPayPeriod()
    {
        return this.currentPayPeriod;
    }

    public Integer getDeliveryCharge()
    {
        return this.deliverCharge;
    }

    public Set<Driver> getDrivers()
    {
        return this.drivers;
    }

    /**
     * @return the extendedDebit
     */
    public Integer getExtendedDebit()
    {
        return this.extendedDebit;
    }

    /**
     * @return the extendedDelivery
     */
    public Integer getExtendedDelivery()
    {
        return this.extendedDelivery;
    }

    /**
     * @return the extendedPickup
     */
    public Integer getExtendedPickup()
    {
        return this.extendedPickup;
    }

    // Property accessors
    @JsonProperty(value = "shopId")
    public Integer getId()
    {
        return this.id;
    }

    public Float getLatitude()
    {
        return this.latitude;
    }

    public Float getLongitude()
    {
        return this.longitude;
    }

    /**
     * @return the message
     */
    public String getMessage()
    {
        return StringUtils.trimToEmpty(this.message);
    }

    /**
     * @return the messageTwo
     */
    public String getMessageTwo()
    {
        return StringUtils.trimToEmpty(this.messageTwo);
    }

    public String getName()
    {
        return this.name;
    }

    public String getNewPayrollCompanyNo()
    {
        return this.newPayrollCompanyNo;
    }

    public String getNewPayrollDepartmentNo()
    {
        return this.newPayrollDepartmentNo;
    }

    public Integer getPayrollYear()
    {
        return this.payrollYear;
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

    public Integer getQuantityOfFrontCounterMachines()
    {
        return this.quantityOfFrontCounterMachines;
    }

    public Integer getQuantityOfWirelessMachines()
    {
        return this.quantityOfWirelessMachines;
    }

    public Integer getRedirectShop()
    {
        return this.redirectShop;
    }

    public String getShop()
    {
        return this.address;
    }

    public String getShortName()
    {
        return StringUtils.trimToEmpty(this.shortName);
    }

    // Convenience method for retrieving address for display on web site.
    public String getWebAddress()
    {
        String webAddress = this.address.trim();// + ", " + this.city.getDisplayName();
        if (this.id == 21)
        {
            webAddress += " (Red Deer Downtown)";
        }
        else if(this.id == 20)
        {
            webAddress += " (Red Deer North)";
        }
        return webAddress;
    }

    @Override
    public int hashCode()
    {
        int result;
        result = (this.id != null ? this.id.hashCode() : 0);

        return result;
    }

    public boolean isClosedDelivery()
    {
        return this.closedDelivery;
    }

    public boolean isClosedPickup()
    {
        return this.closedPickup;
    }

    public void setActive(final char active)
    {
        this.active = active;
    }

    public void setClosedDelivery(final boolean closedDelivery)
    {
        this.closedDelivery = closedDelivery;
    }

    public void setClosedPickup(final boolean closedPickup)
    {
        this.closedPickup = closedPickup;
    }

    public void setClosedReason(final String closedReason)
    {
        this.closedReason = closedReason;
    }

    public void setCurrentPayPeriod(final Integer period)
    {
        this.currentPayPeriod = period;
    }

    public void setExtendedDebit(final Integer extendedDebit)
    {
        this.extendedDebit = extendedDebit;
    }

    public void setExtendedDelivery(final Integer extendedDelivery)
    {
        this.extendedDelivery = extendedDelivery;
    }

    public void setExtendedPickup(final Integer extendedPickup)
    {
        this.extendedPickup = extendedPickup;
    }

    public void setLatitude(final Float latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(final Float longitude)
    {
        this.longitude = longitude;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }

    public void setMessageTwo(final String messageTwo)
    {
        this.messageTwo = messageTwo;
    }

    public void setNewPayrollCompanyNo(final String newPayrollCompanyNo)
    {
        this.newPayrollCompanyNo = newPayrollCompanyNo;
    }

    public void setNewPayrollDepartmentNo(final String newPayrollDepartmentNo)
    {
        this.newPayrollDepartmentNo = newPayrollDepartmentNo;
    }

    public void setPayrollYear(final Integer year)
    {
        this.payrollYear = year;
    }

    public void setQuantityOfFrontCounterMachines(final Integer quantityOfFrontCounterMachines)
    {
        this.quantityOfFrontCounterMachines = quantityOfFrontCounterMachines;
    }

    public void setQuantityOfWirelessMachines(final Integer quantityOfWirelessMachines)
    {
        this.quantityOfWirelessMachines = quantityOfWirelessMachines;
    }

    public void setRedirectShop(final Integer redirectShop)
    {
        this.redirectShop = redirectShop;
    }

    public boolean isLunchDelivery()
    {
        return lunchDelivery;
    }

    public void setLunchDelivery(boolean lunchDelivery)
    {
        this.lunchDelivery = lunchDelivery;
    }

    public boolean isLunchPickup()
    {
        return lunchPickup;
    }

    public void setLunchPickup(boolean lunchPickup)
    {
        this.lunchPickup = lunchPickup;
    }

    @Override
    public String toString()
    {
        return this.shortName;
    }
}