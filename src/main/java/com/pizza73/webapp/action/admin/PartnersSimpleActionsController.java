package com.pizza73.webapp.action.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pizza73.service.LookupManager;

@Controller
public class PartnersSimpleActionsController {
    @Autowired
    private LookupManager lookupMgr;
    
    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String getLoginPage()
    {
        return "login";
    }

    @RequestMapping(value = "/confirmation.html", method = RequestMethod.GET)
    public String getConfirmationPage()
    {
        return "confirmation";
    }

    @RequestMapping(value = "/payrollView.html", method = RequestMethod.GET)
    public String getPayrollViewPage()
    {
        return "payrollView";
    }

    @RequestMapping(value = "/dailySales.html", method = RequestMethod.GET)
    public String getDailySalesPage()
    {
        return "dailySales";
    }

    @RequestMapping(value = "/documentation.html", method = RequestMethod.GET)
    public String getDocumentationPage()
    {
        return "documentation";
    }
    
    /*
    @RequestMapping(value = "/posreport.html", method = RequestMethod.GET)
    public String getPosReportPage()
    {
        return "posreport";
    }
    */    

    @RequestMapping(value = "/shopsSettings.html", method = RequestMethod.GET)
    public String getShopSettingsPage()
    {
        return "shopsSettings";
    }

    @RequestMapping(value = "/payrollAdmin.html", method = RequestMethod.GET)
    public String getPayrollAdmin(ModelMap map)
    {
        Integer currentPayrollYear = Integer.valueOf(this.lookupMgr
                .infoValueForVariable("payroll_year"));
        Integer currentPayrollPeriod = Integer.valueOf(this.lookupMgr
                .infoValueForVariable("payroll_period"));
        
        map.addAttribute("PAYROLL_PERIOD",currentPayrollPeriod);
        map.addAttribute("PAYROLL_YEAR",currentPayrollYear);
        return "payrollAdmin";
    }
}
