package com.pizza73.webapp.action.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pizza73.model.Shop;
import com.pizza73.service.LookupManager;

@Controller
@RequestMapping("/payrollExport.html")
public class ShopListController {
    @Autowired
    private LookupManager lookupManager;

    @RequestMapping(method = RequestMethod.GET)
    protected String getPage(HttpServletRequest request, ModelMap model)
    {
        List<Shop> shops = this.lookupManager.activeShops();
        String selectedParollYear = request.getParameter("year");
        String selectedPayrollPeriod = request.getParameter("period");
        Integer currentPayrollYear = Integer.valueOf(this.lookupManager
                .infoValueForVariable("payroll_year"));
        Integer currentPayrollPeriod = Integer.valueOf(this.lookupManager
                .infoValueForVariable("payroll_period"));
        Integer displayPayrollYear = null;
        Integer displayPayrollPeriod = null;
        if (StringUtils.isNotBlank(selectedParollYear)
                && StringUtils.isNotBlank(selectedPayrollPeriod))
        {
            displayPayrollYear = Integer.valueOf(selectedParollYear);
            displayPayrollPeriod = Integer.valueOf(selectedPayrollPeriod);
        }
        else
        {
            displayPayrollYear = currentPayrollYear;
            displayPayrollPeriod = currentPayrollPeriod;
        }

        Iterator<Shop> iter = shops.iterator();
        Shop shop = null;
        boolean exportable = false;
        Integer maxPayPeriod = null;
        while (iter.hasNext())
        {
            shop = iter.next();
            Integer currentShopPayPeriod = shop.getCurrentPayPeriod();
            Integer currentShopPayYear = shop.getPayrollYear();
            if (currentShopPayPeriod != null && currentShopPayYear != null)
            {
                maxPayPeriod = this.lookupManager.getYearPeriodByYear(currentShopPayYear)
                        .getNumOfPayPeriodYear();
                if ((displayPayrollYear < currentShopPayYear)
                        || (displayPayrollYear.intValue() == currentShopPayYear
                                .intValue() && displayPayrollPeriod.intValue() <= currentShopPayPeriod
                                .intValue()))
                {
                    exportable = true;
                    model.addAttribute("EXPORTABLE_" + shop.getId().toString(), true);
                }
                else if ((displayPayrollPeriod.intValue() == (currentShopPayPeriod
                        .intValue() + 1))
                        || (currentShopPayPeriod.intValue() == maxPayPeriod
                                && (displayPayrollYear.intValue() == (currentShopPayYear
                                        .intValue() + 1)) && (displayPayrollPeriod
                                .intValue() == 1)))
                {
                    model.addAttribute("UPDATABLE_" + shop.getId().toString(), true);
                }
                else
                    model.addAttribute("NA_" + shop.getId().toString(), true);

            }
        }
        model.addAttribute("EXPORTABLE", exportable);
        model.addAttribute("SHOPS", shops);
        model.addAttribute("PAYROLL_YEAR", displayPayrollYear);
        model.addAttribute("PAYROLL_PERIOD", displayPayrollPeriod);

        return "shops";
    }

    @ModelAttribute("PAYROLL_YEARS")
    public List<Integer> populatePayrollYear()
    {
        Integer currentPayrollYear = Integer.valueOf(this.lookupManager
                .infoValueForVariable("payroll_year"));
        Integer currentPayrollPeriod = Integer.valueOf(this.lookupManager
                .infoValueForVariable("payroll_period"));
        List<Integer> payrollYears = new ArrayList<Integer>();
        payrollYears.add(Integer.valueOf(currentPayrollYear));
        if (currentPayrollPeriod - 10 <= 0)
            payrollYears.add(Integer.valueOf(currentPayrollYear - 1));
        return payrollYears;

    }

    @ModelAttribute("PAYROLL_PERIODS")
    public List<Integer> populatePayrollPeriod()
    {
        Integer currentPayrollPeriod = Integer.valueOf(this.lookupManager
                .infoValueForVariable("payroll_period"));
        Integer currentPayrollYear = Integer.valueOf(this.lookupManager
                .infoValueForVariable("payroll_year"));
        List<Integer> payrollPeriods = new ArrayList<Integer>();

        if (currentPayrollPeriod - 10 > 0)
        {
            for (int i = currentPayrollPeriod; i > currentPayrollPeriod - 10; i--)
            {
                payrollPeriods.add(Integer.valueOf(i));
            }
        }
        else
        {
            int count = 0;
            for (int i = currentPayrollPeriod; i > 0; i--)
            {
                count++;
                payrollPeriods.add(Integer.valueOf(i));
            }
            int payrPeriodsLastYear = this.lookupManager.getYearPeriodByYear(
                    currentPayrollYear - 1).getNumOfPayPeriodYear();
            for (int i = 10 - count; i > 0; i--)
            {
                payrollPeriods.add(payrPeriodsLastYear);
                payrPeriodsLastYear--;
            }
        }
        return payrollPeriods;
    }

}
