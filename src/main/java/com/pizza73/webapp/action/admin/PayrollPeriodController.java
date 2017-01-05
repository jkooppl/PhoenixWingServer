package com.pizza73.webapp.action.admin;

import com.pizza73.model.Info;
import com.pizza73.model.Shop;
import com.pizza73.service.LookupManager;
import com.pizza73.webapp.base.model.PayrollSearch;
import com.pizza73.webapp.base.property.ShopProperty;
import edu.emory.mathcs.backport.java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chris
 */
@Controller
@RequestMapping("/payrollPeriod.html")
@SessionAttributes("payrollPeriod")
public class PayrollPeriodController
{
    @Autowired
    private LookupManager lookupManager;

    @Autowired
    private ShopProperty shopProperty;

    private static final int PERIOD_INTERVAL = 3;

    private static final String PAYROLL_PERIOD = "payroll_period ";

    private static final String PAYROLL_YEAR = "payroll_year ";

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@RequestParam(value = "action") String action, @ModelAttribute("payrollPeriod") PayrollSearch payrollPeriod,
        BindingResult result, SessionStatus status, HttpServletRequest request, ModelMap model)
    {
        String message = "";
        Integer payrollYear = payrollPeriod.getPayrollYear();
        Integer period = payrollPeriod.getPayrollPeriod();
        Shop shop = payrollPeriod.getShop();
        if(shop != null)
        {
            shop.setPayrollYear(payrollYear);
            shop.setCurrentPayPeriod(period);
            lookupManager.save(shop);

            message = "Shop " + shop.getId() + " payroll period updated to period: " + period + " year:" + payrollYear;
        }
        else
        {
            Info info = new Info(PAYROLL_PERIOD, period.toString());
            lookupManager.update(info);
            info = new Info(PAYROLL_YEAR, payrollYear.toString());
            lookupManager.update(info);

            message = "Payroll period updated to period: " + period + " year:" + payrollYear;
        }

        HttpSession session = request.getSession();
        session.setAttribute("MESSAGE", message);
        status.setComplete();
        return "redirect:payrollAdmin.html";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(@RequestParam(value = "action", required = false) String action, ModelMap model)
    {
        if(null != action)
        {
            model.addAttribute("ACTION", "SHOP");
            System.out.println("ACTION is shop");
        }

        PayrollSearch payrollSearch = new PayrollSearch();
        Integer currentPayrollYear = Integer.valueOf(this.lookupManager.infoValueForVariable("payroll_year"));
        Integer currentPayrollPeriod = Integer.valueOf(this.lookupManager.infoValueForVariable("payroll_period"));
        payrollSearch.setPayrollPeriod(currentPayrollPeriod);
        payrollSearch.setPayrollYear(currentPayrollYear);

        model.addAttribute("payrollPeriod", payrollSearch);
        return "payrollPeriod";
    }

    @ModelAttribute("SHOPS")
    public List<Shop> populateShops()
    {
        return this.lookupManager.activeShops();
    }

    @ModelAttribute("PAYROLL_YEARS")
    public List<Integer> populatePayrollYear()
    {
        Integer currentPayrollYear = Integer.valueOf(this.lookupManager.infoValueForVariable("payroll_year"));
        Integer currentPayrollPeriod = Integer.valueOf(this.lookupManager.infoValueForVariable("payroll_period"));
        List<Integer> payrollYears = new ArrayList<Integer>();
        payrollYears.add(Integer.valueOf(currentPayrollYear));
        if(currentPayrollPeriod - 3 <= 0)
        {
            payrollYears.add(Integer.valueOf(currentPayrollYear - 1));
        }
        if(currentPayrollPeriod - 24 >= 0)
        {
            payrollYears.add(Integer.valueOf(currentPayrollYear + 1));
        }
        return payrollYears;

    }

    @ModelAttribute("PAYROLL_PERIODS")
    public List<Integer> populatePayrollPeriod()
    {
        Integer currentPayrollPeriod = Integer.valueOf(this.lookupManager.infoValueForVariable("payroll_period"));
        List<Integer> payrollPeriods = new ArrayList<Integer>();

        for(int i = 0; i <= PERIOD_INTERVAL; i++)
        {
            Integer period = Integer.valueOf((currentPayrollPeriod + i) % 26);
            if(period == 0)
            {
                period = 26;
            }
            payrollPeriods.add(period);
        }

        for(int i = 1; i <= PERIOD_INTERVAL; i++)
        {
            Integer period = Integer.valueOf((currentPayrollPeriod - i) % 26);
            payrollPeriods.add(period);
        }
        Collections.sort(payrollPeriods);

        return payrollPeriods;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(Shop.class, shopProperty);
    }

}
