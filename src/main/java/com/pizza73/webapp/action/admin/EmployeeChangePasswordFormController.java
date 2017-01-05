package com.pizza73.webapp.action.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.pizza73.model.Employee;
import com.pizza73.service.EmployeeManager;
import com.pizza73.webapp.validator.LoginIdChangeValidator;

//NOT IN USE YET

/**
 * EmployeeFormController.java TODO comment me
 * 
 * @author chris 7-Nov-06
 * 
 * @Copyright Flying Pizza 73
 */
@Controller
@RequestMapping("/employeeLoginChange.html")
@SessionAttributes("employee")
public class EmployeeChangePasswordFormController {
    @Autowired
    private EmployeeManager employeeManager;

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("employee") Employee employee,
            BindingResult result, SessionStatus status, HttpServletRequest request,
            ModelMap model)
    {
        if (StringUtils.isNotBlank(request.getParameter("CANCEL_ACTION")))
        {
            status.setComplete();
            return "redirect:employeeList.html";
        }
        new LoginIdChangeValidator().validate(employee, result);
        if (result.hasErrors())
            return "employeeChangePassword";
        try
        {
            this.employeeManager.employeeForUsername(employee.getOldPassword());
        }
        catch (UsernameNotFoundException e)
        {
            result.reject("", "No user with Login Id: " + employee.getOldPassword()
                    + " exists");
            return "employeeChangePassword";
        }
        employee.setPassword(employee.getEmail());
        this.employeeManager.save(employee);
        status.setComplete();
        return "redirect:employeeList.html";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(HttpServletRequest request, ModelMap model)
    {
        Employee employee = new Employee();
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(id))
        {
            employee = this.employeeManager.employeeForId(id);
        }
        model.addAttribute("employee", employee);
        return "employeeChangePassword";
    }

    @ModelAttribute("YES_NO")
    public Map<Boolean, String> populateYes_No()
    {
        Map<Boolean, String> yesNo = new HashMap<Boolean, String>(2);
        yesNo.put(Boolean.TRUE, "Yes");
        yesNo.put(Boolean.FALSE, "No");
        return yesNo;
    }

    @ModelAttribute("SEX")
    public Map<Character, String> populateSex()
    {
        Map<Character, String> sex = new HashMap<Character, String>(2);
        sex.put('m', "Male");
        sex.put('f', "Female");
        return sex;
    }
}
