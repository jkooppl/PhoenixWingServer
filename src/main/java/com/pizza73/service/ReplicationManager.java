package com.pizza73.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.Coupon;
import com.pizza73.model.Order;
import com.pizza73.model.OrderItem;
import com.pizza73.model.UniqueCoupon;

/**
 * ReplicationManagerImpl.java TODO comment me
 * 
 * @author chris 27-Mar-07
 * 
 * @Copyright Flying Pizza 73
 */
@Transactional(readOnly = false)
public interface ReplicationManager extends UniversalManager
{
    @Transactional(readOnly = false, rollbackFor = DataAccessException.class)
    public void replicateOrder(Order order, List<OrderItem> items, String operator);

    @Transactional(readOnly = false, rollbackFor = DataAccessException.class)
    public void replicateOrder(Order order, List<OrderItem> items, String operator, Coupon coupon, UniqueCoupon uc);

    @Transactional(readOnly = true)
    public boolean isReplicate();
}
