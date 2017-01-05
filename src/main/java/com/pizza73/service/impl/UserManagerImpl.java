package com.pizza73.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pizza73.dao.UserDao;
import com.pizza73.model.CustomerComment;
import com.pizza73.model.OnlineCustomer;
import com.pizza73.service.MailEngine;
import com.pizza73.service.UserManager;

/**
 * UserManagerImpl.java TODO comment me
 * 
 * @author chris 9-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
@Service("userManager")
// @Transactional(readOnly=true)
public class UserManagerImpl extends UniversalManagerImpl implements UserManager
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private MailEngine mailEngine;
    @Autowired
    private SimpleMailMessage mailMessage;
    // @Autowired
    private String templateName;
    private static final String ACCOUNT_CREATED_MESSAGE = "Thank you for signing up for a Pizza 73 account.  This will make "
        + "your online experience that much easier.  Your login in details are " + "as follows: ";
    private static final String FORGOT_PASSWORD_MESSAGE = "Your login credentials for you Pizza 73 account are as follows: ";

    private static final String LOGIN_URL = "http://www.pizza73.com/";

    private static String CUSTOMER_COMMENT = "Thank you for sending your comments to Pizza 73. We welcome comments and "
        + "feedback from our customers to ensure we continue to provide the high standard of quality service that our "
        + "customers have come to expect.  We will get back to you as soon as possible.";

    public UserManagerImpl()
    {
    }

    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pizza73.service.UserManager#createCustomer(com.pizza73.model.Customer
     * )
     */
    public void createCustomerEmail(OnlineCustomer customer)
    {
        setTemplateName("accountCreated.vm");
        mailMessage.setTo(customer.getName() + "<" + customer.getEmail() + ">");
        mailMessage.setSubject("Your pizza 73 Account has been created.");
        mailMessage.setFrom("noReply@pizza73.com");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("customer", customer);
        model.put("message", ACCOUNT_CREATED_MESSAGE);
        model.put("applicationURL", LOGIN_URL);
        mailEngine.sendMessage(mailMessage, templateName, model);
    }

    public void forgotPassword(String email) throws UsernameNotFoundException
    {
        OnlineCustomer customer = this.customerForEmail(email);
        setTemplateName("accountCreated.vm");
        customer.setPassword(customer.getPassword());

        mailMessage.setFrom("noReply@pizza73.com");
        mailMessage.setTo(customer.getName() + "<" + customer.getEmail() + ">");
        mailMessage.setSubject("Forgotten Password for Pizza 73");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("customer", customer);
        model.put("message", FORGOT_PASSWORD_MESSAGE);
        model.put("applicationURL", LOGIN_URL);
        mailEngine.sendMessage(mailMessage, templateName, model);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pizza73.service.UserManager#customerForEmail(java.lang.String)
     */
    public OnlineCustomer customerForEmail(String email)
    {
        return userDao.customerForEmail(email);
    }

    public OnlineCustomer customerForPhone(String phone)
    {
        return userDao.customerForEmail(phone);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pizza73.service.UserManager#deleteCustomer(com.pizza73.model.Customer
     * )
     */
    public void deleteCustomer(OnlineCustomer customer)
    {
        userDao.remove(OnlineCustomer.class, customer.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pizza73.service.UserManager#findCustomer(java.lang.Integer)
     */
    public OnlineCustomer findCustomer(Integer id)
    {
        return userDao.customerForId(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pizza73.service.UserManager#findCustomer(java.lang.String)
     */
    public OnlineCustomer findCustomer(String id)
    {
        return findCustomer(Integer.valueOf(id));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pizza73.service.UserManager#sendCustomerComment(com.pizza73.model
     * .OnlineCustomer)
     */
    public void sendCustomerComment(CustomerComment customer)
    {
        setTemplateName("customerComment.vm");
        mailMessage.setTo("[Online Customer Feedback] <comments@pizza73.com>");
        String from = customer.getName() + "<" + customer.getEmail() + ">";
        if (StringUtils.isBlank(customer.getEmail()) && StringUtils.isBlank(customer.getName()))
        {
            from = "noReply@pizza73.com";
        }
        else if (StringUtils.isNotBlank(customer.getEmail()) && StringUtils.isBlank(customer.getName()))
        {
            from = customer.getEmail();
        }

        mailMessage.setFrom(from);
        mailMessage.setSubject("[Online Customer Feedback]");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("customer", customer);
        String cityName = customer.getCity().getDisplayName();

        String message = CUSTOMER_COMMENT;
        if (StringUtils.isNotBlank(cityName))
        {
            message += "\n\nCity: " + cityName;
        }

        model.put("message", message);
        model.put("applicationURL", "Download dinner: http://www.pizza73.com");
        mailEngine.sendMessage(mailMessage, templateName, model);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pizza73.service.UserManager#updateRepeatCount(com.pizza73.model.
     * OnlineCustomer)
     */
    public void updateRepeatCount(OnlineCustomer oc)
    {
        this.userDao.updateRepeatCount(oc);
    }

    public void remove(Integer id)
    {
        this.userDao.remove(id);
    }

    public List<OnlineCustomer> cscEmployees()
    {
        return this.userDao.cscEmployees();
    }

    public OnlineCustomer userForEmailAndPassword(String username, String password)
    {
        return this.userDao.userForEmailAndPassword(username, password);
    }
}
