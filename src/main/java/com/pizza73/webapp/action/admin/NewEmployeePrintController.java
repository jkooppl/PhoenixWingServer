package com.pizza73.webapp.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pizza73.model.Employee;
import com.pizza73.model.Shop;
import com.pizza73.service.EmployeeManager;
import com.pizza73.service.LookupManager;

@Controller
@RequestMapping("/newEmployeePrint.html")
public class NewEmployeePrintController extends AbstractController {
    @Autowired
    EmployeeManager employeeManager;
    @Autowired
    LookupManager lookupManager;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
            HttpServletResponse arg1) throws Exception
    {
        Integer empId = Integer.valueOf(arg0.getParameter("id"));
        Employee employee = (Employee) this.employeeManager.get(Employee.class, empId);
        ModelAndView model = new ModelAndView("newEmployeeForm");
        model.addObject("employee", employee);
        model.addObject("shop", (Shop) this.lookupManager.get(Shop.class, employee
                .getShopId()));
        return model;
    }

}
