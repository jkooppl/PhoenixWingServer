package com.pizza73.webapp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pizza73.model.Employee;
import com.pizza73.model.Shop;
import com.pizza73.service.LookupManager;

/**
 * Pizza73AuthenticationProcessingFilter.java TODO comment me
 * 
 * @author chris 10-Nov-06
 * 
 * @Copyright Flying Pizza 73
 */
public class Pizza73AuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter
{
    // @Autowired
    private LookupManager lookupManager;

    public void setLookupManager(LookupManager lMgr)
    {
        this.lookupManager = lMgr;
    }

    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//        FilterChain chain, Authentication authResult) throws IOException, ServletException
//    {
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        Authentication authResult) throws IOException, ServletException
    {
//        super.successfulAuthentication(request, response, chain, authResult);
        super.successfulAuthentication(request, response, authResult);
        Employee emp = (Employee) authResult.getPrincipal();
        Shop shop = (Shop) this.lookupManager.get(Shop.class, emp.getShopId());
        request.getSession().removeAttribute("DAILYSALES_SHOP");
        request.getSession().removeAttribute("HAS_CHANGE_SHOP_PREVILIAGE");
        request.getSession().setAttribute("THIS_SHOP", shop);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException failed) throws IOException, ServletException
    {
        super.unsuccessfulAuthentication(request, response, failed);
        request.getSession().setAttribute("error", "true");
    }
}
