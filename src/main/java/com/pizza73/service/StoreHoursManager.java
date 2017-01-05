package com.pizza73.service;

import org.springframework.transaction.annotation.Transactional;

import com.pizza73.model.mobile.StoreOpen;

@Transactional(readOnly = true)
public interface StoreHoursManager extends UniversalManager
{

    public StoreOpen isStoreOpen(Integer cityId);
}
