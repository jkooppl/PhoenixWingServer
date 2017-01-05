package com.pizza73.webapp.action.admin;

import com.pizza73.model.Employee;
import com.pizza73.model.PayYearPeriod;
import com.pizza73.model.Shop;
import com.pizza73.model.ShopPayroll;
import com.pizza73.service.LookupManager;
import com.pizza73.service.PayrollManager;
import com.pizza73.webapp.base.model.SuccessMessage;
import com.pizza73.webapp.util.ABShopPaymentCalculator;
import com.pizza73.webapp.util.BCShopPaymentCalculator;
import com.pizza73.webapp.util.PayrollUtil;
import com.pizza73.webapp.util.SKShopPaymentCalculator;
import com.pizza73.webapp.util.ShopPaymentCalculator;
import com.pizza73.webapp.validator.PayrollFormValidator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * PayrollFormController.java TODO comment me
 *
 * @author chris 7-Sept-06
 * @Copyright Flying Pizza 73
 */
@Controller
@RequestMapping("/payroll.html")
@SessionAttributes("shopPayroll")
public class PayrollFormController
{
    @Autowired
    private PayrollManager payrollManager;

    @Autowired
    private LookupManager lookupManager;

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("shopPayroll") ShopPayroll payroll, BindingResult result, HttpServletRequest request, SessionStatus status, ModelMap model)
    { // cancel action
        if(StringUtils.isNotBlank(request.getParameter("CANCEL_ACTION")))
        {
            status.setComplete();
            return "redirect:employeeList.html";
        }
        // validation
        new PayrollFormValidator().validate(payroll, result);
        if(result.hasErrors())
        {
            return "payroll";
        }
        // save or submit to office action
        String action = request.getParameter("action");
        this.payrollManager.savePayroll(payroll);
        Shop shop = payroll.getShop();
        Integer payrollYear = payroll.getYear();
        Integer payPeriod = payroll.getPayPeriod();

        String actionMessage = " saved.";
        if(StringUtils.isNotBlank(action) && action.contains("Send") || StringUtils.isNotBlank(action) && action.contains("Submit"))
        {
            actionMessage = " saved and submitted.";
            shop.setPayrollYear(payrollYear);
            shop.setCurrentPayPeriod(payPeriod);
            this.lookupManager.save(shop);
        }

        SuccessMessage message = new SuccessMessage("Payroll Saved Succesfully");
        message.setMessage(shop.getName() + " payroll for " + payPeriod + " (" + payrollYear + ") " + actionMessage);
        request.getSession().setAttribute("MESSAGE", message);
        status.setComplete();

        return "redirect:confirmation.html";
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(HttpServletRequest request, ModelMap model)
    {
        ShopPayroll shopPayroll = null;
        HttpSession session = request.getSession();
        Shop thisShop = (Shop) session.getAttribute("THIS_SHOP");
        String shopId = request.getParameter("id");
        if(hasChangePayrollPriviliage())
        {
            shopPayroll = this.payrollManager.currentShopPayroll((Shop) this.lookupManager.get(Shop.class, Integer.valueOf(shopId)));
        }
        else
        {
            shopPayroll = this.payrollManager.currentShopPayroll(thisShop);
        }

        shopPayroll.setTotalRegHours(Math.round(shopPayroll.getTotalRegHours() * 100) / 100.0);
        shopPayroll.setTotalOTHours(Math.round(shopPayroll.getTotalOTHours() * 100) / 100.0);
        shopPayroll.setTotalStatHours(Math.round(shopPayroll.getTotalStatHours() * 100) / 100.0);
        Calendar now = Calendar.getInstance();
        PayYearPeriod payPeriod = lookupManager.getYearPeriodByYear(now.get(Calendar.YEAR));
        model.addAttribute("shopPayroll", shopPayroll);
        model.addAttribute("PAYROLL_YEAR", shopPayroll.getYear());
        model.addAttribute("PAYROLL_END_DATE", PayrollUtil.payrollEndDate(payPeriod, shopPayroll.getPayPeriod()));
        model.addAttribute("PAYROLL_PERIOD", shopPayroll.getPayPeriod());
        this.getLabourCostForActiveEmployeesInShop(shopPayroll, model);
        return "payroll";
    }

    private boolean hasChangePayrollPriviliage()
    {
        Collection<? extends GrantedAuthority> userAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for(GrantedAuthority auth : userAuthorities)
        {
            if(auth.getAuthority().equalsIgnoreCase("ROLE_PAYROLL") || auth.getAuthority().equalsIgnoreCase("ROLE_SUPREME"))
            {
                return true;
            }
        }
        return false;
    }

    private void getLabourCostForActiveEmployeesInShop(ShopPayroll shopPayroll, ModelMap model)
    {
        Map<String, Double> labourCosts = new HashMap<String, Double>();
        List<Employee> shopPayrollEmployees = shopPayroll.getEmployees();
        Shop shop = shopPayroll.getShop();
        ShopPaymentCalculator shopPaymentCalculator = null;
        String header = "EMPLOYEES_LABOUR_COSTS_";
        double sum = 0;
        double firstWeekCost = 0;
        double secondWeekCost = 0;
        if(shop.getProvince().equals("AB"))
        {
            shopPaymentCalculator = new ABShopPaymentCalculator();
        }
        else if(shop.getProvince().equals("BC"))
        {
            shopPaymentCalculator = new BCShopPaymentCalculator();
        }
        else if(shop.getProvince().equals("SK"))
        {
            shopPaymentCalculator = new SKShopPaymentCalculator();
        }

        Employee employee = null;
        Iterator<Employee> iter = shopPayrollEmployees.iterator();
        while (iter.hasNext())
        {
            employee = iter.next();
            // first week's labour cost
            firstWeekCost = shopPaymentCalculator.calculate(employee.getCurrentPayroll(), 1);
            labourCosts.put(header + String.valueOf(employee.getId() * 2), Double.valueOf(firstWeekCost));
            // second week's labour cost
            secondWeekCost = shopPaymentCalculator.calculate(employee.getCurrentPayroll(), 2);
            labourCosts.put(header + String.valueOf(employee.getId() * 2 + 1), Double.valueOf(secondWeekCost));
            sum += (firstWeekCost + secondWeekCost);
        }

        model.addAttribute("EMPLOYEES_LABOUR_COSTS", labourCosts);
        sum = Math.round(sum * 100) / 100.0;
        model.addAttribute("TOTAL_LABOUR_COST", Double.valueOf(sum));
    }
}
