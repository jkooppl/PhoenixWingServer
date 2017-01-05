package com.pizza73.service;

import com.pizza73.model.OnlineCustomer;
import com.pizza73.ws.industrymailout.subscriber.LoginResponse;

public interface SubscriberService
{
    public LoginResponse login();

    public boolean signupNewSubscriber(final OnlineCustomer customer);
}
