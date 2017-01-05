package com.pizza73.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.pizza73.dao.AuditDao;

/**
 * 
 * @author chris
 * 
 */
@Repository("auditDao")
public class AuditDaoHibernate extends UniversalDaoHibernate implements AuditDao
{
    public AuditDaoHibernate()
    {
    }
}
