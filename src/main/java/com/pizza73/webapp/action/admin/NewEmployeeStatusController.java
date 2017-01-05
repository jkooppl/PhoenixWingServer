package com.pizza73.webapp.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pizza73.model.Employee;
import com.pizza73.service.EmployeeManager;

@Controller
@RequestMapping("/newEmployeeStatusChange.html")
public class NewEmployeeStatusController
{  @Autowired
   private EmployeeManager employeeManager;

   @RequestMapping(method=RequestMethod.GET)
   public String getPage(HttpServletRequest request, ModelMap model)
   {
      String id = request.getParameter("id");
      if(StringUtils.isNotBlank(id))
      {  Employee emp = this.employeeManager.employeeForId(id);
         emp.setNewEmployee(false);
         this.employeeManager.save(emp);
      }
      return "redirect:employeeList.html";
   }
}
