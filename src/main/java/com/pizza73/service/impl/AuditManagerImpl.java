package com.pizza73.service.impl;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.pizza73.model.conversion.Audit;
import com.pizza73.service.AuditManager;

/**
 * AuditManagerImpl.java TODO comment me
 * 
 * @author chris 16-Oct-07
 * 
 * @Copyright Flying Pizza 73
 */
@Service("auditManager")
// @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class AuditManagerImpl extends UniversalManagerImpl implements AuditManager
{
    public AuditManagerImpl()
    {
    }

    public void saveAudit(String user, String message)
    {
        Calendar businessDate = this.businessDate();
        Audit audit = new Audit(businessDate, user, message.toString());

        this.save(audit);
    }
}
