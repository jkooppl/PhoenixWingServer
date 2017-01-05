package com.pizza73.webapp.action.admin.attribute;

import java.text.ParseException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.pizza73.model.DailySales;
import com.pizza73.model.Employee;
import com.pizza73.model.Shop;
import com.pizza73.service.LookupManager;
import com.pizza73.webapp.util.DateParser;

@Configurable
public class CommonAttributesInDailySalesReporting extends
        CommonAttributesInSalesReporting {
    @Autowired
    private LookupManager lookupManager;

    @ModelAttribute("EMPLOYEE")
    public Employee populateEmployee(HttpServletRequest request) throws ParseException
    {
        Calendar requestDate = DateParser.parseTrimedDate(request.getParameter("date"));
        Shop shop = (Shop) request.getSession().getAttribute("DAILYSALES_SHOP");
        DailySales dailySales = this.lookupManager.dailySalesForShopAndDate(shop.getId(),
                requestDate);
        if (dailySales == null || dailySales.isSubmitted() == false)
            return (Employee) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
        else
            return (Employee) (Employee) this.lookupManager.get(Employee.class,
                    dailySales.getEmployeeId());
    }
}
