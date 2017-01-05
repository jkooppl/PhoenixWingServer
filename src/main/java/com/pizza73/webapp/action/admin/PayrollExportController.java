package com.pizza73.webapp.action.admin;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pizza73.model.Shop;
import com.pizza73.model.ShopPayroll;
import com.pizza73.service.LookupManager;
import com.pizza73.service.PayrollManager;

/**
 * PayrollFormController.java TODO comment me
 * 
 * @author chris 7-Sept-06
 * 
 * @Copyright Flying Pizza 73
 */
@Controller
@RequestMapping("/exportPayroll.html")
public class PayrollExportController
{
   @Autowired
   private PayrollManager payrollManager;
   @Autowired
   private LookupManager lookupManager;
      
   @RequestMapping(method=RequestMethod.GET)
   protected String getPage(HttpServletRequest request,ModelMap model)
   {List<ShopPayroll> payrolls = new ArrayList<ShopPayroll>();
      
      Integer displayPayrollYear =Integer.valueOf(request.getParameter("year")); 
      Integer displayPayrollPeriod =  Integer.valueOf(request.getParameter("period")); 
      
      String shopId = request.getParameter("id");
      if(StringUtils.isNotBlank(shopId))
      {
         Shop shop = (Shop)this.lookupManager.get(Shop.class, Integer.valueOf(shopId));      
         ShopPayroll shopPayroll = this.payrollManager.searchForPayroll(shop, displayPayrollPeriod, displayPayrollYear);
         payrolls.add(shopPayroll);
      }
      else
      {
         payrolls = 
            this.payrollManager.allSubmittedPayrolls(
               displayPayrollPeriod, displayPayrollYear);
      }
      
      model.addAttribute("PAYROLL_YEAR", displayPayrollYear);
      model.addAttribute("PAYROLL_PERIOD", displayPayrollPeriod);
      model.addAttribute("PAYROLL", payrolls);      
      return "exportPayrollView";
   }
}

