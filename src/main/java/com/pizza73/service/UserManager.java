package com.pizza73.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.CustomerComment;
import com.pizza73.model.OnlineCustomer;

/**
 * UserManager.java TODO comment me
 * 
 * @author chris 9-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Transactional(readOnly = true)
public interface UserManager extends UniversalManager
{
    public void createCustomerEmail(OnlineCustomer customer);

    // public void updateCustomer(OnlineCustomer customer);

    public void forgotPassword(String email) throws UsernameNotFoundException;

    public OnlineCustomer findCustomer(Integer id);

    public OnlineCustomer findCustomer(String id);

    public OnlineCustomer customerForEmail(String email);

    public OnlineCustomer customerForPhone(String phone);

    // public List<OnlineCustomer> allCustomers();

    /**
     * @param customerComment
     */
    public void sendCustomerComment(CustomerComment customerComment);

    /**
     * @param oc
     */
    @Transactional(readOnly = false)
    public void updateRepeatCount(OnlineCustomer oc);

    public OnlineCustomer userForEmailAndPassword(String username, String password);
}
