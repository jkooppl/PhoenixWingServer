package com.pizza73.model;

import java.io.Serializable;

public class CustomerComment implements Serializable
{

    // FIELDS
    private static final long serialVersionUID = -4743292832307830723L;
    private String jCaptcha = "";
    private String email = "";
    private String name = "";
    private Municipality city;
    private String comment = "";
    private String phone = "";

    public CustomerComment()
    {
    }

    public String getJCaptcha()
    {
        return jCaptcha;
    }

    public void setJCaptcha(String captcha)
    {
        jCaptcha = captcha;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Municipality getCity()
    {
        return city;
    }

    public void setCity(Municipality city)
    {
        this.city = city;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setCustomer(OnlineCustomer oc)
    {
        this.name = oc.getName();
        this.city = oc.getAddress().getCity();
        this.email = oc.getEmail();
        this.phone = oc.getPhone();
    }
}
