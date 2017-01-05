package com.pizza73.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza73.service.FourPakManager;
import com.pizza73.service.LookupManager;

@Service("fourPakManager")
public class FourPakManagerImpl extends UniversalManagerImpl implements FourPakManager
{
    @Autowired
    private LookupManager lookupMgr;

    public FourPakManagerImpl()
    {
    }

}
