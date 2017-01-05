package com.pizza73.webapp.action.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import org.springframework.web.servlet.mvc.CancellableFormController;

import com.pizza73.model.Driver;
import com.pizza73.model.Shop;
import com.pizza73.service.EmployeeManager;
import com.pizza73.webapp.validator.DriverFormValidator;

//NOT IN USE YET
/**
 * RegistrationFormController.java TODO comment me
 * 
 * @author chris 7-Nov-06
 * 
 * @Copyright Flying Pizza 73
 */
@Controller
@RequestMapping("/driver.html")
@SessionAttributes("driver")
public class DriverFormController extends CancellableFormController {
    @Autowired
    private EmployeeManager employeeManager;

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("driver") Driver driver,
            BindingResult result, SessionStatus status, HttpServletRequest request,
            ModelMap model)
    {// cancel action
        if (StringUtils.isNotBlank(request.getParameter("CANCEL_ACTION")))
        {
            status.setComplete();
            return "redirect:driverList.html";
        }
        // validation
        new DriverFormValidator().validate(driver, result);
        if (result.hasErrors())
            return "driver";
        status.setComplete();
        // other actions
        HttpSession session = request.getSession();
        Shop shop = (Shop) session.getAttribute("THIS_SHOP");
        driver.setShop(shop);
        this.employeeManager.saveDriver(driver);
        return "redirect:driverList.html";
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(HttpServletRequest request, ModelMap model)
    {
        Driver driver = new Driver();
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(id))
        {
            driver = employeeManager.driverForId(id);
        }
        model.addAttribute("driver", driver);
        return "driver";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder)
    {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    @ModelAttribute("YES_NO")
    public Map<Boolean, String> populateYesNo()
    {
        Map<Boolean, String> yesNo = new HashMap<Boolean, String>(2);
        yesNo.put(Boolean.TRUE, "Yes");
        yesNo.put(Boolean.FALSE, "No");
        return yesNo;
    }

    @ModelAttribute("DRIVER_STATUS")
    public Map<Character, String> populateStatus()
    {
        Map<Character, String> status = new HashMap<Character, String>(2);
        status.put('a', "Active");
        status.put('x', "Not Active");
        return status;
    }
}
