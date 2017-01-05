package com.pizza73.webapp.action.admin.attribute;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.pizza73.model.Employee;
import com.pizza73.model.Payroll;
import com.pizza73.model.Shop;
import com.pizza73.service.EmployeeManager;
import com.pizza73.service.LookupManager;
import com.pizza73.service.PayrollManager;
import com.pizza73.webapp.util.ABShopPaymentCalculator;
import com.pizza73.webapp.util.BCShopPaymentCalculator;
import com.pizza73.webapp.util.DateParser;
import com.pizza73.webapp.util.SKShopPaymentCalculator;
import com.pizza73.webapp.util.ShopPaymentCalculator;

@Configurable
public class CommonAttributesInWeeklySalesReporting extends CommonAttributesInSalesReporting
{
    @Autowired
    private LookupManager lookupManager;
    @Autowired
    private EmployeeManager employeeManager;
    @Autowired
    private PayrollManager payrollManager;

 /*   @ModelAttribute("LABOUR_COST")
    public Object populateLabourCost(HttpServletRequest request) throws ParseException
    {
        Calendar requestDate = DateParser.parseTrimedDate(request.getParameter("date"));
        Shop shop = (Shop) request.getSession().getAttribute("DAILYSALES_SHOP");
        Calendar nextPayPeriodStartDate = (Calendar) this.lookupManager.getYearPeriodByYear(shop.getPayrollYear())
            .getPayrollYearStartDate().clone();
        nextPayPeriodStartDate.add(Calendar.DATE, (shop.getCurrentPayPeriod()) * 14);
        double labourCost = 0;
        if (requestDate.before(nextPayPeriodStartDate))
            labourCost = this.getLabourCost(shop.getId(), requestDate);
        if (labourCost == 0)
            return "N/A";
        else
            return Double.valueOf(labourCost);
    }
*/
    private double getLabourCost(Integer shopId, Calendar sundayOfWeek)
    {
        List<Payroll> payrolls = this.payrollManager.payrollsForShopAndDate(shopId, sundayOfWeek);
        Iterator<Payroll> iter = payrolls.iterator();
        Payroll payroll = null;
        double sum = 0;
        while (iter.hasNext())
        {
            payroll = iter.next();
            sum += (this.getTotalpay(payroll, sundayOfWeek));
        }
        /*
         * List<Employee>
         * salariedEmployees=this.employeeManager.activeEmployeesForShop(shopId,
         * true); Iterator<Employee> employeeIter =
         * salariedEmployees.iterator(); while(employeeIter.hasNext()){
         * sum+=employeeIter.next().getPrimaryWage().doubleValue()/2.0; }
         */
        return sum;
    }

    private double getTotalpay(Payroll payroll, Calendar sundayOfWeek)
    {
        Employee employee = (Employee) this.employeeManager.get(Employee.class, payroll.getEmployeeId());
        Shop shop = (Shop) this.lookupManager.get(Shop.class, employee.getShopId());
        Integer week = null;
        ShopPaymentCalculator calculator = null;
        if (sundayOfWeek.compareTo(payroll.getPayPeriodStartDate()) == 0)
            week = 1;
        else
            week = 2;
        if (shop.getProvince().equalsIgnoreCase("AB"))
            calculator = new ABShopPaymentCalculator();
        else if (shop.getProvince().equalsIgnoreCase("SK"))
            calculator = new SKShopPaymentCalculator();
        else if (shop.getProvince().equalsIgnoreCase("BC"))
            calculator = new BCShopPaymentCalculator();
        return calculator.calculate(payroll, week);
    }
}
