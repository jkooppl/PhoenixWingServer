package com.pizza73.webapp.action.admin;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pizza73.model.ShopPayroll;
import com.pizza73.service.PayrollManager;

/**
 * ShopHoursExportController.java TODO comment me
 * 
 * @author chris 7-Sept-06
 * 
 * @Copyright Flying Pizza 73
 */
@Controller
@RequestMapping("/exportShopTotals.html")
public class ShopHoursExportController {
    @Autowired
    private PayrollManager payrollManager;

    @RequestMapping(method = RequestMethod.GET)
    public String getPage(HttpServletRequest request, ModelMap model)
    {
        List<ShopPayroll> payrolls = new ArrayList<ShopPayroll>();

        Integer displayPayrollYear = Integer.valueOf(request.getParameter("year"));
        Integer displayPayrollPeriod = Integer.valueOf(request.getParameter("period"));

        payrolls = this.payrollManager.allPayrolls(displayPayrollPeriod,
                displayPayrollYear);

        model.addAttribute("PAYROLL_YEAR", displayPayrollYear);
        model.addAttribute("PAYROLL_PERIOD", displayPayrollPeriod);
        model.addAttribute("PAYROLL", payrolls);

        return "exportShopTotalsView";
    }
}
