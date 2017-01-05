package com.pizza73.webapp.action.admin;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pizza73.model.Employee;
import com.pizza73.model.Shop;
import com.pizza73.service.EmployeeManager;

@Controller
@RequestMapping("/employeeList.html")
public class EmployeesController {
    @Autowired
    private EmployeeManager employeeManager;

    @RequestMapping(method = RequestMethod.GET)
    public String getPage(ModelMap model, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Shop shop = (Shop) session.getAttribute("THIS_SHOP");
        List<Employee> employees = this.employeeManager.hourlyEmployeesForShop(shop
                .getId());
        // compare by last name and then first name
        Collections.sort(employees);
        model.addAttribute("EMPLOYEES", employees);
        return "employeeList";
    }
}
