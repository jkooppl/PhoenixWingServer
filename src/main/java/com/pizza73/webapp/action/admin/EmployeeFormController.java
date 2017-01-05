package com.pizza73.webapp.action.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import com.pizza73.model.Role;
import com.pizza73.model.Shop;
import com.pizza73.service.EmployeeManager;
import com.pizza73.service.LookupManager;
import com.pizza73.webapp.validator.EmployeeFormValidator;

/**
 * EmployeeFormController.java TODO comment me
 * 
 * @author chris 7-Nov-06
 * 
 * @Copyright Flying Pizza 73
 */
@Controller
@RequestMapping("/employee.html")
@SessionAttributes("employee")
public class EmployeeFormController {
    @Autowired
    private EmployeeManager employeeManager;

    @Autowired
    private LookupManager lookupManager;

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
        new EmployeeFormValidator().validate(employee, result);
        if (result.hasErrors())
            return "employee";
        // if this is a new employee
        if (employee.getId() == null || employee.getId() == 0)
        {
            try
            {
                this.employeeManager.employeeForUsername(employee.getEmail());
                result.reject("", "A user with the username/password: "
                        + employee.getEmail() + " already exists");
                return "employee";
            }
            catch (UsernameNotFoundException e)
            {
                // this is fine, we don't have a user with this username so
                // proceed
                // this is a new employee, set it to be a shop employee.
                employee.setPassword(employee.getEmail());
                Role role = (Role) request.getSession().getServletContext().getAttribute(
                        "ROLE_SHOP_EMP");
                Set<Role> roles = new HashSet<Role>();
                roles.add(role);
                employee.setRoles(roles);
                employeeManager.save(employee);
                model.addAttribute("employee", employee);
                model.addAttribute("shop", (Shop) this.lookupManager.get(Shop.class,
                        employee.getShopId()));
                status.setComplete();
                return "newEmployeeForm";
            }
        }
        employeeManager.save(employee);
        status.setComplete();
        return "redirect:employeeList.html";
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(HttpServletRequest request, ModelMap model)
    {
        Employee employee = new Employee();
        String id = request.getParameter("id");
        String shopId = request.getParameter("shopId");
        if (StringUtils.isNotBlank(id))
        {
            employee = this.employeeManager.employeeForId(id);
        }
        else
        {
            employee.setShopId(Integer.valueOf(shopId));
            employee.setEmail(this.employeeManager.generateNewPin());
        }
        model.addAttribute("employee", employee);
        return "employee";
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
        Map<Character, String> sex = new LinkedHashMap<Character, String>(3);
        sex.put(' ', " ");
        sex.put('m', "Male");
        sex.put('f', "Female");
        return sex;
    }

    @ModelAttribute("MARITAL_STATUS")
    public Map<String, String> populateMaritalStatus()
    {
        Map<String, String> sex = new LinkedHashMap<String, String>(3);
        sex.put(" ", " ");
        sex.put("Single", "Single");
        sex.put("Married", "Married");
        sex.put("Common-law", "Common-law");
        return sex;
    }

    @ModelAttribute("TYPE_OF_EMPLOYMENT")
    public Map<String, String> populateTypeOfEmployment()
    {
        Map<String, String> sex = new LinkedHashMap<String, String>(3);
        sex.put(" ", " ");
        sex.put("FT", "Full-time");
        sex.put("PT", "Part-time");
        return sex;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        CustomDateEditor editor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, editor);
    }
}