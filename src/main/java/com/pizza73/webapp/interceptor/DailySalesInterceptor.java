package com.pizza73.webapp.interceptor;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pizza73.model.Shop;
import com.pizza73.service.LookupManager;

public class DailySalesInterceptor extends HandlerInterceptorAdapter
{
    @Autowired
    private LookupManager lookupManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception
    {
        Shop thisShop = (Shop) request.getSession().getAttribute("THIS_SHOP");
        boolean isChangable = hasChangeShopPreviliage();
        String shopId = (request.getParameter("shopId"));
        Shop dailySalesShop = (Shop) request.getSession().getAttribute("DAILYSALES_SHOP");
        if (dailySalesShop == null)
        {
            dailySalesShop = thisShop;
        }
        if (StringUtils.isBlank(shopId) == false && Integer.valueOf(shopId) != dailySalesShop.getId()
            && isChangable == true)
        {
            dailySalesShop = (Shop) this.lookupManager.get(Shop.class, Integer.valueOf(shopId));
        }
        request.getSession().setAttribute("DAILYSALES_SHOP", dailySalesShop);
        request.getSession().setAttribute("HAS_CHANGE_SHOP_PREVILIAGE", Boolean.valueOf(isChangable));
        return true;
    }

    private boolean hasChangeShopPreviliage()
    {
        Collection<? extends GrantedAuthority> userAuthorities = SecurityContextHolder.getContext().getAuthentication()
            .getAuthorities();
        String role = null;
        boolean hasPreviliage = false;
        for (GrantedAuthority auth : userAuthorities)
        {
            role = auth.getAuthority();
            if (role.equalsIgnoreCase("ROLE_SUPREME") || role.equalsIgnoreCase("ROLE_ACCOUNTANT")
                || role.equalsIgnoreCase("ROLE_DSM") || role.equalsIgnoreCase("ROLE_RECEPTION") ||
                role.equalsIgnoreCase("ROLE_CSC"))
            {
                hasPreviliage = true;
                break;
            }
        }
        return hasPreviliage;
    }
}
