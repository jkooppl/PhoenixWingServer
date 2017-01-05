package com.pizza73.webapp.action.admin;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/loginRedirect.html")
public class LoginRedirectController extends AbstractController
{

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception
    {
        Collection<? extends GrantedAuthority> userAuthorities = SecurityContextHolder.getContext().getAuthentication()
            .getAuthorities();
        for (GrantedAuthority auth : userAuthorities)
        {
            if (auth.getAuthority().equalsIgnoreCase("ROLE_ACCOUNTANT")
                || auth.getAuthority().equalsIgnoreCase("ROLE_DSM"))
            {
                return new ModelAndView(new RedirectView("./payrollSearch.html"));
            }
            else if (auth.getAuthority().equalsIgnoreCase("ROLE_DAILY_SALES")
                || auth.getAuthority().equalsIgnoreCase("ROLE_RECEPTION"))
            {
                return new ModelAndView(new RedirectView("./dailySales.html"));
            }
            else if (auth.getAuthority().equalsIgnoreCase("ROLE_CSC") || auth.getAuthority().equalsIgnoreCase("ROLE_CRV"))
                {
                    return new ModelAndView(new RedirectView("./complaintList.html"));
                }
        }
        return new ModelAndView(new RedirectView("./employeeList.html"));
    }

}
