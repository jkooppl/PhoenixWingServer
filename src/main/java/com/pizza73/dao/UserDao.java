package com.pizza73.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.OnlineCustomer;

/**
 * UserDao.java TODO comment me
 * 
 * @author chris 9-Sep-06
 * 
 * @Copyright Flying Pizza 73
 */
public interface UserDao extends UniversalDao
{

    /**
     * @param email
     * @return
     */
    OnlineCustomer customerForEmail(String email) throws UsernameNotFoundException;

    /**
     * @param id
     * @return
     */
    OnlineCustomer customerForId(Integer id);

    /**
     * @param oc
     */
    void updateRepeatCount(OnlineCustomer oc);

    public boolean exists(Integer id);

    public OnlineCustomer get(Integer id);

    public List<OnlineCustomer> getAll();

    public void remove(Integer id);

    public OnlineCustomer save(OnlineCustomer o);

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException;

    /**
     * @return
     */
    List<OnlineCustomer> cscEmployees();

    public OnlineCustomer userForEmailAndPassword(String username, String password);
}
