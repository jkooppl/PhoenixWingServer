package com.pizza73.aspect;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.pizza73.model.Order;
import com.pizza73.model.Shop;
import com.pizza73.model.enums.OrderStatus;
import com.pizza73.service.AuditManager;

//@Component
@Configurable
@Aspect
public class AuditLog
{
    private static final Logger log = Logger.getLogger(AuditLog.class);

    private AuditManager mgr;

    @Autowired(required = true)
    public void setAuditManager(AuditManager m)
    {
        this.mgr = m;
    }

    @Pointcut("execution(* com.pizza73.dao.hibernate.*DaoHibernate.loadUserByUsername(String))")
    public void logLogin()
    {
    }

    @After("logLogin()")
    public void logLogin(JoinPoint call)
    {
        String user = "";
        Object args[] = call.getArgs();
        for (int i = 0; i < args.length; i++)
        {
            Object o = args[i];
            if (o instanceof String)
            {
                user = (String) o;
                break;
            }
        }

        Calendar time = Calendar.getInstance();

        StringBuffer message = new StringBuffer();
        message.append("Login by: ").append(user).append(" - ").append(" Time: ").append(time.get(Calendar.HOUR_OF_DAY))
            .append(":").append(time.get(Calendar.MINUTE));

        log.warn(message.toString());

        if (null != mgr)
        {
            mgr.saveAudit(user, message.toString());
        }
    }

    @Pointcut("execution(* com.pizza73.service.impl.PayrollManagerImpl.*Payroll*(..))")
    public void logGet()
    {
    }

    @After("logGet()")
    public void logGet(JoinPoint call)
    {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String user = "";
        if (obj instanceof UserDetails)
        {
            user = ((UserDetails) obj).getUsername();
        }
        else
        {
            user = obj.toString();
        }

        Object args[] = call.getArgs();
        Shop shop = null;
        for (int i = 0; i < args.length; i++)
        {
            Object o = args[i];
            if (o instanceof Shop)
            {
                shop = (Shop) o;
                break;
            }
        }

        String method = call.getSignature().getName();
        String clazz = call.getSignature().getDeclaringTypeName();
        user = StringUtils.trimToEmpty(user);
        StringBuffer message = new StringBuffer(user);
        message.append(" retrieved data.  ").append(clazz).append('.').append(method);

        if (shop != null)
        {
            message.append(" Shop ID: ").append(shop.getId()).append("  Shop name: ").append(shop.getName());
        }

        log.warn(message.toString());

        if (null != mgr)
        {
            mgr.saveAudit(user, message.toString());
        }
    }

    @Pointcut("execution(* com.pizza73.service.impl.OrderManagerImpl.adminUpdate(..))")
    public void logAdminUpdate()
    {
    }

    @After("logAdminUpdate()")
    public void logAdminUpdate(JoinPoint call)
    {

        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String user = "";
        if (obj instanceof UserDetails)
        {
            user = ((UserDetails) obj).getUsername();
        }
        else
        {
            user = obj.toString();
        }
        Object args[] = call.getArgs();
        Order order = null;
        for (int i = 0; i < args.length; i++)
        {
            Object o = args[i];
            if (o instanceof Order)
            {
                order = (Order) o;
                break;
            }
        }
        Calendar time = Calendar.getInstance();

        OrderStatus status = OrderStatus.statusForShortValue(order.getStatus());
        StringBuffer message = new StringBuffer();
        message.append("ONLINE Order ").append(status.getValue()).append(": ").append(order.getId()).append(" Agent: ")
            .append(user).append(" Time: ").append(time.get(Calendar.HOUR_OF_DAY)).append(":")
            .append(time.get(Calendar.MINUTE));

        log.warn(message.toString());

        if (null != mgr)
        {
            mgr.saveAudit(user, message.toString());
        }
    }
}
