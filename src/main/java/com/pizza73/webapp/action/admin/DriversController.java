package com.pizza73.webapp.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pizza73.model.Driver;
import com.pizza73.model.Shop;
import com.pizza73.service.EmployeeManager;
//NOT IN USE YET
@Controller
@RequestMapping("/driverList.html")
public class DriversController
{  @Autowired
   private EmployeeManager employeeManager;

   @RequestMapping(method=RequestMethod.GET)
   public String getPage(HttpServletRequest request, ModelMap model)
   {
      HttpSession session = request.getSession();
      Shop shop = (Shop) session.getAttribute("THIS_SHOP");
      List<Driver> drivers = this.employeeManager.driversForShop(shop);
      model.addAttribute("DRIVERS", drivers);
      
      return "driverList";
   }
}
