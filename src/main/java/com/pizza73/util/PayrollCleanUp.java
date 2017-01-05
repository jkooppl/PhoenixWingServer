package com.pizza73.util;

import com.pizza73.model.Employee;
import com.pizza73.model.Payroll;
import com.pizza73.model.Shop;
import com.pizza73.model.ShopPayroll;
import com.pizza73.service.EmployeeManager;
import com.pizza73.service.LookupManager;
import com.pizza73.service.PayrollManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class PayrollCleanUp
{  protected ClassPathXmlApplicationContext ctx;
   private PayrollManager payrollManager;
   private EmployeeManager employeeManager;
   private LookupManager lookupManager;

   @SuppressWarnings("unchecked")
   public void setUp()
   {
      String[] paths = {"util-applicationContext-resources.xml", "applicationContext.xml" };
      ctx = new ClassPathXmlApplicationContext(paths);
      payrollManager = (PayrollManager) ctx.getBean("payrollManager");
      employeeManager = (EmployeeManager) ctx.getBean("employeeManager");
      lookupManager =(LookupManager) ctx.getBean("lookupManager");
   }

   public static void main(String[] args) throws Exception
   {
      PayrollCleanUp pcu=new PayrollCleanUp();
      pcu.setUp();
      pcu.cleanup(16, 2008);
   }

   public void cleanup(Integer period, Integer year){
         List<Shop> shops=this.lookupManager.activeShops();
         List<ShopPayroll> shopPayrolls=this.payrollManager.allPayrolls(period, year);
         ShopPayroll temp=null;
         for(int i=0;i<shopPayrolls.size();i++){
            temp=shopPayrolls.get(i);
            if(temp.getShop().getPayrollYear()<year || (temp.getShop().getPayrollYear().compareTo(year) == 0 && temp.getShop().getCurrentPayPeriod()<period)){
               deletePayrolls(this.payrollManager.searchForPayroll(temp.getShop(), period, year), period, year);
            }
         }
   }

   private void deletePayrolls(ShopPayroll temp, Integer period, Integer year){
      if(temp == null)
         return ;
      List<Employee> employees=temp.getEmployees();
      Employee tempEmployee=null;
      Payroll tempPayroll=null;
      for(int i=0;i<employees.size();i++){
         tempEmployee=employees.get(i);
         tempPayroll=this.payrollManager.payrollForPeriod(tempEmployee.getId(), period, year);
         if(tempPayroll !=null){
         System.out.println("delete from iq2_payroll where id="+tempPayroll.getId()+";");
         }
      }
   }

}
