package com.pizza73.webapp.action.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.pizza73.service.EmployeeManager;
import com.pizza73.webapp.base.property.RoleProperty;

/**
 * EmployeeRoleFormController.java TODO comment me
 * 
 * @author chris 7-Nov-06
 * 
 * @Copyright Flying Pizza 73
 */
@Controller
@RequestMapping("/employeeRoles.html")
@SessionAttributes("employee")
public class EmployeeRoleFormController {
    @Autowired
    private EmployeeManager employeeManager;
    @Autowired
    private RoleProperty roleProperty;

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("employee") Employee employee,
            BindingResult result, SessionStatus status, HttpServletRequest request,
            ModelMap model)
    {
        if (StringUtils.isNotBlank(request.getParameter("CANCEL_ACTION")))
            ;// do nothing
        else
        {
            if (employee.getId() == null || employee.getId() == 0)
            {
                result.reject("", "You cannot add role information to an "
                        + "employee that does not exist.");
                return "employeeRoles";
            }
            this.employeeManager.save(employee);
        }
        status.setComplete();
        return "redirect:employeeList.html";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(HttpServletRequest request, ModelMap model)
    {
        Employee employee = new Employee();
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(id))
            employee = this.employeeManager.employeeForId(id);
        model.addAttribute("employee", employee);
        return "employeeRoles";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
        binder.registerCustomEditor(Role.class, roleProperty);
    }

    @ModelAttribute("ROLES")
    public List<Role> populateRole(HttpServletRequest request)
    {
        ServletContext ctx = request.getSession().getServletContext();

        List<Role> roles = new ArrayList<Role>();
        roles.add((Role) ctx.getAttribute("ROLE_SHOP_EMP"));
        roles.add((Role) ctx.getAttribute("ROLE_SHOP_POS"));
        roles.add((Role) ctx.getAttribute("ROLE_TIMESHEET"));
        roles.add((Role) ctx.getAttribute("ROLE_SHOP_CRUD"));
        roles.add((Role) ctx.getAttribute("ROLE_SHOP_MGR"));

        Role ownerRole = (Role) ctx.getAttribute("ROLE_SHOP_OWNER");
        SecurityContext secCtx = SecurityContextHolder.getContext();
        Employee emp = (Employee) secCtx.getAuthentication().getPrincipal();
        if (emp.getRoles().contains(ownerRole))
        {
            roles.add(ownerRole);
        }
        return roles;

    }
}
