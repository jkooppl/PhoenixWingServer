package com.pizza73.model;

import com.pizza73.model.enums.CityEnum;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * OrderCustomer.java TODO comment me
 *
 * @author chris 22-Nov-06
 *
 * @Copyright Flying Pizza 73
 */
@Embeddable
public class OrderCustomer extends User
{
    // Fields
    private static final long serialVersionUID = -1704872613480657159L;

    @Column(name = "online_id", nullable = false)
    private Integer onlineId = new Integer(0);

    @Column(name = "phone_number_2", nullable = false, columnDefinition = "char(10)")
    private String phoneTwo = "";

    @Column(name = "caller_id", nullable = false, columnDefinition = "char(10)")
    private String callerId = "";

    @Embedded
    private Address address = new Address();

    /**
     * Default Constructor
     */
    public OrderCustomer()
    {
    }

    /**
     * @param oc
     */
    public OrderCustomer(User customer)
    {
        this.setName(customer.getName());
        this.setPhone(customer.getPhone());

    }

    /**
     * @return the callerId
     */
    public String getCallerId()
    {
        return this.callerId;
    }

    /**
     * @param callerId
     *            the callerId to set
     */
    public void setCallerId(String callerId)
    {
        this.callerId = callerId;
    }

    public Address getAddress()
    {
        return this.address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
        Municipality city = address.getCity();
        if (city != null)
        {
            if (CityEnum.EDMONTON.isEqualToCityId(city.getId()))
            {
                String sAddress = address.getStreetAddress();
                if (!(sAddress.contains(" NW") || sAddress.contains(" SW")))
                {
                    address.appendStreetDirection(null);
                }
            }
        }
    }

    /**
     * @return the onlineId
     */
    public Integer getOnlineId()
    {
        return this.onlineId;
    }

    /**
     * @param onlineId
     *            the onlineId to set
     */
    public void setOnlineId(Integer onlineId)
    {
        this.onlineId = onlineId;
    }

    /**
     * @return the phoneTwo
     */
    public String getPhoneTwo()
    {
        return this.phoneTwo;
    }

    /**
     * @param phoneTwo
     *            the phoneTwo to set
     */
    public void setPhoneTwo(String phoneTwo)
    {
        this.phoneTwo = phoneTwo;
    }

    @Override
    public boolean equals(Object o)
    {
        return super.equals(o);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("onlineId", this.onlineId)
            .append("callerId", this.callerId).append("phoneTwo", this.phoneTwo).toString();
    }
}