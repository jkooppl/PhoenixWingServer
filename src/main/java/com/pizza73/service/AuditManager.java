package com.pizza73.service;

/**
 * AuditManager.java TODO comment me
 * 
 * @author chris 16-Oct-07
 * 
 * @Copyright Flying Pizza 73
 */
// @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public interface AuditManager extends UniversalManager
{
    public void saveAudit(String user, String message);
}
