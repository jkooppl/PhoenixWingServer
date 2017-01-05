package com.pizza73.webapp.action.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.pizza73.model.Employee;
import com.pizza73.model.PayYearPeriod;
import com.pizza73.model.Shop;
import com.pizza73.model.ShopPayroll;
import com.pizza73.service.LookupManager;
import com.pizza73.service.PayrollManager;
import com.pizza73.webapp.base.model.PayrollSearch;
import com.pizza73.webapp.base.property.ShopProperty;
import com.pizza73.webapp.util.ABShopPaymentCalculator;
import com.pizza73.webapp.util.BCShopPaymentCalculator;
import com.pizza73.webapp.util.PayrollUtil;
import com.pizza73.webapp.util.SKShopPaymentCalculator;
import com.pizza73.webapp.util.ShopPaymentCalculator;

/**
 *
 * @author chris
 *
 */
@Controller
@RequestMapping("/payrollSearch.html")
@SessionAttributes("payrollSearch")
public class PayrollSearchController
{
    @Autowired
    private LookupManager lookupManager;
    @Autowired
    private PayrollManager payrollManager;
    @Autowired
    private ShopProperty shopProperty;

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("payrollSearch") PayrollSearch payrollSearch, BindingResult result,
        SessionStatus status, HttpServletRequest request, ModelMap model)
    {
        Integer payrollYear = payrollSearch.getPayrollYear();
        Integer payrollPeriod = payrollSearch.getPayrollPeriod();
        Shop shop = payrollSearch.getShop();
        Integer shopPayrollYear = shop.getPayrollYear();
        Integer shopPayrollPeriod = shop.getCurrentPayPeriod();
        ShopPayroll shopPayroll = null;
        if (shopPayrollPeriod != null && shopPayrollYear != null)
        {
            if ((shopPayrollYear > payrollYear)
                || (shopPayrollYear.compareTo(payrollYear) == 0 && shopPayrollPeriod >= payrollPeriod))
            {
                shopPayroll = this.payrollManager.searchForPayroll(shop, payrollPeriod, payrollYear);
            }
        }
        if (shopPayroll == null)
        {
            result.reject("", shop.getName() + " no information available for payroll " + "period: " + payrollPeriod
                + " (" + payrollYear + ").");
            return "payrollSearch";
        }
        shopPayroll.setTotalRegHours(Math.round(shopPayroll.getTotalRegHours() * 100) / 100.0);
        shopPayroll.setTotalOTHours(Math.round(shopPayroll.getTotalOTHours() * 100) / 100.0);
        shopPayroll.setTotalStatHours(Math.round(shopPayroll.getTotalStatHours() * 100) / 100.0);

        Calendar now = Calendar.getInstance();
        PayYearPeriod payPeriod = lookupManager.getYearPeriodByYear(now.get(Calendar.YEAR));

        HttpSession session = request.getSession();
        session.setAttribute("PAYROLL", shopPayroll);
        session.setAttribute("PAYROLL_YEAR", payrollYear);
        session.setAttribute("PAYROLL_PERIOD", payrollPeriod);
        session.setAttribute("PAYROLL_END_DATE", PayrollUtil.payrollEndDate(payPeriod, payrollPeriod));
        session.setAttribute("TOTAL_LABOUR_COST", this.getLabourCostForActiveEmployeesInShop(shopPayroll));
        status.setComplete();

        return "redirect:payrollView.html";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(ModelMap model)
    {
        model.addAttribute("payrollSearch", new PayrollSearch());
        return "payrollSearch";
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
        for(int i = 0; i < 4; i++)
        {
            payrollYears.add(Integer.valueOf(currentPayrollYear - i));
        }
        return payrollYears;

    }

    @ModelAttribute("PAYROLL_PERIODS")
    public List<Integer> populatePayrollPeriod()
    {
        Integer currentPayrollPeriod = Integer.valueOf(this.lookupManager.infoValueForVariable("payroll_period"));
        Integer currentPayrollYear = Integer.valueOf(this.lookupManager.infoValueForVariable("payroll_year"));
        List<Integer> payrollPeriods = new ArrayList<Integer>();

        if (currentPayrollPeriod - 25 > 0)
        {
            for (int i = currentPayrollPeriod; i > currentPayrollPeriod - 25; i--)
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
            int payrPeriodsLastYear = this.lookupManager.getYearPeriodByYear(currentPayrollYear - 1)
                .getNumOfPayPeriodYear();
            for (int i = 25 - count; i > 0; i--)
            {
                payrollPeriods.add(payrPeriodsLastYear);
                payrPeriodsLastYear--;
            }
        }
        return payrollPeriods;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(Shop.class, shopProperty);
    }

    private Double getLabourCostForActiveEmployeesInShop(ShopPayroll shopPayroll)
    {
        List<Employee> shopPayrollEmployees = shopPayroll.getEmployees();
        Shop shop = shopPayroll.getShop();
        ShopPaymentCalculator shopPaymentCalculator = null;
        double sum = 0;
        double firstWeekCost = 0;
        double secondWeekCost = 0;
        if (shop.getProvince().equals("AB"))
            shopPaymentCalculator = new ABShopPaymentCalculator();
        else if (shop.getProvince().equals("BC"))
            shopPaymentCalculator = new BCShopPaymentCalculator();
        else if (shop.getProvince().equals("SK"))
            shopPaymentCalculator = new SKShopPaymentCalculator();
        Employee employee = null;
        Iterator<Employee> iter = shopPayrollEmployees.iterator();
        while (iter.hasNext())
        {
            employee = iter.next();
            // first week's labour cost
            firstWeekCost = shopPaymentCalculator.calculate(employee.getCurrentPayroll(), 1);
            // second week's labour cost
            secondWeekCost = shopPaymentCalculator.calculate(employee.getCurrentPayroll(), 2);
            sum += (firstWeekCost + secondWeekCost);
        }
        // List<Employee>
        // saraliedEmployees=this.employeeManager.activeEmployeesForShop(shop.getId(),
        // Boolean.TRUE);
        // for(int i=0;i<saraliedEmployees.size();i++)
        // sum+=((Employee)saraliedEmployees.get(i)).getPrimaryWage().doubleValue();
        sum = Math.round(sum * 100) / 100.0;
        return Double.valueOf(sum);
    }
}
