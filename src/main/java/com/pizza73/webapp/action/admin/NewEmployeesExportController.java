package com.pizza73.webapp.action.admin;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pizza73.model.Employee;
import com.pizza73.model.Shop;
import com.pizza73.service.EmployeeManager;
import com.pizza73.service.LookupManager;

@Controller
@RequestMapping("/exportNewEmployees.html")
public class NewEmployeesExportController {
    @Autowired
    private EmployeeManager employeeManager;
    @Autowired
    private LookupManager lookupManager;

    @RequestMapping(method = RequestMethod.GET)
    public String getPage(HttpServletRequest request, ModelMap model)
            throws ParseException
    {
        List<Employee> allNewEmployees = this.employeeManager.getAllNewEmployees();
        List<Shop> activeShops = this.lookupManager.activeShops();
        model.addAttribute("ALL_NEW_EMPLOYEES", allNewEmployees);
        model.addAttribute("ACTIVE_SHOPS", activeShops);
        return "exportNewEmployeesView";
    }

}
